package com.mqfdy.code.designer.dialogs;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.ToolBar;

import com.mqfdy.code.designer.models.ActionTexts;
import com.mqfdy.code.designer.models.ImageKeys;
import com.mqfdy.code.designer.models.ImageManager;
import com.mqfdy.code.designer.provider.EnumElementLabelProvider;
import com.mqfdy.code.model.EnumElement;
import com.mqfdy.code.model.PropertyEditor;

/**
 * 自定义数据源页面
 * 
 * @author mqfdy
 * 
 */
public class DataSourceCustomPage extends Composite {

	public static final String DELETE_MESSAGE = "请选择要删除的对象";
	public static final String DELETE_MESSAGE_TITLE = "属性";

	private ToolBar toolBar;
	private Table table;
	private TableViewer tableViewer;

	private Action addAction;// 新增
	private Action deleteAction;// 删除

	/**
	 * 表格对应数据源
	 */
	private List<EnumElement> tableItems = new ArrayList<EnumElement>();

	/**
	 * 属性页编辑页面
	 */
	private PropertyEditorPage parentPage;

	/**
	 * 枚举元素对应的单元格修改器
	 * 
	 * @author LQR
	 * 
	 */
	public class EnumElementCellModifier implements ICellModifier {
		private TableViewer tableViewer;

		public EnumElementCellModifier(TableViewer tableViewer) {
			this.tableViewer = tableViewer;
		}

		public boolean canModify(Object element, String property) {
			return true;
		}

		public Object getValue(Object element, String property) {
			EnumElement enumElement = (EnumElement) element;
			if (property.equals("name")) {
				return enumElement.getKey();
			} else {
				return enumElement.getValue();
			}
		}

		public void modify(Object element, String property, Object value) {
			TableItem item = (TableItem) element;
			EnumElement enumElement = (EnumElement) item.getData();
			if (property.equals("name")) {
				enumElement.setKey((String) value);
			} else {
				enumElement.setValue((String) value);
				enumElement.setDisplayName((String) value);
			}
			tableViewer.update(enumElement, null);
		}

	}

	/**
	 * 构造函数
	 * 
	 * @param parent
	 *            上级容器
	 * @param style
	 *            样式
	 * @param parentPage
	 *            属性页
	 */
	public DataSourceCustomPage(Composite parent, int style,
			PropertyEditorPage parentPage) {
		super(parent, style);
		this.parentPage = parentPage;
		createContents(this);
	}

	/**
	 * 创建页面内容
	 * 
	 * @param composite
	 */
	private void createContents(Composite composite) {
		composite.setLayout(new GridLayout(1, false));
		toolBar = new ToolBar(composite, SWT.FLAT | SWT.RIGHT);
		tableViewer = new TableViewer(composite, SWT.SINGLE | SWT.BORDER
				| SWT.FULL_SELECTION);
		table = tableViewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		tableViewer.setContentProvider(new ArrayContentProvider());
		tableViewer.setLabelProvider(new EnumElementLabelProvider());
		tableViewer.setInput(tableItems);
		// 创建表格列
		String[] columnNames = new String[] { "key", "value" };
		int[] columnWidths = new int[] { 100, 100 };
		int[] columnAligns = new int[] { SWT.LEFT, SWT.LEFT };
		for (int i = 0; i < columnNames.length; i++) {
			TableColumn tableColumn = new TableColumn(table, columnAligns[i]);
			tableColumn.setText(columnNames[i]);
			tableColumn.setWidth(columnWidths[i]);
		}

		// 处理表格的可编辑
		tableViewer.setColumnProperties(new String[] { "name", "value" });
		CellEditor[] cellEditors = new CellEditor[2];
		cellEditors[0] = new TextCellEditor(table, SWT.BORDER);
		cellEditors[1] = new TextCellEditor(table, SWT.BORDER);
		tableViewer.setCellEditors(cellEditors);
		tableViewer.setCellModifier(new EnumElementCellModifier(tableViewer));
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		makeAction();
	}

	private void makeAction() {
		addAction = new Action(ActionTexts.MODEL_ELEMENT_ADD, ImageManager
				.getInstance().getImageDescriptor(
						ImageKeys.IMG_MODEL_OPER_NEWELEMENT)) {
			public void run() {
				tableItems.add(new EnumElement("key", "value"));
				tableViewer.refresh();
			}
		};

		deleteAction = new Action(ActionTexts.MODEL_ELEMENT_DELETE,
				ImageManager.getInstance().getImageDescriptor(
						ImageKeys.IMG_OBJECT_OPER_DELETE)) {
			public void run() {
				if (tableViewer.getSelection().isEmpty()) {
					MessageDialog.openInformation(tableViewer.getControl()
							.getShell(), DELETE_MESSAGE_TITLE, DELETE_MESSAGE);
				} else {
					ISelection selection = tableViewer.getSelection();
					EnumElement enumElement = (EnumElement) ((IStructuredSelection) selection)
							.getFirstElement();
					tableItems.remove(enumElement);
					tableViewer.refresh();
				}
			}
		};

		ToolBarManager manager = new ToolBarManager(toolBar);
		manager.add(addAction);
		manager.add(deleteAction);
		manager.update(true);
	}

	public void initControlValue() {
		PropertyEditor editor = parentPage.getEditingProperty().getEditor();
		List<EnumElement> dataList = editor.getEditorData();
		for (int i = 0; i < dataList.size(); i++) {
			tableItems.add(dataList.get(i));
		}
		tableViewer.refresh();
	}

	public List<EnumElement> getTableItems() {
		return tableItems;
	}

}
