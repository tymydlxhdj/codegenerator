package com.mqfdy.code.reverse.mappings;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.mqfdy.code.model.utils.StringUtil;

public class Table implements Comparator<Table>{
	private String id;
	private String name;
	private Map<String, Column> columns = new LinkedHashMap<String, Column>();
	private PrimaryKey primaryKey;
	private Map<String ,ForeignKey> foreignKeys = new HashMap<String ,ForeignKey>();
	private String comment;
	private String schemaName;
	
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

	//拷贝主键
	private void copyPrimaryKey(Table newTable) {
		if(this.primaryKey != null){
			PrimaryKey newPk = this.primaryKey.clone(newTable);
			newTable.setPrimaryKey(newPk);
		}
		
		
	}

	public Table() {
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
		if(StringUtil.isEmpty(colName)){
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
	
}
