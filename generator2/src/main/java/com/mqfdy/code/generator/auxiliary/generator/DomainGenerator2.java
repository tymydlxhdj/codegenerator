package com.mqfdy.code.generator.auxiliary.generator;

import com.mqfdy.code.generator.auxiliary.AbstractJavaClass;
/**
 * 
 * @author mqf
 *
 */
public class DomainGenerator2 extends BOMGenerator {
	
	private final static String PACKAGE_EXTENTION = ".domain";
	
	private final static String JAVA_FILE_SUFFIX = "";
	
	private final static String JAVA_FILE_PREFIX = "";
	
	private final static String TEMPLATE_FILE_PATH = "template/domain/Bean2.vm";

	public DomainGenerator2(AbstractJavaClass domainClass) {
		super(domainClass);
	}

	@Override
	protected String getFileExtension() {
		return JAVA_FILE_EXTENSION;
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
