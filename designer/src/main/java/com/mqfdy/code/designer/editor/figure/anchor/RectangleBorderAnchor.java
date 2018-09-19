package com.mqfdy.code.designer.editor.figure.anchor;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PrecisionPoint;
import org.eclipse.draw2d.geometry.Rectangle;

// TODO: Auto-generated Javadoc
/**
 * RectangleBorderAnchor 方形锚点计算.
 *
 * @author mqfdy
 * @version 1.0 Date: 2011-11-22 下午02:13:03
 */
public class RectangleBorderAnchor extends BorderAnchor {
	
	/** The place. */
	public Point place;

	/**
	 * Instantiates a new rectangle border anchor.
	 *
	 * @param figure
	 *            the figure
	 */
	public RectangleBorderAnchor(IFigure figure) {
		super(figure);
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
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.com.evada.ide.gef.anchor.AbstractBorderAnchor#getBorderPoint(org.eclipse
	 * .draw2d.geometry.Point)
	 */
	@Override
	public Point getBorderPoint(Point reference) {

		// 得到owner矩形，转换为绝对坐标
		Rectangle r = Rectangle.SINGLETON;
		r.setBounds(getOwner().getBounds());
		getOwner().translateToAbsolute(r);
		// 根据角度，计算锚点相对于owner中心点的偏移
		double dx , dy;
		double tan = Math.atan2(r.height, r.width);
		if (angle >= -tan && angle <= tan) {
			dx = r.width >> 1;
			dy = dx * Math.tan(angle);
		} else if (angle >= tan && angle <= Math.PI - tan) {
			dy = r.height >> 1;
			dx = dy / Math.tan(angle);
		} else if (angle <= -tan && angle >= tan - Math.PI) {
			dy = -(r.height >> 1);
			dx = dy / Math.tan(angle);
		} else {
			dx = -(r.width >> 1);
			dy = dx * Math.tan(angle);
		}
		// 得到长方形中心点，加上偏移，得到最终锚点坐标
		PrecisionPoint pp = new PrecisionPoint(r.getCenter());

		pp.translate((int) dx, (int) dy);
		return new Point(pp);
	}

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
			return getLocation1(reference);
		} else {
			return getBorderPoint(reference);
		}
	}

	/**
	 * Gets the location 1.
	 *
	 * @author mqfdy
	 * @param reference
	 *            the reference
	 * @return the location 1
	 * @Date 2018-09-03 09:00
	 */
	public Point getLocation1(Point reference) {
		if (place != null) {
			// 得到owner矩形，转换为绝对坐标
			Rectangle r = Rectangle.SINGLETON;
			r.setBounds(getOwner().getBounds());
			getOwner().translateToAbsolute(r);
			// 得到长方形中心点，加上偏移，得到最终锚点坐标
			PrecisionPoint pp = new PrecisionPoint(r.getCenter());

			pp.translate((int) 0, (int) -r.height / 2);
			return new Point(pp);
		}
		Rectangle r = Rectangle.SINGLETON;
		r.setBounds(getBox());
		r.translate(-1, -1);
		r.resize(1, 1);

		getOwner().translateToAbsolute(r);
		float centerX = r.x + 0.5f * r.width;
		float centerY = r.y + 0.5f * r.height;

		if (r.isEmpty()
				|| (reference.x == (int) centerX && reference.y == (int) centerY))
			return new Point((int) centerX, (int) centerY); // This avoids
															// divide-by-zero

		float dx = reference.x - centerX;
		float dy = reference.y - centerY;

		// r.width, r.height, dx, and dy are guaranteed to be non-zero.
		float scale = 0.5f / Math.max(Math.abs(dx) / r.width, Math.abs(dy)
				/ r.height);

		dx *= scale;
		dy *= scale;
		centerX += dx;
		centerY += dy;
		return new Point(Math.round(centerX), Math.round(centerY));
	}
}
