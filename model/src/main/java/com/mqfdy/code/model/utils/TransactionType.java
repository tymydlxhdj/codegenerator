package com.mqfdy.code.model.utils;

import java.util.ArrayList;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * 事务类型.
 *
 * @author mqfdy
 */
public enum TransactionType {
	
	/** The Required. */
	Required("REQUIRED", "Required"), 
 /** The Supports. */
 Supports("SUPPORTS", "Supports"), 
 /** The Mandatory. */
 Mandatory(
			"MANDATORY", "Mandatory"), 
 /** The Requires new. */
 Requires_new("REQUIRES_NEW",
			"Requires_new"), 
 /** The Not supported. */
 Not_supported("NOT_SUPPORTED", "Not_supported"), 
 /** The Never. */
 Never(
			"NEVER", "Never"), 
 /** The Nested. */
 Nested("NESTED", "Nested");

	/** 值. */
	private String value;

	/** 显示值. */
	private String displayValue;

	/**
	 * Instantiates a new transaction type.
	 *
	 * @param value
	 *            the value
	 * @param displayValue
	 *            the display value
	 */
	TransactionType(String value, String displayValue) {
		this.value = value;
		this.displayValue = displayValue;
	}

	/**
	 * Gets the transaction types.
	 *
	 * @author mqfdy
	 * @return the transaction types
	 * @Date 2018-09-03 09:00
	 */
	public static List<TransactionType> getTransactionTypes() {
		List<TransactionType> list = new ArrayList<TransactionType>();
		for (int i = 0; i < TransactionType.values().length; i++) {
			list.add(TransactionType.values()[i]);
		}
		return list;
	}

	/**
	 * Gets the transaction types string.
	 *
	 * @author mqfdy
	 * @return the transaction types string
	 * @Date 2018-09-03 09:00
	 */
	public static String[] getTransactionTypesString() {
		List<TransactionType> list = getTransactionTypes();
		String[] s = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			s[i] = list.get(i).getDisplayValue();
		}
		return s;
	}

	/**
	 * Gets the transaction type.
	 *
	 * @author mqfdy
	 * @param value
	 *            the value
	 * @return the transaction type
	 * @Date 2018-09-03 09:00
	 */
	public static TransactionType getTransactionType(String value) {
		if (value == null) {
			return null;
		}
		for (TransactionType t : TransactionType.values()) {
			if (value.equals(t.getValue())) {
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
		for (int i = 0; i < TransactionType.values().length; i++) {
			if (value.equals(TransactionType.values()[i].getValue())) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Gets the transaction type.
	 *
	 * @author mqfdy
	 * @param index
	 *            the index
	 * @return the transaction type
	 * @Date 2018-09-03 09:00
	 */
	public static TransactionType getTransactionType(int index) {
		List<TransactionType> list = getTransactionTypes();
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
