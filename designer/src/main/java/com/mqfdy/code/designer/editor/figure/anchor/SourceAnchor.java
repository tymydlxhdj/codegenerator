package com.mqfdy.code.designer.editor.figure.anchor;

import org.eclipse.draw2d.AbstractConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;

/**
 * 关联关系连线的起点
 * 
 * @author mqfdy
 * 
 */
public class SourceAnchor extends AbstractConnectionAnchor {

	public SourceAnchor(IFigure source) {
		super(source);
	}

	public Point getLocation(Point reference) {

		Rectangle r = getOwner().getBounds().getCopy();
		getOwner().translateToAbsolute(r);
		if (r.contains(reference) || r.x < reference.x) {
			return r.getRight();
		} else {
			return r.getLeft();
		}
	}
}
