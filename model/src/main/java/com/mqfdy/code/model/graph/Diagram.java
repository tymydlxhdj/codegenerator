package com.mqfdy.code.model.graph;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Element;

import com.mqfdy.code.model.AbstractModelElement;
import com.mqfdy.code.model.IModelElement;
import com.mqfdy.code.model.ModelPackage;
import com.mqfdy.code.model.utils.StringUtil;

/**
 * 图
 * 
 * @author mqfdy
 * 
 */
public class Diagram extends AbstractModelElement {

	/**
	 * 所属业务包
	 */
	private ModelPackage belongPackage;

	/**
	 * 图元的默认样式
	 */
	private DiagramStyle defaultStyle;

	private List<DiagramElement> elements;

	private boolean isDefault;
	public Diagram() {
		elements = new ArrayList<DiagramElement>();
	}

	@SuppressWarnings("unchecked")
	public Diagram(Element diagramElement) {

		elements = new ArrayList<DiagramElement>();

		initBasicAttributes(diagramElement);
		boolean isDefault = StringUtil
				.string2Boolean(diagramElement
						.attributeValue("isDefault"));// getChildTextTrim
		this.setDefault(isDefault);
		
		// 获取Diagram节点下的DiagramStyle节点
		Element diagramStyleElement = diagramElement.element("DiagramStyle");

		if (diagramStyleElement != null) {
			DiagramStyle diagramStyle = new DiagramStyle(diagramStyleElement);
			setDefaultStyle(diagramStyle);
		}
		// 获取Diagram节点下的Elements节点
		Element elementsElement = diagramElement.element("Elements");
		if (elementsElement != null) {
			List<Element> elementList = elementsElement.elements("Element");
			if (elementList != null) {
				for (Iterator<Element> iterator = elementList.iterator(); iterator
						.hasNext();) {
					Element element = iterator.next();// Element节点
					DiagramElement dElement = new DiagramElement(element);
					dElement.setBelongDiagram(this);
					elements.add(dElement);
				}
			}
		}
	}

	public Diagram(ModelPackage belongPackage, String name, String displayName) {
		super(name, displayName);
		elements = new ArrayList<DiagramElement>();
		DiagramStyle diagramStyle = new DiagramStyle();
		this.setDefaultStyle(diagramStyle);
		setBelongPackage(belongPackage);
	}

	public Element generateXmlElement(Element diagramsElement) {
		Element xmlElement = diagramsElement.addElement("Diagram");// diagramsElement.getChild("Diagram");
		this.generateBasicAttributes(xmlElement);// 设置Diagram节点的公共属性
		if(isDefault){
			xmlElement.addAttribute("isDefault",isDefault+"");
		}
		// 新建DiagramStyle节点
		if (getDefaultStyle() != null) {
			getDefaultStyle().generateXmlElement(xmlElement);
			// xmlElement.addContent(diagramStyleElement);
		}
		List<DiagramElement> diagramElements = getElements();
		if (diagramElements != null && !diagramElements.isEmpty()) {
			// 创建Elements节点
			Element elementsElement = xmlElement.addElement("Elements");
			// xmlElement.addContent(elementsElement);
			for (DiagramElement diagramElement : diagramElements) {
				diagramElement.generateXmlElement(elementsElement);
				// elementsElement.addContent(elementElement);
			}
		}
		return xmlElement;
	}

	public int getPriority() {
		return IModelElement.PRIORITY_DIAGRAM;
	}

	public List<DiagramElement> getElements() {
		return elements;
	}

	public void addElement(DiagramElement element) {
		elements.add(element);
	}

	public DiagramStyle getDefaultStyle() {
		return defaultStyle;
	}

	public void setDefaultStyle(DiagramStyle defaultStyle) {
		this.defaultStyle = defaultStyle;
	}

	public ModelPackage getBelongPackage() {
		return belongPackage;
	}

	public void setBelongPackage(ModelPackage belongPackage) {
		this.belongPackage = belongPackage;
	}

	/*
	 * 根据objectId取DiagramElement
	 */
	public DiagramElement getElementById(String objectId) {
		for (int i = 0; i < elements.size(); i++) {
			if (elements.get(i).getObjectId().equals(objectId))
				return elements.get(i);
		}
		return null;
	}

	public AbstractModelElement getParent() {
		return this.belongPackage;
	}

	public List<AbstractModelElement> getChildren() {
		return EMPTY_CHILD;
	}
	protected void copy(Diagram dest) {
		super.copy(dest);
		for (DiagramElement property : this.elements) {
			DiagramElement element = property.clone();
			element.setBelongDiagram(dest);
			dest.addElement(element);
		}
		dest.setBelongPackage(getBelongPackage());
	}

	public Diagram clone() {
		Diagram bc = new Diagram();
		copy(bc);
		return bc;
	}

	public boolean isDefault() {
		return isDefault;
	}

	public void setDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}
}
