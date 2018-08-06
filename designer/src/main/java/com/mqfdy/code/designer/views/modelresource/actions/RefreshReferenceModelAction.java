package com.mqfdy.code.designer.views.modelresource.actions;

import org.dom4j.DocumentException;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.views.properties.PropertySheet;

import com.mqfdy.code.designer.editor.utils.EditorOperation;
import com.mqfdy.code.designer.editor.utils.Logger;
import com.mqfdy.code.designer.models.BusinessModelManager;
import com.mqfdy.code.designer.models.ImageKeys;
import com.mqfdy.code.designer.models.ImageManager;
import com.mqfdy.code.designer.utils.BusinessModelUtil;
import com.mqfdy.code.designer.views.modelresource.page.ObjectModelOutlinePage;
import com.mqfdy.code.designer.views.properties.MultiPageEditorPropertySheetPage;
import com.mqfdy.code.model.AbstractModelElement;
import com.mqfdy.code.model.BusinessObjectModel;
import com.mqfdy.code.model.IModelElement;
import com.mqfdy.code.model.utils.ModelUtil;
import com.mqfdy.code.resource.BomManager;


public class RefreshReferenceModelAction extends TreeAction{

	private ObjectModelOutlinePage omPage;
	
	public RefreshReferenceModelAction(String text) {
		super(text);
	}

	public RefreshReferenceModelAction(TreeViewer treeViewer) {
		super("重新加载", treeViewer);
		this.setImageDescriptor(ImageManager.getInstance().getImageDescriptor(
				ImageKeys.IMG_MODEL_OPER_REFRESH));
	}
	public RefreshReferenceModelAction(ObjectModelOutlinePage omPage) {
		this(omPage.getRmViewer().getTreeViewer());
		this.omPage = omPage;
	}

	public void run() {
		// 先找到所要刷新的节点
		if (treeViewer != null && treeViewer.getSelection() != null) {
			TreeItem item = treeViewer.getTree().getSelection()[0];
			AbstractModelElement modelElement = (AbstractModelElement) item
					.getData();
			BusinessModelManager manager = BusinessModelUtil
					.getEditorBusinessModelManager();
			BusinessObjectModel node = null;
			if(modelElement instanceof BusinessObjectModel){
				try {
					String path = modelElement.getExtendAttributies().get(IModelElement.REFMODELPATH).toString();
					String omfilePath = EditorOperation.getProjectPath(path);
					BusinessObjectModel fullModel = BomManager.xml2Model(omfilePath);
					BusinessModelUtil.assembReferenceObject(fullModel,path);
					ModelUtil.transformModelStereotype(fullModel,IModelElement.STEREOTYPE_REFERENCE);
					fullModel.getExtendAttributies().put(IModelElement.REFMODELPATH, path);
					item.setData(fullModel);
					treeViewer.refresh(fullModel);
					BusinessModelUtil.getEditorBusinessModelManager().getRepositoryModels().remove(modelElement);
					BusinessModelUtil.getEditorBusinessModelManager().addRepsitoryModel(fullModel);
//					BusinessModelEvent event = new BusinessModelEvent(BusinessModelEvent.REPOSITORY_MODEL_ADD, fullModel);
//					BusinessModelUtil.getEditorBusinessModelManager().businessObjectModelChanged(event);
					node = fullModel;
					//刷新properties视图 
					refreshProView();
				} catch (DocumentException e) {
					Logger.log(e);
				}
			}
			if(node != null)
				treeViewer.expandToLevel(node, 1); // 从选中的节点处展开
			RefreshAction action = new RefreshAction(omPage.getMrViewer().getTreeViewer());
			action.run();
		}
	}
	public void refreshProView(){
		//刷新properties视图 
		IViewPart[] views = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getViews();
		for (int i = 0; i < views.length; i++) {
			if (views[i] instanceof PropertySheet) {
				PropertySheet view = (PropertySheet) views[i];
				if ((((PropertySheet) views[i]).getCurrentPage()) instanceof MultiPageEditorPropertySheetPage) {
					MultiPageEditorPropertySheetPage page = ((MultiPageEditorPropertySheetPage) (((PropertySheet) views[i])
							.getCurrentPage()));
					page.selectionChanged(view,
							this.treeViewer.getSelection());
				}
			}
		}
	}
}
