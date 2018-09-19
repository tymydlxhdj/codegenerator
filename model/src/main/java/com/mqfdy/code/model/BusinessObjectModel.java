package com.mqfdy.code.model;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import com.mqfdy.code.model.graph.Diagram;
import com.mqfdy.code.model.utils.StringUtil;

// TODO: Auto-generated Javadoc
/**
 * 业务对象模型.
 *
 * @author mqfdy
 */
public class BusinessObjectModel extends AbstractModelElement {

	/** 命名空间. */
	private String nameSpace;

	/** 建立对象名与对象实体的对象池. */
	private Hashtable<String, IModelElement> modelElementPool;

	/** 包列表. */
	private List<ModelPackage> packages;

	/** 业务实体列表. */
	private List<BusinessClass> businessClasses;

	/** 注释列表. */
	private List<Annotation> annotations;

	/** 注释列表. */
	private List<LinkAnnotation> links;

	/** 关联关系列表. */
	private List<Association> associations;

	/** 继承关系列表. */
	private List<Inheritance> inheritances;

	/** 数据传输对象列表. */
	private List<DataTransferObject> DTOs;

	/** 枚举类型定义列表. */
	private List<Enumeration> enumerations;

	/** 引用对象列表(引用其他模块项目的模型对象). */
	private List<ReferenceObject> referenceObjects;

	/** 图表列表. */
	private List<Diagram> diagrams;

	/** 版本信息. */
	private VersionInfo versionInfo;

	/**
	 * Instantiates a new business object model.
	 *
	 * @param name
	 *            the name
	 * @param displayName
	 *            the display name
	 */
	public BusinessObjectModel(String name, String displayName) {

		super(name, displayName);

		initInternalObjects();
	}

	/**
	 * 通过 XML 元素构造 BusinessObjectModel对象.
	 *
	 * @param modelElement
	 *            the model element
	 */
	public BusinessObjectModel(Element modelElement) {

		initBasicAttributes(modelElement);
		String nameSpace = modelElement.attributeValue("nameSpace") == null ? ""
				: modelElement.attributeValue("nameSpace");// 取出namespace
		this.setNameSpace(nameSpace);

		initInternalObjects();
		// VersionInfo节点解析
		Element versionInfoElement = modelElement.element("VersionInfo");
		if (versionInfoElement != null) {
			VersionInfo versionInfo = new VersionInfo(versionInfoElement);
			this.setVersionInfo(versionInfo);
		}

		// 包解析
		@SuppressWarnings("unchecked")
		List<Element> packageElements = modelElement.elements("Package");
		if (packageElements != null && packageElements.size() > 0) {
			for (Iterator<Element> iter = packageElements.iterator(); iter
					.hasNext();) {
				Element packageeEement = iter.next();
				ModelPackage thePackage = new ModelPackage(this, packageeEement);
				thePackage.setParent(this);
				addPackage(thePackage);
			}
		}
	}

	/**
	 * Generate xml element.
	 *
	 * @author mqfdy
	 * @param document
	 *            the document
	 * @return the element
	 * @Date 2018-09-03 09:00
	 */
	public Element generateXmlElement(Document document) {
		Element modelElement = document.addElement("Model"); // Model节点
		generateBasicAttributes(modelElement);
		modelElement.addAttribute("nameSpace",
				StringUtil.convertNull2EmptyStr(getNameSpace()));

		VersionInfo info = getVersionInfo();
		if (info != null) {
			info.generateXmlElement(modelElement);
		}

		List<ModelPackage> packages = getChildren();
		for (ModelPackage mp : packages) {
			mp.generateXmlElement(modelElement);
			// modelElement.add(pakcElement);
		}
		return modelElement;
	}
	
	/**
	 * Generate xml element.
	 *
	 * @author mqfdy
	 * @return the document
	 * @Date 2018-09-03 09:00
	 */
	//构造一个dom4结构的xml样式
	public Document generateXmlElement() {
		Document docutment =DocumentHelper.createDocument();
		Element modelElement = docutment.addElement("Model"); // Model节点
		generateBasicAttributes(modelElement);
		modelElement.addAttribute("nameSpace",
				StringUtil.convertNull2EmptyStr(getNameSpace()));

		VersionInfo info = getVersionInfo();
		if (info != null) {
			info.generateXmlElement(modelElement);
		}

		List<ModelPackage> packages = getChildren();
		for (ModelPackage mp : packages) {
			mp.generateXmlElement(modelElement);
			// modelElement.add(pakcElement);
		}
		return docutment;
	}
	
	/**
	 * To xml.
	 *
	 * @author mqfdy
	 * @return the string
	 * @Date 2018-09-03 09:00
	 */
	public String toXml(){
        String faceXml = "";
        
        OutputFormat format = new OutputFormat(" ",true,"UTF-8"); 
        StringWriter sw = new StringWriter(); 

        XMLWriter writer = new XMLWriter(sw,format); 
        try {
			writer.write(this.generateXmlElement());
			StringBuffer sb = sw.getBuffer();
			faceXml = sb.toString();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(sw!=null){
				try {
					sw.close();
				} catch (IOException e) {
					e.printStackTrace();
				} 
			}
			
			if(writer!=null){
				try {
					writer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
        

		return faceXml;
	}
	

	/**
	 * 初始化内部列表对象.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	private void initInternalObjects() {
		modelElementPool = new Hashtable<String, IModelElement>(100);
		packages = new ArrayList<ModelPackage>();
		businessClasses = new ArrayList<BusinessClass>();
		annotations = new ArrayList<Annotation>();
		links = new ArrayList<LinkAnnotation>();
		associations = new ArrayList<Association>();
		inheritances = new ArrayList<Inheritance>();
		DTOs = new ArrayList<DataTransferObject>();
		referenceObjects = new ArrayList<ReferenceObject>();
		diagrams = new ArrayList<Diagram>();
		enumerations = new ArrayList<Enumeration>();
	}

	/**
	 * 根据建模元素ID查找建模元素.
	 *
	 * @author mqfdy
	 * @param <T>
	 *            类型
	 * @param t
	 *            the t
	 * @param elementId
	 *            the element id
	 * @return the model element by id
	 * @Date 2018-09-03 09:00
	 */
	@SuppressWarnings("unchecked")
	public <T extends AbstractModelElement> T getModelElementById(T t,
			String elementId) {
		return (T) modelElementPool.get(elementId);

	}

	/**
	 * Gets the model element by id.
	 *
	 * @author mqfdy
	 * @param elementId
	 *            the element id
	 * @return the model element by id
	 * @Date 2018-09-03 09:00
	 */
	public Object getModelElementById(String elementId) {
		Object obj = modelElementPool.get(elementId);
		if (obj == null) {
			List<ReferenceObject> list = getReferenceObjects();
			if (list != null) {
				for (ReferenceObject object : list) {
					if (object != null
							&& object.getReferenceObject() != null
							&& object.getReferenceObject().getId()
									.equals(elementId)) {
						obj = object.getReferenceObject();
					}
				}
			}
		}
		return obj;
	}

	/**
	 * Gets the name space.
	 *
	 * @author mqfdy
	 * @return the name space
	 * @Date 2018-09-03 09:00
	 */
	public String getNameSpace() {
		return nameSpace;
	}

	/**
	 * Sets the name space.
	 *
	 * @author mqfdy
	 * @param nameSpace
	 *            the new name space
	 * @Date 2018-09-03 09:00
	 */
	public void setNameSpace(String nameSpace) {
		this.nameSpace = nameSpace;
	}

	/**
	 * Gets the packages.
	 *
	 * @author mqfdy
	 * @return the packages
	 * @Date 2018-09-03 09:00
	 */
	public List<ModelPackage> getPackages() {
		return this.packages;
	}

	/**
	 * Adds the diagram.
	 *
	 * @author mqfdy
	 * @param diagram
	 *            the diagram
	 * @Date 2018-09-03 09:00
	 */
	public void addDiagram(Diagram diagram) {
		this.diagrams.add(diagram);
		this.modelElementPool.put(diagram.getId(), diagram);
	}

	/**
	 * Removes the diagram.
	 *
	 * @author mqfdy
	 * @param diagram
	 *            the diagram
	 * @Date 2018-09-03 09:00
	 */
	public void removeDiagram(AbstractModelElement diagram) {
		this.diagrams.remove(diagram);
		this.modelElementPool.remove(diagram.getId());
	}

	/**
	 * Gets the diagrams.
	 *
	 * @author mqfdy
	 * @return the diagrams
	 * @Date 2018-09-03 09:00
	 */
	public List<Diagram> getDiagrams() {
		return diagrams;
	}

	/**
	 * Adds the package.
	 *
	 * @author mqfdy
	 * @param thePackage
	 *            the the package
	 * @Date 2018-09-03 09:00
	 */
	public void addPackage(ModelPackage thePackage) {
		this.packages.add(thePackage);
		this.modelElementPool.put(thePackage.getId(), thePackage);
	}

	/**
	 * Removes the package.
	 *
	 * @author mqfdy
	 * @param thePackage
	 *            the the package
	 * @Date 2018-09-03 09:00
	 */
	public void removePackage(AbstractModelElement thePackage) {
		List<AbstractModelElement> allChild = new ArrayList<AbstractModelElement>();
		for (BusinessClass ele : businessClasses) {
			allChild.add(removeChild(ele, businessClasses, thePackage));
		}
		businessClasses.removeAll(allChild);
		allChild.clear();
		// add by zh
		for (Annotation ele : annotations) {
			allChild.add(removeChild(ele, annotations, thePackage));
		}
		annotations.removeAll(allChild);
		allChild.clear();
		
		for (LinkAnnotation ele : links) {
			allChild.add(removeChild(ele, links, thePackage));
		}
		links.removeAll(allChild);
		allChild.clear();
		
		for (Association ass : associations) {
			allChild.add(removeChild(ass, associations, thePackage));
		}
		associations.removeAll(allChild);
		allChild.clear();

		for (Diagram ele : diagrams) {
			allChild.add(removeChild(ele, diagrams, thePackage));
		}
		diagrams.removeAll(allChild);
		allChild.clear();
		for (Enumeration ele : enumerations) {
			allChild.add(removeChild(ele, enumerations, thePackage));
		}
		enumerations.removeAll(allChild);
		allChild.clear();
		for (ModelPackage ele : packages) {
			allChild.add(removeChild(ele, packages, thePackage));
		}
		packages.removeAll(allChild);
		this.packages.remove(thePackage);
		this.modelElementPool.remove(thePackage.getId());

	}

	/**
	 * Removes the child.
	 *
	 * @author mqfdy
	 * @param ele
	 *            the ele
	 * @param eleList
	 *            the ele list
	 * @param thePackage
	 *            the the package
	 * @return the abstract model element
	 * @Date 2018-09-03 09:00
	 */
	public AbstractModelElement removeChild(AbstractModelElement ele,
			List<?> eleList, AbstractModelElement thePackage) {
		AbstractModelElement parent = null;
		if (ele instanceof Diagram)
			parent = ((Diagram) ele).getBelongPackage();
		else
			parent = ele.getParent();
		while (!(parent instanceof BusinessObjectModel)) {
			if (parent == thePackage)
				return ele;
			parent = parent.getParent();
		}
		return null;
	}

	/**
	 * Adds the business class.
	 *
	 * @author mqfdy
	 * @param bc
	 *            the bc
	 * @Date 2018-09-03 09:00
	 */
	public void addBusinessClass(BusinessClass bc) {
		this.businessClasses.add(bc);
		this.modelElementPool.put(bc.getId(), bc);
	}

	/**
	 * Adds the annotation.
	 *
	 * @author mqfdy
	 * @param bc
	 *            the bc
	 * @Date 2018-09-03 09:00
	 */
	public void addAnnotation(Annotation bc) {
		this.annotations.add(bc);
		this.modelElementPool.put(bc.getId(), bc);
	}
	
	/**
	 * Adds the link annotation.
	 *
	 * @author mqfdy
	 * @param bc
	 *            the bc
	 * @Date 2018-09-03 09:00
	 */
	public void addLinkAnnotation(LinkAnnotation bc) {
		this.links.add(bc);
		this.modelElementPool.put(bc.getId(), bc);
	}
	
	/**
	 * Removes the business class.
	 *
	 * @author mqfdy
	 * @param bc
	 *            the bc
	 * @Date 2018-09-03 09:00
	 */
	public void removeBusinessClass(AbstractModelElement bc) {
		this.businessClasses.remove(bc);
		this.modelElementPool.remove(bc.getId());
	}

	/**
	 * Removes the annotation.
	 *
	 * @author mqfdy
	 * @param bc
	 *            the bc
	 * @Date 2018-09-03 09:00
	 */
	public void removeAnnotation(AbstractModelElement bc) {
		this.annotations.remove(bc);
		this.modelElementPool.remove(bc.getId());
	}

	/**
	 * Removes the link annotation.
	 *
	 * @author mqfdy
	 * @param bc
	 *            the bc
	 * @Date 2018-09-03 09:00
	 */
	public void removeLinkAnnotation(AbstractModelElement bc) {
		this.links.remove(bc);
		this.modelElementPool.remove(bc.getId());
	}

	/**
	 * Adds the association.
	 *
	 * @author mqfdy
	 * @param as
	 *            the as
	 * @Date 2018-09-03 09:00
	 */
	public void addAssociation(Association as) {
		this.associations.add(as);
		this.modelElementPool.put(as.getId(), as);

	}

	/**
	 * Removes the association.
	 *
	 * @author mqfdy
	 * @param as
	 *            the as
	 * @Date 2018-09-03 09:00
	 */
	public void removeAssociation(AbstractModelElement as) {
		this.associations.remove(as);
		this.modelElementPool.remove(as.getId());
	}

	/**
	 * Adds the inheritance.
	 *
	 * @author mqfdy
	 * @param in
	 *            the in
	 * @Date 2018-09-03 09:00
	 */
	public void addInheritance(Inheritance in) {
		this.inheritances.add(in);
		this.modelElementPool.put(in.getId(), in);

	}

	/**
	 * Removes the inheritance.
	 *
	 * @author mqfdy
	 * @param in
	 *            the in
	 * @Date 2018-09-03 09:00
	 */
	public void removeInheritance(AbstractModelElement in) {
		this.inheritances.remove(in);
		this.modelElementPool.remove(in.getId());
	}

	/**
	 * Adds the DTO.
	 *
	 * @author mqfdy
	 * @param dto
	 *            the dto
	 * @Date 2018-09-03 09:00
	 */
	public void addDTO(DataTransferObject dto) {
		this.DTOs.add(dto);
		this.modelElementPool.put(dto.getId(), dto);
	}

	/**
	 * Removes the DTO.
	 *
	 * @author mqfdy
	 * @param dto
	 *            the dto
	 * @Date 2018-09-03 09:00
	 */
	public void removeDTO(AbstractModelElement dto) {
		this.DTOs.remove(dto);
		this.modelElementPool.remove(dto.getId());
	}

	/**
	 * Adds the enumeration.
	 *
	 * @author mqfdy
	 * @param enumeration
	 *            the enumeration
	 * @Date 2018-09-03 09:00
	 */
	public void addEnumeration(Enumeration enumeration) {
		this.enumerations.add(enumeration);
		this.modelElementPool.put(enumeration.getId(), enumeration);
	}

	/**
	 * Removes the enumeration.
	 *
	 * @author mqfdy
	 * @param enumeration
	 *            the enumeration
	 * @Date 2018-09-03 09:00
	 */
	public void removeEnumeration(AbstractModelElement enumeration) {
		this.enumerations.remove(enumeration);
		this.modelElementPool.remove(enumeration.getId());
	}

	/**
	 * Adds the reference object.
	 *
	 * @author mqfdy
	 * @param ro
	 *            the ro
	 * @Date 2018-09-03 09:00
	 */
	public void addReferenceObject(ReferenceObject ro) {
		this.referenceObjects.add(ro);
		this.modelElementPool.put(ro.getId(), ro);
	}

	/**
	 * Removes the reference object.
	 *
	 * @author mqfdy
	 * @param ro
	 *            the ro
	 * @Date 2018-09-03 09:00
	 */
	public void removeReferenceObject(AbstractModelElement ro) {
		this.referenceObjects.remove(ro);
		this.modelElementPool.remove(ro.getId());
	}

	/**
	 * Gets the business classes.
	 *
	 * @author mqfdy
	 * @return the business classes
	 * @Date 2018-09-03 09:00
	 */
	public List<BusinessClass> getBusinessClasses() {
		return businessClasses;
	}

	/**
	 * Gets the annotations.
	 *
	 * @author mqfdy
	 * @return the annotations
	 * @Date 2018-09-03 09:00
	 */
	public List<Annotation> getAnnotations() {
		return annotations;
	}

	/**
	 * Gets the link annotations.
	 *
	 * @author mqfdy
	 * @return the link annotations
	 * @Date 2018-09-03 09:00
	 */
	public List<LinkAnnotation> getLinkAnnotations() {
		return links;
	}

	/**
	 * Gets the associations.
	 *
	 * @author mqfdy
	 * @return the associations
	 * @Date 2018-09-03 09:00
	 */
	public List<Association> getAssociations() {
		return associations;
	}

	/**
	 * Gets the inheritances.
	 *
	 * @author mqfdy
	 * @return the inheritances
	 * @Date 2018-09-03 09:00
	 */
	public List<Inheritance> getInheritances() {
		return inheritances;
	}

	/**
	 * Gets the DT os.
	 *
	 * @author mqfdy
	 * @return the DT os
	 * @Date 2018-09-03 09:00
	 */
	public List<DataTransferObject> getDTOs() {
		return DTOs;
	}

	/**
	 * Gets the enumerations.
	 *
	 * @author mqfdy
	 * @return the enumerations
	 * @Date 2018-09-03 09:00
	 */
	public List<Enumeration> getEnumerations() {
		return enumerations;
	}

	/**
	 * Gets the reference objects.
	 *
	 * @author mqfdy
	 * @return the reference objects
	 * @Date 2018-09-03 09:00
	 */
	public List<ReferenceObject> getReferenceObjects() {
		return referenceObjects;
	}

	/**
	 * Gets the version info.
	 *
	 * @author mqfdy
	 * @return the version info
	 * @Date 2018-09-03 09:00
	 */
	public VersionInfo getVersionInfo() {
		return versionInfo;
	}

	/**
	 * Sets the version info.
	 *
	 * @author mqfdy
	 * @param versionInfo
	 *            the new version info
	 * @Date 2018-09-03 09:00
	 */
	public void setVersionInfo(VersionInfo versionInfo) {
		this.versionInfo = versionInfo;
	}

	/**
	 * 根据元素类型删除对应的元素.
	 *
	 * @author mqfdy
	 * @param modelElement
	 *            the model element
	 * @Date 2018-09-03 09:00
	 */
	public void removeModelElement(AbstractModelElement modelElement) {
		if (modelElement instanceof ModelPackage) {
			removePackage(modelElement);
		} else if (modelElement instanceof Diagram) {
			removeDiagram(modelElement);
		} else if (modelElement instanceof Annotation) {
			removeAnnotation(modelElement);
		} else if (modelElement instanceof LinkAnnotation) {
			removeLinkAnnotation(modelElement);
		} else if (modelElement instanceof BusinessClass) {
			removeBusinessClass(modelElement);
		} else if (modelElement instanceof Association) {
			removeAssociation(modelElement);
		} else if (modelElement instanceof Inheritance) {
			removeInheritance(modelElement);
		} else if (modelElement instanceof Enumeration) {
			removeEnumeration(modelElement);
		} else if (modelElement instanceof DataTransferObject) {
			removeDTO(modelElement);
		} else if (modelElement instanceof ReferenceObject) {
			removeReferenceObject(modelElement);
		} else if (modelElement instanceof DTOProperty) {
			((DataTransferObject) modelElement.getParent()).getProperties()
					.remove(modelElement);
		} else if (modelElement instanceof EnumElement) {
			((Enumeration) modelElement.getParent()).getElements().remove(
					modelElement);
		} else if (modelElement instanceof Property) {
			((BusinessClass) (modelElement.getParent())).getProperties()
					.remove(modelElement);
		} else if (modelElement instanceof BusinessOperation) {
			((BusinessOperation) modelElement).getBelongBusinessClass()
					.getOperations().remove(modelElement);
		}
	}

	/**
	 * 根据元素类型添加元素到对应列表中.
	 *
	 * @author mqfdy
	 * @param modelElement
	 *            the model element
	 * @Date 2018-09-03 09:00
	 */
	public void addModelElement(AbstractModelElement modelElement) {
		if (modelElement instanceof ModelPackage) {
			addPackage((ModelPackage) modelElement);
		} else if (modelElement instanceof Diagram) {
			addDiagram((Diagram) modelElement);
		} else if (modelElement instanceof BusinessClass) {
			addBusinessClass((BusinessClass) modelElement);
		} else if (modelElement instanceof Inheritance) {
			addInheritance((Inheritance) modelElement);
		} else if (modelElement instanceof Association) {
			addAssociation((Association) modelElement);
		} else if (modelElement instanceof DataTransferObject) {
			addDTO((DataTransferObject) modelElement);
		} else if (modelElement instanceof Enumeration) {
			addEnumeration((Enumeration) modelElement);
		} else if (modelElement instanceof ReferenceObject) {
			addReferenceObject((ReferenceObject) modelElement);
		} else if (modelElement instanceof Annotation) {
			addAnnotation((Annotation) modelElement);
		} else if (modelElement instanceof LinkAnnotation) {
			addLinkAnnotation((LinkAnnotation) modelElement);
		}
	}

	/**
	 * @return
	 */
	public AbstractModelElement getParent() {
		return null;
	}

	/**
	 * @return
	 */
	public List<ModelPackage> getChildren() {
		List<ModelPackage> children = new ArrayList<ModelPackage>();
		for (int i = 0; i < this.packages.size(); i++) {
			ModelPackage modelPackage = this.packages.get(i);
			if (modelPackage.getParent().getId().equals(getId())) {
				children.add(modelPackage);
			}
		}
		return children;
	}

	/**
	 * @return
	 */
	public String getFullName() {
		return this.nameSpace + "." + this.name;
	}

}
