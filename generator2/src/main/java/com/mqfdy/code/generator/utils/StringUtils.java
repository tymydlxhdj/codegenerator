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


public class StringUtils {
	
	private static final String EOL = System.getProperty("line.separator");

	/**
	 * 判断字符串是否为 null 或为空字符串。
	 * 
	 * @param str
	 * @return
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
	 * @param fieldName
	 * @return
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
	
	public static String capitalize(String str) {
		if (str == null || str.length() == 0) {
			return str;
		}
		StringBuilder sb = new StringBuilder(str.length());
		sb.append(Character.toUpperCase(str.charAt(0)));
		sb.append(str.substring(1));
		return sb.toString();
	}
	
	public String concat(List<String> list) {
		StringBuffer sb = new StringBuffer();
		int size = list.size();

		for (int i = 0; i < size; i++) {
			sb.append(list.get(i).toString());
		}
		return sb.toString();
	}

	static public String getPackageAsPath(String pckge) {
		return pckge.replace('.', File.separator.charAt(0)) + File.separator;
	}

	static public String lowercaseFirstLetter(String str) {
		String firstLetter = str.substring(0, 1);
		String subLetter = str.substring(1);
		return firstLetter.toLowerCase(Locale.getDefault()).concat(subLetter);
	}

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

	static public String removeAndHump(String data) {
		return removeAndHump(data, "_");
	}

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

	static public String firstLetterCaps(String data) {
		String firstLetter = data.substring(0, 1).toUpperCase(Locale.getDefault());
		String restLetters = data.substring(1).toLowerCase(Locale.getDefault());
		return firstLetter + restLetters;
	}

	static public String capitalizeFirstLetter(String data) {
		String firstLetter = data.substring(0, 1).toUpperCase(Locale.getDefault());
		String restLetters = data.substring(1);
		return firstLetter + restLetters;
	}

	public static String[] split(String line, String delim) {
		List<String> list = new ArrayList<String>();
		StringTokenizer t = new StringTokenizer(line, delim);
		while (t.hasMoreTokens()) {
			list.add(t.nextToken());
		}
		return (String[]) list.toArray(new String[list.size()]);
	}

	public static String chop(String s, int i) {
		return chop(s, i, EOL);
	}

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

	public static StringBuffer stringSubstitution(String argStr, Hashtable<String, String> vars) {
		return stringSubstitution(argStr, (Map<String, String>) vars);
	}

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

	public String select(boolean state, String trueString, String falseString) {
		if (state) {
			return trueString;
		} else {
			return falseString;
		}
	}

	public boolean allEmpty(List<String> list) {
		int size = list.size();

		for (int i = 0; i < size; i++) {
			if (list.get(i) != null && list.get(i).toString().length() > 0) {
				return false;
			}
		}
		return true;
	}

	public static List<String> trimStrings(List<String> list) {
		if (list == null)
			return null;

		int sz = list.size();
		for (int i = 0; i < sz; i++)
			list.set(i, nullTrim((String) list.get(i)));
		return list;
	}

	public static String nullTrim(String s) {
		if (s == null) {
			return null;
		} else {
			return s.trim();
		}
	}
}
