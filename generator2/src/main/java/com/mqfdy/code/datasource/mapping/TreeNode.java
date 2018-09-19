package com.mqfdy.code.datasource.mapping;

import java.util.ArrayList;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class TreeNode.
 *
 * @author mqfdy
 */
public class TreeNode implements Cloneable {

	/** The id. */
	private String id;
	
	/** The name. */
	private String name;
	
	/** The display name. */
	private String displayName;
	
	/** The type. */
	private int type;
	
	/** The is checked. */
	private boolean isChecked;
	
	/** The is disabled. */
	private boolean isDisabled;
	
	/** The data. */
	private Object data;		//复杂类型数据
	
	/** The backup. */
	private Object backup;		//备用数据
	
	/** The model. */
	private TreeModel model;
	
	/** The parent. */
	private TreeNode parent;
	
	/** The childs. */
	private List<TreeNode> childs = new ArrayList<TreeNode>();
	
	/**
	 * Gets the data.
	 *
	 * @author mqfdy
	 * @return the data
	 * @Date 2018-09-03 09:00
	 */
	public Object getData() {
		return data;
	}

	/**
	 * Sets the data.
	 *
	 * @author mqfdy
	 * @param data
	 *            the new data
	 * @Date 2018-09-03 09:00
	 */
	public void setData(Object data) {
		this.data = data;
	}

	/**
	 * Gets the name.
	 *
	 * @author mqfdy
	 * @return the name
	 * @Date 2018-09-03 09:00
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @author mqfdy
	 * @param name
	 *            the new name
	 * @Date 2018-09-03 09:00
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the display name.
	 *
	 * @author mqfdy
	 * @return the display name
	 * @Date 2018-09-03 09:00
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * Sets the display name.
	 *
	 * @author mqfdy
	 * @param displayName
	 *            the new display name
	 * @Date 2018-09-03 09:00
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	/**
	 * Gets the type.
	 *
	 * @author mqfdy
	 * @return the type
	 * @Date 2018-09-03 09:00
	 */
	public int getType() {
		return type;
	}

	/**
	 * Sets the type.
	 *
	 * @author mqfdy
	 * @param type
	 *            the new type
	 * @Date 2018-09-03 09:00
	 */
	public void setType(int type) {
		this.type = type;
	}

	/**
	 * Gets the parent.
	 *
	 * @author mqfdy
	 * @return the parent
	 * @Date 2018-09-03 09:00
	 */
	public TreeNode getParent() {
		return parent;
	}

	/**
	 * Sets the parent.
	 *
	 * @author mqfdy
	 * @param parent
	 *            the new parent
	 * @Date 2018-09-03 09:00
	 */
	public void setParent(TreeNode parent) {
		this.parent = parent;
	}

	/**
	 * Gets the childs.
	 *
	 * @author mqfdy
	 * @return the childs
	 * @Date 2018-09-03 09:00
	 */
	public List<TreeNode> getChilds() {
		return childs;
	}

	/**
	 * Sets the childs.
	 *
	 * @author mqfdy
	 * @param childs
	 *            the new childs
	 * @Date 2018-09-03 09:00
	 */
	public void setChilds(List<TreeNode> childs) {
		this.childs = childs;
	}

	/**
	 * Checks if is checked.
	 *
	 * @author mqfdy
	 * @return true, if is checked
	 * @Date 2018-09-03 09:00
	 */
	public boolean isChecked() {
		return isChecked;
	}

	/**
	 * Sets the checked.
	 *
	 * @author mqfdy
	 * @param isChecked
	 *            the new checked
	 * @Date 2018-09-03 09:00
	 */
	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}

	/**
	 * Checks if is disabled.
	 *
	 * @author mqfdy
	 * @return true, if is disabled
	 * @Date 2018-09-03 09:00
	 */
	public boolean isDisabled() {
		return isDisabled;
	}

	/**
	 * Sets the disabled.
	 *
	 * @author mqfdy
	 * @param isDisabled
	 *            the new disabled
	 * @Date 2018-09-03 09:00
	 */
	public void setDisabled(boolean isDisabled) {
		this.isDisabled = isDisabled;
	}

	/**
	 * Gets the model.
	 *
	 * @author mqfdy
	 * @return the model
	 * @Date 2018-09-03 09:00
	 */
	public TreeModel getModel() {
		return model;
	}

	/**
	 * Sets the model.
	 *
	 * @author mqfdy
	 * @param model
	 *            the new model
	 * @Date 2018-09-03 09:00
	 */
	public void setModel(TreeModel model) {
		this.model = model;
	}

	/**
	 * Gets the backup.
	 *
	 * @author mqfdy
	 * @return the backup
	 * @Date 2018-09-03 09:00
	 */
	public Object getBackup() {
		return backup;
	}

	/**
	 * Sets the backup.
	 *
	 * @author mqfdy
	 * @param backup
	 *            the new backup
	 * @Date 2018-09-03 09:00
	 */
	public void setBackup(Object backup) {
		this.backup = backup;
	}

	/**
	 * Gets the id.
	 *
	 * @author mqfdy
	 * @return the id
	 * @Date 2018-09-03 09:00
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @author mqfdy
	 * @param id
	 *            the new id
	 * @Date 2018-09-03 09:00
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Copy.
	 *
	 * @author mqfdy
	 * @return the tree node
	 * @Date 2018-09-03 09:00
	 */
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
