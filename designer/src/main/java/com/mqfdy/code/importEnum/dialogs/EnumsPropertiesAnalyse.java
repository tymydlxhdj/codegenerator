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

// TODO: Auto-generated Javadoc
/**
 * The Class EnumsPropertiesAnalyse.
 *
 * @author mqfdy
 */
public class EnumsPropertiesAnalyse {
	
	/** The properties. */
	private Properties properties = new Properties();
	
	/** The file. */
	private IFile file;
	
	/** The key list. */
	private List<Object> keyList = new ArrayList<Object>();

	/** The plugin nature id. */
	private static String PLUGIN_NATURE_ID = "com.sgcc.uap.ide.project.pluginnature";//"com.sgcc.uap.ide.dm.project.pluginnature";

	/**
	 * Instantiates a new enums properties analyse.
	 *
	 * @param genProject
	 *            the gen project
	 */
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
	 * 获取Properties中的Key值.
	 *
	 * @author mqfdy
	 * @return List
	 * @Date 2018-09-03 09:00
	 */
	public List<Object> getKeys() {
		for (Object o : properties.keySet()) {
			keyList.add(o);
		}
		return keyList;
	}

	/**
	 * 根据Properties中的Key值取出Value.
	 *
	 * @author mqfdy
	 * @param key
	 *            the key
	 * @return the values
	 * @throws FileNotFoundException
	 *             the file not found exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @Date 2018-09-03 09:00
	 */
	public String getValues(String key) throws FileNotFoundException,
			IOException {
		String value=properties.get(key).toString();
		return value;
	}

}
