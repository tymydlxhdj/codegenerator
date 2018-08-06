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

/**
 * 属性编辑器弹出框
 * 
 * @author mqfdy
 * 
 */
public class FkEditorDialog extends ModelElementEditorDialog {

	public static final String DIALOG_TITLE_ADD = "创建编辑器";
	public static final String DIALOG_TITLE_EDIT = "编辑编辑器";
	public static final String MESSAGE_TITLE = "编辑器";
	public static final String DIALOG_MESSAGE_ADD = "创建编辑器";
	public static final String DIALOG_MESSAGE_EDIT = "修改编辑器";

	private boolean isClassA = false;
	/**
	 * 属性编辑器页面
	 */
	private FkEditorPage fkEditorPage;

	private List<QueryCondition> conditions = new ArrayList<QueryCondition>();

	public FkEditorDialog(Shell parentShell) {
		super(parentShell);
	}

	/**
	 * 构造函数(用于编辑)
	 * 
	 * @param parentShell
	 * @param editingElement
	 * @param parent
	 */
	public FkEditorDialog(boolean isClassA,Shell parentShell,
			AbstractModelElement editingElement, Association parent) {
		super(parentShell, editingElement, parent);
		this.isClassA = isClassA;
	}

	/**
	 * 构造函数(用于新增)
	 * 
	 * @param parentShell
	 * @param parent
	 */
	public FkEditorDialog(boolean isClassA,Shell parentShell, AbstractModelElement parent) {
		super(parentShell, parent);
		this.isClassA = isClassA;
	}

	protected Control createDialogArea(Composite parent) {
		// 初始化窗口
		GridLayout gridLayout = new GridLayout(1, true);
		parent.setLayout(gridLayout);

		fkEditorPage = new FkEditorPage(parent, SWT.NONE, this);
		fkEditorPage.setLayoutData(new GridData(GridData.FILL_BOTH));

		initControlValue();
		return parent;
	}

	protected void createButtonsForButtonBar(Composite composite) {
		createButton(composite, IDialogConstants.OK_ID,
				IDialogConstants.OK_LABEL, true);
		createButton(composite, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
	}

	protected void okPressed() {
		if (validateAllInput() == true) {
			updateTheEditingElement();
			super.okPressed();
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
		} else  if(editingElement != null){
			setMessage(DIALOG_MESSAGE_EDIT,
					IMessageProvider.INFORMATION);
		}

	}

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
	 * 初始化弹出框控件的值
	 */
	private void initControlValue() {
		if (OPERATION_TYPE_EDIT.equals(operationType) && editingElement != null) {
			this.setConditions(((Association) this.parent).getEditor().getConditions());
		} 
		setTitleAndMessage();
		// 初始化各个tab页的控件值
		fkEditorPage.initControlValue();

	}

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
	 * 校验是否匹配
	 * 
	 * @return
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

	private void updateTheEditingElement() {
		fkEditorPage.updateTheEditingElement();
	}

	public List<QueryCondition> getConditions() {
		return conditions;
	}

	public void setConditions(List<QueryCondition> conditions) {
		this.conditions = conditions;
	}

}
