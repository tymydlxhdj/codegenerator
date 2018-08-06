package com.mqfdy.code.designer.utils;

import com.mqfdy.code.designer.models.BusinessModelManager;
import com.mqfdy.code.resource.validator.KeyWordsChecker;

/**
 * 校验工具类
 * 
 * @author mqfdy
 * 
 */
public class CheckerUtil {
	
	public static boolean checkJavaString(String name) {
		if("java".equalsIgnoreCase(name)){
			return true;
		}else{
			return false;
		}
	}
	public static boolean checkCountString(String name) {
		if("count".equalsIgnoreCase(name)){
			return true;
		}else{
			return false;
		}
	}
	public static boolean checkJava(String name) {
		return KeyWordsChecker.doCheckJava(name);
	}

	public static boolean checkSql(String name) {
		return KeyWordsChecker.doCheckSql(name);
	}

	public static boolean checkSguap(String name) {
		return KeyWordsChecker.doCheckSguap(name);
	}

	public static boolean checkIsExist(String name, String modelElementType) {
		BusinessModelManager manager = BusinessModelUtil
				.getEditorBusinessModelManager();
		return manager.isNameExist(name, modelElementType);
	}
}
