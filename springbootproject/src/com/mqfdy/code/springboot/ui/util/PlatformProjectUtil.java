package com.mqfdy.code.springboot.ui.util;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.JavaCore;

/**
 * 项目工具类，负责添加nature等工作
 * 
 * @author mqfdy
 */
public class PlatformProjectUtil {

	
	/**
	 * Returns true if given resource is a Java project.
	 *
	 * @author mqfdy
	 * @param resource
	 *            the resource
	 * @return true, if is java project
	 * @Date 2018-09-03 09:00
	 */
	public static boolean isJavaProject(IResource resource) {
		return isProjectHasNature(resource, JavaCore.NATURE_ID);
	}

	
	private static boolean isProjectHasNature(IResource resource,
			String natureId) {
		if (resource == null)
			return false;
		
		IProject project = null;
		if (resource.getType() == IResource.PROJECT)
			project = (IProject) resource;
		else
			project = resource.getProject();
		if (project == null || !project.isAccessible())
			return false;
		try {
			return ((IProject) resource).hasNature(natureId);
		} catch (CoreException e) {
			// 不会到达
		}
		return false;
	}
}
