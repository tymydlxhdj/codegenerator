package com.mqfdy.code.utils;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
// TODO: Auto-generated Javadoc

/**
 * The Class ProjectUtil.
 *
 * @author mqfdy
 */
public class ProjectUtil {
	
	
	/** The Constant BOM_PROJECT. */
	private static final String BOM_PROJECT = "com.mqfdy.bom.project.nature";
	
	/** The Constant SPRING_PROJECT. */
	private static final String SPRING_PROJECT = "com.mqfdy.code.springboot.nature";
	
	/** The Constant SPRING_PROJECT. */
	private static final String SPRING_PROJECT2 = "org.springframework.ide.eclipse.core.springnature";
	
	/**
	 * 判断当前项目是否微BOM项目.
	 *
	 * @author mqfdy
	 * @param project
	 *            the project
	 * @return true, if is BOM project
	 * @Date 2018-09-03 09:00
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
	 * 判断当前项目是否BOM项目.
	 *
	 * @author mqfdy
	 * @param projectName
	 *            the project name
	 * @return true, if is BOM project
	 * @Date 2018-09-03 09:00
	 */
	public static boolean isBOMProject(String projectName){
		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
		if(project != null){
			return isBOMProject(project);
		}
		return false;
	}
	
	/**
	 * 判断当前项目是否为springboot项目.
	 *
	 * @author mqfdy
	 * @param project
	 *            the project
	 * @return true, if is springboot project
	 * @Date 2018-09-03 09:00
	 */
	public static boolean isSpringbootProject(IProject project){
		
		try {
			return project != null && (project.hasNature(SPRING_PROJECT) || project.hasNature(SPRING_PROJECT2)|| 
					(project.getDescription() != null && 
					project.getDescription().getComment().equals(SPRING_PROJECT)));
		} catch (CoreException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 判断当前项目是否springboot项目.
	 *
	 * @author mqfdy
	 * @param projectName
	 *            the project name
	 * @return true, if is springboot project
	 * @Date 2018-09-03 09:00
	 */
	public static boolean isSpringbootProject(String projectName){
		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
		if(project != null){
			return isBOMProject(project);
		}
		return false;
	}
	
}
