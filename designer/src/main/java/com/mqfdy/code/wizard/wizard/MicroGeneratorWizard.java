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

public class MicroGeneratorWizard extends Wizard implements INewWizard {
	
	private MicroSelectProjectWizardPage microSelectProjectWizardPage;
	private IProject project;
	private IStructuredSelection selection;
	
	
	public void addPages(){
		microSelectProjectWizardPage = new MicroSelectProjectWizardPage("microSelectProjectWizardPage", project);
		addPage(microSelectProjectWizardPage);
		microSelectProjectWizardPage.setSelection(selection);
	}


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

	@Override
	public boolean performFinish() {
		return false;
	}
	
	 @Override
		public boolean canFinish() {
			return microSelectProjectWizardPage.getMicroGeneratorConfigWizard() != null;
	    }


	public IProject getProject() {
		return project;
	}

	public void setProject(IProject project) {
		this.project = project;
	}

	public IStructuredSelection getSelection() {
		return selection;
	}

	public void setSelection(IStructuredSelection selection) {
		this.selection = selection;
	}

}
