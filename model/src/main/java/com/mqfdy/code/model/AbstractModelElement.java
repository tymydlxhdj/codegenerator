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

/**
 * 抽象的模型元素
 * 
 * @author mqfdy
 * 
 */
public abstract class AbstractModelElement implements IModelElement,
		Comparable<AbstractModelElement> {

	public final List<AbstractModelElement> EMPTY_CHILD = new ArrayList<AbstractModelElement>();
	/**
	 * ID唯一编号
	 */
	protected String id;

	/**
	 * 名称
	 */
	protected String name;

	/**
	 * 显示名称
	 */
	protected String displayName;

	/**
	 * 备注
	 */
	protected String remark;

	/**
	 * 构造类型
	 */
	private String stereotype;

	/**
	 * 排序号
	 */
	private int orderNum;

	/**
	 * 扩展属性
	 */
	private Map<String, Object> extendAttributies;

	/**
	 * 构造函数1
	 */
	public AbstractModelElement() {
		setId(UUID.randomUUID().toString().replaceAll("-", ""));
	}

	/**
	 * 构造函数2
	 */
	public AbstractModelElement(String id) {
		setId(id);
	}

	/**
	 * 构造函数4
	 */
	public AbstractModelElement(String name, String displayName) {
		setId(UUID.randomUUID().toString().replaceAll("-", ""));
		setName(name);
		setDisplayName(displayName);
	}

	/**
	 * 生成 XML 元素
	 * 
	 * @return
	 */
	public Element generateXmlElement(Element ele) {
		Element element = ele.addElement(getType());
		generateBasicAttributes(element);
		return element;
	}

	/**
	 * 生成基本属性
	 * 
	 * @param element
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
	 * 创建Param，name，value节点
	 * 
	 * @param paramsElement
	 * @param params
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
	 * 设置基本属性
	 * 
	 * @param element
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

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 返回模型元素类型
	 */
	public String getType() {
		return this.getClass().getSimpleName();
	}

	public String getDisplayType() {
		return ModelUtil.getModelTypeDisplay(getType());
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStereotype() {
		return this.stereotype;
	}

	public void setStereotype(String stereotype) {
//		if (stereotype == null || "".equals(stereotype)) {
//			stereotype = "1";
//		}
		this.stereotype = stereotype;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}

	public Map<String, Object> getExtendAttributies() {
		if (extendAttributies == null) {
			extendAttributies = new HashMap<String, Object>();
		}
		return extendAttributies;
	}

	public void setExtendAttributies(Map<String, Object> extendAttributies) {
		this.extendAttributies = extendAttributies;
	}

	/**
	 * 获取各个元素在树上排序时的等级
	 * 
	 * @return
	 */
	public int getPriority() {
		return IModelElement.PRIORITY_MODELELEMENT;
	}

	protected void copy(AbstractModelElement dest) {
		dest.id = this.id;
		dest.name = this.name;
		dest.displayName = this.displayName;
		dest.orderNum = this.orderNum;
		dest.remark = this.remark;
		dest.stereotype = this.stereotype;
	}

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

	protected void copyChangeId(AbstractModelElement dest) {
		dest.id = UUID.randomUUID().toString().replaceAll("-", "");
		dest.name = this.name;
		dest.displayName = this.displayName;
		dest.orderNum = this.orderNum;
		dest.remark = this.remark;
		dest.stereotype = IModelElement.STEREOTYPE_CUSTOM;
	}

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

	public String getFullName() {
		if (this.getParent() != null) {
			return this.getParent().getFullName() + "." + this.getName();
		} else {
			return this.getName();
		}

	}

}
