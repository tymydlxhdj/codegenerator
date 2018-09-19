package com.mqfdy.code.designer.views.properties.descriptor;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

// TODO: Auto-generated Javadoc
/**
 * 下拉框.
 *
 * @author mqfdy
 */
public class StringListPropertyDescriptor extends TextPropertyDescriptor
		implements IEditorValueDescriptor<String> {

	/** The list. */
	private String[] list;
	
	/** The default value. */
	private String defaultValue;

	/**
	 * Instantiates a new string list property descriptor.
	 *
	 * @param id
	 *            the id
	 * @param displayName
	 *            the display name
	 * @param list
	 *            the list
	 * @param description
	 *            the description
	 */
	public StringListPropertyDescriptor(Object id, String displayName,
			String[] list, String description) {
		super(id, displayName);
		super.setDescription(description);
		this.list = list;
		if (list.length > 0) {
			defaultValue = new String(list[0]);
		}
		setLabelProvider(new PropertyLabelDecoratorUtil(defaultValue));
	}

	/**
	 * Instantiates a new string list property descriptor.
	 *
	 * @param id
	 *            the id
	 * @param displayName
	 *            the display name
	 * @param list
	 *            the list
	 * @param defaultValue
	 *            the default value
	 * @param description
	 *            the description
	 */
	public StringListPropertyDescriptor(Object id, String displayName,
			String[] list, String defaultValue, String description) {
		super(id, displayName);
		super.setDescription(description);
		this.list = list;
		this.defaultValue = new String(defaultValue);
		setLabelProvider(new PropertyLabelDecoratorUtil(defaultValue));
	}

	/**
	 * Creates the property editor.
	 *
	 * @author mqfdy
	 * @param parent
	 *            the parent
	 * @return the cell editor
	 * @Date 2018-09-03 09:00
	 */
	@Override
	public CellEditor createPropertyEditor(Composite parent) {
		CellEditor editor = new ComboBoxCellEditor(parent, list, SWT.NONE) {
			@Override
			public void doSetValue(Object value) {
				if (value == null || value.equals("")) {
					super.doSetValue(new Integer(-1));
				} else {
					for (int i = 0; i < list.length; i++) {
						if (list[i].equalsIgnoreCase(value.toString())) {
							super.doSetValue(new Integer(i));
							return;
						}
					}
					// no hit.
					((CCombo) super.getControl()).setText(value.toString());
				}
			}

			@Override
			public Object doGetValue() {
				return ((CCombo) super.getControl()).getText();
				// int selection = ((Integer) super.doGetValue()).intValue();
				// if (selection == -1) {
				// return AbstractModel.NULL_PROPERTY;
				// } else {
				// return list[selection];
				// }
			}
		};
		if (getValidator() != null) {
			editor.setValidator(getValidator());
		}

		return editor;
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
		} else if (defaultValue != null) {
			return defaultValue;
		} else {
			return "";
		}
	}
}
