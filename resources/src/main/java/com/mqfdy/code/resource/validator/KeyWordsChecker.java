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


// TODO: Auto-generated Javadoc
/**
 * 校验是否关键字 保留字(java、sql、sguap 三类).
 *
 * @author mqfdy
 */
public class KeyWordsChecker {
	
	/** The java key list. */
	private static List<String> javaKeyList;
	
	/** The sql key list. */
	private static List<String> sqlKeyList;
	
	/** The sguap key list. */
	private static List<String> sguapKeyList;

	static {
		javaKeyList = getKeyList("javaKeyWords.txt");
		sqlKeyList = getKeyList("sqlKeyWords.txt");
		sguapKeyList = getKeyList("sguapKeyWords.txt");
	}

	/**
	 * 获取关键字列表.
	 *
	 * @author mqfdy
	 * @param keyFileName
	 *            the key file name
	 * @return the key list
	 * @Date 2018-09-03 09:00
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
