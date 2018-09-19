package com.mqfdy.code.utils;

import java.util.ArrayList;
import java.util.List;
// TODO: Auto-generated Javadoc
/**
 * 数据库类型.
 *
 * @author mqfdy
 */
public enum DBType {

	// "Oracle","DB2","Sybase","MSSQL","MySQL","金仓","达梦"

	/** The Oracle. */
	Oracle("Oracle", "org.hibernate.dialect.Oracle9Dialect"), 
//	DB2("DB2","org.hibernate.dialect.DB2Dialect"), 
/** The My SQL. */
//	Sybase("Sybase","org.hibernate.dialect.SybaseDialect"), 
	MySQL("MySQL","org.hibernate.dialect.MySQLDialect"),
	
	/** The Ms SQL. */
	MsSQL("MSSQL","org.hibernate.dialect.SQLServerDialect"),
	
	/** The postgresql. */
	POSTGRESQL("PostgreSQL","org.hibernate.dialect.PostgreSQLDialect");
	
	/** The db type. */
	private String dbType;

	/** The dialect. */
	private String dialect;

	/**
	 * Instantiates a new DB type.
	 *
	 * @param dbType
	 *            the db type
	 * @param dialect
	 *            the dialect
	 */
	DBType(String dbType, String dialect) {
		this.dbType = dbType;
		this.dialect = dialect;
	}

	/**
	 * Gets the DB types.
	 *
	 * @author mqfdy
	 * @return the DB types
	 * @Date 2018-09-03 09:00
	 */
	public static List<DBType> getDBTypes() {
		List<DBType> list = new ArrayList<DBType>();
		for (int i = 0; i < DBType.values().length; i++) {
			list.add(DBType.values()[i]);
		}
		return list;
	}

	/**
	 * Gets the dialect.
	 *
	 * @author mqfdy
	 * @param dbType
	 *            the db type
	 * @return the dialect
	 * @Date 2018-09-03 09:00
	 */
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

	/**
	 * Gets the db type.
	 *
	 * @author mqfdy
	 * @return the db type
	 * @Date 2018-09-03 09:00
	 */
	public String getDbType() {
		return dbType;
	}

	/**
	 * Sets the db type.
	 *
	 * @author mqfdy
	 * @param dbType
	 *            the new db type
	 * @Date 2018-09-03 09:00
	 */
	public void setDbType(String dbType) {
		this.dbType = dbType;
	}

	/**
	 * Gets the dialect.
	 *
	 * @author mqfdy
	 * @return the dialect
	 * @Date 2018-09-03 09:00
	 */
	public String getDialect() {
		return dialect;
	}

	/**
	 * Sets the dialect.
	 *
	 * @author mqfdy
	 * @param dialect
	 *            the new dialect
	 * @Date 2018-09-03 09:00
	 */
	public void setDialect(String dialect) {
		this.dialect = dialect;
	}

}
