package com.mqfdy.code.generator.auxiliary.generator;

import com.mqfdy.code.generator.auxiliary.AbstractJavaClass;
/**
 * 
 * @author mqf
 *
 */
public class ControllerGenerator extends BOMGenerator {
	
	private final static String PACKAGE_EXTENTION = ".controller";
	
	private final static String JAVA_FILE_SUFFIX = "Controller";
	
	private final static String JAVA_FILE_PREFIX = "";
	
	private static final String TEMPLATE_FILE_PATH = "template/controller/Controller2.vm";

	public ControllerGenerator(AbstractJavaClass controllerClass) {
		super(controllerClass);
	}
	@Override
	protected String getTemplatePath() {
		return TEMPLATE_FILE_PATH;
	}
	@Override
	public String getPackageExtention() {
		return PACKAGE_EXTENTION;
	}
	@Override
	public String getFileNameSuffix() {
		return JAVA_FILE_SUFFIX;
	}
	@Override
	public String getFileNamePrefix() {
		return JAVA_FILE_PREFIX;
	}
}
