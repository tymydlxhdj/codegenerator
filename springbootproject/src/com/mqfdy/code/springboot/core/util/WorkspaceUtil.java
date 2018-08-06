package com.mqfdy.code.springboot.core.util;

import java.io.File;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

/**
 * Utility methods related workspace and resources in the workspace.
 * 
 * @author lenovo
 */
public class WorkspaceUtil {

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

	public static IProject[] getProjects() {
		return ResourcesPlugin.getWorkspace().getRoot().getProjects();
	}

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
