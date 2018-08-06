package com.mqfdy.code.model;

import java.util.List;

/**
 * 模型元素接口
 * 
 * @author mqfdy
 * 
 */
public interface IModelElement {

	/**
	 * 对象优先级
	 */
	public static final int PRIORITY_MODELELEMENT = 100;
	public static final int PRIORITY_BUSINESSCLASS = 0;
	public static final int PRIORITY_ENUMERATION = 1;
	public static final int PRIORITY_DTO = 2;
	public static final int PRIORITY_COMPLEXDATATYPE = 3;
	public static final int PRIORITY_DIAGRAM = 4;
	public static final int PRIORITY_SOLIDIFYPACKAGE = 5;
	public static final int PRIORITY_MODELPACKAGE = 6;

	/**
	 * 对象类型
	 */
	public static final String ELEMENT_TYPE_BUSINESSCLASS = BusinessClass.class
			.getName();;
	public static final String ELEMENT_TYPE_PROPERTY = Property.class.getName();
	public static final String ELEMENT_TYPE_ENUMERATION = Enumeration.class
			.getName();;
	public static final String ELEMENT_TYPE_DTO = DataTransferObject.class
			.getName();;

	public static final String MODEL_STEREOTYPE_ASSOCIATION = "关联关系";
	public static final String MODEL_STEREOTYPE_BEPERMISSION = "实体权限";
	public static final String MODEL_STEREOTYPE_BESTATUS = "实体状态";
	public static final String MODEL_STEREOTYPE_BUSINESSCLASS = "业务实体";
	public static final String MODEL_STEREOTYPE_BUSINESSOPERATION = "实体操作";
	public static final String MODEL_STEREOTYPE_DTO = "数据传输对象";
	public static final String MODEL_STEREOTYPE_ENUMELEMENT = "枚举元素";
	public static final String MODEL_STEREOTYPE_ENUMERATION = "枚举";
	public static final String MODEL_STEREOTYPE_INHERITANCE = "继承关系";
	public static final String MODEL_STEREOTYPE_MODELPACKAGE = "包";
	public static final String MODEL_STEREOTYPE_OPERATIONPARAM = "操作参数";
	public static final String MODEL_STEREOTYPE_PERSISTENCEPROPERTY = "持久化属性";
	public static final String MODEL_STEREOTYPE_DTOPROPERTY = "DTO属性";
	public static final String MODEL_STEREOTYPE_PKPROPERTY = "主键属性";
	public static final String MODEL_STEREOTYPE_PROPERTY = "属性";
	public static final String MODEL_STEREOTYPE_PROPERTYEDITOR = "属性编辑器";
	public static final String MODEL_STEREOTYPE_PROPERTYGROUP = "属性分组";
	public static final String MODEL_STEREOTYPE_REFERENCEOBJECT = "引用对象";
	public static final String MODEL_STEREOTYPE_SOLIDIFYPACKAGE = "固化包";
	public static final String MODEL_STEREOTYPE_VALIDATOR = "校验器";
	public static final String MODEL_STEREOTYPE_DIAGRAM = "业务模型图";
	public static final String MODEL_STEREOTYPE_BUSINESSCLASSOBJECTMODEL = "业务对象模型";

	public static final String MODEL_TYPE_BUSINESSCLASS = "BusinessClass";
	public static final String MODEL_TYPE_ENUM = "Enumeration";
	public static final String MODEL_TYPE_DTO = "DataTransferObject";
	public static final String MODEL_TYPE_ASSOCIATION = "Association";
	public static final String MODEL_TYPE_INHERITANCE = "Inheritance";
	public static final String MODEL_TYPE_REFRENCE = "ReferenceObject";

	public static final String STEREOTYPE_BUILDIN = "0";
	public static final String STEREOTYPE_CUSTOM = "1";
	public static final String STEREOTYPE_REFERENCE = "2";
	public static final String STEREOTYPE_REVERSE = "3";
	public static final String REFMODELPATH = "refModelPath";
	
	public static final String BUILDIN_BUSINESSORGANIZATION_ID = "0cff40dfb4314965b6f7bbe071efff95";
	public static final String BUILDIN_USER_ID = "a94f37f324d147849d9fbb5a34e502fa";
	
	public static final String KEY_SCHEMA = "schemaName";

	/**
	 * 获取显示名
	 * 
	 * @return
	 */
	public String getDisplayName();

	/**
	 * 获取元素名称
	 * 
	 * @return
	 */
	public String getName();

	/**
	 * 获取元素类型
	 * 
	 * @return
	 */
	public String getType();

	/**
	 * 获取元素ID
	 * 
	 * @return
	 */
	public String getId();

	/**
	 * 获取元素构造类型
	 * 
	 * @return
	 */
	public String getStereotype();

	/**
	 * 获取元素的
	 * 
	 * @return
	 */
	public AbstractModelElement getParent();

	/**
	 * 获取对象的子元素列表
	 * 
	 * @return
	 */
	public List<?> getChildren();

	/**
	 * 获取完全限定名(包路径+类名)
	 * 
	 * @return
	 */
	public String getFullName();
}
