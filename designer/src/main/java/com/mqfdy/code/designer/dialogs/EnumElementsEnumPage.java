package com.mqfdy.code.designer.dialogs;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import com.mqfdy.code.importEnum.dialogs.ImportEnumDialog;
import com.mqfdy.code.model.EnumElement;
import com.mqfdy.code.model.utils.StringUtil;
import com.mqfdy.code.resource.validator.ValidatorUtil;

// TODO: Auto-generated Javadoc
/**
 * 枚举属性列表页.
 *
 * @author mqfdy
 */
public class EnumElementsEnumPage extends Composite implements
		IBusinessClassEditorPage {

	/** The enum parent dialog. */
	private ImportEnumDialog enumParentDialog;
	
	/** The elements table. */
	private Table elementsTable = null;
	
	/** The elements table viewer. */
	private TableViewer elementsTableViewer = null;//tableview

	/** The column name 1. */
	private String columnName1 = "序号";
	
	/** The column name 2. */
	private String columnName2 = "Key";
	
	/** The column name 3. */
	private String columnName3 = "值";

	/** The list elements. */
	private final List<EnumElement> listElements = new ArrayList<EnumElement>();
	
	/**
	 * Instantiates a new enum elements enum page.
	 *
	 * @param parent
	 *            the parent
	 * @param style
	 *            the style
	 * @param enumParentDialog
	 *            the enum parent dialog
	 */
	public EnumElementsEnumPage(Composite parent, int style, ImportEnumDialog enumParentDialog) {
		super(parent, style);
		this.enumParentDialog = enumParentDialog;
		createContent();
		initControlValue();
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

		elementsTableViewer.setColumnProperties(new String[] { "", columnName2,columnName3 });
	}

	/**
	 * Sets the enum list.
	 *
	 * @author mqfdy
	 * @param list
	 *            the list
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	public boolean setEnumList(List<EnumElement> list){
		listElements.clear();
		for (EnumElement enumElement : list) {
			listElements.add(enumElement);
		}
		elementsTableViewer.refresh();
		return true;
	}
	

	/**
	 * Refresh table.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	public void refreshTable() {
		this.elementsTableViewer.refresh();
	}

	/**
	 * 
	 */
	public void initControlValue() {
		List<EnumElement> list = enumParentDialog.getEnumeration().getElements();
		for (EnumElement s : list) {
			listElements.add(s.clone());
		}
		if (enumParentDialog.operationType
				.equalsIgnoreCase(ModelElementEditorDialog.OPERATION_TYPE_IMPORT)) {
			elementsTableViewer.setInput(listElements);
		}
	}

	/**
	 * @return
	 */
	public boolean validateInput() {
		int i = 0;
		if (enumParentDialog != null) {
			
			enumParentDialog.setErrorMessage(null);
		}

		if (listElements == null || listElements.size() < 1) {
			enumParentDialog.setErrorMessage("枚举值信息至少有一条！");
			return false;
		}

		for (EnumElement s : listElements) {
			if (!ValidatorUtil.valiNameLength(s.getKey())) {
				enumParentDialog.setErrorMessage(ERROR_ENUM_KEY_LENGTH);
				return false;
			}
			if (!ValidatorUtil.valiDisplayNameLength(s.getValue())) {
				enumParentDialog.setErrorMessage(ERROR_ENUM_VALUE_LENGTH);
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
			enumParentDialog.setErrorMessage("Key值重复！");
			return false;
		}
		return true;
	}
	
	


	/**
	 * 
	 */
	public void updateTheEditingElement() {
		enumParentDialog.getEnumeration().getElements().clear();
		for (int i = 0; i < listElements.size(); i++) {
			EnumElement temp = listElements.get(i);
			temp.setOrderNum(i);
			enumParentDialog.getEnumeration().getElements().add(temp);
		}
	}
	

	/**
	 * LabelProvider.
	 *
	 * @author xuran
	 */
	class TableLabelProvider extends LabelProvider implements
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
	 * contentProvider.
	 *
	 * @author xuran
	 */
	class ContentProvider implements IStructuredContentProvider {
		
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
	 * Gets the parent dialog.
	 *
	 * @author mqfdy
	 * @return the parent dialog
	 * @Date 2018-09-03 09:00
	 */
	public ImportEnumDialog getParentDialog() {
		return this.enumParentDialog;
	}

}
