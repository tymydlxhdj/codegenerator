package com.mqfdy.code.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Element;

import com.mqfdy.code.model.utils.StringUtil;

// TODO: Auto-generated Javadoc
/**
 * 校验器.
 *
 * @author mqfdy
 */
public class Validator extends AbstractModelElement implements Cloneable {

	/** 所属的属性. */
	private Property belongProperty;

	/** 校验器类型. */
	private String validatorType;

	/** 校验错误提示信息. */
	private String validatorMessage;

	/** 校验表达式. */
	private String expression;

	/** 自定义校验器类. */
	private String validatorClass;

	/** 接口方法名. */
	private String interfaceName;

	/** 校验器参数列表. */
	private Map<String, String> validatorParams;

	/**
	 * Instantiates a new validator.
	 */
	public Validator() {
		super();
		validatorParams = new HashMap<String, String>();
	}

	/**
	 * 通过 XML 构造 Validator.
	 *
	 * @param validatorElement
	 *            the validator element
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

	/**
	 * @param validatorsElement
	 * @return
	 */
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

	/**
	 * Gets the validator type.
	 *
	 * @author mqfdy
	 * @return the validator type
	 * @Date 2018-09-03 09:00
	 */
	public String getValidatorType() {
		return validatorType;
	}

	/**
	 * Sets the validator type.
	 *
	 * @author mqfdy
	 * @param validatorType
	 *            the new validator type
	 * @Date 2018-09-03 09:00
	 */
	public void setValidatorType(String validatorType) {
		this.validatorType = validatorType;
	}

	/**
	 * Gets the validator message.
	 *
	 * @author mqfdy
	 * @return the validator message
	 * @Date 2018-09-03 09:00
	 */
	public String getValidatorMessage() {
		return validatorMessage;
	}

	/**
	 * Sets the validator message.
	 *
	 * @author mqfdy
	 * @param validatorMessage
	 *            the new validator message
	 * @Date 2018-09-03 09:00
	 */
	public void setValidatorMessage(String validatorMessage) {
		this.validatorMessage = validatorMessage;
	}

	/**
	 * Gets the validator params.
	 *
	 * @author mqfdy
	 * @return the validator params
	 * @Date 2018-09-03 09:00
	 */
	public Map<String, String> getValidatorParams() {
		return validatorParams;
	}

	/**
	 * Adds the validator param.
	 *
	 * @author mqfdy
	 * @param key
	 *            the key
	 * @param value
	 *            the value
	 * @Date 2018-09-03 09:00
	 */
	public void addValidatorParam(String key, String value) {
		validatorParams.put(key, value);
	}

	/**
	 * Gets the belong property.
	 *
	 * @author mqfdy
	 * @return the belong property
	 * @Date 2018-09-03 09:00
	 */
	public Property getBelongProperty() {
		return belongProperty;
	}

	/**
	 * Sets the belong property.
	 *
	 * @author mqfdy
	 * @param belongProperty
	 *            the new belong property
	 * @Date 2018-09-03 09:00
	 */
	public void setBelongProperty(Property belongProperty) {
		this.belongProperty = belongProperty;
	}

	/**
	 * Gets the expression.
	 *
	 * @author mqfdy
	 * @return the expression
	 * @Date 2018-09-03 09:00
	 */
	public String getExpression() {
		return expression;
	}

	/**
	 * Sets the expression.
	 *
	 * @author mqfdy
	 * @param expression
	 *            the new expression
	 * @Date 2018-09-03 09:00
	 */
	public void setExpression(String expression) {
		this.expression = expression;
	}

	/**
	 * Gets the validator class.
	 *
	 * @author mqfdy
	 * @return the validator class
	 * @Date 2018-09-03 09:00
	 */
	public String getValidatorClass() {
		return validatorClass;
	}

	/**
	 * Sets the validator class.
	 *
	 * @author mqfdy
	 * @param validatorClass
	 *            the new validator class
	 * @Date 2018-09-03 09:00
	 */
	public void setValidatorClass(String validatorClass) {
		this.validatorClass = validatorClass;
	}

	/**
	 * Gets the interface name.
	 *
	 * @author mqfdy
	 * @return the interface name
	 * @Date 2018-09-03 09:00
	 */
	public String getInterfaceName() {
		return interfaceName;
	}

	/**
	 * Sets the interface name.
	 *
	 * @author mqfdy
	 * @param interfaceName
	 *            the new interface name
	 * @Date 2018-09-03 09:00
	 */
	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}

	/**
	 * Sets the validator params.
	 *
	 * @author mqfdy
	 * @param validatorParams
	 *            the validator params
	 * @Date 2018-09-03 09:00
	 */
	public void setValidatorParams(Map<String, String> validatorParams) {
		this.validatorParams = validatorParams;
	}

	/**
	 * @return
	 */
	public Validator clone() {
		Validator va = new Validator();
		copy(va);
		return va;
	}

	/**
	 * Copy.
	 *
	 * @author mqfdy
	 * @param va
	 *            the va
	 * @Date 2018-09-03 09:00
	 */
	public void copy(Validator va) {
		super.copy(va);
		va.expression = this.expression;
		va.interfaceName = this.interfaceName;
		va.validatorClass = this.validatorClass;
		va.validatorMessage = this.validatorMessage;
		va.validatorParams = this.validatorParams;
		va.validatorType = this.validatorType;
	}

	/**
	 * @return
	 */
	public Validator cloneChangeId() {
		Validator va = new Validator();
		copyChangeId(va);
		return va;
	}

	/**
	 * Copy change id.
	 *
	 * @author mqfdy
	 * @param va
	 *            the va
	 * @Date 2018-09-03 09:00
	 */
	public void copyChangeId(Validator va) {
		super.copyChangeId(va);
		va.expression = this.expression;
		va.interfaceName = this.interfaceName;
		va.validatorClass = this.validatorClass;
		va.validatorMessage = this.validatorMessage;
		va.validatorParams = this.validatorParams;
		va.validatorType = this.validatorType;
	}

	/**
	 * @return
	 */
	public AbstractModelElement getParent() {
		return this.belongProperty;
	}

	/**
	 * @return
	 */
	public List<AbstractModelElement> getChildren() {
		return EMPTY_CHILD;
	}

}
