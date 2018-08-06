package com.mqfdy.code.generator.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * hibernate类型类
 * 
 * @author mqfdy
 */
public class HibernateDataType {

	public static List<String> originDataType;

	public static List<String> collectionType;

	static {
		originDataType = new ArrayList<String>();
		originDataType.add("string");
		originDataType.add("byte");
		originDataType.add("short");
		originDataType.add("int");
		originDataType.add("integer");
		originDataType.add("long");
		originDataType.add("character");
		originDataType.add("char");
		originDataType.add("float");
		originDataType.add("double");
		originDataType.add("boolean");
		originDataType.add("date");
		originDataType.add("timestamp");
		originDataType.add("time");
		originDataType.add("text");
		originDataType.add("blob");
		originDataType.add("clob");
		originDataType.add("big_decimal");

		collectionType = new ArrayList<String>();
		collectionType.add("list");
		collectionType.add("map");
		collectionType.add("set");
		collectionType.add("collection");
	}
}
