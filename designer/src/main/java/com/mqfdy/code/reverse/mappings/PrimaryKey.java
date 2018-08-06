package com.mqfdy.code.reverse.mappings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.mqfdy.code.reverse.ReverseContext;

public class PrimaryKey {

	private String name;
	private List<Column> columns = new ArrayList<Column>();
	private Table table;
	private Map<String ,Table> foreignKeyTables = new HashMap<String ,Table>();//当前主键表的外键表
	
	public PrimaryKey clone(Table newTable){
		
		PrimaryKey newPk = new PrimaryKey(this.name);
		newPk.setTable(newTable);
		newPk.setColumns(new ArrayList<Column>(this.columns));
		
		Map<String, Table> newFkTables = new HashMap<String, Table>();
		for(Map.Entry<String, Table> ms : this.foreignKeyTables.entrySet()){
			Table rTable = ms.getValue();
			Table newRtable = rTable.clone();
			newFkTables.put(newRtable.getName(), newRtable);
			ReverseContext.handleTables.put(newRtable.getName(), newRtable);
		}
		
		newPk.setForeignKeyTables(newFkTables);
		return newPk;
	}
	
	public Map<String, Table> getForeignKeyTables() {
		return foreignKeyTables;
	}

	public void setForeignKeyTables(Map<String, Table> foreignKeyTables) {
		this.foreignKeyTables = foreignKeyTables;
	}

	public PrimaryKey(String name){
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Iterator<Column> getColumnIterator() {
		return columns.iterator();
	}

	public void addColumn(Column column) {
		if ( !columns.contains( column ) ) columns.add( column );
	}

	public void addColumns(Iterator<Column> columnIterator) {
		
	}

	/**
	 * @param column
	 * @return true if this constraint already contains a column with same name.
	 */
	public boolean containsColumn(Column column) {
		return columns.contains( column );
	}

	public int getColumnSpan() {
		return columns.size();
	}

	public Column getColumn(int i) {
		return (Column) columns.get( i );
	}

	public Iterator<Column> columnIterator() {
		return columns.iterator();
	}

	public Table getTable() {
		return table;
	}

	public void setTable(Table table) {
		this.table = table;
	}

	public List<Column> getColumns() {
		return columns;
	}
	
	public void setColumns(List<Column> columns) {
		this.columns = columns;
	}

}
