package com.mqfdy.code.designer.dialogs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;

import com.mqfdy.code.designer.dialogs.actions.TableViewerActionGroup;
import com.mqfdy.code.designer.dialogs.widget.NullToEmptyText;
import com.mqfdy.code.designer.models.ActionTexts;
import com.mqfdy.code.designer.models.BusinessModelManager;
import com.mqfdy.code.designer.models.ImageKeys;
import com.mqfdy.code.designer.models.ImageManager;
import com.mqfdy.code.designer.provider.PropertyLabelProvider;
import com.mqfdy.code.designer.utils.BusinessModelUtil;
import com.mqfdy.code.designer.utils.CheckerUtil;
import com.mqfdy.code.designer.views.modelresource.actions.AddPropertyAction;
import com.mqfdy.code.model.AbstractModelElement;
import com.mqfdy.code.model.BusinessClass;
import com.mqfdy.code.model.IModelElement;
import com.mqfdy.code.model.PKProperty;
import com.mqfdy.code.model.PersistenceProperty;
import com.mqfdy.code.model.Property;
import com.mqfdy.code.model.utils.StringUtil;
import com.mqfdy.code.resource.validator.ValidatorUtil;

// TODO: Auto-generated Javadoc
/**
 * 业务实体基本信息编辑页.
 *
 * @author mqfdy
 */
public class BusinessClassBasicInfoPage extends Composite implements
		IBusinessClassEditorPage {

	/** The Constant GROUP_PERSITENCE_TEXT. */
	public static final String GROUP_PERSITENCE_TEXT = "持久化策略";
	
	/** The Constant TABLENAME_LABEL_TEXT. */
	public static final String TABLENAME_LABEL_TEXT = "数据库表名：";
	
	/** The Constant TABLESCHEMA_LABEL_TEXT. */
	public static final String TABLESCHEMA_LABEL_TEXT = "模式名：";

	/** The label name. */
	// 显示组件
	private Label label_name;
	
	/** The text name. */
	private NullToEmptyText text_name;

	/** The label display name. */
	private Label label_displayName;
	
	/** The text display name. */
	private NullToEmptyText text_displayName;
	
	/** The label remark. */
	private Label label_remark;
	
	/** The text remark. */
	private NullToEmptyText text_remark;
	
	/** The label table name. */
	private Label label_tableName;
	
	/** The text table name. */
	private NullToEmptyText text_tableName;
	
	/** The schema name. */
	private Label schemaName;
	
	/** The schema name text. */
	private Text schemaNameText;
	
	/** The business class editor dialog. */
	private BusinessClassEditorDialog businessClassEditorDialog;
	
	/** The tool bar. */
	private ToolBar toolBar = null;
	
	/** The table. */
	private Table table = null;
	
	/** The table viewer. */
	private TableViewer tableViewer = null;
	
	/** The version panel. */
	private VersionInfoPanel versionPanel;

	/** 新增属性动作. */
	private AddPropertyAction addPropertyAction;
	
	/** The delete property action. */
	private Action deletePropertyAction;
	
	/** The up action. */
	private Action upAction;
	
	/** The down action. */
	private Action downAction;
	
	/** The top action. */
	private Action topAction;
	
	/** The bottom action. */
	private Action bottomAction;

	/** The table items. */
	private List<Property> tableItems = new ArrayList<Property>();
	
	/** The group list. */
	private Group group_list;

	/**
	 * Instantiates a new business class basic info page.
	 *
	 * @param parent
	 *            the parent
	 * @param style
	 *            the style
	 * @param businessClassEditorDialog
	 *            the business class editor dialog
	 */
	public BusinessClassBasicInfoPage(Composite parent, int style,
			BusinessClassEditorDialog businessClassEditorDialog) {
		super(parent, style);
		this.businessClassEditorDialog = businessClassEditorDialog;
		createContents(this);
		addListeners();
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
		layout.numColumns = 4;
		composite.setLayout(layout);

		GridData layoutData_normal = new GridData();
		layoutData_normal.horizontalAlignment = SWT.FILL;
		layoutData_normal.grabExcessHorizontalSpace = true;

		label_name = new Label(composite, SWT.NONE);
		label_name.setText(ModelElementEditorDialog.NAME_TEXT);
		text_name = new NullToEmptyText(composite, SWT.BORDER);
		text_name.setLayoutData(layoutData_normal);

		label_displayName = new Label(composite, SWT.NONE);
		label_displayName.setText(ModelElementEditorDialog.DISPLAYNAME_TEXT);
		text_displayName = new NullToEmptyText(composite, SWT.BORDER);
		text_displayName.setLayoutData(layoutData_normal);

		label_tableName = new Label(composite, SWT.NONE);
		label_tableName.setText(TABLENAME_LABEL_TEXT);
		text_tableName = new NullToEmptyText(composite, SWT.BORDER);
		text_tableName.setLayoutData(layoutData_normal);
		
		schemaName = new Label(composite, SWT.NONE);
		schemaName.setText(TABLESCHEMA_LABEL_TEXT);
		schemaNameText = new Text(composite, SWT.BORDER);
		schemaNameText.setLayoutData(layoutData_normal);

		versionPanel = new VersionInfoPanel(composite, SWT.NONE | SWT.FILL);
		GridData versionGridData = new GridData(GridData.FILL_HORIZONTAL);
		versionGridData.horizontalSpan = 4;
		versionGridData.grabExcessHorizontalSpace = true;
		versionGridData.exclude = true;
		versionPanel.setLayoutData(versionGridData);
		versionPanel.setVisible(false);

		label_remark = new Label(composite, SWT.NONE);
		label_remark.setText(ModelElementEditorDialog.REMARK_TEXT);
		text_remark = new NullToEmptyText(composite, SWT.BORDER | SWT.MULTI
				| SWT.VERTICAL | SWT.WRAP);
		GridData layoutData_remark = new GridData();
		layoutData_remark = new GridData(GridData.FILL_HORIZONTAL);
		layoutData_remark.horizontalSpan = 3;
		layoutData_remark.widthHint = 80;
		layoutData_remark.heightHint = 40;
		text_remark.setLayoutData(layoutData_remark);
		group_list = new Group(composite, SWT.NONE);
		group_list.setText("属性列表");
		GridData g = new GridData(GridData.FILL_BOTH);
		g.horizontalSpan = 4;
		group_list.setLayoutData(g);
		group_list.setLayout(new GridLayout(1, false));
		createToolBar();
		createTable();
		makeActions();
	}

	/**
	 * Adds the listeners.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	private void addListeners() {
		text_name.addKeyListener(new KeyListener() {

			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}

			public void keyReleased(KeyEvent arg0) {
				if (businessClassEditorDialog != null) {
					
					businessClassEditorDialog.setErrorMessage(null);
				}
				if (CheckerUtil.checkJava(text_name.getText())) {
					businessClassEditorDialog
							.setErrorMessage(ERROR_MESSAGE_NAME_JAVA);
				}
				if (CheckerUtil.checkSguap(text_name.getText())) {
					businessClassEditorDialog
							.setErrorMessage(ERROR_MESSAGE_NAME_SGUAP);
				}
				if (ModelElementEditorDialog.OPERATION_TYPE_ADD
						.equals(businessClassEditorDialog.getOperationType())
						&& CheckerUtil.checkIsExist(text_name.getText(),
								BusinessModelManager.BUSINESSCLASS_NAME_PREFIX)) {
					businessClassEditorDialog
							.setErrorMessage(ERROR_MESSAGE_NAME_EXIST);
				}
			}

		});

		text_displayName.addFocusListener(new FocusListener() {
			public void focusLost(FocusEvent e) {
			}

			public void focusGained(FocusEvent e) {
				text_displayName.selectAll();

			}
		});
		text_tableName.addKeyListener(new KeyListener() {

			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}

			public void keyReleased(KeyEvent arg0) {
				if (businessClassEditorDialog != null ) {
					
					businessClassEditorDialog.setErrorMessage(null);
				}
				valiTableName();
			}
		});
	}

	/**
	 * Vali table name.
	 *
	 * @author mqfdy
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	/*
	 * 数据库表名重复
	 */
	public boolean valiTableName(){
		if (CheckerUtil.checkSql(text_tableName.getText().trim())) {
			businessClassEditorDialog
					.setErrorMessage(ERROR_MESSAGE_NAME_SQL);
		}

		if (!IModelElement.STEREOTYPE_REVERSE.equals(businessClassEditorDialog
				.getBusinessClassCopy().getStereotype()) && 
				!ValidatorUtil.valiTableName(text_tableName.getText(), businessClassEditorDialog
						.getBusinessClassCopy(), BusinessModelUtil.getEditorBusinessModelManager().getBusinessObjectModel())) {
			businessClassEditorDialog.setErrorMessage("数据库表名重复");
			return false;
		}
		return true;
	}
	
	/**
	 * 初始化各个控件的值.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	public void initControlValue() {
		BusinessClass businessClass = businessClassEditorDialog
				.getBusinessClassCopy();
		// TableViewerActionGroup actionGroup = new
		// TableViewerActionGroup(tableViewer,1,businessClassEditorDialog);
		// actionGroup.fillContextMenu(new MenuManager(),deletePropertyAction);
		final TableViewerActionGroup actionGroup = new TableViewerActionGroup(
				tableViewer, 1, businessClassEditorDialog);
		final MenuManager menu = new MenuManager();
		actionGroup.fillContextMenu(menu, deletePropertyAction);
		table.addMouseListener(new MouseListener() {

			public void mouseUp(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			public void mouseDown(MouseEvent e) {
				if (e.button == 3)
					actionGroup.fillContextMenu(menu, deletePropertyAction);
			}

			public void mouseDoubleClick(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});
		if (businessClassEditorDialog.getOperationType().equals(
				ModelElementEditorDialog.OPERATION_TYPE_ADD)) {
			text_name.setText(businessClass.getName());
			text_tableName.setText(BusinessModelUtil.getTableName(businessClass.getName()));
			text_displayName.setText(businessClass.getName());
			if(businessClass.getExtendAttributies()!=null&&businessClass.getExtendAttributies().get(IModelElement.KEY_SCHEMA)!=null){
				schemaNameText.setText(businessClass.getExtendAttributies().get(IModelElement.KEY_SCHEMA).toString());
			}
		} else {
			text_name.setText(StringUtil.convertNull2EmptyStr(businessClass
					.getName()));
			text_displayName.setText(StringUtil
					.convertNull2EmptyStr(businessClass.getDisplayName()));
			text_remark.setText(StringUtil.convertNull2EmptyStr(businessClass
					.getRemark()));
			text_tableName.setText(StringUtil
					.convertNull2EmptyStr(businessClass.getTableName()));
			if(businessClass.getExtendAttributies()!=null&&businessClass.getExtendAttributies().get(IModelElement.KEY_SCHEMA)!=null){
				schemaNameText.setText(StringUtil
						.convertNull2EmptyStr(businessClass.getExtendAttributies().get(IModelElement.KEY_SCHEMA).toString()));
			}
			versionPanel.initControlValue(businessClass.getVersionInfo());
		}
		List<Property> temp = businessClassEditorDialog.getBusinessClassCopy()
				.getProperties();
		for (int i = 0; i < temp.size(); i++) {
			tableItems.add(temp.get(i));
		}
		tableViewer.setInput(tableItems);
		tableViewer.refresh();
		if (businessClassEditorDialog.getOperationType().equals(
				ModelElementEditorDialog.OPERATION_TYPE_EDIT) 
				&& IModelElement.STEREOTYPE_REVERSE.equals(businessClass.getStereotype())) {
			text_tableName.setEnabled(false);
			schemaNameText.setEnabled(false);
		}
	}

	/**
	 * 校验文本框的输入.
	 *
	 * @author mqfdy
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	public boolean validateInput() {
		if (text_name.getText().trim().length() == 0) {
			businessClassEditorDialog
					.setErrorMessage(ERROR_MESSAGE_NAME_NULLABLE);
			return false;
		}
		// if (!text_name.getText().trim().matches("^[a-zA-Z][a-zA-Z_0-9]*$"))
		// {
		// businessClassEditorDialog.setErrorMessage(ERROR_MESSAGE_NAME_RULE);
		// return false;
		// }
		if (!ValidatorUtil.isFirstUppercase(text_name.getText())) {
			businessClassEditorDialog
					.setErrorMessage(IBusinessClassEditorPage.ERROR_MESSAGE_NAME_UPPER);
			return false;
		}
		if (!ValidatorUtil.valiNameLength(text_name.getText())) {
			businessClassEditorDialog
					.setErrorMessage(IBusinessClassEditorPage.ERROR_MESSAGE_NAME_LENGTH);
			return false;
		}
		if (CheckerUtil.checkJava(text_name.getText())) {
			businessClassEditorDialog.setErrorMessage(ERROR_MESSAGE_NAME_JAVA);
			return false;
		}

		if (CheckerUtil.checkJavaString(text_name.getText())) {
			businessClassEditorDialog.setErrorMessage("名称不能是‘Java’!");
			return false;
		}
		if (CheckerUtil.checkCountString(text_name.getText())) {
			businessClassEditorDialog.setErrorMessage("名称不能是‘count’!");
			return false;
		}
		if (CheckerUtil.checkSguap(text_name.getText())) {
			businessClassEditorDialog.setErrorMessage(ERROR_MESSAGE_NAME_SGUAP);
			return false;
		}
		if (ModelElementEditorDialog.OPERATION_TYPE_ADD
				.equals(businessClassEditorDialog.getOperationType())) {
			if (CheckerUtil.checkIsExist(text_name.getText(),
					BusinessModelManager.BUSINESSCLASS_NAME_PREFIX)) {
				businessClassEditorDialog
						.setErrorMessage(ERROR_MESSAGE_NAME_EXIST);
				return false;
			}
		} else {
			// 编辑时
			if (!text_name.getText().equalsIgnoreCase(
					businessClassEditorDialog.getBusinessClassCopy().getName())) {
				if (CheckerUtil.checkIsExist(text_name.getText(),
						BusinessModelManager.BUSINESSCLASS_NAME_PREFIX)) {
					businessClassEditorDialog
							.setErrorMessage(ERROR_MESSAGE_NAME_EXIST);
					return false;
				}
			}
		}

		if (text_tableName.getText().trim().length() == 0) {
			businessClassEditorDialog
					.setErrorMessage(ERROR_MESSAGE_DATABASENAME_NULLABLE);
			return false;
		}
		if (text_tableName.getEnabled() && CheckerUtil.checkSql(text_tableName.getText().trim())) {
			businessClassEditorDialog.setErrorMessage("数据库表名是SQL关键字！");
			return false;
		}
		if (!ValidatorUtil.valiFirstNo_Name(text_tableName.getText())) {
			businessClassEditorDialog.setErrorMessage("数据库表名格式不正确！");
			return false;
		}
		if (schemaNameText.getEnabled() && CheckerUtil.checkSql(schemaNameText.getText().trim())) {
			businessClassEditorDialog.setErrorMessage("模式名是SQL关键字！");
			return false;
		}
		if (schemaNameText.getText()!=""&&schemaNameText.getText()!=null&&!ValidatorUtil.valiFirstNo_Name(schemaNameText.getText())) {
			businessClassEditorDialog.setErrorMessage("模式名格式不正确！");
			return false;
		}
		if (!ValidatorUtil.valiNameLength(text_tableName.getText())) {
			businessClassEditorDialog
					.setErrorMessage(ERROR_MESSAGE_DATABASENAME_LENGTH);
			return false;
		}
		if(!valiTableName()){
			return false;
		}
		if (text_displayName.getText().trim().length() == 0) {
			businessClassEditorDialog
					.setErrorMessage(ERROR_MESSAGE_DISPLAYNAME_NULLABLE);
			return false;
		}
		if (!ValidatorUtil.valiDisplayName(text_displayName.getText())) {
			businessClassEditorDialog.setErrorMessage(ERROR_DISPLAYNAME);
			return false;
		}
		if (!ValidatorUtil.valiDisplayNameLength(text_displayName.getText())) {
			businessClassEditorDialog.setErrorMessage(ERROR_DISPLAYNAME_LENGTH);
			return false;
		}
		int keyNum = 0;
		for (int i = 0; i < tableItems.size(); i++) {
			if (tableItems.get(i) instanceof PKProperty) {
				keyNum++;
			}
		}
		if (keyNum > 1) {
			businessClassEditorDialog
					.setErrorMessage(ERROR_MESSAGE_PRIMARYKEY_MULT);
			return false;
		}
		if (keyNum < 1) {
			businessClassEditorDialog.setErrorMessage("缺少主键属性!");
			return false;
		}
		int j = 0;
		for (int m = 0; m < tableItems.size(); m++) {
			for (int n = m + 1; n < tableItems.size(); n++) {
				if (tableItems.get(m) instanceof PersistenceProperty
						&& tableItems.get(n) instanceof PersistenceProperty) {
					if (((PersistenceProperty) (tableItems.get(m)))
							.getdBColumnName().equalsIgnoreCase(
									((PersistenceProperty) tableItems.get(n))
											.getdBColumnName()))
						j++;
				}
			}
		}

		if (j > 0) {
			businessClassEditorDialog.setErrorMessage("数据库字段名重复！");
			return false;
		}
		int i = 0;
		// for(Property pro : tableItems){
		// for(Property pro1 : tableItems){
		// if(pro != pro1 && pro.getName().equals(pro1.getName()))
		// i++;
		// }
		// }
		for (int m = 0; m < tableItems.size(); m++) {
			for (int n = m + 1; n < tableItems.size(); n++) {
				if (tableItems.get(m).getName()
						.equals(tableItems.get(n).getName()))
					i++;
			}
		}
		if (i > 0) {
			businessClassEditorDialog.setErrorMessage("属性名称重复！");
			return false;
		}
		
		for (int m = 0; m < tableItems.size(); m++) {
			for (int n = m + 1; n < tableItems.size(); n++) {
				if(tableItems.get(m) instanceof PersistenceProperty
						&& tableItems.get(n) instanceof PersistenceProperty){
					if (((PersistenceProperty)tableItems.get(m)).getdBColumnName()
							.equalsIgnoreCase(
									((PersistenceProperty)tableItems.get(n)).getdBColumnName()))
						i++;
				}
				
			}
		}
		if (i > 0) {
			businessClassEditorDialog.setErrorMessage("属性字段名重复！");
			return false;
		}
		
		if (!ValidatorUtil.valiRemarkLength(text_remark.getText())) {
			businessClassEditorDialog
					.setErrorMessage(IBusinessClassEditorPage.TOOLONG_ERROR_REMARK);
			return false;
		}
		if (businessClassEditorDialog != null) {
			businessClassEditorDialog.setErrorMessage(null);
		}
		return true;
	}

	/**
	 * 更新模型.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	public void updateTheEditingElement() {
		businessClassEditorDialog.getBusinessClassCopy().setName(
				text_name.getText().trim());
		businessClassEditorDialog.getBusinessClassCopy().setDisplayName(
				text_displayName.getText());
		businessClassEditorDialog.getBusinessClassCopy().setRemark(
				text_remark.getText());
		businessClassEditorDialog.getBusinessClassCopy().setTableName(
				text_tableName.getText());
		businessClassEditorDialog.getBusinessClassCopy().setVersionInfo(
				versionPanel.getVersionInfo());
		if(schemaNameText.getText()!=null&&!"".equals(schemaNameText.getText())){
			Map<String, Object> map=new HashMap<String, Object>();
			map.put(IModelElement.KEY_SCHEMA, schemaNameText.getText());
			businessClassEditorDialog.getBusinessClassCopy().setExtendAttributies(map);
		}
		BusinessClass businessClass = (BusinessClass) (businessClassEditorDialog
				.getBusinessClassCopy());
		for (int i = 0; i < tableItems.size(); i++) {
			Property temp = tableItems.get(i);
			temp.setParent(businessClass);
			temp.setOrderNum(i);
		}
		businessClass.getProperties().clear();
		businessClass.getProperties().addAll(tableItems);
	}

	/**
	 * Gets the cur name.
	 *
	 * @author mqfdy
	 * @return the cur name
	 * @Date 2018-09-03 09:00
	 */
	public String getCurName() {
		if (text_name != null) {
			return text_name.getText();
		}
		return "";
	}

	/**
	 * Gets the cur table name.
	 *
	 * @author mqfdy
	 * @return the cur table name
	 * @Date 2018-09-03 09:00
	 */
	public String getCurTableName() {
		if (text_tableName != null) {
			return text_tableName.getText();
		}
		return "";
	}

	/**
	 * The Class PropertyConentProvider.
	 *
	 * @author mqfdy
	 */
	private class PropertyConentProvider implements IStructuredContentProvider {
		
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
						Property element = (Property) objects[i];
						element.setOrderNum(i + 1);
					}
				}
				return objects;
			}
			return new Object[0];
		}

	}

	/**
	 * This method initializes toolBar.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	private void createToolBar() {
		GridData gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalSpan = 4;
		toolBar = new ToolBar(group_list, SWT.FLAT | SWT.RIGHT);
		toolBar.setLayoutData(gridData);
	}

	/**
	 * Creates the table.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	private void createTable() {
		GridData gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.verticalAlignment = SWT.FILL;
		gridData.horizontalSpan = 4;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		gridData.heightHint = 200;

		tableViewer = new TableViewer(group_list, SWT.MULTI
				| SWT.FULL_SELECTION | SWT.BORDER);
		table = tableViewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		table.setLayoutData(gridData);

		tableViewer.setLabelProvider(new PropertyLabelProvider());
		tableViewer.setContentProvider(new PropertyConentProvider());

		String[] columnNames = new String[] { "序号", "名称", "显示名", "类型", "编辑器类型",
				"数据库字段名", "主键", "唯一值", "可空", "只读", "属性分组", "缺省值" };
		int[] columnWidths = new int[] { 40, 80, 80, 80, 80, 80, 50, 50, 50,
				50, 80, 80 };

		int[] columnAligns = new int[] { SWT.LEFT, SWT.LEFT, SWT.LEFT,
				SWT.LEFT, SWT.LEFT, SWT.LEFT, SWT.LEFT, SWT.LEFT, SWT.LEFT,
				SWT.LEFT, SWT.LEFT, SWT.LEFT };

		for (int i = 0; i < columnNames.length; i++) {
			TableColumn tableColumn = new TableColumn(table, columnAligns[i]);
			tableColumn.setText(columnNames[i]);
			tableColumn.setWidth(columnWidths[i]);
		}
	}

	/**
	 * Make actions.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	private void makeActions() {
		text_name.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				String tableName = businessClassEditorDialog
						.getBusinessClassCopy().getTableName();
				if (tableName == null
						|| tableName.trim().length() == 0
						|| tableName.equalsIgnoreCase(BusinessModelUtil.getTableName(businessClassEditorDialog
								.getBusinessClassCopy().getName()))) {
					text_tableName.setText(BusinessModelUtil.getTableName(text_name.getText()));
				}
				String displayName = businessClassEditorDialog
						.getBusinessClassCopy().getDisplayName();
				if (displayName == null
						|| displayName.trim().length() == 0
						|| displayName.equals(businessClassEditorDialog
								.getBusinessClassCopy().getName())) {
					text_displayName.setText(text_name.getText());
				}

			}
		});
		addPropertyAction = new AddPropertyAction(businessClassEditorDialog);

		// 删除操作
		deletePropertyAction = new Action(ActionTexts.MODEL_ELEMENT_DELETE,
				ImageManager.getInstance().getImageDescriptor(
						ImageKeys.IMG_OBJECT_OPER_DELETE)) {
			public void run() {
				if (tableViewer.getSelection().isEmpty()) {
					MessageDialog.openInformation(tableViewer.getControl()
							.getShell(), OPERATE_ITEM_NULL_TITLE,
							OPERATE_ITEM_NULL_MESSAGE);
				} else {
					MessageDialog mes = new MessageDialog(tableViewer
							.getControl().getShell(), OPERATE_ITEM_NULL_TITLE,
							null, DELETE_CONFIRM, 0,
							new String[] { "确定", "取消" }, 0);
					if (mes.open() == TitleAreaDialog.OK) {
						boolean isDel = true;
						ISelection selection = tableViewer.getSelection();
						IStructuredSelection select = (IStructuredSelection) selection;
						Iterator it = select.iterator();
						while (it.hasNext()) {
							Property property = (Property) it.next();
							if(!IModelElement.STEREOTYPE_REVERSE.equals(property.getStereotype())){
								tableItems.remove(property);
							}else{
								if(isDel)
									isDel = false;
							}
						}
						if(!isDel){
							MessageDialog mesDia = new MessageDialog(tableViewer
									.getControl().getShell(), OPERATE_ITEM_NULL_TITLE,
									null, "数据库反向导入的持久化属性不能删除！", 0,
									new String[] { "确定", "取消" }, 0);
							mesDia.open();
						}
						refreshTable();
					}
				}
			}
		};

		upAction = new Action(ActionTexts.MODEL_ELEMENT_UP, ImageManager
				.getInstance().getImageDescriptor(ImageKeys.IMG_UP)) {
			public void run() {
				if (tableViewer.getSelection().isEmpty()) {
					MessageDialog.openInformation(tableViewer.getControl()
							.getShell(), OPERATE_ITEM_NULL_TITLE,
							OPERATE_ITEM_NULL_MESSAGE);
				} else {
					ISelection selection = tableViewer.getSelection();
					Property property = (Property) ((IStructuredSelection) selection)
							.getFirstElement();
					resetOrderNum(property, "up");
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
					Property property = (Property) ((IStructuredSelection) selection)
							.getFirstElement();
					resetOrderNum(property, "top");
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
					Property property = (Property) ((IStructuredSelection) selection)
							.getFirstElement();
					resetOrderNum(property, "down");
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
					Property property = (Property) ((IStructuredSelection) selection)
							.getFirstElement();
					resetOrderNum(property, "bottom");
					refreshTable();
				}
			}
		};

		ToolBarManager manager = new ToolBarManager(toolBar);
		manager.add(addPropertyAction);
		manager.add(deletePropertyAction);
		manager.add(upAction);
		manager.add(topAction);
		manager.add(downAction);
		manager.add(bottomAction);
		manager.update(true);

		// 表格双击事件
		tableViewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				ISelection selection = tableViewer.getSelection();
				AbstractModelElement element = (AbstractModelElement) ((IStructuredSelection) selection)
						.getFirstElement();
				BusinessClass parent = businessClassEditorDialog
						.getBusinessClassCopy();
				PropertyEditorDialog dialog = new PropertyEditorDialog(null,
						element, parent);
				int returnKey = dialog.open();
				if (returnKey == Window.OK
						|| (returnKey == Window.CANCEL && dialog.isChanged())) {
					// 编辑后保存关闭
					if (dialog.getOperationType().equals(
							ModelElementEditorDialog.OPERATION_TYPE_EDIT)) {
						int index = getIndex(tableItems, element);
						tableItems.remove(element);
						tableItems.add(index,
								(Property) dialog.getEditingElement());
						refreshTable();
					}
				}
			}
		});
	}

	/**
	 * Gets the index.
	 *
	 * @author mqfdy
	 * @param list
	 *            the list
	 * @param element
	 *            the element
	 * @return the index
	 * @Date 2018-09-03 09:00
	 */
	private int getIndex(List<Property> list, AbstractModelElement element) {
		int index = 1000;
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getId().equals(element.getId())) {
				index = i;
			}
		}
		return index;
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
	 * @param property
	 *            the property
	 * @param type
	 *            the type
	 * @Date 2018-09-03 09:00
	 */
	private void resetOrderNum(Property property, String type) {
		if ("up".equals(type)) {
			for (int i = 0; i < tableItems.size(); i++) {
				Property temp = tableItems.get(i);
				if (temp.getId().equals(property.getId())) {
					if (i > 0) {
						tableItems.add(i - 1, property);
						tableItems.remove(i + 1);
						break;
					}
				}
			}
		} else if ("down".equals(type)) {
			for (int i = 0; i < tableItems.size(); i++) {
				Property temp = tableItems.get(i);
				if (temp.getId().equals(property.getId())) {
					tableItems.add(i + 2, property);
					tableItems.remove(i);
					break;
				}
			}
		} else if ("top".equals(type)) {
			for (int i = 0; i < tableItems.size(); i++) {
				Property temp = tableItems.get(i);
				if (temp.getId().equals(property.getId())) {
					tableItems.add(0, property);
					tableItems.remove(i + 1);
					break;
				}
			}
		} else if ("bottom".equals(type)) {
			for (int i = 0; i < tableItems.size(); i++) {
				Property temp = tableItems.get(i);
				if (temp.getId().equals(property.getId())) {
					tableItems.add(tableItems.size(), property);
					tableItems.remove(i);
					break;
				}
			}
		}
	}

	/**
	 * 获取当前表格的数据.
	 *
	 * @author mqfdy
	 * @return the table items
	 * @Date 2018-09-03 09:00
	 */
	public List<Property> getTableItems() {
		return tableItems;
	}
}
