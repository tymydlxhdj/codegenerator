package com.mqfdy.code.model.utils;

// TODO: Auto-generated Javadoc
/**
 * The Enum BuildInType.
 *
 * @author mqfdy
 */
public enum BuildInType {
	
	/** The Business organization. */
	// value对应builtin.xml中内置模型的name
	BusinessOrganization("BusinessOrganization"), 
 /** The User. */
 User("User");

	/** The value. */
	private String value;

	/**
	 * Instantiates a new builds the in type.
	 *
	 * @param value
	 *            the value
	 */
	BuildInType(String value) {
		this.value = value;
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
	}
}
