package com.mqfdy.code.reverse.views.providers;

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;

import com.mqfdy.code.designer.models.ImageKeys;
import com.mqfdy.code.designer.models.ImageManager;
import com.mqfdy.code.reverse.DataSourceInfo;


public class DataSourceTableLabelProvider implements ITableLabelProvider {

	public Image getColumnImage(Object element, int columnIndex) {
		if (element instanceof DataSourceInfo) {
			switch (columnIndex) {
				case 0:
					ImageManager manager = ImageManager.getInstance();
					Image image = manager.getImage(ImageKeys.IMG_DATASOURCE);
					return image;
			}
		}
		return null;
	}

	public String getColumnText(Object element, int columnIndex) {
		if (element instanceof DataSourceInfo) {
			DataSourceInfo info = (DataSourceInfo) element;
			switch (columnIndex) {
			case 0:
				return info.getDataSourceName();
			case 1:
				return info.getUapName();
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
