package com.mqfdy.code.thirdparty.wizard;

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
import com.mqfdy.code.reverse.views.pages.SpecialTableWizardPage;
import com.mqfdy.code.reverse.views.providers.SingleTreeLabelProvider;
import com.mqfdy.code.reverse.views.providers.TreeContentProvider;
import com.mqfdy.code.thirdparty.OmImport;

// TODO: Auto-generated Javadoc
/**
 * The Class TableStructureWizardPage.
 *
 * @author mqfdy
 */
public class TableStructureWizardPage extends WizardPage {

	/** The Constant WIDTH. */
	public static final int WIDTH = 500;
	
	/** The check btn. */
	private Button checkBtn;
	
	/** The tree viewer. */
	private CheckboxTreeViewer treeViewer;
	
	/** The tree. */
	private Tree tree;
	
	/** The root. */
	private TreeNode root;
	
	/** The om import. */
	private OmImport omImport;
	
	/** The om reverse. */
	private OmReverse omReverse;
	
	/** The container. */
	private Composite container;
	
	/** The search container. */
	private Composite searchContainer;
	
	/** The table container. */
	private Composite tableContainer;
	
	/** The checked node list. */
	private List<TreeNode> checkedNodeList;//选过的表节点列表
	
	/** The is relative checked. */
	private boolean isRelativeChecked;//选中状态
	
	/** The no PK list. */
	List<Table> noPKList = null;
	
	/** The multi PK list. */
	List<Table> multiPKList = null;
	
	/** The table names. */
	List<String> tableNames;
	
	/** The search text. */
	private Text searchText;
	
	/** The search btn. */
	private Button searchBtn;
	
	/** The searched items. */
	private List<TreeItem> searchedItems = new ArrayList<TreeItem>();
	
	/** The searched count. */
	private int searchedCount = 0;
	
	
	/**
	 * Instantiates a new table structure wizard page.
	 *
	 * @param pageName
	 *            the page name
	 */
	public TableStructureWizardPage(String pageName) {
		super(pageName);
		setTitle("选择要转换的表");
		omImport = new OmImport();
		omReverse = new OmReverse();
		checkedNodeList = new LinkedList<TreeNode>();
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
						//从选过的表姐点列表中清除
						checkedNodeList.remove(currentNode);
					}
					
					List<TreeNode> allChildList = ReverseUtil.getAllChildren(currentNode);
					setAllChecked(allChildList, false);
					//检查是否要选中父节点
					checkParentNode(currentNode, treeViewer);
				}
			}
		});
		
		
		//设置输入模型
		setControl(container);
	}

	/**
	 * 根据前一个页面选择的数据源重画当前页面.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	public void repaint() {
		List<Table> tableList = new ArrayList();
		if(getPreviousPage() instanceof ChooseFileWizardPage){
			String filePath = ((ChooseFileWizardPage)getPreviousPage()).getFilePath();
			tableList = omImport.fetchTables(filePath);
		}
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
		//List<Table> tableList = omReverse.fetchTables(dataSourceInfo);
		//modify by xuran
		
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
	
	/**
	 * @return
	 */
	@Override
	public boolean canFlipToNextPage() {
		return isPageComplete();
	}

	/**
	 * @return
	 */
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
					//Map<String, List<Table>> map = omReverse.findNoPkTable(tableNames, monitor);
					Map<String, List<Table>> map = omImport.findNoPkTable(tableNames, monitor);
					noPKList = map.get(ReverseContants.NOPKTABLE);
					multiPKList = map.get(ReverseContants.MUTILPKTABLE);
					
					Thread.sleep(1000);	//方便使用者看清表的个数
					monitor.done();
				}
			});
			
			//关闭连接对象，释放资源。
			//ReverseContext.wizard.getConnection().close();
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		
//		multiPKList = omReverse.findMutiPkTable(tableNames);
		
		//如果没有特殊的表就跳过特殊表处理页面。
		if((noPKList == null || noPKList.size() == 0) 
				&& (multiPKList == null || multiPKList.size() == 0)) {
			ReverseContext.handleTables.clear();
			return super.getWizard().getPage("osPage");
		}
		
		nextPage.setNoPKTableList(noPKList);
		nextPage.setMutiPkTableList(multiPKList);
		nextPage.repaint();
		
		return nextPage;
	}

	/**
	 * 点击某一个节点时候，检查是否要选中父节点 如果当前节点同级的所有节点都选中，则选中父节点， 否则去掉父节点的勾.
	 *
	 * @author mqfdy
	 * @param currentNode
	 *            the current node
	 * @param treeViewer
	 *            the tree viewer
	 * @Date 2018-09-03 09:00
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
	
	/**
	 * Sets the all checked.
	 *
	 * @author mqfdy
	 * @param nodeList
	 *            the node list
	 * @param checkState
	 *            the check state
	 * @Date 2018-09-03 09:00
	 */
	private void setAllChecked(List<TreeNode> nodeList, boolean checkState) {
		for(TreeNode treeNode : nodeList) {
			if(!treeNode.isDisabled()) {
				treeNode.setChecked(checkState);
				treeViewer.setChecked(treeNode, checkState);
			}
		}
	}
	
	/**
	 * 自动关联有关系的表.
	 *
	 * @author mqfdy
	 * @param currentNode
	 *            当前节点
	 * @param checkState
	 *            选中状态 true:选中, false:不选中
	 * @Date 2018-09-03 09:00
	 */
	private void selectRelativeTables(TreeNode currentNode, boolean checkState) {
		Cursor cursor = new Cursor(container.getDisplay(), SWT.CURSOR_WAIT);
		container.getShell().setCursor(cursor);
		
		//获取具有关联关系的表
		//List<Table> tableList = omReverse.getRelativeTables(currentNode.getName());
		List<Table> tableList = omImport.getRelativeTables(currentNode.getName());
		//获取父节点
		TreeNode parentNode = currentNode.getParent();
		//获取兄弟节点(含自己)
		List<TreeNode> brotherNodeList = parentNode.getChilds();
		//遍历所有兄弟节点,如果当前兄弟节点属于关联的表节点, 改变节点选中的状态
		for(TreeNode brotherNode: brotherNodeList) {
			for(Table table: tableList) {
				if(brotherNode.getName().equals(table.getName())
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
	
}
