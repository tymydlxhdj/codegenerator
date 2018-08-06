package com.mqfdy.code.designer.editor.actions.pages;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.internal.resources.Folder;
import org.eclipse.core.resources.IProject;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.internal.core.PackageFragmentRoot;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;

import com.mqfdy.code.model.utils.DBType;
import com.mqfdy.code.reverse.DataSourceInfo;

/**
 * 代码生成向导参数设置页面
 * 
 * @author mqfdy
 * 
 */
public class ParametersPage extends Composite {
	protected GeneratorDiaolg generatorDiaolg;
	protected Text src;
	protected Folder curLocation;// 路径

	protected IProject curProject;

	// private Text pkgName;//包名
	protected Combo dbType;// 数据库类型

	protected Button java;// 生成Java文件
	protected Button hbm;// 生成HBM文件
	protected Button ddl;// 生成DDL
	protected Button isGenFKddl;// 生成外键的DDL
	protected Button p;// 生成配置文件
	protected Button v;// 生成前校验模型
	protected Button test;// 生成测试代码
	protected Combo se;// 遇到同名文件时提示或覆盖
	protected String dbTypes;// 数据库类型
	protected boolean javas = true;// 生成Java文件
	protected boolean hbms = true;// 生成HBM文件
	protected boolean ddls = true;// 生成DDL
	protected boolean isGenFKddls = true;// 生成DDL
	protected boolean config = true;// 生成配置文件
	protected boolean vs = true;// 生成前校验模型
	protected boolean testCode = false;// 生成测试代码
	protected int ses;// 遇到同名文件时提示或覆盖
	protected List<DataSourceInfo> dataSourceList;//数据库连接
	protected String modelId;
	protected boolean isSyncDbs = false;//是否同步到数据库
	protected Button isSyncDb;//是否同步到数据库
	protected Combo dbListCombo;//数据库连接下拉框
	protected String dataSourceName;//数据库连接下拉框
	
	public ParametersPage(IProject project, TabFolder tabFolder, int style,String fileName) {
		super(tabFolder, style);
		this.modelId = fileName;
		
		// IPath path = IProject project;//new Path(project);
		// IResource res =
		// ResourcesPlugin.getWorkspace().getRoot().findFilesForLocation(path)[0];
		// IFile f = new File(path, null);
		curProject = project;// res.getProject();
		setCurLocation(null);
	}

	public ParametersPage(IProject project, TabFolder tabFolder, int style,
			GeneratorDiaolg generatorDiaolg, String fileName) {
		super(tabFolder, style);
		this.generatorDiaolg = generatorDiaolg;
		this.modelId = fileName;
		this.dataSourceList = generatorDiaolg.getDataSourcesList();
		
		// IPath path = IProject project;//new Path(project);
		// IResource res =
		// ResourcesPlugin.getWorkspace().getRoot().findFilesForLocation(path)[0];
		// IFile f = new File(path, null);
		curProject = project;// res.getProject();
		setCurLocation(null);
		createContents(this);
	}
	
	protected void setCurLocation(String path){
		if(curProject != null){
			if(path ==null || path.equals("")){
				List<String> paths = getSrcPathList(curProject);
				if(paths!=null && paths.size()>0){
					curLocation = (Folder) curProject.getFolder(paths.get(0));
				}else{
					curLocation = (Folder) curProject.getFolder("src");
				}
			}else{
				curLocation = (Folder) curProject.getFolder(path);
			}
		}
	}
	

	
	/**
	 * 获取SRC路径列表
	 * @param project
	 * @return
	 */
	protected List<String> getSrcPathList(IProject project){
		List<String> pathList = new ArrayList();
	    IJavaProject javaProject = JavaCore.create(project);
	    String projectName = project.getName();
	    try {
            IPackageFragmentRoot[] packageFragmentRoot = javaProject.getPackageFragmentRoots();
            for (int i = 0; i < packageFragmentRoot.length; i++){
                if (packageFragmentRoot[i] instanceof PackageFragmentRoot && packageFragmentRoot[i].getKind() == IPackageFragmentRoot.K_SOURCE){
                	String srcPath = packageFragmentRoot[i].getPath().toOSString();
                	srcPath = srcPath.substring(projectName.length()+2);
                	pathList.add(srcPath);
                }
            }
        } catch (JavaModelException e) {
            e.printStackTrace();
        }
		return pathList;
	}

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

		GridData grid = new GridData();
		grid.grabExcessHorizontalSpace = true;
		grid.grabExcessVerticalSpace = true;
		grid.horizontalAlignment = GridData.FILL;
		grid.verticalAlignment = GridData.FILL;
		GridLayout layout2 = new GridLayout();
		layout2.numColumns = 2;
		layout2.makeColumnsEqualWidth = true;
		
		Composite next = new Composite(composite, SWT.None);
		next.setLayout(layout2);
		next.setLayoutData(grid);
		Group content = new Group(next, SWT.FILL);
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
				if(!javas){
					test.setSelection(false);
					testCode = false;
				}
			}

			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub

			}
		});
		java.setSelection(true);
		Label l2 = new Label(content, SWT.NONE);
		l2.setText("生成HBM文件：");
		hbm = new Button(content, SWT.CHECK);
		hbm.addSelectionListener(new SelectionListener() {

			public void widgetSelected(SelectionEvent e) {
				hbms = hbm.getSelection();
			}

			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub

			}
		});
		hbm.setSelection(true);
		
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
		Label l5 = new Label(content, SWT.NONE);
		l5.setText("生成前校验模型");
		v = new Button(content, SWT.CHECK);
		v.addSelectionListener(new SelectionListener() {

			public void widgetSelected(SelectionEvent e) {
				vs = v.getSelection();
			}

			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub

			}
		});
		v.setSelection(true);
		
		Label testLabel = new Label(content, SWT.NONE);
		testLabel.setText("生成测试代码");
		test = new Button(content, SWT.CHECK);
		test.addSelectionListener(new SelectionListener() {

			public void widgetSelected(SelectionEvent e) {
				testCode = test.getSelection();
				if(testCode){
					java.setSelection(true);
					javas=true;
				}
			}

			public void widgetDefaultSelected(SelectionEvent e) {

			}
		});
		test.setSelection(false);
		
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
				// TODO Auto-generated method stub

			}
		});
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
					generatorDiaolg.setMessage("同步到数据库时，仅支持JDBC和DBCP两类数据源！",1);
					dbType.setEnabled(false);
					dbListCombo.setEnabled(true);
					if(dbListCombo.getItemCount() < 1){
						MessageDialog dia = new MessageDialog(getShell(), "提示", null, "请配置UAP项目的数据源！", 2, new String[]{"确定"}, 0);
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
	}
	
	
	protected void changeDbType(){
		dataSourceName = dbListCombo.getText();
		String type = getDataSourceInfo() == null ?"":getDataSourceInfo().getDbType();
		int i = 0;
		for(String item : dbType.getItems()){
			if(item.equals(type))
				dbType.select(i);
			i++;
		}
		dbTypes = dbType.getText();
	}
	/**
	 * 获取数据源名称数组
	 * @return
	 */
	protected String[] getDataSourceComboContent(){
		String[] items = new String[dataSourceList.size()];
		int i = 0;
		for(DataSourceInfo dsi : dataSourceList){
			items[i] = dsi.getDataSourceName() + "@" + dsi.getUapName();
			i ++;
		}
		return items;
	}
	/**
	 * 根据数据源名称获取数据源
	 * @return
	 */
	public DataSourceInfo getDataSourceInfo(){
		for(DataSourceInfo dsi : dataSourceList){
			if((dsi.getDataSourceName() + "@" + dsi.getUapName()).equals(dataSourceName))
				return dsi;
		}
		return null;
	}
	/**
	 * 取得位置
	 */
	public String getSelectPath() {
		Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
				.getShell();// 取得当前的shell
		SrcSelectionDiaolg dialog = new SrcSelectionDiaolg(shell,getSrcPathList(curProject));
		if (dialog.open() == Window.OK) {
			if (dialog.getResult() instanceof String) {
				return dialog.getResult();
			}
		}
		return null;
	}

	// public String getPkgName() {
	// return pkgNames;
	// }
	public String getDbType() {
		return dbTypes;
	}

	public boolean getJava() {
		return javas;
	}

	public boolean getHbm() {
		return hbms;
	}

	public boolean getDdl() {
		return ddls;
	}
	
	public boolean getGenFKDdl() {
		return isGenFKddls;
	}

	public boolean getConfig() {
		return config;
	}

	public boolean getV() {
		return vs;
	}
	
	public boolean getTestCode() {
		return testCode;
	}

	public int getSe() {
		return ses;
	}
	public IProject getCurProject() {
		return curProject;
	}
	public void setCurProject(IProject curProject) {
		this.curProject = curProject;
	}

	public String getCurPath() {
		String srcPath = curLocation.getLocation().toOSString();
		// String pkgPath = getPkgName();

		return srcPath + File.separator;// +pkgPath;
	}

	public boolean isSyncDbs() {
		return isSyncDbs;
	}

	public void setSyncDbs(boolean isSyncDbs) {
		this.isSyncDbs = isSyncDbs;
	}
	
}
