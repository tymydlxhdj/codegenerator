package com.mqfdy.code.reverse.views.pages;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TableColumn;

import com.mqfdy.code.reverse.IOmReverse;
import com.mqfdy.code.reverse.OmReverse;
import com.mqfdy.code.reverse.ReverseContext;
import com.mqfdy.code.reverse.mappings.Table;
import com.mqfdy.code.reverse.views.beans.SpecialTable;
import com.mqfdy.code.reverse.views.constant.IViewConstant;
import com.mqfdy.code.reverse.views.models.SpecialTableModel;
import com.mqfdy.code.reverse.views.providers.TableContentProvider;
import com.mqfdy.code.reverse.views.providers.TableLabelProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class SpecialTableWizardPage.
 *
 * @author mqfdy
 */
public class SpecialTableWizardPage extends WizardPage {

	/** The Constant COLUMN1_WIDTH. */
	public static final int COLUMN1_WIDTH = 300;
	
	/** The Constant COLUMN2_WIDTH. */
	public static final int COLUMN2_WIDTH = 100;
	
	/** The Constant COLUMN3_WIDTH. */
	public static final int COLUMN3_WIDTH = 250;
	
	/** The container. */
	private Composite container;
	
	/** The viewer. */
	private TableViewer viewer;
	
	/** The no PK table list. */
	private List<Table> noPKTableList = new ArrayList<Table>();
	
	/** The muti pk table list. */
	private List<Table> mutiPkTableList = new ArrayList<Table>();
	
	/** The special char table list. */
	private List<Table> specialCharTableList = new ArrayList<Table>();
	
	/** The start with figure table list. */
	private List<Table> startWithFigureTableList = new ArrayList<Table>();
	
	/** The om reverse. */
	private IOmReverse omReverse;
	
	/** The label. */
	private Label label;
	
	/**
	 * Instantiates a new special table wizard page.
	 *
	 * @param pageName
	 *            the page name
	 */
	public SpecialTableWizardPage(String pageName) {
		super(pageName);
		omReverse = new OmReverse();
	}

	/**
	 * Creates the control.
	 *
	 * @author mqfdy
	 * @param parent
	 *            the parent
	 * @Date 2018-09-03 09:00
	 */
	public void createControl(Composite parent) {
		setTitle("无主键、复合主键、非法字段名称的处理");
		setMessage("对于无主键、复合主键、含有非法字段名称的表，系统暂不支持", IMessageProvider.INFORMATION);
		container = new Composite(parent, SWT.NULL);
		
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 1;
		
		GridData gridData = new GridData(GridData.FILL_BOTH);
		container.setLayout(gridLayout);
		container.layout();

		viewer = new TableViewer(container, SWT.SINGLE | SWT.BORDER | SWT.FULL_SELECTION);
		org.eclipse.swt.widgets.Table table = viewer.getTable();
		
		table.setLayoutData(gridData);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		//创建表格列
		TableColumn nameColumn = new TableColumn(table, SWT.NONE);
		nameColumn.setText("表名");
		nameColumn.setWidth(COLUMN1_WIDTH);
		
		TableColumn descColumn = new TableColumn(table, SWT.NONE);
		descColumn.setWidth(COLUMN2_WIDTH);
		descColumn.setText("问题描述");
		
		TableColumn msgColumn = new TableColumn(table, SWT.NONE);
		msgColumn.setWidth(COLUMN3_WIDTH);
		msgColumn.setText("提示");
		
		viewer.setContentProvider(new TableContentProvider());
		viewer.setLabelProvider(new TableLabelProvider());
		
		label = new Label(container, SWT.NULL);
		label.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));
		setControl(container);

	}
  
	/**
	 * Repaint.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	public void repaint() {
		
		SpecialTableModel input = new SpecialTableModel();
		
		SpecialTable specialTable = null;
		if(noPKTableList != null) {
			for(Table table: noPKTableList) {
				specialTable = new SpecialTable();
				specialTable.setName(table.getName());
				specialTable.setDesc("无主键");
				specialTable.setProblemType(IViewConstant.TYPE_NO_PK_TABLE);
				specialTable.setPrompt("请添加主键后重新操作");
				specialTable.setHandle(false);
				input.add(specialTable);
			}
		}
		
		if(mutiPkTableList != null) {
			for(Table table: mutiPkTableList) {
				specialTable = new SpecialTable();
				specialTable.setName(table.getName());
				specialTable.setDesc("复合主键");
				specialTable.setProblemType(IViewConstant.TYPE_MULTI_TABLE);
				specialTable.setPrompt("请修改成单主键后重新操作");
				specialTable.setHandle(false);
				input.add(specialTable);
			}
		}
		if(specialCharTableList != null) {
			for(Table table: specialCharTableList) {
				specialTable = new SpecialTable();
				specialTable.setName(table.getName());
				specialTable.setDesc("表中字段名含有特殊字符");
				specialTable.setProblemType(IViewConstant.TYPE_SPECIAL_CHAR_TABLE);
				specialTable.setPrompt("目前只支持表中字段名称含有字母、数字、下划线，请修改表中含有特殊字符的字段名称后重新操作");
				specialTable.setHandle(false);
				input.add(specialTable);
			}
		}
		
		if(startWithFigureTableList != null) {
			for(Table table: startWithFigureTableList) {
				specialTable = new SpecialTable();
				specialTable.setName(table.getName());
				specialTable.setDesc("表中字段名以数字开头");
				specialTable.setProblemType(IViewConstant.TYPE_SPECIAL_CHAR_TABLE);
				specialTable.setPrompt("请修改表中以数字开头的字段名称后重新操作");
				specialTable.setHandle(false);
				input.add(specialTable);
			}
		}
		
		viewer.setInput(input);
		
		if((noPKTableList.size() + mutiPkTableList.size() + specialCharTableList.size() + startWithFigureTableList.size()) == ReverseContext.selectedTables.size()) {
			setPageComplete(false);
		} else {
			setPageComplete(true);
		}
		
		label.setText("共检查出不合规的表" + (noPKTableList.size() + mutiPkTableList.size()) + specialCharTableList.size() + startWithFigureTableList.size() + "个");
		container.layout();
	}
	
	/**
	 * @return
	 */
	@Override
	public boolean canFlipToNextPage() {
		return  isPageComplete();
	}

	/**
	 * @return
	 */
	public IWizardPage getNextPage() {
		
		SpecialTableModel input = (SpecialTableModel) viewer.getInput();
		//取得表格中的集合
		if(input != null) {
			Object[] objects = input.elements();
			//如果当前页面含有无主键、复合主键的表
			if(objects != null && objects.length != 0) {
				List<SpecialTable> list = new ArrayList<SpecialTable>();
				for(Object object: objects) {
					list.add((SpecialTable) object);
				}
				//处理特殊表 无主键表，自动加上一个主键。 复合主键表，自动改造为单一主键，其关联的子表中的复合外键改造为单一外键
				omReverse.handleSpecialTable(list);
			}
		}
		
		return super.getNextPage();
	}

	/**
	 * Gets the no PK table list.
	 *
	 * @author mqfdy
	 * @return the no PK table list
	 * @Date 2018-09-03 09:00
	 */
	public List<Table> getNoPKTableList() {
		return noPKTableList;
	}

	/**
	 * Sets the no PK table list.
	 *
	 * @author mqfdy
	 * @param noPKTableList
	 *            the new no PK table list
	 * @Date 2018-09-03 09:00
	 */
	public void setNoPKTableList(List<Table> noPKTableList) {
		this.noPKTableList = noPKTableList;
	}

	/**
	 * Gets the muti pk table list.
	 *
	 * @author mqfdy
	 * @return the muti pk table list
	 * @Date 2018-09-03 09:00
	 */
	public List<Table> getMutiPkTableList() {
		return mutiPkTableList;
	}

	/**
	 * Sets the muti pk table list.
	 *
	 * @author mqfdy
	 * @param mutiPkTableList
	 *            the new muti pk table list
	 * @Date 2018-09-03 09:00
	 */
	public void setMutiPkTableList(List<Table> mutiPkTableList) {
		this.mutiPkTableList = mutiPkTableList;
	}
	
	/**
	 * Gets the special char table list.
	 *
	 * @author mqfdy
	 * @return the special char table list
	 * @Date 2018-09-03 09:00
	 */
	public List<Table> getSpecialCharTableList() {
		return specialCharTableList;
	}

	/**
	 * Sets the special char table list.
	 *
	 * @author mqfdy
	 * @param specialCharTableList
	 *            the new special char table list
	 * @Date 2018-09-03 09:00
	 */
	public void setSpecialCharTableList(List<Table> specialCharTableList) {
		this.specialCharTableList = specialCharTableList;
	}

	/**
	 * Gets the start with figure table list.
	 *
	 * @author mqfdy
	 * @return the start with figure table list
	 * @Date 2018-09-03 09:00
	 */
	public List<Table> getStartWithFigureTableList() {
		return startWithFigureTableList;
	}

	/**
	 * Sets the start with figure table list.
	 *
	 * @author mqfdy
	 * @param startWithFigureTableList
	 *            the new start with figure table list
	 * @Date 2018-09-03 09:00
	 */
	public void setStartWithFigureTableList(List<Table> startWithFigureTableList) {
		this.startWithFigureTableList = startWithFigureTableList;
	}

}
