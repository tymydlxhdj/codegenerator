package com.mqfdy.code.designer.editor.commands;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;

import com.mqfdy.code.designer.editor.part.extensions.ConnectionBendpoint;

public class MoveBendpointCommand extends BendpointCommand {
	private Dimension oldDim1;
	private Dimension oldDim2;
	private Point point;

	private Point oldPoint;

	public MoveBendpointCommand(Point point2) {
		super();
		this.oldPoint = point2;
	}

	public void setOldRelativeDimensions(Dimension d1, Dimension d2) {
		this.oldDim1 = d1;
		this.oldDim2 = d2;
	}

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

	public void undo() {
		if (connection.getBendpoints().size() == 0)
			return;
		ConnectionBendpoint cbp = (ConnectionBendpoint) connection
				.getBendpoints().get(index);
		cbp.setRelativeDimensions(oldDim1, oldDim2);
		connection.setBendpointRelativeDimensions(index, oldDim1, oldDim2,
				oldPoint);
	}

	public void setPoint(Point p) {
		this.point = p;
	}

}
