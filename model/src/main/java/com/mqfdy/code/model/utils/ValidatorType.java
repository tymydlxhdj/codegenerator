package com.mqfdy.code.model.utils;

import java.util.ArrayList;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * 校验器类型.
 *
 * @author mqfdy
 */
public enum ValidatorType {

/** The Nullable. */
//字符串长度校验StringLength
	Nullable("NOTNULL", "必填校验", "不能为空", "1"),
	
	/** The String length. */
	// add 2.0
	StringLength("STRING","字符串长度","长度不符合要求","2"),
	
	/** The CN string. */
	CNString("CHINESE", "中文字符串","只能输入中文", "2"),
	
	/** The EN string. */
	ENString("LETTER", "英文字符串", "只能输入英文", "2"), 
	
	/** The Number. */
	Number("DIGIT", "数字校验器（包含小数）", "必须为数字", "2"), 
	
	/** The Integer. */
	Integer("INTEGER", "整数", "必须为整数","2"), 
	
	/** The Long. */
	Long("LONG", "大整数", "必须为整数","2"), 
	
	/** The Past code. */
	PastCode("ZIPCODE", "邮编", "邮编有误", "2"), 
	
	/** The Date time. */
	DateTime("DATETIME","日期", "日期格式不正确", "2"), 
	
	/** The url. */
	URL("URL", "URL校验", "URL格式不正确", "2"), 
	
	/** The Email. */
	Email("EMAIL", "邮箱地址", "邮箱地址格式不正确", "2"),
	
	/** The Regular. */
	Regular("REGEXP", "正则表达式","输入不正确", "2"),
	//Unique("UNIQUE", "唯一", "违反唯一性约束", "3"),
	/** The Custom. */
	// Ognl("Ognl","Ognl表达式"),
	Custom("CUSTOM", "自定义", "输入不正确", "4");

	/** 值. */
	private String value;

	/** 显示值. */
	private String displayValue;

	/** 错误信息. */
	private String errorMessage;

	/** 分组，用于冲突定义. */
	private String group;

	/**
	 * Instantiates a new validator type.
	 *
	 * @param value
	 *            the value
	 * @param displayValue
	 *            the display value
	 */
	ValidatorType(String value, String displayValue) {
		this.value = value;
		this.displayValue = displayValue;
	}

	/**
	 * Instantiates a new validator type.
	 *
	 * @param value
	 *            the value
	 * @param displayValue
	 *            the display value
	 * @param errorMessage
	 *            the error message
	 * @param group
	 *            the group
	 */
	ValidatorType(String value, String displayValue, String errorMessage,
			String group) {
		this.value = value;
		this.displayValue = displayValue;
		this.errorMessage = errorMessage;
		this.group = group;
	}

	/**
	 * Gets the validator types.
	 *
	 * @author mqfdy
	 * @return the validator types
	 * @Date 2018-09-03 09:00
	 */
	public static List<ValidatorType> getValidatorTypes() {
		List<ValidatorType> list = new ArrayList<ValidatorType>();
		for (int i = 0; i < ValidatorType.values().length; i++) {
			list.add(ValidatorType.values()[i]);
		}
		return list;
	}

	/**
	 * Gets the validator types string.
	 *
	 * @author mqfdy
	 * @return the validator types string
	 * @Date 2018-09-03 09:00
	 */
	public static String[] getValidatorTypesString() {
		List<ValidatorType> list = getValidatorTypes();
		String[] s = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			s[i] = list.get(i).displayValue;
		}
		return s;
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
		for (int i = 0; i < ValidatorType.values().length; i++) {
			if (value.equals(ValidatorType.values()[i].getValue())) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Gets the validator type.
	 *
	 * @author mqfdy
	 * @param value
	 *            the value
	 * @return the validator type
	 * @Date 2018-09-03 09:00
	 */
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

	/**
	 * Gets the validator type.
	 *
	 * @author mqfdy
	 * @param index
	 *            the index
	 * @return the validator type
	 * @Date 2018-09-03 09:00
	 */
	public static ValidatorType getValidatorType(int index) {
		List<ValidatorType> list = getValidatorTypes();
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

	/**
	 * Gets the error message.
	 *
	 * @author mqfdy
	 * @return the error message
	 * @Date 2018-09-03 09:00
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * Sets the error message.
	 *
	 * @author mqfdy
	 * @param errorMessage
	 *            the new error message
	 * @Date 2018-09-03 09:00
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	/**
	 * Gets the group.
	 *
	 * @author mqfdy
	 * @return the group
	 * @Date 2018-09-03 09:00
	 */
	public String getGroup() {
		return group;
	}

	/**
	 * Sets the group.
	 *
	 * @author mqfdy
	 * @param group
	 *            the new group
	 * @Date 2018-09-03 09:00
	 */
	public void setGroup(String group) {
		this.group = group;
	}
}
