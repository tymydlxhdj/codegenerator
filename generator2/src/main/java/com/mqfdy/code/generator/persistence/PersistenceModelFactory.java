package com.mqfdy.code.generator.persistence;

import java.util.Map;
import java.util.Map.Entry;

import com.mqfdy.code.datasource.mapping.Column;
import com.mqfdy.code.datasource.mapping.ForeignKey;
import com.mqfdy.code.datasource.mapping.Table;

// TODO: Auto-generated Javadoc
/**
 * 持久模型工厂类.
 *
 * @author mqfdy
 */
public final class PersistenceModelFactory {
	
	/**
	 * Creates a new PersistenceModel object.
	 *
	 * @param table
	 *            the table
	 * @return the i persistence model
	 */
	public static IPersistenceModel createTableModel(final Table table) {
		
		if(table == null) {
			return null;
		}
		
		final IPersistenceModel tableModel = new TableModel(table);
		
		for (Entry<String, Column> entry : table.getColumns().entrySet()) {
			createColumnModel(tableModel, entry.getValue());
		}
		//modified by mqf 创建外键模型对象 2017.04.07
		Map<String, ForeignKey> foreignKeys = table.getForeignKeys();
		for (Entry<String, ForeignKey> entry : foreignKeys.entrySet()) {
			createForeignModel(tableModel, entry.getValue());
		}
		
		return tableModel;
	}
	
	/**
	 * added by mqf 创建外键模型对象 2017.04.07
	 *
	 * @param tableModel
	 *            the table model
	 * @param foreignKey
	 *            the foreign key
	 */
	private static void createForeignModel(IPersistenceModel tableModel, ForeignKey foreignKey) {
		ForeignKeyModel foreignKeyModel = new ForeignKeyModel(tableModel, foreignKey);
		tableModel.addForeignKeyModel(foreignKeyModel);
	}

	/**
	 * Creates a new PersistenceModel object.
	 *
	 * @param tableModel
	 *            the table model
	 * @param column
	 *            the column
	 * @return the i column model
	 */
	private static IColumnModel createColumnModel(
			final IPersistenceModel tableModel, final Column column) {
		final IColumnModel columnModel = new ColumnModel(tableModel, column);
		tableModel.addColumnModel(columnModel);
		return columnModel;
	}
}
