package com.mqfdy.code.designer.editor.actions;

import java.util.List;

import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;

import com.mqfdy.code.designer.console.ConsoleFactory;
import com.mqfdy.code.designer.editor.BusinessModelEditorPlugin;
import com.mqfdy.code.designer.editor.actions.pages.ModelValidatorDiaolg;
import com.mqfdy.code.designer.editor.utils.Logger;
import com.mqfdy.code.designer.models.BusinessModelEvent;
import com.mqfdy.code.designer.models.BusinessModelManager;
import com.mqfdy.code.designer.models.ImageKeys;
import com.mqfdy.code.designer.models.ImageManager;
import com.mqfdy.code.designer.utils.BusinessModelUtil;
import com.mqfdy.code.designer.views.valiresult.ValiView;
import com.mqfdy.code.model.AbstractModelElement;
import com.mqfdy.code.model.BusinessObjectModel;
import com.mqfdy.code.resource.ValidatorManager;
import com.mqfdy.code.resource.validator.ValiResult;
import com.mqfdy.code.resource.validator.Validator;
import com.mqfdy.code.resource.validator.ValidatorContext;

/**
 * 模型校验
 * 
 * @author mqfdy
 * 
 */
public class ModelValidateAction extends SelectionAction implements
		ValidatorContext {
	final static String ERROR = "error";
	final static String INFO = "info";
	private BusinessModelManager businessModelManager;

	public ModelValidateAction(BusinessModelManager businessModelManager) {
		super(null);
		setLazyEnablementCalculation(true);
		this.businessModelManager = businessModelManager;
	}

	@Override
	protected void init() {
		super.init();
		// ISharedImages sharedImages = PlatformUI.getWorkbench()
		// .getSharedImages();
		setText("模型校验");
		setDescription("模型校验");
		setId(ActionFactory.COPY.getId());
		// setHoverImageDescriptor(sharedImages.getImageDescriptor(ISharedImages.IMG_TOOL_COPY));
		setImageDescriptor(ImageManager.getInstance().getImageDescriptor(
				ImageKeys.IMG_VALIDATEACTION));
		// setDisabledImageDescriptor(sharedImages.getImageDescriptor(ISharedImages.IMG_TOOL_COPY_DISABLED));
		setEnabled(false);
	}

	@Override
	protected boolean calculateEnabled() {
		return true;
	}

	@Override
	public void run() {
		Shell shell = BusinessModelEditorPlugin.getActiveWorkbenchWindow()
				.getShell();
		BusinessObjectModel businessObjectModel = businessModelManager
				.getBusinessObjectModel();
		BusinessModelEvent bcAddevent = new BusinessModelEvent(
				BusinessModelEvent.MODEL_ELEMENT_SAVE, businessObjectModel);
		businessModelManager.businessObjectModelChanged(bcAddevent);
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
			BusinessModelUtil.getView(ValiView.class).setFilePath(businessModelManager.getPath());
			BusinessModelUtil.getBusinessModelDiagramEditor().setValiResult(resultList);
			BusinessModelUtil.getView(ValiView.class).setValiData(resultList);
		}
	}

	public void printToConsole(String mesg, String type) {
		if (type.equals(INFO))
			ConsoleFactory.printToConsole(mesg, true, false);// 黑色
		if (type.equals(ERROR))
			ConsoleFactory.printToConsole(mesg, true, true);// 黑色
	}
}