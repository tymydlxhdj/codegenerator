package com.mqfdy.code.designer.editor.actions;

import org.eclipse.core.resources.IProject;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.actions.ActionFactory;

import com.mqfdy.code.designer.editor.BusinessModelEditorPlugin;
import com.mqfdy.code.designer.models.BusinessModelEvent;
import com.mqfdy.code.designer.models.BusinessModelManager;
import com.mqfdy.code.designer.models.ImageKeys;
import com.mqfdy.code.designer.models.ImageManager;
import com.mqfdy.code.model.BusinessObjectModel;
import com.mqfdy.code.wizard.wizard.IMicroGeneratorConfigWizard;
import com.mqfdy.code.wizard.wizard.MicroGeneratorConfigWizard2;

// TODO: Auto-generated Javadoc
/**
 * 代码生成.
 *
 * @author mqfdy
 */
public class GenerateAction extends SelectionAction {
	
	/** The business model manager. */
	private BusinessModelManager businessModelManager;
	
	/** The test project. */
	public static IProject testProject;
    
	/**
	 * Instantiates a new generate action.
	 *
	 * @param businessModelManager
	 *            the business model manager
	 */
	public GenerateAction(BusinessModelManager businessModelManager) {
		super(null);
		// force calculateEnabled() to be called in every context
		setLazyEnablementCalculation(true);
		this.businessModelManager = businessModelManager;
	}

	/**
	 * 
	 */
	@Override
	protected void init() {
		super.init();
		setText("代码生成");
		setDescription("代码生成");
		setId(ActionFactory.COPY.getId());
		setImageDescriptor(ImageManager.getInstance().getImageDescriptor(
				ImageKeys.IMG_GENERATEACTION));
		setEnabled(false);
	}

	/**
	 * @return
	 */
	@Override
	protected boolean calculateEnabled() {
		return true;
	}

	/**
	 * 
	 */
	@Override
	public void run() {
		Shell shell = BusinessModelEditorPlugin.getActiveWorkbenchWindow()
				.getShell();
		BusinessObjectModel businessObjectModel = businessModelManager
				.getBusinessObjectModel();
		BusinessModelEvent bcAddevent = new BusinessModelEvent(
				BusinessModelEvent.MODEL_ELEMENT_SAVE, businessObjectModel);
		businessModelManager.businessObjectModelChanged(bcAddevent);
		IMicroGeneratorConfigWizard microGeneratorWizard = new MicroGeneratorConfigWizard2();
		microGeneratorWizard.initialize(businessModelManager.getPath(), null);
		WizardDialog wd = new WizardDialog(shell, microGeneratorWizard);
		wd.open();
	}
	
}
