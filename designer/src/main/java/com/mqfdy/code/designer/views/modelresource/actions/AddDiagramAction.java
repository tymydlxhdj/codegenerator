package com.mqfdy.code.designer.views.modelresource.actions;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.TreeItem;

import com.mqfdy.code.designer.dialogs.DiagramEditorDialog;
import com.mqfdy.code.designer.models.ActionTexts;
import com.mqfdy.code.designer.models.BusinessModelEvent;
import com.mqfdy.code.designer.models.ImageKeys;
import com.mqfdy.code.designer.models.ImageManager;
import com.mqfdy.code.designer.utils.BusinessModelUtil;
import com.mqfdy.code.model.AbstractModelElement;
import com.mqfdy.code.model.ModelPackage;
import com.mqfdy.code.model.graph.Diagram;

// TODO: Auto-generated Javadoc
/**
 * 新增图动作.
 *
 * @author mqfdy
 */
public class AddDiagramAction extends TreeAction {

	/**
	 * 构造函数(采用默认名称和图标，通过树来创建，通过树来查找上级节点).
	 *
	 * @param treeViewer
	 *            the tree viewer
	 */
	public AddDiagramAction(TreeViewer treeViewer) {
		super(ActionTexts.DIAGRAM_ADD, treeViewer);
		this.setImageDescriptor(ImageManager.getInstance().getImageDescriptor(
				ImageKeys.IMG_MODEL_TYPE_DIAGRAM));

	}

	/**
	 * 构造函数(采用自定义的名称和图标，通过树来创建，通过树来查找上级节点).
	 *
	 * @param text
	 *            the text
	 * @param imageDescriptor
	 *            the image descriptor
	 * @param treeViewer
	 *            the tree viewer
	 */
	public AddDiagramAction(String text, ImageDescriptor imageDescriptor,
			TreeViewer treeViewer) {
		super(text, imageDescriptor, treeViewer);
	}

	/**
	 * 
	 */
	public void run() {
		if (treeViewer != null && treeViewer.getSelection() != null) {
			TreeItem item = treeViewer.getTree().getSelection()[0];
			parent = (AbstractModelElement) item.getData();
		}

		if (parent != null) {
			DiagramEditorDialog dialog = new DiagramEditorDialog(null, parent);
			if (dialog.open() == Window.OK) {
				Diagram diagram = (Diagram) dialog.getEditingElement();
				BusinessModelEvent event = new BusinessModelEvent(
						BusinessModelEvent.MODEL_ELEMENT_ADD, diagram);
				diagram.setBelongPackage((ModelPackage) parent);
				BusinessModelUtil.getEditorBusinessModelManager()
						.businessObjectModelChanged(event);
			}
		}
	}

}
