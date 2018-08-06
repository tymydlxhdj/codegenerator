package com.mqfdy.code.designer.views.properties.descriptor;

import org.eclipse.ui.views.properties.TextPropertyDescriptor;

/**
 * PropertyDescriptor String.
 * 
 * @author mqfdy
 * 
 */
public class StringPropertyDescriptor extends TextPropertyDescriptor implements
		IEditorValueDescriptor<String> {

	private String defaultValue;

	public StringPropertyDescriptor(Object id, String displayName,
			String defaultValue, String description) {
		super(id, displayName);
		super.setDescription(description);
		if (defaultValue == null) {
			this.defaultValue = "";
		} else {
			this.defaultValue = defaultValue;
		}
		setLabelProvider(new PropertyLabelDecoratorUtil(defaultValue));
	}

	/**
	 * implement {@link IEditorValueDescriptor}
	 */
	public String getDefaultValue() {
		return defaultValue;
	}

	/**
	 * implement {@link IEditorValueDescriptor}
	 */
	public String getEditorValue(String value) {
		if (value != null && value.length() != 0) {
			return value;
		} else {
			return defaultValue;
		}
	}

}
