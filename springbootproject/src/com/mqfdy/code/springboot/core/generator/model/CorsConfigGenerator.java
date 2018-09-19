package com.mqfdy.code.springboot.core.generator.model;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IProject;

import com.mqfdy.code.springboot.core.generator.AbstractGenerator;


// TODO: Auto-generated Javadoc
/**
 * The Class CorsConfigGenerator.
 *
 * @author mqfdy
 */
public class CorsConfigGenerator extends AbstractGenerator {
	
	/** The map. */
	protected Map<String, Object> map = new HashMap<String, Object>();
	
	/** The gen project. */
	protected IProject genProject;
	
	/** The folder. */
	protected String folder = "";
	
	/** The base package. */
	protected String basePackage;
	
	/**
	 * Sets the folder.
	 *
	 * @author mqfdy
	 * @param f
	 *            the new folder
	 * @Date 2018-09-03 09:00
	 */
	public void setFolder(String f){
		this.folder = f;
	}
	
	/**
	 * Instantiates a new cors config generator.
	 *
	 * @param genProject
	 *            the gen project
	 * @param basePackage
	 *            the base package
	 */
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
	
	/**
	 * Sets the gen project.
	 *
	 * @author mqfdy
	 * @param genProject
	 *            the new gen project
	 * @Date 2018-09-03 09:00
	 */
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
