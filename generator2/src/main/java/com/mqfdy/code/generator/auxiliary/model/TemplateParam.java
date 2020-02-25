/**
 * 
 */
package com.mqfdy.code.generator.auxiliary.model;

import java.util.Map;

import org.eclipse.core.resources.IProject;

// TODO: Auto-generated Javadoc
/**
 * The Class TemplateParam.
 *
 * @author Administrator
 */
public class TemplateParam {
	
	/** The cur gen project. */
	private IProject curGenProject;
	
	/** The template path. */
	private String templatePath;
	
	/** The output path. */
	private String outputPath;
	
	/** The map. */
	private Map<String,Object> map;
	
	/** velocity模板的跟路径. */
	private String rootPath;
	
	/**
	 * Gets the cur gen project.
	 *
	 * @author Administrator
	 * @return the cur gen project
	 * @Date 2019-12-17 11:37:09
	 */
	public IProject getCurGenProject() {
		return curGenProject;
	}
	
	/**
	 * Sets the cur gen project.
	 *
	 * @author Administrator
	 * @param curGenProject the new cur gen project
	 * @Date 2019-12-17 11:37:09
	 */
	public void setCurGenProject(IProject curGenProject) {
		this.curGenProject = curGenProject;
	}
	
	/**
	 * Gets the template path.
	 *
	 * @author Administrator
	 * @return the template path
	 * @Date 2019-12-17 11:37:09
	 */
	public String getTemplatePath() {
		return templatePath;
	}
	
	/**
	 * Sets the template path.
	 *
	 * @author Administrator
	 * @param templatePath the new template path
	 * @Date 2019-12-17 11:37:09
	 */
	public void setTemplatePath(String templatePath) {
		this.templatePath = templatePath;
	}
	
	/**
	 * Gets the output path.
	 *
	 * @author Administrator
	 * @return the output path
	 * @Date 2019-12-17 11:37:09
	 */
	public String getOutputPath() {
		return outputPath;
	}
	
	/**
	 * Sets the output path.
	 *
	 * @author Administrator
	 * @param outputPath the new output path
	 * @Date 2019-12-17 11:37:09
	 */
	public void setOutputPath(String outputPath) {
		this.outputPath = outputPath;
	}
	
	/**
	 * Gets the map.
	 *
	 * @author Administrator
	 * @return the map
	 * @Date 2019-12-17 11:37:09
	 */
	public Map<String, Object> getMap() {
		return map;
	}
	
	/**
	 * Sets the map.
	 *
	 * @author Administrator
	 * @param map the map
	 * @Date 2019-12-17 11:37:09
	 */
	public void setMap(Map map) {
		this.map = map;
	}
	
	/**
	 * Gets the root path.
	 *
	 * @author Administrator
	 * @return the root path
	 * @Date 2019-12-17 11:37:09
	 */
	public String getRootPath() {
		return rootPath;
	}
	
	/**
	 * Sets the root path.
	 *
	 * @author Administrator
	 * @param rootPath the new root path
	 * @Date 2019-12-17 11:37:09
	 */
	public void setRootPath(String rootPath) {
		this.rootPath = rootPath;
	}
}
