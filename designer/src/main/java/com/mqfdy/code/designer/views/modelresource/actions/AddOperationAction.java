package com.mqfdy.code.designer.views.modelresource.actions;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.TreeItem;

import com.mqfdy.code.designer.dialogs.BusinessClassEditorDialog;
import com.mqfdy.code.designer.dialogs.OperationEditorDialog;
import com.mqfdy.code.designer.models.ActionTexts;
import com.mqfdy.code.designer.models.ImageKeys;
import com.mqfdy.code.designer.models.ImageManager;
import com.mqfdy.code.model.BusinessClass;
import com.mqfdy.code.model.BusinessOperation;
import com.mqfdy.code.model.ModelPackage;

/**
 * 新增业务类属性动作
 * 
 * @author mqfdy
 * 
 */
public class AddOperationAction extends TreeAction {

	private BusinessClassEditorDialog businessClassEditorDialog;

	public AddOperationAction(TreeViewer treeViewer) {
		super(ActionTexts.OPERATION_ADD, treeViewer);
		this.setImageDescriptor(ImageManager.getInstance().getImageDescriptor(
				ImageKeys.IMG_MODEL_OPER_NEWELEMENT));
	}

	public AddOperationAction(
			BusinessClassEditorDialog businessClassEditorDialog) {
		super(ActionTexts.OPERATION_ADD);
		this.setImageDescriptor(ImageManager.getInstance().getImageDescriptor(
				ImageKeys.IMG_MODEL_OPER_NEWELEMENT));
		this.businessClassEditorDialog = businessClassEditorDialog;
	}

	public AddOperationAction(String text, ImageDescriptor imageDescriptor,
			TreeViewer treeViewer) {
		super(text, imageDescriptor, treeViewer);
	}

	public AddOperationAction(String text, ImageDescriptor imageDescriptor,
			BusinessClassEditorDialog businessClassEditorDialog) {
		super(text, imageDescriptor);
		this.businessClassEditorDialog = businessClassEditorDialog;
	}

	public void run() {
		OperationEditorDialog dialog = null;
		if (businessClassEditorDialog != null) {
			parent = businessClassEditorDialog.getBusinessClassCopy();
			dialog = new OperationEditorDialog(null, parent);
			if (dialog.open() == Window.OK) {
				BusinessOperation operation = (BusinessOperation) dialog
						.getEditingElement();
				((BusinessClass) parent).addOperation(operation);
				businessClassEditorDialog.getOperationsPage().getTableItems()
						.add(operation);
				businessClassEditorDialog.getOperationsPage().refreshTable();
			}
		} else {
			if (treeViewer != null && treeViewer.getSelection() != null) {
				TreeItem item = treeViewer.getTree().getSelection()[0];
				parent = (ModelPackage) item.getData();
				// dialog = new
				// OperationEditorDialog(treeViewer.getControl().getShell(),
				// parent);
			} else {
				showMessage("no choice one businessClass!");
			}
		}

	}
}
