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

import com.mqfdy.code.designer.models.BusinessModelEvent;
import com.mqfdy.code.designer.models.BusinessModelManager;
import com.mqfdy.code.designer.models.ImageKeys;
import com.mqfdy.code.designer.models.ImageManager;
import com.mqfdy.code.designer.utils.BusinessModelUtil;
import com.mqfdy.code.model.AbstractModelElement;
import com.mqfdy.code.model.Enumeration;
import com.mqfdy.code.model.ModelPackage;
import com.mqfdy.code.model.SolidifyPackage;
import com.mqfdy.code.model.graph.Diagram;

/**
 * 枚举类型编辑对话框
 * 
 * @author mqfdy
 * 
 */
public class EnumEditDialog extends ModelElementEditorDialog implements
		IBusinessClassEditorPage {

	public String DIALOG_TITLE = "";
	public final String DIALOG_TITLE_OPERATION = "";
	public final String DIALOG_MESSAGE_ADD = "创建枚举类型";
	public final String DIALOG_MESSAGE_EDIT = "修改 枚举类型     ";

	private AbstractModelElement parent;

	private Enumeration enumeration;
	/**
	 * 基本信息标签页
	 */
	private TabFolder tabBasic;

	private EnumBasicInfoPage enumBasicInfoPage;
	/**
	 * 枚举值信息标签页
	 */
	private TabFolder tabEnumeration;

	private EnumElementsPage enumElementsPage;
	/**
	 * 版本信息
	 */
	private VersionInfoPanel versionPanel;

	private boolean hasSave = false;// 是否已经保存

	private BusinessModelManager manager = BusinessModelUtil
			.getEditorBusinessModelManager();
	/**
	 * 从组件面板创建
	 */
	private boolean createFromPlatter = false;
	
	public EnumEditDialog(boolean createFromPlatter,Shell parentShell, AbstractModelElement parent,
			Enumeration enumeration) {
		this(parentShell, parent, enumeration);
		this.createFromPlatter  = createFromPlatter;
		if(createFromPlatter)
			this.DIALOG_TITLE = DIALOG_MESSAGE_ADD;
	}
	public EnumEditDialog(Shell parentShell, AbstractModelElement parent,
			Enumeration enumeration) {
		super(parentShell);

		if (enumeration == null) {
			this.enumeration = new Enumeration();
			this.DIALOG_TITLE = DIALOG_MESSAGE_ADD;
			this.parent = parent;
			if (parent instanceof ModelPackage) {
				this.enumeration.setBelongPackage((ModelPackage) parent);
			} else if (parent instanceof Diagram) {
				this.enumeration.setBelongPackage((ModelPackage) (parent
						.getParent()));
			} else if (parent instanceof SolidifyPackage) {
				this.enumeration.setBelongPackage((ModelPackage) (parent
						.getParent()));
			}
		} else {
			this.enumeration = enumeration;
			this.DIALOG_TITLE = DIALOG_MESSAGE_EDIT
					+ enumeration.getDisplayName();
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
				ImageKeys.IMG_MODEL_TYPE_ENUMERATION));
	}

	/**
	 * 设置标题和信息
	 */
	public void setTitleAndMessage() {
		setTitle("枚举类型");
		if (operationType.equals(OPERATION_TYPE_ADD)) {
			setMessage(DIALOG_MESSAGE_ADD);
		} else if (operationType.equals(OPERATION_TYPE_EDIT) && createFromPlatter) {
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

		tabEnumeration = new TabFolder(composite, SWT.NONE);
		tabEnumeration.setLayoutData(new GridData(GridData.FILL_BOTH));
		TabItem tabItem2 = new TabItem(tabEnumeration, SWT.NONE);
		tabItem2.setText("枚举值信息");

		versionPanel = new VersionInfoPanel(composite, SWT.NONE | SWT.FILL);
		GridData versionGridData = new GridData(GridData.FILL_HORIZONTAL);
		versionGridData.horizontalSpan = 4;
		versionGridData.grabExcessHorizontalSpace = true;
		versionGridData.exclude = true;
		versionPanel.setLayoutData(versionGridData);
		versionPanel.setVisible(false);
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

		enumBasicInfoPage = new EnumBasicInfoPage(tabBasic, SWT.TOP, this);
		tabBasic.getItem(0).setControl(enumBasicInfoPage);

		enumElementsPage = new EnumElementsPage(tabEnumeration, SWT.TOP, this);
		tabEnumeration.getItem(0).setControl(enumElementsPage);
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
		if (this.enumeration != null) {
			enumBasicInfoPage.initControlValue();
			enumElementsPage.initControlValue();
			versionPanel.initControlValue(enumeration.getVersionInfo());
		}
	}

	public boolean validateInput() {
		boolean isOk = enumBasicInfoPage.validateInput();
		if (isOk == false) {
			return false;
		}
		if (isOk == true) {
			isOk = enumElementsPage.validateInput();
		}
		if (isOk == false) {
			return false;
		}
		return true;
	}

	public void updateTheEditingElement() {

		enumBasicInfoPage.updateTheEditingElement();

		enumElementsPage.updateTheEditingElement();

		enumeration.setVersionInfo(versionPanel.getVersionInfo());

		if (ModelElementEditorDialog.OPERATION_TYPE_ADD.equals(operationType)) {
			manager.businessObjectModelChanged(new BusinessModelEvent(
					BusinessModelEvent.MODEL_ELEMENT_ADD, enumeration));
		}
		if (ModelElementEditorDialog.OPERATION_TYPE_EDIT.equals(operationType)) {
			manager.businessObjectModelChanged(new BusinessModelEvent(
					BusinessModelEvent.MODEL_ELEMENT_UPDATE, enumeration));
		}
	}

	@Override
	protected void okPressed() {
		if (validateAllInput() == true /* && !hasSave */) {
			updateTheEditingElement();
			hasSave = true;
			isChaged = true;
			super.okPressed();
		}
	}

	protected void applylPressed() {
		if (validateAllInput() == true && !hasSave) {
			updateTheEditingElement();
			operationType = ModelElementEditorDialog.OPERATION_TYPE_EDIT;
			hasSave = true;
			isChaged = true;
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

	public Enumeration getEnumeration() {
		return enumeration;
	}
}
