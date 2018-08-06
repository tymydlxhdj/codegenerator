package com.mqfdy.code.springboot.ui.util;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;

import com.mqfdy.code.springboot.core.MicroProject;
import com.mqfdy.code.springboot.core.MicroProjectNature;
import com.mqfdy.code.springboot.core.MicroProjectPlugin;


/**
 * Various utility methods for extracting information from UI selections. Goal for these methods is to factor as much as
 * possible the logic for extracting objects of certain types (as expected by operations operating on selections) so
 * that this logic doesn't get duplicated.
 * 
 * @author lenovo
 */
public class SelectionUtils {

	public static MicroProject getGradleProject(ISelection selection) {
		IProject project = getProject(selection);
		if (project!=null && MicroProjectNature.hasNature(project)) {
			return MicroProjectPlugin.getProjectManager().getOrCreate(project);
		}
		return null;
	}

	public static IProject getProject(ISelection selection) {
		IResource rsrc = getResource(selection);
		if (rsrc!=null) {
			return rsrc.getProject();
		}
		return null;
	}

	private static IResource getResource(ISelection selection) {
		if (selection instanceof IStructuredSelection) {
			Object firstEl = ((IStructuredSelection) selection).getFirstElement(); 
			if (firstEl!=null) {
				if (firstEl instanceof IResource) {
					return ((IResource) firstEl).getProject();
				} else if (firstEl instanceof IAdaptable) {
					return (IResource) ((IAdaptable) firstEl).getAdapter(IResource.class);
				}
			}
		}
		return null;
	}

}
