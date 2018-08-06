package com.mqfdy.code.generator.auxiliary.generator;

import com.mqfdy.code.generator.auxiliary.AbstractJavaClass;
/**
 * 
 * @author mqf
 *
 */
public class ServiceGenerator extends BOMGenerator {
	
	private final static String PACKAGE_EXTENTION = ".services";
	
	private final static String JAVA_FILE_SUFFIX = "Service";
	
	private final static String JAVA_FILE_PREFIX = "";
	
	private static final String TEMPLATE_FILE_PATH = "template/services/Service2.vm";

	public ServiceGenerator(AbstractJavaClass serviceClass) {
		super(serviceClass);
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