package com.mqfdy.code.generator.persistence;

import com.mqfdy.code.datasource.model.IModelElementTypes;

// TODO: Auto-generated Javadoc
/**
 * 持久模型的类型.
 *
 * @author mqfdy
 */
public interface IPersistenceModelType extends IModelElementTypes {
	
	/** The table type. */
	int TABLE_TYPE = 60;
	
	/** The view type. */
	int VIEW_TYPE = 61;
	
	/** The column type. */
	int COLUMN_TYPE = 62;
}
