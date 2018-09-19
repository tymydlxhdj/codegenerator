package com.mqfdy.code.generator.auxiliary.generator;

import com.mqfdy.code.generator.auxiliary.AbstractJavaClass;
// TODO: Auto-generated Javadoc

/**
 * The Class ServiceGenerator.
 *
 * @author mqf
 */
public class ServiceGenerator extends BOMGenerator {
	
	/** The Constant PACKAGE_EXTENTION. */
	private final static String PACKAGE_EXTENTION = ".services";
	
	/** The Constant JAVA_FILE_SUFFIX. */
	private final static String JAVA_FILE_SUFFIX = "Service";
	
	/** The Constant JAVA_FILE_PREFIX. */
	private final static String JAVA_FILE_PREFIX = "";
	
	/** The Constant TEMPLATE_FILE_PATH. */
	private static final String TEMPLATE_FILE_PATH = "template/services/Service2.vm";

	/**
	 * Instantiates a new service generator.
	 *
	 * @param serviceClass
	 *            the service class
	 */
	public ServiceGenerator(AbstractJavaClass serviceClass) {
		super(serviceClass);
	}
	
	/**
	 * @see com.mqfdy.code.generator.model.AbstractGenerator#getTemplatePath()
	 * @return ServiceGenerator
	 */
	@Override
	protected String getTemplatePath() {
		return TEMPLATE_FILE_PATH;
	}
	
	/**
	 * @see com.mqfdy.code.generator.auxiliary.generator.BOMGenerator#getPackageExtention()
	 * @return ServiceGenerator
	 */
	@Override
	public String getPackageExtention() {
		return PACKAGE_EXTENTION;
	}
	
	/**
	 * @see com.mqfdy.code.generator.auxiliary.generator.BOMGenerator#getFileNameSuffix()
	 * @return ServiceGenerator
	 */
	@Override
	public String getFileNameSuffix() {
		return JAVA_FILE_SUFFIX;
	}
	
	/**
	 * @see com.mqfdy.code.generator.auxiliary.generator.BOMGenerator#getFileNamePrefix()
	 * @return ServiceGenerator
	 */
	@Override
	public String getFileNamePrefix() {
		return JAVA_FILE_PREFIX;
	}
}
