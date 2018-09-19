package com.mqfdy.code.designer.provider;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import com.mqfdy.code.model.Inheritance;

// TODO: Auto-generated Javadoc
/**
 * The Class InherLabelProvider.
 *
 * @author mqfdy
 */
public class InherLabelProvider extends LabelProvider implements
		ITableLabelProvider {

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
		// TODO Auto-generated method stub
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
