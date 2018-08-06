package com.mqfdy.code.designer.wizards;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.UUID;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import com.mqfdy.code.designer.editor.utils.Logger;
import com.mqfdy.code.model.utils.DateTimeUtil;

public class MicroObjectModelNewWizard extends Wizard implements INewWizard {

	private ISelection selection;

	private MicroObjectModelNewWizardPage omNewWizardPage;

	public MicroObjectModelNewWizard() {
		super();
		setWindowTitle("创建业务对象模型");
		setNeedsProgressMonitor(true);
	}

	public void init(IWorkbench workbench, IStructuredSelection selection) {
		this.selection = selection;
	}

	@Override
	public void addPages() {
		omNewWizardPage = new MicroObjectModelNewWizardPage(selection);
		addPage(omNewWizardPage);
	}

	@Override
	public boolean performFinish() {
		omNewWizardPage.autoSetOmModelPath();
		final String filePath = omNewWizardPage.getFilePath();
		final String fullFilePath = omNewWizardPage.getFullFilePath();
		final String fileName = omNewWizardPage.getFullFileName();
		final String modelName = omNewWizardPage.getModelName();
		final String namespace = omNewWizardPage.getNamespace();
		final String modelDisplayName = omNewWizardPage.getModelDisplayName();
		final String packageName = omNewWizardPage.getModelName();
		final IProject project = omNewWizardPage.getProject();
		final String packageDisplayName = omNewWizardPage.getModelDisplayName();
		// final IFile file;
		// IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		// IResource resource = root.findMember(new Path(filePath));
		// MessageDialog dialog = new
		// MessageDialog(this.omNewWizardPage.getShell(), "提示", null,
		// "文件已存在，是否覆盖？", MessageDialog.QUESTION, new String[]{"覆盖","取消"}, 0);
		// IContainer container = (IContainer) resource;
		// file = container.getFile(new Path(fileName));
		// if (file.exists()) {
		// if(dialog.open() == IDialogConstants.OK_ID){
		// }
		// else
		// return false;
		// }

		IRunnableWithProgress progress = new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor)
					throws InvocationTargetException, InterruptedException {
				try {
					doFinish(filePath, fullFilePath, fileName, modelName,
							modelDisplayName, packageName, packageDisplayName,
							monitor, project, namespace);
				} catch (CoreException e) {
					Logger.log(e);
					throw new InvocationTargetException(e);
				} finally {
					monitor.done();
				}
			}
		};

		try {
			getContainer().run(true, false, progress);
		} catch (InterruptedException e) {
			Logger.log(e);
			return false;
		} catch (InvocationTargetException e) {
			Throwable realException = e.getTargetException();
			MessageDialog.openError(getShell(), "运行出错",
					realException.getMessage());
			Logger.log(realException);
			return false;
		}
		return true;
	}

	private void doFinish(String filePath, String fullFilePath,
			String fileName, String modelName, String modelDisplayName,
			String packageName, String packageDisplayName,
			IProgressMonitor monitor, IProject project, String namespace)
			throws CoreException {
		final IFile file;
		{// 创建元数据文件
			monitor.beginTask("元数据文件[" + fileName + "]创建中...", 2);
			File omDir = new File(fullFilePath + File.separator);
			if (!omDir.exists()) {
				omDir.mkdirs();// 创建单个目录
				project.refreshLocal(IResource.DEPTH_INFINITE,
						new NullProgressMonitor());
			}
			IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
			IResource resource = root.findMember(new Path(filePath));
			// if (!resource.exists() || !(resource instanceof IContainer)) {
			// String errorMessage = "文件存放路径[ \"" + filePath + "\"] 不存在 !";
			// IStatus status = new Status(IStatus.ERROR,
			// getClass().getName(), IStatus.OK, errorMessage, null);
			// throw new CoreException(status);
			// }
			IContainer container = (IContainer) resource;
			file = container.getFile(new Path(fileName));
			try {
				InputStream stream = generateFileContent(modelName,
						modelDisplayName, packageName, packageDisplayName,
						namespace);
				if (file.exists()) {
					file.setContents(stream, true, true, monitor);
				} else {
					file.create(stream, true, monitor);
				}
				stream.close();
			} catch (IOException e) {
				Logger.log(e);
			}
			monitor.worked(1);
		}

		{// 打开元数据文件
			monitor.setTaskName("元数据文件[" + fileName + "]打开中...");
			getShell().getDisplay().asyncExec(new Runnable() {
				public void run() {
					IWorkbenchPage page = PlatformUI.getWorkbench()
							.getActiveWorkbenchWindow().getActivePage();
					try {
						IDE.openEditor(page, file, true);
					} catch (PartInitException e) {
						Logger.log(e);
					}
				}
			});
			monitor.worked(1);
		}
	}

	/**
	 * 生成文件初始化内容
	 * 
	 * @param namespace
	 * 
	 * @return
	 */
	private InputStream generateFileContent(String modelName,
			String modelDisplayName, String packageName,
			String packageDisplayName, String namespace) {
		Document document = new Document();

		Element modelElement = new Element("Model");
		modelElement.setAttribute("id", generateUUID());
		modelElement.setAttribute("name", modelName);
		modelElement.setAttribute("nameSpace", namespace);
		modelElement.setAttribute("displayName", modelDisplayName);

		// VersionInfo
		Element versionInfoElement = new Element("VersionInfo");
		versionInfoElement.setAttribute("id", generateUUID());
		versionInfoElement.addContent(new Element("Version").setText("1.0"));
		versionInfoElement.addContent(new Element("CreatedTime")
				.setText(DateTimeUtil.date2String(new Date())));
		versionInfoElement.addContent(new Element("ChangedTime")
				.setText(DateTimeUtil.date2String(new Date())));
		versionInfoElement.addContent(new Element("Creator").setText(System
				.getProperty("user.name")));
		versionInfoElement.addContent(new Element("Modifier").setText(System
				.getProperty("user.name")));
		versionInfoElement.addContent(new Element("Description")
				.setText("This is a demo model."));
		modelElement.addContent(versionInfoElement);

		// Package
		Element packageElement = new Element("Package");
		packageElement.setAttribute("id", generateUUID());
		packageElement.setAttribute("name", packageName);
		packageElement.setAttribute("displayName", packageDisplayName);
//		packageElement.addContent(new Element("BusinessClasses"));
//		packageElement.addContent(new Element("Associations"));
//		packageElement.addContent(new Element("Inheritances"));
//		packageElement.addContent(new Element("Annotations"));
//		packageElement.addContent(new Element("Links"));
		Element dia = new Element("Diagrams");
		Element diaele = new Element("Diagram");
		diaele.setAttribute("id", generateUUID());
		diaele.setAttribute("name", modelName);
		diaele.setAttribute("displayName", modelDisplayName);
		dia.addContent(diaele);
		Element diastyle = new Element("DiagramStyle");
		Element backGroundColor = new Element("BackGroundColor");
		Element gridStyle = new Element("GridStyle");
		Element zoomScale = new Element("ZoomScale");
		backGroundColor.addContent("white");
		gridStyle.addContent("false");
		zoomScale.addContent("100%");
		diastyle.addContent(backGroundColor);
		diastyle.addContent(gridStyle);
		diastyle.addContent(zoomScale);
		diaele.addContent(diastyle);
		packageElement.addContent(dia);
		modelElement.addContent(packageElement);

		document.setRootElement(modelElement);

		Format format = Format.getPrettyFormat();
		format.setEncoding("utf-8");
		format.setIndent("    ");
		XMLOutputter xmlOutputter = new XMLOutputter(format);
		return new ByteArrayInputStream(xmlOutputter.outputString(document)
				.getBytes());
	}

	public static String generateUUID() {
		return UUID.randomUUID().toString().replace("-", "");
	}
}
