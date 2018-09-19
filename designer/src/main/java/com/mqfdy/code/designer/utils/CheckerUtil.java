package com.mqfdy.code.designer.utils;

import com.mqfdy.code.designer.models.BusinessModelManager;
import com.mqfdy.code.resource.validator.KeyWordsChecker;

// TODO: Auto-generated Javadoc
/**
 * 校验工具类.
 *
 * @author mqfdy
 */
public class CheckerUtil {
	
	/**
	 * Check java string.
	 *
	 * @author mqfdy
	 * @param name
	 *            the name
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	public static boolean checkJavaString(String name) {
		if("java".equalsIgnoreCase(name)){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * Check count string.
	 *
	 * @author mqfdy
	 * @param name
	 *            the name
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	public static boolean checkCountString(String name) {
		if("count".equalsIgnoreCase(name)){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * Check java.
	 *
	 * @author mqfdy
	 * @param name
	 *            the name
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	public static boolean checkJava(String name) {
		return KeyWordsChecker.doCheckJava(name);
	}

	/**
	 * Check sql.
	 *
	 * @author mqfdy
	 * @param name
	 *            the name
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	public static boolean checkSql(String name) {
		return KeyWordsChecker.doCheckSql(name);
	}

	/**
	 * Check sguap.
	 *
	 * @author mqfdy
	 * @param name
	 *            the name
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	public static boolean checkSguap(String name) {
		return KeyWordsChecker.doCheckSguap(name);
	}

	/**
	 * Check is exist.
	 *
	 * @author mqfdy
	 * @param name
	 *            the name
	 * @param modelElementType
	 *            the model element type
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	public static boolean checkIsExist(String name, String modelElementType) {
		BusinessModelManager manager = BusinessModelUtil
				.getEditorBusinessModelManager();
		return manager.isNameExist(name, modelElementType);
	}
}
