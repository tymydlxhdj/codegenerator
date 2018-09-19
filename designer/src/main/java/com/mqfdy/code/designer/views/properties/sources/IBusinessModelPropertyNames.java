package com.mqfdy.code.designer.views.properties.sources;

// TODO: Auto-generated Javadoc
/**
 * 常量.
 *
 * @author mqfdy
 */
public interface IBusinessModelPropertyNames {
	
	/** The property common id. */
	String PROPERTY_COMMON_ID = "id";
	
	/** The property common name. */
	String PROPERTY_COMMON_NAME = "名称";
	
	/** The property common namespace. */
	String PROPERTY_COMMON_NAMESPACE = "命名空间";
	
	/** The property common displayname. */
	String PROPERTY_COMMON_DISPLAYNAME = "显示名称";
	
	/** The property common remark. */
	String PROPERTY_COMMON_REMARK = "备注";
	
	/** The property common objecttype. */
	String PROPERTY_COMMON_OBJECTTYPE = "类型";
	
	/** The property common stereotypetype. */
	String PROPERTY_COMMON_STEREOTYPETYPE = "构造类型";
	
	/** The property businessclass tablename. */
	String PROPERTY_BUSINESSCLASS_TABLENAME = "数据库表名";
	
	/** The property businessclass isabstract. */
	String PROPERTY_BUSINESSCLASS_ISABSTRACT = "IsAbstract";
	
	/** The property businessclass iscright. */
	String PROPERTY_BUSINESSCLASS_ISCRIGHT = "ISCRight";

	/** The PROPERT Y BUSINESSCLAS S properties. */
	String PROPERTY_BUSINESSCLASS_properties = "properties";
	
	/** The PROPERT Y BUSINESSCLAS S groups. */
	String PROPERTY_BUSINESSCLASS_groups = "groups";
	
	/** The PROPERT Y BUSINESSCLAS S operations. */
	String PROPERTY_BUSINESSCLASS_operations = "operations";
	
	/** The PROPERT Y BUSINESSCLAS S statuses. */
	String PROPERTY_BUSINESSCLASS_statuses = "statuses";
	
	/** The PROPERT Y BUSINESSCLAS S permissions. */
	String PROPERTY_BUSINESSCLASS_permissions = "permissions";
	
	/** The PROPERT Y BUSINESSCLAS S version info. */
	String PROPERTY_BUSINESSCLASS_versionInfo = "versionInfo";
	// String PROPERTY_STEREOTYPE = "stereotype";
	// String PROPERTY_STEREOTYPE = "stereotype";
	// String PROPERTY_STEREOTYPE = "stereotype";
	// String PROPERTY_STEREOTYPE = "stereotype";
	// String PROPERTY_STEREOTYPE = "stereotype";
	/** The property association classa. */
	// String PROPERTY_STEREOTYPE = "stereotype";
	String PROPERTY_ASSOCIATION_CLASSA = "实体A";// OBJECT TEXT+BUTTON
	
	/** The property association classb. */
	String PROPERTY_ASSOCIATION_CLASSB = "实体B";// OBJECT TEXT+BUTTON
	
	/** The property association associationtype. */
	String PROPERTY_ASSOCIATION_ASSOCIATIONTYPE = "关联类型";
	
	/** The property association navigetetoclassb. */
	String PROPERTY_ASSOCIATION_NAVIGETETOCLASSB = "支持导航到实体B";// boolean
	
	/** The property association navigetetoclassa. */
	String PROPERTY_ASSOCIATION_NAVIGETETOCLASSA = "支持导航到实体A";// boolean
	
	/** The property association cascadedeleteclassb. */
	String PROPERTY_ASSOCIATION_CASCADEDELETECLASSB = "级联删除实体B";// boolean
	
	/** The property association cascadedeleteclassa. */
	String PROPERTY_ASSOCIATION_CASCADEDELETECLASSA = "级联删除实体A";// boolean
	
	/** The property association persistenceploy. */
	String PROPERTY_ASSOCIATION_PERSISTENCEPLOY = "持久化策略";
	
	/** The property association mitablename. */
	String PROPERTY_ASSOCIATION_MITABLENAME = "中间表名";
	
	/** The property association columnofclassa. */
	String PROPERTY_ASSOCIATION_COLUMNOFCLASSA = "实体A数据库表关联字段";
	
	/** The property association columnofclassb. */
	String PROPERTY_ASSOCIATION_COLUMNOFCLASSB = "实体B数据库表关联字段";
	
	/** The property association major classname. */
	String PROPERTY_ASSOCIATION_MAJOR_CLASSNAME = "主控端名称";

	/** The property inheritance parentclass. */
	String PROPERTY_INHERITANCE_PARENTCLASS = "父实体";// OBJECT TEXT+BUTTON
	
	/** The property inheritance childclass. */
	String PROPERTY_INHERITANCE_CHILDCLASS = "子实体";// OBJECT TEXT+BUTTON
	
	/** The property inheritance persistenceploy. */
	String PROPERTY_INHERITANCE_PERSISTENCEPLOY = "持久化策略";
	
	/** The property inheritance persitenceployparams. */
	String PROPERTY_INHERITANCE_PERSITENCEPLOYPARAMS = "persistencePloyParams";// OBJECT
																				// TEXT+BUTTON

	/**
																				 * The
																				 * property
																				 * pkproperty
																				 * pkname.
																				 */
																				String PROPERTY_PKPROPERTY_PKNAME = "pkName";
	
	/** The property pkproperty primarykeyploy. */
	String PROPERTY_PKPROPERTY_PRIMARYKEYPLOY = "primaryKeyPloy";

	/** The property property group. */
	String PROPERTY_PROPERTY_GROUP = "属性分组";// OBJECT TEXT+BUTTON
	
	/** The property property datatype. */
	String PROPERTY_PROPERTY_DATATYPE = "数据类型";
	
	/** The property property defaultvalue. */
	String PROPERTY_PROPERTY_DEFAULTVALUE = "缺省值";
	
	/** The property property validators. */
	String PROPERTY_PROPERTY_VALIDATORS = "数据校验";
	
	/** The property property ordernum. */
	String PROPERTY_PROPERTY_ORDERNUM = "orderNum";

	/** The property enumerarion values. */
	String PROPERTY_ENUMERARION_VALUES = "values";

	/** The property referenceobject modelid. */
	String PROPERTY_REFERENCEOBJECT_MODELID = "referenceModelId";
	
	/** The property referenceobject objectid. */
	String PROPERTY_REFERENCEOBJECT_OBJECTID = "referenceObjectId";
	
	/** The property referenceobject modelpath. */
	String PROPERTY_REFERENCEOBJECT_MODELPATH = "referenceModePath";
	
	/** The property referenceobject object. */
	String PROPERTY_REFERENCEOBJECT_OBJECT = "referenceObject";

	/** The property persistenceproperty propertytype. */
	String PROPERTY_PERSISTENCEPROPERTY_PROPERTYTYPE = "属性类型";
	
	/** The property persistenceproperty propertygroup. */
	String PROPERTY_PERSISTENCEPROPERTY_PROPERTYGROUP = "属性分组";
	
	/** The property persistenceproperty ispersitenceproperty. */
	String PROPERTY_PERSISTENCEPROPERTY_ISPERSITENCEPROPERTY = "是否持久化属性";
	
	/** The property persistenceproperty defaultvalue. */
	String PROPERTY_PERSISTENCEPROPERTY_DEFAULTVALUE = "缺省值";
	
	/** The property persistenceproperty isprimarykey. */
	String PROPERTY_PERSISTENCEPROPERTY_ISPRIMARYKEY = "是否主键";
	
	/** The property persistenceproperty isnullable. */
	String PROPERTY_PERSISTENCEPROPERTY_ISNULLABLE = "可空";
	
	/** The property persistenceproperty isindexedcolumn. */
	String PROPERTY_PERSISTENCEPROPERTY_ISINDEXEDCOLUMN = "索引列";
	
	/** The property persistenceproperty isreadonly. */
	String PROPERTY_PERSISTENCEPROPERTY_ISREADONLY = "只读";
	
	/** The property persistenceproperty isunique. */
	String PROPERTY_PERSISTENCEPROPERTY_ISUNIQUE = "值唯一";
	
	/** The property persistenceproperty primarykeyploy. */
	String PROPERTY_PERSISTENCEPROPERTY_PRIMARYKEYPLOY = "主键生成策略";
	
	/** The property persistenceproperty ployname. */
	String PROPERTY_PERSISTENCEPROPERTY_PLOYNAME = "主键名称";
	
	/** The property persistenceproperty dbcolumnname. */
	String PROPERTY_PERSISTENCEPROPERTY_DBCOLUMNNAME = "数据库字段名称";
	
	/** The property persistenceproperty dbdatalength. */
	// String PROPERTY_PERSISTENCEPROPERTY_DBDATATYPE = "数据库字段类型";
	String PROPERTY_PERSISTENCEPROPERTY_DBDATALENGTH = "数据库字段长度";
	
	/** The property persistenceproperty precision. */
	String PROPERTY_PERSISTENCEPROPERTY_PRECISION = "数据库字段精度";
	
	/** The property persistenceproperty validator. */
	String PROPERTY_PERSISTENCEPROPERTY_VALIDATOR = "校验器列表";
	
	/** The property persistenceproperty propertyeditor. */
	String PROPERTY_PERSISTENCEPROPERTY_PROPERTYEDITOR = "属性编辑器";

	/** The property operation returndatatype. */
	String PROPERTY_OPERATION_RETURNDATATYPE = "返回数据类型";
	
	/** The property operation transactionsupport. */
	String PROPERTY_OPERATION_TRANSACTIONSUPPORT = "事务支持";
	
	/** The property operation exceptionmessage. */
	String PROPERTY_OPERATION_EXCEPTIONMESSAGE = "异常提示信息";
	
	/** The property operation parameterinfo. */
	String PROPERTY_OPERATION_PARAMETERINFO = "参数信息";

	/** The property version versionnum. */
	String PROPERTY_VERSION_VERSIONNUM = "版本号";
	
	/** The property version creater. */
	String PROPERTY_VERSION_CREATER = "创建人";
	
	/** The property version createtime. */
	String PROPERTY_VERSION_CREATETIME = "创建时间";
	
	/** The property version updater. */
	String PROPERTY_VERSION_UPDATER = "最后修改人";
	
	/** The property version updatetime. */
	String PROPERTY_VERSION_UPDATETIME = "最后修改时间";

	/** The property enumelement key. */
	String PROPERTY_ENUMELEMENT_KEY = "键";
	
	/** The property enumelement value. */
	String PROPERTY_ENUMELEMENT_VALUE = "键值";

	/** The category version. */
	String CATEGORY_VERSION = "版本信息";
	
	/** The referenceobject referencemodelid. */
	// 引用对象
	String REFERENCEOBJECT_REFERENCEMODELID = "引用模型编号";
	
	/** The referenceobject referenceobjectid. */
	String REFERENCEOBJECT_REFERENCEOBJECTID = "引用对象编号";
	
	/** The referenceobject referencemodepath. */
	String REFERENCEOBJECT_REFERENCEMODEPATH = "引用模型路径";
	
	/** 是否 查询属性. */
	// private boolean isQueryProterty;

	String PROPERTY_ID = "id";
	
	/** The property name. */
	String PROPERTY_NAME = "name";
	
	/** The category 1. */
	String CATEGORY_1 = "基本信息";
	
	/** The category association. */
	String CATEGORY_ASSOCIATION = "Association";
	
	/** The category associationa. */
	String CATEGORY_ASSOCIATIONA = "实体A";
	
	/** The category associationb. */
	String CATEGORY_ASSOCIATIONB = "实体B";
	
	/** The category association persistenceploy. */
	String CATEGORY_ASSOCIATION_PERSISTENCEPLOY = "持久化策略";
	
	/** The category property persistenceploy. */
	String CATEGORY_PROPERTY_PERSISTENCEPLOY = "持久化策略";
	
	/** The category pkproperty. */
	String CATEGORY_PKPROPERTY = "PKProperty";
	
	/** The category businessclass. */
	String CATEGORY_BUSINESSCLASS = "BusinessClass";
	
	/** The category enumerarion. */
	String CATEGORY_ENUMERARION = "Enumeration";
	
	/** The category referenceobject. */
	String CATEGORY_REFERENCEOBJECT = "ReferenceObject";
	
	/** The category property. */
	String CATEGORY_PROPERTY = "Property";
	
	/** The category inheritance. */
	String CATEGORY_INHERITANCE = "Inheritance";
	
	/** The CATEGOR Y style. */
	String CATEGORY_Style = "Style";
	
	/** The xpos prop. */
	String XPOS_PROP = "xpos";
	
	/** The ypos prop. */
	String YPOS_PROP = "ypos";
	
	/** The height prop. */
	String HEIGHT_PROP = "height";
	
	/** The width prop. */
	String WIDTH_PROP = "width";
	
	/** The category base. */
	String CATEGORY_BASE = "基本信息";
}
