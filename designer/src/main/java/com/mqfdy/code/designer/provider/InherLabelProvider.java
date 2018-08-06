package com.mqfdy.code.designer.provider;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import com.mqfdy.code.model.Inheritance;

public class InherLabelProvider extends LabelProvider implements
		ITableLabelProvider {

	public Image getColumnImage(Object element, int columnIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getColumnText(Object element, int columnIndex) {
		if (element instanceof Inheritance) {
			Inheritance inher = (Inheritance) element;
			switch (columnIndex) {
			case 0:
				return inher.getDisplayName();
			case 1:
				return inher.getParentClass().getDisplayName();
			case 2:
				return inher.getChildClass().getDisplayName();
			}
		}
		return "";
	}

}
