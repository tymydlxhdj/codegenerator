package com.mqfdy.code.designer.dialogs;

import java.util.List;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

import com.mqfdy.code.designer.dialogs.sheetpage.PersistPolicySheetPage;
import com.mqfdy.code.designer.editor.BusinessModelEditorPlugin;
import com.mqfdy.code.designer.models.ImageKeys;
import com.mqfdy.code.designer.models.ImageManager;
import com.mqfdy.code.designer.preferences.ModelPreferencePage;
import com.mqfdy.code.designer.utils.BusinessModelUtil;
import com.mqfdy.code.model.AbstractModelElement;
import com.mqfdy.code.model.BusinessClass;
import com.mqfdy.code.model.BusinessOperation;
import com.mqfdy.code.model.IModelElement;
import com.mqfdy.code.reconfiguration.ReconstructionDialog;

/**
 * 业务类编辑弹出框(用于新增和编辑)
 * 
 * @author mqfdy
 * 
 */
public class BusinessClassEditorDialog extends ModelElementEditorDialog {

	public static final String DIALOG_TITLE_ADD = "创建业务实体";
	public static final String DIALOG_TITLE_EDIT = "修改业务实体";
	public static final String MESSAGE_TITLE = "业务实体";
	public static final String DIALOG_MESSAGE_ADD = "创建业务实体";
	public static final String DIALOG_MESSAGE_EDIT = "修改 @ 信息";

	public static final String TAB_BASICINFO_TITLE = "基本信息";
	public static final String TAB_PROPERTIES_TITLE = "属性列表";
	public static final String TAB_OPERATIONS_TITLE = "业务操作";
	public static final String TAB_PERSISTENCE_TITLE = "关系";
	// public static final String TAB_PERMISSION_TITLE = "权限";

	private BusinessClass businessClassCopy;
	/**
	 * 标签页对象
	 */
	private TabFolder tabFolder;

	/**
	 * 基本信息页
	 */
	private BusinessClassBasicInfoPage basicInfoPage;

	/**
	 * 属性列表页
	 */
	// private BusinessClassPropertiesPage propertiesPage;

	/**
	 * 操作列表页
	 */
	private BusinessClassOperationsPage operationsPage;

	/**
	 * 持久化策略页面
	 */
	PersistPolicySheetPage persistPolicySheetPage;
	
	/**
	 * 从组件面板创建
	 */
	private boolean  createFromPlatter = false;
	/**
	 * 权限列表页
	 */
	// private BusinessClassPermissionPage permissionPage;

	/**
	 * 新增业务类时构造函数
	 * 
	 * @param parentShell
	 * @param parent
	 *            父节点
	 */
	public BusinessClassEditorDialog(Shell parentShell,
			AbstractModelElement parent) {
		super(parentShell, parent);
	}

	/**
	 * 编辑业务类时构造函数
	 * 
	 * @param parentShell
	 * @param editingElement
	 *            编辑的对象
	 * @param parent
	 *            父节点
	 */
	public BusinessClassEditorDialog(Shell parentShell,
			AbstractModelElement editingElement, AbstractModelElement parent) {
		super(parentShell, editingElement, parent);
	}
	public BusinessClassEditorDialog(boolean createFromPlatter,Shell parentShell,
			AbstractModelElement editingElement, AbstractModelElement parent) {
		super(parentShell, editingElement, parent);
		this.createFromPlatter = createFromPlatter;
	}
	public BusinessClassEditorDialog(Shell shell) {
		super(shell);
	}

	protected Control createDialogArea(Composite composite) {
		// 初始化窗口
		GridLayout gridLayout = new GridLayout(1, true);
		composite.setLayout(gridLayout);

		createtabFolder(composite);// 创建标签页对象

		// tab 内容
		basicInfoPage = new BusinessClassBasicInfoPage(tabFolder, SWT.NONE,
				this);
		// propertiesPage = new BusinessClassPropertiesPage(tabFolder, SWT.NONE,
		// this);
		operationsPage = new BusinessClassOperationsPage(tabFolder, SWT.NONE,
				this);
		persistPolicySheetPage = new PersistPolicySheetPage(tabFolder,
				SWT.NONE, this);
		// permissionPage = new BusinessClassPermissionPage(tabFolder, SWT.NONE,
		// this);

		tabFolder.getItem(0).setControl(basicInfoPage);
		// tabFolder.getItem(1).setControl(propertiesPage);
		tabFolder.getItem(1).setControl(operationsPage);
		tabFolder.getItem(2).setControl(persistPolicySheetPage);
		// tabFolder.getItem(3).setControl(permissionPage);

		initControlValue();

		return composite;
	}

	/**
	 * 初始化创建 标签页
	 */
	private void createtabFolder(Composite composite) {
		tabFolder = new TabFolder(composite, SWT.NONE);
		tabFolder.setLayoutData(new GridData(GridData.FILL_BOTH));
		for (int i = 0; i < 3; i++) {
			TabItem tabItem = new TabItem(tabFolder, SWT.NONE);
			tabItem.setText(getTabTitle(i));
		}

		tabFolder.addMouseListener(new MouseListener() {

			public void mouseDoubleClick(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			public void mouseDown(MouseEvent event) {
				TabFolder tab = (TabFolder) event.getSource();
				/*
				 * if(tab.getSelectionIndex() != 3){
				 * if(permissionPage.getBusinessClassTreeView
				 * ().getDisplay().getSyncThread()!=null){
				 * permissionPage.getBusinessClassTreeView
				 * ().getDisplay().getSyncThread().interrupt(); } }
				 */
				if (tab.getSelectionIndex() == 2) {
					// 当切换到持久化策略页面时，刷新该页
					persistPolicySheetPage.refresh();
				}
			}

			public void mouseUp(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

		});
	}

	/**
	 * 操作按钮
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

	protected void buttonPressed(int buttonId) {
//		if (APPLY_ID == buttonId) {
//			applylPressed();
//		} else 
		if (12000 == buttonId) {
			if (validateAllInput() == true) {
				updateTheEditingElement();
				ReconstructionDialog d = new ReconstructionDialog(getShell(),
						(BusinessClass) editingElement, businessClassCopy);
				d.open();
			}
		} else {
			super.buttonPressed(buttonId);
		}
	}

	protected void okPressed() {
		if (validateAllInput() == true) {
			updateTheEditingElement();
			super.okPressed();
		}
	}

	protected void applylPressed() {
		if (validateAllInput() == true) {
			updateTheEditingElement();
			isChaged = true;
		}

	}

	/**
	 * 验证输入数据是否合法
	 */
	private boolean validateAllInput() {
		boolean isOk = basicInfoPage.validateInput();
		if (isOk == false) {
			return isOk;
		}
		/*
		 * isOk = propertiesPage.validateInput(); if(isOk == false){ return
		 * isOk; }
		 */

		/*
		 * isOk = permissionPage.validateInput(); if(isOk == false){ return
		 * isOk; }
		 */
		isOk = persistPolicySheetPage.validateInput();
		if (isOk == false) {
			return isOk;
		}
		return isOk;
	}

	/**
	 * 更新模型
	 */
	private void updateTheEditingElement() {
		basicInfoPage.updateTheEditingElement();
		// propertiesPage.updateTheEditingElement();
		operationsPage.updateTheEditingElement();
		persistPolicySheetPage.updateTheEditingElement();
		// permissionPage.updateTheEditingElement();
		this.editingElement = this.getBusinessClassCopy();
	}

	/**
	 * 设置标题和信息
	 */
	public void setTitleAndMessage() {
		setTitle(MESSAGE_TITLE);
		setTitleImage(ImageManager.getInstance().getImage(
				ImageKeys.IMG_DIALOG_BUSINESSCLASS));
		if (operationType.equals(OPERATION_TYPE_ADD)) {
			setMessage(DIALOG_MESSAGE_ADD);
		}else if (operationType.equals(OPERATION_TYPE_EDIT) && createFromPlatter) {
			setMessage(DIALOG_MESSAGE_ADD);
		} else {
			String displayName = this.editingElement.getDisplayName();
			setMessage(DIALOG_MESSAGE_EDIT.replaceAll("@", displayName),
					IMessageProvider.INFORMATION);
		}

	}

	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		if (operationType.equals(OPERATION_TYPE_ADD)) {
			newShell.setText(DIALOG_TITLE_ADD);
		} else if (operationType.equals(OPERATION_TYPE_EDIT) && createFromPlatter) {
			newShell.setText(DIALOG_TITLE_ADD);
		} else {
			String displayName = this.editingElement.getDisplayName();
			newShell.setText(DIALOG_TITLE_EDIT + " " + displayName);
		}
		newShell.setImage(ImageManager.getInstance().getImage(
				ImageKeys.IMG_MODEL_TYPE_BUSINESSCLASS));
		if(editingElement != null && IModelElement.STEREOTYPE_REVERSE.equals(editingElement.getStereotype())){
			newShell.setImage(ImageManager.getInstance().getImage(
					ImageKeys.IMG_MODEL_TYPE_BUSINESSCLASS_DB));
		}
	}

	/**
	 * 初始化弹出框控件的值
	 */
	private void initControlValue() {

		if (OPERATION_TYPE_EDIT.equals(operationType) && editingElement != null) {
			if (editingElement instanceof BusinessClass) {
				businessClassCopy = (BusinessClass) editingElement;
//				businessClassCopy
//						.setStereotype(BusinessClass.STEREOTYPE_CUSTOM);
			}
		} else {
			businessClassCopy = new BusinessClass();
			businessClassCopy.setStereotype(BusinessClass.STEREOTYPE_CUSTOM);
			// 添加标准操作
			IPreferenceStore store = BusinessModelEditorPlugin.getDefault()
					.getPreferenceStore();
			String isAdd = store
					.getString(ModelPreferencePage.ISADD_CUSTOMOPERATION);
			if (isAdd.equals("")
					|| store.getBoolean(ModelPreferencePage.ISADD_CUSTOMOPERATION)) {
				List<BusinessOperation> opList = BusinessOperation
						.getStandardOperations();
				for (BusinessOperation operation : opList) {
					operation.setBelongBusinessClass(businessClassCopy);
					businessClassCopy.addOperation(operation);
				}
			}

			String name = "";
			name = BusinessModelUtil.getEditorBusinessModelManager()
					.generateNextBusinessClassName();
			;
			businessClassCopy.setName(name);
		}
		setTitleAndMessage();

		// 初始化各个tab页的控件值
		basicInfoPage.initControlValue();
		// propertiesPage.initControlValue();
		operationsPage.initControlValue();
		persistPolicySheetPage.initControlValue();
		// permissionPage.initControlValue();
	}

	public BusinessClass getBusinessClassCopy() {
		return businessClassCopy;
	}

	private String getTabTitle(int index) {
		switch (index) {
		case 0:
			return TAB_BASICINFO_TITLE;
			/*
			 * case 1: return TAB_PROPERTIES_TITLE;
			 */
		case 1:
			return TAB_OPERATIONS_TITLE;
		case 2:
			return TAB_PERSISTENCE_TITLE;
			/*
			 * case 3: return TAB_PERMISSION_TITLE;
			 */
		default:
			return TAB_BASICINFO_TITLE;
		}
	}

	// public BusinessClassPropertiesPage getPropertiesPage() {
	// return propertiesPage;
	// }

	public BusinessClassOperationsPage getOperationsPage() {
		return operationsPage;
	}

	public BusinessClassBasicInfoPage getBasicInfoPage() {
		return basicInfoPage;
	}

}
