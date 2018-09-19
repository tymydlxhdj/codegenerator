package com.mqfdy.code.wizard.wizard;

import org.eclipse.core.resources.IProject;
import org.eclipse.ui.IWorkbenchWizard;

// TODO: Auto-generated Javadoc
/**
 * The Interface IMicroGeneratorConfigWizard.
 *
 * @author mqfdy
 */
public interface IMicroGeneratorConfigWizard extends IWorkbenchWizard {
	
	/**
	 * Initialize.
	 *
	 * @author mqfdy
	 * @param omPath
	 *            the om path
	 * @param project
	 *            the project
	 * @Date 2018-09-03 09:00
	 */
	public void initialize(String omPath, IProject project);

}
