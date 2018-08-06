package com.mqfdy.code.designer.views.valiresult;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import com.mqfdy.code.model.AbstractModelElement;

public class FindObjectLabelProvider extends LabelProvider implements
		ITableLabelProvider {

	public Image getColumnImage(Object element, int columnIndex) {
		// if (columnIndex == 0) {
		// if (element instanceof PKProperty) {
		// return ImageManager.getInstance().getImage(
		// ImageKeys.IMG_PROPERTY_PRIMARYKEY);
		// }
		// }
		return null;
	}

	public String getColumnText(Object element, int columnIndex) {
		if (element instanceof AbstractModelElement) {
			AbstractModelElement property = (AbstractModelElement) element;
			switch (columnIndex) {
			case 0:
				return property.getDisplayType() + "";
			case 1:
				return property.getName();
			case 2:
				return property.getDisplayName();
			case 3:
				return property.getFullName();
			}
		}
		return "";
	}

}