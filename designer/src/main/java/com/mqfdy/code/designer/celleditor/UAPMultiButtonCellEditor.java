package com.mqfdy.code.designer.celleditor;

import org.eclipse.gmf.runtime.common.ui.services.properties.extended.IPropertyAction;
import org.eclipse.gmf.runtime.common.ui.services.properties.extended.MultiButtonCellEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

public class UAPMultiButtonCellEditor extends MultiButtonCellEditor {

	public UAPMultiButtonCellEditor(Composite parent) {
		super(parent);
	}

	@Override
	protected void initButtons() {
		this.addButton("删除", new IPropertyAction() {
			public Object execute(Control owner) {
				return null;
			}
		});
	}

}
