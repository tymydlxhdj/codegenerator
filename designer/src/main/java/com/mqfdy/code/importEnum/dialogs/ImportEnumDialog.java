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

// TODO: Auto-generated Javadoc
/**
 * The Class ImportEnumDialog.
 *
 * @author mqfdy
 */
public class ImportEnumDialog extends ModelElementEditorDialog {

	/** 基本信息标签页. */
	private TabFolder tabBasic;
	
	/** The enum basic info enum page. */
	private EnumBasicInfoEnumPage enumBasicInfoEnumPage;
	
	/** 枚举值信息标签页. */
	private TabFolder tabEnumeration;
	
	/** The enum elements enum page. */
	private EnumElementsEnumPage enumElementsEnumPage;
	
	/** The enumeration. */
	private Enumeration enumeration;
	
	/** 从组件面板创建. */
	public String DIALOG_TITLE = "";
	
	/** The dialog title operation. */
	public final String DIALOG_TITLE_OPERATION = "";
	
	/** The dialog message import. */
	public final String DIALOG_MESSAGE_IMPORT = "导入枚举类型";
	
	/** The manager. */
	private BusinessModelManager manager = BusinessModelUtil
			.getEditorBusinessModelManager();
	
	
	/**
	 * Instantiates a new import enum dialog.
	 *
	 * @param parentShell
	 *            the parent shell
	 */
	public ImportEnumDialog(Shell parentShell) {
		super(parentShell);
		
	}
	
	/**
	 * Instantiates a new import enum dialog.
	 *
	 * @param parentShell
	 *            the parent shell
	 * @param parent
	 *            the parent
	 * @param enumeration
	 *            the enumeration
	 */
	public ImportEnumDialog(Shell parentShell, AbstractModelElement parent,
			Enumeration enumeration) {
		super(parentShell);
		this.enumeration = enumeration;
		this.DIALOG_TITLE = DIALOG_MESSAGE_IMPORT;
		operationType = ModelElementEditorDialog.OPERATION_TYPE_IMPORT;
	}
	
	
	/**
	 * Creates the dialog area.
	 *
	 * @author mqfdy
	 * @param parent
	 *            the parent
	 * @return the control
	 * @Date 2018-09-03 09:00
	 */
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

		tabEnumeration = new TabFolder(composite, SWT.NONE);
		tabEnumeration.setLayoutData(new GridData(GridData.FILL_BOTH));
		TabItem tabItem2 = new TabItem(tabEnumeration, SWT.NONE);
		tabItem2.setText("枚举值信息");
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

		enumBasicInfoEnumPage = new EnumBasicInfoEnumPage(tabBasic, SWT.TOP, this);
		tabBasic.getItem(0).setControl(enumBasicInfoEnumPage);

		enumElementsEnumPage = new EnumElementsEnumPage(tabEnumeration, SWT.TOP, this);
		tabEnumeration.getItem(0).setControl(enumElementsEnumPage);
	}
	
	/**
	 * Inits the control value.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	public void initControlValue() {
		if (this.enumeration != null) {
			enumBasicInfoEnumPage.initControlValue();
			enumElementsEnumPage.initControlValue();
		}
	}
	

	/**
	 * Validate all input.
	 *
	 * @author mqfdy
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	//验证
	private boolean validateAllInput() {
		boolean isOk = this.validateInput();
		return isOk;
	}
	
	/**
	 * Validate input.
	 *
	 * @author mqfdy
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
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
	
	/**
	 * 
	 */
	@Override
	protected void okPressed() {
		if (validateAllInput() == true ) {
			updateTheEditingElement();
			isChaged = true;
			super.okPressed();
		}
	}
	
	
	/**
	 * shell标题.
	 *
	 * @author mqfdy
	 * @param newShell
	 *            the new shell
	 * @Date 2018-09-03 09:00
	 */
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("导入枚举类型");		
	}
	
	
	/**
	 * 设置标题和信息.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	public void setTitleAndMessage() {
		setTitle("枚举类型");
		if (operationType.equals(OPERATION_TYPE_IMPORT)) {
			setMessage(DIALOG_MESSAGE_IMPORT);
		} 
	}
	
	/**
	 * Update the editing element.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	public void updateTheEditingElement() {
		enumBasicInfoEnumPage.updateTheEditingElement();
		if (ModelElementEditorDialog.OPERATION_TYPE_IMPORT.equals(operationType)) {
			manager.businessObjectModelChanged(new BusinessModelEvent(
					BusinessModelEvent.MODEL_ELEMENT_UPDATE, enumeration));
		}
		
	}
	
	/**
	 * Gets the enum elements enum page.
	 *
	 * @author mqfdy
	 * @return the enum elements enum page
	 * @Date 2018-09-03 09:00
	 */
	public EnumElementsEnumPage getEnumElementsEnumPage(){
		return enumElementsEnumPage;
	}
	
	/**
	 * Gets the enum basic info enum page.
	 *
	 * @author mqfdy
	 * @return the enum basic info enum page
	 * @Date 2018-09-03 09:00
	 */
	public EnumBasicInfoEnumPage getEnumBasicInfoEnumPage(){
		return enumBasicInfoEnumPage;
	}
	
	/**
	 * Gets the enumeration.
	 *
	 * @author mqfdy
	 * @return the enumeration
	 * @Date 2018-09-03 09:00
	 */
	public Enumeration getEnumeration() {
		return enumeration;
	}
	
	
	/**
	 * Sets the enumeration.
	 *
	 * @author mqfdy
	 * @param enumeration
	 *            the new enumeration
	 * @Date 2018-09-03 09:00
	 */
	public void setEnumeration(Enumeration enumeration) {
		this.enumeration = enumeration;
	}

	/**
	 * @return
	 */
	protected int getShellStyle() {
		return super.getShellStyle() | SWT.RESIZE | SWT.MAX;
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
		super.buttonPressed(buttonId);
	}
	
	
}
