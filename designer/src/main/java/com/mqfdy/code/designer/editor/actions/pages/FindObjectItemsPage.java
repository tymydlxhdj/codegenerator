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

/**
 * 代码生成对象选择页面
 * 
 * @author mqfdy
 * 
 */
public class FindObjectItemsPage extends Composite {
	private String type;
	private Combo dbType;
	private Text disNameText;
	private Text nameText;
	private boolean caseSensitive;
	private Button clear;
	private Button sel;

	public FindObjectItemsPage(Composite parent, int style) {
		super(parent, style);
		createContents(this);
		init();
	}

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

	public String getType() {
		return type == null ? "" : type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDisplayName() {
		return disNameText.getText();
	}

	public String getObjectName() {
		return nameText.getText();
	}

	public boolean isCaseSensitive() {
		return caseSensitive;
	}
}
