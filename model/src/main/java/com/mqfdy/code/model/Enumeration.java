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

/**
 * 枚举对象
 * 
 * @author mqfdy
 * 
 */
public class Enumeration extends AbstractModelElement {

	/**
	 * 所属包
	 */
	private ModelPackage belongPackage;

	/**
	 * 枚举信息
	 */
	private List<EnumElement> elements;

	/**
	 * 版本信息
	 */
	private VersionInfo versionInfo;

	public Enumeration() {
		super();
		elements = new ArrayList<EnumElement>();

	}

	/**
	 * 通过 XML 对象构造 Enumeration
	 * 
	 * @param enumerationElement
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
	
	
	
	
	

	public int getPriority() {
		return IModelElement.PRIORITY_ENUMERATION;
	}

	public List<EnumElement> getElements() {
		return elements;
	}

	public void setElements(List<EnumElement> elements) {
		this.elements = elements;
	}

	public void addElement(EnumElement element) {
		this.elements.add(element);
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

	public List<EnumElement> getChildren() {
		return this.elements;
	}

	public VersionInfo getVersionInfo() {
		return versionInfo;
	}

	public void setVersionInfo(VersionInfo versionInfo) {
		this.versionInfo = versionInfo;
	}
	public Enumeration cloneChangeId() {
		Enumeration bc = new Enumeration();
		copyChangeId(bc);
		return bc;
	}

	protected void copyChangeId(AbstractModelElement dest) {
		super.copyChangeId(dest);
		Enumeration destBc = ((Enumeration) dest);
		destBc.setVersionInfo(versionInfo);
		destBc.setElements(elements);
		destBc.setBelongPackage(getBelongPackage());

	}
}
