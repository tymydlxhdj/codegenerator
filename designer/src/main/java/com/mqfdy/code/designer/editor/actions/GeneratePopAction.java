package com.mqfdy.code.designer.editor.actions;

import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.ResourceUtil;

import com.mqfdy.code.designer.console.ConsoleFactory;
import com.mqfdy.code.designer.editor.BusinessModelEditor;
import com.mqfdy.code.designer.editor.BusinessModelEditorPlugin;
import com.mqfdy.code.designer.editor.actions.pages.GeneratorDiaolg;
import com.mqfdy.code.designer.editor.utils.Logger;
import com.mqfdy.code.designer.models.BusinessModelEvent;
import com.mqfdy.code.designer.models.BusinessModelManager;
import com.mqfdy.code.designer.utils.BusinessModelUtil;
import com.mqfdy.code.designer.views.valiresult.ValiView;
import com.mqfdy.code.model.AbstractModelElement;
import com.mqfdy.code.model.BusinessObjectModel;
import com.mqfdy.code.model.ModelPackage;
import com.mqfdy.code.model.SolidifyPackage;
import com.mqfdy.code.resource.ValidatorManager;
import com.mqfdy.code.resource.validator.ValiResult;
import com.mqfdy.code.resource.validator.ValidatorContext;
import com.mqfdy.code.wizard.wizard.IMicroGeneratorConfigWizard;
import com.mqfdy.code.wizard.wizard.MicroGeneratorConfigWizard2;

/**
 * 代码生成
 * 
 * @author mqfdy
 * 
 */
public class GeneratePopAction implements IObjectActionDelegate {
	private StructuredSelection selection = null;

	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
	}

	public void run(IAction action) {

		// 用户没做选择，则直接返回
		if (this.selection == null) {
			return;
		}

		Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
				.getShell();

		boolean flag = true;
		IProject project = null;
		IFile file = null;
		if (selection != null && !selection.isEmpty()
				&& selection instanceof IStructuredSelection) {
			Object object = ((IStructuredSelection) selection)
					.getFirstElement();
			if (object instanceof IFile) {
				project = ((IFile) object).getProject();
				file = (IFile) object;
			}
		}

		IWorkbenchPage workbenchPage = BusinessModelEditorPlugin
				.getActiveWorkbenchWindow().getActivePage();

		IEditorPart editor = ResourceUtil.findEditor(workbenchPage, file);

		// 如果文件是在BusinessModelEditor中进行编辑的，就直接保存
		if (editor instanceof BusinessModelEditor) {
			BusinessObjectModel businessObjectModel = ((BusinessModelEditor) editor)
					.getBuEditor().getBusinessModelManager()
					.getBusinessObjectModel();
			BusinessModelEvent bcAddevent = new BusinessModelEvent(
					BusinessModelEvent.MODEL_ELEMENT_SAVE, businessObjectModel);
			((BusinessModelEditor) editor).getBuEditor()
					.getBusinessModelManager()
					.businessObjectModelChanged(bcAddevent);
		}
		if (file == null)
			return;
		BusinessModelManager businessModelManager  = new BusinessModelManager();
		BusinessObjectModel businessObjectModel = businessModelManager
				.getBusinessObjectModel(file.getLocation().toOSString());

		IMicroGeneratorConfigWizard microGeneratorWizard = new MicroGeneratorConfigWizard2();
		microGeneratorWizard.initialize(businessModelManager.getPath(), null);
		WizardDialog wd = new WizardDialog(shell, microGeneratorWizard);
		wd.open();


	}

	public void selectionChanged(IAction action, ISelection selection) {
		if (selection instanceof StructuredSelection) {
			this.selection = (StructuredSelection) selection;
		}
	}
}