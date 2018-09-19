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
import com.mqfdy.code.model.IModelElement;
import com.mqfdy.code.model.PKProperty;
import com.mqfdy.code.model.Property;

// TODO: Auto-generated Javadoc
/**
 * The Class BusinessClassFigure.
 *
 * @author mqfdy
 * @title:businessClass对象的界面
 * @description:
 */

public class BusinessClassFigure extends NodeFigure {
	
	/** The x length. */
	private int xLength = 0;
	
	/** The y length. */
	private int yLength = 0;
	
	/** The name label. */
	protected Label nameLabel;
	
	/** The pros figure. */
	// protected Color bgColor;
	protected Figure prosFigure;
	
	/** The opers figure. */
	protected Figure opersFigure;
	
	/** The business class. */
	private BusinessClass businessClass;

	/**
	 * Instantiates a new business class figure.
	 *
	 * @param name
	 *            the name
	 * @param businessClass
	 *            the business class
	 */
	public BusinessClassFigure(String name, BusinessClass businessClass) {

		// super();
		setBorder(new LineBorder());
		ToolbarLayout layout = new ToolbarLayout();
		// bgColor = new Color(null, 121, 111, 111);
		setLayoutManager(layout);
		// setBackgroundColor(ColorConstants.lightBlue);
		setOpaque(true);
		this.businessClass = businessClass;
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
		if (g != null) {
			
		Color oldForeground = g.getForegroundColor();
		Color oldBackground = g.getBackgroundColor();
		g.setForegroundColor(ColorConstants.white);
		g.setBackgroundColor(new Color(null,201, 237, 255));
		
		if (IModelElement.STEREOTYPE_BUILDIN.equals(
						businessClass.getStereotype())) {
			g.setBackgroundColor(ColorConstants.lightGray);
		}/*else if (businessClass.getStereotype() != null
				&& businessClass.getStereotype().equals(
						IModelElement.STEREOTYPE_REVERSE)) {
			g.setBackgroundColor(new Color(null, 225, 208, 193));
		}*/
		g.fillGradient(bounds, true);
		g.setForegroundColor(oldForeground);
		g.setBackgroundColor(oldBackground);
		}
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
	public void paintFigure(String name, BusinessClass businessClass) {
		// 内置模型
		Label bu = new Label("<<built-in>>");
		// 数据库反向导入模型
		Label db = new Label("<<db-reverse>>");
		// bu.setFont(JFaceResources.getFontRegistry().get(
		// JFaceResources.DEFAULT_FONT));
		if (IModelElement.STEREOTYPE_BUILDIN.equals(
						businessClass.getStereotype())) {
			add(bu);
		}else if (IModelElement.STEREOTYPE_REVERSE.equals(
						businessClass.getStereotype())) {
			add(db);
		}
		nameLabel = new Label(name);
		// 把名称设置成黑体
		nameLabel.setFont(JFaceResources.getFontRegistry().getBold(
				JFaceResources.DEFAULT_FONT));
		Image icon;
		icon = ImageManager.getInstance().getImage(
				ImageKeys.IMG_MODEL_TYPE_BUSINESSCLASS);
		if(IModelElement.STEREOTYPE_REVERSE.equals(businessClass.getStereotype())){
			icon = ImageManager.getInstance().getImage(
					ImageKeys.IMG_MODEL_TYPE_BUSINESSCLASS_DB);
		}
		nameLabel.setIcon(icon);
		prosFigure = new Figure();
		ToolbarLayout attributesLayout = new ToolbarLayout();
		attributesLayout.setMinorAlignment(ToolbarLayout.ALIGN_TOPLEFT);
		prosFigure.setLayoutManager(attributesLayout);
		prosFigure.setBorder(new SeparatorBorder());
		for (Property pro : businessClass.getProperties()) {
			yLength++;
			String proName = pro.getName();
			if (pro instanceof PKProperty && ((PKProperty) pro).isPrimaryKey())
				proName = proName + "<<PK>>";
			if (xLength < proName.length()) {
				xLength = proName.length();
			}
		}
		for (Property pro : businessClass.getProperties()) {
			ColumnFigure c = new ColumnFigure();
			String proName = pro.getName();
			if (pro instanceof PKProperty && ((PKProperty) pro).isPrimaryKey())
				proName = proName + "<<PK>>";
			if (proName.length() < xLength) {
				proName = String.format("%1$-" + xLength + "s", proName);
			}
			c.fillAttribute("-" + proName + " : " + pro.getDataType(), null);
			prosFigure.add(c);
		}

		// setForegroundColor(new Color(null, 121, 111, 111));
		// setBackgroundColor(bgColor);
		add(nameLabel);
		add(prosFigure);

		if (businessClass.getOperations() != null
				&& businessClass.getOperations().size() > 0) {
			opersFigure = new Figure();
			ToolbarLayout attributesLayout1 = new ToolbarLayout();
			attributesLayout1.setMinorAlignment(ToolbarLayout.ALIGN_TOPLEFT);
			opersFigure.setLayoutManager(attributesLayout1);
			opersFigure.setBorder(new SeparatorBorder());
			for (BusinessOperation pro : businessClass.getOperations()) {
				yLength++;
				ColumnFigure c = new ColumnFigure();
				c.fillAttribute(" " + pro.getName() + "()", null);
				c.setForegroundColor(new Color(null,11, 111, 111));
				opersFigure.add(c);
			}
			add(opersFigure);
		}
		setSize(xLength, yLength);
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
