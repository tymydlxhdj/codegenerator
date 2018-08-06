package com.mqfdy.code.reverse.jdbc;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import com.mqfdy.code.model.utils.DataType;

public class MiddleDataType {

	public static Set<String> stringSet = new HashSet<String>();
	public static Set<String> numberSet = new HashSet<String>();
	public static Set<String> textSet = new HashSet<String>();
	public static Set<String> dateSet = new HashSet<String>();
	public static Set<String> logicSet = new HashSet<String>();
	
	private static Locale defLocal = Locale.getDefault();
	
	static{
		//字符串类型
		stringSet.add("VARCHAR");
		stringSet.add("VARCHAR2");
		stringSet.add("NVARCHAR");
		stringSet.add("NVARCHAR2");
		stringSet.add("CHAR");
		stringSet.add("NCHAR");
		stringSet.add("TEXT");
		//stringSet.add("LONG");
		
		//数值类型
		numberSet.add("NUMBER");
		numberSet.add("NUMERIC");
		numberSet.add("FLOAT");
		numberSet.add("DOUBLE");
		numberSet.add("INT");
		numberSet.add("INTEGER");
		numberSet.add("BINARY_FLOAT");
		numberSet.add("BINARY_DOUBLE");
		
		//MYSQL 特有的int类型
		numberSet.add("BIGINT");
		numberSet.add("SMALLINT");
		numberSet.add("MEDIUMINT");
		numberSet.add("TINYINT");
		
		//PostgreSQL数值类型
		numberSet.add("INT4");
		numberSet.add("INT8");
		numberSet.add("INT2");
		numberSet.add("FLOAT4");
		numberSet.add("FLOAT8");
		
		
		//大文本
		textSet.add("CLOB");
		textSet.add("BLOB");
		//mysql 大文本
		textSet.add("BLOB");
		textSet.add("TINYBLOB");
		textSet.add("LONGBLOB");
		textSet.add("MEDIUMBLOB");
		textSet.add("TEXT");
		textSet.add("TINYTEXT");
		textSet.add("LONGTEXT");
		textSet.add("MEDIUMTEXT");
		
		//时间类型
		dateSet.add("DATE");
		dateSet.add("DATETIME");
		dateSet.add("TIME");
		dateSet.add("TIMESTAMP");
		dateSet.add("TIMESTAMP(6)");
		
		//逻辑类型
		logicSet.add("BOOL");
	}
	
	public static String getMiddleType(String sqlType,Integer length,Integer scale){
		if(stringSet.contains(sqlType.toUpperCase(defLocal))){
			if("char".equals(sqlType.toLowerCase(defLocal)) && length/2 == 1){
				return DataType.Character.getValue_hibernet();
			}else if(length >= 4000){
				return DataType.Clob.getValue_hibernet();
			}
			return DataType.String.getValue_hibernet();
		}else if(numberSet.contains(sqlType.toUpperCase(defLocal))){
			if("FLOAT".equals(sqlType.toUpperCase(defLocal))||"FLOAT4".equals(sqlType.toUpperCase(defLocal)) || "BINARY_FLOAT".equals(sqlType.toUpperCase(defLocal))){
				return DataType.Float.getValue_hibernet();
			}else if("DOUBLE".equals(sqlType.toUpperCase(defLocal))||"FLOAT8".equals(sqlType.toUpperCase(defLocal)) || "BINARY_DOUBLE".equals(sqlType.toUpperCase(defLocal))){
				return DataType.Double.getValue_hibernet();
			}else if("INT2".equals(sqlType.toUpperCase(defLocal))){
				return DataType.Short.getValue_hibernet();
			}else if("INT8".equals(sqlType.toUpperCase(defLocal))){
				return DataType.Long.getValue_hibernet();
			}else if("INT".equals(sqlType.toUpperCase(defLocal))){
				return DataType.Integer.getValue_hibernet();
			}
			else if(length == 1 && scale == 0){
				return DataType.Boolean.getValue_hibernet();
			}else if(length > 19 && scale == 0){
				return DataType.Big_decimal.getValue_hibernet();
			}else if((length<= 19 && length>10) && scale == 0){
				return DataType.Long.getValue_hibernet();
			}else if(((length<=10 && length> 5) && scale == 0)||"INT4".equals(sqlType.toUpperCase(defLocal))){
				return DataType.Integer.getValue_hibernet();
			}else if((length<=5 && length> 3) && scale == 0){
				return DataType.Short.getValue_hibernet();
			}else if((length<=3 && length> 1) && scale == 0){
				return DataType.Byte.getValue_hibernet();
			}else if(scale != 0){
				return DataType.Big_decimal.getValue_hibernet();
			}
		}else if(textSet.contains(sqlType.toUpperCase(defLocal))){
			return DataType.Clob.getValue_hibernet();
		}else if(dateSet.contains(sqlType.toUpperCase(defLocal))){
			if("DATE".equals(sqlType.toUpperCase(defLocal))){
				return DataType.Date.getValue_hibernet();
			}else if("TIME".equals(sqlType.toUpperCase(defLocal))){
				return DataType.Time.getValue_hibernet();
			}
			return DataType.Timestamp.getValue_hibernet();
		}else if("DATETIME".equals(sqlType.toUpperCase(defLocal))){
			return DataType.Date.getValue_hibernet();
		}else if("LONG".equals(sqlType.toUpperCase(defLocal))){
			 return DataType.Long.getValue_hibernet();
		}
		else if(logicSet.contains(sqlType.toUpperCase(defLocal))){
			return DataType.Boolean.getValue_hibernet();
		}
		return "string";
	}
	
}
