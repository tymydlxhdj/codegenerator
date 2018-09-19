package com.mqfdy.code.designer.editor;

import org.eclipse.gef.ui.actions.ActionBarContributor;
import org.eclipse.gef.ui.actions.DeleteRetargetAction;
import org.eclipse.gef.ui.actions.RedoRetargetAction;
import org.eclipse.gef.ui.actions.UndoRetargetAction;
import org.eclipse.gef.ui.actions.ZoomInRetargetAction;
import org.eclipse.gef.ui.actions.ZoomOutRetargetAction;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.RetargetAction;

// TODO: Auto-generated Javadoc
/**
 * 创建编辑器所需的菜单和操作，BusinessModelDiagramEditorActionBarContributor.
 *
 * @author mqfdy
 */

public class BMEditorActionBarContributor extends ActionBarContributor {

	/**
	 * Instantiates a new BM editor action bar contributor.
	 */
	public BMEditorActionBarContributor() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	@Override
	protected void buildActions() {
		addRetargetAction(new UndoRetargetAction());
		addRetargetAction(new RedoRetargetAction());
		// IAction action;
//		DeleteRetargetAction del = new DeleteRetargetAction();
//		del.setText("删除");
//		del.setDescription("删除");
//		addRetargetAction(del);
//		ZoomInRetargetAction s = new ZoomInRetargetAction();
//		s.setText("放大");
//		s.setDescription("放大");
//		addRetargetAction(s);
//		ZoomOutRetargetAction action = new ZoomOutRetargetAction();
//		action.setText("缩小");
//		action.setDescription("缩小");
//		addRetargetAction(action);

		// addRetargetAction(new DeleteRetargetAction());
		// addRetargetAction(new ZoomInRetargetAction());
		// addRetargetAction(new ZoomOutRetargetAction());
		IWorkbenchWindow iww = getPage().getWorkbenchWindow();
//		addRetargetAction((RetargetAction) ActionFactory.COPY.create(iww));
//		addRetargetAction((RetargetAction) ActionFactory.PASTE.create(iww));
//		addRetargetAction((RetargetAction) ActionFactory.CUT.create(iww));
		addRetargetAction((RetargetAction) ActionFactory.SELECT_ALL.create(iww));
	}

	/**
	 * Inits the.
	 *
	 * @author mqfdy
	 * @param bars
	 *            the bars
	 * @Date 2018-09-03 09:00
	 */
	@Override
	public void init(IActionBars bars) {
		// TODO Auto-generated method stub
		super.init(bars);
	}

	/**
	 * 
	 */
	@Override
	protected void declareGlobalActionKeys() {
		addGlobalActionKey(ActionFactory.SELECT_ALL.getId());
		addGlobalActionKey(ActionFactory.COPY.getId());
		addGlobalActionKey(ActionFactory.PASTE.getId());
		addGlobalActionKey(ActionFactory.CUT.getId());
	}
}