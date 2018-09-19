package com.mqfdy.code.model.utils;

import java.util.ArrayList;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Enum ParamType.
 *
 * @author mqfdy
 */
public enum ParamType {

	/** The min length. */
	minLength("minLength", "最小长度"), /** The max length. */
 maxLength("maxLength", "最大长度"), /** The min value. */
 minValue(
			"minValue", "最小值"), 
 /** The max value. */
 maxValue("maxValue", "最大值"), 
 /** The min date. */
 minDate("minDate",
			"最早日期"), 
 /** The max date. */
 maxDate("maxDate", "最晚日期"), 
 /** The expression. */
 expression("expression", "表达式"), 
 /** The custom validator class name. */
 customValidatorClassName(
			"customValidatorClassName", "校验类名称");
	// interfaceName("INTERFACENAME","接口名"),
	// Ognl("OGNL","Ognl表达式"),
	// Custom("CUSTOM","自定义");

	/** 值. */
	private String value;

	/** 显示值. */
	private String displayValue;

	/**
	 * Instantiates a new param type.
	 *
	 * @param value
	 *            the value
	 * @param displayValue
	 *            the display value
	 */
	ParamType(String value, String displayValue) {
		this.value = value;
		this.displayValue = displayValue;
	}

	/**
	 * Gets the param types.
	 *
	 * @author mqfdy
	 * @param key
	 *            the key
	 * @return the param types
	 * @Date 2018-09-03 09:00
	 */
	public static List<ParamType> getParamTypes(String key) {
		List<ParamType> list = new ArrayList<ParamType>();
		if (key == null)
			return list;
		if (key.equals(ValidatorType.DateTime.getValue())
				|| key.equals(ValidatorType.Number.getValue())
				|| key.equals(ValidatorType.Integer.getValue())) {
			list.add(ParamType.minValue);
			list.add(ParamType.maxValue);
		} else if (key.equals(ValidatorType.CNString.getValue())
				|| key.equals(ValidatorType.ENString.getValue())
				|| key.equals(ValidatorType.StringLength.getValue())) {
			list.add(ParamType.minLength);
			list.add(ParamType.maxLength);
		} else if (key.equals(ValidatorType.Regular.getValue())) {
			list.add(ParamType.expression);
		} else if (key.equals(ValidatorType.Custom.getValue())) {
			list.add(ParamType.customValidatorClassName);
		}
		return list;
	}

	/**
	 * Gets the param types.
	 *
	 * @author mqfdy
	 * @param validatorType
	 *            the validator type
	 * @return the param types
	 * @Date 2018-09-03 09:00
	 */
	public static List<ParamType> getParamTypes(ValidatorType validatorType) {
		List<ParamType> list = new ArrayList<ParamType>();
		switch (validatorType) {
		case CNString:
		case ENString:
		case StringLength:
		case DateTime:
		case Number:
			list.add(ParamType.minValue);
			list.add(ParamType.maxValue);
			break;
		case Regular:
			// case Ognl:
			list.add(ParamType.expression);
			break;
		case Custom:
			list.add(ParamType.customValidatorClassName);
			// list.add(ParamType.interfaceName);
			break;

		}
		return list;
	}

	/**
	 * Gets the param types string.
	 *
	 * @author mqfdy
	 * @param key
	 *            the key
	 * @return the param types string
	 * @Date 2018-09-03 09:00
	 */
	public static String[] getParamTypesString(String key) {
		List<ParamType> list = getParamTypes(key);
		String[] s = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			s[i] = list.get(i).displayValue;
		}
		return s;
	}

	/**
	 * Gets the param types value.
	 *
	 * @author mqfdy
	 * @param key
	 *            the key
	 * @return the param types value
	 * @Date 2018-09-03 09:00
	 */
	public static String[] getParamTypesValue(String key) {
		List<ParamType> list = getParamTypes(key);
		String[] s = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			s[i] = list.get(i).value;
		}
		return s;
	}

	/**
	 * Gets the param type value.
	 *
	 * @author mqfdy
	 * @param displayValue
	 *            the display value
	 * @return the param type value
	 * @Date 2018-09-03 09:00
	 */
	public static String getParamTypeValue(String displayValue) {
		if (displayValue == null) {
			return "";
		}
		for (ParamType t : ParamType.values()) {
			if (displayValue.equals(t.getDisplayValue())) {
				return t.getValue();
			}
		}
		return "";
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
