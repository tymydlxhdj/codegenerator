package com.mqfdy.code.generator.persistence;

import com.mqfdy.code.datasource.mapping.Column;
import com.mqfdy.code.datasource.model.AbstractModel;

public class ColumnModel extends AbstractModel implements IColumnModel {
	
	private final Column column;
	
	private IPersistenceModel persistenceModel;
	
	private String dbName;
	
	private String javaName;
	
	private String chineseName;
	
	private boolean isNotNull;

	public ColumnModel(final IPersistenceModel tableModel, final Column column) {
		super(tableModel, column.getName());
		this.column = column;
		this.persistenceModel = tableModel;
		dbName = column.getName();
		javaName = column.getJavaName();
		chineseName = column.getComment();
		this.isNotNull = !column.isNullable();
	}

	@Override
	public int getElementType() {
		return IPersistenceModelType.COLUMN_TYPE;
	}

	public IPersistenceModel getPersistenceModel() {
		return persistenceModel;
	}

	public String getDBName() {
		return dbName;
	}

	public String getJavaName() {
		return javaName;
	}

	public String getChineseName() {
		return chineseName;
	}

	public void setJavaName(String javaName) {
		this.javaName = javaName;

	}

	public boolean isNotNull() {
		return isNotNull;
	}

	public String getJavaType() {
		return column.getJavaType();
	}

	public boolean isPrimaryKey() {
		return column.isPrimaryKey();
	}

	public boolean isForeignKey() {
//		return column.isForeignKey();
		return false;
	}

	public String getSize() {
		return column.getLength().toString();
	}

	public Column getColumn() {
		return this.column;
	}

	@Override
	public <T> T getAdapter(Class<T> adapter) {
		// TODO Auto-generated method stub
		return null;
	}

}
