package com.mqfdy.code.generator.persistence;

import java.util.List;

import com.mqfdy.code.datasource.mapping.ForeignKey;

// TODO: Auto-generated Javadoc
/**
 * 外键模型.
 *
 * @author mqfdy
 */
public interface IForeignKeyModel {

	/**
	 * Gets the foreign table name.
	 *
	 * @author mqfdy
	 * @return the foreign table name
	 * @Date 2018-09-03 09:00
	 */
	public String getForeignTableName();

	/**
	 * Gets the foreign columns.
	 *
	 * @author mqfdy
	 * @return the foreign columns
	 * @Date 2018-09-03 09:00
	 */
	public List<String> getForeignColumns();

	/**
	 * Gets the f sub property name.
	 *
	 * @author mqfdy
	 * @return the f sub property name
	 * @Date 2018-09-03 09:00
	 */
	public String getFSubPropertyName();
	
	/**
	 * Gets the foreign key.
	 *
	 * @author mqfdy
	 * @return the foreign key
	 * @Date 2018-09-03 09:00
	 */
	public ForeignKey getForeignKey();

}
