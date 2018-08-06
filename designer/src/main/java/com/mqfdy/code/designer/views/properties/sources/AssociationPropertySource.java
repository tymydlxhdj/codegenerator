package com.mqfdy.code.designer.views.properties.sources;

import java.util.List;
import java.util.Locale;

import com.mqfdy.code.designer.models.BusinessModelManager;
import com.mqfdy.code.designer.utils.BusinessModelUtil;
import com.mqfdy.code.model.AbstractModelElement;
import com.mqfdy.code.model.Association;
import com.mqfdy.code.model.Property;
import com.mqfdy.code.model.utils.AssociationType;
import com.mqfdy.code.resource.validator.ValidatorUtil;


/**
 * 关联关系associationPropertySource
 * 
 * @author mqfdy
 * 
 */
public class AssociationPropertySource extends ModelPropertySource {
	public Association association;
	private BusinessModelManager manager = BusinessModelUtil
			.getEditorBusinessModelManager();

	public AssociationPropertySource(AbstractModelElement association) {
		super();
		this.association = (Association) association;
		// if (super.getModelProperties().isEmpty()) {
		super.getModelProperties().clear();
		installModelProperty();
		// }
	}

	protected void initializeDescriptors() {
	}

	protected void installModelProperty() {
		addStringModelProperty(IBusinessModelPropertyNames.PROPERTY_COMMON_ID,
				IBusinessModelPropertyNames.CATEGORY_BASE, "", true, "01");
		addStringModelProperty(
				IBusinessModelPropertyNames.PROPERTY_COMMON_OBJECTTYPE,
				IBusinessModelPropertyNames.CATEGORY_BASE, "", true, "02");
		addStringModelProperty(
				IBusinessModelPropertyNames.PROPERTY_COMMON_STEREOTYPETYPE,
				IBusinessModelPropertyNames.CATEGORY_BASE, "", true, "02");
		addStringModelProperty(
				IBusinessModelPropertyNames.PROPERTY_COMMON_NAME,
				IBusinessModelPropertyNames.CATEGORY_BASE, "", "03");
		addStringModelProperty(
				IBusinessModelPropertyNames.PROPERTY_COMMON_DISPLAYNAME,
				IBusinessModelPropertyNames.CATEGORY_BASE, "", "04");
		addStringModelProperty(
				IBusinessModelPropertyNames.PROPERTY_ASSOCIATION_ASSOCIATIONTYPE,
				IBusinessModelPropertyNames.CATEGORY_BASE, "", true, "06");
		// if(association.getAssociationType().trim().equals(AssociationType.mult2one.getValue())||association.getAssociationType().trim().equals(AssociationType.one2mult.getValue())){
		// addButtonModelProperty(IBusinessModelPropertyNames.PROPERTY_ASSOCIATION_CLASSA,IBusinessModelPropertyNames.CATEGORY_ASSOCIATIONA,"","BusinessClassA",null,"07");
		// addButtonModelProperty(IBusinessModelPropertyNames.PROPERTY_ASSOCIATION_CLASSB,IBusinessModelPropertyNames.CATEGORY_ASSOCIATIONB,"","BusinessClassB",null,"08");
		// addBooleanModelProperty(IBusinessModelPropertyNames.PROPERTY_ASSOCIATION_CASCADEDELETECLASSB,IBusinessModelPropertyNames.CATEGORY_ASSOCIATIONA,true,"09");
		// addBooleanModelProperty(IBusinessModelPropertyNames.PROPERTY_ASSOCIATION_NAVIGETETOCLASSB,IBusinessModelPropertyNames.CATEGORY_ASSOCIATIONA,true,"10");
		// addListModelProperty(IBusinessModelPropertyNames.PROPERTY_ASSOCIATION_PERSISTENCEPLOY,IBusinessModelPropertyNames.CATEGORY_BASE,new
		// String[]{"在实体A对应的表中创建外键","在实体B对应的表中创建外键"/*,"在实体A对应的表中创建外键,在实体B对应的表中创建外键"*/},"11");

		// }else
		// if(association.getAssociationType().trim().equals(AssociationType.one2one.getValue())){
		// addButtonModelProperty(IBusinessModelPropertyNames.PROPERTY_ASSOCIATION_CLASSA,IBusinessModelPropertyNames.CATEGORY_ASSOCIATIONA,"","BusinessClass","12");
		// addButtonModelProperty(IBusinessModelPropertyNames.PROPERTY_ASSOCIATION_CLASSB,IBusinessModelPropertyNames.CATEGORY_ASSOCIATIONB,"","BusinessClass","13");
		// addListModelProperty(IBusinessModelPropertyNames.PROPERTY_ASSOCIATION_MAJOR_CLASSNAME,IBusinessModelPropertyNames.CATEGORY_BASE,new
		// String[]{"classA","classB"},"14");
		// addBooleanModelProperty(IBusinessModelPropertyNames.PROPERTY_ASSOCIATION_CASCADEDELETECLASSA,IBusinessModelPropertyNames.CATEGORY_ASSOCIATIONB,true,"15");
		// addBooleanModelProperty(IBusinessModelPropertyNames.PROPERTY_ASSOCIATION_CASCADEDELETECLASSB,IBusinessModelPropertyNames.CATEGORY_ASSOCIATIONA,true,"16");
		// addBooleanModelProperty(IBusinessModelPropertyNames.PROPERTY_ASSOCIATION_NAVIGETETOCLASSA,IBusinessModelPropertyNames.CATEGORY_ASSOCIATIONB,true,"17");
		// addBooleanModelProperty(IBusinessModelPropertyNames.PROPERTY_ASSOCIATION_NAVIGETETOCLASSB,IBusinessModelPropertyNames.CATEGORY_ASSOCIATIONA,true,"18");
		// addListModelProperty(IBusinessModelPropertyNames.PROPERTY_ASSOCIATION_PERSISTENCEPLOY,IBusinessModelPropertyNames.CATEGORY_BASE,new
		// String[]{"在实体A对应的表中创建外键","在实体B对应的表中创建外键"},"19");
		// }else
		// if(association.getAssociationType().trim().equals(AssociationType.one2one.getValue())){
		// addButtonModelProperty(IBusinessModelPropertyNames.PROPERTY_ASSOCIATION_CLASSA,IBusinessModelPropertyNames.CATEGORY_ASSOCIATIONA,"","BusinessClassA",null,"20");
		// addButtonModelProperty(IBusinessModelPropertyNames.PROPERTY_ASSOCIATION_CLASSB,IBusinessModelPropertyNames.CATEGORY_ASSOCIATIONB,"","BusinessClassB",null,"21");
		// //
		// addListModelProperty(IBusinessModelPropertyNames.PROPERTY_ASSOCIATION_MAJOR_CLASSNAME,IBusinessModelPropertyNames.CATEGORY_BASE,new
		// String[]{"classA","classB"},"22");
		// addBooleanModelProperty(IBusinessModelPropertyNames.PROPERTY_ASSOCIATION_CASCADEDELETECLASSA,IBusinessModelPropertyNames.CATEGORY_ASSOCIATIONB,true,"23");
		// addBooleanModelProperty(IBusinessModelPropertyNames.PROPERTY_ASSOCIATION_CASCADEDELETECLASSB,IBusinessModelPropertyNames.CATEGORY_ASSOCIATIONA,true,"24");
		// addBooleanModelProperty(IBusinessModelPropertyNames.PROPERTY_ASSOCIATION_NAVIGETETOCLASSA,IBusinessModelPropertyNames.CATEGORY_ASSOCIATIONB,true,"25");
		// addBooleanModelProperty(IBusinessModelPropertyNames.PROPERTY_ASSOCIATION_NAVIGETETOCLASSB,IBusinessModelPropertyNames.CATEGORY_ASSOCIATIONA,true,"26");
		// addListModelProperty(IBusinessModelPropertyNames.PROPERTY_ASSOCIATION_PERSISTENCEPLOY,IBusinessModelPropertyNames.PROPERTY_ASSOCIATION_PERSISTENCEPLOY,new
		// String[]{"在实体A对应的表中创建外键","在实体B对应的表中创建外键"/*,"在实体A对应的表中创建外键,在实体B对应的表中创建外键"*/},"26");
		// }
		// else
		// if(association.getAssociationType().trim().equals(AssociationType.mult2mult.getValue())){
		// addButtonModelProperty(IBusinessModelPropertyNames.PROPERTY_ASSOCIATION_CLASSA,IBusinessModelPropertyNames.CATEGORY_ASSOCIATIONA,"","BusinessClassA",null,"20");
		// addButtonModelProperty(IBusinessModelPropertyNames.PROPERTY_ASSOCIATION_CLASSB,IBusinessModelPropertyNames.CATEGORY_ASSOCIATIONB,"","BusinessClassB",null,"21");
		// //
		// addListModelProperty(IBusinessModelPropertyNames.PROPERTY_ASSOCIATION_MAJOR_CLASSNAME,IBusinessModelPropertyNames.CATEGORY_BASE,new
		// String[]{"classA","classB"},"22");
		// addBooleanModelProperty(IBusinessModelPropertyNames.PROPERTY_ASSOCIATION_CASCADEDELETECLASSA,IBusinessModelPropertyNames.CATEGORY_ASSOCIATIONB,true,"23");
		// addBooleanModelProperty(IBusinessModelPropertyNames.PROPERTY_ASSOCIATION_CASCADEDELETECLASSB,IBusinessModelPropertyNames.CATEGORY_ASSOCIATIONA,true,"24");
		// addBooleanModelProperty(IBusinessModelPropertyNames.PROPERTY_ASSOCIATION_NAVIGETETOCLASSA,IBusinessModelPropertyNames.CATEGORY_ASSOCIATIONB,true,"25");
		// addBooleanModelProperty(IBusinessModelPropertyNames.PROPERTY_ASSOCIATION_NAVIGETETOCLASSB,IBusinessModelPropertyNames.CATEGORY_ASSOCIATIONA,true,"26");
		// addStringModelProperty(IBusinessModelPropertyNames.PROPERTY_ASSOCIATION_MITABLENAME,IBusinessModelPropertyNames.PROPERTY_ASSOCIATION_PERSISTENCEPLOY,"","27");
		// addListModelProperty(IBusinessModelPropertyNames.PROPERTY_ASSOCIATION_COLUMNOFCLASSA,IBusinessModelPropertyNames.PROPERTY_ASSOCIATION_PERSISTENCEPLOY,getString(association.getClassA().getProperties()),"28");
		// addListModelProperty(IBusinessModelPropertyNames.PROPERTY_ASSOCIATION_COLUMNOFCLASSB,IBusinessModelPropertyNames.PROPERTY_ASSOCIATION_PERSISTENCEPLOY,getString(association.getClassB().getProperties()),"29");
		// }
		addStringModelProperty(
				IBusinessModelPropertyNames.PROPERTY_COMMON_REMARK,
				IBusinessModelPropertyNames.CATEGORY_BASE, "", "31");

	}

	public String[] getString(List<Property> list) {
		String[] ps = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			ps[i] = list.get(i).getName();
		}
		return ps;
	}

	public Object getPropertyValue(Object propertyId) {
		if (!(propertyId instanceof String)) {
			throw new IllegalArgumentException();
		}
		if (propertyId.equals(IBusinessModelPropertyNames.PROPERTY_COMMON_ID)) {
			return association.getId();
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_COMMON_DISPLAYNAME)) {
			return association.getDisplayName();
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_COMMON_NAME)) {
			return association.getName();
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_COMMON_REMARK)) {
			return association.getRemark();
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_COMMON_OBJECTTYPE)) {
			return association.getDisplayType();
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_COMMON_STEREOTYPETYPE)) {
			String stype = association.getStereotype();
			return AbstractModelElementPropertySource.getStereotype(stype);
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_ASSOCIATION_ASSOCIATIONTYPE)) {
			return AssociationType.getAssociationType(
					association.getAssociationType()).getDisplayValue();
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_ASSOCIATION_CASCADEDELETECLASSA)) {
			return association.isCascadeDeleteClassA();
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_ASSOCIATION_CASCADEDELETECLASSB)) {
			return association.isCascadeDeleteClassB();
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_ASSOCIATION_NAVIGETETOCLASSA)) {
			return association.isNavigateToClassA();
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_ASSOCIATION_NAVIGETETOCLASSB)) {
			return association.isNavigateToClassB();
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_ASSOCIATION_PERSISTENCEPLOY)) {
			String s = "";
			if (association.getPersistencePloyParams() != null) {
				if (association.getPersistencePloyParams().get(
						Association.FOREIGNKEYINA) != null
						&& association.getPersistencePloyParams()
								.get(Association.FOREIGNKEYINA).toLowerCase(Locale.getDefault())
								.equals("true")
						&& association.getPersistencePloyParams().get(
								Association.FOREIGNKEYINB) != null
						&& association.getPersistencePloyParams()
								.get(Association.FOREIGNKEYINB).toLowerCase(Locale.getDefault())
								.equals("true"))
					s = "在实体A对应的表中创建外键,在实体B对应的表中创建外键";
				else if (association.getPersistencePloyParams().get(
						Association.FOREIGNKEYINA) != null
						&& association.getPersistencePloyParams()
								.get(Association.FOREIGNKEYINA).toLowerCase(Locale.getDefault())
								.equals("true"))
					s = "在实体A对应的表中创建外键";
				else if (association.getPersistencePloyParams().get(
						Association.FOREIGNKEYINB) != null
						&& association.getPersistencePloyParams()
								.get(Association.FOREIGNKEYINB).toLowerCase(Locale.getDefault())
								.equals("true"))
					s = "在实体B对应的表中创建外键";
			}
			return s;
			// return association.getPersistencePloy();
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_ASSOCIATION_CLASSA)) {
			return association.getClassA() == null ? "" : association
					.getClassA().getDisplayName();
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_ASSOCIATION_CLASSB)) {
			return association.getClassB() == null ? "" : association
					.getClassB().getDisplayName();
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_ASSOCIATION_MITABLENAME)) {
			return association.getPersistencePloyParams() == null ? ""
					: association.getPersistencePloyParams().get(
							Association.RELATIONTABLENAME) == null ? ""
							: association.getPersistencePloyParams().get(
									Association.RELATIONTABLENAME);
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_ASSOCIATION_COLUMNOFCLASSA)) {
			return association.getPersistencePloyParams().get(
					Association.SOURCERELATIONCOLUMN);
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_ASSOCIATION_COLUMNOFCLASSB)) {
			return association.getPersistencePloyParams().get(
					Association.DESTRELATIONCOLUMN);
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_ASSOCIATION_MAJOR_CLASSNAME)) {
			return association.getMajorClassId();
		} else
			return "";

	}

	public void setPropertyValue(Object propertyId, Object value) {
		if (!(propertyId instanceof String)) {
			throw new IllegalArgumentException(propertyId.toString());
		}
		if (propertyId.equals(IBusinessModelPropertyNames.PROPERTY_COMMON_ID)) {
			if (!(value instanceof String)) {
				throw new IllegalArgumentException(value.toString());
			}
			association.setId(value.toString());
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_COMMON_DISPLAYNAME)) {
			if (!(value instanceof String)) {
				throw new IllegalArgumentException(value.toString());
			}
			if (value.toString().length() == 0) {

			} else if (!ValidatorUtil.valiDisplayName(value.toString())) {

			} else if (!ValidatorUtil.valiDisplayNameLength(value.toString())) {

			} else {
				association.setDisplayName(value.toString());
			}
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_COMMON_NAME)) {
			if (!(value instanceof String)) {
				throw new IllegalArgumentException(value.toString());
			}
			if (value.toString().length() == 0) {

			} else if (!ValidatorUtil.valiName(value.toString())) {

			} else if (!ValidatorUtil.valiNameLength(value.toString())) {

			} else {
				int i = 0;
				for (Association bu : manager.getBusinessObjectModel()
						.getAssociations()) {
					if (bu.getName().equals(value.toString())
							&& !bu.getId().equals(association.getId()))
						i++;
				}
				if (i < 1) {
					association.setName(value.toString());
				}
			}
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_COMMON_REMARK)) {
			if (!(value instanceof String)) {
				throw new IllegalArgumentException(value.toString());
			}
			if (ValidatorUtil.valiRemarkLength(value.toString())) {
				association.setRemark(value.toString());
			}
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_COMMON_OBJECTTYPE)) {
			// if (!(value instanceof String)) {
			// throw new IllegalArgumentException(value.toString());
			// }
			// association.setStereotype(value.toString());
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_ASSOCIATION_ASSOCIATIONTYPE)) {
			// if (!(value instanceof String)) {
			// throw new IllegalArgumentException(value.toString());
			// }
			// association.setAssociationType(value.toString());
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_ASSOCIATION_CASCADEDELETECLASSA)) {
			if (!(value instanceof String)) {
				throw new IllegalArgumentException(value.toString());
			}
			association
					.setCascadeDeleteClassA(value.toString().equals("true") ? true
							: false);
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_ASSOCIATION_CASCADEDELETECLASSB)) {
			if (!(value instanceof String)) {
				throw new IllegalArgumentException(value.toString());
			}
			association
					.setCascadeDeleteClassB(value.toString().equals("true") ? true
							: false);
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_ASSOCIATION_NAVIGETETOCLASSA)) {
			if (!(value instanceof String)) {
				throw new IllegalArgumentException(value.toString());
			}
			association
					.setNavigateToClassA(value.toString().equals("true") ? true
							: false);
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_ASSOCIATION_NAVIGETETOCLASSB)) {
			if (!(value instanceof String)) {
				throw new IllegalArgumentException(value.toString());
			}
			association
					.setNavigateToClassB(value.toString().equals("true") ? true
							: false);
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_ASSOCIATION_PERSISTENCEPLOY)) {
			if (!(value instanceof String)) {
				throw new IllegalArgumentException(value.toString());
			}
			if (((String) value).equals("在实体A对应的表中创建外键")) {
				association.getPersistencePloyParams().put(
						Association.FOREIGNKEYINA, "TRUE");
				association.getPersistencePloyParams().put(
						Association.FOREIGNKEYINB, "FALSE");
			}
			if (((String) value).equals("在实体B对应的表中创建外键")) {
				association.getPersistencePloyParams().put(
						Association.FOREIGNKEYINA, "FALSE");
				association.getPersistencePloyParams().put(
						Association.FOREIGNKEYINB, "TRUE");
			}
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_ASSOCIATION_CLASSA)) {
			if (!(value instanceof BusinessClassPropertySource)) {
				throw new IllegalArgumentException(value.toString());
			}
			association
					.setClassA(((BusinessClassPropertySource) value).businessClass);
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_ASSOCIATION_CLASSB)) {
			if (!(value instanceof BusinessClassPropertySource)) {
				throw new IllegalArgumentException(value.toString());
			}
			association
					.setClassB(((BusinessClassPropertySource) value).businessClass);
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_ASSOCIATION_MITABLENAME)) {
			if (!(value instanceof String)) {
				throw new IllegalArgumentException(value.toString());
			}
			if (value.toString().length() == 0) {

			} else if (!ValidatorUtil.valiFirstNo_Name(value.toString())) {

			} else if (!ValidatorUtil.valiNameLength(value.toString())) {

			} else {
				association.getPersistencePloyParams().put(
						Association.RELATIONTABLENAME, value.toString());
			}
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_ASSOCIATION_COLUMNOFCLASSA)) {
			if (!(value instanceof String)) {
				throw new IllegalArgumentException(value.toString());
			}
			if (value.toString().length() == 0) {

			} else if (!ValidatorUtil.valiFirstNo_Name(value.toString())) {

			} else if (!ValidatorUtil.valiNameLength(value.toString())) {

			} else {
				association.getPersistencePloyParams().put(
						Association.SOURCERELATIONCOLUMN, value.toString());
			}
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_ASSOCIATION_COLUMNOFCLASSB)) {
			if (!(value instanceof String)) {
				throw new IllegalArgumentException(value.toString());
			}
			if (value.toString().length() == 0) {

			} else if (!ValidatorUtil.valiFirstNo_Name(value.toString())) {

			} else if (!ValidatorUtil.valiNameLength(value.toString())) {

			} else {
				association.getPersistencePloyParams().put(
						Association.DESTRELATIONCOLUMN, value.toString());
			}
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_ASSOCIATION_MAJOR_CLASSNAME)) {
			if (!(value instanceof String)) {
				throw new IllegalArgumentException(value.toString());
			}
			association.setMajorClassId(value.toString());
		} else {
			throw new IllegalArgumentException(propertyId.toString());
		}
	}

	public boolean isPropertySet(Object propertyId) {
		return true;
	}

	public void resetPropertyValue(Object propertyId) {
		if (!(propertyId instanceof String)) {
			throw new IllegalArgumentException();
		}
		if (propertyId.equals("bbb")) {
			association.setId("11111");
		} /*
		 * else if (propertyId.equals(DETAIL_PROP)) { // details = 0; }
		 */else {
			throw new IllegalArgumentException();
		}
	}

	public Object getEditableValue() {
		return this;
	}
}
