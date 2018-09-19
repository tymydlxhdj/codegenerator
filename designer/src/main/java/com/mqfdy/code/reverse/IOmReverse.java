package com.mqfdy.code.reverse;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.dom4j.DocumentException;
import org.eclipse.core.runtime.IProgressMonitor;

import com.mqfdy.code.model.BusinessObjectModel;
import com.mqfdy.code.reverse.mappings.Table;
import com.mqfdy.code.reverse.mappings.View;
import com.mqfdy.code.reverse.views.beans.SpecialTable;
import com.mqfdy.code.reverse.views.pages.PackageDispachPage;

// TODO: Auto-generated Javadoc
/**
 * The Interface IOmReverse.
 *
 * @author mqfdy
 */
public interface IOmReverse {

	/**
	 * 存储数据源信息.
	 *
	 * @author mqfdy
	 * @param dsi
	 *            the dsi
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws DocumentException
	 *             the document exception
	 * @Date 2018-09-03 09:00
	 */
	public void writeDataSource(DataSourceInfo dsi) throws IOException, DocumentException;
	
	/**
	 * 测试连接是否成功.
	 *
	 * @author mqfdy
	 * @param dsi
	 *            the dsi
	 * @return true, if successful
	 * @throws Exception
	 *             the exception
	 * @Date 2018-09-03 09:00
	 */
	public boolean connectTest(DataSourceInfo dsi) throws Exception;
	
	/**
	 * 下拉列表变更时调用，用于创建Connection对象.
	 *
	 * @author mqfdy
	 * @param dsi
	 *            the dsi
	 * @throws Exception
	 *             the exception
	 * @Date 2018-09-03 09:00
	 */
	public void createConnection(DataSourceInfo dsi) throws Exception;
	
	/**
	 * 获取schema下表信息.
	 *
	 * @author mqfdy
	 * @param dataSourceInfo
	 *            the data source info
	 * @return the list
	 * @Date 2018-09-03 09:00
	 */
	public List<Table> fetchTables(DataSourceInfo dataSourceInfo);

	/**
	 * 获取schema下视图结合.
	 *
	 * @author mqfdy
	 * @param dataSourceInfo
	 *            the data source info
	 * @return the list
	 * @Date 2018-09-03 09:00
	 */
	public List<View> fetchViews(DataSourceInfo dataSourceInfo);
	
	/**
	 * 读取表字段及约束信息.
	 *
	 * @author mqfdy
	 * @param dataSourceInfo
	 *            the data source info
	 * @param tableName
	 *            the table name
	 * @return the table
	 * @Date 2018-09-03 09:00
	 */
	public Table fetchColumnsAndContraints(DataSourceInfo dataSourceInfo,String tableName);
	
	/**
	 * 获取用户下所有sequence名集合.
	 *
	 * @author mqfdy
	 * @param dataSourceInfo
	 *            the data source info
	 * @return the list
	 * @throws Exception
	 *             the exception
	 * @Date 2018-09-03 09:00
	 */
	public List<String> fetchSequences(DataSourceInfo dataSourceInfo) throws Exception;
	
	/**
	 * 封装column名到table对象中.
	 *
	 * @author mqfdy
	 * @param table
	 *            the table
	 * @Date 2018-09-03 09:00
	 */
	public void fetchTableColumnNames(Table table);
	
	/**
	 * 封装column名到View对象中.
	 *
	 * @author mqfdy
	 * @param viewName
	 *            the view name
	 * @return the view
	 * @Date 2018-09-03 09:00
	 */
	public View fetchViewColumns(String viewName);
	
	/**
	 * 获取具有关联关系的表.
	 *
	 * @author mqfdy
	 * @param tableName
	 *            the table name
	 * @return the relative tables
	 * @Date 2018-09-03 09:00
	 */
	public List<Table> getRelativeTables(String tableName);
	
	/**
	 * 查找无主键表.
	 *
	 * @author mqfdy
	 * @param tableNames
	 *            the table names
	 * @param monitor
	 *            the monitor
	 * @return the map
	 * @Date 2018-09-03 09:00
	 */
//	public List<Table> findNoPkTable(List<String> tableNames);
	public  Map<String, List<Table>> findNoPkTable(List<String> tableNames, IProgressMonitor monitor);
	
	/**
	 * 查找复合主键表.
	 *
	 * @author mqfdy
	 * @param tableNames
	 *            the table names
	 * @return the list
	 * @Date 2018-09-03 09:00
	 */
	public List<Table> findMutiPkTable(List<String> tableNames);
	
	/**
	 * Find muti pk table.
	 *
	 * @author mqfdy
	 * @param tableNames
	 *            the table names
	 * @param monitor
	 *            the monitor
	 * @return the list
	 * @Date 2018-09-03 09:00
	 */
	public List<Table> findMutiPkTable(List<String> tableNames, IProgressMonitor monitor);
	
	/**
	 * 处理特殊表 无主键表，自动加上一个主键。 复合主键表，自动改造为单一主键，其关联的子表中的复合外键改造为单一外键。.
	 *
	 * @author mqfdy
	 * @param sTables
	 *            the s tables
	 * @Date 2018-09-03 09:00
	 */
	public void handleSpecialTable(List<SpecialTable> sTables);
	
	/**
	 * 新建bom对象，用于向导“完成”时创建om文件.
	 *
	 * @author mqfdy
	 * @param filePath
	 *            om上级目录全路径
	 * @param nameSpace
	 *            命名空间名
	 * @param modelName
	 *            模块名
	 * @param modelDisplayName
	 *            模块显示名
	 * @Date 2018-09-03 09:00
	 */
	public void createNewBom(String filePath,String nameSpace,String modelName,String modelDisplayName);
	
	/**
	 * 获取与已有om中表同名的集合.
	 *
	 * @author mqfdy
	 * @param omPath
	 *            om文件路径
	 * @return the list
	 * @throws Exception
	 *             the exception
	 * @Date 2018-09-03 09:00
	 */
	public List<Table> fetchExistOm(String omPath) throws Exception;
	
	/**
	 * 处理重复表.
	 *
	 * @author mqfdy
	 * @param sTables
	 *            the s tables
	 * @Date 2018-09-03 09:00
	 */
	public void handleDuplicateTable(List<SpecialTable> sTables);
	
	/**
	 * 获取最终表集合，其中封装列信息.
	 *
	 * @author mqfdy
	 * @return the list
	 * @Date 2018-09-03 09:00
	 */
	public List<Table> fetchLastTables();
	
	/**
	 * 生成业务实体.
	 *
	 * @author mqfdy
	 * @param bom
	 *            the bom
	 * @Date 2018-09-03 09:00
	 */
	public void createBom(BusinessObjectModel bom);
	
	/**
	 * 反向工程完成后，释放内存.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	public void clearMemery();

	/**
	 * Creates the bom.
	 *
	 * @author mqfdy
	 * @param bom
	 *            the bom
	 * @param monitor
	 *            the monitor
	 * @param pdPage
	 *            the pd page
	 * @Date 2018-09-03 09:00
	 */
	void createBom(BusinessObjectModel bom, IProgressMonitor monitor, PackageDispachPage pdPage);

	/**
	 * Write data source.
	 *
	 * @author mqfdy
	 * @param dataSourceXMLPath
	 *            the data source XML path
	 * @param dataSourceInfo
	 *            the data source info
	 * @Date 2018-09-03 09:00
	 */
	void writeDataSource(String dataSourceXMLPath, DataSourceInfo dataSourceInfo);

	/**
	 * Find in valid table.
	 *
	 * @author mqfdy
	 * @param tableNames
	 *            the table names
	 * @param monitor
	 *            the monitor
	 * @return the map
	 * @Date 2018-09-03 09:00
	 */
	public Map<String,List<Table>> findInValidTable(List<String> tableNames, IProgressMonitor monitor);
	
}