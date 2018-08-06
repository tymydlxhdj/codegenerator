package com.mqfdy.code.designer.views.properties.sources;

import com.mqfdy.code.designer.utils.CheckerUtil;
import com.mqfdy.code.model.AbstractModelElement;
import com.mqfdy.code.model.BusinessClass;
import com.mqfdy.code.model.IModelElement;
import com.mqfdy.code.model.PersistenceProperty;
import com.mqfdy.code.model.Property;
import com.mqfdy.code.model.PropertyGroup;
import com.mqfdy.code.model.utils.DataType;
import com.mqfdy.code.resource.validator.ValidatorUtil;


/**
 * 持久化属性PropertySource
 * 
 * @author mqfdy
 * 
 */
public class PersistencePropertySource extends ModelPropertySource {
	public PersistenceProperty persistenceProperty;

	public PersistencePropertySource(AbstractModelElement property) {
		super();
		this.persistenceProperty = (PersistenceProperty) property;
		initializeDescriptors();
		// if (super.getModelProperties().isEmpty()) {
		installModelProperty();
		// }
	}

	protected void initializeDescriptors() {
	}

	protected void installModelProperty() {
		if (persistenceProperty.getStereotype() != null
				&& (IModelElement.STEREOTYPE_BUILDIN.equals(persistenceProperty.getStereotype()) 
						|| IModelElement.STEREOTYPE_REFERENCE.equals(
								persistenceProperty.getStereotype()))) {
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
					IBusinessModelPropertyNames.CATEGORY_BASE, "", true, "20");
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
			// addListModelProperty(IBusinessModelPropertyNames.PROPERTY_PERSISTENCEPROPERTY_PRIMARYKEYPLOY
			// ,IBusinessModelPropertyNames.CATEGORY_PROPERTY_PERSISTENCEPLOY,PrimaryKeyPloyType.getPrimaryKeyPloyTypesString(),"15");
			// addStringModelProperty(IBusinessModelPropertyNames.PROPERTY_PERSISTENCEPROPERTY_PLOYNAME
			// ,IBusinessModelPropertyNames.CATEGORY_BASE,"");
			addStringModelProperty(
					IBusinessModelPropertyNames.PROPERTY_PERSISTENCEPROPERTY_DBCOLUMNNAME,
					IBusinessModelPropertyNames.CATEGORY_PROPERTY_PERSISTENCEPLOY,
					"08");
			// addStringModelProperty(IBusinessModelPropertyNames.PROPERTY_PERSISTENCEPROPERTY_DBDATATYPE
			// ,IBusinessModelPropertyNames.CATEGORY_BASE,"");
			// addStringModelProperty(IBusinessModelPropertyNames.PROPERTY_PERSISTENCEPROPERTY_DBDATALENGTH
			// ,IBusinessModelPropertyNames.CATEGORY_PROPERTY_PERSISTENCEPLOY,"",true,"16");
			// addStringModelProperty(IBusinessModelPropertyNames.PROPERTY_PERSISTENCEPROPERTY_PRECISION
			// ,IBusinessModelPropertyNames.CATEGORY_PROPERTY_PERSISTENCEPLOY,"",true,"17");
			// addButtonModelProperty(IBusinessModelPropertyNames.PROPERTY_PERSISTENCEPROPERTY_VALIDATOR
			// ,IBusinessModelPropertyNames.CATEGORY_PROPERTY_PERSISTENCEPLOY,"","PersistenceProperty",persistenceProperty,"18");
			// addButtonModelProperty(IBusinessModelPropertyNames.PROPERTY_PERSISTENCEPROPERTY_PROPERTYEDITOR
			// ,IBusinessModelPropertyNames.CATEGORY_PROPERTY_PERSISTENCEPLOY,"","PersistenceProperty",persistenceProperty,"19");
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
					IBusinessModelPropertyNames.CATEGORY_BASE, "", "20");
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
			// ,IBusinessModelPropertyNames.CATEGORY_BASE,"");
			if(IModelElement.STEREOTYPE_REVERSE.equals(persistenceProperty.getStereotype())){
				addStringModelProperty(
						IBusinessModelPropertyNames.PROPERTY_PERSISTENCEPROPERTY_DBCOLUMNNAME,
						IBusinessModelPropertyNames.CATEGORY_PROPERTY_PERSISTENCEPLOY,
						"",true,"08");
			}else{
				addStringModelProperty(
						IBusinessModelPropertyNames.PROPERTY_PERSISTENCEPROPERTY_DBCOLUMNNAME,
						IBusinessModelPropertyNames.CATEGORY_PROPERTY_PERSISTENCEPLOY,
						"08");
			}
			// addStringModelProperty(IBusinessModelPropertyNames.PROPERTY_PERSISTENCEPROPERTY_DBDATATYPE
			// ,IBusinessModelPropertyNames.CATEGORY_BASE,"");
			// addStringModelProperty(IBusinessModelPropertyNames.PROPERTY_PERSISTENCEPROPERTY_DBDATALENGTH
			// ,IBusinessModelPropertyNames.CATEGORY_PROPERTY_PERSISTENCEPLOY,"","16");
			// addStringModelProperty(IBusinessModelPropertyNames.PROPERTY_PERSISTENCEPROPERTY_PRECISION
			// ,IBusinessModelPropertyNames.CATEGORY_PROPERTY_PERSISTENCEPLOY,"","17");
			// //
			// addButtonModelProperty(IBusinessModelPropertyNames.PROPERTY_PERSISTENCEPROPERTY_VALIDATOR
			// ,IBusinessModelPropertyNames.CATEGORY_PROPERTY_PERSISTENCEPLOY,"","PersistenceProperty",persistenceProperty,"18");
			// addButtonModelProperty(IBusinessModelPropertyNames.PROPERTY_PERSISTENCEPROPERTY_PROPERTYEDITOR
			// ,IBusinessModelPropertyNames.CATEGORY_PROPERTY_PERSISTENCEPLOY,"","PersistenceProperty",persistenceProperty,"19");
		}
		// addButtonModelProperty(IBusinessModelPropertyNames.PROPERTY_ENUMERARION_VALUES,IBusinessModelPropertyNames.CATEGORY_ENUMERARION,"");
	}

	protected String[] getStrings() {
		java.util.List<PropertyGroup> list = ((BusinessClass) persistenceProperty
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

	private PropertyGroup getGroup(String groupDisplay) {
		if (groupDisplay.trim().equals(""))
			return null;
		java.util.List<PropertyGroup> groups = ((BusinessClass) persistenceProperty
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

	public Object getPropertyValue(Object propertyId) {
		if (!(propertyId instanceof String)) {
			throw new IllegalArgumentException();
		}

		if (propertyId.equals(IBusinessModelPropertyNames.PROPERTY_COMMON_ID)) {
			return persistenceProperty.getId();
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_COMMON_NAME)) {
			return persistenceProperty.getName();
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_COMMON_REMARK)) {
			return persistenceProperty.getRemark() == null ? ""
					: persistenceProperty.getRemark();
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_COMMON_DISPLAYNAME)) {
			return persistenceProperty.getDisplayName();
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_COMMON_OBJECTTYPE)) {
			return persistenceProperty.getDisplayType();
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_COMMON_STEREOTYPETYPE)) {
			String stype = persistenceProperty.getStereotype();
			return AbstractModelElementPropertySource.getStereotype(stype);
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_PERSISTENCEPROPERTY_PROPERTYTYPE)) {
			return persistenceProperty.getDataType();
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_PERSISTENCEPROPERTY_PROPERTYGROUP)) {
			return persistenceProperty.getGroup() == null ? ""
					: persistenceProperty.getGroup().getDisplayName();
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_PERSISTENCEPROPERTY_ISPERSITENCEPROPERTY)) {
			return persistenceProperty.isPersistence();
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_PERSISTENCEPROPERTY_DEFAULTVALUE)) {
			return persistenceProperty.getDefaultValue();
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_PERSISTENCEPROPERTY_ISPRIMARYKEY)) {
			return persistenceProperty.isPrimaryKey();
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_PERSISTENCEPROPERTY_ISNULLABLE)) {
			return persistenceProperty.isNullable();
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_PERSISTENCEPROPERTY_ISINDEXEDCOLUMN)) {
			return persistenceProperty.isIndexedColumn();
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_PERSISTENCEPROPERTY_ISREADONLY)) {
			return persistenceProperty.isReadOnly();
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_PERSISTENCEPROPERTY_ISUNIQUE)) {
			return persistenceProperty.isUnique();
		}/*
		 * else if (propertyId.equals(IBusinessModelPropertyNames.
		 * PROPERTY_PERSISTENCEPROPERTY_PRIMARYKEYPLOY)) { return
		 * "nullllll";//persistenceProperty.getp; }else if
		 * (propertyId.equals(IBusinessModelPropertyNames
		 * .PROPERTY_PERSISTENCEPROPERTY_PLOYNAME)) { return
		 * persistenceProperty.get; }
		 */else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_PERSISTENCEPROPERTY_DBCOLUMNNAME)) {
			return persistenceProperty.getdBColumnName();
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_PERSISTENCEPROPERTY_DBDATALENGTH)) {
			return persistenceProperty.getdBDataLength();
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_PERSISTENCEPROPERTY_PRECISION)) {
			return persistenceProperty.getdBDataPrecision();
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_PERSISTENCEPROPERTY_VALIDATOR)) {
			return "";// persistenceProperty.getValidators();
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_PERSISTENCEPROPERTY_PROPERTYEDITOR)) {
			return persistenceProperty.getEditor() == null ? ""
					: persistenceProperty.getEditor().getDisplayName();
		} else
			return "";
	}

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
			persistenceProperty.setId(value.toString());
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
				if (persistenceProperty.getParent() instanceof BusinessClass) {
					for (Property pro : ((BusinessClass) persistenceProperty
							.getParent()).getProperties()) {
						if (!pro.getId().equals(persistenceProperty.getId())
								&& pro.getName().equals(value.toString())) {
							i++;
						}
					}
				}
				// else if(persistenceProperty.getParent() instanceof
				// DataTransferObject){
				// for(Property pro:((DataTransferObject)
				// persistenceProperty.getParent()).getProperties()){
				// if(pro.getName().equals(persistenceProperty.getName())){
				// i++;
				// }
				// }
				// }
				if (i < 1) {
					persistenceProperty.setName(value.toString());
				}
			}
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_COMMON_REMARK)) {
			if (!(value instanceof String)) {
				throw new IllegalArgumentException(value.toString());
			}
			if (ValidatorUtil.valiRemarkLength(value.toString())) {
				persistenceProperty.setRemark(value.toString());
			}
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_COMMON_OBJECTTYPE)) {
			// if (!(value instanceof String)) {
			// throw new IllegalArgumentException(value.toString());
			// }
			// persistenceProperty.setStereotype(value.toString());
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_COMMON_DISPLAYNAME)) {
			if (!(value instanceof String)) {
				throw new IllegalArgumentException(value.toString());
			}
			if (!ValidatorUtil.valiDisplayName(value.toString())) {

			} else if (!ValidatorUtil.valiDisplayNameLength(value.toString())) {

			} else {
				persistenceProperty.setDisplayName(value.toString());
			}
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_PERSISTENCEPROPERTY_PROPERTYTYPE)) {
			if (!(value instanceof String)) {
				throw new IllegalArgumentException(value.toString());
			}
			persistenceProperty.setDataType(value.toString());
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_PERSISTENCEPROPERTY_PROPERTYGROUP)) {
			if (!(value instanceof String)) {
				throw new IllegalArgumentException(value.toString());
			}
			if (!ValidatorUtil.valiDisplayName(value.toString())) {

			} else if (!ValidatorUtil.valiDisplayNameLength(value.toString())) {

			} else {
				persistenceProperty.setGroup(getGroup(value.toString()));
			}
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_PERSISTENCEPROPERTY_ISPERSITENCEPROPERTY)) {
			if (!(value instanceof String)) {
				throw new IllegalArgumentException(value.toString());
			}
			// persistenceProperty.set(value.toString());//persistenceProperty.isPersistence();
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_PERSISTENCEPROPERTY_DEFAULTVALUE)) {
			if (!(value instanceof String)) {
				throw new IllegalArgumentException(value.toString());
			}
			persistenceProperty.setDefaultValue(value.toString());
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_PERSISTENCEPROPERTY_ISPRIMARYKEY)) {
			if (!(value instanceof String)) {
				throw new IllegalArgumentException(value.toString());
			}
			persistenceProperty
					.setPrimaryKey(value.toString().equals("true") ? true
							: false);
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_PERSISTENCEPROPERTY_ISNULLABLE)) {
			if (!(value instanceof String)) {
				throw new IllegalArgumentException(value.toString());
			}
			persistenceProperty
					.setNullable(value.toString().equals("true") ? true : false);
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_PERSISTENCEPROPERTY_ISINDEXEDCOLUMN)) {
			if (!(value instanceof String)) {
				throw new IllegalArgumentException(value.toString());
			}
			persistenceProperty.setIndexedColumn(value.toString()
					.equals("true") ? true : false);
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_PERSISTENCEPROPERTY_ISREADONLY)) {
			if (!(value instanceof String)) {
				throw new IllegalArgumentException(value.toString());
			}
			persistenceProperty
					.setReadOnly(value.toString().equals("true") ? true : false);
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_PERSISTENCEPROPERTY_ISUNIQUE)) {
			if (!(value instanceof String)) {
				throw new IllegalArgumentException(value.toString());
			}
			persistenceProperty
					.setUnique(value.toString().equals("true") ? true : false);
		}/*
		 * else if (propertyId.equals(IBusinessModelPropertyNames.
		 * PROPERTY_PERSISTENCEPROPERTY_PRIMARYKEYPLOY)) { if (!(value
		 * instanceof String)) { throw new
		 * IllegalArgumentException(value.toString()); }
		 * persistenceProperty.setDisplayName(value.toString()); return
		 * "nullllll";//persistenceProperty.getp; }else if
		 * (propertyId.equals(IBusinessModelPropertyNames
		 * .PROPERTY_PERSISTENCEPROPERTY_PLOYNAME)) { if (!(value instanceof
		 * String)) { throw new IllegalArgumentException(value.toString()); }
		 * persistenceProperty.setDisplayName(value.toString()); return
		 * persistenceProperty.get; }
		 */else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_PERSISTENCEPROPERTY_DBCOLUMNNAME)) {
			if (!(value instanceof String)) {
				throw new IllegalArgumentException(value.toString());
			}
			if (CheckerUtil.checkSql(value.toString())) {

			} else if (!ValidatorUtil.valiFirstNo_Name(value.toString())) {

			} else if (!ValidatorUtil.valiNameLength(value.toString())) {

			} else {
				int i = 0;
				if (persistenceProperty.getParent() instanceof BusinessClass) {
					for (Property pro : ((BusinessClass) persistenceProperty
							.getParent()).getProperties()) {
						if (pro instanceof PersistenceProperty
								&& !pro.getId().equals(
										persistenceProperty.getId())
								&& ((PersistenceProperty) pro)
										.getdBColumnName().equals(
												value.toString())) {
							i++;
						}
					}
				}
				if (i < 1) {
					persistenceProperty.setdBColumnName(value.toString());
				}
			}
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_PERSISTENCEPROPERTY_DBDATALENGTH)) {
			if (!(value instanceof String)) {
				throw new IllegalArgumentException(value.toString());
			}
			persistenceProperty.setdBDataLength(value.toString());
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_PERSISTENCEPROPERTY_PRECISION)) {
			if (!(value instanceof Integer)) {
				throw new IllegalArgumentException(value.toString());
			}
			persistenceProperty.setdBDataPrecision(value.toString());
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_PERSISTENCEPROPERTY_VALIDATOR)) {
			if (!(value instanceof PersistencePropertySource)) {
				// throw new IllegalArgumentException("");//(value.toString());
			} else {
				persistenceProperty = ((PersistencePropertySource) value).persistenceProperty;
			}
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_PERSISTENCEPROPERTY_PROPERTYEDITOR)) {
			if (!(value instanceof PersistencePropertySource)) {
				// throw new IllegalArgumentException("");//(value.toString());
			} else {
				persistenceProperty = ((PersistencePropertySource) value).persistenceProperty;
			}
			// BusinessModelManager businessModelManager =
			// BusinessModelUtil.getEditorBusinessModelManager();
			// BusinessModelEvent businessModelEvent = new
			// BusinessModelEvent(BusinessModelEvent.MODEL_ELEMENT_UPDATE,
			// persistenceProperty);
			// businessModelManager.businessObjectModelChanged(businessModelEvent);
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
			persistenceProperty.setId("11111");
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
