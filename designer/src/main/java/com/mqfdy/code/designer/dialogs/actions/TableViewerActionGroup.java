package com.mqfdy.code.designer.dialogs.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Table;
import org.eclipse.ui.actions.ActionGroup;

import com.mqfdy.code.designer.dialogs.ModelElementEditorDialog;

public class TableViewerActionGroup extends ActionGroup {

	private TableViewer tv;
	// 类型 1为属性 2为操作
	private int type;
	private ModelElementEditorDialog businessClass;

	public TableViewerActionGroup(TableViewer tv, int type,
			ModelElementEditorDialog businessClassEditorDialog) {
		this.tv = tv;
		this.type = type;
		this.businessClass = businessClassEditorDialog;
	}

	public void fillContextMenu(IMenuManager mgr, IAction ac) {
		MenuManager menuManager = (MenuManager) mgr;
		menuManager.removeAll();
		menuManager.add(new CopyModelFromTableAction(tv));
		menuManager.add(new PasteModelToTableAction(tv, type, businessClass));
		menuManager.add(ac);
		Table table = tv.getTable();

		Menu menu = menuManager.createContextMenu(table);
		table.setMenu(menu);
	}
}