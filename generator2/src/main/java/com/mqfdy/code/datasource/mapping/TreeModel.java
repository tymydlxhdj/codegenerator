package com.mqfdy.code.datasource.mapping;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class TreeModel.
 *
 * @author mqfdy
 */
public class TreeModel {

	/** The Constant ADD. */
	public static final String ADD = "add";

	/** The Constant REMOVE. */
	public static final String REMOVE = "remove";
	
	/** The delegate. */
	private PropertyChangeSupport delegate;
	
	/** The root. */
	private TreeNode root;

	/**
	 * Instantiates a new tree model.
	 *
	 * @param root
	 *            the root
	 */
	public TreeModel(TreeNode root) {
		this.root = root;
		delegate = new PropertyChangeSupport(this);
	}
	
	/**
	 * Gets the root.
	 *
	 * @author mqfdy
	 * @return the root
	 * @Date 2018-09-03 09:00
	 */
	public TreeNode getRoot() {
		return root;
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
	 * Removes the.
	 *
	 * @author mqfdy
	 * @param currentNode
	 *            the current node
	 * @Date 2018-09-03 09:00
	 */
	public void remove(TreeNode currentNode) {
		if(currentNode != null) {
			TreeNode parentNode = currentNode.getParent();
			if(parentNode != null) {
				if(parentNode.getChilds().remove(currentNode)) {
					currentNode.setParent(null);
					firePropertyChange(new PropertyChangeEvent(this, REMOVE, null, currentNode));
				}
			} else {
				root = null;
				firePropertyChange(new PropertyChangeEvent(this, REMOVE, null, currentNode));
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
	 * Adds the.
	 *
	 * @author mqfdy
	 * @param parentPath
	 *            the parent path
	 * @param newTree
	 *            the new tree
	 * @Date 2018-09-03 09:00
	 */
	public void add(int[] parentPath, TreeNode newTree) {
		TreeNode parent = findTreeNode(parentPath);
		if (parent != null && !parent.getChilds().contains(newTree)) {
			if(parent.getChilds().add(newTree)) {
				newTree.setParent(parent);
				//此处待修改
				firePropertyChange(new PropertyChangeEvent(this, ADD, null, new Object[] { parent, newTree }));
			}
		}
	}
	
	/**
	 * Find tree node.
	 *
	 * @author mqfdy
	 * @param path
	 *            the path
	 * @return the tree node
	 * @Date 2018-09-03 09:00
	 */
	public TreeNode findTreeNode(int[] path) {
		try {
			TreeNode current = root;
			for (int i = 0; i < path.length; i++) {
				current = (TreeNode) current.getChilds().get(path[i]);
			}
			return current;
		} catch (ArrayIndexOutOfBoundsException e) {
			return null;
		}
	}
	
}
