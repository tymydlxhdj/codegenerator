package com.mqfdy.code.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Element;

// TODO: Auto-generated Javadoc
/**
 * 数据传输对象.
 *
 * @author mqfdy
 */
public class DataTransferObject extends AbstractModelElement {
	
	/** 所属包. */
	private ModelPackage belongPackage;

	/** 属性列表. */
	private List<Property> properties;

	/** 版本信息. */
	private VersionInfo versionInfo;

	/**
	 * Instantiates a new data transfer object.
	 */
	public DataTransferObject() {

		properties = new ArrayList<Property>(50);

	}

	/**
	 * 通过 XML 构造 DataTransferObject.
	 *
	 * @param dtoElement
	 *            the dto element
	 * @param bom
	 *            the bom
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

	/**
	 * @param dtosElement
	 * @return
	 */
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

	/**
	 * @return
	 */
	public int getPriority() {
		return IModelElement.PRIORITY_DTO;
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
	public void addProperty(DTOProperty property) {
		properties.add(property);
	}

	/**
	 * Gets the belong package.
	 *
	 * @author mqfdy
	 * @return the belong package
	 * @Date 2018-09-03 09:00
	 */
	public ModelPackage getBelongPackage() {
		return belongPackage;
	}

	/**
	 * Sets the belong package.
	 *
	 * @author mqfdy
	 * @param belongPackage
	 *            the new belong package
	 * @Date 2018-09-03 09:00
	 */
	public void setBelongPackage(ModelPackage belongPackage) {
		this.belongPackage = belongPackage;
	}

	/**
	 * @return
	 */
	public AbstractModelElement getParent() {
		return this.belongPackage.getEntityPackage();
	}

	/**
	 * @return
	 */
	public List<Property> getChildren() {
		return this.properties;
	}

	/**
	 * Gets the version info.
	 *
	 * @author mqfdy
	 * @return the version info
	 * @Date 2018-09-03 09:00
	 */
	public VersionInfo getVersionInfo() {
		return versionInfo;
	}

	/**
	 * Sets the version info.
	 *
	 * @author mqfdy
	 * @param versionInfo
	 *            the new version info
	 * @Date 2018-09-03 09:00
	 */
	public void setVersionInfo(VersionInfo versionInfo) {
		this.versionInfo = versionInfo;
	}

}
