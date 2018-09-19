package com.mqfdy.code.designer.editor.figure;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;

import com.mqfdy.code.designer.models.ImageKeys;
import com.mqfdy.code.designer.models.ImageManager;
import com.mqfdy.code.model.BusinessClass;
import com.mqfdy.code.model.BusinessOperation;
import com.mqfdy.code.model.PKProperty;
import com.mqfdy.code.model.Property;
import com.mqfdy.code.model.ReferenceObject;

// TODO: Auto-generated Javadoc
/**
 * The Class ReferenceObjectFigure.
 *
 * @author mqfdy
 * @title:businessClass对象的界面
 * @description:
 */

public class ReferenceObjectFigure extends NodeFigure {
	
	/** The name label. */
	protected Label nameLabel;
	
	/** The pros figure. */
	// protected Color bgColor;
	protected Figure prosFigure;
	
	/** The opers figure. */
	protected Figure opersFigure;

	/**
	 * Instantiates a new reference object figure.
	 *
	 * @param name
	 *            the name
	 * @param businessClass
	 *            the business class
	 */
	public ReferenceObjectFigure(String name, ReferenceObject businessClass) {

		// super();
		setBorder(new LineBorder());
		ToolbarLayout layout = new ToolbarLayout();
		// bgColor = new Color(null, 121, 111, 111);
		setLayoutManager(layout);

		// setBackgroundColor(ColorConstants.lightBlue);
		setOpaque(true);
		paintFigure(name, businessClass);
	}

	/**
	 * Paint figure.
	 *
	 * @author mqfdy
	 * @param g
	 *            the g
	 * @Date 2018-09-03 09:00
	 */
	public void paintFigure(Graphics g) {
		super.paintFigure(g);
		Color oldForeground = g.getForegroundColor();
		Color oldBackground = g.getBackgroundColor();
		g.setForegroundColor(ColorConstants.white);
		g.setBackgroundColor(ColorConstants.lightGray);
		g.fillGradient(bounds, true);
		g.setForegroundColor(oldForeground);
		g.setBackgroundColor(oldBackground);
	}

	/**
	 * Paint figure.
	 *
	 * @author mqfdy
	 * @param name
	 *            the name
	 * @param businessClass
	 *            the business class
	 * @Date 2018-09-03 09:00
	 */
	public void paintFigure(String name, ReferenceObject businessClass) {
		Label bu = new Label("<<reference>>");
		// bu.setFont(JFaceResources.getFontRegistry().getBold(
		// JFaceResources.DEFAULT_FONT));
		add(bu);
		nameLabel = new Label(name);
		// 把名称设置成黑体
		nameLabel.setFont(JFaceResources.getFontRegistry().getBold(
				JFaceResources.DEFAULT_FONT));
		Image icon;
		icon = ImageManager.getInstance().getImage(
				ImageKeys.IMG_MODEL_TYPE_REFERENCEOBJECT);
		nameLabel.setIcon(icon);
		prosFigure = new Figure();
		ToolbarLayout attributesLayout = new ToolbarLayout();
		attributesLayout.setMinorAlignment(ToolbarLayout.ALIGN_TOPLEFT);
		prosFigure.setLayoutManager(attributesLayout);
		prosFigure.setBorder(new SeparatorBorder());
		int length = 0;
		add(nameLabel);
		if (businessClass.getReferenceObject() instanceof BusinessClass) {
			BusinessClass buc = (BusinessClass) businessClass
					.getReferenceObject();
			for (Property pro : buc.getProperties()) {
				String proName = pro.getName();
				if (pro instanceof PKProperty)
					proName = proName + "<<PK>>";
				if (length < proName.length()) {
					length = proName.length();
				}
			}
			for (Property pro : buc.getProperties()) {
				ColumnFigure c = new ColumnFigure();
				String proName = pro.getName();
				if (pro instanceof PKProperty)
					proName = proName + "<<PK>>";
				if (proName.length() < length) {
					proName = String.format("%1$-" + length + "s", proName);
				}
				c.fillAttribute("-" + proName + " : " + pro.getDataType(), null);
				prosFigure.add(c);
			}

			// setForegroundColor(new Color(null, 121, 111, 111));
			// setBackgroundColor(bgColor);
			add(prosFigure);

			if (buc.getOperations() != null && buc.getOperations().size() > 0) {
				opersFigure = new Figure();
				ToolbarLayout attributesLayout1 = new ToolbarLayout();
				attributesLayout1
						.setMinorAlignment(ToolbarLayout.ALIGN_TOPLEFT);
				opersFigure.setLayoutManager(attributesLayout1);
				opersFigure.setBorder(new SeparatorBorder());
				for (BusinessOperation pro : buc.getOperations()) {
					ColumnFigure c = new ColumnFigure();
					if (c != null) {
						
						c.fillAttribute(" " + pro.getName() + "()", null);
						c.setForegroundColor(new Color(null,11, 111, 111));
					}
					opersFigure.add(c);
				}
				add(opersFigure);
			}
		}
	}

	/**
	 * Set the name of the class or interface.
	 *
	 * @author mqfdy
	 * @param newName
	 *            The new name.
	 * @Date 2018-09-03 09:00
	 */
	public void setName(String newName) {
		if (nameLabel == null) {
			nameLabel = new Label(newName);
			add(nameLabel);
		} else {
			nameLabel.setText(newName);
		}
	}

	/**
	 * Return the name of class or interface.
	 *
	 * @author mqfdy
	 * @return the text of the nameLabel
	 * @Date 2018-09-03 09:00
	 */
	public String getName() {
		return nameLabel.getText();
	}

	/**
	 * Adds a new Figure with the given constraints to the TableFigure.
	 *
	 * @author mqfdy
	 * @param figure
	 *            the figure to be added
	 * @param constraint
	 *            the constraint used on the figure
	 * @param index
	 *            index of insertion
	 * @Date 2018-09-03 09:00
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
	 * @author mqfdy
	 * @param figure
	 *            the figure to be removed
	 * @Date 2018-09-03 09:00
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
	 * @author mqfdy
	 * @return Returns the nameLabel.
	 * @Date 2018-09-03 09:00
	 */
	public Label getNameLabel() {
		return nameLabel;
	}

	/**
	 * 
	 */
	@Override
	public void emptyFigure() {

	}
}
