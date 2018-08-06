package com.mqfdy.code.generator;

import java.util.List;

import org.eclipse.core.runtime.IStatus;

import com.mqfdy.code.generator.model.IGenerator;

/**
 * 场景生成接口
 * @author mqfdy
 *
 */
public interface ISceneGenerator {

	/**
	 * 获取生成代码接口集合
	 * @return 生成代码接口集合
	 */
	public List<IGenerator> getGenerator();

	/**
	 * 生成代码
	 * @return 返回生成代码状态
	 */
	public IStatus generate();

	public void setParentId(String parentId);
}
