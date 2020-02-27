package com.mqfdy.code.generator.entity;

import java.util.List;

import org.eclipse.core.resources.IProject;

import com.mqfdy.code.generator.model.CodeGenerationException;
import com.mqfdy.code.generator.model.ICodeFileMaterial;
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
	 * Gets the template path.
	 *
	 * @return PoGenerator
	 * @see com.mqfdy.code.generator.model.AbstractGenerator#getTemplatePath()
	 */
	@Override
	protected String getTemplatePath() {
		return TEMPLATE_FILE_PATH;
	}

	/**
	 * Gets the file extension.
	 *
	 * @return PoGenerator
	 * @see com.mqfdy.code.generator.model.AbstractGenerator#getFileExtension()
	 */
	@Override
	protected String getFileExtension() {
		return JAVA_FILE_EXTENSION;
	}

	/**
	 * Gets the file name.
	 *
	 * @return PoGenerator
	 * @see com.mqfdy.code.generator.model.IGenerator#getFileName()
	 */
	public String getFileName() {
		// TODO Auto-generated method stub
		return persistenceModel.getJavaName();
	}
}
