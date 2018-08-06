package com.mqfdy.code.designer.provider;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import com.mqfdy.code.model.EnumElement;

/**
 * 
 * @author mqfdy
 * 
 */
public class EnumElementLabelProvider extends LabelProvider implements
		ITableLabelProvider {

	public Image getColumnImage(Object element, int columnIndex) {
		return null;
	}

	public String getColumnText(Object element, int columnIndex) {
		EnumElement enumElement = (EnumElement) element;
		switch (columnIndex) {
		case 0:
			return enumElement.getKey();
		case 1:
			return enumElement.getValue();
		}
		return "";
	}

}
