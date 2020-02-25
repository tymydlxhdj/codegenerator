package com.mqfdy.code.datasource.mapping;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.mqfdy.code.generator.utils.StringUtils;

// TODO: Auto-generated Javadoc
/**
 * The Class Table.
 *
 * @author mqfdy
 */
public class Table implements Comparator<Table>{
	
	/** 表ID. */
	private String id;
	
	/** 表名. */
	private String name;
	
	/** 列集合. */
	private Map<String, Column> columns = new LinkedHashMap<String, Column>();
	
	/** 主键对象. */
	private PrimaryKey primaryKey;
	
	/** 外键集合. */
	private Map<String ,ForeignKey> foreignKeys = new HashMap<String ,ForeignKey>();
	
	/** 注释. */
	private String comment;
	
	/** javaName. */
	private String javaName;
	
	/** 数据库表所属Schema名. */
	private String schemaName;

	/** 联合主键生成策略. */
	private String compositeMode;
	
	/** 联合主键属性名称. */
	private String compositeName;
	
	/** 联合主键类名. */
	private String compositeClass;
	
	/** 单主键生成策略. */
	private String idMethod;
	
	/** 是否生成PO类. */
	private boolean generatePO = true;

	/**
	 * Instantiates a new table.
	 */
	public Table() {
		
	}
	
	/**
	 * Checks if is generate PO.
	 *
	 * @author mqfdy
	 * @return true, if is generate PO
	 * @Date 2018-09-03 09:00
	 */
	public boolean isGeneratePO() {
		return generatePO;
	}

	/**
	 * Gets the generate PO.
	 *
	 * @author mqfdy
	 * @return the generate PO
	 * @Date 2018-09-03 09:00
	 */
	public boolean getGeneratePO() {
		return generatePO;
	}
	
	/**
	 * Sets the generate PO.
	 *
	 * @author mqfdy
	 * @param generatePO
	 *            the new generate PO
	 * @Date 2018-09-03 09:00
	 */
	public void setGeneratePO(boolean generatePO) {
		this.generatePO = generatePO;
	}
	
	/**
	 * Instantiates a new table.
	 *
	 * @param tbName
	 *            the tb name
	 */
	public Table(String tbName) {
		this();
		setName(tbName);
	}
	
	/**
	 * Gets the id.
	 *
	 * @author mqfdy
	 * @return the id
	 * @Date 2018-09-03 09:00
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @author mqfdy
	 * @param id
	 *            the new id
	 * @Date 2018-09-03 09:00
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Adds the column.
	 *
	 * @author mqfdy
	 * @param column
	 *            the column
	 * @Date 2018-09-03 09:00
	 */
	public void addColumn(Column column){
		
		columns.put(column.getName(), column);
	}
	
	/**
	 * Gets the column.
	 *
	 * @author mqfdy
	 * @param colName
	 *            the col name
	 * @return the column
	 * @Date 2018-09-03 09:00
	 */
	public Column getColumn(String colName){
		if(StringUtils.isEmpty(colName)){
			return null;
		}
		return (Column) columns.get(colName);
	}

	/**
	 * Gets the name.
	 *
	 * @author mqfdy
	 * @return the name
	 * @Date 2018-09-03 09:00
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @author mqfdy
	 * @param name
	 *            the new name
	 * @Date 2018-09-03 09:00
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the primary key.
	 *
	 * @author mqfdy
	 * @return the primary key
	 * @Date 2018-09-03 09:00
	 */
	public PrimaryKey getPrimaryKey() {
		return primaryKey;
	}

	/**
	 * Sets the primary key.
	 *
	 * @author mqfdy
	 * @param primaryKey
	 *            the new primary key
	 * @Date 2018-09-03 09:00
	 */
	public void setPrimaryKey(PrimaryKey primaryKey) {
		this.primaryKey = primaryKey;
	}

	/**
	 * Gets the foreign keys.
	 *
	 * @author mqfdy
	 * @return the foreign keys
	 * @Date 2018-09-03 09:00
	 */
	public Map<String ,ForeignKey> getForeignKeys() {
		return foreignKeys;
	}

	/**
	 * Sets the foreign keys.
	 *
	 * @author mqfdy
	 * @param foreignKeys
	 *            the foreign keys
	 * @Date 2018-09-03 09:00
	 */
	public void setForeignKeys(Map<String ,ForeignKey> foreignKeys) {
		this.foreignKeys = foreignKeys;
	}
	
	/**
	 * Adds the foreign key.
	 *
	 * @author mqfdy
	 * @param fk
	 *            the fk
	 * @Date 2018-09-03 09:00
	 */
	public void addForeignKey(ForeignKey fk){
		this.foreignKeys.put(fk.getName(), fk);
	}

	/**
	 * Gets the comment.
	 *
	 * @author mqfdy
	 * @return the comment
	 * @Date 2018-09-03 09:00
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * Sets the comment.
	 *
	 * @author mqfdy
	 * @param comment
	 *            the new comment
	 * @Date 2018-09-03 09:00
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	/**
	 * Gets the columns.
	 *
	 * @author mqfdy
	 * @return the columns
	 * @Date 2018-09-03 09:00
	 */
	public Map<String, Column> getColumns() {
		return columns;
	}

	/**
	 * Sets the columns.
	 *
	 * @author mqfdy
	 * @param columns
	 *            the columns
	 * @Date 2018-09-03 09:00
	 */
	public void setColumns(Map<String, Column> columns) {
		this.columns = columns;
	}

	/**
	 * Compare.
	 *
	 * @param o1
	 *            the o 1
	 * @param o2
	 *            the o 2
	 * @return Table
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	public int compare(Table o1, Table o2) {
		
		return o1.getName().compareTo(o2.getName());
	}

	/**
	 * Gets the schema name.
	 *
	 * @author mqfdy
	 * @return the schema name
	 * @Date 2018-09-03 09:00
	 */
	public String getSchemaName() {
		return schemaName;
	}

	/**
	 * Sets the schema name.
	 *
	 * @author mqfdy
	 * @param schemaName
	 *            the new schema name
	 * @Date 2018-09-03 09:00
	 */
	public void setSchemaName(String schemaName) {
		this.schemaName = schemaName;
	}

	/**
	 * Gets the java name.
	 *
	 * @author mqfdy
	 * @return the java name
	 * @Date 2018-09-03 09:00
	 */
	public String getJavaName() {
		return javaName;
	}

	/**
	 * Sets the java name.
	 *
	 * @author mqfdy
	 * @param javaName
	 *            the new java name
	 * @Date 2018-09-03 09:00
	 */
	public void setJavaName(String javaName) {
		this.javaName = javaName;
	}

	/**
	 * Gets the composite mode.
	 *
	 * @author mqfdy
	 * @return the composite mode
	 * @Date 2018-09-03 09:00
	 */
	public String getCompositeMode() {
		return compositeMode;
	}

	/**
	 * Sets the composite mode.
	 *
	 * @author mqfdy
	 * @param compositeMode
	 *            the new composite mode
	 * @Date 2018-09-03 09:00
	 */
	public void setCompositeMode(String compositeMode) {
		this.compositeMode = compositeMode;
	}

	/**
	 * Gets the composite name.
	 *
	 * @author mqfdy
	 * @return the composite name
	 * @Date 2018-09-03 09:00
	 */
	public String getCompositeName() {
		return compositeName;
	}

	/**
	 * Sets the composite name.
	 *
	 * @author mqfdy
	 * @param compositeName
	 *            the new composite name
	 * @Date 2018-09-03 09:00
	 */
	public void setCompositeName(String compositeName) {
		this.compositeName = compositeName;
	}

	/**
	 * Gets the composite class.
	 *
	 * @author mqfdy
	 * @return the composite class
	 * @Date 2018-09-03 09:00
	 */
	public String getCompositeClass() {
		return compositeClass;
	}

	/**
	 * Sets the composite class.
	 *
	 * @author mqfdy
	 * @param compositeClass
	 *            the new composite class
	 * @Date 2018-09-03 09:00
	 */
	public void setCompositeClass(String compositeClass) {
		this.compositeClass = compositeClass;
	}

	/**
	 * Gets the id method.
	 *
	 * @author mqfdy
	 * @return the id method
	 * @Date 2018-09-03 09:00
	 */
	public String getIdMethod() {
		return idMethod;
	}

	/**
	 * Sets the id method.
	 *
	 * @author mqfdy
	 * @param idMethod
	 *            the new id method
	 * @Date 2018-09-03 09:00
	 */
	public void setIdMethod(String idMethod) {
		this.idMethod = idMethod;
	}
}
