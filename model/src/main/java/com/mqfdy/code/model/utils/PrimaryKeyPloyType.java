package com.mqfdy.code.model.utils;

import java.util.ArrayList;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * 主键策略枚举.
 *
 * @author mqfdy
 */
public enum PrimaryKeyPloyType {

	/** The uuid. */
	UUID("UUID", "UUID"), /** The sequence. */
 // UUID
	SEQUENCE("SEQUENCE", "数据库序列"), 
 /** The manual. */
 // 数据库序列
	MANUAL("ASSIGNED", "手工填写"),
/** The identity. */
// 手工填写
	IDENTITY("IDENTITY", "自增长");// 自增长

	/** 值. */
	private String value;

	/** 显示值. */
	private String displayValue;

	/**
	 * Instantiates a new primary key ploy type.
	 *
	 * @param value
	 *            the value
	 * @param displayValue
	 *            the display value
	 */
	PrimaryKeyPloyType(String value, String displayValue) {
		this.value = value;
		this.displayValue = displayValue;
	}

	/**
	 * Gets the primary key ploy types.
	 *
	 * @author mqfdy
	 * @return the primary key ploy types
	 * @Date 2018-09-03 09:00
	 */
	public static List<PrimaryKeyPloyType> getPrimaryKeyPloyTypes() {
		List<PrimaryKeyPloyType> list = new ArrayList<PrimaryKeyPloyType>();
		for (int i = 0; i < PrimaryKeyPloyType.values().length; i++) {
			list.add(PrimaryKeyPloyType.values()[i]);
		}
		return list;
	}

	/**
	 * Gets the primary key ploy types string.
	 *
	 * @author mqfdy
	 * @return the primary key ploy types string
	 * @Date 2018-09-03 09:00
	 */
	public static String[] getPrimaryKeyPloyTypesString() {
		List<PrimaryKeyPloyType> list = getPrimaryKeyPloyTypes();
		String[] s = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			s[i] = list.get(i).displayValue;
		}
		return s;
	}
	
	/**
	 * Gets the primary key ploy types by string.
	 *
	 * @author mqfdy
	 * @return the primary key ploy types by string
	 * @Date 2018-09-03 09:00
	 */
	public static String[] getPrimaryKeyPloyTypesByString() {
		String[] s = new String[2];
		s[0] = UUID.displayValue;
		s[1] = MANUAL.displayValue;
		return s;
	}
	
	/**
	 * Gets the primary key ploy types by long.
	 *
	 * @author mqfdy
	 * @return the primary key ploy types by long
	 * @Date 2018-09-03 09:00
	 */
	public static String[] getPrimaryKeyPloyTypesByLong() {
		String[] s = new String[3];
		s[0] = SEQUENCE.displayValue;
		s[1] = MANUAL.displayValue;
		s[2] = IDENTITY.displayValue;
		return s;
	}
	
	/**
	 * Gets the primary key ploy type.
	 *
	 * @author mqfdy
	 * @param value
	 *            the value
	 * @return the primary key ploy type
	 * @Date 2018-09-03 09:00
	 */
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

	/**
	 * Gets the primary key ploy type by display value.
	 *
	 * @author mqfdy
	 * @param value
	 *            the value
	 * @return the primary key ploy type by display value
	 * @Date 2018-09-03 09:00
	 */
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

	/**
	 * Gets the index.
	 *
	 * @author mqfdy
	 * @param value
	 *            the value
	 * @return the index
	 * @Date 2018-09-03 09:00
	 */
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

	/**
	 * Gets the primary key ploy type.
	 *
	 * @author mqfdy
	 * @param index
	 *            the index
	 * @return the primary key ploy type
	 * @Date 2018-09-03 09:00
	 */
	public static PrimaryKeyPloyType getPrimaryKeyPloyType(int index) {
		List<PrimaryKeyPloyType> list = getPrimaryKeyPloyTypes();
		if (index < 0 || index >= list.size()) {
			return null;
		}
		return list.get(index);
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
	 * Gets the display value.
	 *
	 * @author mqfdy
	 * @return the display value
	 * @Date 2018-09-03 09:00
	 */
	public String getDisplayValue() {
		return displayValue;
	}

	/**
	 * Sets the display value.
	 *
	 * @author mqfdy
	 * @param displayValue
	 *            the new display value
	 * @Date 2018-09-03 09:00
	 */
	public void setDisplayValue(String displayValue) {
		this.displayValue = displayValue;
	}

}
