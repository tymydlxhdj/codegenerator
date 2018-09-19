package com.mqfdy.code.generator.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.eclipse.core.resources.IProject;

import com.mqfdy.code.datasource.mapping.DBType;
import com.mqfdy.code.model.utils.StringUtil;

// TODO: Auto-generated Javadoc
/**
 * 读取属性文件.
 *
 * @author mqfdy
 */
public class ProjectPropertiesUtil {
	
	/** The prop. */
	private Properties prop;
	
	/** The path. */
	private String path;
	
	/** The Constant DB_DRIVER. */
	private final static String DB_DRIVER = "spring.datasource.driver-class-name";
	
	/**
	 * Instantiates a new project properties util.
	 *
	 * @param project
	 *            the project
	 */
	public ProjectPropertiesUtil(IProject project){
		this.path = project.getFullPath().toOSString();
		init();
	}
	
	/**
	 * Inits the.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
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
	 * Gets the property.
	 *
	 * @author mqfdy
	 * @param key
	 *            the key
	 * @return the property
	 * @Date 2018-09-03 09:00
	 */
	public String getProperty(String key) {
		return prop.getProperty(key);
	}
	
	/**
	 * Gets the db type.
	 *
	 * @author mqfdy
	 * @return the db type
	 * @Date 2018-09-03 09:00
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
