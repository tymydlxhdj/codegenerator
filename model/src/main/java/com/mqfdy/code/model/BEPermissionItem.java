package com.mqfdy.code.model;

import java.util.UUID;

import org.dom4j.Element;

import com.mqfdy.code.model.utils.StringUtil;

/**
 * 实体权限条目
 * 
 * @author mqfdy
 * 
 */
public class BEPermissionItem {
	/**
	 * 资源唯一ID
	 */
	private String ISCResourceId;
	/**
	 * 角色
	 */
	private String role;

	/**
	 * 资源类型
	 */
	private String resourceType;

	/**
	 * 资源ID
	 */
	private String resourceId;

	/**
	 * 权限
	 */
	private String right;// 101 1代表选中，0代表没选中

	protected BEPermissionItem(String ISCResourceId, String role,
			String resourceType, String resourceId, String right) {
		super();
		this.ISCResourceId = ISCResourceId;
		this.role = role;
		this.resourceType = resourceType;
		this.resourceId = resourceId;
		this.right = right;
	}

	protected BEPermissionItem(String role, String resourceType,
			String resourceId, String right) {
		super();
		this.ISCResourceId = UUID.randomUUID().toString().replaceAll("-", "");
		this.role = role;
		this.resourceType = resourceType;
		this.resourceId = resourceId;
		this.right = right;
	}

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

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getResourceType() {
		return resourceType;
	}

	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	public String getRight() {
		return right;
	}

	public void setRight(String right) {
		this.right = right;
	}

	public String getISCResourceId() {
		return ISCResourceId;
	}

	public void setISCResourceId(String iSCResourceId) {
		ISCResourceId = iSCResourceId;
	}

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
