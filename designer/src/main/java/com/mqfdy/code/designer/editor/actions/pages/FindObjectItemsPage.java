package com.mqfdy.code.designer.editor.actions.pages;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.mqfdy.code.designer.editor.BusinessModelEditorPlugin;
import com.mqfdy.code.model.utils.ModelUtil;

// TODO: Auto-generated Javadoc
/**
 * 代码生成对象选择页面.
 *
 * @author mqfdy
 */
public class FindObjectItemsPage extends Composite {
	
	/** The type. */
	private String type;
	
	/** The db type. */
	private Combo dbType;
	
	/** The dis name text. */
	private Text disNameText;
	
	/** The name text. */
	private Text nameText;
	
	/** The case sensitive. */
	private boolean caseSensitive;
	
	/** The clear. */
	private Button clear;
	
	/** The sel. */
	private Button sel;

	/**
	 * Instantiates a new find object items page.
	 *
	 * @param parent
	 *            the parent
	 * @param style
	 *            the style
	 */
	public FindObjectItemsPage(Composite parent, int style) {
		super(parent, style);
		createContents(this);
		init();
	}

	/**
	 * Inits the.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	private void init() {
		IPreferenceStore store = BusinessModelEditorPlugin.getDefault()
				.getPreferenceStore();

		type = store.getString(FindObjectDiaolg.FINDOBJECT_SEARCHTYPE);
		String searchName = store
				.getString(FindObjectDiaolg.FINDOBJECT_SEARCHNAME);
		String searchDisName = store
				.getString(FindObjectDiaolg.FINDOBJECT_SEARCHDISNAME);
		caseSensitive = store
				.getBoolean(FindObjectDiaolg.FINDOBJECT_CASESENSITIVE);

		sel.setSelection(caseSensitive);
		dbType.setText(type);
		disNameText.setText(searchDisName);
		nameText.setText(searchName);

		clear.addSelectionListener(new SelectionListener() {

			public void widgetSelected(SelectionEvent e) {
				sel.setSelection(false);
				dbType.setText("");
				disNameText.setText("");
				nameText.setText("");
				type = "";
				caseSensitive = false;
			}

			public void widgetDefaultSelected(SelectionEvent e) {
				sel.setSelection(false);
				dbType.setText("");
				disNameText.setText("");
				nameText.setText("");
				type = "";
				caseSensitive = false;
			}
		});
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
		GridLayout layout = new GridLayout();
		layout.numColumns = 3;
		composite.setLayout(layout);
		GridData textgridData = new GridData();
		textgridData.horizontalAlignment = GridData.FILL;
		textgridData.grabExcessHorizontalSpace = true;
		textgridData.verticalAlignment = GridData.CENTER;

		GridLayout layout1 = new GridLayout();
		layout1.numColumns = 2;
		Label dir = new Label(composite, SWT.NONE);
		dir.setText("类别：");
		dbType = new Combo(composite, SWT.READ_ONLY);
		dbType.setLayoutData(textgridData);
		String[] types = ModelUtil.getModelTypeDisplays();
		dbType.setItems(types);
		dbType.select(0);
		dbType.addSelectionListener(new SelectionListener() {

			public void widgetSelected(SelectionEvent e) {
				setType(dbType.getText());
			}

			public void widgetDefaultSelected(SelectionEvent e) {
				setType(dbType.getText());
			}
		});
		sel = new Button(composite, SWT.CHECK);
		sel.setText("区分大小写");
		sel.addSelectionListener(new SelectionListener() {

			public void widgetSelected(SelectionEvent e) {
				if (sel.getSelection()) {
					caseSensitive = true;
				} else
					caseSensitive = false;

			}

			public void widgetDefaultSelected(SelectionEvent e) {
				caseSensitive = false;
			}
		});
		Label name = new Label(composite, SWT.NONE);
		name.setText("名称：");
		nameText = new Text(composite, SWT.BORDER);
		nameText.setLayoutData(textgridData);
		GridData textgridData1 = new GridData();
		textgridData1.horizontalAlignment = GridData.FILL;
		// textgridData1.grabExcessHorizontalSpace = true;
		textgridData1.verticalAlignment = GridData.CENTER;
		clear = new Button(composite, SWT.NONE);
		clear.setText("重置");
		clear.setLayoutData(textgridData1);
		Label disName = new Label(composite, SWT.NONE);
		disName.setText("显示名称：");
		disNameText = new Text(composite, SWT.BORDER);
		disNameText.setLayoutData(textgridData);
	}

	/**
	 * Gets the type.
	 *
	 * @author mqfdy
	 * @return the type
	 * @Date 2018-09-03 09:00
	 */
	public String getType() {
		return type == null ? "" : type;
	}

	/**
	 * Sets the type.
	 *
	 * @author mqfdy
	 * @param type
	 *            the new type
	 * @Date 2018-09-03 09:00
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Gets the display name.
	 *
	 * @author mqfdy
	 * @return the display name
	 * @Date 2018-09-03 09:00
	 */
	public String getDisplayName() {
		return disNameText.getText();
	}

	/**
	 * Gets the object name.
	 *
	 * @author mqfdy
	 * @return the object name
	 * @Date 2018-09-03 09:00
	 */
	public String getObjectName() {
		return nameText.getText();
	}

	/**
	 * Checks if is case sensitive.
	 *
	 * @author mqfdy
	 * @return true, if is case sensitive
	 * @Date 2018-09-03 09:00
	 */
	public boolean isCaseSensitive() {
		return caseSensitive;
	}
}
