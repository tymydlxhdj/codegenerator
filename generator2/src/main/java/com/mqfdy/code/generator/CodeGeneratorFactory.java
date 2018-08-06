package com.mqfdy.code.generator;

import org.eclipse.core.resources.IProject;

import com.mqfdy.code.generator.model.IGenerator;
import com.mqfdy.code.generator.web.WebXmlGenerator;

/**
 * 代码生成器工厂
 * @author mqfdy
 *
 */
public class CodeGeneratorFactory {
	
	/**
	 * 创建代码生成器对象
	 * @param project
	 * @return
	 */
	public static ICodeGenerator createCodeGenerator(IProject project,int option,String packageName,String outputPath){
		return  new MicroGenerator2(project,option,packageName,outputPath);
	}
	
	/**
	 * 创建web.xml生成器对象
	 * @param project
	 * @return
	 */
	public static IGenerator createWebXmlGenerator(IProject project,String projectType,String version){
		return  new WebXmlGenerator(project,projectType,version);
	}
}
