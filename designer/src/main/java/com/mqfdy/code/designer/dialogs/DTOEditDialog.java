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
import com.mqfdy.code.model.DataTransferObject;
import com.mqfdy.code.model.ModelPackage;
import com.mqfdy.code.model.SolidifyPackage;
import com.mqfdy.code.model.graph.Diagram;

// TODO: Auto-generated Javadoc
/**
 * DTO编辑对话框.
 *
 * @author mqfdy
 */
public class DTOEditDialog extends ModelElementEditorDialog implements
		IBusinessClassEditorPage {

	/** The dialog title. */
	public String DIALOG_TITLE = "";
	
	/** The dialog title operation. */
	public final String DIALOG_TITLE_OPERATION = "";
	
	/** The dialog message add. */
	public final String DIALOG_MESSAGE_ADD = "创建数据传输对象 ";
	
	/** The dialog message edit. */
	public final String DIALOG_MESSAGE_EDIT = "修改数据传输对象 ";

	/** The parent. */
	private AbstractModelElement parent;

	/** The dto. */
	DataTransferObject dto;
	
	/** 基本信息标签页. */
	private TabFolder tabBasic;

	/** The dto basic info page. */
	private DTOBasicInfoPage dtoBasicInfoPage;
	
	/** 属性信息标签页. */
	private TabFolder tabProperties;

	/** The dto properties page. */
	private DTOPropertiesPage dtoPropertiesPage;
	
	/** 版本信息. */
	private VersionInfoPanel versionPanel;

	/** The manager. */
	private BusinessModelManager manager = BusinessModelUtil
			.getEditorBusinessModelManager();

	/**
	 * Instantiates a new DTO edit dialog.
	 *
	 * @param parentShell
	 *            the parent shell
	 * @param parent
	 *            the parent
	 * @param dto
	 *            the dto
	 */
	public DTOEditDialog(Shell parentShell, AbstractModelElement parent,
			DataTransferObject dto) {
		super(parentShell);

		if (dto == null) {
			this.dto = new DataTransferObject();
			this.DIALOG_TITLE = DIALOG_MESSAGE_ADD;
			this.parent = parent;
			if (parent instanceof ModelPackage) {
				this.dto.setBelongPackage((ModelPackage) parent);
			} else if (parent instanceof Diagram) {
				this.dto.setBelongPackage((ModelPackage) (parent.getParent()));
			} else if (parent instanceof SolidifyPackage) {
				this.dto.setBelongPackage((ModelPackage) (parent.getParent()));
			}
			operationType = ModelElementEditorDialog.OPERATION_TYPE_ADD;
		} else {
			this.dto = dto;
			this.DIALOG_TITLE = DIALOG_MESSAGE_EDIT + dto.getDisplayName();
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
				ImageKeys.IMG_MODEL_TYPE_DTO));
	}

	/**
	 * 设置标题和信息.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	public void setTitleAndMessage() {
		setTitle("数据传输对象");
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

		tabProperties = new TabFolder(composite, SWT.NONE);
		tabProperties.setLayoutData(new GridData(GridData.FILL_BOTH));
		TabItem tabItem2 = new TabItem(tabProperties, SWT.NONE);
		tabItem2.setText("属性信息");

		versionPanel = new VersionInfoPanel(composite, SWT.NONE | SWT.FILL);
		GridData versionGridData = new GridData(GridData.FILL_HORIZONTAL);
		versionGridData.horizontalSpan = 4;
		versionGridData.grabExcessHorizontalSpace = true;
		versionGridData.exclude = true;
		versionPanel.setLayoutData(versionGridData);
		versionPanel.setVisible(false);
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

		dtoBasicInfoPage = new DTOBasicInfoPage(tabBasic, SWT.TOP, this);
		tabBasic.getItem(0).setControl(dtoBasicInfoPage);

		dtoPropertiesPage = new DTOPropertiesPage(tabProperties, SWT.TOP, this);
		tabProperties.getItem(0).setControl(dtoPropertiesPage);
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
		if (this.dto != null) {
			dtoBasicInfoPage.initControlValue();
			dtoPropertiesPage.initControlValue();
			versionPanel.initControlValue(dto.getVersionInfo());
		}
	}

	/**
	 * @return
	 */
	public boolean validateInput() {
		boolean isOk = dtoBasicInfoPage.validateInput();
		if (isOk == false) {
			return false;
		}
		return true;
	}

	/**
	 * 
	 */
	public void updateTheEditingElement() {

		dtoBasicInfoPage.updateTheEditingElement();

		dtoPropertiesPage.updateTheEditingElement();

		dto.setVersionInfo(versionPanel.getVersionInfo());

		if (ModelElementEditorDialog.OPERATION_TYPE_ADD.equals(operationType)) {
			manager.businessObjectModelChanged(new BusinessModelEvent(
					BusinessModelEvent.MODEL_ELEMENT_ADD, dto));
		}
		if (ModelElementEditorDialog.OPERATION_TYPE_EDIT.equals(operationType)) {
			manager.businessObjectModelChanged(new BusinessModelEvent(
					BusinessModelEvent.MODEL_ELEMENT_UPDATE, dto));
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

}
