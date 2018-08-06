package com.mqfdy.code.model.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据类型枚举
 * 
 * @author mqfdy
 * 
 */
public enum DataType {

	Integer("integer", false, false,11,0), 
	Long("long", false, false,19,0), 
	Short("short", false, false,5,0), 
	Byte("byte", false, false,3,0), 
	Float("float",false, false,12,0), 
	Double("double", false, false,22,0), 
	Big_decimal("big_decimal", true, true, 8, 2), 
	Character("character", true,false, 1), 
	String("string", true, false, 32), 
	Boolean("boolean",false, false), 
	Date("date", false, false,10,0), 
	Time("time", false,false,10,0), 
	Timestamp("timestamp", false, false,19,0), 
	Clob("clob", true,false, 2400),
	// Blob("blob",false,false),
	Set("set", 0), Map("map", 0), List("list", 0);

	public static final int INTERNAL = 0;
	public static final int NORMAL = 1;
	/**
	 * hibernet对应的值
	 */
	private String value_hibernet;

	/**
	 * java对应的值
	 */
	private boolean hasDataLength;

	/**
	 * sql数据库对应的值
	 */
	private boolean hasDataPrecision;

	private int defaultLength;

	private int defaultPrecision;

	private int type = NORMAL;

	DataType(String value_hibernet, boolean hasDataLength,
			boolean hasDataPrecision) {
		this.value_hibernet = value_hibernet;
		this.hasDataLength = hasDataLength;
		this.hasDataPrecision = hasDataPrecision;
	}

	DataType(String value_hibernet, int type) {
		this.value_hibernet = value_hibernet;
		this.type = type;
	}

	DataType(String value_hibernet, boolean hasDataLength,
			boolean hasDataPrecision, int defaultLength) {
		this.value_hibernet = value_hibernet;
		this.hasDataLength = hasDataLength;
		this.hasDataPrecision = hasDataPrecision;
		this.defaultLength = defaultLength;
	}

	DataType(String value_hibernet, boolean hasDataLength,
			boolean hasDataPrecision, int defaultLength, int defaultPrecision) {
		this.value_hibernet = value_hibernet;
		this.hasDataLength = hasDataLength;
		this.hasDataPrecision = hasDataPrecision;
		this.defaultLength = defaultLength;
		this.defaultPrecision = defaultPrecision;
	}

	public static List<DataType> getDataTypes() {
		List<DataType> list = new ArrayList<DataType>();
		for (int i = 0; i < DataType.values().length; i++) {
			if (DataType.values()[i].getType() == NORMAL) {
				list.add(DataType.values()[i]);
			}
		}
		return list;
	}

	public static List<DataType> getAllDataTypes() {
		List<DataType> list = new ArrayList<DataType>();
		for (int i = 0; i < DataType.values().length; i++) {
			list.add(DataType.values()[i]);
		}
		return list;
	}

	public static String[] getDataTypesString() {
		List<DataType> list = getDataTypes();
		String[] s = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			s[i] = list.get(i).value_hibernet;
		}
		return s;
	}

	public static String[] getDataTypeString() {
		String[] s = new String[2];
		s[0] = Long.value_hibernet;
		s[1] = String.value_hibernet;
		return s;
	}

	public static String[] getAllDataTypesString() {
		List<DataType> list = getAllDataTypes();
		String[] s = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			s[i] = list.get(i).value_hibernet;
		}
		return s;
	}

	public static DataType getDataType(String value_hibernet) {
		if (value_hibernet == null) {
			return null;
		}
		for (DataType t : DataType.values()) {
			if (value_hibernet.equals(t.getValue_hibernet())) {
				return t;
			}
		}
		return null;
	}

	public static DataType getDataType(int index) {
		List<DataType> list = getAllDataTypes();
		if (index < 0 || index >= list.size()) {
			return null;
		}
		return list.get(index);
	}

	public static int getIndex(String value_hibernet) {
		for (int i = 0; i < DataType.values().length; i++) {
			if (DataType.values()[i].getValue_hibernet().equals(value_hibernet)) {
				return i;
			}
		}
		return -1;
	}

	public String getValue_hibernet() {
		return value_hibernet;
	}

	public void setValue_hibernet(String value_hibernet) {
		this.value_hibernet = value_hibernet;
	}

	public boolean isHasDataLength() {
		return hasDataLength;
	}

	public void setHasDataLength(boolean hasDataLength) {
		this.hasDataLength = hasDataLength;
	}

	public boolean isHasDataPrecision() {
		return hasDataPrecision;
	}

	public void setHasDataPrecision(boolean hasDataPrecision) {
		this.hasDataPrecision = hasDataPrecision;
	}

	public int getDefaultLength() {
		return defaultLength;
	}

	public void setDefaultLength(int defaultLength) {
		this.defaultLength = defaultLength;
	}

	public int getDefaultPrecision() {
		return defaultPrecision;
	}

	public void setDefaultPrecision(int defaultPrecision) {
		this.defaultPrecision = defaultPrecision;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

}
