package com.mqfdy.code.reverse;

// TODO: Auto-generated Javadoc
/**
 * The Class DataSourceInfo.
 *
 * @author mqfdy
 */
public class DataSourceInfo {

	/** The data source name. */
	private String dataSourceName;// 数据源名称
	
	/** The uap name. */
	private String uapName;//UAP工程名
	
	/** The sid. */
	private String sid;
	
	/** The url. */
	private String url;// 数据库连接地址
	
	/** The user name. */
	private String userName;// 用户名
	
	/** The pwd. */
	private String pwd;// 密码
	
	/** The driver class. */
	private String driverClass;// 驱动类
	
	/** The driver url. */
	private String driverUrl;// 数据库驱动地址
	
	/** The db type. */
	private String dbType;// 数据库类型
	
	/** The is select. */
	private String isSelect = "0";//表示是否被默认选中（放在列表第一个）
	
	/** The ip. */
	private String ip;
	
	/** The port. */
	private String port;

	/**
	 * Gets the ip.
	 *
	 * @author mqfdy
	 * @return the ip
	 * @Date 2018-09-03 09:00
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * Sets the ip.
	 *
	 * @author mqfdy
	 * @param ip
	 *            the new ip
	 * @Date 2018-09-03 09:00
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}

	/**
	 * Gets the port.
	 *
	 * @author mqfdy
	 * @return the port
	 * @Date 2018-09-03 09:00
	 */
	public String getPort() {
		return port;
	}

	/**
	 * Sets the port.
	 *
	 * @author mqfdy
	 * @param port
	 *            the new port
	 * @Date 2018-09-03 09:00
	 */
	public void setPort(String port) {
		this.port = port;
	}

	/**
	 * Gets the checks if is select.
	 *
	 * @author mqfdy
	 * @return the checks if is select
	 * @Date 2018-09-03 09:00
	 */
	public String getIsSelect() {
		return isSelect;
	}

	/**
	 * Sets the checks if is select.
	 *
	 * @author mqfdy
	 * @param isSelect
	 *            the new checks if is select
	 * @Date 2018-09-03 09:00
	 */
	public void setIsSelect(String isSelect) {
		this.isSelect = isSelect;
	}

	/**
	 * Gets the db type.
	 *
	 * @author mqfdy
	 * @return the db type
	 * @Date 2018-09-03 09:00
	 */
	public String getDbType() {
		return dbType;
	}

	/**
	 * Sets the db type.
	 *
	 * @author mqfdy
	 * @param dbType
	 *            the new db type
	 * @Date 2018-09-03 09:00
	 */
	public void setDbType(String dbType) {
		this.dbType = dbType;
	}

	/**
	 * Gets the sid.
	 *
	 * @author mqfdy
	 * @return the sid
	 * @Date 2018-09-03 09:00
	 */
	public String getSid() {
		return sid;
	}

	/**
	 * Sets the sid.
	 *
	 * @author mqfdy
	 * @param sid
	 *            the new sid
	 * @Date 2018-09-03 09:00
	 */
	public void setSid(String sid) {
		this.sid = sid;
	}

	/**
	 * Gets the data source name.
	 *
	 * @author mqfdy
	 * @return the data source name
	 * @Date 2018-09-03 09:00
	 */
	public String getDataSourceName() {
		return dataSourceName;
	}

	/**
	 * Sets the data source name.
	 *
	 * @author mqfdy
	 * @param dataSourceName
	 *            the new data source name
	 * @Date 2018-09-03 09:00
	 */
	public void setDataSourceName(String dataSourceName) {
		this.dataSourceName = dataSourceName;
	}

	/**
	 * Gets the url.
	 *
	 * @author mqfdy
	 * @return the url
	 * @Date 2018-09-03 09:00
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * Sets the url.
	 *
	 * @author mqfdy
	 * @param url
	 *            the new url
	 * @Date 2018-09-03 09:00
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * Gets the user name.
	 *
	 * @author mqfdy
	 * @return the user name
	 * @Date 2018-09-03 09:00
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * Sets the user name.
	 *
	 * @author mqfdy
	 * @param userName
	 *            the new user name
	 * @Date 2018-09-03 09:00
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * Gets the pwd.
	 *
	 * @author mqfdy
	 * @return the pwd
	 * @Date 2018-09-03 09:00
	 */
	public String getPwd() {
		return pwd;
	}

	/**
	 * Sets the pwd.
	 *
	 * @author mqfdy
	 * @param pwd
	 *            the new pwd
	 * @Date 2018-09-03 09:00
	 */
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	/**
	 * Gets the driver class.
	 *
	 * @author mqfdy
	 * @return the driver class
	 * @Date 2018-09-03 09:00
	 */
	public String getDriverClass() {
		return driverClass;
	}

	/**
	 * Sets the driver class.
	 *
	 * @author mqfdy
	 * @param driverClass
	 *            the new driver class
	 * @Date 2018-09-03 09:00
	 */
	public void setDriverClass(String driverClass) {
		this.driverClass = driverClass;
	}

	/**
	 * Gets the driver url.
	 *
	 * @author mqfdy
	 * @return the driver url
	 * @Date 2018-09-03 09:00
	 */
	public String getDriverUrl() {
		return driverUrl;
	}

	/**
	 * Sets the driver url.
	 *
	 * @author mqfdy
	 * @param driverUrl
	 *            the new driver url
	 * @Date 2018-09-03 09:00
	 */
	public void setDriverUrl(String driverUrl) {
		this.driverUrl = driverUrl;
	}

	/**
	 * Gets the uap name.
	 *
	 * @author mqfdy
	 * @return the uap name
	 * @Date 2018-09-03 09:00
	 */
	public String getUapName() {
		return uapName;
	}

	/**
	 * Sets the uap name.
	 *
	 * @author mqfdy
	 * @param uapName
	 *            the new uap name
	 * @Date 2018-09-03 09:00
	 */
	public void setUapName(String uapName) {
		this.uapName = uapName;
	}
	
}
