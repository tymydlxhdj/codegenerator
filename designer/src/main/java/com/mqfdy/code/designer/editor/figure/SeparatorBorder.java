package com.mqfdy.code.designer.editor.figure;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.Rectangle;

/**
 * 边框 Border that consists only of a line in top of the figure.
 * 
 * @author mqfdy
 * 
 */
public class SeparatorBorder extends MarginBorder {

	/**
	 * Constructor of a new SeperatorBorder.
	 * 
	 */
	public SeparatorBorder() {
		// call constructor of MarginBorder
		super(5, 0, 3, 0);
	}

	/**
	 * Paint or Repaint the SeperatorBorder.
	 * 
	 * @param figure
	 *            the figure above the SeperatorBorder
	 * @param graphics
	 *            the figure to be drawn on
	 * @param insets
	 *            the insets to be used
	 */
	@Override
	public void paint(IFigure figure, Graphics graphics, Insets insets) {
		// get rectangle surrending the figure
		Rectangle rec = getPaintRectangle(figure, insets);

		// draw the border line
		graphics.drawLine(rec.x, rec.y, rec.right(), rec.y);

	}

}
