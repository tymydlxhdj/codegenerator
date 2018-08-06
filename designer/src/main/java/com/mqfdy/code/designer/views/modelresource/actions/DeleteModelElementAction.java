package com.mqfdy.code.designer.views.modelresource.actions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.TreeItem;

import com.mqfdy.code.designer.models.ActionTexts;
import com.mqfdy.code.designer.models.BusinessModelEvent;
import com.mqfdy.code.designer.models.BusinessModelManager;
import com.mqfdy.code.designer.models.ImageKeys;
import com.mqfdy.code.designer.models.ImageManager;
import com.mqfdy.code.designer.utils.BusinessModelUtil;
import com.mqfdy.code.model.AbstractModelElement;
import com.mqfdy.code.model.Association;
import com.mqfdy.code.model.BusinessClass;
import com.mqfdy.code.model.BusinessObjectModel;
import com.mqfdy.code.model.Enumeration;
import com.mqfdy.code.model.IModelElement;
import com.mqfdy.code.model.ModelPackage;
import com.mqfdy.code.model.PKProperty;
import com.mqfdy.code.model.Property;
import com.mqfdy.code.model.ReferenceObject;
import com.mqfdy.code.model.graph.Diagram;

/**
 * 新增业务类属性动作
 * 
 * @author mqfdy
 * 
 */
public class DeleteModelElementAction extends TreeAction {

	public DeleteModelElementAction(TreeViewer treeViewer) {
		super(ActionTexts.MODEL_ELEMENT_DELETE, treeViewer);
		this.setImageDescriptor(ImageManager.getInstance().getImageDescriptor(
				ImageKeys.IMG_OBJECT_OPER_DELETE));
	}

	public DeleteModelElementAction(String text,
			ImageDescriptor imageDescriptor, TreeViewer treeViewer) {
		super(text, imageDescriptor, treeViewer);
	}

	public void run() {
		// 先找到所要删除的节点
		if (treeViewer != null && treeViewer.getSelection() != null) {
			if (MessageDialog.openConfirm(null, ActionTexts.DELETE_TITLE,
					ActionTexts.DELETE_CONFIRM)) {
				TreeItem[] items = treeViewer.getTree().getSelection();
				if(items.length == 1){
					
					TreeItem item = treeViewer.getTree().getSelection()[0];
					AbstractModelElement modelElement = (AbstractModelElement) item
							.getData();
					if(modelElement instanceof Association && 
							IModelElement.STEREOTYPE_REVERSE.equals(modelElement.getStereotype())){
						/*MessageDialog.openInformation(null,
								ActionTexts.DELETE_TITLE, "数据库反向关联关系不能删除！");*/
						MessageDialog.openInformation(null,ActionTexts.DELETE_TITLE, "数据库反向关联关系不能删除！");
						return;
					}else if(modelElement instanceof Property && 
							IModelElement.STEREOTYPE_REVERSE.equals(modelElement.getStereotype())){
						/*MessageDialog.openInformation(null,
								ActionTexts.DELETE_TITLE, "数据库反向属性不能删除！");*/
						MessageDialog.openInformation(null,ActionTexts.DELETE_TITLE, "数据库反向属性不能删除！");
						return;
					}
					deleteObject(modelElement,true);
					
				}
				else if(items.length > 1){
					List<AbstractModelElement> delList = new ArrayList<AbstractModelElement>();
					for(int i = 0; i < items.length;i++){
						AbstractModelElement modelElement = (AbstractModelElement) items[i]
								.getData();
						delList.add(modelElement);
					}
					boolean isMes = false;
					for(AbstractModelElement ab : delList){
						if(ab instanceof Association && !delList.contains(((Association) ab).getClassA())
								&& !delList.contains(((Association) ab).getClassB()) && 
								IModelElement.STEREOTYPE_REVERSE.equals(ab.getStereotype())){
							if(!isMes){
								isMes = true;
								/*MessageDialog.openInformation(null,
										ActionTexts.DELETE_TITLE, "数据库反向关联关系不能删除！");*/
								MessageDialog.openInformation(null,
										ActionTexts.DELETE_TITLE, "数据库反向关联关系不能删除！");
							}
						}else if(ab instanceof Property && !delList.contains(ab.getParent()) &&
								IModelElement.STEREOTYPE_REVERSE.equals(ab.getStereotype())){
							if(!isMes){
								isMes = true;
								/*MessageDialog.openInformation(null,
										ActionTexts.DELETE_TITLE, "数据库反向属性不能删除！");*/
								MessageDialog.openInformation(null,
										ActionTexts.DELETE_TITLE,"数据库反向属性不能删除！");
							}
						}
//						else if(delList.contains(ab.getParent()))
//							deleteObject(ab,false);
						else
							deleteObject(ab,true);
					}
				}
			}

		}
	}
	public void deleteObject(AbstractModelElement modelElement,boolean isMessage){
		if (modelElement instanceof PKProperty) {
			if(isMessage)
				/*MessageDialog.openInformation(null,
						ActionTexts.DELETE_TITLE, "主键不能删除！");*/
				MessageDialog.openInformation(null,ActionTexts.DELETE_TITLE, "主键不能删除！");
			return;
		}
		BusinessModelManager manager = BusinessModelUtil
				.getEditorBusinessModelManager();
		if (modelElement instanceof ModelPackage) {
			String info = manager.getRelationInfoOfPackage(
					(ModelPackage) modelElement, modelElement.getId());
			if (info != null && !info.equals("")) {
				/*MessageDialog.openInformation(null,
						ActionTexts.DELETE_TITLE, info);*/
				MessageDialog.openInformation(null,ActionTexts.DELETE_TITLE, info);
				return;
			}
		}
		Map<String, Object> relationInfo = manager
				.getRelationInfo(modelElement);
		String info = relationInfo.get("info") == null ? null
				: relationInfo.get("info").toString();
		if (info != null) {
			/*MessageDialog.openInformation(null,
					ActionTexts.DELETE_TITLE, info);*/
			MessageDialog.openInformation(null,ActionTexts.DELETE_TITLE, info);
			return;
		}

		if (modelElement instanceof Diagram) {
			int diagramsNum = manager.getBusinessObjectModel()
					.getDiagrams().size();
			if (diagramsNum == 1) {
				String warmInfo = "最后一张业务模型图不能删除!";
				/*MessageDialog.openInformation(null,
						ActionTexts.DELETE_TITLE, warmInfo);*/
				MessageDialog.openInformation(null,ActionTexts.DELETE_TITLE, warmInfo);
				return;
			}
		}
		if (modelElement instanceof ModelPackage) {
			int pkgsNum = manager.getBusinessObjectModel()
					.getPackages().size();
			if (pkgsNum == 1) {
				String warmInfo = "模型中最后一个包不能删除!";
				/*MessageDialog.openInformation(null,
						ActionTexts.DELETE_TITLE, warmInfo);*/
				MessageDialog.openInformation(null,ActionTexts.DELETE_TITLE, warmInfo);
				return;
			}
			List<Diagram> dias = manager.getBusinessObjectModel()
					.getDiagrams();
			int diagramsNum = dias.size();
			if (diagramsNum == 1) {
				boolean flag = false;
				Diagram dia = dias.get(0);
				AbstractModelElement parent = dia.getParent();
				while (!(parent instanceof BusinessObjectModel)) {
					if (parent == modelElement)
						flag = true;
					parent = parent.getParent();
				}
				if (flag) {
					String warmInfo = "文件内必须包含一张业务模型图，不能删除此包!";
					/*MessageDialog.openInformation(null,
							ActionTexts.DELETE_TITLE, warmInfo);*/
				MessageDialog.openInformation(null,ActionTexts.DELETE_TITLE, warmInfo);
					return;
				}
			} else {
				boolean flag = true;
				int num = 0;
				for (int i = 0; i < diagramsNum; i++) {
					Diagram dia = dias.get(i);
					AbstractModelElement parent = dia
							.getBelongPackage();
					while (!(parent instanceof BusinessObjectModel)) {
						if (parent == modelElement)
							num++;
						parent = parent.getParent();
					}
				}
				if (!(num < diagramsNum)) {
					String warmInfo = "文件内必须包含一张业务模型图，不能删除此包!";
				/*	MessageDialog.openInformation(null,
							ActionTexts.DELETE_TITLE, warmInfo);*/
					MessageDialog.openInformation(null,ActionTexts.DELETE_TITLE, warmInfo);
					return;
				}
			}
		}
		List<AbstractModelElement> allChild = new ArrayList<AbstractModelElement>();
		List<BusinessClass> businessClasses = BusinessModelUtil
				.getEditorBusinessModelManager()
				.getBusinessObjectModel().getBusinessClasses();
		for (BusinessClass ele : businessClasses) {
			allChild.add(BusinessModelUtil
					.getEditorBusinessModelManager()
					.getBusinessObjectModel()
					.removeChild(ele, businessClasses, modelElement));
		}
		List<Diagram> diagrams = BusinessModelUtil
				.getEditorBusinessModelManager()
				.getBusinessObjectModel().getDiagrams();
		for (Diagram ele : diagrams) {
			allChild.add(BusinessModelUtil
					.getEditorBusinessModelManager()
					.getBusinessObjectModel()
					.removeChild(ele, diagrams, modelElement));
		}
		List<Enumeration> enumerations = BusinessModelUtil
				.getEditorBusinessModelManager()
				.getBusinessObjectModel().getEnumerations();
		for (Enumeration ele : enumerations) {
			allChild.add(BusinessModelUtil
					.getEditorBusinessModelManager()
					.getBusinessObjectModel()
					.removeChild(ele, enumerations, modelElement));
		}
		List<ModelPackage> packages = BusinessModelUtil
				.getEditorBusinessModelManager()
				.getBusinessObjectModel().getPackages();
		for (ModelPackage ele : packages) {
			allChild.add(BusinessModelUtil
					.getEditorBusinessModelManager()
					.getBusinessObjectModel()
					.removeChild(ele, packages, modelElement));
		}
		List<Association> associations = BusinessModelUtil
				.getEditorBusinessModelManager()
				.getBusinessObjectModel().getAssociations();
		for (Association ele : associations) {
			allChild.add(BusinessModelUtil
					.getEditorBusinessModelManager()
					.getBusinessObjectModel()
					.removeChild(ele, associations, modelElement));
		}
		List<ReferenceObject> refs = BusinessModelUtil
				.getEditorBusinessModelManager()
				.getBusinessObjectModel().getReferenceObjects();
		for (ReferenceObject ele : refs) {
			allChild.add(BusinessModelUtil
					.getEditorBusinessModelManager()
					.getBusinessObjectModel()
					.removeChild(ele, refs, modelElement));
		}
		for (AbstractModelElement ab : allChild) {
			if (ab != null) {
				BusinessModelEvent event = new BusinessModelEvent(
						BusinessModelEvent.MODEL_ELEMENT_DELETE, ab);
				BusinessModelUtil.getEditorBusinessModelManager()
						.businessObjectModelChanged(event);
			}
		}

		BusinessModelEvent event = new BusinessModelEvent(
				BusinessModelEvent.MODEL_ELEMENT_DELETE, modelElement);
		BusinessModelUtil.getEditorBusinessModelManager()
				.businessObjectModelChanged(event);
	}
}
