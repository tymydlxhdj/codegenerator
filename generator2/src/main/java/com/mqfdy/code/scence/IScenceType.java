package com.mqfdy.code.scence;

/**
 * 场景类型
 * 
 * @author mqfdy
 */
public interface IScenceType {
	
	/**
	 *  MX单表场景类型
	 */
	int SINGLE_TABLE_SCENE_TYPE = 50;
	
	/**
	 * MX主从表场景类型
	 */
	int DOUBLE_TABLE_SCENE_TYPE = 51;
	
	/**
	 * MX主从表场景类型 主表
	 */
	int DOUBLE_TABLE_SCENE_TYPE_MASTER = 511;
	
	/**
	 * MX主从表场景类型 从表
	 */
	int DOUBLE_TABLE_SCENE_TYPE_SLAVE = 512;
	
	/**
	 * MX树场景类型
	 */
	int TREE_SCENE_TYPE = 53;
	/**
	 * 一对一
	 */
	int ONE2ONE_SCENE_TYPE = 55;
	

	/**
	 *  DB业务场景
	 */
	int DB_SCENE_TYPE = 54;
}
