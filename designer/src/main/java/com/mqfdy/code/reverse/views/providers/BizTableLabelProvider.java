package com.mqfdy.code.reverse.views.providers;

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;

import com.mqfdy.code.designer.models.ImageKeys;
import com.mqfdy.code.designer.models.ImageManager;
import com.mqfdy.code.reverse.views.beans.BizBean;
import com.mqfdy.code.reverse.views.constant.IViewConstant;

public class BizTableLabelProvider implements ITableLabelProvider {

	public Image getColumnImage(Object element, int columnIndex) {
		if (element instanceof BizBean) {
			BizBean bean = (BizBean) element;
			if(IViewConstant.TEMP.equals(bean.getName())) {
				return null;
			}
			
			switch (columnIndex) {
			case 0:
				Image image = null;
				ImageManager manager = ImageManager.getInstance();
				image = manager.getImage(ImageKeys.IMG_MODEL_TYPE_BUSINESSCLASS);
				return image;
			}
		}
		return null;
	}

	public String getColumnText(Object element, int columnIndex) {
		if (element instanceof BizBean) {
			BizBean bizBean = (BizBean) element;
			switch (columnIndex) {
			case 0:
				return bizBean.getName();
			case 1:
				return bizBean.getDisplayName();
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
