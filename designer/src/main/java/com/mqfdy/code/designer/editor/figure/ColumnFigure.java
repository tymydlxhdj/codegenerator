package com.mqfdy.code.designer.editor.figure;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;

import com.mqfdy.code.designer.editor.BusinessModelEditorPlugin;
import com.mqfdy.code.designer.models.ImageKeys;
import com.mqfdy.code.model.AbstractModelElement;

/**
 * @title:属性、操作对象的界面
 * @description:
 * @author mqfdy
 */
public class ColumnFigure extends Label {

	public void fillAttribute(String text, AbstractModelElement columnElement) {

		setText(text);

		ImageRegistry reg = BusinessModelEditorPlugin.getDefault()
				.getImageRegistry();
		Image icon = null;

		// boolean isPrimaryKey = column.isPrimaryKey();
		// if (isPrimaryKey) {
		icon = reg.get(ImageKeys.IMG_PROPERTY_PRIMARYKEY);
		// } else {
		// icon = reg.get(ImageNames.GRAPHICS_ATTR_PUBL);
		// }
		setIcon(icon);
		setLabelAlignment(PositionConstants.LEFT);
		setFont(new Font(null,"", 8, 0));
		
		setBorder(new MarginBorder(3, 0, 0, 0) {
			@Override
			public void paint(IFigure figure, Graphics graphics, Insets insets) {
				// get rectangle surrending the figure
				Rectangle rec = getPaintRectangle(figure, insets);
				// 设置字段间的分割线为虚线
				// graphics.setLineStyle(Graphics.LINE_DOT);
				// graphics.drawLine(rec.x, rec.y, rec.right(), rec.y);

			}
		});
	}
}
