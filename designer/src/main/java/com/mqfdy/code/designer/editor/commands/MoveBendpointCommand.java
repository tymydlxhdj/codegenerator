package com.mqfdy.code.designer.editor.commands;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;

import com.mqfdy.code.designer.editor.part.extensions.ConnectionBendpoint;

// TODO: Auto-generated Javadoc
/**
 * The Class MoveBendpointCommand.
 *
 * @author mqfdy
 */
public class MoveBendpointCommand extends BendpointCommand {
	
	/** The old dim 1. */
	private Dimension oldDim1;
	
	/** The old dim 2. */
	private Dimension oldDim2;
	
	/** The point. */
	private Point point;

	/** The old point. */
	private Point oldPoint;

	/**
	 * Instantiates a new move bendpoint command.
	 *
	 * @param point2
	 *            the point 2
	 */
	public MoveBendpointCommand(Point point2) {
		super();
		this.oldPoint = point2;
	}

	/**
	 * Sets the old relative dimensions.
	 *
	 * @author mqfdy
	 * @param d1
	 *            the d 1
	 * @param d2
	 *            the d 2
	 * @Date 2018-09-03 09:00
	 */
	public void setOldRelativeDimensions(Dimension d1, Dimension d2) {
		this.oldDim1 = d1;
		this.oldDim2 = d2;
	}

	/**
	 * 
	 */
	public void execute() {
		// Remember old location
		if (connection.getBendpoints().size() == 0)
			return;
		ConnectionBendpoint cbp = (ConnectionBendpoint) connection
				.getBendpoints().get(index);
		setOldRelativeDimensions(cbp.getFirstRelativeDimension(),
				cbp.getSecondRelativeDimension());
		// Set new location
		connection.setBendpointRelativeDimensions(index, d1, d2, point);
	}

	/**
	 * 
	 */
	public void undo() {
		if (connection.getBendpoints().size() == 0)
			return;
		ConnectionBendpoint cbp = (ConnectionBendpoint) connection
				.getBendpoints().get(index);
		cbp.setRelativeDimensions(oldDim1, oldDim2);
		connection.setBendpointRelativeDimensions(index, oldDim1, oldDim2,
				oldPoint);
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
