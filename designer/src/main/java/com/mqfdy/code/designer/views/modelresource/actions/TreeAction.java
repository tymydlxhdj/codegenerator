package com.mqfdy.code.designer.views.modelresource.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.TreeViewer;

import com.mqfdy.code.designer.models.ActionTexts;
import com.mqfdy.code.model.AbstractModelElement;

/**
 * 树操作
 * 
 * @author mqfdy
 * 
 */
public class TreeAction extends Action {
	/**
	 * 父节点
	 */
	protected AbstractModelElement parent;

	/**
	 * 树显示器
	 */
	protected TreeViewer treeViewer;

	public TreeAction(String text, ImageDescriptor imageDescriptor,
			TreeViewer treeViewer) {
		super(text, imageDescriptor);
		this.treeViewer = treeViewer;
	}

	public TreeAction(String text, TreeViewer treeViewer) {
		super(text);
		this.treeViewer = treeViewer;
	}

	public TreeAction(String text, ImageDescriptor imageDescriptor) {
		super(text, imageDescriptor);
	}

	public TreeAction(String text) {
		super(text);
	}

	/**
	 * 显示提示信息
	 * 
	 * @param message
	 */
	public void showMessage(String message) {
		MessageDialog.openInformation(null, ActionTexts.MESSAGE_TITLE, message);
	}

}
