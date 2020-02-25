package com.mqfdy.code.generator.auxiliary.generator;

import org.eclipse.core.resources.IProject;

import com.mqfdy.code.generator.auxiliary.AbstractJavaClass;
import com.mqfdy.code.generator.persistence.IPersistenceModel;
// TODO: Auto-generated Javadoc

/**
 * The Class VOGenerator.
 *
 * @author mqf
 */
public class VOGenerator extends BOMGenerator {
	
	/** The Constant PACKAGE_EXTENTION. */
	private final static String PACKAGE_EXTENTION = ".vo";
	
	/** The Constant JAVA_FILE_SUFFIX. */
	private final static String JAVA_FILE_SUFFIX = "VO";
	
	/** The Constant JAVA_FILE_PREFIX. */
	private final static String JAVA_FILE_PREFIX = "";
	
	/** The Constant TEMPLATE_FILE_PATH. */
	private static final String TEMPLATE_FILE_PATH = "template/vo/VO2.vm";

	/**
	 * Instantiates a new VO generator.
	 *
	 * @param voClass
	 *            the vo class
	 */
	public VOGenerator(AbstractJavaClass voClass) {
		super(voClass);
	}


	/**
	 * Gets the template path.
	 *
	 * @return VOGenerator
	 * @see com.mqfdy.code.generator.model.AbstractGenerator#getTemplatePath()
	 */
	@Override
	protected String getTemplatePath() {
		return TEMPLATE_FILE_PATH;
	}
	
	/**
	 * Gets the package extention.
	 *
	 * @return VOGenerator
	 * @see com.mqfdy.code.generator.auxiliary.generator.BOMGenerator#getPackageExtention()
	 */
	@Override
	public String getPackageExtention() {
		return PACKAGE_EXTENTION;
	}
	
	/**
	 * Gets the file name suffix.
	 *
	 * @return VOGenerator
	 * @see com.mqfdy.code.generator.auxiliary.generator.BOMGenerator#getFileNameSuffix()
	 */
	@Override
	public String getFileNameSuffix() {
		return JAVA_FILE_SUFFIX;
	}
	
	/**
	 * Gets the file name prefix.
	 *
	 * @return VOGenerator
	 * @see com.mqfdy.code.generator.auxiliary.generator.BOMGenerator#getFileNamePrefix()
	 */
	@Override
	public String getFileNamePrefix() {
		return JAVA_FILE_PREFIX;
	}
}
