package com.mqfdy.code.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Element;

import com.mqfdy.code.model.utils.StringUtil;

// TODO: Auto-generated Javadoc
/**
 * 复合数据类型.
 *
 * @author mqfdy
 */
public class ComplexDataType extends Property {

	/** 属性列表. */
	private List<Property> properties;

	/**
	 * Instantiates a new complex data type.
	 */
	public ComplexDataType() {
		super();
		properties = new ArrayList<Property>(10);

	}

	/**
	 * Instantiates a new complex data type.
	 *
	 * @param complexDataTypeElement
	 *            the complex data type element
	 */
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

	/**
	 * @return
	 */
	public int getPriority() {
		return IModelElement.PRIORITY_COMPLEXDATATYPE;
	}

	/**
	 * Gets the properties.
	 *
	 * @author mqfdy
	 * @return the properties
	 * @Date 2018-09-03 09:00
	 */
	public List<Property> getProperties() {
		return properties;
	}

	/**
	 * Sets the properties.
	 *
	 * @author mqfdy
	 * @param properties
	 *            the new properties
	 * @Date 2018-09-03 09:00
	 */
	public void setProperties(List<Property> properties) {
		this.properties = properties;
	}

	/**
	 * Adds the property.
	 *
	 * @author mqfdy
	 * @param property
	 *            the property
	 * @Date 2018-09-03 09:00
	 */
	public void addProperty(Property property) {
		properties.add(property);
	}

}
