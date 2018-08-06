package com.mqfdy.code.reverse.views.pages;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.TreeEvent;
import org.eclipse.swt.events.TreeListener;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import com.mqfdy.code.reverse.DataSourceInfo;
import com.mqfdy.code.reverse.IOmReverse;
import com.mqfdy.code.reverse.OmReverse;
import com.mqfdy.code.reverse.ReverseContext;
import com.mqfdy.code.reverse.mappings.Column;
import com.mqfdy.code.reverse.mappings.ForeignKey;
import com.mqfdy.code.reverse.mappings.PrimaryKey;
import com.mqfdy.code.reverse.mappings.Table;
import com.mqfdy.code.reverse.mappings.View;
import com.mqfdy.code.reverse.utils.ReverseContants;
import com.mqfdy.code.reverse.utils.ReverseUtil;
import com.mqfdy.code.reverse.views.beans.TreeNode;
import com.mqfdy.code.reverse.views.constant.IViewConstant;
import com.mqfdy.code.reverse.views.models.TreeModel;
import com.mqfdy.code.reverse.views.providers.SingleTreeLabelProvider;
import com.mqfdy.code.reverse.views.providers.TreeContentProvider;

public class TableStructureWizardPage extends WizardPage {

	public static final int WIDTH = 500;
	private Button checkBtn;
	private CheckboxTreeViewer treeViewer;
	private Tree tree;
	private TreeNode root;
	
	private DataSourceInfo dataSourceInfo;//数据源
	private IOmReverse omReverse;
	private Composite container;
	private Composite searchContainer;
	private Composite tableContainer;
	
	private List<TreeNode> checkedNodeList;//选过的表节点列表
	
	private boolean isRelativeChecked;//选中状态
	
	List<Table> noPKList = null;
	List<Table> multiPKList = null;
	List<Table> specialCharList = null;
	List<Table> startWithFigureList = null;
	List<String> tableNames;
	private Text searchText;
	private Button searchBtn;
	
	private List<TreeItem> searchedItems = new ArrayList<TreeItem>();
	private int searchedCount = 0;
	
	
	public TableStructureWizardPage(String pageName) {
		super(pageName);
		setTitle("选择要转换的表");
		dataSourceInfo = new DataSourceInfo();
		omReverse = new OmReverse();
		checkedNodeList = new LinkedList<TreeNode>();
	}

	public DataSourceInfo getDataSourceInfo() {
		return dataSourceInfo;
	}

	public void setDataSourceInfo(DataSourceInfo dataSourceInfo) {
		this.dataSourceInfo = dataSourceInfo;
	}

	public void createControl(Composite parent) {
		
		//为容器创建布局
		container = new Composite(parent, SWT.NO_REDRAW_RESIZE);
		initializeDialogUnits(container);
		
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 1;
		container.setLayout(gridLayout);
		
		searchContainer = new Composite(container, SWT.NULL);
		searchContainer.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		searchContainer.setLayout(new GridLayout(2, false));
		searchContainer.layout();
		
		searchText = new Text(searchContainer, SWT.BORDER);
		searchText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		searchText.addModifyListener(new ModifyListener() {
			
			public void modifyText(ModifyEvent e) {
				searchedCount = 0;
				searchedItems = new ArrayList<TreeItem>();
			}
		});
		
		searchBtn = new Button(searchContainer, SWT.BUTTON1);

		GridData addFileBtnData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		int widthHint = convertHorizontalDLUsToPixels(IDialogConstants.BUTTON_WIDTH);
		Point minSize = searchBtn.computeSize(SWT.DEFAULT, SWT.DEFAULT, true);
		addFileBtnData.widthHint = Math.max(widthHint, minSize.x);
		
		searchBtn.setText("查找");
		searchBtn.setLayoutData(addFileBtnData);
		searchBtn.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if(searchText.getText().trim().length() == 0) {
					return ;
				}
				searchedCount++;
				
				TreeItem[] treeItems = tree.getItems();
				for(TreeItem treeItem: treeItems[0].getItems()) {
					TreeNode node = (TreeNode) treeItem.getData();
					if(node.getDisplayName().toLowerCase(Locale.getDefault()).contains(searchText.getText().toLowerCase(Locale.getDefault()).trim())) {
						searchedItems.add(treeItem);
					}
				}
				
				if(searchedItems.size() == 0) {
					return ;
				}
				int index = 0;
				if(searchedCount % searchedItems.size() == 0) {
					index = searchedItems.size() - 1;
				} else {
					index = searchedCount % searchedItems.size() - 1;
				}
				TreeItem objItem = searchedItems.get(index);
				tree.setSelection(objItem);
			}
		});
		
		/*parent.getShell().addKeyListener(new KeyAdapter() {

			public void keyReleased(KeyEvent evt) {
				if (evt.keyCode == SWT.CR || evt.keyCode == SWT.KEYPAD_CR) {
                    // 让按键原有的功能失效
					evt.doit = false;
                    // 执行你自己的事件
                    MessageBox box = new MessageBox(new Shell(), SWT.ICON_INFORMATION | SWT.OK);
                    box.setText("提示信息");
                    box.setMessage("按钮按回车键了");
                    box.open();
                }
			}
			
		});*/
		
		tableContainer = new Composite(container, SWT.NULL);
		tableContainer.setLayoutData(new GridData(GridData.FILL_BOTH));
		tableContainer.setLayout(new GridLayout(1, false));
		tableContainer.layout();
		
		//创建复选框按钮
		checkBtn = new Button(tableContainer, SWT.CHECK);
		checkBtn.setText("自动选中有关联关系的表");
		checkBtn.setSelection(true);
		isRelativeChecked = true;
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		checkBtn.setLayoutData(gridData);
		checkBtn.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if(!isRelativeChecked) {
					isRelativeChecked = true;
					
					//遍历选过的表节点，自动选中有关联关系的表。
					for(TreeNode currentNode: checkedNodeList) {
						selectRelativeTables(currentNode, true);
					}
					
				} else {
					isRelativeChecked = false;
					
					//遍历选过的表节点，自动去掉有关联关系的表。
					for(TreeNode currentNode: checkedNodeList) {
						selectRelativeTables(currentNode, false);
					}
				}
			}
		});
		
		//创建带复选框的树查看器
		treeViewer = new CheckboxTreeViewer(tableContainer, SWT.BORDER);
		
		//创建树控件
		tree = treeViewer.getTree();
		tree.setHeaderVisible(false);
		
		//设置树控件布局
		gridData = new GridData(GridData.FILL_BOTH);
		tree.setLayoutData(gridData);
		
		//添加树的监听事件
		tree.addTreeListener(new TreeListener() {
			
			public void treeExpanded(TreeEvent evt) {
				TreeNode currentNode = (TreeNode) evt.item.getData();
				expandTree(currentNode);
			}
			
			public void treeCollapsed(TreeEvent e) {}
		});
		
		//设置树查看器内容提供者
		treeViewer.setContentProvider(new TreeContentProvider());
		//设置树查看器标签提供者
		treeViewer.setLabelProvider(new SingleTreeLabelProvider());
		//增加选择事件监听器
		treeViewer.addCheckStateListener(new ICheckStateListener() {

			public void checkStateChanged(CheckStateChangedEvent event) {
				
				//获取当前节点
				TreeNode currentNode = (TreeNode) event.getElement();
				
				if(currentNode.isDisabled()) {
					treeViewer.setChecked(currentNode, false);
					return ;
				}
				
				//如果当前节点还未选中
				if(!currentNode.isChecked()) {
					//设置当前节点的状态为已选
					currentNode.setChecked(true);
					
					//如果选中了自动关联按钮
					if(IViewConstant.TYPE_TABLE_NODE == currentNode.getType() && checkBtn.getSelection()) {
						selectRelativeTables(currentNode, true);
						//添加到选过的表节点列表中
						checkedNodeList.add(currentNode);
					}
					
					List<TreeNode> allChildList = ReverseUtil.getAllChildren(currentNode);
					setAllChecked(allChildList, true);
					
					//检查是否要选中父节点
					checkParentNode(currentNode, treeViewer);
					
				} else {//如果当前节点还选中
					//设置当前节点的状态为未选中
					currentNode.setChecked(false);
					
					//如果不选中自动关联按钮
					if(IViewConstant.TYPE_TABLE_NODE == currentNode.getType() && checkBtn.getSelection()) {
						//selectRelativeTables(currentNode, false);
						//从选过的表节点列表中清除
						checkedNodeList.remove(currentNode);
					}
					
					List<TreeNode> allChildList = ReverseUtil.getAllChildren(currentNode);
					setAllChecked(allChildList, false);
					//检查是否要选中父节点
					checkParentNode(currentNode, treeViewer);
				}
			}
		});
		
		treeViewer.addDoubleClickListener(new IDoubleClickListener() {
			
			public void doubleClick(DoubleClickEvent event) {
				TreeSelection selection = (TreeSelection) event.getSelection();
				TreeNode currentNode = (TreeNode) selection.getFirstElement();
				if(!treeViewer.getExpandedState(currentNode)) {
					expandTree(currentNode);
				} else {
					treeViewer.setExpandedState(currentNode, false);
				}
			}
		});
		//设置输入模型
		setControl(container);
	}

	/**
	 * 根据前一个页面选择的数据源重画当前页面
	 */
	public void repaint() {
		
		TreeNode tablesNode = new TreeNode();
		tablesNode.setDisplayName(IViewConstant.TABLES);
		tablesNode.setType(IViewConstant.TYPE_TABLE_FOLDER);
		TreeModel input = new TreeModel(tablesNode);
		
		root = tablesNode;
		//表节点
//		TreeNode tableFolderNode = new TreeNode();
//		tableFolderNode.setDisplayName(IViewConstant.TABLES);
//		tableFolderNode.setType(IViewConstant.TYPE_TABLE_FOLDER);
//		input.add(userNameNode, tableFolderNode);
		
		//加载数据库表
		List<Table> tableList = omReverse.fetchTables(dataSourceInfo);
		List<TreeNode> rsNodeList = new ArrayList<TreeNode>(); 
		TreeNode node = null;
		for(Table table: tableList) {
			node = new TreeNode();
			node.setName(table.getName());
			if(table.getComment() == null || table.getComment().trim().length() == 0) {
				node.setDisplayName(table.getName());
			} else {
				node.setDisplayName(table.getName() + " / " + table.getComment());
			}
			node.setType(IViewConstant.TYPE_TABLE_NODE);
			rsNodeList.add(node);
		}
		
		//添加子节点到当前节点
		input.add(tablesNode, rsNodeList);
		
		//序列节点
		/*TreeNode sequencesNode = new TreeNode();
		sequencesNode.setDisplayName(IViewConstant.SEQUENCES);
		sequencesNode.setType(IViewConstant.TYPE_SEQUENCES_FOLDER);
		sequencesNode.setDisabled(true);
		input.add(userNameNode, sequencesNode);*/
		
		//视图节点
		/*TreeNode viewsNode = new TreeNode();
		viewsNode.setDisplayName(IViewConstant.VIEWS);
		viewsNode.setType(IViewConstant.TYPE_VIEWS_FOLDER);
		viewsNode.setDisabled(true);
		input.add(userNameNode, viewsNode);*/
		
		//添加临时节点以备展开
		/*TreeNode tempNode = null;
		List<String> sequecesNames = null;
		try {
			sequecesNames = omReverse.fetchSequences(dataSourceInfo);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		if(sequecesNames != null && sequecesNames.size() != 0) {
			tempNode = new TreeNode();
			tempNode.setType(IViewConstant.TYPE_TEMP);
			input.add(sequencesNode, tempNode);
		}*/
		
		/*List<View> viewsList = omReverse.fetchViews(dataSourceInfo);
		if(viewsList != null && viewsList.size() != 0) {
			tempNode = new TreeNode();
			tempNode.setType(IViewConstant.TYPE_TEMP);
			input.add(viewsNode, tempNode);
		}*/
		
		treeViewer.setInput(input);
		treeViewer.expandToLevel(tablesNode, IViewConstant.EXPAND_LEVEL_1);
		
//		treeViewer.setGrayed(userNameNode, true);
//		treeViewer.setGrayed(sequencesNode, true);
//		treeViewer.setGrayed(viewsNode, true);
	}
	
	@Override
	public boolean canFlipToNextPage() {
		return isPageComplete();
	}

	public IWizardPage getNextPage() {
		
		//获取模型
		TreeModel input = (TreeModel) treeViewer.getInput();
		//找到tables节点
		TreeNode tablesNode = input.findTreeNode(new int[]{});
		
		List<TreeNode> childList = tablesNode.getChilds();
		boolean flag = false;
		for(TreeNode childNode: childList) {
			if(childNode.isChecked()) {
				flag = true;
				break;
			}
		}
		if(!flag) {
			setMessage("至少选择一张表", IMessageProvider.ERROR);
			return null;
		} else {
			setMessage("");
		}
	
		
		SpecialTableWizardPage nextPage = (SpecialTableWizardPage) super.getNextPage();
		
		//找到所有表节点
		List<TreeNode> tableNodeList = tablesNode.getChilds();
		
		tableNames = new ArrayList<String>();
		//遍历所有表节点,找到选中的表名的集合。
		for(TreeNode tableNode: tableNodeList) {
			if(tableNode.isChecked()) {
				tableNames.add(tableNode.getName());
			}
		}
		
		try {
			new ProgressMonitorDialog(getShell()).run(true, false, new IRunnableWithProgress() {
				public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
					monitor.setCanceled(false);
					monitor.beginTask("正在检查是否存在不合规的表", tableNames.size());
					Map<String, List<Table>> map = omReverse.findInValidTable(tableNames, monitor);
					noPKList = map.get(ReverseContants.NOPKTABLE);
					multiPKList = map.get(ReverseContants.MUTILPKTABLE);
					specialCharList = map.get(ReverseContants.SPECIAL_CHAR_TABLE);
					startWithFigureList = map.get(ReverseContants.START_WITH_FIGURE_TABLE);
					
					Thread.sleep(1000);	//方便使用者看清表的个数
					monitor.done();
				}
			});
			
			//关闭连接对象，释放资源。
			ReverseContext.wizard.getConnection().close();
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		
//		multiPKList = omReverse.findMutiPkTable(tableNames);
		
		//如果没有特殊的表就跳过特殊表处理页面。
		if((noPKList == null || noPKList.size() == 0) 
				&& (multiPKList == null || multiPKList.size() == 0) &&
				(specialCharList == null || specialCharList.size() == 0) &&
				(startWithFigureList == null || startWithFigureList.size() == 0)) {
			ReverseContext.handleTables.clear();
			return super.getWizard().getPage("osPage");
		}
		
		nextPage.setNoPKTableList(noPKList);
		nextPage.setMutiPkTableList(multiPKList);
		nextPage.setSpecialCharTableList(specialCharList);
		nextPage.setStartWithFigureTableList(startWithFigureList);
		nextPage.repaint();
		
		return nextPage;
	}

	/**
	 * 点击某一个节点时候，检查是否要选中父节点
	 * 如果当前节点同级的所有节点都选中，则选中父节点， 否则去掉父节点的勾
	 * @param currentNode
	 * @param treeViewer
	 */
	private void checkParentNode(TreeNode currentNode, CheckboxTreeViewer treeViewer) {
		boolean isCheckParent = true;
		TreeNode parentNode = currentNode.getParent();
		
		if(parentNode == null) {
			return ;
		}
		
		List<TreeNode> currentDepthNodes = parentNode.getChilds();
		for(TreeNode currentDepthNode: currentDepthNodes) {
			if(!currentDepthNode.isChecked()) {
				isCheckParent = false;
				break;
			}
		}
		
		if(isCheckParent) {
			parentNode.setChecked(true);
			treeViewer.setChecked(parentNode, true);
		} else {
			parentNode.setChecked(false);
			treeViewer.setChecked(parentNode, false);
		}
	}
	
	private void setAllChecked(List<TreeNode> nodeList, boolean checkState) {
		for(TreeNode treeNode : nodeList) {
			if(!treeNode.isDisabled()) {
				treeNode.setChecked(checkState);
				treeViewer.setChecked(treeNode, checkState);
			}
		}
	}
	
	/**
	 * 自动关联有关系的表
	 * @param currentNode 当前节点
	 * @param checkState 选中状态 true:选中, false:不选中
	 */
	private void selectRelativeTables(TreeNode currentNode, boolean checkState) {
		Cursor cursor = new Cursor(container.getDisplay(), SWT.CURSOR_WAIT);
		container.getShell().setCursor(cursor);
		
		//获取具有关联关系的表
		List<Table> tableList = omReverse.getRelativeTables(currentNode.getName());
		//获取父节点
		TreeNode parentNode = currentNode.getParent();
		//获取兄弟节点(含自己)
		List<TreeNode> brotherNodeList = parentNode.getChilds();
		//遍历所有兄弟节点,如果当前兄弟节点属于关联的表节点, 改变节点选中的状态
		for(TreeNode brotherNode: brotherNodeList) {
			for(Table table: tableList) {
				if(table!=null&&brotherNode.getName().equals(table.getName())
						&& !brotherNode.getName().equals(currentNode.getName())) {
					brotherNode.setChecked(checkState);
					
					List<TreeNode> childList = ReverseUtil.getAllChildren(brotherNode);
					setAllChecked(childList, checkState);
					
					brotherNode.setChecked(checkState);
					treeViewer.setChecked(brotherNode, checkState);
					
				}
			}
		}
		cursor = new Cursor(container.getDisplay(), SWT.CURSOR_ARROW);
		container.getShell().setCursor(cursor);
	}
	
	/**
	 * 树的展开
	 * @param currentNode
	 */
	private void expandTree(TreeNode currentNode) {
		Cursor cursor = new Cursor(container.getDisplay(), SWT.CURSOR_WAIT);
		container.getShell().setCursor(cursor);
		
		if(ReverseContext.wizard.getConnection() != null) {
			List<TreeNode> nodeList = currentNode.getChilds();
			
			//如果当前节点的子节点只有一个，并且是一个临时节点，才从数据库加载所有的表。
			if(nodeList.size() == 1 && nodeList.get(0).getType() == IViewConstant.TYPE_TEMP) {
				//如果选择的表的文件夹
				if(IViewConstant.TYPE_TABLE_FOLDER == currentNode.getType()) {
					currentNode.setDisabled(false);
					treeViewer.setGrayChecked(currentNode, false);
					expandTableFolder(currentNode);
				} else if(IViewConstant.TYPE_TABLE_NODE == currentNode.getType()) {
					expandTableToColumsAndContraints(currentNode);
				} else if(IViewConstant.TYPE_SEQUENCES_FOLDER == currentNode.getType()) {
					expandSequences(currentNode);
				} else if(IViewConstant.TYPE_VIEWS_FOLDER == currentNode.getType()) {
					expandViews(currentNode);
				} else if(IViewConstant.TYPE_VIEWS_NODE == currentNode.getType()) {
					expandViewsToColumns(currentNode);
				}
			}
		}
		
		cursor = new Cursor(tableContainer.getDisplay(), SWT.CURSOR_ARROW);
		container.getShell().setCursor(cursor);
		
		if(currentNode.isChecked()) {
			List<TreeNode> list = ReverseUtil.getAllChildren(currentNode);
			for(TreeNode childNode: list) {
				if(!childNode.isDisabled()) {
					childNode.setChecked(true);
					treeViewer.setChecked(childNode, true);
				}
			}
		}
		
		treeViewer.expandToLevel(currentNode, IViewConstant.EXPAND_LEVEL_1);
		
		List<TreeNode> allChildList = ReverseUtil.getAllChildren(root);
		for(TreeNode node : allChildList) {
			if(node.isDisabled()) {
				treeViewer.setGrayed(node, true);
			}
		}
	}
	
	/**
	 * 展开tables文件夹
	 * @param currentNode
	 */
	private void expandTableFolder(TreeNode currentNode) {
		List<Table> tableList = omReverse.fetchTables(dataSourceInfo);
		//获取模型
		TreeModel input = (TreeModel) treeViewer.getInput();
		
		List<TreeNode> rsNodeList = new ArrayList<TreeNode>(); 
		TreeNode node = null;
		TreeNode temp = null;
		for(Table table: tableList) {
			node = new TreeNode();
			node.setName(table.getName());
			if(table.getComment() == null || table.getComment().trim().length() == 0) {
				node.setDisplayName(table.getName());
			} else {
				node.setDisplayName(table.getName() + "(" + table.getComment() + ")");
			}
			node.setType(IViewConstant.TYPE_TABLE_NODE);
			
			//添加临时节点以备展开
			temp = new TreeNode();
			temp.setType(IViewConstant.TYPE_TEMP);
			input.add(node, temp);
			rsNodeList.add(node);
		}
		
		//清空临时节点 
		currentNode.getChilds().clear();
		
		//添加子节点到当前节点
		input.add(currentNode, rsNodeList);
	
	}
	
	/**
	 * 展开单个表
	 * @param currentNode
	 */
	private void expandTableToColumsAndContraints(TreeNode currentNode) {
		Table table = omReverse.fetchColumnsAndContraints(dataSourceInfo, currentNode.getDisplayName());
		
		//获取模型
		TreeModel input = (TreeModel) treeViewer.getInput();
		
		//定义列节点集合
		List<TreeNode> columnNodeList = new ArrayList<TreeNode>(); 
		TreeNode node = null;
		
		//遍历所有的列实例
		for(Entry<String, Column> entry: table.getColumns().entrySet()) {
			//创建列节点
			node = new TreeNode();
			node.setDisplayName(entry.getKey());
			node.setType(IViewConstant.TYPE_COLUMNS_NODE);
			node.setDisabled(true);
			columnNodeList.add(node);
		}
		
		//创建columns文件夹节点
		TreeNode columnsNode = new TreeNode();
		columnsNode.setDisplayName(IViewConstant.COLUMNS_FOLDER);
		columnsNode.setType(IViewConstant.TYPE_COLUMNS_FOLDER);
		columnsNode.setDisabled(true);
		input.add(columnsNode, columnNodeList);
		
		//获取主键
		PrimaryKey primaryKey = table.getPrimaryKey();
		TreeNode primaryKeyNode = null;
		if(primaryKey != null) {
			//创建主键节点
			primaryKeyNode = new TreeNode();
			primaryKeyNode.setDisabled(true);
			primaryKeyNode.setDisplayName(primaryKey.getName());
			primaryKeyNode.setDisplayName(primaryKey.getName());
			primaryKeyNode.setType(IViewConstant.TYPE_PRIMARY_KEY);
		}
		
		//定义外键节点集合
		List<TreeNode> referenceNodeList = new ArrayList<TreeNode>(); 
		//遍历所有的外键
		for(Entry<String ,ForeignKey> entry: table.getForeignKeys().entrySet()) {
			//创建外键节点
			node = new TreeNode();
			String tableName = entry.getValue().getReferencedTable().getName();
			String referName = entry.getKey();
			node.setName(referName);
			node.setDisplayName(referName + "(-> " + tableName.toUpperCase(Locale.getDefault()) + ")");
			node.setType(IViewConstant.TYPE_REFERENCE_KEY);
			referenceNodeList.add(node);
		}
		
		//创建约束文件夹节点
		TreeNode constraintsNode = new TreeNode();
		constraintsNode.setName(IViewConstant.CONSTRAINT_FOLDER);
		constraintsNode.setDisplayName(IViewConstant.CONSTRAINT_FOLDER);
		constraintsNode.setType(IViewConstant.TYPE_CONSTRAINT_FOLDER);
		if(primaryKeyNode != null) {
			input.add(constraintsNode, primaryKeyNode);
		}
		input.add(constraintsNode, referenceNodeList);
		
		//清空临时节点 
		currentNode.getChilds().clear();
		
		//添加列节点到columns文件夹节点
		input.add(currentNode, columnsNode);
		input.add(currentNode, constraintsNode);
	
	}
	
	/**
	 * 展开序列
	 * @param currentNode
	 */
	private void expandSequences(TreeNode currentNode) {
		try {
			List<String> sequenceList = omReverse.fetchSequences(dataSourceInfo);
			//获取模型
			TreeModel input = (TreeModel) treeViewer.getInput();
			
			List<TreeNode> rsNodeList = new ArrayList<TreeNode>(); 
			TreeNode node = null;
			for(String sequenceName: sequenceList) {
				node = new TreeNode();
				node.setDisabled(true);
				node.setName(sequenceName);
				node.setDisplayName(sequenceName);
				node.setType(IViewConstant.TYPE_SEQUENCES_NODE);
				rsNodeList.add(node);
			}
			
			//清空临时节点 
			currentNode.getChilds().clear();
			
			//添加子节点到当前节点
			input.add(currentNode, rsNodeList);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	
	}
	
	/**
	 * 展开视图
	 * @param currentNode
	 */
	private void expandViews(TreeNode currentNode) {
		List<View> viewsList = omReverse.fetchViews(dataSourceInfo);
		//获取模型
		TreeModel input = (TreeModel) treeViewer.getInput();
		
		List<TreeNode> rsNodeList = new ArrayList<TreeNode>(); 
		TreeNode node = null;
		for(View view: viewsList) {
			node = new TreeNode();
			node.setName(view.getName());
			node.setDisplayName(view.getName());
			node.setType(IViewConstant.TYPE_VIEWS_NODE);
			node.setDisabled(true);
			
			rsNodeList.add(node);
		}
		
		//清空临时节点 
		currentNode.getChilds().clear();
		
		//添加子节点到当前节点
		input.add(currentNode, rsNodeList);
	
	}
	
	private void expandViewsToColumns(TreeNode currentNode) {
		View view = omReverse.fetchViewColumns(currentNode.getName());
		
		//定义列节点集合
		List<TreeNode> columnNodeList = new ArrayList<TreeNode>(); 
		TreeNode node = null;
				
		Map<String, Column> columnMap = view.getColumns();
		for(Entry<String, Column> entry: columnMap.entrySet()) {
			//创建列节点
			node = new TreeNode();
			node.setDisplayName(entry.getKey());
			node.setType(IViewConstant.TYPE_COLUMNS_NODE);
			node.setDisabled(true);
			columnNodeList.add(node);
		}
		
		//创建columns文件夹节点
		TreeNode columnsNode = new TreeNode();
		columnsNode.setDisplayName(IViewConstant.COLUMNS_FOLDER);
		columnsNode.setType(IViewConstant.TYPE_COLUMNS_FOLDER);
		columnsNode.setDisabled(true);
		
		//获取模型
		TreeModel input = (TreeModel) treeViewer.getInput();
		input.add(columnsNode, columnNodeList);
		//添加列节点到columns文件夹节点
		currentNode.getChilds().clear();
		input.add(currentNode, columnsNode);
	}
}
