package com.mqfdy.code.model;

import java.util.List;

import org.dom4j.Element;

import com.mqfdy.code.model.utils.SqlLogicOperateType;
import com.mqfdy.code.model.utils.SqlOperateType;

public class QueryCondition extends AbstractModelElement {
	/**
	 * 所属编辑器
	 */
	private PropertyEditor editor;
	/**
	 * 是否有前括号
	 */
	private String preBraces;
	/**
	 * 属性名称
	 */
	private String propertyName;
	
	/**
	 * 数据类型
	 */
	private String dataType;
	/**
	 * 操作符
	 */
	private SqlOperateType sqlOperateType = SqlOperateType.NONE;
	/**
	 * 值1
	 */
	private String value1;
	/**
	 * 值2
	 */
	private String value2;
	/**
	 * 后括号
	 */
	private String afterBraces;
	/**
	 * 逻辑操作符类型
	 */
	private SqlLogicOperateType sqlLogicOperateType = SqlLogicOperateType.NONE;

	public QueryCondition() {
		this.setPreBraces("");
		this.setPropertyName("");
		this.setValue1("");
		this.setAfterBraces("");
	}

	/**
	 * 构造方法
	 * 
	 * @param editorElement
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

	public AbstractModelElement getParent() {
		return this.editor;
	}

	public List<?> getChildren() {
		return null;
	}

	public PropertyEditor getEditor() {
		return editor;
	}

	public void setEditor(PropertyEditor editor) {
		this.editor = editor;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public SqlOperateType getSqlOperateType() {
		return sqlOperateType;
	}

	public void setSqlOperateType(SqlOperateType sqlOperateType) {
		this.sqlOperateType = sqlOperateType;
	}

	public String getValue1() {
		return value1;
	}

	public void setValue1(String value1) {
		this.value1 = value1;
	}

	public String getValue2() {
		return value2;
	}

	public void setValue2(String value2) {
		this.value2 = value2;
	}

	public SqlLogicOperateType getSqlLogicOperateType() {
		return sqlLogicOperateType;
	}

	public void setSqlLogicOperateType(SqlLogicOperateType sqlLogicOperateType) {
		this.sqlLogicOperateType = sqlLogicOperateType;
	}

	public String getPreBraces() {
		return preBraces;
	}

	public void setPreBraces(String preBraces) {
		this.preBraces = preBraces;
	}

	public String getAfterBraces() {
		return afterBraces;
	}

	public void setAfterBraces(String afterBraces) {
		this.afterBraces = afterBraces;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
}
