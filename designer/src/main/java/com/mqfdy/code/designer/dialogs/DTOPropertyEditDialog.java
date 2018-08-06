package com.mqfdy.code.designer.dialogs;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

import com.mqfdy.code.designer.models.ImageKeys;
import com.mqfdy.code.designer.models.ImageManager;
import com.mqfdy.code.model.DTOProperty;

/**
 * DTO属性编辑对话框
 * 
 * @author mqfdy
 * 
 */
public class DTOPropertyEditDialog extends ModelElementEditorDialog implements
		IBusinessClassEditorPage {

	public String DIALOG_TITLE = "";
	public final String DIALOG_MESSAGE_ADD = "创建数据传输对象属性";
	public final String DIALOG_MESSAGE_EDIT = "修改数据传输对象 ";

	private DTOProperty property;
	/**
	 * 标签页
	 */
	private TabFolder tabBasic;

	private DTOPropertiesPage parentPage;
	private DTOPropertyBasicInfoPage propertyBasicInfoPage;
	private DTOPropertyValidatorPage propertyValidatorPage;

	public DTOPropertyEditDialog(Shell parentShell, DTOProperty property,
			DTOPropertiesPage page) {
		super(parentShell);
		this.parentPage = page;
		if (property == null) {
			this.property = new DTOProperty();
			this.DIALOG_TITLE = DIALOG_MESSAGE_ADD;
			operationType = ModelElementEditorDialog.OPERATION_TYPE_ADD;
		} else {
			this.property = property;
			this.DIALOG_TITLE = DIALOG_MESSAGE_EDIT + property.getDisplayName();
			operationType = ModelElementEditorDialog.OPERATION_TYPE_EDIT;
		}
	}

	protected int getShellStyle() {
		return super.getShellStyle() | SWT.RESIZE | SWT.MAX;
	}

	@Override
	protected Control createDialogArea(Composite composite) {
		// 初始化窗口
		GridLayout gridLayout = new GridLayout(1, true);
		composite.setLayout(gridLayout);
		composite.setSize(700, 500);

		setTitleAndMessage();
		createtabFolder(composite);// 创建标签页对象
		createContent(composite);
		initControlValue();
		return composite;
	}

	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		if (operationType.equals(OPERATION_TYPE_ADD)) {
			newShell.setText(DIALOG_TITLE);
		} else {
			newShell.setText(DIALOG_TITLE);
		}
		newShell.setImage(ImageManager.getInstance().getImage(
				ImageKeys.IMG_MODEL_TYPE_PACKAGE));
	}

	/**
	 * 设置标题和信息
	 */
	public void setTitleAndMessage() {
		setTitle("DTO");
		setTitleImage(ImageManager.getInstance().getImage(
				ImageKeys.IMG_DIALOG_BUSINESSCLASS));
		if (operationType.equals(OPERATION_TYPE_ADD)) {
			setMessage(DIALOG_MESSAGE_ADD);
		} else {
			setMessage(DIALOG_MESSAGE_EDIT, IMessageProvider.INFORMATION);
		}

	}

	/**
	 * 初始化创建 标签页
	 */
	private void createtabFolder(Composite composite) {
		tabBasic = new TabFolder(composite, SWT.NONE);
		tabBasic.setLayoutData(new GridData(GridData.FILL_BOTH));

		TabItem tabItem1 = new TabItem(tabBasic, SWT.NONE);
		tabItem1.setText("基本信息");

		TabItem tabItem2 = new TabItem(tabBasic, SWT.NONE);
		tabItem2.setText("数据校验");

	}

	private void createContent(Composite parent) {
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		layout.marginTop = 5;
		layout.marginBottom = 5;
		layout.marginLeft = 5;
		layout.marginRight = 5;
		layout.verticalSpacing = 5;
		layout.makeColumnsEqualWidth = false;
		parent.setLayout(layout);

		propertyBasicInfoPage = new DTOPropertyBasicInfoPage(tabBasic, SWT.TOP,
				this);
		tabBasic.getItem(0).setControl(propertyBasicInfoPage);

		propertyValidatorPage = new DTOPropertyValidatorPage(tabBasic, SWT.TOP,
				this);
		tabBasic.getItem(1).setControl(propertyValidatorPage);
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

	public void initControlValue() {
		if (this.property != null) {
			propertyBasicInfoPage.initControlValue();
			propertyValidatorPage.initControlValue();
		}
	}

	public boolean validateInput() {

		return true;
	}

	public void updateTheEditingElement() {
		propertyBasicInfoPage.updateTheEditingElement();
		propertyValidatorPage.updateTheEditingElement();
		if (this.property != null) {
			if (getOperationType().equals(
					ModelElementEditorDialog.OPERATION_TYPE_ADD)) {
				parentPage.getDtoDialog().dto.getProperties()
						.add(this.property);
			}
		}
		if (parentPage != null) {
			parentPage.refreshTable();
		}
	}

	@Override
	protected void okPressed() {
		if (validateAllInput() == true) {
			updateTheEditingElement();
			super.okPressed();
		}
	}

	protected void applylPressed() {
		if (validateAllInput() == true) {
			updateTheEditingElement();
		}

	}

	@Override
	protected void buttonPressed(int buttonId) {
//		if (APPLY_ID == buttonId) {
//			applylPressed();
//		} else {
		super.buttonPressed(buttonId);
//		}
	}

	private boolean validateAllInput() {
		boolean isOk = this.validateInput();
		return isOk;
	}

	public DTOPropertiesPage getParentPage() {
		return parentPage;
	}

	public DTOProperty getProperty() {
		return property;
	}
}
