package com.mqfdy.code.resource.validator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;


/**
 * 校验是否关键字 保留字(java、sql、sguap 三类)
 * 
 * @author mqfdy
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
		InputStream is = null;
		BufferedReader sguapbuffer = null;
		Reader r = null;
		try {
			Bundle bundle = FrameworkUtil.getBundle(KeyWordsChecker.class);
			Enumeration<URL> urlEnum = bundle.findEntries("valiText",
					keyFileName, false);
			if (urlEnum.hasMoreElements()) {
				URL url = (URL) urlEnum.nextElement();
				is = url.openStream();
				String content = "";
				r = new InputStreamReader(is);
				sguapbuffer = new BufferedReader(r);
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
		} finally {
			if(is != null){
				try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(r != null){
				try {
					r.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(sguapbuffer != null){
				try {
					sguapbuffer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return keyList;
	}

	public static boolean doCheckSql(String key) {
		return sqlKeyList.contains(key.toLowerCase(Locale.getDefault()));
	}

	public static boolean doCheckSguap(String key) {
		return sguapKeyList.contains(key.toLowerCase(Locale.getDefault()));
	}

	public static boolean doCheckJava(String key) {
		return javaKeyList.contains(key.toLowerCase(Locale.getDefault()));
	}
}
