package com.mqfdy.code.designer.views.properties.sources;

import com.mqfdy.code.designer.utils.CheckerUtil;
import com.mqfdy.code.model.AbstractModelElement;
import com.mqfdy.code.model.BusinessClass;
import com.mqfdy.code.model.DataTransferObject;
import com.mqfdy.code.model.IModelElement;
import com.mqfdy.code.model.Property;
import com.mqfdy.code.model.PropertyGroup;
import com.mqfdy.code.resource.validator.ValidatorUtil;


// TODO: Auto-generated Javadoc
/**
 * 业务实体属性PropertyPropertySource.
 *
 * @author mqfdy
 */
public class BusinessPropertySource extends ModelPropertySource {
	
	/** The property. */
	public Property property;

	/**
	 * Instantiates a new business property source.
	 *
	 * @param property
	 *            the property
	 */
	public BusinessPropertySource(AbstractModelElement property) {
		super();
		this.property = (Property) property;
		initializeDescriptors();
		// if (super.getModelProperties().isEmpty()) {
		installModelProperty();
		// }
	}

	// protected void initializeDescriptors() {
	/**
	 * Gets the strings.
	 *
	 * @author mqfdy
	 * @return the strings
	 * @Date 2018-09-03 09:00
	 */
	// }
	protected String[] getStrings() {
		java.util.List<PropertyGroup> list = ((BusinessClass) property
				.getParent()).getGroups();
		if (list != null && list.size() > 0) {
			String[] s = new String[list.size()];
			for (int i = 0; i < list.size(); i++) {
				s[i] = list.get(i).getDisplayName();
			}
			return s;
		}
		return new String[] {};
	}

	/**
	 * Gets the group.
	 *
	 * @author mqfdy
	 * @param groupDisplay
	 *            the group display
	 * @return the group
	 * @Date 2018-09-03 09:00
	 */
	private PropertyGroup getGroup(String groupDisplay) {
		if (groupDisplay.trim().equals(""))
			return null;
		java.util.List<PropertyGroup> groups = ((BusinessClass) property
				.getParent()).getGroups();
		for (int i = 0; i < groups.size(); i++) {
			if (groupDisplay.equals(groups.get(i).getDisplayName())) {
				return groups.get(i);
			}
		}
		PropertyGroup newGroup = new PropertyGroup("", groupDisplay);
		groups.add(newGroup);
		return newGroup;
	}

	/**
	 * 
	 */
	protected void installModelProperty() {

		if (property.getStereotype() != null
				&& (IModelElement.STEREOTYPE_BUILDIN.equals(
						property.getStereotype()) || IModelElement.STEREOTYPE_REFERENCE.equals(
								property.getStereotype()))) {
			addStringModelProperty(
					IBusinessModelPropertyNames.PROPERTY_COMMON_ID,
					IBusinessModelPropertyNames.CATEGORY_BASE, "", true, "01");
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
					IBusinessModelPropertyNames.CATEGORY_BASE, "", true, "08");
			addStringModelProperty(
					IBusinessModelPropertyNames.PROPERTY_PROPERTY_DATATYPE,
					IBusinessModelPropertyNames.CATEGORY_BASE, "", true, "06");
			addListModelProperty(
					IBusinessModelPropertyNames.PROPERTY_PROPERTY_GROUP,
					IBusinessModelPropertyNames.CATEGORY_BASE, getStrings(),
					"07");
		} else {
			addStringModelProperty(
					IBusinessModelPropertyNames.PROPERTY_COMMON_ID,
					IBusinessModelPropertyNames.CATEGORY_BASE, "", true, "01");
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
					IBusinessModelPropertyNames.CATEGORY_BASE, "", "08");
			addStringModelProperty(
					IBusinessModelPropertyNames.PROPERTY_PROPERTY_DATATYPE,
					IBusinessModelPropertyNames.CATEGORY_BASE, "", "06");
			addListModelProperty(
					IBusinessModelPropertyNames.PROPERTY_PROPERTY_GROUP,
					IBusinessModelPropertyNames.CATEGORY_BASE, getStrings(),
					"07");
		}
		// addIntModelProperty(IBusinessModelPropertyNames.PROPERTY_PROPERTY_ORDERNUM,IBusinessModelPropertyNames.CATEGORY_PROPERTY,"08");
		// addBooleanModelProperty(IBusinessModelPropertyNames.PROPERTY_BUSINESSCLASS_ISABSTRACT,IBusinessModelPropertyNames.CATEGORY_BASE,true);
		// addBooleanModelProperty(IBusinessModelPropertyNames.PROPERTY_BUSINESSCLASS_ISCRIGHT,IBusinessModelPropertyNames.CATEGORY_BASE,true);
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
			return property.getId();
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_COMMON_NAME)) {
			return property.getName();
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_COMMON_REMARK)) {
			return property.getRemark() == null ? "" : property.getRemark();
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_COMMON_DISPLAYNAME)) {
			return property.getDisplayName();
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_COMMON_OBJECTTYPE)) {
			return property.getDisplayType();
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_COMMON_STEREOTYPETYPE)) {
			String stype = property.getStereotype();
			return AbstractModelElementPropertySource.getStereotype(stype);
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_PROPERTY_DATATYPE)) {
			return property.getDataType();
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_PROPERTY_GROUP)) {
			return property.getGroup() == null ? "" : property.getGroup()
					.getDisplayName();
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_PROPERTY_ORDERNUM)) {
			return property.getOrderNum();
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
			property.setId(value.toString());
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_COMMON_NAME)) {
			if (!(value instanceof String)) {
				throw new IllegalArgumentException(value.toString());
			}
			if (!ValidatorUtil.valiName(value.toString())) {

			} else if (!ValidatorUtil.valiNameLength(value.toString())) {

			} else if (!ValidatorUtil.isFirstLowercase(value.toString())) {

			} else if (CheckerUtil.checkJava(value.toString())) {

			} else {
				int i = 0;
				if (property.getParent() instanceof BusinessClass) {
					for (Property pro : ((BusinessClass) property.getParent())
							.getProperties()) {
						if (pro.getName().equals(value.toString())) {
							i++;
						}
					}
				} else if (property.getParent() instanceof DataTransferObject) {
					for (Property pro : ((DataTransferObject) property
							.getParent()).getProperties()) {
						if (pro.getName().equals(value.toString())) {
							i++;
						}
					}
				}
				if (i < 1) {
					property.setName(value.toString());
				}
			}
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_COMMON_REMARK)) {
			if (!(value instanceof String)) {
				throw new IllegalArgumentException(value.toString());
			}
			if (ValidatorUtil.valiRemarkLength(value.toString())) {
				property.setRemark(value.toString());
			}
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_COMMON_OBJECTTYPE)) {
			// if (!(value instanceof String)) {
			// throw new IllegalArgumentException(value.toString());
			// }
			// property.setStereotype(value.toString());
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_COMMON_DISPLAYNAME)) {
			if (!(value instanceof String)) {
				throw new IllegalArgumentException(value.toString());
			}
			if (!ValidatorUtil.valiDisplayName(value.toString())) {

			} else if (!ValidatorUtil.valiDisplayNameLength(value.toString())) {

			} else {
				property.setDisplayName(value.toString());
			}
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_PROPERTY_DATATYPE)) {
			if (!(value instanceof String)) {
				throw new IllegalArgumentException(value.toString());
			}
			property.setDataType(value.toString());
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_PROPERTY_GROUP)) {
			if (!(value instanceof String)) {
				throw new IllegalArgumentException(value.toString());
			}
			if (!(value instanceof String)) {
				throw new IllegalArgumentException(value.toString());
			}
			if (!ValidatorUtil.valiDisplayName(value.toString())) {

			} else if (!ValidatorUtil.valiDisplayNameLength(value.toString())) {

			} else {
				property.setGroup(getGroup(value.toString()));
			}
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_PROPERTY_ORDERNUM)) {
			if (!(value instanceof Integer)) {
				throw new IllegalArgumentException(value.toString());
			}
			property.setOrderNum(Integer.parseInt(value.toString()));
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
			property.setId("11111");
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

	/**
	 * 
	 */
	@Override
	protected void initializeDescriptors() {
		// TODO Auto-generated method stub

	}
}
