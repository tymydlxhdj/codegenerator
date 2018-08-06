package com.mqfdy.code.reverse.mappings;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import com.mqfdy.code.model.utils.StringUtil;

public class View {

	private String name;
	private Map<String, Column> columns = new LinkedHashMap<String, Column>();
	private String comment;
	
	public View() {
	}

	public View(String tbName) {
		this();
		setName(tbName);
	}
	
	public void addColumn(Column column){
		
		columns.put(column.getName().toUpperCase(Locale.getDefault()), column);
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
}
