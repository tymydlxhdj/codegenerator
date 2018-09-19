package com.mqfdy.code.model;

import java.util.UUID;

import org.dom4j.Element;

import com.mqfdy.code.model.utils.StringUtil;

// TODO: Auto-generated Javadoc
/**
 * 实体权限条目.
 *
 * @author mqfdy
 */
public class BEPermissionItem {
	
	/** 资源唯一ID. */
	private String ISCResourceId;
	
	/** 角色. */
	private String role;

	/** 资源类型. */
	private String resourceType;

	/** 资源ID. */
	private String resourceId;

	/** 权限. */
	private String right;// 101 1代表选中，0代表没选中

	/**
	 * Instantiates a new BE permission item.
	 *
	 * @param ISCResourceId
	 *            the ISC resource id
	 * @param role
	 *            the role
	 * @param resourceType
	 *            the resource type
	 * @param resourceId
	 *            the resource id
	 * @param right
	 *            the right
	 */
	protected BEPermissionItem(String ISCResourceId, String role,
			String resourceType, String resourceId, String right) {
		super();
		this.ISCResourceId = ISCResourceId;
		this.role = role;
		this.resourceType = resourceType;
		this.resourceId = resourceId;
		this.right = right;
	}

	/**
	 * Instantiates a new BE permission item.
	 *
	 * @param role
	 *            the role
	 * @param resourceType
	 *            the resource type
	 * @param resourceId
	 *            the resource id
	 * @param right
	 *            the right
	 */
	protected BEPermissionItem(String role, String resourceType,
			String resourceId, String right) {
		super();
		this.ISCResourceId = UUID.randomUUID().toString().replaceAll("-", "");
		this.role = role;
		this.resourceType = resourceType;
		this.resourceId = resourceId;
		this.right = right;
	}

	/**
	 * Generate xml element.
	 *
	 * @author mqfdy
	 * @param permission
	 *            the permission
	 * @param permissionsElement
	 *            the permissions element
	 * @Date 2018-09-03 09:00
	 */
	public void generateXmlElement(BEPermission permission,
			Element permissionsElement) {
		Element permissionElement = permissionsElement.addElement("Permission");// 创建Permission节点
		String iSCResourceId = getISCResourceId();
		String resourceId = getResourceId();
		String resourceType = getResourceType();
		String right = getRight();
		String role = getRole();
		permissionElement.addAttribute("id",
				StringUtil.convertNull2EmptyStr(permission.getId()));
		permissionElement.addAttribute("statusId", StringUtil
				.convertNull2EmptyStr(permission.getBeStatus() == null ? null
						: permission.getBeStatus().getId()));
		permissionElement.addAttribute("resourceId",
				StringUtil.convertNull2EmptyStr(resourceId));
		permissionElement.addAttribute("iSCResourceId",
				StringUtil.convertNull2EmptyStr(iSCResourceId));
		permissionElement.addAttribute("resourceType",
				StringUtil.convertNull2EmptyStr(resourceType));
		permissionElement.addAttribute("right",
				StringUtil.convertNull2EmptyStr(right));
		permissionElement.addAttribute("role",
				StringUtil.convertNull2EmptyStr(role));
	}

	/**
	 * Gets the role.
	 *
	 * @author mqfdy
	 * @return the role
	 * @Date 2018-09-03 09:00
	 */
	public String getRole() {
		return role;
	}

	/**
	 * Sets the role.
	 *
	 * @author mqfdy
	 * @param role
	 *            the new role
	 * @Date 2018-09-03 09:00
	 */
	public void setRole(String role) {
		this.role = role;
	}

	/**
	 * Gets the resource type.
	 *
	 * @author mqfdy
	 * @return the resource type
	 * @Date 2018-09-03 09:00
	 */
	public String getResourceType() {
		return resourceType;
	}

	/**
	 * Sets the resource type.
	 *
	 * @author mqfdy
	 * @param resourceType
	 *            the new resource type
	 * @Date 2018-09-03 09:00
	 */
	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}

	/**
	 * Gets the resource id.
	 *
	 * @author mqfdy
	 * @return the resource id
	 * @Date 2018-09-03 09:00
	 */
	public String getResourceId() {
		return resourceId;
	}

	/**
	 * Sets the resource id.
	 *
	 * @author mqfdy
	 * @param resourceId
	 *            the new resource id
	 * @Date 2018-09-03 09:00
	 */
	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	/**
	 * Gets the right.
	 *
	 * @author mqfdy
	 * @return the right
	 * @Date 2018-09-03 09:00
	 */
	public String getRight() {
		return right;
	}

	/**
	 * Sets the right.
	 *
	 * @author mqfdy
	 * @param right
	 *            the new right
	 * @Date 2018-09-03 09:00
	 */
	public void setRight(String right) {
		this.right = right;
	}

	/**
	 * Gets the ISC resource id.
	 *
	 * @author mqfdy
	 * @return the ISC resource id
	 * @Date 2018-09-03 09:00
	 */
	public String getISCResourceId() {
		return ISCResourceId;
	}

	/**
	 * Sets the ISC resource id.
	 *
	 * @author mqfdy
	 * @param iSCResourceId
	 *            the new ISC resource id
	 * @Date 2018-09-03 09:00
	 */
	public void setISCResourceId(String iSCResourceId) {
		ISCResourceId = iSCResourceId;
	}

	/**
	 * @param obj
	 * @return
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj != null && obj instanceof BEPermissionItem) {
			BEPermissionItem item = (BEPermissionItem) obj;
			if (this.resourceId.equals(item.getResourceId())
					&& this.resourceType.equals(item.getResourceId())) {
				return true;
			}
		}
		return false;
	}

}
