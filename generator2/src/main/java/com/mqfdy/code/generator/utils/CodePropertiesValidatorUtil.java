package com.mqfdy.code.generator.utils;

import com.mqfdy.code.resource.validator.ValidatorUtil;
/**
 * 代码文件属性校验工具类
 * @author mqfdy
 *
 */
public class CodePropertiesValidatorUtil {
	
	private CodePropertiesValidatorUtil(){
		
	}
	
	/**
	 * 校验包名
	 * 
	 * @param packageName
	 * @return
	 */
	public static boolean validatePackageName(String packageName) {
		packageName = packageName.replace(";", "").replace("\n", "");
		return ValidatorUtil.validatePackageName(packageName);
	}

}
