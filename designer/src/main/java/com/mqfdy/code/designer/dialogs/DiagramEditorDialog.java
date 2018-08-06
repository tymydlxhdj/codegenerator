package com.mqfdy.code.designer.dialogs;

import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;

import com.mqfdy.code.designer.editor.BusinessModelEditor;
import com.mqfdy.code.designer.models.ImageKeys;
import com.mqfdy.code.designer.models.ImageManager;
import com.mqfdy.code.model.AbstractModelElement;
import com.mqfdy.code.model.ModelPackage;
import com.mqfdy.code.model.graph.Diagram;
import com.mqfdy.code.resource.validator.ValidatorUtil;

/**
 * 业务模型图编辑弹出框(用于新增或修改)
 * 
 * @author mqfdy
 * 
 */
public class DiagramEditorDialog extends ModelElementEditorDialog {

	public static final String DIALOG_TITLE = "创建业务模型图";

	public static final String MESSAGE_TITLE = "业务模型图";
	public static final String DIALOG_MESSAGE_ADD = "创建业务模型图";
	public static final String DIALOG_MESSAGE_EDIT = "修改业务模型图";

	// 显示组件
	private Label label_displayName;
	private Text text_displayName;

	private Label label_remark;
	private Text text_remark;

	private Label label_name;
	private Text text_name;

	public DiagramEditorDialog(Shell parentShell, AbstractModelElement parent) {
		super(parentShell, parent);
	}

	public DiagramEditorDialog(Shell parentShell, ModelPackage thePackage,
			AbstractModelElement parent) {
		super(parentShell, thePackage, parent);
	}

	protected Control createDialogArea(Composite parent) {
		Composite area = (Composite) super.createDialogArea(parent);
		Composite container = new Composite(area, SWT.NONE);

		// 设置标题和信息
		setTitleAndMessage();

		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		container.setLayout(layout);
		container.setLayoutData(new GridData(GridData.FILL_BOTH));

		GridData gridData = new GridData();
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalAlignment = GridData.FILL;

		label_name = new Label(container, SWT.NONE);
		label_name.setText(NAME_TEXT);

		text_name = new Text(container, SWT.BORDER);
		text_name.setLayoutData(gridData);

		label_displayName = new Label(container, SWT.NONE);
		label_displayName.setText(DISPLAYNAME_TEXT);

		text_displayName = new Text(container, SWT.BORDER);
		text_displayName.setLayoutData(gridData);

		label_remark = new Label(container, SWT.NONE);
		label_remark.setText(REMARK_TEXT);

		text_remark = new Text(container, SWT.MULTI | SWT.BORDER | SWT.VERTICAL | SWT.WRAP);
		GridData gridData2 = new GridData(GridData.FILL_BOTH);
		gridData2.grabExcessVerticalSpace = true;
		text_remark.setLayoutData(gridData2);

		initControlValue();
		return area;
	}

	protected void buttonPressed(int buttonId) {
//		if (APPLY_ID == buttonId) {
//			applylPressed();
//		} else {
			super.buttonPressed(buttonId);
//		}
	}

	protected void okPressed() {
		if (isValidInput()) {
			createDiagram();
			super.okPressed();
		}
	}

	protected void applylPressed() {
		isValidInput();
	}

	private void createDiagram() {
		String name = this.text_name.getText().trim();
		String displayName = this.text_displayName.getText().trim();
		String remark = this.text_remark.getText().trim();
		editingElement = new Diagram((ModelPackage) parent, name, displayName);
		editingElement.setRemark(remark);
	}

	/**
	 * 输入校验
	 * 
	 * @return
	 */
	private boolean isValidInput() {
		boolean valid = true;
		if (text_name.getText().length() == 0) {
			setErrorMessage(IBusinessClassEditorPage.ERROR_MESSAGE_NAME_NULLABLE);
			valid = false;
		}
		if (text_displayName.getText().length() == 0) {
			setErrorMessage(IBusinessClassEditorPage.ERROR_MESSAGE_DISPLAYNAME_NULLABLE);
			valid = false;
		}
		if (!ValidatorUtil.valiName(text_name.getText())) {
			setErrorMessage(IBusinessClassEditorPage.ERROR_MESSAGE_NAME_RULE);
			valid = false;
		}
		if (!ValidatorUtil.valiDisplayName(text_displayName.getText())) {
			setErrorMessage(IBusinessClassEditorPage.ERROR_DISPLAYNAME);
			valid = false;
		}
		if (!ValidatorUtil.valiNameLength(text_name.getText())) {
			setErrorMessage(IBusinessClassEditorPage.ERROR_MESSAGE_NAME_LENGTH);
			return false;
		}
		if (!ValidatorUtil.valiDisplayNameLength(text_displayName.getText())) {
			setErrorMessage(IBusinessClassEditorPage.ERROR_DISPLAYNAME_LENGTH);
			return false;
		}
		if (!ValidatorUtil.valiRemarkLength(text_remark.getText())) {
			setErrorMessage(IBusinessClassEditorPage.TOOLONG_ERROR_REMARK);
			return false;
		}
		int i = 0;
		if (parent instanceof ModelPackage) {
			for (AbstractModelElement dia : ((ModelPackage) parent)
					.getChildren()) {
				if (dia instanceof Diagram) {
					if (dia.getName().equals(text_name.getText()))
						i++;
				}
			}
		}
		if (editingElement != null && i > 1) {
			setErrorMessage("图名称已存在！");
			valid = false;
		}
		if (editingElement == null && i > 0) {
			setErrorMessage("图名称已存在！");
			valid = false;
		}
		return valid;
	}

	/**
	 * 设置标题和信息
	 */
	private void setTitleAndMessage() {
		setTitle(MESSAGE_TITLE);
		setTitleImage(ImageManager.getInstance().getImage(
				ImageKeys.IMG_DIALOG_DIAGRAM));
		if (operationType.equals(OPERATION_TYPE_ADD)) {
			setMessage(DIALOG_MESSAGE_ADD);
		} else {
			setMessage(DIALOG_MESSAGE_EDIT, IMessageProvider.INFORMATION);
		}
	}

	/**
	 * 初始化弹出框控件的值
	 */
	private void initControlValue() {

		if (OPERATION_TYPE_EDIT.equals(operationType) && editingElement != null) {
			text_name.setText(editingElement.getName());
			text_displayName.setText(editingElement.getDisplayName());
			text_remark.setText(editingElement.getRemark());
		} else {

			// String name =
			// BusinessModelManager.getManager().generateNextDiagramName();
			String name = "";
			IEditorPart editorPart = PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow().getActivePage()
					.getActiveEditor();
			if (editorPart instanceof BusinessModelEditor) {
				name = ((BusinessModelEditor) editorPart).getBuEditor()
						.getBusinessModelManager().generateNextDiagramName();
				;
			}
			text_name.setText(name);
			text_displayName.setText(name);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets
	 * .Shell)
	 */
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText(DIALOG_TITLE);
		newShell.setImage(ImageManager.getInstance().getImage(
				ImageKeys.IMG_MODEL_TYPE_DIAGRAM));
	}
}
