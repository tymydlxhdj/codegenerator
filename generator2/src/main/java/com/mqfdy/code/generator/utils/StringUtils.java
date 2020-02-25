package com.mqfdy.code.generator.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.jdt.core.JavaConventions;
import org.eclipse.jdt.core.JavaCore;

import com.mqfdy.code.utils.Logger;


// TODO: Auto-generated Javadoc
/**
 * The Class StringUtils.
 *
 * @author mqfdy
 */
public class StringUtils {
	
	/** EOL. */
	private static final String EOL = System.getProperty("line.separator");

	/**
	 * 判断字符串是否为 null 或为空字符串。
	 * 方法描述：isEmpty.
	 *
	 * @author mqfdy
	 * @param str str
	 * @return boolean实例
	 * @Date 2018年8月31日 下午2:40:02
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
	 * 校验表字段是否合规
	 * 方法描述：validateFieldName.
	 *
	 * @author mqfdy
	 * @param fieldName fieldName
	 * @return boolean实例
	 * @Date 2018年8月31日 下午2:40:17
	 */
	public static boolean validateFieldName(String fieldName) {
		
		IStatus fieldNameStatus = JavaConventions.validateFieldName(fieldName, JavaCore.VERSION_1_4,
				JavaCore.VERSION_1_4);
		
		if (fieldNameStatus.getSeverity() != IStatus.OK) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * 方法描述：capitalize.
	 *
	 * @author mqfdy
	 * @param str str
	 * @return String实例
	 * @Date 2018年8月31日 下午2:40:24
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
	 * 方法描述：concat.
	 *
	 * @author mqfdy
	 * @param list list
	 * @return String实例
	 * @Date 2018年8月31日 下午2:40:30
	 */
	public String concat(List<String> list) {
		StringBuffer sb = new StringBuffer();
		int size = list.size();

		for (int i = 0; i < size; i++) {
			sb.append(list.get(i).toString());
		}
		return sb.toString();
	}

	/**
	 * 方法描述：getPackageAsPath.
	 *
	 * @author mqfdy
	 * @param pckge pckge
	 * @return String实例
	 * @Date 2018年8月31日 下午2:40:36
	 */
	static public String getPackageAsPath(String pckge) {
		return pckge.replace('.', File.separator.charAt(0)) + File.separator;
	}

	/**
	 * 方法描述：lowercaseFirstLetter.
	 *
	 * @author mqfdy
	 * @param str str
	 * @return String实例
	 * @Date 2018年8月31日 下午2:40:41
	 */
	static public String lowercaseFirstLetter(String str) {
		String firstLetter = str.substring(0, 1);
		String subLetter = str.substring(1);
		return firstLetter.toLowerCase(Locale.getDefault()).concat(subLetter);
	}
	/**
	 * 方法描述：uppercaseFirstLetter.
	 *
	 * @author mqfdy
	 * @param str str
	 * @return String实例
	 * @Date 2018年8月31日 下午2:40:41
	 */
	static public String uppercaseFirstLetter(String str) {
		String firstLetter = str.substring(0, 1);
		String subLetter = str.substring(1);
		return firstLetter.toUpperCase(Locale.getDefault()).concat(subLetter);
	}

	/**
	 * 方法描述：removeUnderScores.
	 *
	 * @author mqfdy
	 * @param data data
	 * @return String实例
	 * @Date 2018年8月31日 下午2:40:46
	 */
	@Deprecated
	static public String removeUnderScores(String data) {
		String temp = null;
		StringBuffer out = new StringBuffer();
		temp = data;

		StringTokenizer st = new StringTokenizer(temp, "_");

		while (st.hasMoreTokens()) {
			String element = (String) st.nextElement();
			out.append(firstLetterCaps(element));
		}

		return out.toString();
	}

	/**
	 * 方法描述：removeAndHump.
	 *
	 * @author mqfdy
	 * @param data data
	 * @return String实例
	 * @Date 2018年8月31日 下午2:40:53
	 */
	static public String removeAndHump(String data) {
		return removeAndHump(data, "_");
	}

	/**
	 * 方法描述：removeAndHump.
	 *
	 * @author mqfdy
	 * @param data data
	 * @param replaceThis replaceThis
	 * @return String实例
	 * @Date 2018年8月31日 下午2:40:57
	 */
	static public String removeAndHump(String data, String replaceThis) {
		String temp = null;
		StringBuffer out = new StringBuffer();
		temp = data;

		StringTokenizer st = new StringTokenizer(temp, replaceThis);

		while (st.hasMoreTokens()) {
			String element = (String) st.nextElement();
			out.append(capitalizeFirstLetter(element));
		}// while

		return out.toString();
	}

	/**
	 * 方法描述：firstLetterCaps.
	 *
	 * @author mqfdy
	 * @param data data
	 * @return String实例
	 * @Date 2018年8月31日 下午2:41:05
	 */
	static public String firstLetterCaps(String data) {
		String firstLetter = data.substring(0, 1).toUpperCase(Locale.getDefault());
		String restLetters = data.substring(1).toLowerCase(Locale.getDefault());
		return firstLetter + restLetters;
	}

	/**
	 * 方法描述：capitalizeFirstLetter.
	 *
	 * @author mqfdy
	 * @param data data
	 * @return String实例
	 * @Date 2018年8月31日 下午2:41:10
	 */
	static public String capitalizeFirstLetter(String data) {
		String firstLetter = data.substring(0, 1).toUpperCase(Locale.getDefault());
		String restLetters = data.substring(1);
		return firstLetter + restLetters;
	}

	/**
	 * 方法描述：split.
	 *
	 * @author mqfdy
	 * @param line line
	 * @param delim delim
	 * @return String[]实例
	 * @Date 2018年8月31日 下午2:41:15
	 */
	public static String[] split(String line, String delim) {
		List<String> list = new ArrayList<String>();
		StringTokenizer t = new StringTokenizer(line, delim);
		while (t.hasMoreTokens()) {
			list.add(t.nextToken());
		}
		return (String[]) list.toArray(new String[list.size()]);
	}

	/**
	 * 方法描述：chop.
	 *
	 * @author mqfdy
	 * @param s s
	 * @param i i
	 * @return String实例
	 * @Date 2018年8月31日 下午2:41:23
	 */
	public static String chop(String s, int i) {
		return chop(s, i, EOL);
	}

	/**
	 * 方法描述：chop.
	 *
	 * @author mqfdy
	 * @param s s
	 * @param i i
	 * @param eol eol
	 * @return String实例
	 * @Date 2018年8月31日 下午2:41:31
	 */
	public static String chop(String s, int i, String eol) {
		if (i == 0 || s == null || eol == null) {
			return s;
		}

		int length = s.length();
		
		if (eol.length() == 2 && s.endsWith(eol)) {
			length -= 2;
			i -= 1;
		}

		if (i > 0) {
			length -= i;
		}

		if (length < 0) {
			length = 0;
		}

		return s.substring(0, length);
	}

	/**
	 * 方法描述：stringSubstitution.
	 *
	 * @author mqfdy
	 * @param argStr argStr
	 * @param vars vars
	 * @return StringBuffer实例
	 * @Date 2018年8月31日 下午2:41:42
	 */
	public static StringBuffer stringSubstitution(String argStr, Hashtable<String, String> vars) {
		return stringSubstitution(argStr, (Map<String, String>) vars);
	}

	/**
	 * 方法描述：stringSubstitution.
	 *
	 * @author mqfdy
	 * @param argStr argStr
	 * @param vars  vars
	 * @return StringBuffer实例
	 * @Date 2018年8月31日 下午2:41:48
	 */
	public static StringBuffer stringSubstitution(String argStr, Map<String, String> vars) {
		StringBuffer argBuf = new StringBuffer();

		for (int cIdx = 0; cIdx < argStr.length();) {
			char ch = argStr.charAt(cIdx);

			switch (ch) {
			case '$':
				StringBuffer nameBuf = new StringBuffer();
				for (++cIdx; cIdx < argStr.length(); ++cIdx) {
					ch = argStr.charAt(cIdx);
					if (ch == '_' || Character.isLetterOrDigit(ch))
						nameBuf.append(ch);
					else
						break;
				}

				if (nameBuf.length() > 0) {
					String value = (String) vars.get(nameBuf.toString());

					if (value != null) {
						argBuf.append(value);
					}
				}
				break;

			default:
				argBuf.append(ch);
				++cIdx;
				break;
			}
		}

		return argBuf;
	}

	/**
	 * 方法描述：fileContentsToString.
	 *
	 * @author mqfdy
	 * @param file file
	 * @return String实例
	 * @Date 2018年8月31日 下午2:41:58
	 */
	public static String fileContentsToString(String file) {
		String contents = "";

		File f = null;
		try {
			f = new File(file);

			if (f.exists()) {
				FileReader fr = null;
				try {
					fr = new FileReader(f);
					char[] template = new char[(int) f.length()];
					fr.read(template);
					contents = new String(template);
				} catch (Exception e) {
					Logger.log(e);
				} finally {
					if (fr != null) {
						fr.close();
					}
				}
			}
		} catch (Exception e) {
			Logger.log(e);
		}
		return contents;
	}

	/**
	 * 方法描述：collapseNewlines.
	 *
	 * @author mqfdy
	 * @param argStr argStr
	 * @return String实例
	 * @Date 2018年8月31日 下午2:42:05
	 */
	public static String collapseNewlines(String argStr) {
		char last = argStr.charAt(0);
		StringBuffer argBuf = new StringBuffer();

		for (int cIdx = 0; cIdx < argStr.length(); cIdx++) {
			char ch = argStr.charAt(cIdx);
			if (ch != '\n' || last != '\n') {
				argBuf.append(ch);
				last = ch;
			}
		}

		return argBuf.toString();
	}

	/**
	 * 方法描述：collapseSpaces.
	 *
	 * @author mqfdy
	 * @param argStr argStr
	 * @return String实例
	 * @Date 2018年8月31日 下午2:42:10
	 */
	public static String collapseSpaces(String argStr) {
		char last = argStr.charAt(0);
		StringBuffer argBuf = new StringBuffer();

		for (int cIdx = 0; cIdx < argStr.length(); cIdx++) {
			char ch = argStr.charAt(cIdx);
			if (ch != ' ' || last != ' ') {
				argBuf.append(ch);
				last = ch;
			}
		}

		return argBuf.toString();
	}

	/**
	 * 方法描述：sub.
	 *
	 * @author mqfdy
	 * @param line  line
	 * @param oldString newString
	 * @param newString newString
	 * @return String实例
	 * @Date 2018年8月31日 下午2:42:17
	 */
	public static final String sub(String line, String oldString,
			String newString) {
		int i = 0;
		if ((i = line.indexOf(oldString, i)) >= 0) {
			char[] line2 = line.toCharArray();
			char[] newString2 = newString.toCharArray();
			int oLength = oldString.length();
			StringBuffer buf = new StringBuffer(line2.length);
			buf.append(line2, 0, i).append(newString2);
			i += oLength;
			int j = i;
			while ((i = line.indexOf(oldString, i)) > 0) {
				buf.append(line2, j, i - j).append(newString2);
				i += oLength;
				j = i;
			}
			buf.append(line2, j, line2.length - j);
			return buf.toString();
		}
		return line;
	}

	/**
	 * 方法描述：stackTrace.
	 *
	 * @author mqfdy
	 * @param e e
	 * @return String实例
	 * @Date 2018年8月31日 下午2:42:29
	 */
	public static final String stackTrace(Throwable e) {
		String foo = null;
		try {
			ByteArrayOutputStream ostr = new ByteArrayOutputStream();
			e.printStackTrace(new PrintWriter(ostr, true));
			foo = ostr.toString();
		} catch (Exception f) {
			Logger.log(f);
		}
		return foo;
	}

	/**
	 * 方法描述：normalizePath.
	 *
	 * @author mqfdy
	 * @param path path
	 * @return String实例
	 * @Date 2018年8月31日 下午2:42:33
	 */
	public static final String normalizePath(String path) {
		String normalized = path;
		if (normalized.indexOf('\\') >= 0) {
			normalized = normalized.replace('\\', '/');
		}

		if (!normalized.startsWith("/")) {
			normalized = "/" + normalized;
		}

		while (true) {
			int index = normalized.indexOf("//");
			if (index < 0)
				break;
			normalized = normalized.substring(0, index)
					+ normalized.substring(index + 1);
		}

		while (true) {
			int index = normalized.indexOf("%20");
			if (index < 0)
				break;
			normalized = normalized.substring(0, index) + " "
					+ normalized.substring(index + 3);
		}

		while (true) {
			int index = normalized.indexOf("/./");
			if (index < 0)
				break;
			normalized = normalized.substring(0, index)
					+ normalized.substring(index + 2);
		}

		while (true) {
			int index = normalized.indexOf("/../");
			if (index < 0)
				break;
			if (index == 0)
				return (null); // Trying to go outside our context
			int index2 = normalized.lastIndexOf('/', index - 1);
			normalized = normalized.substring(0, index2)
					+ normalized.substring(index + 3);
		}

		return (normalized);
	}

	/**
	 * 方法描述：select.
	 *
	 * @author mqfdy
	 * @param state state
	 * @param trueString trueString
	 * @param falseString falseString
	 * @return String实例
	 * @Date 2018年8月31日 下午2:42:40
	 */
	public String select(boolean state, String trueString, String falseString) {
		if (state) {
			return trueString;
		} else {
			return falseString;
		}
	}

	/**
	 * 方法描述：allEmpty.
	 *
	 * @author mqfdy
	 * @param list list
	 * @return boolean实例
	 * @Date 2018年8月31日 下午2:42:51
	 */
	public boolean allEmpty(List<String> list) {
		int size = list.size();

		for (int i = 0; i < size; i++) {
			if (list.get(i) != null && list.get(i).toString().length() > 0) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 方法描述：trimStrings.
	 *
	 * @author mqfdy
	 * @param list list
	 * @return List<String>实例
	 * @Date 2018年8月31日 下午2:42:58
	 */
	public static List<String> trimStrings(List<String> list) {
		if (list == null)
			return null;

		int sz = list.size();
		for (int i = 0; i < sz; i++)
			list.set(i, nullTrim((String) list.get(i)));
		return list;
	}

	/**
	 * 方法描述：nullTrim.
	 *
	 * @author mqfdy
	 * @param s s
	 * @return String实例
	 * @Date 2018年8月31日 下午2:43:05
	 */
	public static String nullTrim(String s) {
		if (s == null) {
			return null;
		} else {
			return s.trim();
		}
	}
	
	/**
	 * Concat string with comma.
	 *
	 * @author Administrator
	 * @param str1 the str 1
	 * @param str2 the str 2
	 * @return the string
	 * @Date 2019-12-2 15:22:43
	 */
	public static String concatStringWithComma(String str1,String str2){
		if(isEmpty(str2)){
			str2 = "";
		}
		if(isEmpty(str1)){
			return str2;
		}
		return str1+","+str2;
	}
	
	/**
	 * Concat string with comma.
	 *
	 * @author Administrator
	 * @param str1 the str 1
	 * @param str2 the str 2
	 * @return the string
	 * @Date 2019-12-2 15:22:43
	 */
	public static String concatStringWithCommaValues(String str1,String str2){
		if(isEmpty(str2)){
			str2 = "";
		}
		if(isEmpty(str1)){
			return "#{"+str2+"}";
		}
		return str1+",#{"+str2+"}";
	}
	
	/**
	 * Concat key value mybatis.
	 *
	 * @author Administrator
	 * @param str1 the str 1
	 * @param str2 the str 2
	 * @return the string
	 * @Date 2019-12-2 17:10:05
	 */
	public static String concatKeyValueMybatis(String str1,String str2){
		if(isEmpty(str2)){
			str2 = "";
		}
		if(isEmpty(str1)){
			return str1;
		}
		return str1+"=#{"+str2+"}";
	}
	
	/**
	 * Concat string mybatis result.
	 *
	 * @author Administrator
	 * @param javaName the java name
	 * @param columnName the column name
	 * @param javaType the java type
	 * @return the string
	 * @Date 2019-12-2 17:19:30
	 */
	public static String concatStringMybatisResult(String javaName,String columnName,String javaType){
		return "@Result(property = \""+javaName+"\", column = \""+columnName+"\", javaType="+javaType+".class)";
	}
	
	/**
	 * Removes the end char.
	 *
	 * @author Administrator
	 * @param str the str
	 * @return the string
	 * @Date 2019-11-28 17:24:48
	 */
	public static String removeEndChar(String str){
		if(str != null && str.length()>0){
			str = str.substring(0, str.length()-1);
		}
		return str;
	}
	
	/**
	 * To lower case.
	 *
	 * @author Administrator
	 * @param str the str
	 * @return the string
	 * @Date 2019-12-3 15:38:06
	 */
	public static String toLowerCase(String str){
		return str.toLowerCase(Locale.getDefault());
	}
	
	/**
	 * To upper case.
	 *
	 * @author Administrator
	 * @param str the str
	 * @return the string
	 * @Date 2019-12-3 15:38:57
	 */
	public static String toUpperCase(String str){
		return str.toUpperCase(Locale.getDefault());
	}
	
	/**
	 * Contains.
	 *
	 * @author Administrator
	 * @param str1 the str 1
	 * @param str2 the str 2
	 * @return true, if successful
	 * @Date 2020-1-15 17:32:42
	 */
	public static boolean contains(String str1,String str2){
		return str1.contains(str2);
	}
}
