package com.mqfdy.code.designer.views.modelresource.actions;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.TreeItem;

import com.mqfdy.code.designer.dialogs.BusinessClassEditorDialog;
import com.mqfdy.code.designer.dialogs.DTOEditDialog;
import com.mqfdy.code.designer.models.ActionTexts;
import com.mqfdy.code.designer.models.ImageKeys;
import com.mqfdy.code.designer.models.ImageManager;
import com.mqfdy.code.model.AbstractModelElement;

// TODO: Auto-generated Javadoc
/**
 * 新增业务类属性动作.
 *
 * @author mqfdy
 */
public class AddDTOAction extends TreeAction {

	/** The business class editor dialog. */
	private BusinessClassEditorDialog businessClassEditorDialog;

	/**
	 * Instantiates a new adds the DTO action.
	 *
	 * @param treeViewer
	 *            the tree viewer
	 */
	public AddDTOAction(TreeViewer treeViewer) {
		super(ActionTexts.DTO_ADD, treeViewer);
		this.setImageDescriptor(ImageManager.getInstance().getImageDescriptor(
				ImageKeys.IMG_MODEL_TYPE_DTO));
	}

	/**
	 * Instantiates a new adds the DTO action.
	 *
	 * @param businessClassEditorDialog
	 *            the business class editor dialog
	 */
	public AddDTOAction(BusinessClassEditorDialog businessClassEditorDialog) {
		super(ActionTexts.DTO_ADD);
		this.setImageDescriptor(ImageManager.getInstance().getImageDescriptor(
				ImageKeys.IMG_MODEL_TYPE_DTO));
		this.businessClassEditorDialog = businessClassEditorDialog;
	}

	/**
	 * Instantiates a new adds the DTO action.
	 *
	 * @param text
	 *            the text
	 * @param imageDescriptor
	 *            the image descriptor
	 * @param treeViewer
	 *            the tree viewer
	 */
	public AddDTOAction(String text, ImageDescriptor imageDescriptor,
			TreeViewer treeViewer) {
		super(text, imageDescriptor, treeViewer);
	}

	/**
	 * Instantiates a new adds the DTO action.
	 *
	 * @param text
	 *            the text
	 * @param imageDescriptor
	 *            the image descriptor
	 * @param businessClassEditorDialog
	 *            the business class editor dialog
	 */
	public AddDTOAction(String text, ImageDescriptor imageDescriptor,
			BusinessClassEditorDialog businessClassEditorDialog) {
		super(text, imageDescriptor);
		this.businessClassEditorDialog = businessClassEditorDialog;
	}

	/**
	 * 
	 */
	public void run() {

		if (treeViewer != null && treeViewer.getSelection() != null) {
			TreeItem item = treeViewer.getTree().getSelection()[0];
			parent = (AbstractModelElement) item.getData();

			if (parent != null) {
				DTOEditDialog dialog = new DTOEditDialog(treeViewer
						.getControl().getShell(), parent, null);
				dialog.open();
			}
		}
		// PropertyEditorDialog dialog = null;
		// if(businessClassEditorDialog != null)
		// {
		// parent = businessClassEditorDialog.getBusinessClassCopy();
		// dialog = new PropertyEditorDialog(null, parent);
		// }
		// else
		// {
		// if( treeViewer!= null && treeViewer.getSelection() != null)
		// {
		// TreeItem item= treeViewer.getTree().getSelection()[0];
		// parent = (ModelPackage)item.getData();
		// dialog = new PropertyEditorDialog(treeViewer.getControl().getShell(),
		// parent);
		// }
		// else
		// {
		// showMessage("no choice one businessClass!");
		// }
		// }
		//
		// if(dialog != null)
		// {
		// dialog.create();
		// if (dialog.open() == Window.OK)
		// {
		// showMessage("关闭");
		// }
		// }

	}
}
