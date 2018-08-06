package com.mqfdy.code.model.utils;

import java.util.ArrayList;
import java.util.List;

public enum BuildInProperty {

	ORG_NAME("PARAM_ORG_NAME", "组织名称", BuildInType.BusinessOrganization
			.getValue()), ORG_PROPERTY("PARAM_ORG_PROPERTY", "组织性质",
			BuildInType.BusinessOrganization.getValue()),

	USER_NAME("PARAM_USER_NAME", "用户姓名", BuildInType.User.getValue()), USER_LOGIN_NAME(
			"PARAM_USER_LOGIN_NAME", "用户登录名", BuildInType.User.getValue()), USER_ID(
			"PARAM_USER_IDENTITY_ID", "用户ID", BuildInType.User.getValue());

	private String value;
	private String dispValue;
	private String type;

	public static String type_org = BuildInType.BusinessOrganization.getValue();
	public static String type_user = BuildInType.User.getValue();

	BuildInProperty(String value, String dispValue, String type) {
		this.value = value;
		this.dispValue = dispValue;
		this.type = type;
	}

	public static List<BuildInProperty> getPropertyTypes(String type) {
		List<BuildInProperty> list = new ArrayList<BuildInProperty>();
		for (int i = 0; i < BuildInProperty.values().length; i++) {
			if (type.equals(BuildInProperty.values()[i].getType())) {
				list.add(BuildInProperty.values()[i]);
			}
		}
		return list;
	}

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

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDispValue() {
		return dispValue;
	}

	public void setDispValue(String dispValue) {
		this.dispValue = dispValue;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
