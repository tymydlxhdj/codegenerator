package com.mqfdy.code.designer.editor.commands;

import org.eclipse.draw2d.geometry.Point;

import com.mqfdy.code.designer.editor.part.extensions.ConnectionBendpoint;

// TODO: Auto-generated Javadoc
/**
 * The Class CreateBendpointCommand.
 *
 * @author mqfdy
 */
public class CreateBendpointCommand extends BendpointCommand {

	/** The point. */
	private Point point;

	/**
	 * 
	 */
	public void execute() {
		ConnectionBendpoint cbp = new ConnectionBendpoint(point);
		cbp.setRelativeDimensions(d1, d2);
		connection.addBendpoint(index, cbp, point);
	}

	/**
	 * 
	 */
	public void undo() {
		connection.removeBendpoint(index);
	}

	/**
	 * Sets the point.
	 *
	 * @author mqfdy
	 * @param p
	 *            the new point
	 * @Date 2018-09-03 09:00
	 */
	public void setPoint(Point p) {
		this.point = p;
	}

}
