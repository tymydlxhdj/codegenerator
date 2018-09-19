package com.mqfdy.code.reverse.views.providers;

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;

import com.mqfdy.code.designer.models.ImageKeys;
import com.mqfdy.code.designer.models.ImageManager;
import com.mqfdy.code.reverse.views.beans.BizBean;
import com.mqfdy.code.reverse.views.constant.IViewConstant;

// TODO: Auto-generated Javadoc
/**
 * The Class BizTableLabelProvider.
 *
 * @author mqfdy
 */
public class BizTableLabelProvider implements ITableLabelProvider {

	/**
	 * Gets the column image.
	 *
	 * @author mqfdy
	 * @param element
	 *            the element
	 * @param columnIndex
	 *            the column index
	 * @return the column image
	 * @Date 2018-09-03 09:00
	 */
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

	/**
	 * Gets the column text.
	 *
	 * @author mqfdy
	 * @param element
	 *            the element
	 * @param columnIndex
	 *            the column index
	 * @return the column text
	 * @Date 2018-09-03 09:00
	 */
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

	/**
	 * 
	 */
	public void dispose() {
	}

	/**
	 * Checks if is label property.
	 *
	 * @author mqfdy
	 * @param element
	 *            the element
	 * @param property
	 *            the property
	 * @return true, if is label property
	 * @Date 2018-09-03 09:00
	 */
	public boolean isLabelProperty(Object element, String property) {
		return false;
	}

	/**
	 * Adds the listener.
	 *
	 * @author mqfdy
	 * @param listener
	 *            the listener
	 * @Date 2018-09-03 09:00
	 */
	public void addListener(ILabelProviderListener listener) {
	}

	/**
	 * Removes the listener.
	 *
	 * @author mqfdy
	 * @param listener
	 *            the listener
	 * @Date 2018-09-03 09:00
	 */
	public void removeListener(ILabelProviderListener listener) {
	}
}
