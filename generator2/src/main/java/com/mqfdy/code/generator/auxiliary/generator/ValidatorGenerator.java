package com.mqfdy.code.generator.auxiliary.generator;

import com.mqfdy.code.generator.auxiliary.AbstractJavaClass;
// TODO: Auto-generated Javadoc

/**
 * The Class ValidatorGenerator.
 *
 * @author mqf
 */
public class ValidatorGenerator extends BOMGenerator {
	
	/** The Constant PACKAGE_EXTENTION. */
	private final static String PACKAGE_EXTENTION = ".validator";
	
	/** The Constant JAVA_FILE_SUFFIX. */
	private final static String JAVA_FILE_SUFFIX = "";
	
	/** The Constant JAVA_FILE_PREFIX. */
	private final static String JAVA_FILE_PREFIX = "";
	
	/** The Constant TEMPLATE_FILE_PATH. */
	private final static String TEMPLATE_FILE_PATH = "template/validator/Validator.vm";
	
	/** The class name. */
	private String className;

	/**
	 * Instantiates a new validator generator.
	 *
	 * @param validatorClass
	 *            the validator class
	 */
	public ValidatorGenerator(AbstractJavaClass validatorClass) {
		super(validatorClass);
		this.className = map.get("validatorClassName").toString();
	}

	/**
	 * @see com.mqfdy.code.generator.auxiliary.generator.BOMGenerator#getFileName()
	 * @return ValidatorGenerator
	 */
	@Override
	public String getFileName() {
		return className;
	}

	/**
	 * @see com.mqfdy.code.generator.auxiliary.generator.BOMGenerator#getFileNameWithoutExtension()
	 * @return ValidatorGenerator
	 */
	@Override
	protected String getFileNameWithoutExtension() {
		return className;
	}
	
	/**
	 * @see com.mqfdy.code.generator.auxiliary.generator.BOMGenerator#getFileExtension()
	 * @return ValidatorGenerator
	 */
	@Override
	protected String getFileExtension() {
		return JAVA_FILE_EXTENSION;
	}

	/**
	 * @see com.mqfdy.code.generator.model.AbstractGenerator#getTemplatePath()
	 * @return ValidatorGenerator
	 */
	@Override
	protected String getTemplatePath() {
		return TEMPLATE_FILE_PATH;
	}
	
	/**
	 * @see com.mqfdy.code.generator.auxiliary.generator.BOMGenerator#getPackageExtention()
	 * @return ValidatorGenerator
	 */
	@Override
	public String getPackageExtention() {
		return PACKAGE_EXTENTION;
	}
	
	/**
	 * @see com.mqfdy.code.generator.auxiliary.generator.BOMGenerator#getFileNameSuffix()
	 * @return ValidatorGenerator
	 */
	@Override
	public String getFileNameSuffix() {
		return JAVA_FILE_SUFFIX;
	}
	
	/**
	 * @see com.mqfdy.code.generator.auxiliary.generator.BOMGenerator#getFileNamePrefix()
	 * @return ValidatorGenerator
	 */
	@Override
	public String getFileNamePrefix() {
		return JAVA_FILE_PREFIX;
	}

}
