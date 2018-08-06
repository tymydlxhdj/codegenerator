package com.mqfdy.code.model.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 校验器类型
 * 
 * @author mqfdy
 * 
 */
public enum ValidatorType {
//字符串长度校验StringLength
	Nullable("NOTNULL", "必填校验", "不能为空", "1"),
	// add 2.0
	StringLength("STRING","字符串长度","长度不符合要求","2"),
	CNString("CHINESE", "中文字符串","只能输入中文", "2"),
	ENString("LETTER", "英文字符串", "只能输入英文", "2"), 
	Number("DIGIT", "数字校验器（包含小数）", "必须为数字", "2"), 
	Integer("INTEGER", "整数", "必须为整数","2"), 
	Long("LONG", "大整数", "必须为整数","2"), 
	PastCode("ZIPCODE", "邮编", "邮编有误", "2"), 
	DateTime("DATETIME","日期", "日期格式不正确", "2"), 
	URL("URL", "URL校验", "URL格式不正确", "2"), 
	Email("EMAIL", "邮箱地址", "邮箱地址格式不正确", "2"),
	Regular("REGEXP", "正则表达式","输入不正确", "2"),
	//Unique("UNIQUE", "唯一", "违反唯一性约束", "3"),
	// Ognl("Ognl","Ognl表达式"),
	Custom("CUSTOM", "自定义", "输入不正确", "4");

	/**
	 * 值
	 */
	private String value;

	/**
	 * 显示值
	 */
	private String displayValue;

	/**
	 * 错误信息
	 */
	private String errorMessage;

	/**
	 * 分组，用于冲突定义
	 */
	private String group;

	ValidatorType(String value, String displayValue) {
		this.value = value;
		this.displayValue = displayValue;
	}

	ValidatorType(String value, String displayValue, String errorMessage,
			String group) {
		this.value = value;
		this.displayValue = displayValue;
		this.errorMessage = errorMessage;
		this.group = group;
	}

	public static List<ValidatorType> getValidatorTypes() {
		List<ValidatorType> list = new ArrayList<ValidatorType>();
		for (int i = 0; i < ValidatorType.values().length; i++) {
			list.add(ValidatorType.values()[i]);
		}
		return list;
	}

	public static String[] getValidatorTypesString() {
		List<ValidatorType> list = getValidatorTypes();
		String[] s = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			s[i] = list.get(i).displayValue;
		}
		return s;
	}

	public static int getIndex(String value) {
		for (int i = 0; i < ValidatorType.values().length; i++) {
			if (value.equals(ValidatorType.values()[i].getValue())) {
				return i;
			}
		}
		return -1;
	}

	public static ValidatorType getValidatorType(String value) {
		if (value == null)
			return null;
		for (int i = 0; i < ValidatorType.values().length; i++) {
			if (value.equals(ValidatorType.values()[i].getValue())) {
				return ValidatorType.values()[i];
			}
		}
		return null;
	}

	public static ValidatorType getValidatorType(int index) {
		List<ValidatorType> list = getValidatorTypes();
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

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}
}
