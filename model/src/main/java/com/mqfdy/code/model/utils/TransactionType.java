package com.mqfdy.code.model.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 事务类型
 * 
 * @author mqfdy
 * 
 */
public enum TransactionType {
	Required("REQUIRED", "Required"), Supports("SUPPORTS", "Supports"), Mandatory(
			"MANDATORY", "Mandatory"), Requires_new("REQUIRES_NEW",
			"Requires_new"), Not_supported("NOT_SUPPORTED", "Not_supported"), Never(
			"NEVER", "Never"), Nested("NESTED", "Nested");

	/**
	 * 值
	 */
	private String value;

	/**
	 * 显示值
	 */
	private String displayValue;

	TransactionType(String value, String displayValue) {
		this.value = value;
		this.displayValue = displayValue;
	}

	public static List<TransactionType> getTransactionTypes() {
		List<TransactionType> list = new ArrayList<TransactionType>();
		for (int i = 0; i < TransactionType.values().length; i++) {
			list.add(TransactionType.values()[i]);
		}
		return list;
	}

	public static String[] getTransactionTypesString() {
		List<TransactionType> list = getTransactionTypes();
		String[] s = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			s[i] = list.get(i).getDisplayValue();
		}
		return s;
	}

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

	public static TransactionType getTransactionType(int index) {
		List<TransactionType> list = getTransactionTypes();
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
