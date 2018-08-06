package com.mqfdy.code.springboot.core.util;

/**
 * @author lenovo
 */
public class OsUtils {

	public static boolean isWindows() {
		return System.getProperty("os.name").contains("Windows");
	}

	public static String getUserHome(){
		return System.getProperty("user.home");
	}
}
