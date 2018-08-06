package com.mqfdy.bom.project.wizard;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

import com.mqfdy.bom.project.util.JobUtil;
import com.mqfdy.bom.project.util.Operation2Runnable;


public class NewBOMProjectWizard extends Wizard implements INewWizard {
	
	private final BOMProjectOperation operation = new BOMProjectOperation();
	private ProjectInfoPage infoPage;
	
	public NewBOMProjectWizard(){
		setWindowTitle("新建BOM模型项目");
	}

	public void init(IWorkbench workbench, IStructuredSelection selection) {
		setNeedsProgressMonitor(true);
	}

	@Override
	public void addPages() {
		infoPage = new ProjectInfoPage(operation);
		addPage(infoPage);
	}

	@Override
	public boolean performFinish() {
		JobUtil.userJob(new Operation2Runnable("Create BOM project") {
			@Override
			public void doit(IProgressMonitor monitor) throws OperationCanceledException, CoreException {
				operation.perform(monitor);
			}
		});
		return true;
	}

}
