package com.mqfdy.code.wizard.wizard;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.jface.util.SafeRunnable;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.IWizardNode;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWizard;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.statushandlers.IStatusAdapterConstants;
import org.eclipse.ui.statushandlers.StatusAdapter;
import org.eclipse.ui.statushandlers.StatusManager;

public abstract class MicroGeneratorWizardNode implements IWizardNode {
	
	protected WizardPage parentWizardPage;
	protected IWizard wizard;
	protected IStructuredSelection currentSelection;
	
	public MicroGeneratorWizardNode(WizardPage aWizardPage,
			IStructuredSelection selection) {
		super();
		this.parentWizardPage = aWizardPage;
		currentSelection = selection;
	}
	
	public abstract IMicroGeneratorConfigWizard createWizard() throws CoreException;


	public void dispose() {
		// TODO Auto-generated method stub

	}


	public Point getExtent() {
		return new Point(-1, -1);
	}

	public IWizard getWizard() {
		if (wizard != null) {
			return wizard; // we've already created it
		}

		final IWorkbenchWizard[] workbenchWizard = new IWorkbenchWizard[1];
		final IStatus statuses[] = new IStatus[1];
		// Start busy indicator.
		BusyIndicator.showWhile(parentWizardPage.getShell().getDisplay(),
				new Runnable() {
					public void run() {
						SafeRunner.run(new SafeRunnable() {

							public void run() {
								try {
									workbenchWizard[0] = createWizard();
								} catch (CoreException e) {
									handleException(e);
								}
							}
						});
					}
				});

		if (statuses[0] != null) {
			parentWizardPage
					.setErrorMessage("The selected wizard could not be started");
			StatusAdapter statusAdapter = new StatusAdapter(statuses[0]);
			statusAdapter.addAdapter(Shell.class, parentWizardPage.getShell());
			statusAdapter.setProperty(IStatusAdapterConstants.TITLE_PROPERTY,
					"Problem Opening Wizard");
			StatusManager.getManager()
					.handle(statusAdapter, StatusManager.SHOW);
			return null;
		}

		IStructuredSelection currentSelection = getCurrentSelection();

		workbenchWizard[0].init(getWorkbench(), currentSelection);

		wizard = workbenchWizard[0];
		return wizard;
	}


	public boolean isContentCreated() {
		return wizard != null;
	}

	public WizardPage getParentWizardPage() {
		return parentWizardPage;
	}

	public void setParentWizardPage(WizardPage parentWizardPage) {
		this.parentWizardPage = parentWizardPage;
	}

	public void setWizard(IWizard wizard) {
		this.wizard = wizard;
	}

	public IStructuredSelection getCurrentSelection() {
		return currentSelection;
	}

	public void setCurrentSelection(IStructuredSelection currentSelection) {
		this.currentSelection = currentSelection;
	}
	
	protected IWorkbench getWorkbench() {
		// return parentWizardPage.getWorkbench();
		return PlatformUI.getWorkbench();
	}

}
