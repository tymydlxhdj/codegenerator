package com.mqfdy.code.designer.views.properties.sources;

import com.mqfdy.code.model.AbstractModelElement;
import com.mqfdy.code.model.BusinessObjectModel;
import com.mqfdy.code.model.IModelElement;
import com.mqfdy.code.resource.validator.KeyWordsChecker;
import com.mqfdy.code.resource.validator.ValidatorUtil;


/**
 * 业务模型PropertyPropertySource
 * 
 * @author mqfdy
 * 
 */
public class BusinessObjectModelPropertySource extends ModelPropertySource {
	public BusinessObjectModel element;

	public BusinessObjectModelPropertySource(AbstractModelElement property) {
		super();
		this.element = (BusinessObjectModel) property;
		initializeDescriptors();
		// if (super.getModelProperties().isEmpty()) {
		installModelProperty();
		// }
	}

	protected void installModelProperty() {
		if (element.getStereotype() != null
				&& (IModelElement.STEREOTYPE_BUILDIN.equals(
						element.getStereotype()) || IModelElement.STEREOTYPE_REFERENCE.equals(
								element.getStereotype()))) {
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
					IBusinessModelPropertyNames.PROPERTY_COMMON_NAMESPACE,
					IBusinessModelPropertyNames.CATEGORY_BASE, "", true, "04");
			addStringModelProperty(
					IBusinessModelPropertyNames.PROPERTY_COMMON_DISPLAYNAME,
					IBusinessModelPropertyNames.CATEGORY_BASE, "", true, "04");
			addStringModelProperty(
					IBusinessModelPropertyNames.PROPERTY_COMMON_REMARK,
					IBusinessModelPropertyNames.CATEGORY_BASE, "", true, "05");
			addStringModelProperty(
					IBusinessModelPropertyNames.PROPERTY_VERSION_VERSIONNUM,
					IBusinessModelPropertyNames.CATEGORY_VERSION, "", true,
					"07");
			addStringModelProperty(
					IBusinessModelPropertyNames.PROPERTY_VERSION_CREATER,
					IBusinessModelPropertyNames.CATEGORY_VERSION, "", true,
					"08");
			addStringModelProperty(
					IBusinessModelPropertyNames.PROPERTY_VERSION_CREATETIME,
					IBusinessModelPropertyNames.CATEGORY_VERSION, "", true,
					"09");
			addStringModelProperty(
					IBusinessModelPropertyNames.PROPERTY_VERSION_UPDATER,
					IBusinessModelPropertyNames.CATEGORY_VERSION, "", true,
					"10");
			addStringModelProperty(
					IBusinessModelPropertyNames.PROPERTY_VERSION_UPDATETIME,
					IBusinessModelPropertyNames.CATEGORY_VERSION, "", true,
					"11");
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
					IBusinessModelPropertyNames.PROPERTY_COMMON_NAMESPACE,
					IBusinessModelPropertyNames.CATEGORY_BASE, "", "04");
			addStringModelProperty(
					IBusinessModelPropertyNames.PROPERTY_COMMON_DISPLAYNAME,
					IBusinessModelPropertyNames.CATEGORY_BASE, "", "05");
			addStringModelProperty(
					IBusinessModelPropertyNames.PROPERTY_COMMON_REMARK,
					IBusinessModelPropertyNames.CATEGORY_BASE, "", "06");
			addStringModelProperty(
					IBusinessModelPropertyNames.PROPERTY_VERSION_VERSIONNUM,
					IBusinessModelPropertyNames.CATEGORY_VERSION, "", true,
					"07");
			addStringModelProperty(
					IBusinessModelPropertyNames.PROPERTY_VERSION_CREATER,
					IBusinessModelPropertyNames.CATEGORY_VERSION, "", true,
					"08");
			addStringModelProperty(
					IBusinessModelPropertyNames.PROPERTY_VERSION_CREATETIME,
					IBusinessModelPropertyNames.CATEGORY_VERSION, "", true,
					"09");
			addStringModelProperty(
					IBusinessModelPropertyNames.PROPERTY_VERSION_UPDATER,
					IBusinessModelPropertyNames.CATEGORY_VERSION, "", true,
					"10");
			addStringModelProperty(
					IBusinessModelPropertyNames.PROPERTY_VERSION_UPDATETIME,
					IBusinessModelPropertyNames.CATEGORY_VERSION, "", true,
					"11");
		}// addButtonModelProperty(IBusinessModelPropertyNames.PROPERTY_ENUMERARION_VALUES,IBusinessModelPropertyNames.CATEGORY_ENUMERARION,"","AbstractModelElement","06");
	}

	public Object getPropertyValue(Object propertyId) {
		if (!(propertyId instanceof String)) {
			throw new IllegalArgumentException();
		}
		if (propertyId.equals(IBusinessModelPropertyNames.PROPERTY_COMMON_ID)) {
			return element.getId();
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_COMMON_NAME)) {
			return element.getName();
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_COMMON_NAMESPACE)) {
			return element.getNameSpace();
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_COMMON_REMARK)) {
			return element.getRemark();
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_COMMON_DISPLAYNAME)) {
			return element.getDisplayName();
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_COMMON_OBJECTTYPE)) {
			return element.getDisplayType();
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_VERSION_VERSIONNUM)) {
			return element.getVersionInfo() == null ? "" : element
					.getVersionInfo().getVersionNumber();
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_VERSION_CREATER)) {
			return element.getVersionInfo() == null ? "" : element
					.getVersionInfo().getCreator();
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_VERSION_CREATETIME)) {
			return element.getVersionInfo() == null ? "" : element
					.getVersionInfo().getCreatedTime();
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_VERSION_UPDATER)) {
			return element.getVersionInfo() == null ? "" : element
					.getVersionInfo().getModifier();
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_VERSION_UPDATETIME)) {
			return element.getVersionInfo() == null ? "" : element
					.getVersionInfo().getChangedTime();
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
			element.setId(value.toString());
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_COMMON_NAME)) {
			if (!(value instanceof String)) {
				throw new IllegalArgumentException(value.toString());
			}
			if (!ValidatorUtil.valiName(value.toString())) {

			} else if (!ValidatorUtil.valiNameLength(value.toString())) {

			} else if (new KeyWordsChecker().doCheckJava(value.toString())) {

			} else {
				element.setName(value.toString());
			}
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_COMMON_NAMESPACE)) {
			if (!(value instanceof String)) {
				throw new IllegalArgumentException(value.toString());
			}
			if (ValidatorUtil.validatePackageName(value.toString())) {
				element.setNameSpace(value.toString());
			}
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_COMMON_REMARK)) {
			if (!(value instanceof String)) {
				throw new IllegalArgumentException(value.toString());
			}
			if (ValidatorUtil.valiRemarkLength(value.toString())) {
				element.setRemark(value.toString());
			}
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_COMMON_OBJECTTYPE)) {
			// if (!(value instanceof String)) {
			// throw new IllegalArgumentException(value.toString());
			// }
			// element.setStereotype(value.toString());
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_COMMON_DISPLAYNAME)) {
			if (!(value instanceof String)) {
				throw new IllegalArgumentException(value.toString());
			}
			if (!ValidatorUtil.valiDisplayName(value.toString())) {

			} else if (!ValidatorUtil.valiDisplayNameLength(value.toString())) {

			} else {
				element.setDisplayName(value.toString());
			}
			// }else if
			// (propertyId.equals(IBusinessModelPropertyNames.PROPERTY_ENUMERARION_VALUES))
			// {
			// // if (!(value instanceof String)) {
			// // throw new IllegalArgumentException(value.toString());
			// // }
			// // element.setDisplayName(value.toString());
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
			element.setId("11111");
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
