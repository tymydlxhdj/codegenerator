package com.mqfdy.code.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Element;

import com.mqfdy.code.model.utils.StringUtil;

// TODO: Auto-generated Javadoc
/**
 * 属性编辑器.
 *
 * @author mqfdy
 */
public class PropertyEditor extends AbstractModelElement {

	/** The Constant DATASOURCE_TYPE_ENUM. */
	public static final int DATASOURCE_TYPE_ENUM = 0;
	
	/** The Constant DATASOURCE_TYPE_BUSINESS. */
	public static final int DATASOURCE_TYPE_BUSINESS = 1;
	
	/** The Constant DATASOURCE_TYPE_CUSTOM. */
	public static final int DATASOURCE_TYPE_CUSTOM = 2;

	/** The Constant PARAM_KEY_ENUMERATION_ID. */
	public static final String PARAM_KEY_ENUMERATION_ID = "EnumerationId";
	
	/** The Constant PARAM_KEY_BUSINESSCLASS_ID. */
	public static final String PARAM_KEY_BUSINESSCLASS_ID = "BusinessClassId";
	
	/** The Constant PARAM_KEY_KEY_PROPERTY_ID. */
	public static final String PARAM_KEY_KEY_PROPERTY_ID = "Key_PropertyId";
	
	/** The Constant PARAM_KEY_VALUE_PROPERTY_ID. */
	public static final String PARAM_KEY_VALUE_PROPERTY_ID = "Value_PropertyId";
	
	/** The Constant PARAM_KEY_FILTER_SQL. */
	public static final String PARAM_KEY_FILTER_SQL = "FilterSql";

	/** 所属属性. */
	private Property belongProperty;

	/** 编辑器类型. */
	private String editorType;

	/** The data source type. */
	private int dataSourceType;

	/** The editor data. */
	private List<EnumElement> editorData;

	/** 参数. */
	private Map<String, String> editorParams;

	/** The conditions. */
	private List<QueryCondition> conditions;

	/**
	 * Instantiates a new property editor.
	 */
	public PropertyEditor() {
		editorParams = new HashMap<String, String>();
		editorData = new ArrayList<EnumElement>();
		conditions = new ArrayList<QueryCondition>();
	}

	/**
	 * 通过 XML 构造 PropertyEditor.
	 *
	 * @param editorElement
	 *            the editor element
	 */
	@SuppressWarnings("unchecked")
	public PropertyEditor(Element editorElement) {

		initBasicAttributes(editorElement);

		editorParams = new HashMap<String, String>();
		editorData = new ArrayList<EnumElement>();
		conditions = new ArrayList<QueryCondition>();

		String editorType = editorElement.attributeValue("editorType");
		setEditorType(editorType);
		String dataSourceType = editorElement.attributeValue("dataSourceType");
		setDataSourceType(StringUtil.string2Int(dataSourceType));

		// 获取Property节点下的Editor节点下的Params节点
		Element paramsElement = editorElement.element("Params");
		if (paramsElement != null) {
			List<Element> paramElements = paramsElement.elements("Param");// //获取Params节点下的Param子节点集合
			if (paramElements != null) {
				for (Iterator<Element> iter = paramElements.iterator(); iter
						.hasNext();) {
					Element paramElement = iter.next();// Param节点
					String key = paramElement.elementTextTrim("name");
					String value = "";
					if ("FilterSql".equals(key)) {
						value = paramElement.elementText("value");
					} else {
						value = paramElement.elementTextTrim("value");
					}
					editorParams.put(key, value);
				}
			}

		}
		// 获取Property节点下的Editor节点下的Datas节点
		Element datasElement = editorElement.element("Datas");
		if (datasElement != null) {
			List<Element> dataElements = datasElement.elements("Data");// //获取Datas节点下的Data子节点集合
			if (dataElements != null) {
				for (Iterator<Element> iter = dataElements.iterator(); iter
						.hasNext();) {
					Element dataElement = iter.next();// Data节点
					String key = dataElement.elementTextTrim("name");
					String value = dataElement.elementTextTrim("value");
					editorData.add(new EnumElement(key, value));
				}
			}
		}

		Element conditionsElement = editorElement.element("Conditions");
		if (conditionsElement != null) {
			List<Element> conditoinElements = conditionsElement
					.elements("Condition");// 获取Condition节点下的Data子节点集合
			if (conditoinElements != null) {
				for (Iterator<Element> iter = conditoinElements.iterator(); iter
						.hasNext();) {
					Element conditionElement = iter.next();// Data节点
					QueryCondition conditon = new QueryCondition(
							conditionElement);
					conditions.add(conditon);
				}
			}
		}
	}

	/**
	 * @param propertyElement
	 * @return
	 */
	public Element generateXmlElement(Element propertyElement) {
		Element editorElement = propertyElement.addElement("Editor");// Editor节点
		this.generateBasicAttributes(editorElement);// 设置Editor节点的一般属性
		editorElement.addAttribute("editorType",
				StringUtil.convertNull2EmptyStr(getEditorType()));// 设置editorType特有属性
		editorElement.addAttribute("dataSourceType", getDataSourceType() + "");// 设置editorType特有属性
		Map<String, String> params = getEditorParams();
		if (params != null && !params.isEmpty()) {
			Element paramsElement = editorElement.addElement("Params");
			generateParam(paramsElement, params);
			// editorElement.addContent(paramsElement);
		}

		List<EnumElement> datas = getEditorData();
		if (datas != null && !datas.isEmpty()) {
			Element datasElement = editorElement.addElement("Datas");// Datas节点
			// editorElement.addContent(datasElement);
			for (EnumElement e : datas) {
				Element dataElement = datasElement.addElement("Data");// Data节点
				// datasElement.addContent(dataElement);

				Element nameElement = dataElement.addElement("name");// Data节点下name节点
				nameElement
						.addText(StringUtil.convertNull2EmptyStr(e.getKey()));
				Element valueElement = dataElement.addElement("value");// Data节点下value节点
				valueElement.addText(StringUtil.convertNull2EmptyStr(e
						.getValue()));
			}
		}

		if (conditions != null && !conditions.isEmpty()) {
			Element conditionsElement = editorElement.addElement("Conditions");// Conditions节点
			for (QueryCondition condition : conditions) {
				condition.generateXmlElement(conditionsElement);
			}
		}
		return editorElement;
	}

	/**
	 * Gets the editor type.
	 *
	 * @author mqfdy
	 * @return the editor type
	 * @Date 2018-09-03 09:00
	 */
	public String getEditorType() {
		return editorType;
	}

	/**
	 * Sets the editor type.
	 *
	 * @author mqfdy
	 * @param editorType
	 *            the new editor type
	 * @Date 2018-09-03 09:00
	 */
	public void setEditorType(String editorType) {
		this.editorType = editorType;
	}

	/**
	 * Gets the editor params.
	 *
	 * @author mqfdy
	 * @return the editor params
	 * @Date 2018-09-03 09:00
	 */
	public Map<String, String> getEditorParams() {
		return editorParams;
	}

	/**
	 * Adds the editor param.
	 *
	 * @author mqfdy
	 * @param key
	 *            the key
	 * @param value
	 *            the value
	 * @Date 2018-09-03 09:00
	 */
	public void addEditorParam(String key, String value) {
		editorParams.put(key, value);
	}

	/**
	 * Gets the belong property.
	 *
	 * @author mqfdy
	 * @return the belong property
	 * @Date 2018-09-03 09:00
	 */
	public Property getBelongProperty() {
		return belongProperty;
	}

	/**
	 * Sets the belong property.
	 *
	 * @author mqfdy
	 * @param belongProperty
	 *            the new belong property
	 * @Date 2018-09-03 09:00
	 */
	public void setBelongProperty(Property belongProperty) {
		this.belongProperty = belongProperty;
	}

	/**
	 * Gets the data source type.
	 *
	 * @author mqfdy
	 * @return the data source type
	 * @Date 2018-09-03 09:00
	 */
	public int getDataSourceType() {
		return dataSourceType;
	}

	/**
	 * Sets the data source type.
	 *
	 * @author mqfdy
	 * @param dataSourceType
	 *            the new data source type
	 * @Date 2018-09-03 09:00
	 */
	public void setDataSourceType(int dataSourceType) {
		this.dataSourceType = dataSourceType;
	}

	/**
	 * Gets the editor data.
	 *
	 * @author mqfdy
	 * @return the editor data
	 * @Date 2018-09-03 09:00
	 */
	public List<EnumElement> getEditorData() {
		return editorData;
	}

	/**
	 * Sets the editor data.
	 *
	 * @author mqfdy
	 * @param editorData
	 *            the new editor data
	 * @Date 2018-09-03 09:00
	 */
	public void setEditorData(List<EnumElement> editorData) {
		this.editorData.clear();
		this.editorData.addAll(editorData);
	}

	/**
	 * Sets the editor params.
	 *
	 * @author mqfdy
	 * @param editorParams
	 *            the editor params
	 * @Date 2018-09-03 09:00
	 */
	public void setEditorParams(Map<String, String> editorParams) {
		this.editorParams = editorParams;
	}

	/**
	 * @return
	 */
	public AbstractModelElement getParent() {
		return this.belongProperty;
	}

	/**
	 * @return
	 */
	public List<AbstractModelElement> getChildren() {
		return EMPTY_CHILD;
	}

	/**
	 * Gets the conditions.
	 *
	 * @author mqfdy
	 * @return the conditions
	 * @Date 2018-09-03 09:00
	 */
	public List<QueryCondition> getConditions() {
		return conditions;
	}

	/**
	 * Sets the conditions.
	 *
	 * @author mqfdy
	 * @param conditions
	 *            the new conditions
	 * @Date 2018-09-03 09:00
	 */
	public void setConditions(List<QueryCondition> conditions) {
		this.conditions = conditions;
	}

	/**
	 * @return
	 */
	public PropertyEditor clone() {
		PropertyEditor propertyEditor = new PropertyEditor();
		propertyEditor.dataSourceType = this.dataSourceType;
		propertyEditor.conditions = this.conditions;
		propertyEditor.editorType = this.editorType;
		propertyEditor.editorData = this.editorData;
		propertyEditor.editorParams = this.editorParams;
		copy(propertyEditor);
		return propertyEditor;
	}

	/**
	 * @return
	 */
	public PropertyEditor cloneChangeId() {
		PropertyEditor propertyEditor = new PropertyEditor();
		propertyEditor.dataSourceType = this.dataSourceType;
		propertyEditor.conditions = this.conditions;
		propertyEditor.editorType = this.editorType;
		propertyEditor.editorData = this.editorData;
		propertyEditor.editorParams = this.editorParams;
		super.copyChangeId(propertyEditor);
		return propertyEditor;
	}

	/**
	 * @param dest
	 */
	public void copy(AbstractModelElement dest) {
		super.copy(dest);
	}
}
