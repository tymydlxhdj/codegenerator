package com.mqfdy.code.designer.editor.actions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.Assert;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.commands.Command;
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
import com.mqfdy.code.designer.editor.commands.AutoLayoutCommand;
import com.mqfdy.code.designer.editor.commands.DiagramAutoLayoutCommand;
import com.mqfdy.code.designer.editor.part.DiagramEditPart;
import com.mqfdy.code.designer.editor.utils.Logger;
import com.mqfdy.code.designer.utils.BusinessModelUtil;
import com.mqfdy.code.model.graph.Diagram;
import com.mqfdy.code.model.graph.DiagramStyle;

public class LayoutAlgorithmSelectMenuCreator implements IMenuCreator ,ZoomListener {

	private Menu viewListMenu;
	private IPartService service;
	private IPartListener partListener;
	private BusinessModelDiagramEditor businessModelDiagramEditor;
	public LayoutAlgorithmSelectMenuCreator(
			BusinessModelDiagramEditor businessModelDiagramEditor) {
		setWebletVisualEditor(businessModelDiagramEditor);
		this.businessModelDiagramEditor = businessModelDiagramEditor;
		this.service = businessModelDiagramEditor.getSite().getPage();
		
		Assert.isNotNull(this.service);
	}

	public void setWebletVisualEditor(BusinessModelDiagramEditor editor) {
		this.businessModelDiagramEditor = editor;
	}

	public Menu getMenu(Control parent) {
		return sharedGetMenu(parent);
	}

	public Menu getMenu(Menu parent) {
		return sharedGetMenu(parent);
	}

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
//		Map styles = new HashMap();
//		styles.put("tree", "树状布局");
//		styles.put("radial", "放射状布局");
		
		String[] styles = new String[] { "树状布局","放射状布局" };
		Object dia = businessModelDiagramEditor.getViewer().getContents().getModel();
		if(dia!=null && ((Diagram)dia).getDefaultStyle() == null){
			DiagramStyle style = new DiagramStyle();
			((Diagram)dia).setDefaultStyle(style);
		}
		
	//	Iterator it = styles.keySet().iterator();
		for( int i =0;i<styles.length;i++){
			final int index = i;
//			String key = (String)it.next();
//			String style = (String)styles.get(key);
			MenuItem menuItem = new MenuItem(this.viewListMenu, 32);
			menuItem.setData(i);
			menuItem.setText(styles[i]);
			menuItem.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {
					Command cmd = createAutoLayoutCommand(
							(DiagramEditPart) businessModelDiagramEditor.getViewer().getContents()
							,index);
					if (cmd != null && cmd.canExecute()) {
						businessModelDiagramEditor.getCommandStacks().execute(cmd);
					}
					
					Object diagram = businessModelDiagramEditor.getViewer().getContents().getModel();
					if(diagram!=null && ((Diagram)diagram).getDefaultStyle() != null){
						((Diagram)diagram).getDefaultStyle().setLayout(String.valueOf(((MenuItem)e.getSource()).getData()));
					}
				}

			});
			if (dia != null && String.valueOf(i).equals(((Diagram)dia).getDefaultStyle().getLayout())) {
				menuItem.setSelection(true);
			}
		}
		
		

		return this.viewListMenu;
	}
	
	private Command createAutoLayoutCommand(
			DiagramEditPart diae,int style) {
		if (diae == null) {
			return null;
		}
		DiagramAutoLayoutCommand cmd = new DiagramAutoLayoutCommand(diae,style);
		return cmd;
	}
	public void dispose() {
		if (partListener == null)
			return;
		service.removePartListener(partListener);
		partListener = null;
	}

	/**
	 * @see ZoomListener#zoomChanged(double)
	 */
	public void zoomChanged(double zoom) {

	}
}
