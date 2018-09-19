package com.mqfdy.code.model;

import java.util.Vector;

import org.dom4j.Element;

import com.mqfdy.code.model.utils.StringUtil;

// TODO: Auto-generated Javadoc
/**
 * 持久化属性.
 *
 * @author LQR
 */
public class PersistenceProperty extends Property {

	/** 默认值. */
	private String defaultValue;

	/** 数据库对应字段名. */
	private String dBColumnName;

	/** 数据库字段类型. */
	private String dBDataType;

	/** 字段长度. */
	private String dBDataLength;

	/** 字段精度. */
	private String dBDataPrecision;

	/** 是否 查询属性. */
	private boolean isQueryProterty;

	/** 是否主键. */
	private boolean isPrimaryKey;

	/** 是否唯一. */
	private boolean isUnique;

	/** 是否可空. */
	private boolean isNullable;

	/** 是否唯一. */
	private boolean isReadOnly;

	/** 是否索引列. */
	private boolean isIndexedColumn;

	/**
	 * Instantiates a new persistence property.
	 */
	public PersistenceProperty() {
		super();
	}

	/**
	 * 通过 XML 构造 PersistenceProperty.
	 *
	 * @param propertyElement
	 *            the property element
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

	/**
	 * @param propertiesElement
	 * @return
	 */
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

	/**
	 * @param dest
	 */
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

	/**
	 * @return
	 */
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

	/**
	 * @return
	 */
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

	/**
	 * @param dest
	 */
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
	 * Gets the d B column name.
	 *
	 * @author mqfdy
	 * @return the d B column name
	 * @Date 2018-09-03 09:00
	 */
	public String getdBColumnName() {
		return dBColumnName;
	}

	/**
	 * Sets the d B column name.
	 *
	 * @author mqfdy
	 * @param dBColumnName
	 *            the new d B column name
	 * @Date 2018-09-03 09:00
	 */
	public void setdBColumnName(String dBColumnName) {
		this.dBColumnName = dBColumnName;
	}

	/**
	 * Gets the d B data type.
	 *
	 * @author mqfdy
	 * @return the d B data type
	 * @Date 2018-09-03 09:00
	 */
	public String getdBDataType() {
		return dBDataType;
	}

	/**
	 * Sets the d B data type.
	 *
	 * @author mqfdy
	 * @param dBDataType
	 *            the new d B data type
	 * @Date 2018-09-03 09:00
	 */
	public void setdBDataType(String dBDataType) {
		this.dBDataType = dBDataType;
	}

	/**
	 * Gets the d B data length.
	 *
	 * @author mqfdy
	 * @return the d B data length
	 * @Date 2018-09-03 09:00
	 */
	public String getdBDataLength() {
		return dBDataLength;
	}

	/**
	 * Gets the d B data precision.
	 *
	 * @author mqfdy
	 * @return the d B data precision
	 * @Date 2018-09-03 09:00
	 */
	public String getdBDataPrecision() {
		return dBDataPrecision;
	}

	/**
	 * Sets the d B data precision.
	 *
	 * @author mqfdy
	 * @param dBDataPrecision
	 *            the new d B data precision
	 * @Date 2018-09-03 09:00
	 */
	public void setdBDataPrecision(String dBDataPrecision) {
		this.dBDataPrecision = dBDataPrecision;
	}

	/**
	 * Sets the d B data length.
	 *
	 * @author mqfdy
	 * @param dBDataLength
	 *            the new d B data length
	 * @Date 2018-09-03 09:00
	 */
	public void setdBDataLength(String dBDataLength) {
		this.dBDataLength = dBDataLength;
	}

	/**
	 * Checks if is query proterty.
	 *
	 * @author mqfdy
	 * @return true, if is query proterty
	 * @Date 2018-09-03 09:00
	 */
	public boolean isQueryProterty() {
		return isQueryProterty;
	}

	/**
	 * Sets the query proterty.
	 *
	 * @author mqfdy
	 * @param isQueryProterty
	 *            the new query proterty
	 * @Date 2018-09-03 09:00
	 */
	public void setQueryProterty(boolean isQueryProterty) {
		this.isQueryProterty = isQueryProterty;
	}

	/**
	 * @return
	 */
	public boolean isPrimaryKey() {
		return isPrimaryKey;
	}

	/**
	 * @param isPrimaryKey
	 */
	public void setPrimaryKey(boolean isPrimaryKey) {
		this.isPrimaryKey = isPrimaryKey;
	}

	/**
	 * Checks if is unique.
	 *
	 * @author mqfdy
	 * @return true, if is unique
	 * @Date 2018-09-03 09:00
	 */
	public boolean isUnique() {
		return isUnique;
	}

	/**
	 * Sets the unique.
	 *
	 * @author mqfdy
	 * @param isUnique
	 *            the new unique
	 * @Date 2018-09-03 09:00
	 */
	public void setUnique(boolean isUnique) {
		this.isUnique = isUnique;
	}

	/**
	 * @return
	 */
	public boolean isNullable() {
		return isNullable;
	}

	/**
	 * @param isNullable
	 */
	public void setNullable(boolean isNullable) {
		this.isNullable = isNullable;
	}

	/**
	 * @return
	 */
	public boolean isReadOnly() {
		return isReadOnly;
	}

	/**
	 * @param isReadOnly
	 */
	public void setReadOnly(boolean isReadOnly) {
		this.isReadOnly = isReadOnly;
	}

	/**
	 * Checks if is indexed column.
	 *
	 * @author mqfdy
	 * @return true, if is indexed column
	 * @Date 2018-09-03 09:00
	 */
	public boolean isIndexedColumn() {
		return isIndexedColumn;
	}

	/**
	 * Sets the indexed column.
	 *
	 * @author mqfdy
	 * @param isIndexedColumn
	 *            the new indexed column
	 * @Date 2018-09-03 09:00
	 */
	public void setIndexedColumn(boolean isIndexedColumn) {
		this.isIndexedColumn = isIndexedColumn;
	}

	/**
	 * @return
	 */
	public boolean isPersistence() {
		return true;
	}

}
