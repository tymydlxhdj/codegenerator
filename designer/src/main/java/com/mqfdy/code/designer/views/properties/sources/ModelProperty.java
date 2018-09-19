package com.mqfdy.code.designer.views.properties.sources;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySheetEntry;
import org.eclipse.ui.views.properties.PropertyDescriptor;

// TODO: Auto-generated Javadoc
/**
 * 视图显示对象.
 *
 * @author mqfdy
 */
public class ModelProperty {
	
	/** The Constant ADVANCED. */
	private static final String[] ADVANCED = new String[] { IPropertySheetEntry.FILTER_ID_EXPERT };

	/** The property name. */
	private String propertyName;
	
	/** The descriptor. */
	private IPropertyDescriptor descriptor;
	
	/** The category. */
	private String category;

	/**
	 * Instantiates a new model property.
	 *
	 * @param propertyName
	 *            the property name
	 * @param descriptor
	 *            the descriptor
	 * @param category
	 *            the category
	 */
	public ModelProperty(String propertyName, PropertyDescriptor descriptor,
			String category) {
		this.propertyName = propertyName;
		if (descriptor != null)
			this.descriptor = descriptor;
		this.category = category;
		((PropertyDescriptor) this.descriptor).setCategory(category);
	}

	/**
	 * Gets the descriptor.
	 *
	 * @author mqfdy
	 * @return the descriptor
	 * @Date 2018-09-03 09:00
	 */
	public IPropertyDescriptor getDescriptor() {
		return this.descriptor;
	}

	/**
	 * Sets the descriptor.
	 *
	 * @author mqfdy
	 * @param descriptor
	 *            the new descriptor
	 * @Date 2018-09-03 09:00
	 */
	public void setDescriptor(IPropertyDescriptor descriptor) {
		this.descriptor = descriptor;
	}

	/**
	 * Gets the property name.
	 *
	 * @author mqfdy
	 * @return the property name
	 * @Date 2018-09-03 09:00
	 */
	public String getPropertyName() {
		return propertyName;
	}

	/**
	 * Gets the category.
	 *
	 * @author mqfdy
	 * @return the category
	 * @Date 2018-09-03 09:00
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * Sets the advanced.
	 *
	 * @author mqfdy
	 * @param isAdvanced
	 *            the new advanced
	 * @Date 2018-09-03 09:00
	 */
	public void setAdvanced(boolean isAdvanced) {
		if (isAdvanced) {
			((PropertyDescriptor) this.descriptor).setFilterFlags(ADVANCED);
		} else {
			((PropertyDescriptor) this.descriptor).setFilterFlags(null);
		}
	}

}
