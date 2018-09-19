package com.mqfdy.code.datasource.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.mqfdy.code.generator.utils.StringUtils;

// TODO: Auto-generated Javadoc
/**
 * The Class DateTimeUtil.
 *
 * @author mqfdy
 */
public class DateTimeUtil {

	/**
	 * 时间格式字符串转时间.
	 *
	 * @author mqfdy
	 * @param dateStr
	 *            the date str
	 * @return the date
	 * @Date 2018-09-03 09:00
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
	 * 时间转字符串.
	 *
	 * @author mqfdy
	 * @param date
	 *            the date
	 * @return the string
	 * @Date 2018-09-03 09:00
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
	 * 时间格式字符串转时间.
	 *
	 * @author mqfdy
	 * @param dateStr
	 *            the date str
	 * @param format
	 *            the format
	 * @return the date
	 * @Date 2018-09-03 09:00
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
	 * 将日期按照 yyyy-MM-dd 格式转换为字符串.
	 *
	 * @author mqfdy
	 * @param date
	 *            the date
	 * @return the string
	 * @Date 2018-09-03 09:00
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
