package com.mqfdy.code.importEnum.dialogs;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

import com.mqfdy.code.designer.dialogs.EnumBasicInfoEnumPage;
import com.mqfdy.code.designer.dialogs.EnumElementsEnumPage;
import com.mqfdy.code.designer.dialogs.ModelElementEditorDialog;
import com.mqfdy.code.designer.models.BusinessModelEvent;
import com.mqfdy.code.designer.models.BusinessModelManager;
import com.mqfdy.code.designer.utils.BusinessModelUtil;
import com.mqfdy.code.model.AbstractModelElement;
import com.mqfdy.code.model.Enumeration;

public class ImportEnumDialog extends ModelElementEditorDialog {

	/**
	 * 基本信息标签页
	 */
	private TabFolder tabBasic;
	private EnumBasicInfoEnumPage enumBasicInfoEnumPage;
	/**
	 * 枚举值信息标签页
	 */
	private TabFolder tabEnumeration;
	private EnumElementsEnumPage enumElementsEnumPage;
	private Enumeration enumeration;
	/**
	 * 从组件面板创建
	 */
	public String DIALOG_TITLE = "";
	public final String DIALOG_TITLE_OPERATION = "";
	public final String DIALOG_MESSAGE_IMPORT = "导入枚举类型";
	private BusinessModelManager manager = BusinessModelUtil
			.getEditorBusinessModelManager();
	
	
	public ImportEnumDialog(Shell parentShell) {
		super(parentShell);
		
	}
	
	public ImportEnumDialog(Shell parentShell, AbstractModelElement parent,
			Enumeration enumeration) {
		super(parentShell);
		this.enumeration = enumeration;
		this.DIALOG_TITLE = DIALOG_MESSAGE_IMPORT;
		operationType = ModelElementEditorDialog.OPERATION_TYPE_IMPORT;
	}
	
	
	protected Control createDialogArea(Composite parent) {
		// 初始化窗口
		GridLayout gridLayout = new GridLayout(1, true);
		parent.setLayout(gridLayout);
		parent.setSize(700, 500);
		setTitleAndMessage();
		createtabFolder(parent);// 创建标签页对象
		createContent(parent);
		//负责显示增加的key与value
		initControlValue();
		return parent;
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

		enumBasicInfoEnumPage = new EnumBasicInfoEnumPage(tabBasic, SWT.TOP, this);
		tabBasic.getItem(0).setControl(enumBasicInfoEnumPage);

		enumElementsEnumPage = new EnumElementsEnumPage(tabEnumeration, SWT.TOP, this);
		tabEnumeration.getItem(0).setControl(enumElementsEnumPage);
	}
	
	public void initControlValue() {
		if (this.enumeration != null) {
			enumBasicInfoEnumPage.initControlValue();
			enumElementsEnumPage.initControlValue();
		}
	}
	

	//验证
	private boolean validateAllInput() {
		boolean isOk = this.validateInput();
		return isOk;
	}
	public boolean validateInput() {
		boolean isOk = enumBasicInfoEnumPage.validateInput();
		if (isOk == false) {
			return false;
		}
		if (isOk == true) {
			isOk = enumElementsEnumPage.validateInput();
		}
		if (isOk == false) {
			return false;
		}
		return true;
	}
	
	@Override
	protected void okPressed() {
		if (validateAllInput() == true ) {
			updateTheEditingElement();
			isChaged = true;
			super.okPressed();
		}
	}
	
	
	/**
	 * shell标题
	 */
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("导入枚举类型");		
	}
	
	
	/**
	 * 设置标题和信息
	 */
	public void setTitleAndMessage() {
		setTitle("枚举类型");
		if (operationType.equals(OPERATION_TYPE_IMPORT)) {
			setMessage(DIALOG_MESSAGE_IMPORT);
		} 
	}
	
	public void updateTheEditingElement() {
		enumBasicInfoEnumPage.updateTheEditingElement();
		if (ModelElementEditorDialog.OPERATION_TYPE_IMPORT.equals(operationType)) {
			manager.businessObjectModelChanged(new BusinessModelEvent(
					BusinessModelEvent.MODEL_ELEMENT_UPDATE, enumeration));
		}
		
	}
	
	public EnumElementsEnumPage getEnumElementsEnumPage(){
		return enumElementsEnumPage;
	}
	public EnumBasicInfoEnumPage getEnumBasicInfoEnumPage(){
		return enumBasicInfoEnumPage;
	}
	
	public Enumeration getEnumeration() {
		return enumeration;
	}
	
	
	public void setEnumeration(Enumeration enumeration) {
		this.enumeration = enumeration;
	}

	protected int getShellStyle() {
		return super.getShellStyle() | SWT.RESIZE | SWT.MAX;
	}


	@Override
	protected void buttonPressed(int buttonId) {
		super.buttonPressed(buttonId);
	}
	
	
}
