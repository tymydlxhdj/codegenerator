package com.mqfdy.code.designer.views.properties.descriptor;

import org.eclipse.ui.views.properties.PropertyDescriptor;

// TODO: Auto-generated Javadoc
/**
 * 只读编辑框.
 *
 * @author mqfdy
 */

public class ReadOnlyPropertyDescriptor extends PropertyDescriptor implements
		IEditorValueDescriptor<String> {

	/** The default value. */
	private String defaultValue;

	/**
	 * Instantiates a new read only property descriptor.
	 *
	 * @param id
	 *            the id
	 * @param displayName
	 *            the display name
	 * @param defaultValue
	 *            the default value
	 * @param description
	 *            the description
	 */
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
	 * implement {@link IEditorValueDescriptor}.
	 *
	 * @author mqfdy
	 * @return the default value
	 * @Date 2018-09-03 09:00
	 */
	public String getDefaultValue() {
		return defaultValue;
	}

	/**
	 * implement {@link IEditorValueDescriptor}.
	 *
	 * @author mqfdy
	 * @param value
	 *            the value
	 * @return the editor value
	 * @Date 2018-09-03 09:00
	 */
	public String getEditorValue(String value) {
		if (value != null && value.length() != 0) {
			return value;
		} else {
			return defaultValue;
		}
	}
}
