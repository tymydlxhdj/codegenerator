package com.mqfdy.code.designer.editor.figure.anchor;

import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;

// TODO: Auto-generated Javadoc
/**
 * AbstractBorderAnchor 边缘锚点抽象类.
 *
 * @author mqfdy
 */
public abstract class BorderAnchor extends ChopboxAnchor {
	
	/** The angle. */
	protected double angle;

	/**
	 * Instantiates a new border anchor.
	 *
	 * @param figure
	 *            the figure
	 */
	public BorderAnchor(IFigure figure) {
		super(figure);
		angle = Double.MAX_VALUE;
	}

	/**
	 * Gets the border point.
	 *
	 * @author mqfdy
	 * @param reference
	 *            the reference
	 * @return the border point
	 * @Date 2018-09-03 09:00
	 */
	public abstract Point getBorderPoint(Point reference);

	/**
	 * Gets the location.
	 *
	 * @author mqfdy
	 * @param reference
	 *            the reference
	 * @return the location
	 * @Date 2018-09-03 09:00
	 */
	@Override
	public Point getLocation(Point reference) {
		if (angle == Double.MAX_VALUE) {
			return super.getLocation(reference);
		} else {
			return getBorderPoint(reference);
		}
	}

	/**
	 * Gets the angle.
	 *
	 * @author mqfdy
	 * @return the angle
	 * @Date 2018-09-03 09:00
	 */
	public double getAngle() {
		return angle;
	}

	/**
	 * Sets the angle.
	 *
	 * @author mqfdy
	 * @param angle
	 *            the new angle
	 * @Date 2018-09-03 09:00
	 */
	public void setAngle(double angle) {
		this.angle = angle;
	}
}
