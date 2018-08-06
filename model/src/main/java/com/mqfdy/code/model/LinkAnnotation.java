package com.mqfdy.code.model;

import java.util.List;

import org.dom4j.Element;

import com.mqfdy.code.model.utils.StringUtil;

/**
 * 实体关系
 * 
 * @author mqfdy
 * 
 */
public class LinkAnnotation extends AbstractModelElement {

	private ModelPackage belongPackage;

	/**
	 * 实体A
	 */
	private AbstractModelElement classA;

	/**
	 * 实体B
	 */
	private AbstractModelElement classB;


	public static final String CLASSA = "ClassA";
	
	public static final String CLASSB = "ClassB";
	
	private String classAid;

	private String classBid;

	public LinkAnnotation() {
	}

	/**
	 * 通过 XML 对象构造 Association
	 * 
	 * @param associationElement
	 */
	public LinkAnnotation(Element associationElement, BusinessObjectModel bom) {

		initBasicAttributes(associationElement);

		this.classAid = associationElement.elementTextTrim(CLASSA);
		this.classBid = associationElement.elementTextTrim(CLASSB);

	}

	public Element generateXmlElement(Element associationsElement) {
		Element associationElement = associationsElement
				.addElement("LinkAnnotation");
		this.generateBasicAttributes(associationElement);

		// 在Association节点下创建SourceEntity，DestEntity，Type，SourceCascadeDelete
		// TargetCascadeDelete，NavigateToSource，NavigateToTarget，PersistencePloy子节点
		associationElement.addElement(CLASSA).addText(
				getClassA() == null ? "" : StringUtil
						.convertNull2EmptyStr(getClassA().getId()));
		associationElement.addElement(CLASSB).addText(
				getClassB() == null ? "" : StringUtil
						.convertNull2EmptyStr(getClassB().getId()));

		this.classAid = getClassA() == null ? "" : getClassA().getId();
		this.classBid = getClassB() == null ? "" : getClassB().getId();
		return associationElement;
	}

	public AbstractModelElement getClassA() {
		return classA;
	}

	public LinkAnnotation clone() {
		LinkAnnotation element = new LinkAnnotation();
		copy(element);
		element.setClassA(classA);
		element.setClassB(classB);
		return element;
	}

	protected void copy(LinkAnnotation dest) {
		dest.name = this.name;
		dest.displayName = this.displayName;
		dest.remark = this.remark;
	}

	public void setClassA(AbstractModelElement classA) {
		this.classA = classA;
	}

	public AbstractModelElement getClassB() {
		return classB;
	}

	public void setClassB(AbstractModelElement classB) {
		this.classB = classB;
	}


	public String getClassAid() {
		return classAid;
	}

	public ModelPackage getBelongPackage() {
		return belongPackage;
	}

	public void setBelongPackage(ModelPackage belongPackage) {
		this.belongPackage = belongPackage;
	}

	public void setClassAid(String classAid) {
		this.classAid = classAid;
	}

	public String getClassBid() {
		return classBid;
	}

	public void setClassBid(String classBid) {
		this.classBid = classBid;
	}

	public AbstractModelElement getParent() {
		return this.belongPackage.getLinkPackage();
	}

	public List<AbstractModelElement> getChildren() {
		return EMPTY_CHILD;
	}

}
