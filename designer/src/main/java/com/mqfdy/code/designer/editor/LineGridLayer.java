package com.mqfdy.code.designer.editor;

import org.eclipse.draw2d.FigureUtilities;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.SnapToGrid;
import org.eclipse.swt.graphics.Color;

/**
 * 重载org.eclipse.gef.editparts.GridLayer，改变线条颜色
 * 
 * @author mqfdy
 * 
 */
public class LineGridLayer extends org.eclipse.gef.editparts.GridLayer {

	/**
	 * Field indicating the horizontal grid spacing
	 */
	// protected int gridX = SnapToGrid.DEFAULT_GRID_SIZE;
	/**
	 * Field for the vertical grid spacing
	 */
	// protected int gridY = SnapToGrid.DEFAULT_GRID_SIZE;

	/**
	 * Field indicating what the grid origin is. This is used simply to
	 * determine the offset from 0,0.
	 */
	// protected Point origin = new Point();

	/**
	 * Constructor Sets the default grid color: ColorConstants.lightGray
	 */
	public LineGridLayer() {
		super();
		setForegroundColor(new Color(null,234, 234, 234));
		// setForegroundColor(ColorConstants.lightGray);
	}

	/**
	 * @see org.eclipse.draw2d.Figure#paintFigure(org.eclipse.draw2d.Graphics)
	 */
	protected void paintFigure(Graphics graphics) {
		super.paintFigure(graphics);
		paintGrid(graphics);
	}

	/**
	 * Paints the grid. Sub-classes can override to customize the grid's look.
	 * If this layer is being used with SnapToGrid, this method will only be
	 * invoked when the {@link SnapToGrid#PROPERTY_GRID_VISIBLE visibility}
	 * property is set to true.
	 * 
	 * @param g
	 *            The Graphics object to be used to do the painting
	 * @see FigureUtilities#paintGrid(Graphics, IFigure, Point, int, int)
	 */
	// http://nuoda.iteye.com/blog/216619
	protected void paintGrid(Graphics g) {
		paintGrid(g, this, origin, gridX, gridY);
	}

	public static void paintGrid(Graphics g, IFigure f,
			org.eclipse.draw2d.geometry.Point origin, int distanceX,
			int distanceY) {
		Rectangle clip = g.getClip(Rectangle.SINGLETON);

		if (distanceX > 0) {
			if (origin.x >= clip.x) {
				while (origin.x - distanceX >= clip.x) {
					origin.x -= distanceX;
				}
			} else {
				while (origin.x < clip.x) {
					origin.x += distanceX;
				}
			}
			for (int i = origin.x; i < clip.x + clip.width; i += distanceX) {
				// g.setLineStyle(Graphics.LINE_DOT);
				g.drawLine(i, clip.y, i, clip.y + clip.height);
			}
		}

		if (distanceY > 0) {
			if (origin.y >= clip.y) {
				while (origin.y - distanceY >= clip.y) {
					origin.y -= distanceY;
				}
			} else {
				while (origin.y < clip.y) {
					origin.y += distanceY;
				}
			}
			for (int i = origin.y; i < clip.y + clip.height; i += distanceY) {
				// g.setLineStyle(Graphics.LINE_SOLID);
				g.drawLine(clip.x, i, clip.x + clip.width, i);
			}
		}
	}
}
