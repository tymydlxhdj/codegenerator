package com.mqfdy.code.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.eclipse.core.resources.IProject;

import com.mqfdy.code.model.utils.StringUtil;


// TODO: Auto-generated Javadoc
/**
 * 读取微服务的所有属性文件.
 *
 * @author mqfdy
 */
public class MicroserviceProjectPropertiesUtil {
	
	/** prop. */
	private Properties prop;
	
	/** path. */
	private String path;
	
	/** DB_DRIVER. */
	private final static String DB_DRIVER = "spring.datasource.driver-class-name";
	
	/**
	 * Instantiates a new microservice project properties util.
	 *
	 * @param project project
	 */
	public MicroserviceProjectPropertiesUtil(IProject project){
		this.path = project.getFullPath().toOSString();
		init();
	}
	
	/**
	 * 方法描述：init.
	 *
	 * @author mqfdy
	 * @Date 2018年8月31日 下午2:39:42
	 */
	public void init(){
		prop = new Properties();
		InputStream fis = null;
		File file = new File(path + File.separator + "src" + File.separator
				+ "main" + File.separator + "resources" + File.separator + "application.properties");
		try {
			if(file.exists()){
				fis = new FileInputStream(file);
				prop.load(fis);
			}
			
		} catch (IOException e1) {
			e1.printStackTrace();
		} finally{
			if(fis!=null){
				try{
					fis.close();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 方法描述：getProperty.
	 *
	 * @author mqfdy
	 * @param key key
	 * @return String实例
	 * @Date 2018年8月31日 下午2:39:47
	 */
	public String getProperty(String key) {
		return prop.getProperty(key);
	}
	
	/**
	 * 方法描述：getDbType.
	 *
	 * @author mqfdy
	 * @return String实例
	 * @Date 2018年8月31日 下午2:39:54
	 */
	public String getDbType(){
		String dbType = DBType.MySQL.getDbType();
		String dbDriver = prop.getProperty(DB_DRIVER);
		if(!StringUtil.isEmpty(dbDriver)){
			if(dbDriver.contains("mysql")){
				dbType = DBType.MySQL.getDbType();
			} else if(dbDriver.contains("oracle")){
				dbType = DBType.Oracle.getDbType();
			} else if(dbDriver.contains("postgresql")){
				dbType = DBType.POSTGRESQL.getDbType();
			} 
		}
		return dbType;
	}
	


}
