package com.mqfdy.code.designer.dialogs.widget;

import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;

import com.mqfdy.code.model.utils.DataType;

// TODO: Auto-generated Javadoc
/**
 * The Class DataTypeSelecter.
 *
 * @author mqfdy
 */
public class DataTypeSelecter extends Combo {

	/**
	 * Instantiates a new data type selecter.
	 *
	 * @param parent
	 *            the parent
	 * @param style
	 *            the style
	 */
	public DataTypeSelecter(Composite parent, int style) {
		super(parent, style);
		this.setItems(DataType.getDataTypesString());
		for (int i = 0; i < DataType.getDataTypes().size(); i++) {
			this.setData(i + "", DataType.getDataTypes().get(i));
		}
	}

	/**
	 * Gets the selected data type.
	 *
	 * @author mqfdy
	 * @return the selected data type
	 * @Date 2018-09-03 09:00
	 */
	public DataType getSelectedDataType() {
		if (getSelectionIndex() < 0) {
			return null;
		}
		return (DataType) getData(getSelectionIndex() + "");
	}

	/**
	 * 
	 */
	protected void checkSubclass() {

	}

	/**
	 * Select.
	 *
	 * @author mqfdy
	 * @param dataType
	 *            the data type
	 * @Date 2018-09-03 09:00
	 */
	public void select(String dataType) {
		int index = DataType.getIndex(dataType);
		if (index > -1) {
			select(index);
		}

	}
}
