package com.mqfdy.code.reverse.views.providers;

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;

import com.mqfdy.code.reverse.views.beans.SpecialTable;

public class DupliTableLabelProvider implements ITableLabelProvider {

	public Image getColumnImage(Object element, int columnIndex) {
		return null;
	}

	public String getColumnText(Object element, int columnIndex) {
		if (element instanceof SpecialTable) {
			SpecialTable specialTable = (SpecialTable) element;
			switch (columnIndex) {
			case 0:
				return specialTable.getName();
			case 1:
				return specialTable.getDesc();
			case 2:
				return specialTable.getHandleText();
			}
		}
		return null;
	}

	public void dispose() {
	}

	public boolean isLabelProperty(Object element, String property) {
		return false;
	}

	public void addListener(ILabelProviderListener listener) {
	}

	public void removeListener(ILabelProviderListener listener) {
	}
}
