package com.mqfdy.code.datasource.mapping;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class ForeignKey {

	private String name;
	private List<Column> columns = new ArrayList<Column>();
	private Table table;
	private Table referencedTable;
	private String referencedEntityName;
//	private boolean cascadeDeleteEnabled;
	private Column referencedColumn ;
	private String foreignTableName;//外键关联的表的表名
	private String foreignTableId;//外键关联的表的ID
	
	private String fkColumnName; //外键字段名称
	private String fkColumnJavaName; //外键字段名称
	
	public ForeignKey clone(Table newTable){
		ForeignKey newFk = new ForeignKey();
		newFk.setName(name);
		newFk.setTable(newTable);
		//Table newRcTable = referencedTable.clone();
		newFk.setReferencedTable(referencedTable);
		newFk.setColumns(new ArrayList<Column>(columns));
		return newFk;
	}

	public Table getReferencedTable() {
		return referencedTable;
	}

	public void setReferencedTable(Table referencedTable) {
		this.referencedTable = referencedTable;
	}

	public String getReferencedEntityName() {
		return referencedEntityName;
	}

	public void setReferencedEntityName(String referencedEntityName) {
		this.referencedEntityName = referencedEntityName;
	}

	public Column getReferencedColumn() {
		return referencedColumn;
	}

	public void setReferencedColumn(Column referencedColumn) {
		this.referencedColumn = referencedColumn;
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
		return columns.get( i );
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

	public String getForeignTableName() {
		return foreignTableName;
	}

	public void setForeignTableName(String foreignTableName) {
		this.foreignTableName = foreignTableName;
	}

	public String getForeignTableId() {
		return foreignTableId;
	}

	public void setForeignTableId(String foreignTableId) {
		this.foreignTableId = foreignTableId;
	}

	public String getFkColumnName() {
		return fkColumnName;
	}

	public void setFkColumnName(String fkColumnName) {
		this.fkColumnName = fkColumnName;
	}

	public String getFkColumnJavaName() {
		return fkColumnJavaName;
	}

	public void setFkColumnJavaName(String fkColumnJavaName) {
		this.fkColumnJavaName = fkColumnJavaName;
	}
}
