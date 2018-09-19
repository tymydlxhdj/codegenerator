package com.mqfdy.code.designer.dialogs;

// TODO: Auto-generated Javadoc
/**
 * The Interface IBusinessClassEditorPage.
 *
 * @author mqfdy
 */
public interface IBusinessClassEditorPage {

	/** The Constant ERROR_MESSAGE_NAME_NULLABLE. */
	public static final String ERROR_MESSAGE_NAME_NULLABLE = "名称不能为空";
	
	/** The Constant ERROR_MESSAGE_NAME_UNIQUE. */
	public static final String ERROR_MESSAGE_NAME_UNIQUE = "名称已被占用";
	
	/** The Constant ERROR_MESSAGE_NAME_RULE. */
	public static final String ERROR_MESSAGE_NAME_RULE = "名称只能包含字母、数字和下划线，且以字母或下划线开头";
	
	/** The Constant ERROR_MESSAGE_NAME_UPPER. */
	public static final String ERROR_MESSAGE_NAME_UPPER = "名称只能包含字母、数字和下划线，且以大写字母或下划线开头";
	
	/** The Constant ERROR_MESSAGE_NAME_LOWER. */
	public static final String ERROR_MESSAGE_NAME_LOWER = "名称只能包含字母、数字和下划线，且以小写字母或下划线开头";
	
	/** The Constant ERROR_MESSAGE_NAME_LENGTH. */
	public static final String ERROR_MESSAGE_NAME_LENGTH = "名称长度不能超过30个字符！";
	
	/** The Constant ERROR_MESSAGE_NAME_JAVA. */
	public static final String ERROR_MESSAGE_NAME_JAVA = "名称是Java关键字！";
	
	/** The Constant ERROR_MESSAGE_NAME_SQL. */
	public static final String ERROR_MESSAGE_NAME_SQL = "名称是SQL关键字！";
	
	/** The Constant ERROR_MESSAGE_NAME_SGUAP. */
	public static final String ERROR_MESSAGE_NAME_SGUAP = "名称是SG-UAP平台关键字！";
	
	/** The Constant ERROR_MESSAGE_NAME_EXIST. */
	public static final String ERROR_MESSAGE_NAME_EXIST = "名称已经存在";
	
	/** The Constant ERROR_MESSAGE_DISPLAYNAME_NULLABLE. */
	public static final String ERROR_MESSAGE_DISPLAYNAME_NULLABLE = "显示名称不能为空";
	
	/** The Constant ERROR_DISPLAYNAME. */
	public static final String ERROR_DISPLAYNAME = "显示名称不能包含特殊字符";
	
	/** The Constant ERROR_GROUPDISPLAYNAME. */
	public static final String ERROR_GROUPDISPLAYNAME = "属性分组不能包含特殊字符";
	
	/** The Constant ERROR_DISPLAYNAME_LENGTH. */
	public static final String ERROR_DISPLAYNAME_LENGTH = "显示名称长度不能超过30个字符";
	
	/** The Constant ERROR_GROUPDISPLAYNAME_LENGTH. */
	public static final String ERROR_GROUPDISPLAYNAME_LENGTH = "属性分组长度不能超过30个字符";
	
	/** The Constant TOOLONG_ERROR_REMARK. */
	public static final String TOOLONG_ERROR_REMARK = "备注内容长度不能超过128个字符";
	
	/** The Constant ERROR_MESSAGE_DATABASENAME_NULLABLE. */
	public static final String ERROR_MESSAGE_DATABASENAME_NULLABLE = "对应数据库表名不能为空";
	
	/** The Constant ERROR_MESSAGE_DATABASENAME_LENGTH. */
	public static final String ERROR_MESSAGE_DATABASENAME_LENGTH = "数据库表名长度不能超过30个字符！";
	
	/** The Constant ERROR_MESSAGE_DATATYPE_NULLABLE. */
	public static final String ERROR_MESSAGE_DATATYPE_NULLABLE = "请选择属性类型";

	/** The Constant ERROR_MESSAGE_POLY_NULLABLE. */
	public static final String ERROR_MESSAGE_POLY_NULLABLE = "策略参数_Sequence名不能为空";
	
	/** The Constant ERROR_MESSAGE_POLY_RULE. */
	public static final String ERROR_MESSAGE_POLY_RULE = "策略参数_Sequence名只能包含字母和数字，且以字母开头";

	/** The Constant ERROR_ENUM_KEY_JAVA. */
	public static final String ERROR_ENUM_KEY_JAVA = "key是Java关键字！";
	
	/** The Constant ERROR_ENUM_KEY_RULE. */
	public static final String ERROR_ENUM_KEY_RULE = "key只能包含字母和数字！";
	
	/** The Constant ERROR_ENUM_KEY_MULT. */
	public static final String ERROR_ENUM_KEY_MULT = "key在当前枚举中必须唯一！";
	
	/** The Constant ERROR_ENUM_KEY_LENGTH. */
	public static final String ERROR_ENUM_KEY_LENGTH = "key长度不能超过30个字符！";
	
	/** The Constant ERROR_ENUM_VALUE. */
	public static final String ERROR_ENUM_VALUE = "value格式不正确!";
	
	/** The Constant ERROR_ENUM_VALUE_LENGTH. */
	public static final String ERROR_ENUM_VALUE_LENGTH = "value长度不能超过30个字符！";

	/** The Constant ERROR_MESSAGE_DATABASECOLUMNNAME_NULLABLE. */
	public static final String ERROR_MESSAGE_DATABASECOLUMNNAME_NULLABLE = "数据库字段名不能为空";
	
	/** The Constant ERROR_MESSAGE_DATABASECOLUMNNAME_SQL. */
	public static final String ERROR_MESSAGE_DATABASECOLUMNNAME_SQL = "数据库字段名是SQL关键字！";
	
	/** The Constant ERROR_MESSAGE_DATALENGTH_NULLABLE. */
	public static final String ERROR_MESSAGE_DATALENGTH_NULLABLE = "字段长度不能为空";

	/** The Constant ERROR_MESSAGE_VALIDATORTYPE_NULLABLE. */
	public static final String ERROR_MESSAGE_VALIDATORTYPE_NULLABLE = "请选择校验器类型";
	
	/** The Constant ERROR_MESSAGE_VALIDATORTYPE_NOT_ALLOW. */
	public static final String ERROR_MESSAGE_VALIDATORTYPE_NOT_ALLOW = "校验器类型与现有校验器类型冲突";
	
	/** The Constant ERROR_MESSAGE_ERRORMESSAGE_NULLABLE. */
	public static final String ERROR_MESSAGE_ERRORMESSAGE_NULLABLE = "错误提示不能为空";
	
	/** The Constant ERROR_MESSAGE_ERRORMESSAGE_LENGTH. */
	public static final String ERROR_MESSAGE_ERRORMESSAGE_LENGTH = "错误提示长度不能超过128个字符！";

	/** The Constant ERROR_MESSAGE_PARAMNAME_NULLABLE. */
	public static final String ERROR_MESSAGE_PARAMNAME_NULLABLE = "参数名不能为空";
	
	/** The Constant ERROR_MESSAGE_PARAMNAME_MULT. */
	public static final String ERROR_MESSAGE_PARAMNAME_MULT = "参数名在当前方法中必须唯一";
	
	/** The Constant ERROR_MESSAGE_PARAMDISPLAYNAME_NULLABLE. */
	public static final String ERROR_MESSAGE_PARAMDISPLAYNAME_NULLABLE = "参数显示名不能为空";
	
	/** The Constant ERROR_MESSAGE_PARAMDATATYPE_NULLABLE. */
	public static final String ERROR_MESSAGE_PARAMDATATYPE_NULLABLE = "请选择参数类型";

	/** The Constant ERROR_MESSAGE_RETURNTYPE_NULLABLE. */
	public static final String ERROR_MESSAGE_RETURNTYPE_NULLABLE = "请选择返回数据类型";
	
	/** The Constant ERROR_MESSAGE_TRANSACTIONTYPE_NULLABLE. */
	public static final String ERROR_MESSAGE_TRANSACTIONTYPE_NULLABLE = "请选择事务类型";

	/** The Constant ERROR_MESSAGE_PERSIST_POLICY_NULLABLE. */
	public static final String ERROR_MESSAGE_PERSIST_POLICY_NULLABLE = "请选择持久化策略";
	
	/** The Constant ERROR_MESSAGE_PERSIST_POLICY_CONTROL. */
	public static final String ERROR_MESSAGE_PERSIST_POLICY_CONTROL = "请选择一个主控端";
	
	/** The Constant ERROR_MESSAGE_PERSIST_POLICY_TWO_WAY. */
	public static final String ERROR_MESSAGE_PERSIST_POLICY_TWO_WAY = "请设置为双向导航";
	
	/** The Constant ERROR_MESSAGE_PERSIST_POLICY_FK. */
	public static final String ERROR_MESSAGE_PERSIST_POLICY_FK = "请只选择一端创建外键";

	/** The Constant ERROR_MESSAGE_ROLENAME_NULLABLE. */
	public static final String ERROR_MESSAGE_ROLENAME_NULLABLE = "请填写导航属性名称";
	
	/** The Constant ERROR_MESSAGE_ROLENAME_JAVA. */
	public static final String ERROR_MESSAGE_ROLENAME_JAVA = "导航属性名称是java关键字";
	
	/** The Constant ERROR_MESSAGE_ROLENAME_LENGTH. */
	public static final String ERROR_MESSAGE_ROLENAME_LENGTH = "导航属性名称长度不能超过30个字符";
	
	/** The Constant ERROR_MESSAGE_ROLENAME_MULT. */
	public static final String ERROR_MESSAGE_ROLENAME_MULT = "属性名称已经存在，请更改导航属性名称";
	
	/** The Constant ERROR_MESSAGE_ROLENAME_RULE. */
	public static final String ERROR_MESSAGE_ROLENAME_RULE = "导航属性名称格式不正确";

	/** The Constant ERROR_MESSAGE_FK_NULLABLE. */
	public static final String ERROR_MESSAGE_FK_NULLABLE = "请填写外键字段名";
	
	/** The Constant ERROR_MESSAGE_FK_RULE. */
	public static final String ERROR_MESSAGE_FK_RULE = "外键字段名格式不正确!";
	
	/** The Constant ERROR_MESSAGE_FK_SQL. */
	public static final String ERROR_MESSAGE_FK_SQL = "外键字段名是sql关键字!";
	
	/** The Constant ERROR_MESSAGE_FK_MULT. */
	public static final String ERROR_MESSAGE_FK_MULT = "数据库字段名称已经存在,请更改外键字段名!";
	
	/** The Constant ERROR_MESSAGE_FK_MULT_ALL. */
	public static final String ERROR_MESSAGE_FK_MULT_ALL = "外键字段名已经存在,请更改外键字段名!";

	/** The Constant ERROR_MESSAGE_SQL_PROPERTY_NAME_NULLABLE. */
	public static final String ERROR_MESSAGE_SQL_PROPERTY_NAME_NULLABLE = "请选择属性名称";
	
	/** The Constant ERROR_MESSAGE_SQL_OPERATE_NULLABLE. */
	public static final String ERROR_MESSAGE_SQL_OPERATE_NULLABLE = "请选择操作符";
	
	/** The Constant ERROR_MESSAGE_SQL_VALUE_NULLABLE. */
	public static final String ERROR_MESSAGE_SQL_VALUE_NULLABLE = "请填写值";
	
	/** The Constant ERROR_MESSAGE_SQL_VALUE_DATATYPE. */
	public static final String ERROR_MESSAGE_SQL_VALUE_DATATYPE = "填写值与当前属性类型不匹配";
	
	/** The Constant ERROR_MESSAGE_SQL_LOGIC_OPERATE_NULLABLE. */
	public static final String ERROR_MESSAGE_SQL_LOGIC_OPERATE_NULLABLE = "请选择逻辑运算符";
	
	/** The Constant ERROR_MESSAGE_SQL_BRACES. */
	public static final String ERROR_MESSAGE_SQL_BRACES = "前后括号不匹配";

	/** The Constant ERROR_MESSAGE_CHILD_PARENT_NULLABLE. */
	public static final String ERROR_MESSAGE_CHILD_PARENT_NULLABLE = "请选择子类和父类";

	/** The Constant ERROR_MESSAGE_TABLE_NULLABLE. */
	public static final String ERROR_MESSAGE_TABLE_NULLABLE = "请填写中间表名";
	
	/** The Constant ERROR_MESSAGE_COLUMN_NULLABLE. */
	public static final String ERROR_MESSAGE_COLUMN_NULLABLE = "请填写表字段";

	/** The Constant ERROR_MESSAGE_ENTITYA_NULLABLE. */
	public static final String ERROR_MESSAGE_ENTITYA_NULLABLE = "请选择实体A";
	
	/** The Constant ERROR_MESSAGE_ENTITYB_NULLABLE. */
	public static final String ERROR_MESSAGE_ENTITYB_NULLABLE = "请选择实体B";
	
	/** The Constant ERROR_MESSAGE_ENTITY_NON_CUSTOM. */
	public static final String ERROR_MESSAGE_ENTITY_NON_CUSTOM = "实体A和实体B至少有一个是自定义业务实体";

	/** The Constant ERROR_MESSAGE_PRIMARYKEY_MULT. */
	public static final String ERROR_MESSAGE_PRIMARYKEY_MULT = "只能有一个主键";
	
	/** The Constant ERROR_MESSAGE_PRIMARYKEY_PLOY_NULLABLE. */
	public static final String ERROR_MESSAGE_PRIMARYKEY_PLOY_NULLABLE = "请选择主键策略";
	
	/** The Constant ERROR_MESSAGE_SEQUENCENAME_NULLABLE. */
	public static final String ERROR_MESSAGE_SEQUENCENAME_NULLABLE = "主键序列名不能为空";

	/** The Constant ERROR_MESSAGE_VALIDATORTYPE_EXIST. */
	public static final String ERROR_MESSAGE_VALIDATORTYPE_EXIST = "不支持为同一属性增加两个同类型的校验器";

	/** The Constant ERROR_MESSAGE_BUSINESSCLASS_NULLABLE. */
	public static final String ERROR_MESSAGE_BUSINESSCLASS_NULLABLE = "请选择业务实体";
	
	/** The Constant ERROR_MESSAGE_PROPERTY_NULLABLE. */
	public static final String ERROR_MESSAGE_PROPERTY_NULLABLE = "请选择实体属性";
	
	/** The Constant ERROR_MESSAGE_PROPERTY_REPEAT. */
	public static final String ERROR_MESSAGE_PROPERTY_REPEAT = "实体属性不可重复添加";

	/** The Constant ERROR_MESSAGE_STATUS_NOT_DELETE. */
	public static final String ERROR_MESSAGE_STATUS_NOT_DELETE = "当前状态不可删除";

	/** The Constant OPERATE_ITEM_NULL_MESSAGE. */
	public static final String OPERATE_ITEM_NULL_MESSAGE = "请选择要操作的对象";
	
	/** The Constant OPERATE_ITEM_NULL_TITLE. */
	public static final String OPERATE_ITEM_NULL_TITLE = "提示";

	/** The Constant OPERATE_MESSAGE. */
	public static final String OPERATE_MESSAGE = "请选择要操作的对象";
	
	/** The Constant DELETE_MESSAGE. */
	public static final String DELETE_MESSAGE = "请选择要删除的对象";
	
	/** The Constant DELETE_CONFIRM. */
	public static final String DELETE_CONFIRM = "是否确认要删除所选对象？";
	
	/** The Constant DELETE_MESSAGE_TITLE. */
	public static final String DELETE_MESSAGE_TITLE = "属性";
	
	/** The Constant NAME_MESSAGE. */
	public static final String NAME_MESSAGE = "只能包含字母、数字和下划线，且以字母或下划线开头";
	
	/** The Constant NAME_FIRST_NO_. */
	public static final String NAME_FIRST_NO_ = "只能包含字母、数字和下划线，且以字母开头";
	
	/** 编辑状态. */
	public static final String STATUS_EDIT = "edit";

	/** 新增状态. */
	public static final String STATUS_ADD = "add";

	/** 无状态. */
	public static final String STATUS_NO = "no";

	/**
	 * 初始化各个页面的控件值.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	public void initControlValue();

	/**
	 * 校验页面的输入合法性.
	 *
	 * @author mqfdy
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	public boolean validateInput();

	/**
	 * 更新正在编辑BusinessClass对象的值.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	public void updateTheEditingElement();

}
