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
/**
 * 校验结果类
 * @author mqfdy
 *
 */
public class ValiResult {
	private AbstractModelElement ele;
	private String level;
	private String valiString;
	private String location;
	private String type;
	private String objectName;

	public ValiResult(String level, AbstractModelElement ele,
			String valiString, String location) {
		super();
		this.level = level;
		this.ele = ele;
		this.valiString = valiString;
		this.location = location;
		this.type = ele.getDisplayType();
	}

	public AbstractModelElement getEle() {
		return ele;
	}

	public void setEle(AbstractModelElement ele) {
		this.ele = ele;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getValiString() {
		return valiString;
	}

	public void setValiString(String valiString) {
		this.valiString = valiString;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

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

	public String getLevel() {
		return level;
	}
}
