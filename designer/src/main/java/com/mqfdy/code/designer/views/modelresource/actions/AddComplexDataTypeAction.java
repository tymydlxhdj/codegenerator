package com.mqfdy.code.designer.views.modelresource.actions;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.TreeViewer;

import com.mqfdy.code.designer.dialogs.BusinessClassEditorDialog;
import com.mqfdy.code.designer.models.ActionTexts;
import com.mqfdy.code.designer.models.ImageKeys;
import com.mqfdy.code.designer.models.ImageManager;

// TODO: Auto-generated Javadoc
/**
 * 新增业务类属性动作.
 *
 * @author mqfdy
 */
public class AddComplexDataTypeAction extends TreeAction {

	/** The business class editor dialog. */
	private BusinessClassEditorDialog businessClassEditorDialog;

	/**
	 * Instantiates a new adds the complex data type action.
	 *
	 * @param treeViewer
	 *            the tree viewer
	 */
	public AddComplexDataTypeAction(TreeViewer treeViewer) {
		super(ActionTexts.COMPLEXDATATYPE_ADD, treeViewer);
		this.setImageDescriptor(ImageManager.getInstance().getImageDescriptor(
				ImageKeys.IMG_MODEL_OPER_NEWELEMENT));
	}

	/**
	 * Instantiates a new adds the complex data type action.
	 *
	 * @param businessClassEditorDialog
	 *            the business class editor dialog
	 */
	public AddComplexDataTypeAction(
			BusinessClassEditorDialog businessClassEditorDialog) {
		super(ActionTexts.COMPLEXDATATYPE_ADD);
		this.setImageDescriptor(ImageManager.getInstance().getImageDescriptor(
				ImageKeys.IMG_MODEL_OPER_NEWELEMENT));
		this.businessClassEditorDialog = businessClassEditorDialog;
	}

	/**
	 * Instantiates a new adds the complex data type action.
	 *
	 * @param text
	 *            the text
	 * @param imageDescriptor
	 *            the image descriptor
	 * @param treeViewer
	 *            the tree viewer
	 */
	public AddComplexDataTypeAction(String text,
			ImageDescriptor imageDescriptor, TreeViewer treeViewer) {
		super(text, imageDescriptor, treeViewer);
	}

	/**
	 * Instantiates a new adds the complex data type action.
	 *
	 * @param text
	 *            the text
	 * @param imageDescriptor
	 *            the image descriptor
	 * @param businessClassEditorDialog
	 *            the business class editor dialog
	 */
	public AddComplexDataTypeAction(String text,
			ImageDescriptor imageDescriptor,
			BusinessClassEditorDialog businessClassEditorDialog) {
		super(text, imageDescriptor);
		this.businessClassEditorDialog = businessClassEditorDialog;
	}

	/**
	 * 
	 */
	public void run() {
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
