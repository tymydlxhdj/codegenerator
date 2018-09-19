package com.mqfdy.code.model.graph;

import org.dom4j.Element;

import com.mqfdy.code.model.utils.StringUtil;

// TODO: Auto-generated Javadoc
/**
 * 图元样式.
 *
 * @author mqfdy
 */
public class ElementStyle {

	/** 字体. */
	private String fontFamily;

	/** 字号. */
	private int fontSize;

	/** 宽度. */
	private int width;

	/** 高度. */
	private int height;

	/** X坐标. */
	private float positionX;

	/** Y坐标. */
	private float positionY;

	/** 结束点X坐标. */
	private float endPositionX;

	/** 结束点Y坐标. */
	private float endPositionY;

	/** The point list. */
	private String pointList;

	/** 是否显示边框. */
	private boolean isShowBorder;

	/** 边框颜色. */
	private String borderColor;

	/** 背景色. */
	private String backGroundColor;

	/**
	 * 构造函数.
	 */
	public ElementStyle() {

	}

	/**
	 * 通过 XML 构造 ElementStyle.
	 *
	 * @param elementStyleElement
	 *            the element style element
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

	/**
	 * Generate xml element.
	 *
	 * @author mqfdy
	 * @param diagramStyleElement
	 *            the diagram style element
	 * @return the element
	 * @Date 2018-09-03 09:00
	 */
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

	/**
	 * Gets the font family.
	 *
	 * @author mqfdy
	 * @return the font family
	 * @Date 2018-09-03 09:00
	 */
	public String getFontFamily() {
		return fontFamily;
	}

	/**
	 * Sets the font family.
	 *
	 * @author mqfdy
	 * @param fontFamily
	 *            the new font family
	 * @Date 2018-09-03 09:00
	 */
	public void setFontFamily(String fontFamily) {
		this.fontFamily = fontFamily;
	}

	/**
	 * Gets the font size.
	 *
	 * @author mqfdy
	 * @return the font size
	 * @Date 2018-09-03 09:00
	 */
	public int getFontSize() {
		return fontSize;
	}

	/**
	 * Sets the font size.
	 *
	 * @author mqfdy
	 * @param fontSize
	 *            the new font size
	 * @Date 2018-09-03 09:00
	 */
	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}

	/**
	 * Gets the width.
	 *
	 * @author mqfdy
	 * @return the width
	 * @Date 2018-09-03 09:00
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Sets the width.
	 *
	 * @author mqfdy
	 * @param width
	 *            the new width
	 * @Date 2018-09-03 09:00
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * Gets the height.
	 *
	 * @author mqfdy
	 * @return the height
	 * @Date 2018-09-03 09:00
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Sets the height.
	 *
	 * @author mqfdy
	 * @param height
	 *            the new height
	 * @Date 2018-09-03 09:00
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * Gets the position X.
	 *
	 * @author mqfdy
	 * @return the position X
	 * @Date 2018-09-03 09:00
	 */
	public float getPositionX() {
		return positionX;
	}

	/**
	 * Sets the position X.
	 *
	 * @author mqfdy
	 * @param positionX
	 *            the new position X
	 * @Date 2018-09-03 09:00
	 */
	public void setPositionX(float positionX) {
		this.positionX = positionX;
	}

	/**
	 * Gets the position Y.
	 *
	 * @author mqfdy
	 * @return the position Y
	 * @Date 2018-09-03 09:00
	 */
	public float getPositionY() {
		return positionY;
	}

	/**
	 * Sets the position Y.
	 *
	 * @author mqfdy
	 * @param positionY
	 *            the new position Y
	 * @Date 2018-09-03 09:00
	 */
	public void setPositionY(float positionY) {
		this.positionY = positionY;
	}

	/**
	 * Gets the end position X.
	 *
	 * @author mqfdy
	 * @return the end position X
	 * @Date 2018-09-03 09:00
	 */
	public float getEndPositionX() {
		return endPositionX;
	}

	/**
	 * Sets the end position X.
	 *
	 * @author mqfdy
	 * @param endPositionX
	 *            the new end position X
	 * @Date 2018-09-03 09:00
	 */
	public void setEndPositionX(float endPositionX) {
		this.endPositionX = endPositionX;
	}

	/**
	 * Gets the end position Y.
	 *
	 * @author mqfdy
	 * @return the end position Y
	 * @Date 2018-09-03 09:00
	 */
	public float getEndPositionY() {
		return endPositionY;
	}

	/**
	 * Sets the end position Y.
	 *
	 * @author mqfdy
	 * @param endPositionY
	 *            the new end position Y
	 * @Date 2018-09-03 09:00
	 */
	public void setEndPositionY(float endPositionY) {
		this.endPositionY = endPositionY;
	}

	/**
	 * Checks if is show border.
	 *
	 * @author mqfdy
	 * @return true, if is show border
	 * @Date 2018-09-03 09:00
	 */
	public boolean isShowBorder() {
		return isShowBorder;
	}

	/**
	 * Sets the show border.
	 *
	 * @author mqfdy
	 * @param isShowBorder
	 *            the new show border
	 * @Date 2018-09-03 09:00
	 */
	public void setShowBorder(boolean isShowBorder) {
		this.isShowBorder = isShowBorder;
	}

	/**
	 * Gets the border color.
	 *
	 * @author mqfdy
	 * @return the border color
	 * @Date 2018-09-03 09:00
	 */
	public String getBorderColor() {
		return borderColor;
	}

	/**
	 * Sets the border color.
	 *
	 * @author mqfdy
	 * @param borderColor
	 *            the new border color
	 * @Date 2018-09-03 09:00
	 */
	public void setBorderColor(String borderColor) {
		this.borderColor = borderColor;
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
	 * Gets the point list.
	 *
	 * @author mqfdy
	 * @return the point list
	 * @Date 2018-09-03 09:00
	 */
	public String getPointList() {
		return pointList == null ? "" : pointList;
	}

	/**
	 * Sets the point list.
	 *
	 * @author mqfdy
	 * @param pointList
	 *            the new point list
	 * @Date 2018-09-03 09:00
	 */
	public void setPointList(String pointList) {
		this.pointList = pointList;
	}
}
