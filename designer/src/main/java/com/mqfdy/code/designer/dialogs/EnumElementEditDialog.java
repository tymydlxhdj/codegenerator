package com.mqfdy.code.designer.dialogs;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.mqfdy.code.designer.models.ImageKeys;
import com.mqfdy.code.designer.models.ImageManager;
import com.mqfdy.code.model.AbstractModelElement;
import com.mqfdy.code.model.EnumElement;
import com.mqfdy.code.model.Enumeration;

// TODO: Auto-generated Javadoc
/**
 * 枚举类型编辑对话框.
 *
 * @author mqfdy
 */
public class EnumElementEditDialog extends ModelElementEditorDialog implements
		IBusinessClassEditorPage {

	/** The dialog title. */
	public String DIALOG_TITLE = "";
	
	/** The dialog title operation. */
	public final String DIALOG_TITLE_OPERATION = "";
	
	/** The dialog message add. */
	public final String DIALOG_MESSAGE_ADD = "创建枚举值信息";
	
	/** The dialog message edit. */
	public final String DIALOG_MESSAGE_EDIT = "修改枚举值信息";

	/** The label order no. */
	private Label labelOrderNo;
	
	/** The text order no. */
	private Text textOrderNo;

	/** The label key. */
	private Label labelKey;
	
	/** The text key. */
	private Text textKey;

	/** The label value. */
	private Label labelValue;
	
	/** The text vlue. */
	private Text textVlue;

	/** The element. */
	private EnumElement element;

	/** The parent page. */
	private EnumElementsPage parentPage;

	/** The has save. */
	private boolean hasSave = false;// 是否已经保存

	/**
	 * Instantiates a new enum element edit dialog.
	 *
	 * @param parentShell
	 *            the parent shell
	 * @param parent
	 *            the parent
	 * @param element
	 *            the element
	 * @param parentPage
	 *            the parent page
	 */
	public EnumElementEditDialog(Shell parentShell,
			AbstractModelElement parent, EnumElement element,
			EnumElementsPage parentPage) {
		super(parentShell);
		if (element == null) {
			this.element = new EnumElement("", "");
			this.DIALOG_TITLE = DIALOG_MESSAGE_ADD;
			this.parent = parent;
			this.element.setBelongEnumeration((Enumeration) parent);
		} else {
			this.element = element;
			this.DIALOG_TITLE = DIALOG_MESSAGE_EDIT + element.getDisplayName();
			operationType = ModelElementEditorDialog.OPERATION_TYPE_EDIT;
		}
		this.parentPage = parentPage;
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
		setTitle("EnumElement");
		setTitleImage(ImageManager.getInstance().getImage(
				ImageKeys.IMG_DIALOG_BUSINESSCLASS));
		if (operationType.equals(OPERATION_TYPE_ADD)) {
			setMessage(DIALOG_MESSAGE_ADD);
		} else {
			setMessage(DIALOG_MESSAGE_EDIT, IMessageProvider.INFORMATION);
		}

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
		layout.numColumns = 2;
		layout.marginTop = 5;
		layout.marginBottom = 5;
		layout.marginLeft = 5;
		layout.marginRight = 5;
		layout.verticalSpacing = 5;
		layout.makeColumnsEqualWidth = false;
		parent.setLayout(layout);

		labelOrderNo = new Label(parent, SWT.CENTER);
		labelOrderNo.setText("序号:");

		textOrderNo = new Text(parent, SWT.NONE);
		textOrderNo.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false,
				1, 1));
		textOrderNo.setEnabled(false);

		labelKey = new Label(parent, SWT.CENTER);
		labelKey.setText("Key:");

		textKey = new Text(parent, SWT.NONE);
		textKey.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false, 1,
				1));

		labelValue = new Label(parent, SWT.CENTER);
		labelValue.setText("值:");

		textVlue = new Text(parent, SWT.NONE);
		textVlue.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false, 1,
				1));
	}

	/**
	 * 
	 */
	public void initControlValue() {
		if (ModelElementEditorDialog.OPERATION_TYPE_ADD.equals(operationType)) {
			textOrderNo.setText(parentPage.getListElements().size() + 1 + "");
		}
		if (ModelElementEditorDialog.OPERATION_TYPE_EDIT.equals(operationType)) {
			textOrderNo.setText(element.getOrderNum() + "");
			textKey.setText(element.getKey());
			textVlue.setText(element.getValue());
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
		if (ModelElementEditorDialog.OPERATION_TYPE_ADD.equals(operationType)) {
			element.setKey(textKey.getText());
			element.setValue(textVlue.getText());
			element.setDisplayName(textVlue.getText());
			element.setOrderNum(parentPage.getListElements().size() + 1);
			parentPage.getListElements().add(element);
			parentPage.refreshTable();
		}
		if (ModelElementEditorDialog.OPERATION_TYPE_EDIT.equals(operationType)) {
			element.setKey(textKey.getText());
			element.setValue(textVlue.getText());
			element.setDisplayName(textVlue.getText());
			if (parentPage != null) {
				parentPage.refreshTable();
			}
		}
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
	@Override
	protected void okPressed() {
		if (validateAllInput() == true && !hasSave) {
			updateTheEditingElement();
			hasSave = true;
		}
		super.okPressed();
	}

	/**
	 * Applyl pressed.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	protected void applylPressed() {
		if (validateAllInput() == true && !hasSave) {
			updateTheEditingElement();
			hasSave = true;
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
}
