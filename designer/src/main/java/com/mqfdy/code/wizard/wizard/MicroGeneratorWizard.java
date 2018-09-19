package com.mqfdy.code.wizard.wizard;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

import com.mqfdy.code.wizard.wizardpages.MicroSelectProjectWizardPage;

// TODO: Auto-generated Javadoc
/**
 * The Class MicroGeneratorWizard.
 *
 * @author mqfdy
 */
public class MicroGeneratorWizard extends Wizard implements INewWizard {
	
	/** The micro select project wizard page. */
	private MicroSelectProjectWizardPage microSelectProjectWizardPage;
	
	/** The project. */
	private IProject project;
	
	/** The selection. */
	private IStructuredSelection selection;
	
	
	/**
	 * 
	 */
	public void addPages(){
		microSelectProjectWizardPage = new MicroSelectProjectWizardPage("microSelectProjectWizardPage", project);
		addPage(microSelectProjectWizardPage);
		microSelectProjectWizardPage.setSelection(selection);
	}


	/**
	 * Inits the.
	 *
	 * @author mqfdy
	 * @param workbench
	 *            the workbench
	 * @param selection
	 *            the selection
	 * @Date 2018-09-03 09:00
	 */
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		setWindowTitle("生成代码");
		if (selection != null && !selection.isEmpty()) {
			Object element = ((IStructuredSelection)selection).getFirstElement();
			if (element instanceof IProject) {
				IProject tempProject = ((IProject) element).getProject();
				project = tempProject;
			} else if (element instanceof IJavaProject) {
				IJavaProject tempProject = (IJavaProject)element;
				project = tempProject.getProject();
			}else if(element instanceof IFolder) {
				IFolder folder = ((IFolder) element);
				project = folder.getProject();
			} else if (element instanceof IFile) {
				IFile file = ((IFile) element);
				project = file.getProject();
			}
			this.setSelection(selection);
		}
	}

	/**
	 * @return
	 */
	@Override
	public boolean performFinish() {
		return false;
	}
	
	 /**
 	 * @return
 	 */
 	@Override
		public boolean canFinish() {
			return microSelectProjectWizardPage.getMicroGeneratorConfigWizard() != null;
	    }


	/**
	 * Gets the project.
	 *
	 * @author mqfdy
	 * @return the project
	 * @Date 2018-09-03 09:00
	 */
	public IProject getProject() {
		return project;
	}

	/**
	 * Sets the project.
	 *
	 * @author mqfdy
	 * @param project
	 *            the new project
	 * @Date 2018-09-03 09:00
	 */
	public void setProject(IProject project) {
		this.project = project;
	}

	/**
	 * Gets the selection.
	 *
	 * @author mqfdy
	 * @return the selection
	 * @Date 2018-09-03 09:00
	 */
	public IStructuredSelection getSelection() {
		return selection;
	}

	/**
	 * Sets the selection.
	 *
	 * @author mqfdy
	 * @param selection
	 *            the new selection
	 * @Date 2018-09-03 09:00
	 */
	public void setSelection(IStructuredSelection selection) {
		this.selection = selection;
	}

}
