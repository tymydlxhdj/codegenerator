package com.mqfdy.code.designer.views.properties.sources;

import com.mqfdy.code.designer.utils.CheckerUtil;
import com.mqfdy.code.model.AbstractModelElement;
import com.mqfdy.code.model.BusinessClass;
import com.mqfdy.code.model.BusinessOperation;
import com.mqfdy.code.model.utils.DataType;
import com.mqfdy.code.model.utils.TransactionType;
import com.mqfdy.code.resource.validator.ValidatorUtil;


// TODO: Auto-generated Javadoc
/**
 * 业务操作PropertyPropertySource.
 *
 * @author mqfdy
 */
public class BusinessOperationPropertySource extends ModelPropertySource {
	
	/** The business operation. */
	public BusinessOperation businessOperation;

	/**
	 * Instantiates a new business operation property source.
	 *
	 * @param property
	 *            the property
	 */
	public BusinessOperationPropertySource(AbstractModelElement property) {
		super();
		this.businessOperation = (BusinessOperation) property;
		initializeDescriptors();
		super.getModelProperties().clear();
		// if (super.getModelProperties().isEmpty()) {
		installModelProperty();
		// }
	}

	/**
	 * 
	 */
	protected void installModelProperty() {
		if (businessOperation.getOperationType() != null
				&& businessOperation.getOperationType().equals(
						BusinessOperation.OPERATION_TYPE_STANDARD)) {
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
					IBusinessModelPropertyNames.PROPERTY_COMMON_DISPLAYNAME,
					IBusinessModelPropertyNames.CATEGORY_BASE, "", true, "04");
			addStringModelProperty(
					IBusinessModelPropertyNames.PROPERTY_COMMON_REMARK,
					IBusinessModelPropertyNames.CATEGORY_BASE, "", true, "10");
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
					IBusinessModelPropertyNames.PROPERTY_COMMON_DISPLAYNAME,
					IBusinessModelPropertyNames.CATEGORY_BASE, "", "04");
			addStringModelProperty(
					IBusinessModelPropertyNames.PROPERTY_COMMON_REMARK,
					IBusinessModelPropertyNames.CATEGORY_BASE, "", "10");
			addListReadOnlyModelProperty(
					IBusinessModelPropertyNames.PROPERTY_OPERATION_RETURNDATATYPE,
					IBusinessModelPropertyNames.CATEGORY_BASE,
					DataType.getDataTypesString(), "06");
			addListReadOnlyModelProperty(
					IBusinessModelPropertyNames.PROPERTY_OPERATION_TRANSACTIONSUPPORT,
					IBusinessModelPropertyNames.CATEGORY_BASE,
					TransactionType.getTransactionTypesString(), "07");
			addStringModelProperty(
					IBusinessModelPropertyNames.PROPERTY_OPERATION_EXCEPTIONMESSAGE,
					IBusinessModelPropertyNames.CATEGORY_BASE, "", "08");
			addButtonModelProperty(
					IBusinessModelPropertyNames.PROPERTY_OPERATION_PARAMETERINFO,
					IBusinessModelPropertyNames.CATEGORY_BASE, "",
					"BusinessOperation",
					(AbstractModelElement) businessOperation, "09");
		}// addButtonModelProperty(IBusinessModelPropertyNames.PROPERTY_ENUMERARION_VALUES,IBusinessModelPropertyNames.CATEGORY_ENUMERARION,"");
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
			return businessOperation.getId();
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_COMMON_NAME)) {
			return businessOperation.getName();
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_COMMON_REMARK)) {
			return businessOperation.getRemark();
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_COMMON_DISPLAYNAME)) {
			return businessOperation.getDisplayName();
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_COMMON_OBJECTTYPE)) {
			return businessOperation.getDisplayType();
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_OPERATION_EXCEPTIONMESSAGE)) {
			return businessOperation.getErrorMessage();
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_OPERATION_PARAMETERINFO)) {
			return "请选择...";// businessOperation.getOperationParams();
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_OPERATION_RETURNDATATYPE)) {
			return businessOperation.getReturnDataType();
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_OPERATION_TRANSACTIONSUPPORT)) {
			return businessOperation.getTransactionType();
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
			businessOperation.setId(value.toString());
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
				if (businessOperation.getParent() instanceof BusinessClass) {
					for (BusinessOperation pro : ((BusinessClass) businessOperation
							.getParent()).getOperations()) {
						if (pro.getName().equals(value.toString())) {
							i++;
						}
					}
				}
				if (i < 1) {
					businessOperation.setName(value.toString());
				}
			}
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_COMMON_REMARK)) {
			if (!(value instanceof String)) {
				throw new IllegalArgumentException(value.toString());
			}
			if (ValidatorUtil.valiRemarkLength(value.toString())) {
				businessOperation.setRemark(value.toString());
			}
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_COMMON_OBJECTTYPE)) {
			// if (!(value instanceof String)) {
			// throw new IllegalArgumentException(value.toString());
			// }
			// businessOperation.setStereotype(value.toString());
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_COMMON_DISPLAYNAME)) {
			if (!(value instanceof String)) {
				throw new IllegalArgumentException(value.toString());
			}
			if (value.toString().length() == 0) {

			} else if (!ValidatorUtil.valiDisplayName(value.toString())) {

			} else if (!ValidatorUtil.valiDisplayNameLength(value.toString())) {

			} else {
				businessOperation.setDisplayName(value.toString());
			}
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_OPERATION_EXCEPTIONMESSAGE)) {
			if (!(value instanceof String)) {
				throw new IllegalArgumentException(value.toString());
			}
			if (ValidatorUtil.valiRemarkLength(value.toString())) {
				businessOperation.setErrorMessage(value.toString());
			}
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_OPERATION_PARAMETERINFO)) {
			// if (!(value instanceof String)) {
			// throw new IllegalArgumentException(value.toString());
			// }/////getOperationParams
			// businessOperation.setOperationParams((List<OperationParam>)
			// value);
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_OPERATION_RETURNDATATYPE)) {
			if (!(value instanceof String)) {
				throw new IllegalArgumentException(value.toString());
			}
			businessOperation.setReturnDataType(value.toString());
		} else if (propertyId
				.equals(IBusinessModelPropertyNames.PROPERTY_OPERATION_TRANSACTIONSUPPORT)) {
			if (!(value instanceof String)) {
				throw new IllegalArgumentException(value.toString());
			}
			businessOperation.setTransactionType(value.toString());
		}/*
		 * else if
		 * (propertyId.equals(IBusinessModelPropertyNames.PROPERTY_PROPERTY_DATATYPE
		 * )) { if (!(value instanceof String)) { throw new
		 * IllegalArgumentException(value.toString()); }
		 * businessOperation.setDataType(value.toString()); } else if
		 * (propertyId
		 * .equals(IBusinessModelPropertyNames.PROPERTY_PROPERTY_GROUP)) { if
		 * (!(value instanceof PropertyGroup)) { throw new
		 * IllegalArgumentException(value.toString()); }
		 * businessOperation.setGroup((PropertyGroup) value); } else if
		 * (propertyId
		 * .equals(IBusinessModelPropertyNames.PROPERTY_PROPERTY_ORDERNUM)) { if
		 * (!(value instanceof Integer)) { throw new
		 * IllegalArgumentException(value.toString()); }
		 * businessOperation.setOrderNum(Integer.parseInt(value.toString())); }
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
			businessOperation.setId("11111");
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
