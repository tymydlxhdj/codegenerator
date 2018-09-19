package com.mqfdy.code.designer.views.properties.sources;

import com.mqfdy.code.model.AbstractModelElement;
import com.mqfdy.code.model.EnumElement;

// TODO: Auto-generated Javadoc
/**
 * 业务实体PropertyPropertySource.
 *
 * @author mqfdy
 */
public class EnumElementPropertySource extends ModelPropertySource {
	
	/** The enum element. */
	public EnumElement enumElement;

	/**
	 * Instantiates a new enum element property source.
	 *
	 * @param property
	 *            the property
	 */
	public EnumElementPropertySource(AbstractModelElement property) {
		super();
		this.enumElement = (EnumElement) property;
		initializeDescriptors();
		// if (super.getModelProperties().isEmpty()) {
		installModelProperty();
		// }
	}

	/**
	 * 
	 */
	protected void initializeDescriptors() {
	}

	/**
	 * 
	 */
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

	/**
	 * Gets the property value.
	 *
	 * @author mqfdy
	 * @param propertyId
	 *            the property id
	 * @return the property value
	 * @Date 2018-09-03 09:00
	 */
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

	/**
	 * Sets the property value.
	 *
	 * @author mqfdy
	 * @param propertyId
	 *            the property id
	 * @param value
	 *            the value
	 * @Date 2018-09-03 09:00
	 */
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

	/**
	 * Checks if is property set.
	 *
	 * @author mqfdy
	 * @param propertyId
	 *            the property id
	 * @return true, if is property set
	 * @Date 2018-09-03 09:00
	 */
	public boolean isPropertySet(Object propertyId) {
		return true;
	}

	/**
	 * Reset property value.
	 *
	 * @author mqfdy
	 * @param propertyId
	 *            the property id
	 * @Date 2018-09-03 09:00
	 */
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

	/**
	 * @return
	 */
	public Object getEditableValue() {
		return this;
	}
}
