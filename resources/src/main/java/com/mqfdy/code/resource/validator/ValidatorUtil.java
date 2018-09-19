package com.mqfdy.code.resource.validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mqfdy.code.model.AbstractModelElement;
import com.mqfdy.code.model.Association;
import com.mqfdy.code.model.BusinessClass;
import com.mqfdy.code.model.BusinessObjectModel;
import com.mqfdy.code.model.PKProperty;
import com.mqfdy.code.model.PersistenceProperty;
import com.mqfdy.code.model.Property;
import com.mqfdy.code.model.utils.AssociationType;
import com.mqfdy.code.model.utils.DataType;
import com.mqfdy.code.model.utils.EditorType;
import com.mqfdy.code.model.utils.ValidatorType;

// TODO: Auto-generated Javadoc
/**
 * 校验工具类.
 *
 * @author mqfdy
 */
public class ValidatorUtil {

	
	/** The Constant DISPLAYNAMEREGEX. */
	public final static String DISPLAYNAMEREGEX = "[a-z A-Z_0-9(){}\u4E00-\u9FA5]*$";
	
	/** The Constant NAMEREGEX. */
	public final static String NAMEREGEX = "^[a-z_A-Z][a-zA-Z_0-9]*$";
	
	/** The Constant PACKAGE_NAME. */
	public final static String PACKAGE_NAME="[a-z]+[[.][a-z]+]+$";
	
	/** The Constant FIRSTNO_NAMEREGEX. */
	public final static String FIRSTNO_NAMEREGEX = "^[a-zA-Z][a-zA-Z_0-9]*$";
	
	/** The Constant FIRSTLETTERUPPERCASE. */
	public final static String FIRSTLETTERUPPERCASE = "^[A-Z_][a-zA-Z_0-9]*$";
	
	/** The Constant FIRSTLETTERLOWERCASE. */
	public final static String FIRSTLETTERLOWERCASE = "^[a-z_][a-zA-Z_0-9]*$";
	
	/** The Constant PACKAGENAME. */
	public final static String PACKAGENAME = "^[a-z_][a-z._0-9]*$";
	
	/** The Constant PACKAGENAME1. */
	public final static String PACKAGENAME1 = "^[a-z_][a-z_0-9]*$";
	
	/** The Constant DATETIME. */
	public final static String DATETIME = "^((\\d{2}(([02468][048])|([13579][26]))[\\-]((((0?[13578])|(1[02]))[\\-]((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-]((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-]((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-]((((0?[13578])|(1[02]))[\\-]((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-]((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-]((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|(1[0-9])|(2[0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$";
	
	/** The Constant TIME. */
	public final static String TIME = "^(\\d|[0-1]\\d|2[0-3]):[0-5][0-9]:[0-5][0-9]$";
	
	/** The Constant CHINESE. */
	public final static String CHINESE= "^[\u4e00-\u9fa5]*$"; 
	
	/** The Constant DIRECTORY. */
	public final static String DIRECTORY = "[0-9a-zA-Z][0-9a-zA-Z/{1}0-9a-zA-Z]*$";

	/** The Constant pDateTime. */
	public final static Pattern pDateTime = Pattern.compile(DATETIME);
	
	/** The Constant pTime. */
	public final static Pattern pTime = Pattern.compile(TIME);
	
	/** The Constant DOT. */
	private static final char DOT = '.';
	
	/** The Constant MAX_OBVIOUS. */
	public final static int MAX_OBVIOUS = 128;
	
	/** The Constant OBVIOUS_IDENT_CHAR_NATURES. */
	public final static int[] OBVIOUS_IDENT_CHAR_NATURES = new int[MAX_OBVIOUS];

	/** The Constant C_JLS_SPACE. */
	public final static int C_JLS_SPACE = 0x100;

	/**
	 * Checks if is valid model name.
	 *
	 * @author mqfdy
	 * @param str
	 *            the str
	 * @return true, if is valid model name
	 * @Date 2018-09-03 09:00
	 */
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
	
	/**
	 * Vali table name.
	 *
	 * @author mqfdy
	 * @param tableName
	 *            the table name
	 * @param curModel
	 *            the cur model
	 * @param businessObjectModel
	 *            the business object model
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	/*
	 * 数据库表名重复
	 */
	public static boolean valiTableName(String tableName,AbstractModelElement curModel,
			BusinessObjectModel businessObjectModel){
		int i = 0;
		for (BusinessClass bu : businessObjectModel.getBusinessClasses()) {
			if (((curModel != null && !bu
					.getId().equals(curModel.getId())) || curModel == null)
					&& bu.getTableName().equalsIgnoreCase(tableName))
				i++;
		}
		for (Association as : businessObjectModel.getAssociations()) {
			if (((curModel != null && !as.getId().equals(curModel.getId())) || curModel == null)
					&& as.getPersistencePloyParams().get(Association.RELATIONTABLENAME) != null
					&& as.getPersistencePloyParams().get(Association.RELATIONTABLENAME).equalsIgnoreCase(tableName))
				i++;
		}
		if (i > 0) {
			return false;
		}
		return true;
	}
	
	/**
	 * Check role name property mult.
	 *
	 * @author mqfdy
	 * @param curBusinessClass
	 *            the cur business class
	 * @param newColumnName
	 *            the new column name
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	public static boolean checkRoleNamePropertyMult(BusinessClass curBusinessClass,
			String newColumnName) {
		if (curBusinessClass != null) {
			List<Property> properties = curBusinessClass.getProperties();
			if (properties != null) {
				for (Property property : properties) {
					if (property instanceof PersistenceProperty
							&& newColumnName.equalsIgnoreCase(property
									.getName())) {
						return false;
					}
				}
			}
		} else {
			return true;
		}
		return true;
	}

	/**
	 * 校验导航属性名称是否已经存在（对应实体属性名称以及本实体的所有关联关系的导航属性名称）.
	 *
	 * @author mqfdy
	 * @param businessObjectModel
	 *            the business object model
	 * @param ele
	 *            the ele
	 * @param curBusinessClass
	 *            the cur business class
	 * @param newColumnName
	 *            the new column name
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	public static boolean checkRoleNameMult(BusinessObjectModel businessObjectModel,
			Association ele, BusinessClass curBusinessClass,
			String newColumnName) {
		if (curBusinessClass != null) {
			List<Property> properties = curBusinessClass.getProperties();
			if (properties != null) {
				for (Property property : properties) {
					if (property instanceof PersistenceProperty
							&& newColumnName.equalsIgnoreCase(property
									.getName())) {
						return false;
					}
				}
			}
			if (businessObjectModel != null) {
				List<String> roleNameList = new ArrayList<String>();
				for (Association association : businessObjectModel
						.getAssociations()) {
					if (association != ele
							&& association.getClassA() == curBusinessClass) {
						roleNameList.add(association
								.getNavigateToClassBRoleName());
					} else if (association != ele
							&& association.getClassB() == curBusinessClass) {
						roleNameList.add(association
								.getNavigateToClassARoleName());
					}
				}
				return !roleNameList.contains(newColumnName);
			}
		} else {
			return true;
		}
		return true;
	}

	/**
	 * 判断外键名称与对应实体所有属性的数据库字段名是否重复.
	 *
	 * @author mqfdy
	 * @param curBusinessClass
	 *            the cur business class
	 * @param newColumnName
	 *            the new column name
	 * @param associationType
	 *            关联关系类型
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	public static boolean checkFKMult(BusinessClass curBusinessClass,
			String newColumnName,String associationType) {
		if (curBusinessClass != null) {
			List<Property> properties = curBusinessClass.getProperties();
			if (properties != null) {
				for (Property property : properties) {
					if (property instanceof PersistenceProperty
							&& newColumnName
									.equalsIgnoreCase(((PersistenceProperty) property)
											.getdBColumnName())) {
						if((property instanceof PKProperty)
								&& associationType.equals(AssociationType.one2one.getValue()))
							return true;
						return false;
					}
				}
			}
		} else {
			return true;
		}
		return true;
	}
	
	/**
	 * Checks if is FK exist in bu.
	 *
	 * @author mqfdy
	 * @param curBusinessClass
	 *            the cur business class
	 * @param newColumnName
	 *            the new column name
	 * @return true, if is FK exist in bu
	 * @Date 2018-09-03 09:00
	 */
	public static boolean isFKExistInBu(BusinessClass curBusinessClass,
			String newColumnName) {
		if (curBusinessClass != null) {
			List<Property> properties = curBusinessClass.getProperties();
			if (properties != null) {
				for (Property property : properties) {
					if (property instanceof PersistenceProperty
							&& newColumnName
									.equalsIgnoreCase(((PersistenceProperty) property)
											.getdBColumnName())) {
						return true;
					}
				}
			}
		} else {
			return false;
		}
		return false;
	}
	
	/**
	 * 判断外键名称与本实体的所有关联关系的外键名称是否重复.
	 *
	 * @author mqfdy
	 * @param ele
	 *            the ele
	 * @param curBusinessClass
	 *            the cur business class
	 * @param newColumnName
	 *            the new column name
	 * @param businessObjectModel
	 *            the business object model
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	public static boolean checkFKMultInAll(Association ele, BusinessClass curBusinessClass,
			String newColumnName, BusinessObjectModel businessObjectModel) {
		if (curBusinessClass != null) {
			List<Association> asList = businessObjectModel.getAssociations();
			int count = 0;
			for (Association as : asList) {
				if(!as.getId().equals(ele.getId())){
					if (as.getClassA() == curBusinessClass) {
						if (newColumnName.equalsIgnoreCase(as
								.getPersistencePloyParams().get(
										Association.FOREIGNKEYCOLUMNINA))) {
							count++;
						}

					}
					if (as.getClassB() == curBusinessClass) {
						if (newColumnName.equalsIgnoreCase(as
								.getPersistencePloyParams().get(
										Association.FOREIGNKEYCOLUMNINB))) {
							count++;
						}
					}
				}
			}
			if (count > 0)
				return false;
		} else {
			return true;
		}
		return true;
	}

	/**
	 * Vali package name.
	 *
	 * @author mqfdy
	 * @param pkgName
	 *            the pkg name
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
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
	 * 校验命名空间名.
	 *
	 * @author mqfdy
	 * @param nameSpaceName
	 *            the name space name
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
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
	 * 校验包名.
	 *
	 * @author mqfdy
	 * @param name
	 *            the name
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
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
	 * 是否空格.
	 *
	 * @author mqfdy
	 * @param c
	 *            the c
	 * @return true, if is whitespace
	 * @Date 2018-09-03 09:00
	 */
	public static boolean isWhitespace(char c) {
		return c < MAX_OBVIOUS
				&& ((OBVIOUS_IDENT_CHAR_NATURES[c] & C_JLS_SPACE) != 0);
	}
	
	

	/**
	 * Vali name.
	 *
	 * @author mqfdy
	 * @param name
	 *            the name
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	public static boolean valiName(String name) {
		return name.matches(NAMEREGEX);
	}
	
	/**
	 * Vali pacakge.
	 *
	 * @author mqfdy
	 * @param name
	 *            the name
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	public static boolean valiPacakge(String name) {
		return name.matches(PACKAGE_NAME);
	}

	/**
	 * Validate chinese.
	 *
	 * @author mqfdy
	 * @param value
	 *            the value
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	public static boolean validateChinese(String value){
        Pattern p=Pattern.compile(CHINESE);  
        Matcher result=p.matcher(value);                  
        return result.find(); //是否是中文字符 串
	}
	
	/**
	 * 开头不能是下划线的名称格式.
	 *
	 * @author mqfdy
	 * @param name
	 *            the name
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	public static boolean valiFirstNo_Name(String name) {
		return name.matches(FIRSTNO_NAMEREGEX);
	}

	/**
	 * Checks if is first uppercase.
	 *
	 * @author mqfdy
	 * @param name
	 *            the name
	 * @return true, if is first uppercase
	 * @Date 2018-09-03 09:00
	 */
	public static boolean isFirstUppercase(String name) {
		return name.matches(FIRSTLETTERUPPERCASE);
	}

	/**
	 * Checks if is first lowercase.
	 *
	 * @author mqfdy
	 * @param name
	 *            the name
	 * @return true, if is first lowercase
	 * @Date 2018-09-03 09:00
	 */
	public static boolean isFirstLowercase(String name) {
		return name.matches(FIRSTLETTERLOWERCASE);
	}

	/**
	 * Vali display name.
	 *
	 * @author mqfdy
	 * @param displayName
	 *            the display name
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	public static boolean valiDisplayName(String displayName) {
		return displayName.matches(DISPLAYNAMEREGEX);
	}

	/**
	 * Vali name length.
	 *
	 * @author mqfdy
	 * @param name
	 *            the name
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	public static boolean valiNameLength(String name) {
		return valiStringLength(name, 30);
	}

	/**
	 * Vali display name length.
	 *
	 * @author mqfdy
	 * @param displayName
	 *            the display name
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	public static boolean valiDisplayNameLength(String displayName) {
		return valiStringLength(displayName, 30);
	}

	/**
	 * Vali remark length.
	 *
	 * @author mqfdy
	 * @param remark
	 *            the remark
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	public static boolean valiRemarkLength(String remark) {
		return valiStringLength(remark, 128);
	}
	
	/**
	 * Validate reg.
	 *
	 * @author mqfdy
	 * @param reg
	 *            the reg
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
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
	 * 用于校验日期.
	 *
	 * @author mqfdy
	 * @param value
	 *            the value
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
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
	 * 用于校验日期时间.
	 *
	 * @author mqfdy
	 * @param value
	 *            the value
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
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
	 * 用于校验时间.
	 *
	 * @author mqfdy
	 * @param value
	 *            the value
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
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
 * 用于校验数字.
 *
 * @author mqfdy
 * @param value
 *            the value
 * @return true, if successful
 * @Date 2018-09-03 09:00
 */
	public static boolean valiLong(String value) {
		try {
			Long.parseLong(value);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	/**
	 * 用于校验数字.
	 *
	 * @author mqfdy
	 * @param value
	 *            the value
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	public static boolean valiInteger(String value) {
		try {
			Integer.parseInt(value);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	/**
	 * 用于校验数字.
	 *
	 * @author mqfdy
	 * @param value
	 *            the value
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	public static boolean valiShort(String value) {
		try {
			Short.parseShort(value);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	/**
	 * 用于校验数字.
	 *
	 * @author mqfdy
	 * @param value
	 *            the value
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
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
	 * 用于校验数字.
	 *
	 * @author mqfdy
	 * @param value
	 *            the value
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	public static boolean valiDouble(String value) {
		try {
			Double.parseDouble(value);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	/**
	 * Vali string length.
	 *
	 * @author mqfdy
	 * @param name
	 *            the name
	 * @param length
	 *            the length
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	public static boolean valiStringLength(String name, int length) {
		if (name.length() > length)
			return false;
		return true;
	}

	/**
	 * 校验规则Map.
	 *
	 * @author mqfdy
	 * @return the editor map
	 * @Date 2018-09-03 09:00
	 */
	public static Map<String, Map<EditorType, Object>> getEditorMap() {
		Map<String, Map<EditorType, Object>> map = new HashMap();

		Map<EditorType, Object> mapString = new HashMap();
		mapString.put(EditorType.TextEditor, "");
		mapString.put(EditorType.MultTextEditor, "");
		mapString.put(EditorType.PasswordTextEditor, "");
		mapString.put(EditorType.NumberEditor, "");
		mapString.put(EditorType.RichTextEditor, "");
		mapString.put(EditorType.DateEditor, "");
		mapString.put(EditorType.DateTimeEditor, "");
		mapString.put(EditorType.TimeEditor, "");
		mapString.put(EditorType.ComboBox, "");
		mapString.put(EditorType.ListEditor, "");
		mapString.put(EditorType.DropDownTreeEditor, "");
		mapString.put(EditorType.DropDownCheckBoxTreeEditor, "");
		mapString.put(EditorType.DropDownGridEditor, "");
		mapString.put(EditorType.RichTextEditor, "");
		mapString.put(EditorType.RadioEditor, "");
		mapString.put(EditorType.CheckEditor, "");
		mapString.put(EditorType.CheckComboBox, "");
		mapString.put(EditorType.CheckListEditor, "");
		mapString.put(EditorType.FileEditor, "");
		mapString.put(EditorType.LableEditor, "");
		mapString.put(EditorType.LinkEditor, "");
		mapString.put(EditorType.CustomEditor, "");
		mapString.put(EditorType.AutoCompleteEditor, "");
		map.put(DataType.String.getValue_hibernet(), mapString);

		Map<EditorType, Object> mapChar = new HashMap();
		mapChar.put(EditorType.TextEditor, "");
		mapChar.put(EditorType.MultTextEditor, "");
		mapChar.put(EditorType.PasswordTextEditor, "");
		mapChar.put(EditorType.ComboBox, "");
		mapChar.put(EditorType.CheckComboBox, "");
		mapChar.put(EditorType.CheckListEditor, "");
		mapChar.put(EditorType.ListEditor, "");
		mapChar.put(EditorType.DropDownTreeEditor, "");
//		mapChar.put(EditorType.DropDownCheckBoxTreeEditor, "");
		mapChar.put(EditorType.DropDownGridEditor, "");
		mapChar.put(EditorType.RichTextEditor, "");
		mapChar.put(EditorType.RadioEditor, "");
		map.put(DataType.Character.getValue_hibernet(), mapChar);

		Map<EditorType, Object> mapLong = new HashMap();
		mapLong.put(EditorType.NumberEditor, "");
		mapLong.put(EditorType.TextEditor, "");
		mapLong.put(EditorType.MultTextEditor, "");
		mapLong.put(EditorType.PasswordTextEditor, "");
		mapLong.put(EditorType.ComboBox, "");
		mapLong.put(EditorType.CheckComboBox, "");
		mapLong.put(EditorType.CheckListEditor, "");
		mapLong.put(EditorType.ListEditor, "");
		mapLong.put(EditorType.DropDownTreeEditor, "");
//		mapLong.put(EditorType.DropDownCheckBoxTreeEditor, "");
		mapLong.put(EditorType.DropDownGridEditor, "");
		mapLong.put(EditorType.RichTextEditor, "");
		mapLong.put(EditorType.CheckEditor, "");
		mapLong.put(EditorType.RadioEditor, "");
		mapLong.put(EditorType.FileEditor, "");
		map.put(DataType.Long.getValue_hibernet(), mapLong);

		Map<EditorType, Object> mapIntegter = new HashMap();
		mapIntegter.put(EditorType.NumberEditor, "");
		mapIntegter.put(EditorType.TextEditor, "");
		mapIntegter.put(EditorType.MultTextEditor, "");
		mapIntegter.put(EditorType.PasswordTextEditor, "");
		mapIntegter.put(EditorType.ComboBox, "");
		mapIntegter.put(EditorType.CheckComboBox, "");
		mapIntegter.put(EditorType.CheckListEditor, "");
		mapIntegter.put(EditorType.ListEditor, "");
		mapIntegter.put(EditorType.DropDownTreeEditor, "");
//		mapIntegter.put(EditorType.DropDownCheckBoxTreeEditor, "");
		mapIntegter.put(EditorType.DropDownGridEditor, "");
		mapIntegter.put(EditorType.RichTextEditor, "");
		mapIntegter.put(EditorType.CheckEditor, "");
		mapIntegter.put(EditorType.RadioEditor, "");
		mapIntegter.put(EditorType.FileEditor, "");
		map.put(DataType.Integer.getValue_hibernet(), mapIntegter);

		Map<EditorType, Object> mapShort = new HashMap();
		mapShort.put(EditorType.NumberEditor, "");
		mapShort.put(EditorType.TextEditor, "");
		mapShort.put(EditorType.MultTextEditor, "");
		mapShort.put(EditorType.PasswordTextEditor, "");
		mapShort.put(EditorType.ComboBox, "");
		mapShort.put(EditorType.CheckComboBox, "");
		mapShort.put(EditorType.CheckListEditor, "");
		mapShort.put(EditorType.ListEditor, "");
		mapShort.put(EditorType.DropDownTreeEditor, "");
//		mapShort.put(EditorType.DropDownCheckBoxTreeEditor, "");
		mapShort.put(EditorType.DropDownGridEditor, "");
		mapShort.put(EditorType.RichTextEditor, "");
		mapShort.put(EditorType.CheckEditor, "");
		mapShort.put(EditorType.RadioEditor, "");
		mapShort.put(EditorType.FileEditor, "");
		map.put(DataType.Short.getValue_hibernet(), mapShort);

		Map<EditorType, Object> mapByte = new HashMap();
		mapByte.put(EditorType.NumberEditor, "");
		mapByte.put(EditorType.TextEditor, "");
		mapByte.put(EditorType.MultTextEditor, "");
		mapByte.put(EditorType.PasswordTextEditor, "");
		mapByte.put(EditorType.ComboBox, "");
		mapByte.put(EditorType.CheckComboBox, "");
		mapByte.put(EditorType.CheckListEditor, "");
		mapByte.put(EditorType.ListEditor, "");
		mapByte.put(EditorType.DropDownTreeEditor, "");
		mapByte.put(EditorType.DropDownGridEditor, "");
		mapByte.put(EditorType.RichTextEditor, "");
		mapByte.put(EditorType.CheckEditor, "");
		mapByte.put(EditorType.RadioEditor, "");
		map.put(DataType.Byte.getValue_hibernet(), mapByte);

		Map<EditorType, Object> mapFloat = new HashMap();
		mapFloat.put(EditorType.NumberEditor, "");
		mapFloat.put(EditorType.TextEditor, "");
		mapFloat.put(EditorType.MultTextEditor, "");
		mapFloat.put(EditorType.PasswordTextEditor, "");
		mapFloat.put(EditorType.ComboBox, "");
		mapFloat.put(EditorType.CheckComboBox, "");
		mapFloat.put(EditorType.CheckListEditor, "");
		mapFloat.put(EditorType.ListEditor, "");
		mapFloat.put(EditorType.DropDownTreeEditor, "");
//		mapFloat.put(EditorType.DropDownCheckBoxTreeEditor, "");
		mapFloat.put(EditorType.DropDownGridEditor, "");
		mapFloat.put(EditorType.RichTextEditor, "");
		mapFloat.put(EditorType.CheckEditor, "");
		mapFloat.put(EditorType.RadioEditor, "");
		mapFloat.put(EditorType.FileEditor, "");
		map.put(DataType.Float.getValue_hibernet(), mapFloat);

		Map<EditorType, Object> mapDouble = new HashMap();
		mapDouble.put(EditorType.NumberEditor, "");
		mapDouble.put(EditorType.TextEditor, "");
		mapDouble.put(EditorType.MultTextEditor, "");
		mapDouble.put(EditorType.PasswordTextEditor, "");
		mapDouble.put(EditorType.ComboBox, "");
		mapDouble.put(EditorType.CheckComboBox, "");
		mapDouble.put(EditorType.CheckListEditor, "");
		mapDouble.put(EditorType.ListEditor, "");
		mapDouble.put(EditorType.DropDownTreeEditor, "");
//		mapDouble.put(EditorType.DropDownCheckBoxTreeEditor, "");
		mapDouble.put(EditorType.DropDownGridEditor, "");
		mapDouble.put(EditorType.RichTextEditor, "");
		mapDouble.put(EditorType.CheckEditor, "");
		mapDouble.put(EditorType.RadioEditor, "");
		mapDouble.put(EditorType.FileEditor, "");
		map.put(DataType.Double.getValue_hibernet(), mapDouble);

		Map<EditorType, Object> mapBigdicimal = new HashMap();
		mapBigdicimal.put(EditorType.NumberEditor, "");
		mapBigdicimal.put(EditorType.TextEditor, "");
		mapBigdicimal.put(EditorType.MultTextEditor, "");
		mapBigdicimal.put(EditorType.PasswordTextEditor, "");
		mapBigdicimal.put(EditorType.ComboBox, "");
		mapBigdicimal.put(EditorType.CheckComboBox, "");
		mapBigdicimal.put(EditorType.CheckListEditor, "");
		mapBigdicimal.put(EditorType.ListEditor, "");
		mapBigdicimal.put(EditorType.DropDownTreeEditor, "");
//		mapBigdicimal.put(EditorType.DropDownCheckBoxTreeEditor, "");
		mapBigdicimal.put(EditorType.DropDownGridEditor, "");
		mapBigdicimal.put(EditorType.RichTextEditor, "");
		mapBigdicimal.put(EditorType.CheckEditor, "");
		mapBigdicimal.put(EditorType.RadioEditor, "");
		mapBigdicimal.put(EditorType.FileEditor, "");
		map.put(DataType.Big_decimal.getValue_hibernet(), mapBigdicimal);

		Map<EditorType, Object> mapBoolean = new HashMap();
		mapBoolean.put(EditorType.ComboBox, "");
		mapBoolean.put(EditorType.CheckComboBox, "");
		mapBoolean.put(EditorType.RadioEditor, "");
		mapBoolean.put(EditorType.TextEditor, "");
		mapBoolean.put(EditorType.NumberEditor, "");
		map.put(DataType.Boolean.getValue_hibernet(), mapBoolean);

		// 时间日期
		Map<EditorType, Object> mapDate = new HashMap();
		mapDate.put(EditorType.DateEditor, "");
		map.put(DataType.Date.getValue_hibernet(), mapDate);

		Map<EditorType, Object> mapDateTime = new HashMap();
		mapDateTime.put(EditorType.DateTimeEditor, "");
		map.put(DataType.Timestamp.getValue_hibernet(), mapDateTime);

		Map<EditorType, Object> mapTime = new HashMap();
		mapTime.put(EditorType.TimeEditor, "");
		map.put(DataType.Time.getValue_hibernet(), mapTime);

		Map<EditorType, Object> mapClob = new HashMap();
		mapClob.put(EditorType.RichTextEditor, "");
		map.put(DataType.Clob.getValue_hibernet(), mapClob);

		/*
		 * Map<EditorType,Object> mapBlob = new HashMap();
		 * mapBlob.put(EditorType.FileEditor, "");
		 * map.put(DataType.Blob.getValue_hibernet(), mapBlob);
		 */

		return map;
	}

	/**
	 * 校验规则Map.
	 *
	 * @author mqfdy
	 * @return the validator map
	 * @Date 2018-09-03 09:00
	 */
	public static Map<String, Map<ValidatorType, Object>> getValidatorMap() {
		Map<String, Map<ValidatorType, Object>> map = new HashMap();

		Map<ValidatorType, Object> mapString = new HashMap();
		mapString.put(ValidatorType.Nullable, "");
		mapString.put(ValidatorType.StringLength, "");
		mapString.put(ValidatorType.CNString, "");
		mapString.put(ValidatorType.ENString, "");
		mapString.put(ValidatorType.Number, "");
		mapString.put(ValidatorType.Integer, "");
		mapString.put(ValidatorType.PastCode, "");
		mapString.put(ValidatorType.DateTime, "");
		mapString.put(ValidatorType.URL, "");
		mapString.put(ValidatorType.Email, "");
		mapString.put(ValidatorType.Regular, "");
		//mapString.put(ValidatorType.Unique, "");
		mapString.put(ValidatorType.Custom, "");
		map.put(DataType.String.getValue_hibernet(), mapString);

		Map<ValidatorType, Object> mapChar = new HashMap();
		mapChar.put(ValidatorType.Nullable, "");
		mapChar.put(ValidatorType.CNString, "");
		mapChar.put(ValidatorType.StringLength, "");
		mapChar.put(ValidatorType.ENString, "");
		mapChar.put(ValidatorType.Number, "");
		mapChar.put(ValidatorType.Integer, "");
		mapChar.put(ValidatorType.PastCode, "");
		mapChar.put(ValidatorType.DateTime, "");
		mapChar.put(ValidatorType.URL, "");
		mapChar.put(ValidatorType.Email, "");
		mapChar.put(ValidatorType.Regular, "");
		//mapChar.put(ValidatorType.Unique, "");
		mapChar.put(ValidatorType.Custom, "");
		map.put(DataType.Character.getValue_hibernet(), mapChar);

		Map<ValidatorType, Object> mapLong = new HashMap();
		mapLong.put(ValidatorType.Long, "");
		//mapLong.put(ValidatorType.Unique, "");
		mapLong.put(ValidatorType.Custom, "");
		mapLong.put(ValidatorType.PastCode, "");
		mapLong.put(ValidatorType.Nullable, "");
		map.put(DataType.Long.getValue_hibernet(), mapLong);

		Map<ValidatorType, Object> mapIntegter = new HashMap();
		mapIntegter.put(ValidatorType.Integer, "");
		//mapIntegter.put(ValidatorType.Unique, "");
		mapIntegter.put(ValidatorType.Custom, "");
		mapIntegter.put(ValidatorType.PastCode, "");
		mapIntegter.put(ValidatorType.Nullable, "");
		map.put(DataType.Integer.getValue_hibernet(), mapIntegter);

		Map<ValidatorType, Object> mapShort = new HashMap();
		mapShort.put(ValidatorType.Integer, "");
		//mapShort.put(ValidatorType.Unique, "");
		mapShort.put(ValidatorType.Custom, "");
		mapShort.put(ValidatorType.Nullable, "");
		map.put(DataType.Short.getValue_hibernet(), mapShort);

		Map<ValidatorType, Object> mapByte = new HashMap();
		mapByte.put(ValidatorType.Integer, "");
		//mapByte.put(ValidatorType.Unique, "");
		mapByte.put(ValidatorType.Custom, "");
		mapByte.put(ValidatorType.Nullable, "");
		map.put(DataType.Byte.getValue_hibernet(), mapByte);

		Map<ValidatorType, Object> mapFloat = new HashMap();
		mapFloat.put(ValidatorType.Number, "");
		//mapFloat.put(ValidatorType.Unique, "");
		mapFloat.put(ValidatorType.Custom, "");
		mapFloat.put(ValidatorType.Nullable, "");
		map.put(DataType.Float.getValue_hibernet(), mapFloat);

		Map<ValidatorType, Object> mapDouble = new HashMap();
		mapDouble.put(ValidatorType.Number, "");
		//mapDouble.put(ValidatorType.Unique, "");
		mapDouble.put(ValidatorType.Custom, "");
		mapDouble.put(ValidatorType.Nullable, "");
		map.put(DataType.Double.getValue_hibernet(), mapDouble);

		Map<ValidatorType, Object> mapBigdicimal = new HashMap();
		mapBigdicimal.put(ValidatorType.Number, "");
		mapBigdicimal.put(ValidatorType.Integer, "");
		//mapBigdicimal.put(ValidatorType.Unique, "");
		mapBigdicimal.put(ValidatorType.Custom, "");
		mapBigdicimal.put(ValidatorType.Nullable, "");
		map.put(DataType.Big_decimal.getValue_hibernet(), mapBigdicimal);

		Map<ValidatorType, Object> mapBoolean = new HashMap();
		mapBoolean.put(ValidatorType.StringLength, "");
		mapBoolean.put(ValidatorType.CNString, "");
		mapBoolean.put(ValidatorType.ENString, "");
		mapBoolean.put(ValidatorType.Integer, "");
		mapBoolean.put(ValidatorType.Nullable, "");
		map.put(DataType.Boolean.getValue_hibernet(), mapBoolean);

		// 时间日期
		Map<ValidatorType, Object> mapDate = new HashMap();
		mapDate.put(ValidatorType.DateTime, "");
		mapDate.put(ValidatorType.Nullable, "");
		map.put(DataType.Date.getValue_hibernet(), mapDate);

		Map<ValidatorType, Object> mapDateTime = new HashMap();
		mapDateTime.put(ValidatorType.DateTime, "");
		mapDateTime.put(ValidatorType.Nullable, "");
		map.put(DataType.Timestamp.getValue_hibernet(), mapDateTime);

		Map<ValidatorType, Object> mapTime = new HashMap();
		mapTime.put(ValidatorType.DateTime, "");
		mapTime.put(ValidatorType.Nullable, "");
		map.put(DataType.Time.getValue_hibernet(), mapTime);

		Map<ValidatorType, Object> mapClob = new HashMap();
		mapClob.put(ValidatorType.Nullable, "");
		mapClob.put(ValidatorType.StringLength, "");
		mapClob.put(ValidatorType.CNString, "");
		mapClob.put(ValidatorType.ENString, "");
		mapClob.put(ValidatorType.Number, "");
		mapClob.put(ValidatorType.Integer, "");
		mapClob.put(ValidatorType.PastCode, "");
		mapClob.put(ValidatorType.DateTime, "");
		mapClob.put(ValidatorType.URL, "");
		mapClob.put(ValidatorType.Email, "");
		mapClob.put(ValidatorType.Regular, "");
		//mapClob.put(ValidatorType.Unique, "");
		mapClob.put(ValidatorType.Custom, "");
		map.put(DataType.Clob.getValue_hibernet(), mapClob);

		/*
		 * Map<EditorType,Object> mapBlob = new HashMap();
		 * mapBlob.put(EditorType.FileEditor, "");
		 * map.put(DataType.Blob.getValue_hibernet(), mapBlob);
		 */

		return map;
	}
	
	/**
	 * Del error string.
	 *
	 * @author mqfdy
	 * @param displayName
	 *            the display name
	 * @return the string
	 * @Date 2018-09-03 09:00
	 */
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
	 * 校验IP是否输入正确.
	 *
	 * @author mqfdy
	 * @param ip
	 *            the ip
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
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
			}catch(NumberFormatException e){
				return false;
			}
		}
		return true;		
	}
	
	
	/**
	 * 校验端口是否输入正确.
	 *
	 * @author mqfdy
	 * @param port
	 *            the port
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	public static boolean validatePort(String port){
		try {
			int num=Integer.parseInt(port);
			if(num>65535||num<0){
				return false;
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;		
	}
	
	/**
	 * Validate dirctory.
	 *
	 * @author mqfdy
	 * @param dir
	 *            the dir
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	public static boolean validateDirctory(String dir){
		if (!dir.matches(DIRECTORY)) {
			return false;
		} 		
		return true;		
	}
	
	
}
