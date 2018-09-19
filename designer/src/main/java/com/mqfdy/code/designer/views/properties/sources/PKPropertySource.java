package com.mqfdy.code.designer.views.properties.sources;

import com.mqfdy.code.designer.utils.CheckerUtil;
import com.mqfdy.code.model.AbstractModelElement;
import com.mqfdy.code.model.BusinessClass;
import com.mqfdy.code.model.IModelElement;
import com.mqfdy.code.model.PKProperty;
import com.mqfdy.code.model.PersistenceProperty;
import com.mqfdy.code.model.Property;
import com.mqfdy.code.model.PropertyGroup;
import com.mqfdy.code.model.utils.DataType;
import com.mqfdy.code.model.utils.PrimaryKeyPloyType;
import com.mqfdy.code.resource.validator.ValidatorUtil;


// TODO: Auto-generated Javadoc
/**
 * 业务实体主键属性PropertyPropertySource.
 *
 * @author mqfdy
 */
public class PKPropertySource extends ModelPropertySource {
	
	/** The p K property. */
	public PKProperty pKProperty;

	/**
	 * Instantiates a new PK property source.
	 *
	 * @param property
	 *            the property
	 */
	public PKPropertySource(AbstractModelElement property) {
		super();
		this.pKProperty = (PKProperty) property;
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
	 * Gets the strings.
	 *
	 * @author mqfdy
	 * @return the strings
	 * @Date 2018-09-03 09:00
	 */
	protected String[] getStrings() {
		java.util.List<PropertyGroup> list = ((BusinessClass) pKProperty
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
		java.util.List<PropertyGroup> groups = ((BusinessClass) pKProperty
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
		if (pKProperty.getStereotype() != null
				&& (IModelElement.STEREOTYPE_BUILDIN.equals(
						pKProperty.getStereotype()) || IModelElement.STEREOTYPE_REFERENCE.equals(
								pKProperty.getStereotype()))) {
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
					IBusinessModelPropertyNames.CATEGORY_BASE, "", true, "22");
			addStringModelProperty(
					IBusinessModelPropertyNames.PROPERTY_PERSISTENCEPROPERTY_PROPERTYTYPE,
					IBusinessModelPropertyNames.CATEGORY_BASE, "", true, "06");
			addStringModelProperty(
					IBusinessModelPropertyNames.PROPERTY_PERSISTENCEPROPERTY_PROPERTYGROUP,
					IBusinessModelPropertyNames.CATEGORY_BASE, "", true, "07");
			// addStringModelProperty(IBusinessModelPropertyNames.PROPERTY_PERSISTENCEPROPERTY_ISPERSITENCEPROPERTY
			// ,IBusinessModelPropertyNames.CATEGORY_PROPERTY_PERSISTENCEPLOY,"",true,"08");
			// addStringModelProperty(IBusinessModelPropertyNames.PROPERTY_PERSISTENCEPROPERTY_DEFAULTVALUE
			// ,IBusinessModelPropertyNames.CATEGORY_PROPERTY_PERSISTENCEPLOY,"",true,"09");
			// addStringModelProperty(IBusinessModelPropertyNames.PROPERTY_PERSISTENCEPROPERTY_ISPRIMARYKEY
			// ,IBusinessModelPropertyNames.CATEGORY_PROPERTY_PERSISTENCEPLOY,"",true,"10");
			// addStringModelProperty(IBusinessModelPropertyNames.PROPERTY_PERSISTENCEPROPERTY_ISNULLABLE
			// ,IBusinessModelPropertyNames.CATEGORY_PROPERTY_PERSISTENCEPLOY,"",true,"11");
			// addStringModelProperty(IBusinessModelPropertyNames.PROPERTY_PERSISTENCEPROPERTY_ISINDEXEDCOLUMN
			// ,IBusinessModelPropertyNames.CATEGORY_PROPERTY_PERSISTENCEPLOY,"",true,"12");
			// addStringModelProperty(IBusinessModelPropertyNames.PROPERTY_PERSISTENCEPROPERTY_ISREADONLY
			// ,IBusinessModelPropertyNames.CATEGORY_PROPERTY_PERSISTENCEPLOY,"",true,"13");
			// addCheckModelProperty(IBusinessModelPropertyNames.PROPERTY_PERSISTENCEPROPERTY_ISUNIQUE
			// ,IBusinessModelPropertyNames.CATEGORY_BASE,"");
			// addStringModelProperty(IBusinessModelPropertyNames.PROPERTY_PERSISTENCEPROPERTY_ISUNIQUE
			// ,IBusinessModelPropertyNames.CATEGORY_PROPERTY_PERSISTENCEPLOY,"",true,"14");
			// addStringModelProperty(IBusinessModelPropertyNames.PROPERTY_PERSISTENCEPROPERTY_PRIMARYKEYPLOY
			// ,IBusinessModelPropertyNames.CATEGORY_PROPERTY_PERSISTENCEPLOY,"",true,"15");
			// addStringModelProperty(IBusinessModelPropertyNames.PROPERTY_PERSISTENCEPROPERTY_PLOYNAME
			// ,IBusinessModelPropertyNames.CATEGORY_PROPERTY_PERSISTENCEPLOY,"",true,"16");
			addStringModelProperty(
					IBusinessModelPropertyNames.PROPERTY_PERSISTENCEPROPERTY_DBCOLUMNNAME,
					IBusinessModelPropertyNames.CATEGORY_PROPERTY_PERSISTENCEPLOY,
					"", true, "17");
			// addStringModelProperty(IBusinessModelPropertyNames.PROPERTY_PERSISTENCEPROPERTY_DBDATATYPE
			// ,IBusinessModelPropertyNames.CATEGORY_BASE,"");
			// addStringModelProperty(IBusinessModelPropertyNames.PROPERTY_PERSISTENCEPROPERTY_DBDATALENGTH
			// ,IBusinessModelPropertyNames.CATEGORY_PROPERTY_PERSISTENCEPLOY,"",true,"18");
			// addStringModelProperty(IBusinessModelPropertyNames.PROPERTY_PERSISTENCEPROPERTY_PRECISION
			// ,IBusinessModelPropertyNames.CATEGORY_PROPERTY_PERSISTENCEPLOY,"",true,"19");
			// addButtonModelProperty(IBusinessModelPropertyNames.PROPERTY_PERSISTENCEPROPERTY_VALIDATOR
			// ,IBusinessModelPropertyNames.CATEGORY_PROPERTY_PERSISTENCEPLOY,"","PKProperty",pKProperty,"20");
			// addButtonModelProperty(IBusinessModelPropertyNames.PROPERTY_PERSISTENCEPROPERTY_PROPERTYEDITOR
			// ,IBusinessModelPropertyNames.CATEGORY_PROPERTY_PERSISTENCEPLOY,"","PKProperty",pKProperty,"21");

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
					IBusinessModelPropertyNames.CATEGORY_BASE, "", "22");
			addListReadOnlyModelProperty(
					IBusinessModelPropertyNames.PROPERTY_PERSISTENCEPROPERTY_PROPERTYTYPE,
					IBusinessModelPropertyNames.CATEGORY_BASE,
					DataType.getDataTypesString(), "06");
			addListModelProperty(
					IBusinessModelPropertyNames.PROPERTY_PERSISTENCEPROPERTY_PROPERTYGROUP,
					IBusinessModelPropertyNames.CATEGORY_BASE, getStrings(),
					"07");
			// addBooleanModelProperty(IBusinessModelPropertyNames.PROPERTY_PERSISTENCEPROPERTY_ISPERSITENCEPROPERTY
			// ,IBusinessModelPropertyNames.CATEGORY_PROPERTY_PERSISTENCEPLOY,true,"08");
			// addStringModelProperty(IBusinessModelPropertyNames.PROPERTY_PERSISTENCEPROPERTY_DEFAULTVALUE
			// ,IBusinessModelPropertyNames.CATEGORY_PROPERTY_PERSISTENCEPLOY,"","09");
			// addBooleanModelProperty(IBusinessModelPropertyNames.PROPERTY_PERSISTENCEPROPERTY_ISPRIMARYKEY
			// ,IBusinessModelPropertyNames.CATEGORY_PROPERTY_PERSISTENCEPLOY,true,"10");
			// addBooleanModelProperty(IBusinessModelPropertyNames.PROPERTY_PERSISTENCEPROPERTY_ISNULLABLE
			// ,IBusinessModelPropertyNames.CATEGORY_PROPERTY_PERSISTENCEPLOY,true,"11");
			// addBooleanModelProperty(IBusinessModelPropertyNames.PROPERTY_PERSISTENCEPROPERTY_ISINDEXEDCOLUMN
			// ,IBusinessModelPropertyNames.CATEGORY_PROPERTY_PERSISTENCEPLOY,true,"12");
			// addBooleanModelProperty(IBusinessModelPropertyNames.PROPERTY_PERSISTENCEPROPERTY_ISREADONLY
			// ,IBusinessModelPropertyNames.CATEGORY_PROPERTY_PERSISTENCEPLOY,true,"13");
			// //
			// addCheckModelProperty(IBusinessModelPropertyNames.PROPERTY_PERSISTENCEPROPERTY_ISUNIQUE
			// ,IBusinessModelPropertyNames.CATEGORY_BASE,"");
			// addBooleanModelProperty(IBusinessModelPropertyNames.PROPERTY_PERSISTENCEPROPERTY_ISUNIQUE
			// ,IBusinessModelPropertyNames.CATEGORY_PROPERTY_PERSISTENCEPLOY,true,"14");
			// addListModelProperty(IBusinessModelPropertyNames.PROPERTY_PERSISTENCEPROPERTY_PRIMARYKEYPLOY
			// ,IBusinessModelPropertyNames.CATEGORY_PROPERTY_PERSISTENCEPLOY,PrimaryKeyPloyType.getPrimaryKeyPloyTypesString(),"15");
			// addStringModelProperty(IBusinessModelPropertyNames.PROPERTY_PERSISTENCEPROPERTY_PLOYNAME
			// ,IBusinessModelPropertyNames.CATEGORY_PROPERTY_PERSISTENCEPLOY,"",true,"16");
			if(IModelElement.STEREOTYPE_REVERSE.equals(pKProperty.getStereotype())){
				addStringModelProperty(
						IBusinessModelPropertyNames.PROPERTY_PERSISTENCEPROPERTY_DBCOLUMNNAME,
						IBusinessModelPropertyNames.CATEGORY_PROPERTY_PERSISTENCEPLOY,
						"",true,"17");
			}else{
				addStringModelProperty(
						IBusinessModelPropertyNames.PROPERTY_PERSISTENCEPROPERTY_DBCOLUMNNAME,
						IBusinessModelPropertyNames.CATEGORY_PROPERTY_PERSISTENCEPLOY,
						"17");
			}
			// addStringModelProperty(IBusinessModelPropertyNames.PROPERTY_PERSISTENCEPROPERTY_DBDATATYPE
			// ,IBusinessModelPropertyNames.CATEGORY_BASE,"");
			// addStringModelProperty(IBusinessModelPropertyNames.PROPERTY_PERSISTENCEPROPERTY_DBDATALENGTH
			// ,IBusinessModelPropertyNames.CATEGORY_PROPERTY_PERSISTENCEPLOY,"","18");
			// addStringModelProperty(IBusinessModelPropertyNames.PROPERTY_PERSISTENCEPROPERTY_PRECISION
			// ,IBusinessModelPropertyNames.CATEGORY_PROPERTY_PERSISTENCEPLOY,"","19");
			// //
			// addButtonModelProperty(IBusinessModelPropertyNames.PROPERTY_PERSISTENCEPROPERTY_VALIDATOR
			// ,IBusinessModelPropertyNames.CATEGORY_PROPERTY_PERSISTENCEPLOY,"","PKProperty",pKProperty,"20");
			// addButtonModelProperty(IBusinessModelPropertyNames.PROPERTY_PERSISTENCEPROPERTY_PROPERTYEDITOR
			// ,IBusinessModelPropertyNames.CATEGORY_PROPERTY_PERSISTENCEPLOY,"","PKProperty",pKProperty,"21");

		}
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
			return pKProperty.getId();
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_COMMON_NAME)) {
			return pKProperty.getName();
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_COMMON_REMARK)) {
			return pKProperty.getRemark() == null ? "" : pKProperty.getRemark();
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_COMMON_DISPLAYNAME)) {
			return pKProperty.getDisplayName();
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_COMMON_OBJECTTYPE)) {
			return pKProperty.getDisplayType();
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_COMMON_STEREOTYPETYPE)) {
			String stype = pKProperty.getStereotype();
			return AbstractModelElementPropertySource.getStereotype(stype);
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_PERSISTENCEPROPERTY_PROPERTYTYPE)) {
			return pKProperty.getDataType();// /DATATYPE
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_PERSISTENCEPROPERTY_PROPERTYGROUP)) {
			return pKProperty.getGroup() == null ? "" : pKProperty.getGroup()
					.getDisplayName();
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_PERSISTENCEPROPERTY_ISPERSITENCEPROPERTY)) {
			return pKProperty.isPersistence();
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_PERSISTENCEPROPERTY_DEFAULTVALUE)) {
			return pKProperty.getDefaultValue();
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_PERSISTENCEPROPERTY_ISPRIMARYKEY)) {
			return pKProperty.isPrimaryKey();
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_PERSISTENCEPROPERTY_ISNULLABLE)) {
			return pKProperty.isNullable();
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_PERSISTENCEPROPERTY_ISINDEXEDCOLUMN)) {
			return pKProperty.isIndexedColumn();
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_PERSISTENCEPROPERTY_ISREADONLY)) {
			return pKProperty.isReadOnly();
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_PERSISTENCEPROPERTY_ISUNIQUE)) {
			return pKProperty.isUnique();
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_PERSISTENCEPROPERTY_PRIMARYKEYPLOY)) {
			if (pKProperty.getPrimaryKeyPloy() != null
					&& PrimaryKeyPloyType.getPrimaryKeyPloyType(pKProperty
							.getPrimaryKeyPloy()) != null)
				return PrimaryKeyPloyType.getPrimaryKeyPloyType(
						pKProperty.getPrimaryKeyPloy()).getDisplayValue();
			return "";
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_PERSISTENCEPROPERTY_PLOYNAME)) {
			return pKProperty.getPkName();
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_PERSISTENCEPROPERTY_DBCOLUMNNAME)) {
			return pKProperty.getdBColumnName();
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_PERSISTENCEPROPERTY_DBDATALENGTH)) {
			return pKProperty.getdBDataLength();
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_PERSISTENCEPROPERTY_PRECISION)) {
			return pKProperty.getdBDataPrecision();
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_PERSISTENCEPROPERTY_VALIDATOR)) {
			return "";// pKProperty.getValidators();
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_PERSISTENCEPROPERTY_PROPERTYEDITOR)) {
			return pKProperty.getEditor() == null ? "" : pKProperty.getEditor()
					.getDisplayName();
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
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.views.properties.IPropertySource#setPropertyValue(java
	 * .lang.Object, java.lang.Object)
	 */
	public void setPropertyValue(Object propertyId, Object value) {
		if (!(propertyId instanceof String)) {
			throw new IllegalArgumentException(propertyId.toString());
		}
		if (propertyId.equals(IBusinessModelPropertyNames.PROPERTY_COMMON_ID)) {
			if (!(value instanceof String)) {
				throw new IllegalArgumentException(value.toString());
			}
			pKProperty.setId(value.toString());
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
				if (pKProperty.getParent() instanceof BusinessClass) {
					for (Property pro : ((BusinessClass) pKProperty.getParent())
							.getProperties()) {
						if (pro.getName().equals(value.toString())) {
							i++;
						}
					}
				}
				if (i < 1) {
					pKProperty.setName(value.toString());
				}
			}
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_COMMON_REMARK)) {
			if (!(value instanceof String)) {
				throw new IllegalArgumentException(value.toString());
			}
			if (ValidatorUtil.valiRemarkLength(value.toString())) {
				pKProperty.setRemark(value.toString());
			}
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_COMMON_OBJECTTYPE)) {
			// if (!(value instanceof String)) {
			// throw new IllegalArgumentException(value.toString());
			// }
			// pKProperty.setStereotype(value.toString());
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_COMMON_DISPLAYNAME)) {
			if (!(value instanceof String)) {
				throw new IllegalArgumentException(value.toString());
			}
			if (!ValidatorUtil.valiDisplayName(value.toString())) {

			} else if (!ValidatorUtil.valiDisplayNameLength(value.toString())) {

			} else {
				pKProperty.setDisplayName(value.toString());
			}
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_PERSISTENCEPROPERTY_PROPERTYTYPE)) {
			if (!(value instanceof String)) {
				throw new IllegalArgumentException(value.toString());
			}
			pKProperty.setDataType(value.toString());
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_PERSISTENCEPROPERTY_PROPERTYGROUP)) {
			if (!(value instanceof String)) {
				throw new IllegalArgumentException(value.toString());
			}
			pKProperty.setGroup(getGroup(value.toString()));
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_PERSISTENCEPROPERTY_ISPERSITENCEPROPERTY)) {
			// if (!(value instanceof String)) {
			// throw new IllegalArgumentException(value.toString());
			// }//////
			// pKProperty.set(value.toString());//pKProperty.isPersistence();
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_PERSISTENCEPROPERTY_DEFAULTVALUE)) {
			if (!(value instanceof String)) {
				throw new IllegalArgumentException(value.toString());
			}
			pKProperty.setDefaultValue(value.toString());
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_PERSISTENCEPROPERTY_ISPRIMARYKEY)) {
			if (!(value instanceof String)) {
				throw new IllegalArgumentException(value.toString());
			}
			pKProperty.setPrimaryKey(value.toString().equals("true") ? true
					: false);
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_PERSISTENCEPROPERTY_ISNULLABLE)) {
			if (!(value instanceof String)) {
				throw new IllegalArgumentException(value.toString());
			}
			pKProperty.setNullable(value.toString().equals("true") ? true
					: false);
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_PERSISTENCEPROPERTY_ISINDEXEDCOLUMN)) {
			if (!(value instanceof String)) {
				throw new IllegalArgumentException(value.toString());
			}
			pKProperty.setIndexedColumn(value.toString().equals("true") ? true
					: false);
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_PERSISTENCEPROPERTY_ISREADONLY)) {
			if (!(value instanceof String)) {
				throw new IllegalArgumentException(value.toString());
			}
			pKProperty.setReadOnly(value.toString().equals("true") ? true
					: false);
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_PERSISTENCEPROPERTY_ISUNIQUE)) {
			if (!(value instanceof String)) {
				throw new IllegalArgumentException(value.toString());
			}
			pKProperty
					.setUnique(value.toString().equals("true") ? true : false);
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_PERSISTENCEPROPERTY_PRIMARYKEYPLOY)) {
			if (!(value instanceof String)) {
				throw new IllegalArgumentException(value.toString());
			}
			pKProperty.setPrimaryKeyPloy(PrimaryKeyPloyType
					.getPrimaryKeyPloyTypeByDisplayValue(value.toString())
					.getValue());
			// }else if
			// (propertyId.equals(IBusinessModelPropertyNames.PROPERTY_PERSISTENCEPROPERTY_PLOYNAME))
			// {
			// if (!(value instanceof String)) {
			// throw new IllegalArgumentException(value.toString());
			// }
			// pKProperty.setPkName(value.toString());
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_PERSISTENCEPROPERTY_DBCOLUMNNAME)) {
			if (!(value instanceof String)) {
				throw new IllegalArgumentException(value.toString());
			}
			if (CheckerUtil.checkSql(value.toString())) {

			} else if (!ValidatorUtil.valiFirstNo_Name(value.toString())) {

			} else if (!ValidatorUtil.valiNameLength(value.toString())) {

			} else {
				int i = 0;
				if (pKProperty.getParent() instanceof BusinessClass) {
					for (Property pro : ((BusinessClass) pKProperty.getParent())
							.getProperties()) {
						if (pro instanceof PersistenceProperty
								&& !pro.getId().equals(pKProperty.getId())
								&& ((PersistenceProperty) pro)
										.getdBColumnName().equals(
												value.toString())) {
							i++;
						}
					}
				}
				if (i < 1) {
					pKProperty.setdBColumnName(value.toString());
				}
			}
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_PERSISTENCEPROPERTY_DBDATALENGTH)) {
			if (!(value instanceof String)) {
				throw new IllegalArgumentException(value.toString());
			}
			pKProperty.setdBDataLength(value.toString());
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_PERSISTENCEPROPERTY_PRECISION)) {
			if (!(value instanceof Integer)) {
				throw new IllegalArgumentException(value.toString());
			}
			pKProperty.setdBDataPrecision(value.toString());
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_PERSISTENCEPROPERTY_VALIDATOR)) {
			if (!(value instanceof PKPropertySource)) {
				// throw new IllegalArgumentException("");//(value.toString());
			} else {
				pKProperty = ((PKPropertySource) value).pKProperty;
			}
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_PERSISTENCEPROPERTY_PROPERTYEDITOR)) {
			if (!(value instanceof PKPropertySource)) {
				// throw new IllegalArgumentException("");//value.toString());
			} else {
				pKProperty = ((PKPropertySource) value).pKProperty;
			}
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
			pKProperty.setId("11111");
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
