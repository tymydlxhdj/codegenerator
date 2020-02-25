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
	 * Gets the element type.
	 *
	 * @return ColumnModel
	 * @see com.mqfdy.code.datasource.model.AbstractModel#getElementType()
	 */
	@Override
	public int getElementType() {
		return IPersistenceModelType.COLUMN_TYPE;
	}

	/**
	 * Gets the persistence model.
	 *
	 * @return ColumnModel
	 * @see com.mqfdy.code.generator.persistence.IColumnModel#getPersistenceModel()
	 */
	public IPersistenceModel getPersistenceModel() {
		return persistenceModel;
	}

	/**
	 * Gets the DB name.
	 *
	 * @return ColumnModel
	 * @see com.mqfdy.code.generator.persistence.IColumnModel#getDBName()
	 */
	public String getDBName() {
		return dbName;
	}

	/**
	 * Gets the java name.
	 *
	 * @return ColumnModel
	 * @see com.mqfdy.code.generator.persistence.IColumnModel#getJavaName()
	 */
	public String getJavaName() {
		return javaName;
	}

	/**
	 * Gets the chinese name.
	 *
	 * @return ColumnModel
	 * @see com.mqfdy.code.generator.persistence.IColumnModel#getChineseName()
	 */
	public String getChineseName() {
		return chineseName;
	}

	/**
	 * Sets the java name.
	 *
	 * @param javaName
	 *            ColumnModel
	 * @see com.mqfdy.code.generator.persistence.IColumnModel#setJavaName(java.lang.String)
	 */
	public void setJavaName(String javaName) {
		this.javaName = javaName;

	}

	/**
	 * Checks if is not null.
	 *
	 * @return ColumnModel
	 * @see com.mqfdy.code.generator.persistence.IColumnModel#isNotNull()
	 */
	public boolean isNotNull() {
		return isNotNull;
	}

	/**
	 * Gets the java type.
	 *
	 * @return ColumnModel
	 * @see com.mqfdy.code.generator.persistence.IColumnModel#getJavaType()
	 */
	public String getJavaType() {
		return column.getJavaType();
	}

	/**
	 * Checks if is primary key.
	 *
	 * @return ColumnModel
	 * @see com.mqfdy.code.generator.persistence.IColumnModel#isPrimaryKey()
	 */
	public boolean isPrimaryKey() {
		return column.isPrimaryKey();
	}

	/**
	 * Checks if is foreign key.
	 *
	 * @return ColumnModel
	 * @see com.mqfdy.code.generator.persistence.IColumnModel#isForeignKey()
	 */
	public boolean isForeignKey() {
//		return column.isForeignKey();
		return false;
	}

	/**
	 * Gets the size.
	 *
	 * @return ColumnModel
	 * @see com.mqfdy.code.generator.persistence.IColumnModel#getSize()
	 */
	public String getSize() {
		return column.getLength().toString();
	}

	/**
	 * Gets the column.
	 *
	 * @return ColumnModel
	 * @see com.mqfdy.code.generator.persistence.IColumnModel#getColumn()
	 */
	public Column getColumn() {
		return this.column;
	}

	/**
	 * Gets the adapter.
	 *
	 * @param <T>
	 *            the generic type
	 * @param adapter
	 *            the adapter
	 * @return ColumnModel
	 * @see com.mqfdy.code.datasource.model.AbstractModel#getAdapter(java.lang.Class)
	 */
	@Override
	public <T> T getAdapter(Class<T> adapter) {
		// TODO Auto-generated method stub
		return null;
	}

}
