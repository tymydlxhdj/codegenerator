package com.mqfdy.bom.project.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.eclipse.core.runtime.Platform;
import org.eclipse.osgi.service.datalocation.Location;

import com.mqfdy.bom.project.Activator;
import com.mqfdy.code.security.util.SecurityTestUtil;


/**
 * @author mqfdy
 */
public class ZipFileUtil {

	public static abstract class PermissionSetter {

		/**
		 * Called after a file was succesfully extracted from the zip archive.
		 * 
		 * @throws IOException
		 */
		public abstract void fileUnzipped(ZipEntry entry, File entryFile) throws IOException;

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
		 */
		public static PermissionSetter executableExtensions(final String... exts) {
			if (OsUtils.isWindows()) {
				// It may be ok to do nothing for windows? (note: not tested!)
				return NULL;
			} else {
				return new PermissionSetter() {
					@Override
					public void fileUnzipped(ZipEntry entry, File entryFile) throws IOException {
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
	 * 将模板项目解压拷贝到向导第一页中的LOCATION位置
	 */
	public static void unzip(URL source, File targetFile) throws IOException {
		unzip(source, targetFile, PermissionSetter.NULL);
	}

	/**
	 * 将模板项目解压拷贝到向导第一页中的LOCATION位置
	 * 
	 * @param source
	 * @param targetFile
	 *            F:\runtime-New_configuration\aa
	 * @param permsetter
	 * @throws IOException
	 */
	public static void unzip(URL source, File targetFile, PermissionSetter permsetter) {
		targetFile.mkdirs();

		byte[] buffer = new byte[BUFFER_SIZE];
		ZipInputStream zipIn = null;
		ZipEntry entry = null;
		FileOutputStream out = null;
		try {
			zipIn = new ZipInputStream(source.openStream());
			while ((entry = zipIn.getNextEntry()) != null) {
				String temp = entry.getName();
				String entryPath = targetFile.getAbsolutePath()+File.separator+temp;
				if(!SecurityTestUtil.validateFilePath(entryPath)){
					throw new RuntimeException("不安全路径："+temp);
				}
				File entryFile = new File(entryPath);
				if (entry.isDirectory()) {
					entryFile.mkdirs();
				} else {// 判断文件是是文件
					entryFile.getParentFile().mkdirs();
					out = new FileOutputStream(entryFile);
					int len;
					while ((len = zipIn.read(buffer)) >= 0) {
						out.write(buffer, 0, len);
					}
					long modTime = entry.getTime();
					if (modTime > 0) {
						entryFile.setLastModified(modTime);
					}
					permsetter.fileUnzipped(entry, entryFile);
					out.close();
				}
			}
		} catch (Exception e) {
			Activator.log(e);
		} finally {
			if (zipIn != null) {
				try {
					zipIn.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public static String getStudioInstallLocation() {
		final Location location = Platform.getInstallLocation();
		return new File(location.getURL().getPath()).getParent();
	}
}
