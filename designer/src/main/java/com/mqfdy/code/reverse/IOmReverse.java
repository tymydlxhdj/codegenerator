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

public interface IOmReverse {

	/**
	 * 存储数据源信息
	 * @param dsi
	 * @throws IOException
	 */
	public void writeDataSource(DataSourceInfo dsi) throws IOException, DocumentException;
	
	/**
	 * 测试连接是否成功
	 * @param dsi
	 * @return
	 */
	public boolean connectTest(DataSourceInfo dsi) throws Exception;
	
	/**
	 * 下拉列表变更时调用，用于创建Connection对象
	 * @param dsi
	 */
	public void createConnection(DataSourceInfo dsi) throws Exception;
	
	/**
	 * 获取schema下表信息
	 * @return
	 */
	public List<Table> fetchTables(DataSourceInfo dataSourceInfo);

	/**
	 * 获取schema下视图结合
	 * @param schema
	 * @return
	 */
	public List<View> fetchViews(DataSourceInfo dataSourceInfo);
	
	/**
	 * 读取表字段及约束信息
	 * @param schema
	 * @param table
	 */
	public Table fetchColumnsAndContraints(DataSourceInfo dataSourceInfo,String tableName);
	
	/**
	 * 获取用户下所有sequence名集合
	 * @param dataSourceInfo
	 * @return
	 */
	public List<String> fetchSequences(DataSourceInfo dataSourceInfo) throws Exception;
	
	/**
	 * 封装column名到table对象中
	 * @param table
	 */
	public void fetchTableColumnNames(Table table);
	
	/**
	 * 封装column名到View对象中
	 * @param table
	 */
	public View fetchViewColumns(String viewName);
	
	/**
	 * 获取具有关联关系的表
	 * @return
	 */
	public List<Table> getRelativeTables(String tableName);
	
	/**
	 * 查找无主键表
	 * @param tableNames
	 * @return
	 */
//	public List<Table> findNoPkTable(List<String> tableNames);
	public  Map<String, List<Table>> findNoPkTable(List<String> tableNames, IProgressMonitor monitor);
	
	/**
	 * 查找复合主键表
	 * @param tableNames
	 * @return
	 */
	public List<Table> findMutiPkTable(List<String> tableNames);
	public List<Table> findMutiPkTable(List<String> tableNames, IProgressMonitor monitor);
	
	/**
	 * 处理特殊表
	 * 无主键表，自动加上一个主键。
	 * 复合主键表，自动改造为单一主键，其关联的子表中的复合外键改造为单一外键。
	 * @param noPkTableNames
	 */
	public void handleSpecialTable(List<SpecialTable> sTables);
	
	/**
	 * 新建bom对象，用于向导“完成”时创建om文件
	 * @param filePath om上级目录全路径
	 * @param nameSpace 命名空间名
	 * @param modelName 模块名
	 * @param modelDisplayName 模块显示名
	 */
	public void createNewBom(String filePath,String nameSpace,String modelName,String modelDisplayName);
	
	/**
	 * 获取与已有om中表同名的集合
	 * @param omPath om文件路径
	 */
	public List<Table> fetchExistOm(String omPath) throws Exception;
	
	/**
	 * 处理重复表
	 * @param sTables
	 */
	public void handleDuplicateTable(List<SpecialTable> sTables);
	
	/**
	 * 获取最终表集合，其中封装列信息
	 * @return
	 */
	public List<Table> fetchLastTables();
	
	/**
	 * 生成业务实体
	 * @param bcNames
	 */
	public void createBom(BusinessObjectModel bom);
	
	/**
	 * 反向工程完成后，释放内存
	 */
	public void clearMemery();

	void createBom(BusinessObjectModel bom, IProgressMonitor monitor, PackageDispachPage pdPage);

	void writeDataSource(String dataSourceXMLPath, DataSourceInfo dataSourceInfo);

	public Map<String,List<Table>> findInValidTable(List<String> tableNames, IProgressMonitor monitor);
	
}