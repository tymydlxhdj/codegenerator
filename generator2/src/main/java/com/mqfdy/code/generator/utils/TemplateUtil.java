package com.mqfdy.code.generator.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.mqfdy.code.generator.GeneratorPlugin;
import com.mqfdy.code.utils.PluginUtil;


public class TemplateUtil {
	
	/**
	 * Gets the template types.
	 * 获取模板类型列表
	 *
	 * @author Administrator
	 * @return the template types
	 * @Date 2019-12-4 13:59:13
	 */
	public static List<String> getTemplateTypes(){
		List<String> list = new ArrayList<String>();
		String scencePluginPath;
		try {
			scencePluginPath = PluginUtil
					.getPluginOSPath(GeneratorPlugin.PLUGIN_ID);
			File templateDir = new File(scencePluginPath + File.separator + "template");
			if(templateDir.exists()){
				File[] files = templateDir.listFiles();
				if(files != null){
					for (File file : files) {
						if(file.isDirectory()){
							String fileName = file.getName();
							if("web".equalsIgnoreCase(fileName)){
								continue;
							} else if("controller".equalsIgnoreCase(fileName)){
								continue;
							}else if("domain".equalsIgnoreCase(fileName)){
								continue;
							}else if("services".equalsIgnoreCase(fileName)){
								continue;
							}else if("repositories".equalsIgnoreCase(fileName)){
								continue;
							}else if("validator".equalsIgnoreCase(fileName)){
								continue;
							}else if("vo".equalsIgnoreCase(fileName)){
								continue;
							}/*else if("mybatis".equalsIgnoreCase(fileName)){
								continue;
							}*/
							list.add(fileName);
						}
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return list;
	}

}
