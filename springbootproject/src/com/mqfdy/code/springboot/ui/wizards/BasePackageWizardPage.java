package com.mqfdy.code.springboot.ui.wizards;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.mqfdy.code.springboot.core.NewMicroProjectOperation;
import com.mqfdy.code.springboot.core.generator.utils.StringUtils;
import com.mqfdy.code.springboot.ui.util.KeyWordsChecker;
import com.mqfdy.code.springboot.ui.util.ValidatorUtil;

public class BasePackageWizardPage extends WizardPage {
	
	private static final ImageDescriptor WIZBAN_IMAGE = ImageDescriptor.createFromURL(
			BaseInfoWizardPage.class.getClassLoader().getResource("icons/wizban/boot_wizard.png"));
	

	/**
	 * 基础包名text
	 */
	private Text basePackageText;
	/**
	 * 项目类型下拉框
	 */
	private Combo projectTypeCombo;
	
	private String basePackage;
	private final NewMicroProjectOperation operation;

	protected BasePackageWizardPage(NewMicroProjectOperation operation) {
		super("basePackagePage","基础包配置",WIZBAN_IMAGE);
		this.operation = operation;
		//setTitle("基础包配置");
	}

	@Override
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		container.setLayout(layout);
		layout.numColumns = 2;
		// 文本框布局样式
		GridData textgridData = new GridData();

		Label label = new Label(container, SWT.NONE);
		label.setText("基础包名称:");
		label.setLayoutData(textgridData);
		textgridData = new GridData();
		textgridData.horizontalAlignment = GridData.FILL;
		textgridData.grabExcessHorizontalSpace = true;
		textgridData.verticalAlignment = GridData.CENTER;
		basePackageText = new Text(container, SWT.BORDER);
		basePackageText.setLayoutData(textgridData);
		basePackageText.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				basePackage = basePackageText.getText().trim();
				validatePackage();
				
			}
		});
		// 文本框布局样式
		textgridData = new GridData();

		label = new Label(container, SWT.NONE);
		label.setText("项目类型:");
		label.setLayoutData(textgridData);
		textgridData = new GridData();
		textgridData.horizontalAlignment = GridData.FILL;
		textgridData.grabExcessHorizontalSpace = true;
		textgridData.verticalAlignment = GridData.CENTER;
		projectTypeCombo = new Combo(container, SWT.BORDER);
		projectTypeCombo.setLayoutData(textgridData);
		projectTypeCombo.setItems(new String[]{"gradle","maven"});
		projectTypeCombo.select(0);
		projectTypeCombo.setEnabled(false);
		setControl(container);
		validatePackage();
	}
	/**
	 * 提示信息的输出窗口
	 * 
	 * @param message
	 */
	public void updateStatus(String message) {
		setMessage(message);
		setPageComplete(StringUtils.isEmpty(message));
	}


	/**
	 * 增加当为严重的错误信息时提示信息
	 */
	@Override
	public void setErrorMessage(String newMessage) {
		super.setErrorMessage(newMessage);

		if (newMessage != null) {
		} else if (newMessage == null) {
		}

		setPageComplete(newMessage == null);
	}
	/**
	 * 验证包
	 * @return
	 */
	public void validatePackage(){
		String errorMessage = null;
		if (basePackage == null ||basePackage.length() == 0) {
			errorMessage = "包路径不能为空，请输入 !";
		} else if (!ValidatorUtil.isValidModelName(basePackage)) {
			errorMessage = "包路径格式有误，请重新输入 !";
		} else if (!ValidatorUtil.valiPacakge(basePackage)) {
			errorMessage = "包路径不能包含大写英文、中文和特殊字符，请重新输入 !";
		} else if (KeyWordsChecker.doCheckJava(basePackage)) {
			errorMessage = "包路径不能是Java关键字，请重新输入 !";
		} else if (basePackage.endsWith(".")) {
			errorMessage = "包路径不能以“.”结尾，请重新输入 !";
		} 
		setErrorMessage(errorMessage);
		updateStatus(errorMessage);
	}
	
	@Override
	public IWizardPage getNextPage() {
		operation.setBasePacakageName(basePackageText.getText().trim());
		operation.setProjectType(projectTypeCombo.getText().trim());
		return super.getNextPage();
	}
	public String getBasePackage() {
		return basePackage;
	}

	public void setBasePackage(String basePackage) {
		this.basePackage = basePackage;
	}
	
	
}
