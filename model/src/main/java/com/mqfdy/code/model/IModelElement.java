package com.mqfdy.code.model;

import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * 模型元素接口.
 *
 * @author mqfdy
 */
public interface IModelElement {

	/** 对象优先级. */
	public static final int PRIORITY_MODELELEMENT = 100;
	
	/** The Constant PRIORITY_BUSINESSCLASS. */
	public static final int PRIORITY_BUSINESSCLASS = 0;
	
	/** The Constant PRIORITY_ENUMERATION. */
	public static final int PRIORITY_ENUMERATION = 1;
	
	/** The Constant PRIORITY_DTO. */
	public static final int PRIORITY_DTO = 2;
	
	/** The Constant PRIORITY_COMPLEXDATATYPE. */
	public static final int PRIORITY_COMPLEXDATATYPE = 3;
	
	/** The Constant PRIORITY_DIAGRAM. */
	public static final int PRIORITY_DIAGRAM = 4;
	
	/** The Constant PRIORITY_SOLIDIFYPACKAGE. */
	public static final int PRIORITY_SOLIDIFYPACKAGE = 5;
	
	/** The Constant PRIORITY_MODELPACKAGE. */
	public static final int PRIORITY_MODELPACKAGE = 6;

	/** 对象类型. */
	public static final String ELEMENT_TYPE_BUSINESSCLASS = BusinessClass.class
			.getName();;
	
	/** The Constant ELEMENT_TYPE_PROPERTY. */
	public static final String ELEMENT_TYPE_PROPERTY = Property.class.getName();
	
	/** The Constant ELEMENT_TYPE_ENUMERATION. */
	public static final String ELEMENT_TYPE_ENUMERATION = Enumeration.class
			.getName();;
	
	/** The Constant ELEMENT_TYPE_DTO. */
	public static final String ELEMENT_TYPE_DTO = DataTransferObject.class
			.getName();;

	/** The Constant MODEL_STEREOTYPE_ASSOCIATION. */
	public static final String MODEL_STEREOTYPE_ASSOCIATION = "关联关系";
	
	/** The Constant MODEL_STEREOTYPE_BEPERMISSION. */
	public static final String MODEL_STEREOTYPE_BEPERMISSION = "实体权限";
	
	/** The Constant MODEL_STEREOTYPE_BESTATUS. */
	public static final String MODEL_STEREOTYPE_BESTATUS = "实体状态";
	
	/** The Constant MODEL_STEREOTYPE_BUSINESSCLASS. */
	public static final String MODEL_STEREOTYPE_BUSINESSCLASS = "业务实体";
	
	/** The Constant MODEL_STEREOTYPE_BUSINESSOPERATION. */
	public static final String MODEL_STEREOTYPE_BUSINESSOPERATION = "实体操作";
	
	/** The Constant MODEL_STEREOTYPE_DTO. */
	public static final String MODEL_STEREOTYPE_DTO = "数据传输对象";
	
	/** The Constant MODEL_STEREOTYPE_ENUMELEMENT. */
	public static final String MODEL_STEREOTYPE_ENUMELEMENT = "枚举元素";
	
	/** The Constant MODEL_STEREOTYPE_ENUMERATION. */
	public static final String MODEL_STEREOTYPE_ENUMERATION = "枚举";
	
	/** The Constant MODEL_STEREOTYPE_INHERITANCE. */
	public static final String MODEL_STEREOTYPE_INHERITANCE = "继承关系";
	
	/** The Constant MODEL_STEREOTYPE_MODELPACKAGE. */
	public static final String MODEL_STEREOTYPE_MODELPACKAGE = "包";
	
	/** The Constant MODEL_STEREOTYPE_OPERATIONPARAM. */
	public static final String MODEL_STEREOTYPE_OPERATIONPARAM = "操作参数";
	
	/** The Constant MODEL_STEREOTYPE_PERSISTENCEPROPERTY. */
	public static final String MODEL_STEREOTYPE_PERSISTENCEPROPERTY = "持久化属性";
	
	/** The Constant MODEL_STEREOTYPE_DTOPROPERTY. */
	public static final String MODEL_STEREOTYPE_DTOPROPERTY = "DTO属性";
	
	/** The Constant MODEL_STEREOTYPE_PKPROPERTY. */
	public static final String MODEL_STEREOTYPE_PKPROPERTY = "主键属性";
	
	/** The Constant MODEL_STEREOTYPE_PROPERTY. */
	public static final String MODEL_STEREOTYPE_PROPERTY = "属性";
	
	/** The Constant MODEL_STEREOTYPE_PROPERTYEDITOR. */
	public static final String MODEL_STEREOTYPE_PROPERTYEDITOR = "属性编辑器";
	
	/** The Constant MODEL_STEREOTYPE_PROPERTYGROUP. */
	public static final String MODEL_STEREOTYPE_PROPERTYGROUP = "属性分组";
	
	/** The Constant MODEL_STEREOTYPE_REFERENCEOBJECT. */
	public static final String MODEL_STEREOTYPE_REFERENCEOBJECT = "引用对象";
	
	/** The Constant MODEL_STEREOTYPE_SOLIDIFYPACKAGE. */
	public static final String MODEL_STEREOTYPE_SOLIDIFYPACKAGE = "固化包";
	
	/** The Constant MODEL_STEREOTYPE_VALIDATOR. */
	public static final String MODEL_STEREOTYPE_VALIDATOR = "校验器";
	
	/** The Constant MODEL_STEREOTYPE_DIAGRAM. */
	public static final String MODEL_STEREOTYPE_DIAGRAM = "业务模型图";
	
	/** The Constant MODEL_STEREOTYPE_BUSINESSCLASSOBJECTMODEL. */
	public static final String MODEL_STEREOTYPE_BUSINESSCLASSOBJECTMODEL = "业务对象模型";

	/** The Constant MODEL_TYPE_BUSINESSCLASS. */
	public static final String MODEL_TYPE_BUSINESSCLASS = "BusinessClass";
	
	/** The Constant MODEL_TYPE_ENUM. */
	public static final String MODEL_TYPE_ENUM = "Enumeration";
	
	/** The Constant MODEL_TYPE_DTO. */
	public static final String MODEL_TYPE_DTO = "DataTransferObject";
	
	/** The Constant MODEL_TYPE_ASSOCIATION. */
	public static final String MODEL_TYPE_ASSOCIATION = "Association";
	
	/** The Constant MODEL_TYPE_INHERITANCE. */
	public static final String MODEL_TYPE_INHERITANCE = "Inheritance";
	
	/** The Constant MODEL_TYPE_REFRENCE. */
	public static final String MODEL_TYPE_REFRENCE = "ReferenceObject";

	/** The Constant STEREOTYPE_BUILDIN. */
	public static final String STEREOTYPE_BUILDIN = "0";
	
	/** The Constant STEREOTYPE_CUSTOM. */
	public static final String STEREOTYPE_CUSTOM = "1";
	
	/** The Constant STEREOTYPE_REFERENCE. */
	public static final String STEREOTYPE_REFERENCE = "2";
	
	/** The Constant STEREOTYPE_REVERSE. */
	public static final String STEREOTYPE_REVERSE = "3";
	
	/** The Constant REFMODELPATH. */
	public static final String REFMODELPATH = "refModelPath";
	
	/** The Constant BUILDIN_BUSINESSORGANIZATION_ID. */
	public static final String BUILDIN_BUSINESSORGANIZATION_ID = "0cff40dfb4314965b6f7bbe071efff95";
	
	/** The Constant BUILDIN_USER_ID. */
	public static final String BUILDIN_USER_ID = "a94f37f324d147849d9fbb5a34e502fa";
	
	/** The Constant KEY_SCHEMA. */
	public static final String KEY_SCHEMA = "schemaName";

	/**
	 * 获取显示名.
	 *
	 * @author mqfdy
	 * @return the display name
	 * @Date 2018-09-03 09:00
	 */
	public String getDisplayName();

	/**
	 * 获取元素名称.
	 *
	 * @author mqfdy
	 * @return the name
	 * @Date 2018-09-03 09:00
	 */
	public String getName();

	/**
	 * 获取元素类型.
	 *
	 * @author mqfdy
	 * @return the type
	 * @Date 2018-09-03 09:00
	 */
	public String getType();

	/**
	 * 获取元素ID.
	 *
	 * @author mqfdy
	 * @return the id
	 * @Date 2018-09-03 09:00
	 */
	public String getId();

	/**
	 * 获取元素构造类型.
	 *
	 * @author mqfdy
	 * @return the stereotype
	 * @Date 2018-09-03 09:00
	 */
	public String getStereotype();

	/**
	 * 获取元素的.
	 *
	 * @author mqfdy
	 * @return the parent
	 * @Date 2018-09-03 09:00
	 */
	public AbstractModelElement getParent();

	/**
	 * 获取对象的子元素列表.
	 *
	 * @author mqfdy
	 * @return the children
	 * @Date 2018-09-03 09:00
	 */
	public List<?> getChildren();

	/**
	 * 获取完全限定名(包路径+类名).
	 *
	 * @author mqfdy
	 * @return the full name
	 * @Date 2018-09-03 09:00
	 */
	public String getFullName();
}
