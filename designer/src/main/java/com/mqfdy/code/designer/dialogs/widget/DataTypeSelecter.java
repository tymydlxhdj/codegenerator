package com.mqfdy.code.designer.dialogs.widget;

import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;

import com.mqfdy.code.model.utils.DataType;

public class DataTypeSelecter extends Combo {

	public DataTypeSelecter(Composite parent, int style) {
		super(parent, style);
		this.setItems(DataType.getDataTypesString());
		for (int i = 0; i < DataType.getDataTypes().size(); i++) {
			this.setData(i + "", DataType.getDataTypes().get(i));
		}
	}

	public DataType getSelectedDataType() {
		if (getSelectionIndex() < 0) {
			return null;
		}
		return (DataType) getData(getSelectionIndex() + "");
	}

	protected void checkSubclass() {

	}

	public void select(String dataType) {
		int index = DataType.getIndex(dataType);
		if (index > -1) {
			select(index);
		}

	}
}
