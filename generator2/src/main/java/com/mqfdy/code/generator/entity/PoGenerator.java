package com.mqfdy.code.generator.entity;

import org.eclipse.core.resources.IProject;

import com.mqfdy.code.generator.persistence.IPersistenceModel;

// TODO: Auto-generated Javadoc
/**
 * The Class PoGenerator.
 *
 * @author mqfdy
 */
public class PoGenerator extends EntityGenerator {

	/** The Constant TEMPLATE_FILE_PATH. */
	private static final String TEMPLATE_FILE_PATH = "template/po/Bean.vm";

	/**
	 * Instantiates a new po generator.
	 *
	 * @param genProject
	 *            the gen project
	 * @param persistenceModel
	 *            the persistence model
	 */
	public PoGenerator(IProject genProject,
			IPersistenceModel persistenceModel) {
		super(genProject, persistenceModel);
	}

	/**
	 * @see com.mqfdy.code.generator.model.AbstractGenerator#getTemplatePath()
	 * @return PoGenerator
	 */
	@Override
	protected String getTemplatePath() {
		return TEMPLATE_FILE_PATH;
	}

	/**
	 * @see com.mqfdy.code.generator.model.AbstractGenerator#getFileExtension()
	 * @return PoGenerator
	 */
	@Override
	protected String getFileExtension() {
		return JAVA_FILE_EXTENSION;
	}

	/**
	 * @see com.mqfdy.code.generator.model.IGenerator#getFileName()
	 * @return PoGenerator
	 */
	public String getFileName() {
		// TODO Auto-generated method stub
		return persistenceModel.getJavaName();
	}

}
