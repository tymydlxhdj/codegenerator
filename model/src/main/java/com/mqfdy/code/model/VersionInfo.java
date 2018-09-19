package com.mqfdy.code.model;

import java.util.Date;

import org.dom4j.Element;

import com.mqfdy.code.model.utils.DateTimeUtil;
import com.mqfdy.code.model.utils.StringUtil;

// TODO: Auto-generated Javadoc
/**
 * 版本信息.
 *
 * @author mqfdy
 */
public class VersionInfo {

	/** 创建时间. */
	private Date createdTime;

	/** 创建人员. */
	private String creator;

	/** 最好修改时间. */
	private Date changedTime;

	/** 描述. */
	private String description;

	/** 版本号. */
	private String versionNumber;

	/** 最后 修改人员. */
	private String modifier;

	/**
	 * Instantiates a new version info.
	 */
	public VersionInfo() {
	}

	/**
	 * Instantiates a new version info.
	 *
	 * @param versionInfoElement
	 *            the version info element
	 */
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

	/**
	 * Generate xml element.
	 *
	 * @author mqfdy
	 * @param modelElement
	 *            the model element
	 * @Date 2018-09-03 09:00
	 */
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

	/**
	 * Gets the creator.
	 *
	 * @author mqfdy
	 * @return the creator
	 * @Date 2018-09-03 09:00
	 */
	public String getCreator() {
		return creator;
	}

	/**
	 * Sets the creator.
	 *
	 * @author mqfdy
	 * @param creator
	 *            the new creator
	 * @Date 2018-09-03 09:00
	 */
	public void setCreator(String creator) {
		this.creator = creator;
	}

	/**
	 * Gets the description.
	 *
	 * @author mqfdy
	 * @return the description
	 * @Date 2018-09-03 09:00
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description.
	 *
	 * @author mqfdy
	 * @param description
	 *            the new description
	 * @Date 2018-09-03 09:00
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Gets the version number.
	 *
	 * @author mqfdy
	 * @return the version number
	 * @Date 2018-09-03 09:00
	 */
	public String getVersionNumber() {
		return versionNumber;
	}

	/**
	 * Sets the version number.
	 *
	 * @author mqfdy
	 * @param versionNumber
	 *            the new version number
	 * @Date 2018-09-03 09:00
	 */
	public void setVersionNumber(String versionNumber) {
		this.versionNumber = versionNumber;
	}

	/**
	 * Gets the modifier.
	 *
	 * @author mqfdy
	 * @return the modifier
	 * @Date 2018-09-03 09:00
	 */
	public String getModifier() {
		return modifier;
	}

	/**
	 * Sets the modifier.
	 *
	 * @author mqfdy
	 * @param modifier
	 *            the new modifier
	 * @Date 2018-09-03 09:00
	 */
	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	/**
	 * Gets the created time.
	 *
	 * @author mqfdy
	 * @return the created time
	 * @Date 2018-09-03 09:00
	 */
	public Date getCreatedTime() {
		return createdTime;
	}

	/**
	 * Sets the created time.
	 *
	 * @author mqfdy
	 * @param createdTime
	 *            the new created time
	 * @Date 2018-09-03 09:00
	 */
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	/**
	 * Gets the changed time.
	 *
	 * @author mqfdy
	 * @return the changed time
	 * @Date 2018-09-03 09:00
	 */
	public Date getChangedTime() {
		return changedTime;
	}

	/**
	 * Sets the changed time.
	 *
	 * @author mqfdy
	 * @param changedTime
	 *            the new changed time
	 * @Date 2018-09-03 09:00
	 */
	public void setChangedTime(Date changedTime) {
		this.changedTime = changedTime;
	}

	/**
	 * Gets the version info.
	 *
	 * @author mqfdy
	 * @return the version info
	 * @Date 2018-09-03 09:00
	 */
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
