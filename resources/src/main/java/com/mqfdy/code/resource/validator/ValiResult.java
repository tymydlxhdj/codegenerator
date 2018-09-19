package com.mqfdy.code.resource.validator;

import com.mqfdy.code.model.AbstractModelElement;
import com.mqfdy.code.model.Association;
import com.mqfdy.code.model.BusinessClass;
import com.mqfdy.code.model.BusinessObjectModel;
import com.mqfdy.code.model.BusinessOperation;
import com.mqfdy.code.model.Enumeration;
import com.mqfdy.code.model.ModelPackage;
import com.mqfdy.code.model.Property;
import com.mqfdy.code.model.PropertyGroup;
import com.mqfdy.code.model.graph.Diagram;
// TODO: Auto-generated Javadoc

/**
 * 校验结果类.
 *
 * @author mqfdy
 */
public class ValiResult {
	
	/** The ele. */
	private AbstractModelElement ele;
	
	/** The level. */
	private String level;
	
	/** The vali string. */
	private String valiString;
	
	/** The location. */
	private String location;
	
	/** The type. */
	private String type;
	
	/** The object name. */
	private String objectName;

	/**
	 * Instantiates a new vali result.
	 *
	 * @param level
	 *            the level
	 * @param ele
	 *            the ele
	 * @param valiString
	 *            the vali string
	 * @param location
	 *            the location
	 */
	public ValiResult(String level, AbstractModelElement ele,
			String valiString, String location) {
		super();
		this.level = level;
		this.ele = ele;
		this.valiString = valiString;
		this.location = location;
		this.type = ele.getDisplayType();
	}

	/**
	 * Gets the ele.
	 *
	 * @author mqfdy
	 * @return the ele
	 * @Date 2018-09-03 09:00
	 */
	public AbstractModelElement getEle() {
		return ele;
	}

	/**
	 * Sets the ele.
	 *
	 * @author mqfdy
	 * @param ele
	 *            the new ele
	 * @Date 2018-09-03 09:00
	 */
	public void setEle(AbstractModelElement ele) {
		this.ele = ele;
	}

	/**
	 * Gets the location.
	 *
	 * @author mqfdy
	 * @return the location
	 * @Date 2018-09-03 09:00
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * Sets the location.
	 *
	 * @author mqfdy
	 * @param location
	 *            the new location
	 * @Date 2018-09-03 09:00
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * Gets the vali string.
	 *
	 * @author mqfdy
	 * @return the vali string
	 * @Date 2018-09-03 09:00
	 */
	public String getValiString() {
		return valiString;
	}

	/**
	 * Sets the vali string.
	 *
	 * @author mqfdy
	 * @param valiString
	 *            the new vali string
	 * @Date 2018-09-03 09:00
	 */
	public void setValiString(String valiString) {
		this.valiString = valiString;
	}

	/**
	 * Gets the type.
	 *
	 * @author mqfdy
	 * @return the type
	 * @Date 2018-09-03 09:00
	 */
	public String getType() {
		return type;
	}

	/**
	 * Sets the type.
	 *
	 * @author mqfdy
	 * @param type
	 *            the new type
	 * @Date 2018-09-03 09:00
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Gets the object name.
	 *
	 * @author mqfdy
	 * @return the object name
	 * @Date 2018-09-03 09:00
	 */
	public String getObjectName() {
		if (ele instanceof BusinessClass)
			objectName = "BusinessClass '" + ele.getDisplayName() + "'";
		else if (ele instanceof BusinessObjectModel)
			objectName = "BusinessObjectModel '" + ele.getDisplayName() + "'";
		else if (ele instanceof Enumeration)
			objectName = "Enumeration '" + ele.getDisplayName() + "'";
		else if (ele instanceof Property)
			objectName = "Property '" + ele.getParent().getDisplayName() + "-"
					+ ele.getDisplayName() + "'";
		else if (ele instanceof PropertyGroup)
			objectName = "PropertyGroup '" + ele.getParent().getDisplayName() + "-"
					+ ele.getDisplayName() + "'";
		else if (ele instanceof BusinessOperation)
			objectName = "Operation '" + ele.getDisplayName() + "'";
		else if (ele instanceof ModelPackage)
			objectName = "ModelPackage '" + ele.getDisplayName() + "'";
		else if (ele instanceof Diagram)
			objectName = "Diagram '" + ele.getDisplayName() + "'";
		else if (ele instanceof Association)
			objectName = "Association '" + ele.getDisplayName() + "'";
		return objectName;
	}

	/**
	 * Gets the level.
	 *
	 * @author mqfdy
	 * @return the level
	 * @Date 2018-09-03 09:00
	 */
	public String getLevel() {
		return level;
	}
}
