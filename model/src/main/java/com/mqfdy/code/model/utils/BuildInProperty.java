package com.mqfdy.code.model.utils;

import java.util.ArrayList;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Enum BuildInProperty.
 *
 * @author mqfdy
 */
public enum BuildInProperty {

	/** The org name. */
	ORG_NAME("PARAM_ORG_NAME", "组织名称", BuildInType.BusinessOrganization
			.getValue()), 
 /** The org property. */
 ORG_PROPERTY("PARAM_ORG_PROPERTY", "组织性质",
			BuildInType.BusinessOrganization.getValue()),

	/** The user name. */
	USER_NAME("PARAM_USER_NAME", "用户姓名", BuildInType.User.getValue()), /** The user login name. */
 USER_LOGIN_NAME(
			"PARAM_USER_LOGIN_NAME", "用户登录名", BuildInType.User.getValue()), 
 /** The user id. */
 USER_ID(
			"PARAM_USER_IDENTITY_ID", "用户ID", BuildInType.User.getValue());

	/** The value. */
	private String value;
	
	/** The disp value. */
	private String dispValue;
	
	/** The type. */
	private String type;

	/** The type org. */
	public static String type_org = BuildInType.BusinessOrganization.getValue();
	
	/** The type user. */
	public static String type_user = BuildInType.User.getValue();

	/**
	 * Instantiates a new builds the in property.
	 *
	 * @param value
	 *            the value
	 * @param dispValue
	 *            the disp value
	 * @param type
	 *            the type
	 */
	BuildInProperty(String value, String dispValue, String type) {
		this.value = value;
		this.dispValue = dispValue;
		this.type = type;
	}

	/**
	 * Gets the property types.
	 *
	 * @author mqfdy
	 * @param type
	 *            the type
	 * @return the property types
	 * @Date 2018-09-03 09:00
	 */
	public static List<BuildInProperty> getPropertyTypes(String type) {
		List<BuildInProperty> list = new ArrayList<BuildInProperty>();
		for (int i = 0; i < BuildInProperty.values().length; i++) {
			if (type.equals(BuildInProperty.values()[i].getType())) {
				list.add(BuildInProperty.values()[i]);
			}
		}
		return list;
	}

	/**
	 * Gets the property type.
	 *
	 * @author mqfdy
	 * @param dispValue
	 *            the disp value
	 * @return the property type
	 * @Date 2018-09-03 09:00
	 */
	public static BuildInProperty getPropertyType(String dispValue) {
		if (dispValue == null) {
			return BuildInProperty.ORG_NAME;
		}
		for (BuildInProperty t : BuildInProperty.values()) {
			if (dispValue.equals(t.getDispValue())) {
				return t;
			}
		}
		return BuildInProperty.ORG_NAME;
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

	/**
	 * Gets the disp value.
	 *
	 * @author mqfdy
	 * @return the disp value
	 * @Date 2018-09-03 09:00
	 */
	public String getDispValue() {
		return dispValue;
	}

	/**
	 * Sets the disp value.
	 *
	 * @author mqfdy
	 * @param dispValue
	 *            the new disp value
	 * @Date 2018-09-03 09:00
	 */
	public void setDispValue(String dispValue) {
		this.dispValue = dispValue;
	}

	/**
	 * Gets the type.
	 *
	 * @author mqfdy
	 * @return the type
	 * @Date 2018-09-03 09:00
	 */
	public String getType() {
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
	public void setType(String type) {
		this.type = type;
	}
}
