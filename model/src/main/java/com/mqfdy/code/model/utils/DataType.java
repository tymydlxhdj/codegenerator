package com.mqfdy.code.model.utils;

import java.util.ArrayList;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * 数据类型枚举.
 *
 * @author mqfdy
 */
public enum DataType {

	/** The Integer. */
	Integer("integer", false, false,11,0), 
	
	/** The Long. */
	Long("long", false, false,19,0), 
	
	/** The Short. */
	Short("short", false, false,5,0), 
	
	/** The Byte. */
	Byte("byte", false, false,3,0), 
	
	/** The Float. */
	Float("float",false, false,12,0), 
	
	/** The Double. */
	Double("double", false, false,22,0), 
	
	/** The Big decimal. */
	Big_decimal("big_decimal", true, true, 8, 2), 
	
	/** The Character. */
	Character("character", true,false, 1), 
	
	/** The String. */
	String("string", true, false, 32), 
	
	/** The Boolean. */
	Boolean("boolean",false, false), 
	
	/** The Date. */
	Date("date", false, false,10,0), 
	
	/** The Time. */
	Time("time", false,false,10,0), 
	
	/** The Timestamp. */
	Timestamp("timestamp", false, false,19,0), 
	
	/** The Clob. */
	Clob("clob", true,false, 2400),
	
	/** The Set. */
	// Blob("blob",false,false),
	Set("set", 0), 
 /** The Map. */
 Map("map", 0), 
 /** The List. */
 List("list", 0);

	/** The Constant INTERNAL. */
	public static final int INTERNAL = 0;
	
	/** The Constant NORMAL. */
	public static final int NORMAL = 1;
	
	/** hibernet对应的值. */
	private String value_hibernet;

	/** java对应的值. */
	private boolean hasDataLength;

	/** sql数据库对应的值. */
	private boolean hasDataPrecision;

	/** The default length. */
	private int defaultLength;

	/** The default precision. */
	private int defaultPrecision;

	/** The type. */
	private int type = NORMAL;

	/**
	 * Instantiates a new data type.
	 *
	 * @param value_hibernet
	 *            the value hibernet
	 * @param hasDataLength
	 *            the has data length
	 * @param hasDataPrecision
	 *            the has data precision
	 */
	DataType(String value_hibernet, boolean hasDataLength,
			boolean hasDataPrecision) {
		this.value_hibernet = value_hibernet;
		this.hasDataLength = hasDataLength;
		this.hasDataPrecision = hasDataPrecision;
	}

	/**
	 * Instantiates a new data type.
	 *
	 * @param value_hibernet
	 *            the value hibernet
	 * @param type
	 *            the type
	 */
	DataType(String value_hibernet, int type) {
		this.value_hibernet = value_hibernet;
		this.type = type;
	}

	/**
	 * Instantiates a new data type.
	 *
	 * @param value_hibernet
	 *            the value hibernet
	 * @param hasDataLength
	 *            the has data length
	 * @param hasDataPrecision
	 *            the has data precision
	 * @param defaultLength
	 *            the default length
	 */
	DataType(String value_hibernet, boolean hasDataLength,
			boolean hasDataPrecision, int defaultLength) {
		this.value_hibernet = value_hibernet;
		this.hasDataLength = hasDataLength;
		this.hasDataPrecision = hasDataPrecision;
		this.defaultLength = defaultLength;
	}

	/**
	 * Instantiates a new data type.
	 *
	 * @param value_hibernet
	 *            the value hibernet
	 * @param hasDataLength
	 *            the has data length
	 * @param hasDataPrecision
	 *            the has data precision
	 * @param defaultLength
	 *            the default length
	 * @param defaultPrecision
	 *            the default precision
	 */
	DataType(String value_hibernet, boolean hasDataLength,
			boolean hasDataPrecision, int defaultLength, int defaultPrecision) {
		this.value_hibernet = value_hibernet;
		this.hasDataLength = hasDataLength;
		this.hasDataPrecision = hasDataPrecision;
		this.defaultLength = defaultLength;
		this.defaultPrecision = defaultPrecision;
	}

	/**
	 * Gets the data types.
	 *
	 * @author mqfdy
	 * @return the data types
	 * @Date 2018-09-03 09:00
	 */
	public static List<DataType> getDataTypes() {
		List<DataType> list = new ArrayList<DataType>();
		for (int i = 0; i < DataType.values().length; i++) {
			if (DataType.values()[i].getType() == NORMAL) {
				list.add(DataType.values()[i]);
			}
		}
		return list;
	}

	/**
	 * Gets the all data types.
	 *
	 * @author mqfdy
	 * @return the all data types
	 * @Date 2018-09-03 09:00
	 */
	public static List<DataType> getAllDataTypes() {
		List<DataType> list = new ArrayList<DataType>();
		for (int i = 0; i < DataType.values().length; i++) {
			list.add(DataType.values()[i]);
		}
		return list;
	}

	/**
	 * Gets the data types string.
	 *
	 * @author mqfdy
	 * @return the data types string
	 * @Date 2018-09-03 09:00
	 */
	public static String[] getDataTypesString() {
		List<DataType> list = getDataTypes();
		String[] s = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			s[i] = list.get(i).value_hibernet;
		}
		return s;
	}

	/**
	 * Gets the data type string.
	 *
	 * @author mqfdy
	 * @return the data type string
	 * @Date 2018-09-03 09:00
	 */
	public static String[] getDataTypeString() {
		String[] s = new String[2];
		s[0] = Long.value_hibernet;
		s[1] = String.value_hibernet;
		return s;
	}

	/**
	 * Gets the all data types string.
	 *
	 * @author mqfdy
	 * @return the all data types string
	 * @Date 2018-09-03 09:00
	 */
	public static String[] getAllDataTypesString() {
		List<DataType> list = getAllDataTypes();
		String[] s = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			s[i] = list.get(i).value_hibernet;
		}
		return s;
	}

	/**
	 * Gets the data type.
	 *
	 * @author mqfdy
	 * @param value_hibernet
	 *            the value hibernet
	 * @return the data type
	 * @Date 2018-09-03 09:00
	 */
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

	/**
	 * Gets the data type.
	 *
	 * @author mqfdy
	 * @param index
	 *            the index
	 * @return the data type
	 * @Date 2018-09-03 09:00
	 */
	public static DataType getDataType(int index) {
		List<DataType> list = getAllDataTypes();
		if (index < 0 || index >= list.size()) {
			return null;
		}
		return list.get(index);
	}

	/**
	 * Gets the index.
	 *
	 * @author mqfdy
	 * @param value_hibernet
	 *            the value hibernet
	 * @return the index
	 * @Date 2018-09-03 09:00
	 */
	public static int getIndex(String value_hibernet) {
		for (int i = 0; i < DataType.values().length; i++) {
			if (DataType.values()[i].getValue_hibernet().equals(value_hibernet)) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Gets the value hibernet.
	 *
	 * @author mqfdy
	 * @return the value hibernet
	 * @Date 2018-09-03 09:00
	 */
	public String getValue_hibernet() {
		return value_hibernet;
	}

	/**
	 * Sets the value hibernet.
	 *
	 * @author mqfdy
	 * @param value_hibernet
	 *            the new value hibernet
	 * @Date 2018-09-03 09:00
	 */
	public void setValue_hibernet(String value_hibernet) {
		this.value_hibernet = value_hibernet;
	}

	/**
	 * Checks if is checks for data length.
	 *
	 * @author mqfdy
	 * @return true, if is checks for data length
	 * @Date 2018-09-03 09:00
	 */
	public boolean isHasDataLength() {
		return hasDataLength;
	}

	/**
	 * Sets the checks for data length.
	 *
	 * @author mqfdy
	 * @param hasDataLength
	 *            the new checks for data length
	 * @Date 2018-09-03 09:00
	 */
	public void setHasDataLength(boolean hasDataLength) {
		this.hasDataLength = hasDataLength;
	}

	/**
	 * Checks if is checks for data precision.
	 *
	 * @author mqfdy
	 * @return true, if is checks for data precision
	 * @Date 2018-09-03 09:00
	 */
	public boolean isHasDataPrecision() {
		return hasDataPrecision;
	}

	/**
	 * Sets the checks for data precision.
	 *
	 * @author mqfdy
	 * @param hasDataPrecision
	 *            the new checks for data precision
	 * @Date 2018-09-03 09:00
	 */
	public void setHasDataPrecision(boolean hasDataPrecision) {
		this.hasDataPrecision = hasDataPrecision;
	}

	/**
	 * Gets the default length.
	 *
	 * @author mqfdy
	 * @return the default length
	 * @Date 2018-09-03 09:00
	 */
	public int getDefaultLength() {
		return defaultLength;
	}

	/**
	 * Sets the default length.
	 *
	 * @author mqfdy
	 * @param defaultLength
	 *            the new default length
	 * @Date 2018-09-03 09:00
	 */
	public void setDefaultLength(int defaultLength) {
		this.defaultLength = defaultLength;
	}

	/**
	 * Gets the default precision.
	 *
	 * @author mqfdy
	 * @return the default precision
	 * @Date 2018-09-03 09:00
	 */
	public int getDefaultPrecision() {
		return defaultPrecision;
	}

	/**
	 * Sets the default precision.
	 *
	 * @author mqfdy
	 * @param defaultPrecision
	 *            the new default precision
	 * @Date 2018-09-03 09:00
	 */
	public void setDefaultPrecision(int defaultPrecision) {
		this.defaultPrecision = defaultPrecision;
	}

	/**
	 * Gets the type.
	 *
	 * @author mqfdy
	 * @return the type
	 * @Date 2018-09-03 09:00
	 */
	public int getType() {
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
	public void setType(int type) {
		this.type = type;
	}

}
