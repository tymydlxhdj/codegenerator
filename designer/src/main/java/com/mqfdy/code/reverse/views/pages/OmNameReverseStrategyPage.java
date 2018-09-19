package com.mqfdy.code.reverse.views.pages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.TreeEvent;
import org.eclipse.swt.events.TreeListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.ScrollBar;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;

import com.mqfdy.code.designer.dialogs.IBusinessClassEditorPage;
import com.mqfdy.code.designer.editor.BusinessModelEditorPlugin;
import com.mqfdy.code.designer.preferences.ModelPreferencePage;
import com.mqfdy.code.designer.utils.CheckerUtil;
import com.mqfdy.code.model.Association;
import com.mqfdy.code.model.BusinessClass;
import com.mqfdy.code.model.BusinessObjectModel;
import com.mqfdy.code.model.utils.StringUtil;
import com.mqfdy.code.resource.validator.ValidatorUtil;
import com.mqfdy.code.reverse.IOmReverse;
import com.mqfdy.code.reverse.OmReverse;
import com.mqfdy.code.reverse.ReverseContext;
import com.mqfdy.code.reverse.mappings.Column;
import com.mqfdy.code.reverse.mappings.ForeignKey;
import com.mqfdy.code.reverse.mappings.PrimaryKey;
import com.mqfdy.code.reverse.mappings.Table;
import com.mqfdy.code.reverse.utils.ReverseUtil;
import com.mqfdy.code.reverse.views.beans.TreeNode;
import com.mqfdy.code.reverse.views.constant.IViewConstant;
import com.mqfdy.code.reverse.views.models.TreeModel;
import com.mqfdy.code.reverse.views.providers.MultiTreeContentProvider;
import com.mqfdy.code.reverse.views.providers.TreeLabelProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class OmNameReverseStrategyPage.
 *
 * @author mqfdy
 */
public class OmNameReverseStrategyPage extends WizardPage{
	
	/** The Constant WIDTH. */
	public static final int WIDTH = 600;
	
	/** The Constant TREE_WIDTH. */
	public static final int TREE_WIDTH = 150;
	
	/** The Constant UPGROUP_HEIGHT. */
	public static final int UPGROUP_HEIGHT = 300;
	
	/** The Constant DOWNGROUP_HEIGHT. */
	public static final int DOWNGROUP_HEIGHT = 100;
	
//	public static final String ASSOCIATION = "关联关系";
	
	/** The db tree viewer. */
private TreeViewer dbTreeViewer;
	
	/** The db tree. */
	private Tree dbTree;

	/** The biz tree viewer. */
	private TreeViewer bizTreeViewer;
	
	/** The biz tree. */
	private Tree bizTree;
	
	/** The up group. */
	private Group upGroup;
	
	/** The down group. */
	private Group downGroup;
	
	/** The group 1. */
	private Group group1;
	
	/** The tab name radio 1. */
	private Button tabNameRadio1;
	
	/** The tab name radio 2. */
	private Button tabNameRadio2;
	
	/** The group 2. */
	private Group group2;
	
	/** The prop name radio 2. */
	private Button propNameRadio2;
	
	/** The prop name radio 1. */
	private Button propNameRadio1;

	/** The om reverse. */
	private IOmReverse omReverse;
	
	/** The change strategy. */
	private Button changeStrategy;
	
	/** The down grid data. */
	private GridData downGridData;
	
	/** The db grid data. */
	private GridData dbGridData;
	
	/** The container. */
	private Composite container;
	
	/** The selected node. */
	private TreeNode selectedNode;//选过的节点
	
	/** The node error count. */
	private int nodeErrorCount;//错误的节点个数
	
	/** The is before. */
	private boolean isBefore = true;
	
	/**
	 * Instantiates a new om name reverse strategy page.
	 *
	 * @param pageName
	 *            the page name
	 */
	public OmNameReverseStrategyPage(String pageName) {
		super(pageName);
		omReverse = new OmReverse();
	}
	
	/**
	 * Creates the control.
	 *
	 * @author mqfdy
	 * @param composite
	 *            the composite
	 * @Date 2018-09-03 09:00
	 */
	public void createControl(Composite composite) {

		setTitle("表转换业务实体");
		container = new Composite(composite, SWT.NULL);
		
		//设定整体布局
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 1;
		container.setLayout(gridLayout);
		container.layout();
		
		//在父容器中创建子容器upGroup
		upGroup = new Group(container, SWT.NULL);
		upGroup.setText("表和业务实体映射");
		
		//设置upGroup在父容器中的布局参数
		GridData upGridData = new GridData(GridData.FILL_BOTH);
		upGroup.setLayoutData(upGridData);
		
		//创建upGroup内的布局
		GridLayout upLayout = new GridLayout();
		upLayout.numColumns = 2;
		
		//设置upGroup的布局
		upGroup.setLayout(upLayout);
		upGroup.layout();
		
		//数据库树控件布局参数
		dbGridData = new GridData(GridData.FILL_BOTH);
		dbGridData.heightHint = UPGROUP_HEIGHT + DOWNGROUP_HEIGHT;
				
		//业务实体树查看器
		dbTreeViewer = new TreeViewer(upGroup , SWT.FULL_SELECTION);
		//设置树查看器内容提供者
		dbTreeViewer.setContentProvider(new MultiTreeContentProvider());
		//设置树查看器标签提供者
		dbTreeViewer.setLabelProvider(new TreeLabelProvider());
		
		dbTreeViewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				TreeSelection selection = (TreeSelection) event.getSelection();
				TreeNode currentNode = (TreeNode) selection.getFirstElement();
				
				TreeItem treeItem = findTreeItem(bizTree, currentNode.getId());
				TreeNode currentBizNode = (TreeNode) treeItem.getData();
				if(!dbTreeViewer.getExpandedState(currentNode)) {
					dbTreeViewer.setExpandedState(currentNode, true);
					bizTreeViewer.setExpandedState(currentBizNode, true);
				} else {
					dbTreeViewer.setExpandedState(currentNode, false);
					bizTreeViewer.setExpandedState(currentBizNode, false);
				}
				
			}
		});
		
		dbTree = dbTreeViewer.getTree();
		
		dbTree.setLayoutData(dbGridData);
		dbTree.setHeaderVisible(true);
		dbTree.setLinesVisible(true);
		
		//选中左侧某个树节点,同时选中右侧树节点
		dbTree.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent evt) {
				TreeItem dbItem = (TreeItem) evt.item;
				TreeNode treeNode = (TreeNode) dbItem.getData();
				TreeItem bizItem = findTreeItem(bizTree, treeNode.getId());
				bizTree.select(bizItem);
			}
		});
		
		//左侧树展开事件
		dbTree.addTreeListener(new TreeListener() {
			public void treeExpanded(TreeEvent evt) {
				//同时选择右侧树的对应节点
				TreeItem dbItem = (TreeItem) evt.item;
				TreeNode treeNode = (TreeNode) dbItem.getData();
				TreeItem bizItem = findTreeItem(bizTree, treeNode.getId());
				bizTreeViewer.expandToLevel((TreeNode) bizItem.getData(), 1);
			}
			
			public void treeCollapsed(TreeEvent evt) {
				TreeItem dbItem = (TreeItem) evt.item;
				TreeNode treeNode = (TreeNode) dbItem.getData();
				TreeItem bizItem = findTreeItem(bizTree, treeNode.getId());
				bizTreeViewer.collapseToLevel((TreeNode) bizItem.getData(), 1);
			}
		});
		
		//左侧树滚动条滑动联动事件
		final ScrollBar dbScrollBar = dbTree.getVerticalBar();
		dbScrollBar.addSelectionListener(new SelectionListener() {
			
			public void widgetSelected(SelectionEvent e) {
				//左侧树当前显示的第一个树节点
				TreeItem dbTopItem = dbTree.getTopItem();
				//找到对应右侧树的节点
				TreeNode treeNode = (TreeNode) dbTopItem.getData();
				TreeItem bizItem = findTreeItem(bizTree, treeNode.getId());
				//设置右侧树的显示的第一个节点
				bizTree.setTopItem(bizItem);
				
			}
			
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
		
		//数据库表名列
		TreeColumn tableNameColumn = new TreeColumn(dbTree, SWT.NONE);
		tableNameColumn.setWidth(TREE_WIDTH);
		tableNameColumn.setAlignment(SWT.CENTER);
		tableNameColumn.setText("数据库表");
		
		TreeColumn commnentColumn = new TreeColumn(dbTree, SWT.NONE);
		commnentColumn.setWidth(TREE_WIDTH);
		commnentColumn.setAlignment(SWT.CENTER);
		commnentColumn.setText("备注信息");
		
		//创建带复选框的树查看器
		bizTreeViewer = new TreeViewer(upGroup, SWT.SINGLE | SWT.BORDER| SWT.FULL_SELECTION);
		bizTree = bizTreeViewer.getTree();
		bizTree.setLayoutData(dbGridData);
		bizTree.setHeaderVisible(true);
		bizTree.setLinesVisible(true);
		
		//选中右侧树,同步选中左侧树
		bizTree.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent evt) {
				TreeItem bizItem = (TreeItem) evt.item;
				TreeNode treeNode = (TreeNode) bizItem.getData();
				TreeItem dbItem = findTreeItem(dbTree, treeNode.getId());
				dbTree.select(dbItem);
				
				selectedNode = treeNode;
				
			}
		});
		
		//右侧树展开事件
		bizTree.addTreeListener(new TreeListener() {
			public void treeExpanded(TreeEvent evt) {
				//同时选择左侧树的对应节点
				TreeItem bizItem = (TreeItem) evt.item;
				TreeNode treeNode = (TreeNode) bizItem.getData();
				TreeItem dbItem = findTreeItem(dbTree, treeNode.getId());
				dbTreeViewer.expandToLevel((TreeNode) dbItem.getData(), 1);
				validateTree();
			}
			
			public void treeCollapsed(TreeEvent evt) {
				TreeItem bizItem = (TreeItem) evt.item;
				TreeNode treeNode = (TreeNode) bizItem.getData();
				TreeItem dbItem = findTreeItem(dbTree, treeNode.getId());
				dbTreeViewer.collapseToLevel((TreeNode) dbItem.getData(), 1);
			}
		});
		
		//右侧树滚动条滑动联动事件
		final ScrollBar bizScrollBar = bizTree.getVerticalBar();
		bizScrollBar.addSelectionListener(new SelectionListener() {
			
			public void widgetSelected(SelectionEvent e) {
				//左侧树当前显示的第一个树节点
				TreeItem bizTopItem = bizTree.getTopItem();
				//找到对应右侧树的节点
				TreeNode treeNode = (TreeNode) bizTopItem.getData();
				TreeItem dbItem = findTreeItem(dbTree, treeNode.getId());
				//设置右侧树的显示的第一个节点
				dbTree.setTopItem(dbItem);
				
			}
			
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
		
		//设置树查看器内容提供者
		bizTreeViewer.setContentProvider(new MultiTreeContentProvider());
		//设置树查看器标签提供者
		bizTreeViewer.setLabelProvider(new TreeLabelProvider());
		
		bizTreeViewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				TreeSelection selection = (TreeSelection) event.getSelection();
				TreeNode currentNode = (TreeNode) selection.getFirstElement();
				
				TreeItem treeItem = findTreeItem(dbTree, currentNode.getId());
				TreeNode currentDbNode = (TreeNode) treeItem.getData();
				if(!bizTreeViewer.getExpandedState(currentNode)) {
					bizTreeViewer.setExpandedState(currentNode, true);
					dbTreeViewer.setExpandedState(currentDbNode, true);
				} else {
					bizTreeViewer.setExpandedState(currentNode, false);
					dbTreeViewer.setExpandedState(currentDbNode, false);
				}
				
			}
		});
		
		//业务实体名列
		TreeColumn bcNameColumn = new TreeColumn(bizTree, SWT.NONE);
		bcNameColumn.setWidth(TREE_WIDTH);
		bcNameColumn.setAlignment(SWT.CENTER);
		bcNameColumn.setText("业务实体");
		//业务实体备注列
		TreeColumn bcCommentColumn = new TreeColumn(bizTree, SWT.NONE);
		bcCommentColumn.setWidth(TREE_WIDTH);
		bcCommentColumn.setAlignment(SWT.CENTER);
		bcCommentColumn.setText("显示名");
		
		changeStrategy = new Button(container, SWT.CHECK);
		changeStrategy.setSelection(false);
		changeStrategy.setText("更改转换策略");
		changeStrategy.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent evt) {
				if(changeStrategy.getSelection()) {
					downGroup.setVisible(true);
					dbGridData.heightHint = UPGROUP_HEIGHT;
					downGridData.heightHint = DOWNGROUP_HEIGHT;
				} else {
					downGroup.setVisible(false);
					dbGridData.heightHint = UPGROUP_HEIGHT + DOWNGROUP_HEIGHT;
					downGridData.heightHint = 0;
				}
				container.layout();
			}
			
		});
		
		//在父容器中创建子容器downGroup
		downGroup = new Group(container, SWT.NULL);
		downGroup.setVisible(false);
		
		//设置downGroup在父容器中的布局参数
		downGridData = new GridData(GridData.FILL_BOTH);
		downGridData.heightHint = 0;
		downGroup.setLayoutData(downGridData);
		
		//创建downGroup内的布局
		GridLayout downLayout = new GridLayout();
		downLayout.numColumns = 2;
		//设置downGridData的布局
		downGroup.setLayout(downLayout);
		downGroup.layout();
		
		group1 = new Group(downGroup, SWT.NULL);
		group1.setText("表名转换");
		group1.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		GridLayout group1Layout = new GridLayout();
		group1Layout.numColumns = 1;
		group1.setLayout(group1Layout);
		group1.layout();
		
		tabNameRadio1 = new Button(group1, SWT.RADIO);
		tabNameRadio1.setSelection(true);
		tabNameRadio1.setText("去掉\"_\" + 驼峰式(AA_TAB -> AaTab)");
		tabNameRadio1.setLayoutData(new GridData(SWT.LEFT, SWT.LEFT, true, true, 1, 1));
		tabNameRadio1.addSelectionListener(new TableConvertSelectionAdapter(tabNameRadio1));
		
		tabNameRadio2 = new Button(group1, SWT.RADIO);
		tabNameRadio2.setLayoutData(new GridData(SWT.LEFT, SWT.LEFT, true, true, 1, 1));
		tabNameRadio2.setText("保留\"_\" + 驼峰式(AA_TAB -> Aa_Tab)");
		tabNameRadio2.addSelectionListener(new TableConvertSelectionAdapter(tabNameRadio2));
		
		group2 = new Group(downGroup, SWT.NULL);
		group2.setText("字段名转换");
		group2.setLayoutData(new GridData(GridData.FILL_BOTH));
		GridLayout group2Layout = new GridLayout();
		group1Layout.numColumns = 1;
		group2.setLayout(group2Layout);
		group2.layout();
		
		propNameRadio1 = new Button(group2, SWT.RADIO);
		propNameRadio1.setSelection(true);
		propNameRadio1.setText("去掉\"_\" + 驼峰式(AA_COL -> AaCol)");
		propNameRadio1.setLayoutData(new GridData(SWT.LEFT, SWT.LEFT, true, true, 1, 1));
		propNameRadio1.addSelectionListener(new ColumnConvertSelectionAdapter(propNameRadio1));
		
		propNameRadio2 = new Button(group2, SWT.RADIO);
		propNameRadio2.setLayoutData(new GridData(SWT.LEFT, SWT.LEFT, true, true, 1, 1));
		propNameRadio2.setText("保留\"_\" + 驼峰式(AA_COL -> Aa_Col)");
		propNameRadio2.addSelectionListener(new ColumnConvertSelectionAdapter(propNameRadio2));
		
		setControl(container);
	}
	
	/**
	 * 通过id查找树节点.
	 *
	 * @author mqfdy
	 * @param tree
	 *            the tree
	 * @param id
	 *            the id
	 * @return the tree item
	 * @Date 2018-09-03 09:00
	 */
	public TreeItem findTreeItem(Tree tree, String id) {
		for (int i = 0; i < tree.getItemCount(); i++) {
			TreeItem treeItemLevel1 = tree.getItem(i);
			TreeNode treeNodeLevel1 = (TreeNode) treeItemLevel1.getData();
			
			if(id.equals(treeNodeLevel1.getId())) {
				return treeItemLevel1;
			}
			
			for (int j = 0; j < treeItemLevel1.getItemCount(); j++) {
				TreeItem treeItemLevel2 = treeItemLevel1.getItem(j);
				TreeNode treeNodeLevel2 = (TreeNode) treeItemLevel2.getData();
				if(treeNodeLevel2 != null) {
					if(id.equals(treeNodeLevel2.getId())) {
						return treeItemLevel2;
					}
				}
			}
		}
		return null;
	}
	
	/**
	 * 重画.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	public void repaint(){
		
		nodeErrorCount = 0;
		setMessage("");
		setPageComplete(true);
		
		List<Table> tableList = omReverse.fetchLastTables();
		//列出表转换的清单
		listTables(tableList);
		//根据表加载业务实体
		listBusinessClasses();
		
		bizTreeViewer.setColumnProperties(new String[] {"name", "comment"});
		
		CellEditor[] cellEditor = new CellEditor[2];
		cellEditor[0] = new TextCellEditor(bizTreeViewer.getTree(), SWT.BORDER);
		cellEditor[1] = new TextCellEditor(bizTreeViewer.getTree(), SWT.BORDER);
		bizTreeViewer.setCellEditors(cellEditor);
		bizTreeViewer.setCellModifier(new ICellModifier() {
			
			public void modify(Object element, String property, Object value) {
				TreeItem treeItem = (TreeItem) element;
				TreeNode treeNode = (TreeNode) treeItem.getData();
				
				String newValue = String.valueOf(value);
				if(newValue.trim().length() > 30) {
					newValue = newValue.substring(0, 30);
				}
				
				if (!ValidatorUtil.valiDisplayName(newValue)) {
					setMessage(IBusinessClassEditorPage.ERROR_DISPLAYNAME,IMessageProvider.ERROR);
					return;
				}
				if (!ValidatorUtil.valiDisplayNameLength(newValue)) {
					setMessage(IBusinessClassEditorPage.ERROR_DISPLAYNAME_LENGTH,IMessageProvider.ERROR);
					return;
				}
				
				
				if(property.equals("name")) {
					if(!newValue.matches(ValidatorUtil.FIRSTNO_NAMEREGEX)) {
						setMessage("名称只能包含字母、数字和下划线，且以字母或下划线开头",IMessageProvider.ERROR);
						return;
					}else if(treeNode.getBackup() instanceof Table&&!newValue.matches(ValidatorUtil.FIRSTLETTERUPPERCASE)){
						setMessage("业务实体名首字母必须大写",IMessageProvider.ERROR);
						return;
					}
					else if(newValue.equalsIgnoreCase("java")){
						setMessage("名称不能为\"java\"",IMessageProvider.ERROR);
						return;
					}
					else if(CheckerUtil.checkJava(String.valueOf(newValue))){
						setMessage("名称不能为JAVA关键字",IMessageProvider.ERROR);
						return;
					}else if(CheckerUtil.checkSql(String.valueOf(newValue))){
						setMessage("名称不能为SQL关键字",IMessageProvider.ERROR);
						return;
					}
				
					treeNode.setName(newValue);
					treeNode.setDisplayName(newValue);
					validate(newValue,  treeNode);
					makeRed(treeItem,treeNode);
//					TreeNode parentNode=(TreeNode)(treeItem.getParentItem().getData());
//					if(!(CheckerUtil.checkJava(String.valueOf(parentNode.getDisplayName())) || CheckerUtil.checkSql(parentNode.getDisplayName()) 
//							|| CheckerUtil.checkSguap(parentNode.getDisplayName()))){
//						 boolean bool=true;
//						for(TreeItem ite: treeItem.getParentItem().getItems()){
//							TreeNode node=(TreeNode) ite.getData();
//							if(CheckerUtil.checkJava(String.valueOf(node.getDisplayName())) || CheckerUtil.checkSql(node.getDisplayName()) 
//									|| CheckerUtil.checkSguap(node.getDisplayName())){
//								bool=false;
//								break;
//							}
//						}
//						if(bool){
//							Color color=new Color(null,255,0,0);
//							bizTreeViewer.getTree().getItem(i).setBackground(color);
//						}
//					}
				} else if(property.equals("comment")) {
					Object object = treeNode.getBackup();
					if(object instanceof Table) {
						Table table = (Table) object;
						table.setComment(String.valueOf(newValue));
					} else if(object instanceof Column) {
						Column column = (Column) object;
						column.setComment(String.valueOf(newValue));
					} else if(object instanceof Association) {
						Association association = (Association) object;
//						treeNode.setDisplayName(newValue);
//						((Association)treeNode.getData()).setDisplayName(String.valueOf(newValue));
						association.setDisplayName(String.valueOf(newValue));
					}
				}
				
				bizTreeViewer.update(treeNode, null);
				bizTreeViewer.refresh();
			
			}
			
			public Object getValue(Object element, String property) {
				TreeNode treeNode = (TreeNode) element;
				if(property.equals("comment")) {
					Object object = treeNode.getBackup();
					if(object != null) {
						if(object instanceof Table) {
							Table table = (Table) object;
							return table.getComment() == null ? "" : table.getComment();
						} else if(object instanceof Column) {
							Column column = (Column) object;
							return column.getComment() == null? "":column.getComment();
						} else if(object instanceof Association) {
							Association association = (Association) object;
							return association.getDisplayName();
						}
					}
				}
				return treeNode.getDisplayName();
			}
			
			public boolean canModify(Object element, String property) {
				
				TreeNode treeNode = (TreeNode) element;
				
				if (selectedNode == treeNode) {
					if(treeNode.getType() == IViewConstant.TYPE_RELATION) {
						return false;
					}
					return true;
				}
				return false;
			}
		});
		
		bizTreeViewer.refresh();
	}
	
	/**
	 * Make red.
	 *
	 * @author mqfdy
	 * @param treeItem
	 *            the tree item
	 * @param treeNode
	 *            the tree node
	 * @Date 2018-09-03 09:00
	 */
	private void makeRed(TreeItem treeItem , TreeNode treeNode){
		TreeItem parentItem;
		if(treeItem.getParentItem()==null){
			parentItem=treeItem;
		}else{
			parentItem=treeItem.getParentItem();
		}
		if(parentItem!=null){
			for(int i=0;i<parentItem.getItems().length;i++){
				if((TreeNode)parentItem.getItems()[i].getData()!=null){
					if(((TreeNode)parentItem.getItems()[i].getData()).getType()==IViewConstant.TYPE_PRIMARYKEY_ERROR||
							((TreeNode)parentItem.getItems()[i].getData()).getType()==IViewConstant.TYPE_OPERATION_ERROR||
							((TreeNode)parentItem.getItems()[i].getData()).getType()==IViewConstant.TYPE_BUSINESSCLASS_ERROR){
						
						makeRed(parentItem);
						break;
					}else if(i==parentItem.getItems().length-1&&
							((TreeNode)parentItem.getItems()[i].getData()).getType()!=IViewConstant.TYPE_PRIMARYKEY_ERROR&&
							((TreeNode)parentItem.getItems()[i].getData()).getType()!=IViewConstant.TYPE_OPERATION_ERROR&&
							((TreeNode)parentItem.getItems()[i].getData()).getType()!=IViewConstant.TYPE_BUSINESSCLASS_ERROR){
						makeWhite(parentItem);
					}
				}
				
			}
		}
	}
	
	/**
	 * Make red.
	 *
	 * @author mqfdy
	 * @param treeItem
	 *            the tree item
	 * @Date 2018-09-03 09:00
	 */
	private void makeRed(TreeItem treeItem ){
		Color color=new Color(null,255,0,0);
		treeItem.setBackground(color);
	}
	
	/**
	 * Make white.
	 *
	 * @author mqfdy
	 * @param treeItem
	 *            the tree item
	 * @Date 2018-09-03 09:00
	 */
	private void makeWhite(TreeItem treeItem){
		Color color=new Color(null,255,255,255);
		treeItem.setBackground(color);
	}
	
	/**
	 * 列出表转换的清单.
	 *
	 * @author mqfdy
	 * @param tableList
	 *            the table list
	 * @Date 2018-09-03 09:00
	 */
	private void listTables(List<Table> tableList) {
		List<TreeModel> tableModelList = new ArrayList<TreeModel>();
		
		TreeNode tabNode = null;
		TreeModel tableNodeModel = null;
		
		for(Table table: tableList) {
			//校验表显示名称，如果有特殊字符就过滤掉，如果大于30就截取成30个字符。
			if(table.getComment() != null) {
				table.setComment(ValidatorUtil.delErrorString(table.getComment()));
			}
			
			//创建一个表节点
			tabNode = new TreeNode();
			tabNode.setId(UUID.randomUUID().toString());
			tabNode.setName(table.getName());
			tabNode.setDisplayName(table.getName());
			tabNode.setBackup(table);
			tabNode.setType(IViewConstant.TYPE_TABLE_NODE);
			tabNode.setData(table);
			tableNodeModel = new TreeModel(tabNode);
			
			Map<String, Column> columnMap = table.getColumns();
			
			//校验列显示名称，如果有特殊字符就过滤掉，如果大于30就截取成30个字符。
			for(Entry<String, Column> entry: columnMap.entrySet()) {
				if(entry.getValue().getComment() != null) {
					entry.getValue().setComment(ValidatorUtil.delErrorString(entry.getValue().getComment()));
				}
			}
			
			List<Column> primaryKeyList = new ArrayList<Column>();
			//获得当前表实例的主键列
			PrimaryKey primaryKey = table.getPrimaryKey();
			if(primaryKey != null) {
				primaryKeyList = primaryKey.getColumns();
			}
			
			Map<String, Column> primaryKeyMap = new HashMap<String, Column>();
			for(Column column: primaryKeyList) {
				primaryKeyMap.put(column.getName(), column);
			}
			
			//当前表实例的所有外键包含的列（本表）
			Map<String, Column> allReferColumnMap = new HashMap<String, Column>();
			//当前表实例 的所有外键
			Map<String, ForeignKey> foreignKeyMap = table.getForeignKeys();
			for(Entry<String, ForeignKey> entry: foreignKeyMap.entrySet()) {
				ForeignKey foreignKey = entry.getValue();
				for(Column referCol: foreignKey.getColumns()) {
					allReferColumnMap.put(referCol.getName(), referCol);
				}
			}
			
			if(!ReverseUtil.isManyToManyTable(table)){
				TreeNode colNode = null;
				
				//先找主键的列
				for(Column column: primaryKeyList) {
					colNode = new TreeNode();
					colNode.setId(UUID.randomUUID().toString());
					colNode.setName(column.getName());
					colNode.setDisplayName(column.getName());
					colNode.setBackup(column);
					colNode.setType(IViewConstant.TYPE_PRIMARY_COLUMN);
					colNode.setData(column);
					tableNodeModel.add(tabNode, colNode);
				}
				
				//再找普通的列
				for(Entry<String, Column> entry: columnMap.entrySet()) {
					//当前列不是主键
					if(primaryKeyMap.get(entry.getKey()) == null && allReferColumnMap.get(entry.getKey()) == null) {
						//创建一个列节点
						colNode = new TreeNode();
						colNode.setId(UUID.randomUUID().toString());
						colNode.setName(entry.getKey());
						colNode.setDisplayName(entry.getKey());
						colNode.setBackup(entry.getValue());
						colNode.setType(IViewConstant.TYPE_COLUMNS_NODE);
						colNode.setData(entry.getValue());
						tableNodeModel.add(tabNode, colNode);
					}
				}
				
				//再找外键的列
				for(Entry<String,Column> entry: allReferColumnMap.entrySet()) {
					//创建一个列节点
					colNode = new TreeNode();
					colNode.setId(UUID.randomUUID().toString());
					colNode.setName(entry.getKey());
					colNode.setDisplayName(entry.getKey());
					colNode.setBackup(entry.getValue());
					colNode.setType(IViewConstant.TYPE_REFERENCE_COLUMN);
					colNode.setData(entry.getValue());
					tableNodeModel.add(tabNode, colNode);
				}
			}
			
			//添加到表模型集合中
			tableModelList.add(tableNodeModel);
		}
		
		dbTreeViewer.setInput(tableModelList);
	}
	
	/**
	 * 根据表加载业务实体.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	@SuppressWarnings("unchecked")
	private void listBusinessClasses() {
		IPreferenceStore store = BusinessModelEditorPlugin.getDefault().getPreferenceStore();
		boolean after = store.getBoolean(ModelPreferencePage.ISADD_AFTERONREVERSE);
		boolean before = store.getBoolean(ModelPreferencePage.ISADD_BEFOREONREVERSE);
		if(after && !before) {
			isBefore = false;
		} else {
			isBefore = true;
		}
		
		List<TreeModel> dbTreeModelList = (List<TreeModel>) dbTreeViewer.getInput();
		
		List<TreeModel> bizTreeModelList = new ArrayList<TreeModel>();
		TreeModel bizTreeMode = null;
		TreeNode businessNode = null;
		TreeNode relationNode = null;
		//遍历所有表节点模型
		for(TreeModel dbTreeInput: dbTreeModelList) {
			
			//获取当前一个表节点
			TreeNode tableNode = dbTreeInput.getRoot();
			Table table = (Table) tableNode.getData();
			
			//遍历当前表的外键，生成本表的列与外键的映射,映射的key用表名 + ‘&&’ + 所有列名拼接
			Map<String, ForeignKey> columnForeignKeyMapping = new HashMap<String, ForeignKey>();
			Map<String, ForeignKey> foreignKeyMap = table.getForeignKeys();
			for(Entry<String, ForeignKey> entry: foreignKeyMap.entrySet()) {
				List<Column> columns = entry.getValue().getColumns();
				String key = table.getName() + "&&" + columns.get(0).getName();
				columnForeignKeyMapping.put(key, entry.getValue());
			}
			
			//如果该表不是多对多的中间表。
			if(!ReverseUtil.isManyToManyTable(table)) {
				//创建业务实体节点
				businessNode = new TreeNode();
				//默认驼峰式转换表名到实体名
				String businessName = noUnderlineHumpConvert(tableNode.getName(), true);
				
				businessNode.setId(tableNode.getId());
				businessNode.setName(businessName);
				businessNode.setDisplayName(businessName);
				businessNode.setType(IViewConstant.TYPE_BUSINESSCLASS);
				
				//设置table为当前节点的备用数据, 是为了在业务节点中可以找到对应的表备注和名字
				businessNode.setBackup(table);
				bizTreeMode = new TreeModel(businessNode);
				
				handleKeywords(businessNode);//校验ava关键字并且处理
				
				validateBom(businessNode, ReverseContext.bom);
				
				if(nodeErrorCount == 0) {
					setMessage("");
					setPageComplete(true);
				} else {
					setMessage("业务实体或属性名称的值含有java的关键字, 或者存在与已有om同名的业务实体,\n"
							  + "错误数:  " + nodeErrorCount + "个", IMessageProvider.ERROR);
					setPageComplete(false);
				}
				
				//获取当前表的所有列
				List<TreeNode> columnNodeList = tableNode.getChilds();
				TreeNode bizPropNode = null;
				for(TreeNode columnNode: columnNodeList) {
					handleKeywords(columnNode);
					if(columnNode.getType() == IViewConstant.TYPE_PRIMARY_COLUMN) {
						//处理主键的列
						//默认驼峰式转换列名到属性名
						String propName = noUnderlineHumpConvert(columnNode.getName(), false);
						
						//创建一个实体的属性
						bizPropNode = new TreeNode();
						bizPropNode.setId(columnNode.getId());
						bizPropNode.setName(propName);
						bizPropNode.setDisplayName(propName);
						bizPropNode.setType(IViewConstant.TYPE_PRIMARY_PROPERTY);

						//设置column为当前节点的备用数据, 是为了在业务节点中可以找到对应的表备注和列名字
						Column column = (Column) columnNode.getData();
						bizPropNode.setBackup(column);
						bizTreeMode.add(businessNode, bizPropNode);
						
						handleKeywords(bizPropNode);//校验java关键字并且处理
					} else if(columnNode.getType() == IViewConstant.TYPE_COLUMNS_NODE) {
						//处理普通的列
						
						//默认驼峰式转换列名到属性名
						String propName = noUnderlineHumpConvert(columnNode.getName(), false);
						
						//创建一个实体的属性
						bizPropNode = new TreeNode();
						bizPropNode.setId(columnNode.getId());
						bizPropNode.setName(propName);
						bizPropNode.setDisplayName(propName);
						bizPropNode.setType(IViewConstant.TYPE_PROPERTY_NODE);

						//设置column为当前节点的备用数据, 是为了在业务节点中可以找到对应的表备注和列名字
						Column column = (Column) columnNode.getData();
						bizPropNode.setBackup(column);
						bizTreeMode.add(businessNode, bizPropNode);
						
						handleKeywords(bizPropNode);
					} else if(columnNode.getType() == IViewConstant.TYPE_REFERENCE_COLUMN) {
						//处理外键的列
						
						//获取当前列对应的外键
						String key = table.getName() + "&&" + columnNode.getName();
						ForeignKey foreignKey = columnForeignKeyMapping.get(key);
						
						if(foreignKey != null) {
							
							//本表转换名称
							String tableName = noUnderlineHumpConvert(table.getName(), true);
							//关联表转换名称
							String newTableName = noUnderlineHumpConvert(foreignKey.getReferencedTable().getName(), true);
							//关联关系显示名称
	//						String name = ASSOCIATION + " (" + tableName + " -> " + newTableName + ")";
							String name = tableName + " -> " + newTableName ;
							//创建一个实体的关联关系
							bizPropNode = new TreeNode();
							bizPropNode.setId(columnNode.getId());
							bizPropNode.setName(name);
							bizPropNode.setDisplayName(name);
							bizPropNode.setType(IViewConstant.TYPE_RELATION);
							
							//设置association为当前节点的备用数据, 是为了在属性节点中可以找到对应的备注信息
							Association association = new Association();
							association.setDisplayName("");
							bizPropNode.setBackup(association);
							bizTreeMode.add(businessNode, bizPropNode);
							
							handleKeywords(bizPropNode);
						}
					}
				}
			} else {
				//把中间表转换成关联关系
				relationNode = new TreeNode();
				
				Map<String, ForeignKey> foreignKey = table.getForeignKeys();
				String name = "";
				int index = 0;
				for(Entry<String, ForeignKey> entry: foreignKey.entrySet()) {
					Table referencedTable = entry.getValue().getReferencedTable();
					String newTableName = noUnderlineHumpConvert(referencedTable.getName(), true);
					if(index == 0) {
						name += newTableName + " - ";
					} else {
						name += newTableName;
					}
					index++;
				}
//				name += ")";
				
				relationNode.setId(tableNode.getId());
				relationNode.setName(name);
				relationNode.setDisplayName(name);
				relationNode.setType(IViewConstant.TYPE_RELATION);
				relationNode.setBackup(table);
				bizTreeMode = new TreeModel(relationNode);
				
				handleKeywords(relationNode);
			}
			
			//添加到业务模型集合中
			bizTreeModelList.add(bizTreeMode);
		}
		
		bizTreeViewer.setInput(bizTreeModelList);
		
		
		/*for(TreeModel model: bizTreeModelList) {
			for(TreeNode node: model.getRoot()) {
				
			}
			validate(node.getDisplayName(), node);
		}*/
	}
	
	/**
	 * @return
	 */
	@Override
	public boolean canFlipToNextPage() {
		return  isPageComplete();
	}

	/**
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public IWizardPage getNextPage() {
		//校验是否有同名业务实体
//		List<TreeModel> bizTreeModelList = (List<TreeModel>) bizTreeViewer.getInput();
//		for(TreeModel input: bizTreeModelList) {
////			TreeNode rootNode = input.getRoot();
////			validateBom(rootNode.getDisplayName(), rootNode, ReverseContext.bom);
//			/*List<TreeNode> childNodeList = ReverseUtil.getAllChildren(rootNode);
//			for(TreeNode childNode: childNodeList) {
//				validateBom(childNode.getDisplayName(), childNode, ReverseContext.bom);
//			}*/
//		}
//		bizTreeViewer.refresh();
		
		/*if(bizNodeErrorCount == 0) {
//			setPageComplete(true);
			
			PackageDispachPage nextPage = (PackageDispachPage) super.getNextPage();
			
			List<TreeNode> bizNodeList = new ArrayList<TreeNode>();
			List<TreeModel> modelList = (List<TreeModel>) bizTreeViewer.getInput();
			for(TreeModel input: modelList) {
				TreeNode node = input.getRoot();
				if(node.getType() != IViewConstant.TYPE_RELATION) {
					bizNodeList.add(input.getRoot());
				}
			}
			nextPage.setBizNodeList(bizNodeList);
			nextPage.repaint();
			return nextPage;
		} else {
			String err = "存在与原有Om重名的业务实体, 错误数:  " + bizNodeErrorCount + "个";
			MessageDialog.openError(getShell(), "校验错误", err);
//			setPageComplete(false);
			return null;
		}*/
		
		PackageDispachPage nextPage = (PackageDispachPage) super.getNextPage();
		
		List<TreeNode> bizNodeList = new ArrayList<TreeNode>();
		List<TreeModel> modelList = (List<TreeModel>) bizTreeViewer.getInput();
		for(TreeModel input: modelList) {
			TreeNode node = input.getRoot();
			if(node.getType() != IViewConstant.TYPE_RELATION) {
				bizNodeList.add(input.getRoot());
			}
		}
		nextPage.setBizNodeList(bizNodeList);
		nextPage.repaint();
		return nextPage;
		
		
	}

	/**
	 * 去“_” + 驼峰式策略转换.
	 *
	 * @author mqfdy
	 * @param str
	 *            the str
	 * @param isEntity
	 *            true: 首字母大写 false: 首字母小写
	 * @return the string
	 * @Date 2018-09-03 09:00
	 */
	protected String noUnderlineHumpConvert(String str, boolean isEntity) {

		StringBuffer buffer = new StringBuffer();
		String[] newStrs = str.split("_");
		
		int index = 0;
		for(String string: newStrs) {
			if(StringUtil.isEmpty(string)) {
				continue;
			}
			
			String firstLetter = string.substring(0, 1);
			String remaining = string.substring(1);
			if(!isEntity && index == 0) {
				buffer.append(firstLetter.toLowerCase(Locale.getDefault()));
			} else {
				buffer.append(firstLetter.toUpperCase(Locale.getDefault()));
			}
			
			buffer.append(remaining.toLowerCase(Locale.getDefault()));
			index++;
		}
		
		String result = buffer.toString();
		return result;
	
	}
	
	/**
	 * 驼峰式策略转换.
	 *
	 * @author mqfdy
	 * @param str
	 *            the str
	 * @param isEntity
	 *            true: 首字母大写 false: 首字母小写
	 * @return the string
	 * @Date 2018-09-03 09:00
	 */
	protected String humpConvert(String str, boolean isEntity) {
		StringBuffer buffer = new StringBuffer();
		String[] newStrs = str.split("_");
		
		int index = 0;
		for(String string: newStrs) {
			String firstLetter = "".equals(string)?"":string.substring(0, 1);
			String remaining = "".equals(string)?"":string.substring(1);
			
			if(index == 0) {
				if(!isEntity) {
					buffer.append(firstLetter.toLowerCase(Locale.getDefault()));
				} else {
					buffer.append(firstLetter.toUpperCase(Locale.getDefault()));
				}
				
				buffer.append(remaining.toLowerCase(Locale.getDefault()));
			} else {
				buffer.append("_");
				buffer.append(firstLetter.toUpperCase(Locale.getDefault()));
				buffer.append(remaining.toLowerCase(Locale.getDefault()));
			}
			
			index++;
		}
		return buffer.toString();
	}
	
	/**
	 * Validate tree.
	 *
	 * @author mqfdy
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	public boolean validateTree(){
		boolean result = true;
		TreeItem[] rootItems =bizTreeViewer.getTree().getItems();
		if(rootItems!=null){
			for(TreeItem root : rootItems){
				TreeNode treeNode = (TreeNode) root.getData();
				if(treeNode == null){
					continue;
				}
				String value = treeNode.getName();
				if(!this.validate(value, treeNode)){
					result = false;
					makeRed(root,treeNode);
				}
				
//				List<TreeNode> subNodes = treeNode.getChilds();
//				if(subNodes!=null){
//					for(TreeNode subNode : subNodes){
//						String subValue = subNode.getName();
//						//列名
//						if(!this.validate(subValue, subNode)){
//							result = false;
//							makeRed(root);
//						}
//					}
//				}
				
				TreeItem[] subItems = root.getItems();
				if(subItems!=null && subItems.length>0){
					for(TreeItem sub : subItems){
						TreeNode subNode = (TreeNode) sub.getData();
						if(subNode == null){
							continue;
						}
						String subValue = subNode.getName();
						if(!this.validate(subValue, subNode)){
							result = false;
							makeRed(sub, subNode);
							bizTreeViewer.update(subNode, null);
						}
					}
				}
			}
		}
		return result;
	}
	
	
	
	/**
	 * 校验当前节点的值是否含有java关键字.
	 *
	 * @author mqfdy
	 * @param value
	 *            the value
	 * @param treeNode
	 *            the tree node
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	private boolean validate(Object value, TreeNode treeNode) {
		if(CheckerUtil.checkJava(String.valueOf(value)) || CheckerUtil.checkSql(String.valueOf(value)) || CheckerUtil.checkSguap(String.valueOf(value))) {
			if(treeNode.getType() == IViewConstant.TYPE_BUSINESSCLASS) {
				treeNode.setType(IViewConstant.TYPE_BUSINESSCLASS_ERROR);
				nodeErrorCount++;
			} else if(treeNode.getType() == IViewConstant.TYPE_PROPERTY_NODE) {
				treeNode.setType(IViewConstant.TYPE_OPERATION_ERROR);
				nodeErrorCount++;
			} else if(treeNode.getType() == IViewConstant.TYPE_PRIMARY_PROPERTY) {
				treeNode.setType(IViewConstant.TYPE_PRIMARYKEY_ERROR);
				nodeErrorCount++;
			}
			
		} else {
			if(treeNode.getType() == IViewConstant.TYPE_BUSINESSCLASS_ERROR) {
				nodeErrorCount--;
				treeNode.setType(IViewConstant.TYPE_BUSINESSCLASS);
			} else if(treeNode.getType() == IViewConstant.TYPE_OPERATION_ERROR) {
				nodeErrorCount--;
				treeNode.setType(IViewConstant.TYPE_PROPERTY_NODE);
			} else if(treeNode.getType() == IViewConstant.TYPE_PRIMARYKEY_ERROR) {
				nodeErrorCount--;
				treeNode.setType(IViewConstant.TYPE_PRIMARY_PROPERTY);
			}
		}
		
		validateBom(treeNode, ReverseContext.bom);
		
		if(nodeErrorCount == 0) {
			setMessage("");
			setPageComplete(true);
			return true;
		} else {
			setMessage("业务实体或属性名称的值含有java、sql、SG-UAP的关键字, 或者存在与已有om同名的业务实体,\n"
					  + "错误数:  " + nodeErrorCount + "个", IMessageProvider.ERROR);
			setPageComplete(false);
			return false;
		}
		
	}
	
	/**
	 * Validate bom.
	 *
	 * @author mqfdy
	 * @param treeNode
	 *            the tree node
	 * @param bom
	 *            the bom
	 * @Date 2018-09-03 09:00
	 */
	private void validateBom(TreeNode treeNode, BusinessObjectModel bom) {

		String value = String.valueOf(treeNode.getDisplayName());
		boolean flag = true;
		for(BusinessClass businessClass: bom.getBusinessClasses()) {
			//如果已有om中存在于当前业务实体同名，但数据库表名不相同的,校验不通过.
			if(treeNode.getBackup() instanceof Column){
				Column column = (Column) treeNode.getBackup();
				if (value.equals(businessClass.getName()) 
						&& !column.getName().equalsIgnoreCase(businessClass.getTableName()) 
						&& treeNode.getType() == IViewConstant.TYPE_BUSINESSCLASS) {
					treeNode.setType(IViewConstant.TYPE_BUSINESSCLASS_CONFLICT);
					nodeErrorCount++;
					flag = false;
					break;
				}
			}
			else if(treeNode.getBackup() instanceof Table){
				Table table = (Table) treeNode.getBackup();
				if (value.equals(businessClass.getName()) 
						&& !table.getName().equalsIgnoreCase(businessClass.getTableName()) 
						&& treeNode.getType() == IViewConstant.TYPE_BUSINESSCLASS) {
					treeNode.setType(IViewConstant.TYPE_BUSINESSCLASS_CONFLICT);
					nodeErrorCount++;
					flag = false;
					break;
				}
			}
			
		}
		if(flag && treeNode.getType() == IViewConstant.TYPE_BUSINESSCLASS_CONFLICT) {
			treeNode.setType(IViewConstant.TYPE_BUSINESSCLASS);
			nodeErrorCount--;
		}
		
	}
	
	/**
	 * 处理关键字.
	 *
	 * @author mqfdy
	 * @param treeNode
	 *            the tree node
	 * @Date 2018-09-03 09:00
	 */
	private void handleKeywords(TreeNode treeNode) {
		String name = treeNode.getName();
		String displayName = treeNode.getDisplayName();
		if(CheckerUtil.checkJava(name) || CheckerUtil.checkSguap(name) || CheckerUtil.checkSql(name)) {
			if(isBefore) {
				treeNode.setName("_" + name);
			} else {
				treeNode.setName(name + "_");
			}
		}
		if(CheckerUtil.checkJava(displayName)|| CheckerUtil.checkSguap(name) || CheckerUtil.checkSql(name)) {
			if(isBefore) {
				treeNode.setDisplayName("_" + displayName);
			} else {
				treeNode.setDisplayName(displayName + "_");
			}
		}
	}
	
	/**
	 * The Class TableConvertSelectionAdapter.
	 *
	 * @author mqfdy
	 */
	class TableConvertSelectionAdapter extends SelectionAdapter {
		
		/** The radio btn. */
		private Button radioBtn;

		/**
		 * Instantiates a new table convert selection adapter.
		 *
		 * @param radioBtn
		 *            the radio btn
		 */
		public TableConvertSelectionAdapter(Button radioBtn) {
			super();
			this.radioBtn = radioBtn;
		}
		
		/**
		 * 表名称转换 判断参数的类型 tabNameRadio1：去“_” + 驼峰式策略转换 tabNameRadio2：驼峰式策略转换
		 * tabNameRadio3：不转换.
		 *
		 * @author mqfdy
		 * @param e
		 *            the e
		 * @Date 2018-09-03 09:00
		 */
		@SuppressWarnings("unchecked")
		public void widgetSelected(SelectionEvent e) {
			if(radioBtn.getSelection()) {
				List<TreeModel> dbTreeModelList = (List<TreeModel>) dbTreeViewer.getInput();
				List<TreeModel> bizTreeModelList = (List<TreeModel>) bizTreeViewer.getInput();
				
				for(int i = 0; i < dbTreeModelList.size(); i++) {
					TreeModel dbTreeModel = dbTreeModelList.get(i);
					TreeModel bizTreeModel = bizTreeModelList.get(i);
					
					TreeNode tableNameNode = dbTreeModel.getRoot();
					TreeNode businessNameNode = bizTreeModel.getRoot();
					
					String newName = "";
					
					if(radioBtn.equals(tabNameRadio1)) {
						newName = noUnderlineHumpConvert(tableNameNode.getName(), true);
					} else if(radioBtn.equals(tabNameRadio2)) {
						newName = humpConvert(tableNameNode.getName(), true);
					}
					
					businessNameNode.setName(newName);
					businessNameNode.setDisplayName(newName);
					validate(businessNameNode.getDisplayName(), businessNameNode);
					if(businessNameNode.getType()==IViewConstant.TYPE_PRIMARYKEY_ERROR||
							businessNameNode.getType()==IViewConstant.TYPE_OPERATION_ERROR||
									businessNameNode.getType()==IViewConstant.TYPE_BUSINESSCLASS_ERROR){
						Color color=new Color(null,255, 0, 0);
						
						bizTreeViewer.getTree().getItem(i).setBackground(color);
					}
					else{
						Color color=new Color(null,255, 255, 255);
						bizTreeViewer.getTree().getItem(i).setBackground(color);
					}
				}
				
				bizTreeViewer.refresh();
			}
		}
	}
	
	/**
	 * The Class ColumnConvertSelectionAdapter.
	 *
	 * @author mqfdy
	 */
	class ColumnConvertSelectionAdapter extends SelectionAdapter {
		
		/** The radio btn. */
		private Button radioBtn;
		
		/**
		 * Instantiates a new column convert selection adapter.
		 *
		 * @param radioBtn
		 *            the radio btn
		 */
		public ColumnConvertSelectionAdapter(Button radioBtn) {
			super();
			this.radioBtn = radioBtn;
		}
		
		/**
		 * Widget selected.
		 *
		 * @author mqfdy
		 * @param e
		 *            the e
		 * @Date 2018-09-03 09:00
		 */
		@SuppressWarnings("unchecked")
		public void widgetSelected(SelectionEvent e) {
			if(radioBtn.getSelection()) {

				List<TreeModel> dbTreeModelList = (List<TreeModel>) dbTreeViewer.getInput();
				List<TreeModel> bizTreeModelList = (List<TreeModel>) bizTreeViewer.getInput();
				
				for(int i = 0; i < dbTreeModelList.size(); i++) {
					TreeModel dbTreeModel = dbTreeModelList.get(i);
					TreeModel bizTreeModel = bizTreeModelList.get(i);
					
					TreeNode tableNameNode = dbTreeModel.getRoot();
					TreeNode businessNameNode = bizTreeModel.getRoot();
					
					List<TreeNode> tableChildNodeList = tableNameNode.getChilds();
					List<TreeNode> businessChildNodeList = businessNameNode.getChilds();
					
					int index = 0;
					for(int j = 0; j < tableChildNodeList.size(); j++) {
						TreeNode columnNode = tableChildNodeList.get(j);
						
						//当前列不是外键的列
						if(IViewConstant.TYPE_REFERENCE_COLUMN != columnNode.getType()) {
							TreeNode propertyNode = businessChildNodeList.get(index++);
							
							String newName = "";
							
							if(radioBtn.equals(propNameRadio1)) {
								newName = noUnderlineHumpConvert(columnNode.getName(), false);
							} else if(radioBtn.equals(propNameRadio2)) {
								newName = humpConvert(columnNode.getName(), false);
							}
							
							propertyNode.setName(newName);
							propertyNode.setDisplayName(newName);
							
							validate(propertyNode.getDisplayName(), propertyNode);
							if(propertyNode.getType()==IViewConstant.TYPE_PRIMARYKEY_ERROR||
									propertyNode.getType()==IViewConstant.TYPE_OPERATION_ERROR||
									propertyNode.getType()==IViewConstant.TYPE_BUSINESSCLASS_ERROR){
								Color color=new Color(null,255, 0, 0);
								bizTreeViewer.getTree().getItem(i).setBackground(color);
							}
						}
						
					}
				}
				
				bizTreeViewer.refresh();
			}
		}
	}
}
