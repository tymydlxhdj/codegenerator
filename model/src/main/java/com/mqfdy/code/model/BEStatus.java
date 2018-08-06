package com.mqfdy.code.model;

import java.util.List;

import org.dom4j.Element;

import com.mqfdy.code.model.utils.StringUtil;

/**
 * 业务实体状态
 * 
 * @author mqfdy
 * 
 */
public class BEStatus extends AbstractModelElement {

	private boolean isNoneStatus = false;// 是否是无状态
	
	private String businessCode ;//权限业务编码

	/**
	 * 所属的业务实体对象
	 */
	private List<BusinessClass> belongBusinessClass;
	
	/**
	 * 所属场景
	 */
	private BEScene belongBescne;

	/**
	 * 构造函数1
	 */
	public BEStatus() {
		/*
		 * this.setOrderNum(1); this.setDisplayName("无状态");
		 */
	}

	/**
	 * 通过 XML 构造 BEStatus
	 * 
	 * @param statusElement
	 */
	public BEStatus(Element statusElement) {
		initBasicAttributes(statusElement);
		setNoneStatus(Boolean.valueOf(statusElement
				.attributeValue("isNoneStatus")));
		setOrderNum(StringUtil.string2Int(statusElement
				.attributeValue("OrderNum")));
	}

	public Element generateXmlElement(Element statusesElement) {
		Element element = statusesElement.addElement("Status");// 创建Status节点
		generateBasicAttributes(element);// 创建Status节点属性
		element.addAttribute("OrderNum", String.valueOf(getOrderNum()));
		element.addAttribute("isNoneStatus", String.valueOf(isNoneStatus()));
		return element;
	}

	public List<BusinessClass> getBelongBusinessClass() {
		return belongBusinessClass;
	}

	public void setBelongBusinessClass(List<BusinessClass> belongBusinessClass) {
		this.belongBusinessClass = belongBusinessClass;
	}

	public String getBusinessCode() {
		return businessCode;
	}

	public void setBusinessCode(String businessCode) {
		this.businessCode = businessCode;
	}

	public AbstractModelElement getParent() {
		return this.belongBescne;
	}

	public List<AbstractModelElement> getChildren() {
		return EMPTY_CHILD;
	}

	public boolean isNoneStatus() {
		return isNoneStatus;
	}

	public void setNoneStatus(boolean isNoneStatus) {
		this.isNoneStatus = isNoneStatus;
	}

	public BEScene getBelongBescne() {
		return belongBescne;
	}

	public void setBelongBescne(BEScene belongBescne) {
		this.belongBescne = belongBescne;
	}

}
