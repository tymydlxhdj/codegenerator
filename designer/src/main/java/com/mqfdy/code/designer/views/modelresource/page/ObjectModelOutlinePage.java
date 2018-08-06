package com.mqfdy.code.designer.views.modelresource.page;

import org.eclipse.core.runtime.ListenerList;
import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.util.SafeRunnable;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IPropertyListener;
import org.eclipse.ui.part.IPageSite;
import org.eclipse.ui.part.Page;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;

import com.mqfdy.code.designer.editor.BusinessModelDiagramEditor;
import com.mqfdy.code.designer.models.BusinessModelManager;
import com.mqfdy.code.designer.models.ImageKeys;
import com.mqfdy.code.designer.models.ImageManager;
import com.mqfdy.code.designer.views.modelresource.actions.ModelFilterAction;
import com.mqfdy.code.designer.views.modelresource.tree.ModelResourceView;
import com.mqfdy.code.designer.views.modelresource.tree.RepositoryModelView;

/**
 * @author mqfdy
 * 
 */
public class ObjectModelOutlinePage extends Page implements
		IContentOutlinePage, ISelectionChangedListener, IPropertyListener {

	private ListenerList selectionChangedListeners = new ListenerList();

	private BusinessModelManager businessModelManager;

	private BusinessModelDiagramEditor businessModelDiagramEditor;
	/**
	 * 导航标签页
	 */
	private TabFolder tabFolder;

	/**
	 * 模型资源视图
	 */
	private ModelResourceView mrViewer;

	/**
	 * 引用模型视图
	 */
	private RepositoryModelView rmViewer;

	/**
	 * 树收缩动作
	 */
	private Action collapseTreeAction;
	/**
	 * 过滤条件动作
	 */
	private Action modelFilterAction;

	/**
	 * 树收缩动作
	 */
	private Action expandTreeAction;

	protected Object currentViewer;

	public ObjectModelOutlinePage(BusinessModelManager businessModelManager) {
		this.businessModelManager = businessModelManager;
	}

	public ObjectModelOutlinePage(
			BusinessModelDiagramEditor businessModelDiagramEditor) {
		this.businessModelDiagramEditor = businessModelDiagramEditor;
		this.businessModelManager = businessModelDiagramEditor.getBusinessModelManager();
	}

	public void createControl(Composite parent) {
		createTabFolder(parent);
		createModelResourceView();
		//createRepositoryModelView();
		currentViewer = mrViewer;
		initActions();
	}

	public void init(IPageSite pageSite) {
		super.init(pageSite);

	}

	/**
	 * 初始化标签页
	 */
	private void createTabFolder(Composite parent) {
		tabFolder = new TabFolder(parent, SWT.NONE | SWT.BOTTOM);
		TabItem tabItem_local = new TabItem(tabFolder, SWT.NONE);
		tabItem_local.setImage(ImageManager.getInstance().getImage(
				ImageKeys.IMG_MODEL_TYPE_LOCAL));
		tabFolder.addSelectionListener(new SelectionListener() {

			public void widgetSelected(SelectionEvent e) {
				int selectionIndex = tabFolder.getSelectionIndex();
				if (selectionIndex == 0) {
					currentViewer = mrViewer;
					getSite().setSelectionProvider(mrViewer.getTreeViewer());
				} else {
					currentViewer = rmViewer;
					getSite().setSelectionProvider(rmViewer.getTreeViewer());
				}
			}

			public void widgetDefaultSelected(SelectionEvent e) {

			}
		});
		tabItem_local.setText("模型资源");
		/*TabItem tabItem1 = new TabItem(tabFolder, SWT.NONE);
		tabItem1.setImage(ImageManager.getInstance().getImage(
				ImageKeys.IMG_MODEL_TYPE_REPOSITORY));
		tabItem1.setText("引用模型");*/
	}

	/**
	 * 创建模型资源视图
	 */
	private void createModelResourceView() {
		mrViewer = new ModelResourceView(tabFolder, SWT.NONE,
				businessModelManager);
		mrViewer.initTreeViewerData();
		tabFolder.getItem(0).setControl(mrViewer);
		// getSite().setSelectionProvider(mrViewer.getTreeViewer());

	}

	private void createRepositoryModelView() {
		rmViewer = new RepositoryModelView(tabFolder, SWT.NONE, this,
				businessModelManager);
		rmViewer.initTreeViewerData();
		tabFolder.getItem(1).setControl(rmViewer);
	}

	private void initActions() {
		makeActions();
		contributeToActionBars();
	}

	private void contributeToActionBars() {
		IActionBars bars = this.getSite().getActionBars();
		fillLocalToolBar(bars.getToolBarManager());
	}

	private void fillLocalToolBar(IToolBarManager manager) {
		manager.add(expandTreeAction);
		manager.add(collapseTreeAction);
		manager.add(modelFilterAction);
	}

	private void makeActions() {
		collapseTreeAction = new Action() {
			public void run() {
				int index = tabFolder.getSelectionIndex();
				switch (index) {
				case 0:
					mrViewer.getTreeViewer().collapseAll();
					break;
				case 1:
					rmViewer.getTreeViewer().collapseAll();
					break;
				}

			}
		};
		collapseTreeAction.setText("Collapse All");
		collapseTreeAction.setToolTipText("Collapse All");
		collapseTreeAction.setImageDescriptor(ImageManager.getInstance()
				.getImageDescriptor(ImageKeys.IMG_TREE_OPER_COLLAPSE));
		expandTreeAction = new Action() {
			public void run() {
				int index = tabFolder.getSelectionIndex();
				switch (index) {
				case 0:
					mrViewer.getTreeViewer().expandAll();
					break;
				case 1:
					rmViewer.getTreeViewer().expandAll();
					break;
				}

			}
		};
		modelFilterAction = new ModelFilterAction("filter", this);
		expandTreeAction.setText("Expand All");
		expandTreeAction.setToolTipText("Expand All");
		expandTreeAction.setImageDescriptor(ImageManager.getInstance()
				.getImageDescriptor(ImageKeys.IMG_TREE_OPER_EXPAND));
	}

	public void propertyChanged(Object arg0, int arg1) {
		// TODO Auto-generated method stub

	}

	public void setFocus() {
		int selectionIndex = tabFolder.getSelectionIndex();
		if (selectionIndex == 0) {
			mrViewer.getTreeViewer().getControl().setFocus();
		} else {
			rmViewer.getTreeViewer().getControl().setFocus();
		}
	}

	/*
	 * (non-Javadoc) Method declared on ISelectionProvider.
	 */
	public void addSelectionChangedListener(ISelectionChangedListener listener) {
		if (mrViewer != null && mrViewer.getTreeViewer() != null) {
			mrViewer.getTreeViewer().addSelectionChangedListener(listener);
		}
		if (rmViewer != null && rmViewer.getTreeViewer() != null) {
			rmViewer.getTreeViewer().addSelectionChangedListener(listener);
		}
		selectionChangedListeners.add(listener);
	}

	public ISelection getSelection() {
		if (tabFolder == null) {
			return null;
		}
		int selectionIndex = tabFolder.getSelectionIndex();
		if (selectionIndex == 0) {
			if (mrViewer == null || mrViewer.getTreeViewer() == null) {
				return StructuredSelection.EMPTY;
			} else {
				return mrViewer.getTreeViewer().getSelection();
			}
		} else {
			if (rmViewer == null || rmViewer.getTreeViewer() == null) {
				return StructuredSelection.EMPTY;
			} else {
				return rmViewer.getTreeViewer().getSelection();
			}
		}
	}

	/*
	 * (non-Javadoc) Method declared on ISelectionProvider.
	 */
	public void removeSelectionChangedListener(
			ISelectionChangedListener listener) {
		if (mrViewer != null && mrViewer.getTreeViewer() != null) {
			mrViewer.getTreeViewer().removeSelectionChangedListener(listener);
		}
		if (rmViewer != null && rmViewer.getTreeViewer() != null) {
			rmViewer.getTreeViewer().removeSelectionChangedListener(listener);
		}
		selectionChangedListeners.remove(listener);
	}

	public void setSelection(ISelection selection) {
		if (tabFolder != null) {
			if (mrViewer != null && mrViewer.getTreeViewer() != null) {
				mrViewer.getTreeViewer().setSelection(selection);
			}
			if (rmViewer != null && rmViewer.getTreeViewer() != null) {
				rmViewer.getTreeViewer().setSelection(selection);
			}
		}
	}

	public void selectionChanged(SelectionChangedEvent event) {
		fireSelectionChanged(event.getSelection());
		if (mrViewer != null) {
			mrViewer.getTreeViewer().setSelection(event.getSelection());
		}
		if (rmViewer != null) {
			rmViewer.getTreeViewer().setSelection(event.getSelection());
		}
	}

	/**
	 * Fires a selection changed event.
	 * 
	 * @param selection
	 *            the new selection
	 */
	protected void fireSelectionChanged(ISelection selection) {
		// create an event
		final SelectionChangedEvent event = new SelectionChangedEvent(this,
				selection);

		// fire the event
		Object[] listeners = selectionChangedListeners.getListeners();
		for (int i = 0; i < listeners.length; ++i) {
			final ISelectionChangedListener l = (ISelectionChangedListener) listeners[i];
			SafeRunner.run(new SafeRunnable() {
				public void run() {
					l.selectionChanged(event);
				}
			});
		}
	}

	@Override
	public Control getControl() {
		if (tabFolder == null) {
			return null;
		}
		return tabFolder;
	}

	public BusinessModelManager getBusinessModelManager() {
		return businessModelManager;
	}

	public ModelResourceView getMrViewer() {
		return mrViewer;
	}

	public RepositoryModelView getRmViewer() {
		return rmViewer;
	}

	@Override
	public void dispose() {
		// rmViewer.removeListener();
		super.dispose();
	}

	public Object getCurrentViewer() {
		return currentViewer;
	}

	public BusinessModelDiagramEditor getBusinessModelDiagramEditor() {
		return businessModelDiagramEditor;
	}

}
