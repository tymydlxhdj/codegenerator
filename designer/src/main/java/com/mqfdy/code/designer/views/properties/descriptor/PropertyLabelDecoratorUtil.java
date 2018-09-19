package com.mqfdy.code.designer.views.properties.descriptor;

import org.eclipse.jface.viewers.LabelProvider;

// TODO: Auto-generated Javadoc
/**
 * 默认值.
 *
 * @author mqfdy
 */
public class PropertyLabelDecoratorUtil extends LabelProvider {
	
	/** The default value. */
	private String defaultValue;

	/**
	 * Instantiates a new property label decorator util.
	 *
	 * @param defaultValue
	 *            the default value
	 */
	public PropertyLabelDecoratorUtil(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	/**
	 * Instantiates a new property label decorator util.
	 */
	public PropertyLabelDecoratorUtil() {

	}

	// @Override
	// public Image getImage(Object element) {
	// if (!(element.equals("")) && element != getDefaultValue()) {
	// return ComponentImageRegistry.getSetPropertyImage();
	// } else {
	// return null;
	// }
	// }

	/**
	 * Gets the text.
	 *
	 * @author mqfdy
	 * @param element
	 *            the element
	 * @return the text
	 * @Date 2018-09-03 09:00
	 */
	@Override
	public String getText(Object element) {
		if (element == null || element.equals("")) {
			if (getDefaultValue() == null) {
				return "";
			} else {
				return getDefaultValue();
			}
		} else {
			return getTextString(element);
		}
	}

	/**
	 * Gets the default value.
	 *
	 * @author mqfdy
	 * @return the default value
	 * @Date 2018-09-03 09:00
	 */
	protected String getDefaultValue() {
		return defaultValue;
	}

	/**
	 * Gets the text string.
	 *
	 * @author mqfdy
	 * @param element
	 *            the element
	 * @return the text string
	 * @Date 2018-09-03 09:00
	 */
	protected String getTextString(Object element) {
		return super.getText(element);
	}
}
