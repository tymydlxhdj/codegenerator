package com.mqfdy.code.designer.dialogs;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.TitleAreaDialog;
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
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.ToolBar;

import com.mqfdy.code.designer.celleditor.ComboBoxCellEditor;
import com.mqfdy.code.designer.models.ActionTexts;
import com.mqfdy.code.designer.models.ImageKeys;
import com.mqfdy.code.designer.models.ImageManager;
import com.mqfdy.code.model.BusinessClass;
import com.mqfdy.code.model.Property;
import com.mqfdy.code.model.QueryCondition;
import com.mqfdy.code.model.utils.BuildInProperty;
import com.mqfdy.code.model.utils.BuildInType;
import com.mqfdy.code.model.utils.DataType;
import com.mqfdy.code.model.utils.SqlLogicOperateType;
import com.mqfdy.code.model.utils.SqlOperateType;
import com.mqfdy.code.model.utils.StringUtil;
import com.mqfdy.code.resource.validator.ValidatorUtil;
// TODO: Auto-generated Javadoc

/**
 * The Class QueryDesignPage.
 *
 * @author mqfdy
 */
public class QueryDesignPage extends Composite implements
		IBusinessClassEditorPage {

	/** The parent dialog. */
	private QueryDesignDialog parentDialog;

	/** The table viewer. */
	private TableViewer tableViewer;
	
	/** The table. */
	private Table table = null;
	
	/** The tool bar. */
	private ToolBar toolBar = null;

	/** The add action. */
	private Action addAction;
	
	/** The delete action. */
	private Action deleteAction;
	
	/** The bottom action. */
	private Action bottomAction;
	
	/** The down action. */
	private Action downAction;
	
	/** The top action. */
	private Action topAction;
	
	/** The up action. */
	private Action upAction;

	/** The group design. */
	private Group groupDesign;

	/** The sql str. */
	protected String sqlStr;

	/** The list temp. */
	private List<QueryCondition> listTemp = new ArrayList<QueryCondition>();

	/** The column 1 name. */
	private String column1Name = "前括号";
	
	/** The column 2 name. */
	private String column2Name = "属性名称";
	
	/** The column 3 name. */
	private String column3Name = "属性类型";
	
	/** The column 4 name. */
	private String column4Name = "操作符";
	
	/** The column 5 name. */
	private String column5Name = "值";
	
	/** The column 6 name. */
	private String column6Name = "后括号";
	
	/** The column 7 name. */
	private String column7Name = "逻辑运算";

	/** The is contain brackets. */
	private boolean isContainBrackets = false;

	/**
	 * Instantiates a new query design page.
	 *
	 * @param parent
	 *            the parent
	 * @param style
	 *            the style
	 * @param parentDialog
	 *            the parent dialog
	 * @param list
	 *            the list
	 */
	public QueryDesignPage(Composite parent, int style,
			QueryDesignDialog parentDialog, List<QueryCondition> list) {
		super(parent, style);
		this.parentDialog = parentDialog;
		createContents(this);
		makeActions();
		convertData(list);
		initData();
		addListeners();
	}

	/**
	 * Convert data.
	 *
	 * @author mqfdy
	 * @param list
	 *            the list
	 * @Date 2018-09-03 09:00
	 */
	private void convertData(List<QueryCondition> list) {
		if (list != null) {
			setListTemp(new ArrayList<QueryCondition>());
			for (QueryCondition condition : list) {
				getListTemp().add(condition);
			}
		}
	}

	/**
	 * Inits the data.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	private void initData() {
		tableViewer.setInput(getListTemp());
	}

	/**
	 * Creates the contents.
	 *
	 * @author mqfdy
	 * @param composite
	 *            the composite
	 * @Date 2018-09-03 09:00
	 */
	public void createContents(Composite composite) {
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		composite.setLayout(layout);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1,
				1));

		groupDesign = new Group(composite, SWT.FILL);
		groupDesign.setText("设计区");
		groupDesign.setLayout(new GridLayout(1, true));
		groupDesign.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true,
				1, 1));

		createToolBar(groupDesign);
		createTable(groupDesign);
	}

	/**
	 * Adds the listeners.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	private void addListeners() {
		
	}

	/**
	 * Creates the tool bar.
	 *
	 * @author mqfdy
	 * @param composite
	 *            the composite
	 * @Date 2018-09-03 09:00
	 */
	private void createToolBar(Composite composite) {
		GridData gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.grabExcessHorizontalSpace = true;
		toolBar = new ToolBar(composite, SWT.FLAT | SWT.RIGHT);
		toolBar.setLayoutData(gridData);
	}

	/**
	 * Creates the table.
	 *
	 * @author mqfdy
	 * @param composite
	 *            the composite
	 * @Date 2018-09-03 09:00
	 */
	private void createTable(Composite composite) {
		tableViewer = new TableViewer(composite, SWT.SINGLE
				| SWT.FULL_SELECTION | SWT.BORDER | SWT.SCROLL_LINE);
		table = tableViewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		GridData griddata_Properties = new GridData(SWT.FILL, SWT.FILL, true,
				true, 1, 1);
		griddata_Properties.heightHint = 120;
		table.setLayoutData(griddata_Properties);
		tableViewer.setLabelProvider(new TableLabelProvider());
		tableViewer.setContentProvider(new ContentProvider());

		String[] columnNames = new String[] { column1Name, column2Name,column3Name,
				column4Name, column5Name, column6Name, column7Name };

		int[] columnWidths = new int[] { 60, 150, 80 , 80, 150, 60, 60 };

		int[] columnAligns = new int[] { SWT.LEFT, SWT.LEFT,SWT.LEFT, SWT.LEFT,
				SWT.LEFT, SWT.LEFT, SWT.LEFT };

		for (int i = 0; i < columnNames.length; i++) {
			TableColumn tableColumn = new TableColumn(table, columnAligns[i]);
			tableColumn.setText(columnNames[i]);
			tableColumn.setWidth(columnWidths[i]);
		}

		CellEditor[] cellEditors = new CellEditor[7];

		cellEditors[0] = new ComboBoxCellEditor(table,
				new String[] { "", "(" }, SWT.READ_ONLY);
		cellEditors[1] = new ComboBoxCellEditor(table, getProperties(),
				SWT.READ_ONLY);
		
		cellEditors[2] = new TextCellEditor(table, SWT.READ_ONLY);
		
		if (BusinessClass.STEREOTYPE_BUILDIN
				.equals(this.parentDialog.getBusinessClass().getStereotype())) {
			cellEditors[3] = new ComboBoxCellEditor(
					table,
					new String[] { SqlOperateType.Oracle_equals.getDispName() },
					SWT.READ_ONLY);
		} else {
			cellEditors[3] = new ComboBoxCellEditor(table, SqlOperateType
					.getSqlOperateDispNames().toArray(new String[] {}),
					SWT.READ_ONLY);
		}

		if (this.parentDialog.getBusinessClass().getName()
				.equals(BuildInType.BusinessOrganization.getValue())) {
			cellEditors[4] = new ComboBoxCellEditor(table, new String[] {
					"DEPT", "CORP" }, SWT.NONE);
		} else {
			cellEditors[4] = new ComboBoxCellEditor(table, new String[] { "" },
					SWT.NONE);
		}
		cellEditors[5] = new ComboBoxCellEditor(table,
				new String[] { "", ")" }, SWT.READ_ONLY);
		cellEditors[6] = new ComboBoxCellEditor(table, SqlLogicOperateType
				.getSqlLogicOperateDisNames().toArray(new String[] {}),
				SWT.READ_ONLY);
		ValueCellModifier cellModeifier = new ValueCellModifier(tableViewer);
		tableViewer.setCellModifier(cellModeifier);
		tableViewer
				.setColumnProperties(new String[] { column1Name, column2Name,column3Name,
						column4Name, column5Name, column6Name, column7Name });
		tableViewer.setCellEditors(cellEditors);

		tableViewer.setLabelProvider(new TableLabelProvider());
		tableViewer.setContentProvider(new ContentProvider());
	}

	/*
	 * private static String PROPERTY_ORG_NAME = "组织名称"; private static String
	 * PROPERTY_ORG_PROPERTY = "组织性质";
	 * 
	 * private static String PROPERTY_USER_NAME = "用户姓名"; private static String
	 * PROPERTY_USER_LOGIN_NAME = "用户登录名"; private static String
	 * PROPERTY_USER_ID = "用户ID";
	 */

	/**
	 * Filter properties.
	 *
	 * @author mqfdy
	 * @param businessClass
	 *            the business class
	 * @return the list
	 * @Date 2018-09-03 09:00
	 */
	private List<Property> filterProperties(BusinessClass businessClass) {
		List<Property> properties = null;
		if (businessClass != null) {
			properties = new ArrayList();
			if (BuildInProperty.type_org.equals(parentDialog.getBusinessClass()
					.getName())) {
				List<BuildInProperty> buildinProperties = BuildInProperty
						.getPropertyTypes(BuildInProperty.type_org);
				if (buildinProperties != null) {
					for (BuildInProperty p : buildinProperties) {
						Property property = new Property();
						property.setName(p.getDispValue());
						properties.add(property);
					}
				}
			}

			if (BuildInProperty.type_user.equals(parentDialog
					.getBusinessClass().getName())) {
				List<BuildInProperty> buildinProperties = BuildInProperty
						.getPropertyTypes(BuildInProperty.type_user);
				if (buildinProperties != null) {
					for (BuildInProperty p : buildinProperties) {
						Property property = new Property();
						property.setName(p.getDispValue());
						properties.add(property);
					}
				}
			}
		}
		return properties;
	}

	/**
	 * Gets the properties.
	 *
	 * @author mqfdy
	 * @return the properties
	 * @Date 2018-09-03 09:00
	 */
	private String[] getProperties() {
		List<Property> properties = null;
		if (BusinessClass.STEREOTYPE_BUILDIN
				.equals(this.parentDialog.getBusinessClass().getStereotype())) {
			// 当设计内置模型的查询条件时，只提供固定的属性
			properties = filterProperties(parentDialog.getBusinessClass());
		} else {
			properties = this.parentDialog.getBusinessClass().getProperties();
		}
		String[] items = new String[] {};
		if (properties != null) {
			items = new String[properties.size()];
			for (int i = 0; i < properties.size(); i++) {
				items[i] = properties.get(i).getName();
			}
		}
		return items;
	}

	/**
	 * Refresh table.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	private void refreshTable() {
		tableViewer.refresh();
	}

	/**
	 * Make actions.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	private void makeActions() {
		// 新增操作
		addAction = new Action(ActionTexts.MODEL_ELEMENT_ADD, ImageManager
				.getInstance()
				.getImageDescriptor(ImageKeys.IMG_OBJECT_OPER_ADD)) {
			public void run() {
				QueryCondition condition = new QueryCondition();
				getListTemp().add(condition);
				tableViewer.refresh();
				table.select(getListTemp().size()-1);
			}
		};

		// 删除操作
		deleteAction = new Action(ActionTexts.MODEL_ELEMENT_DELETE,
				ImageManager.getInstance().getImageDescriptor(
						ImageKeys.IMG_OBJECT_OPER_DELETE)) {
			public void run() {
				if (tableViewer.getSelection().isEmpty()) {
					parentDialog.setErrorMessage("请选择一条记录");
				} else {
					MessageDialog mes = new MessageDialog(tableViewer
							.getControl().getShell(), OPERATE_ITEM_NULL_TITLE,
							null, DELETE_CONFIRM, 0,
							new String[] { "确定", "取消" }, 0);
					if (mes.open() == TitleAreaDialog.OK) {
						ISelection selection = tableViewer.getSelection();
						QueryCondition element = (QueryCondition) ((IStructuredSelection) selection)
								.getFirstElement();
						getListTemp().remove(element);
						refreshTable();
					}

				}
			}
		};

		// 置底操作
		bottomAction = new Action(ActionTexts.MODEL_ELEMENT_BOTTOM,
				ImageManager.getInstance().getImageDescriptor(
						ImageKeys.IMG_BOTTOM)) {
			public void run() {
				move("bottom");
			}
		};
		// 下移操作
		downAction = new Action(ActionTexts.MODEL_ELEMENT_DOWN, ImageManager
				.getInstance().getImageDescriptor(ImageKeys.IMG_DOWN)) {
			public void run() {
				move("down");
			}
		};
		// 置顶操作
		topAction = new Action(ActionTexts.MODEL_ELEMENT_TOP, ImageManager
				.getInstance().getImageDescriptor(ImageKeys.IMG_TOP)) {
			public void run() {
				move("top");
			}
		};
		// 上移操作
		upAction = new Action(ActionTexts.MODEL_ELEMENT_UP, ImageManager
				.getInstance().getImageDescriptor(ImageKeys.IMG_UP)) {
			public void run() {
				move("up");
			}
		};

		ToolBarManager manager = new ToolBarManager(toolBar);
		manager.add(addAction);
		manager.add(deleteAction);
		manager.add(bottomAction);
		manager.add(downAction);
		manager.add(topAction);
		manager.add(upAction);
		manager.add(new Separator());
		manager.update(true);

	}

	/**
	 * Move.
	 *
	 * @author mqfdy
	 * @param action
	 *            the action
	 * @Date 2018-09-03 09:00
	 */
	private void move(String action) {
		if (tableViewer.getSelection().isEmpty()) {

		} else {
			ISelection selection = tableViewer.getSelection();
			QueryCondition element = (QueryCondition) ((IStructuredSelection) selection)
					.getFirstElement();
			resetOrderNum(element, action);
			refreshTable();
		}
	}

	/**
	 * Reset order num.
	 *
	 * @author mqfdy
	 * @param element
	 *            the element
	 * @param type
	 *            the type
	 * @Date 2018-09-03 09:00
	 */
	private void resetOrderNum(QueryCondition element, String type) {
		if ("up".equals(type)) {
			for (int i = 0; i < getListTemp().size(); i++) {
				QueryCondition temp = getListTemp().get(i);
				if (temp.getId().equals(element.getId())) {
					if (i > 0) {
						getListTemp().add(i - 1, element);
						getListTemp().remove(i + 1);
						break;
					}
				}
			}
		} else if ("down".equals(type)) {
			for (int i = 0; i < getListTemp().size(); i++) {
				QueryCondition temp = getListTemp().get(i);
				if (temp.getId().equals(element.getId())) {
					getListTemp().add(i + 2, element);
					getListTemp().remove(i);
					break;
				}
			}
		} else if ("top".equals(type)) {
			for (int i = 0; i < getListTemp().size(); i++) {
				QueryCondition temp = getListTemp().get(i);
				if (temp.getId().equals(element.getId())) {
					getListTemp().add(0, element);
					getListTemp().remove(i + 1);
					break;
				}
			}
		} else if ("bottom".equals(type)) {
			for (int i = 0; i < getListTemp().size(); i++) {
				QueryCondition temp = getListTemp().get(i);
				if (temp.getId().equals(element.getId())) {
					getListTemp().add(getListTemp().size(), element);
					getListTemp().remove(i);
					break;
				}
			}
		}
	}

	/**
	 * LabelProvider.
	 *
	 * @author xuran
	 */
	static class TableLabelProvider extends LabelProvider implements
			ITableLabelProvider {

		/**
		 * Gets the column image.
		 *
		 * @author mqfdy
		 * @param arg0
		 *            the arg 0
		 * @param arg1
		 *            the arg 1
		 * @return the column image
		 * @Date 2018-09-03 09:00
		 */
		public Image getColumnImage(Object arg0, int arg1) {
			// TODO Auto-generated method stub
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
			if (element instanceof QueryCondition) {
				QueryCondition condition = (QueryCondition) element;
				switch (columnIndex) {
				case 0:
					return StringUtil.convertNull2EmptyStr(condition
							.getPreBraces());
				case 1:
					return StringUtil.convertNull2EmptyStr(condition
							.getPropertyName());
				case 2:
					return StringUtil.convertNull2EmptyStr(condition
							.getDataType());
				case 3:
					return StringUtil.convertNull2EmptyStr(condition
							.getSqlOperateType().getDispName());
				case 4:
					return StringUtil.convertNull2EmptyStr(condition
							.getValue1());
				case 5:
					return StringUtil.convertNull2EmptyStr(condition
							.getAfterBraces());
				case 6:
					return StringUtil.convertNull2EmptyStr(condition
							.getSqlLogicOperateType().getDispName());
				}
			}
			return "";
		}
	}

	/**
	 * contentProvider.
	 *
	 * @author xuran
	 */
	static class ContentProvider implements IStructuredContentProvider {
		
		/**
		 * Gets the elements.
		 *
		 * @author mqfdy
		 * @param inputElement
		 *            the input element
		 * @return the elements
		 * @Date 2018-09-03 09:00
		 */
		public Object[] getElements(Object inputElement) {
			if (inputElement instanceof List) {
				Object[] objects = ((List) inputElement).toArray();
				if (objects != null) {
					for (int i = 0; i < objects.length; i++) {
						QueryCondition element = (QueryCondition) objects[i];
						element.setOrderNum(i + 1);
					}
				}
				return objects;
			}
			return new Object[0];
		}

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
	}

	/**
	 * 单元格修改器.
	 *
	 * @author LQR
	 */
	public class ValueCellModifier implements ICellModifier {
		
		/** The table viewer. */
		private TableViewer tableViewer;

		/**
		 * Instantiates a new value cell modifier.
		 *
		 * @param tableViewer
		 *            the table viewer
		 */
		public ValueCellModifier(TableViewer tableViewer) {
			this.tableViewer = tableViewer;

		}

		/**
		 * Can modify.
		 *
		 * @author mqfdy
		 * @param element
		 *            the element
		 * @param property
		 *            the property
		 * @return true, if successful
		 * @Date 2018-09-03 09:00
		 */
		public boolean canModify(Object element, String property) {
			if (property != null && property.equals(column3Name)) {
				return false;
			}
			return true;
		}

		/**
		 * Gets the value.
		 *
		 * @author mqfdy
		 * @param element
		 *            the element
		 * @param property
		 *            the property
		 * @return the value
		 * @Date 2018-09-03 09:00
		 */
		public Object getValue(Object element, String property) {
			QueryCondition condition = (QueryCondition) element;
			if (property != null && property.equals(column1Name)) {
				return condition.getPreBraces();
			}
			if (property != null && property.equals(column2Name)) {
				return condition.getPropertyName();
			}
			if (property != null && property.equals(column4Name)) {
				SqlOperateType type = condition.getSqlOperateType();
				if (type != null) {
					return type.getDispName();
				}
			}
			if (property != null && property.equals(column5Name)) {
				if (condition.getValue1() != null) {
					return condition.getValue1();
				} else {
					return "";
				}

			}
			if (property != null && property.equals(column6Name)) {
				return condition.getAfterBraces();
			}
			if (property != null && property.equals(column7Name)) {
				SqlLogicOperateType type = condition.getSqlLogicOperateType();
				if (type != null) {
					return type.getDispName();
				}
			}
			return "";
		}

		/**
		 * Modify.
		 *
		 * @author mqfdy
		 * @param element
		 *            the element
		 * @param property
		 *            the property
		 * @param value
		 *            the value
		 * @Date 2018-09-03 09:00
		 */
		public void modify(Object element, String property, Object value) {
			if (value == null) {
				return;
			}
			TableItem item = (TableItem) element;
			QueryCondition condition = (QueryCondition) item.getData();
			if (property.equals(column1Name)) {
				condition.setPreBraces((String) value);
			}
			if (property.equals(column2Name)) {
				condition.setPropertyName((String) value);
				condition.setDataType(getDataType((String)value).getValue_hibernet());
			}
			if (property.equals(column4Name)) {
				condition.setSqlOperateType(SqlOperateType
						.getSqlOperateTypeByDispName((String) value));
			}
			if (property.equals(column5Name)) {
				condition.setValue1((String) value);
			}
			if (property.equals(column6Name)) {
				condition.setAfterBraces((String) value);
			}
			if (property.equals(column7Name)) {
				condition.setSqlLogicOperateType(SqlLogicOperateType
						.getSqlLogicOperateTypeByName((String) value));
			}
			tableViewer.update(condition, null);
		}

	}

	/**
	 * 
	 */
	public void initControlValue() {
		// TODO Auto-generated method stub

	}

	/**
	 * @return
	 */
	public boolean validateInput() {
		isContainBrackets = false;
		StringBuffer bracketsStr = new StringBuffer();
		// 检查listTemp
		if (getListTemp() != null && parentDialog != null) {
			parentDialog.setErrorMessage(null);
			for (int i = 0; i < getListTemp().size(); i++) {
				QueryCondition condition = getListTemp().get(i);
				if (condition != null) {
					if ("(".equals(condition.getPreBraces())) {
						isContainBrackets = true;
						bracketsStr.append(condition.getPreBraces());
					}
					if (StringUtil.isEmpty(condition.getPropertyName())) {
						parentDialog.setErrorMessage("第" + (i + 1) + "行:"
								+ ERROR_MESSAGE_SQL_PROPERTY_NAME_NULLABLE);
						return false;
					}
					if (SqlOperateType.NONE.equals(condition
							.getSqlOperateType())) {
						parentDialog.setErrorMessage("第" + (i + 1) + "行:"
								+ ERROR_MESSAGE_SQL_OPERATE_NULLABLE);
						return false;
					}
					if (StringUtil.isEmpty(condition.getValue1())) {
						parentDialog.setErrorMessage("第" + (i + 1) + "行:"
								+ ERROR_MESSAGE_SQL_VALUE_NULLABLE);
						return false;
					}
					DataType dataType = getDataType(condition.getPropertyName());

					if (condition.getSqlOperateType().Oracle_in
							.equals(condition.getSqlOperateType())
							|| condition.getSqlOperateType().Oracle_notIn
									.equals(condition.getSqlOperateType())) {
						// 如果是数组，则隔开校验
						String[] values = condition.getValue1().split(",");
						for (String value : values) {
							if (!validateValue(dataType, value, i)) {
								return false;
							}
						}
					} else {
						if (!validateValue(dataType, condition.getValue1(), i)) {
							return false;
						}
					}

					if (")".equals(condition.getAfterBraces())) {
						bracketsStr.append(condition.getAfterBraces());
					}
					if (i < getListTemp().size() - 1) {
						// 不是最后一条
						if (SqlLogicOperateType.NONE.equals(condition
								.getSqlLogicOperateType())) {
							parentDialog.setErrorMessage("第" + (i + 1) + "行:"
									+ ERROR_MESSAGE_SQL_LOGIC_OPERATE_NULLABLE);
							return false;
						}
					}
				}
			}

			// 判断前后括号是否正确
			if (!valiedateBraces(bracketsStr.toString())) {
				parentDialog.setErrorMessage(ERROR_MESSAGE_SQL_BRACES);
				return false;
			}
		}
		return true;
	}

	/**
	 * Validate value.
	 *
	 * @author mqfdy
	 * @param dataType
	 *            the data type
	 * @param value
	 *            the value
	 * @param i
	 *            the i
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	private boolean validateValue(DataType dataType, String value, int i) {
		switch (dataType) {
		case Integer:
			if (!ValidatorUtil.valiInteger(value)) {
				parentDialog.setErrorMessage("第" + (i + 1) + "行:"
						+ ERROR_MESSAGE_SQL_VALUE_DATATYPE);
				return false;
			}
			break;
		case Double:
			if (!ValidatorUtil.valiDouble(value)) {
				parentDialog.setErrorMessage("第" + (i + 1) + "行:"
						+ ERROR_MESSAGE_SQL_VALUE_DATATYPE);
				return false;
			}
			break;
		case Float:
			if (!ValidatorUtil.valiFloat(value)) {
				parentDialog.setErrorMessage("第" + (i + 1) + "行:"
						+ ERROR_MESSAGE_SQL_VALUE_DATATYPE);
				return false;
			}
			break;
		case Big_decimal:
			if (!(ValidatorUtil.valiDouble(value) || ValidatorUtil
					.valiLong(value))) {
				parentDialog.setErrorMessage("第" + (i + 1) + "行:"
						+ ERROR_MESSAGE_SQL_VALUE_DATATYPE);
				return false;
			}
			break;
		case Long:
			if (!ValidatorUtil.valiLong(value)) {
				parentDialog.setErrorMessage("第" + (i + 1) + "行:"
						+ ERROR_MESSAGE_SQL_VALUE_DATATYPE);
				return false;
			}
			break;
		case Short:
			if (!ValidatorUtil.valiShort(value)) {
				parentDialog.setErrorMessage("第" + (i + 1) + "行:"
						+ ERROR_MESSAGE_SQL_VALUE_DATATYPE);
				return false;
			}
			break;
		case Date:
			if (!ValidatorUtil.valiDate(value)) {
				parentDialog.setErrorMessage("第" + (i + 1) + "行:"
						+ ERROR_MESSAGE_SQL_VALUE_DATATYPE);
				return false;
			}
			break;
		case Time:
			if (!ValidatorUtil.valiTime(value)) {
				parentDialog.setErrorMessage("第" + (i + 1) + "行:"
						+ ERROR_MESSAGE_SQL_VALUE_DATATYPE);
				return false;
			}
			break;
		case Timestamp:
			if (!ValidatorUtil.valiDateTime(value)) {
				parentDialog.setErrorMessage("第" + (i + 1) + "行:"
						+ ERROR_MESSAGE_SQL_VALUE_DATATYPE);
				return false;
			}
			break;
		}
		return true;
	}

	/**
	 * Gets the data type.
	 *
	 * @author mqfdy
	 * @param name
	 *            the name
	 * @return the data type
	 * @Date 2018-09-03 09:00
	 */
	public DataType getDataType(String name) {
		String dataType = "";
		List<Property> properties = parentDialog.getBusinessClass()
				.getProperties();
		if (properties != null) {
			for (int i = 0; i < properties.size(); i++) {
				Property property = properties.get(i);
				if (property != null && property.getName().equals(name)) {
					dataType = property.getDataType();
					return DataType.getDataType(dataType);
				}
			}
		}
		return DataType.String;
	}

	/**
	 * Valiedate braces.
	 *
	 * @author mqfdy
	 * @param strBraces
	 *            the str braces
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	private boolean valiedateBraces(String strBraces) {
		Stack stack = new Stack();
		if (strBraces == null) {
			return false;
		} else {
			if ("".equals(strBraces)) {
				return true;
			} else {
				char chars[] = strBraces.toCharArray();
				for (int i = 0; i < chars.length; i++) {
					char brace = chars[i];
					if (brace == '(') {
						stack.push(brace);
					}
					if (brace == ')') {
						if (!stack.empty()) {
							stack.pop();
						} else {
							return false;
						}
					}
				}
				if (stack.empty()) {
					// 最后，如果栈空，则通过验证
					return true;
				} else {
					return false;
				}
			}

		}
	}

	/**
	 * 
	 */
	public void updateTheEditingElement() {
		if(parentDialog.getParentPage().getParentPage() instanceof PropertyEditorPage){
			((PropertyEditorPage) parentDialog.getParentPage().getParentPage()).getParentDialog()
			.setConditions(this.getListTemp());
		}
		if(parentDialog.getParentPage().getParentPage() instanceof FkEditorPage){
			((FkEditorPage) parentDialog.getParentPage().getParentPage()).getParentDialog()
			.setConditions(this.getListTemp());
		}
		
	}

	/**
	 * Gets the list temp.
	 *
	 * @author mqfdy
	 * @return the list temp
	 * @Date 2018-09-03 09:00
	 */
	public List<QueryCondition> getListTemp() {
		return listTemp;
	}

	/**
	 * Sets the list temp.
	 *
	 * @author mqfdy
	 * @param listTemp
	 *            the new list temp
	 * @Date 2018-09-03 09:00
	 */
	public void setListTemp(List<QueryCondition> listTemp) {
		this.listTemp = listTemp;
	}

	/**
	 * sql: name like 'aa%' and id=11 or (status between 1 and 3 and name=1)
	 * 
	 * (deptno like 1% or deptno like 2%) and deptno <> 111
	 * 
	 * id=1 or id=2 and name=3
	 * 
	 * json:
	 * 
	 * filter:[ { junction:"or",//列内组合 columnJunction:"and",//列之间的组合
	 * 
	 * criterions:[ {fieldName:"name","operator":"=",value:"10251"},
	 * {fieldName:"name","operator":"^",value:"10252"} ] },
	 * 
	 * { junction:"or", columnJunction:"and", criterions:[
	 * {fieldName:"id","operator":"=",value:"10251"},
	 * {fieldName:"id","operator":"*",value:"10252"} ] },
	 * 
	 * { junction:"or", columnJunction:"and", criterions:[
	 * {fieldName:"id","operator":"=",value:"10251"},
	 * {fieldName:"id","operator":"*",value:"10252"} ] } ]
	 */
}
