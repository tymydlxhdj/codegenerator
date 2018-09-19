package com.mqfdy.code.designer.editor.actions;

import java.util.List;

import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.jface.action.IMenuCreator;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Widget;

import com.mqfdy.code.designer.editor.BusinessModelDiagramEditor;
import com.mqfdy.code.model.graph.Diagram;

// TODO: Auto-generated Javadoc
/**
 * The Class DiagramSelectMenuCreator.
 *
 * @author mqfdy
 */
public class DiagramSelectMenuCreator implements IMenuCreator {

	/** The visual editor. */
	private BusinessModelDiagramEditor visualEditor;
	
	/** The view list menu. */
	private Menu viewListMenu;
	
	/** The dia name. */
	private String diaName;
	
	/** The dia ID. */
	private String diaID;
	
	/**
	 * Instantiates a new diagram select menu creator.
	 *
	 * @param businessModelDiagramEditor
	 *            the business model diagram editor
	 */
	public DiagramSelectMenuCreator(
			BusinessModelDiagramEditor businessModelDiagramEditor) {
		setWebletVisualEditor(businessModelDiagramEditor);
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
		this.visualEditor = editor;
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
		List<Diagram> views = this.visualEditor.getBusinessModelManager().getBusinessObjectModel().getDiagrams();
		Object dia = visualEditor.getViewer().getContents().getModel();
		for (Diagram view : views) {
			String viewId = view.getId();
			String viewName = view.getDisplayName()+"【"+view.getBelongPackage().getFullName()+"】";

			MenuItem menuItem = new MenuItem(this.viewListMenu, 32);
			menuItem.setText(viewName);
			menuItem.setData(view);
			if (dia != null && viewId.equals(((Diagram) dia).getId())) {
				menuItem.setSelection(true);
			}
			menuItem.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {
					setDiaId(((Diagram) ((MenuItem) e.getSource()).getData()).getId());
					setDiaName(((Diagram) ((MenuItem) e.getSource()).getData()).getBelongPackage().getFullName());
					Diagram d = getDiagram();
					Object oldDia = visualEditor.getViewer().getContents().getModel();
					if(d != null && oldDia != d){
						visualEditor.setDia(d);
						visualEditor.getViewer().setContents(d);
						boolean ise = visualEditor.isDirty();
						visualEditor.getCommandStacks().flush();
						visualEditor.setDirty(ise);
						for(Diagram dia : visualEditor.getBusinessModelManager().getBusinessObjectModel().getDiagrams()){
							dia.setDefault(false);
						}
						d.setDefault(true);
						
						
						ZoomManager zoomMgr = (ZoomManager) visualEditor.getViewer()
								.getProperty(ZoomManager.class.toString());
						if (zoomMgr != null && d.getDefaultStyle() != null) {
							zoomMgr.setZoom(((double) d.getDefaultStyle().getZoomScale()) / 100);
						}
					}
				}



			});
		}

		return this.viewListMenu;
	}
	
	/**
	 * Gets the diagram.
	 *
	 * @author mqfdy
	 * @return the diagram
	 * @Date 2018-09-03 09:00
	 */
	protected Diagram getDiagram(){
		List<Diagram> diagrams = visualEditor.getBusinessModelManager().getBusinessObjectModel().getDiagrams();
		for(int i = 0;i < diagrams.size();i++){
			if(diagrams.get(i).getId().equals(getDiaId())){
				return diagrams.get(i);
			}
		}
		return null;
	}
	
	/**
	 * 
	 */
	public void dispose() {
	}

	/**
	 * Gets the dia name.
	 *
	 * @author mqfdy
	 * @return the dia name
	 * @Date 2018-09-03 09:00
	 */
	public String getDiaName() {
		return diaName;
	}

	/**
	 * Sets the dia name.
	 *
	 * @author mqfdy
	 * @param diaName
	 *            the new dia name
	 * @Date 2018-09-03 09:00
	 */
	public void setDiaName(String diaName) {
		this.diaName = diaName;
	}
	
	/**
	 * Gets the dia id.
	 *
	 * @author mqfdy
	 * @return the dia id
	 * @Date 2018-09-03 09:00
	 */
	public String getDiaId() {
		return diaID;
	}

	
	/**
	 * Sets the dia id.
	 *
	 * @author mqfdy
	 * @param id
	 *            the new dia id
	 * @Date 2018-09-03 09:00
	 */
	private void setDiaId(String id) {
		this.diaID=id;
	}
}
