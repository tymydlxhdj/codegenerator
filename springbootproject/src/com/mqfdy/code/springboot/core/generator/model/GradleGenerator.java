package com.mqfdy.code.springboot.core.generator.model;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IProject;

import com.mqfdy.code.springboot.core.generator.AbstractGenerator;

// TODO: Auto-generated Javadoc
/**
 * gradle文件生成器
 * @author mqfdy
 *
 */
public class GradleGenerator extends AbstractGenerator {
	
	/** The map. */
	protected Map<String, Object> map = new HashMap<String, Object>();
	
	/** The gen project. */
	protected IProject genProject;
	
	/** The folder. */
	protected String folder = "";
	
	/** The base package. */
	protected String basePackage;
	
	private String fileName;
	
	private String templatePath;
	
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
	 * Instantiates a new gradle generator.
	 *
	 * @param genProject
	 *            the gen project
	 * @param basePackage
	 *            the base package
	 * @param fileName
	 *            the file name
	 * @param templatePath
	 *            the template path
	 */
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
