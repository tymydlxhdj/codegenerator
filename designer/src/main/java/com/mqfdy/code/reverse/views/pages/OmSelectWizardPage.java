package com.mqfdy.code.reverse.views.pages;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.TreeEvent;
import org.eclipse.swt.events.TreeListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.ide.ResourceUtil;

import com.mqfdy.code.designer.editor.BusinessModelEditorPlugin;
import com.mqfdy.code.resource.validator.KeyWordsChecker;
import com.mqfdy.code.resource.validator.ValidatorUtil;
import com.mqfdy.code.reverse.IOmReverse;
import com.mqfdy.code.reverse.OmReverse;
import com.mqfdy.code.reverse.ReverseContext;
import com.mqfdy.code.reverse.mappings.Table;
import com.mqfdy.code.reverse.utils.FileUtils;
import com.mqfdy.code.reverse.views.beans.TreeNode;
import com.mqfdy.code.reverse.views.constant.IViewConstant;
import com.mqfdy.code.reverse.views.models.TreeModel;
import com.mqfdy.code.reverse.views.providers.MultiTreeContentProvider;
import com.mqfdy.code.reverse.views.providers.SingleTreeLabelProvider;
import com.mqfdy.code.utils.ProjectUtil;

/**
 * om选择页面
 * @author xuran
 * 2014-9-4
 */
public class OmSelectWizardPage extends WizardPage {

	public static final int LABELDATA_WIDTH = 100;
	public static final String MODEL_DEFAULT_NAME = "com.orgname.projectname";
	public static final String DIR_NAME_MODEL = "model";
	public static final String DIR_NAME_OM = "bom";
	
	private static final String STR_OM = "bom";
	
	private static final String STR_OM_Q = ".bom";
	
	
	private Label pathLabel;
	private Text pathText;
	private Button newOmBtn;
	private Label namespaceLabel;
	private Text namespaceText;
	private Label omNameLabel;
	private Text omNameText;
	private Label omDisplayNameLabel;
	private Text omDisplayText;
	private Button oldOmBtn;
	private TreeViewer treeViewer;
	private Tree tree;
	
	private boolean isNewOm = true;			//是否选择新的om  true:新增 , false: 已有
	private boolean isNewValidate = false;	//新建om验证结果
	private boolean isOldValidate = false;	//已选om验证结果
	private Group upGroup;
	private Group downGroup;
	
	private IOmReverse omReverse;
	private String exsitOmPath;				//已有om的文件路径
	private String omPath;				//最终om的文件上一层目录
	private String omName;				//最终om的文件名称
	private IProject project;
	
	public String getOmName() {
		return omName;
	}

	public void setOmName(String omName) {
		this.omName = omName;
	}

	public String getOmPath() {
		return omPath;
	}

	public void setOmPath(String omPath) {
		this.omPath = omPath;
	}

	public OmSelectWizardPage(String pageName, IProject project) {
		super(pageName);
		setPageComplete(false);
		omReverse = new OmReverse();
		this.project = project;
	}

	public void createControl(Composite parent) {
		setTitle("新建/选择已存在的bom");
		
		Composite container = new Composite(parent, SWT.NULL);
		initializeDialogUnits(container);
		
		//设置容器的布局
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		container.setLayout(layout);
		container.layout();
		
		//新建om的单选按钮
		newOmBtn = new Button(container, SWT.RADIO);
		newOmBtn.setText("新建bom");
		newOmBtn.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		newOmBtn.setSelection(true);
		
		//添加新建om的单选按钮的事件 
		newOmBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(!isNewOm) {
					isNewOm = true;
					newOmBtn.setSelection(true);
					
					upGroup.setEnabled(true);
					pathLabel.setForeground(IViewConstant.LABEL_COLOR);
					namespaceLabel.setForeground(IViewConstant.LABEL_COLOR);
					omNameLabel.setForeground(IViewConstant.LABEL_COLOR);
					omDisplayNameLabel.setForeground(IViewConstant.LABEL_COLOR);
					
					namespaceText.setBackground(IViewConstant.WHITE_COLOR);
					omNameText.setBackground(IViewConstant.WHITE_COLOR);
					omDisplayText.setBackground(IViewConstant.WHITE_COLOR);
					
					oldOmBtn.setSelection(false);
					downGroup.setEnabled(false);
					tree.setBackground(IViewConstant.DISABLED_COLOR);
					
					validateInput();
					if(isNewValidate) {
						setPageComplete(true);
					} else {
						setPageComplete(false);
					}
				}
			}
		});
		
		//在父容器中创建子容器upGroup
		upGroup = new Group(container, SWT.NULL);
		
		//设置upGroup在父容器中的布局参数
		upGroup.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		//创建upGroup内的布局
		GridLayout upLayout = new GridLayout();
		upLayout.numColumns = 2;
		
		//设置upGroup的布局
		upGroup.setLayout(upLayout);
		upGroup.layout();
		
		//---------- 开始创建upGroup内的控件 ---------------
		
		//创建标签元素的布局参数
		GridData labelGridData = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		labelGridData.widthHint = LABELDATA_WIDTH;
				
		pathLabel = new Label(upGroup, SWT.NULL);
		pathLabel.setLayoutData(labelGridData);
		pathLabel.setText("文件存放路径：");
		
		//创建文本框元素的布局参数
		GridData textGridData = new GridData(GridData.FILL_HORIZONTAL);
		
		//文件路径文本框
		pathText = new Text(upGroup, SWT.BORDER| SWT.READ_ONLY);
		pathText.setLayoutData(textGridData);
		String omDirPath = getBomPath();
		pathText.setText(omDirPath);
		pathText.setEditable(false);
		
		//addFileBtn = new Button(upGroup, SWT.BUTTON1);
		
/*		GridData addFileBtnData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		int widthHint = convertHorizontalDLUsToPixels(IDialogConstants.BUTTON_WIDTH);
		Point minSize = addFileBtn.computeSize(SWT.DEFAULT, SWT.DEFAULT, true);
		addFileBtnData.widthHint = Math.max(widthHint, minSize.x);*/
		
		/*addFileBtn.setText("浏  览");
		addFileBtn.setLayoutData(addFileBtnData);
		addFileBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				OmPathSelectDialog directoryDialog = new OmPathSelectDialog(getShell());
				if(ReverseContext.wizard != null){
					directoryDialog.setProject(ReverseContext.wizard.getProject());
				}
				if(directoryDialog.open() == Dialog.OK) {
					pathText.setText(directoryDialog.getFilePath());
				}
			}
		});*/
		
		namespaceLabel = new Label(upGroup, SWT.NULL);
		namespaceLabel.setLayoutData(labelGridData);
		namespaceLabel.setText("命名空间：");
		
		//命名空间文本框
		namespaceText = new Text(upGroup, SWT.BORDER);
		textGridData = new GridData(GridData.FILL_HORIZONTAL);
	//textGridData.horizontalSpan = 2;
		namespaceText.setLayoutData(textGridData);
		namespaceText.addModifyListener(new ModifyListenerAdapter());
		
		//模型名称标签
		omNameLabel = new Label(upGroup, SWT.NULL);
		omNameLabel.setLayoutData(labelGridData);
		omNameLabel.setText("模型名称：");
		
		//模型名称文本框
		omNameText = new Text(upGroup, SWT.BORDER);
		omNameText.setLayoutData(textGridData);
		omNameText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				omDisplayText.setText(omNameText.getText());
				validateInput();
			}
		});
		
		//显示名标签
		omDisplayNameLabel = new Label(upGroup, SWT.NULL);
		omDisplayNameLabel.setLayoutData(labelGridData);
		omDisplayNameLabel.setText("显示名：");
		
		//显示名文本框
		omDisplayText = new Text(upGroup, SWT.BORDER);
		omDisplayText.setLayoutData(textGridData);
		omDisplayText.addModifyListener(new ModifyListenerAdapter());
		
		oldOmBtn = new Button(container, SWT.RADIO);
		oldOmBtn.setText("选择已有");
		oldOmBtn.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		oldOmBtn.setSelection(false);
		//添加选择已有的单选按钮的事件 
		oldOmBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(isNewOm) {
					oldOmBtn.setSelection(true);
					downGroup.setEnabled(true);
					tree.setBackground(IViewConstant.WHITE_COLOR);
					
					newOmBtn.setSelection(false);
					isNewOm = false;
					
					setMessage("");
					upGroup.setEnabled(false);
					pathLabel.setForeground(IViewConstant.GRAY_COLOR);
					namespaceLabel.setForeground(IViewConstant.GRAY_COLOR);
					omNameLabel.setForeground(IViewConstant.GRAY_COLOR);
					omDisplayNameLabel.setForeground(IViewConstant.GRAY_COLOR);
					namespaceText.setBackground(IViewConstant.DISABLED_COLOR);
					omNameText.setBackground(IViewConstant.DISABLED_COLOR);
					omDisplayText.setBackground(IViewConstant.DISABLED_COLOR);
					
					if(isOldValidate) {
						setPageComplete(true);
					} else {
						setPageComplete(false);
					}
				}
			}
		});
		//在父容器中创建子容器downGroup
		downGroup = new Group(container, SWT.NULL);

		//设置downGroup在父容器中的布局参数
		downGroup.setLayoutData(new GridData(GridData.FILL_BOTH));
		downGroup.setEnabled(false);
		
		//创建downGroup内的布局
		GridLayout downLayout = new GridLayout();
		downLayout.numColumns = 1;
		//设置downGridData的布局
		downGroup.setLayout(downLayout);
		downGroup.layout();
		
		//---------- 开始创建downGroup内的控件 ---------------
		
		treeViewer = new TreeViewer(downGroup, SWT.SINGLE| SWT.BORDER);
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
				if(node.getName().indexOf(STR_OM_Q) != -1) {
					setPageComplete(true);
					isOldValidate = true;
					
					//设置选择的已存在om的路径
					setExsitOmPath(node.getName());
					
					setOmPath(node.getParent().getName());
					setOmName(node.getDisplayName());
				} else {
					File file = new File(node.getName());
					List<File> allSuffixFiles = FileUtils.getAllSuffixFiles(file, STR_OM);
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
			}
		});
		
		treeViewer.setContentProvider(new MultiTreeContentProvider());
		treeViewer.setLabelProvider(new SingleTreeLabelProvider());
		
		treeViewer.addDoubleClickListener(new IDoubleClickListener() {
			
			public void doubleClick(DoubleClickEvent event) {
				TreeSelection selection = (TreeSelection) event.getSelection();
				TreeNode currentNode = (TreeNode) selection.getFirstElement();
				
				int index = currentNode.getName().lastIndexOf(".");
				if(index != -1) {
					String suffix = currentNode.getName().substring(index+1);
					//如果双击的是om文件, 直接跳转到下一页.
					if(suffix.toLowerCase(Locale.getDefault()).equals(STR_OM)) {
						IWizardPage page = getNextPage();
						page.getWizard().getContainer().showPage(page);
						
						setOmPath(currentNode.getParent().getName());
						setOmName(currentNode.getDisplayName());
					}
				} else {
					File file = new File(currentNode.getName());
					List<File> allSuffixFiles = FileUtils.getAllSuffixFiles(file, STR_OM);
					//如果om文件只有一个的时候
					if(allSuffixFiles.size() == 1) {
						IWizardPage page = getNextPage();
						page.getWizard().getContainer().showPage(page);
						
						File omFile = allSuffixFiles.get(0);
						String omFilePath = omFile.getAbsolutePath();
						
						setOmPath(omFilePath.substring(0, omFilePath.lastIndexOf(File.separator)));
						setOmName(omFile.getName());
					}
				}
				
				if(!treeViewer.getExpandedState(currentNode)) {
					expandTree(currentNode);
				} else {
					treeViewer.setExpandedState(currentNode, false);
				}
			}
		});
		constructTree();
		setControl(container);
	}

	public IProject getProject() {
		return project;
	}

	public void setProject(IProject project) {
		this.project = project;
		String omDirPath = getBomPath();
		pathText.setText(omDirPath);
	}

	/**
	 * @return
	 */
	private String getBomPath() {
		String omDirPath = "";
		if(project != null){
			String projectFullPath = project.getLocation().makeAbsolute().toFile().getAbsolutePath(); 
			omDirPath = projectFullPath + File.separator + DIR_NAME_MODEL + File.separator + DIR_NAME_OM;
		}
		return omDirPath;
	}

	public void constructTree() {
		if(ReverseContext.wizard != null){
			IProject project = ReverseContext.wizard.getProject();
			if(ProjectUtil.isBOMProject(project)){
				constructBOMTree();
			}
		}
	}
	
	public void constructBOMTree() {
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
					if(FileUtils.isHasFileWithSuffix((new File(projectFullPath)), STR_OM)) {
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
					if(FileUtils.isHasFileWithSuffix((new File(projectFullPath)), STR_OM)) {
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
								List<File> subFiles = FileUtils.getFilesWithSuffix(file, STR_OM);
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
		List<File> subFiles = FileUtils.getFilesWithSuffix(file, STR_OM);
		
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

	@Override
	public boolean canFlipToNextPage() {
		return isPageComplete();
	}

	@SuppressWarnings("deprecation")
	@Override
	public IWizardPage getNextPage() {
		DuplicateNameWizardPage duplicateNameWizardPage = (DuplicateNameWizardPage) super.getNextPage();
		if("java".equalsIgnoreCase(namespaceText.getText())){
			setMessage("java不能做为命名空间名称", IMessageProvider.ERROR);
			return null;
		}
		
		//如果当前选择的不是新建的group
		if(!isNewOm) {
			try {
//				this.setOmPath(getExsitOmPath());
				//获取重复表名
				
				IPath path = new Path(getExsitOmPath());
				IResource res = ResourcesPlugin.getWorkspace().getRoot().findFilesForLocation(path)[0];
				IWorkbenchPage workbenchPage = BusinessModelEditorPlugin.getActiveWorkbenchWindow().getActivePage();
				IEditorPart editor = ResourceUtil.findEditor(workbenchPage, (IFile) res);
				if(editor != null && editor.isDirty()) {
					editor.doSave(null);
				}
				
				List<Table> duplicateNameList = omReverse.fetchExistOm(getExsitOmPath());
				
				//如果没有重复的表名，直接调转到名称转换策略的页面
				if(duplicateNameList == null || duplicateNameList.size() == 0) {
					OmNameReverseStrategyPage nextPage = (OmNameReverseStrategyPage) super.getWizard().getPage("onrsPage");
					nextPage.repaint();
					return nextPage;
				}
				//设置重复表名集合
				duplicateNameWizardPage.setDuplicationTableNames(duplicateNameList);
				//重画下一个页面
				duplicateNameWizardPage.repaint();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		} else {
			//创建om文件
			this.setOmPath(pathText.getText());
			this.setOmName(namespaceText.getText() + "." + omNameText.getText() + STR_OM_Q);
			
			omReverse.createNewBom( pathText.getText()
								  , namespaceText.getText()
								  , omNameText.getText()
								  , omDisplayText.getText());
			OmNameReverseStrategyPage nextPage = (OmNameReverseStrategyPage) super.getWizard().getPage("onrsPage");
			nextPage.repaint();
			return nextPage;
		}
		
		return duplicateNameWizardPage;
	}

	@SuppressWarnings("static-access")
	public void validateInput() {
		//新建om校验
		if(isNewOm) {
			isNewValidate = true;
			if(pathText.getText() == null || pathText.getText().trim().length() == 0) {
				setMessage("请选择bom存放路径", IMessageProvider.ERROR);
				setPageComplete(false);
				isNewValidate = false;
				return ;
			}
			
			if(namespaceText.getText() == null || namespaceText.getText().trim().length() == 0) {
				isNewValidate = false;
				String errorMessage = "命名空间(作为生成代码的根包名)不能为空，请输入(格式参考"
						 + MODEL_DEFAULT_NAME
					     + ") !";
				setMessage(errorMessage, IMessageProvider.ERROR);
				setPageComplete(false);
				return ;
			}
			else if (!ValidatorUtil.valiNameSpaceName(namespaceText.getText())) {
				String errorMessage = "命名空间格式有误，不能以com+数字开头，请重新输入 !";
				setMessage(errorMessage, IMessageProvider.ERROR);
				setPageComplete(false);
				return;
			}
			else if(!namespaceText.getText().matches(ValidatorUtil.PACKAGENAME)){
				String errorMessage = "命名空间(作为生成代码的根包名)格式错误，请输入(格式参考"
						 + MODEL_DEFAULT_NAME
					     + ") !";
				setMessage(errorMessage, IMessageProvider.ERROR);
				setPageComplete(false);
				return ;
			}else if(namespaceText.getText().equalsIgnoreCase("java")){
				String errorMessage = "\"java\"不能做为命名空间名称";
				setMessage(errorMessage, IMessageProvider.ERROR);
				setPageComplete(false);
				return ;
			}
			else {
				if(!ValidatorUtil.valiPackageName(namespaceText.getText())) {
					String errorMessage = "命名空间(作为生成代码的根包名)不能是Java关键字，请输入(格式参考"
								 + MODEL_DEFAULT_NAME
							     + ") !";
					setMessage(errorMessage, IMessageProvider.ERROR);
					setPageComplete(false);
					return ;
				}
			}
			
			if(omNameText.getText() == null || omNameText.getText().trim().length() == 0) {
				isNewValidate = false;
				setMessage("请填写模型名称", IMessageProvider.ERROR);
				setPageComplete(false);
				return ;
			} else {
				String filePath = pathText.getText() + File.separator + namespaceText.getText() + "." + omNameText.getText() + STR_OM_Q;
				File file = new File(filePath);
				if(file.exists()) {
					isNewValidate = false;
					setMessage("当前的模型名称已经存在", IMessageProvider.ERROR);
					setPageComplete(false);
					return ;
				}
				
				if (!ValidatorUtil.isValidModelName(omNameText.getText())) {
					setMessage("模型名称格式有误，请重新输入 !", IMessageProvider.ERROR);
					setPageComplete(false);
					return ;
				} else if (!ValidatorUtil.valiNameLength(omNameText.getText())) {
					setMessage("模型名称长度不得超过30个字符，请重新输入 !", IMessageProvider.ERROR);
					setPageComplete(false);
					return ;
				} else if (!ValidatorUtil.valiName(omNameText.getText())) {
					setMessage("模型名称不能包含中文和特殊字符，请重新输入 !", IMessageProvider.ERROR);
					setPageComplete(false);
					return ;
				} else if (new KeyWordsChecker().doCheckJava(omNameText.getText())) {
					setMessage("模型名称不能是Java关键字，请重新输入 !", IMessageProvider.ERROR);
					setPageComplete(false);
					return ;
				} 
			}
			
			if(omDisplayText.getText() == null || omDisplayText.getText().trim().length() == 0) {
				isNewValidate = false;
				setMessage("请填写显示名", IMessageProvider.ERROR);
				setPageComplete(false);
				return ;
			}
			
			if(isNewValidate) {
				setPageComplete(true);
				setMessage("");
			}
		}
	}
	
	class ModifyListenerAdapter implements ModifyListener {

		public void modifyText(ModifyEvent e) {
			validateInput();
		}
		
	}

	public String getExsitOmPath() {
		return exsitOmPath;
	}

	public void setExsitOmPath(String exsitOmPath) {
		this.exsitOmPath = exsitOmPath;
	}

	public boolean isNewOm() {
		return isNewOm;
	}

}
