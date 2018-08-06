package com.mqfdy.code.designer.views.properties.sources;

import com.mqfdy.code.model.AbstractModelElement;
import com.mqfdy.code.model.graph.DiagramElement;

/**
 * 图元PropertySource
 * 
 * @author mqfdy
 * 
 */
public class DiagramElementSource extends ModelPropertySource {
	public DiagramElement association;

	public DiagramElementSource(AbstractModelElement association) {
		super();
		this.association = (DiagramElement) association;
		if (super.getModelProperties().isEmpty()) {
			installModelProperty();
		}
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
				IBusinessModelPropertyNames.CATEGORY_BASE, "", "31");

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
			association.setDisplayName(value.toString());
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_COMMON_NAME)) {
			if (!(value instanceof String)) {
				throw new IllegalArgumentException(value.toString());
			}
			association.setName(value.toString());
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_COMMON_REMARK)) {
			if (!(value instanceof String)) {
				throw new IllegalArgumentException(value.toString());
			}
			association.setRemark(value.toString());
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_COMMON_OBJECTTYPE)) {
			// if (!(value instanceof String)) {
			// throw new IllegalArgumentException(value.toString());
			// }
			// association.setStereotype(value.toString());
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
