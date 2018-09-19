package com.mqfdy.code.designer.editor.actions;

import org.eclipse.core.runtime.Assert;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.editparts.ZoomListener;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.jface.action.IMenuCreator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IPartService;
import org.eclipse.ui.IWorkbenchPart;

import com.mqfdy.code.designer.editor.BusinessModelDiagramEditor;
import com.mqfdy.code.designer.editor.utils.Logger;
import com.mqfdy.code.designer.utils.BusinessModelUtil;
import com.mqfdy.code.model.graph.Diagram;
import com.mqfdy.code.model.graph.DiagramStyle;

// TODO: Auto-generated Javadoc
/**
 * The Class ZoomSelectMenuCreator.
 *
 * @author mqfdy
 */
public class ZoomSelectMenuCreator implements IMenuCreator ,ZoomListener {

	/** The business model diagram editor. */
	private BusinessModelDiagramEditor businessModelDiagramEditor; 
	
	/** The view list menu. */
	private Menu viewListMenu;
	
	/** The zoom scale. */
	private String zoomScale;
	
	/** The zoom manager. */
	private ZoomManager zoomManager;
	
	/** The service. */
	private IPartService service;
	
	/** The part listener. */
	private IPartListener partListener;
	
	/**
	 * Instantiates a new zoom select menu creator.
	 *
	 * @param businessModelDiagramEditor
	 *            the business model diagram editor
	 */
	public ZoomSelectMenuCreator(
			BusinessModelDiagramEditor businessModelDiagramEditor) {
		setWebletVisualEditor(businessModelDiagramEditor);
		this.service = businessModelDiagramEditor.getSite().getPage();
		Assert.isNotNull(this.service);
		this.service.addPartListener(partListener = new IPartListener() {
			public void partActivated(IWorkbenchPart part) {
				setZoomManager((ZoomManager) part.getAdapter(ZoomManager.class));
			}

			public void partBroughtToTop(IWorkbenchPart p) {
			}

			public void partClosed(IWorkbenchPart p) {
			}

			public void partDeactivated(IWorkbenchPart p) {
			}

			public void partOpened(IWorkbenchPart p) {
			}
		});
	}

	/**
	 * Sets the weblet visual editor.
	 *
	 * @author mqfdy
	 * @param editor
	 *            the new weblet visual editor
	 * @Date 2018-09-03 09:00
	 */
	public void setWebletVisualEditor(BusinessModelDiagramEditor editor) {
		this.businessModelDiagramEditor = editor;
	}

	/**
	 * Gets the menu.
	 *
	 * @author mqfdy
	 * @param parent
	 *            the parent
	 * @return the menu
	 * @Date 2018-09-03 09:00
	 */
	public Menu getMenu(Control parent) {
		return sharedGetMenu(parent);
	}

	/**
	 * Gets the menu.
	 *
	 * @author mqfdy
	 * @param parent
	 *            the parent
	 * @return the menu
	 * @Date 2018-09-03 09:00
	 */
	public Menu getMenu(Menu parent) {
		return sharedGetMenu(parent);
	}

	/**
	 * Shared get menu.
	 *
	 * @author mqfdy
	 * @param parent
	 *            the parent
	 * @return the menu
	 * @Date 2018-09-03 09:00
	 */
	private Menu sharedGetMenu(Widget parent) {
		if (this.viewListMenu != null) {
			this.viewListMenu.dispose();
		}
		if ((parent instanceof Control)) {
			this.viewListMenu = new Menu((Control) parent);
		}
		if ((parent instanceof Menu)) {
			this.viewListMenu = new Menu((Menu) parent);
		}
		String[] zooms = new String[] { "50%","75%","100%","150%","200%","250%","300%","400%" };
		Object dia = businessModelDiagramEditor.getViewer().getContents().getModel();
		if(dia!=null && ((Diagram)dia).getDefaultStyle() == null){
			DiagramStyle style = new DiagramStyle();
			((Diagram)dia).setDefaultStyle(style);
		}
		
//		for (int i = 0; i < zooms.length;i ++) {
		for(String view : zooms){
			final String viewId = view;

			MenuItem menuItem = new MenuItem(this.viewListMenu, 32);
			menuItem.setData(view);
			menuItem.setText(view);
			menuItem.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {
					setZoomScale(viewId);
					handleWidgetDefaultSelected();
				}

			});
			if (dia != null && viewId.equals(((Diagram)dia).getDefaultStyle().getZoomScale()+"%")) {
				menuItem.setSelection(true);
			}

		}

		return this.viewListMenu;
	}
	
	/**
	 * Handle widget default selected.
	 *
	 * @see org.eclipse.swt.events.SelectionListener#widgetDefaultSelected(SelectionEvent)
	 */
	private void handleWidgetDefaultSelected() {
		if (zoomManager != null) {
			zoomManager.setZoomAsText(getZoomScale());
		}
		refresh(false);
	}
	
	/**
	 * 
	 */
	public void dispose() {
		if (partListener == null)
			return;
		service.removePartListener(partListener);
		if (zoomManager != null) {
			zoomManager.removeZoomListener(this);
			zoomManager = null;
		}
		partListener = null;
	}

	/**
	 * Gets the zoom scale.
	 *
	 * @author mqfdy
	 * @return the zoom scale
	 * @Date 2018-09-03 09:00
	 */
	public String getZoomScale() {
		return zoomScale;
	}

	/**
	 * Sets the zoom scale.
	 *
	 * @author mqfdy
	 * @param zoomScale
	 *            the new zoom scale
	 * @Date 2018-09-03 09:00
	 */
	public void setZoomScale(String zoomScale) {
		this.zoomScale = zoomScale;
	}

	/**
	 * Zoom changed.
	 *
	 * @param zoom
	 *            the zoom
	 * @see ZoomListener#zoomChanged(double)
	 */
	public void zoomChanged(double zoom) {
		if (BusinessModelUtil.getBusinessModelDiagramEditor() != null) {
			Object dia = BusinessModelUtil.getBusinessModelDiagramEditor()
					.getViewer().getContents().getModel();
			if (dia instanceof Diagram) {
				if (((Diagram) dia).getDefaultStyle() != null) {
					((Diagram) dia).getDefaultStyle().setZoomScale(
							(int) (zoom * 100));
					BusinessModelUtil.getBusinessModelDiagramEditor().setDirty(
							true);
				}
			}
			// zoomManager = (ZoomManager)
			// viewer.getProperty(ZoomManager.class.toString());
		}
		refresh(false);
	}
	
	/**
	 * Refresh.
	 *
	 * @author mqfdy
	 * @param repopulateCombo
	 *            the repopulate combo
	 * @Date 2018-09-03 09:00
	 */
	private void refresh(boolean repopulateCombo) {
//		if (combo == null || combo.isDisposed())
//			return;
		// $TODO GTK workaround
		try {
			if (zoomManager == null) {
				if (BusinessModelUtil.getBusinessModelDiagramEditor() != null) {
					GraphicalViewer viewer = BusinessModelUtil
							.getBusinessModelDiagramEditor().getViewer();
					zoomManager = (ZoomManager) viewer
							.getProperty(ZoomManager.class.toString());
				}
			}
//			if (zoomManager == null) {
//				combo.setEnabled(false);
//				combo.setText(""); //$NON-NLS-1$
//			} else {
//				if (repopulateCombo)
//					combo.setItems(getZoomManager().getZoomLevelsAsText());
//				String zoom = getZoomManager().getZoomAsText();
//				int index = combo.indexOf(zoom);
//				if (index == -1 || forceSetText)
//					combo.setText(zoom);
//				else
//					combo.select(index);
//				combo.setEnabled(true);
//			}
		} catch (SWTException exception) {
			Logger.log(exception);
			if (!SWT.getPlatform().equals("gtk")) //$NON-NLS-1$
				throw exception;
		}
	}
	
	/**
	 * Returns the zoomManager.
	 *
	 * @author mqfdy
	 * @return ZoomManager
	 * @Date 2018-09-03 09:00
	 */
	public ZoomManager getZoomManager() {
		return zoomManager;
	}

	/**
	 * Sets the ZoomManager.
	 *
	 * @author mqfdy
	 * @param zm
	 *            The ZoomManager
	 * @Date 2018-09-03 09:00
	 */
	public void setZoomManager(ZoomManager zm) {
		if (zoomManager == zm)
			return;
		if (zoomManager != null)
			zoomManager.removeZoomListener(this);

		zoomManager = zm;
		refresh(true);

		if (zoomManager != null)
			zoomManager.addZoomListener(this);
	}
}
