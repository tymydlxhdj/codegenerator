package com.mqfdy.code.generator.persistence;

import java.util.ArrayList;
import java.util.List;

import com.mqfdy.code.datasource.mapping.Column;
import com.mqfdy.code.datasource.mapping.ForeignKey;
import com.mqfdy.code.datasource.model.AbstractModel;

// TODO: Auto-generated Javadoc
/**
 * 外键模型.
 *
 * @author mqfdy
 */
public class ForeignKeyModel extends AbstractModel implements IForeignKeyModel {

	/** The foreign key. */
	private final ForeignKey foreignKey;
	
	/** The persistence model. */
	private IPersistenceModel persistenceModel;

	/**
	 * Instantiates a new foreign key model.
	 *
	 * @param tableModel
	 *            the table model
	 * @param foreignKey
	 *            the foreign key
	 */
	public ForeignKeyModel(final IPersistenceModel tableModel,
			final ForeignKey foreignKey) {
		super(tableModel, foreignKey.getName());
		this.foreignKey = foreignKey;
		this.persistenceModel = tableModel;
	}

	/**
	 * Gets the foreign columns.
	 *
	 * @return ForeignKeyModel
	 * @see com.mqfdy.code.generator.persistence.IForeignKeyModel#getForeignColumns()
	 */
	public List<String> getForeignColumns() {
		List<Column> colList = foreignKey.getColumns();
		List<String> foreignColumns = new ArrayList<String>();
		if(colList != null && !colList.isEmpty()){
			for(Column col :colList){
				foreignColumns.add(col.getName());
			}
		}
		return foreignColumns;
	}

	/**
	 * Gets the foreign table name.
	 *
	 * @return ForeignKeyModel
	 * @see com.mqfdy.code.generator.persistence.IForeignKeyModel#getForeignTableName()
	 */
	public String getForeignTableName() {
		return getForeignKey().getReferencedTable().getName();
	}

	/**
	 * Gets the foreign key.
	 *
	 * @return ForeignKeyModel
	 * @see com.mqfdy.code.generator.persistence.IForeignKeyModel#getForeignKey()
	 */
	public ForeignKey getForeignKey() {
		return foreignKey;
	}

	/**
	 * Gets the persistence model.
	 *
	 * @author mqfdy
	 * @return the persistence model
	 * @Date 2018-09-03 09:00
	 */
	public IPersistenceModel getPersistenceModel() {
		return persistenceModel;
	}

	/**
	 * Gets the f sub property name.
	 *
	 * @return ForeignKeyModel
	 * @see com.mqfdy.code.generator.persistence.IForeignKeyModel#getFSubPropertyName()
	 */
	public String getFSubPropertyName() {
		return null;
	}

}
