package com.mqfdy.code.springboot.core.util;

// TODO: Auto-generated Javadoc
/**
 * The Class OsUtils.
 *
 * @author lenovo
 */
public class OsUtils {

	/**
	 * Checks if is windows.
	 *
	 * @author mqfdy
	 * @return true, if is windows
	 * @Date 2018-09-03 09:00
	 */
	public static boolean isWindows() {
		return System.getProperty("os.name").contains("Windows");
	}

	/**
	 * Gets the user home.
	 *
	 * @author mqfdy
	 * @return the user home
	 * @Date 2018-09-03 09:00
	 */
	public static String getUserHome(){
		return System.getProperty("user.home");
	}
}
