package com.mqfdy.code.designer.views.properties.descriptor;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

/**
 * true、false 下拉框
 * 
 * @author mqfdy
 */
// @Deprecated
public class BooleanPropertyDescriptor extends TextPropertyDescriptor implements
		IEditorValueDescriptor<Boolean> {

	private Boolean defaultValue;

	public BooleanPropertyDescriptor(Object id, String displayName,
			Boolean defaultValue, String description) {
		super(id, displayName);
		super.setDescription(description);
		this.defaultValue = defaultValue;

		setLabelProvider(new MyLabelDecorator());
	}

	@Override
	public CellEditor createPropertyEditor(Composite parent) {
		CellEditor editor = new ComboBoxCellEditor(parent, new String[] {
				"true", "false" }, SWT.READ_ONLY) {
			@Override
			public void doSetValue(Object value) {
				if (value == null || value.equals("")) {
					super.doSetValue(new Integer(-1));
				} else if (((Boolean) value).booleanValue()) {
					super.doSetValue(new Integer(0));
				} else {
					super.doSetValue(new Integer(1));
				}

			}

			@Override
			public Object doGetValue() {
				int selection = ((Integer) super.doGetValue()).intValue();
				if (selection == -1) {
					return "";
				} else if (selection == 0) {
					return new Boolean(true);
				} else {
					return new Boolean(false);
				}
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
	public Boolean getDefaultValue() {
		return defaultValue;
	}

	/**
	 * implement {@link IEditorValueDescriptor}
	 */
	public Boolean getEditorValue(Boolean value) {
		if (value == null) {
			return defaultValue;
		} else {
			return value;
		}
	}

	class MyLabelDecorator extends PropertyLabelDecoratorUtil {
		@Override
		protected String getDefaultValue() {
			return defaultValue.toString();
		}

	}

}
