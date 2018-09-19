package com.mqfdy.code.designer.dialogs;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import com.mqfdy.code.model.AbstractModelElement;

// TODO: Auto-generated Javadoc
/**
 * 模型元素编辑弹出框.
 *
 * @author mqfdy
 */
public class ModelElementEditorDialog extends TitleAreaDialog {

	/** The Constant DISPLAYNAME_TEXT. */
	public static final String DISPLAYNAME_TEXT = "显示名：";
	
	/** The Constant NAME_TEXT. */
	public static final String NAME_TEXT = "名称：";
	
	/** The Constant REMARK_TEXT. */
	public static final String REMARK_TEXT = "备注：";

//	public static final int APPLY_ID = 10000;
//	public static final String APPLY_LABEL = "Apply";

	/** The is chaged. */
protected boolean isChaged = false;

	/** 所要编辑的模型元素. */
	protected AbstractModelElement editingElement;

	/** 父节点. */
	protected AbstractModelElement parent;

	/** The Constant OPERATION_TYPE_ADD. */
	public static final String OPERATION_TYPE_ADD = "add";
	
	/** The Constant OPERATION_TYPE_EDIT. */
	public static final String OPERATION_TYPE_EDIT = "edit";

/** The Constant OPERATION_TYPE_IMPORT. */
//新增导入枚举
	public static final String OPERATION_TYPE_IMPORT = "import";

	/** 当前弹出框的操作类型 默认为新增. */
	protected String operationType = OPERATION_TYPE_ADD;

	/**
	 * Instantiates a new model element editor dialog.
	 *
	 * @param parentShell
	 *            the parent shell
	 */
	public ModelElementEditorDialog(Shell parentShell) {
		super(parentShell);
	}

	/**
	 * 构造方法(新增时).
	 *
	 * @param parentShell
	 *            the parent shell
	 * @param parent
	 *            the parent
	 */
	public ModelElementEditorDialog(Shell parentShell,
			AbstractModelElement parent) {
		super(parentShell);
		this.parent = parent;
		parent.setStereotype("1");
	}

	/**
	 * 构造方法(编辑时).
	 *
	 * @param parentShell
	 *            the parent shell
	 * @param editingElement
	 *            the editing element
	 * @param parent
	 *            the parent
	 */
	public ModelElementEditorDialog(Shell parentShell,
			AbstractModelElement editingElement, AbstractModelElement parent) {
		super(parentShell);
		this.editingElement = editingElement;
		this.parent = parent;
		setOperationType(OPERATION_TYPE_EDIT);
	}

	/**
	 * 获取当前编辑的对象.
	 *
	 * @author mqfdy
	 * @return the editing element
	 * @Date 2018-09-03 09:00
	 */
	public AbstractModelElement getEditingElement() {
		return editingElement;
	}

	/**
	 * Sets the editing element.
	 *
	 * @author mqfdy
	 * @param editingElement
	 *            the new editing element
	 * @Date 2018-09-03 09:00
	 */
	public void setEditingElement(AbstractModelElement editingElement) {
		this.editingElement = editingElement;
	}

	/**
	 * Gets the operation type.
	 *
	 * @author mqfdy
	 * @return the operation type
	 * @Date 2018-09-03 09:00
	 */
	public String getOperationType() {
		return operationType;
	}

	/**
	 * Sets the operation type.
	 *
	 * @author mqfdy
	 * @param operationType
	 *            the new operation type
	 * @Date 2018-09-03 09:00
	 */
	private void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	/**
	 * 操作按钮.
	 *
	 * @author mqfdy
	 * @param parent
	 *            the parent
	 * @Date 2018-09-03 09:00
	 */
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,
				true);
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
//		createButton(parent, APPLY_ID, APPLY_LABEL, true);
	}

	/**
	 * @return
	 */
	protected int getShellStyle() {
		return super.getShellStyle() | SWT.RESIZE | SWT.MAX;
	}

	/**
	 * Checks if is changed.
	 *
	 * @author mqfdy
	 * @return true, if is changed
	 * @Date 2018-09-03 09:00
	 */
	public boolean isChanged() {
		return this.isChaged;
	}

}
