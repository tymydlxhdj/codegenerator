package com.mqfdy.code.springboot.core.generator.model;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IProject;

import com.mqfdy.code.springboot.core.generator.AbstractGenerator;


// TODO: Auto-generated Javadoc
/**
 * The Class PomGenerator.
 *
 * @author mqfdy
 */
public class PomGenerator extends AbstractGenerator {
	
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
	 * Instantiates a new pom generator.
	 *
	 * @param genProject
	 *            the gen project
	 * @param basePackage
	 *            the base package
	 */
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
