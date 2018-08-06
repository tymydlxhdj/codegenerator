package com.mqfdy.code.springboot.core.generator.model;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IProject;

import com.mqfdy.code.springboot.core.generator.AbstractGenerator;


public class CorsConfigGenerator extends AbstractGenerator {
	
	protected Map<String, Object> map = new HashMap<String, Object>();
	
	protected IProject genProject;
	
	protected String folder = "";
	
	protected String basePackage;
	
	public void setFolder(String f){
		this.folder = f;
	}
	
	public CorsConfigGenerator(IProject genProject,String basePackage) {
		super(genProject);
		this.genProject = genProject;
		this.basePackage = basePackage;
		map.put("basePackage", basePackage);
	}

	@Override
	public Map<String, Object> getSourceMap() {
		return map;
	}

	public String getOutputFilePath() {
		return getOutputFolderPath() + getFileNameWithoutExtension()
				+ getFileExtension();

	}

	/**
	 * 获取生成文件路径
	 */
	@Override
	protected String getOutputFolderPath() {
		String path = genProject.getLocation().toOSString() + File.separator + "src"+ File.separator + "main"
				+ File.separator + "java" + File.separator + basePackage.replace(".", File.separator) + File.separator;
		return path;
	}

	protected String getFileNameWithoutExtension() {
		return "CorsConfig";
	}
	
	public IProject getGenProject() {
		return genProject;
	}
	
	public void setGenProject(IProject genProject) {
		this.genProject = genProject;
	}
	
	public String getFileExtension(){
		return ".java";
	}

	@Override
	public String getFileName() {
		
		return "CorsConfig";
	}

	@Override
	protected String getTemplatePath() {
		return "template/CorsConfig.vm";
	}
}
