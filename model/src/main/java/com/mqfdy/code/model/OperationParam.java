package com.mqfdy.code.model;

import java.util.List;

import org.dom4j.Element;

import com.mqfdy.code.model.utils.StringUtil;

// TODO: Auto-generated Javadoc
/**
 * The Class OperationParam.
 *
 * @author mqfdy
 */
public class OperationParam extends AbstractModelElement {

	/** 数据类型. */
	private String dataType;

	/** 默认值. */
	private String defaultValue;

	/** 值. */
	private String value;

	/**
	 * Instantiates a new operation param.
	 */
	public OperationParam() {
	}

	/**
	 * 通过 XML 构造 OperationParam.
	 *
	 * @param paramElement
	 *            the param element
	 */
	public OperationParam(Element paramElement) {

		initBasicAttributes(paramElement);

		// 获取Param节点下的ParamType，DefaultValue，OrderNum子节点值
		String dataTypeText = paramElement.elementTextTrim("DataType");
		String defaultValueText = paramElement.elementTextTrim("DefaultValue");
		String orderNumText = paramElement.elementTextTrim("OrderNum");
		String valueText = paramElement.elementTextTrim("Value");

		setDataType(dataTypeText);
		setDefaultValue(defaultValueText);
		setOrderNum(StringUtil.string2Int(orderNumText));
		setValue(valueText);
	}

	/**
	 * @param paramsElement
	 * @return
	 */
	public Element generateXmlElement(Element paramsElement) {
		Element paramElement = paramsElement.addElement("Param");// Param节点
		this.generateBasicAttributes(paramElement);// 创建Param节点的一般属性
		// 在Param节点下创建DataType，DefaultValue，OrderNum，Value
		paramElement.addElement("DataType").addText(
				StringUtil.convertNull2EmptyStr(getDataType()));
		paramElement.addElement("DefaultValue").addText(
				StringUtil.convertNull2EmptyStr(getDefaultValue()));
		paramElement.addElement("OrderNum").addText(getOrderNum() + "");
		paramElement.addElement("Value").addText(
				StringUtil.convertNull2EmptyStr(getValue()));
		return paramElement;

	}

	/**
	 * Gets the data type.
	 *
	 * @author mqfdy
	 * @return the data type
	 * @Date 2018-09-03 09:00
	 */
	public String getDataType() {
		return dataType;
	}

	/**
	 * Sets the data type.
	 *
	 * @author mqfdy
	 * @param dataType
	 *            the new data type
	 * @Date 2018-09-03 09:00
	 */
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	/**
	 * Gets the default value.
	 *
	 * @author mqfdy
	 * @return the default value
	 * @Date 2018-09-03 09:00
	 */
	public String getDefaultValue() {
		return defaultValue;
	}

	/**
	 * Sets the default value.
	 *
	 * @author mqfdy
	 * @param defaultValue
	 *            the new default value
	 * @Date 2018-09-03 09:00
	 */
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	/**
	 * Gets the value.
	 *
	 * @author mqfdy
	 * @return the value
	 * @Date 2018-09-03 09:00
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Sets the value.
	 *
	 * @author mqfdy
	 * @param value
	 *            the new value
	 * @Date 2018-09-03 09:00
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @return
	 */
	public AbstractModelElement getParent() {
		return null;
	}

	/**
	 * @return
	 */
	public List<AbstractModelElement> getChildren() {
		return EMPTY_CHILD;
	}

	/**
	 * @return
	 */
	public OperationParam clone() {
		OperationParam ele = new OperationParam();
		ele.setId(id);
		ele.setDataType(dataType);
		ele.setDefaultValue(defaultValue);
		ele.setDisplayName(displayName);
		ele.setName(name);
		ele.setExtendAttributies(getExtendAttributies());
		ele.setOrderNum(this.getOrderNum());
		ele.setRemark(remark);
		ele.setValue(value);
		ele.setStereotype(this.getStereotype());
		return ele;
	}

}