package com.mqfdy.code.reverse.jdbc;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import com.mqfdy.code.model.utils.DBType;
import com.mqfdy.code.reverse.ReverseContext;
import com.mqfdy.code.reverse.mappings.Column;
import com.mqfdy.code.reverse.mappings.ForeignKey;
import com.mqfdy.code.reverse.mappings.PrimaryKey;
import com.mqfdy.code.reverse.mappings.Table;
import com.mqfdy.code.reverse.mappings.View;
import com.mqfdy.code.reverse.utils.ReverseContants;
import com.mqfdy.code.reverse.utils.ReverseUtil;

public class JDBCReader {
	
	private List<Table> tableList =new ArrayList<Table>();
	
	/**
	 * 针对oracle数据库，采取发送sql语句的方式读取表结构信息
	 * @author mqfdy
	 * @param connection
	 * @return List<Table> 表结构信息集合
	 */
	public List<Table> readTableInfoBySql(Connection connection,String dbtype) {

		String sql =null;
		
		/*if (dbtype.equals(DBType.DM.getDbType())) {
			sql = ReverseContants.Oracle_SQL.SQL_DM_TABLES_INFO();
		} else{
			sql = ReverseContants.Oracle_SQL.SQL_ORACLE_TABLES_INFO();
		}*/
		sql = ReverseContants.Oracle_SQL.SQL_ORACLE_TABLES_INFO();
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			/**
			 * 发送oracle的sql查询当前用户下所有的列信息
			 * 包括关联的表结构的信息
			 * */
			pst = connection.prepareStatement(sql);
			rs = pst.executeQuery();
			
			List<Map<String, Object>> columnInfoMapList = new ArrayList<Map<String,Object>>();
			Map<String, Object> columnInfoMap = null;
			while (rs.next()) {
				columnInfoMap = new HashMap<String, Object>();

				/*if (dbtype.equals(DBType.DM.getDbType())) {
					columnInfoMap.put("schema_name", rs.getString("schema_name"));
				} */
				
				String charUsed = rs.getString("char_used");//C:数据长度除以2，  否则直接取值
				
				columnInfoMap.put("column_name", rs.getString("column_name"));
				columnInfoMap.put("data_type", rs.getString("data_type"));
				
				int dataLength = rs.getInt("data_length");
				if (charUsed != null && charUsed.trim().length() != 0 && charUsed.equals("C")) {
					dataLength = dataLength / 2;
				}
				columnInfoMap.put("data_length", dataLength);
				
				int precision = rs.getInt("data_precision");
				if (precision > 0) {
					columnInfoMap.put("data_length", precision);
				}
				
				columnInfoMap.put("data_Scale", rs.getInt("data_Scale"));
				columnInfoMap.put("nullable", rs.getString("nullable").equals("N") ? false : true);
				
				String dataDefault = rs.getString("data_default");
				if (dataDefault != null && dataDefault.trim().length() != 0) {
					columnInfoMap.put("data_default", dataDefault.replaceAll("'", ""));
				}
				
				columnInfoMap.put("comments", rs.getString("comments"));
				columnInfoMap.put("table_name", rs.getString("table_name"));
				columnInfoMap.put("table_comment", rs.getString("table_comment"));
				columnInfoMapList.add(columnInfoMap);
			}
			
			/**
			 * 把所有查询结果按照表名进行分组, 放入tableColumnsMap中
			 * */
			Map<String, List<Map<String, Object>>> tableColumnsMap = new HashMap<String, List<Map<String, Object>>>();
			
			for(Map<String, Object> beanMap: columnInfoMapList) {
				String tableName = beanMap.get("table_name").toString();
				List<Map<String, Object>> columns = tableColumnsMap.get(tableName);
				if(columns == null) {
					columns = new ArrayList<Map<String, Object>>();
					tableColumnsMap.put(tableName, columns);
				}
				columns.add(beanMap);
			}

			/**
			 * 把分组后的结果组装成表集合，并且设置每个表中的列信息集合
			 * */
			List<Table> tableList = new ArrayList<Table>();
			Table table = null;
			for(Entry<String, List<Map<String, Object>>> entry: tableColumnsMap.entrySet()) {
				String tableName = entry.getKey();
				List<Map<String, Object>> columnMapList = entry.getValue();
				Map<String, Object> map = columnMapList.get(0);
				String tableComment = map.get("table_comment") == null ? "" : map.get("table_comment").toString();
				
				table = new Table();
				table.setName(tableName);
				table.setComment(tableComment);
				
				/*if (dbtype.equals(DBType.DM.getDbType())) {
					table.setSchemaName(map.get("schema_name").toString());
				}*/
				
				Column column = null;
				for(Map<String, Object> columnMap: columnMapList) {
					column = new Column();
					column.setName(columnMap.get("column_name").toString());
					column.setSqlType(columnMap.get("data_type").toString());
					column.setLength((Integer) columnMap.get("data_length"));
					column.setScale((Integer) columnMap.get("data_Scale"));
					column.setNullable((Boolean) columnMap.get("nullable"));
					column.setDefaultValue(columnMap.get("data_default") == null ? "": columnMap.get("data_default").toString());
					column.setComment(columnMap.get("comments") == null ? "" : columnMap.get("comments").toString());
					table.addColumn(column);
				}
				//缓存数据库所有表对象, 以备后续使用。
				ReverseContext.allTables.put(tableName, table);
				
				tableList.add(table);
			}
			return tableList;
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		} finally {
			ReverseUtil.closeResultSet(rs);
			ReverseUtil.closePreparedStatement(pst);
		}

	}
	
	/**
	 * 查询schema下的所有Table信息
	 * @param connection
	 * @param schema
	 * @return
	 */
	public List<Table> readTables(Connection connection, String schema) {
		ReverseContext.allTables.clear();
		try {
			List<Table> tableList = ReverseUtil.readTable(connection, schema);
			for(Table table : tableList) {
				String tabName = table.getName();
				ReverseContext.allTables.put(tabName, table);
				List<Column> columns = ReverseUtil.readColumn(connection, tabName, schema);

				for (Column column : columns) {
					ReverseContext.allTables.get(tabName).addColumn(column);
				}
			}
			
			List<Table> tables = new ArrayList<Table>();
			for (Entry<String, Table> map : ReverseContext.allTables.entrySet()) {
				if (map.getKey().toUpperCase(Locale.getDefault()).startsWith("BIN$")) {// 排除oracle垃圾表
					continue;
				}
				tables.add(map.getValue());
			}

			return tables;
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	
	/**
	 * 查询schema下的所有View名
	 * @param connection
	 * @param schema
	 * @return
	 * @throws Exception 
	 */
	public List<View> readViews(Connection connection, String schema) throws Exception{
		List<View> views = new ArrayList<View>();
		ResultSet rs = ReverseUtil.getSchemaViews(connection, schema);
		
		while(rs.next()){
			View view = new View(rs.getString("TABLE_NAME"));
			views.add(view);
			ReverseContext.allViews.put(view.getName(), view);
		}
		rs.close();
		return views;
	}
	
	/**
	 * 查询某表下的所有Column名集合
	 * @param connection
	 * @param schema
	 * @param tableName
	 * @return
	 */
	public void readColumns(Object obj){
		ResultSet rs =null;
		try {
			if(obj instanceof Table){
				rs = ReverseUtil.getColumns(ReverseContext.wizard.getConnection(), ReverseContext.info.getUserName(), ((Table)obj).getName());
				while(rs.next()){
					Column column = new Column(rs.getString("COLUMN_NAME"));
					column.setComment(rs.getString("REMARKS"));
					column.setSqlType(rs.getString("TYPE_NAME"));
				    column.setLength(Integer.valueOf(rs.getString("COLUMN_SIZE")));
				    String scale = rs.getString("DECIMAL_DIGITS");
				    if(scale!=null){
				    	column.setScale(Integer.valueOf(scale));
				    }
				    String isNullable = rs.getString("IS_NULLABLE");
				    column.setNullable("YES".equals(isNullable)?true:false);
					((Table)obj).addColumn(column);
				}
			}
			
			if(obj instanceof View){
				rs = ReverseUtil.getColumns(ReverseContext.wizard.getConnection(), ReverseContext.info.getUserName(), ((View)obj).getName());
				while(rs.next()){
					Column column = new Column(rs.getString("COLUMN_NAME"));
					column.setComment(rs.getString("REMARKS"));
					((View)obj).addColumn(column);
				}
			}
			
		} catch (SQLException e) {
			throw new RuntimeException("获取字段错误",e);
		}finally{
			try {
				if(rs!=null){
					rs.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 读取表主外键约束
	 * @param connection
	 * @param schema
	 * @param table
	 */
	public boolean readConstraints(Table table) {
		try {
			readPrimaryKey(ReverseContext.wizard.getConnection().getMetaData(), ReverseContext.info.getUserName(), table) ;
		} catch (Exception e) {
			return false;
		}
		
		try {
			readForeignKey(ReverseContext.wizard.getConnection(), ReverseContext.info.getUserName(), table);
		} catch (SQLException e) {
			return false;
		}
		return true;
	}
	
	/**
	 * 读取某用户下的所有sequence名
	 * @param userName
	 * @throws SQLException 
	 */
	public List<String> readSequence(String userName) throws SQLException {
		List<String> seqNames = new ArrayList<String>();
		String sql = ReverseContants.Oracle_SQL.SQL_ORACLE_SEQUENCE();
		PreparedStatement pst = ReverseContext.wizard.getConnection().prepareStatement(sql);
		pst.setString(1, userName.toLowerCase(Locale.getDefault()));
		ResultSet rs = pst.executeQuery(sql);
		while(rs.next()){
			seqNames.add(rs.getString("SEQUENCE_NAME"));
		}
		pst.close();
		rs.close();
		return seqNames;
	}
	
	/**
	 * 组装表信息到Table对象中
	 * @param rs
	 * @throws Exception 
	 */
	@SuppressWarnings("unused")
	private void readTable(Connection connection, String schema,ResultSet rs) throws Exception{
		
		Map<String,String> tablemap = new HashMap<String, String>();
		getTableMap(rs,tablemap);
		
		for(Iterator<String> iterator = tablemap.keySet().iterator();iterator.hasNext();){
			Table table = new Table(iterator.next());
			table.setComment(tablemap.get(table.getName()));
			tableList.add(table);
			ReverseContext.allTables.put(table.getName(), table);
		}
	}
	
	//绑定ForeignKey信息
	public void readForeignKey(Connection connection, String schema, Table table) throws SQLException {
		
		ResultSet rs = ReverseUtil.getForeignKey(connection, table); // 读取表外键信息
		ForeignKey fk = null;
		while (rs.next()) {
			String fkName = rs.getString("FK_NAME");// 外键名
			String pkTableName = rs.getString("PKTABLE_NAME"); // 获取引用表名

			fk = new ForeignKey();
			fk.setName(fkName);
			fk.setTable(table);
			fk.setReferencedTable(ReverseContext.allTables.get(pkTableName));

			String fkColumnName = rs.getString("FKCOLUMN_NAME"); // 获取外键字段名
			String pkColumnName = rs.getString("PKCOLUMN_NAME");// 获取引用字段

			fk.addColumn(table.getColumn(fkColumnName));
			fk.setReferencedColumn(ReverseContext.allTables.get(rs.getString("PKTABLE_NAME")).getColumn(pkColumnName));
			
			table.addForeignKey(fk);
		}
		ReverseUtil.closeResultSet(rs);

		/*ResultSet fkSet1 = ReverseUtil.getForeignKey(connection, schema, table.getName());
		while (fkSet1.next()) {
			ForeignKey fk = table.getForeignKeys().get(fkSet1.getString("FK_NAME"));
			String fkColumnName = fkSet1.getString("FKCOLUMN_NAME"); // 获取外键字段名
			String pkColumnName = fkSet1.getString("PKCOLUMN_NAME");// 获取引用字段

			fk.addColumn(table.getColumn(fkColumnName));
			fk.setReferencedColumn(ReverseContext.allTables.get(fkSet1.getString("PKTABLE_NAME")).getColumn(pkColumnName));
		}
		fkSet1.close();*/
		 

		/*if(table.getName().equals("CUSTOMER")) {
			System.out.println();
		}
		
		String sql = ReverseContants.Oracle_SQL.SQL_ORACLE_FK_INFO();
		PreparedStatement pst = ReverseContext.wizard.getConnection().prepareStatement(sql);
		pst.setString(1, table.getName());
		ResultSet rs1 = pst.executeQuery();
		while (rs1.next()) {
			ForeignKey fk = new ForeignKey();
			String r_constraintName = rs1.getString("R_CONSTRAINT_NAME");
			String constraintName = rs1.getString("CONSTRAINT_NAME");
			fk.setName(constraintName);
			fk.setTable(table);

			String sql2 = ReverseContants.Oracle_SQL.SQL_ORACLE_FKCOLUMNS_INFO();
			PreparedStatement pst2 = ReverseContext.wizard.getConnection().prepareStatement(sql2);
			pst2.setString(1, r_constraintName);
			ResultSet rs2 = pst2.executeQuery();
			while (rs2.next()) {
				Table rTable = ReverseContext.allTables.get(rs2.getString("table_name"));
				fk.setReferencedTable(rTable);
				fk.setReferencedColumn(rTable.getColumn(rs2.getString("column_name")));
			}
			rs2.close();
			pst2.close();

			String sql3 = ReverseContants.Oracle_SQL.SQL_ORACLE_FKCOLUMNS_INFO();
			PreparedStatement pst3 = ReverseContext.wizard.getConnection().prepareStatement(sql3);
			pst3.setString(1, constraintName);
			ResultSet rs3 = pst3.executeQuery();
			while (rs3.next()) {
				fk.addColumn(table.getColumn(rs3.getString("COLUMN_NAME")));
			}
			rs3.close();
			pst3.close();

			table.addForeignKey(fk);
		}
		rs1.close();
		pst.close();*/
	}

	/**
	 * 组装主键信息
	 * @param connection
	 * @param schema
	 * @param table
	 * @throws Exception
	 */
	public void readPrimaryKey(DatabaseMetaData dbmd, String schema, Table table) throws Exception {
		
		/**
		 * 获取当前表的主键信息：主键名称、列名集合
		 * 设置到当前表中 
		 * */
		ResultSet psResultSet = ReverseUtil.getPrimaryKey(dbmd, table);
		PrimaryKey pk = null;
		if (psResultSet.next()) {
			String pkName = psResultSet.getString("PK_NAME");
			pk = new PrimaryKey(pkName);
			
		}
		
		ReverseUtil.closeResultSet(psResultSet);
		
		if(pk == null) {
			return ;
		}
		
		
		psResultSet = ReverseUtil.getPrimaryKey(dbmd, table);
		while(psResultSet.next()) {
			String colName = psResultSet.getString("COLUMN_NAME");
			pk.addColumn(table.getColumn(colName));
		}

		table.setPrimaryKey(pk);
		ReverseUtil.closeResultSet(psResultSet);
		//----------------------------------------------
		
		/**
		 * 获取当前表的外检表信息
		 * */
		ResultSet childTableResultSet = ReverseUtil.getChildrenTable(dbmd, table);
		while(childTableResultSet.next()){
			String childTableName = childTableResultSet.getString("FKTABLE_NAME");
			Table childTable = ReverseContext.allTables.get(childTableName);//如果当前从表不是当前用户下的表，可能是null
			if(childTable != null) {
				pk.getForeignKeyTables().put(childTableName, childTable);
			}
			pk.getForeignKeyTables().put(childTableName, childTable);
		}
		ReverseUtil.closeResultSet(childTableResultSet);
		//----------------------------------------------
		
		/*String sql = ReverseContants.Oracle_SQL.SQL_ORACLE_TABLEPK_COLS();
		PrimaryKey pk =null;
		PreparedStatement ps = ReverseContext.wizard.getConnection().prepareStatement(sql);
		ps.setString(1, table.getName());
		
		ResultSet rs = ps.executeQuery();
		while(rs.next()){
			if(pk == null){
				pk = new PrimaryKey(table.getName()+"_pk");
				table.setPrimaryKey(pk);
			}
			pk.addColumn(table.getColumn(rs.getString("COLNAME")));
		}
		rs.close();
		ps.close();
		
		ResultSet childrenTableSet = ReverseUtil.getChildrenTable(connection, schema, table.getName());
		while(childrenTableSet.next()){
			String childTableName = childrenTableSet.getString("FKTABLE_NAME");
			Table childTable = ReverseContext.allTables.get(childTableName);
			pk.getForeignKeyTables().put(childTableName, childTable);
		}
		childrenTableSet.close();
		String sqlZB = ReverseContants.Oracle_SQL.SQL_ORACLE_ZIBIAO();
		PreparedStatement pst = connection.prepareStatement(sqlZB);
		pst.setString(1, table.getName());
		ResultSet rlts = pst.executeQuery();
		while(rlts.next()){
			String childTableName = rlts.getString("FKTABLE_NAME");
			Table childTable = ReverseContext.allTables.get(childTableName);
			pk.getForeignKeyTables().put(childTableName, childTable);
		}
		rlts.close();
		pst.close();*/
	}

	/**
	 * 组装column信息
	 * @param tableName
	 * @throws SQLException 
	 */
	public void readColumn(Table table,Set<String> colSet) throws SQLException{
		ResultSet rs = null;
		try {
			rs = ReverseUtil.getColumns(ReverseContext.wizard.getConnection(), ReverseContext.info.getUserName(), table.getName());
			while(rs.next()){
				String colName = rs.getString("COLUMN_NAME");
			    String sqlType = rs.getString("TYPE_NAME");
			    String length = rs.getString("COLUMN_SIZE");
			    String scale = rs.getString("DECIMAL_DIGITS");
			    String isNullable = rs.getString("IS_NULLABLE");
			    String comment = rs.getString("REMARKS");
			    String defaultValue = null;
			    
			    if("oracle".equalsIgnoreCase(ReverseContext.wizard.getConnection().getMetaData().getDatabaseProductName())){
			    	defaultValue = getOracleColumnDefaultValue(table.getName(),colName);
			    }else{
			    	defaultValue = rs.getString("COLUMN_DEF");
			    }
			    
			    Column column = new Column(colName);
			    column.setSqlType(sqlType);
			    column.setLength(Integer.valueOf(length));
			    column.setNullable("YES".equals(isNullable)?true:false);
			    column.setDefaultValue(defaultValue);
			    column.setComment(comment);
			    if(scale!=null){
			    	column.setScale(Integer.valueOf(scale));
			    }
			    
			    //判断该列是否具有unique约束
			    if(colSet!=null && colSet.size()>0 && colSet.contains(colName)){
			    	column.setUnique(true);
			    }
			    table.addColumn(column);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			ReverseUtil.closeResultSet(rs);
		}
	}
	
	public void readColumn(Table table, Set<String> colSet, DatabaseMetaData metaData) throws SQLException{
		ResultSet rs = null;
		try {
			rs = metaData.getColumns(null, ReverseContext.info.getUserName(), table.getName(), null);
			while(rs.next()){
				String colName = rs.getString("COLUMN_NAME");
				String sqlType = rs.getString("TYPE_NAME");
				String length = rs.getString("COLUMN_SIZE");
				String scale = rs.getString("DECIMAL_DIGITS");
				String isNullable = rs.getString("IS_NULLABLE");
				String comment = rs.getString("REMARKS");
				String defaultValue = null;
				
				if("oracle".equalsIgnoreCase(ReverseContext.wizard.getConnection().getMetaData().getDatabaseProductName())){
					defaultValue = getOracleColumnDefaultValue(table.getName(),colName);
				}else{
					defaultValue = rs.getString("COLUMN_DEF");
				}
				
				Column column = new Column(colName);
				column.setSqlType(sqlType);
				column.setLength(Integer.valueOf(length));
				column.setNullable("YES".equals(isNullable)?true:false);
				column.setDefaultValue(defaultValue);
				column.setComment(comment);
				if(scale!=null){
					column.setScale(Integer.valueOf(scale));
				}
				
				//判断该列是否具有unique约束
				if(colSet!=null && colSet.size()>0 && colSet.contains(colName)){
					column.setUnique(true);
				}
				table.addColumn(column);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			ReverseUtil.closeResultSet(rs);
		}
	}
	
	//获取schema下所有非垃圾表的名称与注释
	private void getTableMap(ResultSet rs,Map<String, String> tableMap) {
		try {
			while(rs.next()) {
				String tabName = rs.getString("TABLE_NAME");
				String comment = rs.getString("REMARKS");
				if(!tabName.startsWith("BIN$")){
					tableMap.put(tabName,comment);
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException("", e);
		}
	}
	
	//获取oracle字段默认值
	public String getOracleColumnDefaultValue(String tableName ,String colName) throws SQLException{
		
		String sql = ReverseContants.Oracle_SQL.SQL_ORACLE_DEFAULT();
		PreparedStatement pst = ReverseContext.wizard.getConnection().prepareStatement(sql);
		pst.setString(1, tableName);
		pst.setString(2, colName);
		ResultSet rs = pst.executeQuery();
		
		String defaultValue = null;
		while(rs.next()){
			defaultValue = rs.getString("DATA_DEFAULT");
		}
		rs.close();
		pst.close();
		
		return defaultValue;
	}

	/**
	 * 获取唯一约束字段集合
	 * @param name
	 * @throws SQLException 
	 */
	public Set<String> getUniqueCols(Table table,DatabaseMetaData metaData) throws SQLException {
		Set<String> colSet = new HashSet<String>();
		/*String dbType = ReverseContext.info.getDbType();
		String sql = ReverseContants.Oracle_SQL.getUniqueColumnsSQL(dbType);
		PreparedStatement pst = ReverseContext.wizard.getConnection().prepareStatement(sql);
		pst.setString(1, table.getName());
		
		Set<String> colSet = new HashSet<String>();
		ResultSet rs = pst.executeQuery();
		while (rs.next()) {
			String colName = rs.getString("column_name");
			colSet.add(colName);
		}
		rs.close();
		pst.close();*/
		
		
		ResultSet indexRs= metaData.getIndexInfo(null, table.getSchemaName(), table.getName(), false, true);
		// maps column names to the index name.       
		Map<String , List<String>> indexs = new HashMap(); 
		//一个索引可能包含多个列，此处只查询一个索引下包含一个列，并且"NON_UNIQUE"为false的
		while (indexRs.next()) { 
			String columnName = indexRs.getString("COLUMN_NAME"); 
			String indexName = indexRs.getString("INDEX_NAME");                              
			boolean nonUnique = indexRs.getBoolean("NON_UNIQUE");  
			if (!nonUnique && columnName != null && indexName != null) { 
				//如果索引库中已创建该索引，则直接添加列名；如果未创建该索引，则先创建list，再添加列名
				if(indexs.get(indexName)!=null){
					indexs.get(indexName).add(columnName);
				}else{
					List listColumns = new ArrayList();
					listColumns.add(columnName);
					indexs.put(indexName, listColumns);
				}
			}
		}
		//从索引库中查找只有一个列的索引
		Iterator it = indexs.keySet().iterator();
		while(it.hasNext()){
			String indexName = (String)(it.next());
			if(indexs.get(indexName).size()==1){
				String columnName = indexs.get(indexName).get(0);
				colSet.add(columnName);
			}
		}
		//测试
		
		return colSet;
	}
	
	public List<Table> getTableList() {
		return tableList;
	}

	public void setTableList(List<Table> tableList) {
		this.tableList = tableList;
	}

}
