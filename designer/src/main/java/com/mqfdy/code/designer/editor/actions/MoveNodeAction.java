package com.mqfdy.code.designer.editor.actions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.actions.ActionFactory;

import com.mqfdy.code.designer.editor.commands.MoveMutilNodeConstraintCommand;
import com.mqfdy.code.designer.editor.part.NodeEditPart;
import com.mqfdy.code.designer.utils.BusinessModelUtil;

public class MoveNodeAction extends SelectionAction {
	public int type;

	public MoveNodeAction(IWorkbenchPart part) {
		super(part);
		// force calculateEnabled() to be called in every context
		setLazyEnablementCalculation(true);
	}

	public void setType(int type) {
		this.type = type;
	}

	@Override
	protected void init() {
		super.init();
		setId(ActionFactory.MOVE.getId());
		setEnabled(true);
	}

	private MoveMutilNodeConstraintCommand createCommand(
			List<Object> selectedObjects) {
		if (selectedObjects == null || selectedObjects.isEmpty()) {
			return null;
		}
		List<NodeEditPart> editPartList = new ArrayList<NodeEditPart>();
		MoveMutilNodeConstraintCommand cmd = null;// new
													// NodeSetConstraintCommand((NodeEditPart)
													// nodePart, req,
													// (Rectangle) constraint);
		Iterator<Object> it = selectedObjects.iterator();
		while (it.hasNext()) {
			Object s = it.next();
			if (s instanceof NodeEditPart) {
				NodeEditPart ep = (NodeEditPart) s;
				editPartList.add((NodeEditPart) ep);
			}
		}
		if (editPartList.size() > 0) {
			cmd = new MoveMutilNodeConstraintCommand(editPartList, type);
		}
		return cmd;
	}

	@Override
	protected boolean calculateEnabled() {
		Command cmd = createCommand(BusinessModelUtil.getSelectedEditParts());
		if (cmd == null)
			return false;
		return cmd.canExecute();
	}

	@Override
	public void run() {
		Command cmd = createCommand(BusinessModelUtil.getSelectedEditParts());
		if (cmd != null && cmd.canExecute()) {
			// cmd.execute();
			BusinessModelUtil.getBusinessModelDiagramEditor()
					.getCommandStacks().execute(cmd);
		}

	}
}