package com.mqfdy.code.reverse.views.models;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;

import com.mqfdy.code.reverse.views.beans.TreeNode;


public class MultiTreeModel {

	public static final String ADD = "add";

	public static final String REMOVE = "remove";
	
	private PropertyChangeSupport delegate;
	
	private List<TreeNode> rootList;
	
	public MultiTreeModel(List<TreeNode> rootList) {
		this.setRootList(rootList);
		delegate = new PropertyChangeSupport(this);
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
	
	public void add(TreeNode currentNode, List<TreeNode> newTreeNodeList) {
		for(TreeNode newTreeNode: newTreeNodeList) {
			add(currentNode, newTreeNode);
		}
	}

	public List<TreeNode> getRootList() {
		return rootList;
	}

	public void setRootList(List<TreeNode> rootList) {
		this.rootList = rootList;
	}
	
}
