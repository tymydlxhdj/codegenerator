package com.mqfdy.bom.project.util;

/**
 * @author mqfdy
 */
public class OsUtils {

	public static boolean isWindows() {
		return System.getProperty("os.name").contains("Windows");
	}

	public static String getUserHome(){
		return System.getProperty("user.home");
	}
}
