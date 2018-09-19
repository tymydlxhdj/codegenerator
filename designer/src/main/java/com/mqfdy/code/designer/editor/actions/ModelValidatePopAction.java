package com.mqfdy.code.designer.editor.actions;

import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
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
import com.mqfdy.code.designer.editor.actions.pages.ModelValidatorDiaolg;
import com.mqfdy.code.designer.editor.utils.Logger;
import com.mqfdy.code.designer.models.BusinessModelEvent;
import com.mqfdy.code.designer.models.BusinessModelManager;
import com.mqfdy.code.designer.utils.BusinessModelUtil;
import com.mqfdy.code.designer.views.valiresult.ValiView;
import com.mqfdy.code.model.AbstractModelElement;
import com.mqfdy.code.model.BusinessObjectModel;
import com.mqfdy.code.resource.ValidatorManager;
import com.mqfdy.code.resource.validator.ValiResult;
import com.mqfdy.code.resource.validator.Validator;
import com.mqfdy.code.resource.validator.ValidatorContext;

// TODO: Auto-generated Javadoc
/**
 * 模型校验.
 *
 * @author mqfdy
 */
public class ModelValidatePopAction implements IObjectActionDelegate,
		ValidatorContext {
	
	/** The Constant ERROR. */
	final static String ERROR = "error";
	
	/** The Constant INFO. */
	final static String INFO = "info";
	
	/** The selection. */
	private StructuredSelection selection = null;

	/**
	 * Sets the active part.
	 *
	 * @author mqfdy
	 * @param action
	 *            the action
	 * @param targetPart
	 *            the target part
	 * @Date 2018-09-03 09:00
	 */
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
	}

	/**
	 * Run.
	 *
	 * @author mqfdy
	 * @param action
	 *            the action
	 * @Date 2018-09-03 09:00
	 */
	public void run(IAction action) {
		// 用户没做选择，则直接返回
		if (this.selection == null) {
			return;
		}

		Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
				.getShell();

		// IProject project = null;
		IFile file = null;
		if (selection != null && !selection.isEmpty()
				&& selection instanceof IStructuredSelection) {
			Object object = ((IStructuredSelection) selection)
					.getFirstElement();
			if (object instanceof IFile) {
				// project = ((IFile) object).getProject();
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
		BusinessObjectModel businessObjectModel = new BusinessModelManager()
				.getBusinessObjectModel(file.getLocation().toOSString());
		// dialog.setDefaultImage(ImageKeys.IMG_MODEL_TYPE_DIAGRAM)
		ModelValidatorDiaolg dialog = new ModelValidatorDiaolg(shell,
				businessObjectModel);
		// dialog.setDefaultImage(ImageKeys.IMG_MODEL_TYPE_DIAGRAM)

		if (dialog.open() == TitleAreaDialog.OK) {
			List<AbstractModelElement> list = dialog.getObjectSelectPage()
					.getSelectedModels();
			List<Validator> vaList = dialog.getModelValidatorSelectPage()
					.getSelectedValisators();
			List<ValiResult> resultList = (new ValidatorManager()).checkModel(
					businessObjectModel, list, vaList, this);

			IWorkbenchWindow dw = PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow();
			IWorkbenchPage page = dw.getActivePage();
			try {
				if (page != null) {
					// 打开视图
					page.showView(ValiView.VIEWID);
				}
			} catch (PartInitException e) {
				Logger.log(e);
			}
			BusinessModelUtil.getView(ValiView.class).setFilePath(file.getLocation().toOSString());
			BusinessModelUtil.getView(ValiView.class).setValiData(resultList);
		}
	}

	/**
	 * Selection changed.
	 *
	 * @author mqfdy
	 * @param action
	 *            the action
	 * @param selection
	 *            the selection
	 * @Date 2018-09-03 09:00
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		if (selection instanceof StructuredSelection) {
			this.selection = (StructuredSelection) selection;
		}
	}

	/**
	 * Prints the to console.
	 *
	 * @author mqfdy
	 * @param mesg
	 *            the mesg
	 * @param type
	 *            the type
	 * @Date 2018-09-03 09:00
	 */
	public void printToConsole(String mesg, String type) {
		if (type.equals(INFO))
			ConsoleFactory.printToConsole(mesg, true, false);// 黑色
		if (type.equals(ERROR))
			ConsoleFactory.printToConsole(mesg, true, true);// 黑色
	}
}