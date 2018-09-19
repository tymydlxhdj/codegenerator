package com.mqfdy.code.shareModel.dialogs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;

import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
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
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mqfdy.code.designer.editor.actions.ShareModelActionHttpclient;
import com.mqfdy.code.model.Association;
import com.mqfdy.code.model.BusinessClass;
import com.mqfdy.code.model.BusinessObjectModel;
import com.mqfdy.code.model.Enumeration;
import com.mqfdy.code.model.PersistenceProperty;
import com.mqfdy.code.model.Property;
import com.mqfdy.code.model.utils.EditorType;
import com.mqfdy.code.shareModel.providers.DataGridLabelProvider;
import com.mqfdy.code.shareModel.providers.DataGridTableContentProvider;
import com.mqfdy.code.shareModel.providers.ModelMessageTreeContentProvider;
import com.mqfdy.code.shareModel.providers.ModelMessageTreeLabelProvider;


// TODO: Auto-generated Javadoc
/**
 * 业务实体选择tab页.
 *
 * @author chenwanli
 */
public class BusinessClassInfoPage extends Composite{	
	
	/** The table viewer. */
	private TableViewer tableViewer;
	
	/** The model. */
	private BusinessObjectModel model;
	
	/** The table. */
	public Table table;
	
	/**
	 * Instantiates a new business class info page.
	 *
	 * @param folder
	 *            the folder
	 */
	public BusinessClassInfoPage(Composite folder) {
		super(folder, SWT.NONE);
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
		composite.setLayout(new GridLayout(1,false));	
		
		table = new Table(composite, SWT.BORDER|SWT.FULL_SELECTION|SWT.SINGLE);
		GridData gridData = new GridData(GridData.FILL_BOTH);
		ScrolledComposite tableScroll = new ScrolledComposite(table,
					SWT.BORDER);
		tableScroll.setContent(table);

		tableViewer = new TableViewer(table);
		gridData.heightHint = 380;
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		table.setLayoutData(gridData);
		
		TableColumn name = new TableColumn(table, SWT.CENTER);
		name.setText("业务实体名称");
		name.setWidth(120);
		
		TableColumn displayName = new TableColumn(table, SWT.CENTER);
		displayName.setText("业务实体显示名称");
		displayName.setWidth(120);
		
		TableColumn type = new TableColumn(table, SWT.CENTER);
		type.setText("数据库表名");
		type.setWidth(120);

		
		TableColumn editorType = new TableColumn(table, SWT.CENTER);
		editorType.setText("备注");
		editorType.setWidth(150);	
		
		table.addMouseListener(new MouseListener() {
			
			public void mouseUp(MouseEvent e) {
			}
			
			public void mouseDown(MouseEvent e) {			
			}
			
			public void mouseDoubleClick(MouseEvent e) {
				if(table.getSelection().length>0){
					Object bsClass=table.getSelection()[0].getData();					
					if(bsClass instanceof BusinessClass){
						List<Association> list=new ArrayList<Association>();
						if(model !=null){
							  for(Association ass:model.getAssociations()){
								  if(ass.getClassAid().equals(((BusinessClass)bsClass).getId())||ass.getClassBid().equals(((BusinessClass)bsClass).getId())){
									  list.add(ass);
								  }
							  }
						}
						BusinessClassMessageDailog dialog=new BusinessClassMessageDailog(new Shell(),true,bsClass,list);
						if(dialog.open()==dialog.OK){
							
						}
					}
				}
			}
		});

	}
	
	/**
	 * Inits the data.
	 *
	 * @author mqfdy
	 * @param obj
	 *            the obj
	 * @Date 2018-09-03 09:00
	 */
	public void initData(Object obj){
		model=(BusinessObjectModel)obj;
		for(BusinessClass bs:model.getBusinessClasses()){
			 TableItem item=new TableItem(table, SWT.NONE);
			    item.setData(bs);
			    item.setText(0, bs.getName());
			    item.setText(1, bs.getDisplayName());
			    item.setText(2, bs.getTableName());
			    item.setText(3, bs.getRemark()==null?"":bs.getRemark());
		}
		
	}
}
	

