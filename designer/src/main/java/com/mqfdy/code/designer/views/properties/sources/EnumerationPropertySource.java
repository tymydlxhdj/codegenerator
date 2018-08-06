package com.mqfdy.code.designer.views.properties.sources;

import com.mqfdy.code.designer.models.BusinessModelManager;
import com.mqfdy.code.designer.utils.BusinessModelUtil;
import com.mqfdy.code.designer.utils.CheckerUtil;
import com.mqfdy.code.model.AbstractModelElement;
import com.mqfdy.code.model.Enumeration;
import com.mqfdy.code.resource.validator.ValidatorUtil;


/**
 * 枚举PropertyPropertySource
 * 
 * @author mqfdy
 * 
 */
public class EnumerationPropertySource extends ModelPropertySource {
	public Enumeration enumeration;
	private BusinessModelManager manager = BusinessModelUtil
			.getEditorBusinessModelManager();

	public EnumerationPropertySource(AbstractModelElement property) {
		super();
		this.enumeration = (Enumeration) property;
		initializeDescriptors();
		// if (super.getModelProperties().isEmpty()) {
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
				IBusinessModelPropertyNames.PROPERTY_COMMON_NAME,
				IBusinessModelPropertyNames.CATEGORY_BASE, "", "03");
		addStringModelProperty(
				IBusinessModelPropertyNames.PROPERTY_COMMON_DISPLAYNAME,
				IBusinessModelPropertyNames.CATEGORY_BASE, "", "04");
		addStringModelProperty(
				IBusinessModelPropertyNames.PROPERTY_COMMON_REMARK,
				IBusinessModelPropertyNames.CATEGORY_BASE, "", "05");
		// addButtonModelProperty(IBusinessModelPropertyNames.PROPERTY_ENUMERARION_VALUES,IBusinessModelPropertyNames.CATEGORY_ENUMERARION,"","Enumeration","06");
		addStringModelProperty(
				IBusinessModelPropertyNames.PROPERTY_VERSION_VERSIONNUM,
				IBusinessModelPropertyNames.CATEGORY_VERSION, "", true, "07");
		addStringModelProperty(
				IBusinessModelPropertyNames.PROPERTY_VERSION_CREATER,
				IBusinessModelPropertyNames.CATEGORY_VERSION, "", true, "08");
		addStringModelProperty(
				IBusinessModelPropertyNames.PROPERTY_VERSION_CREATETIME,
				IBusinessModelPropertyNames.CATEGORY_VERSION, "", true, "09");
		addStringModelProperty(
				IBusinessModelPropertyNames.PROPERTY_VERSION_UPDATER,
				IBusinessModelPropertyNames.CATEGORY_VERSION, "", true, "10");
		addStringModelProperty(
				IBusinessModelPropertyNames.PROPERTY_VERSION_UPDATETIME,
				IBusinessModelPropertyNames.CATEGORY_VERSION, "", true, "11");
	}

	public Object getPropertyValue(Object propertyId) {
		if (!(propertyId instanceof String)) {
			throw new IllegalArgumentException();
		}
		if (propertyId.equals(IBusinessModelPropertyNames.PROPERTY_COMMON_ID)) {
			return enumeration.getId();
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_COMMON_NAME)) {
			return enumeration.getName();
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_COMMON_REMARK)) {
			return enumeration.getRemark();
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_COMMON_DISPLAYNAME)) {
			return enumeration.getDisplayName();
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_COMMON_OBJECTTYPE)) {
			return enumeration.getDisplayType();
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_VERSION_VERSIONNUM)) {
			return enumeration.getVersionInfo() == null ? "" : enumeration
					.getVersionInfo().getVersionNumber();
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_VERSION_CREATER)) {
			return enumeration.getVersionInfo() == null ? "" : enumeration
					.getVersionInfo().getCreator();
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_VERSION_CREATETIME)) {
			return enumeration.getVersionInfo() == null ? "" : enumeration
					.getVersionInfo().getCreatedTime();
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_VERSION_UPDATER)) {
			return enumeration.getVersionInfo() == null ? "" : enumeration
					.getVersionInfo().getModifier();
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_VERSION_UPDATETIME)) {
			return enumeration.getVersionInfo() == null ? "" : enumeration
					.getVersionInfo().getChangedTime();
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
			enumeration.setId(value.toString());
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_COMMON_NAME)) {
			if (!(value instanceof String)) {
				throw new IllegalArgumentException(value.toString());
			}
			if (!ValidatorUtil.valiName(value.toString())) {

			} else if (!ValidatorUtil.valiNameLength(value.toString())) {

			} else if (!ValidatorUtil.isFirstUppercase(value.toString())) {

			} else if (CheckerUtil.checkJava(value.toString())) {

			} else if (CheckerUtil.checkSguap(value.toString())) {

			} else {
				int i = 0;
				if (enumeration.getParent() != null) {
					for (Enumeration bu : manager.getBusinessObjectModel()
							.getEnumerations()) {
						if (bu.getName().equals(value.toString())
								&& !bu.getId().equals(enumeration.getId()))
							i++;
					}
				}
				if (i < 1) {
					enumeration.setName(value.toString());
				}
			}
			// enumeration.setName(value.toString());
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_COMMON_REMARK)) {
			if (!(value instanceof String)) {
				throw new IllegalArgumentException(value.toString());
			}
			if (!ValidatorUtil.valiRemarkLength(value.toString())) {
				enumeration.setRemark(value.toString());
			}
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_COMMON_OBJECTTYPE)) {
			// if (!(value instanceof String)) {
			// throw new IllegalArgumentException(value.toString());
			// }
			// enumeration.setStereotype(value.toString());
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_COMMON_DISPLAYNAME)) {
			if (!(value instanceof String)) {
				throw new IllegalArgumentException(value.toString());
			}
			if (!ValidatorUtil.valiDisplayName(value.toString())) {

			} else if (!ValidatorUtil.valiDisplayNameLength(value.toString())) {

			} else {
				enumeration.setDisplayName(value.toString());
			}
		} else {
			// throw new IllegalArgumentException(propertyId.toString());
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
			enumeration.setId("11111");
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