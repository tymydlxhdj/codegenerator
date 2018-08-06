package com.mqfdy.code.generator;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;

/**
 * 生成代码工具类
 * @author mqfdy
 *
 */
public class GenProjectTypeUtilTools {
	
	/**
	 * 获取其生成文件的路径
	 * 
	 * @param genProject
	 *            生成代码的项目
	 * @param fileType
	 *            生成不同类型文件时的文件路径(文件扩展名称)
	 * @return 返回生成不同文件时的路径
	 */
	public static IFolder getOutputFolderPath(IProject genProject,
			String fileType) {
		IFolder folder = null;
		if ("java".equals(fileType)) {
			folder = genProject.getFolder("src");
		}
		return folder;

	}
}
