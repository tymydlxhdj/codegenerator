package com.mqfdy.code.reverse.views.pages;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;

import com.mqfdy.code.model.AbstractModelElement;
import com.mqfdy.code.model.BusinessClass;
import com.mqfdy.code.model.BusinessObjectModel;
import com.mqfdy.code.model.IModelElement;
import com.mqfdy.code.model.ModelPackage;
import com.mqfdy.code.model.PKProperty;
import com.mqfdy.code.model.PersistenceProperty;
import com.mqfdy.code.model.graph.Diagram;
import com.mqfdy.code.model.graph.DiagramStyle;
import com.mqfdy.code.reverse.ReverseContext;
import com.mqfdy.code.reverse.mappings.Column;
import com.mqfdy.code.reverse.utils.ReverseUtil;
import com.mqfdy.code.reverse.views.beans.BizBean;
import com.mqfdy.code.reverse.views.beans.SpecialTable;
import com.mqfdy.code.reverse.views.beans.TreeNode;
import com.mqfdy.code.reverse.views.constant.IViewConstant;
import com.mqfdy.code.reverse.views.dialogs.InsertPackageDialog;
import com.mqfdy.code.reverse.views.dialogs.RenamePackageDialog;
import com.mqfdy.code.reverse.views.models.BizTableModel;
import com.mqfdy.code.reverse.views.models.TreeModel;
import com.mqfdy.code.reverse.views.providers.BizTableContentProvider;
import com.mqfdy.code.reverse.views.providers.BizTableLabelProvider;
import com.mqfdy.code.reverse.views.providers.MultiTreeContentProvider;
import com.mqfdy.code.reverse.views.providers.PackageTreeLabelProvider;
import com.mqfdy.code.thirdparty.wizard.ThirdPartyWizard;

// TODO: Auto-generated Javadoc
/**
 * The Class PackageDispachPage.
 *
 * @author mqfdy
 */
public class PackageDispachPage extends WizardPage {
	
	/** The Constant SUB_WIDTH. */
	public static final int SUB_WIDTH = 120;
	
	/** The container. */
	private Composite container;
	
	/** The down group. */
	private Group downGroup;
	
	/** The table viewer. */
	private TableViewer tableViewer;
	
	/** The sub container. */
	private Composite subContainer;
	
	/** The dispath btn. */
	private Button dispathBtn;
	
	/** The withdraw btn. */
	private Button withdrawBtn;
	
	/** The tree viewer. */
	private TreeViewer treeViewer;
	
	/** The tree. */
	private Tree tree;

	/** The biz node list. */
	private List<TreeNode> bizNodeList;//业务实体节点集合
	
	/** The biz node map. */
	private Map<String, TreeNode> bizNodeMap;//业务实体节点集合映射
	
	/** The table. */
	private Table table;
	
	/** The select all btn. */
	private Button selectAllBtn;
	
	/** The deselect all btn. */
	private Button deselectAllBtn;
	
	/** The left container. */
	private Composite leftContainer;
	
	/** The leftlabel. */
	private Label leftlabel;
	
	/** The right container. */
	private Composite rightContainer;
	
	/** The right label. */
	private Label rightLabel;
	
	/** The new bu list. */
	private List<BusinessClass> newBuList = new ArrayList<BusinessClass>();
	
	/**
	 * Instantiates a new package dispach page.
	 *
	 * @param pageName
	 *            the page name
	 */
	public PackageDispachPage(String pageName) {
		super(pageName);
		bizNodeList = new ArrayList<TreeNode>();
	}

	/**
	 * Inits the message.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	public void initMessage() {
		setMessage("选择左侧区域的业务实体，分配到右侧区域的业务包中。", IMessageProvider.INFORMATION);
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
		setTitle("选择业务包");
		initMessage();
		
		//为容器创建布局
		container = new Composite(parent, SWT.NO_REDRAW_RESIZE);
		initializeDialogUnits(container);
		
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 1;
		container.setLayout(gridLayout);
		
		//在父容器中创建子容器downGroup
		downGroup = new Group(container, SWT.NULL);

		//设置downGroup在父容器中的布局参数
		GridData downGridData = new GridData(GridData.FILL_BOTH);
		downGroup.setLayoutData(downGridData);
		
		//创建downGroup内的布局
		GridLayout downLayout = new GridLayout();
		downLayout.numColumns = 3;
		//设置downGridData的布局
		downGroup.setLayout(downLayout);
		downGroup.layout();
		
		leftContainer = new Composite(downGroup, SWT.NULL);
		leftContainer.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		leftContainer.setLayout(new GridLayout(1, true));
		leftContainer.layout();
		
		leftlabel = new Label(leftContainer, SWT.NULL);
		leftlabel.setText("待分配的业务实体");
		
		tableViewer = new TableViewer(leftContainer, SWT.MULTI| SWT.BORDER| SWT.FULL_SELECTION);
		
		//创建表格
		table = tableViewer.getTable();
		
		GridData tableData = new GridData(GridData.FILL_BOTH);
		
		table.setLayoutData(tableData);
		table.setHeaderVisible(true);
		table.setLinesVisible(false);
		
		//创建表格列
		TableColumn nameColumn = new TableColumn(table, SWT.NONE);
		nameColumn.setText("名称");
		nameColumn.setWidth(SUB_WIDTH);
		nameColumn.setAlignment(SWT.CENTER);
		
		TableColumn displayColumn = new TableColumn(table, SWT.NONE);
		displayColumn.setText("显示名");
		displayColumn.setWidth(SUB_WIDTH);
		displayColumn.setAlignment(SWT.CENTER);
		
		// 表格双击事件
		tableViewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				ISelection selection = tableViewer.getSelection();
				IStructuredSelection structuredSelection = (IStructuredSelection) selection;
				BizBean bizBean = (BizBean) structuredSelection.getFirstElement();
				
				List<BizBean> selectedList = new ArrayList<BizBean>();
				selectedList.add(bizBean);
				
				
				//如果是只有一个包
				if(tree.getItems().length == 1 && tree.getItems()[0].getItems().length == 0) {
					TreeNode currentPackageNode = (TreeNode) tree.getItems()[0].getData();
					//将左侧选中的业务实体分配到右侧的包中
					dispatchToPackage(selectedList, currentPackageNode);
				} else {
					//获取选中的树节点
					TreeItem[] treeItems = tree.getSelection();
					
					//如果选择了包
					if(treeItems != null && treeItems.length != 0) {
						initMessage();
						if(treeItems.length > 1) {
							setMessage("只能选择一个包节点", IMessageProvider.ERROR);
							return ;
						} else {
							initMessage();
						}
						TreeNode currentPackageNode = (TreeNode) treeItems[0].getData();
						
						//将左侧选中的业务实体分配到右侧的包中
						dispatchToPackage(selectedList, currentPackageNode);
					}
					else {
						setMessage("请选择一个包节点", IMessageProvider.ERROR);
					}
				}
				
//				if(tree.getSelection().length>0&& tree.getSelection()[0] !=null){
//					dispatchToPackage(selectedList, (TreeNode) tree.getSelection()[0].getData());	
//				}
//				else{
//					setMessage("请选择一个包", IMessageProvider.ERROR);
//					return;
//				}
				BizTableModel model = (BizTableModel) tableViewer.getInput();
				Object[] objects = model.elements();
				if(objects == null || objects.length == 0) {
					if(getWizard()  instanceof ReverseWizard){
						ReverseWizard wizard = (ReverseWizard) getWizard();
						wizard.setFinish(true);
					}
					if(getWizard()  instanceof ThirdPartyWizard){
						ThirdPartyWizard wizard = (ThirdPartyWizard) getWizard();
						wizard.setFinish(true);
					}
					
					setPageComplete(true);
				}
				validateFinish(selectedList);
			}
		});
				
		tableViewer.setContentProvider(new BizTableContentProvider());
		tableViewer.setLabelProvider(new BizTableLabelProvider());
		
		//创建一个容器用来放 四个按钮
		GridData subData = new GridData(GridData.FILL_BOTH);
		subData.horizontalAlignment = SWT.CENTER;
		subContainer = new Composite(downGroup, SWT.NULL);
		subContainer.setLayoutData(subData);
		
		//设置容器的布局
		GridLayout subLayout = new GridLayout();
		subLayout.numColumns = 1;
		subContainer.setLayout(subLayout);
		subContainer.layout();
		
		Label label = new Label(subContainer, SWT.NULL);
		label.setText("");
		
		//在容器中放第一个按钮
		dispathBtn = new Button(subContainer, SWT.BUTTON1);
		
		GridData btnData = new GridData(SWT.CENTER, SWT.TOP, true, false);
		int widthHint = convertHorizontalDLUsToPixels(IDialogConstants.BUTTON_WIDTH);
		Point minSize = dispathBtn.computeSize(SWT.DEFAULT, SWT.DEFAULT, true);
		btnData.widthHint = Math.max(widthHint, minSize.x);
		
		dispathBtn.setText("分配 ");
		dispathBtn.setLayoutData(btnData);
		//添加一个事件，将实体分配给一个包。
		dispathBtn.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				
				//如果没有选中的，提示请选择
				TableItem[] tableItems = table.getSelection();
				if(tableItems == null || tableItems.length == 0) {
					setMessage("请至少选择一个业务实体", IMessageProvider.ERROR);
					return ;
				} else {
					initMessage();
				}
				
				//定义一组元素，用于保存以选择的业务实体。
				List<BizBean> selectedList = new ArrayList<BizBean>();
				
				for(TableItem tableItem: tableItems) {
					BizBean bizBean = (BizBean) tableItem.getData();
					selectedList.add(bizBean);
				}
				
				//如果是只有一个包
				if(tree.getItems().length == 1 && tree.getItems()[0].getItems().length == 0) {
					TreeNode currentPackageNode = (TreeNode) tree.getItems()[0].getData();
					//将左侧选中的业务实体分配到右侧的包中
					dispatchToPackage(selectedList, currentPackageNode);
				} else {
					//获取选中的树节点
					TreeItem[] treeItems = tree.getSelection();
					
					//如果选择了包
					if(treeItems != null && treeItems.length != 0) {
						initMessage();
						if(treeItems.length > 1) {
							setMessage("只能选择一个包节点", IMessageProvider.ERROR);
							return ;
						} else {
							initMessage();
						}
						TreeNode currentPackageNode = (TreeNode) treeItems[0].getData();
						
						//将左侧选中的业务实体分配到右侧的包中
						dispatchToPackage(selectedList, currentPackageNode);
					}
					else {
						setMessage("请选择一个包节点", IMessageProvider.ERROR);
					}
				}
				
//				BizTableModel model = (BizTableModel) tableViewer.getInput();
//				Object[] objects = model.elements();
//				if(objects == null || objects.length == 0) {
				validateFinish(selectedList);
			}
		});
				
		selectAllBtn = new Button(subContainer, SWT.BUTTON1);
		selectAllBtn.setText("分配所有");
		selectAllBtn.setLayoutData(btnData);
		selectAllBtn.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				table.selectAll();
				TableItem[] tableItems = table.getSelection();
				
				//定义一组元素，用于保存以选择的业务实体。
				List<BizBean> selectedList = new ArrayList<BizBean>();
				
				for(TableItem tableItem: tableItems) {
					BizBean bizBean = (BizBean) tableItem.getData();
					selectedList.add(bizBean);
				}
				
				//如果是只有一个包
				if(tree.getItems().length == 1 && tree.getItems()[0].getItems().length == 0) {
					TreeNode currentPackageNode = (TreeNode) tree.getItems()[0].getData();
					//将左侧选中的业务实体分配到右侧的包中
					dispatchToPackage(selectedList, currentPackageNode);
				} else {
					//获取选中的树节点
					TreeItem[] treeItems = tree.getSelection();
					
					//如果选择了包
					if(treeItems != null && treeItems.length != 0) {
						initMessage();
						if(treeItems.length > 1) {
							setMessage("只能选择一个包节点", IMessageProvider.ERROR);
							return ;
						} else {
							initMessage();
						}
						TreeNode currentPackageNode = (TreeNode) treeItems[0].getData();
						
						//将左侧选中的业务实体分配到右侧的包中
						dispatchToPackage(selectedList, currentPackageNode);
					} else {
						setMessage("请选择一个包节点", IMessageProvider.ERROR);
					}
				}
				
				BizTableModel model = (BizTableModel) tableViewer.getInput();
				Object[] objects = model.elements();
				if(objects == null || objects.length == 0) {
					if(getWizard()  instanceof ReverseWizard){
						ReverseWizard wizard = (ReverseWizard) getWizard();
						wizard.setFinish(true);
					}
					if(getWizard()  instanceof ThirdPartyWizard){
						ThirdPartyWizard wizard = (ThirdPartyWizard) getWizard();
						wizard.setFinish(true);
					}
					setPageComplete(true);
				}
				
			}
		});
		
		//在容器中放第二个按钮
		withdrawBtn = new Button(subContainer, SWT.BUTTON1);
		withdrawBtn.setText("撤销 ");
		withdrawBtn.setLayoutData(btnData);
		//添加一个事件，将包中的实体还原到左侧。
		withdrawBtn.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				
				boolean flag = true;
				TreeItem[] treeItems = tree.getSelection();
				
				if(treeItems.length == 0) {
					setMessage("请至少选择一个业务实体", IMessageProvider.ERROR);
					return ;
				} else {
					initMessage();
				}

				List<TreeNode> treeNodes = new ArrayList<TreeNode>();
				for(TreeItem item: treeItems) {
					TreeNode treeNode = (TreeNode) item.getData();
					if(treeNode.getType() == IViewConstant.TYPE_PACKAGE) {
						flag = false;
						break;
					}
					treeNodes.add(treeNode);
				}
				
				//如果选择的都是业务实体，则添加到左侧列表中
				if(flag) {
					initMessage();
					//撤销业务实体
					withdraw(treeNodes);
					setPageComplete(false);
				} else {
					setMessage("右侧区域选中的项目中含有包", IMessageProvider.ERROR);
					return ;
				}
				
				
				if(newBuList.size()==0){
					setPageComplete(false);
				}
				else{
					if(getWizard()  instanceof ReverseWizard){
						ReverseWizard wizard = (ReverseWizard) getWizard();
						wizard.setFinish(true);
					}
					if(getWizard()  instanceof ThirdPartyWizard){
						ThirdPartyWizard wizard = (ThirdPartyWizard) getWizard();
						wizard.setFinish(true);
					}
					setPageComplete(true);

				}
			}
		});
		
		deselectAllBtn = new Button(subContainer, SWT.BUTTON1);
		deselectAllBtn.setText("撤销所有");
		deselectAllBtn.setLayoutData(btnData);
		deselectAllBtn.addSelectionListener(new SelectionAdapter() {
			@SuppressWarnings("unchecked")
			public void widgetSelected(SelectionEvent e) {
				rebackAll();
			}
		});
		
		rightContainer = new Composite(downGroup, SWT.NONE);
		rightContainer.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		rightContainer.setLayout(new GridLayout(1, true));
		rightContainer.layout();
		
		rightLabel = new Label(rightContainer, SWT.NONE);
		rightLabel.setText("已分配的业务包");
		
		//在downGroup最右边放一个树(视图package)
		treeViewer = new TreeViewer(rightContainer, SWT.BORDER| SWT.MULTI);
		
		//创建一个树
		tree = treeViewer.getTree();
		tree.setHeaderVisible(true);
		tree.setLinesVisible(false);
		
		//创建树控件的布局参数
		GridData treeData = new GridData(GridData.FILL_BOTH);
		tree.setLayoutData(treeData);
		
		tree.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseDown(MouseEvent evt) {
				final TreeItem selectedItem = tree.getItem(new Point(evt.x, evt.y));
				if(evt.button == 3) {
					//创建一个弹出菜单
					Menu menu = new Menu(getShell(), SWT.POP_UP);
					tree.setMenu(menu);
					
					//如果有选中的树节点
					if(selectedItem != null) {
						final TreeNode treeNode = (TreeNode) selectedItem.getData();
						//并且当前节点是一个包
						if(treeNode.getType() == IViewConstant.TYPE_PACKAGE) {
							//--------------  创建一个"添加子包"菜单项  --------------------
							MenuItem addItem = new MenuItem(menu, SWT.PUSH);
							addItem.setText("添加子包");
							addItem.setData(null);
							addItem.addListener(SWT.Selection, new Listener() {

								public void handleEvent(Event event) {
									//添加子包
									addSubPackage(treeNode, selectedItem);
								}
							});
							
							//--------------  如果当前节点下的子节点没有业务节点，并且是一个包， 则可以修改  --------------------
							if(!hasBusinessClass(treeNode)) {
								MenuItem renameItem = new MenuItem(menu, SWT.PUSH);
								renameItem.setText("修改业务包");
								renameItem.setData(null);
								renameItem.addListener(SWT.Selection, new Listener() {

									public void handleEvent(Event event) {
										//修改业务包
										ModelPackage modelPackage = (ModelPackage) treeNode.getData();
										RenamePackageDialog dialog = new RenamePackageDialog(getShell(), modelPackage.getName(), modelPackage.getDisplayName(), modelPackage.getRemark());
										dialog.setCurrentNode(treeNode);
										if(dialog.open() == Dialog.OK) {
											
											treeNode.setName(dialog.getName());
											treeNode.setDisplayName(dialog.getDisplayName());
											
											modelPackage.setName(dialog.getName());
											modelPackage.setDisplayName(dialog.getDisplayName());
											modelPackage.setRemark(dialog.getRemarks());
											treeNode.setData(modelPackage);
											
											treeViewer.update(treeNode, null);
											
											// 添加子包时对 bom的处理
											ModelPackage thePackage = findPackage(ReverseContext.bom, treeNode);
											thePackage.setName(treeNode.getName());
											thePackage.setDisplayName(treeNode.getDisplayName());
											thePackage.setRemark(dialog.getRemarks());
										}
									}
								});
							}
							
							//--------------  如果当前包下无业务节点，则创建"删除"菜单项  --------------------
							boolean canDelete = true;
							if(hasBusinessClass(treeNode)) {
								canDelete = false;
							}
							
							//如果是只有一个包
							if(tree.getItems().length == 1&&selectedItem.getParentItem()==null) {
								canDelete = false;
							}
							if(canDelete) {
								MenuItem deleteItem = new MenuItem(menu, SWT.PUSH);
								deleteItem.setText("删除");
								deleteItem.setData(null);
								deleteItem.addListener(SWT.Selection, new Listener() {
									public void handleEvent(Event event) {
										//删除包
										deletePackage(treeNode);
										tableViewer.refresh();
									}
								});
							}
						}
					} else {
						//如果选中的树的面板，就添加"添加包"(根节点)的菜单项
						MenuItem insertItem = new MenuItem(menu, SWT.PUSH);
						insertItem.setText("添加包");
						insertItem.setData(null);
						insertItem.addListener(SWT.Selection, new Listener() {
							public void handleEvent(Event event) {
								InsertPackageDialog dialog = new InsertPackageDialog(getShell());
								//设置当前节点和树查看器是为了校验
								dialog.setTreeViewer(treeViewer);
								if(dialog.open() == Dialog.OK) {
									//创建一个包
									addPackage(dialog);
								}
							}
						});
					}
				}
				tree.redraw();
			}
			
		});
		
		//创建树的列
		TreeColumn _nameColumn = new TreeColumn(tree, SWT.NONE);
		_nameColumn.setText("名称");
		_nameColumn.setWidth(SUB_WIDTH);
		_nameColumn.setAlignment(SWT.CENTER);
		
		TreeColumn _displayColumn = new TreeColumn(tree, SWT.NONE);
		_displayColumn.setText("显示名");
		_displayColumn.setWidth(SUB_WIDTH);
		_displayColumn.setAlignment(SWT.CENTER);
		
		treeViewer.setContentProvider(new MultiTreeContentProvider());
		treeViewer.setLabelProvider(new PackageTreeLabelProvider());
		treeViewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				TreeSelection selection = (TreeSelection) event.getSelection();
				TreeNode currentNode = (TreeNode) selection.getFirstElement();
				
				//如果是包节点 就执行展开 收缩
				if(currentNode.getType() == IViewConstant.TYPE_PACKAGE) {
					if(!treeViewer.getExpandedState(currentNode)) {
						treeViewer.setExpandedState(currentNode, true);
					} else {
						treeViewer.setExpandedState(currentNode, false);
					}
				} else {
					List<TreeNode> treeNodes = new ArrayList<TreeNode>();
					treeNodes.add(currentNode);
					withdraw(treeNodes);
				}
				List<TreeModel> sList = (List<TreeModel>) treeViewer.getInput();
				boolean canFinish = false;
				if(sList != null && !sList.isEmpty() ) {
					for(TreeModel treeModel : sList){
						List<TreeNode> nodes = treeModel.getRoot().getChilds();
						if(nodes != null && !nodes.isEmpty()){
							canFinish = true;
						}
					}
				} 
				if(getWizard()  instanceof ReverseWizard){
					ReverseWizard wizard = (ReverseWizard) getWizard();
					wizard.setFinish(canFinish);
				}
				if(getWizard()  instanceof ThirdPartyWizard){
					ThirdPartyWizard wizard = (ThirdPartyWizard) getWizard();
					wizard.setFinish(canFinish);
				}
				setPageComplete(canFinish);
				
			}
		});
		
		setControl(container);
	}

	/**
	 * 重画.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	public void repaint() {
		rebackAll();
		if(getWizard()  instanceof ReverseWizard){
			ReverseWizard wizard = (ReverseWizard) getWizard();
			wizard.setFinish(false);
		}
		if(getWizard()  instanceof ThirdPartyWizard){
			ThirdPartyWizard wizard = (ThirdPartyWizard) getWizard();
			wizard.setFinish(false);
		}
		bizNodeMap = new HashMap<String, TreeNode>();
		
		BizTableModel input = new BizTableModel();

		BizBean bean = null;
		
		for(TreeNode treeNode: bizNodeList) {
			bean = new BizBean();
			bean.setId(treeNode.getId());
			bean.setName(treeNode.getName());
			com.mqfdy.code.reverse.mappings.Table table = (com.mqfdy.code.reverse.mappings.Table) treeNode.getBackup();
			bean.setDisplayName(table.getComment());
			input.add(bean);
			
			//放入Map中是为了以后方便查找
			bizNodeMap.put(treeNode.getId(), treeNode);
		}
		
		tableViewer.setInput(input);
		
		try {
			OmSelectWizardPage osPage = (OmSelectWizardPage) super.getWizard().getPage("osPage");
			
			List<TreeModel> modelList = new ArrayList<TreeModel>();
			//如果是新建om
			if(osPage.isNewOm()) {
				
				TreeNode packageNode = null;
				TreeModel packageModel = null;
				
				List<ModelPackage> packageList = ReverseContext.bom.getPackages();
				for (int i=0;i<packageList.size();i++){
					ModelPackage modelPackage=packageList.get(i);
					if(!(modelPackage.getParent() instanceof ModelPackage)){
						packageNode = new TreeNode();
						for(AbstractModelElement child: modelPackage.getChildren()){
							if(child instanceof ModelPackage){
								ModelPackage childPack=(ModelPackage)child;
								TreeNode packageChildNode = new TreeNode();
								packageChildNode.setId(childPack.getId());
								packageChildNode.setName(childPack.getName());
								packageChildNode.setDisplayName(childPack.getDisplayName());
								packageChildNode.setType(IViewConstant.TYPE_PACKAGE);
								packageChildNode.setData(childPack);
								TreeModel packageChildModel = new TreeModel(packageChildNode);
								packageChildNode.setModel(packageChildModel);
								
								packageNode.getChilds().add(packageChildNode);
								setChildPackage(childPack,packageChildNode);
							}
							
						}
						
						packageNode.setId(modelPackage.getId());
						packageNode.setName(modelPackage.getName());
						packageNode.setDisplayName(modelPackage.getDisplayName());
						packageNode.setType(IViewConstant.TYPE_PACKAGE);
						packageNode.setData(modelPackage);
						packageModel = new TreeModel(packageNode);
						packageNode.setModel(packageModel);
						modelList.add(packageModel);
					}
					
				}
			} else {
				//如果是已存在om，读取om中的包。
				List<TreeNode> packageList = readPackages(ReverseContext.OM_STORAGE_PATH);
				
				TreeModel packageModel = null;
				for(TreeNode packageNode: packageList) {
					packageModel = new TreeModel(packageNode);
					setPackageNodeModel(packageNode, packageModel);
					modelList.add(packageModel);
				}
			}
			treeViewer.setInput(modelList);
			treeViewer.expandAll();
			
			tree.select(tree.getItem(0));
			
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	
	/**
	 * 验证是否可以Finish.
	 *
	 * @author mqfdy
	 * @param selectedList
	 *            the selected list
	 * @Date 2018-09-03 09:00
	 */
	private void validateFinish(List<BizBean> selectedList) {
		if(selectedList != null && !selectedList.isEmpty()){					
			if(getWizard()  instanceof ReverseWizard){
				ReverseWizard wizard = (ReverseWizard) getWizard();
				wizard.setFinish(true);
			}
			if(getWizard()  instanceof ThirdPartyWizard){
				ThirdPartyWizard wizard = (ThirdPartyWizard) getWizard();
				wizard.setFinish(true);
			}
			setPageComplete(true);
		}
	}
	
	/**
	 * Sets the child package.
	 *
	 * @author mqfdy
	 * @param modelPackage
	 *            the model package
	 * @param packageChildNode2
	 *            the package child node 2
	 * @Date 2018-09-03 09:00
	 */
	private void setChildPackage(ModelPackage modelPackage, TreeNode packageChildNode2) {
		for(AbstractModelElement child: modelPackage.getChildren()){
			if(child instanceof ModelPackage){
				ModelPackage childPack=(ModelPackage)child;
				TreeNode packageChildNode = new TreeNode();
				packageChildNode.setId(childPack.getId());
				packageChildNode.setName(childPack.getName());
				packageChildNode.setDisplayName(childPack.getDisplayName());
				packageChildNode.setType(IViewConstant.TYPE_PACKAGE);
				packageChildNode.setData(childPack);
				TreeModel packageChildModel = new TreeModel(packageChildNode);
				packageChildNode.setModel(packageChildModel);
				packageChildNode2.getChilds().add(packageChildNode);
				setChildPackage(childPack,packageChildNode);		
			}
				
		}
	}

	/**
	 * 递归给所有的包节点设置模型.
	 *
	 * @author mqfdy
	 * @param packageNode
	 *            the package node
	 * @param packageModel
	 *            the package model
	 * @Date 2018-09-03 09:00
	 */
	public void setPackageNodeModel(TreeNode packageNode, TreeModel packageModel) {
		packageNode.setModel(packageModel);
		List<TreeNode> childList = packageNode.getChilds();
		for(TreeNode childNode: childList) {
			setPackageNodeModel(childNode, packageModel);
		}
	}
	
	/**
	 * 读取视图包.
	 *
	 * @author mqfdy
	 * @param filePath
	 *            the file path
	 * @return the list
	 * @Date 2018-09-03 09:00
	 */
	private List<TreeNode> readPackages(String filePath) {
		BufferedReader bufReader = null;
		Document doc = null;
		FileReader fr = null;
        try {
        	if(ReverseContext.bom!=null){
        		doc=ReverseContext.bom.generateXmlElement();
        	}else{
        		fr = new FileReader(filePath);
        		bufReader = new BufferedReader(fr);
                SAXReader reader = new SAXReader();
                doc = reader.read(bufReader);
        	}
       	 
             Element rootElement = doc.getRootElement();
             
             List<TreeNode> list = readPackages(rootElement);
             return list;
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		} finally {
			if(fr != null){
				try {
					fr.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(bufReader != null){
				try {
					bufReader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 读取视图包.
	 *
	 * @author mqfdy
	 * @param rootElement
	 *            the root element
	 * @return the list
	 * @throws Exception
	 *             the exception
	 * @Date 2018-09-03 09:00
	 */
	@SuppressWarnings("unchecked")
	private List<TreeNode> readPackages(Element rootElement) throws Exception {
		List<TreeNode> resultList = new ArrayList<TreeNode>();
		//获取所有Package节点根节点
        List<Element> packageElements = rootElement.elements("Package");
        
        TreeNode treeNode = null;
        ModelPackage modelPackage = null;
		for (Element element : packageElements) {

			// 创建一个树节点
			treeNode = new TreeNode();
			treeNode.setId(element.attributeValue("id"));
			treeNode.setName(element.attributeValue("name"));
			treeNode.setDisplayName(element.attributeValue("displayName"));
			treeNode.setType(IViewConstant.TYPE_PACKAGE);
			
			modelPackage = new ModelPackage();
			modelPackage.setId(element.attributeValue("id"));
			modelPackage.setName(element.attributeValue("name"));
			modelPackage.setDisplayName(element.attributeValue("displayName"));
			if(element.element("Remark") != null) {
				modelPackage.setRemark(element.element("Remark").getStringValue());
			}
			treeNode.setData(modelPackage);
			// 递归查找子包
			List<TreeNode> subPackageNodeList = readPackages(element);
			treeNode.setChilds(subPackageNodeList);
			for(TreeNode subPackageNode: subPackageNodeList) {
				subPackageNode.setParent(treeNode);
			}
			
			findBusinessClassByPackage(element, treeNode);
			resultList.add(treeNode);
		}
        return resultList;
	}

	/**
	 * 查找包下边的业务实体.
	 *
	 * @author mqfdy
	 * @param packageElement
	 *            the package element
	 * @param packageTreeNode
	 *            the package tree node
	 * @Date 2018-09-03 09:00
	 */
	@SuppressWarnings({"unchecked" })
	private void findBusinessClassByPackage(Element packageElement, TreeNode packageTreeNode) {
		List<BusinessClass> businessClasses = new ArrayList<BusinessClass>();
		Element bizElements = packageElement.element("BusinessClasses");
		if(bizElements != null) {
			
			List<Element> businessClassElements = bizElements.elements("BusinessClass");
			if (businessClassElements != null && businessClassElements.size() > 0) {
				BusinessClass businessClass = null;
				
				for (Element bizClassElement: businessClassElements) {
					businessClass = new BusinessClass();
					businessClass.setId(bizClassElement.attributeValue("id"));
					//设置对应的表名
					businessClasses.add(businessClass);
				}
			}
		}
		
		//业务实体作为备用数据是为了菜单显示的判断
		packageTreeNode.setBackup(businessClasses);
	}
	
	/**
	 * Find package.
	 *
	 * @author mqfdy
	 * @param bom
	 *            the bom
	 * @param currentPackageNode
	 *            the current package node
	 * @return the model package
	 * @Date 2018-09-03 09:00
	 */
	private ModelPackage findPackage(BusinessObjectModel bom, TreeNode currentPackageNode) {
		List<ModelPackage> modelPackageList = bom.getPackages();
		for(ModelPackage modelPackage: modelPackageList) {
			if(modelPackage.getId().equals(currentPackageNode.getId())) {
				return modelPackage;
			}
		}
		return null;
	}
	
	/**
	 * Find business class.
	 *
	 * @author mqfdy
	 * @param businessClasseList
	 *            the business classe list
	 * @param name
	 *            the name
	 * @return the business class
	 * @Date 2018-09-03 09:00
	 */
	private BusinessClass findBusinessClass(List<BusinessClass> businessClasseList, String name) {
		for(BusinessClass businessClass: businessClasseList) {
			if(name.equals(businessClass.getId())) {
				return businessClass;
			}
		}
		return null;
	}
	
	/**
	 * Checks for business class.
	 *
	 * @author mqfdy
	 * @param currentNode
	 *            the current node
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	@SuppressWarnings({"unchecked" })
	private boolean hasBusinessClass(TreeNode currentNode) {
		//所有子节点列表
		List<TreeNode> childList = ReverseUtil.getAllChildren(currentNode);
		//得到当前包下边的业务实体
		List<BusinessClass> businessClassList = (List<BusinessClass>) currentNode.getBackup();
		if(businessClassList == null || businessClassList.size() == 0) {
			for(TreeNode childNode: childList) {
				//得到子包下边的业务实体
				if(childNode.getBackup() != null) {
					List<BusinessClass> subBusinessClassList = (List<BusinessClass>) childNode.getBackup();
					if(subBusinessClassList != null && subBusinessClassList.size() != 0) {
						return true;
					}
				}
			}
		} else {
			return true;
		}
		return false;
	}
	
	/**
	 * Reback all.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	private void rebackAll(){
		List<TreeNode> allList = new ArrayList<TreeNode>();
		List<TreeModel> modelList = (List<TreeModel>) treeViewer.getInput();
		if(modelList==null){
			return;
		}
		for(TreeModel treeModel: modelList) {
			TreeNode node = treeModel.getRoot();
			allList.addAll(ReverseUtil.getAllChildren(node));
		}
		
		List<TreeNode> bcList = new ArrayList<TreeNode>();
		for(TreeNode node: allList) {
			if(node.getType() == IViewConstant.TYPE_BUSINESSCLASS) {
				bcList.add(node);
			}
		}
		withdraw(bcList);
		setPageComplete(false);
	}
	
	/**
	 * 撤销业务实体.
	 *
	 * @author mqfdy
	 * @param treeNodes
	 *            the tree nodes
	 * @Date 2018-09-03 09:00
	 */
	private void withdraw(List<TreeNode> treeNodes) {
		BizTableModel tableModel = (BizTableModel) tableViewer.getInput();
		
		BizBean bizBean = null;
		for(TreeNode treeNode: treeNodes) {
			bizBean = new BizBean();
			bizBean.setId(treeNode.getId());
			bizBean.setName(treeNode.getName());
			bizBean.setDisplayName(treeNode.getDisplayName());
			tableModel.add(bizBean);
			
			//删除右侧刚刚选择的节点
			treeNode.getModel().remove(treeNode);
			
			//找到当前要删除的BusinessClasse
			BusinessClass toBeDeletedClass = findBusinessClass(ReverseContext.bom.getBusinessClasses(), treeNode.getId().replace("-", ""));
			
			boolean isRemove = true;
			DuplicateNameWizardPage page = (DuplicateNameWizardPage) super.getWizard().getPage("dnPage");
			List<SpecialTable> list = page.getSpecialTables();
			for(SpecialTable specialTable: list) {
				if(specialTable.getName().toLowerCase(Locale.getDefault()).equals(toBeDeletedClass.getTableName().toLowerCase(Locale.getDefault()))) {
					isRemove = false;
					break;
				}
			}
			if(isRemove) {
				this.newBuList.remove(toBeDeletedClass);
			}
			
			//在bom中删除这个BusinessClasse
			ReverseContext.bom.removeBusinessClass(toBeDeletedClass);
			
		}
		
		tree.select(tree.getItem(0));
	}
	
	/**
	 * 将左侧选中的业务实体分配到右侧的包中.
	 *
	 * @author mqfdy
	 * @param selectedList
	 *            左侧选中的业务实体
	 * @param currentPackageNode
	 *            当前待分配的包
	 * @Date 2018-09-03 09:00
	 */
	private void dispatchToPackage(List<BizBean> selectedList, TreeNode currentPackageNode) {

		//如果右侧选择的不是一个包
		if(currentPackageNode.getType() != IViewConstant.TYPE_PACKAGE) {
			setMessage("请在右侧区域选择一个包，不能将实体分配到实体中。", IMessageProvider.ERROR);
			return ;
		} else {
			initMessage();
		}
		
		BizTableModel bizModel = (BizTableModel) tableViewer.getInput();
		//用选择的业务实体作为新的节点放在在当前节点下
		//同时删除选择的业务实体
		List<TreeNode> newNodeList = new ArrayList<TreeNode>();
		
		TreeNode newNode = null;
		for(BizBean bizBean: selectedList) {
			newNode = new TreeNode();
			newNode.setId(bizBean.getId());
			newNode.setName(bizBean.getName());
			newNode.setDisplayName(bizBean.getDisplayName());
			newNode.setType(IViewConstant.TYPE_BUSINESSCLASS);
			newNode.setModel(currentPackageNode.getModel());
			newNode.setData(bizBean);
			//添加到新的节点集合中
			newNodeList.add(newNode);
			//在业务实体模型中删除当前实体
			bizModel.remove(bizBean);
		}
		
		//添加到当前包中
		TreeModel treeModel = currentPackageNode.getModel();
		treeModel.add(currentPackageNode, newNodeList);
		//展开
		treeViewer.expandToLevel(currentPackageNode, IViewConstant.EXPAND_LEVEL_1);
		
		/**
		 * 操作树节点的同时,改变bom的结构,使两者同步
		 * 方便后续的操作
		 */
		//在bom中查找当前节点对应的包
		ModelPackage belongPackage = findPackage(ReverseContext.bom, currentPackageNode);
		
		BusinessClass businessClass = null;
		for(BizBean bizBean: selectedList) {
			//获取当前业务实体
			TreeNode bizNode = bizNodeMap.get(bizBean.getId());
			
			//创建一个业务实体
			businessClass = new BusinessClass();
			//设置对应的视图包
			businessClass.setBelongPackage(belongPackage);
			com.mqfdy.code.reverse.mappings.Table table = (com.mqfdy.code.reverse.mappings.Table) bizNode.getBackup();
			businessClass.setTableName(table.getName());
			businessClass.setName(bizNode.getName());
			businessClass.setId(bizNode.getId().replace("-", ""));
			businessClass.setDisplayName(table.getComment());
			businessClass.getExtendAttributies().put(IModelElement.KEY_SCHEMA, table.getSchemaName());
			
			//添加到bom中
			ReverseContext.bom.addBusinessClass(businessClass);
			
			boolean isAdd = true;
			DuplicateNameWizardPage page = (DuplicateNameWizardPage) super.getWizard().getPage("dnPage");
			List<SpecialTable> list = page.getSpecialTables();
			for(SpecialTable specialTable: list) {
				if(specialTable.getName().toLowerCase(Locale.getDefault()).equals(businessClass.getTableName().toLowerCase(Locale.getDefault()))) {
					isAdd = false;
					break;
				}
			}
			if(isAdd) {
				this.newBuList.add(businessClass);
			}
			
			//得到当前节点的属性节点
			List<TreeNode> propertyNodeList = bizNode.getChilds();
			
			PKProperty pkProperty = null;
			PersistenceProperty persisProperty = null;
			//遍历属性节点，转换成bom的Property
			for(TreeNode propertyNode: propertyNodeList) {
				
				Object object = propertyNode.getBackup();
				if(object instanceof Column) {
					Column column = (Column) object;
					if(propertyNode.getType() != IViewConstant.TYPE_RELATION) {
						if(propertyNode.getType() == IViewConstant.TYPE_PRIMARY_PROPERTY) {
							pkProperty = new PKProperty();
							pkProperty.setdBColumnName(column.getName());
							pkProperty.setName(propertyNode.getName());
							pkProperty.setParent(businessClass);
							pkProperty.setDisplayName(column.getComment());
							businessClass.addProperty(pkProperty);
						} else {
							persisProperty = new PersistenceProperty();
							persisProperty.setdBColumnName(column.getName());
							persisProperty.setName(propertyNode.getName());
							persisProperty.setParent(businessClass);
							persisProperty.setDisplayName(column.getComment());
							businessClass.addProperty(persisProperty);
						}
					}
				}
			}
		}
	}
	
	/**
	 * 添加子包.
	 *
	 * @author mqfdy
	 * @param treeNode
	 *            the tree node
	 * @param selectedItem
	 *            the selected item
	 * @Date 2018-09-03 09:00
	 */
	private void addSubPackage(TreeNode treeNode, TreeItem selectedItem) {
		InsertPackageDialog dialog = new InsertPackageDialog(getShell());
		//设置当前节点和树查看器是为了校验
		dialog.setCurrentNode(treeNode);
		dialog.setTreeViewer(treeViewer);
		if(dialog.open() == Dialog.OK) {
			//创建一个子包
			String id = UUID.randomUUID().toString().replace("-", "");
			
			TreeNode subPackageNode = new TreeNode();
			subPackageNode.setId(id);
			subPackageNode.setName(dialog.getName());
			subPackageNode.setDisplayName(dialog.getDisplayName());
			subPackageNode.setType(IViewConstant.TYPE_PACKAGE);
			TreeModel input = treeNode.getModel();
			//加到当前节点上
			input.add(treeNode, subPackageNode);
			subPackageNode.setModel(input);
			
			treeViewer.expandToLevel(treeNode, IViewConstant.EXPAND_LEVEL_1);

			TreeItem item = selectedItem.getItem(selectedItem.getItemCount() - 1);
			tree.setSelection(item);
			
			//添加子包时对 bom的处理
			ModelPackage newPackage = new ModelPackage();
			newPackage.setId(id);
			newPackage.setName(subPackageNode.getName());
			newPackage.setDisplayName(subPackageNode.getDisplayName());
			newPackage.setRemark(dialog.getRemarks());
			newPackage.setStereotype(IModelElement.STEREOTYPE_REVERSE);
			
			//设置父 包
			ModelPackage parentPackage = findPackage(ReverseContext.bom, treeNode);
			newPackage.setParent(parentPackage);
			ReverseContext.bom.addPackage(newPackage);
			
			subPackageNode.setData(newPackage);
			
			//设置包下的图,用于显示.
			Diagram diagram = new Diagram();
			diagram.setName(newPackage.getName());
			diagram.setDisplayName(newPackage.getDisplayName());
			diagram.setId(UUID.randomUUID().toString().replace("-", ""));
			diagram.setBelongPackage(newPackage);
			
			DiagramStyle diagramStyle = new DiagramStyle();
			diagram.setDefaultStyle(diagramStyle);
			
			ReverseContext.bom.addDiagram(diagram);
		}
	}
	
	/**
	 * 添加包.
	 *
	 * @author mqfdy
	 * @param dialog
	 *            the dialog
	 * @Date 2018-09-03 09:00
	 */
	@SuppressWarnings("unchecked")
	private void addPackage(InsertPackageDialog dialog) {
		String id = UUID.randomUUID().toString().replace("-", "");
		
		TreeNode newPackageNode = new TreeNode();
		newPackageNode.setId(id);
		newPackageNode.setName(dialog.getName());
		newPackageNode.setDisplayName(dialog.getDisplayName());
		newPackageNode.setType(IViewConstant.TYPE_PACKAGE);
		
		TreeModel input = new TreeModel(newPackageNode);
		newPackageNode.setModel(input);
		List<TreeModel> modelList = (List<TreeModel>) treeViewer.getInput();
		modelList.add(input);
		treeViewer.setInput(modelList);
		treeViewer.expandAll();

		TreeItem item = tree.getItem(tree.getItemCount() - 1);
		tree.setSelection(item);

		// 添加子包时对 bom的处理
		ModelPackage newPackage = new ModelPackage();
		newPackage.setId(id);
		newPackage.setName(newPackageNode.getName());
		newPackage.setDisplayName(newPackageNode.getDisplayName());
		newPackage.setRemark(dialog.getRemarks());
		newPackage.setStereotype(IModelElement.STEREOTYPE_REVERSE);
		newPackage.setParent(ReverseContext.bom);
		
		//设置包下的图,用于显示.
		Diagram diagram = new Diagram();
		diagram.setName(newPackage.getName());
		diagram.setDisplayName(newPackage.getDisplayName());
		diagram.setId(UUID.randomUUID().toString().replace("-", ""));
		diagram.setBelongPackage(newPackage);
		
		DiagramStyle diagramStyle = new DiagramStyle();
		diagram.setDefaultStyle(diagramStyle);
		
		ReverseContext.bom.addDiagram(diagram);
		
		newPackageNode.setData(newPackage);
		ReverseContext.bom.addPackage(newPackage);
	}
	
	/**
	 * 删除包.
	 *
	 * @author mqfdy
	 * @param treeNode
	 *            the tree node
	 * @Date 2018-09-03 09:00
	 */
	@SuppressWarnings("unchecked")
	private void deletePackage(TreeNode treeNode) {
		
		List<TreeNode> childNodeList = ReverseUtil.getAllChildren(treeNode);
		
		TreeModel input = treeNode.getModel();
		if(treeNode.getData() instanceof ModelPackage){
			if(treeNode.getParent() != null) {
				input.remove(treeNode);
			} else {
				input.remove(treeNode);
				List<TreeModel> modelList = (List<TreeModel>) treeViewer.getInput();
				modelList.remove(input);
			}
		}
		if(treeNode.getData() instanceof BizBean){
			List treeNodes = new ArrayList();
			treeNodes.add(treeNode);
			this.withdraw(treeNodes);
		}
		//删除bom中对应的package
		ModelPackage thePackage = findPackage(ReverseContext.bom, treeNode);
		ReverseContext.bom.getPackages().remove(thePackage);
		
		for(TreeNode childNode: childNodeList) {
			if(childNode.getData() instanceof ModelPackage){
				ModelPackage childPackage = findPackage(ReverseContext.bom, childNode);
				ReverseContext.bom.getPackages().remove(childPackage);
			}
			if(childNode.getData() instanceof BizBean){
				List treeNodes = new ArrayList();
				treeNodes.add(childNode);				
				this.withdraw(treeNodes);
				setPageComplete(false);
			}
		}
	
	}
	
	/**
	 * 返回未被选择转换成om对象的表个数.
	 *
	 * @author mqfdy
	 * @return the unselected to om tables count
	 * @Date 2018-09-03 09:00
	 */
	public int getUnselectedToOmTablesCount(){
		BizTableModel model = (BizTableModel) tableViewer.getInput();
		if(model == null){
			return 0;
		}
		Object[] objects = model.elements();
		if(objects == null || objects.length == 0) {
			return 0;
		}
		return objects.length;
	}
	
	/**
	 * Gets the biz node list.
	 *
	 * @author mqfdy
	 * @return the biz node list
	 * @Date 2018-09-03 09:00
	 */
	public List<TreeNode> getBizNodeList() {
		return bizNodeList;
	}

	/**
	 * Sets the biz node list.
	 *
	 * @author mqfdy
	 * @param bizNodeList
	 *            the new biz node list
	 * @Date 2018-09-03 09:00
	 */
	public void setBizNodeList(List<TreeNode> bizNodeList) {
		this.bizNodeList = bizNodeList;
	}

	/**
	 * Gets the tree viewer.
	 *
	 * @author mqfdy
	 * @return the tree viewer
	 * @Date 2018-09-03 09:00
	 */
	public TreeViewer getTreeViewer() {
		return treeViewer;
	}

	/**
	 * Gets the biz node map.
	 *
	 * @author mqfdy
	 * @return the biz node map
	 * @Date 2018-09-03 09:00
	 */
	public Map<String, TreeNode> getBizNodeMap() {
		return bizNodeMap;
	}

	/**
	 * Gets the new bu list.
	 *
	 * @author mqfdy
	 * @return the new bu list
	 * @Date 2018-09-03 09:00
	 */
	public List<BusinessClass> getNewBuList() {
		return newBuList;
	}

}