package com.mqfdy.code.designer.editor.figure;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.Polygon;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.swt.graphics.Color;

import com.mqfdy.code.model.Annotation;

/**
 * @title:Annotation对象的界面
 * @description:
 * @author mqfdy
 */

public class AnnotationFigure extends NodeFigure {
	protected Label nameLabel;
	// protected Color bgColor;
	private Annotation annotation;

	public AnnotationFigure(Annotation annotation) {

		// super();
		setBorder(new LineBorder());
		ToolbarLayout layout = new ToolbarLayout();
		// bgColor = new Color(null, 121, 111, 111);
		setLayoutManager(layout);
		setForegroundColor(null);
		
//		setOpaque(false);//背景透明
		this.annotation = annotation;
		paintFigure(annotation);
	}

	@Override
	protected void paintBorder(Graphics graphics) {
		// TODO Auto-generated method stub
		if (getBorder() != null){
			getBorder().paint(this, graphics, NO_INSETS);
		}
	}
	private PointList fillPoints1(Polygon figure)
    {
		figure.getPoints().removeAllPoints();
        int x = getBounds().x;
        int y = getBounds().y;
        int w = getBounds().width;
        int h = getBounds().height;
        figure.getPoints().addPoint(x, y);
        figure.getPoints().addPoint(x+w-10, y);
        figure.getPoints().addPoint(x+w-1, y+10);
        
//        figure.getPoints().addPoint(x+w-10, y + 10);
//        figure.getPoints().addPoint(x+w-10, y);
//        figure.getPoints().addPoint(x+w-1, y+10);
        
        figure.getPoints().addPoint(x+w-1, y+h-1);
        figure.getPoints().addPoint(x, y+h-1);
        return figure.getPoints();
    }
	private PointList fillPoints(Polygon figure)
    {
		figure.getPoints().removeAllPoints();
        int x = getBounds().x;
        int y = getBounds().y;
        int w = getBounds().width;
        int h = getBounds().height;
        figure.getPoints().addPoint(x, y);
        figure.getPoints().addPoint(x+w-10, y);
        figure.getPoints().addPoint(x+w-1, y+10);
        
        figure.getPoints().addPoint(x+w-10, y + 10);
        figure.getPoints().addPoint(x+w-10, y);
        figure.getPoints().addPoint(x+w-1, y+10);
        
        figure.getPoints().addPoint(x+w-1, y+h-1);
        figure.getPoints().addPoint(x, y+h-1);
        return figure.getPoints();
    }
	public void paintFigure(Graphics g) {
//		super.paintFigure(g);
		Color oldForeground = g.getForegroundColor();
		Color oldBackground = g.getBackgroundColor();
		g.setForegroundColor(ColorConstants.white);
//		g.setBackgroundColor(new Color(null, 211, 120, 155));
//		g.fillGradient(bounds, true);
		g.fillPolygon(fillPoints1(new Polygon()));
		g.setForegroundColor(oldForeground);
		g.setBackgroundColor(oldBackground);
		PointList points = fillPoints(new Polygon());
		g.drawPolygon(points);
		
		
	}

	public void paintFigure(Annotation annotation) {
		nameLabel = new Label("");
		nameLabel.setVisible(false);
//		nameLabel.setIconAlignment(PositionConstants.RIGHT);
//		// 把名称设置成黑体
//		nameLabel.setFont(JFaceResources.getFontRegistry().getBold(
//				JFaceResources.DEFAULT_FONT));
//		Image icon;
//		icon = ImageManager.getInstance().getImage(
//				ImageKeys.IMG_MODEL_TYPE_ANNOTATION);
//		nameLabel.setIcon(icon);
		// setForegroundColor(new Color(null, 121, 111, 111));
		// setBackgroundColor(bgColor);
		add(nameLabel);
		add(new AnnotationLabel(annotation.getContent()));
//		setSize(xLength, yLength);
	}

	/**
	 * Return the name of class or interface
	 * 
	 * @return the text of the nameLabel
	 */
	public String getName() {
		return nameLabel.getText();
	}

	/**
	 * Adds a new Figure with the given constraints to the TableFigure
	 * 
	 * @param figure
	 *            the figure to be added
	 * @param constraint
	 *            the constraint used on the figure
	 * @param index
	 *            index of insertion
	 */
	@Override
	public void add(IFigure figure, Object constraint, int index) {
		/*
		 * if (figure instanceof ColumnFigure) { // set index to -1 because
		 * editparts thinks we only have one figure inside
		 * columnsFigure.add(figure, constraint, -1); } else {
		 */
		super.add(figure, constraint, index);
		// }
	}

	/**
	 * Remove a subfigure from its parent. If subfigure is ColumnFigure
	 * remove-action is performed here. Otherwise the remove action is performed
	 * in NodeFigure.
	 * 
	 * @param figure
	 *            the figure to be removed
	 */
	@Override
	public void remove(IFigure figure) {
		// if (figure instanceof ColumnFigure) {
		// columnsFigure.remove(figure);
		// }
		// else {
		super.remove(figure);
		// }
	}

	/**
	 * Return nameLabel of the TableFigure.
	 * 
	 * @return Returns the nameLabel.
	 */
	public Label getNameLabel() {
		return nameLabel;
	}

	@Override
	public void emptyFigure() {

	}
}
