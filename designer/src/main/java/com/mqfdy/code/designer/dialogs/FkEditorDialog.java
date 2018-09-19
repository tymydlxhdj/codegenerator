package com.mqfdy.code.designer.dialogs;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

import com.mqfdy.code.designer.models.ImageKeys;
import com.mqfdy.code.designer.models.ImageManager;
import com.mqfdy.code.model.AbstractModelElement;
import com.mqfdy.code.model.Association;
import com.mqfdy.code.model.QueryCondition;
import com.mqfdy.code.model.utils.EditorType;
import com.mqfdy.code.resource.validator.ValidatorUtil;

// TODO: Auto-generated Javadoc
/**
 * 属性编辑器弹出框.
 *
 * @author mqfdy
 */
public class FkEditorDialog extends ModelElementEditorDialog {

	/** The Constant DIALOG_TITLE_ADD. */
	public static final String DIALOG_TITLE_ADD = "创建编辑器";
	
	/** The Constant DIALOG_TITLE_EDIT. */
	public static final String DIALOG_TITLE_EDIT = "编辑编辑器";
	
	/** The Constant MESSAGE_TITLE. */
	public static final String MESSAGE_TITLE = "编辑器";
	
	/** The Constant DIALOG_MESSAGE_ADD. */
	public static final String DIALOG_MESSAGE_ADD = "创建编辑器";
	
	/** The Constant DIALOG_MESSAGE_EDIT. */
	public static final String DIALOG_MESSAGE_EDIT = "修改编辑器";

	/** The is class A. */
	private boolean isClassA = false;
	
	/** 属性编辑器页面. */
	private FkEditorPage fkEditorPage;

	/** The conditions. */
	private List<QueryCondition> conditions = new ArrayList<QueryCondition>();

	/**
	 * Instantiates a new fk editor dialog.
	 *
	 * @param parentShell
	 *            the parent shell
	 */
	public FkEditorDialog(Shell parentShell) {
		super(parentShell);
	}

	/**
	 * 构造函数(用于编辑).
	 *
	 * @param isClassA
	 *            the is class A
	 * @param parentShell
	 *            the parent shell
	 * @param editingElement
	 *            the editing element
	 * @param parent
	 *            the parent
	 */
	public FkEditorDialog(boolean isClassA,Shell parentShell,
			AbstractModelElement editingElement, Association parent) {
		super(parentShell, editingElement, parent);
		this.isClassA = isClassA;
	}

	/**
	 * 构造函数(用于新增).
	 *
	 * @param isClassA
	 *            the is class A
	 * @param parentShell
	 *            the parent shell
	 * @param parent
	 *            the parent
	 */
	public FkEditorDialog(boolean isClassA,Shell parentShell, AbstractModelElement parent) {
		super(parentShell, parent);
		this.isClassA = isClassA;
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

		fkEditorPage = new FkEditorPage(parent, SWT.NONE, this);
		fkEditorPage.setLayoutData(new GridData(GridData.FILL_BOTH));

		initControlValue();
		return parent;
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
		createButton(composite, IDialogConstants.OK_ID,
				IDialogConstants.OK_LABEL, true);
		createButton(composite, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
	}

	/**
	 * 
	 */
	protected void okPressed() {
		if (validateAllInput() == true) {
			updateTheEditingElement();
			super.okPressed();
		}
	}

	/**
	 * 设置标题和信息.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	private void setTitleAndMessage() {
		setTitle(MESSAGE_TITLE);
		setTitleImage(ImageManager.getInstance().getImage(
				ImageKeys.IMG_DIALOG_BUSINESSCLASS));
		if (operationType.equals(OPERATION_TYPE_ADD)) {
			setMessage(DIALOG_MESSAGE_ADD);
		} else  if(editingElement != null){
			setMessage(DIALOG_MESSAGE_EDIT,
					IMessageProvider.INFORMATION);
		}

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
			newShell.setText(DIALOG_TITLE_ADD);
		} else if(editingElement != null) {
			newShell.setText(DIALOG_TITLE_EDIT);
		}
		newShell.setImage(ImageManager.getInstance().getImage(
				ImageKeys.IMG_MODEL_TYPE_PROPERTY));
	}

	/**
	 * 初始化弹出框控件的值.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	private void initControlValue() {
		if (OPERATION_TYPE_EDIT.equals(operationType) && editingElement != null) {
			this.setConditions(((Association) this.parent).getEditor().getConditions());
		} 
		setTitleAndMessage();
		// 初始化各个tab页的控件值
		fkEditorPage.initControlValue();

	}

	/**
	 * Validate all input.
	 *
	 * @author mqfdy
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	private boolean validateAllInput() {
		boolean isOk = fkEditorPage.validateInput();
		if (isOk == false) {
			return isOk;
		}

		isOk = checkEditorType();
		if (isOk == false) {
			this.setErrorMessage("数据类型与编辑器类型不匹配！");
			return isOk;
		}

		return isOk;
	}

	/**
	 * 校验是否匹配.
	 *
	 * @author mqfdy
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	private boolean checkEditorType() {
		String curDataType;
		if(isClassA)
			curDataType = ((Association) parent).getClassA().getPkProperty().getDataType();
		else
			curDataType = ((Association) parent).getClassB().getPkProperty().getDataType();
		EditorType curEditorType = fkEditorPage.getCurType();
		if (curDataType == null || curEditorType == null) {
			return false;
		} else {
			return ValidatorUtil.getEditorMap().get(curDataType)
					.get(curEditorType) != null;
		}
	}

	/**
	 * Update the editing element.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	private void updateTheEditingElement() {
		fkEditorPage.updateTheEditingElement();
	}

	/**
	 * Gets the conditions.
	 *
	 * @author mqfdy
	 * @return the conditions
	 * @Date 2018-09-03 09:00
	 */
	public List<QueryCondition> getConditions() {
		return conditions;
	}

	/**
	 * Sets the conditions.
	 *
	 * @author mqfdy
	 * @param conditions
	 *            the new conditions
	 * @Date 2018-09-03 09:00
	 */
	public void setConditions(List<QueryCondition> conditions) {
		this.conditions = conditions;
	}

}
