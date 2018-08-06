package com.mqfdy.code.designer.editor.actions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;

import com.mqfdy.code.designer.editor.commands.ShareModelNodeCommand;
import com.mqfdy.code.designer.editor.part.AnnotationEditPart;
import com.mqfdy.code.designer.editor.part.BusinessClassEditPart;
import com.mqfdy.code.designer.editor.part.EnumerationEditPart;
import com.mqfdy.code.designer.models.ImageKeys;
import com.mqfdy.code.designer.models.ImageManager;
import com.mqfdy.code.designer.utils.BusinessModelUtil;
import com.mqfdy.code.model.BusinessObjectModel;
import com.mqfdy.code.shareModel.dialogs.ShareModelDialog;

public class ShareModelAction extends SelectionAction {

	public static final String STR = "shardId";
	ShareModelAction shareModelAction;
	ShareModelDialog shareModelDialog;
	List<AbstractGraphicalEditPart> list = null;

	public ShareModelAction(IWorkbenchPart part) {
		super(part);
		setLazyEnablementCalculation(true);
	}

	protected void init() {
		ISharedImages sharedImages = PlatformUI.getWorkbench()
				.getSharedImages();
		setText("上传");
		setDescription("上传");
		setId(STR);
		setHoverImageDescriptor(ImageManager.getInstance().getImageDescriptor(ImageKeys.IMG_RESOURCE_SHARE));
		setImageDescriptor(ImageManager.getInstance().getImageDescriptor(ImageKeys.IMG_RESOURCE_SHARE));
		setDisabledImageDescriptor(sharedImages
				.getImageDescriptor(ISharedImages.IMG_TOOL_CUT_DISABLED));
		setEnabled(true);
	}

	private Command createCommand(
			List<AbstractGraphicalEditPart> selectedObjects) {

		ShareModelNodeCommand cmd = new ShareModelNodeCommand(selectedObjects);
		return cmd;
	}

	@Override
	protected boolean calculateEnabled() {
		@SuppressWarnings("unchecked")
		Command cmd = createCommand(getSelectedObjects());

		return cmd.canExecute();
	}

	@SuppressWarnings({ "static-access", "unchecked" })
	public void run() {
		Command cmd = createCommand(getSelectedObjects());
		if (cmd instanceof ShareModelNodeCommand) {

			List<AbstractGraphicalEditPart> list = ((ShareModelNodeCommand) cmd)
					.getParts();
			ShareModelDialog dialog = new ShareModelDialog(new Shell());
			boolean bool=false;//是否有需要上传的模型或模型文件
			String type = "0";
			//如果没有选中具体模型,默认上传整个模型文件
			if (list.size() == 0) {
				BusinessObjectModel model = BusinessModelUtil
						.getEditorBusinessModelManager()
						.getBusinessObjectModel();
				dialog.setXml(model.toXml());
				dialog.setModelName(model.getDisplayName());
				bool=true;
			} 
			//如果选择多个，上传最后一个业务实体类型或枚举类型
			else {
				for (int i = list.size()-1; i>=0; i--) {
					if(list.get(i) instanceof AnnotationEditPart){
						continue;
					}
				   else if (list.get(i) instanceof BusinessClassEditPart) {
						BusinessClassEditPart busi = (BusinessClassEditPart) list
								.get(i);
						dialog.setXml(busi.getBusinessClass().toXml());
						dialog.setModelName(busi.getBusinessClass()
								.getDisplayName());
						type = "1";
						bool=true;
						break;
					} else if (list.get(i) instanceof EnumerationEditPart) {
						EnumerationEditPart enumEditor = (EnumerationEditPart) list
								.get(i);
						dialog.setXml(enumEditor.getEnumeration().toXml());
						dialog.setModelName(enumEditor.getEnumeration()
								.getDisplayName());
						type = "2";
						bool=true;
						break;
					}

				}
			}
			if(!bool){
				MessageDialog.openInformation(new Shell(), "提示",
						"该模型不能上传至共享模型库,请选择业务实体、枚举、模型文件执行上传操作!");
			}
			dialog.setType(type);
			if (bool&&dialog.open() == dialog.OK) {
			}

		}

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected List getSelectedObjects() {
		list = new ArrayList<AbstractGraphicalEditPart>();
		if (BusinessModelUtil.getBusinessModelDiagramEditor() != null) {
			list.addAll(BusinessModelUtil.getBusinessModelDiagramEditor()
					.getViewer().getSelectedEditParts());
		}

		return list;
	}

}
