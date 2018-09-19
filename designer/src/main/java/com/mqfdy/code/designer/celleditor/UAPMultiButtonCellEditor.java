package com.mqfdy.code.designer.celleditor;

import org.eclipse.gmf.runtime.common.ui.services.properties.extended.IPropertyAction;
import org.eclipse.gmf.runtime.common.ui.services.properties.extended.MultiButtonCellEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

// TODO: Auto-generated Javadoc
/**
 * The Class UAPMultiButtonCellEditor.
 *
 * @author mqfdy
 */
public class UAPMultiButtonCellEditor extends MultiButtonCellEditor {

	/**
	 * Instantiates a new UAP multi button cell editor.
	 *
	 * @param parent
	 *            the parent
	 */
	public UAPMultiButtonCellEditor(Composite parent) {
		super(parent);
	}

	/**
	 * 
	 */
	@Override
	protected void initButtons() {
		this.addButton("删除", new IPropertyAction() {
			public Object execute(Control owner) {
				return null;
			}
		});
	}

}
