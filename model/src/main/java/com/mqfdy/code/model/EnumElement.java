package com.mqfdy.code.model;

import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * 枚举元素.
 *
 * @author mqfdy
 */
public class EnumElement extends AbstractModelElement {

	/** The belong enumeration. */
	private Enumeration belongEnumeration;
	
	/** The key. */
	private String key;
	
	/** The value. */
	private String value;

	/**
	 * Instantiates a new enum element.
	 *
	 * @param key
	 *            the key
	 * @param value
	 *            the value
	 */
	public EnumElement(String key, String value) {
		this.key = key;
		this.value = value;
		this.displayName = value;
	}

	/**
	 * Gets the key.
	 *
	 * @author mqfdy
	 * @return the key
	 * @Date 2018-09-03 09:00
	 */
	public String getKey() {
		return key;
	}

	/**
	 * Sets the key.
	 *
	 * @author mqfdy
	 * @param key
	 *            the new key
	 * @Date 2018-09-03 09:00
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * Gets the value.
	 *
	 * @author mqfdy
	 * @return the value
	 * @Date 2018-09-03 09:00
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Sets the value.
	 *
	 * @author mqfdy
	 * @param value
	 *            the new value
	 * @Date 2018-09-03 09:00
	 */
	public void setValue(String value) {
		this.value = value;
		this.displayName = value;
	}

	/**
	 * Gets the belong enumeration.
	 *
	 * @author mqfdy
	 * @return the belong enumeration
	 * @Date 2018-09-03 09:00
	 */
	public Enumeration getBelongEnumeration() {
		return belongEnumeration;
	}

	/**
	 * Sets the belong enumeration.
	 *
	 * @author mqfdy
	 * @param belongEnumeration
	 *            the new belong enumeration
	 * @Date 2018-09-03 09:00
	 */
	public void setBelongEnumeration(Enumeration belongEnumeration) {
		this.belongEnumeration = belongEnumeration;
	}

	/**
	 * @return
	 */
	public AbstractModelElement getParent() {
		return this.belongEnumeration;
	}

	/**
	 * @return
	 */
	public List<AbstractModelElement> getChildren() {
		return EMPTY_CHILD;
	}

	/**
	 * @return
	 */
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
