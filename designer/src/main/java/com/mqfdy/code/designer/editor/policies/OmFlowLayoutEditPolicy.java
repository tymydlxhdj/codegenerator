package com.mqfdy.code.designer.editor.policies;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.CreateRequest;

import com.mqfdy.code.designer.editor.commands.AtomicAddCommand;
import com.mqfdy.code.designer.editor.commands.AtomicCreateCommand;
import com.mqfdy.code.model.AbstractModelElement;

/**
 * 在透视图中创建对象时调用的编辑策略
 * 
 * @author mqfdy
 * 
 */
public class OmFlowLayoutEditPolicy extends
		org.eclipse.gef.editpolicies.FlowLayoutEditPolicy {

	public OmFlowLayoutEditPolicy() {
		super();
	}

	/**
	 * Command used when a child has been added. Creates a new AtomicAddCommand.
	 */
	@Override
	protected Command createAddCommand(EditPart child, EditPart after) {

		// Create a new AtomicAddCommand
		AtomicAddCommand command = new AtomicAddCommand();
		//
		// set child in AtomicAddCommand
		command.setChild((AbstractModelElement) child.getModel());
		//
		// set parent in AtomicAddCommand
		command.setParent((AbstractModelElement) getHost().getModel());
		//
		// return command
		return command;
	}

	/**
	 * Command used when a child has been moved. Does nothing.
	 */
	@Override
	protected Command createMoveChildCommand(EditPart child, EditPart after) {
		return null;
	}

	/**
	 * Returns the command to be used after a new element has been created.
	 */
	@Override
	protected Command getCreateCommand(CreateRequest request) {
		// get visibility of the element to be added; transmitted by
		// DiagramEditorPaletteFactory
		String visibility = request.getNewObjectType().toString();

		// create a new AtomicCreateCommand with the given visibility
		AtomicCreateCommand command = new AtomicCreateCommand(null);

		// set child in command
		command.setChild((AbstractModelElement) request.getNewObject());
		// set parent in command
		command.setParent((AbstractModelElement) getHost().getModel());

		// return AtomicCreateCommand
		return command;
	}

	/**
	 * Returns the command used for deleting a dependant element. Does nothing.
	 */
	@Override
	protected Command getDeleteDependantCommand(Request request) {
		return null;
	}

	/**
	 * Returns the command used to get orphan children. Does nothing.
	 */
	@Override
	protected Command getOrphanChildrenCommand(Request request) {
		return null;
	}

	/**
	 * Decide if a figures Layout is horizontal or not.
	 */
	@Override
	protected boolean isHorizontal() {
		IFigure figure = ((GraphicalEditPart) getHost()).getContentPane();
		return ((ToolbarLayout) figure.getLayoutManager()).isHorizontal();
	}
}
