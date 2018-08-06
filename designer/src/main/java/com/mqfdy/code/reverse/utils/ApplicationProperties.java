package com.mqfdy.code.reverse.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class ApplicationProperties {
	
	private static final Logger logger = Logger.getLogger(ApplicationProperties.class);
	
	private Properties props;

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
	 * 获取属性值
	 * @param key
	 * @return
	 */
	public String getProperty(String key){
		return props.getProperty(key);
	}

}
