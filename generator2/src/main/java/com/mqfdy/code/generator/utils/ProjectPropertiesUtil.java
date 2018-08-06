package com.mqfdy.code.generator.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.eclipse.core.resources.IProject;

import com.mqfdy.code.datasource.mapping.DBType;
import com.mqfdy.code.model.utils.StringUtil;

/**
 * 读取属性文件
 * @author mqfdy
 *
 */
public class ProjectPropertiesUtil {
	
	private Properties prop;
	private String path;
	
	private final static String DB_DRIVER = "spring.datasource.driver-class-name";
	public ProjectPropertiesUtil(IProject project){
		this.path = project.getFullPath().toOSString();
		init();
	}
	
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
	 * 
	 * @param key
	 * @return
	 */
	public String getProperty(String key) {
		return prop.getProperty(key);
	}
	
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
