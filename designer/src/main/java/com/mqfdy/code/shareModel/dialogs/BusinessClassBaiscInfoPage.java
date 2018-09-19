package com.mqfdy.code.shareModel.dialogs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;

import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mqfdy.code.designer.editor.actions.ShareModelActionHttpclient;
import com.mqfdy.code.model.BusinessClass;
import com.mqfdy.code.model.PersistenceProperty;
import com.mqfdy.code.model.Property;
import com.mqfdy.code.model.utils.EditorType;
import com.mqfdy.code.shareModel.providers.DataGridLabelProvider;
import com.mqfdy.code.shareModel.providers.DataGridTableContentProvider;
import com.mqfdy.code.shareModel.providers.ModelMessageTreeContentProvider;
import com.mqfdy.code.shareModel.providers.ModelMessageTreeLabelProvider;


// TODO: Auto-generated Javadoc
/**
 * 模型文件包含的业务实体tab页.
 *
 * @author chenwanli
 */
public class BusinessClassBaiscInfoPage extends Composite{
	
	
	/** The basic info group. */
	private Group basicInfoGroup;
	
	/** The property group. */
	private Group propertyGroup;
	
	/** The name label. */
	private Label nameLabel;
	
	/** The name text. */
	private Text nameText;
	
	/** The display name label. */
	private Label displayNameLabel;
	
	/** The display name text. */
	private Text displayNameText;
	
	/** The DB name label. */
	private Label DBNameLabel;
	
	/** The DB name text. */
	private Text DBNameText;
	
	
	/** The table viewer. */
	private TableViewer tableViewer;
	
	/** The table. */
	public Table table;
	
	/**
	 * Instantiates a new business class baisc info page.
	 *
	 * @param folder
	 *            the folder
	 */
	public BusinessClassBaiscInfoPage(Composite folder) {
		super(folder, SWT.NONE);
		createContents(this);
	}	

	/**
	 * 创建界面.
	 *
	 * @author mqfdy
	 * @param composite
	 *            the composite
	 * @Date 2018-09-03 09:00
	 */
	private void createContents(Composite composite) {
		composite.setLayout(new GridLayout(1,false));	
		createBasicInfoGroup(composite);
		createPropertyGroup(composite);
		
	}	
	
	/**
	 * 表格显示区域.
	 *
	 * @author mqfdy
	 * @param composite
	 *            the composite
	 * @Date 2018-09-03 09:00
	 */
	private void createPropertyGroup(Composite composite) {
		propertyGroup=new Group(composite,SWT.None);
		GridLayout layout2=new GridLayout(1,false);
		GridData gridData = new GridData(GridData.FILL_BOTH);
		propertyGroup.setLayout(layout2);
		propertyGroup.setLayoutData(gridData);
		
		table = new Table(propertyGroup, SWT.BORDER|SWT.FULL_SELECTION|SWT.SINGLE);
		gridData = new GridData(GridData.FILL_BOTH);
		ScrolledComposite tableScroll = new ScrolledComposite(table,
					SWT.BORDER);
		tableScroll.setContent(table);

		tableViewer = new TableViewer(table);
		gridData.heightHint = 380;
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		table.setLayoutData(gridData);
		
		TableColumn name = new TableColumn(table, SWT.CENTER);
		name.setText("名称");
		name.setWidth(110);
		
		TableColumn displayName = new TableColumn(table, SWT.CENTER);
		displayName.setText("显示名");
		displayName.setWidth(110);
		
		TableColumn type = new TableColumn(table, SWT.CENTER);
		type.setText("类型");
		type.setWidth(90);

		
		TableColumn editorType = new TableColumn(table, SWT.CENTER);
		editorType.setText("编辑器类型");
		editorType.setWidth(90);
		
		TableColumn DBName = new TableColumn(table, SWT.CENTER);
		DBName.setText("数据库字段名");
		DBName.setWidth(110);

		TableColumn isPrimarykey = new TableColumn(table, SWT.CENTER);
		isPrimarykey.setText("主键");
		isPrimarykey.setWidth(60);
		
		TableColumn unique = new TableColumn(table, SWT.CENTER);
		unique.setText("唯一性");
		unique.setWidth(60);
		
		TableColumn nullable = new TableColumn(table, SWT.CENTER);
		nullable.setText("可空");
		nullable.setWidth(50);
		
		TableColumn readOnly = new TableColumn(table, SWT.CENTER);
		readOnly.setText("只读");
		readOnly.setWidth(50);
		
	}

	
	/**
	 * 查询条件显示区域.
	 *
	 * @author mqfdy
	 * @param composite
	 *            the composite
	 * @Date 2018-09-03 09:00
	 */
	private void createBasicInfoGroup(Composite composite) {
		basicInfoGroup=new Group(composite,SWT.None);
		GridLayout layout=new GridLayout(4,false);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		basicInfoGroup.setLayout(layout);
		basicInfoGroup.setLayoutData(gridData);
				
		gridData = new GridData(GridData.BEGINNING);
		gridData.widthHint=60;
		nameLabel =new Label(basicInfoGroup,SWT.NONE);
		nameLabel.setText("名称:");
		nameLabel.setLayoutData(gridData);
		
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.widthHint=120;
		nameText =new Text(basicInfoGroup,SWT.BORDER|SWT.READ_ONLY);
		nameText.setLayoutData(gridData);
		
		gridData = new GridData(GridData.BEGINNING);
		gridData.widthHint=60;
		displayNameLabel =new Label(basicInfoGroup,SWT.NONE);
		displayNameLabel.setText("显示名:");
		displayNameLabel.setLayoutData(gridData);
		
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.widthHint=120;
		displayNameText =new Text(basicInfoGroup,SWT.BORDER|SWT.READ_ONLY);
		displayNameText.setLayoutData(gridData);
		
		gridData = new GridData(GridData.BEGINNING);
		gridData.widthHint=60;
		DBNameLabel =new Label(basicInfoGroup,SWT.NONE);
		DBNameLabel.setText("数据库表名:");
		DBNameLabel.setLayoutData(gridData);
		
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan=3;
		DBNameText =new Text(basicInfoGroup,SWT.BORDER|SWT.READ_ONLY);
		DBNameText.setLayoutData(gridData);
		
	}

	/**
	 * Inits the basic info page.
	 *
	 * @author mqfdy
	 * @param obj
	 *            the obj
	 * @Date 2018-09-03 09:00
	 */
	public void initBasicInfoPage(Object obj) {
		BusinessClass bs=(BusinessClass)obj;
		nameText.setText(bs.getName());
		displayNameText.setText(bs.getDisplayName());
		DBNameText.setText(bs.getTableName());
		
			 for(Property pro:bs.getProperties()){
				    TableItem item=new TableItem(table, SWT.NONE);
				    item.setData(pro);
				    item.setText(0, pro.getName());
				    item.setText(1, pro.getDisplayName());
				    item.setText(2, pro.getDataType());
					/**
					 * 编辑器类型转换
					 * */
					
					//文本域
					String strEditorType = pro.getEditor().getEditorType();
					if(strEditorType.equals("TextEditor")){
						item.setText(3,EditorType.TextEditor.getDisplayValue());
					}else if(strEditorType.equals("MultTextEditor")){
						item.setText(3,EditorType.MultTextEditor.getDisplayValue());
					}else if(strEditorType.equals("PasswordTextEditor")){
						item.setText(3,EditorType.PasswordTextEditor.getDisplayValue());
					}else if(strEditorType.equals("NumberEditor")){
						item.setText(3,EditorType.NumberEditor.getDisplayValue());
					}else if(strEditorType.equals("RichTextEditor")){
						item.setText(3,EditorType.RichTextEditor.getDisplayValue());
					}else if(strEditorType.equals("DateTimeEditor")){
						item.setText(3,EditorType.DateTimeEditor.getDisplayValue());
					}else if(strEditorType.equals("DateEditor")){
						item.setText(3,EditorType.DateEditor.getDisplayValue());
					}else if(strEditorType.equals("TimeEditor")){
						item.setText(3,EditorType.TimeEditor.getDisplayValue());
					}
					//选择类
					else if(strEditorType.equals("ComboBox")){
						item.setText(3,EditorType.ComboBox.getDisplayValue());
					}else if(strEditorType.equals("CheckComboBox")){
						item.setText(3,EditorType.CheckComboBox.getDisplayValue());
					}else if(strEditorType.equals("ListEditor")){
						item.setText(3,EditorType.ListEditor.getDisplayValue());
					}else if(strEditorType.equals("CheckListEditor")){
						item.setText(3,EditorType.CheckListEditor.getDisplayValue());
					}else if(strEditorType.equals("DropDownTreeEditor")){
						item.setText(3,EditorType.DropDownTreeEditor.getDisplayValue());
					}else if(strEditorType.equals("DropDownCheckBoxTreeEditor")){
						item.setText(3,EditorType.DropDownCheckBoxTreeEditor.getDisplayValue());
					}else if(strEditorType.equals("DropDownGridEditor")){
						item.setText(3,EditorType.DropDownGridEditor.getDisplayValue());
					}
					//选择类
					else if(strEditorType.equals("CheckEditor")){
						item.setText(3,EditorType.CheckEditor.getDisplayValue());
					}else if(strEditorType.equals("RadioEditor")){
						item.setText(3,EditorType.RadioEditor.getDisplayValue());
					}
					//其他
					else if(strEditorType.equals("FileEditor")){
						item.setText(3,EditorType.FileEditor.getDisplayValue());
					}else if(strEditorType.equals("LableEditor")){
						item.setText(3,EditorType.LableEditor.getDisplayValue());
					}else if(strEditorType.equals("LinkEditor")){
						item.setText(3,EditorType.LinkEditor.getDisplayValue());
					}else if(strEditorType.equals("CustomEditor")){
						item.setText(3,EditorType.CustomEditor.getDisplayValue());
					}else if(strEditorType.equals("AutoCompleteEditor")){
						item.setText(3,EditorType.AutoCompleteEditor.getDisplayValue());
					}
					if(pro instanceof PersistenceProperty){
						item.setText(4,((PersistenceProperty) pro).getdBColumnName().toString());
						item.setText(5,String.valueOf(((PersistenceProperty) pro).isPrimaryKey()));
						item.setText(6,String.valueOf(((PersistenceProperty) pro).isUnique()));
						item.setText(7,String.valueOf(((PersistenceProperty) pro).isNullable()));
						item.setText(8,String.valueOf(((PersistenceProperty) pro).isReadOnly()));							
					}
			  }
		}
	}
	

