package com.mqfdy.code.designer.views.modelresource.actions;

import java.util.Iterator;
import java.util.Map.Entry;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreeViewer;

import com.mqfdy.code.designer.editor.BusinessModelDiagramEditor;
import com.mqfdy.code.designer.editor.part.NodeEditPart;
import com.mqfdy.code.designer.editor.utils.EditorOperation;
import com.mqfdy.code.designer.models.ActionTexts;
import com.mqfdy.code.designer.models.ImageKeys;
import com.mqfdy.code.designer.models.ImageManager;
import com.mqfdy.code.designer.utils.BusinessModelUtil;
import com.mqfdy.code.model.graph.DiagramElement;

// TODO: Auto-generated Javadoc
/**
 * 新增包动作.
 *
 * @author mqfdy
 */
public class RefreshAction extends TreeAction {

	/**
	 * 构造函数(采用默认名称和图标，通过树来创建，通过树来查找上级节点).
	 *
	 * @param treeViewer
	 *            the tree viewer
	 */
	public RefreshAction(TreeViewer treeViewer) {
		super(ActionTexts.REFRESH, treeViewer);
		this.setImageDescriptor(ImageManager.getInstance().getImageDescriptor(
				ImageKeys.IMG_MODEL_OPER_REFRESH));
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
	public RefreshAction(String text, ImageDescriptor imageDescriptor,
			TreeViewer treeViewer) {
		super(text, imageDescriptor, treeViewer);
	}

	/**
	 * 
	 */
	public void run() {
		if (treeViewer != null && treeViewer.getSelection() != null) {
			BusinessModelUtil.getOutlinePage().getMrViewer()
					.initTreeViewerData();
			BusinessModelDiagramEditor editor = BusinessModelUtil.getBusinessModelDiagramEditor();
			Object o = editor.getViewer().getContents().getModel();
			editor.getViewer().setContents(o);
			Iterator<?> it = editor.getAllEditParts();
			while (it.hasNext()) {
				Object e = ((Entry<?, ?>) it.next()).getValue();
				if (e instanceof NodeEditPart) {
					if (((NodeEditPart) e).getModel() instanceof DiagramElement) {
						((NodeEditPart) e).repaintFigure();
					}
				}
			}
			EditorOperation.refreshProperties((ISelection) BusinessModelUtil
					.getOutlinePage().getMrViewer().getTreeViewer()
					.getSelection());
		}
	}

}
