package com.mqfdy.code.springboot.ui.wizards;

import java.io.File;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IImportWizard;
import org.eclipse.ui.IWorkbench;
import org.springsource.ide.eclipse.gradle.core.util.ErrorHandler;
import org.springsource.ide.eclipse.gradle.core.wizards.GradleImportOperation;

import com.mqfdy.code.springboot.core.util.JobUtil;
import com.mqfdy.code.springboot.core.util.Operation2Runnable;
 // TODO: Auto-generated Javadoc

 /**
	 * The Class MicroProjectImportWizard.
	 *
	 * @author lenovo
	 */
public class MicroProjectImportWizard extends Wizard implements IImportWizard {

	private ImportWizardPageOne pageOne;

	/**
	 * Instantiates a new micro project import wizard.
	 */
	public MicroProjectImportWizard() {
	}

	public void init(IWorkbench workbench, IStructuredSelection selection) {
	}
	
	public void addPages() {
		super.addPages();
		addPage(getPageOne());
	}

	private ImportWizardPageOne getPageOne() {
		if (pageOne==null) {
			pageOne = new ImportWizardPageOne();
		}
		return pageOne;
	}
	
	@Override
	public boolean performFinish() {
		getPageOne().wizardAboutToFinish();
		JobUtil.userJob(new Operation2Runnable("Import Springboot Projects") {
			@Override
			public void doit(IProgressMonitor monitor) throws OperationCanceledException, CoreException {
				Object[] objs = getPageOne().getImportElements();
				if(objs != null){
					for (Object o : objs) {
						if(o instanceof File){
							File f = (File)o;
							//reference Gradle Plugin
							GradleImportOperation importOp = GradleImportOperation.importAll(f.getAbsoluteFile());
							ErrorHandler eh = ErrorHandler.forImportWizard();
							importOp.perform(eh, new SubProgressMonitor(monitor, 1));
							eh.rethrowAsCore();
						}
					}
				}
			}
		});
		return true;
	}

}
