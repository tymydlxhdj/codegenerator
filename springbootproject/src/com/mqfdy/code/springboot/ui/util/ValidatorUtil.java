package com.mqfdy.code.springboot.ui.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class ValidatorUtil {

	
	public static String DISPLAYNAMEREGEX = "[a-z A-Z_0-9(){}\u4E00-\u9FA5]*$";
	public static String NAMEREGEX = "^[a-z_A-Z][a-zA-Z_0-9]*$";
	public static String PACKAGE_NAME="[a-z]+[[.][a-z]+]*$";
	public static String FIRSTNO_NAMEREGEX = "^[a-zA-Z][a-zA-Z_0-9]*$";
	public static String FIRSTLETTERUPPERCASE = "^[A-Z_][a-zA-Z_0-9]*$";
	public static String FIRSTLETTERLOWERCASE = "^[a-z_][a-zA-Z_0-9]*$";
	public static String PACKAGENAME = "^[a-z_][a-z._0-9]*$";
	public static String PACKAGENAME1 = "^[a-z_][a-z_0-9]*$";
	public static String DATETIME = "^((\\d{2}(([02468][048])|([13579][26]))[\\-]((((0?[13578])|(1[02]))[\\-]((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-]((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-]((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-]((((0?[13578])|(1[02]))[\\-]((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-]((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-]((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|(1[0-9])|(2[0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$";
	public static String TIME = "^(\\d|[0-1]\\d|2[0-3]):[0-5][0-9]:[0-5][0-9]$";
	public static String CHINESE= "^[\u4e00-\u9fa5]*$"; 
	public static String DIRECTORY = "[0-9a-zA-Z][0-9a-zA-Z/{1}0-9a-zA-Z]*$";

	public static Pattern pDateTime = Pattern.compile(DATETIME);
	public static Pattern pTime = Pattern.compile(TIME);
	
	private static final char DOT = '.';
	public final static int MAX_OBVIOUS = 128;
	public final static int[] OBVIOUS_IDENT_CHAR_NATURES = new int[MAX_OBVIOUS];

	public final static int C_JLS_SPACE = 0x100;

	public static boolean isValidModelName(String str) {
		if (str.length() == 0
				|| !Character.isJavaIdentifierStart(str.charAt(0)))
			return false;
		String name = str.substring(1);
		for (int i = 0; i < name.length(); i++)
			if ('.' != name.charAt(i)
					&& !Character.isJavaIdentifierPart(name.charAt(i)))
				return false;
		return true;
	}
	
	public static boolean valiPackageName(String pkgName) {
		if (!pkgName.matches(PACKAGENAME)) {
			return false;
		} 		
		else if (!validatePackageName(pkgName)) {
			return false;
		}
		return true;
	}
	
	/**
	 * 校验命名空间名
	 * @param pkgName
	 * @return
	 */
	public static boolean valiNameSpaceName(String nameSpaceName) {
		if(nameSpaceName.startsWith("com")&&nameSpaceName.length()>3){
			  if(nameSpaceName.substring(3).matches("^[0-9][a-zA-Z_0-9]*$"))			  
			     return false; 
			  else
				 return true;
		}
		return true;
	}
	
	
	/**
	 * 校验包名
	 * 
	 * @param name
	 * @return
	 */
	public static boolean validatePackageName(String name) {

		if (name == null) {
			return false;
		}
		int length;
		if ((length = name.length()) == 0) {
			return false;
		}
		if (name.charAt(0) == DOT || name.charAt(length - 1) == DOT) {
			return false;
		}
		if (isWhitespace(name.charAt(0))
				|| isWhitespace(name.charAt(name.length() - 1))) {
			return false;
		}
		int dot = 0;
		while (dot != -1 && dot < length - 1) {
			if ((dot = name.indexOf(DOT, dot + 1)) != -1 && dot < length - 1
					&& name.charAt(dot + 1) == DOT) {
				return false;
			}
		}
		StringTokenizer st = new StringTokenizer(name, "."); //$NON-NLS-1$
		while (st.hasMoreTokens()) {
			String typeName = st.nextToken();
			typeName = typeName.trim(); // grammar allows spaces
			if (!typeName.matches(PACKAGENAME1)) {
				return false;
			}
			if (new KeyWordsChecker().doCheckJava(typeName)) {
				return false;
			}
		}
		return true;
	}
	/**
	 * 是否空格
	 * 
	 * @param c
	 * @return
	 */
	public static boolean isWhitespace(char c) {
		return c < MAX_OBVIOUS
				&& ((OBVIOUS_IDENT_CHAR_NATURES[c] & C_JLS_SPACE) != 0);
	}
	
	

	public static boolean valiName(String name) {
		return name.matches(NAMEREGEX);
	}
	
	public static boolean valiPacakge(String name) {
		return name.matches(PACKAGE_NAME);
	}

	public static boolean validateChinese(String value){
        Pattern p=Pattern.compile(CHINESE);  
        Matcher result=p.matcher(value);                  
        return result.find(); //是否是中文字符 串
	}
	
	/**
	 * 开头不能是下划线的名称格式
	 * @param name
	 * @return
	 */
	public static boolean valiFirstNo_Name(String name) {
		return name.matches(FIRSTNO_NAMEREGEX);
	}

	public static boolean isFirstUppercase(String name) {
		return name.matches(FIRSTLETTERUPPERCASE);
	}

	public static boolean isFirstLowercase(String name) {
		return name.matches(FIRSTLETTERLOWERCASE);
	}

	public static boolean valiDisplayName(String displayName) {
		return displayName.matches(DISPLAYNAMEREGEX);
	}

	public static boolean valiNameLength(String name) {
		return valiStringLength(name, 30);
	}

	public static boolean valiDisplayNameLength(String displayName) {
		return valiStringLength(displayName, 30);
	}

	public static boolean valiRemarkLength(String remark) {
		return valiStringLength(remark, 128);
	}
	
	public static boolean validateReg(String reg) {
		if (reg == null) {
			return false;
		}
		if (!reg.startsWith("/") || !reg.endsWith("/")) {
			return false;
		}
		String newStr = "";
		newStr = reg.substring(1, reg.length()-1);
//		if (!reg.contains("\\/")) {
//			return false;
//		}
		//   /"  \/  \\  \   
//		String temp = reg.replaceFirst(replace, "");
//		if (temp.contains("\\/")) {
//			return false;
//		}
		String temp = newStr.replace("\\/", "");
		if (temp.contains("\\/")) {
			return false;
		}
		temp = temp.replace("\\\\", "");
		
		if((temp.charAt(temp.length() - 1)+"").equals("\\")){
			return false;
		}
		
		int i = 0;
		for(i = 0;i < reg.length();i++){
			i = reg.indexOf("\"",i);
			if(i>0){
				int num = 0;
				for(int j = i-1;j > 0;j--){
					if(!(reg.charAt(j)+"").equals("\\")){
						break;
					}
					num ++;
				}
				if(num%2 == 0)
					return false;
			}
			if(i < 0)
				return true;
		}
		
		
		return true;
	}

	/**
	 * 用于校验日期
	 * 
	 * @param value
	 * @return
	 */
	public static boolean valiDate(String value) {
		if (value == null) {
			return false;
		}
		if(value.indexOf("-") < 0){
			return false;
		}
		if(value.indexOf(":") > -1){
			return false;
		}
		return pDateTime.matcher(value).matches();
	}

	/**
	 * 用于校验日期时间
	 * 
	 * @param value
	 * @return
	 */
	public static boolean valiDateTime(String value) {
		if (value == null) {
			return false;
		}
		if(value.indexOf("-") < 0){
			return false;
		}
		if(value.indexOf(":") < 0){
			return false;
		}
		
		return pDateTime.matcher(value).matches();
	}

	/**
	 * 用于校验时间
	 * 
	 * @param value
	 * @return
	 */
	public static boolean valiTime(String value) {
		if (value == null) {
			return false;
		}
		if(value.indexOf("-") > -1){
			return false;
		}
		if(value.indexOf(":") < 0){
			return false;
		}
		
		return pTime.matcher(value).matches();
	}

//	/**
//	 * 用于校验日期时间
//	 * 
//	 * @param value
//	 * @return
//	 */
//	public static boolean valiTimeStamp(String value) {
//		if (value == null) {
//			return false;
//		}
//		if(value.indexOf("-") < 0){
//			return false;
//		}
//		if(value.indexOf(":") < 0){
//			return false;
//		}
//		
//		return pDateTime.matcher(value).matches();
//	}

	/**
	 * 用于校验数字
	 * 
	 * @param value
	 * @return
	 */
	public static boolean valiLong(String value) {
		try {
			long i = Long.parseLong(value);
//			if (i < 0) {
//				return false;
//			}
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	/**
	 * 用于校验数字
	 * 
	 * @param value
	 * @return
	 */
	public static boolean valiInteger(String value) {
		try {
			int i = Integer.parseInt(value);
//			if (i < 0) {
//				return false;
//			}
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	/**
	 * 用于校验数字
	 * 
	 * @param value
	 * @return
	 */
	public static boolean valiShort(String value) {
		try {
			short i = Short.parseShort(value);
//			if (i < 0) {
//				return false;
//			}
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	/**
	 * 用于校验数字
	 * 
	 * @param value
	 * @return
	 */
	public static boolean valiFloat(String value) {
		try {
			Float.parseFloat(value);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	/**
	 * 用于校验数字
	 * 
	 * @param value
	 * @return
	 */
	public static boolean valiDouble(String value) {
		try {
			Double.parseDouble(value);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	public static boolean valiStringLength(String name, int length) {
		if (name.length() > length)
			return false;
		return true;
	}
	public static String delErrorString(String displayName){
		String newString = "";
		if(valiDisplayName(displayName) && valiDisplayNameLength(displayName))
			return displayName;
		for(int index = 0;index < displayName.length();index++){
			char a = displayName.charAt(index);
			if(newString.length() > 29)
				return newString;
			if(valiDisplayName(a+""))
				newString += a;
		}
		return newString;
		
	}
	
	/**
	 * 校验IP是否输入正确
	 * @param ip
	 * @return
	 */
	public static boolean validateIP(String ip){
		if(ip.trim().split("\\.").length!=4){
			return false;
		}
		for(String str:ip.trim().split("\\.")){
			try{
				int num=Integer.parseInt(str);
				if(num>255||num<0){
					return false;
				}
			}catch(Exception e){
				return false;
			}
		}
		return true;		
	}
	
	
	/**
	 * 校验端口是否输入正确
	 * @param port
	 * @return
	 */
	public static boolean validatePort(String port){
		try{
			int num=Integer.parseInt(port);
			if(num>65535||num<0){
				return false;
			}
		}catch(Exception e){
			return false;
		}
		return true;		
	}
	public static boolean validateDirctory(String dir){
		if (!dir.matches(DIRECTORY)) {
			return false;
		} 		
		return true;		
	}
	
	
}
