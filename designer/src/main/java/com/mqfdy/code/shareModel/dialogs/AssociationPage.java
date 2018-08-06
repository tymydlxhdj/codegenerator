package com.mqfdy.code.shareModel.dialogs;

import java.util.List;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import com.mqfdy.code.model.Association;
import com.mqfdy.code.model.utils.AssociationType;


/**
 * 关联关系tab页
 * @author mqfdy
 *
 */
public class AssociationPage extends Composite{
	private TableViewer tableViewer;
	public Table table;
	
	public AssociationPage(Composite folder) {
		super(folder, SWT.NONE);
		createContents(this);
	}	

	/**
	 * 创建界面
	 * @param composite
	 */
	private void createContents(Composite composite) {
		composite.setLayout(new GridLayout(1,false));	
		
		table = new Table(composite, SWT.BORDER|SWT.FULL_SELECTION|SWT.SINGLE);
		GridData gridData = new GridData(GridData.FILL_BOTH);
		ScrolledComposite tableScroll = new ScrolledComposite(table,
					SWT.BORDER);
		tableScroll.setContent(table);

		setTableViewer(new TableViewer(table));
		gridData.heightHint = 380;
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		table.setLayoutData(gridData);
		
		TableColumn name = new TableColumn(table, SWT.CENTER);
		name.setText("名称");
		name.setWidth(120);
		
		TableColumn a = new TableColumn(table, SWT.CENTER);
		a.setText("实体A");
		a.setWidth(120);
		
		TableColumn b = new TableColumn(table, SWT.CENTER);
		b.setText("实体B");
		b.setWidth(120);

		
		TableColumn relationType = new TableColumn(table, SWT.CENTER);
		relationType.setText("关联关系");
		relationType.setWidth(150);
		
		TableColumn navigate = new TableColumn(table, SWT.CENTER);
		navigate.setText("导航关系");
		navigate.setWidth(150);
		
	}

	public void initAssInfoPage(List<Association> assList) {
		for(Association ass:assList){
			 TableItem item=new TableItem(table, SWT.NONE);
			    item.setData(ass);
			    item.setText(0, ass.getName());
			    item.setText(1,ass.getClassA().getDisplayName());
			    item.setText(2, ass.getClassB().getDisplayName());
			    item.setText(3, getAssociationType(ass.getAssociationType()));
			    item.setText(4, getNavigate(ass));
		}
	}	
	
	/**
	 * 获取导航关系
	 */
	private String getNavigate(Association ass) {
		if(ass.isNavigateToClassA()==true&&ass.isCascadeDeleteClassB()==false){
			return "B导航到A";
		}else if(ass.isNavigateToClassA()==false&&ass.isCascadeDeleteClassB()==true){
			return "A导航到B";
		}else
			return "双向导航";
	}

	/**
	 * 获取关联关系类型
	 * @param type
	 * @return
	 */
	private String getAssociationType(String type) {
		if(AssociationType.mult2mult.getValue().equals(type)){
			return AssociationType.mult2mult.getDisplayValue();
		}else if(AssociationType.one2one.getValue().equals(type)){
			return AssociationType.one2one.getDisplayValue();
		}else if(AssociationType.one2mult.getValue().equals(type)){
			return AssociationType.one2mult.getDisplayValue();
		}else {
			return AssociationType.mult2one.getDisplayValue();
		}
	}

	public TableViewer getTableViewer() {
		return tableViewer;
	}

	public void setTableViewer(TableViewer tableViewer) {
		this.tableViewer = tableViewer;
	}
}
