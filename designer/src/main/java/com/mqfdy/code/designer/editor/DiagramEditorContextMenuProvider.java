package com.mqfdy.code.designer.editor;

import org.eclipse.gef.ContextMenuProvider;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.actions.GEFActionConstants;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.ui.actions.ActionFactory;

import com.mqfdy.code.designer.editor.actions.FindModelAction;
import com.mqfdy.code.designer.editor.actions.ImportEnumerationConfAction;
import com.mqfdy.code.designer.editor.actions.ShareModelAction;

// TODO: Auto-generated Javadoc
/**
 * 定义在编辑器中点击右键时弹出的上下文菜单 需要在编辑器里调用GraphicalViewer.SetContextMenu()
 * 
 * @author mqfdy
 */
public class DiagramEditorContextMenuProvider extends ContextMenuProvider {

	/** The action registry. */
	private ActionRegistry actionRegistry;

	/** The action. */
	IAction action;

	/**
	 * Instantiate a new menu context provider for the specified EditPartViewer
	 * and ActionRegistry.
	 * 
	 * @param viewer
	 *            the editor's graphical viewer
	 * @param registry
	 *            the editor's action registry
	 * @throws IllegalArgumentException
	 *             if registry is <tt>null</tt>.
	 */
	public DiagramEditorContextMenuProvider(EditPartViewer viewer,
			ActionRegistry registry) {
		super(viewer);
		if (registry == null) {
			throw new IllegalArgumentException();
		}
		actionRegistry = registry;

	}

	/**
	 * Called when the context menu is about to show. Actions, whose state is
	 * enabled, will appear in the context menu.
	 *
	 * @param menu
	 *            the menu
	 * @see org.eclipse.gef.ContextMenuProvider#buildContextMenu(org.eclipse.jface.action.IMenuManager)
	 */
	@Override
	public void buildContextMenu(IMenuManager menu) {

		// 创建缺省的组
		GEFActionConstants.addStandardActionGroups(menu);

		// 在右键菜单中添加Undo
		menu.appendToGroup(GEFActionConstants.GROUP_UNDO,
				getAction(ActionFactory.UNDO.getId()));
		// 在右键菜单中添加Redo
		menu.appendToGroup(GEFActionConstants.GROUP_UNDO,
				getAction(ActionFactory.REDO.getId()));
		// 下一分组
		// 在右键菜单中添加Delete
		menu.appendToGroup(GEFActionConstants.GROUP_EDIT,
				getAction(ActionFactory.DELETE.getId()));
		// 在右键菜单中添加Copy
		menu.appendToGroup(GEFActionConstants.GROUP_EDIT,
				getAction(ActionFactory.COPY.getId()));
		// 在右键菜单中添加Cut
		menu.appendToGroup(GEFActionConstants.GROUP_EDIT,
				getAction(ActionFactory.CUT.getId()));
		// 在右键菜单中添加Paste
		menu.appendToGroup(GEFActionConstants.GROUP_EDIT,
				getAction(ActionFactory.PASTE.getId()));

		/*// 在右键菜单中添加发现
				menu.appendToGroup(GEFActionConstants.GROUP_EDIT,
						getAction(FindModelAction.FINDID));

		// 在右键菜单中添加ShareModel
				menu.appendToGroup(GEFActionConstants.GROUP_EDIT,
						getAction(ShareModelAction.STR));
				
// 在右键菜单中新增导入枚举
		menu.appendToGroup(GEFActionConstants.GROUP_EDIT,
		getAction(ImportEnumerationConfAction.ENUMER));*/


	}

	/**
	 * Gets the action.
	 *
	 * @author mqfdy
	 * @param actionId
	 *            the action id
	 * @return the action
	 * @Date 2018-09-03 09:00
	 */
	private IAction getAction(String actionId) {
		return actionRegistry.getAction(actionId);
	}

}
