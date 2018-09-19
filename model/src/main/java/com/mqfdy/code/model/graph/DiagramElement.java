package com.mqfdy.code.model.graph;

import java.util.List;

import org.dom4j.Element;

import com.mqfdy.code.model.AbstractModelElement;
import com.mqfdy.code.model.utils.StringUtil;

// TODO: Auto-generated Javadoc
/**
 * 图元.
 *
 * @author mqfdy
 */
public class DiagramElement extends AbstractModelElement {

	/** 所属模型图. */
	private Diagram belongDiagram;

	/** 默认图元样式. */
	private ElementStyle style;

	/** 对应业务对象Id. */
	private String objectId;

	/**
	 * 构造函数.
	 */
	public DiagramElement() {

	}

	/**
	 * 构造函数.
	 *
	 * @param refObj
	 *            the ref obj
	 */
	public DiagramElement(AbstractModelElement refObj) {
		setName("ref_" + StringUtil.convertNull2EmptyStr(refObj.getName()));
		setDisplayName(refObj.getDisplayName());
		setObjectId(refObj.getId());
	}

	/**
	 * 通过 XML 构造 DiagramElement.
	 *
	 * @param element
	 *            the element
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

	/**
	 * @param elementsElement
	 * @return
	 */
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
	
	/**
	 * Copy.
	 *
	 * @author mqfdy
	 * @param dest
	 *            the dest
	 * @Date 2018-09-03 09:00
	 */
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

	/**
	 * @return
	 */
	public DiagramElement clone() {
		DiagramElement bc = new DiagramElement();
		copy(bc);
		return bc;
	}
	
	/**
	 * Gets the style.
	 *
	 * @author mqfdy
	 * @return the style
	 * @Date 2018-09-03 09:00
	 */
	public ElementStyle getStyle() {
		return style;
	}

	/**
	 * Sets the style.
	 *
	 * @author mqfdy
	 * @param style
	 *            the new style
	 * @Date 2018-09-03 09:00
	 */
	public void setStyle(ElementStyle style) {
		this.style = style;
	}

	/**
	 * Gets the object id.
	 *
	 * @author mqfdy
	 * @return the object id
	 * @Date 2018-09-03 09:00
	 */
	public String getObjectId() {
		return objectId;
	}

	/**
	 * Sets the object id.
	 *
	 * @author mqfdy
	 * @param objectId
	 *            the new object id
	 * @Date 2018-09-03 09:00
	 */
	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	/**
	 * Gets the belong diagram.
	 *
	 * @author mqfdy
	 * @return the belong diagram
	 * @Date 2018-09-03 09:00
	 */
	public Diagram getBelongDiagram() {
		return belongDiagram;
	}

	/**
	 * Sets the belong diagram.
	 *
	 * @author mqfdy
	 * @param belongDiagram
	 *            the new belong diagram
	 * @Date 2018-09-03 09:00
	 */
	public void setBelongDiagram(Diagram belongDiagram) {
		this.belongDiagram = belongDiagram;
	}

	/**
	 * @return
	 */
	public AbstractModelElement getParent() {
		return this.belongDiagram;
	}

	/**
	 * @return
	 */
	public List<AbstractModelElement> getChildren() {
		return EMPTY_CHILD;
	}

}
