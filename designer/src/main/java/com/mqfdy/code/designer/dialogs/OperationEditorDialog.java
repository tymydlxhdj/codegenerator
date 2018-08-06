package com.mqfdy.code.designer.dialogs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.ToolBar;

import com.mqfdy.code.designer.celleditor.ComboDialogCellEditor;
import com.mqfdy.code.designer.dialogs.widget.NullToEmptyText;
import com.mqfdy.code.designer.dialogs.widget.OperationDataTypeSelecter;
import com.mqfdy.code.designer.models.ActionTexts;
import com.mqfdy.code.designer.models.ImageKeys;
import com.mqfdy.code.designer.models.ImageManager;
import com.mqfdy.code.designer.utils.CheckerUtil;
import com.mqfdy.code.model.AbstractModelElement;
import com.mqfdy.code.model.BusinessClass;
import com.mqfdy.code.model.BusinessOperation;
import com.mqfdy.code.model.OperationParam;
import com.mqfdy.code.model.utils.DataType;
import com.mqfdy.code.model.utils.TransactionType;
import com.mqfdy.code.resource.validator.ValidatorUtil;

/**
 * 业务操作编辑弹出框
 * 
 * @author mqfdy
 * 
 */
public class OperationEditorDialog extends ModelElementEditorDialog {

	public static final String DIALOG_TITLE_ADD = "创建业务操作";
	public static final String DIALOG_TITLE_EDIT = "修改业务操作";
	public static final String MESSAGE_TITLE = "业务操作";
	public static final String DIALOG_MESSAGE_ADD = "创建业务操作";
	public static final String DIALOG_MESSAGE_EDIT = "修改 @ 信息";

	public static final String OPERATION_NAME = "名称:";
	public static final String OPERATION_DISPLAYNAME = "显示名称:";

	public static final String RETURNDATATYPE_LABEL_TEXT = "返回数据类型：";
	public static final String TRANSACTIONTYPE_LABEL_TEXT = "事务类型：";
	public static final String ERRORMESSAGE_LABEL_TEXT = "异常提示：";

	public static final String PARAMNAME_LABEL_TEXT = "参数名：";
	public static final String PARAMDISPLAYNAME_LABEL_TEXT = "参数显示名：";
	public static final String PARAMTYPE_LABEL_TEXT = "参数类型：";
	public static final String PARAMDEFAULTVALUE_LABEL_TEXT = "默认值：";

	public static final String GROUP_PARAM_TEXT = "参数信息";
	public static final String GROUP_BASIC_TEXT = "基本信息";
	public static final String GROUP_OPERATEPARAM_TEXT = "操作参数";
	public static final String GROUP_PARAMEDIT_TEXT = "参数编辑";

	public static final String DELETE_MESSAGE = "请选择要删除的对象";
	public static final String DELETE_MESSAGE_TITLE = "操作参数器";

	public static final String COLUMN_ORDER = "序号";
	public static final String COLUMN_NAME = "名称";
	public static final String COLUMN_DISPLAYNAME = "显示名";
	public static final String COLUMN_TYPE = "参数类型";

	// 显示组件
	private Group group_basic;
	private Group group_param;

	// private Group group_operateParam;
	private Group group_paramEdit;

	private Label label_name;
	private NullToEmptyText text_name;

	private Label label_displayName;
	private NullToEmptyText text_displayName;

	private Label label_remark;
	private NullToEmptyText text_remark;

	private Label label_errorMessage;
	private NullToEmptyText text_errorMessage;

	private Label label_returnDataType;
	private OperationDataTypeSelecter combo_returnDataType;

	private Label label_transactionType;
	private Combo combo_transactionType;

	private Table table;
	private TableViewer tableViewer;

	private Label label_paramName;
	private NullToEmptyText text_paramName;

	private Label label_paramDisplayName;
	private NullToEmptyText text_paramDisplayName;

	private Label label_paramType;
	private OperationDataTypeSelecter combo_paramType;

	private Label label_defaultValue;
	private NullToEmptyText text_defaultValue;

	private Label label_paramRemark;
	private NullToEmptyText text_paramRemark;

	private ToolBar toolBar = null;

	private Action addParamAction;// 新增
	private Action deleteParamAction;// 删除

	private String statue = "edit";

	private OperationParam editingOperationParam;

	private BusinessOperation operationCopy;

	// private java.util.List<OperationParam> tableItems = new
	// ArrayList<OperationParam>();
	private java.util.List<OperationParam> tempList = new ArrayList<OperationParam>();
	private String displayName;

	private class OperationParamConentProvider implements
			IStructuredContentProvider {
		public void dispose() {
		}

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}

		@SuppressWarnings("rawtypes")
		public Object[] getElements(Object inputElement) {
			if (inputElement instanceof Collection) {
				Object[] objects = ((Collection) inputElement).toArray();
				if (objects != null) {
					for (int i = 0; i < objects.length; i++) {
						OperationParam element = (OperationParam) objects[i];
						element.setOrderNum(i + 1);
					}
				}
				return objects;
			}
			return new Object[0];
		}

	}

	private class OperationParamLabelProvider extends LabelProvider implements
			ITableLabelProvider {

		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}

		public String getColumnText(Object element, int columnIndex) {
			OperationParam param = (OperationParam) element;
			switch (columnIndex) {
			case 0:
				return param.getOrderNum() + "";
			case 1:
				return param.getName();
			case 2:
				return param.getDisplayName();
			case 3:
				DataType type = DataType.getDataType(param.getDataType());
				if (type != null) {
					return type.getValue_hibernet();
				}
				return param.getDataType();
			}
			return "";
		}
	}

	public OperationEditorDialog(Shell parentShell, AbstractModelElement parent) {
		super(parentShell, parent);
	}

	public OperationEditorDialog(Shell parentShell,
			AbstractModelElement editingElement, AbstractModelElement parent) {
		super(parentShell, editingElement, parent);
	}

	protected Control createDialogArea(Composite parent) {
		// 初始化窗口
		GridLayout gridLayout = new GridLayout(1, true);
		parent.setLayout(gridLayout);

		createGroups(parent);

		label_name = new Label(group_basic, SWT.NONE);
		label_name.setText(OPERATION_NAME);
		text_name = new NullToEmptyText(group_basic, SWT.BORDER);
		text_name
				.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		text_name.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				if (displayName == null)
					text_displayName.setText(text_name.getText());
			}
		});
		label_displayName = new Label(group_basic, SWT.NONE);
		label_displayName.setText(OPERATION_DISPLAYNAME);
		text_displayName = new NullToEmptyText(group_basic, SWT.BORDER);
		text_displayName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false));
		text_displayName.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				displayName = text_displayName.getText();
			}
		});
		label_returnDataType = new Label(group_basic, SWT.NONE);
		label_returnDataType.setText(RETURNDATATYPE_LABEL_TEXT);
		combo_returnDataType = new OperationDataTypeSelecter(group_basic,
				SWT.NONE);
		combo_returnDataType.setLayoutData(new GridData(SWT.FILL, SWT.CENTER,
				true, false));

		label_transactionType = new Label(group_basic, SWT.NONE);
		label_transactionType.setText(TRANSACTIONTYPE_LABEL_TEXT);
		combo_transactionType = new Combo(group_basic, SWT.DROP_DOWN
				| SWT.READ_ONLY);
		combo_transactionType.setLayoutData(new GridData(SWT.FILL, SWT.CENTER,
				true, false));

		label_errorMessage = new Label(group_basic, SWT.NONE);
		label_errorMessage.setText(ERRORMESSAGE_LABEL_TEXT);
		text_errorMessage = new NullToEmptyText(group_basic, SWT.BORDER
				| SWT.MULTI | SWT.VERTICAL | SWT.WRAP);
		GridData gridData_errormessage = new GridData(SWT.FILL, SWT.CENTER,
				true, false, 3, 1);
		gridData_errormessage.heightHint = 40;
		text_errorMessage.setLayoutData(gridData_errormessage);

		label_remark = new Label(group_basic, SWT.NONE);
		label_remark.setText(ModelElementEditorDialog.REMARK_TEXT);
		text_remark = new NullToEmptyText(group_basic, SWT.BORDER | SWT.MULTI
				| SWT.VERTICAL | SWT.WRAP);
		text_remark.setLayoutData(gridData_errormessage);

		/*
		 * label_paramName = new Label(group_paramEdit, SWT.NONE);
		 * label_paramName.setText(PARAMNAME_LABEL_TEXT); text_paramName = new
		 * Text(group_paramEdit, SWT.BORDER); text_paramName.setLayoutData(new
		 * GridData(SWT.FILL,SWT.CENTER,true,false));
		 * 
		 * label_paramDisplayName = new Label(group_paramEdit, SWT.NONE);
		 * label_paramDisplayName.setText(PARAMDISPLAYNAME_LABEL_TEXT);
		 * text_paramDisplayName = new Text(group_paramEdit, SWT.BORDER);
		 * text_paramDisplayName.setLayoutData(new
		 * GridData(SWT.FILL,SWT.CENTER,true,false));
		 * 
		 * label_paramType = new Label(group_paramEdit, SWT.NONE);
		 * label_paramType.setText(PARAMTYPE_LABEL_TEXT); combo_paramType = new
		 * OperationDataTypeSelecter(group_paramEdit, SWT.NONE);
		 * combo_paramType.setLayoutData(new
		 * GridData(SWT.FILL,SWT.CENTER,true,false));
		 * 
		 * 
		 * label_defaultValue = new Label(group_paramEdit, SWT.NONE);
		 * label_defaultValue.setText(PARAMDEFAULTVALUE_LABEL_TEXT);
		 * text_defaultValue = new Text(group_paramEdit, SWT.BORDER);
		 * text_defaultValue.setLayoutData(new
		 * GridData(SWT.FILL,SWT.CENTER,true,false));
		 * 
		 * label_paramRemark = new Label(group_paramEdit, SWT.NONE);
		 * label_paramRemark.setText(ModelElementEditorDialog.REMARK_TEXT);
		 * text_paramRemark = new Text(group_paramEdit,
		 * SWT.BORDER|SWT.MULTI|SWT.VERTICAL); GridData gridData_paramRemark =
		 * new GridData(SWT.FILL,SWT.FILL,true,true,1,1);
		 * gridData_paramRemark.heightHint = 40;
		 * text_paramRemark.setLayoutData(gridData_paramRemark);
		 */

		makeAction();
		initControlValue();
		addListeners();
		return parent;
	}

	private void addListeners() {
		text_name.addKeyListener(new KeyListener() {

			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}

			public void keyReleased(KeyEvent arg0) {
				setErrorMessage(null);
				if (CheckerUtil.checkJava(text_name.getText())) {
					setErrorMessage(IBusinessClassEditorPage.ERROR_MESSAGE_NAME_JAVA);
				}
				if (CheckerUtil.checkSguap(text_name.getText())) {
					setErrorMessage(IBusinessClassEditorPage.ERROR_MESSAGE_NAME_SGUAP);
				}
			}

		});

	}

	private void createToolBar() {
		GridData gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.horizontalSpan = 2;
		gridData.grabExcessHorizontalSpace = true;
		toolBar = new ToolBar(group_param, SWT.FLAT | SWT.RIGHT);
		toolBar.setLayoutData(gridData);
	}

	private void createTable() {
		tableViewer = new TableViewer(group_param, SWT.SINGLE
				| SWT.FULL_SELECTION | SWT.BORDER | SWT.SCROLL_LINE);
		table = tableViewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		GridData tableGridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		tableGridData.heightHint = 160;
		table.setLayoutData(tableGridData);
		tableViewer.setContentProvider(new OperationParamConentProvider());
		tableViewer.setLabelProvider(new OperationParamLabelProvider());
		String[] columnNames = new String[] { COLUMN_ORDER, COLUMN_NAME,
				COLUMN_DISPLAYNAME, COLUMN_TYPE };
		int[] columnWidths = new int[] { 60, 100, 100, 130 };
		int[] columnAligns = new int[] { SWT.LEFT, SWT.LEFT, SWT.LEFT, SWT.LEFT };
		for (int i = 0; i < columnNames.length; i++) {
			TableColumn tableColumn = new TableColumn(table, columnAligns[i]);
			tableColumn.setText(columnNames[i]);
			tableColumn.setWidth(columnWidths[i]);
		}

		tableViewer.setColumnProperties(new String[] { "", COLUMN_NAME,
				COLUMN_DISPLAYNAME, COLUMN_TYPE });
		CellEditor[] cellEditors = new CellEditor[4];

		cellEditors[1] = new TextCellEditor(table, SWT.BORDER);
		cellEditors[2] = new TextCellEditor(table, SWT.BORDER);

		String[] dataTypes = DataType.getAllDataTypesString();
		cellEditors[3] = new ComboDialogCellEditor(table,
				Arrays.asList(dataTypes), null); // 显示数据
		tableViewer.setCellEditors(cellEditors);
		OperateParameterCellModifier cellModeifier = new OperateParameterCellModifier(
				tableViewer);
		tableViewer.setCellModifier(cellModeifier);
	}

	private void makeAction() {
		addParamAction = new Action(ActionTexts.MODEL_ELEMENT_ADD, ImageManager
				.getInstance().getImageDescriptor(
						ImageKeys.IMG_MODEL_OPER_NEWELEMENT)) {
			public void run() {
				// setStatue("add");
				// resetParamDetail(null);
				OperationParam op = new OperationParam();
				String newName = getNewName("param");
				op.setName(newName);
				op.setDisplayName(newName);
				op.setDataType(DataType.String.getValue_hibernet());
				// tableItems.add(op);
				tempList.add(op);
				tableViewer.refresh();
			}
		};

		deleteParamAction = new Action(ActionTexts.MODEL_ELEMENT_DELETE,
				ImageManager.getInstance().getImageDescriptor(
						ImageKeys.IMG_OBJECT_OPER_DELETE)) {
			public void run() {
				if (tableViewer.getSelection().isEmpty()) {
					MessageDialog.openInformation(tableViewer.getControl()
							.getShell(), DELETE_MESSAGE_TITLE, DELETE_MESSAGE);
				} else {
					ISelection selection = tableViewer.getSelection();
					OperationParam param = (OperationParam) ((IStructuredSelection) selection)
							.getFirstElement();
					// removedItems.add(param);
					tableViewer.remove(param);
					tempList.remove(param);
					// tableItems.remove(param);
					setStatue("add");
				}
			}
		};

		/*
		 * saveParamAction = new
		 * Action(ActionTexts.MODEL_ELEMENT_SAVE,ImageManager
		 * .getInstance().getImageDescriptor(ImageKeys.IMG_OBJECT_OPER_SAVE)){
		 * public void run(){ if(saveValidateInput() == true) {
		 * if("add".equals(getStatue())){ //先校验输入 OperationParam param = new
		 * OperationParam(); param.setName(text_paramName.getText());//?
		 * param.setDisplayName(text_paramDisplayName.getText());
		 * param.setDataType(combo_paramType.getDataTypeName());
		 * param.setOrderNum(getNextOrderNumber());
		 * param.setDefaultValue(text_defaultValue.getText());
		 * param.setRemark(text_paramRemark.getText()); saveNewParam(param);
		 * setStatue("edit"); } else if("edit".equals(getStatue())) {
		 * getEditingParam().setName(text_paramName.getText());//?
		 * getEditingParam().setDisplayName(text_paramDisplayName.getText());
		 * getEditingParam().setDataType(combo_paramType.getDataTypeName());
		 * getEditingParam().setOrderNum(getNextOrderNumber());
		 * getEditingParam().setDefaultValue(text_defaultValue.getText());
		 * getEditingParam().setRemark(text_paramRemark.getText());
		 * saveEditingParam(); } } } };
		 */

		ToolBarManager manager = new ToolBarManager(toolBar);
		manager.add(addParamAction);
		manager.add(deleteParamAction);
		manager.add(new Separator());
		manager.update(true);
	}

	private String getNewName(String baseName) {
		String newName = getNewName(baseName, 0);
		return newName;
	}

	private String getNewName(String baseName, int index) {
		String newName = baseName;
		if (index != 0) {
			newName = newName + "_" + index;
		}
		for (OperationParam param : tempList) {
			if (param != null && param.getName().equals(newName)) {
				return getNewName(baseName, index + 1);
			}
		}
		return newName;
	}

	protected void saveEditingParam() {
		tableViewer.refresh();
	}

	protected void saveNewParam(OperationParam param) {
		// tableItems.add(param);
		tempList.add(param);
		tableViewer.add(param);
		table.select(table.getItemCount() - 1);
		this.editingOperationParam = param;
	}

	private void createGroups(Composite parent) {
		group_basic = new Group(parent, SWT.NONE);
		group_basic.setText(GROUP_BASIC_TEXT);
		group_basic.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false));
		group_basic.setLayout(new GridLayout(4, false));

		group_param = new Group(parent, SWT.NONE);
		group_param.setText(GROUP_PARAM_TEXT);
		group_param
				.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		group_param.setLayout(new GridLayout(1, false));

		createToolBar();
		createTable();

		// group_operateParam = new Group(group_param, SWT.NONE);
		// //gourp_operateParam.setLayout(new GridLayout(2, false));
		// group_operateParam.setText(GROUP_OPERATEPARAM_TEXT);
		// group_operateParam.setLayout(new GridLayout(1,false));

		/*
		 * group_paramEdit = new Group(group_param, SWT.NONE);
		 * group_paramEdit.setText(GROUP_PARAMEDIT_TEXT); GridData
		 * gridData_paramEdit = new GridData(SWT.FILL,SWT.FILL,true,true);
		 * gridData_paramEdit.widthHint = 400;
		 * group_paramEdit.setLayoutData(gridData_paramEdit);
		 * group_paramEdit.setLayout(new GridLayout(1, false));
		 */

	}

	/**
	 * 操作按钮
	 */
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,
				true);
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
//		createButton(parent, APPLY_ID, APPLY_LABEL, true);
	}

	protected void buttonPressed(int buttonId) {
//		if (APPLY_ID == buttonId) {
//			applylPressed();
//		} else {
		super.buttonPressed(buttonId);
//		}
	}

	protected void okPressed() {
		if (validateAllInput() == true) {
			updateTheEditingElement();
			super.okPressed();
		}
	}



	protected void applylPressed() {
		if (validateAllInput() == true) {
			updateTheEditingElement();
			isChaged = true;
		}

	}

	public void initControlValue() {
		combo_transactionType.setItems(TransactionType
				.getTransactionTypesString());
		combo_transactionType.select(0);
		if (OPERATION_TYPE_EDIT.equals(operationType) && editingElement != null) {
			this.operationCopy = (BusinessOperation) editingElement;
			this.text_name.setText(this.operationCopy.getName());
			this.displayName = this.operationCopy.getDisplayName();
			this.text_displayName.setText(this.operationCopy.getDisplayName());
			this.text_errorMessage
					.setText(this.operationCopy.getErrorMessage());
			this.text_remark.setText(this.operationCopy.getRemark());
			this.combo_returnDataType.setDataType(this.operationCopy
					.getReturnDataType());
			int index = TransactionType.getIndex(this.operationCopy
					.getTransactionType());
			if (index >= 0) {
				this.combo_transactionType.select(index);
			}

			List<OperationParam> params = getOperationCopy()
					.getOperationParams();
			// tableItems = params;
			if (params != null) {
				for (OperationParam pa : params) {
					tempList.add(pa.clone());
				}
			}
			if (params == null || params.size() == 0) {
				setStatue("add");
			} else {
				table.select(0);
				tableViewer.setSelection(tableViewer.getSelection(), true);
			}
		} else {
			setStatue("add");
			this.operationCopy = new BusinessOperation();
			tableViewer.setInput(this.operationCopy.getOperationParams());
		}

		// tableViewer.setInput(tableItems);
		tableViewer.setInput(tempList);
		setTitleAndMessage();

	}

	private boolean validateAllInput() {
		if (text_name.getText().trim().length() == 0) {
			setErrorMessage(IBusinessClassEditorPage.ERROR_MESSAGE_NAME_NULLABLE);
			return false;
		}
		if (CheckerUtil.checkJava(text_name.getText())) {
			setErrorMessage(IBusinessClassEditorPage.ERROR_MESSAGE_NAME_JAVA);
			return false;
		}

		if (CheckerUtil.checkSguap(text_name.getText())) {
			setErrorMessage(IBusinessClassEditorPage.ERROR_MESSAGE_NAME_SGUAP);
			return false;
		}
		int i = 0;
		if (parent instanceof BusinessClass) {
			for (BusinessOperation op : ((BusinessClass) parent)
					.getOperations()) {
				if (text_name.getText().equals(op.getName())) {
					i++;
				}
			}
		}
		for (BusinessOperation op : BusinessOperation.getStandardOperations()) {
			if (text_name.getText().equals(op.getName())) {
				i++;
			}
		}
		if (editingElement == null && i > 0) {
			setErrorMessage("该方法已存在！");
			return false;
		}
		if (editingElement != null && i > 1) {
			setErrorMessage("该方法已存在！");
			return false;
		}
		if (text_displayName.getText().trim().length() == 0) {
			setErrorMessage(IBusinessClassEditorPage.ERROR_MESSAGE_DISPLAYNAME_NULLABLE);
			return false;
		}
		if (!ValidatorUtil.isFirstLowercase(text_name.getText())) {
			setErrorMessage(IBusinessClassEditorPage.ERROR_MESSAGE_NAME_LOWER);
			return false;
		}
		if (!ValidatorUtil.valiNameLength(text_name.getText())) {
			setErrorMessage(IBusinessClassEditorPage.ERROR_MESSAGE_NAME_LENGTH);
			return false;
		}
		if (!ValidatorUtil.valiDisplayName(text_displayName.getText())) {
			setErrorMessage(IBusinessClassEditorPage.ERROR_DISPLAYNAME);
			return false;
		}
		if (!ValidatorUtil.valiDisplayNameLength(text_displayName.getText())) {
			setErrorMessage(IBusinessClassEditorPage.ERROR_DISPLAYNAME_LENGTH);
			return false;
		}
		if (checkMult()) {
			setErrorMessage(IBusinessClassEditorPage.ERROR_MESSAGE_PARAMNAME_MULT);
			return false;
		}
		// if(combo_returnDataType.getSelectionIndex() < 0){
		// setErrorMessage(IBusinessClassEditorPage.ERROR_MESSAGE_RETURNTYPE_NULLABLE);
		// return false;
		// }
		if (combo_transactionType.getSelectionIndex() < 0) {
			setErrorMessage(IBusinessClassEditorPage.ERROR_MESSAGE_TRANSACTIONTYPE_NULLABLE);
			return false;
		}

		if (text_errorMessage.getText().trim().length() < 0) {
			setErrorMessage(IBusinessClassEditorPage.ERROR_MESSAGE_ERRORMESSAGE_NULLABLE);
			return false;
		}
		if (!ValidatorUtil.valiRemarkLength(text_errorMessage.getText())) {
			setErrorMessage(IBusinessClassEditorPage.ERROR_MESSAGE_ERRORMESSAGE_LENGTH);
			return false;
		}
		if (!ValidatorUtil.valiRemarkLength(text_remark.getText())) {
			setErrorMessage(IBusinessClassEditorPage.TOOLONG_ERROR_REMARK);
			return false;
		}
		setErrorMessage(null);
		return true;
	}

	public void updateTheEditingElement() {
		// if(removedItems.size() > 0){
		// for(int i = 0; i< removedItems.size(); i++)
		// {
		// for(int j = 0;j < tableItems.size();j++){
		// if(removedItems.get(i).getId().equals(tableItems.get(j).getId())){
		// tableItems.remove(j);
		// break;
		// }
		// }
		//
		// }
		// }
		// tableItems.clear();
		// TableItem[] items = tableViewer.getTable().getItems();
		// for(int i = 0;i< items.length;i++){
		// tableItems.add((OperationParam) items[i].getData());
		// }
		this.getOperationCopy().setName(text_name.getText().trim());
		this.getOperationCopy().setDisplayName(text_displayName.getText());
		this.getOperationCopy().setReturnDataType(
				combo_returnDataType.getDataTypeName());
		this.getOperationCopy().setErrorMessage(
				text_errorMessage.getText().trim());
		this.getOperationCopy().setRemark(text_remark.getText().trim());
		this.getOperationCopy().setTransactionType(
				TransactionType.getTransactionType(
						combo_transactionType.getSelectionIndex()).getValue());
		// this.getOperationCopy().setOperationParams(tableItems);
		this.getOperationCopy().setOperationParams(tempList);
		this.getOperationCopy().setOperationType(
				BusinessOperation.OPERATION_TYPE_CUSTOM);
		this.getOperationCopy().setBelongBusinessClass((BusinessClass) parent);
		this.editingElement = this.getOperationCopy();
	}

	/**
	 * 判断该名称是否存在
	 * 
	 * @param name
	 * @return
	 */
	/*
	 * public boolean isExist(String name){ if(parent!= null ){ BusinessClass
	 * businessClass = (BusinessClass)parent; if(businessClass.getOperations()
	 * != null){ for(BusinessOperation operation :
	 * businessClass.getOperations()){ if(operation.getName().equals(name)){
	 * return true; } } return false; }else{ return false; } }else{ return
	 * false; } }
	 */

	private int getNextOrderNumber() {
		// return tableItems.size() + 1;
		return tempList.size() + 1;
	}

	/**
	 * 检查是否参数名重复
	 * 
	 * @return
	 */
	private boolean checkMult() {
//		if (tableItems != null) {
//			for (int i = 0; i < tableItems.size(); i++) {
//				for (int j = i + 1; j < tableItems.size(); j++) {
//					OperationParam curParam = tableItems.get(i);
//					OperationParam temp = tableItems.get(j);
//					if (curParam.getName().equals(temp.getName())) {
//						return true;
//					}
//				}
//			}
//		}
		if (tempList != null) {
			for (int i = 0; i < tempList.size(); i++) {
				for (int j = i + 1; j < tempList.size(); j++) {
					OperationParam curParam = tempList.get(i);
					OperationParam temp = tempList.get(j);
					if (curParam.getName().equals(temp.getName())) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * 设置标题和信息
	 */
	public void setTitleAndMessage() {
		setTitle(MESSAGE_TITLE);
		setTitleImage(ImageManager.getInstance().getImage(
				ImageKeys.IMG_DIALOG_BUSINESSCLASS));
		if (operationType.equals(OPERATION_TYPE_ADD)) {
			setMessage(DIALOG_MESSAGE_ADD);
		} else {
			String displayName = this.editingElement.getDisplayName();
			setMessage(DIALOG_MESSAGE_EDIT.replaceAll("@", displayName),
					IMessageProvider.INFORMATION);
		}
	}

	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		if (operationType.equals(OPERATION_TYPE_ADD)) {
			newShell.setText(DIALOG_TITLE_ADD);
		} else {
			String displayName = this.editingElement.getDisplayName();
			newShell.setText(DIALOG_TITLE_EDIT + " " + displayName);
		}
		newShell.setImage(ImageManager.getInstance().getImage(
				ImageKeys.IMG_MODEL_TYPE_OPERATION));
	}

	public BusinessOperation getOperationCopy() {
		return operationCopy;
	}

	public OperationParam getEditingParam() {
		return editingOperationParam;
	}

	public void setEditingOperationParam(OperationParam editingOperationParam) {
		this.editingOperationParam = editingOperationParam;
	}

	public String getStatue() {
		return statue;
	}

	public void setStatue(String statue) {
		this.statue = statue;
	}

	/**
	 * 操作参数
	 * 
	 * @author xuran
	 * 
	 */
	public class OperateParameterCellModifier implements ICellModifier {
		private TableViewer tableViewer;

		public OperateParameterCellModifier(TableViewer tableViewer) {
			this.tableViewer = tableViewer;
		}

		public boolean canModify(Object element, String property) {
			return true;
		}

		public Object getValue(Object element, String property) {
			OperationParam param = (OperationParam) element;
			if (property.equals(COLUMN_NAME)) {
				String strElement = param.getName();
				return strElement;
			}
			if (property.equals(COLUMN_DISPLAYNAME)) {
				String strElement = param.getDisplayName();
				return strElement;
			}
			if (property.equals(COLUMN_TYPE)) {
				String strElement = param.getDataType();
				return strElement;
			}
			return "";
		}

		public void modify(Object element, String property, Object value) {
			TableItem item = (TableItem) element;
			OperationParam param = (OperationParam) item.getData();
			if ("".equals(value) || !isOk((String) value, property)) {
				return;
			}
			// if(property.equals(COLUMN_NAME)){
			// String strElement = (String)element;
			// return strElement;
			// }
			// if(property.equals(COLUMN_DISPLAYNAME)){
			// String strElement = (String)element;
			// return strElement;
			// }
			// if(property.equals(COLUMN_TYPE)){
			// String strElement = (String)element;
			// return enumElement.getValue();
			// }
			// OperationParam param = (OperationParam)element;
			if (property.equals(COLUMN_NAME)) {
				if (value != null) {
					param.setName((String) value);
				}
			}
			if (property.equals(COLUMN_DISPLAYNAME)) {
				param.setDisplayName((String) value);
			}
			if (property.equals(COLUMN_TYPE)) {
				param.setDataType((String) value);
			}
			tableViewer.update(param, null);
			tableViewer.refresh();
		}

		boolean isOk(String value, String property) {
			if (property.equals(COLUMN_NAME)) {
				setErrorMessage(null);
				if (CheckerUtil.checkJava(value)) {
					setErrorMessage(IBusinessClassEditorPage.ERROR_MESSAGE_NAME_JAVA);
					return false;
				}
				if (!ValidatorUtil.isFirstLowercase(value)) {
					setErrorMessage(IBusinessClassEditorPage.ERROR_MESSAGE_NAME_LOWER);
					return false;
				}
				if (!ValidatorUtil.valiNameLength(value)) {
					setErrorMessage(IBusinessClassEditorPage.ERROR_MESSAGE_NAME_LENGTH);
					return false;
				}
			}

			if (property.equals(COLUMN_DISPLAYNAME)) {
				if (!ValidatorUtil.valiDisplayName(value)) {
					setErrorMessage(IBusinessClassEditorPage.ERROR_DISPLAYNAME);
					return false;
				}
				if (!ValidatorUtil.valiDisplayNameLength(value)) {
					setErrorMessage(IBusinessClassEditorPage.ERROR_DISPLAYNAME_LENGTH);
					return false;
				}
			}
			return true;
		}

	}
}