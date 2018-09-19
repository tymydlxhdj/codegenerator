package com.mqfdy.code.model.graph;

import org.dom4j.Element;

import com.mqfdy.code.model.utils.StringUtil;

// TODO: Auto-generated Javadoc
/**
 * 图样式.
 *
 * @author mqfdy
 */
public class DiagramStyle {

	/** 背景色 默认为 白色. */
	private String backGroundColor = "white";

	/** 是否显示网格. */
	private boolean isGridStyle;

	/** 缩放比例. */
	private int zoomScale = 100;
	
	/** 布局. */
	private String layout ="";

	/** 默认样式. */
	private ElementStyle elementStyle;

	/**
	 * Instantiates a new diagram style.
	 */
	public DiagramStyle() {

	}

	/**
	 * 通过 XML 构造 DiagramStyle.
	 *
	 * @param diagramStyleElement
	 *            the diagram style element
	 */
	public DiagramStyle(Element diagramStyleElement) {

		// 获取Diagram节点下的BackGroundColor，GridStyle

		// 获取Diagram节点下BackGroundColor，GridStyle，ZoomScale子节点值
		String backGroundColor = diagramStyleElement
				.elementTextTrim("BackGroundColor");
		String gridStyle = diagramStyleElement.elementTextTrim("GridStyle");
		String zoomScale = diagramStyleElement.elementTextTrim("ZoomScale");
		String layout = diagramStyleElement.elementTextTrim("Layout");

		setBackGroundColor(backGroundColor);
		setGridStyle(StringUtil.string2Boolean(gridStyle));
		setZoomScale(StringUtil.string2Int(zoomScale));
		setLayout(StringUtil.convertNull2EmptyStr(layout));

		// 获取Diagram节点下ElementStyle节点
		// Element elementStyleElement =
		// diagramStyleElement.getChild("ElementStyle");
		// ElementStyle elementStyle = new ElementStyle(elementStyleElement);
		// setElementStyle(elementStyle);
	}

	/**
	 * Generate xml element.
	 *
	 * @author mqfdy
	 * @param xmlElement
	 *            the xml element
	 * @return the element
	 * @Date 2018-09-03 09:00
	 */
	public Element generateXmlElement(Element xmlElement) {
		Element diagramStyleElement = xmlElement.addElement("DiagramStyle");
		diagramStyleElement.addElement("BackGroundColor").addText(
				getBackGroundColor() == null ? "" : getBackGroundColor());
		diagramStyleElement.addElement("GridStyle").addText(
				isGridStyle() ? "true" : "false");
		diagramStyleElement.addElement("ZoomScale").addText(
				getZoomScale() + "%");
		diagramStyleElement.addElement("Layout").addText(
				getLayout());
		
		if (getElementStyle() != null) {
			// 创建ElementStyle节点
			getElementStyle().generateXmlElement(diagramStyleElement);
			// diagramStyleElement.addContent(elementStyleElement);
		}
		return diagramStyleElement;
	}

	/**
	 * Gets the element style.
	 *
	 * @author mqfdy
	 * @return the element style
	 * @Date 2018-09-03 09:00
	 */
	public ElementStyle getElementStyle() {
		return elementStyle;
	}

	/**
	 * Sets the element style.
	 *
	 * @author mqfdy
	 * @param elementStyle
	 *            the new element style
	 * @Date 2018-09-03 09:00
	 */
	public void setElementStyle(ElementStyle elementStyle) {
		this.elementStyle = elementStyle;
	}

	/**
	 * Gets the back ground color.
	 *
	 * @author mqfdy
	 * @return the back ground color
	 * @Date 2018-09-03 09:00
	 */
	public String getBackGroundColor() {
		return backGroundColor;
	}

	/**
	 * Sets the back ground color.
	 *
	 * @author mqfdy
	 * @param backGroundColor
	 *            the new back ground color
	 * @Date 2018-09-03 09:00
	 */
	public void setBackGroundColor(String backGroundColor) {
		this.backGroundColor = backGroundColor;
	}

	/**
	 * Checks if is grid style.
	 *
	 * @author mqfdy
	 * @return true, if is grid style
	 * @Date 2018-09-03 09:00
	 */
	public boolean isGridStyle() {
		return isGridStyle;
	}

	/**
	 * Sets the grid style.
	 *
	 * @author mqfdy
	 * @param isGridStyle
	 *            the new grid style
	 * @Date 2018-09-03 09:00
	 */
	public void setGridStyle(boolean isGridStyle) {
		this.isGridStyle = isGridStyle;
	}

	/**
	 * Gets the zoom scale.
	 *
	 * @author mqfdy
	 * @return the zoom scale
	 * @Date 2018-09-03 09:00
	 */
	public int getZoomScale() {
		return zoomScale;
	}

	/**
	 * Sets the zoom scale.
	 *
	 * @author mqfdy
	 * @param zoomScale
	 *            the new zoom scale
	 * @Date 2018-09-03 09:00
	 */
	public void setZoomScale(int zoomScale) {
		this.zoomScale = zoomScale;
	}

	/**
	 * Gets the layout.
	 *
	 * @author mqfdy
	 * @return the layout
	 * @Date 2018-09-03 09:00
	 */
	public String getLayout() {
		return layout;
	}

	/**
	 * Sets the layout.
	 *
	 * @author mqfdy
	 * @param layout
	 *            the new layout
	 * @Date 2018-09-03 09:00
	 */
	public void setLayout(String layout) {
		this.layout = layout;
	}

}
