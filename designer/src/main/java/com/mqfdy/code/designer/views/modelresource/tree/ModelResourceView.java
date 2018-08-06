package com.mqfdy.code.designer.views.modelresource.tree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.views.contentoutline.ContentOutline;
import org.eclipse.ui.views.navigator.LocalSelectionTransfer;

import com.mqfdy.code.designer.editor.actions.GenerateAction;
import com.mqfdy.code.designer.editor.actions.ModelValidateAction;
import com.mqfdy.code.designer.editor.part.NodeEditPart;
import com.mqfdy.code.designer.editor.part.OmConnectionEditPart;
import com.mqfdy.code.designer.editor.utils.Logger;
import com.mqfdy.code.designer.models.ActionTexts;
import com.mqfdy.code.designer.models.BusinessModelListenerAdapter;
import com.mqfdy.code.designer.models.BusinessModelManager;
import com.mqfdy.code.designer.utils.BusinessModelUtil;
import com.mqfdy.code.designer.views.modelresource.actions.AddAssocitionAction;
import com.mqfdy.code.designer.views.modelresource.actions.AddBusinessClassAction;
import com.mqfdy.code.designer.views.modelresource.actions.AddDiagramAction;
import com.mqfdy.code.designer.views.modelresource.actions.AddEnumerationAction;
import com.mqfdy.code.designer.views.modelresource.actions.AddPackageAction;
import com.mqfdy.code.designer.views.modelresource.actions.CopyModelFromTreeAction;
import com.mqfdy.code.designer.views.modelresource.actions.DeleteModelElementAction;
import com.mqfdy.code.designer.views.modelresource.actions.DoubleClickTreeAction;
import com.mqfdy.code.designer.views.modelresource.actions.PasteModelFromTreeAction;
import com.mqfdy.code.designer.views.modelresource.actions.RefreshAction;
import com.mqfdy.code.model.AbstractModelElement;
import com.mqfdy.code.model.Association;
import com.mqfdy.code.model.BusinessClass;
import com.mqfdy.code.model.BusinessObjectModel;
import com.mqfdy.code.model.BusinessOperation;
import com.mqfdy.code.model.EnumElement;
import com.mqfdy.code.model.Enumeration;
import com.mqfdy.code.model.IModelElement;
import com.mqfdy.code.model.ModelPackage;
import com.mqfdy.code.model.Property;
import com.mqfdy.code.model.SolidifyPackage;
import com.mqfdy.code.model.graph.DiagramElement;

/**
 * BusinessObjectModel 对象浏览操作视图
 * 
 * @author mqfdy
 * 
 */
public class ModelResourceView extends Composite {

	private BusinessModelManager businessModelManager;

	private TreeViewer treeViewer;

	private Tree tree;
	private FilteredTree filterTree;
	
	private Menu treeMenu;
	private MenuManager menuManager;
	/**
	 * 菜单动作
	 */
//	private CopyModelFromTreeAction copyProAction;
//	private PasteModelFromTreeAction pasteModelFromTreeAction;
	private GenerateAction generateAction;
	private ModelValidateAction validateAction;
	private AddPackageAction addPackageAction;
	private AddDiagramAction addDiagramAction;
	private AddBusinessClassAction addBusinessClassAction;
	private AddEnumerationAction addEnumerationAction;
	private RefreshAction refreshAction;
	// private AddDTOAction addDTOAction;
	// private AddComplexDataTypeAction addComplexDataTypeAction;

	private AddAssocitionAction addOne2OneAction;
	private AddAssocitionAction addOne2MultAction;
	private AddAssocitionAction addMult2OneAction;
	private AddAssocitionAction addMult2MultAction;

	// private AddInheritanceAction addInheritanceAction;

	private DeleteModelElementAction deletedModelElementAction;;

	private DoubleClickTreeAction doubleClickTreeAction;

	private final BusinessModelListenerAdapter bmListener = new BusinessModelListenerAdapter() {

		public void modelElementAdd(AbstractModelElement element) {
			if (element != null) {
				treeViewer.add(element.getParent(), element);
			}

		}

		public void modelElementUpdate(AbstractModelElement element) {
			if (element != null) {
				try {
					treeViewer.refresh(element);
				} catch (Exception e) {
					Logger.log(e);
				}
			}

		}

		public void modelElementDelete(AbstractModelElement element) {
			if (element != null) {
				try {
					treeViewer.remove(element);
				} catch (Exception e) {
					Logger.log(e);
				}
			}

		}
	};

	// private Text filterText;

	public ModelResourceView(Composite parent, int style) {
		super(parent, style);
		setLayout(new FillLayout());
		initTreeViewer();
		createActions();
	}

	public ModelResourceView(Composite parent, int style,
			BusinessModelManager businessModelManager) {
		super(parent, style);
		setLayout(new FillLayout());
		this.businessModelManager = businessModelManager;
		initTreeViewer();
		createActions();
		businessModelManager.addBusinessModelListener(bmListener);
	}

	// public void createFilterText() {
	// GridLayout layout = new GridLayout();
	// layout.numColumns = 1;
	// this.setLayout(layout);
	// filterText = new Text(this, SWT.BORDER);
	// GridData textgridData = new GridData();
	// textgridData.horizontalAlignment = GridData.FILL;
	// textgridData.grabExcessHorizontalSpace = true;
	// textgridData.verticalAlignment = GridData.CENTER;
	// filterText.setLayoutData(textgridData);
	// }
	private void initTreeViewer() {
		// createFilterText();
		filterTree = new FilteredTree(this, SWT.MULTI
				| SWT.V_SCROLL | SWT.H_SCROLL/* | SWT.SINGLE */, true);
		treeViewer = filterTree.getViewer();
		// treeViewer = new TreeViewer(this,SWT.NONE|SWT.V_SCROLL);
		treeViewer.setLabelProvider(new ModelResourceLabelProvider());
		treeViewer.setContentProvider(new ModelResourceContentProvider());
		treeViewer.setSorter(new ModelResourceTreeSorter());
		tree = treeViewer.getTree();

		// GridData textgridData = new GridData();
		// textgridData.horizontalAlignment = GridData.FILL;
		// textgridData.grabExcessHorizontalSpace = true;
		// textgridData.grabExcessVerticalSpace = true;
		// textgridData.verticalAlignment = GridData.FILL;
		// tree.setLayoutData(textgridData);
		tree.addMouseTrackListener(new MouseTrackListener(){

			@Override
			public void mouseEnter(MouseEvent e) {
				IViewPart[] views = PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getActivePage().getViews();
				for (int i = 0; i < views.length; i++) {
					if (views[i] instanceof ContentOutline) {
						views[i].setFocus();
					}
				}
			}

			@Override
			public void mouseExit(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseHover(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		tree.addMouseListener(new MouseAdapter() {
			public void mouseDown(MouseEvent e) {
				resetMenu();// 出始化菜单
				// 选中图上节点
				Iterator<?> it = BusinessModelUtil
						.getBusinessModelDiagramEditor().getAllEditParts();
				TreeItem[] items = tree.getSelection();
				if (items != null && items.length > 0) {
					TreeItem item = items[0];
					AbstractModelElement element = (AbstractModelElement) item
							.getData();

					while (it.hasNext()) {
						Object e1 = ((Entry<?, ?>) it.next()).getValue();
						if (e1 instanceof NodeEditPart) {
							if (((NodeEditPart) e1).getModel() instanceof DiagramElement) {
								if (((DiagramElement) (((NodeEditPart) e1)
										.getModel())).getObjectId().equals(
										element.getId())) {
									BusinessModelUtil
											.getBusinessModelDiagramEditor()
											.getViewer()
											.select((NodeEditPart) e1);
								}
							}
						} else if (e1 instanceof OmConnectionEditPart) {
							if (((OmConnectionEditPart) e1).getModel() instanceof Association) {
								if (((AbstractModelElement) ((OmConnectionEditPart) e1)
										.getModel()).getId().equals(
										element.getId())) {
									BusinessModelUtil
											.getBusinessModelDiagramEditor()
											.getViewer()
											.select((OmConnectionEditPart) e1);
								}
							}
						}
					}
				}
			}
		});

		initDragAndDrop();
		/**
		 * 增加树节点双击事件
		 */
		treeViewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				doubleClickTreeAction.run();
			}
		});

	}

	public void initTreeViewerData() {
		businessModelManager.updateReferenceObjects();
		List<BusinessObjectModel> boms = new ArrayList<BusinessObjectModel>();
		BusinessObjectModel bom = businessModelManager.getBusinessObjectModel();
		boms.add(bom);
		treeViewer.setInput(boms);
		treeViewer.expandToLevel(3);
		treeViewer.refresh();
	}

	/**
	 * 初始化菜单
	 */
	public void resetMenu() {
		if (menuManager == null) {
			menuManager = new MenuManager();
		}
		menuManager.removeAll();
		TreeItem[] items = tree.getSelection();
		if (items != null && items.length > 0) {
			boolean flag = true;//控制操作在选中的节点上是否可见
			for (int i = 0; i < items.length; i++) {
				TreeItem item1 = treeViewer.getTree().getSelection()[i];
				AbstractModelElement modelElement1 = (AbstractModelElement) item1
						.getData();
				if (!(modelElement1 instanceof Property)) {
					flag = false;
				}

			}
			if (!flag) {
				flag = true;
				for (int i = 0; i < items.length; i++) {
					TreeItem item1 = treeViewer.getTree().getSelection()[i];
					AbstractModelElement modelElement1 = (AbstractModelElement) item1
							.getData();
					if (!(modelElement1 instanceof BusinessClass || modelElement1 instanceof Enumeration)){//ReferenceObject)) {
						flag = false;
					}

				}
			}
			if (!flag) {
				flag = true;
				for (int i = 0; i < items.length; i++) {
					TreeItem item1 = treeViewer.getTree().getSelection()[i];
					AbstractModelElement modelElement1 = (AbstractModelElement) item1
							.getData();
					if (!(modelElement1 instanceof BusinessOperation)) {
						flag = false;
					} else if (BusinessOperation.OPERATION_TYPE_STANDARD
							.equalsIgnoreCase(((BusinessOperation) modelElement1)
									.getOperationType())) {
						flag = false;
					}

				}
			}
			if (flag) {
				menuManager.add(new CopyModelFromTreeAction(treeViewer));
			}
			TreeItem item = items[0];
			AbstractModelElement modelElement = (AbstractModelElement) item
					.getData();
			if (!IModelElement.STEREOTYPE_BUILDIN.equals(modelElement
					.getStereotype())) {
				if (modelElement instanceof BusinessObjectModel) {
					menuManager.add(addPackageAction);
					menuManager.add(validateAction);
					menuManager.add(generateAction);
					// menuManager.add(refreshAction);
				} else if (modelElement instanceof ModelPackage) {
					menuManager.add(addPackageAction);
					menuManager.add(addDiagramAction);

					menuManager.add(new Separator());

					menuManager.add(addBusinessClassAction);
					menuManager.add(addEnumerationAction);
					// menuManager.add(addDTOAction);

					menuManager.add(new Separator());

					menuManager.add(addOne2OneAction);
					menuManager.add(addOne2MultAction);
					menuManager.add(addMult2OneAction);
					menuManager.add(addMult2MultAction);
					// menuManager.add(refreshAction);

					menuManager.add(new Separator());
					if (items.length == 1) {
						menuManager.add(new PasteModelFromTreeAction(
								treeViewer, businessModelManager));
					}
					// menuManager.add(addInheritanceAction);

				} else if (modelElement instanceof SolidifyPackage
						&& SolidifyPackage.SOLIDIFY_PACKAGE_BUSINESSCLASS
								.equals(modelElement.getName())) {
					menuManager.add(addBusinessClassAction);
					menuManager.add(addEnumerationAction);
					// menuManager.add(refreshAction);
					// menuManager.add(addDTOAction);
				} else if (modelElement instanceof SolidifyPackage
						&& SolidifyPackage.SOLIDIFY_PACKAGE_ASSOCIATION
								.equals(modelElement.getName())) {
					menuManager.add(addOne2OneAction);
					menuManager.add(addOne2MultAction);
					menuManager.add(addMult2OneAction);
					menuManager.add(addMult2MultAction);
					// menuManager.add(refreshAction);
				}
				// else if(modelElement instanceof SolidifyPackage &&
				// SolidifyPackage.SOLIDIFY_PACKAGE_INHERITANCE.equals(modelElement.getName()))
				// {
				// menuManager.add(addInheritanceAction);
				// }
				if (modelElement instanceof BusinessClass
						/*&& BusinessModelUtil.isCustomObjectModel(modelElement)*/) {
					if (items.length == 1) {
						menuManager.add(new PasteModelFromTreeAction(
								treeViewer, businessModelManager));
					}
				}
				if (!(modelElement instanceof SolidifyPackage || modelElement instanceof BusinessObjectModel || modelElement instanceof EnumElement)) {
					// menuManager.add(new Separator());
					menuManager.add(deletedModelElementAction);
			//		menuManager.add(refreshAction);
				}
			}
		}

		treeMenu = menuManager.createContextMenu(tree);
		tree.setMenu(treeMenu);
	}

	private void createActions() {
		generateAction = new GenerateAction(businessModelManager);
//		copyProAction = new CopyModelFromTreeAction(treeViewer);
//		pasteModelFromTreeAction = new PasteModelFromTreeAction(treeViewer,
//				businessModelManager);
		validateAction = new ModelValidateAction(businessModelManager);
		addPackageAction = new AddPackageAction(treeViewer);
		refreshAction = new RefreshAction(treeViewer);

		addDiagramAction = new AddDiagramAction(treeViewer);

		addBusinessClassAction = new AddBusinessClassAction(treeViewer);
		addEnumerationAction = new AddEnumerationAction(treeViewer);
		// addDTOAction = new AddDTOAction(treeViewer);
		// addComplexDataTypeAction = new AddComplexDataTypeAction(treeViewer);

		addOne2OneAction = new AddAssocitionAction(
				ActionTexts.ASSOCIATION_ONE2ONE_ADD, treeViewer);
		addOne2MultAction = new AddAssocitionAction(
				ActionTexts.ASSOCIATION_ONE2MULT_ADD, treeViewer);
		addMult2OneAction = new AddAssocitionAction(
				ActionTexts.ASSOCIATION_MULT2ONE_ADD, treeViewer);
		addMult2MultAction = new AddAssocitionAction(
				ActionTexts.ASSOCIATION_MULT2MULT_ADD, treeViewer);

		// addInheritanceAction = new AddInheritanceAction(treeViewer);

		deletedModelElementAction = new DeleteModelElementAction(treeViewer);

		doubleClickTreeAction = new DoubleClickTreeAction(treeViewer);
	}

	public TreeViewer getTreeViewer() {
		return this.treeViewer;
	}
	/**
	 * 添加拖动事件
	 */
	public void initDragAndDrop() {
		Transfer[] transfer = new Transfer[] {
				LocalSelectionTransfer.getInstance(),
				TextTransfer.getInstance() };
		LocalSelectionDragAdapter adapter = new LocalSelectionDragAdapter(
				treeViewer);
		//拖动支持
		treeViewer.addDragSupport(DND.DROP_MOVE | DND.DROP_COPY, transfer,
				adapter);
	}

	public BusinessModelManager getBusinessModelManager() {
		return businessModelManager;
	}
	
	public FilteredTree getFilterTree() {
		return filterTree;
	}
}
