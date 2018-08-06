package com.mqfdy.code.designer.views.properties.sources;

import com.mqfdy.code.model.AbstractModelElement;
import com.mqfdy.code.model.DataTransferObject;

/**
 * DTO属性 PropertyPropertySource
 * 
 * @author mqfdy
 * 
 */
public class DataTransferObjectPropertySource extends ModelPropertySource {
	public DataTransferObject dto;

	public DataTransferObjectPropertySource(AbstractModelElement property) {
		super();
		this.dto = (DataTransferObject) property;
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
		// addButtonModelProperty(IBusinessModelPropertyNames.PROPERTY_ENUMERARION_VALUES,IBusinessModelPropertyNames.CATEGORY_ENUMERARION,"");
		addStringModelProperty(
				IBusinessModelPropertyNames.PROPERTY_VERSION_VERSIONNUM,
				IBusinessModelPropertyNames.CATEGORY_VERSION, "", true, "09");
		addStringModelProperty(
				IBusinessModelPropertyNames.PROPERTY_VERSION_CREATER,
				IBusinessModelPropertyNames.CATEGORY_VERSION, "", true, "10");
		addStringModelProperty(
				IBusinessModelPropertyNames.PROPERTY_VERSION_CREATETIME,
				IBusinessModelPropertyNames.CATEGORY_VERSION, "", true, "11");
		addStringModelProperty(
				IBusinessModelPropertyNames.PROPERTY_VERSION_UPDATER,
				IBusinessModelPropertyNames.CATEGORY_VERSION, "", true, "12");
		addStringModelProperty(
				IBusinessModelPropertyNames.PROPERTY_VERSION_UPDATETIME,
				IBusinessModelPropertyNames.CATEGORY_VERSION, "", true, "13");
	}

	public Object getPropertyValue(Object propertyId) {
		if (!(propertyId instanceof String)) {
			throw new IllegalArgumentException();
		}
		if (propertyId.equals(IBusinessModelPropertyNames.PROPERTY_COMMON_ID)) {
			return dto.getId();
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_COMMON_NAME)) {
			return dto.getName();
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_COMMON_REMARK)) {
			return dto.getRemark();
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_COMMON_DISPLAYNAME)) {
			return dto.getDisplayName();
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_COMMON_OBJECTTYPE)) {
			return dto.getDisplayType();
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_VERSION_VERSIONNUM)) {
			return dto.getVersionInfo() == null ? "" : dto.getVersionInfo()
					.getVersionNumber();
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_VERSION_CREATER)) {
			return dto.getVersionInfo() == null ? "" : dto.getVersionInfo()
					.getCreator();
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_VERSION_CREATETIME)) {
			return dto.getVersionInfo() == null ? "" : dto.getVersionInfo()
					.getCreatedTime();
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_VERSION_UPDATER)) {
			return dto.getVersionInfo() == null ? "" : dto.getVersionInfo()
					.getModifier();
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_VERSION_UPDATETIME)) {
			return dto.getVersionInfo() == null ? "" : dto.getVersionInfo()
					.getChangedTime();
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
			dto.setId(value.toString());
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_COMMON_NAME)) {
			if (!(value instanceof String)) {
				throw new IllegalArgumentException(value.toString());
			}
			dto.setName(value.toString());
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_COMMON_REMARK)) {
			if (!(value instanceof String)) {
				throw new IllegalArgumentException(value.toString());
			}
			dto.setRemark(value.toString());
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_COMMON_OBJECTTYPE)) {
			// if (!(value instanceof String)) {
			// throw new IllegalArgumentException(value.toString());
			// }
			// dto.setStereotype(value.toString());
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_COMMON_DISPLAYNAME)) {
			if (!(value instanceof String)) {
				throw new IllegalArgumentException(value.toString());
			}
			dto.setDisplayName(value.toString());
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
			dto.setId("11111");
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
