package com.mqfdy.code.utils;

import java.util.ArrayList;
import java.util.List;
/**
 * 数据库类型
 * @author mqfdy
 *
 */
public enum DBType {

	// "Oracle","DB2","Sybase","MSSQL","MySQL","金仓","达梦"

	Oracle("Oracle", "org.hibernate.dialect.Oracle9Dialect"), 
//	DB2("DB2","org.hibernate.dialect.DB2Dialect"), 
//	Sybase("Sybase","org.hibernate.dialect.SybaseDialect"), 
	MySQL("MySQL","org.hibernate.dialect.MySQLDialect"),
	MsSQL("MSSQL","org.hibernate.dialect.SQLServerDialect"),
	POSTGRESQL("PostgreSQL","org.hibernate.dialect.PostgreSQLDialect");
	private String dbType;

	private String dialect;

	DBType(String dbType, String dialect) {
		this.dbType = dbType;
		this.dialect = dialect;
	}

	public static List<DBType> getDBTypes() {
		List<DBType> list = new ArrayList<DBType>();
		for (int i = 0; i < DBType.values().length; i++) {
			list.add(DBType.values()[i]);
		}
		return list;
	}

	public static String getDialect(String dbType) {
		List<DBType> list = getDBTypes();
		for (int i = 0; i < list.size(); i++) {
			DBType dt = list.get(i);
			if (dt.getDbType().equals(dbType)) {
				return dt.getDialect();
			}
		}
		return null;
	}

	public String getDbType() {
		return dbType;
	}

	public void setDbType(String dbType) {
		this.dbType = dbType;
	}

	public String getDialect() {
		return dialect;
	}

	public void setDialect(String dialect) {
		this.dialect = dialect;
	}

}
