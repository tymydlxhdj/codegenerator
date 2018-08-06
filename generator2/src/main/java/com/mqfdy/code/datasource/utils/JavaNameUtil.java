package com.mqfdy.code.datasource.utils;

import java.util.Locale;
import java.util.StringTokenizer;

/**
 * @title:Java名称转换工具
 * @description:
 * @author mqfdy
 */
public class JavaNameUtil {

	/**
	 * 取得表列所对应的java名称
	 * 
	 * @description:以-或_为分隔符，首段字符串的首位字母小写，其他字符串的首位字母大写，其它字母小写
	 * @param colName
	 *            表列名称
	 * @return
	 */
	public static String getColJavaName(String colName) {
		if (colName == null || colName.trim().equals(""))
			return "";
		String javaName = "";
		StringTokenizer token = new StringTokenizer(colName, "-,_");
		if (token.hasMoreTokens()) {
			String firstToken = token.nextToken();
			javaName = firstToken.toLowerCase(Locale.getDefault());
			if(firstToken.length() > 1){
				javaName = firstToken.substring(0, 1).toLowerCase(Locale.getDefault())+firstToken.substring(1);
			}
		}
		while (token.hasMoreTokens()) {
			String nextToken = token.nextToken();
			nextToken = nextToken.toLowerCase(Locale.getDefault());
			javaName += nextToken.substring(0, 1).toUpperCase(Locale.getDefault())
					+ nextToken.substring(1);
		}
		if (javaName.trim().length() >= 2) {
			javaName = javaName.trim();
			javaName = javaName.substring(0, 2).toLowerCase(Locale.getDefault())
					+ javaName.substring(2);
		}
		return javaName.trim();

	}

	/**
	 * 取得数据库表所对应的java名称
	 * 
	 * @description:以-或_为分隔符，每段字符串的首位字母大写，其它字母小写
	 * @param tableName
	 *            数据库表名称
	 * @return
	 */
	public static String getTableJavaName(String tableName) {
		if (tableName == null || tableName.trim().equals(""))
			return "";
		String javaName = "";
		StringTokenizer token = new StringTokenizer(tableName.trim(), "-,_");
		if (token.hasMoreTokens()) {
			String firstToken = token.nextToken();
			firstToken = firstToken.toLowerCase(Locale.getDefault());

			javaName = firstToken.substring(0, 1).toUpperCase(Locale.getDefault())
					+ firstToken.substring(1);
		}
		while (token.hasMoreTokens()) {
			String nextToken = token.nextToken();
			nextToken = nextToken.toLowerCase(Locale.getDefault());
			javaName += nextToken.substring(0, 1).toUpperCase(Locale.getDefault())
					+ nextToken.substring(1);
		}
		if (javaName.trim().length() >= 2) {
			javaName = javaName.trim();
			javaName = javaName.substring(0, 1)
					+ javaName.substring(1, 2).toLowerCase(Locale.getDefault())
					+ javaName.substring(2);
		}
		return javaName.trim();

	}
}
