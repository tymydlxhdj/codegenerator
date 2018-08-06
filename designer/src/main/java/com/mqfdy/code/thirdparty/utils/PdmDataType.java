package com.mqfdy.code.thirdparty.utils;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mqfdy.code.model.utils.DataType;

public class PdmDataType {
	private static Map<String,String> mapDataType = new HashMap();
	
	static {
		mapDataType.put("INT",  DataType.Integer.getValue_hibernet());
		mapDataType.put("INTEGER",  DataType.Integer.getValue_hibernet());
		mapDataType.put("NUMBER",  DataType.Integer.getValue_hibernet());
		mapDataType.put("LONG",  DataType.Long.getValue_hibernet());
		mapDataType.put("BIGINT",  DataType.Integer.getValue_hibernet());
		mapDataType.put("SMALLINT",  DataType.Integer.getValue_hibernet());
		mapDataType.put("BIT",  DataType.Byte.getValue_hibernet());
		mapDataType.put("FLOAT",  DataType.Float.getValue_hibernet());
		mapDataType.put("DOUBLE",  DataType.Double.getValue_hibernet());
		mapDataType.put("DECIMAL",  DataType.Big_decimal.getValue_hibernet());
		mapDataType.put("CHAR",  DataType.Character.getValue_hibernet());
		mapDataType.put("VARCHAR",  DataType.String.getValue_hibernet());
		mapDataType.put("VARCHAR2",  DataType.String.getValue_hibernet());
		mapDataType.put("NVARCHAR2",  DataType.String.getValue_hibernet());
		mapDataType.put("TEXT",  DataType.String.getValue_hibernet());
		mapDataType.put("BOOL",  DataType.Boolean.getValue_hibernet());
		mapDataType.put("BOOLEAN",  DataType.Boolean.getValue_hibernet());
		mapDataType.put("DATE",  DataType.Date.getValue_hibernet());
		mapDataType.put("TIME",  DataType.Time.getValue_hibernet());
		mapDataType.put("TIMESTAMP",  DataType.Timestamp.getValue_hibernet());
		mapDataType.put("CLOB",  DataType.Clob.getValue_hibernet());
		mapDataType.put("SET",  DataType.Set.getValue_hibernet());
	}
	
	public static String getOmDataType(String pdmType){
		
		if(pdmType!=null){
			String regexp = "([^()]+).*";
			Pattern pa = Pattern.compile(regexp);
			Matcher ma = pa.matcher(pdmType);
			while(ma.find()){
				pdmType = ma.group(1);
			}
		}else{
			pdmType="VARCHAR";
		}
			
		String omDataType = mapDataType.get(pdmType.toUpperCase(Locale.getDefault()));
		if(omDataType!=null){
			return omDataType;
		}else{
			return DataType.String.getValue_hibernet();
		}
	}
}