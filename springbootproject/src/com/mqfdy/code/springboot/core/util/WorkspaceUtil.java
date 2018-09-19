package com.mqfdy.code.springboot.core.util;

import java.io.File;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

// TODO: Auto-generated Javadoc
/**
 * Utility methods related workspace and resources in the workspace.
 * 
 * @author lenovo
 */
public class WorkspaceUtil {

	/**
	 * Gets the containing project.
	 *
	 * @author mqfdy
	 * @param file
	 *            the file
	 * @return the containing project
	 * @Date 2018-09-03 09:00
	 */
	public static IProject getContainingProject(File file) {
		IPath path = new Path(file.getAbsolutePath());
		int longest = 0;
		IProject found = null;
		for (IProject p : getProjects()) {
			IPath loc = p.getLocation();
			if (loc!=null && loc.isPrefixOf(path)) {
				//Found a match
				if (loc.segmentCount()>longest) {
					//Only keep longest match
					found = p;
					longest = loc.segmentCount();
				}
			}
		}
		return found;
	}

	/**
	 * Gets the projects.
	 *
	 * @author mqfdy
	 * @return the projects
	 * @Date 2018-09-03 09:00
	 */
	public static IProject[] getProjects() {
		return ResourcesPlugin.getWorkspace().getRoot().getProjects();
	}

	/**
	 * Gets the project.
	 *
	 * @author mqfdy
	 * @param projectName
	 *            the project name
	 * @return the project
	 * @Date 2018-09-03 09:00
	 */
	public static IProject getProject(String projectName){
		if(projectName != null && !"".equals(projectName)) {
			for (IProject project : getProjects()) {
				if(project.getName().equalsIgnoreCase(projectName)){
					return project;
				}
			}
		}
		return null;
	}
	
}
