package com.mqfdy.code.designer.editor.figure;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;

import com.mqfdy.code.designer.models.ImageKeys;
import com.mqfdy.code.designer.models.ImageManager;
import com.mqfdy.code.model.EnumElement;
import com.mqfdy.code.model.Enumeration;

/**
 * @title:枚举对象的界面
 * @description:
 * @author mqfdy
 */

public class EnumerationFigure extends NodeFigure {
	protected Label nameLabel;
	// protected Color bgColor;
	protected Figure columnsFigure;

	public EnumerationFigure(String name, Enumeration ele) {

		super();
		ToolbarLayout layout = new ToolbarLayout();
		setLayoutManager(layout);
		bgColor = new Color(null,11, 111, 111);
		// setBackgroundColor(ColorConstants.lightGray);
		setOpaque(true);
		paintFigure(name, ele);
	}

	public void paintFigure(Graphics g) {
		super.paintFigure(g);
		Color oldForeground = g.getForegroundColor();
		Color oldBackground = g.getBackgroundColor();
		if (g != null) {
			
			g.setForegroundColor(ColorConstants.white);
		g.setBackgroundColor(new Color(null,120, 205, 192));
		
		g.fillGradient(bounds, true);
		g.setForegroundColor(oldForeground);
		g.setBackgroundColor(oldBackground);
		}
	}

	/**
	 * Set the name of the class or interface.
	 * 
	 * @param newName
	 *            The new name.
	 */
	public void setName(String newName) {
		if (nameLabel == null) {
			nameLabel = new Label(newName);
			add(nameLabel);
		} else {
			nameLabel.setText(newName);
		}
	}

	public void paintFigure(String name, Enumeration ele) {
		nameLabel = new Label(name);
		// 把名称设置成黑体
		nameLabel.setFont(JFaceResources.getFontRegistry().getBold(
				JFaceResources.DEFAULT_FONT));
		Image icon;
		icon = ImageManager.getInstance().getImage(
				ImageKeys.IMG_MODEL_TYPE_ENUMERATION);
		nameLabel.setIcon(icon);

		columnsFigure = new Figure();
		ToolbarLayout attributesLayout = new ToolbarLayout();
		attributesLayout.setMinorAlignment(ToolbarLayout.ALIGN_TOPLEFT);
		columnsFigure.setLayoutManager(attributesLayout);
		columnsFigure.setBorder(new SeparatorBorder());
		// for(EnumElement pro : ele.getChildren()){
		// ColumnFigure c = new ColumnFigure();
		// c.fillAttribute(" "+pro.getKey()+":"+pro.getValue(), null);
		// columnsFigure.add(c);
		// }
		int length = 0;
		for (EnumElement pro : ele.getChildren()) {
			if (length < pro.getKey().length()) {
				length = pro.getKey().length();
			}
		}
		for (EnumElement pro : ele.getChildren()) {
			ColumnFigure c = new ColumnFigure();
			String proName = pro.getKey();
			if (proName.length() < length) {
				proName = String.format("%1$-" + length + "s", proName);
			}
			c.fillAttribute(" " + proName + " : " + pro.getValue(), null);
			columnsFigure.add(c);
		}

		setForegroundColor(bgColor);
		// setBackgroundColor(bgColor);
		add(nameLabel);
		add(columnsFigure);
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
