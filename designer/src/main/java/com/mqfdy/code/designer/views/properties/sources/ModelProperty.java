package com.mqfdy.code.designer.views.properties.sources;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySheetEntry;
import org.eclipse.ui.views.properties.PropertyDescriptor;

/**
 * 视图显示对象
 * 
 * @author mqfdy
 */
public class ModelProperty {
	private static final String[] ADVANCED = new String[] { IPropertySheetEntry.FILTER_ID_EXPERT };

	private String propertyName;
	private IPropertyDescriptor descriptor;
	private String category;

	public ModelProperty(String propertyName, PropertyDescriptor descriptor,
			String category) {
		this.propertyName = propertyName;
		if (descriptor != null)
			this.descriptor = descriptor;
		this.category = category;
		((PropertyDescriptor) this.descriptor).setCategory(category);
	}

	public IPropertyDescriptor getDescriptor() {
		return this.descriptor;
	}

	public void setDescriptor(IPropertyDescriptor descriptor) {
		this.descriptor = descriptor;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public String getCategory() {
		return category;
	}

	public void setAdvanced(boolean isAdvanced) {
		if (isAdvanced) {
			((PropertyDescriptor) this.descriptor).setFilterFlags(ADVANCED);
		} else {
			((PropertyDescriptor) this.descriptor).setFilterFlags(null);
		}
	}

}
