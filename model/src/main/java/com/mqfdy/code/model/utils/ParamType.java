package com.mqfdy.code.model.utils;

import java.util.ArrayList;
import java.util.List;

public enum ParamType {

	minLength("minLength", "最小长度"), maxLength("maxLength", "最大长度"), minValue(
			"minValue", "最小值"), maxValue("maxValue", "最大值"), minDate("minDate",
			"最早日期"), maxDate("maxDate", "最晚日期"), expression("expression", "表达式"), customValidatorClassName(
			"customValidatorClassName", "校验类名称");
	// interfaceName("INTERFACENAME","接口名"),
	// Ognl("OGNL","Ognl表达式"),
	// Custom("CUSTOM","自定义");

	/**
	 * 值
	 */
	private String value;

	/**
	 * 显示值
	 */
	private String displayValue;

	ParamType(String value, String displayValue) {
		this.value = value;
		this.displayValue = displayValue;
	}

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

	public static String[] getParamTypesString(String key) {
		List<ParamType> list = getParamTypes(key);
		String[] s = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			s[i] = list.get(i).displayValue;
		}
		return s;
	}

	public static String[] getParamTypesValue(String key) {
		List<ParamType> list = getParamTypes(key);
		String[] s = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			s[i] = list.get(i).value;
		}
		return s;
	}

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
