package com.mqfdy.code.wizard.wizardpages;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.eclipse.core.resources.IProject;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.ui.JavaElementLabelProvider;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.pde.internal.core.util.PDEJavaHelper;
import org.eclipse.pde.internal.ui.IHelpContextIds;
import org.eclipse.pde.internal.ui.PDEPlugin;
import org.eclipse.pde.internal.ui.parts.ConditionalListSelectionDialog;
import org.eclipse.pde.internal.ui.util.SWTUtil;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;

import com.mqfdy.code.designer.editor.actions.pages.GeneratorDiaolg;
import com.mqfdy.code.designer.editor.actions.pages.ParametersPage;
import com.mqfdy.code.generator.utils.TemplateUtil;
import com.mqfdy.code.reverse.DataSourceInfo;
import com.mqfdy.code.reverse.ReverseException;
import com.mqfdy.code.reverse.utils.ReverseUtil;
import com.mqfdy.code.utils.DBType;

// TODO: Auto-generated Javadoc
/**
 * 代码生成向导参数设置页面.
 *
 * @author mqfdy
 */
@SuppressWarnings("restriction")
public class MicroParametersPage extends ParametersPage {
	
	/** The wizard page. */
	private MicroGeneratorWizardPage wizardPage;
	

	private Combo templateCombo;

	protected String templateType;

	/**
	 * Instantiates a new micro parameters page.
	 *
	 * @param project
	 *            the project
	 * @param tabFolder
	 *            the tab folder
	 * @param style
	 *            the style
	 * @param generatorDiaolg
	 *            the generator diaolg
	 * @param fileName
	 *            the file name
	 */
	public MicroParametersPage(IProject project, TabFolder tabFolder, int style,
			GeneratorDiaolg generatorDiaolg, String fileName) {
		super(project, tabFolder, style, generatorDiaolg, fileName);
	}
	
	/**
	 * Instantiates a new micro parameters page.
	 *
	 * @param project
	 *            the project
	 * @param tabFolder
	 *            the tab folder
	 * @param style
	 *            the style
	 * @param wizardPage
	 *            the wizard page
	 * @param fileName
	 *            the file name
	 */
	public MicroParametersPage(IProject project, TabFolder tabFolder, int style,
			MicroGeneratorWizardPage wizardPage, String fileName) {
		super(project, tabFolder, style, fileName);
		this.wizardPage = wizardPage;
		try {
			dataSourceList = ReverseUtil.readMicroDataSourceList(project);
		} catch (ReverseException e) {
			//MessageDialog.openError(getShell(), "读取数据源错误", e.getMessage());
			e.printStackTrace();
			dataSourceList = new ArrayList<DataSourceInfo>();
		}
		createContents(this);
	}
	
	/**
	 * 重写创建内容.
	 *
	 * @author mqfdy
	 * @param composite
	 *            the composite
	 * @Date 2018-09-03 09:00
	 */
	@Override
	public void createContents(Composite composite) {
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		composite.setLayout(layout);
		GridData textgridData = new GridData();
		textgridData.horizontalAlignment = GridData.FILL;
		textgridData.grabExcessHorizontalSpace = true;
		textgridData.verticalAlignment = GridData.CENTER;

		GridData gridData = new GridData();
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalAlignment = GridData.FILL;
		gridData.verticalAlignment = GridData.FILL;
		Group location = new Group(composite, SWT.FILL);
		GridLayout layout1 = new GridLayout();
		layout1.numColumns = 3;
		location.setLayout(layout1);
		location.setLayoutData(gridData);
		location.setText("位置");
		Label dir = new Label(location, SWT.NONE);
		dir.setText("生成代码存放位置（根目录）：");
		src = new Text(location, SWT.BORDER | SWT.FILL | SWT.READ_ONLY);
		src.setText(curLocation.getName());
		src.setLayoutData(textgridData);
		Button selectButton = new Button(location, SWT.NONE);
		selectButton.setText("浏览...");

		selectButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				String path = getSelectPath();
				setCurLocation(path);
				if (curLocation != null) {
					src.setText(curLocation.getName());
				}
			}
		});
		Label lblPackage = new Label(location, SWT.NONE);
		lblPackage.setText("生成代码存放位置（包路径）：");
		final Text txtPackage = new Text(location, SWT.BORDER | SWT.FILL);
		txtPackage.setLayoutData(textgridData);
		txtPackage.setText(getBasePackage(curProject));
		wizardPage.setPackageName(txtPackage.getText().trim());
		txtPackage.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				wizardPage.setPackageName(txtPackage.getText().trim());
				wizardPage.validate();
			}
		});
		Button btnSelectPackage = new Button(location, SWT.NONE);
		btnSelectPackage.setText("选择...");
		btnSelectPackage.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				ILabelProvider labelProvider = new JavaElementLabelProvider();
				final ConditionalListSelectionDialog dialog = new ConditionalListSelectionDialog(PDEPlugin.getActiveWorkbenchShell(), labelProvider, "显示所有");
				Runnable runnable = new Runnable() {
			
					public void run() {
						ArrayList<IPackageFragment> elements = new ArrayList<>();
						ArrayList<IPackageFragment> conditional = new ArrayList<>();
						IPackageFragment[] fragments = PDEJavaHelper.getPackageFragments(JavaCore.create(curProject), new Vector<>(), true);
						for (int i = 0; i < fragments.length; i++) {
							try {
								if (fragments[i].containsJavaResources()) {
									elements.add(fragments[i]);
								} else {
									conditional.add(fragments[i]);
								}
							} catch (JavaModelException e) {
							}
						}
						dialog.setElements(elements.toArray());
						dialog.setConditionalElements(conditional.toArray());
						dialog.setMultipleSelection(true);
						dialog.setMessage("选择包");
						dialog.setTitle("选择生成代码包");
						dialog.create();
						PlatformUI.getWorkbench().getHelpSystem().setHelp(dialog.getShell(), IHelpContextIds.EXPORT_PACKAGES);
						SWTUtil.setDialogSize(dialog, 400, 500);
					}
				};
				BusyIndicator.showWhile(Display.getCurrent(), runnable);
				if (dialog.open() == Window.OK) {
					Object[] selected = dialog.getResult();
					if(selected != null && selected[0] instanceof IPackageFragment){				
						IPackageFragment packages = (IPackageFragment)selected[0];
						txtPackage.setText(packages.getElementName());
						wizardPage.setPackageName(packages.getElementName());
						wizardPage.validate();
					}
				}
				labelProvider.dispose();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		GridData grid = new GridData();
		grid.grabExcessHorizontalSpace = true;
		grid.grabExcessVerticalSpace = true;
		grid.horizontalAlignment = GridData.FILL;
		grid.verticalAlignment = GridData.FILL;
		GridLayout layout2 = new GridLayout();
		layout2.numColumns = 1;
		layout2.makeColumnsEqualWidth = true;
		
		Composite next = new Composite(composite, SWT.None);
		next.setLayout(layout2);
		next.setLayoutData(grid);
		/*Group content = new Group(next, SWT.FILL);
		content.setText("内容");
		content.setLayoutData(gridData);
		GridLayout layout3 = new GridLayout();
		layout3.numColumns = 2;
		content.setLayout(layout3);
		Label l1 = new Label(content, SWT.NONE);
		l1.setText("生成Java代码：");
		java = new Button(content, SWT.CHECK);
		java.addSelectionListener(new SelectionListener() {

			public void widgetSelected(SelectionEvent e) {
				javas = java.getSelection();
			}

			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub

			}
		});
		java.setSelection(true);
		Label l4 = new Label(content, SWT.NONE);
		l4.setText("生成配置文件：");
		p = new Button(content, SWT.CHECK);
		p.addSelectionListener(new SelectionListener() {

			public void widgetSelected(SelectionEvent e) {
				config = p.getSelection();
			}

			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub

			}
		});
		p.setSelection(true);
		Label l6 = new Label(content, SWT.NONE);
		l6.setText("遇到同名文件时：");
		se = new Combo(content, SWT.READ_ONLY);
		se.add("提示用户确认");
		se.add("直接覆盖");
		se.select(0);
		se.addSelectionListener(new SelectionListener() {

			public void widgetSelected(SelectionEvent e) {
				ses = se.getSelectionIndex();
			}

			public void widgetDefaultSelected(SelectionEvent e) {

			}
		});*/
		Group db = new Group(next, SWT.FILL);
		db.setText("数据库");
		
		GridData gridDataDb = new GridData();
		gridDataDb.grabExcessHorizontalSpace = true;
		// gridData.grabExcessVerticalSpace = true;
		// gridData.horizontalSpan = 3;
		gridDataDb.horizontalAlignment = GridData.FILL;
//		gridDataDb.verticalAlignment = GridData.FILL;
		GridLayout layoutDb = new GridLayout();
		layoutDb.numColumns = 2;
		
		
		db.setLayoutData(gridDataDb);
		db.setLayout(layoutDb);
		Label l7 = new Label(db, SWT.NONE);
		l7.setText("数据库类型：");
		GridData btGridData = new GridData();
		btGridData.horizontalAlignment = GridData.FILL;
		btGridData.grabExcessHorizontalSpace = true;
//		btGridData.verticalAlignment = GridData.CENTER;
		dbType = new Combo(db, SWT.READ_ONLY);
		dbType.setLayoutData(btGridData);
		
		List<DBType> list = DBType.getDBTypes();
		String[] types = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			types[i] = list.get(i).getDbType();
		}
		// String[] types = new
		// String[]{"Oracle","DB2","Sybase","MSSQL","MySQL","金仓","达梦"};
		dbType.setItems(types);
		dbType.select(0);
		dbTypes = dbType.getText();
		dbType.addSelectionListener(new SelectionListener() {

			public void widgetSelected(SelectionEvent e) {
				dbTypes = dbType.getText();
			}

			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub

			}
		});
		Label l3 = new Label(db, SWT.NONE);
		l3.setText("生成DDL：");
		ddl = new Button(db, SWT.CHECK);
		ddl.addSelectionListener(new SelectionListener() {

			public void widgetSelected(SelectionEvent e) {
				ddls = ddl.getSelection();
				if(!ddls){
					isGenFKddl.setSelection(false);
					isGenFKddls = false;
					isGenFKddl.setEnabled(false);
					isSyncDb.setSelection(false);
					isSyncDbs = false;
					isSyncDb.setEnabled(false);
					dbType.setEnabled(false);
					dbListCombo.setEnabled(false);
				}
				if(ddls){
					isGenFKddl.setEnabled(true);
					isSyncDb.setEnabled(true);
					dbType.setEnabled(true);
//					dbListCombo.setEnabled(true);
				}
			}

			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub

			}
		});
		ddl.setSelection(true);
		
		Label lfk = new Label(db, SWT.NONE);
		lfk.setText("外键生成DDL：");
		isGenFKddl = new Button(db, SWT.CHECK);
		isGenFKddl.addSelectionListener(new SelectionListener() {

			public void widgetSelected(SelectionEvent e) {
				isGenFKddls = isGenFKddl.getSelection();
			}

			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub

			}
		});
		isGenFKddl.setSelection(true);
		
		
		
		
		Label sync = new Label(db, SWT.NONE);
		sync.setText("同步到数据库：");
		
		isSyncDb = new Button(db, SWT.CHECK);
		isSyncDb.addSelectionListener(new SelectionListener() {

			public void widgetSelected(SelectionEvent e) {
				isSyncDbs = isSyncDb.getSelection();
				if(!isSyncDbs){
					dbType.setEnabled(true);
					dbListCombo.setEnabled(false);
				}
				if(isSyncDbs){
					if(wizardPage != null){
						wizardPage.setMessage("同步到数据库时，仅支持JDBC和DBCP两类数据源！",1);
					}else{
						generatorDiaolg.setMessage("同步到数据库时，仅支持JDBC和DBCP两类数据源！",1);
					}
					dbType.setEnabled(false);
					dbListCombo.setEnabled(true);
					if(dbListCombo.getItemCount() < 1){
						MessageDialog dia = new MessageDialog(getShell(), "提示", null, "请配置项目的数据源！", 2, new String[]{"确定"}, 0);
						dia.open();
						dbListCombo.setEnabled(false);
						isSyncDb.setSelection(false);
						isSyncDbs = false;
					}
					changeDbType();
				}
			}

			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub

			}
		});
		isSyncDb.setSelection(false);
		
		Label ds = new Label(db, SWT.NONE);
		ds.setText("数据源：");
		dbListCombo = new Combo(db, SWT.READ_ONLY);
		dbListCombo.setItems(getDataSourceComboContent());
		dbListCombo.setLayoutData(btGridData);
		dbListCombo.select(0);
		dataSourceName = dbListCombo.getText();
		String type = getDataSourceInfo() == null ?"":getDataSourceInfo().getDbType();
		int i = 0;
		for(String item : dbType.getItems()){
			if(item.equals(type))
				dbType.select(i);
			i++;
		}
		dbTypes = dbType.getText();
		dbListCombo.addSelectionListener(new SelectionListener() {
			
			public void widgetSelected(SelectionEvent e) {
				changeDbType();
			}
			
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		if(dbListCombo.getItemCount() < 1){
			isSyncDb.setSelection(false);
			isSyncDbs = false;
			dbListCombo.setEnabled(false);
		}
		dbListCombo.setEnabled(false);
		createTemplateControl(next,grid,layout2);
	}

	/**
	 * Creates the template control.
	 *
	 * @author Administrator
	 * @param next the next
	 * @param db the db
	 * @param layoutDb the layout db
	 * @param btGridData the bt grid data
	 * @param types the types
	 * @Date 2019-11-25 17:12:24
	 */
	public void createTemplateControl(Composite next,GridData grid,GridLayout layout2 ) {
		Group template = new Group(next, SWT.FILL);
		template.setText("代码模板");
		GridData gridDataTemplate = new GridData();
		gridDataTemplate.grabExcessHorizontalSpace = true;
		// gridData.grabExcessVerticalSpace = true;
		// gridData.horizontalSpan = 3;
		gridDataTemplate.horizontalAlignment = GridData.FILL;
//		gridDataDb.verticalAlignment = GridData.FILL;
		GridLayout layoutTemplate = new GridLayout();
		layoutTemplate.numColumns = 2;
		
		
		template.setLayoutData(gridDataTemplate);
		template.setLayout(layoutTemplate);
		Label txtTemplate = new Label(template, SWT.NONE);
		txtTemplate.setText("选择模板：");
		GridData templateGridData = new GridData();
		templateGridData.horizontalAlignment = GridData.FILL;
		templateGridData.grabExcessHorizontalSpace = true;
//		btGridData.verticalAlignment = GridData.CENTER;
		templateCombo = new Combo(template, SWT.READ_ONLY);
		templateCombo.setLayoutData(templateGridData);
		
		List<String> listTemp = TemplateUtil.getTemplateTypes();
		String[] typeTemps = new String[listTemp.size()];
		for (int i = 0; i < listTemp.size(); i++) {
			typeTemps[i] = listTemp.get(i);
		}
		templateCombo.setItems(typeTemps);
		templateCombo.select(0);
		templateCombo.addSelectionListener(new SelectionListener() {

			public void widgetSelected(SelectionEvent e) {
				templateType = templateCombo.getText();
			}

			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub

			}
		});
	}
	/**
	 * 获取基础包名.
	 *
	 * @author mqfdy
	 * @param curProject
	 *            the cur project
	 * @return the base package
	 * @Date 2018-09-03 09:00
	 */
	private String getBasePackage(IProject curProject) {
		IPackageFragment[] fragments = PDEJavaHelper.getPackageFragments(JavaCore.create(curProject), new Vector<>(), true);
		if(fragments != null && fragments.length > 0){
			for (int i = 0; i < fragments.length; i++) {
				try {
					if (fragments[i].containsJavaResources()) {
						IJavaElement[] jeArray = fragments[i].getChildren();
						if(jeArray != null && jeArray.length > 0){
							for(int j = 0; j < jeArray.length; j++){
								if("Application.java".equals(jeArray[j].getElementName())){
									return fragments[i].getElementName();
								}
							}
						}
					}
				} catch (JavaModelException e) {
					e.printStackTrace();
				}
			}
		}
		return wizardPage.getBusinessObjectModel().getNameSpace();
	}	
	public String getTemplateType() {
		return templateType;
	}

	public void setTemplateType(String templateType) {
		this.templateType = templateType;
	}	
}
