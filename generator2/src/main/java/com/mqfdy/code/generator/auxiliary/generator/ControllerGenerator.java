package com.mqfdy.code.generator.auxiliary.generator;

import com.mqfdy.code.generator.auxiliary.AbstractJavaClass;
// TODO: Auto-generated Javadoc

/**
 * The Class ControllerGenerator.
 *
 * @author mqf
 */
public class ControllerGenerator extends BOMGenerator {
	
	/** The Constant PACKAGE_EXTENTION. */
	private final static String PACKAGE_EXTENTION = ".controller";
	
	/** The Constant JAVA_FILE_SUFFIX. */
	private final static String JAVA_FILE_SUFFIX = "Controller";
	
	/** The Constant JAVA_FILE_PREFIX. */
	private final static String JAVA_FILE_PREFIX = "";
	
	/** The Constant TEMPLATE_FILE_PATH. */
	private static final String TEMPLATE_FILE_PATH = "template/controller/Controller2.vm";

	/**
	 * Instantiates a new controller generator.
	 *
	 * @param controllerClass
	 *            the controller class
	 */
	public ControllerGenerator(AbstractJavaClass controllerClass) {
		super(controllerClass);
	}
	
	/**
	 * @see com.mqfdy.code.generator.model.AbstractGenerator#getTemplatePath()
	 * @return ControllerGenerator
	 */
	@Override
	protected String getTemplatePath() {
		return TEMPLATE_FILE_PATH;
	}
	
	/**
	 * @see com.mqfdy.code.generator.auxiliary.generator.BOMGenerator#getPackageExtention()
	 * @return ControllerGenerator
	 */
	@Override
	public String getPackageExtention() {
		return PACKAGE_EXTENTION;
	}
	
	/**
	 * @see com.mqfdy.code.generator.auxiliary.generator.BOMGenerator#getFileNameSuffix()
	 * @return ControllerGenerator
	 */
	@Override
	public String getFileNameSuffix() {
		return JAVA_FILE_SUFFIX;
	}
	
	/**
	 * @see com.mqfdy.code.generator.auxiliary.generator.BOMGenerator#getFileNamePrefix()
	 * @return ControllerGenerator
	 */
	@Override
	public String getFileNamePrefix() {
		return JAVA_FILE_PREFIX;
	}
}
