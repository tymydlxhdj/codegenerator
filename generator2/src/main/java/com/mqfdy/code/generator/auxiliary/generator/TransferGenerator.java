package com.mqfdy.code.generator.auxiliary.generator;

import com.mqfdy.code.generator.auxiliary.AbstractJavaClass;
/**
 * 
 * @author mqf
 *
 */
public class TransferGenerator extends BOMGenerator {
	
	private final static String PACKAGE_EXTENTION = ".vo";
	
	private final static String JAVA_FILE_SUFFIX = "Transfer";
	
	private final static String JAVA_FILE_PREFIX = "";
	
	private static final String TEMPLATE_FILE_PATH = "template/vo/Transfer2.vm";

	public TransferGenerator(AbstractJavaClass repositoryClass) {
		super(repositoryClass);
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
