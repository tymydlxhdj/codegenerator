package com.mqfdy.code.designer.views.modelresource.actions;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.TreeItem;

import com.mqfdy.code.designer.dialogs.BusinessClassEditorDialog;
import com.mqfdy.code.designer.models.ActionTexts;
import com.mqfdy.code.designer.models.BusinessModelEvent;
import com.mqfdy.code.designer.models.ImageKeys;
import com.mqfdy.code.designer.models.ImageManager;
import com.mqfdy.code.designer.utils.BusinessModelUtil;
import com.mqfdy.code.model.AbstractModelElement;
import com.mqfdy.code.model.BusinessClass;
import com.mqfdy.code.model.ModelPackage;
import com.mqfdy.code.model.SolidifyPackage;
import com.mqfdy.code.model.graph.Diagram;

// TODO: Auto-generated Javadoc
/**
 * 新建业务类动作.
 *
 * @author mqfdy
 */
public class AddBusinessClassAction extends TreeAction {

	/**
	 * 构造函数(采用默认名称和图标，通过树来创建，通过树来查找上级节点).
	 *
	 * @param treeViewer
	 *            the tree viewer
	 */
	public AddBusinessClassAction(TreeViewer treeViewer) {
		super(ActionTexts.BUSINESSCLASS_ADD);
		this.setImageDescriptor(ImageManager.getInstance().getImageDescriptor(
				ImageKeys.IMG_MODEL_TYPE_BUSINESSCLASS));
		this.treeViewer = treeViewer;

	}

	/**
	 * 构造函数(通过树来创建，通过树来查找上级节点).
	 *
	 * @param text
	 *            the text
	 * @param imageDescriptor
	 *            the image descriptor
	 * @param treeViewer
	 *            the tree viewer
	 */
	public AddBusinessClassAction(String text, ImageDescriptor imageDescriptor,
			TreeViewer treeViewer) {
		super(text, imageDescriptor);
		this.treeViewer = treeViewer;
	}

	/**
	 * 构造函数(采用默认名称和图标，可以不通过树来创建，直接传入上级节点).
	 *
	 * @param parent
	 *            the parent
	 */
	public AddBusinessClassAction(AbstractModelElement parent) {
		super(ActionTexts.BUSINESSCLASS_ADD);
		this.setImageDescriptor(ImageManager.getInstance().getImageDescriptor(
				ImageKeys.IMG_MODEL_TYPE_BUSINESSCLASS));
		this.parent = parent;
	}

	/**
	 * 
	 */
	public void run() {
		// 先找到所要添加的业务类的上级节点
		if (treeViewer != null && treeViewer.getSelection() != null) {
			TreeItem item = treeViewer.getTree().getSelection()[0];
			parent = (AbstractModelElement) item.getData();
		}

		if (parent != null) {
			BusinessClassEditorDialog dialog = new BusinessClassEditorDialog(
					null, parent);
			int returnKey = dialog.open();
			if (returnKey == Window.OK
					|| (returnKey == Window.CANCEL && dialog.isChanged())) {
				BusinessClass newModelElement = (BusinessClass) dialog
						.getEditingElement();
				if (parent instanceof ModelPackage) {
					newModelElement.setBelongPackage((ModelPackage) parent);
				} else if (parent instanceof Diagram) {
					newModelElement.setBelongPackage((ModelPackage) (parent
							.getParent()));
				} else if (parent instanceof SolidifyPackage) {
					newModelElement.setBelongPackage((ModelPackage) (parent
							.getParent()));
				}
				BusinessModelEvent bcAddevent = new BusinessModelEvent(
						BusinessModelEvent.MODEL_ELEMENT_ADD, newModelElement);
				BusinessModelUtil.getEditorBusinessModelManager()
						.businessObjectModelChanged(bcAddevent);
			}
		}
	}
}
