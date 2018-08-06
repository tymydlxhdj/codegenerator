package com.mqfdy.code.designer.views.modelresource.tree;

import java.io.IOException;
import java.util.List;

import org.dom4j.DocumentException;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
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
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.views.contentoutline.ContentOutline;
import org.eclipse.ui.views.navigator.LocalSelectionTransfer;

import com.mqfdy.code.designer.editor.listeners.TreeResourceChangeListener;
import com.mqfdy.code.designer.editor.utils.EditorOperation;
import com.mqfdy.code.designer.editor.utils.Logger;
import com.mqfdy.code.designer.models.BusinessModelEvent;
import com.mqfdy.code.designer.models.BusinessModelListenerAdapter;
import com.mqfdy.code.designer.models.BusinessModelManager;
import com.mqfdy.code.designer.utils.BusinessModelUtil;
import com.mqfdy.code.designer.views.modelresource.actions.DeleteReferenceModelAction;
import com.mqfdy.code.designer.views.modelresource.actions.DoubleClickTreeAction;
import com.mqfdy.code.designer.views.modelresource.actions.ImportRepositoryAction;
import com.mqfdy.code.designer.views.modelresource.actions.RefreshReferenceModelAction;
import com.mqfdy.code.designer.views.modelresource.page.ObjectModelOutlinePage;
import com.mqfdy.code.model.AbstractModelElement;
import com.mqfdy.code.model.BusinessObjectModel;
import com.mqfdy.code.model.IModelElement;
import com.mqfdy.code.model.ReferenceObject;
import com.mqfdy.code.model.utils.ModelUtil;
import com.mqfdy.code.resource.BomManager;


/**
 * 引用对象浏览视图
 * 
 * @author mqfdy
 * 
 */
public class RepositoryModelView extends Composite {

	private BusinessModelManager businessModelManager;

	private TreeViewer viewer;

	private ObjectModelOutlinePage omPage;

	private FilteredTree filterTree;
	/**
	 * 导入引用模型动作
	 */
	private Action importRepositoryAction;

	private MenuManager menuManager;
	
	private DoubleClickTreeAction doubleClickTreeAction;
	private final BusinessModelListenerAdapter bmListener = new BusinessModelListenerAdapter() {

		public void repositoryModelAdd(AbstractModelElement element) {
			viewer.setInput(businessModelManager.getRepositoryModels());
			viewer.refresh();
		}
	};

	private Tree tree;

	private Menu treeMenu;

	public RepositoryModelView(Composite parent, int style,
			ObjectModelOutlinePage omPage,
			BusinessModelManager businessModelManager) {
		super(parent, style);
		this.omPage = omPage;
		this.businessModelManager = businessModelManager;
		setLayout(new FillLayout());
		init();
	}

	private void init() {
		filterTree = new FilteredTree(this, SWT.MULTI
				| SWT.V_SCROLL | SWT.H_SCROLL /* | SWT.SINGLE */, true);
		viewer = filterTree.getViewer();
		// viewer = new TreeViewer(this, SWT.MULTI | SWT.H_SCROLL |
		// SWT.V_SCROLL);
		viewer.setLabelProvider(new ModelResourceLabelProvider());
		viewer.setContentProvider(new ModelResourceContentProvider());
		viewer.setSorter(new ModelResourceTreeSorter());
		initActions();

		businessModelManager.addBusinessModelListener(bmListener);
	}

	/**
	 * 初始化数据
	 */
	public void initTreeViewerData() {
		List<BusinessObjectModel> repositorys = businessModelManager
				.getRepositoryModels();
		BusinessObjectModel inBom = BusinessModelManager.getBuildInOm();
		if (inBom != null) {
			if (!isExists(repositorys, inBom)) {
				repositorys.add(inBom);
			}
		}
		List<ReferenceObject> refList = businessModelManager.getBusinessObjectModel().getReferenceObjects();
		for(ReferenceObject ref : refList){
			BusinessObjectModel bom;
			try {
				boolean isAddRef = true;//是否添加到引用模型
				bom = BomManager.getOnlyModel(EditorOperation.getProjectPath(ref.getReferenceModePath()));
				bom.getExtendAttributies().put(IModelElement.REFMODELPATH, ref.getReferenceModePath());
				for(BusinessObjectModel b : businessModelManager.getRepositoryModels()){
					if(b.getId().equals(bom.getId()))
						isAddRef = false;
				}
				if(isAddRef){
					businessModelManager.addRepsitoryModel(bom);
					BusinessModelEvent event = new BusinessModelEvent(BusinessModelEvent.REPOSITORY_MODEL_ADD, bom);
					businessModelManager.businessObjectModelChanged(event);
					omPage.getBusinessModelDiagramEditor().addListener(
							new TreeResourceChangeListener(ref.getReferenceModePath(), omPage));
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				Logger.log(e);
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				Logger.log(e);
			}
		}
		
		viewer.setInput(repositorys);
		tree = viewer.getTree();
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
			}
		});
		initDragAndDrop();
	}

	/**
	 * 初始化数据
	 */
	public void initTreeViewerData(String path) {
		String fullPath = EditorOperation.getProjectPath(path);
//			ResourcesPlugin.getWorkspace().getRoot()
//				.getLocation().toOSString()
//				+ "\\" + path;
		BusinessObjectModel bom = null;
		try {
			bom = BomManager.xml2Model(fullPath);
		} catch (DocumentException e) {
			Logger.log(e);
		}
		if (bom == null) {
			return;
		}
		BusinessModelUtil.assembReferenceObject(bom,fullPath);
		ModelUtil.transformModelStereotype(bom,
				IModelElement.STEREOTYPE_REFERENCE);
		bom.getExtendAttributies().put(IModelElement.REFMODELPATH, path);
		businessModelManager.updateRepsitoryModel(bom);// 更新修改过的model

		List<BusinessObjectModel> repositorys = businessModelManager
				.getRepositoryModels();
		BusinessObjectModel inBom = BusinessModelManager.getBuildInOm();
		if (inBom != null) {
			if (!isExists(repositorys, inBom)) {
				repositorys.add(inBom);
			}
		}
		try {
			viewer.setInput(repositorys);
		} catch (Exception e) {
			Logger.log(e);
		}
		try {
			viewer.refresh();
		} catch (Exception e) {
			Logger.log(e);
		}
	}

//	private void replaceBom(List<BusinessObjectModel> repositorys,
//			BusinessObjectModel bom) {
//		if (repositorys == null || bom == null) {
//			return;
//		} else {
//			for (BusinessObjectModel model : repositorys) {
//				if (model != null && model.getId().equals(bom.getId())) {
//					repositorys.remove(model);
//					businessModelManager.getRepositoryModels().remove(model);
//					businessModelManager.addRepsitoryModel(bom);
//				}
//			}
//		}
//	}

	private boolean isExists(List<BusinessObjectModel> repositorys,
			BusinessObjectModel inBom) {
		if (repositorys == null || repositorys.size() < 1) {
			return false;
		} else {
			if (inBom == null) {
				return true;
			} else {
				for (BusinessObjectModel model : repositorys) {
					if (model != null && model.getId().equals(inBom.getId())) {
						return true;
					}
				}
				return false;
			}
		}
	}

	/**
	 * 初始化动作事件
	 */
	private void initActions() {
		// drillDownAdapter = new DrillDownAdapter(rmViewer.getTreeViewer());
		doubleClickTreeAction = new DoubleClickTreeAction(viewer);
		/**
		 * 增加树节点双击事件
		 */
		viewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				doubleClickTreeAction.run();
			}
		});
		importRepositoryAction = new ImportRepositoryAction("导入模型", this);
		// hookContextMenu();
		// hookDoubleClickAction();
		contributeToActionBars();
	}

	public FilteredTree getFilterTree() {
		return filterTree;
	}

	private void contributeToActionBars() {
		IActionBars bars = omPage.getSite().getActionBars();
		fillLocalToolBar(bars.getToolBarManager());
	}

	private void fillLocalToolBar(IToolBarManager manager) {
		manager.add(importRepositoryAction);
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
				TreeItem item1 = viewer.getTree().getSelection()[i];
				AbstractModelElement modelElement1 = (AbstractModelElement) item1
						.getData();
				if (!(modelElement1 instanceof BusinessObjectModel)) {
					flag = false;
				}else if (!IModelElement.STEREOTYPE_REFERENCE.equals(modelElement1
						.getStereotype())) {
					flag = false;
				}

			}
			if (flag) {
				menuManager.add(new DeleteReferenceModelAction(viewer));
				menuManager.add(new RefreshReferenceModelAction(omPage));
//				menuManager.add(new RefreshReferenceModelAction(viewer));
			}
		}

		treeMenu = menuManager.createContextMenu(tree);
		tree.setMenu(treeMenu);
	}
	
	public TreeViewer getTreeViewer() {
		return viewer;
	}

	public void initDragAndDrop() {
		Transfer[] transfer = new Transfer[] {
				LocalSelectionTransfer.getInstance(),
				TextTransfer.getInstance() };
		LocalSelectionDragAdapter adapter = new LocalSelectionDragAdapter(
				viewer);
		viewer.addDragSupport(DND.DROP_MOVE | DND.DROP_COPY, transfer, adapter);
	}
}
