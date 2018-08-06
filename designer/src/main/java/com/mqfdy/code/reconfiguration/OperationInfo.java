package com.mqfdy.code.reconfiguration;

import java.util.ArrayList;
import java.util.List;

import com.mqfdy.code.model.BusinessClass;

/**
 * 操作详细信息
 * 
 * @author mqfdy
 */
public class OperationInfo {

	private OperationInfo parentInfo;
	private String operationType;// 操作类型
	private String operationTarget;// 被操作对象
	private Object originalValue;// 原先值
	private Object newVlue;// 变化后值（如果是新增或删除，该值即为新增或删除的对象本身）
	private BusinessClass belongBC;// BusinessClass

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

	public Object getOriginalValue() {
		return originalValue;
	}

	public void setOriginalValue(Object originalValue) {
		this.originalValue = originalValue;
	}

	public Object getNewVlue() {
		return newVlue;
	}

	public void setNewVlue(Object newVlue) {
		this.newVlue = newVlue;
	}

	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	public String getOperationTarget() {
		return operationTarget;
	}

	public void setOperationTarget(String operationTarget) {
		this.operationTarget = operationTarget;
	}

	public BusinessClass getBelongBC() {
		return belongBC;
	}

	public void setBelongBC(BusinessClass belongBC) {
		this.belongBC = belongBC;
	}

	public OperationInfo getParentInfo() {
		return parentInfo;
	}

	public void setParentInfo(OperationInfo parentInfo) {
		this.parentInfo = parentInfo;
	}
}
