package com.mqfdy.code.model;

import java.util.List;

import org.dom4j.Element;

// TODO: Auto-generated Javadoc
/**
 * 属性分组.
 *
 * @author mqfdy
 */
public class PropertyGroup extends AbstractModelElement {

	/**
	 * Instantiates a new property group.
	 */
	public PropertyGroup() {
	}

	/** The parent. */
	private AbstractModelElement parent;

	/**
	 * Instantiates a new property group.
	 *
	 * @param name
	 *            the name
	 * @param displayName
	 *            the display name
	 */
	public PropertyGroup(String name, String displayName) {
		this.name = name;
		this.displayName = displayName;
	}

	/**
	 * Instantiates a new property group.
	 *
	 * @param groupElement
	 *            the group element
	 */
	public PropertyGroup(Element groupElement) {
		initBasicAttributes(groupElement);
	}

	/**
	 * @param groupsElement
	 * @return
	 */
	public Element generateXmlElement(Element groupsElement) {
		Element groupElement = groupsElement.addElement("Group");// 创建Group节点
		generateBasicAttributes(groupElement);

		return groupElement;
	}

	/**
	 * Instantiates a new property group.
	 *
	 * @param id
	 *            the id
	 */
	public PropertyGroup(String id) {
		setId(id);
	}

	/**
	 * @return
	 */
	public AbstractModelElement getParent() {
		return parent;
	}

	/**
	 * @return
	 */
	public List<AbstractModelElement> getChildren() {
		return EMPTY_CHILD;
	}

	/**
	 * Sets the parent.
	 *
	 * @author mqfdy
	 * @param parent
	 *            the new parent
	 * @Date 2018-09-03 09:00
	 */
	public void setParent(AbstractModelElement parent) {
		this.parent = parent;
	}

	/**
	 * @return
	 */
	@Override
	public String getFullName() {
		// TODO Auto-generated method stub
		return parent.getFullName() + "." + displayName;
	}

}
