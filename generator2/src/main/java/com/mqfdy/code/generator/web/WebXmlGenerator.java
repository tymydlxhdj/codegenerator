package com.mqfdy.code.generator.web;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IProject;

import com.mqfdy.code.generator.model.AbstractGenerator;
import com.mqfdy.code.generator.model.CodeGenerationException;

// TODO: Auto-generated Javadoc
/**
 * web.xml的生成器
 * @author mqfdy
 *
 */
public class WebXmlGenerator extends AbstractGenerator {
	

	/** The map. */
	protected Map<String, Object> map = new HashMap<String, Object>();
	
	/** The gen project. */
	protected IProject genProject;
	
	/** The folder. */
	protected String folder = "";
	
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
	 * Instantiates a new web xml generator.
	 *
	 * @param genProject
	 *            the gen project
	 * @param projectType
	 *            the project type
	 * @param version
	 *            the version
	 */
	public WebXmlGenerator(IProject genProject,String projectType,String version) {
		super(genProject);
		this.genProject = genProject;
		map.put("projectType", projectType);
		map.put("projectName", genProject.getName());
		map.put("version", version);
		map.put("version_", version.replace(".", "_"));
		String prefix = "<!--";
		String tail = "-->";
		String commentPrefix = "";
		String commentTail = "";
		if(!"3.0".equals(version)){
			prefix = "";
			tail = "";
			commentPrefix = "<!--";
			commentTail = "-->";
		}
		map.put("prefix", prefix);
		map.put("tail", tail);
		map.put("commentPrefix", commentPrefix);
		map.put("commentTail", commentTail);
	}

	/**
	 * @see com.mqfdy.code.generator.model.AbstractGenerator#getSourceMap()
	 * @return WebXmlGenerator
	 */
	@Override
	public Map<String, Object> getSourceMap() {
		return map;
	}

	/**
	 * @see com.mqfdy.code.generator.model.IGenerator#getOutputFilePath()
	 * @return WebXmlGenerator
	 */
	public String getOutputFilePath() {
		return genProject.getLocation().toOSString()+ File.separator + "WebContent"+ File.separator + "WEB-INF" + File.separator + getFileNameWithoutExtension()
				+ getFileExtension();

	}
	
	/**
	 * Gets the output file path real.
	 *
	 * @author mqfdy
	 * @return the output file path real
	 * @Date 2018-9-3 11:38:28
	 */
	private String getOutputFilePathReal() {
		return genProject.getLocation().toOSString()+ File.separator + "WebContent"+ File.separator + "WEB-INF" + File.separator + getFileNameWithoutExtension()
				+ ".xml";

	}
	
	/**
	 * @see com.mqfdy.code.generator.model.AbstractGenerator#generate() WebXmlGenerator
	 */
	public void generate(){
		try {
			super.generate();
			String realPath = getOutputFilePathReal();
			File fileOld = new File(realPath);
			if(fileOld.exists()){
				fileOld.delete();
			}
			File f = new File(getOutputFilePath());
			f.renameTo(new File(realPath));
		} catch (CodeGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 获取生成文件路径.
	 *
	 * @author mqfdy
	 * @return the output folder path
	 * @Date 2018-9-3 11:38:28
	 */
	@Override
	protected String getOutputFolderPath() {
		return genProject.getLocation().toOSString();
	}

	/**
	 * @see com.mqfdy.code.generator.model.AbstractGenerator#getFileNameWithoutExtension()
	 * @return WebXmlGenerator
	 */
	protected String getFileNameWithoutExtension() {
		return "web";
	}
	
	/**
	 * @see com.mqfdy.code.generator.model.AbstractGenerator#getGenProject()
	 * @return WebXmlGenerator
	 */
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
	
	/**
	 * @see com.mqfdy.code.generator.model.AbstractGenerator#getFileExtension()
	 * @return WebXmlGenerator
	 */
	public String getFileExtension(){
		return ".txt";
	}

	/**
	 * @see com.mqfdy.code.generator.model.IGenerator#getFileName()
	 * @return WebXmlGenerator
	 */
	public String getFileName() {
		return "web";
	}

	/**
	 * @see com.mqfdy.code.generator.model.AbstractGenerator#getTemplatePath()
	 * @return WebXmlGenerator
	 */
	@Override
	protected String getTemplatePath() {
		return "template/web/web.vm";
	}
}
