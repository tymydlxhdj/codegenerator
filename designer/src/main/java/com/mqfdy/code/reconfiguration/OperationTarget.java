package com.mqfdy.code.reconfiguration;

// TODO: Auto-generated Javadoc
/**
 * 操作对象.
 *
 * @author mqfdy
 */
public class OperationTarget {

	/**
	 * The Class BusinessClass.
	 *
	 * @author mqfdy
	 */
	public static class BusinessClass {
		
		/** The Constant BCNAME. */
		public static final String BCNAME = "BcName";// 业务实体名称
		
		/** The Constant DBTNAME. */
		public static final String DBTNAME = "DBTName";// 表名
		
		/** The Constant DPNAME. */
		public static final String DPNAME = "DPName";// 业务实体显示名

		/**
		 * The Class Property.
		 *
		 * @author mqfdy
		 */
		public static class Property {
			
			/** The Constant PROPERTY. */
			public static final String PROPERTY = "Property";// 针对属性的删除与增加操作

			/** The Constant PROPNAME. */
			public static final String PROPNAME = "PropName";// 修改属性名称
			
			/** The Constant PROPDATATYPE. */
			public static final String PROPDATATYPE = "PropDataType";// 修改属性类型
			
			/** The Constant PROPDPNAME. */
			public static final String PROPDPNAME = "PropDpName";// 修改属性显示名

			/** The Constant PROPPERSISTENCE. */
			public static final String PROPPERSISTENCE = "propPersistence";// 修改是否持久化属性值
			
			/** The Constant PROPPRIMARYKEY. */
			public static final String PROPPRIMARYKEY = "propPrimaryKey";// 修改是否主键属性值
			
			/** The Constant PROPNULLABLE. */
			public static final String PROPNULLABLE = "propNullable";// 修改是否可空属性值
			
			/** The Constant PROPUNIQUE. */
			public static final String PROPUNIQUE = "propUnique";// 修改是否唯一属性值

			/** The Constant PROPDBNAME. */
			public static final String PROPDBNAME = "propDbName";// 修改数据库字段名称
			
			/** The Constant PROPDBLENGTH. */
			public static final String PROPDBLENGTH = "propDbLength";// 修改数据库字段长度
			
			/** The Constant PROPDBSCALE. */
			public static final String PROPDBSCALE = "propDbScale";// 修改数据库字段精度
		}
	}

}
