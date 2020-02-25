package com.mqfdy.code.generator;

import org.eclipse.core.resources.IProject;

import com.mqfdy.code.generator.model.IGenerator;
import com.mqfdy.code.generator.web.WebXmlGenerator;

// TODO: Auto-generated Javadoc
/**
 * 代码生成器工厂.
 *
 * @author mqfdy
 */
public class CodeGeneratorFactory {
	
	/**
	 * 创建代码生成器对象.
	 *
	 * @param project
	 *            the project
	 * @param option
	 *            the option
	 * @param packageName
	 *            the package name
	 * @param outputPath
	 *            the output path
	 * @return the i code generator
	 */
	public static MicroGenerator2 createCodeGenerator(IProject project,int option,String packageName,String outputPath){
		return  new MicroGenerator2(project,option,packageName,outputPath);
	}
	
	/**
	 * 创建web.xml生成器对象
	 *
	 * @param project
	 *            the project
	 * @param projectType
	 *            the project type
	 * @param version
	 *            the version
	 * @return the i generator
	 */
	public static IGenerator createWebXmlGenerator(IProject project,String projectType,String version){
		return  new WebXmlGenerator(project,projectType,version);
	}
}
