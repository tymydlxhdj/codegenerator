package com.mqfdy.code.springboot.core.generator.model;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IProject;

import com.mqfdy.code.springboot.core.generator.AbstractGenerator;

/**
 * gradle文件生成器
 * @author mqfdy
 *
 */
public class GradleGenerator extends AbstractGenerator {
	
	protected Map<String, Object> map = new HashMap<String, Object>();
	
	protected IProject genProject;
	
	protected String folder = "";
	
	protected String basePackage;
	
	private String fileName;
	
	private String templatePath;
	
	public void setFolder(String f){
		this.folder = f;
	}
	
	public GradleGenerator(IProject genProject,String basePackage,String fileName,String templatePath) {
		super(genProject);
		this.genProject = genProject;
		this.basePackage = basePackage;
		map.put("basePackage", basePackage);
		map.put("projectName", genProject.getName());
		this.fileName = fileName;
		this.templatePath = templatePath;
	}

	@Override
	public Map<String, Object> getSourceMap() {
		return map;
	}

	public String getOutputFilePath() {
		return getOutputFolderPath() + File.separator + getFileNameWithoutExtension()
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
		return this.fileName;
	}
	
	public IProject getGenProject() {
		return genProject;
	}
	
	public void setGenProject(IProject genProject) {
		this.genProject = genProject;
	}
	
	public String getFileExtension(){
		return ".gradle";
	}

	@Override
	public String getFileName() {
		return getFileNameWithoutExtension() + getFileExtension();
	}

	@Override
	protected String getTemplatePath() {
		return this.templatePath;
	}
}
