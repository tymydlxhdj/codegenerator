package com.mqfdy.code.template.designer.model;

// TODO: Auto-generated Javadoc
/**
 * The Class TemplateModel.
 *
 * @author mqfdy
 */
public class TemplateModel {
	
	/** The source code path. */
	private String sourceCodePath;
	
	/** The output path. */
	private String outputPath;
	
	/** The file name. */
	private String fileName;
	
	/** The class name. */
	private String className;
	
	private String packageSuf;
	

	/**
	 * Gets the source code path.
	 *
	 * @author mqfdy
	 * @return the source code path
	 * @Date 2019-6-5 11:36:24
	 */
	public String getSourceCodePath() {
		return sourceCodePath;
	}

	/**
	 * Sets the source code path.
	 *
	 * @author mqfdy
	 * @param sourceCodePath
	 *            the new source code path
	 * @Date 2019-6-5 11:36:24
	 */
	public void setSourceCodePath(String sourceCodePath) {
		this.sourceCodePath = sourceCodePath;
	}

	/**
	 * Gets the output path.
	 *
	 * @author mqfdy
	 * @return the output path
	 * @Date 2019-6-5 14:13:04
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
	 * @Date 2019-6-5 14:13:04
	 */
	public void setOutputPath(String outputPath) {
		this.outputPath = outputPath;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getPackageSuf() {
		return packageSuf;
	}

	public void setPackageSuf(String packageSuf) {
		this.packageSuf = packageSuf;
	}

}
