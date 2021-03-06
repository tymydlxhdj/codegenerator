package com.mqfdy.code.designer.dialogs;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.dialogs.MessageDialog;
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
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.ToolBar;

import com.mqfdy.code.designer.models.ActionTexts;
import com.mqfdy.code.designer.models.BusinessModelManager;
import com.mqfdy.code.designer.models.ImageKeys;
import com.mqfdy.code.designer.models.ImageManager;
import com.mqfdy.code.designer.utils.BusinessModelUtil;
import com.mqfdy.code.model.AbstractModelElement;
import com.mqfdy.code.model.DTOProperty;
import com.mqfdy.code.model.IModelElement;
import com.mqfdy.code.model.Property;
import com.mqfdy.code.model.utils.StringUtil;

// TODO: Auto-generated Javadoc
/**
 * DTO属性页签.
 *
 * @author mqfdy
 */
public class DTOPropertiesPage extends Composite implements
		IBusinessClassEditorPage {

	/** The dto dialog. */
	private DTOEditDialog dtoDialog;
	
	/** The tool bar. */
	private ToolBar toolBar = null;
	
	/** The property table. */
	private Table propertyTable = null;
	
	/** The property table viewer. */
	private TableViewer propertyTableViewer = null;// tableview

	/** The manager. */
	BusinessModelManager manager = BusinessModelUtil
			.getEditorBusinessModelManager();

	/** The add property action. */
	private Action addPropertyAction;
	
	/** The delete action. */
	private Action deleteAction;
	
	/** The save action. */
	private Action saveAction;
	
	/** The bottom action. */
	private Action bottomAction;
	
	/** The down action. */
	private Action downAction;
	
	/** The top action. */
	private Action topAction;
	
	/** The up action. */
	private Action upAction;
	
	/** The import action. */
	private Action importAction;

	/** The list properties. */
	private List<Property> listProperties = new ArrayList<Property>();;

	/**
	 * Instantiates a new DTO properties page.
	 *
	 * @param parent
	 *            the parent
	 * @param style
	 *            the style
	 * @param dtoDialog
	 *            the dto dialog
	 */
	DTOPropertiesPage(Composite parent, int style, DTOEditDialog dtoDialog) {
		super(parent, style);
		this.dtoDialog = dtoDialog;
		createToolBar();
		createContent();
		makeActions();
		addListeners();
	}

	/**
	 * Creates the content.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	private void createContent() {
		// 属性信息区域
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		layout.marginLeft = 8;
		this.setLayout(layout);

		GridData griddata_Properties = new GridData(SWT.FILL, SWT.FILL, true,
				false, 1, 1);
		griddata_Properties.heightHint = 120;
		propertyTableViewer = new TableViewer(this, SWT.SINGLE
				| SWT.FULL_SELECTION | SWT.BORDER | SWT.SCROLL_LINE);
		propertyTable = propertyTableViewer.getTable();
		propertyTable.setHeaderVisible(true);
		propertyTable.setLinesVisible(true);
		propertyTable.setLayoutData(griddata_Properties);
		propertyTableViewer.setLabelProvider(new TableLabelProvider());
		propertyTableViewer.setContentProvider(new ContentProvider());

		String[] columnNames = new String[] { "序号", "名称", "显示名", "数据类型",
				"属性来源（实体）", "属性来源（实体属性）" };

		int[] columnWidths = new int[] { 40, 80, 80, 80, 120, 120 };

		int[] columnAligns = new int[] { SWT.LEFT, SWT.LEFT, SWT.LEFT,
				SWT.LEFT, SWT.LEFT, SWT.LEFT };

		for (int i = 0; i < columnNames.length; i++) {
			TableColumn tableColumn = new TableColumn(propertyTable,
					columnAligns[i]);
			tableColumn.setText(columnNames[i]);
			tableColumn.setWidth(columnWidths[i]);
		}
	}

	/**
	 * Creates the tool bar.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	private void createToolBar() {
		GridData gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.grabExcessHorizontalSpace = true;
		toolBar = new ToolBar(this, SWT.FLAT | SWT.RIGHT);
		toolBar.setLayoutData(gridData);
	}

	/**
	 * Adds the listeners.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	private void addListeners() {
		propertyTable.addMouseListener(new MouseListener() {

			public void mouseDoubleClick(MouseEvent event) {
				ISelection selection = propertyTableViewer.getSelection();
				AbstractModelElement element = (AbstractModelElement) ((IStructuredSelection) selection)
						.getFirstElement();
				if (element instanceof DTOProperty) {
					DTOPropertyEditDialog dialog = new DTOPropertyEditDialog(
							DTOPropertiesPage.this.getShell(),
							(DTOProperty) element, DTOPropertiesPage.this);
					dialog.open();
				}
			}

			public void mouseDown(MouseEvent event) {
				// TODO Auto-generated method stub

			}

			public void mouseUp(MouseEvent event) {
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

		// 新增操作
		addPropertyAction = new Action(ActionTexts.MODEL_ELEMENT_ADD,
				ImageManager.getInstance().getImageDescriptor(
						ImageKeys.IMG_OBJECT_OPER_ADD)) {
			public void run() {

				DTOPropertyEditDialog dialog = new DTOPropertyEditDialog(
						propertyTableViewer.getControl().getShell(), null,
						DTOPropertiesPage.this);
				if (dialog.open() == Window.OK) {

				}
			}
		};

		// 删除操作
		deleteAction = new Action(ActionTexts.MODEL_ELEMENT_DELETE,
				ImageManager.getInstance().getImageDescriptor(
						ImageKeys.IMG_OBJECT_OPER_DELETE)) {
			public void run() {
				if (propertyTableViewer.getSelection().isEmpty()) {
					MessageDialog.openInformation(propertyTableViewer
							.getControl().getShell(), DELETE_MESSAGE_TITLE,
							DELETE_MESSAGE);
				} else {
					ISelection selection = propertyTableViewer.getSelection();
					DTOProperty property = (DTOProperty) ((IStructuredSelection) selection)
							.getFirstElement();
					dtoDialog.dto.getProperties().remove(property);
					refreshTable();
				}
			}
		};

		// 保存操作
		saveAction = new Action(ActionTexts.MODEL_ELEMENT_SAVE, ImageManager
				.getInstance().getImageDescriptor(ImageKeys.IMG_SAVE)) {
			public void run() {

			}
		};
		// 置底操作
		bottomAction = new Action(ActionTexts.MODEL_ELEMENT_BOTTOM,
				ImageManager.getInstance().getImageDescriptor(
						ImageKeys.IMG_BOTTOM)) {
			public void run() {
				if (propertyTableViewer.getSelection().isEmpty()) {
					MessageDialog.openInformation(propertyTableViewer
							.getControl().getShell(), DELETE_MESSAGE_TITLE,
							OPERATE_MESSAGE);
				} else {
					ISelection selection = propertyTableViewer.getSelection();
					Property property = (Property) ((IStructuredSelection) selection)
							.getFirstElement();
					resetOrderNum(property, "bottom");
					refreshTable();
				}
			}
		};
		// 下移操作
		downAction = new Action(ActionTexts.MODEL_ELEMENT_DOWN, ImageManager
				.getInstance().getImageDescriptor(ImageKeys.IMG_DOWN)) {
			public void run() {
				if (propertyTableViewer.getSelection().isEmpty()) {
					MessageDialog.openInformation(propertyTableViewer
							.getControl().getShell(), DELETE_MESSAGE_TITLE,
							OPERATE_MESSAGE);
				} else {
					ISelection selection = propertyTableViewer.getSelection();
					Property property = (Property) ((IStructuredSelection) selection)
							.getFirstElement();
					resetOrderNum(property, "down");
					refreshTable();
				}
			}
		};
		// 置顶操作
		topAction = new Action(ActionTexts.MODEL_ELEMENT_TOP, ImageManager
				.getInstance().getImageDescriptor(ImageKeys.IMG_TOP)) {
			public void run() {
				if (propertyTableViewer.getSelection().isEmpty()) {
					MessageDialog.openInformation(propertyTableViewer
							.getControl().getShell(), DELETE_MESSAGE_TITLE,
							OPERATE_MESSAGE);
				} else {
					ISelection selection = propertyTableViewer.getSelection();
					DTOProperty property = (DTOProperty) ((IStructuredSelection) selection)
							.getFirstElement();
					resetOrderNum(property, "top");
					refreshTable();
				}
			}
		};
		// 上移操作
		upAction = new Action(ActionTexts.MODEL_ELEMENT_UP, ImageManager
				.getInstance().getImageDescriptor(ImageKeys.IMG_UP)) {
			public void run() {
				if (propertyTableViewer.getSelection().isEmpty()) {
					MessageDialog.openInformation(propertyTableViewer
							.getControl().getShell(), DELETE_MESSAGE_TITLE,
							OPERATE_MESSAGE);
				} else {
					ISelection selection = propertyTableViewer.getSelection();
					DTOProperty property = (DTOProperty) ((IStructuredSelection) selection)
							.getFirstElement();
					resetOrderNum(property, "up");
					refreshTable();
				}
			}
		};
		// 导入操作
		importAction = new Action(ActionTexts.MODEL_ELEMENT_IMPORT,
				ImageManager.getInstance().getImageDescriptor(
						ImageKeys.IMG_IMPORT)) {
			public void run() {
				DTOPropertyImportDialog dialog = new DTOPropertyImportDialog(
						propertyTableViewer.getControl().getShell(),
						DTOPropertiesPage.this,
						new String[] { IModelElement.MODEL_TYPE_BUSINESSCLASS });
				dialog.open();
			}
		};

		ToolBarManager manager = new ToolBarManager(toolBar);
		manager.add(addPropertyAction);
		manager.add(deleteAction);
		manager.add(saveAction);
		manager.add(bottomAction);
		manager.add(downAction);
		manager.add(topAction);
		manager.add(upAction);
		manager.add(importAction);
		manager.add(new Separator());
		manager.update(true);

	}

	/**
	 * Reset order num.
	 *
	 * @author mqfdy
	 * @param property
	 *            the property
	 * @param type
	 *            the type
	 * @Date 2018-09-03 09:00
	 */
	private void resetOrderNum(Property property, String type) {
		if ("up".equals(type)) {
			for (int i = 0; i < listProperties.size(); i++) {
				Property temp = listProperties.get(i);
				if (temp.getId().equals(property.getId())) {
					if (i > 0) {
						listProperties.add(i - 1, property);
						listProperties.remove(i + 1);
						break;
					}
				}
			}
		} else if ("down".equals(type)) {
			for (int i = 0; i < listProperties.size(); i++) {
				Property temp = listProperties.get(i);
				if (temp.getId().equals(property.getId())) {
					listProperties.add(i + 2, property);
					listProperties.remove(i);
					break;
				}
			}
		} else if ("top".equals(type)) {
			for (int i = 0; i < listProperties.size(); i++) {
				Property temp = listProperties.get(i);
				if (temp.getId().equals(property.getId())) {
					listProperties.add(0, property);
					listProperties.remove(i + 1);
					break;
				}
			}
		} else if ("bottom".equals(type)) {
			for (int i = 0; i < listProperties.size(); i++) {
				Property temp = listProperties.get(i);
				if (temp.getId().equals(property.getId())) {
					listProperties.add(listProperties.size(), property);
					listProperties.remove(i);
					break;
				}
			}
		}
	}

	/**
	 * Gets the next order number.
	 *
	 * @author mqfdy
	 * @return the next order number
	 * @Date 2018-09-03 09:00
	 */
	private int getNextOrderNumber() {
		return listProperties.size() + 1;
	}

	/**
	 * Refresh table.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	public void refreshTable() {
		this.propertyTableViewer.refresh();
	}

	/**
	 * 
	 */
	public void initControlValue() {
		listProperties = dtoDialog.dto.getProperties();
		if (dtoDialog.operationType
				.equalsIgnoreCase(ModelElementEditorDialog.OPERATION_TYPE_EDIT)) {
			propertyTableViewer.setInput(listProperties);
		}
		if (dtoDialog.operationType
				.equalsIgnoreCase(ModelElementEditorDialog.OPERATION_TYPE_ADD)) {
			propertyTableViewer.setInput(listProperties);
		}
	}

	/**
	 * @return
	 */
	public boolean validateInput() {
		return false;
	}

	/**
	 * 
	 */
	public void updateTheEditingElement() {
		for (int i = 0; i < listProperties.size(); i++) {
			Property temp = listProperties.get(i);
			temp.setOrderNum(i);
		}
	}

	/**
	 * Gets the list properties.
	 *
	 * @author mqfdy
	 * @return the list properties
	 * @Date 2018-09-03 09:00
	 */
	public List<Property> getListProperties() {
		return listProperties;
	}

	/**
	 * LabelProvider.
	 *
	 * @author xuran
	 */
	static class TableLabelProvider extends LabelProvider implements
			ITableLabelProvider {

		/**
		 * Gets the column image.
		 *
		 * @author mqfdy
		 * @param arg0
		 *            the arg 0
		 * @param arg1
		 *            the arg 1
		 * @return the column image
		 * @Date 2018-09-03 09:00
		 */
		public Image getColumnImage(Object arg0, int arg1) {
			// TODO Auto-generated method stub
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
			if (element instanceof DTOProperty) {
				DTOProperty property = (DTOProperty) element;
				switch (columnIndex) {
				case 0:
					return property.getOrderNum() + "";
				case 1:
					return StringUtil.convertNull2EmptyStr(property.getName());
				case 2:
					return StringUtil.convertNull2EmptyStr(property
							.getDisplayName());
				case 3:
					return StringUtil.convertNull2EmptyStr(property
							.getDataType());
				case 4:
					if (property.getSourceBusinessClass() != null) {
						return StringUtil.convertNull2EmptyStr(property
								.getSourceBusinessClass().getDisplayName());
					} else {
						return "";
					}
				case 5:
					if (property.getSourceProperty() != null) {
						return StringUtil.convertNull2EmptyStr(property
								.getSourceProperty().getDisplayName());
					} else {
						return "";
					}
				}
			}
			return "";
		}
	}

	/**
	 * contentProvider.
	 *
	 * @author xuran
	 */
	static class ContentProvider implements IStructuredContentProvider {
		
		/**
		 * Gets the elements.
		 *
		 * @author mqfdy
		 * @param inputElement
		 *            the input element
		 * @return the elements
		 * @Date 2018-09-03 09:00
		 */
		public Object[] getElements(Object inputElement) {
			if (inputElement instanceof List) {
				Object[] objects = ((List) inputElement).toArray();
				if (objects != null) {
					for (int i = 0; i < objects.length; i++) {
						Property element = (Property) objects[i];
						element.setOrderNum(i + 1);
					}
				}
				return objects;
			}
			return new Object[0];
		}

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
	}

	/**
	 * Gets the dto dialog.
	 *
	 * @author mqfdy
	 * @return the dto dialog
	 * @Date 2018-09-03 09:00
	 */
	public DTOEditDialog getDtoDialog() {
		return dtoDialog;
	}
}
