package com.mqfdy.code.designer.views.modelresource.actions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef.ui.actions.Clipboard;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;

import com.mqfdy.code.designer.models.ActionTexts;
import com.mqfdy.code.designer.utils.BusinessModelUtil;
import com.mqfdy.code.model.AbstractModelElement;
import com.mqfdy.code.model.Association;
import com.mqfdy.code.model.BusinessClass;
import com.mqfdy.code.model.ReferenceObject;

// TODO: Auto-generated Javadoc
/**
 * 从业务模型树上复制对象.
 *
 * @author mqfdy
 */
public class CopyModelFromTreeAction extends TreeAction {
	
	/** The pro list. */
	private List<AbstractModelElement> proList = new ArrayList<AbstractModelElement>();

	/**
	 * Instantiates a new copy model from tree action.
	 *
	 * @param treeViewer
	 *            the tree viewer
	 */
	public CopyModelFromTreeAction(TreeViewer treeViewer) {
		super(ActionTexts.MODEL_ELEMENT_COPY, treeViewer);
		setId(ActionFactory.COPY.getId());
		ISharedImages sharedImages = PlatformUI.getWorkbench()
				.getSharedImages();
		setHoverImageDescriptor(sharedImages
				.getImageDescriptor(ISharedImages.IMG_TOOL_COPY));
		setImageDescriptor(sharedImages
				.getImageDescriptor(ISharedImages.IMG_TOOL_COPY));
		setDisabledImageDescriptor(sharedImages
				.getImageDescriptor(ISharedImages.IMG_TOOL_COPY_DISABLED));
		setEnabled(false);
	}

	/**
	 * 
	 */
	public void run() {
		if (!isEnabled())
			return;
		if (treeViewer != null && treeViewer.getSelection() != null) {
			proList.clear();
			TreeItem[] items = treeViewer.getTree().getSelection();
			for (int j = 0; j < items.length; j++) {
				TreeItem item = treeViewer.getTree().getSelection()[j];
				AbstractModelElement modelElement = (AbstractModelElement) item
						.getData();
				if (modelElement instanceof AbstractModelElement) {
					proList.add(modelElement);
				}
			}

			List<AbstractModelElement> sources = new ArrayList<AbstractModelElement>();// BusinessModelUtil.getBusinessModelDiagramEditor().getViewer().getSelectedEditParts();
			List<AbstractModelElement> targets = new ArrayList<AbstractModelElement>();// BusinessModelUtil.getBusinessModelDiagramEditor().getViewer().getSelectedEditParts();
			for (AbstractModelElement part : proList) {
				if (part instanceof BusinessClass
				// &&
				// !BusinessClass.STEREOTYPE_BUILDIN.equals(((BusinessClassEditPart)
				// part).getBusinessClass().getStereotype())
				) {
					for (Association as : BusinessModelUtil
							.getEditorBusinessModelManager()
							.getBusinessObjectModel().getAssociations()) {
						if (as.getClassA().getId().equals(part.getId()))
							sources.add(as);
						if (as.getClassB().getId().equals(part.getId()))
							targets.add(as);
					}
				} else if (part instanceof ReferenceObject) {
					for (Association as : BusinessModelUtil
							.getEditorBusinessModelManager()
							.getBusinessObjectModel().getAssociations()) {
						if (as.getClassA()
								.getId()
								.equals(((ReferenceObject) part)
										.getReferenceObjectId()))
							sources.add(as);
						if (as.getClassB()
								.getId()
								.equals(((ReferenceObject) part)
										.getReferenceObjectId()))
							targets.add(as);
					}
				}
			}
			for (AbstractModelElement ab : sources) {
				if (targets.contains(ab))
					proList.add(ab);
			}

			if (proList.size() > 0)
				Clipboard.getDefault().setContents(proList);
		}
	}

	/**
	 * @return
	 */
	@Override
	public boolean isEnabled() {
		TreeItem[] items = treeViewer.getTree().getSelection();
		boolean flag = true;
		for (int i = 0; i < items.length; i++) {
			TreeItem item = treeViewer.getTree().getSelection()[i];
			AbstractModelElement modelElement = (AbstractModelElement) item
					.getData();
			if (!(modelElement instanceof AbstractModelElement)) {
				flag = false;
			}
		}
		return flag;
	}
}