package com.mqfdy.code.utils;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
/**
 * 
 * @author mqfdy
 *
 */
public class ProjectUtil {
	
	
	private static final String BOM_PROJECT = "com.mqfdy.bom.project.nature";
	private static final String SPRING_PROJECT = "com.mqfdy.code.springboot.nature";
	/**
	 * 判断当前项目是否微BOM项目
	 * @param project
	 * @return
	 */
	public static boolean isBOMProject(IProject project){
		
		try {
			return project != null && (project.hasNature(BOM_PROJECT) || 
					(project.getDescription() != null && 
					project.getDescription().getComment().equals(BOM_PROJECT)));
		} catch (CoreException e) {
			e.printStackTrace();
		}
		return false;
	}
	/**
	 * 判断当前项目是否BOM项目
	 * @param projectName
	 * @return
	 */
	public static boolean isBOMProject(String projectName){
		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
		if(project != null){
			return isBOMProject(project);
		}
		return false;
	}
	/**
	 * 判断当前项目是否为springboot项目
	 * @param project
	 * @return
	 */
	public static boolean isSpringbootProject(IProject project){
		
		try {
			return project != null && (project.hasNature(SPRING_PROJECT) || 
					(project.getDescription() != null && 
					project.getDescription().getComment().equals(SPRING_PROJECT)));
		} catch (CoreException e) {
			e.printStackTrace();
		}
		return false;
	}
	/**
	 * 判断当前项目是否springboot项目
	 * @param projectName
	 * @return
	 */
	public static boolean isSpringbootProject(String projectName){
		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
		if(project != null){
			return isBOMProject(project);
		}
		return false;
	}
	
}
