package com.mqfdy.code.generator.persistence;

import java.util.ArrayList;
import java.util.List;

import com.mqfdy.code.datasource.mapping.Column;
import com.mqfdy.code.datasource.mapping.ForeignKey;
import com.mqfdy.code.datasource.model.AbstractModel;

/**
 * 外键模型
 * 
 * @author mqfdy
 * 
 */
public class ForeignKeyModel extends AbstractModel implements IForeignKeyModel {

	private final ForeignKey foreignKey;
	private IPersistenceModel persistenceModel;

	public ForeignKeyModel(final IPersistenceModel tableModel,
			final ForeignKey foreignKey) {
		super(tableModel, foreignKey.getName());
		this.foreignKey = foreignKey;
		this.persistenceModel = tableModel;
	}

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

	public String getForeignTableName() {
		return getForeignKey().getReferencedTable().getName();
	}

	public ForeignKey getForeignKey() {
		return foreignKey;
	}

	public IPersistenceModel getPersistenceModel() {
		return persistenceModel;
	}

	public String getFSubPropertyName() {
		return null;
	}

}
