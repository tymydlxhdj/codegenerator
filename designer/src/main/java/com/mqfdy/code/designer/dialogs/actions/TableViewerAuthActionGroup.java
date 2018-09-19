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

// TODO: Auto-generated Javadoc
/**
 * The Class TableViewerAuthActionGroup.
 *
 * @author mqfdy
 */
public class TableViewerAuthActionGroup extends ActionGroup {

	/** The tv. */
	private TableViewer tv;
	
	/** The dialog. */
	private ModelElementEditorDialog dialog;

	/**
	 * Instantiates a new table viewer auth action group.
	 *
	 * @param tv
	 *            the tv
	 * @param type
	 *            the type
	 * @param dialog
	 *            the dialog
	 */
	public TableViewerAuthActionGroup(TableViewer tv, int type,
			ModelElementEditorDialog dialog) {
		this.tv = tv;
		this.dialog = dialog;
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
		menuManager.add(ac);
		Table table = tv.getTable();
		Menu menu = menuManager.createContextMenu(table);
		table.setMenu(menu);
	}
	
	/**
	 * Fill separator.
	 *
	 * @author mqfdy
	 * @param mgr
	 *            the mgr
	 * @Date 2018-09-03 09:00
	 */
	public void fillSeparator(IMenuManager mgr){
		MenuManager menuManager = (MenuManager) mgr;
		menuManager.add(new Separator());
	}
}