package com.mqfdy.code.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Element;

import com.mqfdy.code.model.utils.StringUtil;

/**
 * 校验器
 * 
 * @author mqfdy
 * 
 */
public class Validator extends AbstractModelElement implements Cloneable {

	/**
	 * 所属的属性
	 */
	private Property belongProperty;

	/**
	 * 校验器类型
	 */
	private String validatorType;

	/**
	 * 校验错误提示信息
	 */
	private String validatorMessage;

	/**
	 * 校验表达式
	 */
	private String expression;

	/**
	 * 自定义校验器类
	 */
	private String validatorClass;

	/**
	 * 接口方法名
	 */
	private String interfaceName;

	/**
	 * 校验器参数列表
	 */
	private Map<String, String> validatorParams;

	public Validator() {
		super();
		validatorParams = new HashMap<String, String>();
	}

	/**
	 * 通过 XML 构造 Validator
	 * 
	 * @param validatorElement
	 */
	@SuppressWarnings("unchecked")
	public Validator(Element validatorElement) {

		initBasicAttributes(validatorElement);

		validatorParams = new HashMap<String, String>();

		String validatorType = validatorElement.attributeValue("validatorType");
		String validatorMessage = validatorElement
				.attributeValue("validatorMessage");
		String expression = validatorElement.attributeValue("expression");
		String validatorClass = validatorElement
				.attributeValue("validatorClass");
		String interfaceName = validatorElement.attributeValue("interfaceName");
		setValidatorType(validatorType);
		setValidatorMessage(validatorMessage);
		setExpression(expression);
		setValidatorClass(validatorClass);
		setInterfaceName(interfaceName);

		Element paramsElement = validatorElement.element("Params");
		if (paramsElement != null) {// Validator可能下面没有Params节点
			List<Element> paramElements = paramsElement.elements("Param");// //获取Params节点下的Param子节点集合
			if (paramElements != null) {
				for (Iterator<Element> iter = paramElements.iterator(); iter
						.hasNext();) {
					Element paramElement = iter.next();// Param节点
					String key = paramElement.elementTextTrim("name");
					String value = paramElement.elementTextTrim("value");
					validatorParams.put(key, value);
				}
			}

		}
	}

	public Element generateXmlElement(Element validatorsElement) {
		Element validatorElement = validatorsElement.addElement("Validator");// Validator节点
		this.generateBasicAttributes(validatorElement);// 创建Validator节点的一般属性
		validatorElement.addAttribute("orderNumber",
				StringUtil.convertNull2EmptyStr(getOrderNum() + ""));
		validatorElement.addAttribute("validatorType",
				StringUtil.convertNull2EmptyStr(getValidatorType()));
		validatorElement.addAttribute("validatorMessage",
				StringUtil.convertNull2EmptyStr(getValidatorMessage()));
		validatorElement.addAttribute("expression",
				StringUtil.convertNull2EmptyStr(getExpression()));
		validatorElement.addAttribute("validatorClass",
				StringUtil.convertNull2EmptyStr(getValidatorClass()));
		validatorElement.addAttribute("interfaceName",
				StringUtil.convertNull2EmptyStr(getInterfaceName()));

		// 创建Validator节点下的Params节点，没有就不创建
		Map<String, String> params = getValidatorParams();
		if (!params.isEmpty()) {
			Element paramsElement = validatorElement.addElement("Params");
			generateParam(paramsElement, params);
			// validatorElement.addContent(paramsElement);
		}
		return validatorElement;
	}

	public String getValidatorType() {
		return validatorType;
	}

	public void setValidatorType(String validatorType) {
		this.validatorType = validatorType;
	}

	public String getValidatorMessage() {
		return validatorMessage;
	}

	public void setValidatorMessage(String validatorMessage) {
		this.validatorMessage = validatorMessage;
	}

	public Map<String, String> getValidatorParams() {
		return validatorParams;
	}

	public void addValidatorParam(String key, String value) {
		validatorParams.put(key, value);
	}

	public Property getBelongProperty() {
		return belongProperty;
	}

	public void setBelongProperty(Property belongProperty) {
		this.belongProperty = belongProperty;
	}

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	public String getValidatorClass() {
		return validatorClass;
	}

	public void setValidatorClass(String validatorClass) {
		this.validatorClass = validatorClass;
	}

	public String getInterfaceName() {
		return interfaceName;
	}

	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}

	public void setValidatorParams(Map<String, String> validatorParams) {
		this.validatorParams = validatorParams;
	}

	public Validator clone() {
		Validator va = new Validator();
		copy(va);
		return va;
	}

	public void copy(Validator va) {
		super.copy(va);
		va.expression = this.expression;
		va.interfaceName = this.interfaceName;
		va.validatorClass = this.validatorClass;
		va.validatorMessage = this.validatorMessage;
		va.validatorParams = this.validatorParams;
		va.validatorType = this.validatorType;
	}

	public Validator cloneChangeId() {
		Validator va = new Validator();
		copyChangeId(va);
		return va;
	}

	public void copyChangeId(Validator va) {
		super.copyChangeId(va);
		va.expression = this.expression;
		va.interfaceName = this.interfaceName;
		va.validatorClass = this.validatorClass;
		va.validatorMessage = this.validatorMessage;
		va.validatorParams = this.validatorParams;
		va.validatorType = this.validatorType;
	}

	public AbstractModelElement getParent() {
		return this.belongProperty;
	}

	public List<AbstractModelElement> getChildren() {
		return EMPTY_CHILD;
	}

}
