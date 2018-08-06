package com.mqfdy.code.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Element;

import com.mqfdy.code.model.utils.StringUtil;

/**
 * 继承关系
 * 
 * @author mqfdy
 * 
 */
public class Inheritance extends AbstractModelElement {

	public static final String PERSIST_POLICY = "PersistPolicy";

	public static final String TABLE_PER_CLASS_HIERARCHY = "1";
	public static final String TABLE_PER_CONCRETECLASS = "2";
	public static final String TABLE_PER_SUBCLASS = "3";
	private ModelPackage belongPackage;

	/**
	 * 父类
	 */
	private BusinessClass parentClass;

	/**
	 * 子类
	 */
	private BusinessClass childClass;

	/**
	 * 持久化策略
	 */
	private String persistencePloy;

	/**
	 * 持久化策略参数列表
	 */
	private Map<String, String> persistencePloyParams;

	public Inheritance() {
		this.persistencePloyParams = new HashMap<String, String>();
	}

	public Inheritance(BusinessClass parentClass, BusinessClass childClass) {
		this.persistencePloyParams = new HashMap<String, String>();
		this.parentClass = parentClass;
		this.childClass = childClass;

	}

	/**
	 * 通过 XML 对象构造 Inheritance
	 * 
	 * @param associationElement
	 */
	@SuppressWarnings("unchecked")
	public Inheritance(Element inheritanceElement, BusinessObjectModel bom) {

		persistencePloyParams = new HashMap<String, String>();

		initBasicAttributes(inheritanceElement);

		// 获取Inheritance节点下的ParentEntity，ChildEntity值，找出对应的BusinessEntity对象设值
		String parentClassId = inheritanceElement
				.elementTextTrim("ParentClass");
		String childClassId = inheritanceElement.elementTextTrim("ChildClass");

		List<BusinessClass> bcs = bom.getBusinessClasses();
		for (BusinessClass bc : bcs) {
			if (bc.getId().equals(parentClassId)) {
				setParentClass(bc);
			}
			if (bc.getId().equals(childClassId)) {
				setChildClass(bc);
			}
			if (getParentClass() != null && getChildClass() != null) {
				break;
			}
		}
		// setParentClass(new BusinessClass(parentClassId));
		// setChildClass(new BusinessClass(childClassId));

		// 获取Inheritance节点下的PersistencePloy节点值
		String persistencePloy = inheritanceElement
				.elementTextTrim("PersistencePloy");
		setPersistencePloy(persistencePloy);

		// 获取获取Inheritance节点下PersistencePloyParams节点
		Element persistencePloyParamsElement = inheritanceElement
				.element(Association.PERSISTENCEPLOYPARAMS);
		if (persistencePloyParamsElement != null) {// PersistencePloyParams节点存在
			// 获取PersistencePloyParams节点下的Param节点列表
			List<Element> paramElementList = persistencePloyParamsElement
					.elements("Param");
			for (Iterator<Element> iter = paramElementList.iterator(); iter
					.hasNext();) {
				Element paramElement = iter.next();// Param节点
				// 获取Param节点下的name和value值
				String key = paramElement.elementTextTrim("name");
				String value = paramElement.elementTextTrim("value");
				persistencePloyParams.put(key, value);
			}
		}
	}

	public Element generateXmlElement(Element inheritancesElement) {
		Element inheritanceElement = inheritancesElement
				.addElement("Inheritance");
		this.generateBasicAttributes(inheritanceElement);

		// 在Inheritance节点下创建ParentEntity，ChildEntity，PersistencePloy子节点
		inheritanceElement.addElement("ParentClass").addText(
				getParentClass() == null ? "" : StringUtil
						.convertNull2EmptyStr(getParentClass().getId()));
		inheritanceElement.addElement("ChildClass").addText(
				getChildClass() == null ? "" : StringUtil
						.convertNull2EmptyStr(getChildClass().getId()));
		inheritanceElement.addElement("PersistencePloy").addText(
				StringUtil.convertNull2EmptyStr(getPersistencePloy()));

		// 在Inheritance节点下创建PersistencePloyParams和Param节点
		Map<String, String> params = getPersistencePloyParams();
		if (params != null) {
			Element persistencePloyParamsElement = inheritanceElement
					.addElement(Association.PERSISTENCEPLOYPARAMS);
			// inheritanceElement.addContent(persistencePloyParamsElement);
			this.generateParam(persistencePloyParamsElement, params);
		}
		return inheritanceElement;
	}

	public BusinessClass getParentClass() {
		return parentClass;
	}

	public void setParentClass(BusinessClass parentClass) {
		this.parentClass = parentClass;
	}

	public BusinessClass getChildClass() {
		return childClass;
	}

	public void setChildClass(BusinessClass childClass) {
		this.childClass = childClass;
	}

	public String getPersistencePloy() {
		return persistencePloy;
	}

	public void setPersistencePloy(String persistencePloy) {
		this.persistencePloy = persistencePloy;
	}

	public void setPersistencePloy(String persistencePloy,
			Map<String, String> params) {
		this.persistencePloy = persistencePloy;
		this.persistencePloyParams = params;
	}

	public Map<String, String> getPersistencePloyParams() {
		return this.persistencePloyParams;
	}

	public void setPersistencePloyParams(
			Map<String, String> persistencePloyParams) {
		this.persistencePloyParams = persistencePloyParams;
	}

	public ModelPackage getBelongPackage() {
		return belongPackage;
	}

	public void setBelongPackage(ModelPackage belongPackage) {
		this.belongPackage = belongPackage;
	}

	public AbstractModelElement getParent() {
		return null;// this.belongPackage.getInheritancePackage();
	}

	public List<AbstractModelElement> getChildren() {
		return EMPTY_CHILD;
	}

}
