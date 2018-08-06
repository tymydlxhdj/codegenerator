package com.mqfdy.code.springboot.core;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.ui.PreferenceConstants;

import com.mqfdy.code.springboot.core.util.ExceptionUtil;
import com.mqfdy.code.springboot.core.util.NatureUtils;

/**
 * An instance of this class is responsible for managing the creation of
 * MicroProject instances.
 * 
 * @author mqfdy
 */
public class MicroProjectManager {
	
	public static final String GRADLE_CLASSPATH_ID = "org.springsource.ide.eclipse.gradle.classpathcontainer";

	private static String MICROSERVICE_PROJECT_IDENTIFIER = "com.mqfdy.code.springboot.nature";
	
	/**
	 * Keeps an index of all known MicroProject projects. The key used for the index
	 * is the canonical path of the projects folder location in the file system.
	 */
	private Map<String, MicroProject> gradleProjects = new HashMap<String, MicroProject>();

	public synchronized MicroProject getOrCreate(File location) {
		File canonicalFile = toCanonicalFile(location);
		MicroProject project = get(canonicalFile);
		if (project == null) {
			project = new MicroProject(canonicalFile);
			gradleProjects.put(canonicalFile.getPath(), project);
		}
		return project;
	}

	private File toCanonicalFile(File location) {
		try {	
			return location.getCanonicalFile();
		} catch (IOException e) {
			MicroProjectPlugin.log(e);
			return location.getAbsoluteFile(); // Best we can do
		}
	}

	public MicroProject get(File canonicalFile) {
		return gradleProjects.get(canonicalFile.getPath());
	}

	public MicroProject getOrCreate(IProject project) {
		return getOrCreate(project.getLocation().toFile().getAbsoluteFile());
	}

	/**
	 * 在ECLIPSE中创建JAVA项目(入口)
	 * 
	 * @param projectName
	 * @return
	 */
	public MicroProject newInstance(File location, IProgressMonitor monitor) {

		monitor.setTaskName("Creating Springboot Project: " + location.getName());
		monitor.worked(1);
		IProject project = null;
		MicroProject microProject = gradleProjects.get(location.getPath());
		if (microProject.exists())
			return microProject;
		try {
			// 调用创建项目方法
			IJavaProject javaProject = createJavaProject(location);
			final List<IClasspathEntry> cp = new ArrayList<IClasspathEntry>();
			project = javaProject.getProject();
			addToClasspath(javaProject, cp.toArray(new IClasspathEntry[cp.size()]));
			//application.properties 默认是iso的，修改成uft-8的
			IFile file = project.getFile("src/main/resources/application.properties");
			file.setCharset("UTF-8", monitor);
		} catch (CoreException e) {
			ExceptionUtil.coreException(e);
		}
		convertGradleProject(microProject, monitor);
		return microProject;
	}
	
	protected static void addToClasspath(final IJavaProject jproj,
			final IClasspathEntry[] entry) throws CoreException {
		
		final IClasspathEntry[] existingEntries = jproj.getRawClasspath();
		List<IClasspathEntry> nonExist = new ArrayList<IClasspathEntry>();
		
		boolean found = false;
		for (IClasspathEntry iClasspathEntry : entry) {
			for (IClasspathEntry existingEntry : existingEntries) {
				if (existingEntry.equals(iClasspathEntry)) {
					found = true;
					break;
				}
			}
			if(!found){
				nonExist.add(iClasspathEntry);
			}
		}
		
		final IClasspathEntry[] updated = new IClasspathEntry[existingEntries.length + nonExist.size()];
		System.arraycopy(existingEntries, 0, updated, 0, existingEntries.length);
		System.arraycopy(nonExist.toArray(new IClasspathEntry[nonExist.size()]), 0, updated, existingEntries.length, nonExist.size());
		jproj.setRawClasspath(updated, null);
	}

	
	/**在ECLIPSE中创建JAVA项目
	 * create Java Project
	 * @param location
	 * @return
	 */
	private IJavaProject createJavaProject(File location) {
		// 获取工作区
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		
		// 创建新项目
		final IProject project = root.getProject(location.getName());
		
		// 设置工程的位置
		Path projectLocation = new Path(location.toString());
		final IProjectDescription description = root.getWorkspace()
				.newProjectDescription(project.getName());
		description.setLocation(projectLocation);
		description.setComment(MICROSERVICE_PROJECT_IDENTIFIER);
		
		// 设置工程标记,即为java工程
		String[] javaNature = description.getNatureIds();
		String[] newJavaNature = new String[javaNature.length + 2];
		
		//1要复制的数组，2是从要复制的数组的第几个开始，3是复制到那，4是复制到的数组第几个开始，5是复制长度
		System.arraycopy(javaNature, 0, newJavaNature, 0, javaNature.length);
		newJavaNature[javaNature.length] = JavaCore.NATURE_ID;
		newJavaNature[javaNature.length + 1] = MicroProjectNature.NATURE_ID;
		description.setNatureIds(newJavaNature);
		try {
			NullProgressMonitor mon = new NullProgressMonitor();
			project.create(description, mon);
			project.open(mon);
			project.setDefaultCharset("UTF-8",mon);
		} catch (CoreException e) {
			e.printStackTrace();
		}
		// 转化成java工程
		IJavaProject javaProject = JavaCore.create(project);
		
		// jrelibary
		List<IClasspathEntry> jrelibs = new ArrayList<IClasspathEntry>();
		
		// 创建源代码文件夹
		List<String> folders = new ArrayList<String>();
		
		folders.add("src/main/java");
		folders.add("src/main/resources");
		folders.add("src/test/java");
		folders.add("src/test/resources");
		
		for (String string : folders) {
			// 创建SourceLibrary
			IFolder folder = javaProject.getProject().getFolder(string);
			// 加入JRE
			IClasspathEntry newClasspathEntry = JavaCore.newSourceEntry(folder.getFullPath());
			jrelibs.add(newClasspathEntry);
			mkFolders(folder);
		}
		try {
			// 获取默认的JRE库
			IClasspathEntry[] jreLibrary = PreferenceConstants.getDefaultJRELibrary();
			// 构建classpath
			jrelibs.addAll(Arrays.asList(jreLibrary));
//			//创建gradle classpath
//			IClasspathEntry gradleClasspathEntry = JavaCore.newContainerEntry(new Path(GRADLE_CLASSPATH_ID), true);
//			jrelibs.add(gradleClasspathEntry);
			javaProject.setRawClasspath(
					jrelibs.toArray(new IClasspathEntry[jrelibs.size()]), null);
		} catch (JavaModelException e1) {
			e1.printStackTrace();
		}
		// create ouput folders
		IFolder outPutFolder = javaProject.getProject().getFolder("bin");
		try {
			mkFolders(outPutFolder);
			javaProject.setOutputLocation(outPutFolder.getFullPath(), null);
		} catch (Exception e) {
			MicroProjectPlugin.log(e);
		}
		// 创建输出路径
		try {
			IProjectDescription description2 = javaProject.getProject().getDescription();
			ICommand command = description2.newCommand();
			command.setBuilderName(JavaCore.BUILDER_ID);
			description2.setBuildSpec(new ICommand[] { command });
			description2.setNatureIds(new String[] {JavaCore.NATURE_ID, MicroProjectNature.NATURE_ID});
			javaProject.getProject().setDescription(description2, null);
		} catch (CoreException e) {
			e.printStackTrace();
		}
		
		return javaProject;
	}

	/**
	 * @param folder
	 *            
	 */
	private void mkFolders(final IFolder folder) {
		if (!folder.exists()) {
			final IContainer parent = folder.getParent();
			if (parent instanceof IFolder) {
				mkFolders((IFolder) parent);
			}
			try {
				folder.create(true, true, null);
			} catch (CoreException e) {
				e.printStackTrace();
			}
		}
	}


	private void convertGradleProject(MicroProject pj,
			IProgressMonitor monitor) {
		monitor.setTaskName("Convert to Springboot Project " + pj.getName());
		monitor.worked(1);
		try {
			if (!pj.exists())
				throw ExceptionUtil
						.coreException("Cannot find the project for:"
								+ pj.getName());
			if(!pj.getName().endsWith("_api")){
				NatureUtils.ensure(pj.getProject(), new SubProgressMonitor(monitor,1), 
						MicroProjectNature.NATURE_ID,
						MicroProjectNature.GRADLE_ID,
						JavaCore.NATURE_ID);
				pj.getProject().open(new NullProgressMonitor());
				
			}else{
				NatureUtils.ensure(pj.getProject(), new SubProgressMonitor(monitor,1), 
						MicroProjectNature.GRADLE_ID,
						JavaCore.NATURE_ID);
				pj.getProject().open(new NullProgressMonitor());
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Retrieve a collection of all Gradle projects in the workspace.
	 */
	public Collection<MicroProject> getGradleProjects()  {
		IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
		List<MicroProject> list = new ArrayList<MicroProject>();
		for (IProject p : projects) {
			try {
				if (p.isAccessible() && p.hasNature(MicroProjectNature.NATURE_ID)) {
					list.add(getOrCreate(p));
				}
			} catch (CoreException e) {
				MicroProjectPlugin.log(e);
			}
		}
		return list;
	}
}