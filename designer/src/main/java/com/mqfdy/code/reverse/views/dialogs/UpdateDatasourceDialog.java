package com.mqfdy.code.reverse.views.dialogs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.mqfdy.code.designer.constant.IProjectConstant;
import com.mqfdy.code.model.utils.DBType;
import com.mqfdy.code.resource.validator.ValidatorUtil;
import com.mqfdy.code.reverse.DataSourceInfo;
import com.mqfdy.code.reverse.IOmReverse;
import com.mqfdy.code.reverse.OmReverse;
import com.mqfdy.code.reverse.utils.ReverseUtil;
import com.mqfdy.code.reverse.views.constant.IViewConstant;

// TODO: Auto-generated Javadoc
/**
 * The Class UpdateDatasourceDialog.
 *
 * @author mqfdy
 */
public class UpdateDatasourceDialog extends TitleAreaDialog {

	/** The Constant DIALOG_WIDTH. */
	public static final int DIALOG_WIDTH = 530;
	
	/** The Constant DIALOG_HEIGHT. */
	public static final int DIALOG_HEIGHT = 530;
	
	/** The Constant LABELDATA_WIDTH. */
	public static final int LABELDATA_WIDTH = 100;
	
	/** The Constant TEXTDATA_WIDTH. */
	public static final int TEXTDATA_WIDTH = 500;
    
	/** The uap path label. */
	private Label uapPathLabel;		    //数据源保存路径标签
	
	/** The uap path combo. */
	private Combo uapPathCombo;			//数据源保存路径下拉框
	
	/** The ds name label. */
	private Label dsNameLabel;			//数据源名标签
	
	/** The ds name text. */
	private Text dsNameText;			//数据源名文本框
	
	/** The db type label. */
	private Label dbTypeLabel;		    //数据库类型标签
	
	/** The db type combo. */
	private Combo dbTypeCombo;			//数据库类型下拉框
	
	/** The sid label. */
	private Label sidLabel;				//数据库id标签
	
	/** The sid text. */
	private Text sidText;				//数据库id文本框
	
	/** The host label. */
	private Label hostLabel;			//服务器地址标签
	
	/** The host text. */
	private Text hostText;				//服务器地址文本框
	
	/** The port label. */
	private Label portLabel;			//端口标签
	
	/** The port text. */
	private Text portText;				//端口文本框
	
	/** The user label. */
	private Label userLabel;			//用户名标签
	
	/** The user text. */
	private Text userText;				//用户名文本框
	
	/** The password label. */
	private Label passwordLabel;		//密码标签
	
	/** The password text. */
	private Text passwordText;			//密码文本框
	
	/** The url label. */
	private Label urlLabel;				//URL标签
	
	/** The url text. */
	private Text urlText;				//URL文本框
	
	/** The check button. */
	private Button checkButton;			//保存密码复选框
	
	/** The test button. */
	private Button testButton;			//测试连接按钮

	/** The connection URL. */
	private String connectionURL;
	
	/** The om reverse. */
	private IOmReverse omReverse;
	
	/** The data source. */
	private DataSourceInfo dataSource;
	
	/** The is ok pressed. */
	private boolean isOkPressed;		//完成按钮是否Ok
	
	/** The init ds name. */
	private String initDsName;
	
	/** The project map. */
	private Map<String, IProject> projectMap;
		
	/**
	 * Instantiates a new update datasource dialog.
	 *
	 * @param parentShell
	 *            the parent shell
	 */
	public UpdateDatasourceDialog(Shell parentShell) {
		super(parentShell);
		isOkPressed = true;
		omReverse = new OmReverse();
		projectMap = new HashMap<String, IProject>();
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
	@Override
	protected Control createDialogArea(Composite parent) {
		
		setTitle("修改数据源");
		//设置父容器的布局
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		parent.setLayout(layout);
		parent.layout();
		
		//在父容器中创建子容器group1
		final Group group1 = new Group(parent, SWT.NULL);
		
		//设置group1在父容器中的布局参数
		GridData gridData1 = new GridData(GridData.FILL_BOTH);
		group1.setLayoutData(gridData1);
				
		//创建每个分组内的布局
		GridLayout groupLayout = new GridLayout();
		groupLayout.numColumns = 2;
		
		//设置group1的布局
		group1.setLayout(groupLayout);
		group1.layout();
		
		//创建标签元素的布局参数
		GridData labelGridData = new GridData();
		labelGridData.horizontalAlignment = SWT.LEFT;
		labelGridData.widthHint = LABELDATA_WIDTH;
		
		//创建文本框元素的布局参数
		GridData textGridData = new GridData(GridData.FILL_HORIZONTAL);
		
		//创建group1中的控件
		
		uapPathLabel = new Label(group1, SWT.NULL);
		uapPathLabel.setLayoutData(labelGridData);
		uapPathLabel.setText("选择UAP项目: ");
		
		uapPathCombo = new Combo(group1, SWT.READ_ONLY|SWT.NULL);
		uapPathCombo.setLayoutData(textGridData);
		
		List<IProject> uapProjects = ReverseUtil.getProjectsByNatureId(IProjectConstant.BOM_NATURE_ID);
		for(IProject iProject: uapProjects) {
			projectMap.put(iProject.getName(), iProject);
			uapPathCombo.add(iProject.getName());
		}
		uapPathCombo.setText(dataSource.getUapName());
		
		//数据源名标签
		dsNameLabel = new Label(group1, SWT.NULL);
		dsNameLabel.setLayoutData(labelGridData);
		dsNameLabel.setText("数据源名：");
		
		//数据源名文本框
		dsNameText = new Text(group1, SWT.BORDER);
		dsNameText.setText(dataSource.getDataSourceName());
		dsNameText.setLayoutData(textGridData);
		setInitDsName(dataSource.getDataSourceName());
		
		//数据库类型标签
		dbTypeLabel = new Label(group1, SWT.NULL);
		dbTypeLabel.setLayoutData(labelGridData);
		dbTypeLabel.setText("数据库类型：");
		
		//类型下拉框
		dbTypeCombo = new Combo(group1, SWT.READ_ONLY|SWT.NULL);
		dbTypeCombo.setLayoutData(textGridData);
		
		//初始化数据库类型
		for(DBType type: DBType.values()) {
			dbTypeCombo.add(type.getDbType());
		}
		
		dbTypeCombo.setText(dataSource.getDbType());
		
		dbTypeCombo.addSelectionListener(new SelectionListener() {
			
			public void widgetSelected(SelectionEvent e) {
				connectionURL = ReverseUtil.getURL( dbTypeCombo.getText()
												  , hostText.getText()
												  , sidText.getText()
												  , portText.getText());
				urlText.setText(connectionURL);
				
			}
			
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
		
		//数据库ID标签
		sidLabel = new Label(group1, SWT.NULL);
		sidLabel.setLayoutData(labelGridData);
		sidLabel.setText("数据库ID：");
		
		//数据库ID文本框
		sidText = new Text(group1, SWT.BORDER);
		sidText.setLayoutData(textGridData);
		sidText.setText(dataSource.getSid());
		sidText.addModifyListener(new ConnTextModifyAdapter() {});
		
		//服务器地址标签
		hostLabel = new Label(group1, SWT.NULL);
		hostLabel.setLayoutData(labelGridData);
		hostLabel.setText("服务器地址：");
		
		//服务器地址文本框
		hostText = new Text(group1, SWT.BORDER);
		hostText.setLayoutData(textGridData);
		hostText.setText(dataSource.getIp());
		hostText.addModifyListener(new ConnTextModifyAdapter());
		
		//端口标签
		portLabel = new Label(group1, SWT.NULL);
		portLabel.setLayoutData(labelGridData);
		portLabel.setText("端口：");
		
		//端口文本框
		portText = new Text(group1, SWT.BORDER);
		portText.setLayoutData(textGridData);
		portText.setText(dataSource.getPort());
		portText.addModifyListener(new ConnTextModifyAdapter());
		
		//用户名标签
		userLabel = new Label(group1, SWT.NULL);
		userLabel.setLayoutData(labelGridData);
		userLabel.setText("用户名：");
		
		//用户名文本框
		userText = new Text(group1, SWT.BORDER);
		userText.setLayoutData(textGridData);
		userText.setText(dataSource.getUserName());
		userText.addModifyListener(new ConnTextModifyAdapter());
		
		//密码标签
		passwordLabel = new Label(group1, SWT.NULL);
		passwordLabel.setLayoutData(labelGridData);
		passwordLabel.setText("密码：");
		
		//密码文本框
		passwordText = new Text(group1, SWT.PASSWORD| SWT.BORDER);
		passwordText.setLayoutData(textGridData);
		String ppp = dataSource.getPwd();
		if(ppp == null){
			ppp = "";
		}
		passwordText.setText(ppp);
		passwordText.addModifyListener(new ConnTextModifyAdapter());
		
		//URL标签
		urlLabel = new Label(group1, SWT.NULL);
		urlLabel.setLayoutData(labelGridData);
		urlLabel.setText("URL：");
		
		//URL文本框
		urlText = new Text(group1, SWT.READ_ONLY| SWT.BORDER);
		urlText.setLayoutData(textGridData);
		urlText.setText(dataSource.getUrl());
		connectionURL = dataSource.getUrl();
		
		//保存密码复选框
		checkButton = new Button(group1, SWT.CHECK);
		checkButton.setText("保存密码");
		checkButton.setSelection(true);
		
		//测试连接按钮
		testButton = new Button(group1, SWT.NULL);
		testButton.setText("测试连接");
		
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		data.widthHint = 90;
		data.heightHint = 28;
		data.horizontalAlignment = SWT.RIGHT;
		testButton.setLayoutData(data);
		
		final Composite container = (Composite) parent;
		
		//测试按钮监听事件
		testButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				Cursor cursor = new Cursor(container.getDisplay(), SWT.CURSOR_WAIT);
				container.getShell().setCursor(cursor);
				
				DataSourceInfo dsi = new DataSourceInfo();
				dsi.setDataSourceName(dsNameText.getText());
				dsi.setUserName(userText.getText());
				dsi.setPwd(passwordText.getText());
				dsi.setUrl(connectionURL);
				dsi.setDbType(dbTypeCombo.getText());
				
				try {
					validateInput();
					if(!isOkPressed) {
						cursor = new Cursor(container.getDisplay(), SWT.CURSOR_ARROW);
						container.getShell().setCursor(cursor);
						return ;
					}
					
					MessageBox messageBox = null;
					if(omReverse.connectTest(dsi)) {
						messageBox = new MessageBox(getShell(), SWT.NULL);
						messageBox.setMessage("连接成功！");
					} else {
						messageBox = new MessageBox(getShell(), SWT.ERROR);
						messageBox.setMessage("连接失败");
					}
					messageBox.open();
				} catch (Exception ex) {
					cursor = new Cursor(container.getDisplay(), SWT.CURSOR_ARROW);
					container.getShell().setCursor(cursor);
					
					MessageDialog.openError(getShell(), "数据库连接失败", ex.getMessage());
				}
				
				cursor = new Cursor(container.getDisplay(), SWT.CURSOR_ARROW);
				container.getShell().setCursor(cursor);
			}
		});
		return parent;
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
		super.configureShell(newShell);
		newShell.setText("修改数据源");
	}

	/**
	 * @return
	 */
	@Override
	protected Point getInitialSize() {
		return new Point( IViewConstant.SCREEN_WIDTH  * IViewConstant.DIALOG_WIDTH_PROPORTION/ 100
		        , IViewConstant.SCREEN_HEIGHT * IViewConstant.DIALOG_HEIGHT_PROPORTION/ 100);
	}

	/**
	 * @return
	 */
	@Override
	protected int getShellStyle() {
		return super.getShellStyle() | SWT.RESIZE | SWT.MAX | SWT.MIN;
	}
	
	/**
	 * 
	 */
	@Override
	protected void okPressed() {
		validateInput();
		if(isOkPressed) {
			Cursor cursor = new Cursor(this.getShell().getDisplay(), SWT.CURSOR_WAIT);
			this.getShell().setCursor(cursor);
			
			DataSourceInfo info = new DataSourceInfo();
			info.setUapName(uapPathCombo.getText());
			info.setDataSourceName(dsNameText.getText());
			info.setUserName(userText.getText());
			info.setPwd(passwordText.getText());
			info.setUrl(connectionURL);
			info.setSid(sidText.getText());
			info.setDbType(dbTypeCombo.getText());
			info.setIp(hostText.getText());
			info.setPort(portText.getText());
			
			try {
				//如果连接成功
				if(omReverse.connectTest(info)) {
					//修改的数据源
					setDataSource(info);
					
					cursor = new Cursor(this.getShell().getDisplay(), SWT.CURSOR_ARROW);
					this.getShell().setCursor(cursor);
					super.okPressed();
				} else {
					cursor = new Cursor(this.getShell().getDisplay(), SWT.CURSOR_ARROW);
					this.getShell().setCursor(cursor);
					setMessage("修改失败，当前数据源不能连接数据库", IMessageProvider.ERROR);
				}
			} catch (Exception ex) {
				cursor = new Cursor(this.getShell().getDisplay(), SWT.CURSOR_ARROW);
				this.getShell().setCursor(cursor);
				
				MessageDialog.openError(getShell(), "修改数据源失败", "连接数据库异常:    " + ex.getMessage());
			}
			
		}
	}

	/**
	 * 校验.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	public void validateInput(){
		isOkPressed = true;
		if(uapPathCombo.getText() == null || uapPathCombo.getText().trim().length() == 0) {
			setMessage("请选择数据源所属的UAP工程", IMessageProvider.ERROR);
			uapPathCombo.setFocus();
			isOkPressed = false;
			return ;
		}
		
		if(dsNameText.getText() == null || dsNameText.getText().trim().length() == 0) {
			setMessage("请填写数据源名称", IMessageProvider.ERROR);
			dsNameText.setFocus();
			isOkPressed = false;
			return ;
		} else {
			IProject iProject = projectMap.get(uapPathCombo.getText());
			String dataSourceXMLPath = iProject.getLocation().toOSString() + IProjectConstant.BOM_DATASOURCE_XML_RALATIVE_PATH;
			List<DataSourceInfo> datasourceList = ReverseUtil.readDataSourceXML(dataSourceXMLPath);
			for(DataSourceInfo info: datasourceList) {
				//如果修改了数据源名称后, 所在UAP项目含有和当前修改的名称相同的, 就说明和其他数据源名称冲突.
				if(!initDsName.equals(dsNameText.getText()) && info.getDataSourceName().equals(dsNameText.getText())) {
					setMessage("该数据源名称在工程 " + iProject.getName() + " 中已存在", IMessageProvider.ERROR);
					isOkPressed = false;
					return ;
				}
			}
			
			if(!ValidatorUtil.valiName(dsNameText.getText())){
				setErrorMessage("数据源名称只能包含字母、数字和下划线，且以字母或下划线开头");
				isOkPressed = false;
				return ;
			}
		}
		
		if(dbTypeCombo.getText() == null || dbTypeCombo.getText().trim().length() == 0) {
			setMessage("请选择数据库类型", IMessageProvider.ERROR);
			dbTypeCombo.setFocus();
			isOkPressed = false;
			return ;
		}
		
		if(sidText.getText() == null || sidText.getText().trim().length() == 0) {
			setMessage("请填写数据库ID", IMessageProvider.ERROR);
			sidText.setFocus();
			isOkPressed = false;
			return ;
		}
		
		if(hostText.getText() == null || hostText.getText().trim().length() == 0) {
			setMessage("请填写服务器IP", IMessageProvider.ERROR);
			hostText.setFocus();
			isOkPressed = false;
			return ;
		}
		
		if(portText.getText() == null || portText.getText().trim().length() == 0) {
			setMessage("请填写端口", IMessageProvider.ERROR);
			portText.setFocus();
			isOkPressed = false;
			return ;
		} else {
			String check = "^[0-9]*$";  
	        Pattern regex = Pattern.compile(check);  
	        Matcher matcher = regex.matcher(portText.getText()); 
			if(!matcher.matches()) {
				setMessage("您填写的端口格式不正确", IMessageProvider.ERROR);
				portText.setFocus();
				isOkPressed = false;
				return ;
			}
		}
		
		if(userText.getText() == null || userText.getText().trim().length() == 0) {
			setMessage("请填写用户名", IMessageProvider.ERROR);
			userText.setFocus();
			isOkPressed = false;
			return ;
		}
		
		if(passwordText.getText() == null || passwordText.getText().trim().length() == 0) {
			setMessage("请填写密码", IMessageProvider.ERROR);
			passwordText.setFocus();
			isOkPressed = false;
			return ;
		}
		
	}
	
	/**
	 * Gets the data source.
	 *
	 * @author mqfdy
	 * @return the data source
	 * @Date 2018-09-03 09:00
	 */
	public DataSourceInfo getDataSource() {
		return dataSource;
	}

	/**
	 * Sets the data source.
	 *
	 * @author mqfdy
	 * @param dataSource
	 *            the new data source
	 * @Date 2018-09-03 09:00
	 */
	public void setDataSource(DataSourceInfo dataSource) {
		this.dataSource = dataSource;
	}


	/**
	 * The Class ConnTextModifyAdapter.
	 *
	 * @author mqfdy
	 */
	class ConnTextModifyAdapter implements ModifyListener {
		
		/**
		 * Modify text.
		 *
		 * @author mqfdy
		 * @param event
		 *            the event
		 * @Date 2018-09-03 09:00
		 */
		public void modifyText(ModifyEvent event) {
			connectionURL = ReverseUtil.getURL( dbTypeCombo.getText()
											  , hostText.getText()
											  , sidText.getText()
											  , portText.getText());
			urlText.setText(connectionURL);
		}
	}

	/**
	 * Gets the inits the ds name.
	 *
	 * @author mqfdy
	 * @return the inits the ds name
	 * @Date 2018-09-03 09:00
	 */
	public String getInitDsName() {
		return initDsName;
	}

	/**
	 * Sets the inits the ds name.
	 *
	 * @author mqfdy
	 * @param initDsName
	 *            the new inits the ds name
	 * @Date 2018-09-03 09:00
	 */
	public void setInitDsName(String initDsName) {
		this.initDsName = initDsName;
	}
	
	
}
