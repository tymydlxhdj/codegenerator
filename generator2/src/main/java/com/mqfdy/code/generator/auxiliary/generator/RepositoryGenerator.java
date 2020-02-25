package com.mqfdy.code.generator.auxiliary.generator;

import com.mqfdy.code.generator.auxiliary.AbstractJavaClass;
// TODO: Auto-generated Javadoc

/**
 * The Class RepositoryGenerator.
 *
 * @author mqf
 */
public class RepositoryGenerator extends BOMGenerator {
	
	/** The Constant PACKAGE_EXTENTION. */
	private final static String PACKAGE_EXTENTION = ".repositories";
	
	/** The Constant JAVA_FILE_SUFFIX. */
	private final static String JAVA_FILE_SUFFIX = "Repository";
	
	/** The Constant JAVA_FILE_PREFIX. */
	private final static String JAVA_FILE_PREFIX = "";
	
	/** The Constant TEMPLATE_FILE_PATH. */
	private static final String TEMPLATE_FILE_PATH = "template/repositories/Repository2.vm";

	/**
	 * Instantiates a new repository generator.
	 *
	 * @param transferClass
	 *            the transfer class
	 */
	public RepositoryGenerator(AbstractJavaClass transferClass) {
		super(transferClass);
	}

	/**
	 * Gets the template path.
	 *
	 * @return RepositoryGenerator
	 * @see com.mqfdy.code.generator.model.AbstractGenerator#getTemplatePath()
	 */
	@Override
	protected String getTemplatePath() {
		return TEMPLATE_FILE_PATH;
	}
	
	/**
	 * Gets the package extention.
	 *
	 * @return RepositoryGenerator
	 * @see com.mqfdy.code.generator.auxiliary.generator.BOMGenerator#getPackageExtention()
	 */
	@Override
	public String getPackageExtention() {
		return PACKAGE_EXTENTION;
	}
	
	/**
	 * Gets the file name suffix.
	 *
	 * @return RepositoryGenerator
	 * @see com.mqfdy.code.generator.auxiliary.generator.BOMGenerator#getFileNameSuffix()
	 */
	@Override
	public String getFileNameSuffix() {
		return JAVA_FILE_SUFFIX;
	}
	
	/**
	 * Gets the file name prefix.
	 *
	 * @return RepositoryGenerator
	 * @see com.mqfdy.code.generator.auxiliary.generator.BOMGenerator#getFileNamePrefix()
	 */
	@Override
	public String getFileNamePrefix() {
		return JAVA_FILE_PREFIX;
	}

}
