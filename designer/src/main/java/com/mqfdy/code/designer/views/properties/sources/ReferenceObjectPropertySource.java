package com.mqfdy.code.designer.views.properties.sources;

import com.mqfdy.code.designer.utils.CheckerUtil;
import com.mqfdy.code.model.AbstractModelElement;
import com.mqfdy.code.model.ReferenceObject;
import com.mqfdy.code.resource.validator.ValidatorUtil;


// TODO: Auto-generated Javadoc
/**
 * 引用模型PropertyPropertySource.
 *
 * @author mqfdy
 */
public class ReferenceObjectPropertySource extends ModelPropertySource {
	
	/** The reference object. */
	public ReferenceObject referenceObject;

	/**
	 * Instantiates a new reference object property source.
	 *
	 * @param property
	 *            the property
	 */
	public ReferenceObjectPropertySource(AbstractModelElement property) {
		super();
		this.referenceObject = (ReferenceObject) property;
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
				IBusinessModelPropertyNames.CATEGORY_BASE, "", true, "03");
		addStringModelProperty(
				IBusinessModelPropertyNames.PROPERTY_COMMON_DISPLAYNAME,
				IBusinessModelPropertyNames.CATEGORY_BASE, "", true, "04");
		addStringModelProperty(
				IBusinessModelPropertyNames.REFERENCEOBJECT_REFERENCEMODELID,
				IBusinessModelPropertyNames.CATEGORY_BASE, "", true, "05");
		addStringModelProperty(
				IBusinessModelPropertyNames.REFERENCEOBJECT_REFERENCEOBJECTID,
				IBusinessModelPropertyNames.CATEGORY_BASE, "", true, "06");
		addStringModelProperty(
				IBusinessModelPropertyNames.REFERENCEOBJECT_REFERENCEMODEPATH,
				IBusinessModelPropertyNames.CATEGORY_BASE, "", true, "07");
		// addStringModelProperty(IBusinessModelPropertyNames.PROPERTY_COMMON_REMARK,IBusinessModelPropertyNames.CATEGORY_BASE,"","08");
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
		if (propertyId.equals(IBusinessModelPropertyNames.PROPERTY_COMMON_ID)) {
			return referenceObject.getId();
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_COMMON_NAME)) {
			return referenceObject.getReferenceObject().getName();
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_COMMON_REMARK)) {
			return referenceObject.getRemark();
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.REFERENCEOBJECT_REFERENCEMODEPATH)) {
			return referenceObject.getReferenceModePath();
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.REFERENCEOBJECT_REFERENCEOBJECTID)) {
			return referenceObject.getReferenceObjectId();
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.REFERENCEOBJECT_REFERENCEMODELID)) {
			return referenceObject.getReferenceModelId();
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_COMMON_DISPLAYNAME)) {
			return referenceObject.getReferenceObject().getDisplayName();
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_COMMON_OBJECTTYPE)) {
			return referenceObject.getDisplayType();
		}/*
		 * else if (propertyId.equals(IBusinessModelPropertyNames.
		 * PROPERTY_ENUMERARION_VALUES)) { return referenceObject.getValues();
		 * }else if
		 * (propertyId.equals(IBusinessModelPropertyNames.PROPERTY_PROPERTY_DATATYPE
		 * )) { return referenceObject.getDataType(); }else if
		 * (propertyId.equals
		 * (IBusinessModelPropertyNames.PROPERTY_PROPERTY_GROUP)) { return
		 * referenceObject.getGroup(); }else if
		 * (propertyId.equals(IBusinessModelPropertyNames
		 * .PROPERTY_PROPERTY_ORDERNUM)) { return referenceObject.getOrderNum();
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
			referenceObject.setId(value.toString());
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
				referenceObject.setName(value.toString());
			}
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_COMMON_REMARK)) {
			if (!(value instanceof String)) {
				throw new IllegalArgumentException(value.toString());
			}
			if (!ValidatorUtil.valiRemarkLength(value.toString())) {
				referenceObject.setRemark(value.toString());
			}
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_COMMON_OBJECTTYPE)) {
			// if (!(value instanceof String)) {
			// throw new IllegalArgumentException(value.toString());
			// }
			// referenceObject.setStereotype(value.toString());
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_COMMON_DISPLAYNAME)) {
			if (!(value instanceof String)) {
				throw new IllegalArgumentException(value.toString());
			}
			if (!ValidatorUtil.valiDisplayName(value.toString())) {

			} else if (!ValidatorUtil.valiDisplayNameLength(value.toString())) {

			} else {
				referenceObject.setDisplayName(value.toString());
			}
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_ENUMERARION_VALUES)) {
			// if (!(value instanceof String)) {
			// throw new IllegalArgumentException(value.toString());
			// }
			// referenceObject.setDisplayName(value.toString());
		}/*
		 * else if
		 * (propertyId.equals(IBusinessModelPropertyNames.PROPERTY_PROPERTY_DATATYPE
		 * )) { if (!(value instanceof String)) { throw new
		 * IllegalArgumentException(value.toString()); }
		 * referenceObject.setDataType(value.toString()); } else if
		 * (propertyId.equals
		 * (IBusinessModelPropertyNames.PROPERTY_PROPERTY_GROUP)) { if (!(value
		 * instanceof PropertyGroup)) { throw new
		 * IllegalArgumentException(value.toString()); }
		 * referenceObject.setGroup((PropertyGroup) value); } else if
		 * (propertyId
		 * .equals(IBusinessModelPropertyNames.PROPERTY_PROPERTY_ORDERNUM)) { if
		 * (!(value instanceof Integer)) { throw new
		 * IllegalArgumentException(value.toString()); }
		 * referenceObject.setOrderNum(Integer.parseInt(value.toString())); }
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
			referenceObject.setId("11111");
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
