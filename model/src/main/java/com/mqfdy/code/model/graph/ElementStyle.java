package com.mqfdy.code.model.graph;

import org.dom4j.Element;

import com.mqfdy.code.model.utils.StringUtil;

/**
 * 图元样式
 * 
 * @author mqfdy
 * 
 */
public class ElementStyle {

	/**
	 * 字体
	 */
	private String fontFamily;

	/**
	 * 字号
	 */
	private int fontSize;

	/**
	 * 宽度
	 */
	private int width;

	/**
	 * 高度
	 */
	private int height;

	/**
	 * X坐标
	 */
	private float positionX;

	/**
	 * Y坐标
	 */
	private float positionY;

	/**
	 * 结束点X坐标
	 */
	private float endPositionX;

	/**
	 * 结束点Y坐标
	 */
	private float endPositionY;

	private String pointList;

	/**
	 * 是否显示边框
	 */
	private boolean isShowBorder;

	/**
	 * 边框颜色
	 */
	private String borderColor;

	/**
	 * 背景色
	 */
	private String backGroundColor;

	/**
	 * 构造函数
	 */
	public ElementStyle() {

	}

	/**
	 * 通过 XML 构造 ElementStyle
	 * 
	 * @param elementStyleElement
	 */
	public ElementStyle(Element elementStyleElement) {
		// 获取ElementStyle节点下FontFamily，FontSize，Width，Height，
		// Position_X，Position_Y，Border，BorderColor，BackGroundColor
		String fontFamily = elementStyleElement.elementTextTrim("FontFamily");
		String fontSize = elementStyleElement.elementTextTrim("FontSize");
		String width = elementStyleElement.elementTextTrim("Width");
		String height = elementStyleElement.elementTextTrim("Height");
		String position_X = elementStyleElement.elementTextTrim("Position_X");
		String position_Y = elementStyleElement.elementTextTrim("Position_Y");
		String endPositionX = elementStyleElement
				.elementTextTrim("End_Position_X");
		String endPositionY = elementStyleElement
				.elementTextTrim("End_Position_Y");
		String list = elementStyleElement.elementTextTrim("Point_List");
		String border = elementStyleElement.elementTextTrim("Border");
		String borderColor = elementStyleElement.elementTextTrim("BorderColor");
		String backGroundColor1 = elementStyleElement
				.elementTextTrim("BackGroundColor");
		setFontFamily(fontFamily);
		setFontSize(StringUtil.string2Int(fontSize));
		setWidth(StringUtil.string2Int(width));
		setHeight(StringUtil.string2Int(height));
		if (list != null)
			setPointList(list);// pointlist
		if (StringUtil.string2Float(position_X) != null) {
			setPositionX(StringUtil.string2Float(position_X));
		}
		if (StringUtil.string2Float(position_Y) != null) {
			setPositionY(StringUtil.string2Float(position_Y));
		}

		if (StringUtil.string2Float(endPositionX) != null) {
			setEndPositionX(StringUtil.string2Float(endPositionX));
		}
		if (StringUtil.string2Float(endPositionY) != null) {
			setEndPositionY(StringUtil.string2Float(endPositionY));
		}

		setShowBorder(StringUtil.string2Boolean(border));
		setBorderColor(borderColor);
		setBackGroundColor(backGroundColor1);
	}

	public Element generateXmlElement(Element diagramStyleElement) {
		Element elementStyleElement = diagramStyleElement
				.addElement("ElementStyle");
		elementStyleElement.addElement("FontFamily").addText(
				StringUtil.convertNull2EmptyStr(getFontFamily()));
		elementStyleElement.addElement("FontSize").addText(getFontSize() + "");
		elementStyleElement.addElement("Width").addText(getWidth() + "");
		elementStyleElement.addElement("Height").addText(getHeight() + "");
		elementStyleElement.addElement("Position_X").addText(
				getPositionX() + "");
		elementStyleElement.addElement("Position_Y").addText(
				getPositionY() + "");
		elementStyleElement.addElement("End_Position_X").addText(
				getEndPositionX() + "");
		elementStyleElement.addElement("End_Position_Y").addText(
				getEndPositionY() + "");
		elementStyleElement.addElement("Point_List").addText(getPointList());
		elementStyleElement.addElement("Border").addText(
				isShowBorder() ? "true" : "false");
		elementStyleElement.addElement("BorderColor").addText(
				StringUtil.convertNull2EmptyStr(getBorderColor()));
		elementStyleElement.addElement("BackGroundColor").addText(
				StringUtil.convertNull2EmptyStr(getBackGroundColor()));
		return elementStyleElement;
	}

	public String getFontFamily() {
		return fontFamily;
	}

	public void setFontFamily(String fontFamily) {
		this.fontFamily = fontFamily;
	}

	public int getFontSize() {
		return fontSize;
	}

	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public float getPositionX() {
		return positionX;
	}

	public void setPositionX(float positionX) {
		this.positionX = positionX;
	}

	public float getPositionY() {
		return positionY;
	}

	public void setPositionY(float positionY) {
		this.positionY = positionY;
	}

	public float getEndPositionX() {
		return endPositionX;
	}

	public void setEndPositionX(float endPositionX) {
		this.endPositionX = endPositionX;
	}

	public float getEndPositionY() {
		return endPositionY;
	}

	public void setEndPositionY(float endPositionY) {
		this.endPositionY = endPositionY;
	}

	public boolean isShowBorder() {
		return isShowBorder;
	}

	public void setShowBorder(boolean isShowBorder) {
		this.isShowBorder = isShowBorder;
	}

	public String getBorderColor() {
		return borderColor;
	}

	public void setBorderColor(String borderColor) {
		this.borderColor = borderColor;
	}

	public String getBackGroundColor() {
		return backGroundColor;
	}

	public void setBackGroundColor(String backGroundColor) {
		this.backGroundColor = backGroundColor;
	}

	public String getPointList() {
		return pointList == null ? "" : pointList;
	}

	public void setPointList(String pointList) {
		this.pointList = pointList;
	}
}
