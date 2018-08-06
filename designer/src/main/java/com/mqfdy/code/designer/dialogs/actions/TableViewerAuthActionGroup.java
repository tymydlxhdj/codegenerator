package com.mqfdy.code.designer.dialogs.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Table;
import org.eclipse.ui.actions.ActionGroup;

import com.mqfdy.code.designer.dialogs.ModelElementEditorDialog;

public class TableViewerAuthActionGroup extends ActionGroup {

	private TableViewer tv;
	private ModelElementEditorDialog dialog;

	public TableViewerAuthActionGroup(TableViewer tv, int type,
			ModelElementEditorDialog dialog) {
		this.tv = tv;
		this.dialog = dialog;
	}

	public void fillContextMenu(IMenuManager mgr, IAction ac) {
		MenuManager menuManager = (MenuManager) mgr;
		menuManager.add(ac);
		Table table = tv.getTable();
		Menu menu = menuManager.createContextMenu(table);
		table.setMenu(menu);
	}
	public void fillSeparator(IMenuManager mgr){
		MenuManager menuManager = (MenuManager) mgr;
		menuManager.add(new Separator());
	}
}