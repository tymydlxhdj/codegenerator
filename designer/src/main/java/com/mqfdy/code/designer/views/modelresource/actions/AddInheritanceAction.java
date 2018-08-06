package com.mqfdy.code.designer.views.modelresource.actions;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.TreeItem;

import com.mqfdy.code.designer.dialogs.BusinessClassEditorDialog;
import com.mqfdy.code.designer.dialogs.BusinessClassInheritanceRelationEditDialog;
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
public class AddInheritanceAction extends TreeAction {

	private BusinessClassEditorDialog businessClassEditorDialog;

	public AddInheritanceAction(TreeViewer treeViewer) {
		super(ActionTexts.INHERITANCE_ADD, treeViewer);
		this.setImageDescriptor(ImageManager.getInstance().getImageDescriptor(
				ImageKeys.IMG_MODEL_TYPE_INHERITANCE));
	}

	public AddInheritanceAction(
			BusinessClassEditorDialog businessClassEditorDialog) {
		super(ActionTexts.INHERITANCE_ADD);
		this.setImageDescriptor(ImageManager.getInstance().getImageDescriptor(
				ImageKeys.IMG_MODEL_TYPE_INHERITANCE));
		this.businessClassEditorDialog = businessClassEditorDialog;
	}

	public AddInheritanceAction(String text, ImageDescriptor imageDescriptor,
			TreeViewer treeViewer) {
		super(text, imageDescriptor, treeViewer);
	}

	public AddInheritanceAction(String text, ImageDescriptor imageDescriptor,
			BusinessClassEditorDialog businessClassEditorDialog) {
		super(text, imageDescriptor);
		this.businessClassEditorDialog = businessClassEditorDialog;
	}

	public void run() {
		if (treeViewer != null && treeViewer.getSelection() != null) {
			TreeItem item = treeViewer.getTree().getSelection()[0];
			parent = (AbstractModelElement) item.getData();
			if (parent != null) {
				BusinessClassInheritanceRelationEditDialog dialog = new BusinessClassInheritanceRelationEditDialog(
						treeViewer.getControl().getShell(), null, parent,
						BusinessModelEvent.MODEL_ELEMENT_ADD);
				dialog.open();
			}
		}

	}
}
