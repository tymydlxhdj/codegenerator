package com.mqfdy.code.generator.entity;

import java.io.File;
import java.util.Map;

import org.apache.velocity.runtime.RuntimeConstants;
import org.eclipse.core.resources.IProject;

import com.mqfdy.code.generator.auxiliary.model.TemplateParam;
import com.mqfdy.code.generator.model.AbstractGenerator;


// TODO: Auto-generated Javadoc
/**
 * The Class JsGenerator.
 *
 * @author Administrator
 */
public class FrontGenerator extends AbstractGenerator {
	
	/** The template path. */
	protected String templatePath;
	
	/** The source map. */
	protected Map<String,Object> sourceMap;
	
	/** The output path. */
	protected String outputPath;

	/** The root path. */
	private String rootPath;

	/**
	 * Instantiates a new front generator.
	 *
	 * @param curGenProject the cur gen project
	 * @param templatePath the template path
	 * @param outputPath the output path
	 * @param map the map
	 * @param rootPath the root path
	 */
	public FrontGenerator(IProject curGenProject,String templatePath,String outputPath,Map<String,Object> map,String rootPath){
		super(curGenProject);
		this.sourceMap = map;
		this.templatePath = templatePath;
		this.outputPath = outputPath;
		this.rootPath = rootPath;
		properties.setProperty(RuntimeConstants.FILE_RESOURCE_LOADER_PATH,
				rootPath);
	}
	
	/**
	 * Instantiates a new front generator.
	 *
	 * @param param the param
	 */
	public FrontGenerator(TemplateParam param){
		this(param.getCurGenProject(), param.getTemplatePath(), param.getOutputPath(), param.getMap(), param.getRootPath());
	}

	/**
	 * getOutputFilePath.
	 *
	 * @author Administrator
	 * @return the output file path
	 * @date 2019年12月11日 上午10:06:33
	 */
	@Override
	public String getOutputFilePath() {
		return this.getOutputFolderPath()+File.separator+ this.getFileName();
	}

	/**
	 * getFileName.
	 *
	 * @author Administrator
	 * @return the file name
	 * @date 2019年12月11日 上午10:06:33
	 */
	@Override
	public String getFileName() {
		File templateFile = new File(this.templatePath);
		return  (String) sourceMap.get("className") + 
				templateFile.getName().substring(0, 
						templateFile.getName().length()-3);
	}

	/**
	 * getFileNameWithoutExtension.
	 *
	 * @author Administrator
	 * @return the file name without extension
	 * @date 2019年12月11日 上午10:06:33
	 */
	@Override
	protected String getFileNameWithoutExtension() {
		File templateFile = new File(this.templatePath);
		return (String) sourceMap.get("className") + templateFile.getName().substring(0, 
				templateFile.getName().length()-3);
	}

	/**
	 * getFileExtension.
	 *
	 * @author Administrator
	 * @return the file extension
	 * @date 2019年12月11日 上午10:06:33
	 */
	protected String getFileExtension(){
		String extension = ".js";
		String[] array = this.templatePath.split(".");
		if(array.length > 2){
			extension ="." + array[array.length-2];
		}
		return extension;
	}

	/**
	 * getOutputFolderPath.
	 *
	 * @author Administrator
	 * @return the output folder path
	 * @date 2019年12月11日 上午10:06:33
	 */
	@Override
	protected String getOutputFolderPath() {
		return this.outputPath;
	}

	/**
	 * getTemplatePath.
	 *
	 * @author Administrator
	 * @return the template path
	 * @date 2019年12月11日 上午10:06:33
	 */
	@Override
	protected String getTemplatePath() {
		return this.templatePath;
	}

	/**
	 * getSourceMap.
	 *
	 * @author Administrator
	 * @return the source map
	 * @date 2019年12月11日 上午10:06:34
	 */
	@Override
	protected Map<String, Object> getSourceMap() {
		return sourceMap;
	}

}
