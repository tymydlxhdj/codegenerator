package com.mqfdy.code.designer.dialogs.sheetpage;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.ui.forms.widgets.ScrolledForm;

import com.mqfdy.code.designer.dialogs.BusinessClassEditorDialog;
import com.mqfdy.code.designer.dialogs.IBusinessClassEditorPage;

public class PersistPolicySheetPage extends Composite implements
		IBusinessClassEditorPage {
	private BusinessClassEditorDialog businessClassEditorDialog;

	public PersistPolicySheetPage(Composite parent, int style,
			BusinessClassEditorDialog businessClassEditorDialog) {
		super(parent, style);
		this.businessClassEditorDialog = businessClassEditorDialog;
		createContent();
	}

	public static final String SHEET_BASIC = "基本";
	public static final String SHEET_SENIOR = "自定义";

	/**
	 * 标签页对象
	 */
	private TabFolder tabFolder;

	/**
	 * 基本信息页
	 */
	private PersistPolicyBasicInfoPage basicInfoPage;

	/**
	 * 属性列表页
	 */
	// private PersistPolicySeniorPage seniorPage;

	// private ExpandableComposite ecBasic;

	// private ExpandableComposite ecSenior;

	private ScrolledForm form;

	private void createContent() {
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		this.setLayout(layout);

		// 创建表单对象
		// FormToolkit ft = new FormToolkit(this.getDisplay());
		// 通过表单工具对象创建可滚动的表单对象
		/*
		 * form = ft.createScrolledForm(this); //设置表单布局
		 * form.getBody().setLayout(new GridLayout(1,true));
		 * form.getBody().setLayoutData(new
		 * GridData(SWT.FILL,SWT.FILL,true,true,1,1)); form.setLayoutData(new
		 * GridData(SWT.FILL,SWT.FILL,true,true,1,1));
		 * form.setBackground(this.getBackground());
		 */

		// 创建可折叠的面板
		// ecBasic =
		// ft.createExpandableComposite(form.getBody(),ExpandableComposite.TWISTIE);
		// ecBasic.setText(SHEET_BASIC);
		// ecBasic.setBackground(this.getBackground());
		// ecBasic.setLayoutData(new
		// GridData(SWT.FILL,SWT.FILL,true,false,1,1));

		basicInfoPage = new PersistPolicyBasicInfoPage(this, SWT.NONE, this);
		basicInfoPage.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
				true, 1, 1));

		// seniorPage = new PersistPolicySeniorPage(ecSenior, SWT.NONE,this);

		// ecBasic.setClient(basicInfoPage);
		// ecBasic.setExpanded(true);
		// ecSenior.setClient(seniorPage);
		// ecSenior.setExpanded(false);

		addListeners();
	}

	private void addListeners() {
		// 为折叠面板添加展开 折叠的监听器
		/*
		 * ecBasic.addExpansionListener(new IExpansionListener(){
		 * 
		 * public void expansionStateChanged(ExpansionEvent event) {
		 * form.reflow(true); }
		 * 
		 * public void expansionStateChanging(ExpansionEvent event) { // TODO
		 * Auto-generated method stub
		 * 
		 * }
		 * 
		 * });
		 */
		/*
		 * ecSenior.addExpansionListener(new IExpansionListener(){
		 * 
		 * public void expansionStateChanged(ExpansionEvent event) {
		 * form.reflow(true); }
		 * 
		 * public void expansionStateChanging(ExpansionEvent event) { // TODO
		 * Auto-generated method stub
		 * 
		 * }
		 * 
		 * });
		 */
	}

	public void refresh() {
		// 刷新本页
		basicInfoPage.initControlValue();
	}

	/**
	 * 初始化创建 标签页
	 */
	private void createtabFolder(Composite composite) {
		tabFolder = new TabFolder(composite, SWT.BOTTOM);
		tabFolder.setLayoutData(new GridData(GridData.FILL_BOTH));
		for (int i = 0; i < 2; i++) {
			TabItem tabItem = new TabItem(tabFolder, SWT.NONE);
			tabItem.setText(getTabTitle(i));
		}
	}

	private String getTabTitle(int index) {
		switch (index) {
		case 0:
			return SHEET_BASIC;
		case 1:
			return SHEET_SENIOR;
		default:
			return "";
		}
	}

	public BusinessClassEditorDialog getBusinessClassEditorDialog() {
		return businessClassEditorDialog;
	}

	public void initControlValue() {
		// TODO Auto-generated method stub
		basicInfoPage.initControlValue();
		// seniorPage.initControlValue();
	}

	public boolean validateInput() {
		boolean b1 = basicInfoPage.validateInput();
		// boolean b2 = seniorPage.validateInput();
		boolean b2 = true;
		if (b1 && b2) {
			return true;
		} else {
			return false;
		}
	}

	public void updateTheEditingElement() {
		// TODO Auto-generated method stub
		basicInfoPage.updateTheEditingElement();
		// seniorPage.updateTheEditingElement();
	}

}