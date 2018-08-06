package com.mqfdy.code.model.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * sql查询条件逻辑运算符
 * 
 * @author mqfdy
 * 
 */
public enum SqlLogicOperateType {

	NONE("0", ""), AND("1", "and"), OR("2", "or");

	private String dispName;
	private String id;

	SqlLogicOperateType(String id, String dispName) {
		this.id = id;
		this.dispName = dispName;
	}

	public String getDispName() {
		return dispName;
	}

	public String getId() {
		return id;
	}

	public static List<String> getSqlLogicOperateDisNames() {
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < SqlLogicOperateType.values().length; i++) {
			list.add(SqlLogicOperateType.values()[i].getDispName());
		}
		return list;
	}

	public static List<SqlLogicOperateType> getSqlLogicOperateTypes() {
		List<SqlLogicOperateType> list = new ArrayList<SqlLogicOperateType>();
		for (int i = 0; i < SqlLogicOperateType.values().length; i++) {
			list.add(SqlLogicOperateType.values()[i]);
		}
		return list;
	}

	public static SqlLogicOperateType getSqlLogicOperateTypeById(String id) {
		List<SqlLogicOperateType> list = getSqlLogicOperateTypes();
		for (int i = 0; i < list.size(); i++) {
			if (id != null && id.equals(list.get(i).id)) {
				return list.get(i);
			}
		}
		return null;
	}

	public static SqlLogicOperateType getSqlLogicOperateTypeByName(
			String dispName) {
		List<SqlLogicOperateType> list = getSqlLogicOperateTypes();
		for (int i = 0; i < list.size(); i++) {
			if (dispName != null
					&& dispName.equalsIgnoreCase(list.get(i).dispName)) {
				return list.get(i);
			}
		}
		return null;
	}
}
