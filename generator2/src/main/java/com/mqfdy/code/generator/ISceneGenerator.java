package com.mqfdy.code.generator;

import java.util.List;

import org.eclipse.core.runtime.IStatus;

import com.mqfdy.code.generator.model.IGenerator;

// TODO: Auto-generated Javadoc
/**
 * 场景生成接口.
 *
 * @author mqfdy
 */
public interface ISceneGenerator {

	/**
	 * 获取生成代码接口集合.
	 *
	 * @author mqfdy
	 * @return 生成代码接口集合
	 * @Date 2018-9-3 11:38:33
	 */
	public List<IGenerator> getGenerator();

	/**
	 * 生成代码.
	 *
	 * @author mqfdy
	 * @return 返回生成代码状态
	 * @Date 2018-9-3 11:38:33
	 */
	public IStatus generate();

	/**
	 * Sets the parent id.
	 *
	 * @author mqfdy
	 * @param parentId
	 *            the new parent id
	 * @Date 2018-09-03 09:00
	 */
	public void setParentId(String parentId);
}
