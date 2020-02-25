package com.mqfdy.code.generator.auxiliary.generator;

import com.mqfdy.code.generator.auxiliary.AbstractJavaClass;
// TODO: Auto-generated Javadoc

/**
 * The Class DomainGenerator2.
 *
 * @author mqf
 */
public class DomainGenerator2 extends BOMGenerator {
	
	/** The Constant PACKAGE_EXTENTION. */
	private final static String PACKAGE_EXTENTION = ".domain";
	
	/** The Constant JAVA_FILE_SUFFIX. */
	private final static String JAVA_FILE_SUFFIX = "";
	
	/** The Constant JAVA_FILE_PREFIX. */
	private final static String JAVA_FILE_PREFIX = "";
	
	/** The Constant TEMPLATE_FILE_PATH. */
	private final static String TEMPLATE_FILE_PATH = "template/domain/Bean2.vm";

	/**
	 * Instantiates a new domain generator 2.
	 *
	 * @param domainClass
	 *            the domain class
	 */
	public DomainGenerator2(AbstractJavaClass domainClass) {
		super(domainClass);
	}

	/**
	 * Gets the file extension.
	 *
	 * @return DomainGenerator2
	 * @see com.mqfdy.code.generator.auxiliary.generator.BOMGenerator#getFileExtension()
	 */
	@Override
	public String getFileExtension() {
		return JAVA_FILE_EXTENSION;
	}

	/**
	 * Gets the template path.
	 *
	 * @return DomainGenerator2
	 * @see com.mqfdy.code.generator.model.AbstractGenerator#getTemplatePath()
	 */
	@Override
	protected String getTemplatePath() {
		return TEMPLATE_FILE_PATH;
	}
	
	/**
	 * Gets the package extention.
	 *
	 * @return DomainGenerator2
	 * @see com.mqfdy.code.generator.auxiliary.generator.BOMGenerator#getPackageExtention()
	 */
	@Override
	public String getPackageExtention() {
		return PACKAGE_EXTENTION;
	}
	
	/**
	 * Gets the file name suffix.
	 *
	 * @return DomainGenerator2
	 * @see com.mqfdy.code.generator.auxiliary.generator.BOMGenerator#getFileNameSuffix()
	 */
	@Override
	public String getFileNameSuffix() {
		return JAVA_FILE_SUFFIX;
	}
	
	/**
	 * Gets the file name prefix.
	 *
	 * @return DomainGenerator2
	 * @see com.mqfdy.code.generator.auxiliary.generator.BOMGenerator#getFileNamePrefix()
	 */
	@Override
	public String getFileNamePrefix() {
		return JAVA_FILE_PREFIX;
	}

}
