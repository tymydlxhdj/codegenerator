package com.mqfdy.code.designer.celleditor;

import java.text.MessageFormat;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.events.TraverseListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Layout;

/**
 * 下拉框单元格编辑器
 * 
 * @author mqfdy
 * 
 */
public class ComboBoxCellEditor extends CellEditor {
	// Combo Items
	private String[] items;

	private Composite editor;

	private CCombo comboBox;

	private Control contents;

	private ModifyListener modifyListener;

	private Object value = null;

	/**
	 * Internal class for laying out the dialog.
	 */
	private class DialogCellLayout extends Layout {
		@Override
		public void layout(Composite editor, boolean force) {
			Rectangle bounds = editor.getClientArea();

			if (ComboBoxCellEditor.this.contents != null) {
				ComboBoxCellEditor.this.contents.setBounds(0, 0, bounds.width,
						bounds.height);
			}
		}

		@Override
		public Point computeSize(Composite editor, int wHint, int hHint,
				boolean force) {
			if (wHint != SWT.DEFAULT && hHint != SWT.DEFAULT) {
				return new Point(wHint, hHint);
			}
			Point contentsSize = ComboBoxCellEditor.this.contents.computeSize(
					SWT.DEFAULT, SWT.DEFAULT, force);
			return contentsSize;
		}
	}

	// Combo default style
	private static final int defaultStyle = SWT.NONE;

	public ComboBoxCellEditor() {
		setStyle(defaultStyle);
	}

	public ComboBoxCellEditor(Composite parent, String[] items) {
		this(parent, items, defaultStyle);
	}

	public ComboBoxCellEditor(Composite parent, String[] items, int style) {
		super(parent, style);
		setItems(items);
	}

	public String[] getItems() {
		return this.items;
	}

	public void setItems(String[] items) {
		Assert.isNotNull(items);
		this.items = items;
		populateComboBoxItems();
	}

	protected Button createButton(Composite parent) {
		Button result = new Button(parent, SWT.DOWN);
		result.setText(""); //$NON-NLS-1$  
		return result;
	}

	protected Control createContents(Composite cell) {
		this.comboBox = new CCombo(cell, getStyle());
		this.comboBox.setFont(cell.getFont());
		populateComboBoxItems();

		this.comboBox.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				keyReleaseOccured(e);
			}
		});

		this.comboBox.addModifyListener(getModifyListener());

		this.comboBox.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetDefaultSelected(SelectionEvent event) {
				applyEditorValueAndDeactivate();
			}

			@Override
			public void widgetSelected(SelectionEvent event) {
				ComboBoxCellEditor.this.value = ComboBoxCellEditor.this.comboBox
						.getText();
			}
		});

		this.comboBox.addTraverseListener(new TraverseListener() {
			public void keyTraversed(TraverseEvent e) {
				if (e.detail == SWT.TRAVERSE_ESCAPE
						|| e.detail == SWT.TRAVERSE_RETURN) {
					e.doit = false;
				}
			}
		});

		this.comboBox.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				ComboBoxCellEditor.this.focusLost();
			}
		});

		return this.comboBox;
	}

	@Override
	protected Control createControl(Composite parent) {
		Font font = parent.getFont();
		Color bg = parent.getBackground();

		this.editor = new Composite(parent, getStyle());
		this.editor.setFont(font);
		this.editor.setBackground(bg);
		this.editor.setLayout(new DialogCellLayout());
		this.contents = createContents(this.editor);
		updateContents(this.value);
		setValueValid(true);
		return this.editor;
	}

	@Override
	public void deactivate() {
		super.deactivate();
	}

	@Override
	protected Object doGetValue() {
		return this.value;
	}

	@Override
	protected void doSetFocus() {
		if (this.comboBox != null) {
			this.comboBox.setFocus();
		}
	}

	@Override
	protected void doSetValue(Object value) {
		this.value = value;
		updateContents(value);
	}

	private void populateComboBoxItems() {
		if (this.comboBox != null && this.items != null) {
			this.comboBox.removeAll();
			for (int i = 0; i < this.items.length; i++) {
				this.comboBox.add(this.items[i], i);
			}
			this.comboBox.setText("");
		}
	}

	void applyEditorValueAndDeactivate() {
		String newValue = this.comboBox.getText();
		if (newValue != null && !newValue.equals(this.value.toString())) {
			boolean newValidState = isCorrect(newValue);
			if (newValidState) {
				markDirty();
				doSetValue(newValue);
			} else {
				setErrorMessage(MessageFormat.format(getErrorMessage(),
						new Object[] { newValue.toString() }));
			}
		}
		fireApplyEditorValue();
		deactivate();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.CellEditor#focusLost()
	 */
	@Override
	protected void focusLost() {
		if (isActivated()) {
			applyEditorValueAndDeactivate();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.CellEditor#keyReleaseOccured(org.eclipse.swt
	 * .events.KeyEvent)
	 */
	@Override
	protected void keyReleaseOccured(KeyEvent keyEvent) {
		if (keyEvent.character == '\r') { // Return key
			if (this.comboBox != null && !this.comboBox.isDisposed()) {
				//fireCancelEditor();
				applyEditorValueAndDeactivate();
			}
		} else if (keyEvent.character == '\t') { // tab key
			applyEditorValueAndDeactivate();
		}
	}

	protected void editOccured(ModifyEvent e) {
		String value = this.comboBox.getText();
		if (value == null) {
			value = "";//$NON-NLS-1$  
		}
		Object typedValue = value;
		boolean oldValidState = isValueValid();
		boolean newValidState = isCorrect(typedValue);
		if (typedValue == null && newValidState) {
			Assert.isTrue(false,
					"Validator isn't limiting the cell editor's type range");//$NON-NLS-1$  
		}
		if (!newValidState) {
			// try to insert the current value into the error message.
			setErrorMessage(MessageFormat.format(getErrorMessage(),
					new Object[] { value }));
		}
		valueChanged(oldValidState, newValidState);
	}

	private ModifyListener getModifyListener() {
		if (this.modifyListener == null) {
			this.modifyListener = new ModifyListener() {
				public void modifyText(ModifyEvent e) {
					editOccured(e);
				}
			};
		}
		return this.modifyListener;
	}

	private void updateContents(Object value) {
		Assert.isTrue(this.comboBox != null);

		if (value != null && value instanceof String) {
			this.comboBox.removeModifyListener(getModifyListener());
			this.comboBox.setText((String) value);
			this.comboBox.addModifyListener(getModifyListener());
		}
	}

	public CCombo getComboBox() {
		return comboBox;
	}
}
