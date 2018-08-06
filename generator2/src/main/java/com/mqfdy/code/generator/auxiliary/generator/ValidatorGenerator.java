package com.mqfdy.code.generator.auxiliary.generator;

import com.mqfdy.code.generator.auxiliary.AbstractJavaClass;
/**
 * 
 * @author mqf
 *
 */
public class ValidatorGenerator extends BOMGenerator {
	
	private final static String PACKAGE_EXTENTION = ".validator";
	
	private final static String JAVA_FILE_SUFFIX = "";
	
	private final static String JAVA_FILE_PREFIX = "";
	
	private final static String TEMPLATE_FILE_PATH = "template/validator/Validator.vm";
	
	private String className;

	public ValidatorGenerator(AbstractJavaClass validatorClass) {
		super(validatorClass);
		this.className = map.get("validatorClassName").toString();
	}

	@Override
	public String getFileName() {
		return className;
	}

	@Override
	protected String getFileNameWithoutExtension() {
		return className;
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
