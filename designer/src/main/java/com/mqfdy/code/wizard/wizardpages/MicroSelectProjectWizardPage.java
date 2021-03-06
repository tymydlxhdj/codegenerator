package com.mqfdy.code.wizard.wizardpages;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.IWizardNode;
import org.eclipse.jface.wizard.WizardSelectionPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.TreeEvent;
import org.eclipse.swt.events.TreeListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;
import org.eclipse.ui.model.WorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;

import com.mqfdy.code.reverse.IOmReverse;
import com.mqfdy.code.reverse.utils.FileUtils;
import com.mqfdy.code.reverse.views.beans.TreeNode;
import com.mqfdy.code.reverse.views.constant.IViewConstant;
import com.mqfdy.code.reverse.views.models.TreeModel;
import com.mqfdy.code.reverse.views.providers.MultiTreeContentProvider;
import com.mqfdy.code.reverse.views.providers.SingleTreeLabelProvider;
import com.mqfdy.code.utils.ProjectUtil;
import com.mqfdy.code.wizard.wizard.IMicroGeneratorConfigWizard;
import com.mqfdy.code.wizard.wizard.MicroGeneratorConfigWizard;
import com.mqfdy.code.wizard.wizard.MicroGeneratorWizardNode;

// TODO: Auto-generated Javadoc
/**
 * The Class MicroSelectProjectWizardPage.
 *
 * @author mqfdy
 */
public class MicroSelectProjectWizardPage extends WizardSelectionPage {
	
	/** The Constant LABELDATA_WIDTH. */
	public static final int LABELDATA_WIDTH = 100;
	
	/** The Constant MODEL_DEFAULT_NAME. */
	public static final String MODEL_DEFAULT_NAME = "com.orgname.projectname";
	
	/** The Constant DIR_NAME_MODEL. */
	public static final String DIR_NAME_MODEL = "model";
	
	/** The Constant DIR_NAME_OM. */
	public static final String DIR_NAME_OM = "bom";
	
	/** The Constant STR_MOM. */
	private static final String STR_MOM = "bom";
	
	/** The project. */
	private IProject project;
	
	/** The om project. */
	private IProject omProject;
	
	/** The lbl om. */
	private Label lblOm;
	
	/** The lbl micro project. */
	private Label lblMicroProject;
	
	/** The txt micro project. */
	private Text txtMicroProject;
	
	/** The btn micro project. */
	private Button btnMicroProject;
	
	/** The tree viewer. */
	private TreeViewer treeViewer;
	
	/** The tree. */
	private Tree tree;
	
	/** The om reverse. */
	private IOmReverse omReverse;
	
	/** The exsit om path. */
	private String exsitOmPath;				//已有om的文件路径
	
	/** The om path. */
	private String omPath;				//最终om的文件上一层目录
	
	/** The om name. */
	private String omName;				//最终om的文件名称
	
	/** The is old validate. */
	private boolean isOldValidate = false;	//已选om验证结果
	
	/** The selection. */
	private IStructuredSelection selection;
	
	/** The micro generator config wizard. */
	private MicroGeneratorConfigWizard microGeneratorConfigWizard;
	
	/**
	 * Instantiates a new micro select project wizard page.
	 *
	 * @param pageName
	 *            the page name
	 */
	protected MicroSelectProjectWizardPage(String pageName) {
		super(pageName);
	}

	/**
	 * Instantiates a new micro select project wizard page.
	 *
	 * @param pageName
	 *            the page name
	 * @param project
	 *            the project
	 */
	public MicroSelectProjectWizardPage(String pageName, IProject project) {
		super(pageName);
		this.project = project;
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
		// 初始化窗口
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 1;
		container.setLayout(gridLayout);
		// 初始化窗口
		GridLayout editlayout = new GridLayout();
		editlayout.numColumns = 3;
		container.setLayout(gridLayout);
		Composite editContainer = new Composite(container, SWT.NULL);
		editContainer.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		editContainer.setLayout(editlayout);
		setTitle("基于业务对象模型生成代码");
		setMessage("请选择要生成代码的bom模型", 1);
	/*	lblOm = new Label(editContainer, SWT.NULL);
		lblOm.setText("OM项目:");
		lblOm.setLayoutData(lblOmGrid);

		txtOm = new Text(editContainer, SWT.BORDER | SWT.SINGLE);
		GridData gdd = new GridData(GridData.FILL_HORIZONTAL);
		txtOm.setLayoutData(gdd);
		txtOm.setEditable(false);
		gdd.grabExcessHorizontalSpace = true;
		gdd.verticalAlignment = GridData.CENTER;
		GridData btnOmGrid = new GridData();
		btnOm = new Button(editContainer, SWT.PUSH);
		btnOm.setText("选择...");
		btnOm.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				handleSelectOM();
			}
		});
		btnOm.setLayoutData(btnOmGrid);*/
		GridData lblMicroProjectGrid = new GridData();
		lblMicroProject = new Label(editContainer, SWT.NULL);
		lblMicroProject.setText("项目名称:");
		lblMicroProject.setLayoutData(lblMicroProjectGrid);

		txtMicroProject = new Text(editContainer, SWT.BORDER | SWT.SINGLE);
		GridData gdd2 = new GridData(GridData.FILL_HORIZONTAL);
		txtMicroProject.setLayoutData(gdd2);
		txtMicroProject.setEditable(false);
		gdd2.grabExcessHorizontalSpace = true;
		gdd2.verticalAlignment = GridData.CENTER;
		
		GridData btnMicroProjectGrid = new GridData();
		btnMicroProject = new Button(editContainer, SWT.PUSH);
		btnMicroProject.setText("浏览...");
		btnMicroProject.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				handleSelectMicro();
			}
		});
		btnMicroProject.setLayoutData(btnMicroProjectGrid);
		if(project != null){
			txtMicroProject.setText(project.getName());
			btnMicroProject.setEnabled(false);
		}
/*		//在父容器中创建子容器downGroup
		downGroup = new Group(container, SWT.NULL);

		//设置downGroup在父容器中的布局参数
		downGroup.setLayoutData(new GridData(GridData.FILL_BOTH));
		downGroup.setEnabled(false);
		
		//创建downGroup内的布局
		GridLayout downLayout = new GridLayout();
		downLayout.numColumns = 1;
		//设置downGridData的布局
		downGroup.setLayout(downLayout);
		downGroup.layout();*/
		
		//---------- 开始创建downGroup内的控件 ---------------
		GridData lblOmGrid = new GridData();
		lblOm = new Label(editContainer, SWT.NULL);
		lblOm.setText("选择BOM模型:");
		lblOm.setLayoutData(lblOmGrid);
		treeViewer = new TreeViewer(container, SWT.SINGLE| SWT.BORDER);
		//创建树控件
		tree = treeViewer.getTree();
		tree.setHeaderVisible(false);
		
		//设置树控件布局参数
		GridData treeData = new GridData(GridData.FILL_HORIZONTAL);
		treeData.heightHint = 200;
		tree.setLayoutData(treeData);
		tree.setBackground(IViewConstant.DISABLED_COLOR);
		
		tree.addTreeListener(new TreeListener() {
			
			public void treeExpanded(TreeEvent evt) {
				TreeNode currentNode = (TreeNode) evt.item.getData();
				expandTree(currentNode);
			}
			
			public void treeCollapsed(TreeEvent e) {}
		});
		
		//选中一个树节点事件
		tree.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent evt) {
				TreeNode node = (TreeNode) evt.item.getData();
				
				//如果当前选择的文件时一个om文件，则已存在om的分组验证通过.
				if(node.getName().indexOf(".bom") != -1) {
					setPageComplete(true);
					isOldValidate = true;
					//设置选择的已存在om的路径
					setExsitOmPath(node.getName());
					
					setOmPath(node.getParent().getName());
					setOmName(node.getDisplayName());
					updateWizard();
				} else {
					File file = new File(node.getName());
					List<File> allSuffixFiles = FileUtils.getAllSuffixFiles(file, STR_MOM);
					//如果om文件只有一个的时候
					if(allSuffixFiles.size() == 1) {
						setPageComplete(true);
						isOldValidate = true;
						
						File omFile = allSuffixFiles.get(0);
						String omFilePath = omFile.getAbsolutePath();
						//设置选择的已存在om的路径
						setExsitOmPath(omFilePath);
						
						setOmPath(omFilePath.substring(0, omFilePath.lastIndexOf(File.separator)));
						setOmName(omFile.getName());
					} else {
						setPageComplete(false);
						isOldValidate = false;
					}
					
				}
				validate();
			}
		});
		
		treeViewer.setContentProvider(new MultiTreeContentProvider());
		treeViewer.setLabelProvider(new SingleTreeLabelProvider());
	
		constructOMTree();
		setControl(container);
	}

	/**
	 * update Wizard.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	private void updateWizard() {
		IWizardNode wizardNode = new MicroGeneratorWizardNode(this,selection) {
			
			@Override
			public IMicroGeneratorConfigWizard createWizard() throws CoreException {
				MicroGeneratorConfigWizard wizard = new MicroGeneratorConfigWizard();
				wizard.initialize(exsitOmPath,project);
				setMicroGeneratorConfigWizard(wizard);
				return wizard;
			}
		};
		this.setSelectedNode(wizardNode);
	}
	
	/**
	 * Construct OM tree.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	public void constructOMTree() {
		//获取当前工作空间下的所有工程
		IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
		
		List<TreeModel> modelList = new ArrayList<TreeModel>();
		TreeNode treeNode = null;
		TreeNode tempNode = null;
		TreeNode modelDirNode = null;
		TreeNode omDirNode = null;
		TreeNode omNode = null;
		TreeModel input = null;
		
		//计算含有om文件的模块工程的个数
		int count = 0;
		for(IProject project: projects)  {
			if(project.isOpen()) {
				String projectFullPath = project.getLocation().makeAbsolute().toFile().getAbsolutePath();  
				if(ProjectUtil.isBOMProject(project)) {
					if(FileUtils.isHasFileWithSuffix((new File(projectFullPath)), STR_MOM)) {
						count++;
						if(count > 2) {
							break;
						}
					}
				}
			}
		}
		
		for(IProject project: projects)  {
			if(project.isOpen()) {
				String projectFullPath = project.getLocation().makeAbsolute().toFile().getAbsolutePath();  
				if(ProjectUtil.isBOMProject(project)) {
					if(FileUtils.isHasFileWithSuffix((new File(projectFullPath)), STR_MOM)) {
						treeNode = new TreeNode();
						treeNode.setName(projectFullPath);
						treeNode.setDisplayName(project.getName());
						treeNode.setType(IViewConstant.TYPE_PROJECTS);
						input = new TreeModel(treeNode);
						
						//如果只有一个模块项目，就直接展开所有树节点.
						if(count == 1) {
							//当前目录下是否含有给定的子目录(model/om)
							String omDirPath = projectFullPath + File.separator + DIR_NAME_MODEL + File.separator + DIR_NAME_OM;
							File omDir = new File(omDirPath);
							if(omDir.isDirectory() && omDir.exists()){
								
								//创建"model"这个节点，并添加到跟节点上。
								modelDirNode = new TreeNode();
								modelDirNode.setName(projectFullPath + File.separator + DIR_NAME_MODEL);
								modelDirNode.setDisplayName(DIR_NAME_MODEL);
								modelDirNode.setType(IViewConstant.TYPE_DIRECTORY);
								modelDirNode.setModel(input);
								input.add(treeNode, modelDirNode);
								
								//创建"om"这个节点，并添加到"model"节点上。
								omDirNode = new TreeNode();
								omDirNode.setName(omDirPath);
								omDirNode.setDisplayName(DIR_NAME_OM);
								omDirNode.setType(IViewConstant.TYPE_DIRECTORY);
								omDirNode.setModel(input);
								input.add(modelDirNode, omDirNode);
								
								//加载om文件节点
								File file = new File(omDirPath);
								List<File> subFiles = FileUtils.getFilesWithSuffix(file, STR_MOM);
								for(File subFile: subFiles) {
									if(!subFile.isDirectory()) {
										omNode = new TreeNode();
										omNode.setName(subFile.getAbsolutePath());
										omNode.setDisplayName(subFile.getName());
										omNode.setType(IViewConstant.TYPE_OM_FILE);
										omNode.setModel(input);
										input.add(omDirNode, omNode);
									}
								}
							}
						} else {
							//创建临时节点， 以备展开。
							tempNode = new TreeNode();
							tempNode.setType(IViewConstant.TYPE_TEMP);
							input.add(treeNode, tempNode);
						}
						
						treeNode.setModel(input);
						modelList.add(input);
					}
				}
			}
		}
		
		treeViewer.setInput(modelList);
		if(count == 1) {
			treeViewer.expandAll();
		}
	
	}
	
	/**
	 * Expand tree.
	 *
	 * @author mqfdy
	 * @param currentNode
	 *            the current node
	 * @Date 2018-09-03 09:00
	 */
	protected void expandTree(TreeNode currentNode) {
		//判断如果当前节点有子节点，则说明之前加载过，所以直接展开。
		List<TreeNode> childList = currentNode.getChilds();
		if(childList != null && childList.size() != 0) {
			if(childList.get(0).getType() != IViewConstant.TYPE_TEMP) {
				treeViewer.expandToLevel(currentNode, IViewConstant.EXPAND_LEVEL_1);
				return ;
			}
		}
		
		TreeModel input = currentNode.getModel();
		
		String filePath = currentNode.getName();
		
		File file = new File(filePath);
		List<File> subFiles = FileUtils.getFilesWithSuffix(file, STR_MOM);
		
		TreeNode node = null;
		TreeNode temp = null;
		List<TreeNode> rsNodeList = new ArrayList<TreeNode>(); 
		for(File subFile: subFiles) {
			node = new TreeNode();
			node.setName(subFile.getAbsolutePath());
			if(subFile.isDirectory()) {
				node.setType(IViewConstant.TYPE_DIRECTORY);
			} else {
				node.setType(IViewConstant.TYPE_OM_FILE);
			}
			node.setDisplayName(subFile.getName());
			node.setModel(input);
			
			if(subFile.isDirectory()) {
				temp = new TreeNode();
				temp.setType(IViewConstant.TYPE_TEMP);
				input.add(node, temp);
			}
			rsNodeList.add(node);
		}
		
		currentNode.getChilds().clear();
		input.add(currentNode, rsNodeList);
		treeViewer.expandToLevel(currentNode, IViewConstant.EXPAND_LEVEL_1);
		
		for(TreeNode rsNode: rsNodeList) {
			expandTree(rsNode);
		}
	}
	
	/**
	 * 选择项目.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	private void handleSelectMicro(){
		// 取得当前的shell
		Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
		
		ElementTreeSelectionDialog dialog = new ElementTreeSelectionDialog(shell, new WorkbenchLabelProvider(),
				new WorkbenchContentProvider());
		dialog.setTitle("选择项目");
		dialog.setMessage("请选择项目");
		
		
		dialog.addFilter(new ViewerFilter(){

			@Override
			public boolean select(Viewer viewer, Object parentElement, Object element) {
				if (element instanceof IProject) {
					IProject curProject = (IProject) element;
					if(curProject != null){
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
				if (selection != null) {
					txtMicroProject.setText(selection.getName());
					setProject(selection);
				} 
			}
		} 
		validate();
	}
	
	/**
	 * Validate.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	private void validate(){
		if("".equals(txtMicroProject.getText().trim())){
			setMessage("请选择项目", 1);
			setPageComplete(false);
			return;
		}
		if(exsitOmPath == null){
			setMessage("请选择要生成代码的BOM模型", 1);
			setPageComplete(false);
			return;
		}
		setMessage("");
		setPageComplete(true);
	}
	
	/**
	 * @return
	 */
	@Override
	public boolean canFlipToNextPage() {
		return isPageComplete();
	}

	/**
	 * Gets the project.
	 *
	 * @author mqfdy
	 * @return the project
	 * @Date 2018-09-03 09:00
	 */
	public IProject getProject() {
		return project;
	}

	/**
	 * Sets the project.
	 *
	 * @author mqfdy
	 * @param project
	 *            the new project
	 * @Date 2018-09-03 09:00
	 */
	public void setProject(IProject project) {
		this.project = project;
	}

	/**
	 * Gets the om project.
	 *
	 * @author mqfdy
	 * @return the om project
	 * @Date 2018-09-03 09:00
	 */
	public IProject getOmProject() {
		return omProject;
	}

	/**
	 * Sets the om project.
	 *
	 * @author mqfdy
	 * @param omProject
	 *            the new om project
	 * @Date 2018-09-03 09:00
	 */
	public void setOmProject(IProject omProject) {
		this.omProject = omProject;
	}

	/**
	 * Gets the om reverse.
	 *
	 * @author mqfdy
	 * @return the om reverse
	 * @Date 2018-09-03 09:00
	 */
	public IOmReverse getOmReverse() {
		return omReverse;
	}

	/**
	 * Sets the om reverse.
	 *
	 * @author mqfdy
	 * @param omReverse
	 *            the new om reverse
	 * @Date 2018-09-03 09:00
	 */
	public void setOmReverse(IOmReverse omReverse) {
		this.omReverse = omReverse;
	}

	/**
	 * Gets the exsit om path.
	 *
	 * @author mqfdy
	 * @return the exsit om path
	 * @Date 2018-09-03 09:00
	 */
	public String getExsitOmPath() {
		return exsitOmPath;
	}

	/**
	 * Sets the exsit om path.
	 *
	 * @author mqfdy
	 * @param exsitOmPath
	 *            the new exsit om path
	 * @Date 2018-09-03 09:00
	 */
	public void setExsitOmPath(String exsitOmPath) {
		this.exsitOmPath = exsitOmPath;
	}

	/**
	 * Gets the om path.
	 *
	 * @author mqfdy
	 * @return the om path
	 * @Date 2018-09-03 09:00
	 */
	public String getOmPath() {
		return omPath;
	}

	/**
	 * Sets the om path.
	 *
	 * @author mqfdy
	 * @param omPath
	 *            the new om path
	 * @Date 2018-09-03 09:00
	 */
	public void setOmPath(String omPath) {
		this.omPath = omPath;
	}

	/**
	 * Gets the om name.
	 *
	 * @author mqfdy
	 * @return the om name
	 * @Date 2018-09-03 09:00
	 */
	public String getOmName() {
		return omName;
	}

	/**
	 * Sets the om name.
	 *
	 * @author mqfdy
	 * @param omName
	 *            the new om name
	 * @Date 2018-09-03 09:00
	 */
	public void setOmName(String omName) {
		this.omName = omName;
	}

	/**
	 * Checks if is old validate.
	 *
	 * @author mqfdy
	 * @return true, if is old validate
	 * @Date 2018-09-03 09:00
	 */
	public boolean isOldValidate() {
		return isOldValidate;
	}

	/**
	 * Sets the old validate.
	 *
	 * @author mqfdy
	 * @param isOldValidate
	 *            the new old validate
	 * @Date 2018-09-03 09:00
	 */
	public void setOldValidate(boolean isOldValidate) {
		this.isOldValidate = isOldValidate;
	}

	/**
	 * Gets the selection.
	 *
	 * @author mqfdy
	 * @return the selection
	 * @Date 2018-09-03 09:00
	 */
	public IStructuredSelection getSelection() {
		return selection;
	}

	/**
	 * Sets the selection.
	 *
	 * @author mqfdy
	 * @param selection
	 *            the new selection
	 * @Date 2018-09-03 09:00
	 */
	public void setSelection(IStructuredSelection selection) {
		this.selection = selection;
	}

	/**
	 * Gets the micro generator config wizard.
	 *
	 * @author mqfdy
	 * @return the micro generator config wizard
	 * @Date 2018-09-03 09:00
	 */
	public MicroGeneratorConfigWizard getMicroGeneratorConfigWizard() {
		return microGeneratorConfigWizard;
	}

	/**
	 * Sets the micro generator config wizard.
	 *
	 * @author mqfdy
	 * @param microGeneratorConfigWizard
	 *            the new micro generator config wizard
	 * @Date 2018-09-03 09:00
	 */
	public void setMicroGeneratorConfigWizard(MicroGeneratorConfigWizard microGeneratorConfigWizard) {
		this.microGeneratorConfigWizard = microGeneratorConfigWizard;
	}

}
