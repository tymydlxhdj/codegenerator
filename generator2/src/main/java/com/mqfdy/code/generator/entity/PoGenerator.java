package com.mqfdy.code.generator.entity;

import org.eclipse.core.resources.IProject;

import com.mqfdy.code.generator.persistence.IPersistenceModel;

public class PoGenerator extends EntityGenerator {

	private static final String TEMPLATE_FILE_PATH = "template/po/Bean.vm";

	public PoGenerator(IProject genProject,
			IPersistenceModel persistenceModel) {
		super(genProject, persistenceModel);
	}

	@Override
	protected String getTemplatePath() {
		return TEMPLATE_FILE_PATH;
	}

	@Override
	protected String getFileExtension() {
		return JAVA_FILE_EXTENSION;
	}

	public String getFileName() {
		// TODO Auto-generated method stub
		return persistenceModel.getJavaName();
	}

}
