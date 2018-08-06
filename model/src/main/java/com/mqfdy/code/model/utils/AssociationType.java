package com.mqfdy.code.model.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 关联类型枚举
 * 
 * @author mqfdy
 * 
 */
public enum AssociationType {

	one2one("1-1", "1对1"), // 1对1
	one2mult("1-m", "1对多"), // 1对多
	mult2one("m-1", "多对1"), // 多对1
	mult2mult("m-n", "多对多");// 多对多

	/**
	 * 值
	 */
	private String value;

	/**
	 * 显示值
	 */
	private String displayValue;

	AssociationType(String value, String displayValue) {
		this.value = value;
		this.displayValue = displayValue;
	}

	public static List<AssociationType> getAssociationTypes() {
		List<AssociationType> list = new ArrayList<AssociationType>();
		for (int i = 0; i < AssociationType.values().length; i++) {
			list.add(AssociationType.values()[i]);
		}
		return list;
	}

	public static AssociationType getAssociationType(String value) {
		if (value == null) {
			return AssociationType.one2one;
		}
		for (AssociationType t : AssociationType.values()) {
			if (value.equals(t.getValue())) {
				return t;
			}
		}
		return AssociationType.one2one;
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
