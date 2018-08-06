package com.mqfdy.code.model.utils;

public enum BuildInType {
	// value对应builtin.xml中内置模型的name
	BusinessOrganization("BusinessOrganization"), User("User");

	private String value;

	BuildInType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
