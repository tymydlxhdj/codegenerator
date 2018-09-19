package com.mqfdy.code.springboot.ui.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

// TODO: Auto-generated Javadoc
/**
 * 校验是否关键字 保留字(java、sql、sguap 三类)
 * 
 * @author ZHANGHE
 * 
 */
public class KeyWordsChecker {
	private static List<String> javaKeyList;
	private static List<String> sqlKeyList;
	private static List<String> sguapKeyList;

	static {
		javaKeyList = getKeyList("javaKeyWords.txt");
		sqlKeyList = getKeyList("sqlKeyWords.txt");
		sguapKeyList = getKeyList("sguapKeyWords.txt");
	}

	/**
	 * 获取关键字列表
	 * 
	 * @param keyFilePath
	 *            文件名称
	 * @return
	 */
	private static List<String> getKeyList(String keyFileName) {
		List<String> keyList = new ArrayList<String>();
		try {
			Bundle bundle = FrameworkUtil.getBundle(KeyWordsChecker.class);
			Enumeration<URL> urlEnum = bundle.findEntries("valiText",
					keyFileName, false);
			if (urlEnum.hasMoreElements()) {
				URL url = (URL) urlEnum.nextElement();
				InputStream is = url.openStream();
				String content = "";
				BufferedReader sguapbuffer = new BufferedReader(
						new InputStreamReader(is));
				String keyTemp;
				while (null != (keyTemp = sguapbuffer.readLine())) {
					content += keyTemp;
				}
				String[] keys = content.split(",");

				for (String key : keys) {
					keyList.add(key.trim().toLowerCase(Locale.getDefault()));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return keyList;
	}

	/**
	 * Do check sql.
	 *
	 * @author mqfdy
	 * @param key
	 *            the key
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	public static boolean doCheckSql(String key) {
		return sqlKeyList.contains(key.toLowerCase(Locale.getDefault()));
	}

	/**
	 * Do check sguap.
	 *
	 * @author mqfdy
	 * @param key
	 *            the key
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	public static boolean doCheckSguap(String key) {
		return sguapKeyList.contains(key.toLowerCase(Locale.getDefault()));
	}

	/**
	 * Do check java.
	 *
	 * @author mqfdy
	 * @param key
	 *            the key
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	public static boolean doCheckJava(String key) {
		return javaKeyList.contains(key.toLowerCase(Locale.getDefault()));
	}
}
