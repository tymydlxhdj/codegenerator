package com.mqfdy.code.designer.editor.actions.pages;

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
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
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

import com.mqfdy.code.designer.models.BusinessModelManager;
import com.mqfdy.code.designer.models.ImageKeys;
import com.mqfdy.code.designer.models.ImageManager;
import com.mqfdy.code.model.Association;
import com.mqfdy.code.model.BusinessClass;
import com.mqfdy.code.model.BusinessObjectModel;
import com.mqfdy.code.model.Inheritance;
import com.mqfdy.code.model.ReferenceObject;
import com.mqfdy.code.reverse.DataSourceInfo;
import com.mqfdy.code.reverse.ReverseException;
import com.mqfdy.code.reverse.utils.ReverseUtil;

// TODO: Auto-generated Javadoc
/**
 * 代码生成对话框.
 *
 * @author mqfdy
 */
public class GeneratorDiaolg extends TitleAreaDialog {
	
	/** 标签页对象. */
	private TabFolder tabFolder;
	
	/** The os page. */
	private GeneratorObjectSelectPage osPage;
	
	/** The p page. */
	private ParametersPage pPage;
	
	/** The ok button. */
	private Button okButton;
	
	/** The business object model. */
	private BusinessObjectModel businessObjectModel;
	
	/** The project. */
	private IProject project;
	
	/** The data source list. */
	private List<DataSourceInfo> dataSourceList;//数据库连接
	

	/**
	 * Instantiates a new generator diaolg.
	 *
	 * @param project
	 *            the project
	 * @param shell
	 *            the shell
	 * @param businessObjectModel
	 *            the business object model
	 */
	public GeneratorDiaolg(IProject project, Shell shell,
			BusinessObjectModel businessObjectModel) {
		super(shell);
		this.businessObjectModel = businessObjectModel;
		this.project = project;
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
		okButton.setEnabled(true);
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
		newShell.setText("代码生成向导");
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
		setTitle("基于业务对象模型生成UAP模块项目代码及配置文件");
		setMessage("请选择要生成代码的业务对象，并设置代码生成参数", 1);
		// 创建标签页对象
		tabFolder = new TabFolder(parent, SWT.NONE);
		tabFolder.setLayoutData(new GridData(GridData.FILL_BOTH));
		TabItem tabItem = new TabItem(tabFolder, SWT.NONE);
		tabItem.setText("参数设置");
		TabItem tabItem2 = new TabItem(tabFolder, SWT.NONE);
		tabItem2.setText("对象选择");
		// tab 内容
		osPage = new GeneratorObjectSelectPage(businessObjectModel, tabFolder,
				SWT.NONE);
	/*	try {
			dataSourceList = ReverseUtil.readDataSourceList();
		} catch (ReverseException e) {
			MessageDialog.openError(getShell(), "读取数据源错误", e.getMessage());
			dataSourceList = new ArrayList<DataSourceInfo>();
		}*/
		pPage = new ParametersPage(project, tabFolder, SWT.NONE, this,
				businessObjectModel.getId());
		tabFolder.getItem(0).setControl(pPage);
		tabFolder.getItem(1).setControl(osPage);

		return parent;
	}

	/**
	 * 执行DDL中的SQL脚本.
	 *
	 * @author rongxin.bian
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
			conn = ReverseUtil.createConnection(getParametersPage().getDataSourceInfo());
		} catch (Exception e) {
			String message = "连接数据源 " + getParametersPage().getDataSourceInfo().getDataSourceName() + " 失败:\n" + e.getMessage();
			MessageDialog.openError(getShell(), "同步到数据库错误", message);
			throw new RuntimeException(e);
		}
		
		String ddlName = businessObjectModel.getFullName() + "_" + getParametersPage().getDbType() + ".DDL";
		String ddlPath = getParametersPage().getCurPath() + "ddl" + File.separator + ddlName;
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
	
	/**
	 * Gets the object select page.
	 *
	 * @author mqfdy
	 * @return the object select page
	 * @Date 2018-09-03 09:00
	 */
	public GeneratorObjectSelectPage getObjectSelectPage() {
		return osPage;
	}

	/**
	 * Gets the parameters page.
	 *
	 * @author mqfdy
	 * @return the parameters page
	 * @Date 2018-09-03 09:00
	 */
	public ParametersPage getParametersPage() {
		return pPage;
	}
	
	

	/**
	 * 
	 */
	@Override
	protected void okPressed() {
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
				return;
			} else if (!((buList.contains(as.getClassA()) || refIdList
					.contains(as.getClassA().getId()))
					&& (buList.contains(as.getClassB())) || refIdList
						.contains(as.getClassB().getId()))) {
				this.setMessage("请选择" + as.getName() + "对应的业务实体！", 3);
				// this.setMessage("请选择"+as.getName()+"对应的业务实体（"+as.getClassA().getName()+"和"+as.getClassB().getName()+"）！",3);
				return;
			}
		}
		for (Inheritance in : model.getInheritances()) {
			if (!(buList.contains(in.getChildClass()) && buList.contains(in
					.getParentClass()))) {
				this.setMessage("请选择" + in.getName() + "对应的业务实体！", 3);
				// this.setMessage("请选择"+in.getName()+"对应的业务实体（"+in.getChildClass().getName()+"和"+in.getParentClass().getName()+"）！",3);
				return;
			}
		}
		String dbType = getParametersPage().getDbType();
		boolean isSyncDbs = getParametersPage().isSyncDbs;
		DataSourceInfo dataSourceInfo = getParametersPage().getDataSourceInfo();
		
		if(isSyncDbs && dataSourceInfo != null){
			if(!dataSourceInfo.getDbType().equals(dbType)){
				this.setMessage("数据库类型与数据源不匹配！", 3);
				return;
			}
		}
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

	/**
	 * Gets the data sources list.
	 *
	 * @author mqfdy
	 * @return the data sources list
	 * @Date 2018-09-03 09:00
	 */
	public List<DataSourceInfo> getDataSourcesList() {
		return dataSourceList;
	}

	/**
	 * Gets the pars map.
	 *
	 * @author mqfdy
	 * @return the pars map
	 * @Date 2018-09-03 09:00
	 */
	public Map getParsMap(){
		Map map = new HashMap();
		map.put("generateHbm", getParametersPage().getHbm());
		map.put("generatePO", getParametersPage().getJava());
		map.put("dbType", getParametersPage().getDbType());
		map.put("ddl", getParametersPage().getDdl());
		map.put("generateFk", getParametersPage().getGenFKDdl());
		map.put("isSyncDbs", getParametersPage().isSyncDbs);
		map.put("dataSourceInfo", getParametersPage().getDataSourceInfo());
		map.put("generateConfig", getParametersPage()
				.getConfig());
		map.put("testCode", getParametersPage()
				.getTestCode());
		map.put("isOverride",
				getParametersPage().getSe() == 0 ? false
						: true);
		return map;
	}
}
