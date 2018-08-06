package com.mqfdy.code.wizard.wizardpages;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

import com.mqfdy.code.designer.editor.actions.pages.GeneratorObjectSelectPage;
import com.mqfdy.code.designer.editor.actions.pages.ParametersPage;
import com.mqfdy.code.designer.models.BusinessModelManager;
import com.mqfdy.code.model.Association;
import com.mqfdy.code.model.BusinessClass;
import com.mqfdy.code.model.BusinessObjectModel;
import com.mqfdy.code.model.Inheritance;
import com.mqfdy.code.model.ReferenceObject;
import com.mqfdy.code.resource.validator.KeyWordsChecker;
import com.mqfdy.code.resource.validator.ValidatorUtil;
import com.mqfdy.code.reverse.DataSourceInfo;
import com.mqfdy.code.reverse.utils.ReverseUtil;

public class MicroGeneratorWizardPage extends WizardPage {
	
	/**
	 * 标签页对象
	 */
	private TabFolder tabFolder;
	private GeneratorObjectSelectPage osPage;
	private MicroParametersPage pPage;
	private BusinessObjectModel businessObjectModel;
	private IProject project;
	private List<DataSourceInfo> dataSourceList;//数据库连接
	
	private String packageName;


	public MicroGeneratorWizardPage(String pageName,IProject project, BusinessObjectModel businessObjectModel) {
		super(pageName);
		this.project = project;
		this.businessObjectModel = businessObjectModel;
	}

	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		// 初始化窗口
		GridLayout gridLayout = new GridLayout(1, true);
		container.setLayout(gridLayout);
		setTitle("基于业务对象模型生成代码");
		setMessage("请选择要生成代码的业务对象，并设置代码生成参数", 1);
		// 创建标签页对象
		tabFolder = new TabFolder(container, SWT.NONE);
		tabFolder.setLayoutData(new GridData(GridData.FILL_BOTH));
		TabItem tabItem = new TabItem(tabFolder, SWT.NONE);
		tabItem.setText("参数设置");
		TabItem tabItem2 = new TabItem(tabFolder, SWT.NONE);
		tabItem2.setText("对象选择");
		// tab 内容
		osPage = new GeneratorObjectSelectPage(businessObjectModel, tabFolder,
				SWT.NONE);
		pPage = new MicroParametersPage(project, tabFolder, SWT.NONE, this,
				businessObjectModel.getId());
		tabFolder.getItem(0).setControl(pPage);
		tabFolder.getItem(1).setControl(osPage);
		setControl(container);
	}
	
	/**
	 * 执行DDL中的SQL脚本
	 * @author mqfdy
	 * @return List<String> 执行的语句
	 */
	public List<Map<String, String>> executeDDL() {
		List<Map<String, String>> excuteDDLSentences = new ArrayList<Map<String, String>>();
		MessageDialog d = new MessageDialog(getShell(), "提示", null,
				"您选了将建表DDL同步到数据库，该操作将删除数据库中同名的表，确认要同步吗？", 0, new String[] { "确定", "取消" }, 0);
		if (d.open() != TitleAreaDialog.OK) 
			return excuteDDLSentences;
		Connection conn = null;
		//待解析的DDL中的字符
		String ddlString = "";
		//创建连接 
		try {
			conn = ReverseUtil.createConnection(getpPage().getDataSourceInfo());
		} catch (Exception e) {
			String message = "连接数据源 " + getpPage().getDataSourceInfo().getDataSourceName() + " 失败:\n" + e.getMessage();
			MessageDialog.openError(getShell(), "同步到数据库错误", message);
			throw new RuntimeException(e);
		}
		
		String ddlName = businessObjectModel.getFullName() + "_" + getpPage().getDbType() + ".DDL";
		String ddlPath = getpPage().getCurPath() + "ddl" + File.separator + ddlName;
		File file = new File(ddlPath);
		BufferedReader reader = null;
		
		StringBuffer stringBuffer = new StringBuffer();
		FileReader fr = null;
		try {
			fr = new FileReader(file);
			reader = new BufferedReader(fr);
			String str = "";
			//读取DDL中的字符
			while((str = reader.readLine()) != null) {
				stringBuffer.append(str);
			}
			ddlString = stringBuffer.toString();
		} catch (FileNotFoundException e) {
			MessageDialog.openError(getShell(), "同步到数据库错误\n", e.getMessage());
			throw new RuntimeException(e);
		} catch (IOException e) {
			MessageDialog.openError(getShell(), "同步到数据库错误\n", e.getMessage());
			throw new RuntimeException(e);
		} finally {
			if(fr != null){
				try {
					fr.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(reader != null){
				try {
					reader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		//用";"区分出多条sql脚本.
		String[] ddlSentences = ddlString.split(";");
		
		PreparedStatement ps = null;
		//逐句执行sql脚本
		
		Map<String, String> map = null;
		for(String ddlSentence: ddlSentences) {
			map = new HashMap<String, String>();
			
			if(ddlSentence==null || "".equals(ddlSentence.trim()) ){
				continue;
			}
			
			try {
				ps = conn.prepareStatement(ddlSentence);
				ps.executeUpdate();
				
				map.put("ddlSentence", "执行脚本 '" + ddlSentence + "' 成功");
				map.put("isSuccess", "true");
				excuteDDLSentences.add(map);
			} catch (SQLException sqlException) {
				if(sqlException.getCause() != null) {
					map.put("ddlSentence", "执行脚本 '" + ddlSentence + "' 失败: " + sqlException.getCause().getMessage());
					map.put("isSuccess", "false");
					excuteDDLSentences.add(map);
				} else {
					map.put("ddlSentence", "执行脚本 '" + ddlSentence + "' 失败: " + sqlException.getMessage());
					map.put("isSuccess", "false");
					excuteDDLSentences.add(map);
				}
			}finally{
				try {
					if(ps!=null){
						ps.clearParameters();
						ps.close();
					}
				} catch (SQLException e) {
					map.put("ddlSentence", "关闭prepareStatement失败");
					map.put("isSuccess", "false");
					excuteDDLSentences.add(map);
				}
			}
		}
		
		return excuteDDLSentences;
	}
	
	@SuppressWarnings("static-access")
	public boolean validatePackage(){
		String errorMessage = null;
		if (packageName.length() == 0) {
			errorMessage = "包路径不能为空，请输入 !";
		} else if (!ValidatorUtil.isValidModelName(packageName)) {
			errorMessage = "包路径格式有误，请重新输入 !";
		} else if (!ValidatorUtil.valiPacakge(packageName)) {
			errorMessage = "包路径不能包含大写英文、中文和特殊字符，请重新输入 !";
		} else if (new KeyWordsChecker().doCheckJava(packageName)) {
			errorMessage = "包路径不能是Java关键字，请重新输入 !";
		} 
		setErrorMessage(errorMessage);
		return errorMessage != null;
	}
	
	public boolean validate(){
		if(validatePackage()){
			setPageComplete(false);
			return false;
		}
		// 验证选择项
		BusinessObjectModel model = this.osPage.getBusinessObjectModel();
		List<BusinessClass> buList = model.getBusinessClasses();
		List<ReferenceObject> refList = model.getReferenceObjects();
		List<String> refIdList = new ArrayList<String>();
		for (ReferenceObject re : refList) {
			refIdList.add(re.getReferenceObjectId());
		}
		List<BusinessClass> bcs = BusinessModelManager.getBuildInOm()
				.getBusinessClasses();
		buList.addAll(bcs);

		for (Association as : model.getAssociations()) {
			if (as.getClassA() == null || as.getClassB() == null) {
				this.setMessage("关联关系" + as.getName() + "缺少对应的业务实体！", 3);
				setPageComplete(false);
				return false;
			} else if (!((buList.contains(as.getClassA()) || refIdList
					.contains(as.getClassA().getId()))
					&& (buList.contains(as.getClassB())) || refIdList
						.contains(as.getClassB().getId()))) {
				this.setMessage("请选择" + as.getName() + "对应的业务实体！", 3);
				// this.setMessage("请选择"+as.getName()+"对应的业务实体（"+as.getClassA().getName()+"和"+as.getClassB().getName()+"）！",3);
				setPageComplete(false);
				return false;
			}
		}
		for (Inheritance in : model.getInheritances()) {
			if (!(buList.contains(in.getChildClass()) && buList.contains(in
					.getParentClass()))) {
				this.setMessage("请选择" + in.getName() + "对应的业务实体！", 3);
				// this.setMessage("请选择"+in.getName()+"对应的业务实体（"+in.getChildClass().getName()+"和"+in.getParentClass().getName()+"）！",3);
				setPageComplete(false);
				return false;
			}
		}
		String dbType = getpPage().getDbType();
		boolean isSyncDbs = getpPage().isSyncDbs();
		DataSourceInfo dataSourceInfo = getpPage().getDataSourceInfo();
		
		if(isSyncDbs && dataSourceInfo != null){
			if(!dataSourceInfo.getDbType().equals(dbType)){
				this.setMessage("数据库类型与数据源不匹配！", 3);
				setPageComplete(false);
				return false;
			}
		}
		setPageComplete(true);
		return true;
	}
	

	public BusinessObjectModel getBusinessObjectModel() {
		return businessObjectModel;
	}

	public void setBusinessObjectModel(BusinessObjectModel businessObjectModel) {
		this.businessObjectModel = businessObjectModel;
	}

	public IProject getProject() {
		return project;
	}

	public void setProject(IProject project) {
		this.project = project;
	}

	public List<DataSourceInfo> getDataSourceList() {
		return dataSourceList;
	}

	public void setDataSourceList(List<DataSourceInfo> dataSourceList) {
		this.dataSourceList = dataSourceList;
	}

	public TabFolder getTabFolder() {
		return tabFolder;
	}

	public void setTabFolder(TabFolder tabFolder) {
		this.tabFolder = tabFolder;
	}

	public GeneratorObjectSelectPage getOsPage() {
		return osPage;
	}

	public void setOsPage(GeneratorObjectSelectPage osPage) {
		this.osPage = osPage;
	}

	public MicroParametersPage getpPage() {
		return pPage;
	}
	
	public ParametersPage getParametersPage(){
		return pPage;
	}

	public void setpPage(MicroParametersPage pPage) {
		this.pPage = pPage;
	}
	public GeneratorObjectSelectPage getObjectSelectPage() {
		return osPage;
	}
	@Override
	public boolean canFlipToNextPage() {
		return false;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map getParsMap(){
		Map map = new HashMap();
		map.put("generateHbm", pPage.getHbm());
		map.put("generatePO", pPage.getJava());
		map.put("dbType", pPage.getDbType());
		map.put("ddl", pPage.getDdl());
		map.put("generateFk", pPage.getGenFKDdl());
		map.put("isSyncDbs", pPage.isSyncDbs());
		map.put("dataSourceInfo", pPage.getDataSourceInfo());
		map.put("generateConfig", pPage.getConfig());
		map.put("testCode", pPage.getTestCode());
		map.put("isOverride",pPage.getSe() == 0 ? false: true);
		return map;
	}
	/**
	 * 0代表“提示用户确认”，2代表“直接覆盖”
	 * @return
	 */
	public int getOption() {
		if(pPage.getSe() > 0){
			return 2;
		}
		return 0;
	}
	/**
	 * 获取输出目录路径
	 * @return
	 */
	public String getOutputFolderPath(){
		return pPage.getCurPath();
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
		businessObjectModel.setNameSpace(packageName);
	}

}
