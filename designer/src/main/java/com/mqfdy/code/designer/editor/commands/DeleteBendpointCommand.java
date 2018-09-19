package com.mqfdy.code.designer.editor.commands;

import org.eclipse.draw2d.geometry.Point;

import com.mqfdy.code.designer.editor.part.extensions.ConnectionBendpoint;

// TODO: Auto-generated Javadoc
/**
 * The Class DeleteBendpointCommand.
 *
 * @author mqfdy
 */
public class DeleteBendpointCommand extends BendpointCommand {
	
	/** The deleted bendpoint. */
	private ConnectionBendpoint deletedBendpoint;

	/**
	 * 
	 */
	public void execute() {
		deletedBendpoint = (ConnectionBendpoint) connection.getBendpoints()
				.get(index);
		connection.removeBendpoint(index);
	}

	/**
	 * 
	 */
	public void undo() {
		connection.addBendpoint(index, deletedBendpoint, new Point());
	}

}
