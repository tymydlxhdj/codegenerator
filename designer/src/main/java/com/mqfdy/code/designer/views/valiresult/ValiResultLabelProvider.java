package com.mqfdy.code.designer.views.valiresult;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import com.mqfdy.code.designer.models.ImageKeys;
import com.mqfdy.code.designer.models.ImageManager;
import com.mqfdy.code.model.PKProperty;
import com.mqfdy.code.resource.validator.ValiResult;


public class ValiResultLabelProvider extends LabelProvider implements
		ITableLabelProvider {

	public Image getColumnImage(Object element, int columnIndex) {
		if (columnIndex == 0) {
			if (element instanceof PKProperty) {
				return ImageManager.getInstance().getImage(
						ImageKeys.IMG_PROPERTY_PRIMARYKEY);
			}
		}
		return null;
	}

	public String getColumnText(Object element, int columnIndex) {
		if (element instanceof ValiResult) {
			ValiResult property = (ValiResult) element;
			switch (columnIndex) {
			case 0:
				return property.getLevel() + "";
			case 1:
				return property.getType() + "";
			case 2:
				return property.getValiString();
			case 3:
				return property.getObjectName();
			case 4:
				return property.getLocation();
			}
		}
		return "";
	}

}