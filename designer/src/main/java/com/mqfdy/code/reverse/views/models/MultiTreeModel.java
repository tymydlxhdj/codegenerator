package com.mqfdy.code.reverse.views.models;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;

import com.mqfdy.code.reverse.views.beans.TreeNode;


// TODO: Auto-generated Javadoc
/**
 * The Class MultiTreeModel.
 *
 * @author mqfdy
 */
public class MultiTreeModel {

	/** The Constant ADD. */
	public static final String ADD = "add";

	/** The Constant REMOVE. */
	public static final String REMOVE = "remove";
	
	/** The delegate. */
	private PropertyChangeSupport delegate;
	
	/** The root list. */
	private List<TreeNode> rootList;
	
	/**
	 * Instantiates a new multi tree model.
	 *
	 * @param rootList
	 *            the root list
	 */
	public MultiTreeModel(List<TreeNode> rootList) {
		this.setRootList(rootList);
		delegate = new PropertyChangeSupport(this);
	}
	
	/**
	 * 添加属性监听.
	 *
	 * @author mqfdy
	 * @param listener
	 *            the listener
	 * @Date 2018-09-03 09:00
	 */
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		delegate.addPropertyChangeListener(listener);
	}

	/**
	 * 触发属性改变的事件，通知监听器。.
	 *
	 * @author mqfdy
	 * @param evt
	 *            the evt
	 * @Date 2018-09-03 09:00
	 */
	public void firePropertyChange(PropertyChangeEvent evt) {
		delegate.firePropertyChange(evt);
	}

	/**
	 * 删除属性监听.
	 *
	 * @author mqfdy
	 * @param listener
	 *            the listener
	 * @Date 2018-09-03 09:00
	 */
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		delegate.removePropertyChangeListener(listener);
	}
	
	/**
	 * Adds the.
	 *
	 * @author mqfdy
	 * @param currentNode
	 *            the current node
	 * @param newTreeNode
	 *            the new tree node
	 * @Date 2018-09-03 09:00
	 */
	public void add(TreeNode currentNode, TreeNode newTreeNode) {
		if (currentNode != null && !currentNode.getChilds().contains(newTreeNode)) {
			if(currentNode.getChilds().add(newTreeNode)) {
				newTreeNode.setParent(currentNode);
				firePropertyChange(new PropertyChangeEvent(this, ADD, null, new Object[] { currentNode, newTreeNode }));
			}
		}
	}
	
	/**
	 * Adds the.
	 *
	 * @author mqfdy
	 * @param currentNode
	 *            the current node
	 * @param newTreeNodeList
	 *            the new tree node list
	 * @Date 2018-09-03 09:00
	 */
	public void add(TreeNode currentNode, List<TreeNode> newTreeNodeList) {
		for(TreeNode newTreeNode: newTreeNodeList) {
			add(currentNode, newTreeNode);
		}
	}

	/**
	 * Gets the root list.
	 *
	 * @author mqfdy
	 * @return the root list
	 * @Date 2018-09-03 09:00
	 */
	public List<TreeNode> getRootList() {
		return rootList;
	}

	/**
	 * Sets the root list.
	 *
	 * @author mqfdy
	 * @param rootList
	 *            the new root list
	 * @Date 2018-09-03 09:00
	 */
	public void setRootList(List<TreeNode> rootList) {
		this.rootList = rootList;
	}
	
}
