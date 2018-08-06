package com.mqfdy.code.designer.views.properties.descriptor;

import org.eclipse.ui.views.properties.PropertyDescriptor;

/**
 * 只读编辑框
 * 
 * @author mqfdy
 * 
 */

public class ReadOnlyPropertyDescriptor extends PropertyDescriptor implements
		IEditorValueDescriptor<String> {

	private String defaultValue;

	public ReadOnlyPropertyDescriptor(Object id, String displayName,
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
