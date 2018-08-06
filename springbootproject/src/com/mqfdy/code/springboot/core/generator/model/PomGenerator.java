package com.mqfdy.code.springboot.core.generator.model;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IProject;

import com.mqfdy.code.springboot.core.generator.AbstractGenerator;


public class PomGenerator extends AbstractGenerator {
	
	protected Map<String, Object> map = new HashMap<String, Object>();
	
	protected IProject genProject;
	
	protected String folder = "";
	
	protected String basePackage;
	
	public void setFolder(String f){
		this.folder = f;
	}
	
	public PomGenerator(IProject genProject,String basePackage) {
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
		return genProject.getLocation().toOSString();
	}

	protected String getFileNameWithoutExtension() {
		return "pom";
	}
	
	public IProject getGenProject() {
		return genProject;
	}
	
	public void setGenProject(IProject genProject) {
		this.genProject = genProject;
	}
	
	public String getFileExtension(){
		return ".xml";
	}

	@Override
	protected String getTemplatePath() {
		return "template/pom.vm";
	}
	
	@Override
	public String getFileName() {
		return getFileNameWithoutExtension() + getFileExtension();
	}

}
