package com.mqfdy.code.designer.views.modelresource.actions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gef.ui.actions.Clipboard;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;

import com.mqfdy.code.designer.editor.part.BusinessClassEditPart;
import com.mqfdy.code.designer.editor.part.ReferenceObjectEditPart;
import com.mqfdy.code.designer.editor.part.RelationEditPart;
import com.mqfdy.code.designer.models.ActionTexts;
import com.mqfdy.code.designer.models.BusinessModelEvent;
import com.mqfdy.code.designer.models.BusinessModelManager;
import com.mqfdy.code.designer.utils.BusinessModelUtil;
import com.mqfdy.code.model.AbstractModelElement;
import com.mqfdy.code.model.Association;
import com.mqfdy.code.model.BusinessClass;
import com.mqfdy.code.model.BusinessOperation;
import com.mqfdy.code.model.Enumeration;
import com.mqfdy.code.model.ModelPackage;
import com.mqfdy.code.model.PKProperty;
import com.mqfdy.code.model.PersistenceProperty;
import com.mqfdy.code.model.Property;
import com.mqfdy.code.model.ReferenceObject;

// TODO: Auto-generated Javadoc
/**
 * 在业务模型树上粘贴节点.
 *
 * @author mqfdy
 */
public class PasteModelFromTreeAction extends TreeAction {
	
	/** The list. */
	private List<Object> list = new ArrayList<Object>();
	
	/** The tree viewer. */
	private TreeViewer treeViewer;
	
	/** The model element. */
	private AbstractModelElement modelElement;
	
	/** The business model manager. */
	private BusinessModelManager businessModelManager;
	
	/** The bu map. */
	// 新旧模型对象的Map
	private Map<BusinessClass, BusinessClass> buMap = new HashMap<BusinessClass, BusinessClass>();

	/**
	 * Instantiates a new paste model from tree action.
	 *
	 * @param treeViewer
	 *            the tree viewer
	 * @param businessModelManager
	 *            the business model manager
	 */
	public PasteModelFromTreeAction(TreeViewer treeViewer,
			BusinessModelManager businessModelManager) {
		super(ActionTexts.MODEL_ELEMENT_PASTE, treeViewer);
		this.treeViewer = treeViewer;
		this.businessModelManager = businessModelManager;
		// list.addAll(treeViewer.getSelection());
		setId(ActionFactory.PASTE.getId());
		ISharedImages sharedImages = PlatformUI.getWorkbench()
				.getSharedImages();
		setHoverImageDescriptor(sharedImages
				.getImageDescriptor(ISharedImages.IMG_TOOL_PASTE));
		setImageDescriptor(sharedImages
				.getImageDescriptor(ISharedImages.IMG_TOOL_PASTE));
		setDisabledImageDescriptor(sharedImages
				.getImageDescriptor(ISharedImages.IMG_TOOL_PASTE_DISABLED));
		setEnabled(false);
	}

	/**
	 * Creates the pasted model list.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	private void createPastedModelList() {
		list.clear();
		if (Clipboard.getDefault().getContents() == null)
			return;
		list.addAll((List<Object>) Clipboard.getDefault().getContents());
	}

	/**
	 * @return
	 */
	@Override
	public boolean isEnabled() {
		TreeItem[] items = treeViewer.getTree().getSelection();
		if (items.length > 0) {
			TreeItem item = items[0];
			modelElement = (AbstractModelElement) item.getData();
		}
		if (modelElement == null) {
			return false;
		}
		createPastedModelList();
		if (list.size() > 0 && list.get(0) instanceof Property
				&& modelElement instanceof BusinessClass 
				&& BusinessModelUtil.isCustomObjectModel(modelElement)){
			
			for (Object pro : list) {
				if(!BusinessModelUtil.isCustomObjectModel(modelElement)
						&& !(pro instanceof PersistenceProperty)){
					return true;
				}
				if(BusinessModelUtil.isCustomObjectModel(modelElement)){
					return true;
				}
			}
			
			return false;
		}
		else if (list.size() > 0
				&& (list.get(0) instanceof BusinessClass 
						|| list.get(0) instanceof Enumeration
						|| list.get(0) instanceof ReferenceObject)
				&& modelElement instanceof ModelPackage)
			return true;
		else if (list.size() > 0 && list.get(0) instanceof BusinessOperation
				&& modelElement instanceof BusinessClass)
			return true;
		else if (list.size() > 0
				&& list.get(0) instanceof AbstractGraphicalEditPart
				&& modelElement instanceof ModelPackage)
			return true;
		return false;
	}

	/**
	 * 
	 */
	@Override
	public void run() {
		if (isEnabled()) {
			if (list.size() > 0 && list.get(0) instanceof Property)
				pasteProperties();
			else if (list.size() > 0
					&& (list.get(0) instanceof BusinessClass 
							|| list.get(0) instanceof Enumeration
							|| list.get(0) instanceof ReferenceObject))
				pasteBusinessClasses();
			else if (list.size() > 0
					&& list.get(0) instanceof AbstractGraphicalEditPart) {
				List<Object> copyList = new ArrayList<Object>();
				for (Object part : list) {
					if (part instanceof BusinessClassEditPart)
						copyList.add(((BusinessClassEditPart) part)
								.getBusinessClass());
					else if (part instanceof ReferenceObjectEditPart)
						copyList.add(((ReferenceObjectEditPart) part)
								.getReferenceObject());
					else if (part instanceof RelationEditPart)
						copyList.add(((RelationEditPart) part).getAssociation());
				}
				list = copyList;
				pasteBusinessClasses();
			} else if (list.size() > 0
					&& list.get(0) instanceof BusinessOperation)
				pasteOperations();
		}
	}

	/**
	 * Paste properties.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	public void pasteProperties() {
		// modelElement = (BusinessClass)modelElement;
		for (Object pro : list) {
			if(!BusinessModelUtil.isCustomObjectModel(modelElement)
					&& pro instanceof PersistenceProperty){
				continue;
			}
			Property newPro = ((Property) pro).cloneChangeId();
			newPro.setParent((BusinessClass) modelElement);
			String name = businessModelManager.generateNextPropertyName(
					newPro.getName(), (BusinessClass) modelElement);
			String disName = BusinessModelUtil.getEditorBusinessModelManager()
					.generateNextPropertyDisName(newPro.getDisplayName(),(BusinessClass) modelElement);
			
			newPro.setDisplayName(disName);
//			if (!name.equals(newPro.getName())) {
//			newPro.setDisplayName(name);
//			}
			newPro.setName(name);
			if (newPro instanceof PersistenceProperty)
				((PersistenceProperty) newPro)
						.setdBColumnName(businessModelManager
								.generateNextDbColumnName(
										((PersistenceProperty) newPro)
												.getdBColumnName(),
										(BusinessClass) modelElement));
			if (newPro instanceof PKProperty
					&& ((BusinessClass) modelElement).hasPkProperty()) {
				((PKProperty) newPro).setPrimaryKey(false);
			}
			newPro.setId(UUID.randomUUID().toString().replaceAll("-", ""));

			((BusinessClass) modelElement).addProperty(newPro);
		}
		BusinessModelEvent bcAddevent = new BusinessModelEvent(
				BusinessModelEvent.MODEL_ELEMENT_UPDATE, modelElement);
		businessModelManager.businessObjectModelChanged(bcAddevent);

	}

	/**
	 * Paste operations.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	public void pasteOperations() {
		// modelElement = (BusinessClass)modelElement;
		for (Object op : list) {
			BusinessOperation newOp = ((BusinessOperation) op).cloneChangeId();
			newOp.setBelongBusinessClass((BusinessClass) modelElement);
			String name = businessModelManager.generateNextOperationName(
					newOp.getName(), (BusinessClass) modelElement);
			String disName = BusinessModelUtil.getEditorBusinessModelManager()
					.generateNextOperationDisName(newOp.getDisplayName(),(BusinessClass) modelElement);
			
			newOp.setDisplayName(disName);
//			if (!name.equals(newOp.getName())) {
//				newOp.setDisplayName(name);
//			}
			newOp.setName(name);
			newOp.setId(UUID.randomUUID().toString().replaceAll("-", ""));

			((BusinessClass) modelElement).addOperation(newOp);
		}
		BusinessModelEvent bcAddevent = new BusinessModelEvent(
				BusinessModelEvent.MODEL_ELEMENT_UPDATE, modelElement);
		businessModelManager.businessObjectModelChanged(bcAddevent);
	}

	/**
	 * Paste business classes.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	public void pasteBusinessClasses() {
		// modelElement = (ModelPackage)modelElement;
		for (Object abObject : list) {
			if (abObject instanceof Enumeration){
				Enumeration newBu = ((Enumeration) abObject).cloneChangeId();
				newBu.setBelongPackage((ModelPackage) modelElement);
				String name = BusinessModelUtil.getEditorBusinessModelManager()
						.generateNextEnumerationName(newBu.getName());
				String disName = BusinessModelUtil.getEditorBusinessModelManager()
						.generateNextEnumerationDisName(newBu.getDisplayName());
				newBu.setDisplayName(disName);
				newBu.setName(name);

				BusinessModelEvent bcAddevent = new BusinessModelEvent(
						BusinessModelEvent.MODEL_ELEMENT_ADD, newBu);
				BusinessModelUtil.getEditorBusinessModelManager()
						.businessObjectModelChanged(bcAddevent);

			}
			else if (abObject instanceof BusinessClass) {
				BusinessClass newBu = ((BusinessClass) abObject)
						.cloneChangeId();
				newBu.setBelongPackage((ModelPackage) modelElement);
				String name = BusinessModelUtil.getEditorBusinessModelManager()
						.generateNextBusinessClassName(newBu.getName());
				newBu.setTableName(BusinessModelUtil
						.getEditorBusinessModelManager()
						.generateNextBusinessClassTableName(
								newBu.getTableName()));
				String disName = BusinessModelUtil.getEditorBusinessModelManager()
						.generateNextBusinessClassDisName(newBu.getDisplayName());
				
				newBu.setDisplayName(disName);

//				if (!name.equals(newBu.getName())) {
//					newBu.setDisplayName(name);
//				}
				newBu.setName(name);
				buMap.put((BusinessClass) abObject, newBu);

				BusinessModelEvent bcAddevent = new BusinessModelEvent(
						BusinessModelEvent.MODEL_ELEMENT_ADD, newBu);
				businessModelManager.businessObjectModelChanged(bcAddevent);
			} else if (abObject instanceof ReferenceObject) {
				boolean flag = false;
				ReferenceObject busref = null;
				for (ReferenceObject ref : businessModelManager
						.getBusinessObjectModel().getReferenceObjects()) {
					if (ref.getReferenceObjectId()
							.equals(((ReferenceObject) abObject)
									.getReferenceObjectId())) {
						flag = true;
						busref = ref;
					}
				}
				boolean flag1 = false;
				BusinessClass bus = null;
				for (BusinessClass bu : BusinessModelUtil
						.getBusinessModelDiagramEditor()
						.getBusinessModelManager().getBusinessObjectModel()
						.getBusinessClasses()) {
					if (bu.getId().equals(
							((ReferenceObject) abObject).getReferenceObject()
									.getId())
							&& bu.getName().equals(
									((ReferenceObject) abObject)
											.getReferenceObject().getName())) {
						flag1 = true;
						bus = bu;
					}
				}
				if (bus != null)
					buMap.put((BusinessClass) ((ReferenceObject) abObject)
							.getReferenceObject(), bus);
				else if (busref != null)
					buMap.put((BusinessClass) ((ReferenceObject) abObject)
							.getReferenceObject(), (BusinessClass) busref
							.getReferenceObject());
				else
					buMap.put((BusinessClass) ((ReferenceObject) abObject)
							.getReferenceObject(),
							(BusinessClass) ((ReferenceObject) abObject)
									.getReferenceObject());
				// 新建一个引用对象
				if (!flag && !flag1) {
					ReferenceObject ref = new ReferenceObject(
							((ReferenceObject) abObject).getReferenceObject());
					ref.setBelongPackage((ModelPackage) modelElement);
					BusinessModelEvent event1 = new BusinessModelEvent(
							BusinessModelEvent.MODEL_ELEMENT_ADD, ref);
					BusinessModelUtil.getEditorBusinessModelManager()
							.businessObjectModelChanged(event1);
				}
			}
		}
		for (Object abObject : list) {
			if (abObject instanceof Association) {
				Association as = ((Association) abObject).clone();

				if (as.getMajorClassId().equals(as.getClassA().getId())
						&& buMap.get(as.getClassA()) != null) {
					as.setMajorClassId(buMap.get(as.getClassA()).getId());
				} else if (as.getMajorClassId().equals(as.getClassB().getId())
						&& buMap.get(as.getClassB()) != null) {
					as.setMajorClassId(buMap.get(as.getClassB()).getId());
				}
				as.setClassA(buMap.get(as.getClassA()));
				as.setClassB(buMap.get(as.getClassB()));
				as.setBelongPackage((ModelPackage) modelElement);
				String name = BusinessModelUtil.getEditorBusinessModelManager()
						.generateNextAssName(as.getName());
				String disName = BusinessModelUtil.getEditorBusinessModelManager()
						.generateNextAssDisName(as.getDisplayName());
				
				as.setDisplayName(disName);

//				if (!name.equals(as.getName())) {
//					as.setDisplayName(name);
//				}
				as.setName(name);
				BusinessModelEvent bcAddevent = new BusinessModelEvent(
						BusinessModelEvent.MODEL_ELEMENT_ADD, as);
				BusinessModelUtil.getEditorBusinessModelManager()
						.businessObjectModelChanged(bcAddevent);
			}
		}
	}
}
