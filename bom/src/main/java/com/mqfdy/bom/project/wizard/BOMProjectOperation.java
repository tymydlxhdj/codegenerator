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


public class BOMProjectOperation {
	
	private String projectName;
	
	private String location;

	private IProject parentProject;
	
	private static final FilenameFilter IGNORED_NAMES = new FilenameFilter() {
		public boolean accept(File dir, String name) {
			return name.endsWith(".zip");
		}
	};
	
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
	

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
	
	public IProject getParentProject() {
		return parentProject;
	}

	public void setParentProject(IProject parentProject) {
		this.parentProject = parentProject;
	}
}
