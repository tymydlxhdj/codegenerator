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

/**
 * 业务对象模型
 * 
 * @author mqfdy
 * 
 */
public class BusinessObjectModel extends AbstractModelElement {

	/**
	 * 命名空间
	 */
	private String nameSpace;

	/**
	 * 建立对象名与对象实体的对象池
	 */
	private Hashtable<String, IModelElement> modelElementPool;

	/**
	 * 包列表
	 */
	private List<ModelPackage> packages;

	/**
	 * 业务实体列表
	 */
	private List<BusinessClass> businessClasses;

	/**
	 * 注释列表
	 */
	private List<Annotation> annotations;

	/**
	 * 注释列表
	 */
	private List<LinkAnnotation> links;

	/**
	 * 关联关系列表
	 */
	private List<Association> associations;

	/**
	 * 继承关系列表
	 */
	private List<Inheritance> inheritances;

	/**
	 * 数据传输对象列表
	 */
	private List<DataTransferObject> DTOs;

	/**
	 * 枚举类型定义列表
	 */
	private List<Enumeration> enumerations;

	/**
	 * 引用对象列表(引用其他模块项目的模型对象)
	 */
	private List<ReferenceObject> referenceObjects;

	/**
	 * 图表列表
	 */
	private List<Diagram> diagrams;

	/**
	 * 版本信息
	 */
	private VersionInfo versionInfo;

	public BusinessObjectModel(String name, String displayName) {

		super(name, displayName);

		initInternalObjects();
	}

	/**
	 * 通过 XML 元素构造 BusinessObjectModel对象
	 * 
	 * @param modelElement
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
	 * 初始化内部列表对象
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
	 * 根据建模元素ID查找建模元素
	 * 
	 * @param <T>
	 *            类型
	 * @param t
	 * @param elementId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T extends AbstractModelElement> T getModelElementById(T t,
			String elementId) {
		return (T) modelElementPool.get(elementId);

	}

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

	public String getNameSpace() {
		return nameSpace;
	}

	public void setNameSpace(String nameSpace) {
		this.nameSpace = nameSpace;
	}

	public List<ModelPackage> getPackages() {
		return this.packages;
	}

	public void addDiagram(Diagram diagram) {
		this.diagrams.add(diagram);
		this.modelElementPool.put(diagram.getId(), diagram);
	}

	public void removeDiagram(AbstractModelElement diagram) {
		this.diagrams.remove(diagram);
		this.modelElementPool.remove(diagram.getId());
	}

	public List<Diagram> getDiagrams() {
		return diagrams;
	}

	public void addPackage(ModelPackage thePackage) {
		this.packages.add(thePackage);
		this.modelElementPool.put(thePackage.getId(), thePackage);
	}

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

	public void addBusinessClass(BusinessClass bc) {
		this.businessClasses.add(bc);
		this.modelElementPool.put(bc.getId(), bc);
	}

	public void addAnnotation(Annotation bc) {
		this.annotations.add(bc);
		this.modelElementPool.put(bc.getId(), bc);
	}
	
	public void addLinkAnnotation(LinkAnnotation bc) {
		this.links.add(bc);
		this.modelElementPool.put(bc.getId(), bc);
	}
	
	public void removeBusinessClass(AbstractModelElement bc) {
		this.businessClasses.remove(bc);
		this.modelElementPool.remove(bc.getId());
	}

	public void removeAnnotation(AbstractModelElement bc) {
		this.annotations.remove(bc);
		this.modelElementPool.remove(bc.getId());
	}

	public void removeLinkAnnotation(AbstractModelElement bc) {
		this.links.remove(bc);
		this.modelElementPool.remove(bc.getId());
	}

	public void addAssociation(Association as) {
		this.associations.add(as);
		this.modelElementPool.put(as.getId(), as);

	}

	public void removeAssociation(AbstractModelElement as) {
		this.associations.remove(as);
		this.modelElementPool.remove(as.getId());
	}

	public void addInheritance(Inheritance in) {
		this.inheritances.add(in);
		this.modelElementPool.put(in.getId(), in);

	}

	public void removeInheritance(AbstractModelElement in) {
		this.inheritances.remove(in);
		this.modelElementPool.remove(in.getId());
	}

	public void addDTO(DataTransferObject dto) {
		this.DTOs.add(dto);
		this.modelElementPool.put(dto.getId(), dto);
	}

	public void removeDTO(AbstractModelElement dto) {
		this.DTOs.remove(dto);
		this.modelElementPool.remove(dto.getId());
	}

	public void addEnumeration(Enumeration enumeration) {
		this.enumerations.add(enumeration);
		this.modelElementPool.put(enumeration.getId(), enumeration);
	}

	public void removeEnumeration(AbstractModelElement enumeration) {
		this.enumerations.remove(enumeration);
		this.modelElementPool.remove(enumeration.getId());
	}

	public void addReferenceObject(ReferenceObject ro) {
		this.referenceObjects.add(ro);
		this.modelElementPool.put(ro.getId(), ro);
	}

	public void removeReferenceObject(AbstractModelElement ro) {
		this.referenceObjects.remove(ro);
		this.modelElementPool.remove(ro.getId());
	}

	public List<BusinessClass> getBusinessClasses() {
		return businessClasses;
	}

	public List<Annotation> getAnnotations() {
		return annotations;
	}

	public List<LinkAnnotation> getLinkAnnotations() {
		return links;
	}

	public List<Association> getAssociations() {
		return associations;
	}

	public List<Inheritance> getInheritances() {
		return inheritances;
	}

	public List<DataTransferObject> getDTOs() {
		return DTOs;
	}

	public List<Enumeration> getEnumerations() {
		return enumerations;
	}

	public List<ReferenceObject> getReferenceObjects() {
		return referenceObjects;
	}

	public VersionInfo getVersionInfo() {
		return versionInfo;
	}

	public void setVersionInfo(VersionInfo versionInfo) {
		this.versionInfo = versionInfo;
	}

	/**
	 * 根据元素类型删除对应的元素
	 * 
	 * @param modelElement
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
	 * 根据元素类型添加元素到对应列表中
	 * 
	 * @param modelElement
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

	public AbstractModelElement getParent() {
		return null;
	}

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

	public String getFullName() {
		return this.nameSpace + "." + this.name;
	}

}
