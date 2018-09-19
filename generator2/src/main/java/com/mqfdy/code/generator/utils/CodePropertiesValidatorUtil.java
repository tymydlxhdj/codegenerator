package com.mqfdy.code.generator.utils;

import com.mqfdy.code.resource.validator.ValidatorUtil;
// TODO: Auto-generated Javadoc

/**
 * 代码文件属性校验工具类.
 *
 * @author mqfdy
 */
public class CodePropertiesValidatorUtil {
	
	/**
	 * Instantiates a new code properties validator util.
	 */
	private CodePropertiesValidatorUtil(){
		
	}
	
	/**
	 * 校验包名.
	 *
	 * @author mqfdy
	 * @param packageName
	 *            the package name
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	public static boolean validatePackageName(String packageName) {
		packageName = packageName.replace(";", "").replace("\n", "");
		return ValidatorUtil.validatePackageName(packageName);
	}

}
