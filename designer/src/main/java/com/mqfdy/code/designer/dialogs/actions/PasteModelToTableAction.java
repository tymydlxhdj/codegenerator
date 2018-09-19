package com.mqfdy.code.designer.dialogs.actions;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.eclipse.gef.ui.actions.Clipboard;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;
import org.springframework.beans.BeanUtils;

import com.mqfdy.code.designer.dialogs.BusinessClassEditorDialog;
import com.mqfdy.code.designer.dialogs.ModelElementEditorDialog;
import com.mqfdy.code.designer.models.ActionTexts;
import com.mqfdy.code.designer.utils.BusinessModelUtil;
import com.mqfdy.code.model.AbstractModelElement;
import com.mqfdy.code.model.BusinessClass;
import com.mqfdy.code.model.BusinessOperation;
import com.mqfdy.code.model.IModelElement;
import com.mqfdy.code.model.PKProperty;
import com.mqfdy.code.model.PersistenceProperty;
import com.mqfdy.code.model.Property;

// TODO: Auto-generated Javadoc
/**
 * The Class PasteModelToTableAction.
 *
 * @author mqfdy
 */
public class PasteModelToTableAction extends Action {
	
	/** The pro list. */
	private List<AbstractModelElement> proList = new ArrayList<AbstractModelElement>();
	
	/** The table viewer. */
	private TableViewer tableViewer;
	
	/** The type. */
	// 类型 1为属性 2为操作
	private int type;
	
	/** The business class. */
	private ModelElementEditorDialog businessClass;

	/**
	 * Instantiates a new paste model to table action.
	 *
	 * @param tableViewer
	 *            the table viewer
	 * @param type
	 *            the type
	 * @param businessClass2
	 *            the business class 2
	 */
	public PasteModelToTableAction(TableViewer tableViewer, int type,
			ModelElementEditorDialog businessClass2) {
		super(ActionTexts.MODEL_ELEMENT_PASTE);
		this.tableViewer = tableViewer;
		this.type = type;
		setId(ActionFactory.PASTE.getId());
		ISharedImages sharedImages = PlatformUI.getWorkbench()
				.getSharedImages();
		setHoverImageDescriptor(sharedImages
				.getImageDescriptor(ISharedImages.IMG_TOOL_PASTE));
		setImageDescriptor(sharedImages
				.getImageDescriptor(ISharedImages.IMG_TOOL_PASTE));
		setDisabledImageDescriptor(sharedImages
				.getImageDescriptor(ISharedImages.IMG_TOOL_PASTE_DISABLED));
		setEnabled(true);
		this.businessClass = businessClass2;

	}

	/**
	 * 
	 */
	public void run() {
		if (!isEnabled())
			return;
		for (AbstractModelElement pro : proList) {
			if (type == 1) {
				if(IModelElement.STEREOTYPE_REVERSE.equals(businessClass.getEditingElement().getStereotype())
						&& pro instanceof PersistenceProperty){
					continue;
				}
				Property newPro = ((Property) pro).cloneChangeId();
				newPro.setParent(businessClass.getEditingElement());
				newPro.setId(UUID.randomUUID().toString().replaceAll("-", ""));

				String name = BusinessModelUtil
						.getBusinessModelDiagramEditor()
						.getBusinessModelManager()
						.generateNextPropertyName(
								newPro.getName(),
								(BusinessClass) businessClass
										.getEditingElement());
				String disname = BusinessModelUtil
						.getBusinessModelDiagramEditor()
						.getBusinessModelManager()
						.generateNextPropertyDisName(
								newPro.getDisplayName(),
								(BusinessClass) businessClass
										.getEditingElement());
//				if (!name.equals(newPro.getName())) {
					newPro.setDisplayName(disname);
//				}
				newPro.setName(name);
				 if (newPro instanceof PKProperty
						&& ((BusinessClass) businessClass.getEditingElement())
								.hasPkProperty()) {
					((PKProperty) newPro).setPrimaryKey(false);
					PersistenceProperty persistenceProperty=new PersistenceProperty();					
					BeanUtils.copyProperties(newPro, persistenceProperty);									
					persistenceProperty.setPrimaryKey(false);
					persistenceProperty.setdBColumnName(persistenceProperty.getName());
					((BusinessClass) businessClass.getEditingElement()).addProperty(persistenceProperty);
					tableViewer.add(persistenceProperty);
					tableViewer.refresh();
					((BusinessClassEditorDialog) businessClass).getBasicInfoPage()
						.getTableItems().add(persistenceProperty);
					((BusinessClassEditorDialog) businessClass).getBasicInfoPage()
						.refreshTable();
				}
				 else if (newPro instanceof PersistenceProperty){
					((PersistenceProperty) newPro)
					.setdBColumnName(BusinessModelUtil
							.getBusinessModelDiagramEditor()
							.getBusinessModelManager()
							.generateNextDbColumnName(
									((PersistenceProperty) newPro)
											.getdBColumnName(),
									(BusinessClass) businessClass
											.getEditingElement()));
					((BusinessClass) businessClass.getEditingElement())
							.addProperty(newPro);
					tableViewer.add(newPro);
					tableViewer.refresh();
					((BusinessClassEditorDialog) businessClass).getBasicInfoPage()
							.getTableItems().add(newPro);
					((BusinessClassEditorDialog) businessClass).getBasicInfoPage()
							.refreshTable();
				}
					

				else{
					((BusinessClass) businessClass.getEditingElement()).addProperty(newPro);
					tableViewer.add(newPro);
					tableViewer.refresh();
					((BusinessClassEditorDialog) businessClass).getBasicInfoPage()
						.getTableItems().add(newPro);
					((BusinessClassEditorDialog) businessClass).getBasicInfoPage()
						.refreshTable();
			 }
				
			} else if (type == 2) {
				BusinessOperation newPro = ((BusinessOperation) pro)
						.cloneChangeId();
				newPro.setBelongBusinessClass((BusinessClass) businessClass
						.getEditingElement());
				newPro.setId(UUID.randomUUID().toString().replaceAll("-", ""));

				String name = BusinessModelUtil
						.getBusinessModelDiagramEditor()
						.getBusinessModelManager()
						.generateNextOperationName(
								newPro.getName(),
								(BusinessClass) businessClass
										.getEditingElement());
				String disname = BusinessModelUtil
						.getBusinessModelDiagramEditor()
						.getBusinessModelManager()
						.generateNextOperationDisName(
								newPro.getDisplayName(),
								(BusinessClass) businessClass
										.getEditingElement());
//				if (!name.equals(newPro.getName())) {
					newPro.setDisplayName(disname);
//				}
				newPro.setName(name);
				((BusinessClass) businessClass.getEditingElement())
						.addOperation(newPro);
				tableViewer.add(newPro);
				tableViewer.refresh();
				((BusinessClassEditorDialog) businessClass).getOperationsPage()
						.getTableItems().add(newPro);
				((BusinessClassEditorDialog) businessClass).getOperationsPage()
						.refreshTable();
			}
		}
	}

	/**
	 * @return
	 */
	@Override
	public boolean isEnabled() {
		proList.clear();
		if (Clipboard.getDefault().getContents() != null)
			proList.addAll((List<AbstractModelElement>) Clipboard.getDefault()
					.getContents());
		if (proList.isEmpty())
			return false;
		boolean isAllPerPty = true;
		for(int i=0;i<proList.size();i++){
			if(proList.get(i) instanceof AbstractModelElement){
				if(!(proList.get(i) instanceof PersistenceProperty)){
					isAllPerPty = false;
				}
			}
		}
		if (type == 1 && proList.get(0) instanceof Property){
			if(IModelElement.STEREOTYPE_REVERSE.equals(businessClass.getEditingElement().getStereotype())
					&& isAllPerPty){
				return false;
			}
			return true;
		}
		else if (type == 2 && proList.get(0) instanceof BusinessOperation)
			return true;
		return false;
	}
}
