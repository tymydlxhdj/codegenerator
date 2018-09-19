package com.mqfdy.code.designer.views.modelresource.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.TreeViewer;

import com.mqfdy.code.designer.models.ActionTexts;
import com.mqfdy.code.model.AbstractModelElement;

// TODO: Auto-generated Javadoc
/**
 * 树操作.
 *
 * @author mqfdy
 */
public class TreeAction extends Action {
	
	/** 父节点. */
	protected AbstractModelElement parent;

	/** 树显示器. */
	protected TreeViewer treeViewer;

	/**
	 * Instantiates a new tree action.
	 *
	 * @param text
	 *            the text
	 * @param imageDescriptor
	 *            the image descriptor
	 * @param treeViewer
	 *            the tree viewer
	 */
	public TreeAction(String text, ImageDescriptor imageDescriptor,
			TreeViewer treeViewer) {
		super(text, imageDescriptor);
		this.treeViewer = treeViewer;
	}

	/**
	 * Instantiates a new tree action.
	 *
	 * @param text
	 *            the text
	 * @param treeViewer
	 *            the tree viewer
	 */
	public TreeAction(String text, TreeViewer treeViewer) {
		super(text);
		this.treeViewer = treeViewer;
	}

	/**
	 * Instantiates a new tree action.
	 *
	 * @param text
	 *            the text
	 * @param imageDescriptor
	 *            the image descriptor
	 */
	public TreeAction(String text, ImageDescriptor imageDescriptor) {
		super(text, imageDescriptor);
	}

	/**
	 * Instantiates a new tree action.
	 *
	 * @param text
	 *            the text
	 */
	public TreeAction(String text) {
		super(text);
	}

	/**
	 * 显示提示信息.
	 *
	 * @author mqfdy
	 * @param message
	 *            the message
	 * @Date 2018-09-03 09:00
	 */
	public void showMessage(String message) {
		MessageDialog.openInformation(null, ActionTexts.MESSAGE_TITLE, message);
	}

}
