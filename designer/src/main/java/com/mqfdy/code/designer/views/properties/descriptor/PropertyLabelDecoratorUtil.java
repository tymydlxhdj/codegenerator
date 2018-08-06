package com.mqfdy.code.designer.views.properties.descriptor;

import org.eclipse.jface.viewers.LabelProvider;

/**
 * 默认值
 * 
 * @author mqfdy
 * 
 */
public class PropertyLabelDecoratorUtil extends LabelProvider {
	private String defaultValue;

	public PropertyLabelDecoratorUtil(String defaultValue) {
		this.defaultValue = defaultValue;
	}

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

	protected String getDefaultValue() {
		return defaultValue;
	}

	protected String getTextString(Object element) {
		return super.getText(element);
	}
}
