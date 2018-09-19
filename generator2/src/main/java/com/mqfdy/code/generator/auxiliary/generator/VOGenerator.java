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
	 * @see com.mqfdy.code.generator.model.AbstractGenerator#getTemplatePath()
	 * @return VOGenerator
	 */
	@Override
	protected String getTemplatePath() {
		return TEMPLATE_FILE_PATH;
	}
	
	/**
	 * @see com.mqfdy.code.generator.auxiliary.generator.BOMGenerator#getPackageExtention()
	 * @return VOGenerator
	 */
	@Override
	public String getPackageExtention() {
		return PACKAGE_EXTENTION;
	}
	
	/**
	 * @see com.mqfdy.code.generator.auxiliary.generator.BOMGenerator#getFileNameSuffix()
	 * @return VOGenerator
	 */
	@Override
	public String getFileNameSuffix() {
		return JAVA_FILE_SUFFIX;
	}
	
	/**
	 * @see com.mqfdy.code.generator.auxiliary.generator.BOMGenerator#getFileNamePrefix()
	 * @return VOGenerator
	 */
	@Override
	public String getFileNamePrefix() {
		return JAVA_FILE_PREFIX;
	}
}
