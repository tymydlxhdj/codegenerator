package com.mqfdy.code.datasource.constant;

public interface IDataSource {

	/**
	 * MySQL(new)
	 */
	public static final String DRIVER_CLASS_MYSQL_NEW = "com.mysql.jdbc.Driver";
	
	/**
	 * MySQL(old)
	 */
	public static final String DRIVER_CLASS_MYSQL_OLD = "org.gjt.mm.mysql.Driver";
	
	/**
	 * Oracle
	 */
	public static final String DRIVER_CLASS_ORACLE = "oracle.jdbc.driver.OracleDriver";
	
	/**
	 * SqlServer(new)
	 */
	public static final String DRIVER_CLASS_SQLSERVER_NEW = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	
	/**
	 * SqlServer(old)
	 */
	public static final String DRIVER_CLASS_SQLSERVER_OLD = "com.microsoft.jdbc.sqlserver.SQLServerDriver";
	
	/**
	 * Sybase(new)
	 */
	public static final String DRIVER_CLASS_SYBASE_NEW = "com.sybase.jdbc3.jdbc.SybDriver";
	
	/**
	 * Sybase(old)
	 */
	public static final String DRIVER_CLASS_SYBASE_OLD = "com.sysbase.jdbc.SybDriver";
	
	/**
	 * 达梦
	 */
	public static final String DRIVER_CLASS_DM = "dm.jdbc.driver.DmDriver";
	
	/**
	 * 金仓
	 */
	public static final String DRIVER_CLASS_KINGBASE = "com.kingbase.Driver";
	
	/**
	 * DB2
	 */
	public static final String DRIVER_CLASS_DB2 = "com.ibm.db2.jcc.DB2Driver";
	
	
}
