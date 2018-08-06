package com.mqfdy.code.designer.views.modelresource.actions;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.TreeItem;

import com.mqfdy.code.designer.dialogs.BusinessClassEditorDialog;
import com.mqfdy.code.designer.dialogs.BusinessClassRelationEditDialog;
import com.mqfdy.code.designer.models.ActionTexts;
import com.mqfdy.code.designer.models.BusinessModelEvent;
import com.mqfdy.code.designer.models.ImageKeys;
import com.mqfdy.code.designer.models.ImageManager;
import com.mqfdy.code.model.AbstractModelElement;

/**
 * 新增业务类属性动作
 * 
 * @author mqfdy
 * 
 */
public class AddAssocitionAction extends TreeAction {

	private BusinessClassEditorDialog businessClassEditorDialog;

	public AddAssocitionAction(String text, TreeViewer treeViewer) {
		super(text, treeViewer);
		if (ActionTexts.ASSOCIATION_ONE2ONE_ADD.equals(text)) {
			this.setImageDescriptor(ImageManager.getInstance()
					.getImageDescriptor(
							ImageKeys.IMG_MODEL_TYPE_ASSOCIATION_ONE2ONE));
		} else if (ActionTexts.ASSOCIATION_ONE2MULT_ADD.equals(text)) {
			this.setImageDescriptor(ImageManager.getInstance()
					.getImageDescriptor(
							ImageKeys.IMG_MODEL_TYPE_ASSOCIATION_ONE2MULT));
		} else if (ActionTexts.ASSOCIATION_MULT2ONE_ADD.equals(text)) {
			this.setImageDescriptor(ImageManager.getInstance()
					.getImageDescriptor(
							ImageKeys.IMG_MODEL_TYPE_ASSOCIATION_MULT2ONE));
		} else if (ActionTexts.ASSOCIATION_MULT2MULT_ADD.equals(text)) {
			this.setImageDescriptor(ImageManager.getInstance()
					.getImageDescriptor(
							ImageKeys.IMG_MODEL_TYPE_ASSOCIATION_MULT2MULT));
		}
	}

	public AddAssocitionAction(String text, ImageDescriptor imageDescriptor,
			TreeViewer treeViewer) {
		super(text, imageDescriptor, treeViewer);
	}

	public AddAssocitionAction(String text, ImageDescriptor imageDescriptor,
			BusinessClassEditorDialog businessClassEditorDialog) {
		super(text, imageDescriptor);
		this.businessClassEditorDialog = businessClassEditorDialog;
	}

	public void run() {
		if (treeViewer != null && treeViewer.getSelection() != null) {
			TreeItem item = treeViewer.getTree().getSelection()[0];
			parent = (AbstractModelElement) item.getData();
			if (parent != null) {
				BusinessClassRelationEditDialog dialog = new BusinessClassRelationEditDialog(
						treeViewer.getControl().getShell(), null, parent,
						BusinessModelEvent.MODEL_ELEMENT_ADD, getText());
				dialog.open();
			}
		}
	}
}
