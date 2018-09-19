package com.mqfdy.code.designer.wizards;

import java.io.File;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;
import org.eclipse.ui.model.WorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;

import com.mqfdy.code.resource.validator.KeyWordsChecker;
import com.mqfdy.code.resource.validator.ValidatorUtil;
import com.mqfdy.code.utils.ProjectUtil;

// TODO: Auto-generated Javadoc
/**
 * The Class MicroObjectModelNewWizardPage.
 *
 * @author mqfdy
 */
public class MicroObjectModelNewWizardPage extends WizardPage {

	/** The Constant OM_FILE_TYPE. */
	public static final String OM_FILE_TYPE = ".bom";
	
	/** The Constant MODEL_DEFAULT_NAME. */
	// public static final String OM_FILE_DEFAULT_NAME = "md-om";
	public static final String MODEL_DEFAULT_NAME = "com.orgname.projectname";
	
	/** The Constant PACKAGE_DEFAULT_NAME. */
	public static final String PACKAGE_DEFAULT_NAME = "demo";

	/** The selection. */
	private ISelection selection;

	/** The file path text. */
	private Text filePathText;
	
	/** The namespace text. */
	private Text namespaceText;
	
	/** The model name text. */
	private Text modelNameText;
	
	/** The model display name text. */
	private Text modelDisplayNameText;
	
	/** The model display name. */
	private String modelDisplayName;
	
	/** The project. */
	private IProject project = null;
	
	/** The des project. */
	private IProject desProject = null;

	/**
	 * Instantiates a new micro object model new wizard page.
	 *
	 * @param selection
	 *            the selection
	 */
	protected MicroObjectModelNewWizardPage(ISelection selection) {
		super("omNewWizardPage");
		setTitle("BOM文件");
		setDescription("新建一个业务对象模型文件");
		this.selection = selection;
	}

	/**
	 * Creates the control.
	 *
	 * @author mqfdy
	 * @param parent
	 *            the parent
	 * @Date 2018-09-03 09:00
	 */
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		container.setLayout(layout);
		layout.numColumns = 3;
//		layout.verticalSpacing = 9;
		GridData gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		//GridData的组件占两列显示
		gridData.horizontalSpan = 2;
		// 元数据文件存放路径设置
		Label filePathlabel = new Label(container, SWT.NULL);
		filePathlabel.setText("文件存放路径：");

		filePathText = new Text(container, SWT.BORDER | SWT.SINGLE
				| SWT.READ_ONLY);
		GridData gd_filePathText = new GridData(GridData.FILL_HORIZONTAL);
		filePathText.setLayoutData(gd_filePathText);
		if (selection != null && !selection.isEmpty()
				&& selection instanceof IStructuredSelection) {
			Object object = ((IStructuredSelection) selection)
					.getFirstElement();
			if (object instanceof IProject) {
				project = (IProject) object;
			} else if (object instanceof IJavaProject) {
				project = ((IJavaProject) object).getProject();
			} else if (object instanceof IFolder) {
				project = ((IFolder) object).getProject();
			} else if (object instanceof IFile) {
				project = ((IFile) object).getProject();
			}
		}
		if (project != null && ProjectUtil.isBOMProject(project))
			filePathText.setText("/" + project.getName() + "/model/bom");

		Button browseButton = new Button(container, SWT.PUSH);
		browseButton.setText("浏览...");
		browseButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				handleSelect();
			}
		});
		

		// 元数据参数设置
		// 模型名称
		Label namespaceLabel = new Label(container, SWT.NULL);
		namespaceLabel.setText("命名空间：");
		namespaceText = new Text(container, SWT.BORDER | SWT.SINGLE);
		namespaceText.setLayoutData(gridData);
		Label modelNamelabel = new Label(container, SWT.NULL);
		modelNamelabel.setText("模型名称：");
		modelNameText = new Text(container, SWT.BORDER | SWT.SINGLE);
		modelNameText.setLayoutData(gridData);
		modelNameText.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				if (modelDisplayName == null)
					modelDisplayNameText.setText(modelNameText.getText());
			}
		});

		// 模型显示名称
		Label modelDisplayNamelabel = new Label(container, SWT.NULL);
		modelDisplayNamelabel.setText("显示名称：");

		modelDisplayNameText = new Text(container, SWT.BORDER | SWT.SINGLE);
		modelDisplayNameText.setLayoutData(gridData);
		modelDisplayNameText.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				modelDisplayName = modelDisplayNameText.getText();
			}
		});
		

		// 初始化监听器
		initializeListener();

		setControl(container);
		validateData();
//		parent.getShell().setSize(500, 230);
	}
	
	/**
	 * 处理选择模块.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	private void handleSelect() {
		
		// 取得当前的shell
		Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
		
		ElementTreeSelectionDialog dialog = new ElementTreeSelectionDialog(shell, new WorkbenchLabelProvider(),
				new WorkbenchContentProvider());
		dialog.setTitle("请选择BOM项目");
		
		// 过滤掉所有的非bom的项目
		dialog.addFilter(new ViewerFilter(){

			@Override
			public boolean select(Viewer viewer, Object parentElement, Object element) {
				if (element instanceof IProject) {
					IProject curProject = (IProject) element;
					if(ProjectUtil.isBOMProject(curProject)){
						return true;
					}
					
				}
				return false;
			}
			
		});
		
		dialog.setInput(ResourcesPlugin.getWorkspace().getRoot());

		if (dialog.open() == Window.OK) {

			if (dialog.getResult() != null){
				IProject selection = (IProject) dialog.getResult()[0];
				desProject = selection;
				if(!desProject.isOpen()){
					MessageDialog.openInformation(getShell(), "提示", "所选项目没有打开！");
					filePathText.setText("");
				}
				else
					filePathText.setText("/"+desProject.getName()+"/model/bom/");
			}
			

		} 


	}
	
	/**
	 * 检测目标文件夹是否存在 不存在则创建.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	protected void autoSetOmModelPath(/*IProject project*/){
		if(desProject==null) return;		
		IFolder folder=(IFolder)desProject.getFolder("/model/om");
		if(!folder.exists()){
			try {
				if(!folder.getParent().exists()){
					((IFolder)folder.getParent()).create(true, true, null);
				}
				folder.create(true, true, null);
			} catch (CoreException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 初始化监听器.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	private void initializeListener() {
		filePathText.addModifyListener(new DataValidateListener());
		namespaceText.addModifyListener(new DataValidateListener());
		modelNameText.addModifyListener(new DataValidateListener());
		modelDisplayNameText.addModifyListener(new DataValidateListener());
	}

	/**
	 * Gets the model name.
	 *
	 * @author mqfdy
	 * @return the model name
	 * @Date 2018-09-03 09:00
	 */
	public String getModelName() {
		return modelNameText.getText();
	}

	/**
	 * Gets the namespace.
	 *
	 * @author mqfdy
	 * @return the namespace
	 * @Date 2018-09-03 09:00
	 */
	public String getNamespace() {
		return namespaceText.getText();
	}

	/**
	 * Gets the model display name.
	 *
	 * @author mqfdy
	 * @return the model display name
	 * @Date 2018-09-03 09:00
	 */
	public String getModelDisplayName() {
		return modelDisplayNameText.getText();
	}

	/**
	 * Gets the file path.
	 *
	 * @author mqfdy
	 * @return the file path
	 * @Date 2018-09-03 09:00
	 */
	public String getFilePath() {
		return filePathText.getText();
	}

	/**
	 * Gets the full file path.
	 *
	 * @author mqfdy
	 * @return the full file path
	 * @Date 2018-09-03 09:00
	 */
	public String getFullFilePath() {
		if (project != null) {
			String[] path = filePathText.getText().split("/");
			String path1 = "";
			for (int i = 2; i < path.length; i++) {
				path1 += path[i] + File.separator;
			}
			return project.getLocation().toOSString() + File.separator + path1;// filePathText.getText().replace(project.getName(),
																				// "");
		} else
			return "";
	}

	/**
	 * Gets the full file name.
	 *
	 * @author mqfdy
	 * @return the full file name
	 * @Date 2018-09-03 09:00
	 */
	public String getFullFileName() {
		return getNamespace() + "." + getModelName() + OM_FILE_TYPE;
	}

	/**
	 * 数据校验监听器.
	 *
	 * @see DataValidateEvent
	 */
	class DataValidateListener implements ModifyListener {
		
		/**
		 * Modify text.
		 *
		 * @author mqfdy
		 * @param e
		 *            the e
		 * @Date 2018-09-03 09:00
		 */
		public void modifyText(ModifyEvent e) {
			validateData();
		}
	}

	/**
	 * Validate data.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	@SuppressWarnings("static-access")
	private void validateData() {
		String errorMessage = null;
		if (getFilePath().length() == 0) {
			errorMessage = "文件存放路径不能为空，请选择 !";
		} else if (getNamespace().length() == 0) {
			errorMessage = "命名空间(作为生成代码的根包名)不能为空，请输入(格式参考" + MODEL_DEFAULT_NAME
					+ ") !";
		} else if (!ValidatorUtil.valiRemarkLength(getNamespace())) {
			errorMessage = "命名空间长度不得超过128个字符，请重新输入 !";
		} else if (!ValidatorUtil.valiPackageName(getNamespace())) {
			errorMessage = "命名空间格式有误，请重新输入 !";
		}else if (!ValidatorUtil.valiNameSpaceName(getNamespace())) {
			errorMessage = "命名空间格式有误，不能以com+数字开头，请重新输入 !";
		}else if (getNamespace().contains("java")) {
			errorMessage = "命名空间不能包含'java'关键字，请重新输入 !";
		} else if (getModelName().length() == 0) {
			errorMessage = "模型名称不能为空，请输入 !";
		} else if (!ValidatorUtil.isValidModelName(getModelName())) {
			errorMessage = "模型名称格式有误，请重新输入 !";
		} else if (!ValidatorUtil.valiNameLength(getModelName())) {
			errorMessage = "模型名称长度不得超过30，请重新输入 !";
		} else if (!ValidatorUtil.valiName(getModelName())) {
			errorMessage = "模型名称不能包含中文和特殊字符，请重新输入 !";
		} else if (new KeyWordsChecker().doCheckJava(getModelName())) {
			errorMessage = "模型名称不能是Java关键字，请重新输入 !";
		} else if (!ValidatorUtil.valiDisplayName(getModelDisplayName())) {
			errorMessage = "显示名称格式有误，请重新输入 !";
		}  else if (!ValidatorUtil.valiDisplayNameLength(getModelDisplayName())) {
			errorMessage = "显示名称长度不得超过30，请重新输入 !";
		} else {
			final String filePath = getFilePath();
			final String fileName = getFullFileName();
			final IFile file;
			IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
			IResource resource = root.findMember(new Path(filePath));
			IContainer container = (IContainer) resource;
			if (container != null) {
				file = container.getFile(new Path(fileName));
				if (file.exists()) {
					errorMessage = "当前指定的模型文件" + "\"" + fileName
							+ "\"已存在，请重新命名。";
				}
			}
		}
		setErrorMessage(errorMessage);
		setPageComplete(errorMessage == null);
	}

	/**
	 * Gets the project.
	 *
	 * @author mqfdy
	 * @return the project
	 * @Date 2018-09-03 09:00
	 */
	public IProject getProject() {
		// TODO Auto-generated method stub
		return project;
	}
	
}
