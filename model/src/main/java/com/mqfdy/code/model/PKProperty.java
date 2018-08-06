package com.mqfdy.code.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.dom4j.Element;

import com.mqfdy.code.model.utils.StringUtil;

/**
 * 业务主键列属性
 * 
 * @author mqfdy
 * 
 */
public class PKProperty extends PersistenceProperty {

	public static final String PRIMARYKEY_PREFIX = "PRIMARY_KEY_";// 序列名

	public static final String PARAM_KEY_SEQUENCENAME = "suquenceName";// 序列名

	/**
	 * 主键名称
	 */
	private String pkName;
	/**
	 * 主键生成策略
	 */
	private String primaryKeyPloy;

	/**
	 * 参数列表
	 */
	private Map<String, String> primaryKeyParams;

	public PKProperty() {
		super();
		primaryKeyParams = new HashMap<String, String>(10);

	}

	public void copy(AbstractModelElement dest) {
		super.copy(dest);
		((PKProperty) dest).pkName = this.pkName;
		((PKProperty) dest).primaryKeyPloy = this.primaryKeyPloy;
	}

	public PKProperty clone() {
		PKProperty property = new PKProperty();
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

	public PKProperty cloneChangeId() {
		PKProperty property = new PKProperty();
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
		((PKProperty) dest).pkName = this.pkName;
		((PKProperty) dest).primaryKeyPloy = this.primaryKeyPloy;
	}

	/**
	 * 通过 XML 构造 PKProperty
	 * 
	 * @param propertyElement
	 */
	@SuppressWarnings("unchecked")
	public PKProperty(Element propertyElement) {
		super(propertyElement);
		primaryKeyParams = new HashMap<String, String>(10);
		setPrimaryKeyPloy(propertyElement.elementTextTrim("PrimaryKeyPloy"));
		setPkName(propertyElement.elementTextTrim("PkName"));

		// 获取Property节点下的PrimaryKeyParams节点内容
		Element primaryKeyParamsElement = propertyElement
				.element("PrimaryKeyParams");// PrimaryKeyPloyParams节点
		if (primaryKeyParamsElement != null) {
			List<Element> paramElements = primaryKeyParamsElement
					.elements("Param");
			if (paramElements != null) {
				for (Iterator<Element> iter = paramElements.iterator(); iter
						.hasNext();) {
					Element paramElement = iter.next();// Param节点
					String nameText = paramElement.elementTextTrim("name");
					String valueText = paramElement.elementTextTrim("value");
					primaryKeyParams.put(nameText, valueText);
				}
			}

		}
	}

	public Element generateXmlElement(Element propertiesElement) {
		Element propertyElement = super.generateXmlElement(propertiesElement);// 创建Property节点
		propertyElement.addElement("PkName").addText(
				StringUtil.convertNull2EmptyStr(getPkName()));
		// 在Property节点下添加PrimaryKeyPloy节点
		propertyElement.addElement("PrimaryKeyPloy").addText(
				StringUtil.convertNull2EmptyStr(getPrimaryKeyPloy()));
		// 在Property节点下添加PrimaryKeyParams节点，如果没有就不创建
		Map<String, String> params = getPrimaryKeyParams();
		if (!params.isEmpty()) {
			Element paramsElement = propertyElement
					.addElement("PrimaryKeyParams");// PrimaryKeyParams节点
			generateParam(paramsElement, params);
			// propertyElement.addContent(paramsElement);
		}
		return propertyElement;
	}

	public String getPkName() {
		return pkName;
	}

	public void setPkName(String pkName) {
		this.pkName = pkName;
	}

	public String getPrimaryKeyPloy() {
		return primaryKeyPloy;
	}

	public void setPrimaryKeyPloy(String primaryKeyPloy) {
		this.primaryKeyPloy = primaryKeyPloy;
	}

	public Map<String, String> getPrimaryKeyParams() {
		return primaryKeyParams;
	}

	public Map<String, String> setPrimaryKeyParams(Map<String, String> map) {
		return this.primaryKeyParams = map;
	}

	public void addPrimaryKeyParam(String pName, String pValue) {
		primaryKeyParams.put(pName, pValue);
	}

}
