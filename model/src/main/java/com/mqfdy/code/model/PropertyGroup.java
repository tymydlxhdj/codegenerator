package com.mqfdy.code.model;

import java.util.List;

import org.dom4j.Element;

/**
 * 属性分组
 * 
 * @author mqfdy
 * 
 */
public class PropertyGroup extends AbstractModelElement {

	public PropertyGroup() {
	}

	private AbstractModelElement parent;

	public PropertyGroup(String name, String displayName) {
		this.name = name;
		this.displayName = displayName;
	}

	public PropertyGroup(Element groupElement) {
		initBasicAttributes(groupElement);
	}

	public Element generateXmlElement(Element groupsElement) {
		Element groupElement = groupsElement.addElement("Group");// 创建Group节点
		generateBasicAttributes(groupElement);

		return groupElement;
	}

	public PropertyGroup(String id) {
		setId(id);
	}

	public AbstractModelElement getParent() {
		return parent;
	}

	public List<AbstractModelElement> getChildren() {
		return EMPTY_CHILD;
	}

	public void setParent(AbstractModelElement parent) {
		this.parent = parent;
	}

	@Override
	public String getFullName() {
		// TODO Auto-generated method stub
		return parent.getFullName() + "." + displayName;
	}

}
