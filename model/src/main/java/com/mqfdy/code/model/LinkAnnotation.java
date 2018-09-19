package com.mqfdy.code.model;

import java.util.List;

import org.dom4j.Element;

import com.mqfdy.code.model.utils.StringUtil;

// TODO: Auto-generated Javadoc
/**
 * 实体关系.
 *
 * @author mqfdy
 */
public class LinkAnnotation extends AbstractModelElement {

	/** The belong package. */
	private ModelPackage belongPackage;

	/** 实体A. */
	private AbstractModelElement classA;

	/** 实体B. */
	private AbstractModelElement classB;


	/** The Constant CLASSA. */
	public static final String CLASSA = "ClassA";
	
	/** The Constant CLASSB. */
	public static final String CLASSB = "ClassB";
	
	/** The class aid. */
	private String classAid;

	/** The class bid. */
	private String classBid;

	/**
	 * Instantiates a new link annotation.
	 */
	public LinkAnnotation() {
	}

	/**
	 * 通过 XML 对象构造 Association.
	 *
	 * @param associationElement
	 *            the association element
	 * @param bom
	 *            the bom
	 */
	public LinkAnnotation(Element associationElement, BusinessObjectModel bom) {

		initBasicAttributes(associationElement);

		this.classAid = associationElement.elementTextTrim(CLASSA);
		this.classBid = associationElement.elementTextTrim(CLASSB);

	}

	/**
	 * @param associationsElement
	 * @return
	 */
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

	/**
	 * Gets the class A.
	 *
	 * @author mqfdy
	 * @return the class A
	 * @Date 2018-09-03 09:00
	 */
	public AbstractModelElement getClassA() {
		return classA;
	}

	/**
	 * @return
	 */
	public LinkAnnotation clone() {
		LinkAnnotation element = new LinkAnnotation();
		copy(element);
		element.setClassA(classA);
		element.setClassB(classB);
		return element;
	}

	/**
	 * Copy.
	 *
	 * @author mqfdy
	 * @param dest
	 *            the dest
	 * @Date 2018-09-03 09:00
	 */
	protected void copy(LinkAnnotation dest) {
		dest.name = this.name;
		dest.displayName = this.displayName;
		dest.remark = this.remark;
	}

	/**
	 * Sets the class A.
	 *
	 * @author mqfdy
	 * @param classA
	 *            the new class A
	 * @Date 2018-09-03 09:00
	 */
	public void setClassA(AbstractModelElement classA) {
		this.classA = classA;
	}

	/**
	 * Gets the class B.
	 *
	 * @author mqfdy
	 * @return the class B
	 * @Date 2018-09-03 09:00
	 */
	public AbstractModelElement getClassB() {
		return classB;
	}

	/**
	 * Sets the class B.
	 *
	 * @author mqfdy
	 * @param classB
	 *            the new class B
	 * @Date 2018-09-03 09:00
	 */
	public void setClassB(AbstractModelElement classB) {
		this.classB = classB;
	}


	/**
	 * Gets the class aid.
	 *
	 * @author mqfdy
	 * @return the class aid
	 * @Date 2018-09-03 09:00
	 */
	public String getClassAid() {
		return classAid;
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
	 * Sets the class aid.
	 *
	 * @author mqfdy
	 * @param classAid
	 *            the new class aid
	 * @Date 2018-09-03 09:00
	 */
	public void setClassAid(String classAid) {
		this.classAid = classAid;
	}

	/**
	 * Gets the class bid.
	 *
	 * @author mqfdy
	 * @return the class bid
	 * @Date 2018-09-03 09:00
	 */
	public String getClassBid() {
		return classBid;
	}

	/**
	 * Sets the class bid.
	 *
	 * @author mqfdy
	 * @param classBid
	 *            the new class bid
	 * @Date 2018-09-03 09:00
	 */
	public void setClassBid(String classBid) {
		this.classBid = classBid;
	}

	/**
	 * @return
	 */
	public AbstractModelElement getParent() {
		return this.belongPackage.getLinkPackage();
	}

	/**
	 * @return
	 */
	public List<AbstractModelElement> getChildren() {
		return EMPTY_CHILD;
	}

}
