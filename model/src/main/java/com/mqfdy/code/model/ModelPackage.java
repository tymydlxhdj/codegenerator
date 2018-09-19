package com.mqfdy.code.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Element;

import com.mqfdy.code.model.graph.Diagram;

// TODO: Auto-generated Javadoc
/**
 * 包对象.
 *
 * @author mqfdy
 */
public class ModelPackage extends AbstractPackage {

	/** The parent. */
	private AbstractModelElement parent;
	
	/** 实体固化包. */
	private SolidifyPackage entityPackage;
	
	/** 注释 固化包. */
	private SolidifyPackage annotationPackage;
	
	/** 连线固化包. */
	private SolidifyPackage linkPackage;

	/** 关联关系固化包. */
	private SolidifyPackage associationPackage;

	/**
	 * 继承关系固化包.
	 */
	// private SolidifyPackage inheritancePackage;

	public ModelPackage() {
		initInternalObjects();
	}

	/**
	 * Instantiates a new model package.
	 *
	 * @param name
	 *            the name
	 * @param displayName
	 *            the display name
	 */
	public ModelPackage(String name, String displayName) {
		super(name, displayName);
		initInternalObjects();
	}

	/**
	 * Instantiates a new model package.
	 *
	 * @param parent
	 *            the parent
	 * @param name
	 *            the name
	 * @param displayName
	 *            the display name
	 */
	public ModelPackage(AbstractModelElement parent, String name,
			String displayName) {
		super(name, displayName);
		this.setParent(parent);
		initInternalObjects();
	}

	/**
	 * 通过 XML 元素构造 ModelPackage对象.
	 *
	 * @param bom
	 *            the bom
	 * @param packageElement
	 *            the package element
	 */
	@SuppressWarnings("unchecked")
	public ModelPackage(BusinessObjectModel bom, Element packageElement) {
		initBasicAttributes(packageElement);
		initInternalObjects();
		// 获取package节点下的所有annotation节点
		Element annotationsElement = packageElement.element("Annotations");
		if (annotationsElement != null) {
			List<Element> annotationElements = annotationsElement
					.elements("Annotation");
			if (annotationElements != null
					&& annotationElements.size() > 0) {
				for (Iterator<Element> iter = annotationElements.iterator(); iter
						.hasNext();) {
					Element businessClassElement = iter.next();
					Annotation anno = new Annotation(businessClassElement);
					anno.setBelongPackage(this);
					bom.addAnnotation(anno);
				}
			}
		}
				
		// 获取package节点下的所有link节点
		Element linksElement = packageElement.element("Links");
		if (linksElement != null) {
			List<Element> linkElements = linksElement
					.elements("LinkAnnotation");
			if (linkElements != null
					&& linkElements.size() > 0) {
				for (Iterator<Element> iter = linkElements.iterator(); iter
						.hasNext();) {
					Element businessClassElement = iter.next();
					LinkAnnotation anno = new LinkAnnotation(businessClassElement,bom);
					anno.setBelongPackage(this);
					bom.addLinkAnnotation(anno);
				}
			}
		}
				
		// 获取package节点下的所有BusinessClasses节点
		Element businessClassesElement = packageElement.element("BusinessClasses");
		if (businessClassesElement != null) {
			List<Element> businessClassElements = businessClassesElement
					.elements("BusinessClass");
			if (businessClassElements != null
					&& businessClassElements.size() > 0) {
				for (Iterator<Element> iter = businessClassElements.iterator(); iter
						.hasNext();) {
					Element businessClassElement = iter.next();
					BusinessClass businessClass = new BusinessClass(
							businessClassElement);
					businessClass.setBelongPackage(this);
					bom.addBusinessClass(businessClass);
				}
			}
		}

		// 获取package节点下的所有Association节点
		Element associationsElement = packageElement.element("Associations");
		if (associationsElement != null) {
			List<Element> associationElements = associationsElement
					.elements("Association");
			if (associationElements != null && associationElements.size() > 0) {
				for (Iterator<Element> iter = associationElements.iterator(); iter
						.hasNext();) {
					Element associationElement = iter.next();
					Association sssociation = new Association(
							associationElement, bom);
					sssociation.setBelongPackage(this);
					bom.addAssociation(sssociation);
				}
			}
		}

		// 获取package节点下的所有Inheritance节点
		Element inheritancesElement = packageElement.element("Inheritances");
		if (inheritancesElement != null) {
			List<Element> inheritanceElements = inheritancesElement
					.elements("Inheritance");
			if (inheritanceElements != null && inheritanceElements.size() > 0) {
				for (Iterator<Element> iter = inheritanceElements.iterator(); iter
						.hasNext();) {
					Element inheritanceElement = iter.next();
					Inheritance inheritance = new Inheritance(
							inheritanceElement, bom);
					inheritance.setBelongPackage(this);
					bom.addInheritance(inheritance);
				}
			}
		}

		// 获取package节点下的所有ComplexDataType节点(先不用此复合数据类型)
		/*
		 * Element complexDataTypesElement =
		 * packageElement.element("ComplexDataTypes");
		 * if(complexDataTypesElement != null){ List<Element>
		 * complexDataTypeElements =
		 * complexDataTypesElement.getChildren("ComplexDataType");
		 * if(complexDataTypeElements != null && complexDataTypeElements.size()
		 * > 0){ for(Iterator<Element> iter =
		 * complexDataTypeElements.iterator();iter.hasNext();){ Element
		 * complexDataTypeElement = iter.next(); ComplexDataType complexDataType
		 * = new ComplexDataType(complexDataTypeElement);
		 * complexDataType.setParent(this.getEntityPackage());
		 * bom.addComplexDataType(complexDataType); } } }
		 */

		// 获取package节点下的所有DTO节点
		Element dtosElement = packageElement.element("DTOs");
		if (dtosElement != null) {
			List<Element> dtoElements = dtosElement.elements("DTO");
			if (dtoElements != null && dtoElements.size() > 0) {
				for (Iterator<Element> iter = dtoElements.iterator(); iter
						.hasNext();) {
					Element dtoElement = iter.next();
					DataTransferObject dto = new DataTransferObject(dtoElement,
							bom);
					dto.setBelongPackage(this);
					bom.addDTO(dto);
				}
			}
		}

		// 获取package节点下的所有Enumeration节点
		Element enumerationsElement = packageElement.element("Enumerations");
		if (enumerationsElement != null) {
			List<Element> enumerationElements = enumerationsElement
					.elements("Enumeration");
			if (enumerationElements != null && enumerationElements.size() > 0) {
				for (Iterator<Element> iter = enumerationElements.iterator(); iter
						.hasNext();) {
					Element enumerationElement = iter.next();
					Enumeration enumeration = new Enumeration(
							enumerationElement);
					enumeration.setBelongPackage(this);
					bom.addEnumeration(enumeration);
				}
			}
		}

		// 获取package节点下的所有Enumeration节点
		Element refObjectsElement = packageElement.element("RefObjects");
		if (refObjectsElement != null) {
			List<Element> refObjectElements = refObjectsElement
					.elements("RefObject");
			if (refObjectElements != null && refObjectElements.size() > 0) {
				for (Iterator<Element> iter = refObjectElements.iterator(); iter
						.hasNext();) {
					Element refObjectElement = iter.next();
					ReferenceObject refObject = new ReferenceObject(
							refObjectElement);
					refObject.setBelongPackage(this);
					bom.addReferenceObject(refObject);
				}
			}
		}

		// 获取package节点下的所有Diagram节点
		Element diagramsElement = packageElement.element("Diagrams");
		if (diagramsElement != null) {
			List<Element> diagramElements = diagramsElement.elements("Diagram");
			if (diagramElements != null && diagramElements.size() > 0) {
				for (Iterator<Element> iter = diagramElements.iterator(); iter
						.hasNext();) {
					Element diagramElement = iter.next();
					Diagram diagram = new Diagram(diagramElement);
					diagram.setBelongPackage(this);
					bom.addDiagram(diagram);
				}
			}
		}

		// 直接获取package节点下的所有Package节点
		List<Element> subPackageElements = packageElement.elements("Package");
		if (subPackageElements != null && subPackageElements.size() > 0) {
			for (Iterator<Element> iter = subPackageElements.iterator(); iter
					.hasNext();) {
				Element subpackageElement = iter.next();
				// 递归调用包的构造函数
				ModelPackage subPackage = new ModelPackage(bom,
						subpackageElement);
				subPackage.setParent(this);
				bom.addPackage(subPackage);
			}
		}
	}

	/**
	 * @param modelElement
	 * @return
	 */
	//
	public Element generateXmlElement(Element modelElement) {
		Element packageElement = modelElement.addElement("Package");// 生成Package节点

		generateBasicAttributes(packageElement);
		List<AbstractModelElement> elementList = getChildren();
		for (AbstractModelElement element : elementList) {
			// 注释
			if (element instanceof SolidifyPackage
					&& SolidifyPackage.SOLIDIFY_PACKAGE_ANNOTATION
							.equals(element.getName())) {
				List<AbstractModelElement> entitys = ((SolidifyPackage) element)
						.getChildren();
				for (AbstractModelElement entity : entitys) {
					if (entity instanceof Annotation) {
						Element annotationsElement = packageElement.element("Annotations");//
						if (annotationsElement == null) {
							annotationsElement = packageElement.addElement("Annotations");
							// packageElement.addContent(dtosElement);//BusinessEntity节点创建完成
						}
						entity.generateXmlElement(annotationsElement);
					} 
				}
			}
			// 连线
			else if (element instanceof SolidifyPackage
					&& SolidifyPackage.SOLIDIFY_PACKAGE_LINK
					.equals(element.getName())) {
				List<AbstractModelElement> entitys = ((SolidifyPackage) element)
						.getChildren();
				for (AbstractModelElement entity : entitys) {
					if (entity instanceof LinkAnnotation) {
						Element linksElement = packageElement.element("Links");//
						if (linksElement == null) {
							linksElement = packageElement.addElement("Links");
							// packageElement.addContent(dtosElement);//BusinessEntity节点创建完成
						}
						entity.generateXmlElement(linksElement);
					} 
				}
			}
			// 业务实体
			else if (element instanceof SolidifyPackage
					&& SolidifyPackage.SOLIDIFY_PACKAGE_BUSINESSCLASS
							.equals(element.getName())) {
				List<AbstractModelElement> entitys = ((SolidifyPackage) element)
						.getChildren();
				for (AbstractModelElement entity : entitys) {
					if (entity instanceof BusinessClass) {
						Element businessEntitysElement = packageElement
								.element("BusinessClasses");//
						if (businessEntitysElement == null) {
							businessEntitysElement = packageElement
									.addElement("BusinessClasses");
							// packageElement.addText(businessEntitysElement);//BusinessEntity节点创建完成
						}
						entity.generateXmlElement(businessEntitysElement);
					} else if (entity instanceof DataTransferObject) {
						Element dtosElement = packageElement.element("DTOs");//
						if (dtosElement == null) {
							dtosElement = packageElement.addElement("DTOs");
							// packageElement.addContent(dtosElement);//BusinessEntity节点创建完成
						}
						entity.generateXmlElement(dtosElement);
					} else if (entity instanceof Enumeration) {
						Element enumerationsElement = packageElement
								.element("Enumerations");//
						if (enumerationsElement == null) {
							enumerationsElement = packageElement
									.addElement("Enumerations");
							// packageElement.add(enumerationsElement);//BusinessEntity节点创建完成
						}
						entity.generateXmlElement(enumerationsElement);
					} else if (entity instanceof ReferenceObject) {
						Element refObjectsElement = packageElement
								.element("RefObjects");//
						if (refObjectsElement == null) {
							refObjectsElement = packageElement
									.addElement("RefObjects");
							// packageElement.addContent(refObjectsElement);//BusinessEntity节点创建完成
						}
						entity.generateXmlElement(refObjectsElement);
					}
				}
			}
			// 关联关系
			else if (element instanceof SolidifyPackage
					&& SolidifyPackage.SOLIDIFY_PACKAGE_ASSOCIATION
							.equals(element.getName())) {
				List<AbstractModelElement> associations = ((SolidifyPackage) element)
						.getChildren();
				for (AbstractModelElement association : associations) {
					Element associationsElement = packageElement
							.element("Associations");//
					if (associationsElement == null) {
						associationsElement = packageElement
								.addElement("Associations");
						// packageElement.addContent(associationsElement);//BusinessEntity节点创建完成
					}
					association.generateXmlElement(associationsElement);
				}
			}
			// 继承关系
			else if (element instanceof SolidifyPackage
					&& SolidifyPackage.SOLIDIFY_PACKAGE_INHERITANCE
							.equals(element.getName())) {
				List<AbstractModelElement> inheritances = ((SolidifyPackage) element)
						.getChildren();
				for (AbstractModelElement inheritance : inheritances) {
					Element inheritancesElement = packageElement
							.element("Inheritances");//
					if (inheritancesElement == null) {
						inheritancesElement = packageElement
								.addElement("Inheritances");
						// packageElement.addContent(inheritancesElement);//BusinessEntity节点创建完成
					}
					inheritance.generateXmlElement(inheritancesElement);
				}
			}
			// 图
			else if (element instanceof Diagram) {
				Element diagramsElement = packageElement.element("Diagrams");//
				if (diagramsElement == null) {
					diagramsElement = packageElement.addElement("Diagrams");
					// packageElement.addContent(diagramsElement);//BusinessEntity节点创建完成
				}
				element.generateXmlElement(diagramsElement);
			}
			// 包
			else if (element instanceof ModelPackage) {
				element.generateXmlElement(packageElement);
				// packageElement.add(subPackageElement);
			}
		}
		return packageElement;
	}

	/**
	 * 初始化内部列表对象.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	private void initInternalObjects() {
		// entityPackage = new SolidifyPackage(this,
		// SolidifyPackage.SOLIDIFY_PACKAGE_BUSINESSCLASS,
		// SolidifyPackage.SOLIDIFY_PACKAGE_BUSINESSCLASS_DISPLAYNAME);
		// entityPackage.setStereotype(getStereotype());
		//
		// associationPackage = new SolidifyPackage(this,
		// SolidifyPackage.SOLIDIFY_PACKAGE_ASSOCIATION,
		// SolidifyPackage.SOLIDIFY_PACKAGE_ASSOCIATION_DISPLAYNAME);
		// associationPackage.setStereotype(getStereotype());
		//
		// inheritancePackage = new SolidifyPackage(this,
		// SolidifyPackage.SOLIDIFY_PACKAGE_INHERITANCE,
		// SolidifyPackage.SOLIDIFY_PACKAGE_INHERITANCE_DISPLAYNAME);
		// inheritancePackage.setStereotype(getStereotype());
	}

	/**
	 * Gets the entity package.
	 *
	 * @author mqfdy
	 * @return the entity package
	 * @Date 2018-09-03 09:00
	 */
	public SolidifyPackage getEntityPackage() {
		if (entityPackage == null) {
			entityPackage = new SolidifyPackage(this,
					SolidifyPackage.SOLIDIFY_PACKAGE_BUSINESSCLASS,
					SolidifyPackage.SOLIDIFY_PACKAGE_BUSINESSCLASS_DISPLAYNAME);
			entityPackage.setStereotype(getStereotype());
		}
		return entityPackage;
	}

	/**
	 * Gets the annotation package.
	 *
	 * @author mqfdy
	 * @return the annotation package
	 * @Date 2018-09-03 09:00
	 */
	public SolidifyPackage getAnnotationPackage() {
		if (annotationPackage == null) {
			annotationPackage = new SolidifyPackage(this,
					SolidifyPackage.SOLIDIFY_PACKAGE_ANNOTATION,
					SolidifyPackage.SOLIDIFY_PACKAGE_ANNOTATION_DISPLAYNAME);
			annotationPackage.setStereotype(getStereotype());
		}
		return annotationPackage;
	}

	/**
	 * Gets the link package.
	 *
	 * @author mqfdy
	 * @return the link package
	 * @Date 2018-09-03 09:00
	 */
	public SolidifyPackage getLinkPackage() {
		if (linkPackage == null) {
			linkPackage = new SolidifyPackage(this,
					SolidifyPackage.SOLIDIFY_PACKAGE_LINK,
					SolidifyPackage.SOLIDIFY_PACKAGE_LINK_DISPLAYNAME);
			linkPackage.setStereotype(getStereotype());
		}
		return linkPackage;
	}

	/**
	 * Gets the association package.
	 *
	 * @author mqfdy
	 * @return the association package
	 * @Date 2018-09-03 09:00
	 */
	public SolidifyPackage getAssociationPackage() {
		if (associationPackage == null) {
			associationPackage = new SolidifyPackage(this,
					SolidifyPackage.SOLIDIFY_PACKAGE_ASSOCIATION,
					SolidifyPackage.SOLIDIFY_PACKAGE_ASSOCIATION_DISPLAYNAME);
			associationPackage.setStereotype(getStereotype());
		}
		return associationPackage;
	}

	// public SolidifyPackage getInheritancePackage() {
	// if(inheritancePackage == null){
	// inheritancePackage = new SolidifyPackage(this,
	// SolidifyPackage.SOLIDIFY_PACKAGE_INHERITANCE,
	// SolidifyPackage.SOLIDIFY_PACKAGE_INHERITANCE_DISPLAYNAME);
	// inheritancePackage.setStereotype(getStereotype());
	// }
	// return inheritancePackage;
	// }

	/**
	 * @return
	 */
	public int getPriority() {
		return IModelElement.PRIORITY_MODELPACKAGE;
	}

	/**
	 * @return
	 */
	public AbstractModelElement getParent() {
		return parent;
	}

	/**
	 * Sets the parent.
	 *
	 * @author mqfdy
	 * @param parent
	 *            the new parent
	 * @Date 2018-09-03 09:00
	 */
	public void setParent(AbstractModelElement parent) {
		this.parent = parent;
	}

	/**
	 * @return
	 */
	public List<AbstractModelElement> getChildren() {
		List<AbstractModelElement> children = new ArrayList<AbstractModelElement>();
		AbstractModelElement bom;
		bom = getParent();
		while (bom != null && !(bom instanceof BusinessObjectModel)) {
			bom = bom.getParent();
		}
		if (bom != null) {
			BusinessObjectModel businessObjectModel = (BusinessObjectModel) bom;
			for (int i = 0; i < businessObjectModel.getDiagrams().size(); i++) {
				Diagram diagram = businessObjectModel.getDiagrams().get(i);
				if (diagram.getBelongPackage().getId().equals(getId())) {
					children.add(diagram);
				}
			}
			for (int i = 0; i < businessObjectModel.getPackages().size(); i++) {
				ModelPackage modelPackage = businessObjectModel.getPackages()
						.get(i);
				if (modelPackage.getParent().getId().equals(getId())) {
					children.add(modelPackage);
				}
			}
		}
		// children.addAll(subPackages);
		children.add(getEntityPackage());
		children.add(getAnnotationPackage());
		children.add(getLinkPackage());
		children.add(getAssociationPackage());
		// children.add(getInheritancePackage());
		return children;
	}

	/**
	 * @return
	 */
	public String getFullName() {
		if (this.getParent() != null) {
			if (this.getParent() instanceof BusinessObjectModel) {
				return ((BusinessObjectModel) this.getParent()).getNameSpace()
						+ "." + this.name;
			} else {
				return this.getParent().getFullName() + "." + this.name;

			}
		} else {
			return this.getName();
		}

	}

}
