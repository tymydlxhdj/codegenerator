package com.mqfdy.code.model;

import java.util.Vector;

import org.dom4j.Element;

import com.mqfdy.code.model.utils.StringUtil;

/**
 * 持久化属性
 * 
 * @author LQR
 * 
 */
public class PersistenceProperty extends Property {

	/**
	 * 默认值
	 */
	private String defaultValue;

	/**
	 * 数据库对应字段名
	 */
	private String dBColumnName;

	/**
	 * 数据库字段类型
	 */
	private String dBDataType;

	/**
	 * 字段长度
	 */
	private String dBDataLength;

	/**
	 * 字段精度
	 */
	private String dBDataPrecision;

	/**
	 * 是否 查询属性
	 */
	private boolean isQueryProterty;

	/**
	 * 是否主键
	 */
	private boolean isPrimaryKey;

	/**
	 * 是否唯一
	 */
	private boolean isUnique;

	/**
	 * 是否可空
	 */
	private boolean isNullable;

	/**
	 * 是否唯一
	 */
	private boolean isReadOnly;

	/**
	 * 是否索引列
	 */
	private boolean isIndexedColumn;

	public PersistenceProperty() {
		super();
	}

	/**
	 * 通过 XML 构造 PersistenceProperty
	 * 
	 * @param propertyElement
	 */
	public PersistenceProperty(Element propertyElement) {
		super(propertyElement);

		setDefaultValue(propertyElement.elementTextTrim("DefaultValue"));
		setdBColumnName(propertyElement.elementTextTrim("DBColumnName"));
		setdBDataType(propertyElement.elementTextTrim("DBDataType"));
		setdBDataLength(propertyElement.elementTextTrim("DBDataLength"));
		setdBDataPrecision(propertyElement.elementTextTrim("DBDataPrecision"));
		setQueryProterty(StringUtil.string2Boolean(propertyElement
				.elementTextTrim("QueryProterty")));
		setPrimaryKey(StringUtil.string2Boolean(propertyElement
				.elementTextTrim("IsPrimaryKey")));
		setUnique(StringUtil.string2Boolean(propertyElement
				.elementTextTrim("Unique")));
		setNullable(StringUtil.string2Boolean(propertyElement
				.elementTextTrim("Nullable")));
		setReadOnly(StringUtil.string2Boolean(propertyElement
				.elementTextTrim("ReadOnly")));
		setIndexedColumn(StringUtil.string2Boolean(propertyElement
				.elementTextTrim("IndexedColumn")));
	}

	public Element generateXmlElement(Element propertiesElement) {
		Element propertyElement = super.generateXmlElement(propertiesElement);// 创建Property节点

		propertyElement.addElement("DefaultValue").addText(
				StringUtil.convertNull2EmptyStr(getDefaultValue()));
		propertyElement.addElement("DBColumnName").addText(
				StringUtil.convertNull2EmptyStr(getdBColumnName()));
		propertyElement.addElement("DBDataType").addText(
				StringUtil.convertNull2EmptyStr(getdBDataType()));
		propertyElement.addElement("DBDataLength").addText(
				StringUtil.convertNull2EmptyStr(getdBDataLength()));
		propertyElement.addElement("DBDataPrecision").addText(
				StringUtil.convertNull2EmptyStr(getdBDataPrecision() + ""));
		propertyElement.addElement("QueryProperty").addText(
				isQueryProterty() ? "true" : "false");
		propertyElement.addElement("IsPrimaryKey").addText(
				isPrimaryKey() ? "true" : "false");
		propertyElement.addElement("Unique").addText(
				isUnique() ? "true" : "false");
		propertyElement.addElement("Nullable").addText(
				isNullable() ? "true" : "false");
		propertyElement.addElement("ReadOnly").addText(
				isReadOnly() ? "true" : "false");
		propertyElement.addElement("IndexedColumn").addText(
				isIndexedColumn() ? "true" : "false");
		propertyElement.addElement("Persistence").addText(
				isPersistence() ? "true" : "false");

		return propertyElement;
	}

	public void copy(AbstractModelElement dest) {
		super.copy(dest);
		((PersistenceProperty) dest).defaultValue = this.defaultValue;
		((PersistenceProperty) dest).dBColumnName = this.dBColumnName;
		((PersistenceProperty) dest).dBDataType = this.dBDataType;
		((PersistenceProperty) dest).dBDataLength = this.dBDataLength;
		((PersistenceProperty) dest).dBDataPrecision = this.dBDataPrecision;
		((PersistenceProperty) dest).isQueryProterty = this.isQueryProterty;
		((PersistenceProperty) dest).isPrimaryKey = this.isPrimaryKey;
		((PersistenceProperty) dest).isUnique = this.isUnique;
		((PersistenceProperty) dest).isNullable = this.isNullable;
		((PersistenceProperty) dest).isReadOnly = this.isReadOnly;
		((PersistenceProperty) dest).isIndexedColumn = this.isIndexedColumn;
	}

	public PersistenceProperty clone() {
		PersistenceProperty property = new PersistenceProperty();
		copy(property);
		PropertyEditor editorClone = (PropertyEditor) ((PropertyEditor) getEditor())
				.clone();
		editorClone.setBelongProperty(property);
		property.setEditor(editorClone);
		Vector<Validator> vaList = getValidators();
		for (Validator va : vaList) {
			Validator val = va.clone();
			val.setBelongProperty(property);
			property.addValidator(val);
		}
		return property;
	}

	public PersistenceProperty cloneChangeId() {
		PersistenceProperty property = new PersistenceProperty();
		copyChangeId(property);
		PropertyEditor editorClone = (PropertyEditor) ((PropertyEditor) getEditor())
				.cloneChangeId();
		editorClone.setBelongProperty(property);
		property.setEditor(editorClone);
		Vector<Validator> vaList = getValidators();
		for (Validator va : vaList) {
			Validator val = va.cloneChangeId();
			val.setBelongProperty(property);
			property.addValidator(val);
		}
		return property;
	}

	public void copyChangeId(AbstractModelElement dest) {
		super.copyChangeId(dest);
		((PersistenceProperty) dest).defaultValue = this.defaultValue;
		((PersistenceProperty) dest).dBColumnName = this.dBColumnName;
		((PersistenceProperty) dest).dBDataType = this.dBDataType;
		((PersistenceProperty) dest).dBDataLength = this.dBDataLength;
		((PersistenceProperty) dest).dBDataPrecision = this.dBDataPrecision;
		((PersistenceProperty) dest).isQueryProterty = this.isQueryProterty;
		((PersistenceProperty) dest).isPrimaryKey = this.isPrimaryKey;
		((PersistenceProperty) dest).isUnique = this.isUnique;
		((PersistenceProperty) dest).isNullable = this.isNullable;
		((PersistenceProperty) dest).isReadOnly = this.isReadOnly;
		((PersistenceProperty) dest).isIndexedColumn = this.isIndexedColumn;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public String getdBColumnName() {
		return dBColumnName;
	}

	public void setdBColumnName(String dBColumnName) {
		this.dBColumnName = dBColumnName;
	}

	public String getdBDataType() {
		return dBDataType;
	}

	public void setdBDataType(String dBDataType) {
		this.dBDataType = dBDataType;
	}

	public String getdBDataLength() {
		return dBDataLength;
	}

	public String getdBDataPrecision() {
		return dBDataPrecision;
	}

	public void setdBDataPrecision(String dBDataPrecision) {
		this.dBDataPrecision = dBDataPrecision;
	}

	public void setdBDataLength(String dBDataLength) {
		this.dBDataLength = dBDataLength;
	}

	public boolean isQueryProterty() {
		return isQueryProterty;
	}

	public void setQueryProterty(boolean isQueryProterty) {
		this.isQueryProterty = isQueryProterty;
	}

	public boolean isPrimaryKey() {
		return isPrimaryKey;
	}

	public void setPrimaryKey(boolean isPrimaryKey) {
		this.isPrimaryKey = isPrimaryKey;
	}

	public boolean isUnique() {
		return isUnique;
	}

	public void setUnique(boolean isUnique) {
		this.isUnique = isUnique;
	}

	public boolean isNullable() {
		return isNullable;
	}

	public void setNullable(boolean isNullable) {
		this.isNullable = isNullable;
	}

	public boolean isReadOnly() {
		return isReadOnly;
	}

	public void setReadOnly(boolean isReadOnly) {
		this.isReadOnly = isReadOnly;
	}

	public boolean isIndexedColumn() {
		return isIndexedColumn;
	}

	public void setIndexedColumn(boolean isIndexedColumn) {
		this.isIndexedColumn = isIndexedColumn;
	}

	public boolean isPersistence() {
		return true;
	}

}
