package com.mqfdy.code.reconfiguration;

import java.util.ArrayList;
import java.util.List;

import com.mqfdy.code.model.BusinessClass;
import com.mqfdy.code.model.PKProperty;
import com.mqfdy.code.model.PersistenceProperty;
import com.mqfdy.code.model.Property;

// TODO: Auto-generated Javadoc
/**
 * 重构操作分析.
 *
 * @author mqfdy
 */
@Deprecated
public class OperationInfoAnalyzer {

	/** The old one. */
	private Object oldOne;// 原对象
	
	/** The new one. */
	private Object newOne;// 新对象
	
	/** The infos. */
	private List<OperationInfo> infos = new ArrayList<OperationInfo>();

	/**
	 * 无参构造.
	 */
	public OperationInfoAnalyzer() {
	}

	/**
	 * Instantiates a new operation info analyzer.
	 *
	 * @param oldOne
	 *            the old one
	 * @param newOne
	 *            the new one
	 */
	public OperationInfoAnalyzer(Object oldOne, Object newOne) {
		this.oldOne = oldOne;
		this.newOne = newOne;
	}

	/**
	 * Analyse.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	public void analyse() {
		// 如果重构的对象时业务实体
		if (oldOne instanceof BusinessClass && newOne instanceof BusinessClass) {
			BusinessClass oldBc = (BusinessClass) oldOne;
			BusinessClass newBc = (BusinessClass) newOne;

			// 业务实体显示名发生改变
			if (!oldBc.getDisplayName().equals(newBc.getDisplayName())) {

				this.generateInfo(OperationType.MODIFY,
						OperationTarget.BusinessClass.DPNAME,
						oldBc.getDisplayName(), newBc.getDisplayName(), oldBc);
			}

			// 业务实体名称发生改变
			if (!oldBc.getName().equals(newBc.getName())) {

				this.generateInfo(OperationType.MODIFY,
						OperationTarget.BusinessClass.BCNAME, oldBc.getName(),
						newBc.getName(), oldBc);
			}
			//
			// //业务实体数据库表名发生改变
			// if(!oldBc.getTableName().equals(newBc.getTableName())){
			//
			// this.generateInfo(OperationType.MODIFY,
			// OperationTarget.BusinessClass.DBTNAME,
			// oldBc.getTableName(),
			// newBc.getTableName(),oldBc);
			// }
			//
			// //分析业务实体中的属性操作信息
			// List<Property> oldBps = oldBc.getProperties();
			// List<Property> newBps = newBc.getProperties();
			// this.generateBpInfo(oldBps , newBps ,oldBc);

		}
	}

	/**
	 * 分析业务实体中的属性操作信息.
	 *
	 * @author mqfdy
	 * @param oldBps
	 *            the old bps
	 * @param newBps
	 *            the new bps
	 * @param oldBc
	 *            the old bc
	 * @Date 2018-09-03 09:00
	 */
	private void generateBpInfo(List<Property> oldBps, List<Property> newBps,
			BusinessClass oldBc) {

		// 首先找出新增的与修改的属性
		for (Property newProperty : newBps) {
			String newPropId = newProperty.getId();
			boolean hasProp = false;
			for (Property oldProperty : oldBps) {
				if (newPropId.equals(oldProperty.getId())) {
					// 找出属性是否被更改的条件
					this.propertyModified(oldProperty, newProperty, oldBc);
					hasProp = true;
					break;
				}
			}
			if (!hasProp) {// 找出新增的方法(主键的删除与新增会被当做修改来处理)
				this.generateInfo(OperationType.ADD,
						OperationTarget.BusinessClass.Property.PROPERTY, null,
						newProperty, oldBc);
			}
		}

		// 找出被删除的属性
		for (Property oldProperty : newBps) {
			String oldPropId = oldProperty.getId();
			boolean hasProp = false;
			for (Property newProperty : newBps) {
				if (oldPropId.equals(newProperty.getId())) {
					hasProp = true;
					break;
				}
			}
			if (!hasProp) {// 找出被删除的属性
				this.generateInfo(OperationType.DELETE,
						OperationTarget.BusinessClass.Property.PROPERTY,
						oldProperty, null, oldBc);
			}
		}
	}

	/**
	 * 根据属性被修改情况统计信息 主键到普通属性的改变，可被分解为新增主键和删除普通属性 普通属性到主键的改变，可被分解为删除主键和新增普通属性.
	 *
	 * @author mqfdy
	 * @param oldProperty
	 *            the old property
	 * @param newProperty
	 *            the new property
	 * @param oldBc
	 *            the old bc
	 * @Date 2018-09-03 09:00
	 */
	private void propertyModified(Property oldProperty, Property newProperty,
			BusinessClass oldBc) {

		// 属性名称更改
		if (!oldProperty.getName().equals(newProperty.getName())) {
			this.generateInfo(OperationType.MODIFY,
					OperationTarget.BusinessClass.Property.PROPNAME,
					oldProperty.getName(), newProperty.getName(), oldBc);
		}
		// 属性显示名更改
		if (!oldProperty.getDisplayName().equals(newProperty.getDisplayName())) {
			this.generateInfo(OperationType.MODIFY,
					OperationTarget.BusinessClass.Property.PROPDPNAME,
					oldProperty.getDisplayName(), newProperty.getDisplayName(),
					oldBc);
		}
		// 属性数据类型更改
		if (!oldProperty.getDataType().equals(newProperty.getDataType())) {
			this.generateInfo(OperationType.MODIFY,
					OperationTarget.BusinessClass.Property.PROPDATATYPE,
					oldProperty.getDataType(), newProperty.getDataType(), oldBc);
		}
		// 属性持久化类型更改
		if (oldProperty.isPersistence() != newProperty.isPersistence()) {
			this.generateInfo(OperationType.MODIFY,
					OperationTarget.BusinessClass.Property.PROPPERSISTENCE,
					oldProperty.isPersistence(), newProperty.isPersistence(),
					oldBc);
		}
		// 主键类型更改
		if (((PersistenceProperty) oldProperty).isPrimaryKey() != ((PersistenceProperty) newProperty)
				.isPrimaryKey()) {
			this.generateInfo(OperationType.MODIFY,
					OperationTarget.BusinessClass.Property.PROPPRIMARYKEY,
					((PKProperty) oldProperty).isPrimaryKey(),
					((PKProperty) newProperty).isPrimaryKey(), oldBc);
		}
		// 可空类型更改
		if (((PersistenceProperty) oldProperty).isNullable() != ((PersistenceProperty) newProperty)
				.isNullable()) {
			this.generateInfo(OperationType.MODIFY,
					OperationTarget.BusinessClass.Property.PROPNULLABLE,
					((PKProperty) oldProperty).isNullable(),
					((PKProperty) newProperty).isNullable(), oldBc);
		}
		// 唯一类型更改
		if (((PersistenceProperty) oldProperty).isUnique() != ((PersistenceProperty) newProperty)
				.isUnique()) {
			this.generateInfo(OperationType.MODIFY,
					OperationTarget.BusinessClass.Property.PROPUNIQUE,
					((PKProperty) oldProperty).isUnique(),
					((PKProperty) newProperty).isUnique(), oldBc);
		}
		// 数据库字段名更改
		if (((PersistenceProperty) oldProperty).getdBColumnName().equals(
				((PersistenceProperty) newProperty).getdBColumnName())) {
			this.generateInfo(OperationType.MODIFY,
					OperationTarget.BusinessClass.Property.PROPDBNAME,
					((PersistenceProperty) oldProperty).getdBColumnName(),
					((PersistenceProperty) newProperty).getdBColumnName(),
					oldBc);
		}
		// 数据库字段长度更改
		if (((PersistenceProperty) oldProperty).getdBDataLength().equals(
				((PersistenceProperty) newProperty).getdBColumnName())) {
			this.generateInfo(OperationType.MODIFY,
					OperationTarget.BusinessClass.Property.PROPDBLENGTH,
					((PersistenceProperty) oldProperty).getdBDataLength(),
					((PersistenceProperty) newProperty).getdBColumnName(),
					oldBc);
		}
		// 数据库字段精度更改
		if (((PersistenceProperty) oldProperty).getdBDataPrecision().equals(
				((PersistenceProperty) newProperty).getdBDataPrecision())) {
			this.generateInfo(OperationType.MODIFY,
					OperationTarget.BusinessClass.Property.PROPDBSCALE,
					((PersistenceProperty) oldProperty).getdBDataPrecision(),
					((PersistenceProperty) newProperty).getdBDataPrecision(),
					oldBc);
		}
	}

	/**
	 * 生成具体的修改操作OperationInfo对象.
	 *
	 * @author mqfdy
	 * @param type
	 *            the type
	 * @param target
	 *            the target
	 * @param oldValue
	 *            the old value
	 * @param newValue
	 *            the new value
	 * @param belongBc
	 *            the belong bc
	 * @Date 2018-09-03 09:00
	 */
	private void generateInfo(String type, String target, Object oldValue,
			Object newValue, BusinessClass belongBc) {
		OperationInfo info = new OperationInfo();
		info.setOperationType(type);// 操作类型为修改
		info.setOperationTarget(target);// 对象为业务实体名称
		info.setOriginalValue(oldValue);
		info.setNewVlue(newValue);
		info.setBelongBC(belongBc);
		infos.add(info);

	}

	/**
	 * Gets the infos.
	 *
	 * @author mqfdy
	 * @return the infos
	 * @Date 2018-09-03 09:00
	 */
	public List<OperationInfo> getInfos() {
		return infos;
	}

	/**
	 * Sets the infos.
	 *
	 * @author mqfdy
	 * @param infos
	 *            the new infos
	 * @Date 2018-09-03 09:00
	 */
	public void setInfos(List<OperationInfo> infos) {
		this.infos = infos;
	}

}
