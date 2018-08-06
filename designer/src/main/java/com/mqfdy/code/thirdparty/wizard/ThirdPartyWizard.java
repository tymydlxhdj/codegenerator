package com.mqfdy.code.thirdparty.wizard;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;

import com.mqfdy.code.designer.editor.utils.EditorOperation;
import com.mqfdy.code.reverse.IOmReverse;
import com.mqfdy.code.reverse.OmReverse;
import com.mqfdy.code.reverse.ReverseContext;
import com.mqfdy.code.reverse.ReverseException;
import com.mqfdy.code.reverse.utils.ReverseUtil;
import com.mqfdy.code.reverse.views.pages.DuplicateNameWizardPage;
import com.mqfdy.code.reverse.views.pages.OmNameReverseStrategyPage;
import com.mqfdy.code.reverse.views.pages.OmSelectWizardPage;
import com.mqfdy.code.reverse.views.pages.PackageDispachPage;
import com.mqfdy.code.reverse.views.pages.SpecialTableWizardPage;
import com.mqfdy.code.thirdparty.OmImport;

public class ThirdPartyWizard extends Wizard implements INewWizard {
	private ChooseFileWizardPage cfPage;
	private TableStructureWizardPage tsPage;
	private SpecialTableWizardPage stPage;
	private OmSelectWizardPage osPage;
	private DuplicateNameWizardPage dnPage;
	private OmNameReverseStrategyPage onrsPage;
	private PackageDispachPage pdPage;
	
	private OmImport omImport;
	private IOmReverse omReverse = new OmReverse();
	
	public static final int TOTAL_WORK = 5;
	
	private boolean isFinish = false;
	
	public boolean isFinish() {
		return isFinish;
	}

	public void setFinish(boolean isFinish) {
		this.isFinish = isFinish;
	}
	
	public ThirdPartyWizard(){
		super();
		setWindowTitle("导入PDM文件");
		setNeedsProgressMonitor(true);
		omImport = new OmImport();
	}
	
	public void init(IWorkbench arg0, IStructuredSelection arg1) {
		setWindowTitle("pdm反向生成om - 向导");
	}

	@Override
	public boolean performFinish() {
		final IWorkbenchPage[] pages = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getPages();
		try {
			ProgressMonitorDialog progressMonitorDialog = new ProgressMonitorDialog(pdPage.getShell());
			progressMonitorDialog.run(true, false, new IRunnableWithProgress() {
				public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
					monitor.beginTask("正在转换到om中......", TOTAL_WORK);
					doFinish(pages, monitor);
					monitor.done();
				}
			});
			
			//MessageDialog.openInformation(getShell(), "成功", "转换成功! 共转换表" + ReverseContext.btMap.size() + "个");
			MessageDialog.openInformation(getShell(), "成功", "转换成功!");
			IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
			
			IPath path = new Path(osPage.getOmPath());
			path = path.makeRelativeTo(root.getLocation());
			IResource resource = root.findMember(path);
			IContainer container = (IContainer) resource;
			IFile file = container.getFile(new Path(osPage.getOmName()));
			IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
			IDE.openEditor(page, file, true);
		} catch (Exception e) {
			MessageDialog.openError(pdPage.getShell(), "Error", e.getMessage());
			throw new RuntimeException(e.getMessage(), e);
		}
		EditorOperation.refreshEditorByFile(ReverseContext.OM_STORAGE_PATH, pages);
		
		return true;
	}

	@Override
	public boolean canFinish() {
		if(isFinish) {
			return super.canFinish();
		}
		return false;
	}
	
	@SuppressWarnings("deprecation")
	private void doFinish(IWorkbenchPage[] pages, IProgressMonitor monitor) {
		omImport.createBom(ReverseContext.bom, monitor, pdPage);
		
		String filepath = osPage.getOmPath();
		IPath path = new Path(filepath);
		IResource res = ResourcesPlugin.getWorkspace().getRoot().findFilesForLocation(path)[0];
		try {
			res.getProject().refreshLocal(IResource.DEPTH_INFINITE, null);
		} catch (CoreException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		
//		EditorOperation.refreshEditorByFile(ReverseContext.OM_STORAGE_PATH, pages);
	}

	@Override
	public void addPages() {
		cfPage = new ChooseFileWizardPage("cfPage");
		addPage(cfPage);
		
		tsPage = new TableStructureWizardPage("tsPage");
		addPage(tsPage);
		
		stPage = new SpecialTableWizardPage("stPage");
		addPage(stPage);
		
		/*osPage = new OmSelectWizardPage("osPage");
		addPage(osPage);*/
		
		dnPage = new DuplicateNameWizardPage("dnPage");
		addPage(dnPage);
		
		onrsPage = new OmNameReverseStrategyPage("onrsPage");
		addPage(onrsPage);
		
		pdPage = new PackageDispachPage("pdPage");
		addPage(pdPage);
		
	}

	@Override
	public void dispose() {
		omReverse.clearMemery();
	}
	
	
	
}
