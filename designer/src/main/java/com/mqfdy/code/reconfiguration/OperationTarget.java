package com.mqfdy.code.reconfiguration;

/**
 * 操作对象
 * 
 * @author mqfdy
 * 
 */
public class OperationTarget {

	public static class BusinessClass {
		public static final String BCNAME = "BcName";// 业务实体名称
		public static final String DBTNAME = "DBTName";// 表名
		public static final String DPNAME = "DPName";// 业务实体显示名

		public static class Property {
			public static final String PROPERTY = "Property";// 针对属性的删除与增加操作

			public static final String PROPNAME = "PropName";// 修改属性名称
			public static final String PROPDATATYPE = "PropDataType";// 修改属性类型
			public static final String PROPDPNAME = "PropDpName";// 修改属性显示名

			public static final String PROPPERSISTENCE = "propPersistence";// 修改是否持久化属性值
			public static final String PROPPRIMARYKEY = "propPrimaryKey";// 修改是否主键属性值
			public static final String PROPNULLABLE = "propNullable";// 修改是否可空属性值
			public static final String PROPUNIQUE = "propUnique";// 修改是否唯一属性值

			public static final String PROPDBNAME = "propDbName";// 修改数据库字段名称
			public static final String PROPDBLENGTH = "propDbLength";// 修改数据库字段长度
			public static final String PROPDBSCALE = "propDbScale";// 修改数据库字段精度
		}
	}

}
