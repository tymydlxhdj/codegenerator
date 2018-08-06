package com.mqfdy.code.designer.celleditor;

import java.util.List;

import org.eclipse.jface.window.Window;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.mqfdy.code.designer.dialogs.widget.ModelElementSelecterDailog;
import com.mqfdy.code.designer.models.BusinessModelManager;
import com.mqfdy.code.designer.utils.BusinessModelUtil;
import com.mqfdy.code.model.AbstractModelElement;
import com.mqfdy.code.model.IModelElement;

/**
 * 下拉框单元格编辑器
 * 
 * @author mqfdy
 * 
 */
public class ComboDialogCellEditor extends ComboBoxDialogCellEditor {

	ModelElementSelecterDailog dialog;

	public ComboDialogCellEditor(Composite parent, List list,
			ModelElementSelecterDailog dialog) {
		super(parent, new String[] {});
		init(list, dialog);
		addListeners();
	}

	public void init(List list, ModelElementSelecterDailog selecterDialog) {
		BusinessModelManager manager = BusinessModelUtil
				.getEditorBusinessModelManager();

		this.dialog  = new ModelElementSelecterDailog(
				new String[] { IModelElement.MODEL_TYPE_BUSINESSCLASS },
				manager);
		getComboBox().setEditable(false);
		if (list != null) {
			for (Object element : list) {
				if (element instanceof String) {
					getComboBox().add((String) element);
				}
				if (element instanceof AbstractModelElement) {
					getComboBox().add(
							((AbstractModelElement) element).getDisplayName());
				}
			}
		}
	}

	private void addListeners() {
		getComboBox().addSelectionListener(new SelectionListener() {

			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}

			public void widgetSelected(SelectionEvent event) {

			}

		});

	}

	@Override
	protected Object openDialogBox(Control cellEditorWindow) {
		BusinessModelManager manager = BusinessModelUtil
				.getEditorBusinessModelManager();

		this.dialog  = new ModelElementSelecterDailog(
				new String[] { IModelElement.MODEL_TYPE_BUSINESSCLASS,IModelElement.MODEL_TYPE_REFRENCE },
				manager);
		if (dialog != null) {
			if (dialog.open() == Window.OK) {
				AbstractModelElement businessObject = (AbstractModelElement) dialog
						.getResult()[0];
				getComboBox().setText(businessObject.getName());
				getComboBox().setData(businessObject.getDisplayName(),
						businessObject);
				// dataTypeName = businessObject.getName();
			}
		}
		return null;
	}

}
