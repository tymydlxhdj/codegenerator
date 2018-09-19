package com.mqfdy.code.springboot.core.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Locale;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.eclipse.core.runtime.Platform;
import org.eclipse.osgi.service.datalocation.Location;
import org.jasypt.util.password.ConfigEncryptUtils;

import com.mqfdy.code.security.util.SecurityTestUtil;
import com.mqfdy.code.springboot.core.MicroProjectPlugin;
import com.mqfdy.code.springboot.core.generator.utils.StringUtils;
import com.mqfdy.code.springboot.dbs.ConnectionProfile;

// TODO: Auto-generated Javadoc
/**
 * The Class ZipFileUtil.
 *
 * @author zjing
 */
public class ZipFileUtil {
	
	private static final String APPLICATION_NAME = "spring.application.name=";
	private static final String DS_URL = "spring.datasource.url=";
	private static final String DS_URL_ = "#spring.datasource.url=";
	private static final String DS_URL_TIMEZONE = "?serverTimezone=GMT%2b8";
	private static final String DS_NAME = "spring.datasource.username=";
	private static final String DS_NAME_ = "#spring.datasource.username=";
	private static final String DS_PASS = "spring.datasource.password=";
	private static final String DS_PASS_ = "#spring.datasource.password=";
	private static final String DS_DRIVER = "spring.datasource.driver-class-name=";
	private static final String DS_DRIVER_ = "#spring.datasource.driver-class-name=";
	private static final String MYSQL_DRIVER_OLD = "com.mysql.jdbc.Driver";
	private static final String MYSQL_DRIVER_NEW = "com.mysql.cj.jdbc.Driver";
	/**
	 * 加密工具密码的key
	 */
	private static final String ENC_PWD = "jasypt.encryptor.password=";

	/**
	 * The Class PermissionSetter.
	 *
	 * @author mqfdy
	 */
	public static abstract class PermissionSetter {

		/**
		 * Called after a file was succesfully extracted from the zip archive.
		 *
		 * @author mqfdy
		 * @param entry
		 *            the entry
		 * @param entryFile
		 *            the entry file
		 * @throws IOException
		 *             Signals that an I/O exception has occurred.
		 * @Date 2018-09-03 09:00
		 */
		public abstract void fileUnzipped(ZipEntry entry, File entryFile)
				throws IOException;

		/**
		 * A permission setter that does nothing.
		 */
		public static final PermissionSetter NULL = new PermissionSetter() {
			@Override
			public void fileUnzipped(ZipEntry entry, File entryFile) {
				// Do nothing
			}
		};

		/**
		 * Creates an executable permission setter based on a list of file
		 * extensions.
		 * <p>
		 * Any file ending with the extension will be made executable.
		 *
		 * @author mqfdy
		 * @param exts
		 *            the exts
		 * @return the permission setter
		 * @Date 2018-09-03 09:00
		 */
		public static PermissionSetter executableExtensions(
				final String... exts) {
			if (OsUtils.isWindows()) {
				// It may be ok to do nothing for windows? (note: not tested!)
				return NULL;
			} else {
				return new PermissionSetter() {
					@Override
					public void fileUnzipped(ZipEntry entry, File entryFile)
							throws IOException {
						String name = entryFile.toString();
						for (String ext : exts) {
							if (name.endsWith(ext)) {
								// This only works with Java 6:
								entryFile.setExecutable(true);
							}
						}
					}
				};
			}
		}

	}

	private static final int BUFFER_SIZE = 1024 * 1024;


	/**
	 * 将模板项目解压拷贝到向导第一页中的LOCATION位置.
	 *
	 * @author mqfdy
	 * @param source
	 *            the source
	 * @param targetFile
	 *            the target file
	 * @param prefix
	 *            the prefix
	 * @param connectName
	 *            the connect name
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @Date 2018-09-03 09:00
	 */
	public static void unzip(URL source, File targetFile, String prefix,String connectName)
			throws IOException {
		unzip(source, targetFile, prefix, PermissionSetter.NULL,connectName);
	}

	
	/**
	 * 将模板项目解压拷贝到向导第一页中的LOCATION位置.
	 *
	 * @author mqfdy
	 * @param source
	 *            the source
	 * @param targetFile
	 *            F:\runtime-New_configuration\aa
	 * @param prefix
	 *            the prefix
	 * @param permsetter
	 *            the permsetter
	 * @param connectName
	 *            the connect name
	 * @Date 2018-09-03 09:00
	 */
	public static void unzip(URL source, File targetFile, String prefix,
			PermissionSetter permsetter,String connectName) {
		targetFile.mkdirs();
		ConnectionProfile cp = null;
		if(null != connectName){
			cp = MicroProjectPlugin.instance().getConnectionProfileRepository().connectionProfileNamed(connectName);
		}
		
		byte[] buffer = new byte[BUFFER_SIZE];
		ZipInputStream zipIn = null;
		FileOutputStream out = null;
		InputStreamReader isr = null;
		BufferedReader reader = null;
		InputStream is = null;
		try {
			is = source.openStream();
			zipIn = new ZipInputStream(is);
			ZipEntry entry;
			while ((entry = zipIn.getNextEntry()) != null) {
				
				String temp = entry.getName();
				String entryPath = targetFile.getAbsolutePath()+File.separator+temp;
				if(!SecurityTestUtil.validateFilePath(entryPath)){
					throw new RuntimeException("不安全路径："+temp);
				}
				File entryFile = new File(entryPath);
				if (entry.isDirectory()) {
					entryFile.mkdirs();
				} else {//判断文件是是文件
					
					entryFile.getParentFile().mkdirs();
					out = new FileOutputStream(entryFile);
					String entryName = entryFile.getName();
					
					if(isApplicationfile(entryName)){
						isr = new InputStreamReader(zipIn);
						reader = new BufferedReader(new InputStreamReader(zipIn));
						String str = null;
						String encPwd = null;
						while((str = reader.readLine()) != null) {
							if(str.contains(ENC_PWD)){
								encPwd = str.replace(ENC_PWD, "");
							}
							if (str.contains(APPLICATION_NAME)) {
								str = str.replace(APPLICATION_NAME, APPLICATION_NAME + targetFile.getName());
						    } 
							else if (null != cp && str.contains(DS_URL)) {
								String dsUrl = DS_URL;
								if(str.contains(DS_URL_)){
									dsUrl = DS_URL_;
								}
								if(cp.getDriverClassName().equals(MYSQL_DRIVER_OLD)){
									str = dsUrl + cp.getUrl() + DS_URL_TIMEZONE;
								}else{
									str = dsUrl + cp.getUrl();
								}
								
						    } 
							else if (null != cp && str.contains(DS_NAME)) {
								String dsName = DS_NAME;
								if(str.contains(DS_NAME_)){
									dsName = DS_NAME_;
								}
								str = str.replace(str, dsName + cp.getUserName());
						    } 
							else if (null != cp && str.contains(DS_PASS)) {
								String dsPass = DS_PASS;
								if(str.contains(DS_PASS_)){
									dsPass = DS_PASS_;
								}
								//用工具获取密码的密文
								String cipherText = ConfigEncryptUtils.getChiperText(cp.getUserPassword());
								if(!StringUtils.isEmpty(encPwd)){
									cipherText = ConfigEncryptUtils.getChiperText(cp.getUserPassword(),encPwd);
								}
								
								str = str.replace(str, dsPass + cipherText);
						    } 
							else if (null != cp && str.contains(DS_DRIVER)) {
								String dsDriver = DS_DRIVER;
								if(str.contains(DS_DRIVER_)){
									dsDriver = DS_DRIVER_;
								}
								if(cp.getDriverClassName().equals(MYSQL_DRIVER_OLD)){
									str = str.replace(str, dsDriver + MYSQL_DRIVER_NEW);
								}else{
									str = str.replace(str, dsDriver + cp.getDriverClassName());
								}
						    }
							str = str + "\n";
							out.write(str.getBytes());
						}
					} else {
						int len;
						while ((len = zipIn.read(buffer)) >= 0) {
							out.write(buffer,0,len);
						}
					}
					long modTime = entry.getTime();
					if (modTime > 0) {
						entryFile.setLastModified(modTime);
					}
					permsetter.fileUnzipped(entry, entryFile);
				}
			}
		}catch(Exception e){ 
			System.out.println(e.getMessage());
			MicroProjectPlugin.log(e);
		}finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if(zipIn != null){
				try {
					zipIn.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(reader != null){
				try {
					reader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(isr != null){
				try {
					isr.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(is != null){
				try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(out != null){
				try {
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * Gets the studio install location.
	 *
	 * @author mqfdy
	 * @return the studio install location
	 * @Date 2018-09-03 09:00
	 */
	public static String getStudioInstallLocation() {
		final Location location = Platform.getInstallLocation();
		return new File(location.getURL().getPath()).getParent();
	}
	
	
	private static boolean isApplicationfile(String name) {
		return name.toLowerCase(Locale.getDefault()).endsWith("application.properties");
	}
}
