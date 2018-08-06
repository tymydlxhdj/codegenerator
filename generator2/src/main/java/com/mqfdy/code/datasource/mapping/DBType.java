package com.mqfdy.code.datasource.mapping;

import java.util.ArrayList;
import java.util.List;

public enum DBType {

	Oracle("Oracle", "org.hibernate.dialect.Oracle9Dialect"), 
	DB2("DB2","org.hibernate.dialect.DB2Dialect"), 
	Sybase("Sybase","org.hibernate.dialect.SybaseDialect"), 
	MySQL("MySQL","org.hibernate.dialect.MySQLDialect"),
	MsSQL("MSSQL","org.hibernate.dialect.SQLServerDialect"),
	POSTGRESQL("PostgreSQL","org.hibernate.dialect.PostgreSQLDialect"),
	SQLSERVER("SqlServer","org.hibernate.dialect.SQLServerDialect");
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
