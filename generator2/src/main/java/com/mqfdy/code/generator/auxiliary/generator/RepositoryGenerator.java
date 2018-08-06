package com.mqfdy.code.generator.auxiliary.generator;

import com.mqfdy.code.generator.auxiliary.AbstractJavaClass;
/**
 * 
 * @author mqf
 *
 */
public class RepositoryGenerator extends BOMGenerator {
	
	private final static String PACKAGE_EXTENTION = ".repositories";
	
	private final static String JAVA_FILE_SUFFIX = "Repository";
	
	private final static String JAVA_FILE_PREFIX = "";
	
	private static final String TEMPLATE_FILE_PATH = "template/repositories/Repository2.vm";

	public RepositoryGenerator(AbstractJavaClass transferClass) {
		super(transferClass);
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
