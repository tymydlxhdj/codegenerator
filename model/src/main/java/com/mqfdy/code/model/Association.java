package com.mqfdy.code.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Element;

import com.mqfdy.code.model.utils.StringUtil;

/**
 * 实体关系
 * 
 * @author mqfdy
 * 
 */
public class Association extends AbstractModelElement {

	private ModelPackage belongPackage;

	/**
	 * 实体A
	 */
	private BusinessClass classA;

	/**
	 * 实体B
	 */
	private BusinessClass classB;

	/**
	 * 主控端id
	 */
	private String majorClassId;

	/**
	 * 关系类型(1-1，1-m,m-1，m-m)
	 */
	private String associationType;

	/**
	 * 直接在classA中访问classB
	 */
	private boolean navigateToClassB;

	/**
	 * 直接在classB中访问classA
	 */
	private boolean navigateToClassA;

	/**
	 * 删除classA时，级联删除classB
	 */
	private boolean cascadeDeleteClassB;

	/**
	 * 删除classB时，级联删除classA
	 */
	private boolean cascadeDeleteClassA;

	/**
	 * 持久化策略
	 */
	private String persistencePloy;

	/**
	 * 导航关系名称
	 */
	private String navigateToClassBRoleName;

	/**
	 * 导航关系名称
	 */
	private String navigateToClassARoleName;

	/**
	 * 持久化策略参数
	 */
	private Map<String, String> persistencePloyParams;

	public static final String CLASSA = "ClassA";
	
	public static final String CLASSB = "ClassB";
	
	public static final String TRUE = "TRUE";
	
	public static final String FALSE = "FALSE";

	public static final String RELATIONTABLENAME = "RelationTableName";

	public static final String SOURCERELATIONCOLUMN = "SourceRelationColumn";

	public static final String DESTRELATIONCOLUMN = "DestRelationColumn";

	public static final String FOREIGNKEYINA = "ForeignKeyInA";

	public static final String FOREIGNKEYINB = "ForeignKeyInB";

	public static final String FOREIGNKEYCOLUMNINA = "ForeignKeyColumnInA";

	public static final String FOREIGNKEYCOLUMNINB = "ForeignKeyColumnInB";

	public static final String PERSISTENCEPLOYPARAMS = "PersistencePloyParams";
	
	//外键非空
	public static final String NOTNULLFKA = "NotNullFKA";

	public static final String NOTNULLFKB = "NotNullFKB";

	private String classAid;

	private String classBid;

	/**
	 * 属性编辑器
	 */
	private PropertyEditor editor;

	public Association() {
		persistencePloyParams = new HashMap<String, String>();
	}

	/**
	 * 通过 XML 对象构造 Association
	 * 
	 * @param associationElement
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

	public BusinessClass getClassA() {
		return classA;
	}

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

	public void setClassA(BusinessClass classA) {
		this.classA = classA;
	}

	public BusinessClass getClassB() {
		return classB;
	}

	public void setClassB(BusinessClass classB) {
		this.classB = classB;
	}

	public String getMajorClassId() {
		return majorClassId;
	}

	public void setMajorClassId(String majorClassId) {
		this.majorClassId = majorClassId;
	}

	public String getAssociationType() {
		return associationType;
	}

	public void setAssociationType(String associationType) {
		this.associationType = associationType;
	}

	public boolean isNavigateToClassB() {
		return navigateToClassB;
	}

	public void setNavigateToClassB(boolean navigateToClassB) {
		this.navigateToClassB = navigateToClassB;
	}

	public boolean isNavigateToClassA() {
		return navigateToClassA;
	}

	public void setNavigateToClassA(boolean navigateToClassA) {
		this.navigateToClassA = navigateToClassA;
	}

	public boolean isCascadeDeleteClassB() {
		return cascadeDeleteClassB;
	}

	public void setCascadeDeleteClassB(boolean cascadeDeleteClassB) {
		this.cascadeDeleteClassB = cascadeDeleteClassB;
	}

	public boolean isCascadeDeleteClassA() {
		return cascadeDeleteClassA;
	}

	public void setCascadeDeleteClassA(boolean cascadeDeleteClassA) {
		this.cascadeDeleteClassA = cascadeDeleteClassA;
	}

	public String getPersistencePloy() {
		return persistencePloy;
	}

	public void setPersistencePloy(String persistencePloy) {
		this.persistencePloy = persistencePloy;
	}

	public Map<String, String> getPersistencePloyParams() {
		return this.persistencePloyParams;
	}

	public void setPersistencePloyParams(
			Map<String, String> persistencePloyParams) {
		this.persistencePloyParams = persistencePloyParams;
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

	public String getNavigateToClassBRoleName() {
		return navigateToClassBRoleName;
	}

	public void setNavigateToClassBRoleName(String navigateToClassBRoleName) {
		this.navigateToClassBRoleName = navigateToClassBRoleName;
	}

	public String getNavigateToClassARoleName() {
		return navigateToClassARoleName;
	}

	public void setNavigateToClassARoleName(String navigateToClassARoleName) {
		this.navigateToClassARoleName = navigateToClassARoleName;
	}

	public AbstractModelElement getParent() {
		return this.belongPackage.getAssociationPackage();
	}

	public List<AbstractModelElement> getChildren() {
		return EMPTY_CHILD;
	}
	
	public PropertyEditor getEditor() {
		return editor;
	}

	public void setEditor(PropertyEditor editor) {
		this.editor = editor;
	}
}
