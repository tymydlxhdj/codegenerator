package com.mqfdy.code.generator.persistence;

import com.mqfdy.code.datasource.mapping.Column;
import com.mqfdy.code.datasource.model.IModel;

/**
 * DB中的Column模型
 * 
 * @author mqfdy
 */
public interface IColumnModel extends IModel {
	
	/**
	 * 获取持久层模型
	 * @return 持久层模型
	 */
	public IPersistenceModel getPersistenceModel();

	/**
	 * 获取column对应的表字段名称
	 */
	public String getDBName();

	/**
	 * 获取column对应的字段中文名称
	 */
	public String getChineseName();

	/**
	 * 获取column对应的持久层pojo的属性名称
	 */
	public String getJavaName();

	/**
	 * 判断这个字段是否是主键字段
	 */
	public boolean isPrimaryKey();

	/**
	 * 获取column对应的持久层pojo的属性的java类型
	 */
	public String getJavaType();

	public void setJavaName(String javaName);

	/**
	 * 判断column对应的表中的字段是否允许为空
	 */
	public boolean isNotNull();

	/**
	 * 判断字段是否外键
	 * 
	 * @return
	 */
	public boolean isForeignKey();

	public String getSize();
	
	public Column getColumn();
}
