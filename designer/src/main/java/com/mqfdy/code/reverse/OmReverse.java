package com.mqfdy.code.reverse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.DocumentException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.zest.core.widgets.GraphConnection;
import org.eclipse.zest.core.widgets.GraphNode;
import org.eclipse.zest.core.widgets.ZestStyles;
import org.eclipse.zest.layouts.LayoutAlgorithm;
import org.eclipse.zest.layouts.LayoutStyles;
import org.eclipse.zest.layouts.algorithms.CompositeLayoutAlgorithm;

import com.mqfdy.code.designer.editor.auto.algorithms.GXTreeLayoutAlgorithm;
import com.mqfdy.code.designer.editor.auto.algorithms.GraphOm;
import com.mqfdy.code.designer.editor.auto.algorithms.ShiftDiagramLayoutAlgorithm;
import com.mqfdy.code.designer.editor.commands.AutoLayoutCommand;
import com.mqfdy.code.designer.utils.BusinessModelUtil;
import com.mqfdy.code.model.AbstractModelElement;
import com.mqfdy.code.model.Association;
import com.mqfdy.code.model.BusinessClass;
import com.mqfdy.code.model.BusinessObjectModel;
import com.mqfdy.code.model.ModelPackage;
import com.mqfdy.code.model.graph.Diagram;
import com.mqfdy.code.model.graph.DiagramElement;
import com.mqfdy.code.model.graph.ElementStyle;
import com.mqfdy.code.model.utils.AssociationType;
import com.mqfdy.code.model.utils.DBType;
import com.mqfdy.code.resource.BomManager;
import com.mqfdy.code.reverse.jdbc.JDBCBinder;
import com.mqfdy.code.reverse.jdbc.JDBCReader;
import com.mqfdy.code.reverse.mappings.Column;
import com.mqfdy.code.reverse.mappings.ForeignKey;
import com.mqfdy.code.reverse.mappings.PrimaryKey;
import com.mqfdy.code.reverse.mappings.Table;
import com.mqfdy.code.reverse.mappings.View;
import com.mqfdy.code.reverse.utils.ReverseComparator;
import com.mqfdy.code.reverse.utils.ReverseContants;
import com.mqfdy.code.reverse.utils.ReverseUtil;
import com.mqfdy.code.reverse.views.beans.SpecialTable;
import com.mqfdy.code.reverse.views.constant.IViewConstant;
import com.mqfdy.code.reverse.views.pages.PackageDispachPage;
// TODO: Auto-generated Javadoc

/**
 * The Class OmReverse.
 *
 * @author mqfdy
 */
public class OmReverse implements IOmReverse{
	
	/** The ignored duplication. */
	private static boolean ignoredDuplication = false;  //是否有重复表名的业务实体
	
	/** The ignored special. */
	private static boolean ignoredSpecial = false;	//是否处理过不合规的表 
	
	/** The y. */
	private int y = 0;
	
	/**
	 * 写入新配置数据源.
	 *
	 * @author mqfdy
	 * @param dsi
	 *            the dsi
	 * @throws DocumentException
	 *             the document exception
	 * @Date 2018-09-03 09:00
	 */
	public void writeDataSource(DataSourceInfo dsi) throws DocumentException{
		
		ReverseUtil.writeDataSource(dsi);
	}
	
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
	public boolean connectTest(DataSourceInfo dsi) throws Exception{
		dsi.setDriverUrl(ReverseUtil.getDriverPath(dsi.getDbType()));
		Connection connection = null;
		try {
			connection = ReverseUtil.createConnection(dsi);
			//ReverseContext.wizard.setConnection(connection);//测试连接时就缓存该连接
			if(connection!=null){
				return true;
			}
			return false;
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(),e);
		}
	}

	/**
	 * 获取schema下所有表.
	 *
	 * @author mqfdy
	 * @param dataSourceInfo
	 *            the data source info
	 * @return the list
	 * @Date 2018-09-03 09:00
	 */
	public List<Table> fetchTables(DataSourceInfo dataSourceInfo) {
		List<Table> tableList = new ArrayList<Table>();
		JDBCReader reader = new JDBCReader();
		if(DBType.Oracle.getDbType().equals(dataSourceInfo.getDbType())) {
			tableList = reader.readTableInfoBySql(ReverseContext.wizard.getConnection(),DBType.Oracle.getDbType());
			Collections.sort(tableList, new ReverseComparator());
		}
//		else if(DBType.DM.getDbType().equals(dataSourceInfo.getDbType())) {
//			tableList = reader.readTableInfoBySql(ReverseContext.wizard.getConnection(),DBType.DM.getDbType());
//			Collections.sort(tableList, new ReverseComparator());
//		}
//		else if(DBType.KingBase.getDbType().equals(dataSourceInfo.getDbType())) {
//			tableList = reader.readTableInfoBySql(ReverseContext.wizard.getConnection(),DBType.KingBase.getDbType());
//			Collections.sort(tableList, new ReverseComparator());
//		}
		else {
			tableList = reader.readTables(ReverseContext.wizard.getConnection(), null);
		}
		
		return tableList;
	}
	
	/**
	 * 获取schema下所有视图.
	 *
	 * @author mqfdy
	 * @param dataSourceInfo
	 *            the data source info
	 * @return the list
	 * @Date 2018-09-03 09:00
	 */
	public List<View> fetchViews(DataSourceInfo dataSourceInfo) {
		
		JDBCReader reader = new JDBCReader();
		List<View> views = null;
		try {
			views = reader.readViews(ReverseContext.wizard.getConnection(), dataSourceInfo.getUserName());
		} catch (Exception e) {
			throw new RuntimeException("读取视图错误", e);
		}
		return views;
	}
	
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
	public Table fetchColumnsAndContraints(DataSourceInfo dataSourceInfo,String tableName){
		JDBCReader reader = new JDBCReader();
		Table table = ReverseContext.allTables.get(tableName);
		reader.readColumns(table);
		reader.readConstraints(table);
		
		return table;
	}
	
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
	public List<String> fetchSequences(DataSourceInfo dataSourceInfo) throws Exception{
		JDBCReader reader = new JDBCReader();
		try {
			return reader.readSequence(dataSourceInfo.getUserName());
		} catch (SQLException e) {
			throw new RuntimeException("获取sequence信息出错",e);
		}
	}
	
	/**
	 * 封装column名到table对象中.
	 *
	 * @author mqfdy
	 * @param table
	 *            the table
	 * @Date 2018-09-03 09:00
	 */
	public void fetchTableColumnNames(Table table){
		JDBCReader reader = new JDBCReader();
		reader.readColumns(table);
	}
	
	/**
	 * 封装column名VIEW对象.
	 *
	 * @author mqfdy
	 * @param viewName
	 *            the view name
	 * @return the view
	 * @Date 2018-09-03 09:00
	 */
	public View fetchViewColumns(String viewName){
		JDBCReader reader = new JDBCReader();
		View view = ReverseContext.allViews.get(viewName);
		reader.readColumns(view);
		
		return view;
	}

	/**
	 * 数据源下拉列表值改变时，触发该方法用于创建连接.
	 *
	 * @author mqfdy
	 * @param dsi
	 *            the dsi
	 * @throws Exception
	 *             the exception
	 * @Date 2018-09-03 09:00
	 */
	public void createConnection(DataSourceInfo dsi) throws Exception {
		dsi.setDriverUrl(ReverseUtil.getDriverPath(dsi.getDbType()));
		try {
			ReverseContext.wizard.setConnection(ReverseUtil.createConnection(dsi));
			ReverseContext.info = dsi;
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	/**
	 * 获取具有关联关系的表.
	 *
	 * @author mqfdy
	 * @param tableName
	 *            the table name
	 * @return the relative tables
	 * @Date 2018-09-03 09:00
	 */
	public List<Table> getRelativeTables(String tableName) {
		Table selectedTable = ReverseContext.allTables.get(tableName);
		JDBCReader reader = new JDBCReader();
		
//		boolean bool=reader.readConstraints(selectedTable);
//		if(bool){
//			return new ArrayList<Table>();
//		}
		try {
			reader.readPrimaryKey(ReverseContext.wizard.getConnection().getMetaData(), ReverseContext.info.getUserName(), selectedTable) ;
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			reader.readForeignKey(ReverseContext.wizard.getConnection(), ReverseContext.info.getUserName(), selectedTable);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		List<Table> relativeTables = ReverseUtil.getRelativeTables(selectedTable);
		for(Table rTable : relativeTables){
			//读取相关表列及主外键信息
			//if(rTable.getColumns().size()==0){//如果没有列信息,说明还未读取该表的列及主外键信息
				//reader.readColumns(rTable);
			if(rTable!=null){
				reader.readConstraints(rTable);
			}
				
			//}
		}
		
		if(ReverseUtil.isManyToManyTable(selectedTable)){//如果选中的表就是中间表，那么下面的步骤就不用走了
			return relativeTables;
		}
		
		List<Table> otherTables = new ArrayList<Table>();
		for(Table rTable : relativeTables){
			if(rTable!=null&&ReverseUtil.isManyToManyTable(rTable)){
				Table otherTable = null;
				Map<String, ForeignKey> fks = rTable.getForeignKeys();
				for(Map.Entry<String, ForeignKey> mfk : fks.entrySet()){
					ForeignKey fk = mfk.getValue();
					if(!fk.getReferencedTable().getName().equalsIgnoreCase(tableName)){
						otherTable = fk.getReferencedTable();
						break;
					}
				}
				if(otherTable != null){
					//if(otherTable.getColumns().size()==0){
						//reader.readColumns(otherTable);
						reader.readConstraints(otherTable);
					//}
					otherTables.add(otherTable);
				}
			}
		}
		
		relativeTables.addAll(otherTables);
		return relativeTables;
	}
	
//	/**
//	 * 查找出无主键的表
//	 * @param tableNames
//	 * @return
//	 */
//	public List<Table> findNoPkTable(List<String> tableNames){
//		ReverseContext.selectedTables.clear();
//		ReverseContext.lastTables.clear();
//		List<Table> noPkTables = new ArrayList<Table>();
//		for(String tableName : tableNames){
//			Table table = ReverseContext.allTables.get(tableName);
//			//缓存选择的表
//			ReverseContext.selectedTables.put(tableName, table);
//				
//			JDBCReader reader = new JDBCReader();
//			try {
//				reader.readColumn(table, reader.getUniqueCols(table));
//			} catch (SQLException e) {
//				throw new RuntimeException(e.getMessage());
//			}
//			reader.readConstraints(table);
//			
//			//缓存无主键表与复合主键表
//			if(ReverseUtil.isNoPkTable(table)){
//				ReverseContext.noPks.add(table.getName());
//				noPkTables.add(table);
//			}
//		}
//		
//		return noPkTables;
//	}
	
	/**
 * Find no pk table.
 *
 * @author mqfdy
 * @param tableNames
 *            the table names
 * @param monitor
 *            the monitor
 * @return the map
 * @Date 2018-09-03 09:00
 */
public Map<String, List<Table>> findNoPkTable(List<String> tableNames, IProgressMonitor monitor){
		ReverseContext.selectedTables.clear();
		ReverseContext.lastTables.clear();
		ignoredSpecial = false;
		
		Map<String, List<Table>> specialTables = new HashMap<String, List<Table>>();
		List<Table> noPkTables = new ArrayList<Table>();
		List<Table> mutilPkTables = new ArrayList<Table>();
		
		specialTables.put(ReverseContants.NOPKTABLE, noPkTables);
		specialTables.put(ReverseContants.MUTILPKTABLE, mutilPkTables);
		
		JDBCReader reader = new JDBCReader();
		for(String tableName : tableNames){
			Table table = ReverseContext.allTables.get(tableName);
			//缓存选择的表
			ReverseContext.selectedTables.put(tableName, table);
			
			reader.readConstraints(table);
			
			//缓存无主键表与复合主键表
			if(ReverseUtil.isNoPkTable(table)){
				ReverseContext.noPks.add(table.getName());
				noPkTables.add(table);
			}
			
			if(ReverseUtil.isMultiPkTable(table)){
				ReverseContext.noPks.add(table.getName());
				mutilPkTables.add(table);
			}
			
			String subTask = "正在分析表:  " + tableName;
			subTask       += "\n" + "共有" + tableNames.size() + "个表，已分析出不合规的表：" + (noPkTables.size() + mutilPkTables.size()) + "个"; 
			
			monitor.subTask(subTask);
			monitor.worked(1);
		}
		
		return specialTables;
	}
	
	/**
	 * 查找出复合主键的表.
	 *
	 * @author mqfdy
	 * @param tableNames
	 *            the table names
	 * @return the list
	 * @Date 2018-09-03 09:00
	 */
	public List<Table> findMutiPkTable(List<String> tableNames){
		List<Table> mutiPkTables = new ArrayList<Table>();
		for(String tableName : tableNames){
			Table table = ReverseContext.allTables.get(tableName);
			if(ReverseUtil.isMultiPkTable(table)){
				ReverseContext.noPks.add(table.getName());
				mutiPkTables.add(table);
			}
		}
		return mutiPkTables;
	}
	
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
	public List<Table> findMutiPkTable(List<String> tableNames, IProgressMonitor monitor){
		//monitor.setTaskName("正在分析含有复合主键的表  ......");
		List<Table> mutiPkTables = new ArrayList<Table>();
		for(String tableName : tableNames){
			Table table = ReverseContext.allTables.get(tableName);
			if(ReverseUtil.isMultiPkTable(table)){
				ReverseContext.noPks.add(table.getName());
				mutiPkTables.add(table);
			}
		}
		
		return mutiPkTables;
	}
	
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
	public void createNewBom(String filePath,String nameSpace,String modelName,String modelDisplayName){
		ReverseContext.OM_STORAGE_PATH = filePath+ReverseContants.SEPERATOR+nameSpace+ReverseContants.DOT+modelName+ReverseContants.DOT+"bom";
		ReverseUtil.createNewBom(nameSpace, modelName, modelDisplayName);
		
		if(ignoredSpecial){//忽略过特殊表
			ReverseContext.lastTables = new HashMap<String, Table>(ReverseContext.handleTables);
		}else{
			ReverseContext.lastTables = new HashMap<String, Table>(ReverseContext.selectedTables);
		}
	}
	
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
	public List<Table> fetchExistOm(String omPath) throws Exception{
		ReverseContext.OM_STORAGE_PATH = omPath;
		try {
			ReverseContext.bom = BomManager.xml2Model(omPath);
			
			BusinessModelUtil.assembReferenceObject(ReverseContext.bom,omPath);
			
		} catch (DocumentException e) {
			throw new RuntimeException("解析om文件出错", e);
		}
		ReverseUtil.getExitBcs();//存储已有om中业务实体
		
		List<Table> snTables = new ArrayList<Table>();
		for(Association ass : ReverseContext.bom.getAssociations()){
			if(ass.getAssociationType().equals(AssociationType.mult2mult.getValue())){//找多对多的关联关系
				if(ReverseContext.handleTables.isEmpty()){
					for(String tableName : ReverseContext.selectedTables.keySet()){
						if(tableName.equals(ass.getPersistencePloyParams().get(Association.RELATIONTABLENAME))){
							snTables.add(ReverseContext.allTables.get(tableName));
							break;
						}
					}
				}else{
					for(String tableName : ReverseContext.handleTables.keySet()){
						if(tableName.equals(ass.getPersistencePloyParams().get(Association.RELATIONTABLENAME))){
							snTables.add(ReverseContext.allTables.get(tableName));
							break;
						}
					}
				}
			}
		}
		
		for(BusinessClass bc : ReverseContext.bom.getBusinessClasses()){
			if(ReverseContext.handleTables.isEmpty()){
				for(String tableName : ReverseContext.selectedTables.keySet()){
					if(tableName.equalsIgnoreCase(bc.getTableName())){
						snTables.add(ReverseContext.allTables.get(tableName));
						break;
					}
				}
			}else{
				for(String tableName : ReverseContext.handleTables.keySet()){
					if(tableName.equalsIgnoreCase(bc.getTableName())){
						snTables.add(ReverseContext.allTables.get(tableName));
						break;
					}
				}
			}
		}
		return snTables;
	}
	
	/**
	 * 处理重复表.
	 *
	 * @author mqfdy
	 * @param sTables
	 *            the s tables
	 * @Date 2018-09-03 09:00
	 */
	public void handleDuplicateTable(List<SpecialTable> sTables){
		ignoredDuplication = false;
		if(ignoredSpecial){
			ReverseContext.lastTables = new HashMap<String, Table>(ReverseContext.handleTables);
		}else{
			ReverseContext.lastTables = new HashMap<String, Table>(ReverseContext.selectedTables);
		}
		
		for(SpecialTable sTable : sTables){
			Table table = ReverseContext.lastTables.get(sTable.getName());
			if(sTable.isHandle()){//选择覆盖
				for(BusinessClass bc :ReverseContext.bom.getBusinessClasses()){
					if(bc.getTableName().equalsIgnoreCase(table.getName())){
						ReverseContext.bom.getExtendAttributies().put(bc.getId(), bc.getTableName());//将选择覆盖的表对应的bc标志为被替换
						break;
					}
				}
			}else{//选择忽略
				ReverseContext.lastTables.remove(table.getName());
				ignoredDuplication = true;
			}
		}
	}
	
	/**
	 * 获取最终表集合，其中封装列信息.
	 *
	 * @author mqfdy
	 * @return the list
	 * @Date 2018-09-03 09:00
	 */
	public List<Table> fetchLastTables(){
		if(!ignoredSpecial && !ignoredDuplication && ReverseContext.lastTables.isEmpty()){//没经过特殊表处理 也没经过重复表处理
			ReverseContext.lastTables = new HashMap<String, Table>(ReverseContext.selectedTables);
		}else if(ignoredSpecial && !ignoredDuplication && ReverseContext.lastTables.isEmpty()){
			ReverseContext.lastTables = new HashMap<String, Table>(ReverseContext.handleTables);
		}
		
		List<Table> lastTables = new ArrayList<Table>();
		for(Map.Entry<String, Table> m : ReverseContext.lastTables.entrySet()){
			lastTables.add(m.getValue());
		}
		return lastTables;
	}
	
	/**
	 * 生成业务实体.
	 *
	 * @author mqfdy
	 * @param bom
	 *            the bom
	 * @Date 2018-09-03 09:00
	 */
	public void createBom(BusinessObjectModel bom){
		
		List<BusinessClass> bcList = bom.getBusinessClasses();
		for(BusinessClass bc : bcList){
			Table bcTable = ReverseContext.lastTables.get(bc.getTableName());
			if(bcTable != null){
				ReverseContext.btMap.put(bc, bcTable);
				ReverseContext.tbMap.put(bcTable, bc);
			}
		}
		
		JDBCBinder jb = new JDBCBinder();
		jb.createBusinessClasses();
		jb.createAssociation();
		
		BomManager.outputXmlFile(bom, ReverseContext.OM_STORAGE_PATH);
	}
	
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
	public void createBom(BusinessObjectModel bom, IProgressMonitor monitor,
			final PackageDispachPage pdPage) {

		List<BusinessClass> bcList = bom.getBusinessClasses();
		for (BusinessClass bc : bcList) {
			Table bcTable = ReverseContext.lastTables.get(bc.getTableName());
			if (bcTable != null) {
				ReverseContext.btMap.put(bc, bcTable);
				ReverseContext.tbMap.put(bcTable, bc);
			}
		}

		monitor.subTask("正在读取表信息  ......");
		JDBCBinder jb = new JDBCBinder();
		monitor.worked(1);
		monitor.subTask("正在将表信息转为业务实体对象  ......");
		jb.createBusinessClasses();
		monitor.worked(1);
		monitor.subTask("正在创建关联关系对象  ......");
		jb.createAssociation();
		monitor.worked(1);
		// 添加图元 并布局
		monitor.subTask("正在创建图元  ......");
		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				createDiagramElement(pdPage);
			}
		});
		monitor.worked(1);
		for (BusinessClass bc : bcList) {
			bc.setStereotype("1");
		}
		monitor.subTask("正在写入xml文件  ......");
		BomManager.outputXmlFile(bom, ReverseContext.OM_STORAGE_PATH);
		monitor.worked(1);
	}
	
	/**
	 * 处理特殊表 无主键表，自动加上一个主键。 复合主键表，自动改造为单一主键，其关联的子表中的复合外键改造为单一外键。.
	 *
	 * @author mqfdy
	 * @param sTables
	 *            the s tables
	 * @Date 2018-09-03 09:00
	 */
	public void handleSpecialTable(List<SpecialTable> sTables){
		ReverseUtil.copySepcialTables();
		ignoredSpecial = false;
		if(sTables != null){
			for(SpecialTable sTable : sTables){
				Table specialTable = ReverseContext.handleTables.get(sTable.getName());
				
				if(sTable.getProblemType() == IViewConstant.TYPE_NO_PK_TABLE){
					if(sTable.isHandle()){//无主键，选择自动处理
						if((!ReverseUtil.isMultiPkTable(specialTable)) && (!ReverseUtil.isNoPkTable(specialTable))){
							continue;
						}
						
						String pkName = ReverseUtil.createPkColumnName(specialTable);
						PrimaryKey pk = new PrimaryKey("PK_"+specialTable.getName());
						pk.setTable(specialTable);
						Column pkColumn = new Column(pkName);
						pkColumn.setSqlType("varchar2");
						pkColumn.setLength(64);
						pkColumn.setComment(pkName);
						pkColumn.setNullable(false);
						pkColumn.setUnique(true);
						pk.addColumn(pkColumn);
						
						specialTable.setPrimaryKey(pk);
						specialTable.getColumns().put(pkName, pkColumn);
					}else{//选择“忽略”
						ReverseContext.handleTables.remove(specialTable.getName());
						ignoredSpecial = true;
					}
				}else if(sTable.getProblemType() == IViewConstant.TYPE_MULTI_TABLE){
					//获取使用该表主键的子表集合
					Map<String, Table> fkTableMaps =  specialTable.getPrimaryKey().getForeignKeyTables();
					if(sTable.isHandle()){//复合主键，选择自动处理
						
						if((!ReverseUtil.isMultiPkTable(specialTable)) && (!ReverseUtil.isNoPkTable(specialTable))){
							continue;
						}
						
						//将复合主键转为单一主键（新加）
						PrimaryKey newPk = new PrimaryKey("PK_"+specialTable.getName());
						String pkColumnName = ReverseUtil.createPkColumnName(specialTable);
						Column pkColumn = new Column(pkColumnName);
						pkColumn.setSqlType("varchar2");
						pkColumn.setLength(64);
						pkColumn.setComment(pkColumnName);
						pkColumn.setNullable(false);
						pkColumn.setUnique(true);
						newPk.getColumns().add(pkColumn);
						newPk.setTable(specialTable);
						specialTable.setPrimaryKey(newPk);
						specialTable.getColumns().put(pkColumn.getName(), pkColumn);
						
						//子表需删除外键，及外键字段
						for(Map.Entry<String, Table> m1:fkTableMaps.entrySet()){
							Table childTable = m1.getValue();
							//清除子表复合外键字段
							Map<String, ForeignKey> foreignKeyMap = childTable.getForeignKeys();
							for(Map.Entry<String, ForeignKey> m2 : foreignKeyMap.entrySet()){
								ForeignKey fk = m2.getValue();
								if(fk.getReferencedTable().getName().equals(specialTable.getName())){
									List<Column> fkColumns = fk.getColumns();
									for(Column fkColumn :fkColumns){
										childTable.getColumns().remove(fkColumn.getName());
									}
								}
								childTable.getForeignKeys().remove(fk.getName());//清除外键
							}
							//子表添加新外键
							ForeignKey newfk = new ForeignKey();
							newfk.setTable(childTable);
							newfk.setName("FK_"+childTable.getName()+"_"+sTable.getName());
							newfk.setReferencedTable(specialTable);
							newfk.setReferencedColumn(specialTable.getPrimaryKey().getColumns().get(0));
							Column newFKColumn = new Column(specialTable.getName()+"_ID");
							newFKColumn.setSqlType("varchar2");
							newFKColumn.setLength(64);
							newFKColumn.setComment(pkColumnName);
							newFKColumn.setNullable(true);
							newFKColumn.setUnique(false);
							newfk.getColumns().add(newFKColumn);
							childTable.addForeignKey(newfk);
							childTable.addColumn(newFKColumn);
						}
					}else{//选择忽略
						for(Map.Entry<String, Table> m1:fkTableMaps.entrySet()){//子表需删除外键，及外键字段
							Table childTable = m1.getValue();
							
							if(childTable == null) {
								continue;
							}
							
							//清除子表复合外键字段
							Map<String, ForeignKey> foreignKeyMap = childTable.getForeignKeys();
							for(Map.Entry<String, ForeignKey> m2 : foreignKeyMap.entrySet()){
								ForeignKey fk = m2.getValue();
								if(fk.getReferencedTable().getName().equals(specialTable.getName())){
									List<Column> fkColumns = fk.getColumns();
									for(Column fkColumn :fkColumns){
										childTable.getColumns().remove(fkColumn.getName());
									}
									break;
								}
							}
							childTable.getForeignKeys().clear();//清除外键
						}
						ReverseContext.handleTables.remove(specialTable.getName());
						ignoredSpecial = true;
					}
				}
			}
		}
	}
	
	/**
	 * 反向工程完成后，释放内存.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	public void clearMemery() {
		if(ReverseContext.wizard!=null){
			ReverseUtil.closeConnection(ReverseContext.wizard.getConnection());
		}
		ReverseContext.wizard = null;
		ReverseContext.allTables.clear();
		ReverseContext.allViews.clear();
		ReverseContext.selectedTables.clear();
		ReverseContext.handleTables.clear();
		ReverseContext.lastTables.clear();
		ReverseContext.bom = null;
		ReverseContext.existBcs.clear();
		ReverseContext.bcIds.clear();
		ReverseContext.OM_STORAGE_PATH = null;
		ReverseContext.tbMap.clear();
		ReverseContext.btMap.clear();
		ReverseContext.info = null;
		
		ReverseContext.noPks.clear();
	}
	
	/**
	 * 写入数据源到指定路径.
	 *
	 * @author mqfdy
	 * @param dataSourceXMLPath
	 *            the data source XML path
	 * @param dataSourceInfo
	 *            the data source info
	 * @Date 2018-09-03 09:00
	 */
	public void writeDataSource(String dataSourceXMLPath, DataSourceInfo dataSourceInfo) {
		ReverseUtil.writeDatasourceXML(dataSourceXMLPath, dataSourceInfo);
	}
	
	/**
	 * Creates the diagram element.
	 *
	 * @author mqfdy
	 * @param pdPage
	 *            the pd page
	 * @Date 2018-09-03 09:00
	 */
	// 添加图元 并布局
		private void createDiagramElement(PackageDispachPage pdPage) {
			// 添加图元 并布局
			BusinessObjectModel bom = ReverseContext.bom;
			List<BusinessClass> newBuList = pdPage.getNewBuList();
			List<ModelPackage> newpkgList = new ArrayList<ModelPackage>();
			Map<ModelPackage, List<BusinessClass>> map = new HashMap<ModelPackage, List<BusinessClass>>();
			for (BusinessClass bu : newBuList) {
				ModelPackage pkg = bu.getBelongPackage();
				if (!newpkgList.contains(pkg)) {
					newpkgList.add(pkg);
					map.put(pkg, new ArrayList<BusinessClass>());
				}
				map.get(pkg).add(bu);
			}
			int layoutStyle = LayoutStyles.NO_LAYOUT_NODE_RESIZING;
			for (ModelPackage pkg : newpkgList) {
				List<BusinessClass> nonConBuList = new ArrayList<BusinessClass>();
				Shell shell = pdPage.getShell();
				GraphOm omGraph = new GraphOm(shell, SWT.NONE);
				Diagram dia = null;
				dia = getDiagram(pkg);
				if (dia == null)
					return;
				omGraph.setConnectionStyle(ZestStyles.CONNECTIONS_DIRECTED);
				// 计算已存在的图形位置信息-Y的最大值
				int h = 0;
				for (DiagramElement de : dia.getElements()) {
					// Object obj = bom.getModelElementById(de.getObjectId());
					// if((obj instanceof BusinessClass) || (obj instanceof
					// Enumeration)
					// || (obj instanceof Annotation)
					// || (obj instanceof DataTransferObject))
					if (de.getStyle().getPositionY() > 0
							&& de.getStyle().getHeight() > 0)
						h = (int) Math.max(h, de.getStyle().getPositionY()
								+ de.getStyle().getHeight());
				}

				boolean isNonCon = true;
				for (BusinessClass bu : map.get(pkg)) {
					isNonCon = true;
					for (Association as : bom.getAssociations()) {
						if(as.getClassA() == null || as.getClassB() == null) {
							continue;
						}
					
						if ((bu.getId().equals(as.getClassA().getId()) && map.get(pkg).contains(as.getClassB())) 
								|| (bu.getId().equals(as.getClassB().getId()) && map.get(pkg).contains(as.getClassA()))) {
							boolean isExsit = false;
							for (Object node : omGraph.getNodes()) {
								if (node instanceof GraphNode) {
									if (((GraphNode) node).getData() == bu) {
										isExsit = true;
									}
								}
							}
							if (!isExsit) {
								GraphNode node1 = new GraphNode(omGraph, ZestStyles.NODES_FISHEYE, bu);
								node1.setSize(150, 200);
								isNonCon = false;
							}
						}
					}
					
					if (isNonCon) {
						nonConBuList.add(bu);
					}
					
				}
				
				for (Association as : bom.getAssociations()) {
					if(as.getClassA() == null || as.getClassB() == null) {
						continue;
					}
					
					if (map.get(pkg).contains(as.getClassA())
							&& map.get(pkg).contains(as.getClassB())) {
						new GraphConnection(omGraph, SWT.NONE,
								AutoLayoutCommand.getNode(as.getClassA(), omGraph),
								AutoLayoutCommand.getNode(as.getClassB(), omGraph));
						DiagramElement ele = new DiagramElement();
						ElementStyle style = new ElementStyle();
						ele.setObjectId(as.getId());
						ele.setStyle(style);
						dia.addElement(ele);
					}
				}
				omGraph.setLayoutAlgorithm(new CompositeLayoutAlgorithm(
						layoutStyle, new LayoutAlgorithm[] {
								new GXTreeLayoutAlgorithm(layoutStyle),
								new ShiftDiagramLayoutAlgorithm(layoutStyle) }),
						true);
				omGraph.applyLayoutInternal();

//				int x = 0;
				h += 20;
				y = h;
//				int l = x;
				for (Object node : omGraph.getNodes()) {
					if (node instanceof GraphNode) {
						if (((GraphNode) node).getData() instanceof BusinessClass) {
							BusinessClass e = (BusinessClass) ((GraphNode) node)
									.getData();
							int ph = 200;
							int px = ((GraphNode) node).getLocation().x;
							int py = ((GraphNode) node).getLocation().y;
							if (py + ph + h > y)
								y = py + ph + h;
							DiagramElement ele = new DiagramElement();
							ElementStyle style = new ElementStyle();
							ele.setObjectId(e.getId());
							style.setPositionX(px/* + l */);
							style.setPositionY(py + h);
							style.setHeight(200);
							style.setWidth(150);
							ele.setStyle(style);
							dia.addElement(ele);
							// x = Math.max(x, px + l + 150);
						}
					}
				}
				layoutNoConnNode(nonConBuList, dia, y);
			}

		}

		/**
		 * Gets the diagram.
		 *
		 * @author mqfdy
		 * @param pkg
		 *            the pkg
		 * @return the diagram
		 * @Date 2018-09-03 09:00
		 */
		private Diagram getDiagram(ModelPackage pkg) {
			// TODO Auto-generated method stub
			for (AbstractModelElement ab : pkg.getChildren()) {
				if (ab instanceof Diagram) {
					return (Diagram) ab;
				}
			}
			return null;
		}

		/**
		 * 布局没有关联关系的节点.
		 *
		 * @author mqfdy
		 * @param nonConBuList
		 *            the non con bu list
		 * @param dia
		 *            the dia
		 * @param y2
		 *            the y 2
		 * @Date 2018-09-03 09:00
		 */
		private void layoutNoConnNode(List<BusinessClass> nonConBuList,
				Diagram dia, int y2) {
			int x = 10;
			int num = 0;
			int maxHeight = 200;
			y = y2 + 20;
			// 布局没有关联关系的节点
			for (BusinessClass e : nonConBuList) {
				if (e instanceof BusinessClass) {
					if (num > 4) {
						y = y + 20 + maxHeight;
						maxHeight = 0;
						x = 10;
						num = 0;
					}
					DiagramElement ele = new DiagramElement();
					ElementStyle style = new ElementStyle();
					ele.setObjectId(e.getId());
					style.setPositionX(x);
					style.setPositionY(y);
					style.setHeight(200);
					style.setWidth(150);
					ele.setStyle(style);
					dia.addElement(ele);
					num++;
					x = x + 10 + 150;
				}
			}
		}

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
		@Override
		public Map<String, List<Table>> findInValidTable(List<String> tableNames, IProgressMonitor monitor) {
			ReverseContext.selectedTables.clear();
			ReverseContext.lastTables.clear();
			ignoredSpecial = false;
			
			Map<String, List<Table>> specialTables = new HashMap<String, List<Table>>();
			List<Table> noPkTables = new ArrayList<Table>();
			List<Table> mutilPkTables = new ArrayList<Table>();
			List<Table> specialCharTables = new ArrayList<Table>();
			List<Table> startWithFigureTables = new ArrayList<Table>();
			
			specialTables.put(ReverseContants.NOPKTABLE, noPkTables);
			specialTables.put(ReverseContants.MUTILPKTABLE, mutilPkTables);
			specialTables.put(ReverseContants.SPECIAL_CHAR_TABLE, specialCharTables);
			specialTables.put(ReverseContants.START_WITH_FIGURE_TABLE, startWithFigureTables);
			
			JDBCReader reader = new JDBCReader();
			for(String tableName : tableNames){
				Table table = ReverseContext.allTables.get(tableName);
				//缓存选择的表
				ReverseContext.selectedTables.put(tableName, table);
				
				reader.readConstraints(table);
				
				//缓存无主键表与复合主键表
				if(ReverseUtil.isNoPkTable(table)){
					ReverseContext.noPks.add(table.getName());
					noPkTables.add(table);
				}
				
				if(ReverseUtil.isMultiPkTable(table)){
					ReverseContext.noPks.add(table.getName());
					mutilPkTables.add(table);
				}
				
				//缓存字段含有特殊字符的表
				if(ReverseUtil.isSpecialCharTable(table)){
					ReverseContext.specialChars.add(table.getName());
					specialCharTables.add(table);
				}
				//字段名称以数字开头
				if(ReverseUtil.startWithFigure(table)){
					ReverseContext.startWithFigures.add(table.getName());
					startWithFigureTables.add(table);
				}
				
				String subTask = "正在分析表:  " + tableName;
				subTask       += "\n" + "共有" + tableNames.size() + "个表，已分析出不合规的表：" + 
						     (noPkTables.size() + mutilPkTables.size() + specialCharTables.size() + 
						      startWithFigureTables.size()) + "个"; 
				
				monitor.subTask(subTask);
				monitor.worked(1);
			}
			
			return specialTables;
		}

		
}
