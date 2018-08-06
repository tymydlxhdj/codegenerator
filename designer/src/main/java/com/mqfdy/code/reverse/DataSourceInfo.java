package com.mqfdy.code.reverse;

public class DataSourceInfo {

	private String dataSourceName;// 数据源名称
	private String uapName;//UAP工程名
	private String sid;
	private String url;// 数据库连接地址
	private String userName;// 用户名
	private String pwd;// 密码
	private String driverClass;// 驱动类
	private String driverUrl;// 数据库驱动地址
	private String dbType;// 数据库类型
	private String isSelect = "0";//表示是否被默认选中（放在列表第一个）
	
	private String ip;
	private String port;

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getIsSelect() {
		return isSelect;
	}

	public void setIsSelect(String isSelect) {
		this.isSelect = isSelect;
	}

	public String getDbType() {
		return dbType;
	}

	public void setDbType(String dbType) {
		this.dbType = dbType;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public String getDataSourceName() {
		return dataSourceName;
	}

	public void setDataSourceName(String dataSourceName) {
		this.dataSourceName = dataSourceName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getDriverClass() {
		return driverClass;
	}

	public void setDriverClass(String driverClass) {
		this.driverClass = driverClass;
	}

	public String getDriverUrl() {
		return driverUrl;
	}

	public void setDriverUrl(String driverUrl) {
		this.driverUrl = driverUrl;
	}

	public String getUapName() {
		return uapName;
	}

	public void setUapName(String uapName) {
		this.uapName = uapName;
	}
	
}
