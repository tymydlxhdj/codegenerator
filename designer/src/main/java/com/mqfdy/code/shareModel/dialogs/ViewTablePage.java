package com.mqfdy.code.shareModel.dialogs;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;

import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mqfdy.code.designer.dialogs.BusinessClassEditorDialog;
import com.mqfdy.code.designer.editor.actions.ShareModelActionHttpclient;
import com.mqfdy.code.designer.models.BusinessModelManager;
import com.mqfdy.code.model.BusinessClass;
import com.mqfdy.code.model.Enumeration;
import com.mqfdy.code.shareModel.providers.DataGridLabelProvider;
import com.mqfdy.code.shareModel.providers.DataGridTableContentProvider;
import com.mqfdy.code.shareModel.providers.ModelMessageTreeContentProvider;
import com.mqfdy.code.shareModel.providers.ModelMessageTreeLabelProvider;


// TODO: Auto-generated Javadoc
/**
 * 业务实体选择tab页.
 *
 * @author mqfdy
 */
public class ViewTablePage extends Composite{
	
	
	/** The serch box group. */
	private Group serchBoxGroup;
	
	/** The table group. */
	private Group tableGroup;
	
	/** The tree view. */
	private TreeViewer treeView;
	
	/** The tree. */
	private Tree tree;
	
	/** The tree scroll. */
	private ScrolledComposite treeScroll;
	
	/** The model type label. */
	private Label modelTypeLabel;
	
	/** The model type combo. */
	private Combo modelTypeCombo;
	
	/** The model name label. */
	private Label modelNameLabel;
	
	/** The model name text. */
	private Text modelNameText;
	
	/** The model desc label. */
	private Label modelDescLabel;
	
	/** The model desc text. */
	private Text modelDescText;
	
	/** The share person label. */
	private Label sharePersonLabel;
	
	/** The share person text. */
	private Text sharePersonText;
	
	/** The start time label. */
	private Label startTimeLabel;
	
	/** The start text. */
	private DateTime  startText;
	
	/** The end time label. */
	private Label endTimeLabel;
	
	/** The end text. */
	private DateTime  endText;
	
	/** The serach. */
	private Button serach;
	
	/** The reset. */
	private Button reset;
	
	/** The table viewer. */
	private TableViewer tableViewer;
	
	/** The table. */
	public Table table;
	
	/** The find model dialog. */
	private FindModelDialog findModelDialog;

	/**
	 * Instantiates a new view table page.
	 *
	 * @param folder
	 *            the folder
	 * @param findModelDialog
	 *            the find model dialog
	 */
	public ViewTablePage(Composite folder,FindModelDialog findModelDialog) {
		super(folder, SWT.NONE);
		this.findModelDialog=findModelDialog;
		createContents(this);
	}	

	/**
	 * 创建界面.
	 *
	 * @author mqfdy
	 * @param composite
	 *            the composite
	 * @Date 2018-09-03 09:00
	 */
	private void createContents(Composite composite) {
		composite.setLayout(new GridLayout(2,false));	
		createTree(composite);
		createSerchBoxGroup(composite);
		createTableGroup(composite);
		
	}	
	
	/**
	 * 表格显示区域.
	 *
	 * @author mqfdy
	 * @param composite
	 *            the composite
	 * @Date 2018-09-03 09:00
	 */
	private void createTableGroup(Composite composite) {
		tableGroup=new Group(composite,SWT.None);
		GridLayout layout2=new GridLayout(1,false);
		GridData gridData = new GridData(GridData.FILL_BOTH);
		tableGroup.setLayout(layout2);
		tableGroup.setLayoutData(gridData);
		
		table = new Table(tableGroup, SWT.BORDER|SWT.FULL_SELECTION|SWT.SINGLE);
		gridData = new GridData(GridData.FILL_BOTH);
		ScrolledComposite tableScroll = new ScrolledComposite(table,
					SWT.BORDER);
		tableScroll.setContent(table);

		tableViewer = new TableViewer(table);
		tableViewer
					.setContentProvider(new DataGridTableContentProvider());
		tableViewer.setLabelProvider(new DataGridLabelProvider());
		tableViewer.setInput(new Object[0]);
		gridData.heightHint = 380;
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		table.setLayoutData(gridData);
		table.addMouseListener(new MouseListener() {
			
			public void mouseUp(MouseEvent e) {
			}
			
			public void mouseDown(MouseEvent e) {			
			}
			
			public void mouseDoubleClick(MouseEvent e) {
				if(table.getSelection().length>0){
					Object obj=FindModelDialog.getModleDesc(table.getSelection()[0].getText(0));
					if(obj instanceof BusinessClass){
						BusinessClassMessageDailog dialog=new BusinessClassMessageDailog(new Shell(),false,obj,null);
						if(dialog.open()==dialog.OK){
							
						}
					}else if(obj instanceof Enumeration){
						EnumMessageDialog dialog=new EnumMessageDialog(new Shell(),obj);
						if(dialog.open()==dialog.OK){
							
						}
					}else{
						BusinessObjectModelMessageDialog dialog=new BusinessObjectModelMessageDialog(new Shell(),obj);
						if(dialog.open()==dialog.OK){
							
						}
					}
				}
			}
		});
		
		table.addMouseTrackListener(new MouseTrackListener() {
			
			public void mouseHover(MouseEvent e) {
				table.setToolTipText("");
			}
			
			public void mouseExit(MouseEvent e) {
				
			}
			
			public void mouseEnter(MouseEvent e) {
				
			}
		});
		
		TableColumn id = new TableColumn(table, SWT.CENTER);
		id.setText("ID");
		id.setWidth(0);
		id.setResizable(false);
		
		TableColumn type = new TableColumn(table, SWT.CENTER);
		type.setText("类型");
		type.setWidth(40);
		type.setResizable(false);
		
		TableColumn name = new TableColumn(table, SWT.CENTER);
		name.setText("名称");
		name.setWidth(150);

		
		TableColumn desc = new TableColumn(table, SWT.CENTER);
		desc.setText("描述");
		desc.setWidth(150);
		
		TableColumn sharePerson = new TableColumn(table, SWT.CENTER);
		sharePerson.setText("共享人");
		sharePerson.setWidth(150);

		TableColumn createTime = new TableColumn(table, SWT.CENTER);
		createTime.setText("创建时间");
		createTime.setWidth(150);
	
		
	}

	
	/**
	 * 查询条件显示区域.
	 *
	 * @author mqfdy
	 * @param composite
	 *            the composite
	 * @Date 2018-09-03 09:00
	 */
	private void createSerchBoxGroup(Composite composite) {
		serchBoxGroup=new Group(composite,SWT.None);
		GridLayout layout=new GridLayout(8,false);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		serchBoxGroup.setLayout(layout);
		serchBoxGroup.setLayoutData(gridData);
		
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.widthHint=60;
		modelTypeLabel =new Label(serchBoxGroup,SWT.NONE);
		modelTypeLabel.setText("类型:");
		modelTypeLabel.setLayoutData(gridData);
		
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.widthHint=120;
		modelTypeCombo =new Combo(serchBoxGroup,SWT.BORDER|SWT.READ_ONLY);
		modelTypeCombo.setLayoutData(gridData);
		intiCombo();
		modelTypeCombo.select(0);
		
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.widthHint=60;
		modelNameLabel =new Label(serchBoxGroup,SWT.NONE);
		modelNameLabel.setText("名称:");
		modelNameLabel.setLayoutData(gridData);
		
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.widthHint=120;
		modelNameText =new Text(serchBoxGroup,SWT.BORDER);
		modelNameText.setLayoutData(gridData);
		
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.widthHint=60;
		modelDescLabel =new Label(serchBoxGroup,SWT.NONE);
		modelDescLabel.setText("描述:");
		modelDescLabel.setLayoutData(gridData);
		
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.widthHint=120;
		gridData.horizontalSpan=3;
		modelDescText =new Text(serchBoxGroup,SWT.BORDER);
		modelDescText.setLayoutData(gridData);
		
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.widthHint=60;
		sharePersonLabel =new Label(serchBoxGroup,SWT.NONE);
		sharePersonLabel.setText("共享人:");
		sharePersonLabel.setLayoutData(gridData);
		
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.widthHint=120;
		sharePersonText =new Text(serchBoxGroup,SWT.BORDER);
		sharePersonText.setLayoutData(gridData);
		
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.widthHint=60;
		startTimeLabel =new Label(serchBoxGroup,SWT.NONE);
		startTimeLabel.setText("创建时间:");
		startTimeLabel.setLayoutData(gridData);
		
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.widthHint=120;
		startText =new DateTime (serchBoxGroup,SWT.DATE|SWT.BORDER);
		startText.setLayoutData(gridData);
		startText.setYear(2000);
		startText.setMonth(0);
		startText.setDay(1);
		
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.widthHint=60;
		endTimeLabel =new Label(serchBoxGroup,SWT.NONE);
		endTimeLabel.setText("至:");
		endTimeLabel.setLayoutData(gridData);
		
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.widthHint=120;
		endText =new DateTime (serchBoxGroup,SWT.DATE|SWT.BORDER);
		endText.setLayoutData(gridData);
		endText.setDay(endText.getDay());
		
		gridData = new GridData(GridData.CENTER);
		gridData.widthHint=60;
		serach=new Button(serchBoxGroup,SWT.NONE);
		serach.setText("查询");
		serach.setLayoutData(gridData);
		serach.addSelectionListener(new SelectionListener() {			
			public void widgetSelected(SelectionEvent e) {
				createTableById();
			}
			
			public void widgetDefaultSelected(SelectionEvent e) {
				
			}
		});
		
		gridData = new GridData(GridData.CENTER);
		gridData.widthHint=60;
		reset=new Button(serchBoxGroup,SWT.NONE);
		reset.setText("重置");
		reset.setLayoutData(gridData);
		reset.addSelectionListener(new SelectionListener() {			
			public void widgetSelected(SelectionEvent e) {
				modelTypeCombo.select(0);
				modelNameText.setText("");
				modelDescText.setText("");
				sharePersonText.setText("");
				Calendar c = Calendar.getInstance();//可以对每个时间域单独修改

				int year = c.get(Calendar.YEAR); 
				int month = c.get(Calendar.MONTH); 
				int date = c.get(Calendar.DATE); 
				startText.setDate(2000, 0, 1);
				endText.setDate(year, month, date);
			}
			
			public void widgetDefaultSelected(SelectionEvent e) {
				
			}
		});
	}

	
	/**
	 * 初始化模型类型选择下拉框.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	private void intiCombo() {
		modelTypeCombo.add("--请选择--");
		modelTypeCombo.setData(0 + "", "模型文件");
		
		modelTypeCombo.add("模型文件");
		modelTypeCombo.setData(1 + "", "模型文件");
		
		modelTypeCombo.add("业务实体");
		modelTypeCombo.setData(2 + "", "业务实体");
		
		modelTypeCombo.add("枚举");
		modelTypeCombo.setData(3 + "", "枚举");
	}

	/**
	 * 树显示区域.
	 *
	 * @author mqfdy
	 * @param composite
	 *            the composite
	 * @Date 2018-09-03 09:00
	 */
	private void createTree(Composite composite) {
		GridData gridData = new GridData(GridData.FILL_BOTH);
		gridData.widthHint=200;
		treeView=new TreeViewer(composite,SWT.BORDER);
		treeView.setLabelProvider(new ModelMessageTreeLabelProvider());
		treeView.setContentProvider(new ModelMessageTreeContentProvider());	
		
		gridData = new GridData(GridData.FILL_BOTH);
		gridData.widthHint=260;
		gridData.verticalSpan=2;
		tree=treeView.getTree();
		treeScroll=new ScrolledComposite(tree,SWT.BORDER);
		treeScroll.setContent(tree);
		
		tree.setLayoutData(gridData);
		 List<JSONObject> list=treeData();
		if(list.size()==0){
			
		}else{
			treeView.setInput(list);	
			treeView.expandToLevel(3);
		}
		
		treeView.refresh();
		treeView.expandAll();
		
		tree.addSelectionListener(new SelectionListener() {			
			public void widgetSelected(SelectionEvent e) {
				createTableById();
			}
			
			public void widgetDefaultSelected(SelectionEvent e) {
				
			}
		});
	}

	/**
	 * 根据输入框的条件和所选树节点ID获取共享模型信息.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	protected void createTableById() {
		TreeItem[] item=tree.getSelection();
		String treeNodeId=null;
		if(item.length>0){					
			try {
				JSONObject obj=(JSONObject) item[0].getData();
				treeNodeId=obj.getString("id");	
				table.removeAll();
				List<JSONObject> list=new ArrayList<JSONObject>();
				String url = FindModelDialog.URL+"/CreateTableServlet";
				Map<String,String> params=new HashMap<String,String>();
				params.put("ssxm", treeNodeId);
				if(modelTypeCombo.getSelectionIndex()>0){
					params.put("modelType", modelTypeCombo.getSelectionIndex()-1+"");
				}
				if(null!=modelNameText.getText()&&!"".equals(modelNameText.getText())){
					params.put("modelName", modelNameText.getText());
				}
				if(null!=modelDescText.getText()&&!"".equals(modelDescText.getText())){
					params.put("desc", modelDescText.getText());
				}
				if(null!=sharePersonText.getText()&&!"".equals(sharePersonText.getText())){
					params.put("sharePerson", sharePersonText.getText());
				}
				params.put("startTime", getDateStr(startText.getYear(),startText.getMonth(),startText.getDay()));
				params.put("endTime", getDateStr(endText.getYear(),endText.getMonth(),endText.getDay()));
				JSONArray jsonArr;
				try {
					jsonArr = new JSONArray(ShareModelActionHttpclient.getDoPostResponseDataByURL(url, params ,  "utf-8" ,  true));
					
					for(int i=0, len=jsonArr.length(); i<len; i++) {
						list.add(jsonArr.optJSONObject(i));				
					}
					tableViewer.setInput(list);
					tableViewer.refresh();
				} catch (Exception e) {
					e.printStackTrace();
				}
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * 通过日期输入框获取日期格式字符串.
	 *
	 * @author mqfdy
	 * @param year
	 *            the year
	 * @param month
	 *            // 8月通过getMonth取出来是7
	 * @param day
	 *            the day
	 * @return the date str
	 * @Date 2018-09-03 09:00
	 */
	private String getDateStr(int year, int month, int day) {
		String str=String.valueOf(year)+"-";
		if(month<=8){
			str=str.concat("0").concat(String.valueOf(month+1)).concat("-");
		}else{
			str=str.concat(String.valueOf(month+1)).concat("-");
		}
		if(day<=8){
			str=str.concat("0").concat(String.valueOf(day));
		}else{
			str=str.concat(String.valueOf(day));
		}
		return str;
	}

	/**
	 * 获取树节点信息.
	 *
	 * @author mqfdy
	 * @return the list
	 * @Date 2018-09-03 09:00
	 */
	public  List<JSONObject>  treeData(){
		List<JSONObject> list=new ArrayList<JSONObject>();
		String url = FindModelDialog.URL+"/CreateTreeServlet";
		
		JSONArray jsonArr;
		try {
			String jsonStr=ShareModelActionHttpclient.getDoPostResponseDataByURL(url, null ,  "utf-8" ,  true);
			if(ShareModelActionHttpclient.FAILCONNECTION.equals(jsonStr)){
				findModelDialog.setMessage(jsonStr, IMessageProvider.INFORMATION);
				return list;
			}
			jsonArr = new JSONArray(jsonStr);
			
			for(int i=0, len=jsonArr.length(); i<len; i++) {
				list.add(jsonArr.optJSONObject(i));				
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	
}
