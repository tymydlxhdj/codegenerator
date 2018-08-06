package com.mqfdy.code.generator.persistence;

import java.util.List;

import com.mqfdy.code.datasource.mapping.Table;
import com.mqfdy.code.datasource.model.IModel;

/**
 * 持久层模型的根模型，试图统一Torque的Table和View
 * 
 * @author mqfdy
 */
public interface IPersistenceModel extends IModel {
	
	public Table getTable();

	public void addColumnModel(IColumnModel columnModel);

	public List<IColumnModel> getColumnModels();

	/**
	 * 获得这个持久模型对应的POJO所在的源代码目录 通常是项目名称+src，例如test/src
	 */
	public String getSrcFolder();

	/**
	 * 设置这个持久模型对应的POJO所在的源代码目录
	 */
	public void setSrcFolder(String srcFolder);

	/**
	 * 获得这个持久层模型所属的package
	 */
	public String getJavaPackage();
	
	/**
	 * 获得这个持久层模型对应的POJO所属的package
	 */
	public String getPoJavaPackage();
	
	/**
	 * 设置这个持久层模型所属的package
	 */
	public void setJavaPackage(String defaultPackage);

	/**
	 * 获得这个持久层模型对应的POJO的类名
	 */
	public String getJavaName();

	/**
	 * 设置这个持久层模型对应的POJO的类名
	 */
	public void setJavaName(String javaName);

	/**
	 * 获得持久模型的主键 TODO 没有考虑联合主键
	 * 
	 * @return
	 */
	public IColumnModel getPrimaryKeyColumn();

	public void addForeignKeyModel(IForeignKeyModel foreignKeyModel);

	public List<IForeignKeyModel> getForeignKeyModels();

	/**
	 * 判断这个表是否有从表
	 */
	public boolean hasForeignTable();

	/**
	 * 获得这个持久层模型对应的POJO的主键生成策略
	 * 
	 * @return
	 */
	public String getIdMethod();

	public String getDBName();
	
	public String getScenceName();
	
	public void setScenceName(String scenceName);

}
