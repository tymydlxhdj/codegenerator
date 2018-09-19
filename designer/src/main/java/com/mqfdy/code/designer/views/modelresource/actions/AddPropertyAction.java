package com.mqfdy.code.designer.views.modelresource.actions;

import java.util.List;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.TreeItem;

import com.mqfdy.code.designer.dialogs.BusinessClassEditorDialog;
import com.mqfdy.code.designer.dialogs.PropertyEditorDialog;
import com.mqfdy.code.designer.models.ActionTexts;
import com.mqfdy.code.designer.models.ImageKeys;
import com.mqfdy.code.designer.models.ImageManager;
import com.mqfdy.code.model.ModelPackage;
import com.mqfdy.code.model.Property;

// TODO: Auto-generated Javadoc
/**
 * 新增业务类属性动作.
 *
 * @author mqfdy
 */
public class AddPropertyAction extends TreeAction {

	/** The business class editor dialog. */
	private BusinessClassEditorDialog businessClassEditorDialog;

	/**
	 * Instantiates a new adds the property action.
	 *
	 * @param treeViewer
	 *            the tree viewer
	 */
	public AddPropertyAction(TreeViewer treeViewer) {
		super(ActionTexts.PROPERTY_ADD, treeViewer);
		this.setImageDescriptor(ImageManager.getInstance().getImageDescriptor(
				ImageKeys.IMG_MODEL_OPER_NEWELEMENT));
	}

	/**
	 * Instantiates a new adds the property action.
	 *
	 * @param businessClassEditorDialog
	 *            the business class editor dialog
	 */
	public AddPropertyAction(BusinessClassEditorDialog businessClassEditorDialog) {
		super(ActionTexts.PROPERTY_ADD);
		this.setImageDescriptor(ImageManager.getInstance().getImageDescriptor(
				ImageKeys.IMG_MODEL_OPER_NEWELEMENT));
		this.businessClassEditorDialog = businessClassEditorDialog;
	}

	/**
	 * Instantiates a new adds the property action.
	 *
	 * @param text
	 *            the text
	 * @param imageDescriptor
	 *            the image descriptor
	 * @param treeViewer
	 *            the tree viewer
	 */
	public AddPropertyAction(String text, ImageDescriptor imageDescriptor,
			TreeViewer treeViewer) {
		super(text, imageDescriptor, treeViewer);
	}

	/**
	 * Instantiates a new adds the property action.
	 *
	 * @param text
	 *            the text
	 * @param imageDescriptor
	 *            the image descriptor
	 * @param businessClassEditorDialog
	 *            the business class editor dialog
	 */
	public AddPropertyAction(String text, ImageDescriptor imageDescriptor,
			BusinessClassEditorDialog businessClassEditorDialog) {
		super(text, imageDescriptor);
		this.businessClassEditorDialog = businessClassEditorDialog;
	}

	/**
	 * 
	 */
	public void run() {
		PropertyEditorDialog dialog = null;
		if (businessClassEditorDialog != null) {
			List<Property> tempProperties = businessClassEditorDialog
					.getBasicInfoPage().getTableItems();
			parent = businessClassEditorDialog.getBusinessClassCopy();
			dialog = new PropertyEditorDialog(null, tempProperties, parent);
			int d = dialog.open();
			if (d == Window.OK) {
				Property property = (Property) dialog.getEditingElement();
				businessClassEditorDialog.getBasicInfoPage().getTableItems()
						.add(property);
				businessClassEditorDialog.getBasicInfoPage().refreshTable();
			}
			else if (d == PropertyEditorDialog.SAVE_AND_CONTINUE_ID) {
				Property property = (Property) dialog.getEditingElement();
				businessClassEditorDialog.getBasicInfoPage().getTableItems()
						.add(property);
				businessClassEditorDialog.getBasicInfoPage().refreshTable();
				run();
			}
		} else {
			if (treeViewer != null && treeViewer.getSelection() != null) {
				TreeItem item = treeViewer.getTree().getSelection()[0];
				parent = (ModelPackage) item.getData();
				// dialog = new
				// PropertyEditorDialog(treeViewer.getControl().getShell(),
				// parent);
			} else {
				showMessage("no choice one businessClass!");
			}
		}

	}
}
