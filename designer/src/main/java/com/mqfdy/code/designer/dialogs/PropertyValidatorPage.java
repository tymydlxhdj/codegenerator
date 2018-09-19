package com.mqfdy.code.designer.dialogs;

import java.util.Collection;
import java.util.List;
import java.util.Vector;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.ToolBar;

import com.mqfdy.code.designer.models.ActionTexts;
import com.mqfdy.code.designer.models.ImageKeys;
import com.mqfdy.code.designer.models.ImageManager;
import com.mqfdy.code.model.AbstractModelElement;
import com.mqfdy.code.model.Property;
import com.mqfdy.code.model.Validator;
import com.mqfdy.code.model.utils.EditorType;
import com.mqfdy.code.model.utils.ParamType;
import com.mqfdy.code.model.utils.ValidatorType;

// TODO: Auto-generated Javadoc
/**
 * 属性校验器编辑页面.
 *
 * @author mqfdy
 */
public class PropertyValidatorPage extends Composite implements
		IBusinessClassEditorPage {

	/** 属性编辑弹出框. */
	private PropertyEditorDialog parentDialog;

	/** The Constant GROUP_VALIDATORLIST_TEXT. */
	public static final String GROUP_VALIDATORLIST_TEXT = "已应用校验器";

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

	/** The group validator list. */
	private Group group_validatorList;

	/** The add validator action. */
	private Action addValidatorAction;// 新增
	
	/** The delete validator action. */
	private Action deleteValidatorAction;// 删除

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
	
	/** The page. */
	PropertyValidatorPage page = this;
	
	/** 当前编辑的校验器. */
	// private Validator editingValidator;

	/**
	 * 表格数据源
	 */
	private Vector<Validator> tableItems = new Vector<Validator>();

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
			case 0:// 序号、校验器类型、校验器名称、失败提示信息
				return validator.getOrderNum() + "";
			case 1:
				return validator.getValidatorType();
			case 2:
				return validator.getDisplayName() + "";
			case 3:
				return validator.getValidatorMessage();
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
	public PropertyValidatorPage(Composite parent, int style) {
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
	public PropertyValidatorPage(Composite parent, int style,
			PropertyEditorDialog parentDialog) {
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
		layout.numColumns = 1;
		composite.setLayout(layout);

		createToolBar();

		GridData layoutData = new GridData(GridData.FILL_BOTH);
		group_validatorList = new Group(composite, SWT.NONE);
		group_validatorList.setText(GROUP_VALIDATORLIST_TEXT);
		group_validatorList.setLayoutData(layoutData);
//		group_validatorList.setLayout(new FillLayout());

		GridLayout gridLayout_primarykey = new GridLayout(1, false);
		gridLayout_primarykey.marginTop = 4;
		group_validatorList.setLayout(gridLayout_primarykey);
		
		
		createValidatorListTable();

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
		GridData gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.verticalAlignment = SWT.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		tableViewer = new TableViewer(group_validatorList, SWT.SINGLE
				| SWT.FULL_SELECTION | SWT.BORDER);
		table = tableViewer.getTable();
		table.setLayoutData(gridData);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		tableViewer.setContentProvider(new ValidatorConentProvider());
		tableViewer.setLabelProvider(new ValidatorLabelProvider());
		String[] columnNames = new String[] { "序号", "校验器类型", "校验器名称", "失败提示信息" };
		int[] columnWidths = new int[] { 40, 150, 150, 150 };
		int[] columnAligns = new int[] { SWT.LEFT, SWT.LEFT, SWT.LEFT, SWT.LEFT };
		for (int i = 0; i < columnNames.length; i++) {
			TableColumn tableColumn = new TableColumn(table, columnAligns[i]);
			tableColumn.setText(columnNames[i]);
			tableColumn.setWidth(columnWidths[i]);
		}
		tableViewer.setInput(tableItems);
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
				PropertyValidatorEditDialog s = new PropertyValidatorEditDialog(
						page, getShell(), parentDialog.getPropertyCopy());
				if (s.open() == TitleAreaDialog.OK) {
					tableItems.add(s.getEditingValidator());
					tableViewer.refresh();
				}
				setStatue(STATUS_ADD);
				// resetValidatorDetail(null);
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
					// resetValidatorDetail(null);
				}
			}
		};

		// 表格点击事件
		// tableViewer.addSelectionChangedListener(new
		// ISelectionChangedListener() {
		// public void selectionChanged(SelectionChangedEvent event) {
		// ISelection selection = event.getSelection();
		// Validator validator =
		// (Validator)((IStructuredSelection)selection).getFirstElement();
		// // resetValidatorDetail(validator);
		// PropertyValidatorEditDialog dia = new
		// PropertyValidatorEditDialog(getShell(),validator);
		// dia.open();
		// setStatue(STATUS_EDIT);
		// }
		// });
		// 表格双击事件
		tableViewer.addDoubleClickListener(new IDoubleClickListener() {

			public void doubleClick(DoubleClickEvent event) {
				ISelection selection = event.getSelection();
				Validator validator = (Validator) ((IStructuredSelection) selection)
						.getFirstElement();
				PropertyValidatorEditDialog dia = new PropertyValidatorEditDialog(
						page, getShell(), validator, validator
								.getBelongProperty());
				if (dia.open() == TitleAreaDialog.OK) {
					validator = dia.getEditingValidator();
					tableViewer.refresh();
				}
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
		manager.add(upAction);
		manager.add(topAction);
		manager.add(downAction);
		manager.add(bottomAction);
		manager.update(true);
	}

	/**
	 * Gets the next order number.
	 *
	 * @author mqfdy
	 * @return the next order number
	 * @Date 2018-09-03 09:00
	 */
	public int getNextOrderNumber() {
		return tableItems.size() + 1;
	}

	/**
	 * Gets the items.
	 *
	 * @author mqfdy
	 * @return the items
	 * @Date 2018-09-03 09:00
	 */
	public List<Validator> getItems() {
		return tableItems;
	}

	/**
	 * 
	 */
	public void initControlValue() {
		tableItems.clear();
		AbstractModelElement editingElement = parentDialog.getPropertyCopy();
		if (editingElement instanceof Property) {
			Property property = (Property) editingElement;
			java.util.List<Validator> validators = property.getValidators();
			if (validators != null) {
				for (int i = 0; i < validators.size(); i++) {
					tableItems.add(validators.get(i));
				}
			}
		}
		tableViewer.refresh();
	}

	/**
	 * @return
	 */
	public boolean validateInput() {
		
		if(parentDialog.getPropertyEditorPage().getCurType()!=null && parentDialog.getPropertyEditorPage().getCurType().getValue().equals(EditorType.TimeEditor.getValue())){
			for (int i = 0; i < tableItems.size(); i++) {
				Validator temp = tableItems.get(i);
				if(temp.getValidatorType()!=null && temp.getValidatorType().equals(ValidatorType.DateTime.getValue())){
					if(temp.getValidatorParams()!= null){
						if(temp.getValidatorParams().get(ParamType.minDate.getValue()).indexOf("-") > -1){
							parentDialog.setErrorMessage("日期检验器最早日期格式应该是时间类型，例如： 12:00:00");
							return false;
						}
						if(temp.getValidatorParams().get(ParamType.maxDate.getValue()).indexOf("-") > -1){
							parentDialog.setErrorMessage("日期检验器最晚日期格式应该是时间类型，例如： 12:00:00");
							return false;
						}
					}
				}
			}
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
		parentDialog.getPropertyCopy().setValidators(tableItems);
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
	 * Refresh.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	public void refresh() {
		tableViewer.refresh();
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

	/**
	 * Gets the table items.
	 *
	 * @author mqfdy
	 * @return the table items
	 * @Date 2018-09-03 09:00
	 */
	public Vector<Validator> getTableItems() {
		return tableItems;
	}

	/**
	 * Gets the parent dialog.
	 *
	 * @author mqfdy
	 * @return the parent dialog
	 * @Date 2018-09-03 09:00
	 */
	public PropertyEditorDialog getParentDialog() {
		return parentDialog;
	}
}
