package com.mqfdy.code.reverse.views.pages;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.ICellEditorListener;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import com.mqfdy.code.reverse.IOmReverse;
import com.mqfdy.code.reverse.OmReverse;
import com.mqfdy.code.reverse.ReverseContext;
import com.mqfdy.code.reverse.mappings.Table;
import com.mqfdy.code.reverse.views.beans.SpecialTable;
import com.mqfdy.code.reverse.views.constant.IViewConstant;
import com.mqfdy.code.reverse.views.models.SpecialTableModel;
import com.mqfdy.code.reverse.views.providers.DupliTableLabelProvider;
import com.mqfdy.code.reverse.views.providers.TableContentProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class DuplicateNameWizardPage.
 *
 * @author mqfdy
 */
public class DuplicateNameWizardPage extends WizardPage {

	/** The Constant COLUMN1_WIDTH. */
	public static final int COLUMN1_WIDTH = 250;
	
	/** The Constant COLUMN2_WIDTH. */
	public static final int COLUMN2_WIDTH = 250;
	
	/** The Constant COLUMN3_WIDTH. */
	public static final int COLUMN3_WIDTH = 170;
	
	/** The container. */
	private Composite container;
	
	/** The viewer. */
	private TableViewer viewer;
	
	/** The special tables. */
	private List<SpecialTable> specialTables = new ArrayList<SpecialTable>();
	
	/** The Constant HANDLE_TYPES. */
	public static final String[] HANDLE_TYPES = new String[] {"忽略", "替换"};

	/** The duplication table names. */
	private List<Table> duplicationTableNames = new ArrayList<Table>();//重复表名集合
	
	/** The om reverse. */
	private IOmReverse omReverse;
	
	/** The label. */
	private Label label;
	
	/**
	 * Gets the special tables.
	 *
	 * @author mqfdy
	 * @return the special tables
	 * @Date 2018-09-03 09:00
	 */
	public List<SpecialTable> getSpecialTables() {
		return specialTables;
	}

	/**
	 * Sets the special tables.
	 *
	 * @author mqfdy
	 * @param specialTables
	 *            the new special tables
	 * @Date 2018-09-03 09:00
	 */
	public void setSpecialTables(List<SpecialTable> specialTables) {
		this.specialTables = specialTables;
	}

	/**
	 * Gets the duplication table names.
	 *
	 * @author mqfdy
	 * @return the duplication table names
	 * @Date 2018-09-03 09:00
	 */
	public List<Table> getDuplicationTableNames() {
		return duplicationTableNames;
	}

	/**
	 * Sets the duplication table names.
	 *
	 * @author mqfdy
	 * @param duplicationTableNames
	 *            the new duplication table names
	 * @Date 2018-09-03 09:00
	 */
	public void setDuplicationTableNames(List<Table> duplicationTableNames) {
		this.duplicationTableNames = duplicationTableNames;
	}

	/**
	 * Instantiates a new duplicate name wizard page.
	 *
	 * @param pageName
	 *            the page name
	 */
	public DuplicateNameWizardPage(String pageName) {
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
		setTitle("以下表名与现有模型重复");
		setMessage("选择“替换”,会替换业务实体的所有信息.", IMessageProvider.INFORMATION);
		container = new Composite(parent, SWT.NULL);
		
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 1;
		
		GridData gridData = new GridData(GridData.FILL_BOTH);
		container.setLayout(gridLayout);
		container.layout();

		viewer = new TableViewer(container, SWT.SINGLE | SWT.BORDER | SWT.FULL_SELECTION);
		final org.eclipse.swt.widgets.Table table = viewer.getTable();
		
		table.setLayoutData(gridData);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		viewer.setContentProvider(new TableContentProvider());
		viewer.setLabelProvider(new DupliTableLabelProvider());
		
		//创建表格列
		TableColumn nameColumn = new TableColumn(table, SWT.NONE);
		nameColumn.setText("表名");
		nameColumn.setWidth(COLUMN1_WIDTH);
		
		TableColumn descColumn = new TableColumn(table, SWT.NONE);
		descColumn.setWidth(COLUMN2_WIDTH);
		descColumn.setText("显示名");
		
		TableColumn handleColumn = new TableColumn(table, SWT.NONE);
		handleColumn.setWidth(COLUMN3_WIDTH);
		handleColumn.setText("处理");
		
		viewer.setColumnProperties(new String[] { "name", "desc", "handle" });
		final CellEditor[] cellEditor = new CellEditor[3];
		cellEditor[2] = new ComboBoxCellEditor(table, HANDLE_TYPES, SWT.READ_ONLY);		
		cellEditor[2].addListener(new ICellEditorListener() {
			
			@Override
			public void editorValueChanged(boolean oldValidState, boolean newValidState) {
				cellEditor[2].setFocus();
				table.select(0);
			}
			
			@Override
			public void cancelEditor() {
				cellEditor[2].setFocus();
			}
			
			@Override
			public void applyEditorValue() {				
				cellEditor[2].setFocus();
			}
		});
		viewer.setCellEditors(cellEditor);
		
		viewer.setCellModifier(new ICellModifier() {
			public boolean canModify(Object element, String property) {
				return true;
			}

			public Object getValue(Object element, String property) {
				try {
					SpecialTable specialTable = (SpecialTable) element;
					if("name".equals(property) ){
						return specialTable.getName();
					} else if("desc".equals(property)) {
						return specialTable.getDesc();
					} else if("handle".equals(property)) {
						String handleText = specialTable.getHandleText();
						if(HANDLE_TYPES[0].equals(handleText)) {
							specialTable.setHandle(false);
						} else {
							specialTable.setHandle(true);
						}
						return getNameIndex(specialTable.getHandleText());
					}
					
					return "";
				} catch (Exception e) {
					e.printStackTrace();
					throw new RuntimeException(e);
				}
			}

			public void modify(Object element, String property, Object value) {
				TableItem item = (TableItem) element;
				SpecialTable specialTable = (SpecialTable) item.getData();
				Integer index = (Integer) value;
				if ("handle".equals(property)) {
					if (index.intValue() == -1) {
						return;
					}
					String newname = HANDLE_TYPES[index.intValue()];
					if(HANDLE_TYPES[0].equals(newname)) {
						specialTable.setHandle(false);
					} else {
						specialTable.setHandle(true);
					}
					specialTable.setHandleText(newname);
				}
				viewer.update(specialTable, null);
				
				if(isCanNext()){
					setPageComplete(true);
				}else{
					setPageComplete(false);
				}
			}
		});
		viewer.refresh();
		
		label = new Label(container, SWT.NULL);
		label.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));
		
		setControl(container);

	}
  
	/**
	 * Vali finish.
	 *
	 * @author mqfdy
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	public boolean valiFinish() {
		SpecialTableModel model = (SpecialTableModel)viewer.getInput();
		if(model == null){
			return false;
		}
		Object [] tables = model.elements();
		if(tables == null){
			return false;
		}
		boolean isFinish = true;
		//SpecialTable[] tables = (SpecialTable [])model.elements();
		if(tables!=null){
			for(Object object : tables){
				SpecialTable table = (SpecialTable)object;
				if(table.isHandle() == true){
					isFinish = false;
				}
			}
		}
		if(tables!=null && tables.length!=ReverseContext.selectedTables.size()){
			isFinish = false;
		}
		return isFinish;
	}

	/**
	 * Gets the name index.
	 *
	 * @author mqfdy
	 * @param name
	 *            the name
	 * @return the name index
	 * @Date 2018-09-03 09:00
	 */
	private int getNameIndex(String name) {
		for (int i = 0; i < HANDLE_TYPES.length; i++) {
			if (HANDLE_TYPES[i].equals(name)) {
				return i;
			}
		}
		return -1;
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
		for(Table table: duplicationTableNames) {
			specialTable = new SpecialTable();
			specialTable.setName(table.getName());
			specialTable.setDesc(table.getComment());
			specialTable.setProblemType(IViewConstant.TYPE_DUPLICATE_NAME);
			specialTable.setHandleText(HANDLE_TYPES[1]);
			specialTable.setHandle(true);
			input.add(specialTable);
		}
		
		viewer.setInput(input);
		
		label.setText("共有重复表名的业务实体" + duplicationTableNames.size() + "个");
		container.layout();
	}
	
	/**
	 * Checks if is can next.
	 *
	 * @author mqfdy
	 * @return true, if is can next
	 * @Date 2018-09-03 09:00
	 */
	private boolean isCanNext(){
		boolean bNext = true;
		SpecialTableModel model = (SpecialTableModel)viewer.getInput();
		Object [] tables = model.elements();
		//SpecialTable[] tables = (SpecialTable [])model.elements();
		if(tables!=null && tables.length==ReverseContext.selectedTables.size()){
			bNext = false;
			for(Object object : tables){
				SpecialTable table = (SpecialTable)object;
				if(table.isHandle() == true){
					bNext = true;
				}
			}
		}
		return bNext;
	}

	/**
	 * @return
	 */
	@Override
	public boolean canFlipToNextPage() {
		return isCanNext();
	}

	/**
	 * @return
	 */
	@Override
	public IWizardPage getNextPage() {
		List<SpecialTable> sTables = new ArrayList<SpecialTable>();
		
		SpecialTableModel input = (SpecialTableModel) viewer.getInput();
		if(input != null) {
			Object[] objects = input.elements();
			for(Object object: objects) {
				sTables.add((SpecialTable) object);
			}
		}
		setSpecialTables(sTables);
		//处理重复表
		omReverse.handleDuplicateTable(sTables);
		OmNameReverseStrategyPage nextPage = (OmNameReverseStrategyPage) super.getNextPage();
		nextPage.repaint();
		return nextPage;
	}
	
}
