package com.mqfdy.code.generator;

import java.util.Map;

// TODO: Auto-generated Javadoc
/**
 * 代码生成配置类.
 *
 * @author mqfdy
 */
public class MddConfiguration {
	
	/** The export path. */
	private String exportPath;
	
	/** The test export path. */
	private String testExportPath;
	
	/** The map. */
	private Map<String, Object> map;
	
	/** The context. */
	private GeneratorContext context;

	/**
	 * Gets the context.
	 *
	 * @author mqfdy
	 * @return the context
	 * @Date 2018-09-03 09:00
	 */
	public GeneratorContext getContext() {
		return context;
	}

	/**
	 * Sets the context.
	 *
	 * @author mqfdy
	 * @param context
	 *            the new context
	 * @Date 2018-09-03 09:00
	 */
	public void setContext(GeneratorContext context) {
		this.context = context;
	}

	/**
	 * Gets the export path.
	 *
	 * @author mqfdy
	 * @return the export path
	 * @Date 2018-09-03 09:00
	 */
	public String getExportPath() {
		return exportPath;
	}

	/**
	 * Sets the export path.
	 *
	 * @author mqfdy
	 * @param exportPath
	 *            the new export path
	 * @Date 2018-09-03 09:00
	 */
	public void setExportPath(String exportPath) {
		this.exportPath = exportPath;
	}

	/**
	 * Gets the map.
	 *
	 * @author mqfdy
	 * @return the map
	 * @Date 2018-09-03 09:00
	 */
	public Map<String, Object> getMap() {
		return map;
	}

	/**
	 * Sets the map.
	 *
	 * @author mqfdy
	 * @param map
	 *            the map
	 * @Date 2018-09-03 09:00
	 */
	public void setMap(Map<String, Object> map) {
		this.map = map;
	}

	/**
	 * Gets the test export path.
	 *
	 * @author mqfdy
	 * @return the test export path
	 * @Date 2018-09-03 09:00
	 */
	public String getTestExportPath()
	{
		return this.testExportPath;
	}
  
	/**
	 * Sets the test export path.
	 *
	 * @author mqfdy
	 * @param testExportPath
	 *            the new test export path
	 * @Date 2018-09-03 09:00
	 */
	public void setTestExportPath(String testExportPath)
	{
		this.testExportPath = testExportPath;
	}
}
