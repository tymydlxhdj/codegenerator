package com.mqfdy.code.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Element;

/**
 * 数据传输对象
 * 
 * @author mqfdy
 * 
 */
public class DataTransferObject extends AbstractModelElement {
	/**
	 * 所属包
	 */
	private ModelPackage belongPackage;

	/**
	 * 属性列表
	 */
	private List<Property> properties;

	/**
	 * 版本信息
	 */
	private VersionInfo versionInfo;

	public DataTransferObject() {

		properties = new ArrayList<Property>(50);

	}

	/**
	 * 通过 XML 构造 DataTransferObject
	 * 
	 * @param dtoElement
	 */
	@SuppressWarnings("unchecked")
	public DataTransferObject(Element dtoElement, BusinessObjectModel bom) {

		properties = new ArrayList<Property>(50);

		initBasicAttributes(dtoElement);
		// 组装dto对象中的VersionInfo对象 ok
		Element versionInfoElement = dtoElement.element("VersionInfo");
		if (versionInfoElement != null) {
			VersionInfo versionInfo = new VersionInfo(versionInfoElement);
			setVersionInfo(versionInfo);
		}

		// 获取DTO节点下的Properties节点值
		Element propertiesElement = dtoElement.element("Properties");
		if (propertiesElement != null) {
			// 获取Properties节点下的Property节点集合
			List<Element> propertyElements = propertiesElement
					.elements("Property");
			for (Iterator<Element> iter = propertyElements.iterator(); iter
					.hasNext();) {
				Element propertyElement = iter.next();// Property节点
				DTOProperty property = new DTOProperty(propertyElement, bom);// Property对象
				addProperty(property);
			}
		}
	}

	public Element generateXmlElement(Element dtosElement) {
		Element dtoElement = dtosElement.addElement("DTO");
		this.generateBasicAttributes(dtoElement);

		// 创建dto节点下的VersionInfo节点，如果没有就不创建
		if (getVersionInfo() != null) {
			getVersionInfo().generateXmlElement(dtoElement);
			// dtoElement.addContent(versionInfoElement);
		}

		// 在DTO节点下创建Properties和Property节点，没有就不创建
		List<Property> properties = getProperties();
		if (properties != null && !properties.isEmpty()) {
			Element propertiesElement = dtoElement.addElement("Properties");// Properties节点
			// dtoElement.addContent(propertiesElement);
			for (Property property : properties) {
				property.generateXmlElement(propertiesElement);
				// propertiesElement.addContent(propertyElement);
			}
		}

		return dtoElement;
	}

	public int getPriority() {
		return IModelElement.PRIORITY_DTO;
	}

	public List<Property> getProperties() {
		return properties;
	}

	public void setProperties(List<Property> properties) {
		this.properties = properties;
	}

	public void addProperty(DTOProperty property) {
		properties.add(property);
	}

	public ModelPackage getBelongPackage() {
		return belongPackage;
	}

	public void setBelongPackage(ModelPackage belongPackage) {
		this.belongPackage = belongPackage;
	}

	public AbstractModelElement getParent() {
		return this.belongPackage.getEntityPackage();
	}

	public List<Property> getChildren() {
		return this.properties;
	}

	public VersionInfo getVersionInfo() {
		return versionInfo;
	}

	public void setVersionInfo(VersionInfo versionInfo) {
		this.versionInfo = versionInfo;
	}

}
