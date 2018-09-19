package com.mqfdy.code.springboot.core.generator.model;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IProject;

import com.mqfdy.code.springboot.core.generator.AbstractGenerator;


// TODO: Auto-generated Javadoc
/**
 * The Class JavaGenerator.
 *
 * @author mqfdy
 */
public class JavaGenerator extends AbstractGenerator {
	
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
	 * Instantiates a new java generator.
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
	public JavaGenerator(IProject genProject,String basePackage,String fileName,String templatePath) {
		super(genProject);
		this.genProject = genProject;
		this.basePackage = basePackage;
		map.put("basePackage", basePackage);
		this.fileName = fileName;
		this.templatePath = templatePath;
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
		return ".java";
	}

	@Override
	public String getFileName() {
		return getFileNameWithoutExtension()  + this.getFileExtension();
	}

	@Override
	protected String getTemplatePath() {
		return this.templatePath;
	}
}
