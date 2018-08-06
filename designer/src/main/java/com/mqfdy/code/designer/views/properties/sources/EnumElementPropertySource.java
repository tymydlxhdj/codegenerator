package com.mqfdy.code.designer.views.properties.sources;

import com.mqfdy.code.model.AbstractModelElement;
import com.mqfdy.code.model.EnumElement;

/**
 * 业务实体PropertyPropertySource
 * 
 * @author mqfdy
 * 
 */
public class EnumElementPropertySource extends ModelPropertySource {
	public EnumElement enumElement;

	public EnumElementPropertySource(AbstractModelElement property) {
		super();
		this.enumElement = (EnumElement) property;
		initializeDescriptors();
		// if (super.getModelProperties().isEmpty()) {
		installModelProperty();
		// }
	}

	protected void initializeDescriptors() {
	}

	protected void installModelProperty() {
		addStringModelProperty(
				IBusinessModelPropertyNames.PROPERTY_ENUMELEMENT_KEY,
				IBusinessModelPropertyNames.CATEGORY_BASE, "", true, "01");
		addStringModelProperty(
				IBusinessModelPropertyNames.PROPERTY_ENUMELEMENT_VALUE,
				IBusinessModelPropertyNames.CATEGORY_BASE, "", "02");
		// addStringModelProperty(IBusinessModelPropertyNames.PROPERTY_COMMON_ID,IBusinessModelPropertyNames.CATEGORY_BASE,"",true,"01");
		// addStringModelProperty(IBusinessModelPropertyNames.PROPERTY_COMMON_STEREOTYPE,IBusinessModelPropertyNames.CATEGORY_BASE,"",true,"02");
		// addStringModelProperty(IBusinessModelPropertyNames.PROPERTY_COMMON_NAME,IBusinessModelPropertyNames.CATEGORY_BASE,"","03");
		// addStringModelProperty(IBusinessModelPropertyNames.PROPERTY_COMMON_DISPLAYNAME,IBusinessModelPropertyNames.CATEGORY_BASE,"","04");
		// addStringModelProperty(IBusinessModelPropertyNames.PROPERTY_COMMON_REMARK,IBusinessModelPropertyNames.CATEGORY_BASE,"","05");
		// addButtonModelProperty(IBusinessModelPropertyNames.PROPERTY_ENUMERARION_VALUES,IBusinessModelPropertyNames.CATEGORY_ENUMERARION,"","EnumElement","06");
	}

	public Object getPropertyValue(Object propertyId) {
		if (!(propertyId instanceof String)) {
			throw new IllegalArgumentException();
		}
		if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_ENUMELEMENT_KEY)) {
			return enumElement.getKey();
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_ENUMELEMENT_VALUE)) {
			return enumElement.getValue();
		} else
			return "";
	}

	public void setPropertyValue(Object propertyId, Object value) {
		if (!(propertyId instanceof String)) {
			throw new IllegalArgumentException(propertyId.toString());
		}
		if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_ENUMELEMENT_KEY)) {
			// if (!(value instanceof String)) {
			// throw new IllegalArgumentException(value.toString());
			// }
			// enumElement.setId(value.toString());
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_ENUMELEMENT_VALUE)) {
			if (!(value instanceof String)) {
				throw new IllegalArgumentException(value.toString());
			}
			enumElement.setValue(value.toString());
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
			enumElement.setId("11111");
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
