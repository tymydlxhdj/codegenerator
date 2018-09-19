package com.mqfdy.code.designer.editor.figure;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.graphics.Image;

import com.mqfdy.code.designer.models.ImageKeys;
import com.mqfdy.code.designer.models.ImageManager;
import com.mqfdy.code.model.DataTransferObject;
import com.mqfdy.code.model.Property;

// TODO: Auto-generated Javadoc
/**
 * The Class DTOFigure.
 *
 * @author mqfdy
 * @title:DTO对象的界面
 * @description:
 */

public class DTOFigure extends NodeFigure {
	
	/** The name label. */
	protected Label nameLabel;

	/** The columns figure. */
	protected Figure columnsFigure;

	/** The dto. */
	protected DataTransferObject dto;

	/**
	 * Instantiates a new DTO figure.
	 *
	 * @param name
	 *            the name
	 * @param dto
	 *            the dto
	 */
	public DTOFigure(String name, DataTransferObject dto) {

		super();
		this.dto = dto;
		ToolbarLayout layout = new ToolbarLayout();
		layout.setMinorAlignment(0);
		setLayoutManager(layout);
		paintFigure(name);
	}

	/**
	 * Paint figure.
	 *
	 * @author mqfdy
	 * @param name
	 *            the name
	 * @Date 2018-09-03 09:00
	 */
	public void paintFigure(String name) {
		nameLabel = new Label(name);
		// 把名称设置成黑体
		nameLabel.setFont(JFaceResources.getFontRegistry().getBold(
				JFaceResources.DEFAULT_FONT));
		Image icon;
		icon = ImageManager.getInstance()
				.getImage(ImageKeys.IMG_MODEL_TYPE_DTO);
		nameLabel.setIcon(icon);

		columnsFigure = new Figure();
		ToolbarLayout attributesLayout = new ToolbarLayout();
		attributesLayout.setMinorAlignment(ToolbarLayout.ALIGN_TOPLEFT);
		columnsFigure.setLayoutManager(attributesLayout);
		columnsFigure.setBorder(new SeparatorBorder());
		// for(Property pro : dto.getProperties()){
		// ColumnFigure c = new ColumnFigure();
		// c.fillAttribute("-"+pro.getDisplayName(), null);
		// columnsFigure.add(c);
		// }
		int length = 0;
		for (Property pro : dto.getProperties()) {
			if (length < pro.getName().length()) {
				length = pro.getName().length();
			}
		}
		for (Property pro : dto.getProperties()) {
			ColumnFigure c = new ColumnFigure();
			String proName = pro.getName();
			if (proName.length() < length) {
				proName = String.format("%1$-" + length + "s", proName);
			}
			c.fillAttribute("-" + proName + " : " + pro.getDataType(), null);
			columnsFigure.add(c);
		}
		add(nameLabel);
		add(columnsFigure);
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
		super.add(figure, constraint, index);
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
		/*
		 * if (figure instanceof ColumnFigure) { columnsFigure.remove(figure); }
		 * else {
		 */
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
