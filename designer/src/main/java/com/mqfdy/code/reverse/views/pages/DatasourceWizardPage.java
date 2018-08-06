package com.mqfdy.code.reverse.views.pages;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;
import org.eclipse.ui.model.WorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;

import com.mqfdy.code.designer.constant.IProjectConstant;
import com.mqfdy.code.reverse.DataSourceInfo;
import com.mqfdy.code.reverse.IOmReverse;
import com.mqfdy.code.reverse.OmReverse;
import com.mqfdy.code.reverse.ReverseContext;
import com.mqfdy.code.reverse.ReverseException;
import com.mqfdy.code.reverse.utils.ReverseUtil;
import com.mqfdy.code.reverse.views.dialogs.ConnectDatabaseDetailDialog;
import com.mqfdy.code.reverse.views.dialogs.OMConnectDatabaseDetailDialog;
import com.mqfdy.code.reverse.views.dialogs.OMUpdateDatasourceDialog;
import com.mqfdy.code.reverse.views.dialogs.UpdateDatasourceDialog;
import com.mqfdy.code.reverse.views.models.DataSourceTableModel;
import com.mqfdy.code.reverse.views.providers.DataSourceTableContentProvider;
import com.mqfdy.code.reverse.views.providers.DataSourceTableLabelProvider;
import com.mqfdy.code.utils.ProjectUtil;

public class DatasourceWizardPage extends WizardPage {

	public static final int COLUMN1_WIDTH = 300;
	public static final int COLUMN2_WIDTH = 250;
	private IOmReverse omReverse;
	private Map<String, DataSourceInfo> dataSourceMap;//数据源缓存 key:数据源名称，value:数据源对象
	
	private String dataSourceKey;//数据源key
	private Composite container;
	private TableViewer tableViewer;
	private Table table;
	
	private String currentDataSourceName;	//当前数据源名称
	private Button addBtn;
	private Button editBtn;
	private Button removeBtn;
	private Composite upContainer;
	private Composite downContainer;
	private Composite rightContainer;
	private Composite omContainer;
	private Text txtOm;
	private Button btnOm;
	
	private ReverseWizard reverseWizard;
	
	public String getCurrentDataSourceName() {
		return currentDataSourceName;
	}

	public void setCurrentDataSourceName(String currentDataSourceName) {
		this.currentDataSourceName = currentDataSourceName;
	}

	public Map<String, DataSourceInfo> getDataSourceMap() {
		return dataSourceMap;
	}

	public void setDataSourceMap(Map<String, DataSourceInfo> dataSourceMap) {
		this.dataSourceMap = dataSourceMap;
	}

	public String getDataSourceKey() {
		return dataSourceKey;
	}

	public void setDataSourceKey(String dataSourceKey) {
		this.dataSourceKey = dataSourceKey;
	}

	protected DatasourceWizardPage(ReverseWizard reverseWizard, String pageName) throws ReverseException {
		super(pageName);
		setTitle("连接数据库");
		this.reverseWizard = reverseWizard;
		initDatasource();
	}
	/**
	 * 初始化数据源
	 * @throws ReverseException
	 */
	private void initDatasource() throws ReverseException {
		IProject project = reverseWizard.getProject();

		//缓存向导对象
		ReverseContext.wizard = reverseWizard;
				
		omReverse = new OmReverse();
		dataSourceMap = new LinkedHashMap<String, DataSourceInfo>();
		if(project != null){
			try {
				List<DataSourceInfo> dataSourceList = readDataSourceList(project);
				
				DataSourceInfo lastDatasource = ReverseUtil.getLastSelectedDataSource(dataSourceList);
				for(DataSourceInfo info : dataSourceList) {
					if(info.equals(lastDatasource)) {
						info.setIsSelect("1");
						break;
					}
				}
				
				//初始化数据源
				for(DataSourceInfo info: dataSourceList) {
					//用数据源名称 + “@” + uap项目 来确定数据源唯一性
					dataSourceMap.put(info.getDataSourceName() + "@" + info.getUapName(), info);
				}
			} catch (Exception e) {
				MessageDialog.openError(getShell(), "读取数据源错误", e.getMessage());
				throw new ReverseException(e);
			}
		}
	}
	
	public void createControl(Composite parent) {
		container = new Composite(parent, SWT.NULL);
		
		initializeDialogUnits(container);//调用后，界面的控件大小风格统一
	
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 1;
		
		GridData gridData = new GridData(GridData.FILL_BOTH);
		container.setLayout(gridLayout);
		
		upContainer = new Composite(container, SWT.NULL);
//		upContainer.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		upContainer.setLayout(new GridLayout(1, true));
		upContainer.layout();
		
		Label label = new Label(upContainer, SWT.NULL);
		label.setText("历史数据源");
		
		downContainer = new Composite(container, SWT.NULL);
		downContainer.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		downContainer.setLayout(new GridLayout(2, false));
		downContainer.layout();
		
		tableViewer = new TableViewer(downContainer, SWT.BORDER | SWT.FULL_SELECTION);
		tableViewer.setContentProvider(new DataSourceTableContentProvider());
		tableViewer.setLabelProvider(new DataSourceTableLabelProvider());
		
		tableViewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				IWizardPage page = getNextPage();
				if(page != null) {
					page.getWizard().getContainer().showPage(page);
				}
			}
		});
		
		table = tableViewer.getTable();
		table.setLayoutData(gridData);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		table.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if(!editBtn.getEnabled()) {
					editBtn.setEnabled(true);
				}
				if(!removeBtn.getEnabled()) {
					removeBtn.setEnabled(true);
				}
				
				setPageComplete(true);
			}
		});
		
		//创建表格列
		TableColumn nameColumn = new TableColumn(table, SWT.NONE);
		nameColumn.setText("数据源名称");
		nameColumn.setWidth(COLUMN1_WIDTH);
		nameColumn.setAlignment(SWT.CENTER);
		
		TableColumn uapColumn = new TableColumn(table, SWT.NONE);
		uapColumn.setText("BOM项目名称");
		
		uapColumn.setWidth(COLUMN2_WIDTH);
		uapColumn.setAlignment(SWT.CENTER);
		
		initDatasourceTable();
		
		container.layout();
		
		if(table.getItemCount() == 0) {
			setPageComplete(false);
		}
		
		rightContainer = new Composite(downContainer, SWT.NULL);
		rightContainer.setLayoutData(new GridData(GridData.FILL_VERTICAL));
		
		rightContainer.setLayout(new GridLayout(1, true));
		rightContainer.layout();
		
		
		addBtn = new Button(rightContainer, SWT.BUTTON1);
		
		//调用后，界面的控件大小风格统一
		GridData data = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		int widthHint = convertHorizontalDLUsToPixels(IDialogConstants.BUTTON_WIDTH);
		Point minSize = addBtn.computeSize(SWT.DEFAULT, SWT.DEFAULT, true);
		data.widthHint = Math.max(widthHint, minSize.x);
		
		addBtn.setLayoutData(data);
		
		addBtn.setText("新增");
		addBtn.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent evt) {
				ConnectDatabaseDetailDialog dialog = getConnectDatabaseDialog();
				if(dialog.open() == Dialog.OK){
					//获取对话框的数据源
					DataSourceInfo info = dialog.getDataSource();
					//新增数据源缓存
					dataSourceMap.put(info.getDataSourceName() + "@" + info.getUapName(), info);
					
					DataSourceTableModel input = (DataSourceTableModel) tableViewer.getInput();
					Object[] objects = input.elements();
					
					input = new DataSourceTableModel();
					input.add(info);
					
					for(Object obj: objects) {
						DataSourceInfo dataSourceInfo = (DataSourceInfo) obj;
						input.add(dataSourceInfo);
					}
					
					tableViewer.setInput(input);
					table.setSelection(0);
					
					if(!editBtn.getEnabled()) {
						editBtn.setEnabled(true);
					}
					if(!removeBtn.getEnabled()) {
						removeBtn.setEnabled(true);
					}
					
					setPageComplete(true);
				}
			}
		});
		editBtn = new Button(rightContainer, SWT.BUTTON1);
		editBtn.setLayoutData(data);
		editBtn.setText("修改");
		editBtn.addSelectionListener(new SelectionAdapter() {
			@SuppressWarnings("deprecation")
			public void widgetSelected(SelectionEvent evt) {
				
				DataSourceInfo oldDataSourceInfo = (DataSourceInfo) table.getSelection()[0].getData();
				String oldSourceName = oldDataSourceInfo.getDataSourceName();
				String oldUapName = oldDataSourceInfo.getUapName();
				
				UpdateDatasourceDialog dialog = getUpdateDatasourceDialog();
				
				String datasourceRelativePath = getDatasourceRelativePath();

				dialog.setDataSource(oldDataSourceInfo);
				if(dialog.open() == Dialog.OK) {
					try {
						DataSourceInfo newInfo = dialog.getDataSource();

						String dataSourceXMLPath = ReverseUtil.getWorkspacePath() + newInfo.getUapName() 
												 + datasourceRelativePath;
						//修改数据源
						ReverseUtil.updateDatasourceXML(oldUapName, oldSourceName, dataSourceXMLPath, newInfo);
						
						IPath path = new Path(dataSourceXMLPath);
						IResource res = ResourcesPlugin.getWorkspace().getRoot().findFilesForLocation(path)[0];
						try {
							res.getProject().refreshLocal(IResource.DEPTH_INFINITE, null);
						} catch (CoreException e) {
							throw new RuntimeException(e.getMessage(), e);
						}
						
						DataSourceTableModel tableModel = (DataSourceTableModel) tableViewer.getInput();
						
						tableModel.update(oldDataSourceInfo, newInfo);
						tableViewer.refresh();
						
						dataSourceMap.remove(oldSourceName + "@" + oldUapName);
						dataSourceMap.put(newInfo.getDataSourceName() + "@" + newInfo.getUapName(), newInfo);
						
						setMessage("");
						if(table.getSelection() == null || table.getSelection().length == 0) {
							editBtn.setEnabled(false);
							removeBtn.setEnabled(false);
						}
					} catch (ReverseException e) {
						ReverseException cause = (ReverseException) e.getCause();
						MessageDialog.openError(getShell(), "修改数据源失败", cause.getExceptionMsg());
					}
				}
			}
		});
		
		removeBtn = new Button(rightContainer, SWT.BUTTON1);
		removeBtn.setLayoutData(data);
		removeBtn.setText("删除");
		removeBtn.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent evt) {
				TableItem tableItem = table.getSelection()[0];
				DataSourceInfo dataSourceInfo = (DataSourceInfo) tableItem.getData();
//				String dsName = tableItem.getText();
				if(MessageDialog.openConfirm(getShell(), "删除确认", "您确认要删除在项目 " + dataSourceInfo.getUapName() + " 的数据源 " + dataSourceInfo.getDataSourceName() + " 吗?")) {
					try {
//						DataSourceInfo dataSourceInfo = (DataSourceInfo) tableItem.getData();
						//删除指定的数据源
						deleteDataSource(dataSourceInfo);
						DataSourceTableModel input = (DataSourceTableModel) tableViewer.getInput();
						input.remove(dataSourceInfo);
						
						setMessage("");
						if(table.getSelection() == null || table.getSelection().length == 0) {
							editBtn.setEnabled(false);
							removeBtn.setEnabled(false);
							setPageComplete(false);
						}
						
						if(table.getItemCount() == 0) {
							setPageComplete(false);
						}
					} catch (ReverseException e) {
						ReverseException cause = (ReverseException) e.getCause();
						MessageDialog.openError(getShell(), "删除数据源失败", cause.getExceptionMsg());
					}
				 }
			}
		});
		createSelectBOMUI();
		setControl(container);
	}

	private void initDatasourceTable() {
		//初始化列表
		DataSourceTableModel input = new DataSourceTableModel();
		for(Entry<String, DataSourceInfo> entry: dataSourceMap.entrySet()) {
			DataSourceInfo dataSourceInfo = entry.getValue();
			if("1".equals(dataSourceInfo.getIsSelect())) {
				input.add(entry.getValue());
				break;
			}
		}
		
		for(Entry<String, DataSourceInfo> entry: dataSourceMap.entrySet()) {
			DataSourceInfo dataSourceInfo = entry.getValue();
			if(!"1".equals(dataSourceInfo.getIsSelect())) {
				input.add(entry.getValue());
			}
		}
		tableViewer.setInput(input);
		table.select(0);
		setPageComplete(table.getItemCount() > 0);
	}
	/**
	 * 创建BOMUI
	 */
	private void createSelectBOMUI() {
		omContainer = new Composite(downContainer, SWT.NULL);
//		upContainer.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		GridLayout layout = new GridLayout();
		omContainer.setLayout(layout);
		layout.numColumns = 3;
		layout.verticalSpacing = 9;
		omContainer.layout();
		omContainer.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		Label lblOm = new Label(omContainer, SWT.NULL);
		lblOm.setText("BOM项目：");
		
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		txtOm = new Text(omContainer,SWT.BORDER);
		txtOm.setEditable(false);
		txtOm.setLayoutData(gd);
		
		btnOm = new Button(omContainer,SWT.NULL);
		btnOm.setText("浏览..");
		btnOm.setEnabled(true);
		if(reverseWizard.getProject() != null){
			txtOm.setText(reverseWizard.getProject().getName());
			btnOm.setEnabled(false);
			editBtn.setEnabled(true);
			removeBtn.setEnabled(true);
			addBtn.setEnabled(true);
		}else{
			editBtn.setEnabled(false);
			removeBtn.setEnabled(false);
			addBtn.setEnabled(false);
		}
		btnOm.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				handleSelect();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	/**
	 * 处理选择模块
	 */
	private void handleSelect() {
		
		// 取得当前的shell
		Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
		
		ElementTreeSelectionDialog dialog = new ElementTreeSelectionDialog(shell, new WorkbenchLabelProvider(),
				new WorkbenchContentProvider());
		dialog.setTitle("请选择bom项目");
		
		// 过滤掉所有的非om的项目
		dialog.addFilter(new ViewerFilter(){

			@Override
			public boolean select(Viewer viewer, Object parentElement, Object element) {
				if (element instanceof IProject) {
					IProject curProject = (IProject) element;
					if(ProjectUtil.isBOMProject(curProject)){
						return true;
					}
					
				}
				return false;
			}
			
		});
		
		dialog.setInput(ResourcesPlugin.getWorkspace().getRoot());

		if (dialog.open() == Window.OK) {

			if (dialog.getResult() != null){
				Object o = dialog.getResult()[0];
				if(o instanceof IProject){
					IProject selection = (IProject) dialog.getResult()[0];
					if(!selection.isOpen()){
						MessageDialog.openInformation(getShell(), "提示", "所选项目没有打开！");
						txtOm.setText("");
						editBtn.setEnabled(false);
						removeBtn.setEnabled(false);
						addBtn.setEnabled(false);
					}
					else{
						txtOm.setText(selection.getName());
						editBtn.setEnabled(true);
						removeBtn.setEnabled(true);
						addBtn.setEnabled(true);
						((ReverseWizard)this.getWizard()).setProject(selection);
						try {
							initDatasource();
							initDatasourceTable();
						} catch (ReverseException e) {
							e.printStackTrace();
						}
					}
						
						
				}
			}
			

		} 


	}
	
	public void deleteDataSource(DataSourceInfo dataSourceInfo) throws ReverseException {
		IProject project = ReverseContext.wizard.getProject();
		if(ProjectUtil.isBOMProject(project)){
			ReverseUtil.deleteOMDatasourceXML(dataSourceInfo.getUapName(), dataSourceInfo.getDataSourceName());
		}
	}
	/**
	 * 获取新增数据库对话框
	 * @return
	 */
	public ConnectDatabaseDetailDialog getConnectDatabaseDialog() {
		IProject project = ReverseContext.wizard.getProject();
		if(ProjectUtil.isBOMProject(project)){
			return new OMConnectDatabaseDetailDialog(getShell(),project);
		}
		return new OMConnectDatabaseDetailDialog(getShell(),project);
	}
	
	public String getDatasourceRelativePath() {
		return IProjectConstant.BOM_DATASOURCE_XML_RALATIVE_PATH;
	}
	/**
	 * 获取修改数据源对话框
	 * @return
	 */
	public UpdateDatasourceDialog getUpdateDatasourceDialog() {
		IProject project = ReverseContext.wizard.getProject();
		if(ProjectUtil.isBOMProject(project)){
			return new OMUpdateDatasourceDialog(getShell(),project);
		}
		return new UpdateDatasourceDialog(getShell());
	}
	
	/**
	 * 读取数据源
	 * @param project
	 * @return
	 * @throws ReverseException
	 */
	private List<DataSourceInfo> readDataSourceList(IProject project) throws ReverseException {
		List<DataSourceInfo> dataSourceList = new ArrayList<DataSourceInfo>();
		if(project != null){
			dataSourceList = ReverseUtil.readOMDataSourceList(project);
		}
		return dataSourceList;
	}

	@Override
	public boolean canFlipToNextPage() {
		return isPageComplete();
	}

	/**
	 * 获得下一个页面
	 * @return
	 */
	@Override
	public IWizardPage getNextPage() {
		TableStructureWizardPage nextPage = (TableStructureWizardPage) super.getWizard().getPage("tsPage");
		
		Cursor cursor = null;
		try {
			
			DataSourceInfo info = (DataSourceInfo) table.getSelection()[0].getData();
			dataSourceKey = info.getDataSourceName() + "@" + info.getUapName();
			
			//如果当前选择的数据源有改变才连接数据库
			//if(currentDataSourceName == null || !currentDataSourceName.equals(dataSourceKey)) {
				cursor = new Cursor(container.getDisplay(), SWT.CURSOR_WAIT);
				container.getShell().setCursor(cursor);
				
				//连接数据库
				omReverse.createConnection(info);//无返回值, 连接对象Connection 保存在ReverseWizaid中
				//存储当前的数据源以便下次默认选中
				ReverseUtil.saveReverseConfig("reverse.selected", dataSourceKey);
				//记录当前的数据源
				currentDataSourceName = dataSourceKey;
				
				if(ReverseContext.wizard.getConnection() != null){
					//重画下一个页面
					setMessage("");
					nextPage.setDataSourceInfo(info);
					nextPage.repaint();
				} else {
					setMessage("当前数据源连接数据库失败", IMessageProvider.ERROR);
				}
				
				cursor = new Cursor(container.getDisplay(), SWT.CURSOR_ARROW);
				container.getShell().setCursor(cursor);
			//}
			return nextPage;
		} catch (Exception ex) {
			ex.printStackTrace();
			cursor = new Cursor(container.getDisplay(), SWT.CURSOR_ARROW);
			container.getShell().setCursor(cursor);
			
			MessageDialog.openError(getShell(), "数据库连接失败", ex.getMessage());
			return null;
		}
	}
}
