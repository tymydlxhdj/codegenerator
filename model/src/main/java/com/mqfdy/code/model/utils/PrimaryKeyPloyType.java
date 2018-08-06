package com.mqfdy.code.model.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 主键策略枚举
 * 
 * @author mqfdy
 * 
 */
public enum PrimaryKeyPloyType {

	UUID("UUID", "UUID"), // UUID
	SEQUENCE("SEQUENCE", "数据库序列"), // 数据库序列
	MANUAL("ASSIGNED", "手工填写"),// 手工填写
	IDENTITY("IDENTITY", "自增长");// 自增长

	/**
	 * 值
	 */
	private String value;

	/**
	 * 显示值
	 */
	private String displayValue;

	PrimaryKeyPloyType(String value, String displayValue) {
		this.value = value;
		this.displayValue = displayValue;
	}

	public static List<PrimaryKeyPloyType> getPrimaryKeyPloyTypes() {
		List<PrimaryKeyPloyType> list = new ArrayList<PrimaryKeyPloyType>();
		for (int i = 0; i < PrimaryKeyPloyType.values().length; i++) {
			list.add(PrimaryKeyPloyType.values()[i]);
		}
		return list;
	}

	public static String[] getPrimaryKeyPloyTypesString() {
		List<PrimaryKeyPloyType> list = getPrimaryKeyPloyTypes();
		String[] s = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			s[i] = list.get(i).displayValue;
		}
		return s;
	}
	public static String[] getPrimaryKeyPloyTypesByString() {
		String[] s = new String[2];
		s[0] = UUID.displayValue;
		s[1] = MANUAL.displayValue;
		return s;
	}
	public static String[] getPrimaryKeyPloyTypesByLong() {
		String[] s = new String[3];
		s[0] = SEQUENCE.displayValue;
		s[1] = MANUAL.displayValue;
		s[2] = IDENTITY.displayValue;
		return s;
	}
	public static PrimaryKeyPloyType getPrimaryKeyPloyType(String value) {
		if (value == null) {
			return null;
		}
		for (PrimaryKeyPloyType t : PrimaryKeyPloyType.values()) {
			if (value.equals(t.getValue())) {
				return t;
			}
		}
		return null;
	}

	public static PrimaryKeyPloyType getPrimaryKeyPloyTypeByDisplayValue(
			String value) {
		if (value == null) {
			return null;
		}
		for (PrimaryKeyPloyType t : PrimaryKeyPloyType.values()) {
			if (value.equals(t.getDisplayValue())) {
				return t;
			}
		}
		return null;
	}

	public static int getIndex(String value) {
		if (value == null) {
			return -1;
		}
		for (int i = 0; i < PrimaryKeyPloyType.values().length; i++) {
			if (value.equals(PrimaryKeyPloyType.values()[i].getValue())) {
				return i;
			}
		}
		return -1;
	}

	public static PrimaryKeyPloyType getPrimaryKeyPloyType(int index) {
		List<PrimaryKeyPloyType> list = getPrimaryKeyPloyTypes();
		if (index < 0 || index >= list.size()) {
			return null;
		}
		return list.get(index);
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDisplayValue() {
		return displayValue;
	}

	public void setDisplayValue(String displayValue) {
		this.displayValue = displayValue;
	}

}
