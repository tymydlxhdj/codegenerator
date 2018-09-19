package com.mqfdy.code.model.utils;

import java.util.ArrayList;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * 关联类型枚举.
 *
 * @author mqfdy
 */
public enum AssociationType {

	/** The one 2 one. */
	one2one("1-1", "1对1"), /** The one 2 mult. */
 // 1对1
	one2mult("1-m", "1对多"), 
 /** The mult 2 one. */
 // 1对多
	mult2one("m-1", "多对1"), 
 /** The mult 2 mult. */
 // 多对1
	mult2mult("m-n", "多对多");// 多对多

	/** 值. */
	private String value;

	/** 显示值. */
	private String displayValue;

	/**
	 * Instantiates a new association type.
	 *
	 * @param value
	 *            the value
	 * @param displayValue
	 *            the display value
	 */
	AssociationType(String value, String displayValue) {
		this.value = value;
		this.displayValue = displayValue;
	}

	/**
	 * Gets the association types.
	 *
	 * @author mqfdy
	 * @return the association types
	 * @Date 2018-09-03 09:00
	 */
	public static List<AssociationType> getAssociationTypes() {
		List<AssociationType> list = new ArrayList<AssociationType>();
		for (int i = 0; i < AssociationType.values().length; i++) {
			list.add(AssociationType.values()[i]);
		}
		return list;
	}

	/**
	 * Gets the association type.
	 *
	 * @author mqfdy
	 * @param value
	 *            the value
	 * @return the association type
	 * @Date 2018-09-03 09:00
	 */
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
