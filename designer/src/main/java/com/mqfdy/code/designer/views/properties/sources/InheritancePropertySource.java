package com.mqfdy.code.designer.views.properties.sources;

import com.mqfdy.code.model.AbstractModelElement;
import com.mqfdy.code.model.Inheritance;

// TODO: Auto-generated Javadoc
/**
 * 继承关系InheritancePropertySource.
 *
 * @author mqfdy
 */
public class InheritancePropertySource extends ModelPropertySource {
	
	/** The inheritance. */
	public Inheritance inheritance;

	/**
	 * Instantiates a new inheritance property source.
	 *
	 * @param inheritance
	 *            the inheritance
	 */
	public InheritancePropertySource(AbstractModelElement inheritance) {
		super();
		this.inheritance = (Inheritance) inheritance;
		initializeDescriptors();
		if (super.getModelProperties().isEmpty()) {
			installModelProperty();
		}
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
		addButtonModelProperty(
				IBusinessModelPropertyNames.PROPERTY_INHERITANCE_CHILDCLASS,
				IBusinessModelPropertyNames.CATEGORY_BASE, "", "Inheritance",
				null, "06");
		addButtonModelProperty(
				IBusinessModelPropertyNames.PROPERTY_INHERITANCE_PARENTCLASS,
				IBusinessModelPropertyNames.CATEGORY_BASE, "", "Inheritance",
				null, "07");
		addListModelProperty(
				IBusinessModelPropertyNames.PROPERTY_INHERITANCE_PERSISTENCEPLOY,
				IBusinessModelPropertyNames.CATEGORY_BASE, new String[] {
						"父子实体类生成同一张表", "父类和子类各生成表" }, "08");
		// addButtonModelProperty(IBusinessModelPropertyNames.PROPERTY_INHERITANCE_PERSITENCEPLOYPARAMS,IBusinessModelPropertyNames.CATEGORY_INHERITANCE,"","Inheritance");
		// /MAP
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
			return inheritance.getId();
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_COMMON_DISPLAYNAME)) {
			return inheritance.getDisplayName();
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_COMMON_NAME)) {
			return inheritance.getName();
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_COMMON_REMARK)) {
			return inheritance.getRemark();
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_COMMON_OBJECTTYPE)) {
			return inheritance.getDisplayType();
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_INHERITANCE_CHILDCLASS)) {
			return inheritance.getChildClass() == null ? "" : inheritance
					.getChildClass().getDisplayName();
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_INHERITANCE_PARENTCLASS)) {
			return inheritance.getParentClass() == null ? "" : inheritance
					.getParentClass().getDisplayName();
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_INHERITANCE_PERSISTENCEPLOY)) {

			// "父子实体类生成同一张表","父类和子类各生成表"
			if (inheritance.getPersistencePloy().equals("1"))
				return "父子实体类生成同一张表";
			if (inheritance.getPersistencePloy().equals("2"))
				return "父类和子类各生成表";
			return "";
			// return inheritance.getPersistencePloy();
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_INHERITANCE_PERSITENCEPLOYPARAMS)) {
			return inheritance.getPersistencePloyParams();
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
		if (propertyId.equals(IBusinessModelPropertyNames.PROPERTY_COMMON_ID)) {
			if (!(value instanceof String)) {
				throw new IllegalArgumentException(value.toString());
			}
			inheritance.setId(value.toString());
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_COMMON_DISPLAYNAME)) {
			if (!(value instanceof String)) {
				throw new IllegalArgumentException(value.toString());
			}
			inheritance.setDisplayName(value.toString());
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_COMMON_NAME)) {
			if (!(value instanceof String)) {
				throw new IllegalArgumentException(value.toString());
			}
			inheritance.setName(value.toString());
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_COMMON_REMARK)) {
			if (!(value instanceof String)) {
				throw new IllegalArgumentException(value.toString());
			}
			inheritance.setRemark(value.toString());
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_COMMON_OBJECTTYPE)) {
			// if (!(value instanceof String)) {
			// throw new IllegalArgumentException(value.toString());
			// }
			// inheritance.setStereotype(value.toString());
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_INHERITANCE_CHILDCLASS)) {
			if (value != null
					&& !(value instanceof BusinessClassPropertySource)) {
				throw new IllegalArgumentException(value.toString());
			}
			if (value != null)
				inheritance
						.setChildClass(((BusinessClassPropertySource) value).businessClass);
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_INHERITANCE_PARENTCLASS)) {
			if (value != null
					&& !(value instanceof BusinessClassPropertySource)) {
				throw new IllegalArgumentException(value.toString());
			}
			if (value != null)
				inheritance
						.setParentClass(((BusinessClassPropertySource) value).businessClass);
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_INHERITANCE_PERSISTENCEPLOY)) {
			if (!(value instanceof String)) {
				throw new IllegalArgumentException(value.toString());
			}
			if (value.toString().equals("父子实体类生成同一张表"))
				inheritance.setPersistencePloy("1");
			if (value.toString().equals("父类和子类各生成表"))
				inheritance.setPersistencePloy("2");

			// inheritance.setPersistencePloy(value.toString());
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_INHERITANCE_PERSITENCEPLOYPARAMS)) {
			if (!(value instanceof String)) {
				throw new IllegalArgumentException(value.toString());
			}
			// inheritance.setPersistencePloyParams((Map<String, String>)
			// value);
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
		/*
		 * if (propertyId.equals("bbb")) { setId("11111"); } else if
		 * (propertyId.equals(DETAIL_PROP)) { // details = 0; }else { throw new
		 * IllegalArgumentException(); }
		 */
	}

	/**
	 * @return
	 */
	public Object getEditableValue() {
		return this;
	}
}
