package com.mqfdy.code.generator.auxiliary.generator;

import org.eclipse.core.resources.IProject;

import com.mqfdy.code.generator.auxiliary.AbstractJavaClass;
import com.mqfdy.code.generator.persistence.IPersistenceModel;
/**
 * 
 * @author mqf
 *
 */
public class VOGenerator extends BOMGenerator {
	
	private final static String PACKAGE_EXTENTION = ".vo";
	
	private final static String JAVA_FILE_SUFFIX = "VO";
	
	private final static String JAVA_FILE_PREFIX = "";
	
	private static final String TEMPLATE_FILE_PATH = "template/vo/VO2.vm";

	public VOGenerator(AbstractJavaClass voClass) {
		super(voClass);
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
