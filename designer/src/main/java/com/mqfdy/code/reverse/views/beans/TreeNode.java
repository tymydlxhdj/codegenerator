package com.mqfdy.code.reverse.views.beans;

import java.util.ArrayList;
import java.util.List;

import com.mqfdy.code.reverse.views.models.TreeModel;

public class TreeNode implements Cloneable {

	private String id;
	private String name;
	private String displayName;
	private int type;
	private boolean isChecked;
	private boolean isDisabled;
	
	private Object data;		//复杂类型数据
	private Object backup;		//备用数据
	
	private TreeModel model;
	
	private TreeNode parent;
	private List<TreeNode> childs = new ArrayList<TreeNode>();
	
	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public TreeNode getParent() {
		return parent;
	}

	public void setParent(TreeNode parent) {
		this.parent = parent;
	}

	public List<TreeNode> getChilds() {
		return childs;
	}

	public void setChilds(List<TreeNode> childs) {
		this.childs = childs;
	}

	public boolean isChecked() {
		return isChecked;
	}

	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}

	public boolean isDisabled() {
		return isDisabled;
	}

	public void setDisabled(boolean isDisabled) {
		this.isDisabled = isDisabled;
	}

	public TreeModel getModel() {
		return model;
	}

	public void setModel(TreeModel model) {
		this.model = model;
	}

	public Object getBackup() {
		return backup;
	}

	public void setBackup(Object backup) {
		this.backup = backup;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public TreeNode copy() {
		TreeNode newTreeNode = new TreeNode();
		newTreeNode.setId(id);
		newTreeNode.setName(name);
		newTreeNode.setDisplayName(displayName);
		newTreeNode.setType(type);
		newTreeNode.setChecked(isChecked);
		newTreeNode.setDisabled(isDisabled);
		newTreeNode.setData(data);
		newTreeNode.setBackup(backup);
		newTreeNode.setModel(model);
		newTreeNode.setParent(parent);
		newTreeNode.setChilds(childs);
		return newTreeNode;
	}
}
