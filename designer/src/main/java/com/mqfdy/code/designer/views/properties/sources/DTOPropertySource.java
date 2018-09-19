package com.mqfdy.code.designer.views.properties.sources;

import com.mqfdy.code.model.AbstractModelElement;
import com.mqfdy.code.model.DTOProperty;
import com.mqfdy.code.model.utils.DataType;

// TODO: Auto-generated Javadoc
/**
 * DTO PropertyPropertySource.
 *
 * @author mqfdy
 */
public class DTOPropertySource extends ModelPropertySource {
	
	/** The dto property. */
	public DTOProperty dtoProperty;

	/**
	 * Instantiates a new DTO property source.
	 *
	 * @param property
	 *            the property
	 */
	public DTOPropertySource(AbstractModelElement property) {
		super();
		this.dtoProperty = (DTOProperty) property;
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
				IBusinessModelPropertyNames.CATEGORY_BASE, "", "09");
		addListModelProperty(
				IBusinessModelPropertyNames.PROPERTY_PROPERTY_DATATYPE,
				IBusinessModelPropertyNames.CATEGORY_BASE,
				DataType.getDataTypesString(), "06");
		addStringModelProperty(
				IBusinessModelPropertyNames.PROPERTY_PROPERTY_DEFAULTVALUE,
				IBusinessModelPropertyNames.CATEGORY_BASE, "", "07");
		addButtonModelProperty(
				IBusinessModelPropertyNames.PROPERTY_PROPERTY_VALIDATORS,
				IBusinessModelPropertyNames.CATEGORY_BASE, "", "DTOProperty",
				dtoProperty, "08");
		// addButtonModelProperty(IBusinessModelPropertyNames.PROPERTY_ENUMERARION_VALUES,IBusinessModelPropertyNames.CATEGORY_ENUMERARION,"");
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
		/*
		 * if (propertyId.equals(NAME_PROP)) { return getName(); } else if
		 * (propertyId.equals(DETAIL_PROP)) { return Integer.valueOf(details);
		 * }else{return getID();}
		 */
		if (propertyId.equals(IBusinessModelPropertyNames.PROPERTY_COMMON_ID)) {
			return dtoProperty.getId();
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_COMMON_NAME)) {
			return dtoProperty.getName();
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_COMMON_REMARK)) {
			return dtoProperty.getRemark();
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_COMMON_DISPLAYNAME)) {
			return dtoProperty.getDisplayName();
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_COMMON_OBJECTTYPE)) {
			return dtoProperty.getDisplayType();
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_PROPERTY_DATATYPE)) {
			if (dtoProperty.getDataType() == null)
				return "";
			if (DataType.getDataType(dtoProperty.getDataType()) == null)
				return "";
			return DataType.getDataType(dtoProperty.getDataType())
					.getValue_hibernet();
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_PROPERTY_DEFAULTVALUE)) {
			return dtoProperty.getDisplayType();
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_PROPERTY_VALIDATORS)) {
			return "";// / dtoProperty.getValidators();
		}/*
		 * else if (propertyId.equals(IBusinessModelPropertyNames.
		 * PROPERTY_ENUMERARION_VALUES)) { return dtoProperty.getValues(); }
		 *//*
			 * else if (propertyId.equals(IBusinessModelPropertyNames.
			 * PROPERTY_PROPERTY_DATATYPE)) { return dtoProperty.getDataType();
			 * }else if (propertyId.equals(IBusinessModelPropertyNames.
			 * PROPERTY_PROPERTY_GROUP)) { return dtoProperty.getGroup(); }else
			 * if (propertyId.equals(IBusinessModelPropertyNames.
			 * PROPERTY_PROPERTY_ORDERNUM)) { return dtoProperty.getOrderNum();
			 * }
			 */else
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
		if (propertyId.equals(IBusinessModelPropertyNames.PROPERTY_COMMON_ID)) {
			if (!(value instanceof String)) {
				throw new IllegalArgumentException(value.toString());
			}
			dtoProperty.setId(value.toString());
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_COMMON_NAME)) {
			if (!(value instanceof String)) {
				throw new IllegalArgumentException(value.toString());
			}
			dtoProperty.setName(value.toString());
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_COMMON_REMARK)) {
			if (!(value instanceof String)) {
				throw new IllegalArgumentException(value.toString());
			}
			dtoProperty.setRemark(value.toString());
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_COMMON_OBJECTTYPE)) {
			// if (!(value instanceof String)) {
			// throw new IllegalArgumentException(value.toString());
			// }
			// dtoProperty.setStereotype(value.toString());
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_COMMON_DISPLAYNAME)) {
			if (!(value instanceof String)) {
				throw new IllegalArgumentException(value.toString());
			}
			dtoProperty.setDisplayName(value.toString());
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_PROPERTY_DATATYPE)) {
			if (!(value instanceof String)) {
				throw new IllegalArgumentException(value.toString());
			}
			dtoProperty.setDataType(value.toString());
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_PROPERTY_DEFAULTVALUE)) {
			if (!(value instanceof String)) {
				throw new IllegalArgumentException(value.toString());
			}
			// dtoProperty.setDataType(value.toString());
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_PROPERTY_VALIDATORS)) {
			if (!(value instanceof DTOPropertySource)) {
				// throw new IllegalArgumentException("");
			} else {
				dtoProperty = ((DTOPropertySource) value).dtoProperty;
			}
			// BusinessModelManager businessModelManager =
			// BusinessModelUtil.getEditorBusinessModelManager();
			// BusinessModelEvent businessModelEvent = new
			// BusinessModelEvent(BusinessModelEvent.MODEL_ELEMENT_UPDATE,
			// dtoProperty);
			// businessModelManager.businessObjectModelChanged(businessModelEvent);

			// dtoProperty.setValidators((List<Validator>) value);
		}/*
		 * else if
		 * (propertyId.equals(IBusinessModelPropertyNames.PROPERTY_PROPERTY_DATATYPE
		 * )) { if (!(value instanceof String)) { throw new
		 * IllegalArgumentException(value.toString()); }
		 * dtoProperty.setDataType(value.toString()); } else if
		 * (propertyId.equals
		 * (IBusinessModelPropertyNames.PROPERTY_PROPERTY_GROUP)) { if (!(value
		 * instanceof PropertyGroup)) { throw new
		 * IllegalArgumentException(value.toString()); }
		 * dtoProperty.setGroup((PropertyGroup) value); } else if
		 * (propertyId.equals
		 * (IBusinessModelPropertyNames.PROPERTY_PROPERTY_ORDERNUM)) { if
		 * (!(value instanceof Integer)) { throw new
		 * IllegalArgumentException(value.toString()); }
		 * dtoProperty.setOrderNum(Integer.parseInt(value.toString())); }
		 */else {
			// throw new IllegalArgumentException(propertyId.toString());
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
			dtoProperty.setId("11111");
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
