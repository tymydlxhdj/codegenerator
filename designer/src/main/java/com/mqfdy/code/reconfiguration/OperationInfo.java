package com.mqfdy.code.reconfiguration;

import java.util.ArrayList;
import java.util.List;

import com.mqfdy.code.model.BusinessClass;

// TODO: Auto-generated Javadoc
/**
 * 操作详细信息.
 *
 * @author mqfdy
 */
public class OperationInfo {

	/** The parent info. */
	private OperationInfo parentInfo;
	
	/** The operation type. */
	private String operationType;// 操作类型
	
	/** The operation target. */
	private String operationTarget;// 被操作对象
	
	/** The original value. */
	private Object originalValue;// 原先值
	
	/** The new vlue. */
	private Object newVlue;// 变化后值（如果是新增或删除，该值即为新增或删除的对象本身）
	
	/** The belong BC. */
	private BusinessClass belongBC;// BusinessClass

	/**
	 * Gets the ops.
	 *
	 * @author mqfdy
	 * @return the ops
	 * @Date 2018-09-03 09:00
	 */
	public static List<OperationInfo> getOps() {
		List<OperationInfo> list = new ArrayList<OperationInfo>();
		OperationInfo root = new OperationInfo();
		root.setNewVlue(null);
		root.setOperationTarget("业务实体");
		root.setParentInfo(null);
		list.add(root);

		OperationInfo jiben = new OperationInfo();
		jiben.setNewVlue(null);
		jiben.setOperationTarget("基本信息");
		jiben.setParentInfo(root);

		OperationInfo shuxing = new OperationInfo();
		shuxing.setNewVlue(null);
		shuxing.setOperationTarget("属性");
		shuxing.setParentInfo(root);

		OperationInfo caozuo = new OperationInfo();
		caozuo.setNewVlue(null);
		caozuo.setOperationTarget("操作");
		caozuo.setParentInfo(root);

		return list;
	}

	/**
	 * Gets the original value.
	 *
	 * @author mqfdy
	 * @return the original value
	 * @Date 2018-09-03 09:00
	 */
	public Object getOriginalValue() {
		return originalValue;
	}

	/**
	 * Sets the original value.
	 *
	 * @author mqfdy
	 * @param originalValue
	 *            the new original value
	 * @Date 2018-09-03 09:00
	 */
	public void setOriginalValue(Object originalValue) {
		this.originalValue = originalValue;
	}

	/**
	 * Gets the new vlue.
	 *
	 * @author mqfdy
	 * @return the new vlue
	 * @Date 2018-09-03 09:00
	 */
	public Object getNewVlue() {
		return newVlue;
	}

	/**
	 * Sets the new vlue.
	 *
	 * @author mqfdy
	 * @param newVlue
	 *            the new new vlue
	 * @Date 2018-09-03 09:00
	 */
	public void setNewVlue(Object newVlue) {
		this.newVlue = newVlue;
	}

	/**
	 * Gets the operation type.
	 *
	 * @author mqfdy
	 * @return the operation type
	 * @Date 2018-09-03 09:00
	 */
	public String getOperationType() {
		return operationType;
	}

	/**
	 * Sets the operation type.
	 *
	 * @author mqfdy
	 * @param operationType
	 *            the new operation type
	 * @Date 2018-09-03 09:00
	 */
	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	/**
	 * Gets the operation target.
	 *
	 * @author mqfdy
	 * @return the operation target
	 * @Date 2018-09-03 09:00
	 */
	public String getOperationTarget() {
		return operationTarget;
	}

	/**
	 * Sets the operation target.
	 *
	 * @author mqfdy
	 * @param operationTarget
	 *            the new operation target
	 * @Date 2018-09-03 09:00
	 */
	public void setOperationTarget(String operationTarget) {
		this.operationTarget = operationTarget;
	}

	/**
	 * Gets the belong BC.
	 *
	 * @author mqfdy
	 * @return the belong BC
	 * @Date 2018-09-03 09:00
	 */
	public BusinessClass getBelongBC() {
		return belongBC;
	}

	/**
	 * Sets the belong BC.
	 *
	 * @author mqfdy
	 * @param belongBC
	 *            the new belong BC
	 * @Date 2018-09-03 09:00
	 */
	public void setBelongBC(BusinessClass belongBC) {
		this.belongBC = belongBC;
	}

	/**
	 * Gets the parent info.
	 *
	 * @author mqfdy
	 * @return the parent info
	 * @Date 2018-09-03 09:00
	 */
	public OperationInfo getParentInfo() {
		return parentInfo;
	}

	/**
	 * Sets the parent info.
	 *
	 * @author mqfdy
	 * @param parentInfo
	 *            the new parent info
	 * @Date 2018-09-03 09:00
	 */
	public void setParentInfo(OperationInfo parentInfo) {
		this.parentInfo = parentInfo;
	}
}
