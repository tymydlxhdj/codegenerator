package com.mqfdy.code.datasource.utils;

import com.mqfdy.code.datasource.mapping.DBType;

// TODO: Auto-generated Javadoc
/**
 * The Class ReverseContants.
 *
 * @author mqfdy
 */
public class ReverseContants {

	/** The Constant SEPERATOR. */
	public final static String SEPERATOR = "\\";
	
	/** The Constant DOT. */
	public final static String DOT = ".";

	/** The Constant NOPKTABLE. */
	public final static String NOPKTABLE = "noPkTable";
	
	/** The Constant MUTILPKTABLE. */
	public final static String MUTILPKTABLE = "mutilPkTable";

	/**
	 * The Class DataSource.
	 *
	 * @author mqfdy
	 */
	public final static class DataSource {
		
		/** The Constant DS_NAME. */
		// 数据源信息
		public static final String DS_NAME = "DataSourceName";
		
		/** The Constant SID. */
		public static final String SID = "Sid";
		
		/** The Constant URL. */
		public static final String URL = "Url";
		
		/** The Constant USER. */
		public static final String USER = "User";
		
		/** The Constant PPP. */
		public static final String PPP = "Password";
		
		/** The Constant DRIVER_URL. */
		public static final String DRIVER_URL = "DriverUrl";
		
		/** The Constant DRIVER_CLASS. */
		public static final String DRIVER_CLASS = "DriverClass";
		
		/** The Constant DB_TYPE. */
		public static final String DB_TYPE = "DbType";
		
		/** The Constant DB_SELECT. */
		public static final String DB_SELECT = "Select";
		
		/** The Constant DB_IP. */
		public static final String DB_IP = "Ip";
		
		/** The Constant DB_PORT. */
		public static final String DB_PORT = "Port";

		/** The Constant DRIVER_JAR_PARENT. */
		public static final String DRIVER_JAR_PARENT = "plugins";
		
		/** The plugin driver. */
		public static String PLUGIN_DRIVER = "com.sgcc.uap.ide.database.drivers";
		
		/** The Constant DRIVER_JAR_CHILD. */
		public static final String DRIVER_JAR_CHILD = "lib";

	}

	/**
	 * The Class Oracle_SQL.
	 *
	 * @author mqfdy
	 */
	public final static class Oracle_SQL {
		
		/**
		 * Sql oracle default.
		 *
		 * @author mqfdy
		 * @return the string
		 * @Date 2018-09-03 09:00
		 */
		// 查询oracle下的字段默认值
		public static String SQL_ORACLE_DEFAULT() {

			return "SELECT DATA_DEFAULT " +
					 "FROM  USER_TAB_COLUMNS " + 
					"WHERE TABLE_NAME=?" +
					" AND COLUMN_NAME=?";
		}

		/**
		 * Sql oracle tablepk cols.
		 *
		 * @author mqfdy
		 * @return the string
		 * @Date 2018-09-03 09:00
		 */
		// 查询某表的主键列集合
		public static String SQL_ORACLE_TABLEPK_COLS() {

			return "select col.column_name colName " +
					 "from user_constraints con,  user_cons_columns col " +
					"where con.constraint_name = col.constraint_name " +
					  "and con.constraint_type='P' " +
					  "and col.table_name = ?";
		}

		
		/**
		 * 查询表下具有唯一约束的字段名集合的sql语句 .
		 *
		 * @author mqfdy
		 * @param dbType
		 *            the db type
		 * @return String
		 * @Date 2018-09-03 09:00
		 */
		public static String getUniqueColumnsSQL(String dbType) {
			StringBuffer buffer = new StringBuffer();
			if(DBType.Oracle.getDbType().equals(dbType)){
				buffer.append("select column_name column_name ");
				buffer.append("  from user_cons_columns cu, user_constraints au ");
				buffer.append(" where cu.constraint_name = au.constraint_name ");
				buffer.append("   and au.constraint_type = 'U' ");
				buffer.append("   and au.table_name = ?");
			} else if(DBType.MySQL.getDbType().equals(dbType)||DBType.MsSQL.getDbType().equals(dbType)) {
				buffer.append("SELECT KC.COLUMN_NAME column_name");
				buffer.append("  FROM INFORMATION_SCHEMA.KEY_COLUMN_USAGE KC, INFORMATION_SCHEMA.TABLE_CONSTRAINTS TC");
				buffer.append(" WHERE KC.CONSTRAINT_NAME = TC.CONSTRAINT_NAME");
				buffer.append("   AND TC.CONSTRAINT_TYPE = 'UNIQUE'");
				buffer.append("   AND TC.TABLE_NAME = ?");
			}
			
			return buffer.toString();
		}
		
		/** The sql oracle unique. */
		// 查询表下具有唯一约束的字段名集合
		public static String SQL_ORACLE_UNIQUE = "select column_name " + 
												   "from user_cons_columns cu, user_constraints au " + 
												  "where cu.constraint_name = au.constraint_name " +
													"and au.constraint_type = 'U' " +
													"and au.table_name =";
		
		/** The sql kingbase unique. */
		// 查询金仓数据库表下具有唯一约束的字段名集合
		public static String SQL_KINGBASE_UNIQUE = "select col.column_name column_name from   " +
													" user_constraints au, all_objects obj ,user_ind_columns col"+
				  									" where au.constraint_name=obj.object_name "+
				  									" and au.constraint_type = 'U' and col.index_name=obj.object_name   and au.table_name=";
		
		/**
		 * Sql oracle sequence.
		 *
		 * @author mqfdy
		 * @return the string
		 * @Date 2018-09-03 09:00
		 */
		// 查询某用户下的所有sequence名集合
		public static String SQL_ORACLE_SEQUENCE() {
			return "SELECT SEQUENCE_NAME FROM ALL_SEQUENCES WHERE SEQUENCE_OWNER=?";
		}

		/**
		 * 查询某表下所有外键信息.
		 *
		 * @author mqfdy
		 * @return 查询某表下所有外键信息SQL
		 * @Date 2018-9-3 11:38:36
		 */
		public static String SQL_ORACLE_FK_INFO() {

			return "select c.table_name" +
					    ", c.constraint_name" +
					    ", c.r_constraint_name " +
					 "from user_constraints c " + 
					"where c.constraint_type = 'R' " +
					  "and c.table_name = ?";
		}

		/**
		 * Sql oracle zibiao.
		 *
		 * @author mqfdy
		 * @return the string
		 * @Date 2018-09-03 09:00
		 */
		public static String SQL_ORACLE_ZIBIAO() {

			return "SELECT a.table_name FKTABLE_NAME " +
					 "FROM USER_CONSTRAINTs a,user_constraints b " +
					"where b.constraint_name=a.R_CONSTRAINT_NAME " +
					  "and a.constraint_type='R' " +
					  "AND b.table_name= ?";
		}

		/**
		 * 根据外键约束名获取该外键引用table名与引用的字段名.
		 *
		 * @author mqfdy
		 * @return the string
		 * @Date 2018-09-03 09:00
		 */
		public static String SQL_ORACLE_FKCOLUMNS_INFO() {
			return "select table_name,column_name " +
					 "from user_cons_columns cl " +
					"where cl.constraint_name =?";
		}

		/**
		 * Sql oracle columns info.
		 *
		 * @author mqfdy
		 * @return the string
		 * @Date 2018-09-03 09:00
		 */
		// 读取表列信息
		public static String SQL_ORACLE_COLUMNS_INFO() {

			return "select A.char_used" +
					    ", A.column_name column_name" +
					    ", A.data_type data_type" +
					    ", A.data_length data_length" +
					    ", A.data_precision data_precision" +
					    ", A.data_Scale data_Scale" +
					    ", A.nullable nullable" +
					    ", A.data_default data_default" +
					    ", B.comments comments " +
					 "from user_tab_columns A" +
					    ", user_col_comments B " +
					"where A.Table_Name = B.Table_Name " +
					  "and A.Column_Name = B.Column_Name " +
					  "and A.Table_Name = ?";
		}
		
		/**
		 * 查询当前用户下所有的列信息的sql语句.
		 *
		 * @author mqfdy
		 * @return String sql语句
		 * @Date 2018-9-3 11:38:36
		 */
		public static String SQL_ORACLE_TABLES_INFO() {

			StringBuffer buffer = new StringBuffer();
			buffer.append("select col.char_used,");
			buffer.append("       col.column_name    column_name,");
			buffer.append("       col.data_type      data_type,");
			buffer.append("       col.data_length    data_length,");
			buffer.append("       col.data_precision data_precision,");
			buffer.append("       col.data_Scale     data_Scale,");
			buffer.append("       col.nullable       nullable,");
			buffer.append("       col.data_default   data_default,");
			buffer.append("       cc.comments        comments,");
			buffer.append("       tab.Table_Name     table_name,");
			buffer.append("       tc.comments        table_comment");
			buffer.append("  from user_tab_columns  col,");
			buffer.append("       user_col_comments cc,");
			buffer.append("       user_tables       tab,");
			buffer.append("       user_tab_comments tc");
			buffer.append(" where tab.Table_Name = tc.table_name");
			buffer.append("   and col.Table_Name = tab.Table_Name");
			buffer.append("   and col.TABLE_NAME = cc.table_name");
			buffer.append("   and col.Column_Name = cc.Column_Name order by col.COLUMN_ID asc");
			return buffer.toString();
		}
		
		/**
		 * 查询达梦数据库当前用户下所有的列信息的sql语句.
		 *
		 * @author mqfdy
		 * @return String sql语句
		 * @Date 2018-9-3 11:38:36
		 */
		public static String SQL_DM_TABLES_INFO() {
			StringBuffer buffer = new StringBuffer();
			buffer.append("select col.Table_Name,");
			buffer.append("       so1.name           schema_name,");
			buffer.append("       col.char_used,");
			buffer.append("       col.column_name    column_name,");
			buffer.append("       col.data_type      data_type,");
			buffer.append("       col.data_length    data_length,");
			buffer.append("       col.data_precision data_precision,");
			buffer.append("       col.data_Scale     data_Scale,");
			buffer.append("       col.nullable       nullable,");
			buffer.append("       col.data_default   data_default,");
			buffer.append("       tc.comments        comments,");
			buffer.append("       tab.Table_Name     table_name,");
			buffer.append("       tc.comments        table_comment");
			buffer.append("  from user_tab_columns  col,");
			buffer.append("       user_tables       tab,");
			buffer.append("       sysobjects        so1,");
			buffer.append("       sysobjects        so2,");
			buffer.append("       user_tab_comments tc");
			buffer.append(" where tab.Table_Name = tc.table_name");
			buffer.append("   and col.Table_Name = tab.Table_Name");
			buffer.append("   and so2.type$ = 'SCHOBJ'");
			buffer.append("   and so1.id = so2.SCHID");
			buffer.append("   and so2.name = tab.Table_Name");
			buffer.append("   and tab.Table_Name not like '##%'");
			return buffer.toString();
		}
	}

}
