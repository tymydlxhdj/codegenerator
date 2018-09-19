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
import org.eclipse.swt.events.MouseListener;
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
import org.eclipse.swt.widgets.Shell;
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
import com.mqfdy.code.model.Association;
import com.mqfdy.code.model.BusinessClass;
import com.mqfdy.code.model.BusinessObjectModel;
import com.mqfdy.code.model.BusinessOperation;
import com.mqfdy.code.model.Enumeration;
import com.mqfdy.code.shareModel.providers.DataGridLabelProvider;
import com.mqfdy.code.shareModel.providers.DataGridTableContentProvider;
// TODO: Auto-generated Javadoc

/**
 * 显示业务实体文件包含的所有枚举类型.
 *
 * @author mqfdy
 */
public class EnumrationInfoPage extends Composite{
	
	/** The table viewer. */
	private TableViewer tableViewer;
	
	/** The table. */
	public Table table;

	/**
	 * Instantiates a new enumration info page.
	 *
	 * @param parent
	 *            the parent
	 */
	public EnumrationInfoPage(Composite parent) {
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
			name.setText("名称");
			name.setWidth(150);
			
			TableColumn displayName = new TableColumn(table, SWT.CENTER);
			displayName.setText("显示名称");
			displayName.setWidth(150);
			
			table.addMouseListener(new MouseListener() {
				
				public void mouseUp(MouseEvent e) {
				}
				
				public void mouseDown(MouseEvent e) {			
				}
				
				public void mouseDoubleClick(MouseEvent e) {
					if(table.getSelection().length>0){
						Object en=table.getSelection()[0].getData();					
						if(en instanceof Enumeration){
							EnumMessageDialog dialog=new EnumMessageDialog(new Shell(),en);
							if(dialog.open()==dialog.OK){
								
							}
						}
					}
				}
			});
			
	}
	
	/**
	 * 初始化表格数据(根据业务模型文件).
	 *
	 * @author mqfdy
	 * @param obj
	 *            the obj
	 * @Date 2018-09-03 09:00
	 */
	public void initData(Object obj){
		BusinessObjectModel model=(BusinessObjectModel)obj;
		for(Enumeration en:model.getEnumerations()){
			 TableItem item=new TableItem(table, SWT.NONE);
			    item.setData(en);
			    item.setText(0, en.getName());
			    item.setText(1, en.getDisplayName());
		}
	}
	
}
