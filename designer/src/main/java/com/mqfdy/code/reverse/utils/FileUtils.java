package com.mqfdy.code.reverse.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class FileUtils.
 *
 * @author mqfdy 文件工具类
 */
public class FileUtils {

	/**
	 * 当前目录下的所有子目录以及子文件中 是否含有'suffix'后缀文件.
	 *
	 * @author mqfdy
	 * @param file
	 *            the file
	 * @param suffix
	 *            the suffix
	 * @return true, if is checks for file with suffix
	 * @Date 2018-09-03 09:00
	 */
	public static boolean isHasFileWithSuffix(File file, String suffix) {
		if(file.isDirectory()) {//当前文件是一个目录
			//得到当前文件的子文件/目录
			File[] subFiles = file.listFiles();
			
			List<File> list = new ArrayList<File>();
			//遍历当前文件的子文件/目录
			for(File subFile: subFiles) {
				if(subFile.isDirectory()) {
					//如果当前文件是目录，就加入集合待分析。
					list.add(subFile);
				} else {
					//如果当前文件是一个带'suffix'后缀的文件就返回true
					int lastIndex = subFile.getName().lastIndexOf(".");
					String currentSuffix = subFile.getName().substring(lastIndex+1);
					if (suffix.equals(currentSuffix)) {
						return true;
					}
				}
			}
			
			for(File subDirectory: list) {
				if(isHasFileWithSuffix(subDirectory, suffix)) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * 得到当前目录下的带'suffix'后缀文件的目录集合和文件集合（只包含当前子目录）.
	 *
	 * @author mqfdy
	 * @param file
	 *            the file
	 * @param suffix
	 *            the suffix
	 * @return the files with suffix
	 * @Date 2018-09-03 09:00
	 */
	public static List<File> getFilesWithSuffix(File file, String suffix) {
		List<File> resultList = new ArrayList<File>();
		if(file.isDirectory()) {
			File[] subFiles = file.listFiles();
			
			for(File subFile: subFiles) {
				if(subFile.isDirectory() && isHasFileWithSuffix(subFile, suffix)) {
					resultList.add(subFile);
				} else {
					int lastIndex = subFile.getName().lastIndexOf(".");
					String currentSuffix = subFile.getName().substring(lastIndex+1);
					if (suffix.equals(currentSuffix)) {
						resultList.add(subFile);
					}
				}
			}
		}
		return resultList;
	}
	
	/**
	 * 得到当前目录下所有带有某一后缀的文件（含子目录的）.
	 *
	 * @author mqfdy
	 * @param file
	 *            当前的目录 File
	 * @param suffix
	 *            后缀名 String
	 * @return the all suffix files
	 * @Date 2018-09-03 09:00
	 */
	public static List<File> getAllSuffixFiles(File file, String suffix) {
		List<File> resultList = new ArrayList<File>();
		if(file.isDirectory()) {
			File[] subFiles = file.listFiles();
			
			for(File subFile: subFiles) {
				if(subFile.isDirectory()) {
					if(isHasFileWithSuffix(subFile, suffix)) {
						resultList.addAll(getAllSuffixFiles(subFile, suffix));
					}
				} else {
					String subFileName = subFile.getName();
					if(subFileName.substring(subFileName.lastIndexOf(".") + 1).equals(suffix)) {
						resultList.add(subFile);
					}
				}
			}
		}
		return resultList;
	}
	
	/**
	 * 得到当前目录下的不带'suffix'后缀文件的目录集合（只包含当前子目录）.
	 *
	 * @author mqfdy
	 * @param file
	 *            the file
	 * @param suffix
	 *            the suffix
	 * @return the files with no suffix
	 * @Date 2018-09-03 09:00
	 */
	public static List<File> getFilesWithNoSuffix(File file, String suffix) {
		List<File> resultList = new ArrayList<File>();
		if(file.isDirectory()) {
			File[] subFiles = file.listFiles();
			
			for(File subFile: subFiles) {
				if(subFile.isDirectory() && !isHasFileWithSuffix(subFile, suffix)) {
					resultList.add(subFile);
				}
			}
		}
		return resultList;
	}
	
	/**
	 * 通过文件得到子目录(不含文件).
	 *
	 * @author mqfdy
	 * @param file
	 *            the file
	 * @return the directory by file
	 * @Date 2018-09-03 09:00
	 */
	public static List<File> getDirectoryByFile(File file) {
		List<File> resultList = new ArrayList<File>();
		if(file.isDirectory()) {
			File[] subFiles = file.listFiles();
			for(File subFile: subFiles) {
				if(subFile.isDirectory()) {
					resultList.add(subFile);
				}
			}
		}
		return resultList;
	}
	
	/**
	 * 通过文件得到不以str开头的子目录(不含文件).
	 *
	 * @author mqfdy
	 * @param file
	 *            the file
	 * @param filterString
	 *            the filter string
	 * @return the directory by file
	 * @Date 2018-09-03 09:00
	 */
	public static List<File> getDirectoryByFile(File file, String filterString) {
		List<File> resultList = new ArrayList<File>();
		if(file.isDirectory()) {
			File[] subFiles = file.listFiles();
			for(File subFile: subFiles) {
				if(subFile.isDirectory() && subFile.getName().substring(0, 1).indexOf(filterString) == -1) {
					resultList.add(subFile);
				}
			}
		}
		return resultList;
	}
	
	/**
	 * 当前目录是否有子目录.
	 *
	 * @author mqfdy
	 * @param directory
	 *            the directory
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	public static boolean hasSubDirectorys(File directory) {
		
		File[] subDirectorys = directory.listFiles();
		if(subDirectorys.length != 0) {
			for(File subFile: subDirectorys) {
				if(subFile.isDirectory()) {
					return true;
				}
			}
		}
		return false;
	}
}
