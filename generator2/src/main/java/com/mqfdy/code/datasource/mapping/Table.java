package com.mqfdy.code.datasource.mapping;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.mqfdy.code.generator.utils.StringUtils;

public class Table implements Comparator<Table>{
	
	/**
	 * 表ID
	 */
	private String id;
	
	/**
	 * 表名
	 */
	private String name;
	
	/**
	 * 列集合
	 */
	private Map<String, Column> columns = new LinkedHashMap<String, Column>();
	
	/**
	 * 主键对象
	 */
	private PrimaryKey primaryKey;
	
	/**
	 * 外键集合
	 */
	private Map<String ,ForeignKey> foreignKeys = new HashMap<String ,ForeignKey>();
	
	/**
	 * 注释
	 */
	private String comment;
	
	/**
	 * javaName
	 */
	private String javaName;
	
	/**
	 * 数据库表所属Schema名
	 */
	private String schemaName;

	/**
	 * 联合主键生成策略
	 */
	private String compositeMode;
	
	/**
	 * 联合主键属性名称
	 */
	private String compositeName;
	
	/**
	 * 联合主键类名
	 */
	private String compositeClass;
	
	/**
	 * 单主键生成策略
	 */
	private String idMethod;
	
	/**
	 * 是否生成PO类
	 */
	private boolean generatePO = true;

	public Table() {
		
	}
	
	public boolean isGeneratePO() {
		return generatePO;
	}

	public boolean getGeneratePO() {
		return generatePO;
	}
	
	public void setGeneratePO(boolean generatePO) {
		this.generatePO = generatePO;
	}
	
	public Table(String tbName) {
		this();
		setName(tbName);
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void addColumn(Column column){
		
		columns.put(column.getName(), column);
	}
	
	public Column getColumn(String colName){
		if(StringUtils.isEmpty(colName)){
			return null;
		}
		return (Column) columns.get(colName);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public PrimaryKey getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(PrimaryKey primaryKey) {
		this.primaryKey = primaryKey;
	}

	public Map<String ,ForeignKey> getForeignKeys() {
		return foreignKeys;
	}

	public void setForeignKeys(Map<String ,ForeignKey> foreignKeys) {
		this.foreignKeys = foreignKeys;
	}
	
	public void addForeignKey(ForeignKey fk){
		this.foreignKeys.put(fk.getName(), fk);
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
	public Map<String, Column> getColumns() {
		return columns;
	}

	public void setColumns(Map<String, Column> columns) {
		this.columns = columns;
	}

	public int compare(Table o1, Table o2) {
		
		return o1.getName().compareTo(o2.getName());
	}

	public String getSchemaName() {
		return schemaName;
	}

	public void setSchemaName(String schemaName) {
		this.schemaName = schemaName;
	}

	public String getJavaName() {
		return javaName;
	}

	public void setJavaName(String javaName) {
		this.javaName = javaName;
	}

	public String getCompositeMode() {
		return compositeMode;
	}

	public void setCompositeMode(String compositeMode) {
		this.compositeMode = compositeMode;
	}

	public String getCompositeName() {
		return compositeName;
	}

	public void setCompositeName(String compositeName) {
		this.compositeName = compositeName;
	}

	public String getCompositeClass() {
		return compositeClass;
	}

	public void setCompositeClass(String compositeClass) {
		this.compositeClass = compositeClass;
	}

	public String getIdMethod() {
		return idMethod;
	}

	public void setIdMethod(String idMethod) {
		this.idMethod = idMethod;
	}
}
