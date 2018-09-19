package com.mqfdy.code.datasource.mapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

// TODO: Auto-generated Javadoc
/**
 * The Class PrimaryKey.
 *
 * @author mqfdy
 */
public class PrimaryKey {

	/** The name. */
	private String name;
	
	/** The columns. */
	private List<Column> columns = new ArrayList<Column>();
	
	/** The table. */
	private Table table;
	
	/** The foreign key tables. */
	private Map<String ,Table> foreignKeyTables = new HashMap<String ,Table>();//当前主键表的外键表
	
	/**
	 * Gets the foreign key tables.
	 *
	 * @author mqfdy
	 * @return the foreign key tables
	 * @Date 2018-09-03 09:00
	 */
	public Map<String, Table> getForeignKeyTables() {
		return foreignKeyTables;
	}

	/**
	 * Sets the foreign key tables.
	 *
	 * @author mqfdy
	 * @param foreignKeyTables
	 *            the foreign key tables
	 * @Date 2018-09-03 09:00
	 */
	public void setForeignKeyTables(Map<String, Table> foreignKeyTables) {
		this.foreignKeyTables = foreignKeyTables;
	}

	/**
	 * Instantiates a new primary key.
	 *
	 * @param name
	 *            the name
	 */
	public PrimaryKey(String name){
		this.name = name;
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
	 * @return true, if successful
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
		return (Column) columns.get( i );
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

}
