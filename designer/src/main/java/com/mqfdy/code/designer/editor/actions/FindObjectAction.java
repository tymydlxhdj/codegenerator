package com.mqfdy.code.designer.editor.actions;

import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.actions.ActionFactory;

import com.mqfdy.code.designer.editor.BusinessModelEditorPlugin;
import com.mqfdy.code.designer.editor.actions.pages.FindObjectDiaolg;
import com.mqfdy.code.designer.models.BusinessModelEvent;
import com.mqfdy.code.designer.models.BusinessModelManager;
import com.mqfdy.code.designer.models.ImageKeys;
import com.mqfdy.code.designer.models.ImageManager;
import com.mqfdy.code.model.BusinessObjectModel;

public class FindObjectAction extends SelectionAction {

	private BusinessModelManager businessModelManager;

	public FindObjectAction(BusinessModelManager businessModelManager) {
		super(null);
		// force calculateEnabled() to be called in every context
		setLazyEnablementCalculation(true);
		this.businessModelManager = businessModelManager;
	}

	@Override
	protected void init() {
		super.init();
		// ISharedImages sharedImages = PlatformUI.getWorkbench()
		// .getSharedImages();
		setText("查询模型");
		setDescription("查询模型");
		setId(ActionFactory.FIND.getId());
		// setHoverImageDescriptor(sharedImages.getImageDescriptor(ISharedImages.IMG_TOOL_COPY));
		setImageDescriptor(ImageManager.getInstance().getImageDescriptor(
				ImageKeys.IMG_FINDOBJECT));
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
		// IPath path = new Path(businessModelManager.getPath());
		BusinessModelEvent bcAddevent = new BusinessModelEvent(
				BusinessModelEvent.MODEL_ELEMENT_SAVE, businessObjectModel);
		businessModelManager.businessObjectModelChanged(bcAddevent);
		FindObjectDiaolg dialog = new FindObjectDiaolg(shell);
		// dialog.setDefaultImage(ImageKeys.IMG_MODEL_TYPE_DIAGRAM)
		if (dialog.open() == TitleAreaDialog.OK) {

		}
	}
}
