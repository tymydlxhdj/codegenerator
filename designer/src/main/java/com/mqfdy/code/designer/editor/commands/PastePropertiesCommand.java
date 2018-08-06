package com.mqfdy.code.designer.editor.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gef.ui.actions.Clipboard;
import org.springframework.beans.BeanUtils;

import com.mqfdy.code.designer.editor.part.BusinessClassEditPart;
import com.mqfdy.code.designer.models.BusinessModelEvent;
import com.mqfdy.code.designer.utils.BusinessModelUtil;
import com.mqfdy.code.model.AbstractModelElement;
import com.mqfdy.code.model.BusinessClass;
import com.mqfdy.code.model.PKProperty;
import com.mqfdy.code.model.PersistenceProperty;
import com.mqfdy.code.model.Property;

/**
 * 粘贴图形
 * 
 * @author mqfdy
 * 
 */

public class PastePropertiesCommand extends Command {
	// 内存中复制的业务实体属性Property
	List<Property> propertiesList = new ArrayList<Property>();
	// 复制出的对象
	List<AbstractModelElement> newProList = new ArrayList<AbstractModelElement>();
	private BusinessClass oldBu;

	public PastePropertiesCommand() {
		super();
		propertiesList.clear();
		if (Clipboard.getDefault().getContents() != null)
			propertiesList.addAll((List<Property>) Clipboard.getDefault()
					.getContents());
	}

	@Override
	public boolean canExecute() {
		if (propertiesList.isEmpty())
			return false;

		if (BusinessModelUtil.getBusinessModelDiagramEditor() == null)
			return false;
		// 不是业务实体
		List<AbstractGraphicalEditPart> list = BusinessModelUtil
				.getSelectedEditParts();
		if (list.size() != 1
				|| !(list.get(0) instanceof BusinessClassEditPart)
				|| BusinessClass.STEREOTYPE_BUILDIN
						.equals(((BusinessClassEditPart) list.get(0))
								.getBusinessClass().getStereotype()))
			return false;
		return true;
	}

	@Override
	public void execute() {
		if (!canExecute())
			return;
		redo();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void redo() {
		List<AbstractGraphicalEditPart> list = BusinessModelUtil
				.getBusinessModelDiagramEditor().getViewer()
				.getSelectedEditParts();
		oldBu = ((BusinessClassEditPart) list.get(0)).getBusinessClass();
		for (Property pro : propertiesList) {
			Property newPro = pro.cloneChangeId();
			newPro.setParent(oldBu);
			newPro.setId(UUID.randomUUID().toString().replaceAll("-", ""));

			String name = BusinessModelUtil.getBusinessModelDiagramEditor()
					.getBusinessModelManager()
					.generateNextPropertyName(newPro.getName(), oldBu);
			String disName = BusinessModelUtil.getBusinessModelDiagramEditor()
					.getBusinessModelManager()
					.generateNextPropertyDisName(newPro.getDisplayName(), oldBu);
			newPro.setDisplayName(disName);
//			if (!name.equals(newPro.getName())) {
//				newPro.setDisplayName(name);
//			}
			newPro.setName(name);
			 if (newPro instanceof PKProperty && oldBu.hasPkProperty()) {
					PersistenceProperty persistenceProperty=new PersistenceProperty();
					BeanUtils.copyProperties(newPro, persistenceProperty);									
					persistenceProperty.setPrimaryKey(false);
					persistenceProperty.setdBColumnName(BusinessModelUtil
							.getBusinessModelDiagramEditor()
							.getBusinessModelManager()
							.generateNextDbColumnName(
									((PersistenceProperty) newPro)
											.getdBColumnName(), oldBu));
					oldBu.addProperty(persistenceProperty);
					newProList.add(persistenceProperty);
				
				}
			 else if (newPro instanceof PersistenceProperty){
				((PersistenceProperty) newPro)
				.setdBColumnName(BusinessModelUtil
						.getBusinessModelDiagramEditor()
						.getBusinessModelManager()
						.generateNextDbColumnName(
								((PersistenceProperty) newPro)
										.getdBColumnName(), oldBu));
				oldBu.addProperty(newPro);
				newProList.add(newPro);
			}				
			else{
				oldBu.addProperty(newPro);
				newProList.add(newPro);
			}
			
		}
		BusinessModelEvent bcAddevent = new BusinessModelEvent(
				BusinessModelEvent.MODEL_ELEMENT_UPDATE, oldBu);
		BusinessModelUtil.getEditorBusinessModelManager()
				.businessObjectModelChanged(bcAddevent);
	}

	@Override
	public boolean canUndo() {
		return canExecute();
	}

	@Override
	public void undo() {
		oldBu.getProperties().removeAll(newProList);
		BusinessModelEvent bcAddevent = new BusinessModelEvent(
				BusinessModelEvent.MODEL_ELEMENT_UPDATE, oldBu);
		BusinessModelUtil.getEditorBusinessModelManager()
				.businessObjectModelChanged(bcAddevent);
	}
}