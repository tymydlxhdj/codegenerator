package com.mqfdy.code.designer.provider;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import com.mqfdy.code.model.Association;
import com.mqfdy.code.model.utils.AssociationType;

// TODO: Auto-generated Javadoc
/**
 * The Class AssociationLabelProvider.
 *
 * @author mqfdy
 */
public class AssociationLabelProvider extends LabelProvider implements
		ITableLabelProvider {

	/** The Constant NAVI_DOUBLE. */
	public static final String NAVI_DOUBLE = "双向导航";
	
	/** The Constant NAVI_A2B. */
	public static final String NAVI_A2B = "A to B";
	
	/** The Constant NAVI_B2A. */
	public static final String NAVI_B2A = "B to A";

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
		if (element instanceof Association) {
			Association association = (Association) element;
			switch (columnIndex) {
			case 0:
				return association.getDisplayName();
			case 1:
				return association.getClassA().getDisplayName();
			case 2:
				return association.getClassB().getDisplayName();
			case 3:
				return AssociationType.getAssociationType(
						association.getAssociationType()).getDisplayValue();

			case 4:
				String result = "";
				if (association.isNavigateToClassA()) {
					if (association.isNavigateToClassB()) {
						result = NAVI_DOUBLE;
					} else {
						result = NAVI_B2A;
					}
				} else {
					if (association.isNavigateToClassB()) {
						result = NAVI_A2B;
					}
				}
				return result;
			}
		}
		return "";
	}

}
