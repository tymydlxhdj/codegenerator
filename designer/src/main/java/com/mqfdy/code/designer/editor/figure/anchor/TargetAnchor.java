package com.mqfdy.code.designer.editor.figure.anchor;

import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PrecisionPoint;
import org.eclipse.draw2d.geometry.Rectangle;

// TODO: Auto-generated Javadoc
/**
 * 关联关系的起点.
 *
 * @author mqfdy
 */
public class TargetAnchor extends ChopboxAnchor {

	/** The source. */
	private IFigure source;

	/** The place. */
	public Point place;

	/**
	 * Instantiates a new target anchor.
	 *
	 * @param source
	 *            the source
	 */
	public TargetAnchor(IFigure source) {
		super(source);
		this.source = source;
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
	public Point getLocation(Point reference) {
		if (place != null) {
			// 得到owner矩形，转换为绝对坐标
			Rectangle r = Rectangle.SINGLETON;
			r.setBounds(getOwner().getBounds());
			getOwner().translateToAbsolute(r);
			// 得到长方形中心点，加上偏移，得到最终锚点坐标
			PrecisionPoint pp = new PrecisionPoint(r.getCenter());

			pp.translate((int) 0, (int) -r.height / 2);
			return new Point(pp);
		} else {
			Rectangle r = getOwner().getBounds().getCopy();
			getOwner().translateToAbsolute(r);
			if (r.contains(reference) || r.x < reference.x) {
				return r.getRight();
			} else {
				return r.getLeft();
			}
		}
	}
}
