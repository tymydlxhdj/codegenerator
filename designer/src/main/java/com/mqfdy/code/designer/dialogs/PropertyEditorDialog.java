package com.mqfdy.code.designer.dialogs;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

import com.mqfdy.code.designer.models.ImageKeys;
import com.mqfdy.code.designer.models.ImageManager;
import com.mqfdy.code.model.AbstractModelElement;
import com.mqfdy.code.model.PKProperty;
import com.mqfdy.code.model.PersistenceProperty;
import com.mqfdy.code.model.Property;
import com.mqfdy.code.model.QueryCondition;
import com.mqfdy.code.model.Validator;
import com.mqfdy.code.model.utils.EditorType;
import com.mqfdy.code.model.utils.ValidatorType;
import com.mqfdy.code.resource.validator.ValidatorUtil;

/**
 * 属性编辑器弹出框
 * 
 * @author mqfdy
 * 
 */
public class PropertyEditorDialog extends ModelElementEditorDialog {

	public static final String DIALOG_TITLE_ADD = "创建属性";
	public static final String DIALOG_TITLE_EDIT = "编辑属性";
	public static final String MESSAGE_TITLE = "属性";
	public static final String DIALOG_MESSAGE_ADD = "创建属性";
	public static final String DIALOG_MESSAGE_EDIT = "修改 @ 属性";

	public static final String SAVE_AND_CONTINUE_NAME = "Save && New";
	public static final int SAVE_AND_CONTINUE_ID = 1111;
	public static final String TAB_BASICINFO_TITLE = "基本信息";
	public static final String TAB_VALIDATOR_TITLE = "校验器";
	public static final String TAB_EDITOR_TITLE = "编辑器";

	private TabFolder tabFolder;

	/**
	 * 属性基本信息页
	 */
	private PropertyBasicInfoPage propertyBasicInfoPage;

	/**
	 * 属性校验器页面
	 */
	private PropertyValidatorPage propertyValidatorPage;

	/**
	 * 属性编辑器页面
	 */
	private PropertyEditorPage propertyEditorPage;

	/**
	 * 属性拷贝对象
	 */
	private Property propertyCopy;

	private List<QueryCondition> conditions = new ArrayList<QueryCondition>();

	private List<Property> tempProperties;

	public PropertyEditorDialog(Shell parentShell) {
		super(parentShell);
	}

	/**
	 * 构造函数(用于编辑)
	 * 
	 * @param parentShell
	 * @param editingElement
	 * @param parent
	 */
	public PropertyEditorDialog(Shell parentShell,
			AbstractModelElement editingElement, AbstractModelElement parent) {
		super(parentShell, editingElement, parent);
	}

	/**
	 * 构造函数(用于新增)
	 * 
	 * @param parentShell
	 * @param parent
	 */
	public PropertyEditorDialog(Shell parentShell, AbstractModelElement parent) {
		super(parentShell, parent);
	}

	public PropertyEditorDialog(Shell parentShell, List<Property> properties,
			AbstractModelElement parent) {
		super(parentShell, parent);
		tempProperties = properties;
	}

	protected Control createDialogArea(Composite parent) {
		// 初始化窗口
		GridLayout gridLayout = new GridLayout(1, true);
		parent.setLayout(gridLayout);

		createtabFolder(parent);// 创建标签页对象

		propertyBasicInfoPage = new PropertyBasicInfoPage(tabFolder, SWT.NONE,
				this);
		tabFolder.getItem(0).setControl(propertyBasicInfoPage);// 初始化第1个tab的内容：基本信息

		propertyValidatorPage = new PropertyValidatorPage(tabFolder, SWT.NONE, this);
		tabFolder.getItem(1).setControl(propertyValidatorPage);// 初始化第2个tab的内容：校验器

		propertyEditorPage = new PropertyEditorPage(tabFolder, SWT.NONE, this);
		tabFolder.getItem(2).setControl(propertyEditorPage);// 初始化第3个tab的内容：基本信息

		initControlValue();
		addListeners();
		return parent;
	}

	private void addListeners() {
		tabFolder.addMouseListener(new MouseListener() {

			public void mouseDoubleClick(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			public void mouseDown(MouseEvent event) {
				TabFolder tab = (TabFolder) event.getSource();
				if (tab.getSelectionIndex() == 2) {

					if (propertyBasicInfoPage.isChooseDataType()) {
						propertyEditorPage.change(propertyBasicInfoPage
								.getCurDataType());
					} else {
						propertyEditorPage.change(null);
					}
				}
			}

			public void mouseUp(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

		});
	}

	protected void createButtonsForButtonBar(Composite composite) {
		// if(operationType.equals(OPERATION_TYPE_EDIT))
		// createButton(composite, 12000, "重构", true);
		// if(operationType.equals(OPERATION_TYPE_ADD))
		if (operationType.equals(OPERATION_TYPE_ADD)) {
			createButton(composite, SAVE_AND_CONTINUE_ID,
					SAVE_AND_CONTINUE_NAME, true);
		}
		createButton(composite, IDialogConstants.OK_ID,
				IDialogConstants.OK_LABEL, true);
		createButton(composite, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
//		createButton(composite, APPLY_ID, APPLY_LABEL, true);
	}

	protected void buttonPressed(int buttonId) {
		if (SAVE_AND_CONTINUE_ID == buttonId) {
			okPressed();
			setReturnCode(SAVE_AND_CONTINUE_ID);
		} else {
			super.buttonPressed(buttonId);
		}
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

	/**
	 * 设置标题和信息
	 */
	private void setTitleAndMessage() {
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
				ImageKeys.IMG_MODEL_TYPE_PROPERTY));
	}

	/**
	 * 初始化创建 标签页
	 */
	private void createtabFolder(Composite parent) {
		tabFolder = new TabFolder(parent, SWT.NONE);
		tabFolder.setLayoutData(new GridData(GridData.FILL_BOTH));
		for (int i = 0; i < 3; i++) {
			TabItem tabItem = new TabItem(tabFolder, SWT.NONE);
			tabItem.setText(getTabTitle(i));
		}
	}

	private String getTabTitle(int index) {
		switch (index) {
		case 0:
			return TAB_BASICINFO_TITLE;
		case 1:
			return TAB_VALIDATOR_TITLE;
		case 2:
			return TAB_EDITOR_TITLE;
		default:
			return TAB_BASICINFO_TITLE;
		}
	}

	/**
	 * 初始化弹出框控件的值
	 */
	private void initControlValue() {

		if (OPERATION_TYPE_EDIT.equals(operationType) && editingElement != null) {
			if (editingElement instanceof PKProperty) {
				this.propertyCopy = (PKProperty) editingElement;
			} else if (editingElement instanceof PersistenceProperty) {
				this.propertyCopy = (PersistenceProperty) editingElement;
			} else if (editingElement instanceof Property) {
				this.propertyCopy = (Property) editingElement;
			}
			this.propertyCopy.setParent(this.parent);
			this.setConditions(this.propertyCopy.getEditor().getConditions());
		} else {
			this.propertyCopy = new PersistenceProperty();
			this.propertyCopy.setParent(this.parent);

		}

		setTitleAndMessage();

		// 初始化各个tab页的控件值
		propertyBasicInfoPage.initControlValue();
		propertyValidatorPage.initControlValue();
		propertyEditorPage.initControlValue();

	}

	private boolean validateAllInput() {
		boolean isOk = propertyBasicInfoPage.validateInput();
		if (isOk == false) {
			return isOk;
		}

		isOk = propertyValidatorPage.validateInput();
		if (isOk == false) {
			return isOk;
		}
		
		isOk = propertyEditorPage.validateInput();
		if (isOk == false) {
			return isOk;
		}

		isOk = checkValidatorType();
		if (isOk == false) {
			this.setErrorMessage("数据类型与校验器类型不匹配！");
			return isOk;
		}

		isOk = checkEditorType();
		if (isOk == false) {
			this.setErrorMessage("数据类型与编辑器类型不匹配！");
			return isOk;
		}
		EditorType curEditorType = propertyEditorPage.getCurType();
		isOk = propertyBasicInfoPage.validateDefault(curEditorType);
		
		String defaultValue=propertyBasicInfoPage.getDefaultValue();
		Vector<Validator> validators= propertyValidatorPage.getTableItems();
		if(validators.size()>0){
			for(Validator a :validators){
				if(a.getValidatorType().equals(ValidatorType.CNString.getValue())&&
						!ValidatorUtil.validateChinese(defaultValue)){
					this.setErrorMessage("缺省值中文校验不通过!"); 
					return false;
				}
			}
		}
		
		return isOk;
	}

	/**
	 * 校验是否匹配
	 * 
	 * @return
	 */
	private boolean checkValidatorType() {
		String curDataType = getPropertyBasicInfoPage().getCurDataType();
		Vector<Validator> curValidators = getPropertyValidatorPage()
				.getTableItems();
		if (curDataType == null || curValidators == null) {
			return false;
		} else {
			for (Validator tempValidator : curValidators) {
				boolean b = ValidatorUtil
						.getValidatorMap()
						.get(curDataType)
						.get(ValidatorType.getValidatorType(tempValidator
								.getValidatorType())) != null;
				if (!b) {
					// 校验没通过
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * 校验是否匹配
	 * 
	 * @return
	 */
	private boolean checkEditorType() {
		String curDataType = propertyBasicInfoPage.getCurDataType();
		EditorType curEditorType = propertyEditorPage.getCurType();
		if (curDataType == null || curEditorType == null) {
			return false;
		} else {
			return ValidatorUtil.getEditorMap().get(curDataType)
					.get(curEditorType) != null;
		}

	}

	private void updateTheEditingElement() {
		propertyBasicInfoPage.updateTheEditingElement();
		propertyValidatorPage.updateTheEditingElement();
		propertyEditorPage.updateTheEditingElement();
		this.editingElement = this.getPropertyCopy();
	}

	public void refresh() {
		this.propertyValidatorPage.refresh();
	}

	public Property getPropertyCopy() {
		return propertyCopy;
	}

	public void setPropertyCopy(Property propertyCopy) {
		this.propertyCopy = propertyCopy;
	}

	public TabFolder getTabFolder() {
		return tabFolder;
	}

	public int getPropertySize() {
		if (tempProperties != null) {
			return tempProperties.size();
		}
		return 0;
	}

	public List<Property> getProperties() {
		if (tempProperties != null) {
			return tempProperties;
		}
		return null;
	}

	public PropertyBasicInfoPage getPropertyBasicInfoPage() {
		return propertyBasicInfoPage;
	}

	public PropertyEditorPage getPropertyEditorPage() {
		return propertyEditorPage;
	}

	public List<QueryCondition> getConditions() {
		return conditions;
	}

	public void setConditions(List<QueryCondition> conditions) {
		this.conditions = conditions;
	}

	public PropertyValidatorPage getPropertyValidatorPage() {
		return propertyValidatorPage;
	}

}