package com.mqfdy.code.designer.views.modelresource.actions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.TreeItem;

import com.mqfdy.code.designer.models.ActionTexts;
import com.mqfdy.code.designer.models.BusinessModelEvent;
import com.mqfdy.code.designer.models.BusinessModelManager;
import com.mqfdy.code.designer.models.ImageKeys;
import com.mqfdy.code.designer.models.ImageManager;
import com.mqfdy.code.designer.utils.BusinessModelUtil;
import com.mqfdy.code.model.AbstractModelElement;
import com.mqfdy.code.model.BusinessObjectModel;
import com.mqfdy.code.model.ReferenceObject;
// TODO: Auto-generated Javadoc

/**
 * 删除引用模型.
 *
 * @author mqfdy
 */
public class DeleteReferenceModelAction  extends TreeAction{

	/**
	 * Instantiates a new delete reference model action.
	 *
	 * @param text
	 *            the text
	 */
	public DeleteReferenceModelAction(String text) {
		super(text);
	}

	/**
	 * Instantiates a new delete reference model action.
	 *
	 * @param treeViewer
	 *            the tree viewer
	 */
	public DeleteReferenceModelAction(TreeViewer treeViewer) {
		super(ActionTexts.MODEL_ELEMENT_DELETE, treeViewer);
		this.setImageDescriptor(ImageManager.getInstance().getImageDescriptor(
				ImageKeys.IMG_OBJECT_OPER_DELETE));
	}
	
	/**
	 * 
	 */
	public void run() {
		// 先找到所要删除的节点
		
		if (treeViewer != null && treeViewer.getSelection() != null) {
			if (MessageDialog.openConfirm(null,ActionTexts.DELETE_TITLE,
					ActionTexts.DELETEREF_CONFIRM)) {
				TreeItem item = treeViewer.getTree().getSelection()[0];
				AbstractModelElement modelElement = (AbstractModelElement) item
						.getData();
				BusinessModelManager manager = BusinessModelUtil
						.getEditorBusinessModelManager();
				if (modelElement instanceof BusinessObjectModel) {
					manager.getRepositoryModels().remove(modelElement);
					treeViewer.remove(modelElement);
//					treeViewer.refresh();
					//删除引用对象
					List<ReferenceObject> delObjects = new ArrayList<ReferenceObject>();
					List<ReferenceObject> referenceObjects = manager.getBusinessObjectModel().getReferenceObjects();
					for(ReferenceObject ref : referenceObjects){
						if(ref.getReferenceModelId().equals(modelElement.getId()))
							delObjects.add(ref);
					}
					int size = delObjects.size();
					for(int i = 0;i < size;i++){
						ReferenceObject ref = delObjects.get(i);
						BusinessModelEvent event = new BusinessModelEvent(
								BusinessModelEvent.MODEL_ELEMENT_DELETE, ref);
						manager.businessObjectModelChanged(event);
					}
				}
			}
		}
	}
}
