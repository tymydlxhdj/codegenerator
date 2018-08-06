package com.mqfdy.code.designer.editor.actions;

import java.util.List;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.NodeEditPart;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.internal.GEFMessages;
import org.eclipse.gef.requests.GroupRequest;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;

/**
 * 删除对象动作    同gef的DeleteAction
 * 用于解决一次删除多个node对象时，重复发送delCommand
 */
public class DeleteAction extends SelectionAction {

	/** @deprecated Use ActionFactory.DELETE.getId() instead. */
	public static final String ID = ActionFactory.DELETE.getId();

	/**
	 * Constructs a <code>DeleteAction</code> using the specified part.
	 * 
	 * @param part
	 *            The part for this action
	 */
	public DeleteAction(IWorkbenchPart part) {
		super(part);
		// 右键菜单名称
		setText("删除");
		setDescription("删除");
		setLazyEnablementCalculation(false);
	}

	/**
	 * Returns <code>true</code> if the selected objects can be deleted. Returns
	 * <code>false</code> if there are no objects selected or the selected
	 * objects are not {@link EditPart}s.
	 * 
	 * @return <code>true</code> if the command should be enabled
	 */
	protected boolean calculateEnabled() {
		Command cmd = createDeleteCommand(getSelectedObjects());
		if (cmd == null)
			return false;
		return cmd.canExecute();
	}

	/**
	 * Create a command to remove the selected objects.
	 * 
	 * @param objects
	 *            The objects to be deleted.
	 * @return The command to remove the selected objects.
	 */
	public Command createDeleteCommand(List objects) {
		if (objects.isEmpty())
			return null;
		if (!(objects.get(0) instanceof EditPart))
			return null;

		GroupRequest deleteReq = new GroupRequest(RequestConstants.REQ_DELETE);
		deleteReq.setEditParts(objects);

		CompoundCommand compoundCmd = new CompoundCommand(
				GEFMessages.DeleteAction_ActionDeleteCommandName);
//		for (int i = 0; i < objects.size(); i++) {
//			EditPart object = (EditPart) objects.get(0);
//			Command cmd = object.getCommand(deleteReq);
//			if (cmd != null)
//				compoundCmd.add(cmd);
//		}
		// modified by zh
		boolean isDelNode = true;
		for (int i = 0; i < objects.size(); i++) {
			EditPart object = (EditPart) objects.get(i);
			if(!(object instanceof NodeEditPart)){
				Command cmd = object.getCommand(deleteReq);
				if (cmd != null)
					compoundCmd.add(cmd);
			}
		}
		for (int i = 0; i < objects.size(); i++) {
			EditPart object = (EditPart) objects.get(i);
			if(object instanceof NodeEditPart){
				// 删除多个node时，只发送一次command
				if(isDelNode){
					Command cmd = object.getCommand(deleteReq);
					if (cmd != null)
						compoundCmd.add(cmd);
					isDelNode = false;
				}
			}
		}
		return compoundCmd;
	}

	/**
	 * Initializes this action's text and images.
	 */
	protected void init() {
		super.init();
		setText(GEFMessages.DeleteAction_Label);
		setToolTipText(GEFMessages.DeleteAction_Tooltip);
		setId(ActionFactory.DELETE.getId());
		ISharedImages sharedImages = PlatformUI.getWorkbench()
				.getSharedImages();
		setImageDescriptor(sharedImages
				.getImageDescriptor(ISharedImages.IMG_TOOL_DELETE));
		setDisabledImageDescriptor(sharedImages
				.getImageDescriptor(ISharedImages.IMG_TOOL_DELETE_DISABLED));
		setEnabled(false);
	}

	/**
	 * Performs the delete action on the selected objects.
	 */
	public void run() {
		execute(createDeleteCommand(getSelectedObjects()));
	}

}
