package com.mqfdy.code.designer.views.properties;

import org.eclipse.ui.views.properties.IPropertySheetEntry;
import org.eclipse.ui.views.properties.PropertySheetSorter;

// TODO: Auto-generated Javadoc
/**
 * 属性页排序.
 *
 * @author mqfdy
 */
public class BusinessPropertySheetSorter extends PropertySheetSorter {

	/**
	 * Compare.
	 *
	 * @author mqfdy
	 * @param entryA
	 *            the entry A
	 * @param entryB
	 *            the entry B
	 * @return the int
	 * @Date 2018-09-03 09:00
	 */
	@Override
	public int compare(IPropertySheetEntry entryA, IPropertySheetEntry entryB) {
		if (entryA.getDescription() != null && entryB.getDescription() != null) {
			return getCollator().compare(entryA.getDescription(),
					entryB.getDescription());
		}
		return 0;
	}

	/**
	 * Compare categories.
	 *
	 * @author mqfdy
	 * @param categoryA
	 *            the category A
	 * @param categoryB
	 *            the category B
	 * @return the int
	 * @Date 2018-09-03 09:00
	 */
	@Override
	public int compareCategories(String categoryA, String categoryB) {
		return getCollator().compare(categoryB, categoryA);
	}
}
