package com.mqfdy.code.model.graph;

import java.util.List;

import org.dom4j.Element;

import com.mqfdy.code.model.AbstractModelElement;
import com.mqfdy.code.model.utils.StringUtil;

/**
 * 图元
 * 
 * @author mqfdy
 * 
 */
public class DiagramElement extends AbstractModelElement {

	/**
	 * 所属模型图
	 */
	private Diagram belongDiagram;

	/**
	 * 默认图元样式
	 */
	private ElementStyle style;

	/**
	 * 对应业务对象Id
	 */
	private String objectId;

	/**
	 * 构造函数
	 */
	public DiagramElement() {

	}

	/**
	 * 构造函数
	 */
	public DiagramElement(AbstractModelElement refObj) {
		setName("ref_" + StringUtil.convertNull2EmptyStr(refObj.getName()));
		setDisplayName(refObj.getDisplayName());
		setObjectId(refObj.getId());
	}

	/**
	 * 通过 XML 构造 DiagramElement
	 * 
	 * @param element
	 */
	public DiagramElement(Element element) {

		initBasicAttributes(element);

		String objectId = element.attributeValue("objectId");
		setObjectId(objectId);

		// 获取Element节点的Style节点
		Element elementStyleElement1 = element.element("ElementStyle");
		ElementStyle elementStyle = new ElementStyle(elementStyleElement1);
		setStyle(elementStyle);
	}

	public Element generateXmlElement(Element elementsElement) {
		Element diagramElement = elementsElement.addElement("Element");
		if (getStyle() != null) {
			getStyle().generateXmlElement(diagramElement);
			// diagramElement.addContent(styleElement);
		}

		diagramElement.addAttribute("id", getId());
		diagramElement.addAttribute("objectId", getObjectId());

		return diagramElement;
	}
	protected void copy(DiagramElement dest) {
		super.copy(dest);
		dest.setObjectId(this.objectId);
		ElementStyle style1 = new ElementStyle();
		style1.setBackGroundColor(style.getBackGroundColor());
		style1.setBorderColor(style.getBorderColor());
		style1.setEndPositionX(style.getEndPositionX());
		style1.setEndPositionY(style.getEndPositionY());
		style1.setFontFamily(style.getFontFamily());
		style1.setFontSize(style.getFontSize());
		style1.setHeight(style.getHeight());
		style1.setPointList(style.getPointList());
		style1.setPositionX(style.getPositionX());
		style1.setPositionY(style.getPositionY());
		style1.setWidth(style.getWidth());
		dest.setStyle(style1);
	}

	public DiagramElement clone() {
		DiagramElement bc = new DiagramElement();
		copy(bc);
		return bc;
	}
	public ElementStyle getStyle() {
		return style;
	}

	public void setStyle(ElementStyle style) {
		this.style = style;
	}

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public Diagram getBelongDiagram() {
		return belongDiagram;
	}

	public void setBelongDiagram(Diagram belongDiagram) {
		this.belongDiagram = belongDiagram;
	}

	public AbstractModelElement getParent() {
		return this.belongDiagram;
	}

	public List<AbstractModelElement> getChildren() {
		return EMPTY_CHILD;
	}

}
