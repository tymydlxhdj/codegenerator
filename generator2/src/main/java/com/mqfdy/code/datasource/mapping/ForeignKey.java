package com.mqfdy.code.datasource.mapping;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


// TODO: Auto-generated Javadoc
/**
 * The Class ForeignKey.
 *
 * @author mqfdy
 */
public class ForeignKey {

	/** The name. */
	private String name;
	
	/** The columns. */
	private List<Column> columns = new ArrayList<Column>();
	
	/** The table. */
	private Table table;
	
	/** The referenced table. */
	private Table referencedTable;
	
	/** The referenced entity name. */
	private String referencedEntityName;

/** The referenced column. */
//	private boolean cascadeDeleteEnabled;
	private Column referencedColumn ;
	
	/** The foreign table name. */
	private String foreignTableName;//外键关联的表的表名
	
	/** The foreign table id. */
	private String foreignTableId;//外键关联的表的ID
	
	/** The fk column name. */
	private String fkColumnName; //外键字段名称
	
	/** The fk column java name. */
	private String fkColumnJavaName; //外键字段名称
	
	/**
	 * Clone.
	 *
	 * @author mqfdy
	 * @param newTable
	 *            the new table
	 * @return the foreign key
	 * @Date 2018-09-03 09:00
	 */
	public ForeignKey clone(Table newTable){
		ForeignKey newFk = new ForeignKey();
		newFk.setName(name);
		newFk.setTable(newTable);
		//Table newRcTable = referencedTable.clone();
		newFk.setReferencedTable(referencedTable);
		newFk.setColumns(new ArrayList<Column>(columns));
		return newFk;
	}

	/**
	 * Gets the referenced table.
	 *
	 * @author mqfdy
	 * @return the referenced table
	 * @Date 2018-09-03 09:00
	 */
	public Table getReferencedTable() {
		return referencedTable;
	}

	/**
	 * Sets the referenced table.
	 *
	 * @author mqfdy
	 * @param referencedTable
	 *            the new referenced table
	 * @Date 2018-09-03 09:00
	 */
	public void setReferencedTable(Table referencedTable) {
		this.referencedTable = referencedTable;
	}

	/**
	 * Gets the referenced entity name.
	 *
	 * @author mqfdy
	 * @return the referenced entity name
	 * @Date 2018-09-03 09:00
	 */
	public String getReferencedEntityName() {
		return referencedEntityName;
	}

	/**
	 * Sets the referenced entity name.
	 *
	 * @author mqfdy
	 * @param referencedEntityName
	 *            the new referenced entity name
	 * @Date 2018-09-03 09:00
	 */
	public void setReferencedEntityName(String referencedEntityName) {
		this.referencedEntityName = referencedEntityName;
	}

	/**
	 * Gets the referenced column.
	 *
	 * @author mqfdy
	 * @return the referenced column
	 * @Date 2018-09-03 09:00
	 */
	public Column getReferencedColumn() {
		return referencedColumn;
	}

	/**
	 * Sets the referenced column.
	 *
	 * @author mqfdy
	 * @param referencedColumn
	 *            the new referenced column
	 * @Date 2018-09-03 09:00
	 */
	public void setReferencedColumn(Column referencedColumn) {
		this.referencedColumn = referencedColumn;
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
	 * Gets the column iterator.
	 *
	 * @author mqfdy
	 * @return the column iterator
	 * @Date 2018-09-03 09:00
	 */
	public Iterator<Column> getColumnIterator() {
		return columns.iterator();
	}

	/**
	 * Adds the column.
	 *
	 * @author mqfdy
	 * @param column
	 *            the column
	 * @Date 2018-09-03 09:00
	 */
	public void addColumn(Column column) {
		if ( !columns.contains( column ) ) columns.add( column );
	}

	/**
	 * Adds the columns.
	 *
	 * @author mqfdy
	 * @param columnIterator
	 *            the column iterator
	 * @Date 2018-09-03 09:00
	 */
	public void addColumns(Iterator<Column> columnIterator) {
		
	}

	/**
	 * Contains column.
	 *
	 * @author mqfdy
	 * @param column
	 *            the column
	 * @return true if this constraint already contains a column with same name.
	 * @Date 2018-09-03 09:00
	 */
	public boolean containsColumn(Column column) {
		return columns.contains( column );
	}

	/**
	 * Gets the column span.
	 *
	 * @author mqfdy
	 * @return the column span
	 * @Date 2018-09-03 09:00
	 */
	public int getColumnSpan() {
		return columns.size();
	}

	/**
	 * Gets the column.
	 *
	 * @author mqfdy
	 * @param i
	 *            the i
	 * @return the column
	 * @Date 2018-09-03 09:00
	 */
	public Column getColumn(int i) {
		return columns.get( i );
	}

	/**
	 * Column iterator.
	 *
	 * @author mqfdy
	 * @return the iterator
	 * @Date 2018-09-03 09:00
	 */
	public Iterator<Column> columnIterator() {
		return columns.iterator();
	}

	/**
	 * Gets the table.
	 *
	 * @author mqfdy
	 * @return the table
	 * @Date 2018-09-03 09:00
	 */
	public Table getTable() {
		return table;
	}

	/**
	 * Sets the table.
	 *
	 * @author mqfdy
	 * @param table
	 *            the new table
	 * @Date 2018-09-03 09:00
	 */
	public void setTable(Table table) {
		this.table = table;
	}

	/**
	 * Gets the columns.
	 *
	 * @author mqfdy
	 * @return the columns
	 * @Date 2018-09-03 09:00
	 */
	public List<Column> getColumns() {
		return columns;
	}
	
	/**
	 * Sets the columns.
	 *
	 * @author mqfdy
	 * @param columns
	 *            the new columns
	 * @Date 2018-09-03 09:00
	 */
	public void setColumns(List<Column> columns) {
		this.columns = columns;
	}

	/**
	 * Gets the foreign table name.
	 *
	 * @author mqfdy
	 * @return the foreign table name
	 * @Date 2018-09-03 09:00
	 */
	public String getForeignTableName() {
		return foreignTableName;
	}

	/**
	 * Sets the foreign table name.
	 *
	 * @author mqfdy
	 * @param foreignTableName
	 *            the new foreign table name
	 * @Date 2018-09-03 09:00
	 */
	public void setForeignTableName(String foreignTableName) {
		this.foreignTableName = foreignTableName;
	}

	/**
	 * Gets the foreign table id.
	 *
	 * @author mqfdy
	 * @return the foreign table id
	 * @Date 2018-09-03 09:00
	 */
	public String getForeignTableId() {
		return foreignTableId;
	}

	/**
	 * Sets the foreign table id.
	 *
	 * @author mqfdy
	 * @param foreignTableId
	 *            the new foreign table id
	 * @Date 2018-09-03 09:00
	 */
	public void setForeignTableId(String foreignTableId) {
		this.foreignTableId = foreignTableId;
	}

	/**
	 * Gets the fk column name.
	 *
	 * @author mqfdy
	 * @return the fk column name
	 * @Date 2018-09-03 09:00
	 */
	public String getFkColumnName() {
		return fkColumnName;
	}

	/**
	 * Sets the fk column name.
	 *
	 * @author mqfdy
	 * @param fkColumnName
	 *            the new fk column name
	 * @Date 2018-09-03 09:00
	 */
	public void setFkColumnName(String fkColumnName) {
		this.fkColumnName = fkColumnName;
	}

	/**
	 * Gets the fk column java name.
	 *
	 * @author mqfdy
	 * @return the fk column java name
	 * @Date 2018-09-03 09:00
	 */
	public String getFkColumnJavaName() {
		return fkColumnJavaName;
	}

	/**
	 * Sets the fk column java name.
	 *
	 * @author mqfdy
	 * @param fkColumnJavaName
	 *            the new fk column java name
	 * @Date 2018-09-03 09:00
	 */
	public void setFkColumnJavaName(String fkColumnJavaName) {
		this.fkColumnJavaName = fkColumnJavaName;
	}
}
