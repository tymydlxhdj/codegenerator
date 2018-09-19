package com.mqfdy.code.springboot.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.datatools.connectivity.drivers.DriverInstance;
import org.eclipse.datatools.connectivity.drivers.DriverManager;
import org.eclipse.datatools.connectivity.drivers.DriverMgmtMessages;
import org.eclipse.datatools.connectivity.drivers.IDriverMgmtConstants;
import org.eclipse.datatools.connectivity.drivers.IPropertySet;
import org.eclipse.datatools.connectivity.drivers.PropertySetImpl;
import org.eclipse.datatools.connectivity.drivers.models.CategoryDescriptor;
import org.eclipse.datatools.connectivity.drivers.models.OverrideTemplateDescriptor;
import org.eclipse.datatools.connectivity.drivers.models.TemplateDescriptor;

// TODO: Auto-generated Javadoc
/**
 * The Class DatabaseInfoConfigUtil.
 *
 * @author papa
 * @mail rain2sunny@gmail.com 2007-12-30
 */
public class DatabaseInfoConfigUtil {
	private static DatabaseInfoConfigUtil instance;
	
	/** The database category ID. */
	public static String databaseCategoryID = "org.eclipse.datatools.connectivity.db.driverCategory";
	
	/** The generic category ID. */
	public static String genericCategoryID = "org.eclipse.datatools.connectivity.db.genericDriverCategory";
	
	/** The generic template ID. */
	public static String genericTemplateID = "org.eclipse.datatools.connectivity.db.generic.genericDriverTemplate";
	
	/** The Constant ORACLE_DRIVER_INSTANCE_ID. */
	public static final String ORACLE_DRIVER_INSTANCE_ID = "DriverDefn.oracle.dbtools.dtp.connectivity.db.genericDriverTemplate";

	/**
	 * Gets the single instance of DatabaseInfoConfigUtil.
	 *
	 * @author mqfdy
	 * @return single instance of DatabaseInfoConfigUtil
	 * @Date 2018-09-03 09:00
	 */
	public static DatabaseInfoConfigUtil getInstance() {
		if (instance == null)
			instance = new DatabaseInfoConfigUtil();
		return instance;
	}

	private DatabaseInfoConfigUtil() {
	}

	/**
	 * 得到Studio中配置好的DriverInstance的名称列表 注：在这里DriverInstance的名称和id是相同的.
	 *
	 * @author mqfdy
	 * @return the driver instance names
	 * @Date 2018-09-03 09:00
	 */
	public String[] getDriverInstanceNames() {
		DriverInstance[] driverInstances = DriverManager.getInstance()
				.getAllDriverInstances();
		int size = driverInstances.length;
		String[] names = new String[size];
		for (int i = 0; i < size; i++) {
			names[i] = driverInstances[i].getName();
		}
		return names;
	}

	/**
	 * 根据driver id来获取DriverInstance实例.
	 *
	 * @author mqfdy
	 * @param driverInstanceId
	 *            the driver instance id
	 * @return the driver instance by ID
	 * @Date 2018-09-03 09:00
	 */
	public DriverInstance getDriverInstanceByID(String driverInstanceId) {
		return DriverManager.getInstance().getDriverInstanceByID(
				driverInstanceId);
	}

	/**
	 * 自动配置dtp中需要的driverInstance
	 */
	public void createAllSupportDriverInstance() {
		TemplateDescriptor[] tds = TemplateDescriptor
				.getDriverTemplateDescriptors();
		for (TemplateDescriptor td : tds) {
			if (!td.getId().equals(genericTemplateID))
				createDirverInstance(td);
		}
	}

	/**
	 * 得到databaseCategoryID代表的节点的所有孩子节点.
	 *
	 * @author mqfdy
	 * @param databaseCategoryID
	 *            the database category ID
	 * @return the database descriptor list
	 * @Date 2018-09-03 09:00
	 */
	@SuppressWarnings("unchecked")
	public List<CategoryDescriptor> getDatabaseDescriptorList(
			String databaseCategoryID) {
		CategoryDescriptor databaseCategory = CategoryDescriptor
				.getCategoryDescriptor(databaseCategoryID);
		return databaseCategory.getChildCategories();
	}

	/**
	 * 得到databaseCategoryID代表的节点下所有的孩子节点，但是过滤掉filterList中包含的id的节点.
	 *
	 * @author mqfdy
	 * @param databaseCategoryID
	 *            the database category ID
	 * @param filterList
	 *            the filter list
	 * @return the database descriptor list
	 * @Date 2018-09-03 09:00
	 */
	public List<CategoryDescriptor> getDatabaseDescriptorList(
			String databaseCategoryID, List<String> filterList) {
		List<CategoryDescriptor> categoryList = getDatabaseDescriptorList(databaseCategoryID);
		Map<String, CategoryDescriptor> map = new HashMap<String, CategoryDescriptor>(
				categoryList.size());
		for (CategoryDescriptor cd : categoryList) {
			map.put(cd.getId(), cd);
		}
		for (String filterID : filterList) {
			CategoryDescriptor cd = map.get(filterID);
			if (cd != null)
				categoryList.remove(cd);
		}
		return categoryList;
	}

	/**
	 * 得到当前studio中支持的数据库名称--id对应的map,不包括通用的数据库类型，即Generic JDBC Driver.
	 *
	 * @author mqfdy
	 * @return the database type name ID map
	 * @Date 2018-09-03 09:00
	 */
	public Map<String, String> getDatabaseTypeNameIDMap() {
		List<String> filterList = new ArrayList<String>();
		filterList.add(genericCategoryID);
		List<CategoryDescriptor> list = getDatabaseDescriptorList(
				databaseCategoryID, filterList);
		Map<String, String> map = new HashMap<String, String>();
		for (CategoryDescriptor cd : list) {
			map.put(cd.getName(), cd.getId());
		}
		return map;
	}

	/**
	 * 得到当前studio中支持的数据库类型名称数组.
	 *
	 * @author mqfdy
	 * @return the database type name array
	 * @Date 2018-09-03 09:00
	 */
	public String[] getDatabaseTypeNameArray() {
		return (String[]) getDatabaseTypeNameIDMap().keySet().toArray(
				new String[] {});
	}

	/**
	 * 根据数据库类型id得到该数据库的版本名称字符串数组.
	 *
	 * @author mqfdy
	 * @param databaseTypeID
	 *            数据库类型id 可以通过#getDatabaseTypeNameIDMap方法由数据库名称得到
	 * @return the database version array by type ID
	 * @Date 2018-09-03 09:00
	 */
	public String[] getDatabaseVersionArrayByTypeID(String databaseTypeID) {
		return ((String[]) (getDatabaseVersionNameIDMap(databaseTypeID)
				.keySet()).toArray(new String[] {}));
	}

	/**
	 * 根据数据库类型ID获得该类型下包含的数据库版本名称--ID对应的map.
	 *
	 * @author mqfdy
	 * @param databaseTypeID
	 *            the database type ID
	 * @return the database version name ID map
	 * @Date 2018-09-03 09:00
	 */
	public Map<String, String> getDatabaseVersionNameIDMap(String databaseTypeID) {
		Map<String, String> map = new HashMap<String, String>();
		for (CategoryDescriptor cd : getDatabaseDescriptorList(databaseCategoryID)) {
			map.put(cd.getName(), cd.getId());
		}
		return map;
	}

	/**
	 * 根据数据库名称得到该数据库的版本名称的字符串数组
	 * 
	 * @param databaseType
	 *            数据库名称，例如SQL Server
	 * @return 该数据库支持的版本名称，例如{"2000","2005"}
	 */
	public String[] getDatabaseVersionArrayByName(String databaseType) {
		return getDatabaseVersionArrayByTypeID(getDatabaseTypeNameIDMap().get(
				databaseType));
	}

	private void createDirverInstance(TemplateDescriptor td) {
		DriverInstance di = DriverManager.getInstance()
				.getDriverInstanceByName(td.getName());
		if (di != null)
			return;
		String propIdPrefix = DriverMgmtMessages
				.getString("EditDriverDialog.text.id_prefix"); //$NON-NLS-1$
		String mDriverName = td.getName();
		String propId = propIdPrefix + mDriverName;

		IPropertySet mPropertySet = new PropertySetImpl(propId, mDriverName);
		mPropertySet.setName(mDriverName);
		Properties props = new Properties();
		String jarList = DriverManager.getInstance().updatePluginJarList(td);
		props.setProperty(IDriverMgmtConstants.PROP_DEFN_JARLIST, jarList);
		IConfigurationElement[] templateprops = td.getProperties();
		if (templateprops != null && templateprops.length > 0) {
			for (int i = 0; i < templateprops.length; i++) {
				IConfigurationElement prop = templateprops[i];
				String id = prop.getAttribute("id"); //$NON-NLS-1$

				String value = prop.getAttribute("value"); //$NON-NLS-1$
				OverrideTemplateDescriptor[] otds = OverrideTemplateDescriptor
						.getByDriverTemplate(td.getId());
				if (otds != null && otds.length > 0) {
					boolean removetemp = otds[0]
							.getPropertyRemoveFlagFromID(id);
					if (removetemp)
						continue;
					String valuetemp = otds[0].getPropertyValueFromId(id);
					if (valuetemp != null && valuetemp.length() > 0)
						value = valuetemp;
				}
				props.setProperty(id, value == null ? new String() : value);
			}
		}
		props.setProperty(IDriverMgmtConstants.PROP_DEFN_TYPE, td.getId());
		mPropertySet.setBaseProperties(props);
		DriverManager.getInstance().addDriverInstance(mPropertySet);

	}
}
