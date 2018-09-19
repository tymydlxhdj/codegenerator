package com.mqfdy.code.model;

import java.util.ArrayList;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * 场景.
 *
 * @author mqfdy
 */
public class BEScene extends AbstractModelElement {

	/** The business code. */
	private String businessCode ;
	
	/** 所属的业务实体对象. */
	private BusinessClass belongBusinessClass;
	
	/** 实体状态列表. */
	private List<BEStatus> statuses;

	/**
	 * 构造函数.
	 */
	public BEScene() {
		statuses = new ArrayList<BEStatus>(50);
	}

	/**
	 * Gets the belong business class.
	 *
	 * @author mqfdy
	 * @return the belong business class
	 * @Date 2018-09-03 09:00
	 */
	public BusinessClass getBelongBusinessClass() {
		return belongBusinessClass;
	}

	/**
	 * Sets the belong business class.
	 *
	 * @author mqfdy
	 * @param belongBusinessClass
	 *            the new belong business class
	 * @Date 2018-09-03 09:00
	 */
	public void setBelongBusinessClass(BusinessClass belongBusinessClass) {
		this.belongBusinessClass = belongBusinessClass;
	}

	/**
	 * Gets the business code.
	 *
	 * @author mqfdy
	 * @return the business code
	 * @Date 2018-09-03 09:00
	 */
	public String getBusinessCode() {
		return businessCode;
	}

	/**
	 * Sets the business code.
	 *
	 * @author mqfdy
	 * @param businessCode
	 *            the new business code
	 * @Date 2018-09-03 09:00
	 */
	public void setBusinessCode(String businessCode) {
		this.businessCode = businessCode;
	}

	/**
	 * @return
	 */
	public AbstractModelElement getParent() {
		return this.belongBusinessClass.getStatusPackage();
	}

	/**
	 * @return
	 */
	public List<AbstractModelElement> getChildren() {
		return EMPTY_CHILD;
	}

	/**
	 * Gets the statuses.
	 *
	 * @author mqfdy
	 * @return the statuses
	 * @Date 2018-09-03 09:00
	 */
	public List<BEStatus> getStatuses() {
		return statuses;
	}

	/**
	 * Sets the statuses.
	 *
	 * @author mqfdy
	 * @param statuses
	 *            the new statuses
	 * @Date 2018-09-03 09:00
	 */
	public void setStatuses(List<BEStatus> statuses) {
		this.statuses = statuses;
	}

	/**
	 * Adds the status.
	 *
	 * @author mqfdy
	 * @param status
	 *            the status
	 * @Date 2018-09-03 09:00
	 */
	public void addStatus(BEStatus status) {
		this.statuses.add(status);
	}
}
