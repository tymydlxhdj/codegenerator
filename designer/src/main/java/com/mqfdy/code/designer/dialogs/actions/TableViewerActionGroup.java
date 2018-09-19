package com.mqfdy.code.designer.dialogs.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Table;
import org.eclipse.ui.actions.ActionGroup;

import com.mqfdy.code.designer.dialogs.ModelElementEditorDialog;

// TODO: Auto-generated Javadoc
/**
 * The Class TableViewerActionGroup.
 *
 * @author mqfdy
 */
public class TableViewerActionGroup extends ActionGroup {

	/** The tv. */
	private TableViewer tv;
	
	/** The type. */
	// 类型 1为属性 2为操作
	private int type;
	
	/** The business class. */
	private ModelElementEditorDialog businessClass;

	/**
	 * Instantiates a new table viewer action group.
	 *
	 * @param tv
	 *            the tv
	 * @param type
	 *            the type
	 * @param businessClassEditorDialog
	 *            the business class editor dialog
	 */
	public TableViewerActionGroup(TableViewer tv, int type,
			ModelElementEditorDialog businessClassEditorDialog) {
		this.tv = tv;
		this.type = type;
		this.businessClass = businessClassEditorDialog;
	}

	/**
	 * Fill context menu.
	 *
	 * @author mqfdy
	 * @param mgr
	 *            the mgr
	 * @param ac
	 *            the ac
	 * @Date 2018-09-03 09:00
	 */
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