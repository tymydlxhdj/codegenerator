package com.mqfdy.code.reverse.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Driver;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.preference.IPreferenceStore;
import org.osgi.framework.Bundle;

import com.mqfdy.code.designer.constant.IProjectConstant;
import com.mqfdy.code.designer.editor.BusinessModelEditorPlugin;
import com.mqfdy.code.designer.editor.utils.Logger;
import com.mqfdy.code.model.BusinessClass;
import com.mqfdy.code.model.BusinessObjectModel;
import com.mqfdy.code.model.ModelPackage;
import com.mqfdy.code.model.VersionInfo;
import com.mqfdy.code.model.graph.Diagram;
import com.mqfdy.code.model.graph.DiagramStyle;
import com.mqfdy.code.model.utils.DBType;
import com.mqfdy.code.model.utils.StringUtil;
import com.mqfdy.code.reverse.DataSourceInfo;
import com.mqfdy.code.reverse.ReverseContext;
import com.mqfdy.code.reverse.ReverseException;
import com.mqfdy.code.reverse.jdbc.JDBCReader;
import com.mqfdy.code.reverse.mappings.Column;
import com.mqfdy.code.reverse.mappings.ForeignKey;
import com.mqfdy.code.reverse.mappings.PrimaryKey;
import com.mqfdy.code.reverse.mappings.Table;
import com.mqfdy.code.reverse.views.beans.TreeNode;

/**
 * @author mqfdy
 *
 */
public class ReverseUtil {

	private static final String diagram_bgc_color = "white";

	public static List<String> tableNames = new ArrayList<String>();
	
	public static String PLUGIN_DRIVER = "com.mqfdy.code.commonlib";
	
	private static final String KEY_DRIVER_CLASS_NAME = "driverClassName";
	private static final String KEY_URL = "url";
	private static final String KEY_USERNAME = "username";
	private static final String KEY_PPP = "pwd";
	
	private static final String MICRO_KEY_DRIVER_CLASS_NAME = "spring.datasource.driver-class-name";
	private static final String MICRO_KEY_URL = "spring.datasource.url";
	private static final String MICRO_KEY_USERNAME = "spring.datasource.username";
	private static final String MICRO_KEY_PPP = "spring.datasource.password";
	
	/**
	 * 根据配置项获取数据库连接
	 * @param properties
	 * @return
	 * @throws Exception
	 */
	public static Connection createConnection(DataSourceInfo dsi) throws Exception{
		
//		String driver_url = dsi.getDriverUrl();
		if(dsi == null){
			return null;
		}
		String url = dsi.getUrl();
		parcelDriverClassName(dsi);
		String user = dsi.getUserName();
		String pwd = dsi.getPwd();
		
		ClassLoader loader= createClassLoader(getDriverPath(dsi.getDbType()));
		
		Properties prop = new Properties();
		prop.put("remarksReporting","true");
		if(!StringUtil.isEmpty(user)){
			prop.setProperty("user", user);
		}
		if(!StringUtil.isEmpty(pwd)){
			prop.setProperty("password", pwd);
		}
		try {
			Driver jdbcDriver = (Driver) loader.loadClass(dsi.getDriverClass()).newInstance();
			return jdbcDriver.connect(url, prop);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage(), e);
		} 
	}
	
	/**
	 * 关闭连接
	 * @param connection
	 */
	public static void closeConnection(Connection connection){
		try {
			if(connection != null) {
				connection.close();
			}
		} catch (SQLException e) {
			throw new RuntimeException("连接不能正常关闭",e);
		}
	}
	
	/*public static void readTableInfo(Connection connection,String schema) throws Exception{
		readTable(connection,schema);
		
		String sql = ReverseContants.Oracle_SQL.SQL_ORACLE_COLUMNS_INFO();
		PreparedStatement pst = connection.prepareStatement(sql);
		for(String tabName : tableNames){
			readColumn(connection,tabName,schema,pst);
		}
		pst.close();
	}*/
	
	//读取列信息
	public static List<Column> readColumn(Connection connection, String tableName, String schema) throws Exception {

		List<Column> columns = new ArrayList<Column>();
		DatabaseMetaData dbmd = connection.getMetaData();
		ResultSet rs = dbmd.getColumns(null, null, tableName, "%");
		while (rs.next()) {
			
			
			Column column = new Column(rs.getString("COLUMN_NAME"));
			column.setComment(rs.getString("REMARKS"));
			column.setSqlType(rs.getString("TYPE_NAME"));
			String length=rs.getString("COLUMN_SIZE");
			//MYSQL数据库读取出来的日期和文本类型字段长度为null，进行赋值处理
			if(length==null&&("DATE".equals(column.getSqlType())||"DATETIME".equals(column.getSqlType())
					||"TIME".equals(column.getSqlType()))){
				length="10";
			}else if(length==null&&"TEXT".equals(column.getSqlType())){
				length="256";
			}else if("json".equalsIgnoreCase(column.getSqlType())){
				length="99999";
			}else if(length==null&&"BIT".equals(column.getSqlType())){
				length="0";
			}
			column.setLength(Integer.valueOf(length));
			String scale = rs.getString("DECIMAL_DIGITS");
			if (scale != null) {
				column.setScale(Integer.valueOf(scale));
			}
			String isNullable = rs.getString("IS_NULLABLE");
			column.setNullable("YES".equals(isNullable) ? true : false);
			column.setDefaultValue(rs.getString("COLUMN_DEF"));
			columns.add(column);
		}

		ReverseUtil.closeResultSet(rs);
		return columns;
	}

	public static List<Table> readTable(Connection connection,String schema) throws Exception{
		List<Table> tableList = new ArrayList<Table>();
		DatabaseMetaData dbmd = connection.getMetaData();
		ResultSet rs = null;
		if(schema!=null){
			rs = dbmd.getTables(null, schema.toUpperCase(Locale.getDefault()), "%", new String[] { "TABLE" });
		}else{
			rs = dbmd.getTables(null, null, "%", new String[] { "TABLE" });
		}
		
		Table table = null;
		while(rs.next()) {
			String tabName = rs.getString("TABLE_NAME");
			if(!tabName.startsWith("##") && !tabName.contains("$") && !tabName.contains(".")){
				String comment = rs.getString("REMARKS");
				String schemaName= rs.getString("TABLE_SCHEM");
				
				table = new Table(tabName);
				table.setComment(comment);
				table.setSchemaName(schemaName);
				tableList.add(table);
			}

		}
		closeResultSet(rs);
		return tableList;
	}
	
	/**
	 * 查找某用户下的所有表
	 * @param schema 用户
	 * @return
	 */
	public static ResultSet getSchemaTables(Connection connection ,String schema) throws Exception{
		DatabaseMetaData dbmd = connection.getMetaData();
		ResultSet tableSet = dbmd.getTables(null, schema.toUpperCase(Locale.getDefault()),"%",new String[]{"TABLE"});
		
		return tableSet;
	}
	
	
	
	/**
	 * 查找某用户下的所有视图
	 * @param schema 用户
	 * @return
	 */
	public static ResultSet getSchemaViews(Connection connection ,String schema) throws Exception{
		DatabaseMetaData dbmd = connection.getMetaData();
		ResultSet viewSet = dbmd.getTables(null, schema.toUpperCase(Locale.getDefault()),"%",new String[]{"VIEW"});
		
		return viewSet;
	}

	/**
	 * 获取表下所有列
	 * @param connection
	 * @param schema
	 * @param tabName
	 * @return
	 * @throws SQLException
	 */
	public static ResultSet getColumns(Connection connection,String schema,String tabName) throws SQLException{
		DatabaseMetaData dbmd = connection.getMetaData();
		ResultSet crs = dbmd.getColumns(null, schema.toUpperCase(Locale.getDefault()), tabName, null);
		
		return crs;
	}
	
	/**
	 * 获取主键ResultSet
	 * @param connection
	 * @param schema
	 * @param tabName
	 * @return
	 * @throws SQLException
	 */
	public static ResultSet getPrimaryKey(DatabaseMetaData dbmd, Table table) throws SQLException {
		//DatabaseMetaData dbmd = connection.getMetaData();
		if(dbmd==null){
			return null;
		}
		ResultSet pkSet = dbmd.getPrimaryKeys(null, table.getSchemaName(), table.getName());
		return pkSet;
	}
	
	/**
	 * 获取引用本表主键的子表
	 * @param connection
	 * @param schema
	 * @param tabName
	 * @return
	 * @throws SQLException
	 */
	public static ResultSet getChildrenTable(DatabaseMetaData dbmd, Table table) throws SQLException{
		//DatabaseMetaData dbmd = connection.getMetaData();
		if(dbmd==null){
			return null;
		}
		ResultSet pkSet = dbmd.getExportedKeys(null, table.getSchemaName(), table.getName());
		return pkSet;
	}
	
	/**
	 * 获取主键ResultSet
	 * @param connection
	 * @param schema
	 * @param tabName
	 * @return
	 * @throws SQLException
	 */
	public static ResultSet getForeignKey(Connection connection, Table table) throws SQLException{
		DatabaseMetaData dbmd = connection.getMetaData();
		ResultSet fkSet = dbmd.getImportedKeys(null, table.getSchemaName(), table.getName());  
		return fkSet;
	}
	
	/**
	 * 获取ClassLoader对象，用于加载本地数据库驱动
	 * @param driver_url
	 * @return
	 * @throws Exception
	 */
	private static ClassLoader createClassLoader(String driver_url){

		String[] jarStrings = new String[]{driver_url};
		URL[] jars = new URL[jarStrings.length];
		for (int index = 0, count = jars.length; index < count; ++index) {
			try {
				jars[index] = new File(jarStrings[index]).toURL();
			}
			catch (MalformedURLException e) {
				throw new RuntimeException("驱动加载不成功", e); //$NON-NLS-1$
			}
		}
		return URLClassLoader.newInstance(jars);
	}
	
	/**
	 * 修改选中节点
	 * @param dsName
	 * @return
	 * @throws DocumentException 
	 * @throws IOException 
	 */
	/*public static void updateSelectDs(String dsName) throws DocumentException, IOException {
		//读取datasource.xml文件
//		File dataSourcePath = new File(getStoragePath());//首先判断该storage.xml文件是否存在
		
		SAXReader saxReader = new SAXReader();
		Document document = saxReader.read(getStoragePath());
		Element root = document.getRootElement();
		List<Element> nodes = root.elements("DataSource");
		for(Iterator<Element> iterator = nodes.iterator();iterator.hasNext();){
			Element dataSourceEle = iterator.next();
			if(dataSourceEle.attribute("name").getValue().equals(dsName)){
				Element selectEle = dataSourceEle.element(ReverseContants.DataSource.DB_SELECT);
				selectEle.setText("1");
			}else{
				Element selectEle = dataSourceEle.element(ReverseContants.DataSource.DB_SELECT);
				selectEle.setText("0");
			}
		}
		
		OutputFormat format = OutputFormat.createPrettyPrint();
        XMLWriter writer = new XMLWriter(new FileOutputStream(getStoragePath()),format);
        
        writer.write(document);
        writer.close();
	}*/	
	
	/**
	 * 将数据源信息写入dataSource.xml文件中
	 * @param properties
	 * @throws IOException 
	 * @throws DocumentException 
	 */
	@SuppressWarnings("unchecked")
	public static void writeDataSource(DataSourceInfo dsi) throws DocumentException{
		
		Document document ;
		Element root;
		File xmlFile = new File(getStoragePath());
		boolean needsWrite = true;
		if(xmlFile.exists()){//如果已经存在
			SAXReader saxReader = new SAXReader();
			document = saxReader.read(getStoragePath());
			root = document.getRootElement();
			//判断是否存在重名数据源，如有重名将不再写入
			List<Element> nodes = root.elements("DataSource");
			for(Iterator<Element> iterator = nodes.iterator();iterator.hasNext();){
				Element dataSource = iterator.next();
				String dsName = dataSource.attributeValue("name");
				if(dsName.equals(dsi.getDataSourceName())){
					needsWrite = false;
					break;
				}
			}
		}else{
			document = DocumentHelper.createDocument();
			root = document.addElement("DataSources");
		}
        if(needsWrite){
        	if(dsi.getDbType().equals(DBType.Oracle.getDbType())){
        		dsi.setDriverClass("oracle.jdbc.driver.OracleDriver");
        	}
        	Element dsElement = root.addElement("DataSource").addAttribute("name",dsi.getDataSourceName());
            dsElement.addElement(ReverseContants.DataSource.DRIVER_CLASS).addText(dsi.getDriverClass());
            dsElement.addElement(ReverseContants.DataSource.SID).addText(dsi.getSid());
            dsElement.addElement(ReverseContants.DataSource.USER).addText(dsi.getUserName());
            dsElement.addElement(ReverseContants.DataSource.PPP).addText(dsi.getPwd());
            dsElement.addElement(ReverseContants.DataSource.URL).addText(dsi.getUrl());
            dsElement.addElement(ReverseContants.DataSource.DB_TYPE).addText(dsi.getDbType());
            dsElement.addElement(ReverseContants.DataSource.DB_SELECT).addText(dsi.getIsSelect());
            dsElement.addElement(ReverseContants.DataSource.DB_IP).addText(dsi.getIp());
            dsElement.addElement(ReverseContants.DataSource.DB_PORT).addText(dsi.getPort());

            OutputFormat format = OutputFormat.createPrettyPrint();
            FileOutputStream fileOutputStream = null;
            XMLWriter writer = null;
			try {
				fileOutputStream = new FileOutputStream(getStoragePath());
				writer = new XMLWriter(fileOutputStream,format);
				writer.write(document);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if(fileOutputStream != null){
	            	try{
	            		fileOutputStream.close();
	            	}catch(Exception e){
	            		Logger.log(e.getMessage());
	            	}
	            }
	            if(writer!=null){
	            	try{
	            		writer.close();
	            	}catch(Exception e){
	            		Logger.log(e.getMessage());
	            	}
	            }
			}
            
            
            
            
        }
	}

	/**
	 * 读出历史数据源列表
	 * @param workspacePath
	 * @throws DocumentException 
	 */
//	@SuppressWarnings("unchecked")
	/*public static List<DataSourceInfo> readDataSource() throws DocumentException {
		
		File dataSourcePath = new File(getStoragePath());//首先判断该storage.xml文件是否存在
		if(!dataSourcePath.exists()){
			return new ArrayList<DataSourceInfo>();
		}
		List<DataSourceInfo> dsis = new ArrayList<DataSourceInfo>();
		
		SAXReader saxReader = new SAXReader();
		Document document = saxReader.read(getStoragePath());
		Element root = document.getRootElement();
		List<Element> nodes = root.elements("DataSource");
		for(Iterator<Element> iterator = nodes.iterator();iterator.hasNext();){
			Element dataSource = iterator.next();
			String dsName = dataSource.attributeValue("name");
			String driverClass = dataSource.elementTextTrim(ReverseContants.DataSource.DRIVER_CLASS);
			String sid = dataSource.elementTextTrim(ReverseContants.DataSource.SID);
			String user = dataSource.elementTextTrim(ReverseContants.DataSource.USER);
			String pwd = dataSource.elementTextTrim(ReverseContants.DataSource.PWD);
			String url = dataSource.elementTextTrim(ReverseContants.DataSource.URL);
			String dbType = dataSource.elementTextTrim(ReverseContants.DataSource.DB_TYPE);
			String isSelect = dataSource.elementTextTrim(ReverseContants.DataSource.DB_SELECT);
			String ip = dataSource.elementTextTrim(ReverseContants.DataSource.DB_IP);
			String port = dataSource.elementTextTrim(ReverseContants.DataSource.DB_PORT);
			
			DataSourceInfo dsi = new DataSourceInfo();
			dsi.setSid(sid);
			dsi.setDataSourceName(dsName);
			dsi.setDriverClass(driverClass);
			dsi.setPwd(pwd);
			dsi.setUrl(url);
			dsi.setUserName(user);
			dsi.setDbType(dbType);
			dsi.setIsSelect(isSelect);
			dsi.setIp(ip);
			dsi.setPort(port);
			dsis.add(dsi);
			
		}
		return dsis;
	}*/
	
	public static String getURL(String dbType, String serverIP, String dbId, String port) {
		
		if(dbType == null || dbType.length() == 0) {
			return "";
		}
		String connectionURL = "";
		String type = dbType.toLowerCase(Locale.getDefault());
		if(DBType.Oracle.getDbType().toLowerCase(Locale.getDefault()).equals(type)) {
			StringBuffer buffer = new StringBuffer();
			buffer.append("jdbc:");
			buffer.append(DBType.Oracle.getDbType().toLowerCase(Locale.getDefault()));
			buffer.append(":thin:@");
			buffer.append(serverIP);
			buffer.append(":");
			buffer.append(port);
			buffer.append(":");
			buffer.append(dbId);
			connectionURL = buffer.toString();
		} else if(DBType.MySQL.getDbType().toLowerCase(Locale.getDefault()).equals(type)) {			
			StringBuffer buffer = new StringBuffer();
			buffer.append("jdbc:mysql://");
			buffer.append(serverIP);
			buffer.append(":");
			buffer.append(port);
			buffer.append("/");
			buffer.append(dbId);
			connectionURL = buffer.toString();
		}else if(DBType.MsSQL.getDbType().toLowerCase(Locale.getDefault()).equals(type)) {			
			StringBuffer buffer = new StringBuffer();
			buffer.append("jdbc:sqlserver://");
			buffer.append(serverIP);
			buffer.append(":");
			buffer.append(port);
			buffer.append(";databaseName=");
			buffer.append(dbId);
			connectionURL = buffer.toString();
		}
		else if(DBType.MsSQL.getDbType().toLowerCase(Locale.getDefault()).equals(type)) {
			StringBuffer buffer = new StringBuffer();
			buffer.append("jdbc:dm://");
			buffer.append(serverIP);
			buffer.append(":");
			buffer.append(port);
			buffer.append("/");
			buffer.append(dbId);
			connectionURL = buffer.toString();
		}
		else if(DBType.POSTGRESQL.getDbType().toLowerCase(Locale.getDefault()).equals(type)) {
			StringBuffer buffer = new StringBuffer();
			buffer.append("jdbc:postgresql://");
			buffer.append(serverIP);
			buffer.append(":");
			buffer.append(port);
			buffer.append("/");
			buffer.append(dbId);
			connectionURL = buffer.toString();
		}
//		else if(DBType.DB2.getDbType().toLowerCase(Locale.getDefault()).equals(type)) {
//			StringBuffer buffer = new StringBuffer();
//			buffer.append("jdbc:db2://");
//			buffer.append(serverIP);
//			buffer.append(":");
//			buffer.append(port);
//			buffer.append("/");
//			buffer.append(dbId);
//			connectionURL = buffer.toString();
//		}
//		else if(DBType.Sybase.getDbType().toLowerCase(Locale.getDefault()).equals(type)) {
//			StringBuffer buffer = new StringBuffer();
//			buffer.append("jdbc:sybase:Tds:"); 
//			buffer.append(serverIP);
//			buffer.append(":");
//			buffer.append(port);
//			buffer.append("/");
//			buffer.append(dbId);
//			connectionURL = buffer.toString();
//		}
		return connectionURL;
	}
	
	/**
	 * 获取工作弓箭路径
	 * @return
	 */
	public static String getWorkspacePath(){
		IPath path = Platform.getLocation();
		String workspacePath = path.makeAbsolute().toFile().getAbsolutePath();
		
		return workspacePath+File.separator;
	}
	
	/**
	 * 返回存储
	 * @return
	 */
	public static String getStoragePath(){
		return getWorkspacePath()+ReverseContants.DataSource.STORAGE;
	}
	
	//根据数据库类型获取uap安装目录下的驱动包路径
	public static String getDriverPath(String dbType) {
		StringBuffer jar_path = new StringBuffer();

		Bundle bundle = Platform.getBundle(PLUGIN_DRIVER);
		File bundleFile;
		try {
			bundleFile = FileLocator.getBundleFile(bundle);
			jar_path.append(bundleFile.getPath());
			jar_path.append(File.separator);
			jar_path.append(ReverseContants.DataSource.DRIVER_JAR_CHILD);
			jar_path.append(File.separator);
			if (dbType.equals(DBType.Oracle.getDbType())) {
				jar_path.append("ojdbc8.jar");
//			} else if (dbType.equals(DBType.DB2.getDbType())) {
//				jar_path.append("db2jcc.jar");
			} else if (dbType.equals(DBType.MySQL.getDbType())) {
				jar_path.append("mysql.jar");
			} else if (dbType.equals(DBType.MsSQL.getDbType())) {
				jar_path.append("sqljdbc4.jar");
			} else if (dbType.equals(DBType.POSTGRESQL.getDbType())) {
				jar_path.append("postgresql-9.3-1102.jdbc4.jar");
//			} else if (dbType.equals(DBType.Sybase.getDbType())) {
//				jar_path.append("jconn3.jar");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jar_path.toString();
	}
	

//	/**
//	 * 按照默认规则转换表名、字段名到业务实体名、属性名
//	 * @param tables
//	 * @return
//	 */
//	public static List<BusinessClass> reverseDb(List<Table> tables) {
//		List<BusinessClass> bcs = new ArrayList<BusinessClass>();
//		for(Table table : tables){
//			String tname = table.getName();
//			String[] splits = tname.split("_");
//			String bcName = "";
//			for (int i = 0; i < splits.length; i++) {
//				bcName = bcName + StringUtil.capitalize(splits[i].toLowerCase(Locale.getDefault()));
//			}
//			BusinessClass bc = new BusinessClass();
//			for(Iterator<String> iterator = table.getColumns().keySet().iterator();iterator.hasNext();){
//				String colName = iterator.next();
//				String[] colns =  colName.split("_");
//				String propName = "";
//				for (int i = 0; i < colns.length; i++) {
//					propName = propName + StringUtil.capitalize(colns[i].toLowerCase(Locale.getDefault()));
//				}	
//				Property property = new Property();
//				property.setParent(bc);
//				property.setName(propName);
//				bc.addProperty(property);
//				
//				ReverseContext.cpMap.put(table.getColumns().get(colName), property);
//			}
//			
//			bc.setName(bcName);
//			bcs.add(bc);
//			ReverseContext.tbMap.put(table, bc);
//		}
//		return bcs;
//	}
	
	/**
	 * 将column名转为属性名
	 * @param keySet
	 * @return
	 */
	public static List<String> reverseColumnsName(Set<String> colNames) {
		List<String> propertyNames = new ArrayList<String>();
		for(Iterator<String> iterator = colNames.iterator();iterator.hasNext();){
			String colName = iterator.next();
			String[] splits = colName.split("_");
			String propName = "";
			for (int i = 0; i < splits.length; i++) {
				propName = propName + StringUtil.capitalize(splits[i].toLowerCase(Locale.getDefault()));
			}
			propertyNames.add(propName);
		}
		return propertyNames;
	}

	/**
	 * 获取关联表
	 * @param selectedTable
	 * @return
	 */
	public static List<Table> getRelativeTables(Table selectedTable) {
		//得到其外键关联的表
		List<Table> relativeTables = new ArrayList<Table>();
		Map<String, ForeignKey> foreignKeys = selectedTable.getForeignKeys();
		for(Map.Entry<String, ForeignKey> m : foreignKeys.entrySet()){
			ForeignKey fk = m.getValue();
			relativeTables.add(fk.getReferencedTable());
		}
		//得到主键关联的表
		if(selectedTable.getPrimaryKey() != null){
			Map<String, Table> childrenTable = selectedTable.getPrimaryKey().getForeignKeyTables();
			for(Map.Entry<String, Table> m:childrenTable.entrySet()){
				Table table = m.getValue();
				relativeTables.add(table);
			}
		}
		return relativeTables;
	}
	
	/**
	 * 判断是否无主键表
	 * @param table
	 * @return
	 */
	public static boolean isNoPkTable(Table table){
		
		if(table.getPrimaryKey() == null){
			if(isManyToManyTable(table)){
				return false;
			}
			return true;
		}else {
			return false;
		}
	}
	
	/**
	 * 判断是否复合主键表
	 * @param table
	 * @return
	 */
	public static boolean isMultiPkTable(Table table){
		if(table.getPrimaryKey() != null && table.getPrimaryKey().getColumns().size()>1){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 有且只有两列，且都为外键列才可判断为中间表
	 * 判断是中间表的依据: 当前表无主键,并且两个外键,仅有两个字段
	 * @param table
	 */
	public static boolean isManyToManyTable(Table table){
		Map<String, Column> columns = table.getColumns();
		if(columns.isEmpty()){
			JDBCReader reader = new JDBCReader();
			reader.readColumns(table);
			reader.readConstraints(table);
		}
		PrimaryKey pk = table.getPrimaryKey();
		Map<String, ForeignKey> foreignKeys = table.getForeignKeys();
		
		if(foreignKeys.size()!=2){ 
			return false;
		}else if(columns.size()==2 && pk==null && foreignKeys.size()==2){
			return true;
		}
//		else if(pk.getColumns().size()==table.getColumns().size()-2 && foreignKeys.size()==2){
//			return true;
//		}
		return false;
	}
	
	//创建bom实例
	public static void createNewBom(String nameSpace,String modelName,String modelDisplayName){
		BusinessObjectModel bom = new BusinessObjectModel(modelName, modelDisplayName);
		ReverseContext.bom = bom;
		bom.setName(modelName);
		bom.setDisplayName(modelDisplayName);
		bom.setNameSpace(nameSpace);
		bom.setStereotype("3");
		
		//VersionInfo 对象
		VersionInfo vInfo = new VersionInfo();
		vInfo.setCreator(System.getProperty("user.name"));
		vInfo.setModifier(System.getProperty("user.name"));
		vInfo.setCreatedTime(new Date());
		vInfo.setChangedTime(new Date());
		vInfo.setDescription("This is a demo model.");
		vInfo.setVersionNumber("1.0");
		bom.setVersionInfo(vInfo);
		
		//ModelPackage
		ModelPackage modelPackage=new ModelPackage();
		modelPackage.setName(modelName);
		modelPackage.setDisplayName(modelDisplayName);
		modelPackage.setParent(bom);
		modelPackage.setStereotype("3");
		bom.addPackage(modelPackage);
		
		//Diagram
		Diagram diagram = new Diagram();
		diagram.setBelongPackage(modelPackage);
		diagram.setName(modelName);
		diagram.setDisplayName(modelDisplayName);
		diagram.setStereotype("3");
		bom.addDiagram(diagram);
		
		DiagramStyle diagramStyle = new DiagramStyle();
		diagramStyle.setBackGroundColor(diagram_bgc_color);
		diagramStyle.setGridStyle(false);
		diagramStyle.setZoomScale(100);
		diagram.setDefaultStyle(diagramStyle);
		
	}

	//拷贝map
	public static void copySepcialTables() {
		ReverseContext.handleTables.clear();
		for(Map.Entry<String, Table> selectedTables : ReverseContext.selectedTables.entrySet()){
			Table table = selectedTables.getValue();
			
			ReverseContext.handleTables.put(selectedTables.getKey(), table);
			
			/*if(isNoPkTable(table) || isMultiPkTable(table)){
				Table ttt = table.clone();
				ReverseContext.handleTables.put(selectedTables.getKey(), table.clone());
			}else{
				ReverseContext.handleTables.put(selectedTables.getKey(), table);
			}*/
		}
	}
	
	//给特鼠标创建一个主键列
	public static String createPkColumnName(Table specialTable){
		if(specialTable.getColumn("ID") == null){
			return "ID";
		}
		if(specialTable.getColumn("ID") == null){
			return specialTable.getName()+"_ID";
		}
		if(specialTable.getColumn(specialTable.getName()+"_ID") == null){
			return "PK_ID";
		}
		return "TABLE_ID";
	}

	/**
	 * 在选择已经存在om文件时，缓存om文件中的
	 */
	public static void getExitBcs() {
		
		ReverseContext.existBcs = new ArrayList<BusinessClass>(ReverseContext.bom.getBusinessClasses());
		
		for(BusinessClass bc : ReverseContext.existBcs){
			ReverseContext.bcIds.add(bc.getId());
		}
	}
	
	/**
	 * 获取当前节点的所有子节点
	 * @param currentNode
	 * @return
	 */
	public static List<TreeNode> getAllChildren(TreeNode currentNode) {
		List<TreeNode> resultList = new ArrayList<TreeNode>();
		
		//当前节点的子节点
		List<TreeNode> childList = currentNode.getChilds();
		//如果当前节点没有子节点，返回空.
		if(currentNode.getChilds() == null || currentNode.getChilds().size() == 0) {
			return new ArrayList<TreeNode>();
		}
		
		//遍历当前节点的所有子节点, 添加到结果集中
		for(TreeNode children: childList) {
			resultList.add(children);
		}
		
		//遍历当前节点的所有子节点,递归寻找子节点的子节点
		for(TreeNode children: childList) {
			List<TreeNode> tempList = getAllChildren(children);
			if(tempList != null && tempList.size() != 0) {
				resultList.addAll(tempList);
			}
		}
		return resultList;
	}	
	
	//将长度大于28的字符串截取
	public static String getAssName(String name){
		if(name == null){
			return null;
		}
		if(name.length()>28){
			if(name.contains("_")){
				String[] cnames = name.split("_");
				while((cnames[0]+"_"+cnames[1]).length()>28){
					if(cnames[0].length()>1){
						cnames[0] = cnames[0].substring(0, cnames[0].length()-1);
					}
					if(cnames[1].length()>1){
						cnames[1] = cnames[1].substring(0, cnames[1].length()-1);
					}
				}
				return cnames[0]+"_"+cnames[1];
			}else{
				return name.substring(0, 28);
			}
		}
		return name;
	}
	
	private static void parcelDriverClassName(DataSourceInfo dsi){
		
		if(dsi.getDbType().equals(DBType.Oracle.getDbType())){//oracle
			dsi.setDriverClass("oracle.jdbc.driver.OracleDriver");
//		}else if(dsi.getDbType().equals(DBType.DB2.getDbType())){//db2
//			dsi.setDriverClass("com.ibm.db2.jcc.DB2Driver");
		}else if(dsi.getDbType().equals(DBType.MySQL.getDbType())){//mysql
			dsi.setDriverClass("com.mysql.jdbc.Driver");
		}else if(dsi.getDbType().equals(DBType.MsSQL.getDbType())){//sqlserver
			dsi.setDriverClass("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//		}else if(dsi.getDbType().equals(DBType.Sybase.getDbType())){//sybase
//			dsi.setDriverClass("com.sybase.jdbc3.jdbc.SybDriver");
		}else if(dsi.getDbType().equals(DBType.POSTGRESQL.getDbType())){//postgresql
			dsi.setDriverClass("org.postgresql.Driver");
		}
	}

	public static String getDBType(String driverClassName) {
		if("oracle.jdbc.driver.OracleDriver".equals(driverClassName)) {
			return DBType.Oracle.getDbType();
//		} else if("com.ibm.db2.jcc.DB2Driver".equals(driverClassName)) {
//			return DBType.DB2.getDbType();
		} else if("com.mysql.jdbc.Driver".equals(driverClassName) || "com.mysql.cj.jdbc.Driver".equals(driverClassName) ) {
			return DBType.MySQL.getDbType();
		} else if("com.microsoft.sqlserver.jdbc.SQLServerDriver".equals(driverClassName)) {
			return DBType.MsSQL.getDbType();
//		} else if("com.sybase.jdbc3.jdbc.SybDriver".equals(driverClassName)) {
//			return DBType.Sybase.getDbType();
		}else if("org.postgresql.Driver".equals(driverClassName)) {
			return DBType.POSTGRESQL.getDbType();
		}
		return DBType.Oracle.getDbType();
	}
	
	public static void setIPPortSid(DataSourceInfo dsi, String url, String dbType) {
		if (dbType.equals(DBType.Oracle.getDbType())) {// oracle
			String[] IPPortSid = url.substring(url.indexOf("@") + 1).split(":");
			dsi.setIp(IPPortSid[0]);
			dsi.setPort(IPPortSid[1]);
			dsi.setSid(IPPortSid[2]);
//		} else if (dbType.equals(DBType.DB2.getDbType())) {// db2
//			//TODO jdbc:db2://localhost:5000/sample
//			//r如下  add code hu
//			String[] IPPortSid = url.split("/");
//			dsi.setIp(IPPortSid[2].split(":")[0]);
//			dsi.setPort(IPPortSid[2].split(":")[1]);
//			dsi.setSid(IPPortSid[3]);
			
		} else if (dbType.equals(DBType.MySQL.getDbType())) {// mysql
			String[] IPPortSid = url.split("/");
			dsi.setIp(IPPortSid[2].split(":")[0]);
			dsi.setPort(IPPortSid[2].split(":")[1]);
			dsi.setSid(IPPortSid[3]);			
		} else if (dbType.equals(DBType.MsSQL.getDbType())) {// sqlserver
			//jdbc:sqlserver://192.168.20.217:1433;databaseName=master
			String[] IPPort = url.split("/");
			dsi.setIp(IPPort[2].split(":")[0]);
			dsi.setPort(IPPort[2].split(":")[1]);
			String[] sid = url.split("=");
			dsi.setSid(sid[sid.length-1]);
			
//		} else if (dbType.equals(DBType.Sybase.getDbType())) {// sybase
//			//TODO jdbc:sybase:Tds:localhost:5007/myDB
//			String[] IPPortSid = url.split("/");
//			dsi.setIp(IPPortSid[2].split(":")[0]);
//			dsi.setPort(IPPortSid[2].split(":")[1]);
//			dsi.setSid(IPPortSid[3]);	
//			
		} 
		else if (dbType.equals(DBType.POSTGRESQL.getDbType())) {// postgresql
			String[] IPPortSid = url.split("/");
			dsi.setIp(IPPortSid[2].split(":")[0]);
			dsi.setPort(IPPortSid[2].split(":")[1]);
			dsi.setSid(IPPortSid[3]);
		}
	}
	/**
	 * 获取工作空间中加载当前模块项目的所有OM项目配置的数据源
	 * @author mqf
	 * @return List<DataSourceInfo> 数据源列表
	 */
	public static List<DataSourceInfo> readOMDataSourceList(IProject project) throws ReverseException {
		
		List<DataSourceInfo> dataSourceList = new ArrayList<DataSourceInfo>();
		//工作空间中所有的OM项目
		try {
			String projectPath = project.getLocation().toOSString();
			String dataSourceXMLPath = projectPath + IProjectConstant.BOM_DATASOURCE_XML_RALATIVE_PATH;
			
			List<DataSourceInfo> dataSourceInfos = ReverseUtil.readDataSourceXML(dataSourceXMLPath);
			for(DataSourceInfo dataSourceInfo: dataSourceInfos) {
				dataSourceInfo.setUapName(project.getName());
			}
			dataSourceList.addAll(dataSourceInfos);
			
			return dataSourceList;
		} catch (Exception e) {
			String msg = "读取数据源文件错误, 请检查datasource.xml文件格式是否符合规范.";
			throw new ReverseException(msg, e);
		}
	}
	
	/**
	 * 解析datasource.xml文件
	 * @param dataSourceXMLPath datasource.xml文件路径
	 * @return List<DataSourceInfo> 数据源列表
	 */
	@SuppressWarnings("unchecked")
	public static List<DataSourceInfo> readDataSourceXML(String dataSourceXMLPath) {
		BufferedReader bufReader = null;
		Document doc = null;
		//数据源集合
		List<DataSourceInfo> dataSourceList = new ArrayList<DataSourceInfo>();
		FileReader fr = null;
		try {
			//读取datasource.xml文件
			fr = new FileReader(dataSourceXMLPath);
			bufReader = new BufferedReader(fr);
			SAXReader reader = new SAXReader();
			doc = reader.read(bufReader);
			Element rootElement = doc.getRootElement();
			
			//所有bean元素
			List<Element> datasourceElements = rootElement.elements("bean");
			DataSourceInfo dataSource = null;
			//bean的property的值, 
			Map<String, String> attributeMap = new HashMap<String, String>();
			for(Element datasourceElement: datasourceElements) {
				
				//modify by xuran 过滤jndi数据源
				if(datasourceElement.attributeValue("class").equals("org.springframework.jndi.JndiObjectFactoryBean")){
					continue;
				}
				//modify by xuran 

				List<Element> propertyList = datasourceElement.elements("property");
				attributeMap = new HashMap<String, String>();
				for(Element property: propertyList) {
					attributeMap.put(property.attributeValue("name"), property.attributeValue("value"));
				}
				//创建数据源对象
				dataSource = new DataSourceInfo();
				dataSource.setDataSourceName(datasourceElement.attributeValue("id"));
				String dbType = ReverseUtil.getDBType(attributeMap.get(KEY_DRIVER_CLASS_NAME));
				dataSource.setDbType(dbType);
				dataSource.setDriverClass(attributeMap.get(KEY_DRIVER_CLASS_NAME));
				dataSource.setPwd(attributeMap.get(KEY_PPP));
				dataSource.setUrl(attributeMap.get(KEY_URL));
				dataSource.setUserName(attributeMap.get(KEY_USERNAME));
				
				setIPPortSid(dataSource, attributeMap.get(KEY_URL), dbType);
				//加入到结果中
				dataSourceList.add(dataSource);
			}
			return dataSourceList;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if(bufReader != null) {
				try {
					bufReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(fr != null) {
				try {
					fr.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 写入数据源
	 * @param dataSourceXMLPath
	 * @param dataSource
	 */
	public static void writeDatasourceXML(String dataSourceXMLPath, DataSourceInfo dataSource) {
		BufferedReader bufReader = null;
		Document doc = null;
		XMLWriter writer = null;
		FileOutputStream fos = null;
		FileReader fr = null;
		//读取datasource.xml文件
		try {
			fr = new FileReader(dataSourceXMLPath);
			bufReader = new BufferedReader(fr);
			SAXReader reader = new SAXReader();
			doc = reader.read(bufReader);
			Element rootElement = doc.getRootElement();
			
			Element beanElement = rootElement.addElement("bean");
			
			beanElement.addAttribute("id", dataSource.getDataSourceName());
			beanElement.addAttribute("name", dataSource.getDataSourceName());
			beanElement.addAttribute("class", "org.apache.commons.dbcp.BasicDataSource");
			beanElement.addAttribute("destroy-method", "close");
			
			Element property = null;
			property = beanElement.addElement("property");
			property.addAttribute("name", KEY_DRIVER_CLASS_NAME);
			property.addAttribute("value", dataSource.getDriverClass());
			
			property = beanElement.addElement("property");
			property.addAttribute("name", KEY_URL);
			property.addAttribute("value", dataSource.getUrl());
			
			property = beanElement.addElement("property");
			property.addAttribute("name", KEY_USERNAME);
			property.addAttribute("value", dataSource.getUserName());
			
			property = beanElement.addElement("property");
			property.addAttribute("name", KEY_PPP);
			property.addAttribute("value", dataSource.getPwd());
			
			property = beanElement.addElement("property");
			property.addAttribute("name", "maxActive");
			property.addAttribute("value", "100");
			
			property = beanElement.addElement("property");
			property.addAttribute("name", "maxIdle");
			property.addAttribute("value", "30");
			
			property = beanElement.addElement("property");
			property.addAttribute("name", "maxWait");
			property.addAttribute("value", "5000");
			
			property = beanElement.addElement("property");
			property.addAttribute("name", "defaultAutoCommit");
			property.addAttribute("value", "true");
			
			property = beanElement.addElement("property");
			property.addAttribute("name", "removeAbandoned");
			property.addAttribute("value", "true");
			
			property = beanElement.addElement("property");
			property.addAttribute("name", "removeAbandonedTimeout");
			property.addAttribute("value", "60");
			
			property = beanElement.addElement("property");
			property.addAttribute("name", "logAbandoned");
			property.addAttribute("value", "true");
			
	        OutputFormat format = OutputFormat.createPrettyPrint();
	        fos = new FileOutputStream(dataSourceXMLPath);
	        writer = new XMLWriter(fos,format);
	        
	        writer.write(doc);

		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if(bufReader != null) {
				try {
					bufReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(fr != null) {
				try {
					fr.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
	/**
	 * 修改数据源
	 * @param dataSourceXMLPath 路径
	 * @param oldSourceName 旧的数据源名称
	 * @param dataSource 新的数据源参数
	 */
	@SuppressWarnings("unchecked")
	public static void updateDatasourceXML(String oldUapName, String oldSourceName, String dataSourceXMLPath, DataSourceInfo dataSource) throws ReverseException {
		
		BufferedReader bufReader = null;
		Document doc = null;
		FileOutputStream fos=null;
		XMLWriter writer = null;
		FileReader fileReader = null;
		try {
			
			String oldDataSourceXMLPath = getWorkspacePath()
										 + oldUapName
										+ IProjectConstant.BOM_DATASOURCE_XML_RALATIVE_PATH;
			fileReader = new FileReader(oldDataSourceXMLPath);
			bufReader = new BufferedReader(fileReader);
			SAXReader reader = new SAXReader();
			doc = reader.read(bufReader);
			Element rootElement = doc.getRootElement();
			
			List<Element> eleList = rootElement.elements("bean");
			
			/**
			 * 如果已经修改了所属的uap项目 并且被修改的数据源配置文件中之有一个配置  则不可以移动，因为要保证一个UAP项目至少配置一个数据源
			 * 如果把数据源移动了  并且修改后的数据源名称与新的uap配置又冲突 则修改失败
			 * */
			if(!oldUapName.equals(dataSource.getUapName())) {
				List<DataSourceInfo> newDataSourceList = readDataSourceXML(dataSourceXMLPath);
				for(DataSourceInfo newInfo: newDataSourceList) {
					if(newInfo.getDataSourceName().equals(dataSource.getDataSourceName())) {
						String msg = "修改数据源错误, 数据源名称'" + dataSource.getDataSourceName() + "'不可用, 在项目 " + dataSource.getUapName() + " 中存在同名的数据源.";
						ReverseException reverseException = new ReverseException();
						reverseException.setExceptionMsg(msg);
						throw reverseException;
					}
				}
			}
			
			for(Element element: eleList) {
				if(element.attribute("id").getText().equals(oldSourceName)) {
					rootElement.remove(element);
					break;
				}
			}
			
			OutputFormat format = OutputFormat.createPrettyPrint();
			fos=new FileOutputStream(oldDataSourceXMLPath);
	        writer = new XMLWriter(fos,format);
	        
	        writer.write(doc);
			writeDatasourceXML(dataSourceXMLPath, dataSource);
		} catch (Exception ex) {
			throw new ReverseException(ex);
		} finally {
			if(fos!=null){
				 try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}		   
			if(bufReader != null) {
				try {
					bufReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(writer != null){
				 try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(fileReader != null){
				 try {
					 fileReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 删除数据源
	 * @author mqf
	 * @param uapName
	 * @param sourceName
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static boolean deleteOMDatasourceXML(String uapName, String sourceName) throws ReverseException {
		BufferedReader bufReader = null;
		Document doc = null;
		XMLWriter writer = null;
		OutputStream os = null;
		FileReader fileReader = null;
		try {
			String dataSourceXMLPath = getWorkspacePath()
										+ File.separator + uapName
										+ IProjectConstant.BOM_DATASOURCE_XML_RALATIVE_PATH;
			fileReader = new FileReader(dataSourceXMLPath);
			bufReader = new BufferedReader(fileReader);
			SAXReader reader = new SAXReader();
			doc = reader.read(bufReader);
			Element rootElement = doc.getRootElement();
			
			List<Element> eleList = rootElement.elements("bean");
			for(Element element: eleList) {
				if(element.attribute("id").getText().equals(sourceName)) {
					rootElement.remove(element);
					break;
				}
			}
			os = new FileOutputStream(dataSourceXMLPath);
			OutputFormat format = OutputFormat.createPrettyPrint();
	        writer = new XMLWriter(os,format);
	        
	        writer.write(doc);
		} catch (Exception e) {
			throw new ReverseException(e);
		} finally {
			if(bufReader != null) {
				try {
					bufReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(fileReader != null){
				 try {
					 fileReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(writer != null){
				 try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(os != null){
				 try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return true;
	}
	/**
	 * 根据nature获取指定类型的工程
	 * @param natureId
	 * @return
	 */
	public static List<IProject> getProjectsByNatureId(String natureId) {
		List<IProject> natureProjects = new ArrayList<IProject>();
		
		//获取当前工作空间下的所有工程
		IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
		
		for(IProject project: projects)  {
			try {
				if(project.isOpen() && (project.hasNature(natureId) || 
						(project.getDescription() != null &&
						project.getDescription().getComment().equals(natureId)) )) {
					//筛选出指定的工程
					natureProjects.add(project);
				}
			} catch (CoreException e) {
				throw new RuntimeException(e);
			}
		}
		return natureProjects;
	}
	
	public static DataSourceInfo getLastSelectedDataSource(List<DataSourceInfo> datasourceList) {
		String selected = getReverseConfig("reverse.selected");
		if(selected != null && selected.trim().length() != 0) {
			for(DataSourceInfo dataSourceInfo: datasourceList) {
				if(selected.equals(dataSourceInfo.getDataSourceName() + "@" + dataSourceInfo.getUapName())) {
					return dataSourceInfo;
				}
			}
		}
		return null;
	}
	
	/**
	 * 读取studio参数：上一次连接的数据源
	 * @param property
	 * @return
	 */
	public static String getReverseConfig(String property) {
		IPreferenceStore store = BusinessModelEditorPlugin.getDefault().getPreferenceStore();
		return store.getString(property);
	}

	/**
	 * 保存上次选择的数据源
	 * @param property
	 * @param value
	 */
	@SuppressWarnings("deprecation")
	public static void saveReverseConfig(String property, String value) {
		IPreferenceStore store = BusinessModelEditorPlugin.getDefault().getPreferenceStore();
		store.setValue(property, value);
		BusinessModelEditorPlugin.getDefault().savePluginPreferences();
	}
	
	public static void closeResultSet(ResultSet rs) {
		if(rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				throw new RuntimeException(e.getMessage(), e);
			}
		}
	}
	
	public static void closePreparedStatement(PreparedStatement pst) {
		if(pst != null) {
			try {
				pst.close();
			} catch (SQLException e) {
				throw new RuntimeException(e.getMessage(), e);
			}
		}
	}
	/**
	 * 读取项目得数据源
	 * @return
	 * @throws ReverseException 
	 */
	public static List<DataSourceInfo> readMicroDataSourceList(IProject project) throws ReverseException {
		List<DataSourceInfo> dataSourceList = new ArrayList<DataSourceInfo>();
		//工作空间中所有的UAP项目
		try {
			if(project != null){	
				List<DataSourceInfo> dataSourceInfos = ReverseUtil.readDataSourceProperties(project);
				for(DataSourceInfo dataSourceInfo: dataSourceInfos) {
					dataSourceInfo.setUapName(project.getName());
				}
				dataSourceList.addAll(dataSourceInfos);
			}
			
			return dataSourceList;
		} catch (RuntimeException e) {
			String msg = "数据源密码加密";
			throw new ReverseException(msg, e);
		} catch (IOException e) {
			String msg = "读取数据源文件错误, 请检查appliacation.properties是否存在.";
			throw new ReverseException(msg, e);
		}catch (Exception e) {
			String msg = "读取数据源文件错误, 请检查appliacation.properties文件格式是否符合规范.";
			throw new ReverseException(msg, e);
		}
	}

	private static List<DataSourceInfo> readDataSourceProperties(IProject project) throws IOException{
		String projectPath = project.getLocation().toOSString();
		//数据源集合
		List<DataSourceInfo> dataSourceList = new ArrayList<DataSourceInfo>();
		ApplicationProperties appProperties = new ApplicationProperties(projectPath);
		//创建数据源对象
		DataSourceInfo dataSource = new DataSourceInfo();
		dataSource.setDataSourceName(project.getName());
		String dbType = ReverseUtil.getDBType(appProperties.getProperty(MICRO_KEY_DRIVER_CLASS_NAME));
		dataSource.setDbType(dbType);
		dataSource.setDriverClass(appProperties.getProperty(MICRO_KEY_DRIVER_CLASS_NAME));
		String pwd = appProperties.getProperty(MICRO_KEY_PPP);
		/*if(pwd.startsWith("ENC(")){
			String encryptorPwd = appProperties.getProperty(JASYPT_ENCRYPTOR_KEY_PPP);
			pwd = ConfigEncryptUtils.getPwd(pwd, encryptorPwd);
		}*/
		dataSource.setPwd(pwd);
		dataSource.setUrl(appProperties.getProperty(MICRO_KEY_URL));
		dataSource.setUserName(appProperties.getProperty(MICRO_KEY_USERNAME));
		
		setIPPortSid(dataSource, appProperties.getProperty(MICRO_KEY_URL), dbType);
		//加入到结果中
		dataSourceList.add(dataSource);
		return dataSourceList;
	}
	/**
	 * 判断表中字段是否含有特殊字符
	 * @param table
	 * @return
	 */
	public static boolean isSpecialCharTable(Table table) {
		Collection<Column> columns = table.getColumns().values();
		for(Column col : columns){
			if(isSpecialChar(col.getName())){
				return true;
			}
		}
		return false;
	}
	/**
     * 判断是否含有特殊字符
     *
     * @param str
     * @return true为包含，false为不包含
     */
    public static boolean isSpecialChar(String str) {
    	str = str.replaceAll("\\d", "");
        String regEx = "[ `~!@#$%^&*()+-=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]|\n|\r|\t";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.find();
    }
	/**
	 * 判断表中字段是否含有特殊字符
	 * @param table
	 * @return
	 */
	public static boolean startWithFigure(Table table) {
		Collection<Column> columns = table.getColumns().values();
		for(Column col : columns){
			if(startWithFigure(col.getName())){
				return true;
			}
		}
		return false;
	}
    /**
     * 判断是以数字开头
     *
     * @param str
     * @return 
     */
    public static boolean startWithFigure(String str) {
    	if(str == null || "".equals(str)){
    		return false;
    	}
    	String temp = str.charAt(0)+"";
	    String regEx = "\\d";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(temp);
        return m.find();
    }
}