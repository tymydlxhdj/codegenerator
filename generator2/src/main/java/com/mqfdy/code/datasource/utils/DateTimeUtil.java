package com.mqfdy.code.datasource.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.mqfdy.code.generator.utils.StringUtils;

public class DateTimeUtil {

	/**
	 * 时间格式字符串转时间
	 * 
	 * @param dateStr
	 * @return
	 */
	public static Date string2Date(String dateStr) {
		if (StringUtils.isEmpty(dateStr)) {
			return null;
		}
		DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = df1.parse(dateStr);
		} catch (ParseException e) {
			throw new RuntimeException("时间格式字符串转时间出错,请检查字符串是否正确", e);
		}

		return date;
	}

	/**
	 * 时间转字符串
	 * 
	 * @param dateStr
	 * @return
	 */
	public static String date2String(Date date) {
		if (date == null) {
			return "";
		}

		DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateStr = "";
		try {
			dateStr = df1.format(date);
		} catch (Exception e) {
			throw new RuntimeException("时间转字符串出错", e);
		}

		return dateStr;
	}

	/**
	 * 时间格式字符串转时间
	 * 
	 * @param dateStr
	 * @return
	 */
	public static Date string2Date(String dateStr, String format) {
		if (StringUtils.isEmpty(dateStr)) {
			return null;
		}
		DateFormat df1 = new SimpleDateFormat(format);
		Date date = null;
		try {
			date = df1.parse(dateStr);
		} catch (ParseException e) {
			throw new RuntimeException("时间格式字符串转时间出错,请检查字符串是否正确", e);
		}

		return date;
	}

	/**
	 * 将日期按照 yyyy-MM-dd 格式转换为字符串
	 * 
	 * @return
	 */
	public static String date2String2(Date date) {
		if (date == null) {
			return "";
		}
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String dateStr = "";
		dateStr = df.format(date);

		return dateStr;
	}
}
