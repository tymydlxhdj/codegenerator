package com.mqfdy.code.model;

import java.util.List;

import org.dom4j.Element;

import com.mqfdy.code.model.utils.StringUtil;

// TODO: Auto-generated Javadoc
/**
 * 业务实体状态.
 *
 * @author mqfdy
 */
public class BEStatus extends AbstractModelElement {

	/** The is none status. */
	private boolean isNoneStatus = false;// 是否是无状态
	
	/** The business code. */
	private String businessCode ;//权限业务编码

	/** 所属的业务实体对象. */
	private List<BusinessClass> belongBusinessClass;
	
	/** 所属场景. */
	private BEScene belongBescne;

	/**
	 * 构造函数1.
	 */
	public BEStatus() {
		/*
		 * this.setOrderNum(1); this.setDisplayName("无状态");
		 */
	}

	/**
	 * 通过 XML 构造 BEStatus.
	 *
	 * @param statusElement
	 *            the status element
	 */
	public BEStatus(Element statusElement) {
		initBasicAttributes(statusElement);
		setNoneStatus(Boolean.valueOf(statusElement
				.attributeValue("isNoneStatus")));
		setOrderNum(StringUtil.string2Int(statusElement
				.attributeValue("OrderNum")));
	}

	/**
	 * @param statusesElement
	 * @return
	 */
	public Element generateXmlElement(Element statusesElement) {
		Element element = statusesElement.addElement("Status");// 创建Status节点
		generateBasicAttributes(element);// 创建Status节点属性
		element.addAttribute("OrderNum", String.valueOf(getOrderNum()));
		element.addAttribute("isNoneStatus", String.valueOf(isNoneStatus()));
		return element;
	}

	/**
	 * Gets the belong business class.
	 *
	 * @author mqfdy
	 * @return the belong business class
	 * @Date 2018-09-03 09:00
	 */
	public List<BusinessClass> getBelongBusinessClass() {
		return belongBusinessClass;
	}

	/**
	 * Sets the belong business class.
	 *
	 * @author mqfdy
	 * @param belongBusinessClass
	 *            the new belong business class
	 * @Date 2018-09-03 09:00
	 */
	public void setBelongBusinessClass(List<BusinessClass> belongBusinessClass) {
		this.belongBusinessClass = belongBusinessClass;
	}

	/**
	 * Gets the business code.
	 *
	 * @author mqfdy
	 * @return the business code
	 * @Date 2018-09-03 09:00
	 */
	public String getBusinessCode() {
		return businessCode;
	}

	/**
	 * Sets the business code.
	 *
	 * @author mqfdy
	 * @param businessCode
	 *            the new business code
	 * @Date 2018-09-03 09:00
	 */
	public void setBusinessCode(String businessCode) {
		this.businessCode = businessCode;
	}

	/**
	 * @return
	 */
	public AbstractModelElement getParent() {
		return this.belongBescne;
	}

	/**
	 * @return
	 */
	public List<AbstractModelElement> getChildren() {
		return EMPTY_CHILD;
	}

	/**
	 * Checks if is none status.
	 *
	 * @author mqfdy
	 * @return true, if is none status
	 * @Date 2018-09-03 09:00
	 */
	public boolean isNoneStatus() {
		return isNoneStatus;
	}

	/**
	 * Sets the none status.
	 *
	 * @author mqfdy
	 * @param isNoneStatus
	 *            the new none status
	 * @Date 2018-09-03 09:00
	 */
	public void setNoneStatus(boolean isNoneStatus) {
		this.isNoneStatus = isNoneStatus;
	}

	/**
	 * Gets the belong bescne.
	 *
	 * @author mqfdy
	 * @return the belong bescne
	 * @Date 2018-09-03 09:00
	 */
	public BEScene getBelongBescne() {
		return belongBescne;
	}

	/**
	 * Sets the belong bescne.
	 *
	 * @author mqfdy
	 * @param belongBescne
	 *            the new belong bescne
	 * @Date 2018-09-03 09:00
	 */
	public void setBelongBescne(BEScene belongBescne) {
		this.belongBescne = belongBescne;
	}

}
