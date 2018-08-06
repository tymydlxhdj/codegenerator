package com.mqfdy.code.generator.persistence;

import com.mqfdy.code.datasource.model.IModelElementTypes;

/**
 * 持久模型的类型
 * 
 * @author mqfdy
 */
public interface IPersistenceModelType extends IModelElementTypes {
	int TABLE_TYPE = 60;
	int VIEW_TYPE = 61;
	int COLUMN_TYPE = 62;
}
