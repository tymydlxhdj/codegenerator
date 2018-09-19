package com.mqfdy.code.designer.dialogs;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.ToolBar;

import com.mqfdy.code.designer.dialogs.widget.NullToEmptyText;
import com.mqfdy.code.designer.models.ActionTexts;
import com.mqfdy.code.designer.models.ImageKeys;
import com.mqfdy.code.designer.models.ImageManager;
import com.mqfdy.code.model.AbstractModelElement;
import com.mqfdy.code.model.DTOProperty;
import com.mqfdy.code.model.Validator;
import com.mqfdy.code.model.utils.ParamType;
import com.mqfdy.code.model.utils.StringUtil;
import com.mqfdy.code.model.utils.ValidatorType;

// TODO: Auto-generated Javadoc
/**
 * DTO属性校验器页.
 *
 * @author mqfdy
 */
public class DTOPropertyValidatorPage extends Composite implements
		IBusinessClassEditorPage {
	
	/** The parent dialog. */
	DTOPropertyEditDialog parentDialog;
	
	/** 属性编辑弹出框. */

	public static final String GROUP_VALIDATORLIST_TEXT = "已应用校验策略列表";
	
	/** The Constant GROUP_VALIDATOREDITOR_TEXT. */
	public static final String GROUP_VALIDATOREDITOR_TEXT = "数据校验策略编辑";
	
	/** The Constant GROUP_VALIDATORPARAM_TEXT. */
	public static final String GROUP_VALIDATORPARAM_TEXT = "校验参数";

	/** The Constant VALIDATORNAME_LABEL_TEXT. */
	public static final String VALIDATORNAME_LABEL_TEXT = "策略名称：";
	
	/** The Constant VALIDATORTYPE_LABEL_TEXT. */
	public static final String VALIDATORTYPE_LABEL_TEXT = "校验器类型：";
	
	/** The Constant ERRORMESSAGE_LABEL_TEXT. */
	public static final String ERRORMESSAGE_LABEL_TEXT = "失败提示：";

	/** The Constant DELETE_MESSAGE. */
	public static final String DELETE_MESSAGE = "请选择要删除的对象";
	
	/** The Constant DELETE_MESSAGE_TITLE. */
	public static final String DELETE_MESSAGE_TITLE = "校验器";

	/** The tool bar. */
	private ToolBar toolBar = null;

	/** The table. */
	private Table table;
	
	/** The table viewer. */
	private TableViewer tableViewer;

	/** The label validator type. */
	private Label label_validatorType;
	
	/** The list validator type. */
	private List list_validatorType;

	/** The label error message. */
	private Label label_errorMessage;
	
	/** The text error message. */
	private NullToEmptyText text_errorMessage;

	/** The combo validator param. */
	private Combo combo_validatorParam;
	
	/** The text validator param. */
	private NullToEmptyText text_validatorParam;

	/** The group validator list. */
	private Group group_validatorList;
	
	/** The group validator editor. */
	private Group group_validatorEditor;
	
	/** The group validator param. */
	private Group group_validatorParam;

	/** The add validator action. */
	private Action addValidatorAction;// 新增
	
	/** The delete validator action. */
	private Action deleteValidatorAction;// 删除
	
	/** The save validator action. */
	private Action saveValidatorAction;// 保存

	/** The up action. */
	private Action upAction;
	
	/** The down action. */
	private Action downAction;
	
	/** The top action. */
	private Action topAction;
	
	/** The bottom action. */
	private Action bottomAction;

	/** The statue. */
	private String statue = STATUS_NO;

	/** 当前编辑的校验器. */
	private Validator editingValidator;

	/** 表格数据源. */
	private Vector<Validator> tableItems = new Vector<Validator>();

	/** 校验器参数. */
	private Map<String, String> validatorParams = new HashMap<String, String>();

	/**
	 * 校验器表格内容 提供者.
	 *
	 * @author LQR
	 */
	private class ValidatorConentProvider implements IStructuredContentProvider {
		
		/**
		 * 
		 */
		public void dispose() {
		}

		/**
		 * Input changed.
		 *
		 * @author mqfdy
		 * @param viewer
		 *            the viewer
		 * @param oldInput
		 *            the old input
		 * @param newInput
		 *            the new input
		 * @Date 2018-09-03 09:00
		 */
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}

		/**
		 * Gets the elements.
		 *
		 * @author mqfdy
		 * @param inputElement
		 *            the input element
		 * @return the elements
		 * @Date 2018-09-03 09:00
		 */
		@SuppressWarnings("rawtypes")
		public Object[] getElements(Object inputElement) {
			if (inputElement instanceof Collection) {
				Object[] objects = ((Collection) inputElement).toArray();
				if (objects != null) {
					for (int i = 0; i < objects.length; i++) {
						Validator element = (Validator) objects[i];
						element.setOrderNum(i + 1);
					}
				}
				return objects;
			}
			return new Object[0];
		}

	}

	/**
	 * 校验器表格标签提供者.
	 *
	 * @author LQR
	 */
	private class ValidatorLabelProvider extends LabelProvider implements
			ITableLabelProvider {

		/**
		 * Gets the column image.
		 *
		 * @author mqfdy
		 * @param element
		 *            the element
		 * @param columnIndex
		 *            the column index
		 * @return the column image
		 * @Date 2018-09-03 09:00
		 */
		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}

		/**
		 * Gets the column text.
		 *
		 * @author mqfdy
		 * @param element
		 *            the element
		 * @param columnIndex
		 *            the column index
		 * @return the column text
		 * @Date 2018-09-03 09:00
		 */
		public String getColumnText(Object element, int columnIndex) {
			Validator validator = (Validator) element;
			switch (columnIndex) {
			case 0:
				return validator.getOrderNum() + "";
			case 1:
				return validator.getDisplayName();
			}
			return "";
		}
	}

	/**
	 * 构造函数(用于新增).
	 *
	 * @param parent
	 *            上级容器
	 * @param style
	 *            样式
	 */
	public DTOPropertyValidatorPage(Composite parent, int style) {
		super(parent, style);
	}

	/**
	 * 构造函数(用于编辑).
	 *
	 * @param parent
	 *            上级容器
	 * @param style
	 *            样式
	 * @param parentDialog
	 *            属性编辑弹出框
	 */
	public DTOPropertyValidatorPage(Composite parent, int style,
			DTOPropertyEditDialog parentDialog) {
		super(parent, style);
		this.parentDialog = parentDialog;
		createContents(this);
	}

	/**
	 * 创建页面内容.
	 *
	 * @author mqfdy
	 * @param composite
	 *            the composite
	 * @Date 2018-09-03 09:00
	 */
	private void createContents(Composite composite) {
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		composite.setLayout(layout);

		createToolBar();

		GridData layoutData = new GridData(GridData.FILL_VERTICAL);
		layoutData.widthHint = 230;
		group_validatorList = new Group(composite, SWT.NONE);
		group_validatorList.setText(GROUP_VALIDATORLIST_TEXT);
		group_validatorList.setLayoutData(layoutData);
		group_validatorList.setLayout(new FillLayout());

		group_validatorEditor = new Group(composite, SWT.NONE);
		group_validatorEditor.setText(GROUP_VALIDATOREDITOR_TEXT);
		group_validatorEditor.setLayoutData(new GridData(SWT.FILL, SWT.FILL,
				true, true, 1, 1));
		GridLayout validatorEditorLayout = new GridLayout();
		validatorEditorLayout.numColumns = 2;
		group_validatorEditor.setLayout(validatorEditorLayout);

		createValidatorListTable();
		createValidatorEditorPanel();

		makeActions();

	}

	/**
	 * 创建工具条.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	private void createToolBar() {
		GridData gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.horizontalSpan = 2;
		gridData.grabExcessHorizontalSpace = true;
		toolBar = new ToolBar(this, SWT.FLAT | SWT.RIGHT);
		toolBar.setLayoutData(gridData);
	}

	/**
	 * 创建校验策略列表表格.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	private void createValidatorListTable() {
		tableViewer = new TableViewer(group_validatorList, SWT.SINGLE
				| SWT.FULL_SELECTION | SWT.BORDER);
		table = tableViewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		tableViewer.setContentProvider(new ValidatorConentProvider());
		tableViewer.setLabelProvider(new ValidatorLabelProvider());
		String[] columnNames = new String[] { "序号", "校验策略" };
		int[] columnWidths = new int[] { 40, 150 };
		int[] columnAligns = new int[] { SWT.LEFT, SWT.LEFT };
		for (int i = 0; i < columnNames.length; i++) {
			TableColumn tableColumn = new TableColumn(table, columnAligns[i]);
			tableColumn.setText(columnNames[i]);
			tableColumn.setWidth(columnWidths[i]);
		}
		tableViewer.setInput(tableItems);
	}

	/**
	 * 创建校验器编辑面板.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	private void createValidatorEditorPanel() {
		/*
		 * label_name = new Label(group_validatorEditor,SWT.NONE);
		 * label_name.setText(VALIDATORNAME_LABEL_TEXT); text_name = new
		 * Text(group_validatorEditor,SWT.BORDER); text_name.setLayoutData(new
		 * GridData(SWT.FILL,SWT.CENTER,true,false));
		 */

		label_validatorType = new Label(group_validatorEditor, SWT.NONE);
		label_validatorType.setText(VALIDATORTYPE_LABEL_TEXT);
		list_validatorType = new List(group_validatorEditor, SWT.VERTICAL
				| SWT.BORDER);
		list_validatorType.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
				true));

		group_validatorParam = new Group(group_validatorEditor, SWT.NONE);
		group_validatorParam.setText(GROUP_VALIDATORPARAM_TEXT);
		group_validatorParam.setLayoutData(new GridData(SWT.FILL, SWT.CENTER,
				false, false, 2, 1));
		group_validatorParam.setLayout(new GridLayout(2, false));
		combo_validatorParam = new Combo(group_validatorParam, SWT.DROP_DOWN
				| SWT.READ_ONLY);
		GridData comboGriddata = new GridData(SWT.FILL, SWT.CENTER, false,
				false);
		comboGriddata.widthHint = 60;
		combo_validatorParam.setLayoutData(comboGriddata);
		text_validatorParam = new NullToEmptyText(group_validatorParam,
				SWT.BORDER);
		text_validatorParam.setLayoutData(new GridData(SWT.FILL, SWT.CENTER,
				true, false));

		label_errorMessage = new Label(group_validatorEditor, SWT.NONE);
		label_errorMessage.setText(ERRORMESSAGE_LABEL_TEXT);
		text_errorMessage = new NullToEmptyText(group_validatorEditor,
				SWT.MULTI | SWT.BORDER | SWT.VERTICAL | SWT.WRAP);
		GridData gridData = new GridData(SWT.FILL, SWT.CENTER, true, false);
		gridData.heightHint = 50;
		text_errorMessage.setLayoutData(gridData);

	}

	/**
	 * 初始化动作.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	private void makeActions() {
		addValidatorAction = new Action(ActionTexts.MODEL_ELEMENT_ADD,
				ImageManager.getInstance().getImageDescriptor(
						ImageKeys.IMG_MODEL_OPER_NEWELEMENT)) {
			public void run() {
				setStatue(STATUS_ADD);
				resetValidatorDetail(null);
			}
		};

		deleteValidatorAction = new Action(ActionTexts.MODEL_ELEMENT_DELETE,
				ImageManager.getInstance().getImageDescriptor(
						ImageKeys.IMG_OBJECT_OPER_DELETE)) {
			public void run() {
				if (tableViewer.getSelection().isEmpty()) {
					MessageDialog.openInformation(tableViewer.getControl()
							.getShell(), DELETE_MESSAGE_TITLE, DELETE_MESSAGE);
				} else {
					ISelection selection = tableViewer.getSelection();
					Validator validator = (Validator) ((IStructuredSelection) selection)
							.getFirstElement();
					tableItems.remove(validator);
					refreshTable();
					setStatue(STATUS_NO);
					resetValidatorDetail(null);
				}
			}
		};

		// 保存事件
		saveValidatorAction = new Action(ActionTexts.MODEL_ELEMENT_SAVE,
				ImageManager.getInstance().getImageDescriptor(
						ImageKeys.IMG_OBJECT_OPER_SAVE)) {
			public void run() {
				if (validateInput() == true) {
					validatorParamsUpdate();
					if (STATUS_ADD.equals(getStatue())
							|| STATUS_NO.equals(getStatue())) {
						// 先校验输入
						Validator newValidator = new Validator();
						ValidatorType validatorType = ValidatorType
								.getValidatorType(list_validatorType
										.getSelectionIndex());
						newValidator.setName(validatorType.getValue());
						newValidator.setDisplayName(validatorType
								.getDisplayValue());
						newValidator.setOrderNum(getNextOrderNumber());
						newValidator.setValidatorType(validatorType.getValue());
						newValidator.setValidatorParams(validatorParams);
						newValidator.getValidatorParams().putAll(
								validatorParams);
						if (StringUtil.isEmpty(text_errorMessage.getText())) {
							newValidator.setValidatorMessage(validatorType
									.getErrorMessage());
						} else {
							newValidator.setValidatorMessage(text_errorMessage
									.getText());
						}
						saveNewValidator(newValidator);
					} else if (STATUS_EDIT.equals(getStatue())) {
						ValidatorType validatorType = ValidatorType
								.getValidatorType(list_validatorType
										.getSelectionIndex());
						getEditingValidator().setName(validatorType.getValue());
						getEditingValidator().setDisplayName(
								validatorType.getDisplayValue());
						getEditingValidator().setOrderNum(getNextOrderNumber());
						getEditingValidator().setValidatorType(
								validatorType.getValue());
						if (StringUtil.isEmpty(text_errorMessage.getText())) {
							getEditingValidator().setValidatorMessage(
									validatorType.getErrorMessage());
						} else {
							getEditingValidator().setValidatorMessage(
									text_errorMessage.getText());
						}
						saveEditingValidator();
					}
				}
			}
		};

		// 下拉框事件
		combo_validatorParam.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (combo_validatorParam.getItems() != null
						&& combo_validatorParam.getItems().length > 0) {
					String paramDisplay = combo_validatorParam.getText();
					String paramKey = ParamType.getParamTypeValue(paramDisplay);
					if (STATUS_ADD.equals(getStatue())) {
						text_validatorParam.setText(validatorParams
								.get(paramKey));
					} else if (STATUS_EDIT.equals(getStatue())) {
						text_validatorParam.setText(getEditingValidator()
								.getValidatorParams().get(paramKey));
					}
				}
			}
		});

		// 下拉列表点击事件
		list_validatorType.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				resetValidatorParam();
			}
		});

		// 参数文本框焦点失去时d
		text_validatorParam.addFocusListener(new FocusListener() {
			public void focusLost(FocusEvent e) {
				validatorParamsUpdate();
			}

			public void focusGained(FocusEvent e) {
			}
		});

		// 表格点击事件
		tableViewer
				.addSelectionChangedListener(new ISelectionChangedListener() {
					public void selectionChanged(SelectionChangedEvent event) {
						ISelection selection = event.getSelection();
						Validator validator = (Validator) ((IStructuredSelection) selection)
								.getFirstElement();
						resetValidatorDetail(validator);
						setStatue(STATUS_EDIT);
					}
				});

		upAction = new Action(ActionTexts.MODEL_ELEMENT_UP, ImageManager
				.getInstance().getImageDescriptor(ImageKeys.IMG_UP)) {
			public void run() {
				if (tableViewer.getSelection().isEmpty()) {
					MessageDialog.openInformation(tableViewer.getControl()
							.getShell(), OPERATE_ITEM_NULL_TITLE,
							OPERATE_ITEM_NULL_MESSAGE);
				} else {
					ISelection selection = tableViewer.getSelection();
					Validator validator = (Validator) ((IStructuredSelection) selection)
							.getFirstElement();
					resetOrderNum(validator, "up");
					refreshTable();
				}
			}
		};

		topAction = new Action(ActionTexts.MODEL_ELEMENT_TOP, ImageManager
				.getInstance().getImageDescriptor(ImageKeys.IMG_TOP)) {
			public void run() {
				if (tableViewer.getSelection().isEmpty()) {
					MessageDialog.openInformation(tableViewer.getControl()
							.getShell(), OPERATE_ITEM_NULL_TITLE,
							OPERATE_ITEM_NULL_MESSAGE);
				} else {
					ISelection selection = tableViewer.getSelection();
					Validator validator = (Validator) ((IStructuredSelection) selection)
							.getFirstElement();
					resetOrderNum(validator, "top");
					refreshTable();
				}
			}
		};

		downAction = new Action(ActionTexts.MODEL_ELEMENT_DOWN, ImageManager
				.getInstance().getImageDescriptor(ImageKeys.IMG_DOWN)) {
			public void run() {
				if (tableViewer.getSelection().isEmpty()) {
					MessageDialog.openInformation(tableViewer.getControl()
							.getShell(), OPERATE_ITEM_NULL_TITLE,
							OPERATE_ITEM_NULL_MESSAGE);
				} else {
					ISelection selection = tableViewer.getSelection();
					Validator validator = (Validator) ((IStructuredSelection) selection)
							.getFirstElement();
					resetOrderNum(validator, "down");
					refreshTable();
				}
			}
		};

		bottomAction = new Action(ActionTexts.MODEL_ELEMENT_BOTTOM,
				ImageManager.getInstance().getImageDescriptor(
						ImageKeys.IMG_BOTTOM)) {
			public void run() {
				if (tableViewer.getSelection().isEmpty()) {
					MessageDialog.openInformation(tableViewer.getControl()
							.getShell(), OPERATE_ITEM_NULL_TITLE,
							OPERATE_ITEM_NULL_MESSAGE);
				} else {
					ISelection selection = tableViewer.getSelection();
					Validator validator = (Validator) ((IStructuredSelection) selection)
							.getFirstElement();
					resetOrderNum(validator, "bottom");
					refreshTable();
				}
			}
		};

		ToolBarManager manager = new ToolBarManager(toolBar);
		manager.add(addValidatorAction);
		manager.add(deleteValidatorAction);
		manager.add(saveValidatorAction);
		manager.add(upAction);
		manager.add(topAction);
		manager.add(downAction);
		manager.add(bottomAction);
		manager.update(true);
	}

	/**
	 * 更新校验参数列表.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	private void validatorParamsUpdate() {
		if (combo_validatorParam.getSelectionIndex() < 0) {
			return;
		}
		String text = text_validatorParam.getText().trim();
		if (text.length() > 0) {
			String paramString = combo_validatorParam
					.getItem(combo_validatorParam.getSelectionIndex());
			String key = ParamType.getParamTypeValue(paramString);
			if ("add".equals(getStatue())) {
				validatorParams.put(key, text);
			} else {
				getEditingValidator().getValidatorParams().put(key, text);
			}
		}
	}

	/**
	 * Gets the next order number.
	 *
	 * @author mqfdy
	 * @return the next order number
	 * @Date 2018-09-03 09:00
	 */
	private int getNextOrderNumber() {
		return tableItems.size() + 1;
	}

	/**
	 * 新增.
	 *
	 * @author mqfdy
	 * @param validator
	 *            the validator
	 * @Date 2018-09-03 09:00
	 */
	private void saveNewValidator(Validator validator) {
		tableItems.add(validator);
		refreshTable();
		table.select(table.getItemCount() - 1);
		resetValidatorDetail(validator);

	}

	/**
	 * 保存.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	private void saveEditingValidator() {
		tableViewer.refresh();
	}

	/**
	 * 
	 */
	public void initControlValue() {
		list_validatorType.setItems(ValidatorType.getValidatorTypesString());
		AbstractModelElement editingElement = parentDialog.getProperty();
		if (editingElement instanceof DTOProperty) {
			DTOProperty property = (DTOProperty) editingElement;
			java.util.List<Validator> validators = property.getValidators();
			for (int i = 0; i < validators.size(); i++) {
				tableItems.add(validators.get(i));
			}
		}
		tableViewer.refresh();
	}

	/**
	 * @return
	 */
	public boolean validateInput() {
		if (STATUS_NO.equals(getStatue()) || STATUS_ADD.equals(getStatue())) {
			if (list_validatorType.getSelectionIndex() < 0) {
				parentDialog
						.setErrorMessage(ERROR_MESSAGE_VALIDATORTYPE_NULLABLE);
				return false;
			}
			for (int i = 0; i < tableItems.size(); i++) {
				Validator temp = tableItems.get(i);
				if (temp.getValidatorType().equals(
						ValidatorType.getValidatorType(
								list_validatorType.getSelectionIndex())
								.getValue())) {
					parentDialog
							.setErrorMessage(ERROR_MESSAGE_VALIDATORTYPE_EXIST);
					return false;
				}
			}
			if (parentDialog != null) {
				
				parentDialog.setErrorMessage(null);
			}
			return true;
		} else if (STATUS_EDIT.equals(getStatue())) {
			for (int i = 0; i < tableItems.size(); i++) {
				Validator temp = tableItems.get(i);
				if (temp.getValidatorType().equals(
						ValidatorType.getValidatorType(
								list_validatorType.getSelectionIndex())
								.getValue())) {
					if (!temp.getId().equals(getEditingValidator().getId())) {
						parentDialog
								.setErrorMessage(ERROR_MESSAGE_VALIDATORTYPE_EXIST);
						return false;
					}
				}
			}
			if (parentDialog != null) {
				
				parentDialog.setErrorMessage(null);
			}
			return true;
		}
		return true;
	}

	/**
	 * 
	 */
	public void updateTheEditingElement() {
		for (int i = 0; i < tableItems.size(); i++) {
			Validator temp = tableItems.get(i);
			temp.setOrderNum(i);
		}
		DTOProperty property = (DTOProperty) (parentDialog.getProperty());
		property.setValidators(tableItems);
	}

	/**
	 * 刷新表格.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	public void refreshTable() {
		tableViewer.refresh();
	}

	/**
	 * 重置右边校验器明细.
	 *
	 * @author mqfdy
	 * @param validator
	 *            the validator
	 * @Date 2018-09-03 09:00
	 */
	private void resetValidatorDetail(Validator validator) {
		this.editingValidator = validator;
		if (validator == null) {
			// text_name.setText("");
			list_validatorType.select(0);
			text_errorMessage.setText("");
			combo_validatorParam.setItems(new String[] {});
			setStatue(STATUS_ADD);
		} else {
			// text_name.setText(validator.getDisplayName());
			text_errorMessage.setText(validator.getValidatorMessage());
			String validatorType = validator.getValidatorType();
			int index = ValidatorType.getIndex(validatorType);
			list_validatorType.setSelection(index);
			resetValidatorParam();
			setStatue(STATUS_EDIT);
		}

	}

	/**
	 * 重新设置校验参数.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	private void resetValidatorParam() {
		int index = list_validatorType.getSelectionIndex();
		ValidatorType validatorType = ValidatorType.getValidatorType(index);
		if (validatorType != null) {
			String[] paramDisplays = ParamType
					.getParamTypesString(validatorType.getValue());
			String[] paramKeys = ParamType.getParamTypesValue(validatorType
					.getValue());
			combo_validatorParam.setItems(paramDisplays);
			if (paramKeys.length > 0) {
				combo_validatorParam.select(0);
				if (this.editingValidator != null) {
					Map<String, String> paramMap = this.editingValidator
							.getValidatorParams();
					text_validatorParam.setText(paramMap.get(paramKeys[0]));
				}
			}
			text_errorMessage.setText(validatorType.getErrorMessage());
		}
	}

	/**
	 * Reset order num.
	 *
	 * @author mqfdy
	 * @param validator
	 *            the validator
	 * @param type
	 *            the type
	 * @Date 2018-09-03 09:00
	 */
	private void resetOrderNum(Validator validator, String type) {
		if ("up".equals(type)) {
			for (int i = 0; i < tableItems.size(); i++) {
				Validator temp = tableItems.get(i);
				if (temp.getId().equals(validator.getId())) {
					if (i > 0) {
						tableItems.add(i - 1, validator);
						tableItems.remove(i + 1);
						break;
					}
				}
			}
		} else if ("down".equals(type)) {
			for (int i = 0; i < tableItems.size(); i++) {
				Validator temp = tableItems.get(i);
				if (temp.getId().equals(validator.getId())) {
					tableItems.add(i + 2, validator);
					tableItems.remove(i);
					break;
				}
			}
		} else if ("top".equals(type)) {
			for (int i = 0; i < tableItems.size(); i++) {
				Validator temp = tableItems.get(i);
				if (temp.getId().equals(validator.getId())) {
					tableItems.add(0, validator);
					tableItems.remove(i + 1);
					break;
				}
			}
		} else if ("bottom".equals(type)) {
			for (int i = 0; i < tableItems.size(); i++) {
				Validator temp = tableItems.get(i);
				if (temp.getId().equals(validator.getId())) {
					tableItems.add(tableItems.size(), validator);
					tableItems.remove(i);
					break;
				}
			}
		}
	}

	/**
	 * Gets the editing validator.
	 *
	 * @author mqfdy
	 * @return the editing validator
	 * @Date 2018-09-03 09:00
	 */
	public Validator getEditingValidator() {
		return editingValidator;
	}

	/**
	 * Gets the statue.
	 *
	 * @author mqfdy
	 * @return the statue
	 * @Date 2018-09-03 09:00
	 */
	public String getStatue() {
		return statue;
	}

	/**
	 * Sets the statue.
	 *
	 * @author mqfdy
	 * @param statue
	 *            the new statue
	 * @Date 2018-09-03 09:00
	 */
	public void setStatue(String statue) {
		this.statue = statue;
	}

}
