package com.mqfdy.code.designer.dialogs;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import com.mqfdy.code.designer.dialogs.widget.ModelElementSelecterDailog;
import com.mqfdy.code.designer.dialogs.widget.NullToEmptyText;
import com.mqfdy.code.designer.models.BusinessModelManager;
import com.mqfdy.code.designer.provider.EnumElementLabelProvider;
import com.mqfdy.code.designer.utils.BusinessModelUtil;
import com.mqfdy.code.model.Association;
import com.mqfdy.code.model.EnumElement;
import com.mqfdy.code.model.Enumeration;
import com.mqfdy.code.model.IModelElement;
import com.mqfdy.code.model.PropertyEditor;

// TODO: Auto-generated Javadoc
/**
 * 枚举数据源页面.
 *
 * @author mqfdy
 */
public class DataSourceEnumPage extends Composite {

	/** The Constant ERROR_MESSAGE_ENUM_NULLABLE. */
	private static final String ERROR_MESSAGE_ENUM_NULLABLE = "请选择枚举";

	/** The Constant LABEL_ENUMSELECT_TEXT. */
	private static final String LABEL_ENUMSELECT_TEXT = "选择枚举：";
	
	/** The Constant BUTTON_ENUMSELECT_TEXT. */
	private static final String BUTTON_ENUMSELECT_TEXT = "查找";

	/** The label enum select. */
	private Label label_enumSelect;
	
	/** The text enum display name. */
	private NullToEmptyText text_enumDisplayName;
	
	/** The btn select. */
	private Button btn_select;

	/** The table. */
	private Table table;
	
	/** The table viewer. */
	private TableViewer tableViewer;

	/** The enumeration. */
	private Enumeration enumeration;

	/** The table items. */
	private List<EnumElement> tableItems = new ArrayList<EnumElement>();

	/** The parent page. */
	private IBusinessClassEditorPage parentPage;

	/**
	 * Instantiates a new data source enum page.
	 *
	 * @param parent
	 *            the parent
	 * @param style
	 *            the style
	 * @param parentPage
	 *            the parent page
	 */
	public DataSourceEnumPage(Composite parent, int style,
			IBusinessClassEditorPage parentPage) {
		super(parent, style);
		this.parentPage = parentPage;
		createContents(this);
	}

	/**
	 * Creates the contents.
	 *
	 * @author mqfdy
	 * @param composite
	 *            the composite
	 * @Date 2018-09-03 09:00
	 */
	private void createContents(Composite composite) {

		composite.setLayout(new GridLayout(3, false));
		GridData g = new GridData();
		// g.verticalSpan = 2;
		g.horizontalSpan = 2;
		g.grabExcessHorizontalSpace = true;
		g.horizontalAlignment = SWT.FILL;
		g.verticalAlignment = SWT.FILL;
		g.heightHint = 300;
		composite.setLayoutData(g);

		label_enumSelect = new Label(composite, SWT.NONE);
		label_enumSelect.setText(LABEL_ENUMSELECT_TEXT);

		text_enumDisplayName = new NullToEmptyText(composite, SWT.BORDER
				| SWT.READ_ONLY);
		text_enumDisplayName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER,
				true, false));

		btn_select = new Button(composite, SWT.NONE);
		btn_select.setText(BUTTON_ENUMSELECT_TEXT);

		tableViewer = new TableViewer(composite, SWT.SINGLE | SWT.BORDER
				| SWT.FULL_SELECTION);
		table = tableViewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		tableViewer.setContentProvider(new ArrayContentProvider());
		tableViewer.setLabelProvider(new EnumElementLabelProvider());
		tableViewer.setInput(tableItems);

		String[] columnNames = new String[] { "key", "value" };
		int[] columnWidths = new int[] { 100, 100 };
		int[] columnAligns = new int[] { SWT.LEFT, SWT.LEFT };

		for (int i = 0; i < columnNames.length; i++) {
			TableColumn tableColumn = new TableColumn(table, columnAligns[i]);
			tableColumn.setText(columnNames[i]);
			tableColumn.setWidth(columnWidths[i]);
		}
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 3, 1));

		makeAction();
	}

	/**
	 * Make action.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	private void makeAction() {
		btn_select.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				BusinessModelManager manager = BusinessModelUtil
						.getEditorBusinessModelManager();
				ModelElementSelecterDailog dialog = new ModelElementSelecterDailog(
						new String[] { IModelElement.MODEL_TYPE_ENUM }, manager);
				if (dialog.open() == Window.OK) {
					enumeration = (Enumeration) dialog.getResult()[0];
					text_enumDisplayName.setText(enumeration.getDisplayName());
					setTableItems(enumeration.getElements());
					refreshTable();
				}

			}
		});
	}

	/**
	 * Inits the control value.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	public void initControlValue() {
		PropertyEditor editor = null;
		if(parentPage instanceof PropertyEditorPage){
			editor = ((PropertyEditorPage) parentPage).getEditingProperty().getEditor();
		}
		if(parentPage instanceof FkEditorPage){
			editor = ((Association) ((FkEditorPage) parentPage).getParentDialog().parent).getEditor();
		}
		if (editor != null) {
			String enumerationId = editor.getEditorParams().get(
					PropertyEditor.PARAM_KEY_ENUMERATION_ID);
			if (enumerationId != null) {
				BusinessModelManager manager = BusinessModelUtil
						.getEditorBusinessModelManager();
				Enumeration enumeration = (Enumeration) manager
						.queryObjectById(enumerationId);
				if(enumeration != null){
					this.enumeration = enumeration;
					text_enumDisplayName.setText(enumeration.getDisplayName());
					setTableItems(enumeration.getElements());
				}
			}

		}
		tableViewer.refresh();

	}

	/**
	 * Validate input.
	 *
	 * @author mqfdy
	 * @return the string
	 * @Date 2018-09-03 09:00
	 */
	public String validateInput() {
		String errorMessage = null;
		if (enumeration == null) {
			errorMessage = ERROR_MESSAGE_ENUM_NULLABLE;
		}
		return errorMessage;
	}

	/**
	 * Sets the content back grand.
	 *
	 * @author mqfdy
	 * @param color
	 *            the new content back grand
	 * @Date 2018-09-03 09:00
	 */
	public void setContentBackGrand(Color color) {
		label_enumSelect.setBackground(color);
	}

	/**
	 * Sets the table items.
	 *
	 * @author mqfdy
	 * @param items
	 *            the new table items
	 * @Date 2018-09-03 09:00
	 */
	public void setTableItems(List<EnumElement> items) {
		this.tableItems.clear();
		this.tableItems.addAll(items);
	}

	/**
	 * Refresh table.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	public void refreshTable() {
		tableViewer.refresh();
	}

	/**
	 * Gets the enumeration.
	 *
	 * @author mqfdy
	 * @return the enumeration
	 * @Date 2018-09-03 09:00
	 */
	public Enumeration getEnumeration() {
		return enumeration;
	}

}
