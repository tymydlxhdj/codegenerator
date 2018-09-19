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

// TODO: Auto-generated Javadoc
/**
 * DTO属性编辑对话框.
 *
 * @author mqfdy
 */
public class DTOPropertyEditDialog extends ModelElementEditorDialog implements
		IBusinessClassEditorPage {

	/** The dialog title. */
	public String DIALOG_TITLE = "";
	
	/** The dialog message add. */
	public final String DIALOG_MESSAGE_ADD = "创建数据传输对象属性";
	
	/** The dialog message edit. */
	public final String DIALOG_MESSAGE_EDIT = "修改数据传输对象 ";

	/** The property. */
	private DTOProperty property;
	
	/** 标签页. */
	private TabFolder tabBasic;

	/** The parent page. */
	private DTOPropertiesPage parentPage;
	
	/** The property basic info page. */
	private DTOPropertyBasicInfoPage propertyBasicInfoPage;
	
	/** The property validator page. */
	private DTOPropertyValidatorPage propertyValidatorPage;

	/**
	 * Instantiates a new DTO property edit dialog.
	 *
	 * @param parentShell
	 *            the parent shell
	 * @param property
	 *            the property
	 * @param page
	 *            the page
	 */
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

	/**
	 * @return
	 */
	protected int getShellStyle() {
		return super.getShellStyle() | SWT.RESIZE | SWT.MAX;
	}

	/**
	 * Creates the dialog area.
	 *
	 * @author mqfdy
	 * @param composite
	 *            the composite
	 * @return the control
	 * @Date 2018-09-03 09:00
	 */
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

	/**
	 * Configure shell.
	 *
	 * @author mqfdy
	 * @param newShell
	 *            the new shell
	 * @Date 2018-09-03 09:00
	 */
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
	 * 设置标题和信息.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
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
	 * 初始化创建 标签页.
	 *
	 * @author mqfdy
	 * @param composite
	 *            the composite
	 * @Date 2018-09-03 09:00
	 */
	private void createtabFolder(Composite composite) {
		tabBasic = new TabFolder(composite, SWT.NONE);
		tabBasic.setLayoutData(new GridData(GridData.FILL_BOTH));

		TabItem tabItem1 = new TabItem(tabBasic, SWT.NONE);
		tabItem1.setText("基本信息");

		TabItem tabItem2 = new TabItem(tabBasic, SWT.NONE);
		tabItem2.setText("数据校验");

	}

	/**
	 * Creates the content.
	 *
	 * @author mqfdy
	 * @param parent
	 *            the parent
	 * @Date 2018-09-03 09:00
	 */
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

	/**
	 * Creates the buttons for button bar.
	 *
	 * @author mqfdy
	 * @param composite
	 *            the composite
	 * @Date 2018-09-03 09:00
	 */
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

	/**
	 * 
	 */
	public void initControlValue() {
		if (this.property != null) {
			propertyBasicInfoPage.initControlValue();
			propertyValidatorPage.initControlValue();
		}
	}

	/**
	 * @return
	 */
	public boolean validateInput() {

		return true;
	}

	/**
	 * 
	 */
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

	/**
	 * 
	 */
	@Override
	protected void okPressed() {
		if (validateAllInput() == true) {
			updateTheEditingElement();
			super.okPressed();
		}
	}

	/**
	 * Applyl pressed.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	protected void applylPressed() {
		if (validateAllInput() == true) {
			updateTheEditingElement();
		}

	}

	/**
	 * Button pressed.
	 *
	 * @author mqfdy
	 * @param buttonId
	 *            the button id
	 * @Date 2018-09-03 09:00
	 */
	@Override
	protected void buttonPressed(int buttonId) {
//		if (APPLY_ID == buttonId) {
//			applylPressed();
//		} else {
		super.buttonPressed(buttonId);
//		}
	}

	/**
	 * Validate all input.
	 *
	 * @author mqfdy
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	private boolean validateAllInput() {
		boolean isOk = this.validateInput();
		return isOk;
	}

	/**
	 * Gets the parent page.
	 *
	 * @author mqfdy
	 * @return the parent page
	 * @Date 2018-09-03 09:00
	 */
	public DTOPropertiesPage getParentPage() {
		return parentPage;
	}

	/**
	 * Gets the property.
	 *
	 * @author mqfdy
	 * @return the property
	 * @Date 2018-09-03 09:00
	 */
	public DTOProperty getProperty() {
		return property;
	}
}
