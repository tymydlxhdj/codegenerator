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
	 * Gets the file name.
	 *
	 * @return ValidatorGenerator
	 * @see com.mqfdy.code.generator.auxiliary.generator.BOMGenerator#getFileName()
	 */
	@Override
	public String getFileName() {
		return className;
	}

	/**
	 * Gets the file name without extension.
	 *
	 * @return ValidatorGenerator
	 * @see com.mqfdy.code.generator.auxiliary.generator.BOMGenerator#getFileNameWithoutExtension()
	 */
	@Override
	public String getFileNameWithoutExtension() {
		return className;
	}
	
	/**
	 * Gets the file extension.
	 *
	 * @return ValidatorGenerator
	 * @see com.mqfdy.code.generator.auxiliary.generator.BOMGenerator#getFileExtension()
	 */
	@Override
	public String getFileExtension() {
		return JAVA_FILE_EXTENSION;
	}

	/**
	 * Gets the template path.
	 *
	 * @return ValidatorGenerator
	 * @see com.mqfdy.code.generator.model.AbstractGenerator#getTemplatePath()
	 */
	@Override
	protected String getTemplatePath() {
		return TEMPLATE_FILE_PATH;
	}
	
	/**
	 * Gets the package extention.
	 *
	 * @return ValidatorGenerator
	 * @see com.mqfdy.code.generator.auxiliary.generator.BOMGenerator#getPackageExtention()
	 */
	@Override
	public String getPackageExtention() {
		return PACKAGE_EXTENTION;
	}
	
	/**
	 * Gets the file name suffix.
	 *
	 * @return ValidatorGenerator
	 * @see com.mqfdy.code.generator.auxiliary.generator.BOMGenerator#getFileNameSuffix()
	 */
	@Override
	public String getFileNameSuffix() {
		return JAVA_FILE_SUFFIX;
	}
	
	/**
	 * Gets the file name prefix.
	 *
	 * @return ValidatorGenerator
	 * @see com.mqfdy.code.generator.auxiliary.generator.BOMGenerator#getFileNamePrefix()
	 */
	@Override
	public String getFileNamePrefix() {
		return JAVA_FILE_PREFIX;
	}

}
