package com.mqfdy.code.designer.dialogs;

import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;

import com.mqfdy.code.designer.dialogs.widget.NullToEmptyText;
import com.mqfdy.code.designer.editor.utils.Logger;
import com.mqfdy.code.designer.utils.BusinessModelUtil;
import com.mqfdy.code.designer.utils.CheckerUtil;
import com.mqfdy.code.model.BusinessClass;
import com.mqfdy.code.model.IModelElement;
import com.mqfdy.code.model.PKProperty;
import com.mqfdy.code.model.PersistenceProperty;
import com.mqfdy.code.model.Property;
import com.mqfdy.code.model.PropertyGroup;
import com.mqfdy.code.model.Validator;
import com.mqfdy.code.model.utils.DataType;
import com.mqfdy.code.model.utils.EditorType;
import com.mqfdy.code.model.utils.PrimaryKeyPloyType;
import com.mqfdy.code.model.utils.StringUtil;
import com.mqfdy.code.model.utils.ValidatorType;
import com.mqfdy.code.resource.validator.ValidatorUtil;


/**
 * 业务实体属性基本信息编辑页
 * 
 * @author mqfdy
 * 
 */
public class PropertyBasicInfoPage extends Composite implements
		IBusinessClassEditorPage {

	public static final String GROUP_PERSITENCE_TEXT = "持久化策略";
	public static final String GROUP_PRIMARYKET_TEXT = "主键";

	public static final String DATATYPE_LABEL_TEXT = "属性类型：";
	public static final String GROUPINFO_LABEL_TEXT = "分组信息：";
	public static final String PRIMARYKEYPLOY_LABEL_TEXT = "主键策略";
	public static final String PRIMARYKEYPLOY_PARAM_LABEL_TEXT = "策略参数_sequence名：";
	public static final String DEFAULTVALUE_LABEL_TEXT = "缺省值：";
	public static final String DBCOLUMNNAME_LABEL_TEXT = "数据库字段名：";
	public static final String DBDATALENGTH_LABEL_TEXT = "字段长度：";
	public static final String DBDATAPRECISION_LABEL_TEXT = "字段精度：";

	public static final String BUTTON_ISPERSISTENCE_TEXT = "持久化";
	public static final String BUTTON_ISPRIMARYKEY_TEXT = "主键";
	public static final String BUTTON_NULLABLE_TEXT = "可空";
	// public static final String BUTTON_ISINDEXCOLUMN_TEXT = "索引列";
	public static final String BUTTON_READONLY_TEXT = "只读";
	public static final String BUTTON_ISUNIQUE_TEXT = "唯一";

	// 显示组件
	private Label label_name;
	private NullToEmptyText text_name;

	private Label label_displayName;
	private NullToEmptyText text_displayName;

	private Label label_remark;
	private NullToEmptyText text_remark;

	private Label label_dBDataType;
	private Combo combo_dBDataType;

	private Label label_group;
	private Combo combo_group;

	private Button btn_isPersistence;
	private Button btn_isPrimaryKey;
	private Button btn_nullable;
	// private Button btn_indexColumn;
	private Button btn_readOnly;
	private Button btn_isUnique;

	private Label label_priamryKeyPloy;
	private Combo combo_priamryKeyPloy;

	private Label label_primaryKeyPloyParam;
	private NullToEmptyText text_primaryKeyPloyParam;

	private Label label_dBColumnName;
	private NullToEmptyText text_dBColumnName;

	private Label label_defaultValue;
	private NullToEmptyText text_defaultValue;

	private Label label_dBDataLength;
	private NullToEmptyText text_dBDataLength;

	private Label label_dBDataPrecision;
	private NullToEmptyText text_dBDataPrecision;

	private Group group_primarykey;

	private Group group_persitence;

	private PropertyEditorDialog parentDialog;
	protected Object dBColumnName;
	protected Object displayName;

	private boolean isChooseDataType;

	public PropertyBasicInfoPage(Composite parent, int style) {
		super(parent, style);
	}

	public PropertyBasicInfoPage(Composite parent, int style,
			PropertyEditorDialog parentDialog) {
		super(parent, style);
		this.parentDialog = parentDialog;
		createContents(this);
		addListeners();
	}

	private void createContents(Composite composite) {
		GridLayout layout = new GridLayout();
		layout.numColumns = 4;
		composite.setLayout(layout);

		GridData layoutData_normal = new GridData();
		layoutData_normal = new GridData(GridData.FILL_HORIZONTAL);

		label_name = new Label(composite, SWT.NONE);
		label_name.setText(ModelElementEditorDialog.NAME_TEXT);
		text_name = new NullToEmptyText(composite, SWT.BORDER);
		text_name.setLayoutData(layoutData_normal);

		label_displayName = new Label(composite, SWT.NONE);
		label_displayName.setText(ModelElementEditorDialog.DISPLAYNAME_TEXT);
		text_displayName = new NullToEmptyText(composite, SWT.BORDER);
		text_displayName.setLayoutData(layoutData_normal);

		label_dBDataType = new Label(composite, SWT.NONE);
		label_dBDataType.setText(DATATYPE_LABEL_TEXT);
		combo_dBDataType = new Combo(composite, SWT.DROP_DOWN | SWT.READ_ONLY);
		combo_dBDataType.setLayoutData(layoutData_normal);

		label_group = new Label(composite, SWT.NONE);
		label_group.setText(GROUPINFO_LABEL_TEXT);
		combo_group = new Combo(composite, SWT.DROP_DOWN);
		combo_group.setLayoutData(layoutData_normal);

		// 持久化策略分组
		group_persitence = new Group(composite, SWT.FILL);
		group_persitence.setText(GROUP_PERSITENCE_TEXT);
		GridData layoutData_group = new GridData();
		layoutData_group = new GridData(GridData.FILL_HORIZONTAL);
		layoutData_group.horizontalSpan = 4;
		group_persitence.setLayoutData(layoutData_group);
		GridLayout gridLayout_group = new GridLayout();
		gridLayout_group.numColumns = 6;
		gridLayout_group.makeColumnsEqualWidth = true;
		group_persitence.setLayout(gridLayout_group);

		btn_isPersistence = new Button(group_persitence, SWT.CHECK);
		btn_isPersistence.setText(BUTTON_ISPERSISTENCE_TEXT);
		btn_isPrimaryKey = new Button(group_persitence, SWT.CHECK);
		btn_isPrimaryKey.setText(BUTTON_ISPRIMARYKEY_TEXT);
		
		btn_nullable = new Button(group_persitence, SWT.CHECK);
		btn_nullable.setText(BUTTON_NULLABLE_TEXT);
		btn_nullable.setSelection(true);
		// btn_indexColumn = new Button(group_persitence, SWT.CHECK);
		// btn_indexColumn.setText(BUTTON_ISINDEXCOLUMN_TEXT);
		btn_readOnly = new Button(group_persitence, SWT.CHECK);
		btn_readOnly.setText(BUTTON_READONLY_TEXT);
		btn_isUnique = new Button(group_persitence, SWT.CHECK);
		btn_isUnique.setText(BUTTON_ISUNIQUE_TEXT);

		// 主键分组
		group_primarykey = new Group(group_persitence, SWT.FILL);
		group_primarykey.setText(GROUP_PRIMARYKET_TEXT);
		GridData layoutData_primarykey = new GridData();
		layoutData_primarykey = new GridData(GridData.FILL_HORIZONTAL);
		layoutData_primarykey.horizontalSpan = 6;
		layoutData_primarykey.grabExcessHorizontalSpace = true;
		group_primarykey.setLayoutData(layoutData_primarykey);

		GridLayout gridLayout_primarykey = new GridLayout();
		gridLayout_primarykey.numColumns = 4;
		group_primarykey.setLayout(gridLayout_primarykey);

		label_priamryKeyPloy = new Label(group_primarykey, SWT.NONE);
		label_priamryKeyPloy.setText(PRIMARYKEYPLOY_LABEL_TEXT);

		combo_priamryKeyPloy = new Combo(group_primarykey, SWT.DROP_DOWN
				| SWT.READ_ONLY);
		combo_priamryKeyPloy.setLayoutData(new GridData(
				GridData.FILL_HORIZONTAL));

		label_primaryKeyPloyParam = new Label(group_primarykey, SWT.NONE);
		label_primaryKeyPloyParam.setText(PRIMARYKEYPLOY_PARAM_LABEL_TEXT);
		text_primaryKeyPloyParam = new NullToEmptyText(group_primarykey,
				SWT.BORDER);
		text_primaryKeyPloyParam.setVisible(false);
		label_primaryKeyPloyParam.setVisible(false);
		GridData layoutData2 = new GridData(GridData.FILL_HORIZONTAL);
		text_primaryKeyPloyParam.setLayoutData(layoutData2);

		label_dBColumnName = new Label(group_persitence, SWT.NONE);
		label_dBColumnName.setText(DBCOLUMNNAME_LABEL_TEXT);
		text_dBColumnName = new NullToEmptyText(group_persitence, SWT.BORDER);
		text_dBColumnName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER,
				true, false, 2, 1));

		label_defaultValue = new Label(group_persitence, SWT.NONE);
		label_defaultValue.setText(DEFAULTVALUE_LABEL_TEXT);
		text_defaultValue = new NullToEmptyText(group_persitence, SWT.BORDER);
		text_defaultValue.setLayoutData(new GridData(SWT.FILL, SWT.CENTER,
				true, false, 2, 1));

		label_dBDataLength = new Label(group_persitence, SWT.NONE);
		label_dBDataLength.setText(DBDATALENGTH_LABEL_TEXT);
		text_dBDataLength = new NullToEmptyText(group_persitence, SWT.BORDER);
		text_dBDataLength.setLayoutData(new GridData(SWT.FILL, SWT.CENTER,
				true, false, 2, 1));

		label_dBDataPrecision = new Label(group_persitence, SWT.NONE);
		label_dBDataPrecision.setText(DBDATAPRECISION_LABEL_TEXT);
		text_dBDataPrecision = new NullToEmptyText(group_persitence, SWT.BORDER);
		text_dBDataPrecision.setLayoutData(new GridData(SWT.FILL, SWT.CENTER,
				true, false, 2, 1));

		label_remark = new Label(composite, SWT.NONE);
		label_remark.setText(ModelElementEditorDialog.REMARK_TEXT);
		text_remark = new NullToEmptyText(composite, SWT.BORDER | SWT.MULTI
				| SWT.VERTICAL | SWT.WRAP);
		text_remark.setLayoutData(new GridData(SWT.FILL, SWT.LEFT, true, false,
				4, 1));
		GridData layoutData_remark = new GridData();
		layoutData_remark = new GridData(GridData.FILL_BOTH);
		layoutData_remark.horizontalSpan = 4;
		// layoutData_remark.heightHint = 60;
		text_remark.setLayoutData(layoutData_remark);

	}

	private void addListeners() {
		combo_dBDataType.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				isChooseDataType = true;
				parentDialog.getPropertyEditorPage().change(
						combo_dBDataType.getText());
				initDataType();
			}
		});
		btn_isPrimaryKey.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				group_primarykey.setEnabled(btn_isPrimaryKey.getSelection());
				if (btn_isPrimaryKey.getSelection()) {
					if (combo_priamryKeyPloy.getSelectionIndex() < 0) {
						String type = combo_dBDataType.getText().trim();
						if ("long".equalsIgnoreCase(type) || "integer".equalsIgnoreCase(type) || "short".equalsIgnoreCase(type) ||
								"folat".equalsIgnoreCase(type) || "double".equalsIgnoreCase(type) || "big_decimal".equalsIgnoreCase(type)) {
							combo_dBDataType.removeAll();
							combo_dBDataType.setItems(DataType.getDataTypeString());
							combo_dBDataType.select(0);
							combo_priamryKeyPloy.select(PrimaryKeyPloyType
									.getIndex(PrimaryKeyPloyType.IDENTITY.getValue()));
						}else{
							combo_priamryKeyPloy.select(PrimaryKeyPloyType
									.getIndex(PrimaryKeyPloyType.UUID.getValue()));
						}
					}
					text_defaultValue.setEnabled(false);
//					if (combo_priamryKeyPloy.getSelectionIndex() == 0) {
					if(combo_priamryKeyPloy.getText().equals(PrimaryKeyPloyType.UUID.getDisplayValue())){
						text_defaultValue.setEnabled(false);
					}
//					if (combo_priamryKeyPloy.getSelectionIndex() == 2) {
					if(combo_priamryKeyPloy.getText().equals(PrimaryKeyPloyType.SEQUENCE.getDisplayValue())){
						combo_dBDataType.removeAll();
						combo_dBDataType.setItems(DataType.getDataTypeString());
						combo_dBDataType.select(0);
					}
					if(combo_priamryKeyPloy.getText().equals(PrimaryKeyPloyType.IDENTITY.getDisplayValue())){
						combo_dBDataType.removeAll();
						combo_dBDataType.setItems(DataType.getDataTypeString());
						combo_dBDataType.select(0);
					}
					
					text_defaultValue.setText("");
					text_defaultValue.setEnabled(false);

				} else {
					String curType = combo_dBDataType.getText();
					combo_dBDataType.removeAll();
					combo_dBDataType.setItems(DataType.getDataTypesString());
					if(combo_priamryKeyPloy.getText().equals(PrimaryKeyPloyType.SEQUENCE.getDisplayValue())){
//					if (combo_priamryKeyPloy.getSelectionIndex() == 2) {
						text_defaultValue.setEnabled(true);
						
					}
					if(curType.equals(DataType.Long.getValue_hibernet())){
						combo_dBDataType.select(1);
					}
					if(curType.equals(DataType.String.getValue_hibernet())){
						combo_dBDataType.select(8);
					}
				}
				isPrimaryKeyChanged();
				
			}
		});
		btn_isPersistence.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				isPersistenceChanged();
			}
		});
		
		/*
		 * btn_isPrimaryKey.addSelectionListener(new SelectionAdapter() { public
		 * void widgetSelected(SelectionEvent e) {
		 * if(btn_isPrimaryKey.getSelection()){ //必填校验器
		 * checkForAdd(ValidatorType.Nullable); }else{
		 * checkForDelete(ValidatorType.Nullable); } } });
		 */
		btn_nullable.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (!btn_nullable.getSelection()) {
					// 必填校验器
					checkForAdd(ValidatorType.Nullable);
				} else {
					checkForDelete(ValidatorType.Nullable);
				}
			}
		});

		btn_isUnique.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				/*if (btn_isUnique.getSelection()) {
					// 唯一校验器
					checkForAdd(ValidatorType.Unique);
				} else {
					checkForDelete(ValidatorType.Unique);
				}*/
			}
		});

		text_name.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				if (dBColumnName == null)
					text_dBColumnName.setText(BusinessModelUtil.getProColumnName(text_name.getText()));
				if (displayName == null)
					text_displayName.setText(text_name.getText());
			}
		});
		text_displayName.addFocusListener(new FocusListener() {
			public void focusLost(FocusEvent e) {
			}

			public void focusGained(FocusEvent e) {
				text_displayName.selectAll();

			}
		});
		text_dBColumnName.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				dBColumnName = text_dBColumnName.getText();
			}
		});
		text_displayName.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				displayName = text_displayName.getText();
			}
		});
		combo_priamryKeyPloy.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				priamryKeyPloyChanged();
			}

		});
		
		/*
		 * combo_dBDataType.addSelectionListener(new SelectionAdapter(){ public
		 * void widgetSelected(SelectionEvent e) { DataType dataType =
		 * DataType.getDataType(combo_dBDataType.getText());
		 * checkForDeleteAll();//清除除了Nullable以外的所有校验器 switch(dataType){ case
		 * Big_decimal: checkForAdd(ValidatorType.Number); break; case Double:
		 * checkForAdd(ValidatorType.Number); break; case Float:
		 * checkForAdd(ValidatorType.Number); break; case Integer:
		 * checkForAdd(ValidatorType.Integer); break; case Long:
		 * checkForAdd(ValidatorType.Integer); break; case Short:
		 * checkForAdd(ValidatorType.Integer); break; case Date:
		 * checkForAdd(ValidatorType.DateTime); break; case Time:
		 * checkForAdd(ValidatorType.DateTime); break; case Timestamp:
		 * checkForAdd(ValidatorType.DateTime); break; } parentDialog.refresh();
		 * } });
		 */
	}

	/**
	 * 主键生成策略改变
	 */
	private void priamryKeyPloyChanged() {
//		int index = combo_priamryKeyPloy.getSelectionIndex();
		if (combo_priamryKeyPloy.getText().equals(PrimaryKeyPloyType.UUID.getDisplayValue())) {
//		if (index == 0) {
			label_primaryKeyPloyParam.setVisible(false);
			text_primaryKeyPloyParam.setVisible(false);
			text_dBDataLength.setText("32");
			text_dBDataLength.setEnabled(false);
			combo_dBDataType.removeAll();
			combo_dBDataType.setItems(DataType.getDataTypesString());
			combo_dBDataType.select(8);
			combo_dBDataType.setEnabled(false);
		} /*else if (index == 1) {*/
		else if (combo_priamryKeyPloy.getText().equals(PrimaryKeyPloyType.SEQUENCE.getDisplayValue())) {
			label_primaryKeyPloyParam.setVisible(true);
			text_primaryKeyPloyParam.setVisible(true);
			text_dBDataLength.setEnabled(true);
			combo_dBDataType.setEnabled(true);
			combo_dBDataType.removeAll();
			combo_dBDataType.setItems(DataType.getDataTypesString());
			combo_dBDataType.select(1);
			combo_dBDataType.setEnabled(false);
		}else if (combo_priamryKeyPloy.getText().equals(PrimaryKeyPloyType.IDENTITY.getDisplayValue())) {
			label_primaryKeyPloyParam.setVisible(false);
			text_primaryKeyPloyParam.setVisible(false);
			text_dBDataLength.setEnabled(true);
			combo_dBDataType.setEnabled(true);
			combo_dBDataType.removeAll();
			combo_dBDataType.setItems(DataType.getDataTypesString());
			combo_dBDataType.select(1);
			combo_dBDataType.setEnabled(false);
		} else {
			label_primaryKeyPloyParam.setVisible(false);
			text_primaryKeyPloyParam.setVisible(false);
			String type = null;
			if(combo_dBDataType.getText().equals(DataType.String.getValue_hibernet()) || combo_dBDataType.getText().equals(DataType.Long.getValue_hibernet())){
				type = combo_dBDataType.getText();
			}
			combo_dBDataType.removeAll();
			combo_dBDataType.setItems(DataType.getDataTypeString());
			combo_dBDataType.select(0);
			if(type != null){
				combo_dBDataType.select(getIndex(combo_dBDataType.getItems(), type));
			}
			text_dBDataLength.setEnabled(true);
			combo_dBDataType.setEnabled(true);
		}
		setReverse();
	}
	/**
	 * 主键复选框改变
	 */
	private void isPrimaryKeyChanged() {
		boolean isPrimaryKey = btn_isPrimaryKey.getSelection();
		combo_priamryKeyPloy.setEnabled(isPrimaryKey);
		text_primaryKeyPloyParam.setEditable(isPrimaryKey);
		if (isPrimaryKey) {
			btn_nullable.setSelection(false);
			btn_nullable.setEnabled(false);
			btn_isUnique.setSelection(true);
			btn_isUnique.setEnabled(false);
			text_defaultValue.setEnabled(false);
			// combo_dBDataType.setEnabled(false);
			int index = combo_priamryKeyPloy.getSelectionIndex();
			if (index < 0) {
				combo_priamryKeyPloy.select(PrimaryKeyPloyType
						.getIndex(PrimaryKeyPloyType.UUID.getValue()));
				combo_dBDataType.select(8);
				combo_dBDataType.setEnabled(false);
			}
			if (index == 0) {
				combo_dBDataType.removeAll();
				combo_dBDataType.setItems(DataType.getDataTypesString());
				text_dBDataLength.setText("32");
				text_dBDataLength.setEnabled(false);
				combo_dBDataType.select(8);
				combo_dBDataType.setEnabled(false);
			}
			if (index == 1 || index == 3) {
				combo_dBDataType.removeAll();
				combo_dBDataType.setItems(DataType.getDataTypesString());
				combo_dBDataType.select(1);
				combo_dBDataType.setEnabled(false);
			}
			if (index == 2) {
				String type = null;
				if(combo_dBDataType.getText().equals(DataType.String.getValue_hibernet()) || combo_dBDataType.getText().equals(DataType.Long.getValue_hibernet())){
					type = combo_dBDataType.getText();
				}
				combo_dBDataType.removeAll();
				combo_dBDataType.setItems(DataType.getDataTypeString());
				combo_dBDataType.select(0);
				if(type != null){
					combo_dBDataType.select(getIndex(combo_dBDataType.getItems(), type));
				}
			}
		} else {
			btn_nullable.setEnabled(true);
			btn_nullable.setSelection(true);
			btn_isUnique.setEnabled(true);
			btn_isUnique.setSelection(false);
			text_defaultValue.setEnabled(true);
			int index = combo_dBDataType.getSelectionIndex();
			combo_dBDataType.setEnabled(true);
			if (index >= 0) {
				combo_dBDataType.select(index);
				DataType dataType = DataType.getDataType(index);
				text_dBDataLength.setEnabled(dataType.isHasDataLength());
				text_dBDataLength.setText(dataType.isHasDataLength() ? dataType
						.getDefaultLength() + "" : "");
				text_dBDataLength
						.setEnabled(dataType.isHasDataLength()
								&& !(dataType.getValue_hibernet()
										.equals(DataType.Character
												.getValue_hibernet())));
				text_dBDataPrecision.setEnabled(dataType.isHasDataPrecision());
				text_dBDataPrecision
						.setText(dataType.isHasDataPrecision() ? dataType
								.getDefaultPrecision() + "" : "");
			}
		}
	}
	/**
	 * 持久化复选框改变
	 */
	private void isPersistenceChanged() {
		boolean isPersitence = btn_isPersistence.getSelection();
		boolean isPrimaryKey = btn_isPrimaryKey.getSelection();
		btn_isPrimaryKey.setEnabled(isPersitence);
		combo_priamryKeyPloy.setEnabled(isPersitence && isPrimaryKey);
		btn_nullable.setEnabled(isPersitence);
		// btn_indexColumn.setEnabled(isPersitence);
		btn_readOnly.setEnabled(isPersitence);
		btn_isUnique.setEnabled(isPersitence);

		group_primarykey.setEnabled(isPersitence);

		text_dBColumnName.setEnabled(isPersitence);
		text_defaultValue.setEnabled(isPersitence);

		if (isPersitence == false) {
			text_dBDataLength.setEnabled(isPersitence);
			text_dBDataPrecision.setEnabled(isPersitence);
		} else {
			initDataType();
		}

		// parentDialog.getTabFolder().getItem(1).getControl().setEnabled(isPersitence);
		if (isPersitence == true && isPrimaryKey == true) {
			btn_nullable.setEnabled(false);
			btn_isUnique.setEnabled(false);
		}

	}
	/**
	 * 根据数据类型判断字段长度和字段精度
	 */
	private void initDataType() {
		int index = combo_dBDataType.getSelectionIndex();
		if (index >= 0) {
			DataType dataType = DataType.getDataType(combo_dBDataType.getText());
//			DataType dataType = DataType.getDataType(index);
			text_dBDataLength.setEnabled(dataType.isHasDataLength());
			boolean isDblEnable = dataType.isHasDataLength()
					&& !(dataType.getValue_hibernet().equals(DataType.Character
							.getValue_hibernet()));
			text_dBDataLength.setEnabled(isDblEnable);
			if(!dataType.isHasDataLength())
				text_dBDataLength.setText("");
			// char 和 boolean 类型长度为1
			if(dataType.equals(DataType.Character) || dataType.equals(DataType.Boolean))
				text_dBDataLength.setText("1");
			else 
				text_dBDataLength.setText( dataType
						.getDefaultLength() + "" );
				
			text_dBDataPrecision.setEnabled(dataType.isHasDataPrecision());
			text_dBDataPrecision
					.setText(dataType.isHasDataPrecision() ? dataType
							.getDefaultPrecision() + "" : "");
			// int index1 = combo_priamryKeyPloy.getSelectionIndex();
			// if(index1 == 0 && btn_isPrimaryKey.getSelection() ){
			// text_dBDataLength.setEnabled(false);
			// }
			// if(index1 == 0 && btn_isPrimaryKey.getSelection() &&
			// dataType.getValue_hibernet().equals(DataType.String.getValue_hibernet())){
			// text_dBDataLength.setText("32");
			// }
		}

	}

	public void initControlValue() {
		// 初始化下拉框的值
		combo_dBDataType.setItems(DataType.getDataTypesString());
		combo_dBDataType.select(DataType.getIndex(DataType.String
				.getValue_hibernet()));
		initDataType();
		combo_priamryKeyPloy.setItems(PrimaryKeyPloyType
				.getPrimaryKeyPloyTypesString());
		combo_group.setItems(getGroupDisplays());

		Property property = parentDialog.getPropertyCopy();
		if (parentDialog.getOperationType().equals(
				ModelElementEditorDialog.OPERATION_TYPE_EDIT)) {
			text_name.setText(StringUtil.convertNull2EmptyStr(property
					.getName()));
			text_displayName.setText(StringUtil.convertNull2EmptyStr(property
					.getDisplayName()));
			displayName = text_displayName.getText();
			text_remark.setText(StringUtil.convertNull2EmptyStr(property
					.getRemark()));
			int index;
			index = DataType.getIndex(((Property) property).getDataType());
			if (index >= 0) {
				combo_dBDataType.select(index);
				initDataType();
			}

			if (property instanceof PKProperty) {
				PKProperty pkProperty = (PKProperty) property;
				String primaryKeyPloy = pkProperty.getPrimaryKeyPloy();
				index = PrimaryKeyPloyType.getIndex(primaryKeyPloy);
				if (index >= 0) {
					combo_priamryKeyPloy.select(index);
					priamryKeyPloyChanged();
					// 主键为序列时参数
					if (PrimaryKeyPloyType.SEQUENCE.getValue().equals(
							primaryKeyPloy)) {
						label_primaryKeyPloyParam.setVisible(true);
						text_primaryKeyPloyParam.setVisible(true);
						text_primaryKeyPloyParam.setText(pkProperty
								.getPrimaryKeyParams().get(
										PKProperty.PARAM_KEY_SEQUENCENAME));
					}
				}
				text_name.setText(pkProperty.getName());
				btn_isPersistence.setSelection(pkProperty.isPersistence());
				isPersistenceChanged();
				btn_isPrimaryKey.setSelection(pkProperty.isPrimaryKey());
				Property pro = parentDialog.getPropertyCopy();
				
				isPrimaryKeyChanged();
				btn_nullable.setSelection(pkProperty.isNullable());
				// btn_indexColumn.setSelection(pkProperty.isIndexedColumn());
				btn_readOnly.setSelection(pkProperty.isReadOnly());
				btn_isUnique.setSelection(pkProperty.isUnique());
				index = -1;
				if (pkProperty.getGroup() != null) {
					index = getGroupIndex(pkProperty.getGroup().getId());
					if (index >= 0) {
						combo_group.select(index);
					}
				}
				text_dBColumnName.setText(pkProperty.getdBColumnName());
				dBColumnName = text_dBColumnName.getText();
				text_defaultValue.setText(pkProperty.getDefaultValue());
				text_dBDataLength.setText(pkProperty.getdBDataLength());
				text_dBDataPrecision.setText(pkProperty.getdBDataPrecision());
				//根据字段类型设置主键生成策略
				if(parentDialog.getOperationType().equals(
						ModelElementEditorDialog.OPERATION_TYPE_EDIT)
						&& IModelElement.STEREOTYPE_REVERSE.equals(pro.getParent().getStereotype())
						){
					if(pro.getDataType().equals(DataType.String.getValue_hibernet())){
						combo_priamryKeyPloy.setItems(PrimaryKeyPloyType.getPrimaryKeyPloyTypesByString());
						if(primaryKeyPloy.equals(PrimaryKeyPloyType.UUID.getValue()))
							combo_priamryKeyPloy.select(0);
						if(primaryKeyPloy.equals(PrimaryKeyPloyType.MANUAL.getValue()))
							combo_priamryKeyPloy.select(1);
					}
					if(pro.getDataType().equals(DataType.Long.getValue_hibernet())){
						combo_priamryKeyPloy.setItems(PrimaryKeyPloyType.getPrimaryKeyPloyTypesByLong());
						if(primaryKeyPloy.equals(PrimaryKeyPloyType.SEQUENCE.getValue()))
							combo_priamryKeyPloy.select(0);
						if(primaryKeyPloy.equals(PrimaryKeyPloyType.MANUAL.getValue()))
							combo_priamryKeyPloy.select(1);
						if(primaryKeyPloy.equals(PrimaryKeyPloyType.IDENTITY.getValue()))
							combo_priamryKeyPloy.select(2);
					}
					
				}
			} else if (property instanceof PersistenceProperty) {
				PersistenceProperty persitenceProperty = (PersistenceProperty) property;
				index = -1;
				if (persitenceProperty.getGroup() != null) {
					index = getGroupIndex(persitenceProperty.getGroup().getId());
					if (index >= 0) {
						combo_group.select(index);
					}
				}

				text_name.setText(persitenceProperty.getName());
				btn_isPersistence.setSelection(persitenceProperty
						.isPersistence());
				isPersistenceChanged();
				btn_isPrimaryKey
						.setSelection(persitenceProperty.isPrimaryKey());
				isPrimaryKeyChanged();
				btn_nullable.setSelection(persitenceProperty.isNullable());
				// btn_indexColumn.setSelection(persitenceProperty.isIndexedColumn());
				btn_readOnly.setSelection(persitenceProperty.isReadOnly());
				btn_isUnique.setSelection(persitenceProperty.isUnique());

				text_dBColumnName.setText(persitenceProperty.getdBColumnName());
				dBColumnName = text_dBColumnName.getText();
				text_defaultValue.setText(persitenceProperty.getDefaultValue());
				text_dBDataLength.setText(persitenceProperty.getdBDataLength());
				text_dBDataPrecision.setText(persitenceProperty
						.getdBDataPrecision());
			}

			else if (property instanceof Property) {
				Property pro = (Property) property;
				index = -1;
				if (pro.getGroup() != null) {
					index = getGroupIndex(pro.getGroup().getId());
					if (index >= 0) {
						combo_group.select(index);
					}
				}

				text_name.setText(pro.getName());
				btn_isPersistence.setSelection(pro.isPersistence());
				isPersistenceChanged();
			}
		} else {
			btn_isPersistence.setSelection(true);
			isPersistenceChanged();
			combo_priamryKeyPloy.setEnabled(false);
			text_primaryKeyPloyParam.setEditable(false);
			int propertySize = parentDialog.getPropertySize() + 1;
			String defaultName = "property" + (propertySize);
			for (Property proper : parentDialog.getProperties()) {
				if (proper.getName().equals(defaultName)) {
					propertySize++;
					defaultName = "property" + (propertySize);
				}
			}
			text_name.setText(defaultName);
			text_displayName.setText(defaultName);
			text_dBColumnName.setText(BusinessModelUtil.getProColumnName(defaultName));
		}
		if (parentDialog.getOperationType().equals(
				ModelElementEditorDialog.OPERATION_TYPE_EDIT)
				&& IModelElement.STEREOTYPE_REVERSE.equals(property.getParent().getStereotype())
				&& property instanceof PersistenceProperty
				) {
			combo_dBDataType.setEnabled(false);
			combo_priamryKeyPloy.setEnabled(false);
			btn_isPersistence.setEnabled(false);
			btn_isPrimaryKey.setEnabled(false);
			btn_isUnique.setEnabled(false);
			btn_nullable.setEnabled(false);
			btn_readOnly.setEnabled(false);
			text_dBColumnName.setEnabled(false);
			text_dBDataLength.setEnabled(false);
			text_dBDataPrecision.setEnabled(false);
		}
		if (parentDialog.getOperationType().equals(
				ModelElementEditorDialog.OPERATION_TYPE_EDIT)
				&& IModelElement.STEREOTYPE_REVERSE.equals(property.getParent().getStereotype())
				&& !(property instanceof PersistenceProperty)
				) {
			combo_priamryKeyPloy.setEnabled(false);
			btn_isPersistence.setEnabled(false);
			btn_isPrimaryKey.setEnabled(false);
			btn_isUnique.setEnabled(false);
			btn_nullable.setEnabled(false);
			btn_readOnly.setEnabled(false);
			text_dBColumnName.setEnabled(false);
			text_dBDataLength.setEnabled(false);
			text_dBDataPrecision.setEnabled(false);
		}
		if (parentDialog.getOperationType().equals(
				ModelElementEditorDialog.OPERATION_TYPE_ADD)
				&& IModelElement.STEREOTYPE_REVERSE.equals(property.getParent().getStereotype())
				) {
//			combo_dBDataType.setEnabled(false);
			combo_priamryKeyPloy.setEnabled(false);
			btn_isPersistence.setSelection(false);
			btn_isPrimaryKey.setSelection(false);
			btn_isUnique.setSelection(false);
			btn_nullable.setSelection(false);
			btn_readOnly.setSelection(false);
			btn_isPersistence.setEnabled(false);
			btn_isPrimaryKey.setEnabled(false);
			btn_isUnique.setEnabled(false);
			btn_nullable.setEnabled(false);
			btn_readOnly.setEnabled(false);
			text_dBColumnName.setEnabled(true);
			text_dBDataLength.setEnabled(true);
			text_dBDataPrecision.setEnabled(false);
			isPersistenceChanged();
		}
		if (parentDialog.getOperationType().equals(
				ModelElementEditorDialog.OPERATION_TYPE_EDIT)
				&& IModelElement.STEREOTYPE_REVERSE.equals(property.getStereotype())
				) {
			combo_dBDataType.setEnabled(false);
			combo_priamryKeyPloy.setEnabled(property instanceof PKProperty);
			btn_isPersistence.setEnabled(false);
			btn_isPrimaryKey.setEnabled(false);
			btn_isUnique.setEnabled(false);
			btn_nullable.setEnabled(false);
			btn_readOnly.setEnabled(false);
			text_dBColumnName.setEnabled(false);
			text_dBDataLength.setEnabled(false);
			text_dBDataPrecision.setEnabled(false);
			text_defaultValue.setEnabled(!(property instanceof PKProperty));
		}
	}

	
	public void resetDataLength(int length) {
		this.text_dBDataLength.setText(length + "");
	}

	
	/**
	 * 反向业务实体的只读控制
	 */
	private void setReverse() {
		if (parentDialog.getOperationType().equals(
				ModelElementEditorDialog.OPERATION_TYPE_EDIT)
				&& IModelElement.STEREOTYPE_REVERSE.equals(parentDialog.getEditingElement().getStereotype())
				) {
			combo_dBDataType.setEnabled(false);
			combo_priamryKeyPloy.setEnabled(parentDialog.getPropertyCopy() instanceof PKProperty);
			btn_isPersistence.setEnabled(false);
			btn_isPrimaryKey.setEnabled(false);
			btn_isUnique.setEnabled(false);
			btn_nullable.setEnabled(false);
			btn_readOnly.setEnabled(false);
			text_dBColumnName.setEnabled(false);
			text_dBDataLength.setEnabled(false);
			text_dBDataPrecision.setEnabled(false);
			text_defaultValue.setEnabled(!(parentDialog.getPropertyCopy() instanceof PKProperty));
		}
	}
	
	/**
	 * 检查并删除校验器
	 * @param validatorType
	 */
	private void checkForDelete(ValidatorType validatorType) {
		List<Validator> validators = parentDialog.getPropertyValidatorPage()
				.getTableItems();
		if (validators != null) {
			Iterator<Validator> it = validators.iterator();
			while (it.hasNext()) {
				Validator validator = (Validator) it.next();
				if (validator.getValidatorType().equals(
						validatorType.getValue())) {
					// 存在则删除
					it.remove();
				}
			}
			parentDialog.refresh();
		}
	}
	/**
	 * 检查并添加校验器
	 * @param validatorType
	 */
	private void checkForAdd(ValidatorType validatorType) {
		List<Validator> validators = parentDialog.getPropertyValidatorPage()
				.getTableItems();
		if (validators != null) {
			for (Validator validator : validators) {
				if (validator.getValidatorType().equals(
						validatorType.getValue())) {
					// 存在，则不添加
					return;
				}
			}
			// 不存在，则添加
			validators.add(0, generateValidator(validatorType));
			parentDialog.refresh();
		}
	}

	/**
	 * 根据校验器类型生成校验器
	 * @param validatorType
	 * @return
	 */
	private Validator generateValidator(ValidatorType validatorType) {
		Validator newValidator = new Validator();
		newValidator.setValidatorType(validatorType.getValue());
		newValidator.setName(validatorType.getValue());
		newValidator.setDisplayName(validatorType.getDisplayValue());
		newValidator.setValidatorMessage(validatorType.getErrorMessage());
		return newValidator;
	}
	
	public boolean validateInput() {
		if (text_name.getText().trim().length() == 0) {
			parentDialog.setErrorMessage(ERROR_MESSAGE_NAME_NULLABLE);
			return false;
		}
		if (!ValidatorUtil.isFirstLowercase(text_name.getText())) {
			parentDialog.setErrorMessage(ERROR_MESSAGE_NAME_LOWER);
			return false;
		}
		if (!ValidatorUtil.valiNameLength(text_name.getText())) {
			parentDialog.setErrorMessage(ERROR_MESSAGE_NAME_LENGTH);
			return false;
		}
		if (!ValidatorUtil.valiDisplayName(text_displayName.getText())) {
			parentDialog.setErrorMessage(ERROR_DISPLAYNAME);
			return false;
		}
		if (!ValidatorUtil.valiDisplayName(combo_group.getText())) {
			parentDialog.setErrorMessage(ERROR_GROUPDISPLAYNAME);
			return false;
		}
		if (!ValidatorUtil.valiDisplayNameLength(text_displayName.getText())) {
			parentDialog.setErrorMessage(ERROR_DISPLAYNAME_LENGTH);
			return false;
		}
		if (!ValidatorUtil.valiDisplayNameLength(combo_group.getText())) {
			parentDialog.setErrorMessage(ERROR_GROUPDISPLAYNAME_LENGTH);
			return false;
		}
//		if (isNameExist()) {
//			parentDialog.setErrorMessage(ERROR_MESSAGE_NAME_UNIQUE);
//			return false;
//		}
		if (CheckerUtil.checkJava(text_name.getText())) {
			parentDialog.setErrorMessage(ERROR_MESSAGE_NAME_JAVA);
			return false;
		}

		if (CheckerUtil.checkSguap(text_name.getText())) {
			parentDialog.setErrorMessage(ERROR_MESSAGE_NAME_SGUAP);
			return false;
		}
		if (text_displayName.getText().trim().length() == 0) {
			parentDialog.setErrorMessage(ERROR_MESSAGE_DISPLAYNAME_NULLABLE);
			return false;
		}
		if (!ValidatorUtil.valiRemarkLength(text_remark.getText())) {
			parentDialog.setErrorMessage(TOOLONG_ERROR_REMARK);
			return false;
		}
		if (btn_isPersistence.getSelection() == false) {
			parentDialog.setErrorMessage(null);
			return true;
		}
		if (combo_dBDataType.getSelectionIndex() < 0) {
			parentDialog.setErrorMessage(ERROR_MESSAGE_DATATYPE_NULLABLE);
			return false;
		}
		if (btn_isPersistence.getSelection() == true
				&& btn_isPrimaryKey.getSelection() == true) {
			if (combo_priamryKeyPloy.getSelectionIndex() < 0) {
				parentDialog
						.setErrorMessage(ERROR_MESSAGE_PRIMARYKEY_PLOY_NULLABLE);
				return false;
			} else {
				if (combo_priamryKeyPloy.getText().equals(PrimaryKeyPloyType.SEQUENCE.getValue())) {
					if (combo_dBDataType.getText().equals(
							DataType.Long.getValue_hibernet())) {
//
//					} else if (combo_dBDataType.getText().equals(
//							DataType.Double.getValue_hibernet())) {
//
//					} else if (combo_dBDataType.getText().equals(
//							DataType.Float.getValue_hibernet())) {
//
//					} else if (combo_dBDataType.getText().equals(
//							DataType.Integer.getValue_hibernet())) {
//
//					} else if (combo_dBDataType.getText().equals(
//							DataType.Big_decimal.getValue_hibernet())) {
//
//					} else if (combo_dBDataType.getText().equals(
//							DataType.Short.getValue_hibernet())) {

					} else {
						parentDialog
								.setErrorMessage("主键生成策略为SEQUENCE，属性类型不是long类型");
						return false;
					}
				} else if (combo_priamryKeyPloy.getText().equals(PrimaryKeyPloyType.UUID.getDisplayValue())) {
					if (!combo_dBDataType.getText().equals(
							DataType.String.getValue_hibernet())) {
						parentDialog
								.setErrorMessage("主键生成策略为UUID，属性类型不是string");
						return false;
					}
				}
			}
			if (combo_priamryKeyPloy.getText().equals(PrimaryKeyPloyType.SEQUENCE.getDisplayValue())) {
				if (text_primaryKeyPloyParam.getText().trim().length() == 0) {
					parentDialog.setErrorMessage(ERROR_MESSAGE_POLY_NULLABLE);
					return false;
				}
			}
		}

		if (btn_isPrimaryKey.getSelection() == true
//				&& combo_priamryKeyPloy.getSelectionIndex() == 1
				&& combo_priamryKeyPloy.getText().equals(PrimaryKeyPloyType.SEQUENCE.getDisplayValue())
				) {
			if (text_primaryKeyPloyParam.getText().trim().length() == 0) {
				parentDialog.setErrorMessage(ERROR_MESSAGE_POLY_NULLABLE);
				return false;
			}
			if (!text_primaryKeyPloyParam.getText().trim()
					.matches("^[a-zA-Z][a-zA-Z_0-9]*$")) {
				parentDialog.setErrorMessage(ERROR_MESSAGE_POLY_RULE);
				return false;
			}
		}

		if (btn_isPersistence.getSelection() == true
				&& text_dBColumnName.getEnabled()
				&& CheckerUtil.checkSql(text_dBColumnName.getText())) {
			parentDialog.setErrorMessage(ERROR_MESSAGE_DATABASECOLUMNNAME_SQL);
			return false;
		}
		if (btn_isPersistence.getSelection() == true
				&& StringUtils.isEmpty(text_dBColumnName.getText())) {
			parentDialog.setErrorMessage("数据库字段名不能为空！");
			return false;
		}
		if (btn_isPersistence.getSelection() == true
				&& !ValidatorUtil.valiFirstNo_Name(text_dBColumnName.getText())) {
			parentDialog.setErrorMessage("数据库字段名格式不正确！\n（命名中只能包含英文字母、下划线及阿拉伯数字，且以英文字母开头！）");//^[a-zA-Z][a-zA-Z_0-9]*$
			return false;
		}
		
//		if (isDBColumnNameExist()) {
//			parentDialog.setErrorMessage("数据库字段名重复！");
//			return false;
//		}
		if (text_dBDataLength.isEnabled()) {
			if(text_dBDataLength.getText().trim().equals("")){
				parentDialog.setErrorMessage("字段长度不是数字或超出范围！");
				return false;
			}
			try {
				Integer.parseInt(text_dBDataLength.getText());
			} catch (Exception e) {
				parentDialog.setErrorMessage("字段长度不是数字或超出范围！");
				Logger.log(e);
				return false;
			}
		}

		if (text_dBDataPrecision.isEnabled()
				&& text_dBDataPrecision.getText() != null
				&& text_dBDataPrecision.getText().length() > 0) {
			try {
				Integer.valueOf(text_dBDataPrecision.getText());
			} catch (Exception e) {
				parentDialog.setErrorMessage("字段精度不是数字或超出范围");
				Logger.log(e);
				return false;
			}
		}
		if (combo_dBDataType.getText().equals(
				DataType.Big_decimal.getValue_hibernet())) {
			if (!"".equals(text_dBDataLength.getText())
					&& !"".equals(text_dBDataPrecision.getText())) {
				// modified by mqfdy
//				有效数位：从左边第一个不为0的数算起，小数点和负号不计入有效位数。
//			      number(p,s)
//			      p:1~38
//			      s:-84~127
			    int length = Integer.parseInt(text_dBDataLength.getText());
				
				int precision = Integer.valueOf(text_dBDataPrecision.getText());
				
				if(length < 1){
					parentDialog.setErrorMessage("Big_decimal类型的字段长度不能小于1！");
					return false;
				}
				if(length > 38){
					parentDialog.setErrorMessage("Big_decimal类型的字段长度不能大于38！");
					return false;
				}
				
				if(precision < -84){
					parentDialog.setErrorMessage("Big_decimal类型的字段精度不能小于-84！");
					return false;
				}
				if(precision > 127){
					parentDialog.setErrorMessage("Big_decimal类型的字段精度不能大于127！");
					return false;
				}
				
				
//				if (Integer.parseInt(text_dBDataLength.getText()) < Integer
//						.valueOf(text_dBDataPrecision.getText())) {
//					parentDialog.setErrorMessage("字段长度不能小于字段精度！");
//					return false;
//				}
			}
		}
		if (btn_isPersistence.getSelection() == true
				&& !"".equals(text_defaultValue.getText())) {
			if (combo_dBDataType.getText().equals(
					DataType.Float.getValue_hibernet())) {
				try {
					Float.parseFloat(text_defaultValue.getText());
				} catch (Exception e) {
					parentDialog.setErrorMessage("缺省值格式不正确！");
					Logger.log(e);
					return false;
				}
			}
			if (combo_dBDataType.getText().equals(
					DataType.Double.getValue_hibernet())) {
				try {
					Double.parseDouble(text_defaultValue.getText());
				} catch (Exception e) {
					parentDialog.setErrorMessage("缺省值格式不正确！");
					Logger.log(e);
					return false;
				}
			}
			if (combo_dBDataType.getText().equals(
					DataType.Boolean.getValue_hibernet())) {
				if (!text_defaultValue.getText().equals("true")
						&& !text_defaultValue.getText().equals("false")) {
					parentDialog.setErrorMessage("缺省值格式不正确！布尔值请填写\"true\"或\"false\"");
					return false;
				}
			}
			if (combo_dBDataType.getText().equals(
					DataType.Integer.getValue_hibernet())) {
				try {
					Integer.parseInt(text_defaultValue.getText());
				} catch (Exception e) {
					parentDialog.setErrorMessage("缺省值格式不正确！");
					Logger.log(e);
					return false;
				}
			}
			if (combo_dBDataType.getText().equals(
					DataType.Long.getValue_hibernet())) {
				try {
					Long.parseLong(text_defaultValue.getText());
				} catch (Exception e) {
					parentDialog.setErrorMessage("缺省值格式不正确！");
					Logger.log(e);
					return false;
				}
			}
			if (combo_dBDataType.getText().equals(
					DataType.Short.getValue_hibernet())) {
				try {
					Short.parseShort(text_defaultValue.getText());
				} catch (Exception e) {
					parentDialog.setErrorMessage("缺省值格式不正确！");
					Logger.log(e);
					return false;
				}
			}
			if (combo_dBDataType.getText().equals(
					DataType.Big_decimal.getValue_hibernet())) {
				try {
					Double.parseDouble(text_defaultValue.getText());
				} catch (Exception e) {
					parentDialog.setErrorMessage("缺省值格式不正确！");
					Logger.log(e);
					return false;
				}
			}
			if (combo_dBDataType.getText().equals(
					DataType.Byte.getValue_hibernet())) {
				try {
					int i = Integer.parseInt(text_defaultValue.getText());
					if (i < -128 || i > 255) {
						parentDialog.setErrorMessage("缺省值格式不正确！");
						return false;
					}
				} catch (Exception e) {
					parentDialog.setErrorMessage("缺省值格式不正确！");
					Logger.log(e);
					return false;
				}
			}
			if (combo_dBDataType.getText().equals(
					DataType.Date.getValue_hibernet())) {
//				if (DateTimeUtil.string2Date(text_defaultValue.getText(),
//						"yyyy-MM-dd") == null) {
				if(!ValidatorUtil.valiDate(text_defaultValue.getText())){	
					parentDialog.setErrorMessage("缺省值格式不正确！");
					return false;
				}
			}
			if (combo_dBDataType.getText().equals(
					DataType.Time.getValue_hibernet())) {
//				if (DateTimeUtil.string2Date(text_defaultValue.getText(),
//						"HH:mm:ss") == null) {
				if(!ValidatorUtil.valiTime(text_defaultValue.getText())){	
					parentDialog.setErrorMessage("缺省值格式不正确！");
					return false;
				}
			}
			if (combo_dBDataType.getText().equals(
					DataType.Timestamp.getValue_hibernet())) {
//				if (DateTimeUtil.string2Date(text_defaultValue.getText(),
//						"yyyy-MM-dd HH:mm:ss") == null) {
				if(!ValidatorUtil.valiDateTime(text_defaultValue.getText())){	
					parentDialog.setErrorMessage("缺省值格式不正确！");
					return false;
				}
			}
			if (combo_dBDataType.getText().equals(
					DataType.Character.getValue_hibernet())) {
				if (text_defaultValue.getText().length() > 1) {
					parentDialog.setErrorMessage("缺省值格式不正确！");
					return false;
				}
			}
		}
		if (btn_isPersistence.getSelection() == true
				&& !ValidatorUtil.valiNameLength(text_dBColumnName.getText())) {
			parentDialog.setErrorMessage("数据库字段名长度不能超过30！");
			return false;
		}

		parentDialog.setErrorMessage(null);
		return true;
	}

	public boolean validateDefault(EditorType curEditorType){
		if (btn_isPersistence.getSelection() == true
				&& !"".equals(text_defaultValue.getText())) {
			if (combo_dBDataType.getText().equals(
					DataType.Float.getValue_hibernet())) {
				try {
					Float.parseFloat(text_defaultValue.getText());
				} catch (Exception e) {
					parentDialog.setErrorMessage("缺省值格式不正确！");
					Logger.log(e);
					return false;
				}
			}
			if (combo_dBDataType.getText().equals(
					DataType.Double.getValue_hibernet())) {
				try {
					Double.parseDouble(text_defaultValue.getText());
				} catch (Exception e) {
					parentDialog.setErrorMessage("缺省值格式不正确！");
					Logger.log(e);
					return false;
				}
			}
			if (combo_dBDataType.getText().equals(
					DataType.Boolean.getValue_hibernet())) {
				if (!text_defaultValue.getText().toUpperCase(Locale.getDefault()).equals("TRUE")
						&& !text_defaultValue.getText().toUpperCase(Locale.getDefault())
								.equals("FALSE")) {
					parentDialog.setErrorMessage("缺省值格式不正确！");
					return false;
				}
			}
			if (combo_dBDataType.getText().equals(
					DataType.Integer.getValue_hibernet())) {
				try {
					Integer.parseInt(text_defaultValue.getText());
				} catch (Exception e) {
					parentDialog.setErrorMessage("缺省值格式不正确！");
					Logger.log(e);
					return false;
				}
			}
			if (combo_dBDataType.getText().equals(
					DataType.Long.getValue_hibernet())) {
				try {
					Long.parseLong(text_defaultValue.getText());
				} catch (Exception e) {
					parentDialog.setErrorMessage("缺省值格式不正确！");
					Logger.log(e);
					return false;
				}
			}
			if (combo_dBDataType.getText().equals(
					DataType.Short.getValue_hibernet())) {
				try {
					Short.parseShort(text_defaultValue.getText());
				} catch (Exception e) {
					parentDialog.setErrorMessage("缺省值格式不正确！");
					Logger.log(e);
					return false;
				}
			}
			if (combo_dBDataType.getText().equals(
					DataType.Big_decimal.getValue_hibernet())) {
				try {
					Double.parseDouble(text_defaultValue.getText());
				} catch (Exception e) {
					parentDialog.setErrorMessage("缺省值格式不正确！");
					Logger.log(e);
					return false;
				}
			}
			if (combo_dBDataType.getText().equals(
					DataType.Byte.getValue_hibernet())) {
				try {
					int i = Integer.parseInt(text_defaultValue.getText());
					if (i < -128 || i > 255) {
						parentDialog.setErrorMessage("缺省值格式不正确！");
						return false;
					}
				} catch (Exception e) {
					parentDialog.setErrorMessage("缺省值格式不正确！");
					Logger.log(e);
					return false;
				}
			}
			if (combo_dBDataType.getText().equals(
					DataType.Date.getValue_hibernet())) {
//				if (DateTimeUtil.string2Date(text_defaultValue.getText(),
//						"yyyy-MM-dd") == null) {
				if(!ValidatorUtil.valiDate(text_defaultValue.getText())){	
					parentDialog.setErrorMessage("缺省值格式不正确！");
					return false;
				}
			}
			if (combo_dBDataType.getText().equals(
					DataType.Time.getValue_hibernet())) {
//				if (DateTimeUtil.string2Date(text_defaultValue.getText(),
//						"HH:mm:ss") == null) {
				if(!ValidatorUtil.valiTime(text_defaultValue.getText())){	
					parentDialog.setErrorMessage("缺省值格式不正确！");
					return false;
				}
			}
			if (combo_dBDataType.getText().equals(
					DataType.Timestamp.getValue_hibernet())) {
//				if (DateTimeUtil.string2Date(text_defaultValue.getText(),
//						"yyyy-MM-dd HH:mm:ss") == null) {
				if(!ValidatorUtil.valiDateTime(text_defaultValue.getText())){	
					parentDialog.setErrorMessage("缺省值格式不正确！");
					return false;
				}
			}
			if (combo_dBDataType.getText().equals(
					DataType.Character.getValue_hibernet())) {
				if (text_defaultValue.getText().length() > 1) {
					parentDialog.setErrorMessage("缺省值格式不正确！");
					return false;
				}
			}
			if (combo_dBDataType.getText().equals(
					DataType.String.getValue_hibernet())) {
				if (curEditorType.getValue().equals(EditorType.NumberEditor.getValue())) {
					try {
						Double.parseDouble(text_defaultValue.getText());
					} catch (Exception e) {
						parentDialog.setErrorMessage("缺省值格式不正确！");
						Logger.log(e);
						return false;
					}
				}
				
				if (curEditorType.getValue().equals(EditorType.DateEditor.getValue())) {
					if(!ValidatorUtil.valiDate(text_defaultValue.getText())){	
						parentDialog.setErrorMessage("缺省值格式不正确！");
						return false;
					}
				}
				if (curEditorType.getValue().equals(EditorType.TimeEditor.getValue())) {
					if(!ValidatorUtil.valiTime(text_defaultValue.getText())){	
						parentDialog.setErrorMessage("缺省值格式不正确！");
						return false;
					}
				}
				if (curEditorType.getValue().equals(EditorType.DateTimeEditor.getValue())) {
					if(!ValidatorUtil.valiDateTime(text_defaultValue.getText())){	
						parentDialog.setErrorMessage("缺省值格式不正确！");
						return false;
					}
				}
			}
		}
		parentDialog.setErrorMessage(null);
		return true;
	}

	public void updateTheEditingElement() {
		Property property = null;
		if (btn_isPersistence.getSelection() && btn_isPrimaryKey.getSelection()) {
			property = new PKProperty();
			((PKProperty) property).setPkName(PKProperty.PRIMARYKEY_PREFIX
					+ text_name.getText().trim().toUpperCase(Locale.getDefault()));
//			int index = combo_priamryKeyPloy.getSelectionIndex();
			PrimaryKeyPloyType primaryKeyPloyType = PrimaryKeyPloyType
					.getPrimaryKeyPloyTypeByDisplayValue(combo_priamryKeyPloy.getText());
			//PrimaryKeyPloyType(index);
			if (primaryKeyPloyType != null) {
				((PKProperty) property).setPrimaryKeyPloy(primaryKeyPloyType
						.getValue());
			}
			if (primaryKeyPloyType != null
					&& primaryKeyPloyType.getValue().equals(
							PrimaryKeyPloyType.SEQUENCE.getValue())) {
				((PKProperty) property).addPrimaryKeyParam(
						PKProperty.PARAM_KEY_SEQUENCENAME,
						text_primaryKeyPloyParam.getText());
			}
			((PKProperty) property).setPrimaryKey(btn_isPrimaryKey
					.getSelection());
			((PKProperty) property).setNullable(btn_nullable.getSelection());
			// ((PKProperty)property).setIndexedColumn(btn_indexColumn.getSelection());
			((PKProperty) property).setReadOnly(btn_readOnly.getSelection());
			((PKProperty) property).setUnique(btn_isUnique.getSelection());

			((PKProperty) property).setdBColumnName(text_dBColumnName.getText()
					.trim());
			((PKProperty) property)
					.setDefaultValue(text_defaultValue.getText());
			((PKProperty) property)
					.setdBDataLength(text_dBDataLength.getText());
			((PKProperty) property).setdBDataPrecision(text_dBDataPrecision
					.getText());
		} else if (btn_isPersistence.getSelection()
				&& btn_isPrimaryKey.getSelection() == false) {
			property = new PersistenceProperty();
			((PersistenceProperty) property).setPrimaryKey(btn_isPrimaryKey
					.getSelection());
			((PersistenceProperty) property).setNullable(btn_nullable
					.getSelection());
			// ((PersistenceProperty)property).setIndexedColumn(btn_indexColumn.getSelection());
			((PersistenceProperty) property).setReadOnly(btn_readOnly
					.getSelection());
			((PersistenceProperty) property).setUnique(btn_isUnique
					.getSelection());

			((PersistenceProperty) property).setdBColumnName(text_dBColumnName
					.getText().trim());
			((PersistenceProperty) property).setDefaultValue(text_defaultValue
					.getText());
			((PersistenceProperty) property).setdBDataLength(text_dBDataLength
					.getText());
			((PersistenceProperty) property)
					.setdBDataPrecision(text_dBDataPrecision.getText());
		} else {
			property = new PersistenceProperty();
		}
		property.setName(text_name.getText().trim());
		property.setDataType(combo_dBDataType.getText());
		property.setDisplayName(text_displayName.getText());
		property.setRemark(text_remark.getText());
		property.setStereotype(parentDialog.getPropertyCopy().getStereotype());
		String groupDisplay = combo_group.getText().trim();
		if (groupDisplay.length() > 0) {
			property.setGroup(getGroup(groupDisplay));
		} else {
			property.setGroup(null);
		}

		if (parentDialog.getPropertyCopy() != null) {
			property.setParent(parentDialog.getPropertyCopy().getParent());
		}

		if (parentDialog.getPropertyCopy() == null) {
			parentDialog.setPropertyCopy(property);
		} else {
			property.setId(parentDialog.getPropertyCopy().getId());
			property.setOrderNum(parentDialog.getPropertyCopy().getOrderNum());
			parentDialog.setPropertyCopy(property);
		}

	}
	
	private java.util.List<PropertyGroup> getGroups() {
		BusinessClass businessClass = (BusinessClass) (parentDialog.parent);
		return businessClass.getGroups();
	}

	private String[] getGroupDisplays() {
		java.util.List<PropertyGroup> list = getGroups();
		if (list != null && list.size() > 0) {
			String[] s = new String[list.size()];
			for (int i = 0; i < list.size(); i++) {
				s[i] = list.get(i).getDisplayName();
			}
			return s;
		}
		return new String[] {};
	}

	private PropertyGroup getGroup(String groupDisplay) {
		java.util.List<PropertyGroup> groups = getGroups();
		for (int i = 0; i < groups.size(); i++) {
			if (groupDisplay.equals(groups.get(i).getName())) {
				return groups.get(i);
			}
		}
		PropertyGroup newGroup = new PropertyGroup(groupDisplay, groupDisplay);
		groups.add(newGroup);
		return newGroup;
	}
	/**
	 * 获取属性分组索引
	 * @param groupId
	 * @return
	 */
	private int getGroupIndex(String groupId) {
		java.util.List<PropertyGroup> groups = getGroups();
		for (int i = 0; i < groups.size(); i++) {
			if (groupId.equals(groups.get(i).getId())) {
				return i;
			}
		}
		return -1;
	}

	public String getCurDataType() {
		return combo_dBDataType.getText();
	}

	public boolean isChooseDataType() {
		return isChooseDataType;
	}
	/**
	 * 获取下拉框的值在下拉框数据源中的排序
	 * @param items
	 * @param dataType
	 * @return
	 */
	private int getIndex(String[] items, String dataType) {
		for(int i = 0;i < items.length;i++){
			if(items[i].equals(dataType)){
				return i;
			}
		}
		return -1;
	}
//	private boolean valiFloat(String value) {
//	if (value == null) {
//		return true;
//	}
//	return value.matches("^(-?\\d+)(\\.\\d+)?$");
//}

//	private boolean isNameExist() {
//		boolean result = false;
//		BusinessClass bc = (BusinessClass) parentDialog.getPropertyCopy()
//				.getParent();
//		List<Property> list = bc.getProperties();
//		if (list != null & list.size() > 0) {
//			for (int i = 0; i < list.size(); i++) {
//				Property property = list.get(i);
//				if (!property.getId().equals(parentDialog.getPropertyCopy().getId())
//						&& property.getName().equals(text_name.getText())) {
//					result = true;
//					break;
//				}
//			}
//		}
//		return result;
//	}
	
	private boolean isDBColumnNameExist() {
		boolean result = false;
		BusinessClass bc = (BusinessClass) parentDialog.getPropertyCopy()
				.getParent();
		List<Property> list = bc.getProperties();
		if (list != null & list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Property property = list.get(i);
				if(btn_isPersistence.getSelection() && property instanceof PersistenceProperty){
					if (!property.getId().equals(parentDialog.getEditingElement().getId())
							&& ((PersistenceProperty)property).getdBColumnName().equalsIgnoreCase(text_dBColumnName.getText())) {
						result = true;
						break;
					}
				}
			}
		}
		return result;
	}
	
	public String getDefaultValue(){
		return text_defaultValue.getText();
	}
	
//	private void checkForDeleteAll() {
//	List<Validator> validators = parentDialog.getPropertyValidatorPage()
//			.getTableItems();
//	if (validators != null) {
//		Iterator<Validator> it = validators.iterator();
//		while (it.hasNext()) {
//			Validator validator = (Validator) it.next();
//			if (validator.getValidatorType().equals(
//					ValidatorType.Nullable.getValue())) {
//
//			} else {
//				it.remove();
//			}
//		}
//		parentDialog.refresh();
//	}
//}

}
