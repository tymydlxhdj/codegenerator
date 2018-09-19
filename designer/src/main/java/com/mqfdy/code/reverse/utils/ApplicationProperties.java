package com.mqfdy.code.reverse.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

// TODO: Auto-generated Javadoc
/**
 * The Class ApplicationProperties.
 *
 * @author mqfdy
 */
public class ApplicationProperties {
	
	/** The Constant logger. */
	private static final Logger logger = Logger.getLogger(ApplicationProperties.class);
	
	/** The props. */
	private Properties props;

	/**
	 * Instantiates a new application properties.
	 *
	 * @param projectPath
	 *            the project path
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public ApplicationProperties(String projectPath) throws IOException{
		InputStream is = null;
		try {
			is = new FileInputStream(projectPath +"/src/main/resources/application.properties");
			props = new Properties();
			props.load(is);
		} catch (IOException e) {
			logger.error("加载配置文件application.properties失败", e);
			throw e;
		}finally{
			if(is != null){
				is.close();
			}
		}
		
	}
	
	/**
	 * 获取属性值.
	 *
	 * @author mqfdy
	 * @param key
	 *            the key
	 * @return the property
	 * @Date 2018-09-03 09:00
	 */
	public String getProperty(String key){
		return props.getProperty(key);
	}

}
