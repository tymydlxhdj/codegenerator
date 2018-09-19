package com.mqfdy.code.generator.persistence;

import java.util.List;

import com.mqfdy.code.datasource.mapping.Table;
import com.mqfdy.code.datasource.model.IModel;

// TODO: Auto-generated Javadoc
/**
 * 持久层模型的根模型，试图统一Torque的Table和View.
 *
 * @author mqfdy
 */
public interface IPersistenceModel extends IModel {
	
	/**
	 * Gets the table.
	 *
	 * @author mqfdy
	 * @return the table
	 * @Date 2018-09-03 09:00
	 */
	public Table getTable();

	/**
	 * Adds the column model.
	 *
	 * @author mqfdy
	 * @param columnModel
	 *            the column model
	 * @Date 2018-09-03 09:00
	 */
	public void addColumnModel(IColumnModel columnModel);

	/**
	 * Gets the column models.
	 *
	 * @author mqfdy
	 * @return the column models
	 * @Date 2018-09-03 09:00
	 */
	public List<IColumnModel> getColumnModels();

	/**
	 * 获得这个持久模型对应的POJO所在的源代码目录 通常是项目名称+src，例如test/src.
	 *
	 * @author mqfdy
	 * @return the src folder
	 * @Date 2018-09-03 09:00
	 */
	public String getSrcFolder();

	/**
	 * 设置这个持久模型对应的POJO所在的源代码目录.
	 *
	 * @author mqfdy
	 * @param srcFolder
	 *            the new src folder
	 * @Date 2018-09-03 09:00
	 */
	public void setSrcFolder(String srcFolder);

	/**
	 * 获得这个持久层模型所属的package.
	 *
	 * @author mqfdy
	 * @return the java package
	 * @Date 2018-09-03 09:00
	 */
	public String getJavaPackage();
	
	/**
	 * 获得这个持久层模型对应的POJO所属的package.
	 *
	 * @author mqfdy
	 * @return the po java package
	 * @Date 2018-09-03 09:00
	 */
	public String getPoJavaPackage();
	
	/**
	 * 设置这个持久层模型所属的package.
	 *
	 * @author mqfdy
	 * @param defaultPackage
	 *            the new java package
	 * @Date 2018-09-03 09:00
	 */
	public void setJavaPackage(String defaultPackage);

	/**
	 * 获得这个持久层模型对应的POJO的类名.
	 *
	 * @author mqfdy
	 * @return the java name
	 * @Date 2018-09-03 09:00
	 */
	public String getJavaName();

	/**
	 * 设置这个持久层模型对应的POJO的类名.
	 *
	 * @author mqfdy
	 * @param javaName
	 *            the new java name
	 * @Date 2018-09-03 09:00
	 */
	public void setJavaName(String javaName);

	/**
	 * 获得持久模型的主键 TODO 没有考虑联合主键.
	 *
	 * @author mqfdy
	 * @return the primary key column
	 * @Date 2018-09-03 09:00
	 */
	public IColumnModel getPrimaryKeyColumn();

	/**
	 * Adds the foreign key model.
	 *
	 * @author mqfdy
	 * @param foreignKeyModel
	 *            the foreign key model
	 * @Date 2018-09-03 09:00
	 */
	public void addForeignKeyModel(IForeignKeyModel foreignKeyModel);

	/**
	 * Gets the foreign key models.
	 *
	 * @author mqfdy
	 * @return the foreign key models
	 * @Date 2018-09-03 09:00
	 */
	public List<IForeignKeyModel> getForeignKeyModels();

	/**
	 * 判断这个表是否有从表.
	 *
	 * @author mqfdy
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	public boolean hasForeignTable();

	/**
	 * 获得这个持久层模型对应的POJO的主键生成策略.
	 *
	 * @author mqfdy
	 * @return the id method
	 * @Date 2018-09-03 09:00
	 */
	public String getIdMethod();

	/**
	 * Gets the DB name.
	 *
	 * @author mqfdy
	 * @return the DB name
	 * @Date 2018-09-03 09:00
	 */
	public String getDBName();
	
	/**
	 * Gets the scence name.
	 *
	 * @author mqfdy
	 * @return the scence name
	 * @Date 2018-09-03 09:00
	 */
	public String getScenceName();
	
	/**
	 * Sets the scence name.
	 *
	 * @author mqfdy
	 * @param scenceName
	 *            the new scence name
	 * @Date 2018-09-03 09:00
	 */
	public void setScenceName(String scenceName);

}
