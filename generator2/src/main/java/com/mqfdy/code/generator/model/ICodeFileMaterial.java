/**
 * 
 */
package com.mqfdy.code.generator.model;

import java.io.File;
import java.util.Map;

/**
 * @author mqfdy
 *
 */
public interface ICodeFileMaterial {
	
	/**
	 * Gets the template path.
	 *
	 * @author mqfdy
	 * @return the template path
	 * @Date 2018-9-19 10:16:40
	 */
	public String getTemplatePath();
	/**
	 * Gets the velocity map.
	 *
	 * @author mqfdy
	 * @return the velocity map
	 * @Date 2018-9-19 10:16:40
	 */
	public Map<String, Object> getVelocityMap();
	/**
	 * Gets the output path.
	 *
	 * @author mqfdy
	 * @return the output path
	 * @Date 2018-9-19 10:16:40
	 */
	public String getOutputPath();
	/**
	 * Gets the file name.
	 *
	 * @author mqfdy
	 * @return the file name
	 * @Date 2018-9-19 10:16:40
	 */
	public String getFileName();
	/**
	 * Gets the pre.
	 *
	 * @author mqfdy
	 * @return the pre
	 * @Date 2018-9-19 10:16:40
	 */
	public String getPre();
	/**
	 * Gets the suf.
	 *
	 * @author mqfdy
	 * @return the suf
	 * @Date 2018-9-19 10:16:40
	 */
	public String getSuf();
	/**
	 * Gets the extension.
	 *
	 * @author mqfdy
	 * @return the extension
	 * @Date 2018-9-19 10:16:40
	 */
	public String getExtension();
	/**
	 * Gets the output file path.
	 *
	 * @author mqfdy
	 * @return the output file path
	 * @Date 2018-9-19 10:18:27
	 */
	public default String getOutputFilePath(){
		return this.getOutputPath()+File.separator+this.getPre()+this.getFileName()+this.getSuf()+this.getExtension();
	};

}
