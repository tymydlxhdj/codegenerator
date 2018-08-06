package com.mqfdy.code.model.utils;

import java.util.List;
import java.util.Locale;

import com.mqfdy.code.model.AbstractModelElement;
import com.mqfdy.code.model.Association;
import com.mqfdy.code.model.BEPermission;
import com.mqfdy.code.model.BEStatus;
import com.mqfdy.code.model.BusinessClass;
import com.mqfdy.code.model.BusinessObjectModel;
import com.mqfdy.code.model.BusinessOperation;
import com.mqfdy.code.model.DTOProperty;
import com.mqfdy.code.model.DataTransferObject;
import com.mqfdy.code.model.EnumElement;
import com.mqfdy.code.model.Enumeration;
import com.mqfdy.code.model.IModelElement;
import com.mqfdy.code.model.Inheritance;
import com.mqfdy.code.model.ModelPackage;
import com.mqfdy.code.model.OperationParam;
import com.mqfdy.code.model.PKProperty;
import com.mqfdy.code.model.PersistenceProperty;
import com.mqfdy.code.model.Property;
import com.mqfdy.code.model.PropertyGroup;
import com.mqfdy.code.model.ReferenceObject;
import com.mqfdy.code.model.SolidifyPackage;
import com.mqfdy.code.model.Validator;
import com.mqfdy.code.model.graph.Diagram;

/**
 * 模型工具类
 * 
 * @author mqfdy
 * 
 */
public class ModelUtil {
	public static String[] getModelTypeDisplays() {
		String[] types = new String[] { "",
				IModelElement.MODEL_STEREOTYPE_BUSINESSCLASSOBJECTMODEL,
				IModelElement.MODEL_STEREOTYPE_MODELPACKAGE,
				IModelElement.MODEL_STEREOTYPE_BUSINESSCLASS,
				IModelElement.MODEL_STEREOTYPE_DIAGRAM,
				IModelElement.MODEL_STEREOTYPE_ASSOCIATION,
				IModelElement.MODEL_STEREOTYPE_ENUMERATION,
				IModelElement.MODEL_STEREOTYPE_PROPERTY,
				IModelElement.MODEL_STEREOTYPE_PERSISTENCEPROPERTY,
				IModelElement.MODEL_STEREOTYPE_PKPROPERTY,
				IModelElement.MODEL_STEREOTYPE_REFERENCEOBJECT,
				IModelElement.MODEL_STEREOTYPE_BUSINESSOPERATION };

		return types;
	}

	/**
	 * 获取模型类型的显示名称
	 * 
	 * @param type
	 * @return
	 */
	public static String getModelTypeDisplay(String type) {
		String display = "";
		if (BusinessObjectModel.class.getSimpleName().equals(type)) {

			display = IModelElement.MODEL_STEREOTYPE_BUSINESSCLASSOBJECTMODEL;

		} else if (ModelPackage.class.getSimpleName().equals(type)) {

			display = IModelElement.MODEL_STEREOTYPE_MODELPACKAGE;

		} else if (SolidifyPackage.class.getSimpleName().equals(type)) {

			display = IModelElement.MODEL_STEREOTYPE_SOLIDIFYPACKAGE;

		} else if (BusinessClass.class.getSimpleName().equals(type)) {

			display = IModelElement.MODEL_STEREOTYPE_BUSINESSCLASS;

		} else if (Diagram.class.getSimpleName().equals(type)) {

			display = IModelElement.MODEL_STEREOTYPE_DIAGRAM;

		} else if (DataTransferObject.class.getSimpleName().equals(type)) {

			display = IModelElement.MODEL_STEREOTYPE_DTO;

		} else if (Association.class.getSimpleName().equals(type)) {
			display = IModelElement.MODEL_STEREOTYPE_ASSOCIATION;

		} else if (Inheritance.class.getSimpleName().equals(type)) {

			display = IModelElement.MODEL_STEREOTYPE_INHERITANCE;

		} else if (Enumeration.class.getSimpleName().equals(type)) {

			display = IModelElement.MODEL_STEREOTYPE_ENUMERATION;

		} else if (BusinessOperation.class.getSimpleName().equals(type)) {

			display = IModelElement.MODEL_STEREOTYPE_BUSINESSOPERATION;

		} else if (Property.class.getSimpleName().equals(type)) {

			display = IModelElement.MODEL_STEREOTYPE_PROPERTY;

		} else if (PersistenceProperty.class.getSimpleName().equals(type)) {

			display = IModelElement.MODEL_STEREOTYPE_PERSISTENCEPROPERTY;

		} else if (DTOProperty.class.getSimpleName().equals(type)) {

			display = IModelElement.MODEL_STEREOTYPE_DTOPROPERTY;

		} else if (PKProperty.class.getSimpleName().equals(type)) {

			display = IModelElement.MODEL_STEREOTYPE_PKPROPERTY;
		} else if (BEStatus.class.getSimpleName().equals(type)) {

			display = IModelElement.MODEL_STEREOTYPE_BESTATUS;

		} else if (BEPermission.class.getSimpleName().equals(type)) {

			display = IModelElement.MODEL_STEREOTYPE_BEPERMISSION;

		} else if (OperationParam.class.getSimpleName().equals(type)) {

			display = IModelElement.MODEL_STEREOTYPE_OPERATIONPARAM;

		} else if (Validator.class.getSimpleName().equals(type)) {

			display = IModelElement.MODEL_STEREOTYPE_VALIDATOR;

		} else if (ReferenceObject.class.getSimpleName().equals(type)) {

			display = IModelElement.MODEL_STEREOTYPE_REFERENCEOBJECT;

		} else if (PropertyGroup.class.getSimpleName().equals(type)) {

			display = IModelElement.MODEL_STEREOTYPE_PROPERTYGROUP;

		} else if (EnumElement.class.getSimpleName().equals(type)) {

			display = IModelElement.MODEL_STEREOTYPE_ENUMELEMENT;

		}

		return display;
	}

	/**
	 * 转换模型的构造类型
	 * 
	 * @param bom
	 * @param stereotype
	 * @return
	 */
	public static BusinessObjectModel transformModelStereotype(
			BusinessObjectModel bom, String stereotype) {
		bom.setStereotype(stereotype);
		List<BusinessClass> bcs = bom.getBusinessClasses();
		for (BusinessClass bc : bcs) {
			bc.setStereotype(stereotype);
			List<Property> propertys = bc.getProperties();
			for (Property property : propertys) {
				property.setStereotype(stereotype);
			}
		}

		List<ModelPackage> packs = bom.getPackages();
		for (ModelPackage pack : packs) {
			pack.setStereotype(stereotype);
		}

		List<Association> associations = bom.getAssociations();
		for (Association association : associations) {
			association.setStereotype(stereotype);
		}

		List<Inheritance> inheritances = bom.getInheritances();
		for (Inheritance inheritance : inheritances) {
			inheritance.setStereotype(stereotype);
		}

		List<DataTransferObject> dtos = bom.getDTOs();
		for (DataTransferObject dto : dtos) {
			dto.setStereotype(stereotype);
		}

		List<Enumeration> enumerations = bom.getEnumerations();
		for (Enumeration enumeration : enumerations) {
			enumeration.setStereotype(stereotype);
		}

		List<ReferenceObject> referenceObjects = bom.getReferenceObjects();
		for (ReferenceObject referenceObject : referenceObjects) {
			referenceObject.setStereotype(stereotype);
		}

		List<Diagram> diagrams = bom.getDiagrams();
		for (Diagram diagram : diagrams) {
			diagram.setStereotype(stereotype);
		}

		return bom;
	}

	public static BusinessObjectModel getBusinessModelOfElement(
			AbstractModelElement element) {
		if (element == null) {
			return null;
		} else if (element instanceof BusinessObjectModel) {
			return (BusinessObjectModel) element;
		}

		AbstractModelElement bom;
		bom = element.getParent();
		while (bom != null && !(bom instanceof BusinessObjectModel)) {
			bom = bom.getParent();
		}
		if (bom != null) {
			return (BusinessObjectModel) bom;
		} else {
			return null;
		}
	}

	public static BusinessClass getAssociateBusinessClass(
			BusinessObjectModel bom, BusinessClass bc, String associateClassName) {
		BusinessClass associateBc = null;
		List<Association> associations = bom.getAssociations();
		for (Association association : associations) {
			if (association.getClassA().equals(bc)) {
				String className = association.getClassB().getName()
						.toUpperCase(Locale.getDefault());
				if (className.equals(associateClassName.toUpperCase(Locale.getDefault()))) {
					return association.getClassB();
				}
			} else if (association.getClassB().equals(bc)) {
				String className = association.getClassA().getName()
						.toUpperCase(Locale.getDefault());
				if (className.equals(associateClassName.toUpperCase(Locale.getDefault()))) {
					return association.getClassA();
				}
			}
		}
		return associateBc;
	}
}
