package com.mqfdy.code.generator.persistence;

import com.mqfdy.code.datasource.mapping.Column;
import com.mqfdy.code.datasource.model.IModel;

// TODO: Auto-generated Javadoc
/**
 * DB中的Column模型.
 *
 * @author mqfdy
 */
public interface IColumnModel extends IModel {
	
	/**
	 * 获取持久层模型.
	 *
	 * @author mqfdy
	 * @return 持久层模型
	 * @Date 2018-9-3 11:38:24
	 */
	public IPersistenceModel getPersistenceModel();

	/**
	 * 获取column对应的表字段名称.
	 *
	 * @author mqfdy
	 * @return the DB name
	 * @Date 2018-09-03 09:00
	 */
	public String getDBName();

	/**
	 * 获取column对应的字段中文名称.
	 *
	 * @author mqfdy
	 * @return the chinese name
	 * @Date 2018-09-03 09:00
	 */
	public String getChineseName();

	/**
	 * 获取column对应的持久层pojo的属性名称.
	 *
	 * @author mqfdy
	 * @return the java name
	 * @Date 2018-09-03 09:00
	 */
	public String getJavaName();

	/**
	 * 判断这个字段是否是主键字段.
	 *
	 * @author mqfdy
	 * @return true, if is primary key
	 * @Date 2018-09-03 09:00
	 */
	public boolean isPrimaryKey();

	/**
	 * 获取column对应的持久层pojo的属性的java类型.
	 *
	 * @author mqfdy
	 * @return the java type
	 * @Date 2018-09-03 09:00
	 */
	public String getJavaType();

	/**
	 * Sets the java name.
	 *
	 * @author mqfdy
	 * @param javaName
	 *            the new java name
	 * @Date 2018-09-03 09:00
	 */
	public void setJavaName(String javaName);

	/**
	 * 判断column对应的表中的字段是否允许为空.
	 *
	 * @author mqfdy
	 * @return true, if is not null
	 * @Date 2018-09-03 09:00
	 */
	public boolean isNotNull();

	/**
	 * 判断字段是否外键.
	 *
	 * @author mqfdy
	 * @return true, if is foreign key
	 * @Date 2018-09-03 09:00
	 */
	public boolean isForeignKey();

	/**
	 * Gets the size.
	 *
	 * @author mqfdy
	 * @return the size
	 * @Date 2018-09-03 09:00
	 */
	public String getSize();
	
	/**
	 * Gets the column.
	 *
	 * @author mqfdy
	 * @return the column
	 * @Date 2018-09-03 09:00
	 */
	public Column getColumn();
}
