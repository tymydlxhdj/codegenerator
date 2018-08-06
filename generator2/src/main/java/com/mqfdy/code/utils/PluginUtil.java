package com.mqfdy.code.utils;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Plugin;

/**
 * 定义和插件相关的工具类
 */
public class PluginUtil {
	/**
	 * 取得id为pluginID的插件的安装目录
	 * 
	 * @param pluginID
	 *            要得到安装目录的插件ID, not null
	 * @return 
	 *         插件安装目录的绝对路径，注意这个路径以"\"开头，例如"/F:/workspace/com.mqfdy.project/"
	 * @throws IOException
	 *             使用FileLocator进行URL转换的时候发生错误会抛出
	 */
	public static String getPluginPath(String pluginID) throws IOException {
		if (pluginID == null) {
			throw new IllegalArgumentException();
		}
		URL pluginInstalledUrl = Platform.getBundle(pluginID).getEntry(
				File.separator);

		pluginInstalledUrl = FileLocator.toFileURL(pluginInstalledUrl);
		return pluginInstalledUrl.getFile();

	}

	/**
	 * 取得id为pluginID的插件下相对路径为filePath的文件的url, filePath应该以“/”开头
	 */
	public static URL getPluginFileURL(String pluginID, String filePath)
			throws IOException {
		URL pluginInstalledUrl = Platform.getBundle(pluginID)
				.getEntry(filePath);
		return FileLocator.toFileURL(pluginInstalledUrl);

	}

	/**
	 * 取得id为pluginID的插件的安装目录
	 * 
	 * @param pluginID
	 *            要得到安装目录的插件ID, not null
	 * @return 插件安装目录的绝对路径，并且该路径完全符合操作系统的规范，例如
	 *         "F:\workspace\com.mqfdy.project\
	 *         "
	 * @throws IOException
	 *             使用FileLocator进行URL转换的时候发生错误会抛出
	 */
	public static String getPluginOSPath(String pluginID) throws IOException {
		return Path.fromOSString(getPluginPath(pluginID)).toOSString();
	}

	/**
	 * 取得id为pluginID的插件的安装目录
	 * 
	 * @param plugin
	 *            要得到安装目录的插件对象
	 * @return 插件安装目录的绝对路径
	 * @throws IOException
	 *             使用FileLocator进行URL转换的时候发生错误会抛出
	 */
	public static String getPluginPath(Plugin plugin) throws IOException {
		return getPluginPath(plugin.getBundle().getSymbolicName());
	}

	/**
	 * 得到插件安装目录下的某一个目录的路径
	 * 
	 * @param pluginID
	 *            目标插件ID, not null
	 * @param filePath
	 *            相对于插件的文件路径, not null
	 * @return 文件的IPath路径
	 * @throws IOException
	 *             使用FileLocator进行URL转换的时候发生错误会抛出
	 */
	public static IPath filePathFromPlugin(String pluginID, String filePath)
			throws IOException {
		if (pluginID == null || filePath == null) {
			throw new IllegalArgumentException();
		}

		URL url = Platform.getBundle(pluginID).getEntry(
				File.separator + filePath + File.separator);

		url = FileLocator.toFileURL(url);

		return new Path(url.getFile());

	}
}
