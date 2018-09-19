package com.mqfdy.code.model;

import java.util.ArrayList;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * 业务实体权限.
 *
 * @author mqfdy
 */
public class BEPermission extends AbstractModelElement {

	/** 所属业务实体. */
	private BusinessClass belongBusinessClass;

	/** 业务状态. */
	private BEStatus beStatus;// (考虑删掉，业务状态树使用BEPermission-BusinessClass-BeStatus列表展示)

	/** 权限条目。key：资源ID 资源ID：属性或操作的ID. */
	// private Map<String , List<BEPermissionItem>> permissionItemsCache;

	/**
	 * 权限条目
	 */
	private List<BEPermissionItem> permissionItems;

	/**
	 * Instantiates a new BE permission.
	 *
	 * @param belongBusinessClass
	 *            the belong business class
	 * @param beStatus
	 *            the be status
	 */
	public BEPermission(BusinessClass belongBusinessClass, BEStatus beStatus) {
		permissionItems = new ArrayList<BEPermissionItem>();
		//beStatus.setBelongBusinessClass(belongBusinessClass);
		// belongBusinessClass.addPermission(this);
		this.belongBusinessClass = belongBusinessClass;
		this.beStatus = beStatus;
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
	 * Gets the be status.
	 *
	 * @author mqfdy
	 * @return the be status
	 * @Date 2018-09-03 09:00
	 */
	public BEStatus getBeStatus() {
		return beStatus;
	}

	/**
	 * Gets the permission items.
	 *
	 * @author mqfdy
	 * @return the permission items
	 * @Date 2018-09-03 09:00
	 */
	public List<BEPermissionItem> getPermissionItems() {
		return permissionItems;
	}

	/**
	 * Gets the permission items.
	 *
	 * @author mqfdy
	 * @param resourceId
	 *            the resource id
	 * @return the permission items
	 * @Date 2018-09-03 09:00
	 */
	public List<BEPermissionItem> getPermissionItems(String resourceId) {
		/*
		 * List<BEPermissionItem> resourcePermissionItems =
		 * permissionItemsCache.get(resourceId); if(resourcePermissionItems ==
		 * null){ return null; }else{ return resourcePermissionItems; }
		 */
		// 遍历列表查询
		if (permissionItems != null) {
			List<BEPermissionItem> list = new ArrayList<BEPermissionItem>();
			for (int i = 0; i < permissionItems.size(); i++) {
				BEPermissionItem item = permissionItems.get(i);
				if (item.getResourceId().equals(resourceId)) {
					list.add(item);
				}
			}
			return list;
		}
		return null;
	}

	/**
	 * 删除权限条目.
	 *
	 * @author mqfdy
	 * @param item
	 *            the item
	 * @Date 2018-09-03 09:00
	 */
	public void removePermissionItems(BEPermissionItem item) {
		if (permissionItems != null) {
			// List<BEPermissionItem> list = new ArrayList<BEPermissionItem>();
			for (int i = 0; i < permissionItems.size(); i++) {
				BEPermissionItem itemOld = permissionItems.get(i);
				if (itemOld.getResourceId().equals(item.getResourceId())
						&& itemOld.getResourceType().equals(
								item.getResourceType())) {
					permissionItems.remove(i);
				}
			}
		}
	}

	/**
	 * 添加条目的同时，注册到缓存.
	 *
	 * @author mqfdy
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
	 * @return the BE permission item
	 * @Date 2018-09-03 09:00
	 */
	public BEPermissionItem addPermissionItem(String ISCResourceId,
			String role, String resourceType, String resourceId, String right) {
		BEPermissionItem item = new BEPermissionItem(ISCResourceId, role,
				resourceType, resourceId, right);
		permissionItems.add(item);
		return item;
		/*
		 * if(permissionItemsCache!= null){ //从缓存中获取到当前资源对应的权限列表
		 * List<BEPermissionItem> resourceItems =
		 * permissionItemsCache.get(resourceId); if(resourceItems == null){
		 * resourceItems = new ArrayList<BEPermissionItem>();
		 * permissionItemsCache.put(resourceId, resourceItems); }
		 * resourceItems.add(new BEPermissionItem(role, resourceType,
		 * resourceId, right)); }
		 */
	}

	/**
	 * Adds the permission item.
	 *
	 * @author mqfdy
	 * @param role
	 *            the role
	 * @param resourceType
	 *            the resource type
	 * @param resourceId
	 *            the resource id
	 * @param right
	 *            the right
	 * @return the BE permission item
	 * @Date 2018-09-03 09:00
	 */
	public BEPermissionItem addPermissionItem(String role, String resourceType,
			String resourceId, String right) {
		BEPermissionItem item = new BEPermissionItem(role, resourceType,
				resourceId, right);
		permissionItems.add(item);
		return item;
		/*
		 * if(permissionItemsCache!= null){ //从缓存中获取到当前资源对应的权限列表
		 * List<BEPermissionItem> resourceItems =
		 * permissionItemsCache.get(resourceId); if(resourceItems == null){
		 * resourceItems = new ArrayList<BEPermissionItem>();
		 * permissionItemsCache.put(resourceId, resourceItems); }
		 * resourceItems.add(new BEPermissionItem(role, resourceType,
		 * resourceId, right)); }
		 */
	}

	/**
	 * Gets the permissoin by status.
	 *
	 * @author mqfdy
	 * @param status
	 *            the status
	 * @return the permissoin by status
	 * @Date 2018-09-03 09:00
	 */
	public static BEPermission getPermissoinByStatus(BEStatus status) {
		/*if (status != null && status.getBelongBusinessClass() != null) {
			List<BEPermission> listPermissions = status
					.getBelongBusinessClass().getPermissions();
			if (listPermissions == null) {
				return null;
			}
			for (BEPermission bePermission : listPermissions) {
				BEStatus curStatus = bePermission.getBeStatus();
				if (curStatus != null && status != null
						&& curStatus.getId().equals(status.getId())) {
					return bePermission;
				}
			}
		}*/
		return null;
	}

	/**
	 * Gets the by ISC resource id.
	 *
	 * @author mqfdy
	 * @param status
	 *            the status
	 * @param ISCResourceId
	 *            the ISC resource id
	 * @return the by ISC resource id
	 * @Date 2018-09-03 09:00
	 */
	public static String getByISCResourceId(BEStatus status,
			String ISCResourceId) {
		if (status == null) {
			return null;
		}
		BEPermission permission = getPermissoinByStatus(status);
		if (permission == null) {
			return null;
		}
		List<BEPermissionItem> items = permission.getPermissionItems();
		if (items == null) {
			return null;
		} else {
			for (BEPermissionItem item : items) {
				if (item != null
						&& item.getISCResourceId().equals(ISCResourceId)) {
					return item.getResourceId();
				}
			}
			return null;
		}
	}

	/**
	 * Gets the ISC resource id.
	 *
	 * @author mqfdy
	 * @param status
	 *            the status
	 * @param resourceId
	 *            the resource id
	 * @return the ISC resource id
	 * @Date 2018-09-03 09:00
	 */
	public static String getISCResourceId(BEStatus status, String resourceId) {
		BEPermission permission = getPermissoinByStatus(status);
		if (permission == null) {
			return null;
		}
		List<BEPermissionItem> items = permission.getPermissionItems();
		if (items == null) {
			return null;
		} else {
			for (BEPermissionItem item : items) {
				if (item != null && item.getResourceId().equals(resourceId)) {
					return item.getISCResourceId();
				}
			}
			return null;
		}
	}

	/**
	 * @return
	 */
	public AbstractModelElement getParent() {
		return this.belongBusinessClass.getPermissionPackage();
	}

	/**
	 * @return
	 */
	public List<AbstractModelElement> getChildren() {
		return EMPTY_CHILD;
	}

}
