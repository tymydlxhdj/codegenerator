package com.mqfdy.code.model.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

// TODO: Auto-generated Javadoc
/**
 * 字符串处理工具类.
 *
 * @author mqfdy
 */
public class StringUtil {

	/**
	 * String 2 byte.
	 *
	 * @author mqfdy
	 * @param str
	 *            the str
	 * @return the byte[]
	 * @throws UnsupportedEncodingException
	 *             the unsupported encoding exception
	 * @Date 2018-09-03 09:00
	 */
	public static byte[] string2Byte(String str)
			throws UnsupportedEncodingException {
		if (str == null)
			str = "";
		return str.getBytes("UTF-8");
	}

	/**
	 * 判断字符串是否为 null 或为空字符串。.
	 *
	 * @author mqfdy
	 * @param str
	 *            the str
	 * @return true, if is empty
	 * @Date 2018-09-03 09:00
	 */
	public static boolean isEmpty(String str) {
		if (str == null) {
			return true;
		} else if (str.equals("")) {
			return true;
		}
		return false;
	}

	/**
	 * 将字符串转化为 boolean.
	 *
	 * @author mqfdy
	 * @param str
	 *            the str
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	public static boolean string2Boolean(String str) {
		str = convertNull2EmptyStr(str);
		if ("TRUE".equals(str.trim().toUpperCase(Locale.getDefault()))) {
			return true;
		}
		if ("1".equals(str)) {
			return true;
		}
		return false;
	}

	/**
	 * 将boolean转化为 字符串.
	 *
	 * @author mqfdy
	 * @param b
	 *            the b
	 * @return the string
	 * @Date 2018-09-03 09:00
	 */
	public static String boolean2String(boolean b) {
		if (b) {
			return "true";
		}
		return "false";
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

	/**
	 * 判断数组是否包含.
	 *
	 * @author mqfdy
	 * @param strs
	 *            the strs
	 * @param str
	 *            the str
	 * @return true, if is contain
	 * @Date 2018-09-03 09:00
	 */
	public static boolean isContain(String[] strs, String str) {
		if (str == null || str.length() == 0) {
			return false;
		}
		for (int i = 0; i < strs.length; i++) {
			if (strs[i].equals(str)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 重排xml格式.
	 *
	 * @author mqfdy
	 * @param str
	 *            the str
	 * @return the string
	 * @Date 2018-09-03 09:00
	 */
	public static String prettyXml(String str) {
		StringReader in = null;
		StringWriter out = null;
		try {
			SAXReader reader = new SAXReader();
			// 创建一个串的字符输入流
			in = new StringReader(str);
			org.dom4j.Document doc = reader.read(in);
			// 创建输出格式
			OutputFormat formate = OutputFormat.createPrettyPrint();
			// 创建输出
			out = new StringWriter();
			// 创建输出流
			XMLWriter writer = new XMLWriter(out, formate);
			// 输出格式化的串到目标中,格式化后的串保存在out中。
			writer.write(doc);
		} catch (Exception ioe) {
			return str;
		} finally {
			if (in != null) {
				in.close();
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return out.toString();
	}

	/**
	 * 将字符串转换为int类型.
	 *
	 * @author mqfdy
	 * @param numberStr
	 *            the number str
	 * @return the integer
	 * @Date 2018-09-03 09:00
	 */
	public static Integer string2Int(String numberStr) {
		if (isEmpty(numberStr)) {
			return 0;
		}
		if (numberStr.endsWith("%")) {
			numberStr = numberStr.substring(0, numberStr.lastIndexOf("%"));
		}
		return Integer.parseInt(numberStr);
	}

	/**
	 * 判断字符串是否是数字，null值返回false.
	 *
	 * @author mqfdy
	 * @param str
	 *            the str
	 * @return true, if is numeric
	 * @Date 2018-09-03 09:00
	 */
	public static boolean isNumeric(String str) {
		if (isEmpty(str)) {
			return false;
		}
		int sz = str.length();
		for (int i = 0; i < sz; i++) {
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 将字符串转换为float类型.
	 *
	 * @author mqfdy
	 * @param numberStr
	 *            the number str
	 * @return the float
	 * @Date 2018-09-03 09:00
	 */
	public static Float string2Float(String numberStr) {
		if (isEmpty(numberStr)) {
			return null;
		}
		return Float.parseFloat(numberStr);
	}

	/**
	 * 将NULL转换为""返回.
	 *
	 * @author mqfdy
	 * @param str
	 *            the str
	 * @return the string
	 * @Date 2018-09-03 09:00
	 */
	public static String convertNull2EmptyStr(String str) {
		if (null == str) {
			return "";
		}

		return str;
	}

	/**
	 * 将类名改为表名，用于创建Table对象.
	 *
	 * @author mqfdy
	 * @param className
	 *            持久化类
	 * @return the string
	 * @Date 2018-09-03 09:00
	 */
	public static String convertFromClassName2TableName(String className) {

		return className.toUpperCase(Locale.getDefault());
	}

	/**
	 * 根据数据类型判断默认值的格式。如数值类型 0 ，字符串类型 'vvvv'.
	 *
	 * @author mqfdy
	 * @param dataType
	 *            the data type
	 * @param defaultValue
	 *            the default value
	 * @return the string
	 * @Date 2018-09-03 09:00
	 */
	public static String handledefaultValue(String dataType, String defaultValue) {
		if (isEmpty(defaultValue)) {
			return null;
		}

		if ("string".equalsIgnoreCase(dataType)) {
			defaultValue = "'" + defaultValue + "'";
		}

		return defaultValue;
	}

	/**
	 * 从“包名+类名”中提取出类名.
	 *
	 * @author mqfdy
	 * @param fullName
	 *            the full name
	 * @return the class name from full name
	 * @Date 2018-09-03 09:00
	 */
	public static String getClassNameFromFullName(String fullName) {

		if (isEmpty(fullName)) {
			return null;
		}
		return fullName.substring(fullName.lastIndexOf(".") + 1);
	}

	/**
	 * 形如“src/com.mqfdy”，只需后面的包名
	 *
	 * @author mqfdy
	 * @param path
	 *            the path
	 * @return the load package
	 * @Date 2018-09-03 09:00
	 */
	public static String getLoadPackage(String path) {
		if (isEmpty(path)) {
			return null;
		}
		String loadPackage = path.substring(path.lastIndexOf("\\") + 1);
		return loadPackage;
	}

	/**
	 * 形如“src/com.mqfdy”，只需src
	 *
	 * @author mqfdy
	 * @param path
	 *            the path
	 * @return the porject source path
	 * @Date 2018-09-03 09:00
	 */
	public static String getPorjectSourcePath(String path) {

		return path.substring(0, path.lastIndexOf("\\") + 1);
	}

	/**
	 * Gets the package.
	 *
	 * @author mqfdy
	 * @param str
	 *            the str
	 * @return the package
	 * @Date 2018-09-03 09:00
	 */
	public static String getPackage(String str) {
		if (isEmpty(str)) {
			return null;
		}
		return str.substring(0, str.lastIndexOf("."));
	}

	/**
	 * Capitalize.
	 *
	 * @author mqfdy
	 * @param str
	 *            the str
	 * @return the string
	 * @Date 2018-09-03 09:00
	 */
	public static String capitalize(String str) {
		if (str == null || str.length() == 0) {
			return str;
		}
		StringBuilder sb = new StringBuilder(str.length());
		sb.append(Character.toUpperCase(str.charAt(0)));
		sb.append(str.substring(1));
		return sb.toString();
	}

	/**
	 * Un capitalize.
	 *
	 * @author mqfdy
	 * @param str
	 *            the str
	 * @return the string
	 * @Date 2018-09-03 09:00
	 */
	public static String unCapitalize(String str) {
		if (str == null || str.length() == 0) {
			return str;
		}
		StringBuilder sb = new StringBuilder(str.length());
		sb.append(Character.toLowerCase(str.charAt(0)));
		sb.append(str.substring(1));
		return sb.toString();
	}

	/**
	 * Gets the file name list.
	 *
	 * @author mqfdy
	 * @param projectPath
	 *            the project path
	 * @return the file name list
	 * @Date 2018-09-03 09:00
	 */
	public static List<String> getFileNameList(String projectPath) {
		List<String> fileNameList = new ArrayList<String>();
		File fileDir = new File(projectPath);
		getAllFile(fileDir, fileNameList);
		return fileNameList;
	}

	/**
	 * Gets the all file.
	 *
	 * @author mqfdy
	 * @param f
	 *            the f
	 * @param fileNameList
	 *            the file name list
	 * @return the all file
	 * @Date 2018-09-03 09:00
	 */
	private static void getAllFile(File f, List<String> fileNameList) {
		if (f.isDirectory() && f.exists()) {
			File[] files = f.listFiles();
			for (int i = 0; i < files.length; i++) {
				if (files[i].isFile()
						&& files[i].getName().equalsIgnoreCase(
								"DataServiceController.java")) {
					fileNameList.add(files[i].getName());
				} else {
					getAllFile(files[i], fileNameList);
				}
			}
		}
	}

	/**
	 * To camel case.
	 *
	 * @author mqfdy
	 * @param s
	 *            the s
	 * @return the string
	 * @Date 2018-09-03 09:00
	 */
	/*
	 * 去下划线,并改成驼峰式
	 */
	public static  String toCamelCase(String s){
		if (s == null) {  
             return null;  
         }  
        	s = s.toLowerCase(Locale.getDefault());  
   
       StringBuilder sb = new StringBuilder(s.length());  
       boolean upperCase = false;  
       for (int i = 0; i < s.length(); i++) {  
            char c = s.charAt(i);  
   
            if (c=='_') {  
                upperCase = true;  
            } else if (upperCase) {  
                sb.append(Character.toUpperCase(c));  
                upperCase = false;  
            } else {  
                sb.append(c);  
           }  
      }  
 
        return sb.toString();  
	}
	
	/**
	 * First upper.
	 *
	 * @author mqfdy
	 * @param s
	 *            the s
	 * @return the string
	 * @Date 2018-09-03 09:00
	 */
	/*
	 * 去下划线,并将首字母大写
	 */
	public static  String firstUpper(String s){
		
       String str=toCamelCase(s);
       if(str==null){
    	   return null;
       }
 
        return str.substring(0, 1).toUpperCase(Locale.getDefault()) + str.substring(1);  
	}
	
	/**
	 * 列出某路径下的所有文件.
	 *
	 * @author mqfdy
	 * @param path
	 *            the path
	 * @return the list
	 * @Date 2018-09-03 09:00
	 */
	public static List<File> listFile(String path) {
		File dir = new File(path);
		if (!dir.exists()) {
			return null;
		}
		List<File> fileList = new ArrayList<File>();
		File[] files = dir.listFiles();
		for (int i = 0; i < files.length; i++) {
			if (files[i].isFile()) {
				fileList.add(files[i]);
			}
		}
		return fileList;
	}

	/**
	 * 去掉xxx.java中的.java
	 *
	 * @author mqfdy
	 * @param fileName
	 *            the file name
	 * @return the string
	 * @Date 2018-09-03 09:00
	 */
	public static String removeSuffixJava(String fileName) {
		if (isEmpty(fileName)) {
			return "";
		}

		String[] strs = fileName.split("[.]");
		return strs[0];
	}

}
