package com.mqfdy.code.designer.views.properties.descriptor;

import org.eclipse.gmf.runtime.common.ui.services.properties.extended.IPropertyAction;
import org.eclipse.gmf.runtime.common.ui.services.properties.extended.MultiButtonCellEditor;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

import com.mqfdy.code.designer.dialogs.DTOPropertyEditDialog;
import com.mqfdy.code.designer.dialogs.ModelElementEditorDialog;
import com.mqfdy.code.designer.dialogs.OperationEditorDialog;
import com.mqfdy.code.designer.dialogs.PropertyEditorDialog;
import com.mqfdy.code.designer.dialogs.widget.ModelElementSelecterDailog;
import com.mqfdy.code.designer.models.BusinessModelManager;
import com.mqfdy.code.designer.utils.BusinessModelUtil;
import com.mqfdy.code.model.AbstractModelElement;
import com.mqfdy.code.model.BusinessClass;
import com.mqfdy.code.model.BusinessOperation;
import com.mqfdy.code.model.DTOProperty;
import com.mqfdy.code.model.Property;

/**
 * 按钮
 * 
 * @author mqfdy
 */
// @Deprecated
public class ButtonPropertyDescriptor extends TextPropertyDescriptor implements
		IEditorValueDescriptor<String> {

	private String defaultValue;
	// 加一个属性 判断打开哪个窗口
	private String dialogType;
	private AbstractModelElement element;
	BusinessModelManager manager = BusinessModelUtil
			.getEditorBusinessModelManager();

	public ButtonPropertyDescriptor(Object id, String displayName,
			String defaultValue, String dialogType, String description,
			AbstractModelElement node) {
		super(id, displayName);
		this.element = node;
		super.setDescription(description);
		this.defaultValue = defaultValue;
		this.dialogType = dialogType;
		setLabelProvider(new MyLabelDecorator());
	}

	@Override
	public CellEditor createPropertyEditor(final Composite parent) {

		CellEditor editor = new MultiButtonCellEditor(parent, SWT.READ_ONLY) {
			@Override
			public void doSetValue(Object value) {
				if (value == null || value.equals("")) {
					super.doSetValue(null);
				} /*
				 * else if (((Boolean) value).booleanValue()) {
				 * super.doSetValue(value); }
				 */else {
					super.doSetValue(value);
					// super.setValue(((BusinessClass)value).getDisplayName());
				}

			}

			@Override
			public Object doGetValue() {
				/*
				 * int selection = ((Integer) super.doGetValue()).intValue(); if
				 * (selection == -1) { return ""; } else if (selection == 0) {
				 * return new Boolean(true); } else { return new Boolean(false);
				 * }
				 */
				return super.doGetValue();
			}

			@Override
			protected void initButtons() {
				IPropertyAction buttonAction = new IPropertyAction() {

					public Object execute(Control owner) {
						return openDialogBox(owner);
					}
				};
				super.addButton("...", buttonAction);
			}
		};

		if (getValidator() != null) {
			editor.setValidator(getValidator());
		}

		return editor;
	
	}

	public Object openDialogBox(Control cellEditorWindow) {

		// Invoke the property dialog
		// BusinessClassEditorDialog d = new
		// BusinessClassEditorDialog(cellEditorWindow.getShell());
		// d.open();
		if (dialogType.equals("BusinessClassA")) {
			ModelElementSelecterDailog dialog = new ModelElementSelecterDailog(
					new String[] { "BusinessClass" }, manager);
			if (dialog.open() == IDialogConstants.OK_ID) {
				BusinessClass businessClass = (BusinessClass) dialog
						.getFirstResult();
				return businessClass;
			}
		}
		if (dialogType.equals("BusinessClassB")
				|| dialogType.equals("Inheritance")) {
			ModelElementSelecterDailog dialog = new ModelElementSelecterDailog(
					new String[] { "BusinessClass" }, manager, false,true);
			if (dialog.open() == IDialogConstants.OK_ID) {
				BusinessClass businessClass = (BusinessClass) dialog
						.getFirstResult();
				return businessClass;
			}
		}
		if (dialogType.equals("BusinessOperation")) {
			OperationEditorDialog dialog = new OperationEditorDialog(
					cellEditorWindow.getShell(), element,
					((BusinessOperation) element).getBelongBusinessClass());
			int returnKey = dialog.open();
			if (returnKey == Window.OK
					|| (returnKey == Window.CANCEL && dialog.isChanged())) {
				// 编辑后保存关闭
				if (dialog.getOperationType().equals(
						ModelElementEditorDialog.OPERATION_TYPE_EDIT)) {
					return (BusinessOperation) dialog.getEditingElement();
				}
			}
		}
		if (dialogType.equals("PersistenceProperty")
				|| dialogType.equals("PKProperty")) {
			// BusinessModelManager manager =
			// BusinessModelUtil.getEditorBusinessModelManager();
			PropertyEditorDialog dialog = new PropertyEditorDialog(
					cellEditorWindow.getShell(), element, element.getParent());
			int returnKey = dialog.open();
			if (returnKey == Window.OK
					|| (returnKey == Window.CANCEL && dialog.isChanged())) {
				// 编辑后保存关闭
				if (dialog.getOperationType().equals(
						ModelElementEditorDialog.OPERATION_TYPE_EDIT)) {
					return (Property) dialog.getEditingElement();
				}
			}
		}
		if (dialogType.equals("DTOProperty")) {
			// BusinessModelManager manager =
			// BusinessModelUtil.getEditorBusinessModelManager();
			DTOPropertyEditDialog dialog = new DTOPropertyEditDialog(null,
					(DTOProperty) element, null);
			int returnKey = dialog.open();
			if (returnKey == Window.OK
					|| (returnKey == Window.CANCEL && dialog.isChanged())) {
				// 编辑后保存关闭
				if (dialog.getOperationType().equals(
						ModelElementEditorDialog.OPERATION_TYPE_EDIT)) {
					return (Property) dialog.getEditingElement();
				}
			}
		}// handle invokation of cell editor from collection editor
		/*
		 * PropertyPagePropertyDescriptor realDescriptor = null; if (getValue()
		 * instanceof ElementValue) { Object element = ((ElementValue)
		 * getValue()).getElement(); if (element instanceof
		 * PropertyPagePropertyDescriptor) { realDescriptor =
		 * (PropertyPagePropertyDescriptor) element; } }
		 * 
		 * List pages = null; if (realDescriptor != null) { pages =
		 * realDescriptor.createPropertyPages(); } else { pages =
		 * getPropertyDescriptor().createPropertyPages(); }
		 * 
		 * for (Iterator i = pages.iterator(); i.hasNext();) { PropertyPage page
		 * = (PropertyPage) i.next();
		 * 
		 * // handle invokation of cell editor from collection editor if
		 * (realDescriptor != null) { final IPropertySource source =
		 * realDescriptor.getPropertySource(); page.setElement(new IAdaptable()
		 * { public Object getAdapter(Class adapter) { if
		 * (adapter.equals(IPropertySource.class)) { return source; } return
		 * null; } }); }
		 * 
		 * dialog.getPreferenceManager().addToRoot( new
		 * PreferenceNode(StringStatics.BLANK, page)); }
		 * 
		 * dialog.create(); dialog.open();
		 * 
		 * // refresh property for collection editor for (Iterator i =
		 * pages.iterator(); i.hasNext();) { PropertyPage page = (PropertyPage)
		 * i.next(); IAdaptable adaptable = page.getElement(); if (adaptable !=
		 * null) { IPropertySource source = (IPropertySource)
		 * adaptable.getAdapter( IPropertySource.class); if (source instanceof
		 * IExtendedPropertySource) { Object element =
		 * ((IExtendedPropertySource) source).getElement();
		 * 
		 * IPropertySource propertySource =
		 * PropertiesService.getInstance().getPropertySource(
		 * 
		 * element); assert null != propertySource;
		 * 
		 * for (Iterator j = Arrays
		 * .asList(propertySource.getPropertyDescriptors()) .iterator();
		 * j.hasNext(); ) { IPropertyDescriptor descriptor =
		 * (IPropertyDescriptor) j.next(); if (descriptor .getId()
		 * .equals(getPropertyDescriptor().getId())) { // apply new value in
		 * cell editor setValue( new ElementValue( source,
		 * propertySource.getPropertyValue( descriptor.getId())));
		 * fireApplyEditorValue(); break; } } } } }
		 */
		return null;
	}

	/**
	 * implement {@link IEditorValueDescriptor}
	 */

	/**
	 * implement {@link IEditorValueDescriptor}
	 */
	public String getEditorValue(String value) {
		if (value == null) {
			return defaultValue;
		} else {
			return value;
		}
	}

	class MyLabelDecorator extends PropertyLabelDecoratorUtil {
		@Override
		protected String getDefaultValue() {
			if (defaultValue == null) {
				return "";
			} else {
				return defaultValue.toString();
			}
		}

	}

	public String getDefaultValue() {
		if (defaultValue == null) {
			return "";
		} else {
			return defaultValue.toString();
		}
	}

}
