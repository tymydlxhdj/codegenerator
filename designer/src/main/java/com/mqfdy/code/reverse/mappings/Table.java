package com.mqfdy.code.reverse.mappings;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.mqfdy.code.model.utils.StringUtil;

// TODO: Auto-generated Javadoc
/**
 * The Class Table.
 *
 * @author mqfdy
 */
public class Table implements Comparator<Table>{
	
	/** The id. */
	private String id;
	
	/** The name. */
	private String name;
	
	/** The columns. */
	private Map<String, Column> columns = new LinkedHashMap<String, Column>();
	
	/** The primary key. */
	private PrimaryKey primaryKey;
	
	/** The foreign keys. */
	private Map<String ,ForeignKey> foreignKeys = new HashMap<String ,ForeignKey>();
	
	/** The comment. */
	private String comment;
	
	/** The schema name. */
	private String schemaName;
	
	/**
	 * @return
	 */
	public Table clone(){
		Table newTable = new Table(this.name);
		newTable.setComment(this.comment);
		newTable.setColumns(new HashMap<String, Column>(this.columns));
		
		
		//拷贝主键
		copyPrimaryKey(newTable);
		//拷贝外键
		copyForeignKey(newTable);
		return newTable;
	}

	/**
	 * Copy foreign key.
	 *
	 * @author mqfdy
	 * @param newTable
	 *            the new table
	 * @Date 2018-09-03 09:00
	 */
	//拷贝外键
	private void copyForeignKey(Table newTable) {
		Map<String, ForeignKey> newForeignKeys= new HashMap<String, ForeignKey>();
		for(Map.Entry<String, ForeignKey> oldFks : this.foreignKeys.entrySet()){
			ForeignKey oldFk = oldFks.getValue();
			ForeignKey newFk = oldFk.clone(newTable);
			newForeignKeys.put(newFk.getName(), newFk);
		}
		newTable.setForeignKeys(newForeignKeys);
	}

	/**
	 * Copy primary key.
	 *
	 * @author mqfdy
	 * @param newTable
	 *            the new table
	 * @Date 2018-09-03 09:00
	 */
	//拷贝主键
	private void copyPrimaryKey(Table newTable) {
		if(this.primaryKey != null){
			PrimaryKey newPk = this.primaryKey.clone(newTable);
			newTable.setPrimaryKey(newPk);
		}
		
		
	}

	/**
	 * Instantiates a new table.
	 */
	public Table() {
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
		if(StringUtil.isEmpty(colName)){
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
	 * @author mqfdy
	 * @param o1
	 *            the o 1
	 * @param o2
	 *            the o 2
	 * @return the int
	 * @Date 2018-09-03 09:00
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
	
}
