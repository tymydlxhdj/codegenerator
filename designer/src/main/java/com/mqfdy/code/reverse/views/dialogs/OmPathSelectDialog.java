package com.mqfdy.code.reverse.views.dialogs;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;

import com.mqfdy.code.reverse.views.beans.TreeNode;
import com.mqfdy.code.reverse.views.constant.IViewConstant;
import com.mqfdy.code.reverse.views.models.TreeModel;
import com.mqfdy.code.reverse.views.providers.MultiTreeContentProvider;
import com.mqfdy.code.reverse.views.providers.SingleTreeLabelProvider;
import com.mqfdy.code.utils.ProjectUtil;

public class OmPathSelectDialog extends TitleAreaDialog {

	public static final int DIALOG_WIDTH = 500;
	public static final int DIALOG_HEIGHT = 550;
	public static final int TREE_WIDTH = 460;
	public static final int TREE_HEIGHT = 500;
	public static final String DIR_NAME_MODEL = "model";
	public static final String DIR_NAME_OM = "bom";
	
	private TreeViewer treeViewer;
	private Tree tree;
	private String filePath;
	
	private IProject project;
	
	public OmPathSelectDialog(Shell parentShell, IProject iProject) {
		super(parentShell);
		this.project = project;
	}
	
	public OmPathSelectDialog(Shell parentShell) {
		super(parentShell);
	}
	
	/**
	 * @param parent
	 * @return
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		
		setTitle("选择UAP 模块项目");
		//设置父容器的布局
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		parent.setLayout(layout);
		parent.layout();
		
		//---------- 开始创建控件 ---------------
		
		treeViewer = new TreeViewer(parent, SWT.SINGLE| SWT.BORDER);
		//创建树控件
		tree = treeViewer.getTree();
		tree.setHeaderVisible(false);
		//设置树控件布局参数
		GridData treeData = new GridData(SWT.CENTER, SWT.CENTER, true, true, 1, 1);
		treeData.widthHint = TREE_WIDTH;
		treeData.heightHint = TREE_HEIGHT;
		tree.setLayoutData(treeData);
		
		//添加监听事件
		/*tree.addTreeListener(new TreeListener() {
			
			public void treeExpanded(TreeEvent evt) {
				TreeNode currentNode = (TreeNode) evt.item.getData();
				expandTree(currentNode);
			}
			
			public void treeCollapsed(TreeEvent e) {}
		});*/

		//树选中事件
		tree.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				TreeNode currentNode = (TreeNode) e.item.getData();
				setFilePath(currentNode.getName() + File.separator + DIR_NAME_MODEL + File.separator + DIR_NAME_OM);
			}
		});
		
		treeViewer.setContentProvider(new MultiTreeContentProvider());
		treeViewer.setLabelProvider(new SingleTreeLabelProvider());
		
		//鼠标双击事件
		treeViewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				TreeSelection selection = (TreeSelection) event.getSelection();
				TreeNode currentNode = (TreeNode) selection.getFirstElement();
				
				setFilePath(currentNode.getName() + File.separator + DIR_NAME_MODEL + File.separator + DIR_NAME_OM);
				okPressed();
			}
		});
		List<TreeModel> modelList = getModelList();
		
		
		treeViewer.setInput(modelList);
		treeViewer.expandAll();
		return parent;
	}

	public List<TreeModel> getModelList() {
		return filterOMService();
	}
	
	public List<TreeModel> filterOMService() {
		//获取当前工作空间下的所有工程
		IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
		List<TreeModel> modelList = new ArrayList<TreeModel>();
		setTitle("选择BOM模型项目");
		TreeNode treeNode;
		TreeModel input;
		for(IProject project: projects)  {
			if(project.isOpen()) {
				String projectFullPath = project.getLocation().makeAbsolute().toFile().getAbsolutePath(); 
				if(ProjectUtil.isBOMProject(project)){
					treeNode = new TreeNode();
					treeNode.setName(projectFullPath);
					treeNode.setDisplayName(project.getName());
					treeNode.setType(IViewConstant.TYPE_PROJECTS);
					input = new TreeModel(treeNode);
					
					String omDirPath = projectFullPath + File.separator + DIR_NAME_MODEL + File.separator + DIR_NAME_OM;
					File omDir = new File(omDirPath);
					if(!omDir.exists()) {
						omDir.mkdirs();
					}
					treeNode.setModel(input);
					modelList.add(input);
				}
				
				
			}
		}
		return modelList;
	}
	@Override
	protected Point getInitialSize() {
		return new Point(DIALOG_WIDTH, DIALOG_HEIGHT);
	}

	@Override
	protected int getShellStyle() {
		return super.getShellStyle() | SWT.RESIZE | SWT.MAX | SWT.MIN;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public IProject getProject() {
		return project;
	}

	public void setProject(IProject project) {
		this.project = project;
	}

}