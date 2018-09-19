package com.mqfdy.code.springboot.ui.wizards;

import org.eclipse.jdt.internal.ui.JavaPlugin;
import org.eclipse.jdt.internal.ui.workingsets.IWorkingSetIDs;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.IWorkingSet;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.WorkingSetConfigurationBlock;


// TODO: Auto-generated Javadoc
/**
 * Note: the initial version of this class was originally copied from org.eclipse.jdt.ui.wizards.NewJavaProjectWizardPageOne.WorkingSetGroup.
 * <p>
 * It contains some code to create a group of UI widgets to choose whether or not to add imported projects to a Java working set.
 * <p>
 * We have augmented our version of this component with a button to enable the quick creation of a workingset based
 * on a default working set name (in our case, based on the name of the project at the root of the imported project
 * hierarchy.
 * <p>
 * Note that this extra button doesn't actually live inside the same group of widgets but is added to another group
 * of checkable options in the page (this to make the UI clearer and more intuitive). We still keep all the workingset
 * related GUI code here together however.
 * 
 * @author lenovo
 */
@SuppressWarnings("restriction")
public final class WorkingSetGroup {
	
	private WorkingSetConfigurationBlock fWorkingSetBlock;
	private String quickWorkingSetName;
	private Button quickWorkingSetButton;

	/**
	 * Instantiates a new working set group.
	 */
	public WorkingSetGroup() {
		String[] workingSetIds= new String[] { IWorkingSetIDs.JAVA, IWorkingSetIDs.RESOURCE };
		fWorkingSetBlock= new WorkingSetConfigurationBlock(workingSetIds, JavaPlugin.getDefault().getDialogSettings());
	}

	/**
	 * Creates the control.
	 *
	 * @author mqfdy
	 * @param placeForQuickWorkingSetButton
	 *            The parent composite to which the quick workingSetButton will
	 *            be added.
	 * @param parent
	 *            The parent composite to which the "standard" working set group
	 *            elements will be added.
	 * @return the control
	 * @Date 2018-09-03 09:00
	 */
	public Control createControl(Composite placeForQuickWorkingSetButton, Composite parent) {
		Group workingSetGroup= new Group(parent, SWT.NONE);
		workingSetGroup.setFont(parent.getFont());
		workingSetGroup.setText("Additional working sets");
		workingSetGroup.setLayout(new GridLayout(1, false));
		
		quickWorkingSetButton = new Button(placeForQuickWorkingSetButton, SWT.CHECK);
		quickWorkingSetButton.setText("Create working set based on root project name");
		quickWorkingSetButton.setToolTipText("Creates a working set named after the " +
						"root project and adds all imported projects to this working set.");
		quickWorkingSetButton.setSelection(true);

		fWorkingSetBlock.createContent(workingSetGroup);

		return workingSetGroup;
	}

	/**
	 * Gets the selected working sets.
	 *
	 * @author mqfdy
	 * @return the selected working sets
	 * @Date 2018-09-03 09:00
	 */
	public IWorkingSet[] getSelectedWorkingSets() {
		return fWorkingSetBlock.getSelectedWorkingSets();
	}
	
//	public void setSelectedWorkingSets(IWorkingSet[] workingSets) {
//		try { 
//			fWorkingSetBlock.setWorkingSets(workingSets);
//		} catch (IllegalArgumentException e) {
//			//A eclipse bug: https://bugs.eclipse.org/bugs/show_bug.cgi?id=358785
//			//causes this so this method won't work... but we can't really do much about it... so ignore.
//		}
//	}
	
	/**
 * Gets the quick working set name.
 *
 * @author mqfdy
 * @return the quick working set name
 * @Date 2018-09-03 09:00
 */
public String getQuickWorkingSetName() {
		if (quickWorkingSetButton.getSelection()) {
			return quickWorkingSetName;
		}
		return null;
	}

	/**
	 * Sets the default working set name that will be automatically selected.
	 *
	 * @author mqfdy
	 * @param quickWorkingSetName
	 *            the new quick working set name
	 * @Date 2018-09-03 09:00
	 */
	public void setQuickWorkingSetName(String quickWorkingSetName) {
		if (this.quickWorkingSetName==null && quickWorkingSetName == null) {
			return;
		}
		if (this.quickWorkingSetName==null || !this.quickWorkingSetName.equals(quickWorkingSetName)) {
			this.quickWorkingSetName = quickWorkingSetName;
			IWorkingSet existing = PlatformUI.getWorkbench().getWorkingSetManager().getWorkingSet(quickWorkingSetName);
			this.quickWorkingSetButton.setText(
					existing==null 
						?"Create workingset '"+quickWorkingSetName+"'"
						:"Add to workingset '"+quickWorkingSetName+"'"
			);
		}
	}
	
	/**
	 * Gets the quick working set enabled.
	 *
	 * @author mqfdy
	 * @return the quick working set enabled
	 * @Date 2018-09-03 09:00
	 */
	public boolean getQuickWorkingSetEnabled() {
		return quickWorkingSetButton.getSelection();
	}
	
	/**
	 * Sets the quick working set enabled.
	 *
	 * @author mqfdy
	 * @param enabled
	 *            the new quick working set enabled
	 * @Date 2018-09-03 09:00
	 */
	public void setQuickWorkingSetEnabled(boolean enabled) {
		quickWorkingSetButton.setSelection(enabled);
	}

}
