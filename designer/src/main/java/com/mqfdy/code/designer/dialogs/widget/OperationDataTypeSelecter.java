package com.mqfdy.code.designer.dialogs.widget;

import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;

import com.mqfdy.code.designer.models.BusinessModelManager;
import com.mqfdy.code.designer.utils.BusinessModelUtil;
import com.mqfdy.code.model.AbstractModelElement;
import com.mqfdy.code.model.IModelElement;
import com.mqfdy.code.model.utils.DataType;
import com.mqfdy.code.model.utils.StringUtil;

// TODO: Auto-generated Javadoc
/**
 * The Class OperationDataTypeSelecter.
 *
 * @author mqfdy
 */
public class OperationDataTypeSelecter extends Composite {

	/** The Constant BTN_SELECT_LABEL. */
	private final static String BTN_SELECT_LABEL = "选择";

	/** The com data type. */
	private Combo com_dataType;

	/** The btn select. */
	private Button btn_select;

	/** The data types. */
	private String[] dataTypes;

	/** The business object. */
	private AbstractModelElement businessObject;

	/** The data type name. */
	private String dataTypeName;

	/** The temp text. */
	private String tempText;

	/**
	 * Instantiates a new operation data type selecter.
	 *
	 * @param parent
	 *            the parent
	 * @param style
	 *            the style
	 */
	public OperationDataTypeSelecter(Composite parent, int style) {
		super(parent, style);
		GridLayout layout = new GridLayout(2, false);
		layout.horizontalSpacing = 0;
		layout.marginLeft = 0;
		layout.marginRight = 0;
		this.setLayout(layout);

		com_dataType = new Combo(this, SWT.DROP_DOWN);
		com_dataType.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false));

		btn_select = new Button(this, SWT.NONE);
		btn_select.setText(BTN_SELECT_LABEL);
		initControlValue();
		makeAction();
	}

	/**
	 * Inits the control value.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	private void initControlValue() {
		dataTypes = DataType.getAllDataTypesString();
		com_dataType.setItems(dataTypes);
		for (int i = 0; i < DataType.getDataTypes().size(); i++) {
			com_dataType.setData(i + "", DataType.getDataTypes().get(i));
		}
	}

	/**
	 * Make action.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	private void makeAction() {
		btn_select.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				BusinessModelManager manager = BusinessModelUtil
						.getEditorBusinessModelManager();
				ModelElementSelecterDailog dialog = new ModelElementSelecterDailog(
						new String[] { IModelElement.MODEL_TYPE_BUSINESSCLASS,
								IModelElement.MODEL_TYPE_DTO }
						// , IModelElement.MODEL_TYPE_ENUM }
						, manager);
				if (dialog.open() == Window.OK) {
					businessObject = (AbstractModelElement) dialog.getResult()[0];
					com_dataType.setText(businessObject.getName());
					dataTypeName = businessObject.getName();
				}

			}
		});

		com_dataType.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				int index = com_dataType.getSelectionIndex();
				if (index > -1) {
					dataTypeName = DataType.getDataType(index)
							.getValue_hibernet();
				}

			}
		});

		com_dataType.setMenu(new Menu(com_dataType));
		com_dataType.addKeyListener(new KeyListener() {

			public void keyReleased(KeyEvent e) {
				e.doit = false;
				com_dataType.setText(tempText);
			}

			public void keyPressed(KeyEvent e) {
				tempText = com_dataType.getText();
				com_dataType.setText(tempText);
				e.doit = false;
			}
		});

	}

	/**
	 * Sets the data type.
	 *
	 * @author mqfdy
	 * @param dataType
	 *            the new data type
	 * @Date 2018-09-03 09:00
	 */
	public void setDataType(String dataType) {
		int index = DataType.getIndex(dataType);
		if (index > -1) {
			com_dataType.select(index);
			dataTypeName = dataType;
		} else {
			com_dataType.setText(StringUtil.convertNull2EmptyStr(dataType));
			dataTypeName = dataType;
		}
	}

	/**
	 * Gets the data type name.
	 *
	 * @author mqfdy
	 * @return the data type name
	 * @Date 2018-09-03 09:00
	 */
	public String getDataTypeName() {
		return dataTypeName;
	}

}
