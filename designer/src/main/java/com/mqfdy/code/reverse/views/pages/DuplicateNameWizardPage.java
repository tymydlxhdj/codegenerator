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

public class DuplicateNameWizardPage extends WizardPage {

	public static final int COLUMN1_WIDTH = 250;
	public static final int COLUMN2_WIDTH = 250;
	public static final int COLUMN3_WIDTH = 170;
	
	private Composite container;
	private TableViewer viewer;
	private List<SpecialTable> specialTables = new ArrayList<SpecialTable>();
	
	public static final String[] HANDLE_TYPES = new String[] {"忽略", "替换"};

	private List<Table> duplicationTableNames = new ArrayList<Table>();//重复表名集合
	
	private IOmReverse omReverse;
	private Label label;
	
	public List<SpecialTable> getSpecialTables() {
		return specialTables;
	}

	public void setSpecialTables(List<SpecialTable> specialTables) {
		this.specialTables = specialTables;
	}

	public List<Table> getDuplicationTableNames() {
		return duplicationTableNames;
	}

	public void setDuplicationTableNames(List<Table> duplicationTableNames) {
		this.duplicationTableNames = duplicationTableNames;
	}

	public DuplicateNameWizardPage(String pageName) {
		super(pageName);
		omReverse = new OmReverse();
	}

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

	private int getNameIndex(String name) {
		for (int i = 0; i < HANDLE_TYPES.length; i++) {
			if (HANDLE_TYPES[i].equals(name)) {
				return i;
			}
		}
		return -1;
	}
	  
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

	@Override
	public boolean canFlipToNextPage() {
		return isCanNext();
	}

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
