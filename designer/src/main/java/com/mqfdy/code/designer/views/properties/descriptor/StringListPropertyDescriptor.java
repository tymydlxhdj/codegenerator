package com.mqfdy.code.designer.views.properties.descriptor;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

/**
 * 下拉框
 * 
 * @author mqfdy
 * 
 */
public class StringListPropertyDescriptor extends TextPropertyDescriptor
		implements IEditorValueDescriptor<String> {

	private String[] list;
	private String defaultValue;

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

	public StringListPropertyDescriptor(Object id, String displayName,
			String[] list, String defaultValue, String description) {
		super(id, displayName);
		super.setDescription(description);
		this.list = list;
		this.defaultValue = new String(defaultValue);
		setLabelProvider(new PropertyLabelDecoratorUtil(defaultValue));
	}

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
		} else if (defaultValue != null) {
			return defaultValue;
		} else {
			return "";
		}
	}
}
