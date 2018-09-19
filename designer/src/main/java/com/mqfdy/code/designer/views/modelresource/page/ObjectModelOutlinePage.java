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

// TODO: Auto-generated Javadoc
/**
 * The Class ObjectModelOutlinePage.
 *
 * @author mqfdy
 */
public class ObjectModelOutlinePage extends Page implements
		IContentOutlinePage, ISelectionChangedListener, IPropertyListener {

	/** The selection changed listeners. */
	private ListenerList selectionChangedListeners = new ListenerList();

	/** The business model manager. */
	private BusinessModelManager businessModelManager;

	/** The business model diagram editor. */
	private BusinessModelDiagramEditor businessModelDiagramEditor;
	
	/** 导航标签页. */
	private TabFolder tabFolder;

	/** 模型资源视图. */
	private ModelResourceView mrViewer;

	/** 引用模型视图. */
	private RepositoryModelView rmViewer;

	/** 树收缩动作. */
	private Action collapseTreeAction;
	
	/** 过滤条件动作. */
	private Action modelFilterAction;

	/** 树收缩动作. */
	private Action expandTreeAction;

	/** The current viewer. */
	protected Object currentViewer;

	/**
	 * Instantiates a new object model outline page.
	 *
	 * @param businessModelManager
	 *            the business model manager
	 */
	public ObjectModelOutlinePage(BusinessModelManager businessModelManager) {
		this.businessModelManager = businessModelManager;
	}

	/**
	 * Instantiates a new object model outline page.
	 *
	 * @param businessModelDiagramEditor
	 *            the business model diagram editor
	 */
	public ObjectModelOutlinePage(
			BusinessModelDiagramEditor businessModelDiagramEditor) {
		this.businessModelDiagramEditor = businessModelDiagramEditor;
		this.businessModelManager = businessModelDiagramEditor.getBusinessModelManager();
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
		createTabFolder(parent);
		createModelResourceView();
		//createRepositoryModelView();
		currentViewer = mrViewer;
		initActions();
	}

	/**
	 * Inits the.
	 *
	 * @author mqfdy
	 * @param pageSite
	 *            the page site
	 * @Date 2018-09-03 09:00
	 */
	public void init(IPageSite pageSite) {
		super.init(pageSite);

	}

	/**
	 * 初始化标签页.
	 *
	 * @author mqfdy
	 * @param parent
	 *            the parent
	 * @Date 2018-09-03 09:00
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
	 * 创建模型资源视图.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	private void createModelResourceView() {
		mrViewer = new ModelResourceView(tabFolder, SWT.NONE,
				businessModelManager);
		mrViewer.initTreeViewerData();
		tabFolder.getItem(0).setControl(mrViewer);
		// getSite().setSelectionProvider(mrViewer.getTreeViewer());

	}

	/**
	 * Creates the repository model view.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	private void createRepositoryModelView() {
		rmViewer = new RepositoryModelView(tabFolder, SWT.NONE, this,
				businessModelManager);
		rmViewer.initTreeViewerData();
		tabFolder.getItem(1).setControl(rmViewer);
	}

	/**
	 * Inits the actions.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	private void initActions() {
		makeActions();
		contributeToActionBars();
	}

	/**
	 * Contribute to action bars.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	private void contributeToActionBars() {
		IActionBars bars = this.getSite().getActionBars();
		fillLocalToolBar(bars.getToolBarManager());
	}

	/**
	 * Fill local tool bar.
	 *
	 * @author mqfdy
	 * @param manager
	 *            the manager
	 * @Date 2018-09-03 09:00
	 */
	private void fillLocalToolBar(IToolBarManager manager) {
		manager.add(expandTreeAction);
		manager.add(collapseTreeAction);
		manager.add(modelFilterAction);
	}

	/**
	 * Make actions.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
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

	/**
	 * Property changed.
	 *
	 * @author mqfdy
	 * @param arg0
	 *            the arg 0
	 * @param arg1
	 *            the arg 1
	 * @Date 2018-09-03 09:00
	 */
	public void propertyChanged(Object arg0, int arg1) {
		// TODO Auto-generated method stub

	}

	/**
	 * 
	 */
	public void setFocus() {
		int selectionIndex = tabFolder.getSelectionIndex();
		if (selectionIndex == 0) {
			mrViewer.getTreeViewer().getControl().setFocus();
		} else {
			rmViewer.getTreeViewer().getControl().setFocus();
		}
	}

	/**
	 * Adds the selection changed listener.
	 *
	 * @author mqfdy
	 * @param listener
	 *            the listener
	 * @Date 2018-09-03 09:00
	 */
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

	/**
	 * @return
	 */
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

	/**
	 * Removes the selection changed listener.
	 *
	 * @author mqfdy
	 * @param listener
	 *            the listener
	 * @Date 2018-09-03 09:00
	 */
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

	/**
	 * Sets the selection.
	 *
	 * @author mqfdy
	 * @param selection
	 *            the new selection
	 * @Date 2018-09-03 09:00
	 */
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

	/**
	 * Selection changed.
	 *
	 * @author mqfdy
	 * @param event
	 *            the event
	 * @Date 2018-09-03 09:00
	 */
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
	 * @author mqfdy
	 * @param selection
	 *            the new selection
	 * @Date 2018-09-03 09:00
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

	/**
	 * @return
	 */
	@Override
	public Control getControl() {
		if (tabFolder == null) {
			return null;
		}
		return tabFolder;
	}

	/**
	 * Gets the business model manager.
	 *
	 * @author mqfdy
	 * @return the business model manager
	 * @Date 2018-09-03 09:00
	 */
	public BusinessModelManager getBusinessModelManager() {
		return businessModelManager;
	}

	/**
	 * Gets the mr viewer.
	 *
	 * @author mqfdy
	 * @return the mr viewer
	 * @Date 2018-09-03 09:00
	 */
	public ModelResourceView getMrViewer() {
		return mrViewer;
	}

	/**
	 * Gets the rm viewer.
	 *
	 * @author mqfdy
	 * @return the rm viewer
	 * @Date 2018-09-03 09:00
	 */
	public RepositoryModelView getRmViewer() {
		return rmViewer;
	}

	/**
	 * 
	 */
	@Override
	public void dispose() {
		// rmViewer.removeListener();
		super.dispose();
	}

	/**
	 * Gets the current viewer.
	 *
	 * @author mqfdy
	 * @return the current viewer
	 * @Date 2018-09-03 09:00
	 */
	public Object getCurrentViewer() {
		return currentViewer;
	}

	/**
	 * Gets the business model diagram editor.
	 *
	 * @author mqfdy
	 * @return the business model diagram editor
	 * @Date 2018-09-03 09:00
	 */
	public BusinessModelDiagramEditor getBusinessModelDiagramEditor() {
		return businessModelDiagramEditor;
	}

}
