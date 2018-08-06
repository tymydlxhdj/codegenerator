package com.mqfdy.code.designer.views.modelresource.actions;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.TreeItem;

import com.mqfdy.code.designer.dialogs.EnumEditDialog;
import com.mqfdy.code.designer.models.ActionTexts;
import com.mqfdy.code.designer.models.ImageKeys;
import com.mqfdy.code.designer.models.ImageManager;
import com.mqfdy.code.model.AbstractModelElement;

/**
 * 新增枚举动作
 * 
 * @author mqfdy
 * 
 */
public class AddEnumerationAction extends TreeAction {

	public AddEnumerationAction(TreeViewer treeViewer) {
		super(ActionTexts.ENUMERATION_ADD);
		this.setImageDescriptor(ImageManager.getInstance().getImageDescriptor(
				ImageKeys.IMG_MODEL_TYPE_ENUMERATION));
		this.treeViewer = treeViewer;

	}

	public AddEnumerationAction(String text, ImageDescriptor imageDescriptor,
			TreeViewer treeViewer) {
		super(text, imageDescriptor);
		this.treeViewer = treeViewer;
	}

	@Override
	public void run() {

		if (treeViewer != null && treeViewer.getSelection() != null) {
			TreeItem item = treeViewer.getTree().getSelection()[0];
			parent = (AbstractModelElement) item.getData();
			if (parent != null) {
				EnumEditDialog dialog = new EnumEditDialog(treeViewer
						.getControl().getShell(), parent, null);
				dialog.open();
			}
		}
	}

}
