package com.mqfdy.code.designer.views.modelresource.tree;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import com.mqfdy.code.designer.models.ImageKeys;
import com.mqfdy.code.designer.models.ImageManager;
import com.mqfdy.code.model.AbstractModelElement;
import com.mqfdy.code.model.Association;
import com.mqfdy.code.model.IModelElement;
import com.mqfdy.code.model.PKProperty;
import com.mqfdy.code.model.ReferenceObject;
import com.mqfdy.code.model.utils.AssociationType;
import com.mqfdy.code.model.utils.StringUtil;

public class ModelResourceLabelProvider extends LabelProvider implements
		ILabelProvider {

	public Image getImage(Object element) {
		Image img;
		AbstractModelElement modelElement = (AbstractModelElement) element;
		String type = modelElement.getType();
		if ("BusinessObjectModel".equals(type)) {

			img = ImageManager.getInstance().getImage(
					ImageKeys.IMG_MODEL_TYPE_OBJECTMODEL);

		} else if ("ModelPackage".equals(type)) {

			img = ImageManager.getInstance().getImage(
					ImageKeys.IMG_MODEL_TYPE_PACKAGE);

		} else if ("SolidifyPackage".equals(type)) {

			img = ImageManager.getInstance().getImage(
					ImageKeys.IMG_MODEL_TYPE_SOLIDIFYPACKAGE);

		} else if ("BusinessClass".equals(type)) {

			img = ImageManager.getInstance().getImage(
					ImageKeys.IMG_MODEL_TYPE_BUSINESSCLASS);
			if(IModelElement.STEREOTYPE_REVERSE.equals(((AbstractModelElement) element).getStereotype())){
				img = ImageManager.getInstance().getImage(
						ImageKeys.IMG_MODEL_TYPE_BUSINESSCLASS_DB);
			}
		} else if ("ReferenceObject".equals(type)) {

			img = ImageManager.getInstance().getImage(
					ImageKeys.IMG_MODEL_TYPE_REFERENCEOBJECT);

		} else if ("Diagram".equals(type)) {

			img = ImageManager.getInstance().getImage(
					ImageKeys.IMG_MODEL_TYPE_DIAGRAM);

		} else if ("DataTransferObject".equals(type)) {

			img = ImageManager.getInstance().getImage(
					ImageKeys.IMG_MODEL_TYPE_DTO);

		} else if ("Association".equals(type)) {
			img = ImageManager.getInstance().getImage(
					ImageKeys.IMG_MODEL_TYPE_ASSOCIATION);
			Association association = (Association) modelElement;
			String associationType = association.getAssociationType();
			if (AssociationType.one2one.getValue().equals(associationType)) {
				img = ImageManager.getInstance().getImage(
						ImageKeys.IMG_MODEL_TYPE_ASSOCIATION_ONE2ONE);
			} else if (AssociationType.one2mult.getValue().equals(
					associationType)) {
				img = ImageManager.getInstance().getImage(
						ImageKeys.IMG_MODEL_TYPE_ASSOCIATION_ONE2MULT);
			} else if (AssociationType.mult2one.getValue().equals(
					associationType)) {
				img = ImageManager.getInstance().getImage(
						ImageKeys.IMG_MODEL_TYPE_ASSOCIATION_MULT2ONE);
			} else if (AssociationType.mult2mult.getValue().equals(
					associationType)) {
				img = ImageManager.getInstance().getImage(
						ImageKeys.IMG_MODEL_TYPE_ASSOCIATION_MULT2MULT);
			}

		} else if ("Inheritance".equals(type)) {

			img = ImageManager.getInstance().getImage(
					ImageKeys.IMG_MODEL_TYPE_INHERITANCE);

		} else if ("Enumeration".equals(type)) {

			img = ImageManager.getInstance().getImage(
					ImageKeys.IMG_MODEL_TYPE_ENUMERATION);

		} else if ("BusinessOperation".equals(type)) {

			img = ImageManager.getInstance().getImage(
					ImageKeys.IMG_MODEL_TYPE_OPERATION);

		} else if ("Property".equals(type) || "PKProperty".equals(type)
				|| "PersistenceProperty".equals(type)
				|| "DTOProperty".equals(type)) {
			img = ImageManager.getInstance().getImage(
					ImageKeys.IMG_MODEL_TYPE_PROPERTY);
			if ("PKProperty".equals(type)
					&& ((PKProperty) modelElement).isPrimaryKey()) {
				img = ImageManager.getInstance().getImage(
						ImageKeys.IMG_PROPERTY_PRIMARYKEY);
			}

		} else if ("BEStatus".equals(type)) {

			img = ImageManager.getInstance().getImage(
					ImageKeys.IMG_MODEL_TYPE_STATUS);

		} else {
			img = ImageManager.getInstance().getImage(
					ImageKeys.IMG_MODEL_TYPE_DEFAULT);
		}

		return img;
	}

	public String getText(Object element) {
		AbstractModelElement modelElement = (AbstractModelElement) element;
		if (!StringUtil.isEmpty(modelElement.getDisplayName())) {
			if (modelElement instanceof ReferenceObject) {
				return ((ReferenceObject) modelElement).getReferenceObject()
						.getDisplayName();
			}
			return modelElement.getDisplayName();
		} else if (!StringUtil.isEmpty(modelElement.getName())) {
			if (modelElement instanceof ReferenceObject) {
				return ((ReferenceObject) modelElement).getReferenceObject()
						.getName();
			}
			return modelElement.getName();
		} else if (!StringUtil.isEmpty(modelElement.getId())) {
			return modelElement.getId();
		}

		return "Unknown";
	}

	public void updateLabel() {
	}

}
