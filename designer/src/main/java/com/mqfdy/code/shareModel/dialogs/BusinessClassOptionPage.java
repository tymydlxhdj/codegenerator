package com.mqfdy.code.shareModel.dialogs;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mqfdy.code.designer.editor.actions.ShareModelActionHttpclient;
import com.mqfdy.code.model.BusinessClass;
import com.mqfdy.code.model.BusinessOperation;
import com.mqfdy.code.shareModel.providers.DataGridLabelProvider;
import com.mqfdy.code.shareModel.providers.DataGridTableContentProvider;
// TODO: Auto-generated Javadoc

/**
 * 业务实体操作tab页.
 *
 * @author mqfdy
 */
public class BusinessClassOptionPage extends Composite{
	
	/** The table viewer. */
	private TableViewer tableViewer;
	
	/** The table. */
	public Table table;
	
	/** The table group. */
	private Group tableGroup;

	/**
	 * Instantiates a new business class option page.
	 *
	 * @param parent
	 *            the parent
	 */
	public BusinessClassOptionPage(Composite parent) {
		super(parent, SWT.NONE);
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
			createTableGroup(composite);
	}


	/**
	 * Creates the table group.
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
		gridData.heightHint = 380;
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		table.setLayoutData(gridData);
		
		TableColumn name = new TableColumn(table, SWT.CENTER);
		name.setText("名称");
		name.setWidth(150);
		
		TableColumn opratorName = new TableColumn(table, SWT.CENTER);
		opratorName.setText("方法名");
		opratorName.setWidth(150);
		
	}

	/**
	 * 初始化业务实体的操作信息.
	 *
	 * @author mqfdy
	 * @param obj
	 *            the obj
	 * @Date 2018-09-03 09:00
	 */
	public void initOptionInfoPage(Object obj) {
		BusinessClass bs=(BusinessClass)obj;
		 for(BusinessOperation operation :bs.getOperations()){
			 TableItem item=new TableItem(table, SWT.NONE);
			 item.setData(operation);
			 item.setText(0,operation.getDisplayName());
			 item.setText(1,operation.getName());
		}
	}


	
}
