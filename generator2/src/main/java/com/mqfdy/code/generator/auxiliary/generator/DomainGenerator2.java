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
	 * @see com.mqfdy.code.generator.auxiliary.generator.BOMGenerator#getFileExtension()
	 * @return DomainGenerator2
	 */
	@Override
	protected String getFileExtension() {
		return JAVA_FILE_EXTENSION;
	}

	/**
	 * @see com.mqfdy.code.generator.model.AbstractGenerator#getTemplatePath()
	 * @return DomainGenerator2
	 */
	@Override
	protected String getTemplatePath() {
		return TEMPLATE_FILE_PATH;
	}
	
	/**
	 * @see com.mqfdy.code.generator.auxiliary.generator.BOMGenerator#getPackageExtention()
	 * @return DomainGenerator2
	 */
	@Override
	public String getPackageExtention() {
		return PACKAGE_EXTENTION;
	}
	
	/**
	 * @see com.mqfdy.code.generator.auxiliary.generator.BOMGenerator#getFileNameSuffix()
	 * @return DomainGenerator2
	 */
	@Override
	public String getFileNameSuffix() {
		return JAVA_FILE_SUFFIX;
	}
	
	/**
	 * @see com.mqfdy.code.generator.auxiliary.generator.BOMGenerator#getFileNamePrefix()
	 * @return DomainGenerator2
	 */
	@Override
	public String getFileNamePrefix() {
		return JAVA_FILE_PREFIX;
	}

}
