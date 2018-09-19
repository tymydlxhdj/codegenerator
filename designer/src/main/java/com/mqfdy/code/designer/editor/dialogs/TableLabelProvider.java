package com.mqfdy.code.designer.editor.dialogs;

import org.eclipse.core.resources.IResource;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;

// TODO: Auto-generated Javadoc
/**
 * The Class TableLabelProvider.
 *
 * @author mqfdy
 */
public class TableLabelProvider implements ITableLabelProvider {

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
		if (element instanceof IResource) {
			switch (columnIndex) {
				case 0:
					return ((IResource) element).getName();
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
