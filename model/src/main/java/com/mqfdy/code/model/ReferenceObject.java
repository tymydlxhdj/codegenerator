package com.mqfdy.code.model;

import java.util.List;

import org.dom4j.Element;

import com.mqfdy.code.model.utils.ModelUtil;
import com.mqfdy.code.model.utils.StringUtil;

// TODO: Auto-generated Javadoc
/**
 * 引用对象.
 *
 * @author mqfdy
 */
public class ReferenceObject extends AbstractModelElement {

	/** The belong package. */
	private ModelPackage belongPackage;
	
	/** 引用模型(唯一编号). */
	private String referenceModelId;

	/** 引用对象(唯一编号). */
	private String referenceObjectId;

	/** 引用模型路径. */
	private String referenceModePath;

	/** 引用对象实例. */
	private AbstractModelElement referenceObject;

	/**
	 * Instantiates a new reference object.
	 *
	 * @param refObjectElement
	 *            the ref object element
	 */
	public ReferenceObject(Element refObjectElement) {

		initBasicAttributes(refObjectElement);

		// 获取RefObject节点refId，refModelId, refModelPath属性值
		String refId = refObjectElement.attributeValue("refId");
		String refModelId = refObjectElement.attributeValue("refModelId");
		String refModelPath = refObjectElement.attributeValue(IModelElement.REFMODELPATH);
		// setId(id);
		setReferenceModelId(refModelId);
		setReferenceObjectId(refId);
		setReferenceModePath(refModelPath);
	}

	/**
	 * Instantiates a new reference object.
	 */
	public ReferenceObject() {
	}

	/**
	 * Instantiates a new reference object.
	 *
	 * @param referenceObject
	 *            the reference object
	 */
	public ReferenceObject(AbstractModelElement referenceObject) {
		BusinessObjectModel rbom = ModelUtil
				.getBusinessModelOfElement(referenceObject);
		String refModelPath = rbom.getExtendAttributies().get(IModelElement.REFMODELPATH)
				.toString();
		String referenceObjectId = referenceObject.getId();
		String referenceModelId = rbom.getId();
		this.setReferenceModelId(referenceModelId);
		this.setReferenceObjectId(referenceObjectId);
		this.setReferenceModePath(refModelPath);
		this.setName(referenceObject.getName());
		this.setDisplayName(referenceObject.getDisplayName());
		this.setReferenceObject(referenceObject);
	}

	/**
	 * @param refObjectsElement
	 * @return
	 */
	public Element generateXmlElement(Element refObjectsElement) {
		Element refObjectElement = refObjectsElement.addElement("RefObject");// RefObject节点
		this.generateBasicAttributes(refObjectElement);// 创建RefObject节点的普通属性
		refObjectElement.addAttribute("refId", getReferenceObjectId());
		refObjectElement.addAttribute("refModelId", getReferenceModelId());
		refObjectElement.addAttribute(IModelElement.REFMODELPATH,
				StringUtil.convertNull2EmptyStr(getReferenceModePath()));
		return refObjectElement;
	}

	/**
	 * Gets the reference object id.
	 *
	 * @author mqfdy
	 * @return the reference object id
	 * @Date 2018-09-03 09:00
	 */
	public String getReferenceObjectId() {
		return referenceObjectId;
	}

	/**
	 * Sets the reference object id.
	 *
	 * @author mqfdy
	 * @param referenceObjectId
	 *            the new reference object id
	 * @Date 2018-09-03 09:00
	 */
	public void setReferenceObjectId(String referenceObjectId) {
		this.referenceObjectId = referenceObjectId;
	}

	/**
	 * Gets the reference model id.
	 *
	 * @author mqfdy
	 * @return the reference model id
	 * @Date 2018-09-03 09:00
	 */
	public String getReferenceModelId() {
		return referenceModelId;
	}

	/**
	 * Sets the reference model id.
	 *
	 * @author mqfdy
	 * @param referenceModelId
	 *            the new reference model id
	 * @Date 2018-09-03 09:00
	 */
	public void setReferenceModelId(String referenceModelId) {
		this.referenceModelId = referenceModelId;
	}

	/**
	 * Gets the reference object.
	 *
	 * @author mqfdy
	 * @return the reference object
	 * @Date 2018-09-03 09:00
	 */
	public AbstractModelElement getReferenceObject() {
		if (referenceObject != null)
			referenceObject.setStereotype("2");
		return referenceObject;
	}

	/**
	 * Sets the reference object.
	 *
	 * @author mqfdy
	 * @param referenceObject
	 *            the new reference object
	 * @Date 2018-09-03 09:00
	 */
	public void setReferenceObject(AbstractModelElement referenceObject) {
		this.referenceObject = referenceObject;
	}

	/**
	 * Gets the reference mode path.
	 *
	 * @author mqfdy
	 * @return the reference mode path
	 * @Date 2018-09-03 09:00
	 */
	public String getReferenceModePath() {
		return referenceModePath;
	}

	/**
	 * Sets the reference mode path.
	 *
	 * @author mqfdy
	 * @param referenceModePath
	 *            the new reference mode path
	 * @Date 2018-09-03 09:00
	 */
	public void setReferenceModePath(String referenceModePath) {
		this.referenceModePath = referenceModePath;
	}

	/**
	 * Gets the belong package.
	 *
	 * @author mqfdy
	 * @return the belong package
	 * @Date 2018-09-03 09:00
	 */
	public ModelPackage getBelongPackage() {
		return belongPackage;
	}

	/**
	 * Sets the belong package.
	 *
	 * @author mqfdy
	 * @param belongPackage
	 *            the new belong package
	 * @Date 2018-09-03 09:00
	 */
	public void setBelongPackage(ModelPackage belongPackage) {
		this.belongPackage = belongPackage;
	}

	/**
	 * @return
	 */
	public AbstractModelElement getParent() {
		return this.belongPackage.getEntityPackage();
	}

	/**
	 * @return
	 */
	public List<AbstractModelElement> getChildren() {
		return EMPTY_CHILD;
	}

}
