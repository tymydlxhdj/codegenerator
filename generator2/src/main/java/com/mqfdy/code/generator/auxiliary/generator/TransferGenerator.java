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
	 * @see com.mqfdy.code.generator.model.AbstractGenerator#getTemplatePath()
	 * @return TransferGenerator
	 */
	@Override
	protected String getTemplatePath() {
		return TEMPLATE_FILE_PATH;
	}
	
	/**
	 * @see com.mqfdy.code.generator.auxiliary.generator.BOMGenerator#getPackageExtention()
	 * @return TransferGenerator
	 */
	@Override
	public String getPackageExtention() {
		return PACKAGE_EXTENTION;
	}
	
	/**
	 * @see com.mqfdy.code.generator.auxiliary.generator.BOMGenerator#getFileNameSuffix()
	 * @return TransferGenerator
	 */
	@Override
	public String getFileNameSuffix() {
		return JAVA_FILE_SUFFIX;
	}
	
	/**
	 * @see com.mqfdy.code.generator.auxiliary.generator.BOMGenerator#getFileNamePrefix()
	 * @return TransferGenerator
	 */
	@Override
	public String getFileNamePrefix() {
		return JAVA_FILE_PREFIX;
	}

}
