package com.mqfdy.code.designer.views.properties.sources;

import com.mqfdy.code.designer.models.BusinessModelManager;
import com.mqfdy.code.designer.utils.BusinessModelUtil;
import com.mqfdy.code.designer.utils.CheckerUtil;
import com.mqfdy.code.model.AbstractModelElement;
import com.mqfdy.code.model.BusinessClass;
import com.mqfdy.code.model.IModelElement;
import com.mqfdy.code.resource.validator.ValidatorUtil;


/**
 * 业务实体BusinessClassPropertySource
 * 
 * @author mqfdy
 * 
 */
public class BusinessClassPropertySource extends ModelPropertySource {
	public BusinessClass businessClass;
	private BusinessModelManager manager = BusinessModelUtil
			.getEditorBusinessModelManager();

	public BusinessClassPropertySource(AbstractModelElement businessClass) {
		super();
		this.businessClass = (BusinessClass) businessClass;
		initializeDescriptors();
		super.getModelProperties().clear();
		// if (super.getModelProperties().isEmpty()) {
		installModelProperty();
		// }
	}

	protected void initializeDescriptors() {
	}

	protected void installModelProperty() {
		if (businessClass.getStereotype() != null
				&& (IModelElement.STEREOTYPE_BUILDIN.equals(businessClass.getStereotype()) 
						|| IModelElement.STEREOTYPE_REFERENCE.equals(businessClass.getStereotype()
								))) {
			addStringModelProperty(
					IBusinessModelPropertyNames.PROPERTY_COMMON_ID,
					IBusinessModelPropertyNames.CATEGORY_BASE, "", true, "01")
					.setAdvanced(false);
			addStringModelProperty(
					IBusinessModelPropertyNames.PROPERTY_COMMON_OBJECTTYPE,
					IBusinessModelPropertyNames.CATEGORY_BASE, "", true, "02");
			addStringModelProperty(
					IBusinessModelPropertyNames.PROPERTY_COMMON_STEREOTYPETYPE,
					IBusinessModelPropertyNames.CATEGORY_BASE, "", true, "02");
			addStringModelProperty(
					IBusinessModelPropertyNames.PROPERTY_COMMON_NAME,
					IBusinessModelPropertyNames.CATEGORY_BASE, "", true, "03");
			addStringModelProperty(
					IBusinessModelPropertyNames.PROPERTY_COMMON_DISPLAYNAME,
					IBusinessModelPropertyNames.CATEGORY_BASE, "", true, "04");
			addStringModelProperty(
					IBusinessModelPropertyNames.PROPERTY_COMMON_REMARK,
					IBusinessModelPropertyNames.CATEGORY_BASE, "", true, "06");
			addStringModelProperty(
					IBusinessModelPropertyNames.PROPERTY_BUSINESSCLASS_TABLENAME,
					IBusinessModelPropertyNames.CATEGORY_BASE, "", true, "05");
			addStringModelProperty(
					IBusinessModelPropertyNames.PROPERTY_VERSION_VERSIONNUM,
					IBusinessModelPropertyNames.CATEGORY_VERSION, "", true,
					"07");
			addStringModelProperty(
					IBusinessModelPropertyNames.PROPERTY_VERSION_CREATER,
					IBusinessModelPropertyNames.CATEGORY_VERSION, "", true,
					"08");
			addStringModelProperty(
					IBusinessModelPropertyNames.PROPERTY_VERSION_CREATETIME,
					IBusinessModelPropertyNames.CATEGORY_VERSION, "", true,
					"09");
			addStringModelProperty(
					IBusinessModelPropertyNames.PROPERTY_VERSION_UPDATER,
					IBusinessModelPropertyNames.CATEGORY_VERSION, "", true,
					"10");
			addStringModelProperty(
					IBusinessModelPropertyNames.PROPERTY_VERSION_UPDATETIME,
					IBusinessModelPropertyNames.CATEGORY_VERSION, "", true,
					"11");
		} else {
			addStringModelProperty(
					IBusinessModelPropertyNames.PROPERTY_COMMON_ID,
					IBusinessModelPropertyNames.CATEGORY_BASE, "", true, "01")
					.setAdvanced(false);
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
					IBusinessModelPropertyNames.PROPERTY_COMMON_REMARK,
					IBusinessModelPropertyNames.CATEGORY_BASE, "", "06");
			if(IModelElement.STEREOTYPE_REVERSE.equals(businessClass.getStereotype())){
				addStringModelProperty(
						IBusinessModelPropertyNames.PROPERTY_BUSINESSCLASS_TABLENAME,
						IBusinessModelPropertyNames.CATEGORY_BASE, "",true, "05");
			}
			else{
				addStringModelProperty(
						IBusinessModelPropertyNames.PROPERTY_BUSINESSCLASS_TABLENAME,
						IBusinessModelPropertyNames.CATEGORY_BASE, "", "05");
			}
			addStringModelProperty(
					IBusinessModelPropertyNames.PROPERTY_VERSION_VERSIONNUM,
					IBusinessModelPropertyNames.CATEGORY_VERSION, "", true,
					"07");
			addStringModelProperty(
					IBusinessModelPropertyNames.PROPERTY_VERSION_CREATER,
					IBusinessModelPropertyNames.CATEGORY_VERSION, "", true,
					"08");
			addStringModelProperty(
					IBusinessModelPropertyNames.PROPERTY_VERSION_CREATETIME,
					IBusinessModelPropertyNames.CATEGORY_VERSION, "", true,
					"09");
			addStringModelProperty(
					IBusinessModelPropertyNames.PROPERTY_VERSION_UPDATER,
					IBusinessModelPropertyNames.CATEGORY_VERSION, "", true,
					"10");
			addStringModelProperty(
					IBusinessModelPropertyNames.PROPERTY_VERSION_UPDATETIME,
					IBusinessModelPropertyNames.CATEGORY_VERSION, "", true,
					"11");
		}
	}

	public Object getPropertyValue(Object propertyId) {
		if (!(propertyId instanceof String)) {
			throw new IllegalArgumentException();
		}
		if (propertyId.equals(IBusinessModelPropertyNames.PROPERTY_COMMON_ID)) {
			return businessClass.getId();
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_COMMON_NAME)) {
			return businessClass.getName();
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_COMMON_REMARK)) {
			return businessClass.getRemark();
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_COMMON_DISPLAYNAME)) {
			return businessClass.getDisplayName();
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_COMMON_OBJECTTYPE)) {
			return businessClass.getDisplayType();
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_COMMON_STEREOTYPETYPE)) {
			String stype = businessClass.getStereotype();
			return AbstractModelElementPropertySource.getStereotype(stype);
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_BUSINESSCLASS_TABLENAME)) {
			return businessClass.getTableName();
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_BUSINESSCLASS_ISABSTRACT)) {
			return businessClass.isAbstract();
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_VERSION_VERSIONNUM)) {
			return businessClass.getVersionInfo() == null ? "" : businessClass
					.getVersionInfo().getVersionNumber();
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_VERSION_CREATER)) {
			return businessClass.getVersionInfo() == null ? "" : businessClass
					.getVersionInfo().getCreator();
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_VERSION_CREATETIME)) {
			return businessClass.getVersionInfo() == null ? "" : businessClass
					.getVersionInfo().getCreatedTime();
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_VERSION_UPDATER)) {
			return businessClass.getVersionInfo() == null ? "" : businessClass
					.getVersionInfo().getModifier();
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_VERSION_UPDATETIME)) {
			return businessClass.getVersionInfo() == null ? "" : businessClass
					.getVersionInfo().getChangedTime();
		} else
			return "";
	}

	public void setPropertyValue(Object propertyId, Object value) {
		if (!(propertyId instanceof String)) {
			throw new IllegalArgumentException(propertyId.toString());
		}
		if (propertyId.equals(IBusinessModelPropertyNames.PROPERTY_COMMON_ID)) {
			// if (!(value instanceof String)) {
			// throw new IllegalArgumentException(value.toString());
			// }
			// businessClass.setId(value.toString());
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_COMMON_NAME)) {
			if (!(value instanceof String)) {
				throw new IllegalArgumentException(value.toString());
			}
			if (!ValidatorUtil.valiName(value.toString())) {

			}
			if (!ValidatorUtil.isFirstUppercase(value.toString())) {

			} else if (CheckerUtil.checkJava(value.toString())) {

			} else if (CheckerUtil.checkSguap(value.toString())) {

			} else {
				int i = 0;
				if (businessClass.getParent() != null) {
					for (BusinessClass bu : manager.getBusinessObjectModel()
							.getBusinessClasses()) {
						if (bu.getName().equals(value.toString())
								&& !bu.getId().equals(businessClass.getId()))
							i++;
					}
				}
				if (i < 1) {
					businessClass.setName(value.toString());
				}
			}
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_COMMON_REMARK)) {
			if (!(value instanceof String)) {
				throw new IllegalArgumentException(value.toString());
			}
			if (ValidatorUtil.valiRemarkLength(value.toString())) {
				businessClass.setRemark(value.toString());
			}
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_COMMON_OBJECTTYPE)) {
			// if (!(value instanceof String)) {
			// throw new IllegalArgumentException(value.toString());
			// }
			// businessClass.setStereotype(value.toString());
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_COMMON_DISPLAYNAME)) {
			if (!(value instanceof String)) {
				throw new IllegalArgumentException(value.toString());
			}
			if (!ValidatorUtil.valiDisplayName(value.toString())) {

			} else if (!ValidatorUtil.valiDisplayNameLength(value.toString())) {

			} else {
				businessClass.setDisplayName(value.toString());
			}
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_BUSINESSCLASS_ISABSTRACT)) {
			// if (!(value instanceof String)) {
			// throw new IllegalArgumentException(value.toString());
			// }
			// businessClass.)(value.toString());
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_BUSINESSCLASS_TABLENAME)) {
			if (!(value instanceof String)) {
				throw new IllegalArgumentException(value.toString());
			}
			if (!ValidatorUtil.valiFirstNo_Name(value.toString())) {

			} else if (CheckerUtil.checkSql(value.toString())) {

			} else if (!ValidatorUtil.valiNameLength(value.toString())) {

			} else {
				int i = 0;
				if (businessClass.getParent() != null) {
					for (BusinessClass bu : manager.getBusinessObjectModel()
							.getBusinessClasses()) {
						if (bu.getTableName().equals(value.toString())
								&& !bu.getId().equals(businessClass.getId()))
							i++;
					}
				}
				if (i < 1) {
					businessClass.setTableName(value.toString());
				}
			}
		} /*
		 * else if (propertyId.equals(DETAIL_PROP)) { if (value instanceof
		 * Integer) { int i = ((Integer) value).intValue(); // details = i; }
		 * else { throw new IllegalArgumentException(propertyId.toString()); } }
		 */else {
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
			businessClass.setId("11111");
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
