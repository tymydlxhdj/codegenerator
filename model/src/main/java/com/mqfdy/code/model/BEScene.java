package com.mqfdy.code.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 场景
 * 
 * @author mqfdy
 * 
 */
public class BEScene extends AbstractModelElement {

	private String businessCode ;
	/**
	 * 所属的业务实体对象
	 */
	private BusinessClass belongBusinessClass;
	
	/**
	 * 实体状态列表
	 */
	private List<BEStatus> statuses;

	/**
	 * 构造函数
	 */
	public BEScene() {
		statuses = new ArrayList<BEStatus>(50);
	}

	public BusinessClass getBelongBusinessClass() {
		return belongBusinessClass;
	}

	public void setBelongBusinessClass(BusinessClass belongBusinessClass) {
		this.belongBusinessClass = belongBusinessClass;
	}

	public String getBusinessCode() {
		return businessCode;
	}

	public void setBusinessCode(String businessCode) {
		this.businessCode = businessCode;
	}

	public AbstractModelElement getParent() {
		return this.belongBusinessClass.getStatusPackage();
	}

	public List<AbstractModelElement> getChildren() {
		return EMPTY_CHILD;
	}

	public List<BEStatus> getStatuses() {
		return statuses;
	}

	public void setStatuses(List<BEStatus> statuses) {
		this.statuses = statuses;
	}

	public void addStatus(BEStatus status) {
		this.statuses.add(status);
	}
}
