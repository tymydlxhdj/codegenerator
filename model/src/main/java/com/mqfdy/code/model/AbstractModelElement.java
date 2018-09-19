package com.mqfdy.code.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.dom4j.Element;

import com.mqfdy.code.model.utils.ModelUtil;
import com.mqfdy.code.model.utils.StringUtil;

// TODO: Auto-generated Javadoc
/**
 * 抽象的模型元素.
 *
 * @author mqfdy
 */
public abstract class AbstractModelElement implements IModelElement,
		Comparable<AbstractModelElement> {

	/** The empty child. */
	public final List<AbstractModelElement> EMPTY_CHILD = new ArrayList<AbstractModelElement>();
	
	/** ID唯一编号. */
	protected String id;

	/** 名称. */
	protected String name;

	/** 显示名称. */
	protected String displayName;

	/** 备注. */
	protected String remark;

	/** 构造类型. */
	private String stereotype;

	/** 排序号. */
	private int orderNum;

	/** 扩展属性. */
	private Map<String, Object> extendAttributies;

	/**
	 * 构造函数1.
	 */
	public AbstractModelElement() {
		setId(UUID.randomUUID().toString().replaceAll("-", ""));
	}

	/**
	 * 构造函数2.
	 *
	 * @param id
	 *            the id
	 */
	public AbstractModelElement(String id) {
		setId(id);
	}

	/**
	 * 构造函数4.
	 *
	 * @param name
	 *            the name
	 * @param displayName
	 *            the display name
	 */
	public AbstractModelElement(String name, String displayName) {
		setId(UUID.randomUUID().toString().replaceAll("-", ""));
		setName(name);
		setDisplayName(displayName);
	}

	/**
	 * 生成 XML 元素.
	 *
	 * @author mqfdy
	 * @param ele
	 *            the ele
	 * @return the element
	 * @Date 2018-09-03 09:00
	 */
	public Element generateXmlElement(Element ele) {
		Element element = ele.addElement(getType());
		generateBasicAttributes(element);
		return element;
	}

	/**
	 * 生成基本属性.
	 *
	 * @author mqfdy
	 * @param element
	 *            the element
	 * @Date 2018-09-03 09:00
	 */
	public void generateBasicAttributes(Element element) {
		element.addAttribute("id", StringUtil.convertNull2EmptyStr(getId()));
		element.addAttribute("name", StringUtil.convertNull2EmptyStr(getName()));
		element.addAttribute("displayName",StringUtil.convertNull2EmptyStr(getDisplayName()));
		if(!StringUtil.isEmpty(getStereotype())){
			element.addAttribute("stereotype",StringUtil.convertNull2EmptyStr(getStereotype()));
		}
		if (!StringUtil.isEmpty(remark)) {
			element.addElement("Remark").addText(
					StringUtil.convertNull2EmptyStr(getRemark()));
		}
	}

	/**
	 * 创建Param，name，value节点.
	 *
	 * @author mqfdy
	 * @param paramsElement
	 *            the params element
	 * @param params
	 *            the params
	 * @Date 2018-09-03 09:00
	 */
	protected void generateParam(Element paramsElement,
			Map<String, String> params) {
		Set<String> paramSet = params.keySet();
		for (Iterator<String> iterator = paramSet.iterator(); iterator
				.hasNext();) {
			Element paramElement = paramsElement.addElement("Param");
			String name = iterator.next();

			paramElement.addElement("name").addText(
					StringUtil.convertNull2EmptyStr(name));// Param节点name节点

			if ("FilterSql".equals(name)) {
				paramElement.addElement("value").addCDATA(
						StringUtil.convertNull2EmptyStr(params.get(name)));// Param节点value节点
			} else {
				paramElement.addElement("value").addText(
						StringUtil.convertNull2EmptyStr(params.get(name)));// Param节点value节点
			}
			// paramsElement.addContent(paramElement);
		}
	}

	/**
	 * 设置基本属性.
	 *
	 * @author mqfdy
	 * @param element
	 *            the element
	 * @Date 2018-09-03 09:00
	 */
	public void initBasicAttributes(Element element) {
		String id = element.attributeValue("id");
		String name = element.attributeValue("name");
		String stereotype = element.attributeValue("stereotype");
		String displayName = element.attributeValue("displayName");
		String remark = element.elementTextTrim("Remark");
		this.setId(id);
		this.setName(name);
		this.setStereotype(stereotype);
		this.setDisplayName(displayName);
		this.setRemark(remark);
	}

	/**
	 * @return
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * Sets the display name.
	 *
	 * @author mqfdy
	 * @param displayName
	 *            the new display name
	 * @Date 2018-09-03 09:00
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	/**
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @author mqfdy
	 * @param name
	 *            the new name
	 * @Date 2018-09-03 09:00
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 返回模型元素类型.
	 *
	 * @author mqfdy
	 * @return the type
	 * @Date 2018-09-03 09:00
	 */
	public String getType() {
		return this.getClass().getSimpleName();
	}

	/**
	 * Gets the display type.
	 *
	 * @author mqfdy
	 * @return the display type
	 * @Date 2018-09-03 09:00
	 */
	public String getDisplayType() {
		return ModelUtil.getModelTypeDisplay(getType());
	}

	/**
	 * @return
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @author mqfdy
	 * @param id
	 *            the new id
	 * @Date 2018-09-03 09:00
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return
	 */
	public String getStereotype() {
		return this.stereotype;
	}

	/**
	 * Sets the stereotype.
	 *
	 * @author mqfdy
	 * @param stereotype
	 *            the new stereotype
	 * @Date 2018-09-03 09:00
	 */
	public void setStereotype(String stereotype) {
//		if (stereotype == null || "".equals(stereotype)) {
//			stereotype = "1";
//		}
		this.stereotype = stereotype;
	}

	/**
	 * Gets the remark.
	 *
	 * @author mqfdy
	 * @return the remark
	 * @Date 2018-09-03 09:00
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * Sets the remark.
	 *
	 * @author mqfdy
	 * @param remark
	 *            the new remark
	 * @Date 2018-09-03 09:00
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * Gets the order num.
	 *
	 * @author mqfdy
	 * @return the order num
	 * @Date 2018-09-03 09:00
	 */
	public int getOrderNum() {
		return orderNum;
	}

	/**
	 * Sets the order num.
	 *
	 * @author mqfdy
	 * @param orderNum
	 *            the new order num
	 * @Date 2018-09-03 09:00
	 */
	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}

	/**
	 * Gets the extend attributies.
	 *
	 * @author mqfdy
	 * @return the extend attributies
	 * @Date 2018-09-03 09:00
	 */
	public Map<String, Object> getExtendAttributies() {
		if (extendAttributies == null) {
			extendAttributies = new HashMap<String, Object>();
		}
		return extendAttributies;
	}

	/**
	 * Sets the extend attributies.
	 *
	 * @author mqfdy
	 * @param extendAttributies
	 *            the extend attributies
	 * @Date 2018-09-03 09:00
	 */
	public void setExtendAttributies(Map<String, Object> extendAttributies) {
		this.extendAttributies = extendAttributies;
	}

	/**
	 * 获取各个元素在树上排序时的等级.
	 *
	 * @author mqfdy
	 * @return the priority
	 * @Date 2018-09-03 09:00
	 */
	public int getPriority() {
		return IModelElement.PRIORITY_MODELELEMENT;
	}

	/**
	 * Copy.
	 *
	 * @author mqfdy
	 * @param dest
	 *            the dest
	 * @Date 2018-09-03 09:00
	 */
	protected void copy(AbstractModelElement dest) {
		dest.id = this.id;
		dest.name = this.name;
		dest.displayName = this.displayName;
		dest.orderNum = this.orderNum;
		dest.remark = this.remark;
		dest.stereotype = this.stereotype;
	}

	/**
	 * @return
	 */
	public AbstractModelElement clone() {
		AbstractModelElement element = new AbstractModelElement() {

			public AbstractModelElement getParent() {
				return null;
			}

			public List<?> getChildren() {
				return null;
			}
		};
		copy(element);
		return element;
	}

	/**
	 * Clone change id.
	 *
	 * @author mqfdy
	 * @return the abstract model element
	 * @Date 2018-09-03 09:00
	 */
	public AbstractModelElement cloneChangeId() {
		AbstractModelElement element = new AbstractModelElement() {

			public AbstractModelElement getParent() {
				return null;
			}

			public List<?> getChildren() {
				return null;
			}
		};
		copyChangeId(element);
		return element;
	}

	/**
	 * Copy change id.
	 *
	 * @author mqfdy
	 * @param dest
	 *            the dest
	 * @Date 2018-09-03 09:00
	 */
	protected void copyChangeId(AbstractModelElement dest) {
		dest.id = UUID.randomUUID().toString().replaceAll("-", "");
		dest.name = this.name;
		dest.displayName = this.displayName;
		dest.orderNum = this.orderNum;
		dest.remark = this.remark;
		dest.stereotype = IModelElement.STEREOTYPE_CUSTOM;
	}

	/**
	 * @param element
	 * @return
	 */
	public int compareTo(AbstractModelElement element) {
		int result = 0;
		if (getPriority() == element.getPriority()) {
			if (STEREOTYPE_BUILDIN.equals(getStereotype())
					&& STEREOTYPE_BUILDIN.equals(element.getStereotype())
					|| STEREOTYPE_CUSTOM.equals(getStereotype())
					&& STEREOTYPE_CUSTOM.equals(element.getStereotype())) {
				result = this.getOrderNum() - element.getOrderNum();
				if (result == 0) {
					result = StringUtil.convertNull2EmptyStr(
							this.getDisplayName()).compareTo(
							StringUtil.convertNull2EmptyStr(element
									.getDisplayName()));
				}
			} else {
				result = StringUtil.convertNull2EmptyStr(this.getStereotype())
						.compareTo(
								StringUtil.convertNull2EmptyStr(element
										.getStereotype()));
			}
		} else {
			result = getPriority() - element.getPriority();
		}

		return result;
	}

	/**
	 * @return
	 */
	public String getFullName() {
		if (this.getParent() != null) {
			return this.getParent().getFullName() + "." + this.getName();
		} else {
			return this.getName();
		}

	}

}
