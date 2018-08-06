package com.mqfdy.code.reverse.views.models;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;

import com.mqfdy.code.reverse.views.beans.TreeNode;


public class TreeModel {

	public static final String ADD = "add";

	public static final String REMOVE = "remove";
	
	private PropertyChangeSupport delegate;
	
	private TreeNode root;

	public TreeModel(TreeNode root) {
		this.root = root;
		delegate = new PropertyChangeSupport(this);
	}
	
	public TreeNode getRoot() {
		return root;
	}
	
	/**
	 * 添加属性监听
	 * @param listener
	 */
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		delegate.addPropertyChangeListener(listener);
	}

	/**
	 * 触发属性改变的事件，通知监听器。
	 * @param evt
	 */
	public void firePropertyChange(PropertyChangeEvent evt) {
		delegate.firePropertyChange(evt);
	}

	/**
	 * 删除属性监听
	 * @param listener
	 */
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		delegate.removePropertyChangeListener(listener);
	}
	
	public void add(TreeNode currentNode, TreeNode newTreeNode) {
		if (currentNode != null && !currentNode.getChilds().contains(newTreeNode)) {
			if(currentNode.getChilds().add(newTreeNode)) {
				newTreeNode.setParent(currentNode);
				firePropertyChange(new PropertyChangeEvent(this, ADD, null, new Object[] { currentNode, newTreeNode }));
			}
		}
	}
	
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
	
	public void add(TreeNode currentNode, List<TreeNode> newTreeNodeList) {
		for(TreeNode newTreeNode: newTreeNodeList) {
			add(currentNode, newTreeNode);
		}
	}
	
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
