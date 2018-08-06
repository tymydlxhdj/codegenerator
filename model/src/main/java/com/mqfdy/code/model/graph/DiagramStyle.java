package com.mqfdy.code.model.graph;

import org.dom4j.Element;

import com.mqfdy.code.model.utils.StringUtil;

/**
 * 图样式
 * 
 * @author mqfdy
 */
public class DiagramStyle {

	/**
	 * 背景色 默认为 白色
	 */
	private String backGroundColor = "white";

	/**
	 * 是否显示网格
	 */
	private boolean isGridStyle;

	/**
	 * 缩放比例
	 */
	private int zoomScale = 100;
	
	/**
	 * 布局
	 */
	private String layout ="";

	/**
	 * 默认样式
	 */
	private ElementStyle elementStyle;

	public DiagramStyle() {

	}

	/**
	 * 通过 XML 构造 DiagramStyle
	 * 
	 * @param diagramStyleElement
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

	public ElementStyle getElementStyle() {
		return elementStyle;
	}

	public void setElementStyle(ElementStyle elementStyle) {
		this.elementStyle = elementStyle;
	}

	public String getBackGroundColor() {
		return backGroundColor;
	}

	public void setBackGroundColor(String backGroundColor) {
		this.backGroundColor = backGroundColor;
	}

	public boolean isGridStyle() {
		return isGridStyle;
	}

	public void setGridStyle(boolean isGridStyle) {
		this.isGridStyle = isGridStyle;
	}

	public int getZoomScale() {
		return zoomScale;
	}

	public void setZoomScale(int zoomScale) {
		this.zoomScale = zoomScale;
	}

	public String getLayout() {
		return layout;
	}

	public void setLayout(String layout) {
		this.layout = layout;
	}

}
