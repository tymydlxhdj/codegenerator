package com.mqfdy.code.designer.editor.actions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;

import com.mqfdy.code.designer.editor.commands.CopyNodeCommand;
import com.mqfdy.code.designer.utils.BusinessModelUtil;

// TODO: Auto-generated Javadoc
/**
 * 复制图形.
 *
 * @author mqfdy
 */
public class CopyNodeAction extends SelectionAction {
	
	/**
	 * Instantiates a new copy node action.
	 *
	 * @param part
	 *            the part
	 */
	public CopyNodeAction(IWorkbenchPart part) {
		super(part);
		// force calculateEnabled() to be called in every context
		setLazyEnablementCalculation(true);
	}

	/**
	 * 
	 */
	@Override
	protected void init() {
		super.init();
		ISharedImages sharedImages = PlatformUI.getWorkbench()
				.getSharedImages();
		setText("复制");
		setDescription("复制");
		setId(ActionFactory.COPY.getId());
		setHoverImageDescriptor(sharedImages
				.getImageDescriptor(ISharedImages.IMG_TOOL_COPY));
		setImageDescriptor(sharedImages
				.getImageDescriptor(ISharedImages.IMG_TOOL_COPY));
		setDisabledImageDescriptor(sharedImages
				.getImageDescriptor(ISharedImages.IMG_TOOL_COPY_DISABLED));
		setEnabled(false);
	}

	/**
	 * Creates the copy command.
	 *
	 * @author mqfdy
	 * @param selectedObjects
	 *            the selected objects
	 * @return the command
	 * @Date 2018-09-03 09:00
	 */
	private Command createCopyCommand(
			List<AbstractGraphicalEditPart> selectedObjects) {
		if (selectedObjects == null || selectedObjects.isEmpty()) {
			return null;
		}
		CopyNodeCommand cmd = new CopyNodeCommand(selectedObjects);
		return cmd;
	}

	/**
	 * @return
	 */
	@Override
	protected boolean calculateEnabled() {
		Command cmd = createCopyCommand(getSelectedObjects());
		if (cmd == null)
			return false;
		return cmd.canExecute();
	}

	/**
	 * 
	 */
	@Override
	public void run() {
		Command cmd = createCopyCommand(getSelectedObjects());
		if (cmd != null && cmd.canExecute()) {
			cmd.execute();
		}
	}

	/**
	 * @return
	 */
	@Override
	protected List getSelectedObjects() {
		List<AbstractGraphicalEditPart> list = new ArrayList<AbstractGraphicalEditPart>();
		if (BusinessModelUtil.getBusinessModelDiagramEditor() != null) {
			list.addAll(BusinessModelUtil.getBusinessModelDiagramEditor()
					.getViewer().getSelectedEditParts());
		}
		return list;
	}
}