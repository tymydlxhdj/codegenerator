package com.mqfdy.code.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Element;

import com.mqfdy.code.model.utils.StringUtil;

// TODO: Auto-generated Javadoc
/**
 * 实体关系.
 *
 * @author mqfdy
 */
public class Association extends AbstractModelElement {

	/** The belong package. */
	private ModelPackage belongPackage;

	/** 实体A. */
	private BusinessClass classA;

	/** 实体B. */
	private BusinessClass classB;

	/** 主控端id. */
	private String majorClassId;

	/** 关系类型(1-1，1-m,m-1，m-m). */
	private String associationType;

	/** 直接在classA中访问classB. */
	private boolean navigateToClassB;

	/** 直接在classB中访问classA. */
	private boolean navigateToClassA;

	/** 删除classA时，级联删除classB. */
	private boolean cascadeDeleteClassB;

	/** 删除classB时，级联删除classA. */
	private boolean cascadeDeleteClassA;

	/** 持久化策略. */
	private String persistencePloy;

	/** 导航关系名称. */
	private String navigateToClassBRoleName;

	/** 导航关系名称. */
	private String navigateToClassARoleName;

	/** 持久化策略参数. */
	private Map<String, String> persistencePloyParams;

	/** The Constant CLASSA. */
	public static final String CLASSA = "ClassA";
	
	/** The Constant CLASSB. */
	public static final String CLASSB = "ClassB";
	
	/** The Constant TRUE. */
	public static final String TRUE = "TRUE";
	
	/** The Constant FALSE. */
	public static final String FALSE = "FALSE";

	/** The Constant RELATIONTABLENAME. */
	public static final String RELATIONTABLENAME = "RelationTableName";

	/** The Constant SOURCERELATIONCOLUMN. */
	public static final String SOURCERELATIONCOLUMN = "SourceRelationColumn";

	/** The Constant DESTRELATIONCOLUMN. */
	public static final String DESTRELATIONCOLUMN = "DestRelationColumn";

	/** The Constant FOREIGNKEYINA. */
	public static final String FOREIGNKEYINA = "ForeignKeyInA";

	/** The Constant FOREIGNKEYINB. */
	public static final String FOREIGNKEYINB = "ForeignKeyInB";

	/** The Constant FOREIGNKEYCOLUMNINA. */
	public static final String FOREIGNKEYCOLUMNINA = "ForeignKeyColumnInA";

	/** The Constant FOREIGNKEYCOLUMNINB. */
	public static final String FOREIGNKEYCOLUMNINB = "ForeignKeyColumnInB";

	/** The Constant PERSISTENCEPLOYPARAMS. */
	public static final String PERSISTENCEPLOYPARAMS = "PersistencePloyParams";
	
	/** The Constant NOTNULLFKA. */
	//外键非空
	public static final String NOTNULLFKA = "NotNullFKA";

	/** The Constant NOTNULLFKB. */
	public static final String NOTNULLFKB = "NotNullFKB";

	/** The class aid. */
	private String classAid;

	/** The class bid. */
	private String classBid;

	/** 属性编辑器. */
	private PropertyEditor editor;

	/**
	 * Instantiates a new association.
	 */
	public Association() {
		persistencePloyParams = new HashMap<String, String>();
	}

	/**
	 * 通过 XML 对象构造 Association.
	 *
	 * @param associationElement
	 *            the association element
	 * @param bom
	 *            the bom
	 */
	@SuppressWarnings("unchecked")
	public Association(Element associationElement, BusinessObjectModel bom) {

		persistencePloyParams = new HashMap<String, String>();

		initBasicAttributes(associationElement);

		// 获取Association节点下Type，SourceCascadeDelete，TargetCascadeDelete，
		// NavigateToSource,NavigateToTarget,Remark,PersistencePloy子节点值

		String type = associationElement.elementTextTrim("Type");
		String majorClassId = associationElement
				.elementTextTrim("MajorClassId");
		String cascadeDeleteClassB = associationElement
				.elementTextTrim("CascadeDeleteClassB");
		String cascadeDeleteClassA = associationElement
				.elementTextTrim("CascadeDeleteClassA");
		String navigateToClassB = associationElement
				.elementTextTrim("NavigateToClassB");
		String navigateToClassA = associationElement
				.elementTextTrim("NavigateToClassA");
		String persistencePloy = associationElement
				.elementTextTrim("PersistencePloy");
		String navigateToClassBRoleName = associationElement
				.elementTextTrim("navigateToClassBRoleName");
		String navigateToClassARoleName = associationElement
				.elementTextTrim("navigateToClassARoleName");

		// if(AssociationType.mult2one.getValue().equals(type)){
		// setAssociationType(AssociationType.one2mult.getValue());
		//
		// setCascadeDeleteClassA(StringUtil.string2Boolean(cascadeDeleteClassB));
		// setCascadeDeleteClassB(StringUtil.string2Boolean(cascadeDeleteClassA));
		// setNavigateToClassA(StringUtil.string2Boolean(navigateToClassB));
		// setNavigateToClassB(StringUtil.string2Boolean(navigateToClassA));
		// setPersistencePloy(persistencePloy);
		//
		// //获取Association节点下SourceEntity，DestEntity查找Business对象再设值
		// this.classAid = associationElement.getChildTextTrim("ClassB");
		// this.classBid = associationElement.getChildTextTrim("ClassA");
		// if(!StringUtil.isEmpty(majorClassId)){//主控端ID不为空，说明一定是双向
		// if(majorClassId.equals(this.classAid)){
		// setMajorClassId(this.classBid);
		// }
		// if(majorClassId.equals(this.classBid)){
		// setMajorClassId(this.classAid);
		// }
		// }
		// }else{
		setAssociationType(type);
		setMajorClassId(majorClassId);
		setCascadeDeleteClassA(StringUtil.string2Boolean(cascadeDeleteClassA));
		setCascadeDeleteClassB(StringUtil.string2Boolean(cascadeDeleteClassB));
		setNavigateToClassA(StringUtil.string2Boolean(navigateToClassA));
		setNavigateToClassB(StringUtil.string2Boolean(navigateToClassB));
		setPersistencePloy(persistencePloy);
		setNavigateToClassBRoleName(navigateToClassBRoleName);
		setNavigateToClassARoleName(navigateToClassARoleName);

		// 获取Association节点下SourceEntity，DestEntity查找Business对象再设值
		this.classAid = associationElement.elementTextTrim(CLASSA);
		this.classBid = associationElement.elementTextTrim(CLASSB);
		// }

		// 获取获取Association节点下PersistencePloyParams节点
		Element PersistencePloyParamsElement = associationElement
				.element(PERSISTENCEPLOYPARAMS);
		// 获取PersistencePloyParams节点下的Param节点列表
		if (PersistencePloyParamsElement != null) {
			List<Element> paramElementList = PersistencePloyParamsElement
					.elements("Param");
			for (Iterator<Element> iterator2 = paramElementList.iterator(); iterator2
					.hasNext();) {
				Element paramElement = iterator2.next();// Param节点
				// 获取Param节点下的name和value值
				String key = paramElement.elementTextTrim("name");
				String value = paramElement.elementTextTrim("value");
				persistencePloyParams.put(key, value);
			}
		}
		Element editorElement = associationElement.element("Editor");
		if (editorElement != null) {
			PropertyEditor editor = new PropertyEditor(editorElement);
			setEditor(editor);
		}
	}

	/**
	 * @param associationsElement
	 * @return
	 */
	public Element generateXmlElement(Element associationsElement) {
		Element associationElement = associationsElement
				.addElement("Association");
		this.generateBasicAttributes(associationElement);

		// 在Association节点下创建SourceEntity，DestEntity，Type，SourceCascadeDelete
		// TargetCascadeDelete，NavigateToSource，NavigateToTarget，PersistencePloy子节点
		associationElement.addElement(CLASSA).addText(
				getClassA() == null ? "" : StringUtil
						.convertNull2EmptyStr(getClassA().getId()));
		associationElement.addElement(CLASSB).addText(
				getClassB() == null ? "" : StringUtil
						.convertNull2EmptyStr(getClassB().getId()));
		associationElement.addElement("Type").addText(
				StringUtil.convertNull2EmptyStr(getAssociationType()));
		associationElement.addElement("CascadeDeleteClassA").addText(
				isCascadeDeleteClassA() ? "true" : "false");
		associationElement.addElement("CascadeDeleteClassB").addText(
				isCascadeDeleteClassB() ? "true" : "false");
		associationElement.addElement("NavigateToClassA").addText(
				isNavigateToClassA() ? "true" : "false");
		associationElement.addElement("NavigateToClassB").addText(
				isNavigateToClassB() ? "true" : "false");
		associationElement.addElement("navigateToClassBRoleName").addText(
				StringUtil.convertNull2EmptyStr(getNavigateToClassBRoleName()));
		associationElement.addElement("navigateToClassARoleName").addText(
				StringUtil.convertNull2EmptyStr(getNavigateToClassARoleName()));
		associationElement.addElement("MajorClassId").addText(
				StringUtil.convertNull2EmptyStr(getMajorClassId()));
		associationElement.addElement("PersistencePloy").addText(
				StringUtil.convertNull2EmptyStr(getPersistencePloy()));

		Map<String, String> params = getPersistencePloyParams();
		if (params != null) {
			Element persistencePloyParamsElement = associationElement
					.addElement(PERSISTENCEPLOYPARAMS);
			// associationElement.addContent(persistencePloyParamsElement);
			this.generateParam(persistencePloyParamsElement, params);
		}
		// 属性编辑器
		PropertyEditor editor = getEditor();
		if (editor != null) {
			editor.generateXmlElement(associationElement);// Editor节点
			// propertyElement.addContent(editorElement);
		}
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
	public BusinessClass getClassA() {
		return classA;
	}

	/**
	 * @return
	 */
	public Association clone() {
		Association element = new Association();
		copy(element);
		element.setClassA(classA);
		element.setClassB(classB);
		if(getEditor() != null){
			PropertyEditor editorClone = (PropertyEditor) ((PropertyEditor) getEditor())
					.clone();
			element.setEditor(editorClone);
		}
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
	protected void copy(Association dest) {
		dest.name = this.name;
		dest.displayName = this.displayName;
		dest.remark = this.remark;
		dest.associationType = this.associationType;
		dest.cascadeDeleteClassA = this.cascadeDeleteClassA;
		dest.cascadeDeleteClassB = this.cascadeDeleteClassB;
		dest.majorClassId = this.majorClassId;
		dest.navigateToClassA = this.navigateToClassA;
		dest.navigateToClassB = this.navigateToClassB;
		dest.navigateToClassARoleName = this.navigateToClassARoleName;
		dest.navigateToClassBRoleName = this.navigateToClassBRoleName;
		dest.persistencePloy = this.persistencePloy;
		dest.persistencePloyParams = this.persistencePloyParams;
	}

	/**
	 * Sets the class A.
	 *
	 * @author mqfdy
	 * @param classA
	 *            the new class A
	 * @Date 2018-09-03 09:00
	 */
	public void setClassA(BusinessClass classA) {
		this.classA = classA;
	}

	/**
	 * Gets the class B.
	 *
	 * @author mqfdy
	 * @return the class B
	 * @Date 2018-09-03 09:00
	 */
	public BusinessClass getClassB() {
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
	public void setClassB(BusinessClass classB) {
		this.classB = classB;
	}

	/**
	 * Gets the major class id.
	 *
	 * @author mqfdy
	 * @return the major class id
	 * @Date 2018-09-03 09:00
	 */
	public String getMajorClassId() {
		return majorClassId;
	}

	/**
	 * Sets the major class id.
	 *
	 * @author mqfdy
	 * @param majorClassId
	 *            the new major class id
	 * @Date 2018-09-03 09:00
	 */
	public void setMajorClassId(String majorClassId) {
		this.majorClassId = majorClassId;
	}

	/**
	 * Gets the association type.
	 *
	 * @author mqfdy
	 * @return the association type
	 * @Date 2018-09-03 09:00
	 */
	public String getAssociationType() {
		return associationType;
	}

	/**
	 * Sets the association type.
	 *
	 * @author mqfdy
	 * @param associationType
	 *            the new association type
	 * @Date 2018-09-03 09:00
	 */
	public void setAssociationType(String associationType) {
		this.associationType = associationType;
	}

	/**
	 * Checks if is navigate to class B.
	 *
	 * @author mqfdy
	 * @return true, if is navigate to class B
	 * @Date 2018-09-03 09:00
	 */
	public boolean isNavigateToClassB() {
		return navigateToClassB;
	}

	/**
	 * Sets the navigate to class B.
	 *
	 * @author mqfdy
	 * @param navigateToClassB
	 *            the new navigate to class B
	 * @Date 2018-09-03 09:00
	 */
	public void setNavigateToClassB(boolean navigateToClassB) {
		this.navigateToClassB = navigateToClassB;
	}

	/**
	 * Checks if is navigate to class A.
	 *
	 * @author mqfdy
	 * @return true, if is navigate to class A
	 * @Date 2018-09-03 09:00
	 */
	public boolean isNavigateToClassA() {
		return navigateToClassA;
	}

	/**
	 * Sets the navigate to class A.
	 *
	 * @author mqfdy
	 * @param navigateToClassA
	 *            the new navigate to class A
	 * @Date 2018-09-03 09:00
	 */
	public void setNavigateToClassA(boolean navigateToClassA) {
		this.navigateToClassA = navigateToClassA;
	}

	/**
	 * Checks if is cascade delete class B.
	 *
	 * @author mqfdy
	 * @return true, if is cascade delete class B
	 * @Date 2018-09-03 09:00
	 */
	public boolean isCascadeDeleteClassB() {
		return cascadeDeleteClassB;
	}

	/**
	 * Sets the cascade delete class B.
	 *
	 * @author mqfdy
	 * @param cascadeDeleteClassB
	 *            the new cascade delete class B
	 * @Date 2018-09-03 09:00
	 */
	public void setCascadeDeleteClassB(boolean cascadeDeleteClassB) {
		this.cascadeDeleteClassB = cascadeDeleteClassB;
	}

	/**
	 * Checks if is cascade delete class A.
	 *
	 * @author mqfdy
	 * @return true, if is cascade delete class A
	 * @Date 2018-09-03 09:00
	 */
	public boolean isCascadeDeleteClassA() {
		return cascadeDeleteClassA;
	}

	/**
	 * Sets the cascade delete class A.
	 *
	 * @author mqfdy
	 * @param cascadeDeleteClassA
	 *            the new cascade delete class A
	 * @Date 2018-09-03 09:00
	 */
	public void setCascadeDeleteClassA(boolean cascadeDeleteClassA) {
		this.cascadeDeleteClassA = cascadeDeleteClassA;
	}

	/**
	 * Gets the persistence ploy.
	 *
	 * @author mqfdy
	 * @return the persistence ploy
	 * @Date 2018-09-03 09:00
	 */
	public String getPersistencePloy() {
		return persistencePloy;
	}

	/**
	 * Sets the persistence ploy.
	 *
	 * @author mqfdy
	 * @param persistencePloy
	 *            the new persistence ploy
	 * @Date 2018-09-03 09:00
	 */
	public void setPersistencePloy(String persistencePloy) {
		this.persistencePloy = persistencePloy;
	}

	/**
	 * Gets the persistence ploy params.
	 *
	 * @author mqfdy
	 * @return the persistence ploy params
	 * @Date 2018-09-03 09:00
	 */
	public Map<String, String> getPersistencePloyParams() {
		return this.persistencePloyParams;
	}

	/**
	 * Sets the persistence ploy params.
	 *
	 * @author mqfdy
	 * @param persistencePloyParams
	 *            the persistence ploy params
	 * @Date 2018-09-03 09:00
	 */
	public void setPersistencePloyParams(
			Map<String, String> persistencePloyParams) {
		this.persistencePloyParams = persistencePloyParams;
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
	 * Gets the navigate to class B role name.
	 *
	 * @author mqfdy
	 * @return the navigate to class B role name
	 * @Date 2018-09-03 09:00
	 */
	public String getNavigateToClassBRoleName() {
		return navigateToClassBRoleName;
	}

	/**
	 * Sets the navigate to class B role name.
	 *
	 * @author mqfdy
	 * @param navigateToClassBRoleName
	 *            the new navigate to class B role name
	 * @Date 2018-09-03 09:00
	 */
	public void setNavigateToClassBRoleName(String navigateToClassBRoleName) {
		this.navigateToClassBRoleName = navigateToClassBRoleName;
	}

	/**
	 * Gets the navigate to class A role name.
	 *
	 * @author mqfdy
	 * @return the navigate to class A role name
	 * @Date 2018-09-03 09:00
	 */
	public String getNavigateToClassARoleName() {
		return navigateToClassARoleName;
	}

	/**
	 * Sets the navigate to class A role name.
	 *
	 * @author mqfdy
	 * @param navigateToClassARoleName
	 *            the new navigate to class A role name
	 * @Date 2018-09-03 09:00
	 */
	public void setNavigateToClassARoleName(String navigateToClassARoleName) {
		this.navigateToClassARoleName = navigateToClassARoleName;
	}

	/**
	 * @return
	 */
	public AbstractModelElement getParent() {
		return this.belongPackage.getAssociationPackage();
	}

	/**
	 * @return
	 */
	public List<AbstractModelElement> getChildren() {
		return EMPTY_CHILD;
	}
	
	/**
	 * Gets the editor.
	 *
	 * @author mqfdy
	 * @return the editor
	 * @Date 2018-09-03 09:00
	 */
	public PropertyEditor getEditor() {
		return editor;
	}

	/**
	 * Sets the editor.
	 *
	 * @author mqfdy
	 * @param editor
	 *            the new editor
	 * @Date 2018-09-03 09:00
	 */
	public void setEditor(PropertyEditor editor) {
		this.editor = editor;
	}
}
