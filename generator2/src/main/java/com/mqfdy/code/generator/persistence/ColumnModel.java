package com.mqfdy.code.generator.persistence;

import com.mqfdy.code.datasource.mapping.Column;
import com.mqfdy.code.datasource.model.AbstractModel;

// TODO: Auto-generated Javadoc
/**
 * The Class ColumnModel.
 *
 * @author mqfdy
 */
public class ColumnModel extends AbstractModel implements IColumnModel {
	
	/** The column. */
	private final Column column;
	
	/** The persistence model. */
	private IPersistenceModel persistenceModel;
	
	/** The db name. */
	private String dbName;
	
	/** The java name. */
	private String javaName;
	
	/** The chinese name. */
	private String chineseName;
	
	/** The is not null. */
	private boolean isNotNull;

	/**
	 * Instantiates a new column model.
	 *
	 * @param tableModel
	 *            the table model
	 * @param column
	 *            the column
	 */
	public ColumnModel(final IPersistenceModel tableModel, final Column column) {
		super(tableModel, column.getName());
		this.column = column;
		this.persistenceModel = tableModel;
		dbName = column.getName();
		javaName = column.getJavaName();
		chineseName = column.getComment();
		this.isNotNull = !column.isNullable();
	}

	/**
	 * @see com.mqfdy.code.datasource.model.AbstractModel#getElementType()
	 * @return ColumnModel
	 */
	@Override
	public int getElementType() {
		return IPersistenceModelType.COLUMN_TYPE;
	}

	/**
	 * @see com.mqfdy.code.generator.persistence.IColumnModel#getPersistenceModel()
	 * @return ColumnModel
	 */
	public IPersistenceModel getPersistenceModel() {
		return persistenceModel;
	}

	/**
	 * @see com.mqfdy.code.generator.persistence.IColumnModel#getDBName()
	 * @return ColumnModel
	 */
	public String getDBName() {
		return dbName;
	}

	/**
	 * @see com.mqfdy.code.generator.persistence.IColumnModel#getJavaName()
	 * @return ColumnModel
	 */
	public String getJavaName() {
		return javaName;
	}

	/**
	 * @see com.mqfdy.code.generator.persistence.IColumnModel#getChineseName()
	 * @return ColumnModel
	 */
	public String getChineseName() {
		return chineseName;
	}

	/**
	 * @see com.mqfdy.code.generator.persistence.IColumnModel#setJavaName(java.lang.String)
	 * @param javaName ColumnModel
	 */
	public void setJavaName(String javaName) {
		this.javaName = javaName;

	}

	/**
	 * @see com.mqfdy.code.generator.persistence.IColumnModel#isNotNull()
	 * @return ColumnModel
	 */
	public boolean isNotNull() {
		return isNotNull;
	}

	/**
	 * @see com.mqfdy.code.generator.persistence.IColumnModel#getJavaType()
	 * @return ColumnModel
	 */
	public String getJavaType() {
		return column.getJavaType();
	}

	/**
	 * @see com.mqfdy.code.generator.persistence.IColumnModel#isPrimaryKey()
	 * @return ColumnModel
	 */
	public boolean isPrimaryKey() {
		return column.isPrimaryKey();
	}

	/**
	 * @see com.mqfdy.code.generator.persistence.IColumnModel#isForeignKey()
	 * @return ColumnModel
	 */
	public boolean isForeignKey() {
//		return column.isForeignKey();
		return false;
	}

	/**
	 * @see com.mqfdy.code.generator.persistence.IColumnModel#getSize()
	 * @return ColumnModel
	 */
	public String getSize() {
		return column.getLength().toString();
	}

	/**
	 * @see com.mqfdy.code.generator.persistence.IColumnModel#getColumn()
	 * @return ColumnModel
	 */
	public Column getColumn() {
		return this.column;
	}

	/**
	 * @see com.mqfdy.code.datasource.model.AbstractModel#getAdapter(java.lang.Class)
	 * @param adapter
	 * @return ColumnModel
	 */
	@Override
	public <T> T getAdapter(Class<T> adapter) {
		// TODO Auto-generated method stub
		return null;
	}

}
