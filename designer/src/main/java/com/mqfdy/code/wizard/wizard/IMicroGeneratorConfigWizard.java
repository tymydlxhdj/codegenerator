package com.mqfdy.code.wizard.wizard;

import org.eclipse.core.resources.IProject;
import org.eclipse.ui.IWorkbenchWizard;

public interface IMicroGeneratorConfigWizard extends IWorkbenchWizard {
	
	public void initialize(String omPath, IProject project);

}
