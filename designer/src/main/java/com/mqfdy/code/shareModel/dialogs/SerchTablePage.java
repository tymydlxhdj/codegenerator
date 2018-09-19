package com.mqfdy.code.shareModel.dialogs;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.json.JSONArray;

import com.mqfdy.code.designer.editor.actions.ShareModelActionHttpclient;
import com.mqfdy.code.model.BusinessClass;
import com.mqfdy.code.model.Enumeration;
import com.mqfdy.code.shareModel.providers.DataGridLabelProvider;
import com.mqfdy.code.shareModel.providers.DataGridTableContentProvider;
// TODO: Auto-generated Javadoc

/**
 * 业务实体选择tab页.
 *
 * @author mqfdy
 */
public class SerchTablePage extends Composite{
	
	/** The key word text. */
	private Text keyWordText;
	
	/** The serach button. */
	private Button serachButton;
	
	/** The table viewer. */
	private TableViewer tableViewer;
	
	/** The table. */
	public Table table;
	
	/** The table group. */
	private Group tableGroup;
	
	/** The find model dialog. */
	private FindModelDialog findModelDialog;
	
	/**
	 * Instantiates a new serch table page.
	 *
	 * @param parent
	 *            the parent
	 * @param findModelDialog
	 *            the find model dialog
	 */
	public SerchTablePage(Composite parent,FindModelDialog findModelDialog) {
		super(parent, SWT.NONE);
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
			createSerchBoxGroup(composite);
			createTableGroup(composite);
	}


	/**
	 * 创建表格区域.
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
		gridData.horizontalSpan=2;
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
		name.setWidth(180);

		
		TableColumn desc = new TableColumn(table, SWT.CENTER);
		desc.setText("描述");
		desc.setWidth(180);
		
		TableColumn sharePerson = new TableColumn(table, SWT.CENTER);
		sharePerson.setText("共享人");
		sharePerson.setWidth(180);

		TableColumn createTime = new TableColumn(table, SWT.CENTER);
		createTime.setText("创建时间");
		createTime.setWidth(180);
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
	}


	/**
	 * Creates the serch box group.
	 *
	 * @author mqfdy
	 * @param composite
	 *            the composite
	 * @Date 2018-09-03 09:00
	 */
	private void createSerchBoxGroup(Composite composite) {
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.widthHint=200;
		
		keyWordText =new Text(composite,SWT.BORDER);
		keyWordText.setLayoutData(gridData);
		
		gridData = new GridData(GridData.BEGINNING);
		
		serachButton =new Button(composite,SWT.NONE);
		serachButton.setLayoutData(gridData);
		serachButton.setText("搜索");
		serachButton.addSelectionListener(new SelectionListener() {			
			public void widgetSelected(SelectionEvent e) {
				createTableById();
			}
			
			public void widgetDefaultSelected(SelectionEvent e) {
				
			}
		});
	}

	/**
	 * Creates the table by id.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	protected void createTableById() {
			if(keyWordText.getText()==null||"".equals(keyWordText.getText())){
				return;
			}
			List list=new ArrayList();
			table.removeAll();
			String url = FindModelDialog.URL+"/CreateTableServlet";
			Map<String,String> params=new HashMap<String,String>();
			params.put("keyWords", keyWordText.getText());
			JSONArray jsonArr;
			try {
				String jsonStr=ShareModelActionHttpclient.getDoPostResponseDataByURL(url, params ,  "utf-8" ,  true);
				if(ShareModelActionHttpclient.FAILCONNECTION.equals(jsonStr)){
					findModelDialog.setMessage(jsonStr, IMessageProvider.INFORMATION);
					return;
				}
				jsonArr = new JSONArray(jsonStr);
				
				for(int i=0, len=jsonArr.length(); i<len; i++) {
					list.add(jsonArr.optJSONObject(i));				
				}
				tableViewer.setInput(list);
				tableViewer.refresh();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	
}
