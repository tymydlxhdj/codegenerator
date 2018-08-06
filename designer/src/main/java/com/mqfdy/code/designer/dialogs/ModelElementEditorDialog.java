package com.mqfdy.code.designer.dialogs;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import com.mqfdy.code.model.AbstractModelElement;

/**
 * 模型元素编辑弹出框
 * 
 * @author mqfdy
 * 
 */
public class ModelElementEditorDialog extends TitleAreaDialog {

	public static final String DISPLAYNAME_TEXT = "显示名：";
	public static final String NAME_TEXT = "名称：";
	public static final String REMARK_TEXT = "备注：";

//	public static final int APPLY_ID = 10000;
//	public static final String APPLY_LABEL = "Apply";

	protected boolean isChaged = false;

	/**
	 * 所要编辑的模型元素
	 */
	protected AbstractModelElement editingElement;

	/**
	 * 父节点
	 */
	protected AbstractModelElement parent;

	public static final String OPERATION_TYPE_ADD = "add";
	public static final String OPERATION_TYPE_EDIT = "edit";
//新增导入枚举
	public static final String OPERATION_TYPE_IMPORT = "import";

	/**
	 * 当前弹出框的操作类型 默认为新增
	 */
	protected String operationType = OPERATION_TYPE_ADD;

	public ModelElementEditorDialog(Shell parentShell) {
		super(parentShell);
	}

	/**
	 * 构造方法(新增时)
	 * 
	 * @param parentShell
	 * @param parent
	 */
	public ModelElementEditorDialog(Shell parentShell,
			AbstractModelElement parent) {
		super(parentShell);
		this.parent = parent;
		parent.setStereotype("1");
	}

	/**
	 * 构造方法(编辑时)
	 * 
	 * @param parentShell
	 * @param parent
	 */
	public ModelElementEditorDialog(Shell parentShell,
			AbstractModelElement editingElement, AbstractModelElement parent) {
		super(parentShell);
		this.editingElement = editingElement;
		this.parent = parent;
		setOperationType(OPERATION_TYPE_EDIT);
	}

	/**
	 * 获取当前编辑的对象
	 * 
	 * @return
	 */
	public AbstractModelElement getEditingElement() {
		return editingElement;
	}

	public void setEditingElement(AbstractModelElement editingElement) {
		this.editingElement = editingElement;
	}

	public String getOperationType() {
		return operationType;
	}

	private void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	/**
	 * 操作按钮
	 */
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,
				true);
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
//		createButton(parent, APPLY_ID, APPLY_LABEL, true);
	}

	protected int getShellStyle() {
		return super.getShellStyle() | SWT.RESIZE | SWT.MAX;
	}

	public boolean isChanged() {
		return this.isChaged;
	}

}
