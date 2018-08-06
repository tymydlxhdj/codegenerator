package com.mqfdy.code.designer.dialogs;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.jdt.core.JavaConventions;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;

import com.mqfdy.code.designer.dialogs.widget.NullToEmptyText;
import com.mqfdy.code.designer.models.ImageKeys;
import com.mqfdy.code.designer.models.ImageManager;
import com.mqfdy.code.model.AbstractModelElement;
import com.mqfdy.code.model.Property;
import com.mqfdy.code.model.Validator;
import com.mqfdy.code.model.utils.DataType;
import com.mqfdy.code.model.utils.ParamType;
import com.mqfdy.code.model.utils.StringUtil;
import com.mqfdy.code.model.utils.ValidatorType;
import com.mqfdy.code.resource.validator.ValidatorUtil;

/**
 * 校验器编辑弹出框(用于新增和编辑)
 * 
 * @author mqfdy
 * 
 */
public class PropertyValidatorEditDialog extends ModelElementEditorDialog {

	public static final String GROUP_VALIDATOREDITOR_TEXT = "校验器编辑";
	public static final String GROUP_VALIDATORPARAM_TEXT = "校验参数";

	public static final String VALIDATORNAME_LABEL_TEXT = "策略名称：";
	public static final String VALIDATORTYPE_LABEL_TEXT = "校验器类型";
	public static final String ERRORMESSAGE_LABEL_TEXT = "失败提示：";

	public static final String MIN_LENGTH_LABEL_TEXT = ParamType.minLength
			.getDisplayValue() + "：";
	public static final String MAX_LENGTH_LABEL_TEXT = ParamType.maxLength
			.getDisplayValue() + "：";

	public static final String MIN_VALUE_LABEL_TEXT = ParamType.minValue
			.getDisplayValue() + "：";
	public static final String MAX_VALUE_LABEL_TEXT = ParamType.maxValue
			.getDisplayValue() + "：";

	public static final String MIN_DATE_LABEL_TEXT = ParamType.minDate
			.getDisplayValue() + "：";
	public static final String MAX_DATE_LABEL_TEXT = ParamType.maxDate
			.getDisplayValue() + "：";

	public static final String DATE_LABEL_DEMO = "日期填写样例：";
	public static final String DATE_LABEL_DEMO1 = "2001-01-01";
	public static final String DATE_LABEL_DEMO2 = "2001-01-01 16:16:16";
	// public static final String DATE_LABEL_DEMO3 = "2001-01-01 16:16:16.123";
	public static final String DATE_LABEL_DEMO4 = "16:16:16";

	public static final String REG_EXP_LABEL_TEXT = ParamType.expression
			.getDisplayValue() + "：";
	public static final String CUSTOM_BEAN_LABEL_TEXT = ParamType.customValidatorClassName
			.getDisplayValue() + "：";

	private Composite group;
	private Composite right;
	private Label label_validateParam;
	private Label label_validatorType;
	private List list_validatorType;

	private Label label_errorMessage;
	private NullToEmptyText text_errorMessage;

	private Label label_MinLength;
	private NullToEmptyText text_MinLength;
	private Label label_MaxLength;
	private NullToEmptyText text_MaxLength;

	private Label label_MinValue;
	private NullToEmptyText text_MinValue;
	private Label label_MaxValue;
	private NullToEmptyText text_MaxValue;

	private Label label_MinDate;
	private NullToEmptyText text_MinDate;
	private Label label_MaxDate;
	private NullToEmptyText text_MaxDate;

	private Label label_DateDemoBlock;
	private Label label_DateDemoBlock1;
	private Label label_DateDemoBlock2;
	// private Label label_DateDemoBlock3;
	private Label label_DateDemoBlock4;
	private Label label_DateDemo;
	private Label label_DateDemo1;
	private Label label_DateDemo2;
	// private Label label_DateDemo3;
	private Label label_DateDemo4;

	private Label label_RegExp;
	private NullToEmptyText text_RegExp;

	private Label label_CustomBean;
	private NullToEmptyText text_CustomBean;
	/**
	 * 当前编辑的校验器
	 */
	private Validator editingValidator;
	/**
	 * 校验器参数
	 */
	private Map<String, String> validatorParams = new HashMap<String, String>();
	private PropertyValidatorPage page;
	private Map<ValidatorType,String> mapErrorMessage = new HashMap();

	/**
	 * 新增业务类时构造函数
	 * 
	 * @param page
	 * @param parentShell
	 * @param parent
	 *            父节点
	 * @param property
	 */
	public PropertyValidatorEditDialog(PropertyValidatorPage page,
			Shell parentShell, AbstractModelElement parent) {
		super(parentShell);
		this.parent = parent;
		this.operationType = ModelElementEditorDialog.OPERATION_TYPE_ADD;
		this.page = page;
	}

	/**
	 * 编辑业务类时构造函数
	 * 
	 * @param i
	 * @param parentShell
	 * @param editingValidator
	 *            编辑的对象
	 * @param parent
	 *            父节点
	 */
	public PropertyValidatorEditDialog(PropertyValidatorPage page,
			Shell parentShell, AbstractModelElement editingValidator,
			AbstractModelElement parent) {
		super(parentShell);
		this.parent = parent;
		this.editingValidator = (Validator) editingValidator;
		this.operationType = ModelElementEditorDialog.OPERATION_TYPE_EDIT;
		this.page = page;
		mapErrorMessage.clear();
	}

	public PropertyValidatorEditDialog(Shell shell) {
		super(shell);
	}

	protected void createButtonsForButtonBar(Composite composite) {
		// if(operationType.equals(OPERATION_TYPE_EDIT))
		// createButton(composite, 12000, "重构", true);
		// if(operationType.equals(OPERATION_TYPE_ADD))
		createButton(composite, IDialogConstants.OK_ID,
				IDialogConstants.OK_LABEL, true);
		createButton(composite, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
//		createButton(composite, APPLY_ID, APPLY_LABEL, true);
	}

	protected void buttonPressed(int buttonId) {
//		if (ModelElementEditorDialog.APPLY_ID == buttonId) {
//			applylPressed();
//		} else {
			super.buttonPressed(buttonId);
//		}
	}

	protected void okPressed() {
		if (validateAllInput() == true) {
			updateTheeditingValidator();
			super.okPressed();
		}
	}

	protected void applylPressed() {
		if (validateAllInput() == true) {
			updateTheeditingValidator();
			isChaged = true;
		}

	}

	private boolean isAllowAdd(ValidatorType validatorType) {
		Vector<Validator> list = page.getTableItems();
		if (list != null) {
			for (Validator vt : list) {
				ValidatorType tempType = ValidatorType.getValidatorType(vt
						.getValidatorType());
				if (tempType != null
						&& tempType.getGroup().equals(validatorType.getGroup())) {
					return false;// 与现有校验器分组相同
				}
			}
		}
		return true;
	}
	
	private boolean isAllowEdit(ValidatorType validatorType) {
		Vector<Validator> list = page.getTableItems();
		if (list != null) {
			for (Validator vt : list) {
				ValidatorType tempType = ValidatorType.getValidatorType(vt
						.getValidatorType());
				if (tempType != null
						&& tempType.getGroup().equals(validatorType.getGroup())) {
					Validator curValidator = getEditingValidator();
					if(!vt.equals(curValidator)){
						return false;// 与现有校验器分组相同
					}
				}
			}
		}
		return true;
	}

	private boolean validateAllInput() {
		ValidatorType validatorType = ValidatorType
				.getValidatorType(list_validatorType.getSelectionIndex());
		this.setErrorMessage(null);

		boolean isOk = checkValidatorType(validatorType);
		if (isOk == false) {
			this.setErrorMessage("数据类型与校验器类型不匹配！");
			return isOk;
		}

		if (ModelElementEditorDialog.OPERATION_TYPE_ADD.equals(operationType)) {
			// 校验与已有的校验器是否冲突
			if (!isAllowAdd(validatorType)) {
				this.setErrorMessage(IBusinessClassEditorPage.ERROR_MESSAGE_VALIDATORTYPE_NOT_ALLOW);
				return false;
			}
			if (list_validatorType.getSelectionIndex() < 0) {
				this.setErrorMessage(IBusinessClassEditorPage.ERROR_MESSAGE_VALIDATORTYPE_NULLABLE);
				return false;
			}
			for (int i = 0; i < page.getItems().size(); i++) {
				Validator temp = page.getItems().get(i);
				if (temp.getValidatorType().equals(
						ValidatorType.getValidatorType(
								list_validatorType.getSelectionIndex())
								.getValue())) {
					this.setErrorMessage(IBusinessClassEditorPage.ERROR_MESSAGE_VALIDATORTYPE_EXIST);
					return false;
				}
			}

		} else if (ModelElementEditorDialog.OPERATION_TYPE_EDIT
				.equals(operationType)) {
			if (!isAllowEdit(validatorType)) {
				this.setErrorMessage(IBusinessClassEditorPage.ERROR_MESSAGE_VALIDATORTYPE_NOT_ALLOW);
				return false;
			}
					
		}
		if (ValidatorType.CNString.equals(validatorType)
				|| (ValidatorType.ENString.equals(validatorType))
				|| (ValidatorType.StringLength.equals(validatorType))) {
			if (!"".equals(text_MinLength.getText())) {
				if (!ValidatorUtil.valiInteger(text_MinLength.getText())) {
					this.setErrorMessage("最小长度必须是数字");
					return false;
				}
				if (Integer.parseInt(text_MinLength.getText())<0) {
					this.setErrorMessage("最小长度不能为负数");
					return false;
				}
				
			}
			if (!"".equals(text_MaxLength.getText())) {
				if (!ValidatorUtil.valiInteger(text_MaxLength.getText())) {
					this.setErrorMessage("最大长度必须是数字");
					return false;
				}
				if (Integer.parseInt(text_MaxLength.getText())<0) {
					this.setErrorMessage("最大长度不能为负数");
					return false;
				}
			}
			if (!"".equals(text_MinLength.getText())
					&& !"".equals(text_MaxLength.getText())) {
				if (Integer.parseInt(text_MinLength.getText()) > Integer
						.parseInt(text_MaxLength.getText())) {
					this.setErrorMessage("最小长度必须小于最大长度");
					return false;
				}
			}
		} else if (ValidatorType.Number.equals(validatorType)) {
			if (!"".equals(text_MinValue.getText())) {
				if (!ValidatorUtil.valiDouble(text_MinValue.getText())) {
					this.setErrorMessage("最小值必须是数字");
					return false;
				}
			}
			if (!"".equals(text_MaxValue.getText())) {
				if (!ValidatorUtil.valiDouble(text_MaxValue.getText())) {
					this.setErrorMessage("最大值必须是数字");
					return false;
				}
			}
			if (!"".equals(text_MinValue.getText())
					&& !"".equals(text_MaxValue.getText())) {
				if (Double.parseDouble(text_MinValue.getText()) > Double
						.parseDouble(text_MaxValue.getText())) {
					this.setErrorMessage("最小值必须小于最大值");
					return false;
				}
			}
		} else if ((ValidatorType.Integer.equals(validatorType))) {
			if (!"".equals(text_MinValue.getText())) {
				if (!ValidatorUtil.valiInteger(text_MinValue.getText())) {
					this.setErrorMessage("最小值必须是整数,取值范围-2147483648~2147483647");
					return false;
				}
			}
			if (!"".equals(text_MaxValue.getText())) {
				if (!ValidatorUtil.valiInteger(text_MaxValue.getText())) {
					this.setErrorMessage("最大值必须是整数,取值范围-2147483648~2147483647");
					return false;
				}
			}
			if (!"".equals(text_MinValue.getText())
					&& !"".equals(text_MaxValue.getText())) {
				if (Integer.parseInt(text_MinValue.getText()) > Integer
						.parseInt(text_MaxValue.getText())) {
					this.setErrorMessage("最小值必须小于最大值");
					return false;
				}
			}
		} else if (ValidatorType.DateTime.equals(validatorType)) {
			if (!"".equals(text_MinDate.getText())) {
				if (!valiMinDate()) {
					if(getErrorMessage() == null)
						this.setErrorMessage("最早日期格式不正确");
					return false;
				}
			}
			if (!"".equals(text_MaxDate.getText())) {
				if (!valiMaxDate()) {
					if(getErrorMessage() == null)
						this.setErrorMessage("最晚日期格式不正确");
					return false;
				}
			}
			if(!valiMinMaxDate()){
				if(getErrorMessage() == null)
					this.setErrorMessage("最晚日期应大于最早日期，且两个日期格式必须一致！");
				return false;
			}
		} else if (ValidatorType.Regular.equals(validatorType)) {
			if (!ValidatorUtil.validateReg(text_RegExp.getText())) {
				this.setErrorMessage("正则表达式格式不正确，必须以/开始以/结束，且符合Javascript的正则表达式格式");
				return false;
			}
		} else if (ValidatorType.Custom.equals(validatorType)) {
			if ("".equals(text_CustomBean.getText())) {
				this.setErrorMessage("请填写自定义校验类名称");
				return false;
			}
			if (!validateClassName(text_CustomBean.getText())) {
				this.setErrorMessage("自定义校验类名称格式不正确,必须以下划线或大写字母开头");
				return false;
			}
		} else {

		}
		
		if(text_errorMessage.getText().length()>300){
			this.setErrorMessage("失败提示信息长度过长，必须小于300字");
			return false;
		}
		return true;
	}

	/**
	 * 校验是否匹配
	 * 
	 * @return
	 */
	private boolean checkValidatorType(ValidatorType validatorType) {
		String curDataType = page.getParentDialog().getPropertyBasicInfoPage()
				.getCurDataType();
		Vector<Validator> curValidators = page.getParentDialog()
				.getPropertyValidatorPage().getTableItems();
		if (curDataType == null || curValidators == null) {
			return false;
		} else {
			boolean b = ValidatorUtil.getValidatorMap().get(curDataType)
					.get(validatorType) != null;
			if (!b) {
				// 校验没通过
				return false;
			}
		}
		return true;
	}

	private boolean validateClassName(String name) {
		IStatus status = JavaConventions.validateFieldName(name,
				JavaCore.VERSION_1_4, JavaCore.VERSION_1_4);
		if (status.getSeverity() == IStatus.ERROR) {
			return false;
		}
		if (!ValidatorUtil.isFirstUppercase(name)) {
			return false;
		}
		return true;
	}

	@Override
	protected Control createDialogArea(Composite composite) {
		setTitleAndMessage();
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		composite.setLayout(layout);

		group = new Composite(composite, SWT.NONE);
		group.setLayout(new GridLayout(2, true));
		group.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		createLeftArea(group);
		createRightArea(group);

		setVisable(true, false, false, false, false, false);

		addListeners();
		resetValidatorDetail(editingValidator);
		resetValidatorParam(editingValidator);
		return composite;

	}

	private void setVisable(boolean b_label, boolean b_length, boolean b_value,
			boolean b_date, boolean b_reg, boolean b_custom) {
		setVisableLable(b_label);
		setVisableLength(b_length);
		setVisableValue(b_value);
		setVisableDate(b_date);
		setVisableReg(b_reg);
		setVisableCustom(b_custom);
	}

	private void setVisableLable(boolean b) {
		((GridData) label_validateParam.getLayoutData()).exclude = !b;
		label_validateParam.setVisible(b);
	}

	private void setVisableLength(boolean b) {
		((GridData) label_MinLength.getLayoutData()).exclude = !b;
		label_MinLength.setVisible(b);
		((GridData) text_MinLength.getLayoutData()).exclude = !b;
		text_MinLength.setVisible(b);

		((GridData) label_MaxLength.getLayoutData()).exclude = !b;
		label_MaxLength.setVisible(b);
		((GridData) text_MaxLength.getLayoutData()).exclude = !b;
		text_MaxLength.setVisible(b);
	}

	private void setVisableValue(boolean b) {
		((GridData) label_MinValue.getLayoutData()).exclude = !b;
		label_MinValue.setVisible(b);
		((GridData) text_MinValue.getLayoutData()).exclude = !b;
		text_MinValue.setVisible(b);

		((GridData) label_MaxValue.getLayoutData()).exclude = !b;
		label_MaxValue.setVisible(b);
		((GridData) text_MaxValue.getLayoutData()).exclude = !b;
		text_MaxValue.setVisible(b);
	}

	private void setVisableDate(boolean b) {
		((GridData) label_MinDate.getLayoutData()).exclude = !b;
		label_MinDate.setVisible(b);
		((GridData) text_MinDate.getLayoutData()).exclude = !b;
		text_MinDate.setVisible(b);

		((GridData) label_MaxDate.getLayoutData()).exclude = !b;
		label_MaxDate.setVisible(b);
		((GridData) text_MaxDate.getLayoutData()).exclude = !b;
		text_MaxDate.setVisible(b);

		((GridData) label_DateDemo.getLayoutData()).exclude = !b;
		label_DateDemo.setVisible(b);

		((GridData) label_DateDemo1.getLayoutData()).exclude = !b;
		label_DateDemo1.setVisible(b);

		((GridData) label_DateDemo2.getLayoutData()).exclude = !b;
		label_DateDemo2.setVisible(b);

		// ((GridData) label_DateDemo3.getLayoutData()).exclude = !b;
		// label_DateDemo3.setVisible(b);

		((GridData) label_DateDemo4.getLayoutData()).exclude = !b;
		label_DateDemo4.setVisible(b);

		((GridData) label_DateDemoBlock.getLayoutData()).exclude = !b;
		label_DateDemoBlock.setVisible(b);

		((GridData) label_DateDemoBlock1.getLayoutData()).exclude = !b;
		label_DateDemoBlock1.setVisible(b);

		((GridData) label_DateDemoBlock2.getLayoutData()).exclude = !b;
		label_DateDemoBlock2.setVisible(b);

		// ((GridData) label_DateDemoBlock3.getLayoutData()).exclude = !b;
		// label_DateDemoBlock3.setVisible(b);

		((GridData) label_DateDemoBlock4.getLayoutData()).exclude = !b;
		label_DateDemoBlock4.setVisible(b);
	}

	private void setVisableReg(boolean b) {
		((GridData) label_RegExp.getLayoutData()).exclude = !b;
		label_RegExp.setVisible(b);
		((GridData) text_RegExp.getLayoutData()).exclude = !b;
		text_RegExp.setVisible(b);
	}

	private void setVisableCustom(boolean b) {
		((GridData) label_CustomBean.getLayoutData()).exclude = !b;
		label_CustomBean.setVisible(b);
		((GridData) text_CustomBean.getLayoutData()).exclude = !b;
		text_CustomBean.setVisible(b);
	}

	private void addListeners() {
		list_validatorType.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				resetValidatorParam(editingValidator);
				int index = list_validatorType.getSelectionIndex();
				ValidatorType validatorType = ValidatorType
						.getValidatorType(index);
				if(mapErrorMessage.get(validatorType) == null){
					text_errorMessage.setText(validatorType.getErrorMessage());
				}else{
					text_errorMessage.setText(mapErrorMessage.get(validatorType));
				}
			}
		});
		text_MinDate.addModifyListener(new ModifyListener() {

			public void modifyText(ModifyEvent e) {
				validateAllInput();
//				valiMinDate();
			}
		});
		text_MaxDate.addModifyListener(new ModifyListener() {

			public void modifyText(ModifyEvent e) {
				validateAllInput();
//				valiMaxDate();

			}
		});
		
		text_errorMessage.addKeyListener(new KeyListener(){

			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			public void keyReleased(KeyEvent arg0) {
				ValidatorType validatorType = ValidatorType
						.getValidatorType(list_validatorType.getSelectionIndex());
				mapErrorMessage.put(validatorType, text_errorMessage.getText());
			}
			
		});
	}

	private void createLeftArea(Composite group) {
		GridData layoutData = new GridData(GridData.FILL_BOTH);
		layoutData.horizontalAlignment = SWT.FILL;
		layoutData.verticalAlignment = SWT.FILL;

		Composite left = new Composite(group, SWT.NONE);
		left.setLayout(new GridLayout(1, true));
		left.setLayoutData(layoutData);

		label_validatorType = new Label(left, SWT.NONE);
		label_validatorType.setText(VALIDATORTYPE_LABEL_TEXT);
		list_validatorType = new List(left, SWT.VERTICAL | SWT.BORDER);
		list_validatorType.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
				true));
		list_validatorType.setItems(ValidatorType.getValidatorTypesString());
		//list_validatorType.remove(ValidatorType.getIndex(ValidatorType.Custom.getValue()));
	}

	private void createRightArea(Composite group) {
		right = new Composite(group, SWT.NONE);
		right.setLayout(new GridLayout(2, false));
		right.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		label_validateParam = new Label(right, SWT.NONE);
		label_validateParam.setText(GROUP_VALIDATORPARAM_TEXT);
		label_validateParam.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER,
				false, false, 2, 1));// 校验参数label
		// 最小长度
		label_MinLength = new Label(right, SWT.NONE);
		label_MinLength.setText(MIN_LENGTH_LABEL_TEXT);
		label_MinLength.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 1, 1));
		text_MinLength = new NullToEmptyText(right, SWT.MULTI | SWT.BORDER);
		text_MinLength.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 1, 1));
		// 最大长度
		label_MaxLength = new Label(right, SWT.NONE);
		label_MaxLength.setText(MAX_LENGTH_LABEL_TEXT);
		label_MaxLength.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 1, 1));
		text_MaxLength = new NullToEmptyText(right, SWT.MULTI | SWT.BORDER);
		text_MaxLength.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 1, 1));
		// 最小值
		label_MinValue = new Label(right, SWT.NONE);
		label_MinValue.setText(MIN_VALUE_LABEL_TEXT);
		label_MinValue.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 1, 1));
		text_MinValue = new NullToEmptyText(right, SWT.MULTI | SWT.BORDER);
		text_MinValue.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 1, 1));
		// 最大值
		label_MaxValue = new Label(right, SWT.NONE);
		label_MaxValue.setText(MAX_VALUE_LABEL_TEXT);
		text_MaxValue = new NullToEmptyText(right, SWT.MULTI | SWT.BORDER);
		label_MaxValue.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 1, 1));
		text_MaxValue.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 1, 1));
		// 最早日期
		label_MinDate = new Label(right, SWT.NONE);
		label_MinDate.setText(MIN_DATE_LABEL_TEXT);
		label_MinDate.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 1, 1));
		text_MinDate = new NullToEmptyText(right, SWT.MULTI | SWT.BORDER);
		text_MinDate.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 1, 1));

		// 最晚日期
		label_MaxDate = new Label(right, SWT.NONE);
		label_MaxDate.setText(MAX_DATE_LABEL_TEXT);
		label_MaxDate.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 1, 1));
		text_MaxDate = new NullToEmptyText(right, SWT.MULTI | SWT.BORDER);
		text_MaxDate.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 1, 1));

		label_DateDemoBlock = new Label(right, SWT.NONE);
		label_DateDemoBlock.setLayoutData(new GridData(SWT.FILL, SWT.CENTER,
				false, false, 1, 1));
		label_DateDemo = new Label(right, SWT.NONE);
		label_DateDemo.setText(DATE_LABEL_DEMO);
		label_DateDemo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 1, 1));

		label_DateDemoBlock1 = new Label(right, SWT.NONE);
		label_DateDemoBlock1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER,
				false, false, 1, 1));
		label_DateDemo1 = new Label(right, SWT.NONE);
		label_DateDemo1.setText(DATE_LABEL_DEMO1);
		label_DateDemo1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 1, 1));

		label_DateDemoBlock2 = new Label(right, SWT.NONE);
		label_DateDemoBlock2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER,
				false, false, 1, 1));
		label_DateDemo2 = new Label(right, SWT.NONE);
		label_DateDemo2.setText(DATE_LABEL_DEMO2);
		label_DateDemo2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 1, 1));

		// label_DateDemoBlock3 = new Label(right,SWT.NONE);
		// label_DateDemoBlock3.setLayoutData(new
		// GridData(SWT.FILL,SWT.CENTER,false,false,1,1));
		// label_DateDemo3 = new Label(right,SWT.NONE);
		// label_DateDemo3.setText(DATE_LABEL_DEMO3);
		// label_DateDemo3.setLayoutData(new
		// GridData(SWT.FILL,SWT.CENTER,false,false,1,1));

		label_DateDemoBlock4 = new Label(right, SWT.NONE);
		label_DateDemoBlock4.setLayoutData(new GridData(SWT.FILL, SWT.CENTER,
				false, false, 1, 1));
		label_DateDemo4 = new Label(right, SWT.NONE);
		label_DateDemo4.setText(DATE_LABEL_DEMO4);
		label_DateDemo4.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 1, 1));
		// 正则表达式
		label_RegExp = new Label(right, SWT.NONE);
		label_RegExp.setText(REG_EXP_LABEL_TEXT);
		label_RegExp.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 1, 1));
		text_RegExp = new NullToEmptyText(right, SWT.MULTI | SWT.BORDER);
		text_RegExp.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 1, 1));
		// 自定义
		label_CustomBean = new Label(right, SWT.NONE);
		label_CustomBean.setText(CUSTOM_BEAN_LABEL_TEXT);
		label_CustomBean.setLayoutData(new GridData(SWT.FILL, SWT.CENTER,
				false, false, 1, 1));
		text_CustomBean = new NullToEmptyText(right, SWT.MULTI | SWT.BORDER);
		text_CustomBean.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 1, 1));

		label_errorMessage = new Label(right, SWT.NONE);
		label_errorMessage.setText(ERRORMESSAGE_LABEL_TEXT);
		text_errorMessage = new NullToEmptyText(right, SWT.MULTI | SWT.BORDER
				| SWT.VERTICAL | SWT.WRAP);
		GridData gridData = new GridData(SWT.FILL, SWT.CENTER, true, false);
		gridData.heightHint = 120;
		text_errorMessage.setLayoutData(gridData);
	}

	/**
	 * 重置右边校验器明细
	 * 
	 * @param validator
	 */
	private void resetValidatorDetail(Validator validator) {
		if (validator == null) {
			list_validatorType.select(0);
//			text_errorMessage.setText("");
			text_errorMessage.setText(ValidatorType.Nullable.getErrorMessage());
		} else {
			String validatorType = validator.getValidatorType();
			int index = ValidatorType.getIndex(validatorType);
			list_validatorType.setSelection(index);
			text_errorMessage.setText(validator.getValidatorMessage());
		}

	}

	/**
	 * 重新设置校验参数
	 */
	private void resetValidatorParam(Validator validator) {
		int index = list_validatorType.getSelectionIndex();
		setMessage(null);
		ValidatorType validatorType = ValidatorType.getValidatorType(index);
		if (validatorType != null) {
			text_errorMessage.setEditable(true);
			if (ValidatorType.Nullable.equals(validatorType)) {
				text_errorMessage.setEditable(false);
				setVisable(true, false, false, false, false, false);
			} else if (ValidatorType.CNString.equals(validatorType)
					|| (ValidatorType.ENString.equals(validatorType))
					|| (ValidatorType.StringLength.equals(validatorType))) {
				setVisable(true, true, false, false, false, false);
			} else if (ValidatorType.Number.equals(validatorType)
					|| (ValidatorType.Integer.equals(validatorType))
					|| (ValidatorType.Long.equals(validatorType))) {
				setVisable(true, false, true, false, false, false);
			} else if (ValidatorType.DateTime.equals(validatorType)) {
				setVisable(true, false, false, true, false, false);
			} else if (ValidatorType.Regular.equals(validatorType)) {
				setMessage("正则表达式必须以/开始以/结束，且符合Javascript的正则表达式格式");
				setVisable(true, false, false, false, true, false);
			} else if (ValidatorType.Custom.equals(validatorType)) {
				setVisable(true, false, false, false, false, true);
			} else {
				setVisable(true, false, false, false, false, false);
			}

		}
		right.layout();
		PropertyValidatorEditDialog.this.getShell().layout(true);

		if (validator != null) {
			if(validator.getValidatorParams().get(
					ParamType.minLength.getValue()) != null){
				text_MinLength.setText(validator.getValidatorParams().get(
						ParamType.minLength.getValue()));
			}
			if(validator.getValidatorParams().get(
					ParamType.maxLength.getValue()) != null){
				text_MaxLength.setText(validator.getValidatorParams().get(
						ParamType.maxLength.getValue()));
			}
			if(validator.getValidatorParams().get(
					ParamType.minValue.getValue()) != null){
				text_MinValue.setText(validator.getValidatorParams().get(
						ParamType.minValue.getValue()));
			}
			if(validator.getValidatorParams().get(
					ParamType.maxValue.getValue()) != null){
				text_MaxValue.setText(validator.getValidatorParams().get(
						ParamType.maxValue.getValue()));
			}
			if(validator.getValidatorParams().get(
					ParamType.minDate.getValue()) != null){
				text_MinDate.setText(validator.getValidatorParams().get(
						ParamType.minDate.getValue()));
			}
			if(validator.getValidatorParams().get(
					ParamType.maxDate.getValue()) != null){
				text_MaxDate.setText(validator.getValidatorParams().get(
						ParamType.maxDate.getValue()));
			}
			if(validator.getValidatorParams().get(
					ParamType.expression.getValue()) != null){
				text_RegExp.setText(validator.getValidatorParams().get(
						ParamType.expression.getValue()));
			}
			if(validator.getValidatorParams().get(
					ParamType.customValidatorClassName.getValue()) != null){
				text_CustomBean.setText(validator.getValidatorParams().get(
						ParamType.customValidatorClassName.getValue()));
			}
			if (validator.getValidatorMessage()!=null && !"".equals(validator.getValidatorMessage())) {
				text_errorMessage.setText(validator.getValidatorMessage());
			}
		}
	}

	private void updateTheeditingValidator() {
		updateParams();

		if (this.editingValidator == null) {
			this.editingValidator = new Validator();
			this.editingValidator.setBelongProperty((Property) parent);
			ValidatorType validatorType = ValidatorType
					.getValidatorType(list_validatorType.getSelectionIndex());
			((Validator) this.editingValidator).setValidatorType(validatorType
					.getValue());
			this.editingValidator.setName(validatorType.getValue());
			this.editingValidator.setDisplayName(validatorType
					.getDisplayValue());
			if (StringUtil.isEmpty(text_errorMessage.getText())) {
				((Validator) this.editingValidator)
						.setValidatorMessage(validatorType.getErrorMessage());
			} else {
				((Validator) this.editingValidator)
						.setValidatorMessage(text_errorMessage.getText());
			}
			((Validator) this.editingValidator).setOrderNum(page
					.getNextOrderNumber());
			((Validator) this.editingValidator)
					.setValidatorParams(validatorParams);
			((Validator) this.editingValidator).getValidatorParams().putAll(
					validatorParams);
			((Property) parent).addValidator(getEditingValidator());
		} else {
			ValidatorType validatorType = ValidatorType
					.getValidatorType(list_validatorType.getSelectionIndex());
			getEditingValidator().setValidatorType(validatorType.getValue());
			getEditingValidator().setName(validatorType.getValue());// ?
			getEditingValidator().setDisplayName(
					validatorType.getDisplayValue());
			getEditingValidator().setValidatorParams(validatorParams);
			if (StringUtil.isEmpty(text_errorMessage.getText())) {
				getEditingValidator().setValidatorMessage(
						validatorType.getErrorMessage());
			} else {
				getEditingValidator().setValidatorMessage(
						text_errorMessage.getText());
			}
			getEditingValidator().setOrderNum(page.getNextOrderNumber());
		}

	}

	private void updateParams() {
		validatorParams.clear();
		// 根据左侧选择，保存右侧校验信息
		ValidatorType validatorType = ValidatorType
				.getValidatorType(list_validatorType.getSelectionIndex());
		if (ValidatorType.StringLength.equals(validatorType)) {
			validatorParams.clear();
			if (!"".equals(text_MinLength.getText())) {
				validatorParams.put(ParamType.minLength.getValue(),
						text_MinLength.getText());
			}
			if (!"".equals(text_MaxLength.getText())) {
				validatorParams.put(ParamType.maxLength.getValue(),
						text_MaxLength.getText());
			}
		} else if (ValidatorType.CNString.equals(validatorType)) {
			validatorParams.clear();
			if (!"".equals(text_MinLength.getText())) {
				validatorParams.put(ParamType.minLength.getValue(),
						text_MinLength.getText());
			}
			if (!"".equals(text_MaxLength.getText())) {
				validatorParams.put(ParamType.maxLength.getValue(),
						text_MaxLength.getText());
			}
		} else if (ValidatorType.ENString.equals(validatorType)) {
			validatorParams.clear();
			if (!"".equals(text_MinLength.getText())) {
				validatorParams.put(ParamType.minLength.getValue(),
						text_MinLength.getText());
			}
			if (!"".equals(text_MaxLength.getText())) {
				validatorParams.put(ParamType.maxLength.getValue(),
						text_MaxLength.getText());
			}
		} else if (ValidatorType.Number.equals(validatorType)
				|| (ValidatorType.Integer.equals(validatorType))) {
			validatorParams.clear();
			if (!"".equals(text_MinValue.getText())) {
				validatorParams.put(ParamType.minValue.getValue(),
						text_MinValue.getText());
			}
			if (!"".equals(text_MaxValue.getText())) {
				validatorParams.put(ParamType.maxValue.getValue(),
						text_MaxValue.getText());
			}
		} else if (ValidatorType.DateTime.equals(validatorType)) {
			validatorParams.clear();
			if (!"".equals(text_MinDate.getText())) {
				validatorParams.put(ParamType.minDate.getValue(),
						text_MinDate.getText());
			}
			if (!"".equals(text_MaxDate.getText())) {
				validatorParams.put(ParamType.maxDate.getValue(),
						text_MaxDate.getText());
			}
		} else if (ValidatorType.Regular.equals(validatorType)) {
			validatorParams.clear();
			if (!"".equals(text_RegExp.getText())) {
				validatorParams.put(ParamType.expression.getValue(),
						text_RegExp.getText());
			}
		} else if (ValidatorType.Custom.equals(validatorType)) {
			validatorParams.clear();
			if (!"".equals(text_CustomBean.getText())) {
				validatorParams.put(
						ParamType.customValidatorClassName.getValue(),
						text_CustomBean.getText());
			}
		} else {
			validatorParams.clear();
		}
	}

	public Validator getEditingValidator() {
		return (Validator) editingValidator;
	}

	/**
	 * 设置标题和信息
	 */
	public void setTitleAndMessage() {
		setTitle("校验器");
		setTitleImage(ImageManager.getInstance().getImage(
				ImageKeys.IMG_DIALOG_BUSINESSCLASS));
		if (operationType.equals(ModelElementEditorDialog.OPERATION_TYPE_ADD)) {
			setMessage("新增校验器");
		} else {
			String displayName = this.editingValidator.getDisplayName();
			setMessage(displayName, IMessageProvider.INFORMATION);
		}

	}

	@Override
	protected Point getInitialSize() {
		return new Point(700, 600);
	}

	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		if (operationType.equals(ModelElementEditorDialog.OPERATION_TYPE_ADD)) {
			newShell.setText("校验器");
		} else {
			String displayName = this.editingValidator.getDisplayName();
			newShell.setText("校验器" + " " + displayName);
		}
		newShell.setImage(ImageManager.getInstance().getImage(
				ImageKeys.IMG_MODEL_TYPE_BUSINESSCLASS));
	}
	protected boolean valiMinDate(){
		setErrorMessage(null);
		if (!"".equals(text_MinDate.getText())) {
			String dataType = page.getParentDialog().getPropertyBasicInfoPage().getCurDataType();
			if(dataType.equals(DataType.Time.getValue_hibernet())){
				if(!ValidatorUtil.valiTime(text_MinDate.getText())){
					setErrorMessage("最早日期格式应该是时间类型，例如： "+DATE_LABEL_DEMO4);
					return false;
				}
			}
			if(dataType.equals(DataType.Timestamp.getValue_hibernet())){
				if(!ValidatorUtil.valiDateTime(text_MinDate.getText())){
					setErrorMessage("最早日期格式应该是日期时间类型，例如： "+DATE_LABEL_DEMO2);
					return false;
				}
			}
			if(dataType.equals(DataType.Date.getValue_hibernet())){
				if(!ValidatorUtil.valiDate(text_MinDate.getText())){
					setErrorMessage("最早日期格式应该是日期类型，例如： "+DATE_LABEL_DEMO1);
					return false;
				}
			}
		}
		return true;
	}
	protected boolean valiMaxDate(){
		setErrorMessage(null);
		if (!"".equals(text_MaxDate.getText())) {
			String dataType = page.getParentDialog().getPropertyBasicInfoPage().getCurDataType();
			if(dataType.equals(DataType.Time.getValue_hibernet())){
				if(!ValidatorUtil.valiTime(text_MaxDate.getText())){
					setErrorMessage("最晚日期格式应该是时间类型，例如： "+DATE_LABEL_DEMO4);
					return false;
				}
			}
			if(dataType.equals(DataType.Timestamp.getValue_hibernet())){
				if(!ValidatorUtil.valiDateTime(text_MaxDate.getText())){
					setErrorMessage("最晚日期格式应该是日期时间类型，例如： "+DATE_LABEL_DEMO2);
					return false;
				}
			}
			if(dataType.equals(DataType.Date.getValue_hibernet())){
				if(!ValidatorUtil.valiDate(text_MaxDate.getText())){
					setErrorMessage("最晚日期格式应该是日期类型，例如： "+DATE_LABEL_DEMO1);
					return false;
				}
			}
		}
		return true;
	}
	
	private boolean valiMinMaxDate(){
		if (!"".equals(text_MinDate.getText()) && !"".equals(text_MaxDate.getText())) {
			//都不为空的时候进行大小比对
			String dataType = page.getParentDialog().getPropertyBasicInfoPage().getCurDataType();
			Date minDate = null;
			Date maxDate = null;
			if(dataType.equals(DataType.Time.getValue_hibernet())){
				SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
				try {
					minDate = df.parse(text_MinDate.getText());
					maxDate = df.parse(text_MaxDate.getText());
				} catch (ParseException e) {
					e.printStackTrace();
					setErrorMessage("时间转换出错！");
					return false;
				}
			}
			if(dataType.equals(DataType.Timestamp.getValue_hibernet())){
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				try {
					minDate = df.parse(text_MinDate.getText());
					maxDate = df.parse(text_MaxDate.getText());
				} catch (ParseException e) {
					e.printStackTrace();
					setErrorMessage("时间转换出错！");
					return false;
				}
			}
			if(dataType.equals(DataType.Date.getValue_hibernet())){
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				try {
					minDate = df.parse(text_MinDate.getText());
					maxDate = df.parse(text_MaxDate.getText());
				} catch (ParseException e) {
					e.printStackTrace();
					setErrorMessage("时间转换出错！");
					return false;
				}
			}
			if(dataType.equals(DataType.String.getValue_hibernet())){
				if(ValidatorUtil.valiDate(text_MinDate.getText())
						&& ValidatorUtil.valiDate(text_MaxDate.getText())){
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
					try {
						minDate = df.parse(text_MinDate.getText());
						maxDate = df.parse(text_MaxDate.getText());
					} catch (ParseException e) {
						e.printStackTrace();
						setErrorMessage("时间转换出错！");
						return false;
					}
				}
				if(ValidatorUtil.valiTime(text_MinDate.getText())
						&& ValidatorUtil.valiTime(text_MaxDate.getText())){
					SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
					try {
						minDate = df.parse(text_MinDate.getText());
						maxDate = df.parse(text_MaxDate.getText());
					} catch (ParseException e) {
						e.printStackTrace();
						setErrorMessage("时间转换出错！");
						return false;
					}
				}
				if(ValidatorUtil.valiDateTime(text_MinDate.getText())
						&& ValidatorUtil.valiDateTime(text_MaxDate.getText())){
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					try {
						minDate = df.parse(text_MinDate.getText());
						maxDate = df.parse(text_MaxDate.getText());
					} catch (ParseException e) {
						e.printStackTrace();
						setErrorMessage("时间转换出错！");
						return false;
					}
				}
			}
			//判断最大和最小日期
			if(minDate != null){
				if(minDate.compareTo(maxDate) > 0){
					return false;
				}else{
					return true;
				}
			}else{
				return false;
			}
		}
		return true;
	}
}
