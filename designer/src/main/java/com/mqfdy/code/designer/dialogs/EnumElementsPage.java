package com.mqfdy.code.designer.dialogs;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
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
import com.mqfdy.code.model.EnumElement;
import com.mqfdy.code.model.utils.StringUtil;
import com.mqfdy.code.resource.validator.ValidatorUtil;

/**
 * 枚举属性列表页
 * 
 * @author mqfdy
 * 
 */
public class EnumElementsPage extends Composite implements
		IBusinessClassEditorPage {

	private EnumEditDialog parentDialog;
	private ToolBar toolBar = null;
	private Table elementsTable = null;
	private TableViewer elementsTableViewer = null;// tableview

	private Action addElementAction;
	private Action deleteAction;
	private Action bottomAction;
	private Action downAction;
	private Action topAction;
	private Action upAction;

	private String columnName1 = "序号";
	private String columnName2 = "Key";
	private String columnName3 = "值";

	private List<EnumElement> listElements = new ArrayList<EnumElement>();;

	EnumElementsPage(Composite parent, int style, EnumEditDialog parentDialog) {
		super(parent, style);
		this.parentDialog = parentDialog;
		createToolBar();
		createContent();
		makeActions();
		addListeners();
	}

	private void createContent() {
		// 属性信息区域
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		layout.marginLeft = 8;
		this.setLayout(layout);

		GridData griddata_Properties = new GridData(SWT.FILL, SWT.FILL, true,
				false, 1, 1);
		griddata_Properties.heightHint = 120;
		elementsTableViewer = new TableViewer(this, SWT.FULL_SELECTION
				| SWT.BORDER | SWT.SCROLL_LINE | SWT.MULTI);
		elementsTable = elementsTableViewer.getTable();
		elementsTable.setHeaderVisible(true);
		elementsTable.setLinesVisible(true);
		elementsTable.setLayoutData(griddata_Properties);
		elementsTableViewer.setLabelProvider(new TableLabelProvider());
		elementsTableViewer.setContentProvider(new ContentProvider());

		String[] columnNames = new String[] { columnName1, columnName2,
				columnName3 };

		int[] columnWidths = new int[] { 40, 80, 80 };

		int[] columnAligns = new int[] { SWT.LEFT, SWT.LEFT, SWT.LEFT };

		for (int i = 0; i < columnNames.length; i++) {
			TableColumn tableColumn = new TableColumn(elementsTable,
					columnAligns[i]);
			tableColumn.setText(columnNames[i]);
			tableColumn.setWidth(columnWidths[i]);
		}

		elementsTableViewer.setColumnProperties(new String[] { "", columnName2,
				columnName3 });
		CellEditor[] cellEditors = new CellEditor[3];

		cellEditors[1] = new TextCellEditor(elementsTable, SWT.BORDER);
		cellEditors[2] = new TextCellEditor(elementsTable, SWT.BORDER);
		elementsTableViewer.setCellEditors(cellEditors);
		EnumElementCellModifier cellModeifier = new EnumElementCellModifier(
				elementsTableViewer);
		elementsTableViewer.setCellModifier(cellModeifier);
	}

	private void createToolBar() {
		GridData gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.grabExcessHorizontalSpace = true;
		toolBar = new ToolBar(this, SWT.FLAT | SWT.RIGHT);
		toolBar.setLayoutData(gridData);
	}

	private void addListeners() {
		/*
		 * elementsTable.addMouseListener(new MouseListener() {
		 * 
		 * public void mouseDoubleClick(MouseEvent event) { ISelection selection
		 * = elementsTableViewer.getSelection(); AbstractModelElement element =
		 * (AbstractModelElement) ((IStructuredSelection) selection)
		 * .getFirstElement(); if (element instanceof EnumElement) {
		 * EnumElementEditDialog dialog = new EnumElementEditDialog(
		 * elementsTableViewer.getControl().getShell(), null, (EnumElement)
		 * element, EnumElementsPage.this);
		 * 
		 * dialog.open(); } }
		 * 
		 * public void mouseDown(MouseEvent event) { // TODO Auto-generated
		 * method stub
		 * 
		 * }
		 * 
		 * public void mouseUp(MouseEvent event) { // TODO Auto-generated method
		 * stub
		 * 
		 * }
		 * 
		 * });
		 */
	}

	private String getNewName(String baseName) {
		if (listElements == null || listElements.size() < 1) {
			return baseName;
		} else {
			return getNewName(baseName, 0);
		}
	}

	private String getNewName(String baseName, int no) {
		String newName = baseName;
		if (no == 0) {
			newName = baseName;
		} else {
			newName = baseName + "_" + no;
		}
		for (EnumElement ele : listElements) {
			if (newName != null && newName.equals(ele.getKey())) {
				return getNewName(baseName, no + 1);
			}
		}
		return newName;
	}

	private void makeActions() {

		// 新增操作
		addElementAction = new Action(ActionTexts.MODEL_ELEMENT_ADD,
				ImageManager.getInstance().getImageDescriptor(
						ImageKeys.IMG_OBJECT_OPER_ADD)) {
			public void run() {
				EnumElement element = new EnumElement(getNewName("key"),
						"value");
				element.setOrderNum(getListElements().size() + 1);
				listElements.add(element);
				elementsTableViewer.refresh();
			}
		};

		// 删除操作
		deleteAction = new Action(ActionTexts.MODEL_ELEMENT_DELETE,
				ImageManager.getInstance().getImageDescriptor(
						ImageKeys.IMG_OBJECT_OPER_DELETE)) {
			public void run() {
				if (elementsTableViewer.getSelection().isEmpty()) {
					MessageDialog.openInformation(elementsTableViewer
							.getControl().getShell(), DELETE_MESSAGE_TITLE,
							DELETE_MESSAGE);
				} else {
					MessageDialog mes = new MessageDialog(elementsTableViewer
							.getControl().getShell(), OPERATE_ITEM_NULL_TITLE,
							null, DELETE_CONFIRM, 0,
							new String[] { "确定", "取消" }, 0);
					if (mes.open() == TitleAreaDialog.OK) {
						ISelection selection = elementsTableViewer
								.getSelection();
						IStructuredSelection select = (IStructuredSelection) selection;
						Iterator it = select.iterator();
						while (it.hasNext()) {
							EnumElement element = (EnumElement) it.next();
							listElements.remove(element);
						}
						refreshTable();
					}

				}
			}
		};

		// 置底操作
		bottomAction = new Action(ActionTexts.MODEL_ELEMENT_BOTTOM,
				ImageManager.getInstance().getImageDescriptor(
						ImageKeys.IMG_BOTTOM)) {
			public void run() {
				if (elementsTableViewer.getSelection().isEmpty()) {
					MessageDialog.openInformation(elementsTableViewer
							.getControl().getShell(), DELETE_MESSAGE_TITLE,
							OPERATE_MESSAGE);
				} else {
					ISelection selection = elementsTableViewer.getSelection();
					EnumElement element = (EnumElement) ((IStructuredSelection) selection)
							.getFirstElement();
					resetOrderNum(element, "bottom");
					refreshTable();
				}
			}
		};
		// 下移操作
		downAction = new Action(ActionTexts.MODEL_ELEMENT_DOWN, ImageManager
				.getInstance().getImageDescriptor(ImageKeys.IMG_DOWN)) {
			public void run() {
				if (elementsTableViewer.getSelection().isEmpty()) {
					MessageDialog.openInformation(elementsTableViewer
							.getControl().getShell(), DELETE_MESSAGE_TITLE,
							OPERATE_MESSAGE);
				} else {
					ISelection selection = elementsTableViewer.getSelection();
					EnumElement element = (EnumElement) ((IStructuredSelection) selection)
							.getFirstElement();
					resetOrderNum(element, "down");
					refreshTable();
				}
			}
		};
		// 置顶操作
		topAction = new Action(ActionTexts.MODEL_ELEMENT_TOP, ImageManager
				.getInstance().getImageDescriptor(ImageKeys.IMG_TOP)) {
			public void run() {
				if (elementsTableViewer.getSelection().isEmpty()) {
					MessageDialog.openInformation(elementsTableViewer
							.getControl().getShell(), DELETE_MESSAGE_TITLE,
							OPERATE_MESSAGE);
				} else {
					ISelection selection = elementsTableViewer.getSelection();
					EnumElement element = (EnumElement) ((IStructuredSelection) selection)
							.getFirstElement();
					resetOrderNum(element, "top");
					refreshTable();
				}
			}
		};
		// 上移操作
		upAction = new Action(ActionTexts.MODEL_ELEMENT_UP, ImageManager
				.getInstance().getImageDescriptor(ImageKeys.IMG_UP)) {
			public void run() {
				if (elementsTableViewer.getSelection().isEmpty()) {
					MessageDialog.openInformation(elementsTableViewer
							.getControl().getShell(), DELETE_MESSAGE_TITLE,
							OPERATE_MESSAGE);
				} else {
					ISelection selection = elementsTableViewer.getSelection();
					EnumElement element = (EnumElement) ((IStructuredSelection) selection)
							.getFirstElement();
					resetOrderNum(element, "up");
					refreshTable();
				}
			}
		};

		ToolBarManager manager = new ToolBarManager(toolBar);
		manager.add(addElementAction);
		manager.add(deleteAction);
		manager.add(bottomAction);
		manager.add(downAction);
		manager.add(topAction);
		manager.add(upAction);
		manager.add(new Separator());
		manager.update(true);

	}

	private void resetOrderNum(EnumElement element, String type) {
		if ("up".equals(type)) {
			for (int i = 0; i < listElements.size(); i++) {
				EnumElement temp = listElements.get(i);
				if (temp.getId().equals(element.getId())) {
					if (i > 0) {
						listElements.add(i - 1, element);
						listElements.remove(i + 1);
						break;
					}
				}
			}
		} else if ("down".equals(type)) {
			for (int i = 0; i < listElements.size(); i++) {
				EnumElement temp = listElements.get(i);
				if (temp.getId().equals(element.getId())) {
					listElements.add(i + 2, element);
					listElements.remove(i);
					break;
				}
			}
		} else if ("top".equals(type)) {
			for (int i = 0; i < listElements.size(); i++) {
				EnumElement temp = listElements.get(i);
				if (temp.getId().equals(element.getId())) {
					listElements.add(0, element);
					listElements.remove(i + 1);
					break;
				}
			}
		} else if ("bottom".equals(type)) {
			for (int i = 0; i < listElements.size(); i++) {
				EnumElement temp = listElements.get(i);
				if (temp.getId().equals(element.getId())) {
					listElements.add(listElements.size(), element);
					listElements.remove(i);
					break;
				}
			}
		}
	}

	public void refreshTable() {
		this.elementsTableViewer.refresh();
	}

	public void initControlValue() {
		List<EnumElement> list = parentDialog.getEnumeration().getElements();
		for (EnumElement s : list) {
			listElements.add(s.clone());
		}
		if (parentDialog.operationType
				.equalsIgnoreCase(ModelElementEditorDialog.OPERATION_TYPE_EDIT)) {
			elementsTableViewer.setInput(listElements);
		}
		if (parentDialog.operationType
				.equalsIgnoreCase(ModelElementEditorDialog.OPERATION_TYPE_ADD)) {
			elementsTableViewer.setInput(listElements);
		}
	}

	public boolean validateInput() {
		int i = 0;
		if (parentDialog != null) {
			
			parentDialog.setErrorMessage(null);
		}

		if (listElements == null || listElements.size() < 1) {
			parentDialog.setErrorMessage("枚举值信息至少有一条！");
			return false;
		}

		for (EnumElement s : listElements) {
			if (!ValidatorUtil.valiNameLength(s.getKey())) {
				parentDialog.setErrorMessage(ERROR_ENUM_KEY_LENGTH);
				return false;
			}
			if (!ValidatorUtil.valiDisplayNameLength(s.getValue())) {
				parentDialog.setErrorMessage(ERROR_ENUM_VALUE_LENGTH);
				return false;
			}
		}
		for (int m = 0; m < listElements.size(); m++) {
			for (int n = m + 1; n < listElements.size(); n++) {
				if (listElements.get(m).getKey()
						.equals(listElements.get(n).getKey()))
					i++;
			}
		}

		if (i > 0) {
			parentDialog.setErrorMessage("Key值重复！");
			return false;
		}
		return true;
	}

	public void updateTheEditingElement() {
		parentDialog.getEnumeration().getElements().clear();
		for (int i = 0; i < listElements.size(); i++) {
			EnumElement temp = listElements.get(i);
			temp.setOrderNum(i);
			parentDialog.getEnumeration().getElements().add(temp);
		}
	}

	public List<EnumElement> getListElements() {
		return listElements;
	}

	/**
	 * LabelProvider
	 * 
	 * @author xuran
	 * 
	 */
	static class TableLabelProvider extends LabelProvider implements
			ITableLabelProvider {

		public Image getColumnImage(Object arg0, int arg1) {
			// TODO Auto-generated method stub
			return null;
		}

		public String getColumnText(Object element, int columnIndex) {
			if (element instanceof EnumElement) {
				EnumElement temp = (EnumElement) element;
				switch (columnIndex) {
				case 0:
					return temp.getOrderNum() + "";
				case 1:
					return StringUtil.convertNull2EmptyStr(temp.getKey());
				case 2:
					return StringUtil.convertNull2EmptyStr(temp.getValue());
				}
			}
			return "";
		}
	}

	/**
	 * contentProvider
	 * 
	 * @author xuran
	 * 
	 */
	static class ContentProvider implements IStructuredContentProvider {
		public Object[] getElements(Object inputElement) {
			if (inputElement instanceof List) {
				Object[] objects = ((List) inputElement).toArray();
				if (objects != null) {
					for (int i = 0; i < objects.length; i++) {
						EnumElement element = (EnumElement) objects[i];
						element.setOrderNum(i + 1);
					}
				}
				return objects;
			}
			return new Object[0];
		}

		public void dispose() {
		}

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}
	}

	public EnumEditDialog getParentDialog() {
		return this.parentDialog;
	}

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
			if (property.equals(columnName2)) {
				return enumElement.getKey();
			}
			if (property.equals(columnName3)) {
				return enumElement.getValue();
			}
			return "";
		}

		public void modify(Object element, String property, Object value) {
			if ("".equals(value) || !isOk((String) value, property)) {
				return;
			}
			TableItem item = (TableItem) element;
			EnumElement enumElement = (EnumElement) item.getData();
			if (property.equals(columnName2)) {
				if (isKeyExist(enumElement, (String) value)) {
					return;
				}
				enumElement.setKey((String) value);
			}
			if (property.equals(columnName3)) {
				enumElement.setValue((String) value);
			}
			tableViewer.update(enumElement, null);
		}

		boolean isKeyExist(EnumElement enumElement, String key) {
			boolean b = false;
			if (enumElement != null && listElements != null) {
				for (EnumElement temp : listElements) {
					if (!temp.getId().equals(enumElement.getId())
							&& temp.getKey().equals(key)) {
						parentDialog
								.setErrorMessage(IBusinessClassEditorPage.ERROR_ENUM_KEY_MULT);
						b = true;
					}
				}
			}
			return b;
		}

		boolean isOk(String value, String property) {
			if (property.equals(columnName2) && parentDialog != null) {
				parentDialog.setErrorMessage(null);
				/*
				 * if(CheckerUtil.checkJava(value)){
				 * parentDialog.setErrorMessage
				 * (IBusinessClassEditorPage.ERROR_ENUM_KEY_JAVA); return false;
				 * }
				 */
				if (!value.matches("^[a-z0-9A-Z_][a-zA-Z_0-9]*$")) {
					parentDialog
							.setErrorMessage(IBusinessClassEditorPage.ERROR_ENUM_KEY_RULE);
					return false;
				}
			}

			if (property.equals(columnName3)) {
				if (!ValidatorUtil.valiDisplayName(value)) {
					parentDialog
							.setErrorMessage(IBusinessClassEditorPage.ERROR_ENUM_VALUE);
					return false;
				}
			}
			return true;
		}
	}

}
