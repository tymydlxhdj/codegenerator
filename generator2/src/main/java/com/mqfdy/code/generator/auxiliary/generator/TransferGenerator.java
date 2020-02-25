package com.mqfdy.code.generator.auxiliary.generator;

import com.mqfdy.code.generator.auxiliary.AbstractJavaClass;
// TODO: Auto-generated Javadoc

/**
 * The Class TransferGenerator.
 *
 * @author mqf
 */
public class TransferGenerator extends BOMGenerator {
	
	/** The Constant PACKAGE_EXTENTION. */
	private final static String PACKAGE_EXTENTION = ".vo";
	
	/** The Constant JAVA_FILE_SUFFIX. */
	private final static String JAVA_FILE_SUFFIX = "Transfer";
	
	/** The Constant JAVA_FILE_PREFIX. */
	private final static String JAVA_FILE_PREFIX = "";
	
	/** The Constant TEMPLATE_FILE_PATH. */
	private static final String TEMPLATE_FILE_PATH = "template/vo/Transfer2.vm";

	/**
	 * Instantiates a new transfer generator.
	 *
	 * @param repositoryClass
	 *            the repository class
	 */
	public TransferGenerator(AbstractJavaClass repositoryClass) {
		super(repositoryClass);
	}
	
	/**
	 * Gets the template path.
	 *
	 * @return TransferGenerator
	 * @see com.mqfdy.code.generator.model.AbstractGenerator#getTemplatePath()
	 */
	@Override
	protected String getTemplatePath() {
		return TEMPLATE_FILE_PATH;
	}
	
	/**
	 * Gets the package extention.
	 *
	 * @return TransferGenerator
	 * @see com.mqfdy.code.generator.auxiliary.generator.BOMGenerator#getPackageExtention()
	 */
	@Override
	public String getPackageExtention() {
		return PACKAGE_EXTENTION;
	}
	
	/**
	 * Gets the file name suffix.
	 *
	 * @return TransferGenerator
	 * @see com.mqfdy.code.generator.auxiliary.generator.BOMGenerator#getFileNameSuffix()
	 */
	@Override
	public String getFileNameSuffix() {
		return JAVA_FILE_SUFFIX;
	}
	
	/**
	 * Gets the file name prefix.
	 *
	 * @return TransferGenerator
	 * @see com.mqfdy.code.generator.auxiliary.generator.BOMGenerator#getFileNamePrefix()
	 */
	@Override
	public String getFileNamePrefix() {
		return JAVA_FILE_PREFIX;
	}

}
