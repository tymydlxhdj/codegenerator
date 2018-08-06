package com.mqfdy.code.designer.preferences;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.mqfdy.code.designer.editor.BusinessModelEditorPlugin;
import com.mqfdy.code.resource.validator.ValidatorUtil;

/**
 * 模型驱动PreferencePage
 * 
 * @author mqfdy
 * 
 */
public class ModelPreferencePage extends PreferencePage implements
		IWorkbenchPreferencePage {
	public static String CONSOLE_PRINT = "console.print";
	public static String ISADD_CUSTOMOPERATION = "customoperation.add";
	public static String ISADD_BEFOREONREVERSE = "isbeforeonreverse.add";
	public static String ISADD_AFTERONREVERSE = "isafteronreverse.add";
	
	public static String TABLENAMEPREFIX = "tablenameprefix";
	public static String ISPRONAMEUPPERCASE = "ispronameuppercase";
	public static String ISTABLENAMEUPPERCASE = "istablenameuppercase";
	
	public static String SHAREMODELIP = "sharemodelip";
	public static String SHAREMODELPORT = "sharemodelport";
	
	
	public static String PRINTALL = "all";
	public static String PRINTONLYERROR = "onlyError";
	Button addOper;// 业务实体新建时默认添加基本操作
	Text tableNamePrefixText;// 表名前缀
	Button tableNameUpperCase;// 表名默认大写
	Button proNameUpperCase;// 属性名默认大写_
	Button isPrintValiMsg;// 是否输出模型校验信息
	Button add_before;// 是关键字时,在前边加_
	Button add_after;// 是关键字时,在后边加_
	
	Label ipLabel;// ip
	Text ipText;// ip输入框
	
	Label portLabel;// 端口
	Text portText;// 端口输入框

	public ModelPreferencePage() {
	}

	public ModelPreferencePage(String title) {
		super(title);
	}

	public ModelPreferencePage(String title, ImageDescriptor image) {
		super(title, image);
	}

	public void init(IWorkbench workbench) {
		// IPreferenceStore
		// store=BusinessModelEditorPlugin.getDefault().getPreferenceStore();
		// String pkgs = store.getString("PkgName");
		// store.getBoolean(pkgs);
	}

	public void initContents() {
		IPreferenceStore store = BusinessModelEditorPlugin.getDefault()
				.getPreferenceStore();
		// String print = store.getString(CONSOLE_PRINT);
//		String isAdd = store.getString(ISADD_CUSTOMOPERATION);
		// if(print.equals(PRINTONLYERROR))
		// modelVali_onlyError.setSelection(true);
		// else
		// modelVali_all.setSelection(true);
		boolean isAddOper = store.getBoolean(ISADD_CUSTOMOPERATION);
		if(isAddOper){
			addOper.setSelection(true);
		}
		
		if (/* print.equals("")|| */store.getBoolean(CONSOLE_PRINT))
			isPrintValiMsg.setSelection(true);
		if (/* print.equals("")|| */store.getBoolean(ISADD_AFTERONREVERSE))
			add_after.setSelection(true);
		if (/* print.equals("")|| */store.getBoolean(ISADD_BEFOREONREVERSE))
			add_before.setSelection(true);
		if(!store.getBoolean(ISADD_AFTERONREVERSE) && !store.getBoolean(ISADD_BEFOREONREVERSE))
			add_before.setSelection(true);
		if (/*isAdd.equals("") || */store.getBoolean(ISTABLENAMEUPPERCASE))
			tableNameUpperCase.setSelection(true);
		if (/* print.equals("")|| */store.getBoolean(ISPRONAMEUPPERCASE))
			proNameUpperCase.setSelection(true);
		tableNamePrefixText.setText(store.getString(TABLENAMEPREFIX));
		ipText.setText(store.getString(SHAREMODELIP));
		portText.setText(store.getString(SHAREMODELPORT));
	}

	@Override
	protected Control createContents(Composite parent) {
		
		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		layout.numColumns = 1;
		composite.setLayout(layout);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1,
				1));

		isPrintValiMsg = new Button(composite, SWT.CHECK);
		isPrintValiMsg.setText("在控制台输出校验信息");
		addOper = new Button(composite, SWT.CHECK);
		addOper.setText("创建业务实体时,自动创建标准操作");
		GridData ngd4 = new GridData();
		ngd4.horizontalSpan = 2;
		addOper.setLayoutData(ngd4);
		new Label(composite,SWT.NONE);
		Group newg = new Group(composite, SWT.NONE);
		GridLayout ngl = new GridLayout(2,true);
		newg.setLayout(ngl);
		GridData ngd = new GridData(SWT.FILL, SWT.FILL, true, false, 1,1);
		newg.setLayoutData(ngd);
		newg.setText("对象-数据库映射规则配置");
		
		GridData ngd2 = new GridData();
		ngd2.horizontalSpan = 2;
		tableNameUpperCase = new Button(newg, SWT.CHECK);
		tableNameUpperCase.setText("数据库表名默认大写");
		tableNameUpperCase.setLayoutData(ngd2);
//		new Label(newg,SWT.NONE);
		
		proNameUpperCase = new Button(newg, SWT.CHECK);
		proNameUpperCase.setText("数据库字段名默认大写");
		GridData ngd3 = new GridData();
		ngd3.horizontalSpan = 2;
		proNameUpperCase.setLayoutData(ngd3);
//		new Label(newg,SWT.NONE);
		Label l = new Label(newg,SWT.NONE);
		l.setText("数据库表名添加前缀：");
		tableNamePrefixText = new Text(newg, SWT.BORDER);
		GridData ngd1 = new GridData(SWT.FILL, SWT.FILL, false, false, 1,1);
		ngd1.horizontalAlignment = SWT.FILL;
		tableNamePrefixText.setLayoutData(ngd1);
		
		tableNamePrefixText.addKeyListener(new KeyListener(){

			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				if(!"".equals(tableNamePrefixText.getText()) && (tableNamePrefixText.getText().length() > 30 || !ValidatorUtil.valiFirstNo_Name(tableNamePrefixText.getText()))){
					setErrorMessage("表名前缀格式错误");
					return;
				}
				else{
					setErrorMessage(null);
				}
			}
			
			
		});		
		
		
//		Composite tp = new Composite(newg, SWT.NONE);
//		GridData ngd2 = new GridData(SWT.FILL, SWT.FILL, true, false, 1,1);
//		ngd2.horizontalSpan = 2;
//		ngd2.horizontalIndent = 0;
//		tp.setLayoutData(ngd2);
//		GridLayout ngl1 = new GridLayout(2,true);
//		tp.setLayout(ngl1);
		
		
		new Label(composite,SWT.NONE);
		
		Group reverse = new Group(composite, SWT.NONE);
		GridLayout gl = new GridLayout(2,true);
		reverse.setLayout(gl);
		GridData gd = new GridData(SWT.FILL, SWT.FILL, true, false, 1,1);
		reverse.setLayoutData(gd);
		reverse.setText("数据库反向到OM转换配置");
		add_before = new Button(reverse, SWT.RADIO);
//		遇到关键字时
		add_before.setText("是java关键字时,在前边加\"_\"");
		add_after = new Button(reverse, SWT.RADIO);
		add_after.setText("是java关键字时,在后边加\"_\"");
		
		Group group = new Group(composite, SWT.NONE);
		group.setText("共享模型库连接信息配置");
		GridLayout gl2 = new GridLayout(2,true);
		group.setLayout(gl2);
		GridData gd2 = new GridData(SWT.FILL, SWT.FILL, true, false, 1,1);
		group.setLayoutData(gd2);
		
		ipLabel=new Label(group,SWT.NONE);		
		ipLabel.setText("IP：");
		
		ipText=new Text(group,SWT.BORDER);
		gd2 = new GridData(GridData.FILL_HORIZONTAL);
		gd2.horizontalAlignment = SWT.FILL;
		ipText.setLayoutData(gd2);
		
		portLabel=new Label(group,SWT.NONE);		
		portLabel.setText("端口：");
		
		portText=new Text(group,SWT.BORDER);
		gd2 = new GridData(GridData.FILL_HORIZONTAL);
		gd2.horizontalAlignment = SWT.FILL;
		portText.setLayoutData(gd2);
		
		
		initContents();
//		performDefaults();
		return parent;
	}

	@Override
	protected void performApply() {
		IPreferenceStore store = BusinessModelEditorPlugin.getDefault()
				.getPreferenceStore();
		setErrorMessage(null);
		if(!"".equals(tableNamePrefixText.getText()) && (tableNamePrefixText.getText().length() > 30 || !ValidatorUtil.valiFirstNo_Name(tableNamePrefixText.getText()))){
			setErrorMessage("表名前缀格式错误");
			return;
		}
		if(!"".equals(ipText.getText())&&ipText.getText()!=null){
			if(!ValidatorUtil.validateIP(ipText.getText())){
				setErrorMessage("IP格式不正确");
				return;
			}
			else{
				store.setValue(SHAREMODELIP, ipText.getText().trim());
			}
		}
		if(!"".equals(portText.getText())&&portText.getText()!=null){
			if(!ValidatorUtil.validatePort(portText.getText())){
				setErrorMessage("端口格式不正确");
				return;
			}
			else{
				store.setValue(SHAREMODELPORT, portText.getText().trim());
			}
		}

		store.setValue(CONSOLE_PRINT, isPrintValiMsg.getSelection() + "");
		store.setValue(ISADD_CUSTOMOPERATION, addOper.getSelection() + "");
		store.setValue(ISADD_BEFOREONREVERSE, add_before.getSelection() + "");
		store.setValue(ISADD_AFTERONREVERSE, add_after.getSelection() + "");
		
		store.setValue(ISTABLENAMEUPPERCASE, tableNameUpperCase.getSelection() + "");
		store.setValue(ISPRONAMEUPPERCASE, proNameUpperCase.getSelection() + "");
		store.setValue(TABLENAMEPREFIX, tableNamePrefixText.getText());
		BusinessModelEditorPlugin.getDefault().savePluginPreferences();
	}

	@Override
	protected void performDefaults() {
		addOper.setSelection(true);
		isPrintValiMsg.setSelection(false);
		add_before.setSelection(true);
		add_after.setSelection(false);
		tableNameUpperCase.setSelection(true);
		proNameUpperCase.setSelection(true);
		tableNamePrefixText.setText("");
		ipText.setText("");
		portText.setText("");
		// modelVali_all.setSelection(true);
		// modelVali_onlyError.setSelection(false);
		setErrorMessage(null);
		super.performDefaults();
	}

	@Override
	public boolean performOk() {
		setErrorMessage(null);
		if(!"".equals(tableNamePrefixText.getText()) && (tableNamePrefixText.getText().length() > 30 || !ValidatorUtil.valiName(tableNamePrefixText.getText()))){
			setErrorMessage("表名前缀格式错误");
			return false;
		}
		if(!"".equals(ipText.getText())&&ipText.getText()!=null){
			if(!ValidatorUtil.validateIP(ipText.getText())){
				setErrorMessage("IP格式不正确");
				return false;
			}
		}
		if(!"".equals(portText.getText())&&portText.getText()!=null){
			if(!ValidatorUtil.validatePort(portText.getText())){
				setErrorMessage("端口格式不正确");
				return false;
			}
		}
		performApply();
		return super.performOk();
	}

}
