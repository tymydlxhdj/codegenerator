/**
 * 
 */
package com.mqfdy.code.generator.model;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.mqfdy.code.generator.exception.CodeGeneratorException;

// TODO: Auto-generated Javadoc
/**
 * CodeFileMaterial.
 *
 * @author mqfdy
 */
public  class CodeFileMaterial {
	
	/** The template path. */
	private String templatePath;
	
	/** The velocity map. */
	private Map<String,Object> velocityMap = new HashMap<>();
	
	/** The output path. */
	private String outputPath;
	
	/** The file name. */
	private String fileName;
	
	/** The extension. */
	private String extension = "";
	
	/** The pre. */
	private String pre = "";
	
	/** The suf. */
	private String suf = "";
	
	/**
	 * Instantiates a new code file material.
	 *
	 * @param templatePath
	 *            the template path
	 * @param velocityMap
	 *            the velocity map
	 * @param outputPath
	 *            the output path
	 * @param fileName
	 *            the file name
	 * @param extension
	 *            the extension
	 * @param pre
	 *            the pre
	 * @param suf
	 *            the suf
	 */
	public CodeFileMaterial( String templatePath,Map<String,Object> velocityMap,
			String outputPath,String fileName,String extension,String pre,String suf){

		//校验路径
		if(templatePath == null){
			throw new CodeGeneratorException("模板路径不能为空！");
		}
		this.templatePath = templatePath;
		
		//校验输出路径
		if(outputPath == null){
			throw new CodeGeneratorException("输出路径不能为空！");
		}
		this.outputPath = outputPath;
		
		this.velocityMap = new HashMap<>();
		if(velocityMap != null){
			this.velocityMap.putAll(velocityMap);
		}
		
		this.fileName = fileName;
		if(extension != null){
			this.extension = extension;
		}
		if(pre != null){
			this.pre = pre;
		}
		if(suf != null){
			this.suf = suf;
		}
		
	}

	/**
	 * Gets the template path.
	 *
	 * @author mqfdy
	 * @return the template path
	 * @Date 2018-9-19 10:16:40
	 */
	public String getTemplatePath() {
		return templatePath;
	}

	/**
	 * Sets the template path.
	 *
	 * @author mqfdy
	 * @param templatePath
	 *            the new template path
	 * @Date 2018-9-19 10:16:40
	 */
	public void setTemplatePath(String templatePath) {
		this.templatePath = templatePath;
	}

	/**
	 * Gets the velocity map.
	 *
	 * @author mqfdy
	 * @return the velocity map
	 * @Date 2018-9-19 10:16:40
	 */
	public Map<String, Object> getVelocityMap() {
		return velocityMap;
	}

	/**
	 * Sets the velocity map.
	 *
	 * @author mqfdy
	 * @param velocityMap
	 *            the velocity map
	 * @Date 2018-9-19 10:16:40
	 */
	public void setVelocityMap(Map<String, Object> velocityMap) {
		this.velocityMap = velocityMap;
	}

	/**
	 * Gets the output path.
	 *
	 * @author mqfdy
	 * @return the output path
	 * @Date 2018-9-19 10:16:40
	 */
	public String getOutputPath() {
		return outputPath;
	}

	/**
	 * Sets the output path.
	 *
	 * @author mqfdy
	 * @param outputPath
	 *            the new output path
	 * @Date 2018-9-19 10:16:40
	 */
	public void setOutputPath(String outputPath) {
		this.outputPath = outputPath;
	}

	/**
	 * Gets the file name.
	 *
	 * @author mqfdy
	 * @return the file name
	 * @Date 2018-9-19 10:16:40
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * Sets the file name.
	 *
	 * @author mqfdy
	 * @param fileName
	 *            the new file name
	 * @Date 2018-9-19 10:16:40
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * Gets the extension.
	 *
	 * @author mqfdy
	 * @return the extension
	 * @Date 2018-9-19 10:16:40
	 */
	public String getExtension() {
		return extension;
	}

	/**
	 * Sets the extension.
	 *
	 * @author mqfdy
	 * @param extension
	 *            the new extension
	 * @Date 2018-9-19 10:16:40
	 */
	public void setExtension(String extension) {
		this.extension = extension;
	}

	/**
	 * Gets the pre.
	 *
	 * @author mqfdy
	 * @return the pre
	 * @Date 2018-9-19 10:16:40
	 */
	public String getPre() {
		return pre;
	}

	/**
	 * Sets the pre.
	 *
	 * @author mqfdy
	 * @param pre
	 *            the new pre
	 * @Date 2018-9-19 10:16:40
	 */
	public void setPre(String pre) {
		this.pre = pre;
	}

	/**
	 * Gets the suf.
	 *
	 * @author mqfdy
	 * @return the suf
	 * @Date 2018-9-19 10:16:40
	 */
	public String getSuf() {
		return suf;
	}

	/**
	 * Sets the suf.
	 *
	 * @author mqfdy
	 * @param suf
	 *            the new suf
	 * @Date 2018-9-19 10:16:40
	 */
	public void setSuf(String suf) {
		this.suf = suf;
	}
	
	/**
	 * Gets the output file path.
	 *
	 * @author mqfdy
	 * @return the output file path
	 * @Date 2018-9-19 10:18:27
	 */
	public String getOutputFilePath(){
		return this.getOutputPath()+File.separator+this.getPre()+this.getFileName()+this.getSuf()+this.getExtension();
	}

}
