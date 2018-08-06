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

import com.mqfdy.code.designer.editor.commands.CutNodeCommand;
import com.mqfdy.code.designer.utils.BusinessModelUtil;

/**
 * 剪切图形
 * 
 * @author mqfdy
 * 
 */
public class CutNodeAction extends SelectionAction {
	public CutNodeAction(IWorkbenchPart part) {
		super(part);
		setLazyEnablementCalculation(true);
	}

	@Override
	protected void init() {
		super.init();
		ISharedImages sharedImages = PlatformUI.getWorkbench()
				.getSharedImages();
		setText("剪切");
		setDescription("剪切");
		setId(ActionFactory.CUT.getId());
		setHoverImageDescriptor(sharedImages
				.getImageDescriptor(ISharedImages.IMG_TOOL_CUT));
		setImageDescriptor(sharedImages
				.getImageDescriptor(ISharedImages.IMG_TOOL_CUT));
		setDisabledImageDescriptor(sharedImages
				.getImageDescriptor(ISharedImages.IMG_TOOL_CUT_DISABLED));
		setEnabled(false);
	}

	private Command createCutCommand(
			List<AbstractGraphicalEditPart> selectedObjects) {
		if (selectedObjects == null || selectedObjects.isEmpty()) {
			return null;
		}

		CutNodeCommand cmd = new CutNodeCommand(selectedObjects);
		return cmd;
	}

	@Override
	protected boolean calculateEnabled() {
		Command cmd = createCutCommand(getSelectedObjects());
		if (cmd == null)
			return false;
		return cmd.canExecute();
	}

	@Override
	public void run() {
		Command cmd = createCutCommand(getSelectedObjects());
		if (cmd != null && cmd.canExecute()) {
			execute(cmd);
		}
	}

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
