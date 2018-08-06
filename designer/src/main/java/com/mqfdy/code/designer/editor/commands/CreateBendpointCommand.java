package com.mqfdy.code.designer.editor.commands;

import org.eclipse.draw2d.geometry.Point;

import com.mqfdy.code.designer.editor.part.extensions.ConnectionBendpoint;

public class CreateBendpointCommand extends BendpointCommand {

	private Point point;

	public void execute() {
		ConnectionBendpoint cbp = new ConnectionBendpoint(point);
		cbp.setRelativeDimensions(d1, d2);
		connection.addBendpoint(index, cbp, point);
	}

	public void undo() {
		connection.removeBendpoint(index);
	}

	public void setPoint(Point p) {
		this.point = p;
	}

}
