package com.mqfdy.code.model;

import java.util.Date;

import org.dom4j.Element;

import com.mqfdy.code.model.utils.DateTimeUtil;
import com.mqfdy.code.model.utils.StringUtil;

/**
 * 版本信息
 * 
 * @author mqfdy
 * 
 */
public class VersionInfo {

	/**
	 * 创建时间
	 */
	private Date createdTime;

	/**
	 * 创建人员
	 */
	private String creator;

	/**
	 * 最好修改时间
	 */
	private Date changedTime;

	/**
	 * 描述
	 */
	private String description;

	/**
	 * 版本号
	 */
	private String versionNumber;

	/**
	 * 最后 修改人员
	 */
	private String modifier;

	public VersionInfo() {
	}

	public VersionInfo(Element versionInfoElement) {

		// 取出VersionInfo节点下的CreatedTime，ChangedTime，Description，Version，Creator，Modifier子节点值
		String versionInfo_CreatedTime = versionInfoElement
				.elementTextTrim("CreatedTime");
		String versionInfo_ChangedTime = versionInfoElement
				.elementTextTrim("ChangedTime");
		String versionInfo_Description = versionInfoElement
				.elementTextTrim("Description");
		String versionInfo_Version = versionInfoElement
				.elementTextTrim("Version");
		String versionInfo_Creator = versionInfoElement
				.elementTextTrim("Creator");
		String versionInfo_Modifier = versionInfoElement
				.elementTextTrim("Modifier");

		this.setCreatedTime(DateTimeUtil.string2Date(versionInfo_CreatedTime));
		this.setChangedTime(DateTimeUtil.string2Date(versionInfo_ChangedTime));
		this.setDescription(versionInfo_Description);
		this.setVersionNumber(versionInfo_Version);
		this.setCreator(versionInfo_Creator);
		this.setModifier(versionInfo_Modifier);

	}

	public void generateXmlElement(Element modelElement) {
		Element versionInfoElement = modelElement.addElement("VersionInfo");
		// 在versionInfoElement节点中创建CreatedTime，ChangedTime，Description，Version，Creator，Modifier
		versionInfoElement.addElement("CreatedTime").addText(
				DateTimeUtil.date2String(getCreatedTime()));
		versionInfoElement.addElement("ChangedTime").addText(
				DateTimeUtil.date2String(new Date()));
		versionInfoElement.addElement("Description").addText(
				StringUtil.convertNull2EmptyStr(getDescription()));
		versionInfoElement.addElement("Version").addText(
				StringUtil.convertNull2EmptyStr(getVersionNumber()));
		versionInfoElement.addElement("Creator").addText(
				StringUtil.convertNull2EmptyStr(getCreator()));
		versionInfoElement.addElement("Modifier").addText(
				StringUtil.convertNull2EmptyStr(getModifier()));
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getVersionNumber() {
		return versionNumber;
	}

	public void setVersionNumber(String versionNumber) {
		this.versionNumber = versionNumber;
	}

	public String getModifier() {
		return modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public Date getChangedTime() {
		return changedTime;
	}

	public void setChangedTime(Date changedTime) {
		this.changedTime = changedTime;
	}

	public static VersionInfo getVersionInfo() {
		VersionInfo versionInfo = new VersionInfo();
		versionInfo.setCreatedTime(new Date());
		versionInfo.setChangedTime(new Date());
		versionInfo.setVersionNumber("1");
		versionInfo.setCreator(System.getProperty("user.name"));
		versionInfo.setModifier(System.getProperty("user.name"));
		return versionInfo;
	}

}
