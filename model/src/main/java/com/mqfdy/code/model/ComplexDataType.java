package com.mqfdy.code.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Element;

import com.mqfdy.code.model.utils.StringUtil;

/**
 * 复合数据类型
 * 
 * @author mqfdy
 * 
 */
public class ComplexDataType extends Property {

	/**
	 * 属性列表
	 */
	private List<Property> properties;

	public ComplexDataType() {
		super();
		properties = new ArrayList<Property>(10);

	}

	@SuppressWarnings("unchecked")
	public ComplexDataType(Element complexDataTypeElement) {
		super(complexDataTypeElement);
		properties = new ArrayList<Property>(10);

		initBasicAttributes(complexDataTypeElement);

		List<Element> propertyElements = complexDataTypeElement
				.elements("Property");
		if (propertyElements != null) {
			for (Iterator<Element> iter = propertyElements.iterator(); iter
					.hasNext();) {
				Element propertyElement = iter.next();
				Property property = null;
				boolean isPrimaryKey = StringUtil
						.string2Boolean(propertyElement
								.elementTextTrim("IsPrimaryKey"));
				boolean persistence = StringUtil.string2Boolean(propertyElement
						.elementTextTrim("Persistence"));
				if (persistence && isPrimaryKey) {// 是PersistenceProperty类型
					property = new PKProperty(propertyElement);
				} else if (persistence) {
					property = new PersistenceProperty(propertyElement);
				} else {
					property = new Property(propertyElement);
				}
				// property.setParent(this);
				addProperty(property);
			}
		}
	}

	public int getPriority() {
		return IModelElement.PRIORITY_COMPLEXDATATYPE;
	}

	public List<Property> getProperties() {
		return properties;
	}

	public void setProperties(List<Property> properties) {
		this.properties = properties;
	}

	public void addProperty(Property property) {
		properties.add(property);
	}

}
