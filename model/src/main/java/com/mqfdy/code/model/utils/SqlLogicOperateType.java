package com.mqfdy.code.model.utils;

import java.util.ArrayList;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * sql查询条件逻辑运算符.
 *
 * @author mqfdy
 */
public enum SqlLogicOperateType {

	/** The none. */
	NONE("0", ""), /** The and. */
 AND("1", "and"), /** The or. */
 OR("2", "or");

	/** The disp name. */
	private String dispName;
	
	/** The id. */
	private String id;

	/**
	 * Instantiates a new sql logic operate type.
	 *
	 * @param id
	 *            the id
	 * @param dispName
	 *            the disp name
	 */
	SqlLogicOperateType(String id, String dispName) {
		this.id = id;
		this.dispName = dispName;
	}

	/**
	 * Gets the disp name.
	 *
	 * @author mqfdy
	 * @return the disp name
	 * @Date 2018-09-03 09:00
	 */
	public String getDispName() {
		return dispName;
	}

	/**
	 * Gets the id.
	 *
	 * @author mqfdy
	 * @return the id
	 * @Date 2018-09-03 09:00
	 */
	public String getId() {
		return id;
	}

	/**
	 * Gets the sql logic operate dis names.
	 *
	 * @author mqfdy
	 * @return the sql logic operate dis names
	 * @Date 2018-09-03 09:00
	 */
	public static List<String> getSqlLogicOperateDisNames() {
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < SqlLogicOperateType.values().length; i++) {
			list.add(SqlLogicOperateType.values()[i].getDispName());
		}
		return list;
	}

	/**
	 * Gets the sql logic operate types.
	 *
	 * @author mqfdy
	 * @return the sql logic operate types
	 * @Date 2018-09-03 09:00
	 */
	public static List<SqlLogicOperateType> getSqlLogicOperateTypes() {
		List<SqlLogicOperateType> list = new ArrayList<SqlLogicOperateType>();
		for (int i = 0; i < SqlLogicOperateType.values().length; i++) {
			list.add(SqlLogicOperateType.values()[i]);
		}
		return list;
	}

	/**
	 * Gets the sql logic operate type by id.
	 *
	 * @author mqfdy
	 * @param id
	 *            the id
	 * @return the sql logic operate type by id
	 * @Date 2018-09-03 09:00
	 */
	public static SqlLogicOperateType getSqlLogicOperateTypeById(String id) {
		List<SqlLogicOperateType> list = getSqlLogicOperateTypes();
		for (int i = 0; i < list.size(); i++) {
			if (id != null && id.equals(list.get(i).id)) {
				return list.get(i);
			}
		}
		return null;
	}

	/**
	 * Gets the sql logic operate type by name.
	 *
	 * @author mqfdy
	 * @param dispName
	 *            the disp name
	 * @return the sql logic operate type by name
	 * @Date 2018-09-03 09:00
	 */
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
