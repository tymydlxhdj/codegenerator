package com.mqfdy.code.model.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * sql查询条件操作符
 * 
 * @author mqfdy
 * 
 */
public enum SqlOperateType {

	NONE("000", ""), Oracle_equals("100", "="), Oracle_smallThan("101", "<"), Oracle_bigThan(
			"102", ">"), Oracle_smallThanOrEquals("103", "<="), Oracle_bigThanOrEquals(
			"104", ">="), Oracle_like("105", "like"), Oracle_in("106", "in"), Oracle_notIn(
			"107", "not in"), Oracle_notEquals("108", "<>");

	/*
	 * DB2_equals("200","=",DBType.DB2), DB2_smallThan("201","=",DBType.DB2),
	 * DB2_bigThan("202","=",DBType.DB2),
	 * DB2_smallThanOrEquals("203","<=",DBType.DB2),
	 * DB2_bigThanOrEquals("204",">=",DBType.DB2),
	 * DB2_like("205","like",DBType.DB2),
	 * DB2_between("206","between",DBType.DB2),
	 * DB2_isNull("207","is null",DBType.DB2),
	 * DB2_isNotNull("208","is not null",DBType.DB2),
	 * DB2_notEquals("209","<>",DBType.DB2),
	 * 
	 * Sybase_equals("300","=",DBType.Sybase),
	 * Sybase_smallThan("301","=",DBType.Sybase),
	 * Sybase_bigThan("302","=",DBType.Sybase),
	 * Sybase_smallThanOrEquals("303","<=",DBType.Sybase),
	 * Sybase_bigThanOrEquals("304",">=",DBType.Sybase),
	 * Sybase_like("305","like",DBType.Sybase),
	 * Sybase_between("306","between",DBType.Sybase),
	 * Sybase_isNull("307","is null",DBType.Sybase),
	 * Sybase_isNotNull("308","is not null",DBType.Sybase),
	 * Sybase_notEquals("309","<>",DBType.Sybase),
	 * 
	 * MySQL_equals("400","=",DBType.MySQL),
	 * MySQL_smallThan("401","=",DBType.MySQL),
	 * MySQL_bigThan("402","=",DBType.MySQL),
	 * MySQL_smallThanOrEquals("403","<=",DBType.MySQL),
	 * MySQL_bigThanOrEquals("404",">=",DBType.MySQL),
	 * MySQL_like("405","like",DBType.MySQL),
	 * MySQL_between("406","between",DBType.MySQL),
	 * MySQL_isNull("407","is null",DBType.MySQL),
	 * MySQL_isNotNull("408","is not null",DBType.MySQL),
	 * MySQL_notEquals("409","<>",DBType.MySQL),
	 * 
	 * MsSQL_equals("500","=",DBType.MsSQL),
	 * MsSQL_smallThan("501","=",DBType.MsSQL),
	 * MsSQL_bigThan("502","=",DBType.MsSQL),
	 * MsSQL_smallThanOrEquals("503","<=",DBType.MsSQL),
	 * MsSQL_bigThanOrEquals("504",">=",DBType.MsSQL),
	 * MsSQL_like("505","like",DBType.MsSQL),
	 * MsSQL_between("506","between",DBType.MsSQL),
	 * MsSQL_isNull("507","is null",DBType.MsSQL),
	 * MsSQL_isNotNull("508","is not null",DBType.MsSQL),
	 * MsSQL_notEquals("509","<>",DBType.MsSQL),
	 * 
	 * KingBase_equals("600","=",DBType.KingBase),
	 * KingBase_smallThan("601","=",DBType.KingBase),
	 * KingBase_bigThan("602","=",DBType.KingBase),
	 * KingBase_smallThanOrEquals("603","<=",DBType.KingBase),
	 * KingBase_bigThanOrEquals("604",">=",DBType.KingBase),
	 * KingBase_like("605","like",DBType.KingBase),
	 * KingBase_between("606","between",DBType.KingBase),
	 * KingBase_isNull("607","is null",DBType.KingBase),
	 * KingBase_isNotNull("608","is not null",DBType.KingBase),
	 * KingBase_notEquals("609","<>",DBType.KingBase),
	 * 
	 * DM_equals("700","=",DBType.DM), DM_smallThan("701","=",DBType.DM),
	 * DM_bigThan("702","=",DBType.DM),
	 * DM_smallThanOrEquals("703","<=",DBType.DM),
	 * DM_bigThanOrEquals("704",">=",DBType.DM),
	 * DM_like("705","like",DBType.DM), DM_between("706","between",DBType.DM),
	 * DM_isNull("707","is null",DBType.DM),
	 * DM_isNotNull("708","is not null",DBType.DM),
	 * DM_notEquals("709","<>",DBType.DM);
	 */

	private String id;
	private String dispName;

	SqlOperateType(String id, String dispName) {
		this.id = id;
		this.dispName = dispName;
	}

	/**
	 * 获取所有的Sql操作枚举列表
	 * 
	 * @return
	 */
	public static List<SqlOperateType> getSqlOperateTypes() {
		List<SqlOperateType> list = new ArrayList<SqlOperateType>();
		for (int i = 0; i < SqlOperateType.values().length; i++) {
			list.add(SqlOperateType.values()[i]);
		}
		return list;
	}

	public static List<String> getSqlOperateDispNames() {
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < SqlOperateType.values().length; i++) {
			list.add(SqlOperateType.values()[i].getDispName());
		}
		return list;
	}

	/**
	 * 通过id获取枚举对象
	 * 
	 * @param id
	 * @return
	 */
	public static SqlOperateType getSqlOperateTypeById(String id) {
		List<SqlOperateType> list = getSqlOperateTypes();
		for (int i = 0; i < list.size(); i++) {
			if (id != null && id.equals(list.get(i).id)) {
				return list.get(i);
			}
		}
		return null;
	}

	/**
	 * 通过名称和数据库类型获取枚举对象
	 * 
	 * @param disName
	 * @param dbType
	 * @return
	 */
	public static SqlOperateType getSqlOperateTypeByDispName(String disName) {
		List<SqlOperateType> list = getSqlOperateTypes();
		for (int i = 0; i < list.size(); i++) {
			if (disName != null
					&& disName.equalsIgnoreCase(list.get(i).dispName)) {
				return list.get(i);
			}
		}
		return null;
	}

	public String getId() {
		return id;
	}

	public String getDispName() {
		return dispName;
	}

}
