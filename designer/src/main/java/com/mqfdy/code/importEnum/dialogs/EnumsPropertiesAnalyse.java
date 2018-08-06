package com.mqfdy.code.importEnum.dialogs;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;

import com.mqfdy.code.designer.editor.utils.Logger;

public class EnumsPropertiesAnalyse {
	private Properties properties = new Properties();
	private IFile file;
	private List<Object> keyList = new ArrayList<Object>();

	private static String PLUGIN_NATURE_ID = "com.sgcc.uap.ide.project.pluginnature";//"com.sgcc.uap.ide.dm.project.pluginnature";

	public EnumsPropertiesAnalyse(IProject genProject) {
		try {
			if (genProject.getNature(PLUGIN_NATURE_ID) != null) {
				file = genProject.getFile("config/enum/enums.properties");
				if(null != file){
					properties.load(file.getContents());
				}
			}
			
		} catch (CoreException e) {
			Logger.log(e);
		} catch (IOException e) {
			Logger.log(e);
		}

	}

	/**
	 * 获取Properties中的Key值
	 * 
	 * @return List
	 */
	public List<Object> getKeys() {
		for (Object o : properties.keySet()) {
			keyList.add(o);
		}
		return keyList;
	}

	/**
	 * 根据Properties中的Key值取出Value
	 * 
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public String getValues(String key) throws FileNotFoundException,
			IOException {
		String value=properties.get(key).toString();
		return value;
	}

}
