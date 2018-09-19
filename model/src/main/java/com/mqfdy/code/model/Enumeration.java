package com.mqfdy.code.model;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

// TODO: Auto-generated Javadoc
/**
 * 枚举对象.
 *
 * @author mqfdy
 */
public class Enumeration extends AbstractModelElement {

	/** 所属包. */
	private ModelPackage belongPackage;

	/** 枚举信息. */
	private List<EnumElement> elements;

	/** 版本信息. */
	private VersionInfo versionInfo;

	/**
	 * Instantiates a new enumeration.
	 */
	public Enumeration() {
		super();
		elements = new ArrayList<EnumElement>();

	}

	/**
	 * 通过 XML 对象构造 Enumeration.
	 *
	 * @param enumerationElement
	 *            the enumeration element
	 */
	@SuppressWarnings("unchecked")
	public Enumeration(Element enumerationElement) {

		initBasicAttributes(enumerationElement);

		// 组装dto对象中的VersionInfo对象 ok
		Element versionInfoElement = enumerationElement.element("VersionInfo");
		if (versionInfoElement != null) {
			VersionInfo versionInfo = new VersionInfo(versionInfoElement);
			setVersionInfo(versionInfo);
		}

		elements = new ArrayList<EnumElement>();
		// 获取Enumeration节点下的Element节点集合
		List<Element> enumeElements = enumerationElement.elements("Element");
		if (enumeElements != null) {
			for (Iterator<Element> iter = enumeElements.iterator(); iter
					.hasNext();) {
				Element element = iter.next();// Element节点
				// 获取Element节点的name，value属性值
				String key = element.attributeValue("name");
				String value = element.attributeValue("value");
				EnumElement enumElement = new EnumElement(key, value);
				addElement(enumElement);
			}
		}
	}

	/**
	 * @param enumerationsElement
	 * @return
	 */
	public Element generateXmlElement(Element enumerationsElement) {
		Element enumerationElement = enumerationsElement
				.addElement("Enumeration");
		this.generateBasicAttributes(enumerationElement);
		// 创建dto节点下的VersionInfo节点，如果没有就不创建
		if (getVersionInfo() != null) {
			getVersionInfo().generateXmlElement(enumerationElement);
			// enumerationElement.addContent(versionInfoElement);
		}
		// 对每一个Enumeration节点生成其下面的Element节点
		List<EnumElement> enumElements = getElements();
		for (Iterator<EnumElement> iterator = enumElements.iterator(); iterator
				.hasNext();) {
			EnumElement enumElement = iterator.next();
			Element elementElement = enumerationElement.addElement("Element");
			elementElement.addAttribute("name", enumElement.getKey());
			elementElement.addAttribute("value", enumElement.getValue());
			// enumerationElement.addContent(elementElement);
		}

		return enumerationElement;
	}
	
	/**
	 * Generate xml element.
	 *
	 * @author mqfdy
	 * @return the document
	 * @Date 2018-09-03 09:00
	 */
	public Document generateXmlElement() {
		Document document = DocumentHelper.createDocument();
		Element enumerationElement = document.addElement("Enumeration");
		this.generateBasicAttributes(enumerationElement);
		// 创建dto节点下的VersionInfo节点，如果没有就不创建
		if (getVersionInfo() != null) {
			getVersionInfo().generateXmlElement(enumerationElement);
			// enumerationElement.addContent(versionInfoElement);
		}
		// 对每一个Enumeration节点生成其下面的Element节点
		List<EnumElement> enumElements = getElements();
		for (Iterator<EnumElement> iterator = enumElements.iterator(); iterator
				.hasNext();) {
			EnumElement enumElement = iterator.next();
			Element elementElement = enumerationElement.addElement("Element");
			elementElement.addAttribute("name", enumElement.getKey());
			elementElement.addAttribute("value", enumElement.getValue());
			// enumerationElement.addContent(elementElement);
		}

		return document;
	}
	
	/**
	 * To xml.
	 *
	 * @author mqfdy
	 * @return the string
	 * @Date 2018-09-03 09:00
	 */
	public String toXml(){
        String faceXml = "";
        
        OutputFormat format = new OutputFormat(" ",true,"UTF-8"); 
        StringWriter sw = new StringWriter(); 

        XMLWriter writer = new XMLWriter(sw,format); 
        try {
			writer.write(this.generateXmlElement());
			StringBuffer sb = sw.getBuffer();
			faceXml = sb.toString();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(sw!=null){
				try {
					sw.close();
				} catch (IOException e) {
					e.printStackTrace();
				} 
			}
			
			if(writer!=null){
				try {
					writer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
        

		return faceXml;
	}
	
	
	
	
	

	/**
	 * @return
	 */
	public int getPriority() {
		return IModelElement.PRIORITY_ENUMERATION;
	}

	/**
	 * Gets the elements.
	 *
	 * @author mqfdy
	 * @return the elements
	 * @Date 2018-09-03 09:00
	 */
	public List<EnumElement> getElements() {
		return elements;
	}

	/**
	 * Sets the elements.
	 *
	 * @author mqfdy
	 * @param elements
	 *            the new elements
	 * @Date 2018-09-03 09:00
	 */
	public void setElements(List<EnumElement> elements) {
		this.elements = elements;
	}

	/**
	 * Adds the element.
	 *
	 * @author mqfdy
	 * @param element
	 *            the element
	 * @Date 2018-09-03 09:00
	 */
	public void addElement(EnumElement element) {
		this.elements.add(element);
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
	public List<EnumElement> getChildren() {
		return this.elements;
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
	
	/**
	 * @return
	 */
	public Enumeration cloneChangeId() {
		Enumeration bc = new Enumeration();
		copyChangeId(bc);
		return bc;
	}

	/**
	 * @param dest
	 */
	protected void copyChangeId(AbstractModelElement dest) {
		super.copyChangeId(dest);
		Enumeration destBc = ((Enumeration) dest);
		destBc.setVersionInfo(versionInfo);
		destBc.setElements(elements);
		destBc.setBelongPackage(getBelongPackage());

	}
}
