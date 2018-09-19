package com.mqfdy.code.shareModel.dialogs;

import java.io.File;
import java.util.UUID;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ContainerSelectionDialog;

import com.mqfdy.code.designer.editor.BusinessModelEditor;
import com.mqfdy.code.designer.models.BusinessModelManager;
import com.mqfdy.code.model.BusinessObjectModel;
import com.mqfdy.code.resource.BomManager;
import com.mqfdy.code.resource.validator.KeyWordsChecker;
import com.mqfdy.code.resource.validator.ValidatorUtil;

// TODO: Auto-generated Javadoc
/**
 * The Class ObjectModelNewDialog.
 *
 * @author mqfdy
 */
public class ObjectModelNewDialog extends TitleAreaDialog {
	
	/** The Constant OM_FILE_TYPE. */
	public static final String OM_FILE_TYPE = ".om";
	
	/** The Constant MODEL_DEFAULT_NAME. */
	public static final String MODEL_DEFAULT_NAME = "com.orgname.projectname";
	
	/** The Constant PACKAGE_DEFAULT_NAME. */
	public static final String PACKAGE_DEFAULT_NAME = "demo";
	
	/** The error message. */
	public String errorMessage = null;

	/** The selection. */
	private ISelection selection;
    
    /** The parent dialog. */
    public FindModelDialog parentDialog;
	
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
	
	/** The obj. */
	private BusinessObjectModel obj;
	
	/**
	 * Instantiates a new object model new dialog.
	 *
	 * @param parent
	 *            the parent
	 * @param parentDialog
	 *            the parent dialog
	 * @param obj
	 *            the obj
	 */
	public ObjectModelNewDialog(Shell parent,FindModelDialog parentDialog, BusinessObjectModel obj) {
		super(parent);
		this.parentDialog=parentDialog;
		this.obj=obj;
	}

		/**
		 * Creates the dialog area.
		 *
		 * @author mqfdy
		 * @param parent
		 *            the parent
		 * @return the control
		 * @Date 2018-09-03 09:00
		 */
		public Control createDialogArea(Composite parent) {
			Composite area = (Composite) super.createDialogArea(parent);
			Composite container = new Composite(area, SWT.NONE);
			GridLayout layout = new GridLayout(3,false);
			container.setLayout(layout);
			container.setLayoutData(new GridData(GridData.FILL_BOTH));
			GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
			//GridData的组件占两列显示
			gridData.horizontalSpan = 2;
			// 元数据文件存放路径设置
			Label filePathlabel = new Label(container, SWT.NULL);
			filePathlabel.setText("文件存放路径：");

			filePathText = new Text(container, SWT.BORDER | SWT.SINGLE
					| SWT.READ_ONLY);
			GridData gd_filePathText = new GridData(GridData.FILL_HORIZONTAL);
			filePathText.setLayoutData(gd_filePathText);


			Button browseButton = new Button(container, SWT.PUSH);
			browseButton.setText("浏览...");
			browseButton.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {
					handleBrowse();
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
			IEditorPart editorPart = PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow().getActivePage().getActiveEditor();
			BusinessModelManager businessModelManager=null;
			if (editorPart instanceof BusinessModelEditor) 
				businessModelManager=  ((BusinessModelEditor) editorPart).getBuEditor()
						.getBusinessModelManager();
			if (businessModelManager != null) {
				
			BusinessObjectModel businessObjectModel = businessModelManager
					.getBusinessObjectModel();
			}
			IPath path = new Path(businessModelManager.getPath());
			IResource res = ResourcesPlugin.getWorkspace().getRoot()
					.findFilesForLocation(path)[0];
			project=res.getProject();
			filePathText.setText("/"+project.getName()+"/model/om/");
			namespaceText.setText(((BusinessObjectModel)obj).getNameSpace());
			modelNameText.setText(((BusinessObjectModel)obj).getName());
			modelDisplayNameText.setText(((BusinessObjectModel)obj).getDisplayName());
			validateData();
			return parent; 
		}

		/**
		 * 浏览文件夹.
		 *
		 * @author mqfdy
		 * @Date 2018-09-03 09:00
		 */
		private void handleBrowse() {
			ContainerSelectionDialog dialog = new ContainerSelectionDialog(
					getShell(), ResourcesPlugin.getWorkspace().getRoot(), false,
					"选择一个文件夹");
			if (dialog.open() == ContainerSelectionDialog.OK) {
				Object[] result = dialog.getResult();
				if (result.length == 1) {
					IResource container = ResourcesPlugin.getWorkspace().getRoot()
							.findMember((Path) result[0]);
					project  = container.getProject();
					filePathText.setText("/"+project.getName()+"/model/om/");
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
		 * 校验设置是否正确.
		 *
		 * @author mqfdy
		 * @Date 2018-09-03 09:00
		 */
		private void validateData() {
			errorMessage=null;
			if (getFilePath().length() == 0) {
				errorMessage = "文件存放路径不能为空，请选择 !";
			} else if (getNamespace().length() == 0) {
				errorMessage = "命名空间(作为生成代码的根包名)不能为空，请输入(格式参考" + MODEL_DEFAULT_NAME
						+ ") !";

			} else if (!ValidatorUtil.valiRemarkLength(getNamespace())) {
				errorMessage = "命名空间长度不得超过128个字符，请重新输入 !";

			} else if (!ValidatorUtil.valiPackageName(getNamespace())) {
				errorMessage = "命名空间格式有误，请重新输入 !";
			} else if (!ValidatorUtil.valiNameSpaceName(getNamespace())) {
				errorMessage = "命名空间格式有误，不能以com+数字开头，请重新输入 !";
			}else if ("java".equalsIgnoreCase(getNamespace())) {
				errorMessage = "命名空间不能为\"java\" !";
			}else if (getModelName().length() == 0) {
				errorMessage = "模型名称不能为空，请输入 !";

			}else if ("java".equalsIgnoreCase(getModelName())) {
				errorMessage = "模型名称不能为\"java\"!";

			}else if (!ValidatorUtil.isValidModelName(getModelName())) {
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
			setMessage(errorMessage, IMessageProvider.ERROR);			
		
		}

		
		/**
		 * 
		 */
		@Override
		protected void okPressed() {
			if(errorMessage!=null){
				return;
			}
			String path=getFullFilePath() +getFullFileName();
			((BusinessObjectModel)obj).setId(UUID.randomUUID().toString().replace("-", ""));
			((BusinessObjectModel)obj).setNameSpace(namespaceText.getText());
			((BusinessObjectModel)obj).setName(modelNameText.getText());
			((BusinessObjectModel)obj).setDisplayName(modelDisplayNameText.getText());
			BomManager.outputXmlFile((BusinessObjectModel)obj, path);

			parentDialog.refreshEditor(path.replaceAll("\\\\", "/"));
			super.okPressed();			
		}
		
		/**
		 * @return
		 */
		@Override
		protected Point getInitialSize() {
			return new Point(400, 300);
		}
		
		/**
		 * Configure shell.
		 *
		 * @author mqfdy
		 * @param newShell
		 *            the new shell
		 * @Date 2018-09-03 09:00
		 */
		protected void configureShell(Shell newShell) {
			super.configureShell(newShell);
			newShell.setText("创建业务对象模型");
		}
		
		/**
		 * @return
		 */
		protected int getShellStyle() {
			return super.getShellStyle() | SWT.RESIZE | SWT.MAX |SWT.MIN ;
		}
		
		/**
		 * @return
		 */
		@Override
		public boolean isHelpAvailable() {
			return false;
		}
}
