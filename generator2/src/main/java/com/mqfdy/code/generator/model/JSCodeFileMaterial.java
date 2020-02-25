package com.mqfdy.code.generator.model;

import java.util.Map;

import com.mqfdy.code.generator.constant.IGeneratorConstant;

// TODO: Auto-generated Javadoc
/**
 * The Class JSCodeFileMaterial.
 *
 * @author mqfdy
 */
public class JSCodeFileMaterial extends CodeFileMaterial {
	
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
	public JSCodeFileMaterial(String templatePath,Map<String,Object> velocityMap,
			String outputPath,String fileName,String pre,String suf){
		super(templatePath, velocityMap, outputPath, fileName, IGeneratorConstant.JS_FILE_EXTENSION, pre, suf);
	}

}
