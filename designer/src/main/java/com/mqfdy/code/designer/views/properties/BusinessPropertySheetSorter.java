package com.mqfdy.code.designer.views.properties;

import org.eclipse.ui.views.properties.IPropertySheetEntry;
import org.eclipse.ui.views.properties.PropertySheetSorter;

/**
 * 属性页排序
 * 
 * @author mqfdy
 * 
 */
public class BusinessPropertySheetSorter extends PropertySheetSorter {

	@Override
	public int compare(IPropertySheetEntry entryA, IPropertySheetEntry entryB) {
		if (entryA.getDescription() != null && entryB.getDescription() != null) {
			return getCollator().compare(entryA.getDescription(),
					entryB.getDescription());
		}
		return 0;
	}

	@Override
	public int compareCategories(String categoryA, String categoryB) {
		return getCollator().compare(categoryB, categoryA);
	}
}
