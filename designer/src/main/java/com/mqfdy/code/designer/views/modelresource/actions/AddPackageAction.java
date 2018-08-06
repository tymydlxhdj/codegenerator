package com.mqfdy.code.designer.views.modelresource.actions;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.TreeItem;

import com.mqfdy.code.designer.dialogs.PackageEditorDialog;
import com.mqfdy.code.designer.models.ActionTexts;
import com.mqfdy.code.designer.models.BusinessModelEvent;
import com.mqfdy.code.designer.models.ImageKeys;
import com.mqfdy.code.designer.models.ImageManager;
import com.mqfdy.code.designer.utils.BusinessModelUtil;
import com.mqfdy.code.model.AbstractModelElement;
import com.mqfdy.code.model.ModelPackage;
import com.mqfdy.code.model.graph.Diagram;

/**
 * 新增包动作
 * 
 * @author mqfdy
 * 
 */
public class AddPackageAction extends TreeAction {

	/**
	 * 构造函数(采用默认名称和图标，通过树来创建，通过树来查找上级节点)
	 * 
	 * @param parent
	 */
	public AddPackageAction(TreeViewer treeViewer) {
		super(ActionTexts.PACKAGE_ADD, treeViewer);
		this.setImageDescriptor(ImageManager.getInstance().getImageDescriptor(
				ImageKeys.IMG_MODEL_TYPE_PACKAGE));
	}

	/**
	 * 构造函数(通过树来创建，通过树来查找上级节点)
	 * 
	 * @param parent
	 */
	public AddPackageAction(String text, ImageDescriptor imageDescriptor,
			TreeViewer treeViewer) {
		super(text, imageDescriptor, treeViewer);
	}

	public void run() {
		if (treeViewer != null && treeViewer.getSelection() != null) {
			TreeItem item = treeViewer.getTree().getSelection()[0];
			parent = (AbstractModelElement) item.getData();
		}

		if (parent != null) {
			PackageEditorDialog dialog = new PackageEditorDialog(null, parent);
			if (dialog.open() == Window.OK) {
				ModelPackage newPackage = (ModelPackage) dialog
						.getEditingElement();
				newPackage.setParent(parent);
				Diagram dia = new Diagram();
				dia.setBelongPackage(newPackage);
				dia.setName(newPackage.getName());
//						BusinessModelUtil.getEditorBusinessModelManager()
//						.generateNextDiagramName());
				dia.setDisplayName(newPackage.getDisplayName());
//						dia.getName());
				BusinessModelEvent event = new BusinessModelEvent(
						BusinessModelEvent.MODEL_ELEMENT_ADD, newPackage);
				BusinessModelUtil.getEditorBusinessModelManager()
						.businessObjectModelChanged(event);
				event = new BusinessModelEvent(
						BusinessModelEvent.MODEL_ELEMENT_ADD, dia);
				BusinessModelUtil.getEditorBusinessModelManager()
						.businessObjectModelChanged(event);
			}
		}
	}

}
