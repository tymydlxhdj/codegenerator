package com.mqfdy.code.designer.views.modelresource.actions;

import org.dom4j.DocumentException;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.views.properties.PropertySheet;

import com.mqfdy.code.designer.dialogs.BusinessClassEditorDialog;
import com.mqfdy.code.designer.dialogs.BusinessClassInheritanceRelationEditDialog;
import com.mqfdy.code.designer.dialogs.BusinessClassRelationEditDialog;
import com.mqfdy.code.designer.dialogs.DTOEditDialog;
import com.mqfdy.code.designer.dialogs.DTOPropertyEditDialog;
import com.mqfdy.code.designer.dialogs.EnumEditDialog;
import com.mqfdy.code.designer.dialogs.OperationEditorDialog;
import com.mqfdy.code.designer.dialogs.PropertyEditorDialog;
import com.mqfdy.code.designer.editor.BusinessModelDiagramEditor;
import com.mqfdy.code.designer.editor.utils.EditorOperation;
import com.mqfdy.code.designer.editor.utils.Logger;
import com.mqfdy.code.designer.models.BusinessModelEvent;
import com.mqfdy.code.designer.utils.BusinessModelUtil;
import com.mqfdy.code.designer.views.properties.MultiPageEditorPropertySheetPage;
import com.mqfdy.code.model.AbstractModelElement;
import com.mqfdy.code.model.Association;
import com.mqfdy.code.model.BusinessClass;
import com.mqfdy.code.model.BusinessObjectModel;
import com.mqfdy.code.model.BusinessOperation;
import com.mqfdy.code.model.ComplexDataType;
import com.mqfdy.code.model.DTOProperty;
import com.mqfdy.code.model.DataTransferObject;
import com.mqfdy.code.model.EnumElement;
import com.mqfdy.code.model.Enumeration;
import com.mqfdy.code.model.IModelElement;
import com.mqfdy.code.model.Inheritance;
import com.mqfdy.code.model.ModelPackage;
import com.mqfdy.code.model.Property;
import com.mqfdy.code.model.SolidifyPackage;
import com.mqfdy.code.model.graph.Diagram;
import com.mqfdy.code.model.utils.ModelUtil;
import com.mqfdy.code.resource.BomManager;

// TODO: Auto-generated Javadoc
/**
 * The Class DoubleClickTreeAction.
 *
 * @author mqfdy
 */
public class DoubleClickTreeAction extends TreeAction {

	/**
	 * Instantiates a new double click tree action.
	 *
	 * @param treeViewer
	 *            the tree viewer
	 */
	public DoubleClickTreeAction(TreeViewer treeViewer) {
		super("double Click", treeViewer);
	}

	/**
	 * 
	 */
	public void run() {
		ISelection selection = this.treeViewer.getSelection();
		Object node = ((IStructuredSelection) selection).getFirstElement();
		AbstractModelElement modelElement = (AbstractModelElement) node;
		if (modelElement instanceof SolidifyPackage
				|| modelElement instanceof ModelPackage
				|| modelElement instanceof BusinessObjectModel
				|| IModelElement.STEREOTYPE_BUILDIN.equals(modelElement
						.getStereotype())
				|| IModelElement.STEREOTYPE_REFERENCE.equals(modelElement
						.getStereotype())) {
			TreeItem[] items = treeViewer.getTree().getSelection();
			if (null == items || items.length == 0) {
				return;
			}
			if (items[0].getExpanded()) {
				items[0].setExpanded(false);
			} else {
				if(modelElement instanceof BusinessObjectModel && modelElement.getChildren().size() == 0){
					try {
						String path = modelElement.getExtendAttributies().get(IModelElement.REFMODELPATH).toString();
						String oPath = EditorOperation.getProjectPath(path);
						BusinessObjectModel fullModel = BomManager.xml2Model(oPath);
						BusinessModelUtil.assembReferenceObject(fullModel,oPath);
						ModelUtil.transformModelStereotype(fullModel,IModelElement.STEREOTYPE_REFERENCE);
						fullModel.getExtendAttributies().put(IModelElement.REFMODELPATH, path);
						items[0].setData(fullModel);
						BusinessModelUtil.getEditorBusinessModelManager().getRepositoryModels().remove(modelElement);
						BusinessModelUtil.getEditorBusinessModelManager().addRepsitoryModel(fullModel);
//						BusinessModelEvent event = new BusinessModelEvent(BusinessModelEvent.REPOSITORY_MODEL_ADD, fullModel);
//						BusinessModelUtil.getEditorBusinessModelManager().businessObjectModelChanged(event);
						node = fullModel;
						//刷新properties视图 
						refreshProView();
					} catch (DocumentException e) {
						Logger.log(e);
					}
				}
				treeViewer.expandToLevel(node, 1); // 从选中的节点处展开
			}
		} else {
			boolean flag = false;
			if (modelElement instanceof BusinessClass) {
				BusinessClassEditorDialog dialog = new BusinessClassEditorDialog(
						null, modelElement, modelElement.getParent());
				int returnKey = dialog.open();
				if (returnKey == Window.OK
						|| (returnKey == Window.CANCEL && dialog.isChanged())) {
					flag = true;
					BusinessModelEvent event = new BusinessModelEvent(
							BusinessModelEvent.MODEL_ELEMENT_UPDATE,
							dialog.getEditingElement());
					BusinessModelUtil.getEditorBusinessModelManager()
							.businessObjectModelChanged(event);
				}
			} else if (modelElement instanceof Association) {
				BusinessClassRelationEditDialog dialog = new BusinessClassRelationEditDialog(
						treeViewer.getControl().getShell(),
						(Association) modelElement, null,
						BusinessModelEvent.MODEL_ELEMENT_UPDATE, "");
				int returnKey = dialog.open();
				if (returnKey == Window.OK
						|| (returnKey == Window.CANCEL && dialog.isChanged())) {
					flag = true;
				}
			} else if (modelElement instanceof Inheritance) {
				BusinessClassInheritanceRelationEditDialog dialog = new BusinessClassInheritanceRelationEditDialog(
						treeViewer.getControl().getShell(),
						(Inheritance) modelElement, null,
						BusinessModelEvent.MODEL_ELEMENT_UPDATE);
				int returnKey = dialog.open();
				if (returnKey == Window.OK
						|| (returnKey == Window.CANCEL && dialog.isChanged())) {
					flag = true;
				}
			} else if (modelElement instanceof DataTransferObject) {
				DTOEditDialog dialog = new DTOEditDialog(treeViewer
						.getControl().getShell(), null,
						(DataTransferObject) modelElement);
				int returnKey = dialog.open();
				if (returnKey == Window.OK
						|| (returnKey == Window.CANCEL && dialog.isChanged())) {
					flag = true;
				}
			} else if (modelElement instanceof Enumeration) {
				EnumEditDialog dialog = new EnumEditDialog(treeViewer
						.getControl().getShell(), null,
						(Enumeration) modelElement);
				int returnKey = dialog.open();
				if (returnKey == Window.OK
						|| (returnKey == Window.CANCEL && dialog.isChanged())) {
					flag = true;
				}
			} else if (modelElement instanceof EnumElement) {
				// EnumElementEditDialog dialog = new
				// EnumElementEditDialog(treeViewer.getControl().getShell(),null,(EnumElement)modelElement,null);
				// if (dialog.open() == Window.OK ){
				// BusinessModelEvent event = new
				// BusinessModelEvent(BusinessModelEvent.MODEL_ELEMENT_UPDATE,(EnumElement)modelElement);
				// BusinessModelUtil.getEditorBusinessModelManager().businessObjectModelChanged(event);
				// }

			} else if (modelElement instanceof ComplexDataType) {

			} else if (modelElement instanceof Diagram) {
				BusinessModelDiagramEditor bmdEditor = BusinessModelUtil
						.getBusinessModelDiagramEditor();
//				IEditorPart editor;
				if (bmdEditor == null) {
//					IEditorInput input = new AppEditorInput(new Path(
//							BusinessModelUtil.getEditorBusinessModelManager()
//									.getPath()));
//					IWorkbenchWindow[] benchs = PlatformUI.getWorkbench()
//							.getWorkbenchWindows();// ActiveWorkbenchWindow().getPages();
//					for (int j = 0; j < benchs.length; j++) {
//						IWorkbenchPage[] pages = benchs[j].getPages();
//						for (int i = 0; i < pages.length; i++) {
//							editor = pages[i].findEditor(input);
//						}
//
//					}
					return;
				}
				if (bmdEditor.getViewer().getContents() == null
						|| !bmdEditor.getViewer().getContents().getModel()
								.equals(modelElement)) {
					bmdEditor.getCommandStacks().flush();
					for(Diagram dia : bmdEditor.getBusinessModelManager().getBusinessObjectModel().getDiagrams()){
						dia.setDefault(false);
					}
					((Diagram) bmdEditor.getViewer().getContents().getModel()).setDefault(false);
					bmdEditor.getViewer().setContents(modelElement);
					bmdEditor.setDirty(true);
					((Diagram) modelElement).setDefault(true);
					ZoomManager zoomMgr = (ZoomManager) bmdEditor.getViewer()
							.getProperty(ZoomManager.class.toString());
					if (zoomMgr != null
							&& ((Diagram) modelElement).getDefaultStyle() != null) {
						zoomMgr.setZoom(((double) ((Diagram) modelElement)
								.getDefaultStyle().getZoomScale()) / 100);
					}
				}
			} else if (modelElement instanceof Property) {

				if (modelElement instanceof DTOProperty) {
					DTOPropertyEditDialog dialog = new DTOPropertyEditDialog(
							null, (DTOProperty) modelElement, null);
					int returnKey = dialog.open();
					if (returnKey == Window.OK
							|| (returnKey == Window.CANCEL && dialog
									.isChanged())) {
						flag = true;
					}
					return;
				}

				BusinessClass bc = (BusinessClass) (modelElement.getParent());
				PropertyEditorDialog dialog = new PropertyEditorDialog(null,
						modelElement, bc);
				int returnKey = dialog.open();
				if (returnKey == Window.OK
						|| (returnKey == Window.CANCEL && dialog.isChanged())) {
					bc.getProperties().remove(modelElement);
					bc.addProperty((Property) dialog.getEditingElement());
					BusinessModelEvent event = new BusinessModelEvent(
							BusinessModelEvent.MODEL_ELEMENT_UPDATE, bc);
					BusinessModelUtil.getEditorBusinessModelManager()
							.businessObjectModelChanged(event);
					flag = true;
				}
			} else if (modelElement instanceof BusinessOperation) {
				BusinessOperation operation = (BusinessOperation) modelElement;
				if (BusinessOperation.OPERATION_TYPE_CUSTOM.equals(operation
						.getOperationType())) {
					BusinessClass bc = operation.getBelongBusinessClass();
					OperationEditorDialog dialog = new OperationEditorDialog(
							null, modelElement, bc);
					int returnKey = dialog.open();
					if (returnKey == Window.OK
							|| (returnKey == Window.CANCEL && dialog
									.isChanged())) {
						flag = true;
						// 编辑后保存关闭
						BusinessModelEvent event = new BusinessModelEvent(
								BusinessModelEvent.MODEL_ELEMENT_UPDATE,
								modelElement);
						BusinessModelUtil.getEditorBusinessModelManager()
								.businessObjectModelChanged(event);
					}
				}
			}
			// 修改后 刷新属性视图
			if (flag) {
				refreshProView();
			}

		}
	}
	
	/**
	 * Refresh pro view.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
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
