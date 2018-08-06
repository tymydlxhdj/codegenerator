package com.mqfdy.code.designer.dialogs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import com.mqfdy.code.designer.dialogs.widget.ModelElementSelecterDailog;
import com.mqfdy.code.designer.dialogs.widget.NullToEmptyText;
import com.mqfdy.code.designer.models.BusinessModelManager;
import com.mqfdy.code.designer.utils.BusinessModelUtil;
import com.mqfdy.code.model.Association;
import com.mqfdy.code.model.BusinessClass;
import com.mqfdy.code.model.IModelElement;
import com.mqfdy.code.model.PKProperty;
import com.mqfdy.code.model.Property;
import com.mqfdy.code.model.PropertyEditor;
import com.mqfdy.code.model.ReferenceObject;
/**
 * 下拉框枚举来源于业务实体页面
 * @author mqfdy
 *
 */
public class DataSourceBusinessPage extends Composite {

	private static final String ERROR_MESSAGE_BUSINESSCLASS_NULLABLE = "请选择业务实体";
	private static final String ERROR_MESSAGE_KEY_NULLABLE = "请选择KEY在实体中的属性";
	private static final String ERROR_MESSAGE_VALUE_NULLABLE = "请选择VALUE在实体中的属性";

	private static final String LABEL_BUSINESSCLASSSELECT_TEXT = "业务实体：";
	private static final String BUTTON_ENUMSELECT_TEXT = "查找";

	private static final String LABEL_BUSINESSCLASS_KEY_TEXT = "KEY属性：";
	private static final String LABEL_BUSINESSCLASS_VALUE_TEXT = "VALUE属性：";
	private static final String LABEL_SQL_TEXT = "查询条件：";
	private static final String BUTTON_SQL_DESIGN = "设计查询条件";

	private Label label_bcSelect;
	private NullToEmptyText text_bcDisplayName;
	private Button btn_select;

	private Label label_key;
	private Combo combo_key;
	private Label label_value;
	private Combo combo_value;
	private String text_sql;

	private Button btn_design;

	private BusinessClass businessClass;

	/**
	 * 属性页编辑页面
	 */
	private IBusinessClassEditorPage parentPage;
	private PropertyEditor editor = null;

	public DataSourceBusinessPage(Composite parent, int style,
			IBusinessClassEditorPage parentPage) {
		super(parent, style);
		this.parentPage = parentPage;
		createContents(this);
	}

	private void createContents(Composite composite) {

		composite.setLayout(new GridLayout(3, false));
		GridData g = new GridData();
		// g.verticalSpan = 2;
		g.horizontalSpan = 2;
		g.grabExcessHorizontalSpace = true;
		g.horizontalAlignment = SWT.FILL;
		g.verticalAlignment = SWT.FILL;
		g.heightHint = 300;
		composite.setLayoutData(g);
		label_bcSelect = new Label(composite, SWT.NONE);
		label_bcSelect.setText(LABEL_BUSINESSCLASSSELECT_TEXT);
		label_bcSelect.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false));

		text_bcDisplayName = new NullToEmptyText(composite, SWT.BORDER
				| SWT.READ_ONLY);
		text_bcDisplayName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER,
				true, false));

		btn_select = new Button(composite, SWT.NONE);
		btn_select.setText(BUTTON_ENUMSELECT_TEXT);

		label_key = new Label(composite, SWT.NONE);
		label_key.setText(LABEL_BUSINESSCLASS_KEY_TEXT);
		label_key.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false));

		combo_key = new Combo(composite, SWT.READ_ONLY);
		combo_key.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false,
				2, 1));

		label_value = new Label(composite, SWT.NONE);
		label_value.setText(LABEL_BUSINESSCLASS_VALUE_TEXT);
		label_value.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false));
		combo_value = new Combo(composite, SWT.READ_ONLY);
		combo_value.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 2, 1));

		btn_design = new Button(composite, SWT.NONE | SWT.RIGHT);
		btn_design.setText(BUTTON_SQL_DESIGN);
		btn_design.setLayoutData(new GridData(SWT.RIGHT, SWT.NONE, true, false,
				3, 1));

		makeAction();
	}

	private void makeAction() {
		btn_select.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				BusinessModelManager manager = BusinessModelUtil
						.getEditorBusinessModelManager();
				ModelElementSelecterDailog dialog = new ModelElementSelecterDailog(
						new String[] { IModelElement.MODEL_TYPE_BUSINESSCLASS,
								IModelElement.MODEL_TYPE_REFRENCE }, manager,
						true, true);
				if (dialog.open() == Window.OK) {
					if (dialog.getResult()[0] instanceof BusinessClass) {
						businessClass = (BusinessClass) dialog.getResult()[0];
					}
					if (dialog.getResult()[0] instanceof ReferenceObject) {
						ReferenceObject rf = (ReferenceObject) dialog
								.getResult()[0];
						businessClass = (BusinessClass) rf.getReferenceObject();
					}
					text_bcDisplayName.setText(businessClass.getDisplayName());
					combo_key.setItems(businessClass.getPropertiesDisplayName());
					combo_value.setItems(businessClass
							.getPropertiesDisplayName());

					List<Property> properties = businessClass.getProperties();
					if (properties != null) {
						for (int i = 0; i < properties.size(); i++) {
							if (properties.get(i) instanceof PKProperty) {
								combo_key.select(i);
							}
						}
					}
				}

			}
		});
		btn_design.addMouseListener(new MouseListener() {

			public void mouseDoubleClick(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			public void mouseDown(MouseEvent arg0) {
				if (businessClass == null) {
					return;
				}
				QueryDesignDialog dialog = new QueryDesignDialog(
						DataSourceBusinessPage.this.getShell(), businessClass,
						DataSourceBusinessPage.this);
				if (dialog.open() == Window.OK) {
				}
			}

			public void mouseUp(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

		});
	}

	public void initControlValue() {
//		PropertyEditor editor = parentPage.getEditingProperty().getEditor();
		if(parentPage instanceof PropertyEditorPage){
			editor  = ((PropertyEditorPage) parentPage).getEditingProperty().getEditor();
		}
		if(parentPage instanceof FkEditorPage){
			editor = ((Association) ((FkEditorPage) parentPage).getParentDialog().parent).getEditor();
		}
		String businessClassId = editor.getEditorParams().get(
				PropertyEditor.PARAM_KEY_BUSINESSCLASS_ID);
		businessClass = (BusinessClass) (BusinessModelUtil
				.getEditorBusinessModelManager()
				.queryObjectById(businessClassId));
		if (businessClass == null && businessClassId != null) {
			businessClass = (BusinessClass) (BusinessModelManager
					.getBuildInOm().getModelElementById(businessClassId));
		}
		if (businessClass != null) {
			text_bcDisplayName.setText(businessClass.getDisplayName());
			combo_key.setItems(businessClass.getPropertiesDisplayName());
			combo_value.setItems(businessClass.getPropertiesDisplayName());
			String keyId = editor.getEditorParams().get(
					PropertyEditor.PARAM_KEY_KEY_PROPERTY_ID);
			String valueId = editor.getEditorParams().get(
					PropertyEditor.PARAM_KEY_VALUE_PROPERTY_ID);
			for (int i = 0; i < businessClass.getProperties().size(); i++) {
				if (keyId.equals(businessClass.getProperties().get(i).getId())) {
					combo_key.select(i);
				}
				if (valueId
						.equals(businessClass.getProperties().get(i).getId())) {
					combo_value.select(i);
				}
			}
		}
		text_sql = editor.getEditorParams().get(
				PropertyEditor.PARAM_KEY_FILTER_SQL);

	}

	public String validateInput() {
		String errorMessage = null;
		if (businessClass != null) {
			int indexKey = combo_key.getSelectionIndex();
			int indexValue = combo_value.getSelectionIndex();
			if (indexKey > -1 && indexValue == -1) {
				errorMessage = ERROR_MESSAGE_VALUE_NULLABLE;
			} else if (indexValue > -1 && indexKey == -1) {
				errorMessage = ERROR_MESSAGE_KEY_NULLABLE;
			}
		} else {
			errorMessage = ERROR_MESSAGE_BUSINESSCLASS_NULLABLE;
		}
		return errorMessage;
	}

	public void setContentBackGrand(Color color) {
		label_bcSelect.setBackground(color);
		label_key.setBackground(color);
		label_value.setBackground(color);
	}

	public Map<String, String> getParamMap() {
		Map<String, String> map = new HashMap<String, String>();
		int indexKey = combo_key.getSelectionIndex();
		int indexValue = combo_value.getSelectionIndex();
		map.put(PropertyEditor.PARAM_KEY_BUSINESSCLASS_ID,
				businessClass.getId());
		map.put(PropertyEditor.PARAM_KEY_FILTER_SQL, text_sql);
		map.put(PropertyEditor.PARAM_KEY_KEY_PROPERTY_ID, businessClass
				.getProperties().get(indexKey).getId());
		map.put(PropertyEditor.PARAM_KEY_VALUE_PROPERTY_ID, businessClass
				.getProperties().get(indexValue).getId());
		return map;
	}

	public void setTextSql(String str) {
		this.text_sql = str;
	}

	public BusinessClass getBusinessClass() {
		return businessClass;
	}

	public IBusinessClassEditorPage getParentPage() {
		return parentPage;
	}

	public PropertyEditor getEditor() {
		return editor;
	}

}