package com.mqfdy.code.generator.persistence;

import java.util.List;

import com.mqfdy.code.datasource.mapping.ForeignKey;

/**
 * 外键模型
 * 
 * @author mqfdy
 * 
 */
public interface IForeignKeyModel {

	public String getForeignTableName();

	public List<String> getForeignColumns();

	public String getFSubPropertyName();
	
	public ForeignKey getForeignKey();

}
