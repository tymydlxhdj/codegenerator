package com.mqfdy.code.model;

import java.util.List;

import org.dom4j.Element;

import com.mqfdy.code.model.utils.ModelUtil;
import com.mqfdy.code.model.utils.StringUtil;

/**
 * 引用对象
 * 
 * @author mqfdy
 * 
 */
public class ReferenceObject extends AbstractModelElement {

	private ModelPackage belongPackage;
	/**
	 * 引用模型(唯一编号)
	 */
	private String referenceModelId;

	/**
	 * 引用对象(唯一编号)
	 */
	private String referenceObjectId;

	/**
	 * 引用模型路径
	 */
	private String referenceModePath;

	/**
	 * 引用对象实例
	 */
	private AbstractModelElement referenceObject;

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

	public ReferenceObject() {
	}

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

	public Element generateXmlElement(Element refObjectsElement) {
		Element refObjectElement = refObjectsElement.addElement("RefObject");// RefObject节点
		this.generateBasicAttributes(refObjectElement);// 创建RefObject节点的普通属性
		refObjectElement.addAttribute("refId", getReferenceObjectId());
		refObjectElement.addAttribute("refModelId", getReferenceModelId());
		refObjectElement.addAttribute(IModelElement.REFMODELPATH,
				StringUtil.convertNull2EmptyStr(getReferenceModePath()));
		return refObjectElement;
	}

	public String getReferenceObjectId() {
		return referenceObjectId;
	}

	public void setReferenceObjectId(String referenceObjectId) {
		this.referenceObjectId = referenceObjectId;
	}

	public String getReferenceModelId() {
		return referenceModelId;
	}

	public void setReferenceModelId(String referenceModelId) {
		this.referenceModelId = referenceModelId;
	}

	public AbstractModelElement getReferenceObject() {
		if (referenceObject != null)
			referenceObject.setStereotype("2");
		return referenceObject;
	}

	public void setReferenceObject(AbstractModelElement referenceObject) {
		this.referenceObject = referenceObject;
	}

	public String getReferenceModePath() {
		return referenceModePath;
	}

	public void setReferenceModePath(String referenceModePath) {
		this.referenceModePath = referenceModePath;
	}

	public ModelPackage getBelongPackage() {
		return belongPackage;
	}

	public void setBelongPackage(ModelPackage belongPackage) {
		this.belongPackage = belongPackage;
	}

	public AbstractModelElement getParent() {
		return this.belongPackage.getEntityPackage();
	}

	public List<AbstractModelElement> getChildren() {
		return EMPTY_CHILD;
	}

}
