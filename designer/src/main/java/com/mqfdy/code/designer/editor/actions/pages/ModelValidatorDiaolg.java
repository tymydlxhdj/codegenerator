package com.mqfdy.code.designer.editor.actions.pages;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

import com.mqfdy.code.designer.models.ImageKeys;
import com.mqfdy.code.designer.models.ImageManager;
import com.mqfdy.code.model.BusinessObjectModel;

// TODO: Auto-generated Javadoc
/**
 * 模型校验Dialog.
 *
 * @author ZHANGHE
 */
public class ModelValidatorDiaolg extends TitleAreaDialog {
	
	/** 标签页对象. */
	private TabFolder tabFolder;
	
	/** The vos page. */
	private ValidatorObjectSelectPage vosPage;
	
	/** The sel page. */
	private ModelValidatorSelectPage selPage;
	
	/** The business object model. */
	private BusinessObjectModel businessObjectModel;
	
	/** The ok button. */
	private Button okButton;

	/**
	 * Instantiates a new model validator diaolg.
	 *
	 * @param shell
	 *            the shell
	 * @param businessObjectModel
	 *            the business object model
	 */
	public ModelValidatorDiaolg(Shell shell,
			BusinessObjectModel businessObjectModel) {
		// TODO Auto-generated constructor stub
		super(shell);
		this.businessObjectModel = businessObjectModel;
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
		okButton = createButton(parent, IDialogConstants.OK_ID,
				IDialogConstants.OK_LABEL, true);
		// okButton.setEnabled(false);
		Button cancelButton = createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
	}

	/**
	 * Configure shell.
	 *
	 * @author mqfdy
	 * @param newShell
	 *            the new shell
	 * @Date 2018-09-03 09:00
	 */
	@Override
	protected void configureShell(Shell newShell) {
		// TODO Auto-generated method stub
		super.configureShell(newShell);
		newShell.setText("模型校验向导");
		Image icon;
		icon = ImageManager.getInstance().getImage(
				ImageKeys.IMG_MODEL_TYPE_DIAGRAM);
		newShell.setImage(icon);
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
		setTitle("校验业务对象模型格式和逻辑是否正确");
		setMessage("请选择校验项及校验对象", 1);
		// 创建标签页对象
		tabFolder = new TabFolder(parent, SWT.NONE);
		tabFolder.setLayoutData(new GridData(GridData.FILL_BOTH));
		TabItem tabItem = new TabItem(tabFolder, SWT.NONE);
		tabItem.setText("校验项选择");
		TabItem tabItem2 = new TabItem(tabFolder, SWT.NONE);
		tabItem2.setText("校验对象选择");
		// tab 内容
		selPage = new ModelValidatorSelectPage(tabFolder, SWT.NONE);
		vosPage = new ValidatorObjectSelectPage(businessObjectModel, tabFolder,
				SWT.NONE);
		// osPage = new ObjectSelectPage(tabFolder,
		// SWT.NONE,this,businessObjectModel);
		// pPage = new ParametersPage(tabFolder, SWT.NONE,this);
		tabFolder.getItem(0).setControl(selPage);
		tabFolder.getItem(1).setControl(vosPage);
		return parent;
	}

	/**
	 * Gets the object select page.
	 *
	 * @author mqfdy
	 * @return the object select page
	 * @Date 2018-09-03 09:00
	 */
	public ValidatorObjectSelectPage getObjectSelectPage() {
		return vosPage;
	}

	/**
	 * Gets the model validator select page.
	 *
	 * @author mqfdy
	 * @return the model validator select page
	 * @Date 2018-09-03 09:00
	 */
	public ModelValidatorSelectPage getModelValidatorSelectPage() {
		return selPage;
	}

	/**
	 * 
	 */
	@Override
	protected void okPressed() {
		super.okPressed();
	}

	/**
	 * Gets the ok button.
	 *
	 * @author mqfdy
	 * @return the ok button
	 * @Date 2018-09-03 09:00
	 */
	public Button getOkButton() {
		return okButton;
	}

}
