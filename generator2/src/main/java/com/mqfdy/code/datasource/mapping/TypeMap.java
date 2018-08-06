package com.mqfdy.code.datasource.mapping;

import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;

public class TypeMap {

	private static Hashtable<String, String> jdbcToJavaNativeMap = null;
	
	private static Hashtable<String, String> propertyToJavaMap = null;
	
	private static Hashtable<String, String> omTypeToVOMap = null;
	
	private static Hashtable<String, String> editorTypeToVOMap = null;

	private static boolean isInitialized = false;

	public TypeMap() {
	}

	public static synchronized void initialize() {
		if (!isInitialized) {
			jdbcToJavaNativeMap = new Hashtable<String, String>();
			jdbcToJavaNativeMap.put("CHAR", "String");
			jdbcToJavaNativeMap.put("VARCHAR", "String");
			jdbcToJavaNativeMap.put("LONGVARCHAR", "String");
			jdbcToJavaNativeMap.put("CLOB", "String");
			jdbcToJavaNativeMap.put("TEXT", "String");
			jdbcToJavaNativeMap.put("LONGTEXT", "String");
			jdbcToJavaNativeMap.put("MEDIUMTEXT", "String");
			jdbcToJavaNativeMap.put("TINYTEXT", "String");
			jdbcToJavaNativeMap.put("NUMERIC", "BigDecimal");
			jdbcToJavaNativeMap.put("DECIMAL", "BigDecimal");
			jdbcToJavaNativeMap.put("BIT", "boolean");
			jdbcToJavaNativeMap.put("TINYINT", "byte");
			jdbcToJavaNativeMap.put("SMALLINT", "short");
			jdbcToJavaNativeMap.put("INTEGER", "int");
			jdbcToJavaNativeMap.put("INT", "int");
			jdbcToJavaNativeMap.put("MEDIUMINT", "int");
			jdbcToJavaNativeMap.put("BIGINT", "long");
			jdbcToJavaNativeMap.put("REAL", "float");
			jdbcToJavaNativeMap.put("FLOAT", "float");
			jdbcToJavaNativeMap.put("DOUBLE", "double");
			jdbcToJavaNativeMap.put("BINARY", "byte[]");
			jdbcToJavaNativeMap.put("VARBINARY", "byte[]");
			jdbcToJavaNativeMap.put("LONGVARBINARY", "byte[]");
			jdbcToJavaNativeMap.put("BLOB", "byte[]");
			jdbcToJavaNativeMap.put("MEDIUMBLOB", "byte[]");
			jdbcToJavaNativeMap.put("TINYBLOB", "byte[]");
			jdbcToJavaNativeMap.put("LONGBLOB", "byte[]");
			jdbcToJavaNativeMap.put("DATE", "Date");
			jdbcToJavaNativeMap.put("DATETIME", "Timestamp");
			jdbcToJavaNativeMap.put("TIME", "Date");
			jdbcToJavaNativeMap.put("TIMESTAMP", "Date");
			jdbcToJavaNativeMap.put("BOOLEANCHAR", "boolean");
			jdbcToJavaNativeMap.put("BOOLEANINT", "boolean");
			jdbcToJavaNativeMap.put("YEAR", "Date");
			
			propertyToJavaMap = new Hashtable<String, String>();
			propertyToJavaMap.put("string", "String");
			propertyToJavaMap.put("clob", "String");
			propertyToJavaMap.put("character", "String");
			propertyToJavaMap.put("boolean", "Boolean");
			propertyToJavaMap.put("date", "Date");
			propertyToJavaMap.put("datetime", "Date");
			propertyToJavaMap.put("timestamp", "Timestamp");
			propertyToJavaMap.put("integer", "Integer");
			propertyToJavaMap.put("double", "Double");
			propertyToJavaMap.put("float", "Float");
			propertyToJavaMap.put("short", "Short");
			propertyToJavaMap.put("long", "Long");
			propertyToJavaMap.put("byte", "Byte");
			propertyToJavaMap.put("big_decimal", "BigDecimal");
			propertyToJavaMap.put("list", "List");
			propertyToJavaMap.put("set", "Set");
			propertyToJavaMap.put("map", "Map");
			propertyToJavaMap.put("time", "Time");
			
			omTypeToVOMap = new Hashtable<String, String>();
			omTypeToVOMap.put("string", "AttributeType.STRING");
			omTypeToVOMap.put("clob", "AttributeType.STRING");
			omTypeToVOMap.put("character", "AttributeType.CHARACTER");
			omTypeToVOMap.put("boolean", "AttributeType.BOOLEAN");
			omTypeToVOMap.put("time", "AttributeType.TIME");
			omTypeToVOMap.put("timestamp", "AttributeType.TIMESTAMP");
			omTypeToVOMap.put("date", "AttributeType.TIMESTAMP");
			omTypeToVOMap.put("integer", "AttributeType.INTEGER");
			omTypeToVOMap.put("double", "AttributeType.DOUBLE");
			omTypeToVOMap.put("float", "AttributeType.FLOAT");
			omTypeToVOMap.put("short", "AttributeType.SHORT");
			omTypeToVOMap.put("long", "AttributeType.LONG");
			omTypeToVOMap.put("byte", "AttributeType.BYTE");
			omTypeToVOMap.put("byte[]", "AttributeType.BYTEARRAY");
			omTypeToVOMap.put("big_decimal", "AttributeType.BIGDECIMAL");
			omTypeToVOMap.put("object", "AttributeType.OBJECT");
			
			editorTypeToVOMap = new Hashtable<String,String>();
			editorTypeToVOMap.put("TextEditor", "TextEditor");
			editorTypeToVOMap.put("NumberEditor", "NumberEditor");
			editorTypeToVOMap.put("MultTextEditor", "TextEditor");
			editorTypeToVOMap.put("PasswordTextEditor", "TextEditor");
			editorTypeToVOMap.put("RichTextEditor", "RichTextEditor");
			editorTypeToVOMap.put("TimeEditor", "TimeEditor");
			editorTypeToVOMap.put("DateEditor", "DateTimeEditor");
			editorTypeToVOMap.put("DateTimeEditor", "DateTimeEditor");
			editorTypeToVOMap.put("ComboBox", "DropDownEditor");
			editorTypeToVOMap.put("CheckComboBox", "DropDownEditor");
			editorTypeToVOMap.put("ListEditor", "ListEditor");
			editorTypeToVOMap.put("CheckListEditor", "CheckListEditor");
			editorTypeToVOMap.put("CheckEditor", "CheckListEditor");
			editorTypeToVOMap.put("RadioEditor", "CheckListEditor");
			editorTypeToVOMap.put("DropDownTreeEditor", "DropDownTreeEditor");
			editorTypeToVOMap.put("FileEditor", "FileEditor");
			editorTypeToVOMap.put("LinkEditor", "LinkEditor");
			editorTypeToVOMap.put("LabelEditor", "LabelEditor");
			editorTypeToVOMap.put("CustomEditor", "CustomEditor");
			
			isInitialized = true;
		}
	}
	public static boolean isInitialized() {
		return isInitialized;
	}
	
	public static String getEditorTypeVO(String propertyType) {
		if (!isInitialized)
			initialize();
		return (String) editorTypeToVOMap.get(propertyType);
	}

	public static Set<String> getEditorTypeToVOList() {
		Set<String> javaNatives = new HashSet<String>();
		Enumeration<String> keys = editorTypeToVOMap.keys();
		while(keys.hasMoreElements()) {
			javaNatives.add(editorTypeToVOMap.get(keys.nextElement()).toString());
		}
		return javaNatives;
	}
	
	public static String getOmVO(String propertyType) {
		if (!isInitialized)
			initialize();
		return (String) omTypeToVOMap.get(propertyType);
	}

	public static Set<String> getOmTypeToVOList() {
		Set<String> javaNatives = new HashSet<String>();
		Enumeration<String> keys = omTypeToVOMap.keys();
		while(keys.hasMoreElements()) {
			javaNatives.add(omTypeToVOMap.get(keys.nextElement()).toString());
		}
		return javaNatives;
	}
	
	public static String getPropertyJava(String propertyType) {
		if (!isInitialized)
			initialize();
		return (String) propertyToJavaMap.get(propertyType);
	}

	public static Set<String> getPropertyJavaList() {
		Set<String> javaNatives = new HashSet<String>();
		Enumeration<String> keys = propertyToJavaMap.keys();
		while(keys.hasMoreElements()) {
			javaNatives.add(propertyToJavaMap.get(keys.nextElement()).toString());
		}
		return javaNatives;
	}

	public static String getJavaNative(String jdbcType) {
		if (!isInitialized)
			initialize();
		return (String) jdbcToJavaNativeMap.get(jdbcType);
	}

	public static Set<String> getJavaNativeList() {
		Set<String> javaNatives = new HashSet<String>();
		Enumeration<String> keys = jdbcToJavaNativeMap.keys();
		while(keys.hasMoreElements()) {
			javaNatives.add(jdbcToJavaNativeMap.get(keys.nextElement()).toString());
		}
		return javaNatives;
	}

	public static Hashtable<String, String> getJdbcToJavaNativeMap() {
		return jdbcToJavaNativeMap;
	}

	public static Hashtable<String, String> getPropertyToJavaMap() {
		return propertyToJavaMap;
	}

	public static Hashtable<String, String> getOmTypeToVOMap() {
		return omTypeToVOMap;
	}

}
