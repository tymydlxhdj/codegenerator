package com.mqfdy.code.generator;

import java.util.Map;

/**
 * 代码生成配置类
 * 
 * @author mqfdy
 * 
 */
public class MddConfiguration {
	private String exportPath;
	private String testExportPath;
	private Map<String, Object> map;
	private GeneratorContext context;

	public GeneratorContext getContext() {
		return context;
	}

	public void setContext(GeneratorContext context) {
		this.context = context;
	}

	public String getExportPath() {
		return exportPath;
	}

	public void setExportPath(String exportPath) {
		this.exportPath = exportPath;
	}

	public Map<String, Object> getMap() {
		return map;
	}

	public void setMap(Map<String, Object> map) {
		this.map = map;
	}

	public String getTestExportPath()
	{
		return this.testExportPath;
	}
  
	public void setTestExportPath(String testExportPath)
	{
		this.testExportPath = testExportPath;
	}
}
