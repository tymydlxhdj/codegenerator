package com.mqfdy.code.designer.editor.part.extensions;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.jdom2.Element;

// TODO: Auto-generated Javadoc
/**
 * The Class ConnectionBendpoint.
 *
 * @author mqfdy
 */
public class ConnectionBendpoint extends Element {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The weight. */
	private float weight = 0.5f;
	
	/** The d 2. */
	private Dimension d1, d2;
	
	/** The point. */
	private Point point;

	/**
	 * Instantiates a new connection bendpoint.
	 *
	 * @param p
	 *            the p
	 */
	public ConnectionBendpoint(Point p) {
		this.setPoint(p);
	}

	/**
	 * Gets the first relative dimension.
	 *
	 * @author mqfdy
	 * @return the first relative dimension
	 * @Date 2018-09-03 09:00
	 */
	public Dimension getFirstRelativeDimension() {
		return d1;
	}

	/**
	 * Gets the second relative dimension.
	 *
	 * @author mqfdy
	 * @return the second relative dimension
	 * @Date 2018-09-03 09:00
	 */
	public Dimension getSecondRelativeDimension() {
		return d2;
	}

	/**
	 * Gets the weight.
	 *
	 * @author mqfdy
	 * @return the weight
	 * @Date 2018-09-03 09:00
	 */
	public float getWeight() {
		return weight;
	}

	/**
	 * Sets the relative dimensions.
	 *
	 * @author mqfdy
	 * @param dim1
	 *            the dim 1
	 * @param dim2
	 *            the dim 2
	 * @Date 2018-09-03 09:00
	 */
	public void setRelativeDimensions(Dimension dim1, Dimension dim2) {
		d1 = dim1;
		d2 = dim2;
	}

	/**
	 * Sets the weight.
	 *
	 * @author mqfdy
	 * @param w
	 *            the new weight
	 * @Date 2018-09-03 09:00
	 */
	public void setWeight(float w) {
		weight = w;
	}

	/**
	 * Gets the point.
	 *
	 * @author mqfdy
	 * @return the point
	 * @Date 2018-09-03 09:00
	 */
	public Point getPoint() {
		return point;
	}

	/**
	 * Sets the point.
	 *
	 * @author mqfdy
	 * @param point
	 *            the new point
	 * @Date 2018-09-03 09:00
	 */
	public void setPoint(Point point) {
		this.point = point;
	}

}
