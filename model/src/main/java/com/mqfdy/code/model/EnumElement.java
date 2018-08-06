package com.mqfdy.code.model;

import java.util.List;

/**
 * 枚举元素
 * 
 * @author mqfdy
 * 
 */
public class EnumElement extends AbstractModelElement {

	private Enumeration belongEnumeration;
	private String key;
	private String value;

	public EnumElement(String key, String value) {
		this.key = key;
		this.value = value;
		this.displayName = value;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
		this.displayName = value;
	}

	public Enumeration getBelongEnumeration() {
		return belongEnumeration;
	}

	public void setBelongEnumeration(Enumeration belongEnumeration) {
		this.belongEnumeration = belongEnumeration;
	}

	public AbstractModelElement getParent() {
		return this.belongEnumeration;
	}

	public List<AbstractModelElement> getChildren() {
		return EMPTY_CHILD;
	}

	public EnumElement clone() {
		EnumElement ele = new EnumElement(this.key, this.value);
		ele.setId(id);
		ele.setBelongEnumeration(belongEnumeration);
		ele.setDisplayName(displayName);
		ele.setName(name);
		ele.setOrderNum(this.getOrderNum());
		ele.setRemark(remark);
		ele.setStereotype(this.getStereotype());
		return ele;
	}

}
