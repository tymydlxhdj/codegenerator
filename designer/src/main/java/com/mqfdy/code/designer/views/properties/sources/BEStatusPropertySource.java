package com.mqfdy.code.designer.views.properties.sources;

import com.mqfdy.code.model.AbstractModelElement;
import com.mqfdy.code.model.BEStatus;

/**
 * 状态PropertyPropertySource
 * 
 * @author mqfdy
 * 
 */
public class BEStatusPropertySource extends ModelPropertySource {
	public BEStatus status;

	public BEStatusPropertySource(AbstractModelElement property) {
		super();
		this.status = (BEStatus) property;
		initializeDescriptors();
		// if (super.getModelProperties().isEmpty()) {
		installModelProperty();
		// }
	}

	protected void installModelProperty() {
		if (this.status.isNoneStatus()) {
			addStringModelProperty(
					IBusinessModelPropertyNames.PROPERTY_COMMON_ID,
					IBusinessModelPropertyNames.CATEGORY_BASE, "", true, "01");
			addStringModelProperty(
					IBusinessModelPropertyNames.PROPERTY_COMMON_OBJECTTYPE,
					IBusinessModelPropertyNames.CATEGORY_BASE, "", true, "02");
			addStringModelProperty(
					IBusinessModelPropertyNames.PROPERTY_COMMON_NAME,
					IBusinessModelPropertyNames.CATEGORY_BASE, "", true, "03");
			addStringModelProperty(
					IBusinessModelPropertyNames.PROPERTY_COMMON_DISPLAYNAME,
					IBusinessModelPropertyNames.CATEGORY_BASE, "", true, "04");
			addStringModelProperty(
					IBusinessModelPropertyNames.PROPERTY_COMMON_REMARK,
					IBusinessModelPropertyNames.CATEGORY_BASE, "", true, "05");
		} else {
			addStringModelProperty(
					IBusinessModelPropertyNames.PROPERTY_COMMON_ID,
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
		}
	}

	public Object getPropertyValue(Object propertyId) {
		if (!(propertyId instanceof String)) {
			throw new IllegalArgumentException();
		}
		/*
		 * if (propertyId.equals(NAME_PROP)) { return getName(); } else if
		 * (propertyId.equals(DETAIL_PROP)) { return Integer.valueOf(details);
		 * }else{return getID();}
		 */
		if (propertyId.equals(IBusinessModelPropertyNames.PROPERTY_COMMON_ID)) {
			return status.getId();
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_COMMON_NAME)) {
			return status.getName();
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_COMMON_REMARK)) {
			return status.getRemark();
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_COMMON_DISPLAYNAME)) {
			return status.getDisplayName();
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_COMMON_OBJECTTYPE)) {
			return status.getDisplayType();
			// }else if
			// (propertyId.equals(IBusinessModelPropertyNames.PROPERTY_ENUMERARION_VALUES))
			// {
			// return status.getValues();
		}/*
		 * else if
		 * (propertyId.equals(IBusinessModelPropertyNames.PROPERTY_PROPERTY_DATATYPE
		 * )) { return status.getDataType(); }else if
		 * (propertyId.equals(IBusinessModelPropertyNames
		 * .PROPERTY_PROPERTY_GROUP)) { return status.getGroup(); }else if
		 * (propertyId
		 * .equals(IBusinessModelPropertyNames.PROPERTY_PROPERTY_ORDERNUM)) {
		 * return status.getOrderNum(); }
		 */else
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
			status.setId(value.toString());
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_COMMON_NAME)) {
			if (!(value instanceof String)) {
				throw new IllegalArgumentException(value.toString());
			}
			status.setName(value.toString());
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_COMMON_REMARK)) {
			if (!(value instanceof String)) {
				throw new IllegalArgumentException(value.toString());
			}
			status.setRemark(value.toString());
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_COMMON_OBJECTTYPE)) {
			// if (!(value instanceof String)) {
			// throw new IllegalArgumentException(value.toString());
			// }
			// status.setStereotype(value.toString());
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_COMMON_DISPLAYNAME)) {
			if (!(value instanceof String)) {
				throw new IllegalArgumentException(value.toString());
			}
			status.setDisplayName(value.toString());
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
			status.setId("11111");
		} /*
		 * else if (propertyId.equals(DETAIL_PROP)) { // details = 0; }
		 */else {
			throw new IllegalArgumentException();
		}
	}

	public Object getEditableValue() {
		return this;
	}

	@Override
	protected void initializeDescriptors() {
		// TODO Auto-generated method stub

	}
}
