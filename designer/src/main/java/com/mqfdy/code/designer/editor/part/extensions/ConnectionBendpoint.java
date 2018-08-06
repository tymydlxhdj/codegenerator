package com.mqfdy.code.designer.editor.part.extensions;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.jdom2.Element;

public class ConnectionBendpoint extends Element {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private float weight = 0.5f;
	private Dimension d1, d2;
	private Point point;

	public ConnectionBendpoint(Point p) {
		this.setPoint(p);
	}

	public Dimension getFirstRelativeDimension() {
		return d1;
	}

	public Dimension getSecondRelativeDimension() {
		return d2;
	}

	public float getWeight() {
		return weight;
	}

	public void setRelativeDimensions(Dimension dim1, Dimension dim2) {
		d1 = dim1;
		d2 = dim2;
	}

	public void setWeight(float w) {
		weight = w;
	}

	public Point getPoint() {
		return point;
	}

	public void setPoint(Point point) {
		this.point = point;
	}

}
