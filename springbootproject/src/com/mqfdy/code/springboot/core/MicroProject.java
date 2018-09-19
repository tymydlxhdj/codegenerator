package com.mqfdy.code.springboot.core;

import java.io.File;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;


// TODO: Auto-generated Javadoc
/**
 * The Class MicroProject.
 *
 * @author zjing
 */
public class MicroProject {
	
	
	/**
	 * Canonical File pointing to the root of this project's location in the file system.
	 * Never null.
	 */
	private File location;
	

	private IProject cachedProject;

	/**
	 * Instantiates a new micro project.
	 *
	 * @param canonicalFile
	 *            the canonical file
	 */
	public MicroProject(File canonicalFile) {
		Assert.isLegal(canonicalFile!=null, "Project location must not be null");
		Assert.isLegal(canonicalFile.exists(), "Project location doesn't exist: "+canonicalFile);
		Assert.isLegal(canonicalFile.isAbsolute(), "Project location must be absolute: "+canonicalFile);
		Assert.isLegal(canonicalFile.isDirectory(), "Project location must be a directory: "+canonicalFile);
		this.location = canonicalFile;
	}
	
	
	/**
	 * Return the "eclipse" name of this gradle project. That is the name of the project in the workspace
	 * that corresponds to this Gradle project. 
	 * <p>
	 * @return Name of an IProject that exists in the workpace, or null if this project was not imported into the workspace.
	 */
	public String getName() {
		IProject project = getProject();
		if (project!=null) {
			return project.getName();
		} 
		return null; 
	}

	/**
	 * Gets the java project.
	 *
	 * @author mqfdy
	 * @return the IJavaProject instance associated with this project, or null
	 *         if this project is not imported in the workspace.
	 * @Date 2018-09-03 09:00
	 */
	public IJavaProject getJavaProject() {
		//TODO: cache this?
		IProject project = getProject();
		if (project!=null) {
			return JavaCore.create(project);
		}
		return null;
	}

	/**
	 * Get the IProject in the workspace corresponding to this GradleProject. If
	 * a corresponding project doesn't exist in the workspace this methhod
	 * returns null.
	 *
	 * @author mqfdy
	 * @return the project
	 * @Date 2018-09-03 09:00
	 */
	public IProject getProject() {
		if (cachedProject!=null && cachedProject.exists()) {
			return cachedProject;
		}
		for (IProject project : ResourcesPlugin.getWorkspace().getRoot().getProjects()) {
			IPath loc = project.getLocation();
			if (loc!=null && loc.toFile().equals(location)) {
				this.cachedProject = project;
				return project;
			}
		};
		return null;
	}

	@Override
	public String toString() {
		IProject project = getProject();
		if (project!=null) {
			return "D"+project;
		}
		return "D"+location;
	}

	
	/**
	 * This returns the folder where the Gradle build output for this project
	 * are being stored.
	 * 
	 * This may return null if the GradleProject is not imported in the
	 * workspace.
	 * 
	 * This may return a IFolder instance (handle) that doesn't physically exist
	 * (yet). E.g. this may happen if a project is imported to the workspace but
	 * has not yet been built by gradle prior to importing it. In this case the
	 * build folder may not have been created yet.
	 *
	 * @author mqfdy
	 * @return the builds the folder
	 * @Date 2018-09-03 09:00
	 */
	public IFolder getBuildFolder() {
		IProject p = getProject();
		if (p!=null) {
			return p.getFolder("build");
		}
		return null;
	}

	/**
	 * Exists.
	 *
	 * @author mqfdy
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	//see if we can find the project
	public boolean exists() {
		IProject project = getProject();
		if(project == null)
			return false;
		return project.exists();
	}

	/**
	 * New instance.
	 *
	 * @author mqfdy
	 * @param location
	 *            the location
	 * @param monitor
	 *            the monitor
	 * @return the micro project
	 * @Date 2018-09-03 09:00
	 */
	//create  project
	public MicroProject newInstance(File location,IProgressMonitor monitor) {
		monitor.beginTask("Create Springboot Project: " + location.getName(), 
				NewMicroProjectOperation.totalWork);
		monitor.worked(NewMicroProjectOperation.progress++);
		
		// Springboot newInstance
		MicroProjectManager manager = MicroProjectPlugin.getProjectManager();
		MicroProject microProject = manager.newInstance(location,monitor);
		try {
			microProject.getProject().refreshLocal(IResource.DEPTH_INFINITE,
					new NullProgressMonitor());
		} catch (CoreException e) {
			e.printStackTrace();
		}
		
		return microProject;
	}
	
}
