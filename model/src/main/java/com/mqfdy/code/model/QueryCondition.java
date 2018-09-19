package com.mqfdy.code.model;

import java.util.List;

import org.dom4j.Element;

import com.mqfdy.code.model.utils.SqlLogicOperateType;
import com.mqfdy.code.model.utils.SqlOperateType;

// TODO: Auto-generated Javadoc
/**
 * The Class QueryCondition.
 *
 * @author mqfdy
 */
public class QueryCondition extends AbstractModelElement {
	
	/** 所属编辑器. */
	private PropertyEditor editor;
	
	/** 是否有前括号. */
	private String preBraces;
	
	/** 属性名称. */
	private String propertyName;
	
	/** 数据类型. */
	private String dataType;
	
	/** 操作符. */
	private SqlOperateType sqlOperateType = SqlOperateType.NONE;
	
	/** 值1. */
	private String value1;
	
	/** 值2. */
	private String value2;
	
	/** 后括号. */
	private String afterBraces;
	
	/** 逻辑操作符类型. */
	private SqlLogicOperateType sqlLogicOperateType = SqlLogicOperateType.NONE;

	/**
	 * Instantiates a new query condition.
	 */
	public QueryCondition() {
		this.setPreBraces("");
		this.setPropertyName("");
		this.setValue1("");
		this.setAfterBraces("");
	}

	/**
	 * 构造方法.
	 *
	 * @param editorElement
	 *            the editor element
	 */
	public QueryCondition(Element editorElement) {
		initBasicAttributes(editorElement);
		String preBraces = editorElement.attributeValue("preBraces");
		String propertyName = editorElement.attributeValue("propertyName");
		String dataType = editorElement.attributeValue("dataType");
		String sqlOperateType = editorElement.attributeValue("sqlOperateType");// id
		String value1 = editorElement.attributeValue("value1");
		String value2 = editorElement.attributeValue("value2");
		String afterBraces = editorElement.attributeValue("afterBraces");
		String sqlLogicOperateType = editorElement
				.attributeValue("sqlLogicOperateType");// id

		this.setPreBraces(preBraces);
		this.setPropertyName(propertyName);
		this.setDataType(dataType);
		this.setSqlOperateType(SqlOperateType
				.getSqlOperateTypeById(sqlOperateType));
		this.setValue1(value1);
		this.setValue2(value2);
		this.setAfterBraces(afterBraces);
		this.setSqlLogicOperateType(SqlLogicOperateType
				.getSqlLogicOperateTypeById(sqlLogicOperateType));
	}

	/**
	 * @param conditionsElement
	 * @return
	 */
	public Element generateXmlElement(Element conditionsElement) {
		Element conditionElement = conditionsElement.addElement("Condition");// Condition节点
		conditionElement.addAttribute("id", getId());
		conditionElement.addAttribute("name", getName());
		conditionElement.addAttribute("preBraces", getPreBraces());
		conditionElement.addAttribute("propertyName", getPropertyName());
		conditionElement.addAttribute("dataType", getDataType());
		conditionElement.addAttribute("sqlOperateType", getSqlOperateType()
				.getId());
		conditionElement.addAttribute("value1", getValue1());
		conditionElement.addAttribute("value2", getValue2());
		conditionElement.addAttribute("afterBraces", getAfterBraces());
		conditionElement.addAttribute("sqlLogicOperateType",
				getSqlLogicOperateType().getId());

		return conditionElement;
	}

	/**
	 * @return
	 */
	public AbstractModelElement getParent() {
		return this.editor;
	}

	/**
	 * @return
	 */
	public List<?> getChildren() {
		return null;
	}

	/**
	 * Gets the editor.
	 *
	 * @author mqfdy
	 * @return the editor
	 * @Date 2018-09-03 09:00
	 */
	public PropertyEditor getEditor() {
		return editor;
	}

	/**
	 * Sets the editor.
	 *
	 * @author mqfdy
	 * @param editor
	 *            the new editor
	 * @Date 2018-09-03 09:00
	 */
	public void setEditor(PropertyEditor editor) {
		this.editor = editor;
	}

	/**
	 * Gets the property name.
	 *
	 * @author mqfdy
	 * @return the property name
	 * @Date 2018-09-03 09:00
	 */
	public String getPropertyName() {
		return propertyName;
	}

	/**
	 * Sets the property name.
	 *
	 * @author mqfdy
	 * @param propertyName
	 *            the new property name
	 * @Date 2018-09-03 09:00
	 */
	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	/**
	 * Gets the sql operate type.
	 *
	 * @author mqfdy
	 * @return the sql operate type
	 * @Date 2018-09-03 09:00
	 */
	public SqlOperateType getSqlOperateType() {
		return sqlOperateType;
	}

	/**
	 * Sets the sql operate type.
	 *
	 * @author mqfdy
	 * @param sqlOperateType
	 *            the new sql operate type
	 * @Date 2018-09-03 09:00
	 */
	public void setSqlOperateType(SqlOperateType sqlOperateType) {
		this.sqlOperateType = sqlOperateType;
	}

	/**
	 * Gets the value 1.
	 *
	 * @author mqfdy
	 * @return the value 1
	 * @Date 2018-09-03 09:00
	 */
	public String getValue1() {
		return value1;
	}

	/**
	 * Sets the value 1.
	 *
	 * @author mqfdy
	 * @param value1
	 *            the new value 1
	 * @Date 2018-09-03 09:00
	 */
	public void setValue1(String value1) {
		this.value1 = value1;
	}

	/**
	 * Gets the value 2.
	 *
	 * @author mqfdy
	 * @return the value 2
	 * @Date 2018-09-03 09:00
	 */
	public String getValue2() {
		return value2;
	}

	/**
	 * Sets the value 2.
	 *
	 * @author mqfdy
	 * @param value2
	 *            the new value 2
	 * @Date 2018-09-03 09:00
	 */
	public void setValue2(String value2) {
		this.value2 = value2;
	}

	/**
	 * Gets the sql logic operate type.
	 *
	 * @author mqfdy
	 * @return the sql logic operate type
	 * @Date 2018-09-03 09:00
	 */
	public SqlLogicOperateType getSqlLogicOperateType() {
		return sqlLogicOperateType;
	}

	/**
	 * Sets the sql logic operate type.
	 *
	 * @author mqfdy
	 * @param sqlLogicOperateType
	 *            the new sql logic operate type
	 * @Date 2018-09-03 09:00
	 */
	public void setSqlLogicOperateType(SqlLogicOperateType sqlLogicOperateType) {
		this.sqlLogicOperateType = sqlLogicOperateType;
	}

	/**
	 * Gets the pre braces.
	 *
	 * @author mqfdy
	 * @return the pre braces
	 * @Date 2018-09-03 09:00
	 */
	public String getPreBraces() {
		return preBraces;
	}

	/**
	 * Sets the pre braces.
	 *
	 * @author mqfdy
	 * @param preBraces
	 *            the new pre braces
	 * @Date 2018-09-03 09:00
	 */
	public void setPreBraces(String preBraces) {
		this.preBraces = preBraces;
	}

	/**
	 * Gets the after braces.
	 *
	 * @author mqfdy
	 * @return the after braces
	 * @Date 2018-09-03 09:00
	 */
	public String getAfterBraces() {
		return afterBraces;
	}

	/**
	 * Sets the after braces.
	 *
	 * @author mqfdy
	 * @param afterBraces
	 *            the new after braces
	 * @Date 2018-09-03 09:00
	 */
	public void setAfterBraces(String afterBraces) {
		this.afterBraces = afterBraces;
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
}
