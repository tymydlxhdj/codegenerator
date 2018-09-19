package com.mqfdy.code.designer.dialogs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.ToolBar;

import com.mqfdy.code.designer.dialogs.actions.TableViewerActionGroup;
import com.mqfdy.code.designer.models.ActionTexts;
import com.mqfdy.code.designer.models.ImageKeys;
import com.mqfdy.code.designer.models.ImageManager;
import com.mqfdy.code.designer.views.modelresource.actions.AddOperationAction;
import com.mqfdy.code.model.AbstractModelElement;
import com.mqfdy.code.model.BusinessClass;
import com.mqfdy.code.model.BusinessOperation;
import com.mqfdy.code.model.utils.DataType;
import com.mqfdy.code.model.utils.TransactionType;

// TODO: Auto-generated Javadoc
/**
 * 业务实体权限编辑页.
 *
 * @author mqfdy
 */
public class BusinessClassOperationsPage extends Composite implements
		IBusinessClassEditorPage {

	/** The Constant GROUP_STANDARD_TEXT. */
	public static final String GROUP_STANDARD_TEXT = "标准操作";
	
	/** The Constant GROUP_CUSTOM_TEXT. */
	public static final String GROUP_CUSTOM_TEXT = "自定义操作";

	/** The parent dialog. */
	private BusinessClassEditorDialog parentDialog;

	// private Group group_standard;
	// private Group group_custom;

	// private Table standardTable;
	// private TableViewer standardTableViewer;

	/** The custom table. */
	private Table customTable;
	
	/** The custom table viewer. */
	private TableViewer customTableViewer;
	
	/** The op button list. */
	private List<Button> opButtonList = new ArrayList<Button>();
	// private List<BusinessOperation> selectedOpList = new
	/** The op list. */
	// ArrayList<BusinessOperation>();
	private List<BusinessOperation> opList = BusinessOperation
			.getStandardOperations();
	
	/** The tool bar. */
	private ToolBar toolBar;

	/** The add operation action. */
	private Action addOperationAction;// 新增
	
	/** The delete operation action. */
	private Action deleteOperationAction;// 删除
	
	/** The up action. */
	private Action upAction;
	
	/** The down action. */
	private Action downAction;
	
	/** The top action. */
	private Action topAction;
	
	/** The bottom action. */
	private Action bottomAction;
	// private Action saveOperationAction;//保存

	/** The table items. */
	private List<BusinessOperation> tableItems = new ArrayList<BusinessOperation>();

	/**
	 * The Class CustomOperationConentProvider.
	 *
	 * @author mqfdy
	 */
	private class CustomOperationConentProvider implements
			IStructuredContentProvider {
		
		/**
		 * 
		 */
		public void dispose() {
		}

		/**
		 * Input changed.
		 *
		 * @author mqfdy
		 * @param viewer
		 *            the viewer
		 * @param oldInput
		 *            the old input
		 * @param newInput
		 *            the new input
		 * @Date 2018-09-03 09:00
		 */
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}

		/**
		 * Gets the elements.
		 *
		 * @author mqfdy
		 * @param inputElement
		 *            the input element
		 * @return the elements
		 * @Date 2018-09-03 09:00
		 */
		@SuppressWarnings("rawtypes")
		public Object[] getElements(Object inputElement) {
			if (inputElement instanceof Collection) {
				Object[] objects = ((Collection) inputElement).toArray();
				if (objects != null) {
					for (int i = 0; i < objects.length; i++) {
						BusinessOperation element = (BusinessOperation) objects[i];
						element.setOrderNum(i + 1);
					}
				}
				return objects;
			}
			return new Object[0];
		}
	}

	/**
	 * The Class CustomOperationLabelProvider.
	 *
	 * @author mqfdy
	 */
	private class CustomOperationLabelProvider extends LabelProvider implements
			ITableLabelProvider {

		/**
		 * Gets the column image.
		 *
		 * @author mqfdy
		 * @param element
		 *            the element
		 * @param columnIndex
		 *            the column index
		 * @return the column image
		 * @Date 2018-09-03 09:00
		 */
		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}

		/**
		 * Gets the column text.
		 *
		 * @author mqfdy
		 * @param element
		 *            the element
		 * @param columnIndex
		 *            the column index
		 * @return the column text
		 * @Date 2018-09-03 09:00
		 */
		public String getColumnText(Object element, int columnIndex) {
			BusinessOperation operation = (BusinessOperation) element;
			switch (columnIndex) {
			case 0:
				return operation.getOrderNum() + "";
			case 1:
				return operation.getDisplayName();
			case 2:
				return operation.getName();
			case 3:
				DataType type = DataType.getDataType(operation
						.getReturnDataType());
				if (type != null) {
					return type.getValue_hibernet();
				}
				return operation.getReturnDataType();
			case 4:
				TransactionType transactionType = TransactionType
						.getTransactionType(operation.getTransactionType());
				if (transactionType != null) {
					return transactionType.getDisplayValue();
				}
				return operation.getTransactionType();
			}
			return "";
		}
	}

	/**
	 * Instantiates a new business class operations page.
	 *
	 * @param parent
	 *            the parent
	 * @param style
	 *            the style
	 * @param parentDialog
	 *            the parent dialog
	 */
	public BusinessClassOperationsPage(Composite parent, int style,
			BusinessClassEditorDialog parentDialog) {
		super(parent, style);
		this.parentDialog = parentDialog;

		createContent(this);
	}

	/**
	 * This method initializes this.
	 *
	 * @author mqfdy
	 * @param composite
	 *            the composite
	 * @Date 2018-09-03 09:00
	 */
	private void createContent(Composite composite) {
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		// gridLayout.marginWidth = 1;
		// gridLayout.horizontalSpacing = 42;
		composite.setLayout(gridLayout);
		toolBar = new ToolBar(composite, SWT.NONE);
		Composite buc = new Composite(composite, SWT.RIGHT | SWT.FILL);
		GridLayout gridLayout1 = new GridLayout();
		gridLayout1.numColumns = 6;
		gridLayout1.marginLeft = 90;
		gridLayout1.marginWidth = 1;
		gridLayout1.horizontalSpacing = 10;
		GridData g = new GridData();
		g.horizontalAlignment = SWT.RIGHT;
		buc.setLayoutData(g);
		buc.setLayout(gridLayout1);
		Label l = new Label(buc, SWT.NONE);
		l.setText("标准操作：");
		// toolBar.setLayoutData(new
		// GridData(SWT.FILL,SWT.CENTER,true,false,1,1));
		for (int i = 0; i < opList.size(); i++) {
			final Button bu = new Button(buc, SWT.CHECK | SWT.RIGHT);
			final BusinessOperation op = opList.get(i);
			bu.setText(op.getDisplayName());
			// bu.setLayoutData(new
			// GridData(SWT.FILL,SWT.RIGHT,true,false,1,1));
			bu.addSelectionListener(new SelectionListener() {

				public void widgetSelected(SelectionEvent e) {
					if (bu.getSelection()) {
						tableItems.add(op);
					} else {
						List<BusinessOperation> temp = parentDialog
								.getBusinessClassCopy().getOperations();
						for (int i = 0; i < temp.size(); i++) {
							if (BusinessOperation.OPERATION_TYPE_STANDARD
									.equals(temp.get(i).getOperationType())) {
								if (op.getName().equals(temp.get(i).getName())) {
									tableItems.remove(temp.get(i));
								}
							}
						}
						tableItems.remove(op);
					}
					refreshTable();
				}

				public void widgetDefaultSelected(SelectionEvent e) {
					// TODO Auto-generated method stub

				}
			});
			opButtonList.add(bu);
		}
		customTableViewer = new TableViewer(composite, SWT.MULTI
				| SWT.FULL_SELECTION | SWT.BORDER);
		customTable = customTableViewer.getTable();
		customTable.setHeaderVisible(true);
		customTable.setLinesVisible(true);
		customTableViewer
				.setContentProvider(new CustomOperationConentProvider());
		customTableViewer.setLabelProvider(new CustomOperationLabelProvider());
		String[] columnNames = new String[] { "序号", "名称", "方法名", "返回类型", "事务类型" };

		int[] columnWidths = new int[] { 40, 100, 100, 100, 100 };

		int[] columnAligns = new int[] { SWT.LEFT, SWT.LEFT, SWT.LEFT,
				SWT.LEFT, SWT.LEFT };

		for (int i = 0; i < columnNames.length; i++) {
			TableColumn tableColumn = new TableColumn(customTable,
					columnAligns[i]);
			tableColumn.setText(columnNames[i]);
			tableColumn.setWidth(columnWidths[i]);
		}
		GridData tableGridData = new GridData();
		tableGridData.horizontalSpan = 2;
		tableGridData.horizontalAlignment = SWT.FILL;
		tableGridData.verticalAlignment = SWT.FILL;
		tableGridData.grabExcessHorizontalSpace = true;
		tableGridData.grabExcessVerticalSpace = true;
		customTable.setLayoutData(tableGridData);

		// shell.open();
		// customTable.setLayoutData(new GridData(GridData.FILL_BOTH));
		makeActions();
		final TableViewerActionGroup actionGroup = new TableViewerActionGroup(
				customTableViewer, 2, parentDialog);
		final MenuManager menu = new MenuManager();
		actionGroup.fillContextMenu(menu, deleteOperationAction);
		customTable.addMouseListener(new MouseListener() {

			public void mouseUp(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			public void mouseDown(MouseEvent e) {
				if (e.button == 3)
					actionGroup.fillContextMenu(menu, deleteOperationAction);
			}

			public void mouseDoubleClick(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});
	}

	/**
	 * Make actions.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	private void makeActions() {
		addOperationAction = new AddOperationAction(parentDialog);
		deleteOperationAction = new Action(ActionTexts.MODEL_ELEMENT_DELETE,
				ImageManager.getInstance().getImageDescriptor(
						ImageKeys.IMG_OBJECT_OPER_DELETE)) {
			public void run() {
				if (customTableViewer.getSelection().isEmpty()) {
					MessageDialog.openInformation(customTableViewer
							.getControl().getShell(), OPERATE_ITEM_NULL_TITLE,
							OPERATE_ITEM_NULL_MESSAGE);
				} else {
					MessageDialog mes = new MessageDialog(customTableViewer
							.getControl().getShell(), OPERATE_ITEM_NULL_TITLE,
							null, DELETE_CONFIRM, 0,
							new String[] { "确定", "取消" }, 0);
					if (mes.open() == TitleAreaDialog.OK) {
						ISelection selection = customTableViewer.getSelection();
						IStructuredSelection select = (IStructuredSelection) selection;
						Iterator it = select.iterator();
						while (it.hasNext()) {
							BusinessOperation operation = (BusinessOperation) it
									.next();
							tableItems.remove(operation);
							if (BusinessOperation.OPERATION_TYPE_STANDARD
									.equals(operation.getOperationType())) {
								for (int j = 0; j < opList.size(); j++) {
									BusinessOperation standardOperation = (BusinessOperation) opList
											.get(j);
									if (standardOperation.getName().equals(
											operation.getName())) {
										((BusinessOperation) (opList.get(j)))
												.setId(operation.getId());
										opButtonList.get(j).setSelection(false);
										// selectedOpList.add(standardOperation);
									}
								}
							}
						}
						/*
						 * ISelection selection1 =
						 * customTableViewer.getSelection(); BusinessOperation
						 * operation =
						 * (BusinessOperation)((IStructuredSelection)
						 * selection1).getFirstElement();
						 * tableItems.remove(operation);
						 * if(BusinessOperation.OPERATION_TYPE_STANDARD
						 * .equals(operation.getOperationType())){ for(int j =
						 * 0;j < opList.size(); j++){ BusinessOperation
						 * standardOperation = (BusinessOperation)opList.get(j);
						 * if
						 * (standardOperation.getName().equals(operation.getName
						 * ())){
						 * ((BusinessOperation)(opList.get(j))).setId(operation
						 * .getId()); opButtonList.get(j).setSelection(false);
						 * // selectedOpList.add(standardOperation); } } }
						 */
						refreshTable();
					}
				}
			}
		};

		customTableViewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				ISelection selection = customTableViewer.getSelection();
				AbstractModelElement element = (AbstractModelElement) ((IStructuredSelection) selection)
						.getFirstElement();
				if (element instanceof BusinessOperation
						&& !((BusinessOperation) element)
								.getOperationType()
								.equals(BusinessOperation.OPERATION_TYPE_STANDARD)) {
					BusinessClass parent = parentDialog.getBusinessClassCopy();
					OperationEditorDialog dialog = new OperationEditorDialog(
							null, element, parent);
					int returnKey = dialog.open();
					if (returnKey == Window.OK
							|| (returnKey == Window.CANCEL && dialog
									.isChanged())) {
						// 编辑后保存关闭
						if (dialog.getOperationType().equals(
								ModelElementEditorDialog.OPERATION_TYPE_EDIT)) {
							refreshTable();
						}
					}
				}
			}
		});

		upAction = new Action(ActionTexts.MODEL_ELEMENT_UP, ImageManager
				.getInstance().getImageDescriptor(ImageKeys.IMG_UP)) {
			public void run() {
				if (customTableViewer.getSelection().isEmpty()) {
					MessageDialog.openInformation(customTableViewer
							.getControl().getShell(), OPERATE_ITEM_NULL_TITLE,
							OPERATE_ITEM_NULL_MESSAGE);
				} else {
					ISelection selection = customTableViewer.getSelection();
					BusinessOperation operation = (BusinessOperation) ((IStructuredSelection) selection)
							.getFirstElement();
					resetOrderNum(operation, "up");
					refreshTable();
				}
			}
		};

		topAction = new Action(ActionTexts.MODEL_ELEMENT_TOP, ImageManager
				.getInstance().getImageDescriptor(ImageKeys.IMG_TOP)) {
			public void run() {
				if (customTableViewer.getSelection().isEmpty()) {
					MessageDialog.openInformation(customTableViewer
							.getControl().getShell(), OPERATE_ITEM_NULL_TITLE,
							OPERATE_ITEM_NULL_MESSAGE);
				} else {
					ISelection selection = customTableViewer.getSelection();
					BusinessOperation operation = (BusinessOperation) ((IStructuredSelection) selection)
							.getFirstElement();
					resetOrderNum(operation, "top");
					refreshTable();
				}
			}
		};

		downAction = new Action(ActionTexts.MODEL_ELEMENT_DOWN, ImageManager
				.getInstance().getImageDescriptor(ImageKeys.IMG_DOWN)) {
			public void run() {
				if (customTableViewer.getSelection().isEmpty()) {
					MessageDialog.openInformation(customTableViewer
							.getControl().getShell(), OPERATE_ITEM_NULL_TITLE,
							OPERATE_ITEM_NULL_MESSAGE);
				} else {
					ISelection selection = customTableViewer.getSelection();
					BusinessOperation operation = (BusinessOperation) ((IStructuredSelection) selection)
							.getFirstElement();
					resetOrderNum(operation, "down");
					refreshTable();
				}
			}
		};

		bottomAction = new Action(ActionTexts.MODEL_ELEMENT_BOTTOM,
				ImageManager.getInstance().getImageDescriptor(
						ImageKeys.IMG_BOTTOM)) {
			public void run() {
				if (customTableViewer.getSelection().isEmpty()) {
					MessageDialog.openInformation(customTableViewer
							.getControl().getShell(), OPERATE_ITEM_NULL_TITLE,
							OPERATE_ITEM_NULL_MESSAGE);
				} else {
					ISelection selection = customTableViewer.getSelection();
					BusinessOperation operation = (BusinessOperation) ((IStructuredSelection) selection)
							.getFirstElement();
					resetOrderNum(operation, "bottom");
					refreshTable();
				}
			}
		};

		ToolBarManager manager = new ToolBarManager(toolBar);
		manager.add(addOperationAction);
		manager.add(deleteOperationAction);
		manager.add(upAction);
		manager.add(topAction);
		manager.add(downAction);
		manager.add(bottomAction);
		manager.update(true);
	}

	/**
	 * 
	 */
	public void initControlValue() {
		List<BusinessOperation> temp = parentDialog.getBusinessClassCopy()
				.getOperations();
		for (int i = 0; i < opList.size(); i++) {
			final BusinessOperation op = opList.get(i);
			op.setBelongBusinessClass(parentDialog.getBusinessClassCopy());
		}
		for (int i = 0; i < temp.size(); i++) {
			// if(BusinessOperation.OPERATION_TYPE_STANDARD.equals(temp.get(i).getOperationType())){
			// continue;
			// }
			tableItems.add(temp.get(i));
		}
		customTableViewer.setInput(tableItems);

		for (int i = 0; i < temp.size(); i++) {
			// List<BusinessOperation> items =
			// BusinessOperation.getStandardOperations();
			if (BusinessOperation.OPERATION_TYPE_STANDARD.equals(temp.get(i)
					.getOperationType())) {
				for (int j = 0; j < opList.size(); j++) {
					BusinessOperation standardOperation = (BusinessOperation) opList
							.get(j);
					if (standardOperation.getName().equals(
							temp.get(i).getName())) {
						((BusinessOperation) (opList.get(j))).setId(temp.get(i)
								.getId());
						opButtonList.get(j).setSelection(true);
						// selectedOpList.add(standardOperation);
					}
				}
			}
		}
	}

	/**
	 * @return
	 */
	public boolean validateInput() {
		// TODO Auto-generated method stub
		return true;
	}

	/**
	 * 
	 */
	public void updateTheEditingElement() {
		for (int i = 0; i < tableItems.size(); i++) {
			BusinessOperation temp = tableItems.get(i);
			temp.setOrderNum(i);
		}
		// List<BusinessOperation> list = getSelectedStandardOperation();
		// list.addAll(tableItems);
		BusinessClass businessClass = (BusinessClass) (parentDialog
				.getBusinessClassCopy());
		businessClass.getOperations().clear();
		businessClass.getOperations().addAll(tableItems);
	}

	// public List<BusinessOperation> getSelectedStandardOperation(){
	// selectedOpList.clear();
	// for(int i = 0;i<opButtonList.size();i++){
	// if(opButtonList.get(i).getSelection()){
	// selectedOpList.add(opList.get(i));
	// }
	// }
	// return selectedOpList;
	// }

	/**
	 * Reset order num.
	 *
	 * @author mqfdy
	 * @param operation
	 *            the operation
	 * @param type
	 *            the type
	 * @Date 2018-09-03 09:00
	 */
	private void resetOrderNum(BusinessOperation operation, String type) {
		if ("up".equals(type)) {
			for (int i = 0; i < tableItems.size(); i++) {
				BusinessOperation temp = tableItems.get(i);
				if (temp.getId().equals(operation.getId())) {
					if (i > 0) {
						tableItems.add(i - 1, operation);
						tableItems.remove(i + 1);
						break;
					}
				}
			}
		} else if ("down".equals(type)) {
			for (int i = 0; i < tableItems.size(); i++) {
				BusinessOperation temp = tableItems.get(i);
				if (temp.getId().equals(operation.getId())) {
					tableItems.add(i + 2, operation);
					tableItems.remove(i);
					break;
				}
			}
		} else if ("top".equals(type)) {
			for (int i = 0; i < tableItems.size(); i++) {
				BusinessOperation temp = tableItems.get(i);
				if (temp.getId().equals(operation.getId())) {
					tableItems.add(0, operation);
					tableItems.remove(i + 1);
					break;
				}
			}
		} else if ("bottom".equals(type)) {
			for (int i = 0; i < tableItems.size(); i++) {
				BusinessOperation temp = tableItems.get(i);
				if (temp.getId().equals(operation.getId())) {
					tableItems.add(tableItems.size(), operation);
					tableItems.remove(i);
					break;
				}
			}
		}
	}

	/**
	 * 刷新表格.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	public void refreshTable() {
		customTableViewer.refresh();
	}

	/**
	 * 获取当前表格的数据.
	 *
	 * @author mqfdy
	 * @return the table items
	 * @Date 2018-09-03 09:00
	 */
	public List<BusinessOperation> getTableItems() {
		return tableItems;
	}

}
