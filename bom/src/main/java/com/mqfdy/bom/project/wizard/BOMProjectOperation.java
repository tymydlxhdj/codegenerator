package com.mqfdy.bom.project.wizard;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.osgi.framework.Bundle;

import com.mqfdy.bom.project.Activator;
import com.mqfdy.bom.project.util.ZipFileUtil;


// TODO: Auto-generated Javadoc
/**
 * The Class BOMProjectOperation.
 *
 * @author mqfdy
 */
public class BOMProjectOperation {
	
	/** The project name. */
	private String projectName;
	
	/** The location. */
	private String location;

	/** The parent project. */
	private IProject parentProject;
	
	/** The Constant IGNORED_NAMES. */
	private static final FilenameFilter IGNORED_NAMES = new FilenameFilter() {
		public boolean accept(File dir, String name) {
			return name.endsWith(".zip");
		}
	};
	
	/**
	 * Perform.
	 *
	 * @author mqfdy
	 * @param monitor
	 *            the monitor
	 * @return true, if successful
	 * @throws CoreException
	 *             the core exception
	 * @Date 2018-09-03 09:00
	 */
	//拷贝代码到本地目录下,并创建JAVA工程
	public boolean perform(IProgressMonitor monitor) throws CoreException {
		monitor.beginTask("Create project contents", 5);
		monitor.worked(1);
		try {
			File locationDir = new File(getLocation() + File.separator + projectName);
			
			//将模板项目解压拷贝到向导第一页中的LOCATION位置
			File localSamplesDir = getLocalSamplesDir();
			File[] projects = localSamplesDir.listFiles(IGNORED_NAMES);
			File project = projects[0];// 只有一个模板项目
			try {
				ZipFileUtil.unzip(project.toURI().toURL(), locationDir);
			} catch (IOException e) {
				e.printStackTrace();
			}

			monitor.worked(3);
			
			
			
			
			
			String fileName = locationDir.getAbsolutePath();
			File f = new File(fileName);
			//在ECLIPSE中创建JAVA工程.
			IProject subProject = Activator.getProjectManager().newInstance(f,monitor);
			//updateSettingsGradle();
			
			final IJavaProject javaProject = JavaCore.create(subProject);
			/*boolean isEnabled = GradleClassPathContainer.isOnClassPath(javaProject);
			if (!isEnabled) {
				GradleClassPathContainer.addTo(javaProject, monitor);
			}*/
			
			// 更新项目
			/*List<IProject> projectsList = new ArrayList<IProject>();
			projectsList.add(subProject);
			RefreshAllActionCore.callOn(projectsList);*/
			
		} finally {
			monitor.done();
		}
		return true;
	}
	
	/**
	 * Gets the local samples dir.
	 *
	 * @author mqfdy
	 * @return the local samples dir
	 * @Date 2018-09-03 09:00
	 */
	private File getLocalSamplesDir() {
		Bundle bundle = Platform.getBundle(Activator.TOOL_PLUGIN_ID);
		try {
			File bundleFile = FileLocator.getBundleFile(bundle);
			if (bundleFile != null && bundleFile.exists() && bundleFile.isDirectory()) {
				File samplesDir = new File(bundleFile, Activator.RESOURCESPATH);
				if (samplesDir.isDirectory()) {
					return samplesDir;
				} else {
					Activator.log("Directory 'rescourse' not found in plugin "+Activator.TOOL_PLUGIN_ID);
				}
			} else {
				Activator.log("Couldn't access the plugin "+Activator.TOOL_PLUGIN_ID+" as a directory. Maybe it is not installed as an 'exploded' bundle?");
			}
		} catch (IOException e) {
			e.printStackTrace();
			Activator.log(e);
		}
		return null;
	}
	

	/**
	 * Gets the project name.
	 *
	 * @author mqfdy
	 * @return the project name
	 * @Date 2018-09-03 09:00
	 */
	public String getProjectName() {
		return projectName;
	}

	/**
	 * Sets the project name.
	 *
	 * @author mqfdy
	 * @param projectName
	 *            the new project name
	 * @Date 2018-09-03 09:00
	 */
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	/**
	 * Gets the location.
	 *
	 * @author mqfdy
	 * @return the location
	 * @Date 2018-09-03 09:00
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * Sets the location.
	 *
	 * @author mqfdy
	 * @param location
	 *            the new location
	 * @Date 2018-09-03 09:00
	 */
	public void setLocation(String location) {
		this.location = location;
	}
	
	/**
	 * Gets the parent project.
	 *
	 * @author mqfdy
	 * @return the parent project
	 * @Date 2018-09-03 09:00
	 */
	public IProject getParentProject() {
		return parentProject;
	}

	/**
	 * Sets the parent project.
	 *
	 * @author mqfdy
	 * @param parentProject
	 *            the new parent project
	 * @Date 2018-09-03 09:00
	 */
	public void setParentProject(IProject parentProject) {
		this.parentProject = parentProject;
	}
}
