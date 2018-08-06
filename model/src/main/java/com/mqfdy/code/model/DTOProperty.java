package com.mqfdy.code.model;

import java.util.List;

import org.dom4j.Element;

import com.mqfdy.code.model.utils.StringUtil;

/**
 * 数据传输对象属性
 * 
 * @author mqfdy
 * 
 */
public class DTOProperty extends Property {

	/**
	 * 属性来源至业务类时对应的业务类
	 */
	private BusinessClass sourceBusinessClass;

	private Property sourceProperty;

	public DTOProperty() {

	}

	public DTOProperty(Element propertyElement, BusinessObjectModel bom) {

		super(propertyElement);

		// 获取Property节点中的sourceClass,sourceClassProperty属性值，
		// 分别转换为BusinessEntity对象和DTOProperty对象设值
		String sourceClassId = propertyElement.attributeValue("sourceClass");
		String sourceClassPropertyId = propertyElement
				.attributeValue("sourceClassProperty");

		if (!StringUtil.isEmpty(sourceClassId)
				&& !StringUtil.isEmpty(sourceClassPropertyId)) {// 如果存在引用属性
			BusinessClass sourceBusinessClass = null;
			List<BusinessClass> bcs = bom.getBusinessClasses();
			for (BusinessClass bc : bcs) {
				if (bc.getId().equalsIgnoreCase(sourceClassId)) {
					sourceBusinessClass = bc;
					setSourceBusinessClass(bc);
					break;
				}
			}
			if (sourceBusinessClass != null
					&& sourceBusinessClass.getProperties() != null) {
				List<Property> properties = sourceBusinessClass.getProperties();
				for (Property property : properties) {
					if (sourceClassPropertyId
							.equalsIgnoreCase(property.getId())) {
						setSourceProperty(property);
						this.setDataType(property.getDataType());
						break;
					}
				}
			}
		}
	}

	public Element generateXmlElement(Element propertiesElement) {
		Element propertyElement = super.generateXmlElement(propertiesElement);// 创建Property节点
		// 创建property节点的sourceClass，sourceClassProperty属性
		propertyElement
				.addAttribute(
						"sourceClass",
						getSourceBusinessClass() == null ? "" : StringUtil
								.convertNull2EmptyStr(getSourceBusinessClass()
										.getId()));
		propertyElement.addAttribute(
				"sourceClassProperty",
				getSourceProperty() == null ? "" : StringUtil
						.convertNull2EmptyStr(getSourceProperty().getId()));
		if (getSourceProperty() != null) {// 如果引用的存在，就用引用属性的id，name，displayName
			getSourceProperty().generateBasicAttributes(propertyElement);
		}
		this.generateBasicAttributes(propertyElement);// 如果自己有的话，就用自己的
		return propertyElement;
	}

	public BusinessClass getSourceBusinessClass() {
		return sourceBusinessClass;
	}

	public void setSourceBusinessClass(BusinessClass sourceBusinessClass) {
		this.sourceBusinessClass = sourceBusinessClass;
	}

	public Property getSourceProperty() {
		return sourceProperty;
	}

	public void setSourceProperty(Property sourceProperty) {
		this.sourceProperty = sourceProperty;
	}

}
