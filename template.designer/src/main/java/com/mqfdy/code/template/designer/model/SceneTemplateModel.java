package com.mqfdy.code.template.designer.model;

public class SceneTemplateModel {
	
	/** The source code path. */
	private String sourceCodePath;
	
	/** The depend jar path. */
	private String dependJarPath;
	
	/** The depend script file path. */
	private String dependScriptFilePath;
	
	/** The output path. */
	private String outputPath;
	
	/** The package packagePre. */
	private String packagePre;
	
	public SceneTemplateModel(String sourceCodePath,String dependJarPath,
			String dependScriptFilePath,String outputPath){
		this.sourceCodePath = sourceCodePath;
		this.dependJarPath = dependJarPath;
		this.dependScriptFilePath = dependScriptFilePath;
		this.outputPath = outputPath;
	}
	


	public String getSourceCodePath() {
		return sourceCodePath;
	}

	public void setSourceCodePath(String sourceCodePath) {
		this.sourceCodePath = sourceCodePath;
	}

	public String getDependJarPath() {
		return dependJarPath;
	}

	public void setDependJarPath(String dependJarPath) {
		this.dependJarPath = dependJarPath;
	}

	public String getDependScriptFilePath() {
		return dependScriptFilePath;
	}

	public void setDependScriptFilePath(String dependScriptFilePath) {
		this.dependScriptFilePath = dependScriptFilePath;
	}

	public String getOutputPath() {
		return outputPath;
	}

	public void setOutputPath(String outputPath) {
		this.outputPath = outputPath;
	}

	public String getPackagePre() {
		return packagePre;
	}

	public void setPackagePre(String packagePre) {
		this.packagePre = packagePre;
	}

}
