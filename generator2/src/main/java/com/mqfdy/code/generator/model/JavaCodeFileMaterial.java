package com.mqfdy.code.generator.model;

import java.util.Map;

import com.mqfdy.code.generator.constant.IGeneratorConstant;

// TODO: Auto-generated Javadoc
/**
 * The Class JavaCodeFileMaterial.
 *
 * @author mqfdy
 */
public class JavaCodeFileMaterial extends CodeFileMaterial {
	
	/** The packate str. */
	private String packateStr;
	
	
	/**
	 * Instantiates a new java code file material.
	 *
	 * @param templatePath
	 *            the template path
	 * @param velocityMap
	 *            the velocity map
	 * @param outputPath
	 *            the output path
	 * @param fileName
	 *            the file name
	 * @param pre
	 *            the pre
	 * @param suf
	 *            the suf
	 */
	public JavaCodeFileMaterial(String templatePath,Map<String,Object> velocityMap,
			String outputPath,String fileName,String pre,String suf){
		super(templatePath, velocityMap, outputPath, fileName, IGeneratorConstant.JAVA_FILE_EXTENSION, pre, suf);
		
	}


	/**
	 * Gets the packate str.
	 *
	 * @author mqfdy
	 * @return the packate str
	 * @Date 2018-10-8 15:08:38
	 */
	public String getPackateStr() {
		return packateStr;
	}


	/**
	 * Sets the packate str.
	 *
	 * @author mqfdy
	 * @param packateStr
	 *            the new packate str
	 * @Date 2018-10-8 15:08:38
	 */
	public void setPackateStr(String packateStr) {
		this.packateStr = packateStr;
	}

}
