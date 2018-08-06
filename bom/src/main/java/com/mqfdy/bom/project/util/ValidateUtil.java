package com.mqfdy.bom.project.util;

import java.io.File;
import java.io.FilenameFilter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Platform;

public class ValidateUtil {
	
	//不能为数字
	public static boolean  isnumber(String str){
		Pattern pattern = Pattern.compile("[0-9]*"); 
		   Matcher isNum = pattern.matcher(str);
		   if( !isNum.matches() ){
		       return false; 
		   } 
		   return true; 
	}

	public static boolean isChineseCharacter(String chineseStr) {
		char[] charArray = chineseStr.toCharArray();
		for (int i = 0; i < charArray.length; i++) {
			if ((charArray[i] >= 0x4e00) && (charArray[i] <= 0x9fbb)) {
				// Java判断一个字符串是否有中文是利用Unicode编码来判断，
				// 因为中文的编码区间为：0x4e00--0x9fbb
				return true;
			}
		}
		return false;
	}
	//非法字符
	public static boolean isAllowedChar(char c) {
		return Character.isLetterOrDigit(c) 
			|| "-_.".indexOf(c)>=0;
	}
	
	public static boolean existsInWorkspace(String projectName) {
		IProject[] eclipseProjects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
		for (IProject p : eclipseProjects) {
			if (p.getName().equals(projectName)) {
				return true;
			}
		}
		return false;
	}
	
	private static final FilenameFilter IGNORE_SCM_META_DATA = new FilenameFilter() {
		public boolean accept(File dir, String name) {
			return !name.equals(".git");
		}
	};

	public static File[] listFiles(File file) {
		return file.listFiles(IGNORE_SCM_META_DATA);
	}

	public static boolean isEmptyDirectory(File file) {
		File[] files = listFiles(file);
		if (files!=null) {
			return files.length==0;
		}
		return false;
	}
	
	public static String getDefaultPathDisplayString(IProject parentProject) {
		if (parentProject != null) {
			return parentProject.getLocation().toOSString();
		} else {
			return Platform.getLocation().toOSString();
		}
	}

}
