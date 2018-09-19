package com.mqfdy.code.datasource.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;


// TODO: Auto-generated Javadoc
/**
 * Properties文件操作类.
 *
 * @author mqfdy
 */
public class PropertiesUtil {

	/**
	 * 保存属性（追加 如果重复则覆盖）.
	 *
	 * @author mqfdy
	 * @param enumFile
	 *            the enum file
	 * @param map
	 *            the map
	 * @throws FileNotFoundException
	 *             the file not found exception
	 * @Date 2018-09-03 09:00
	 */
	public static void save(File enumFile,Map<String,String> map) throws FileNotFoundException {
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream(enumFile);
		OutputStream os = null;
		try {
			prop.load(fis);
			prop.putAll(map);

			Set keySet = prop.keySet();
			for (Iterator ite = keySet.iterator(); ite.hasNext();) {
				String key = (String) ite.next();
				String value = prop.getProperty(key);
			}

			os = new FileOutputStream(enumFile);
			prop.store(os, null);
		} catch (IOException e1) {
			e1.printStackTrace();
		} finally{
			if(os!=null){
				try{
					os.flush();
				}catch(Exception e){
					e.printStackTrace();
				}
				try{
					os.close();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			if(fis!=null){
				try{
					fis.close();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
	}
}
