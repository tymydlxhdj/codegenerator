package com.mqfdy.bom.project.wizard;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.core.resources.IContainer;
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

import com.mqfdy.bom.project.Activator;
import com.mqfdy.bom.project.BOMProjectNature;
import com.mqfdy.bom.project.util.NatureUtils;


public class BOMProjectManager {

	/**
	 * 在ECLIPSE中创建JAVA项目(入口)
	 * 
	 * @param projectName
	 * @return
	 */
	public IProject newInstance(File location, IProgressMonitor monitor) {

		monitor.setTaskName("Creating BOM Project: " + location.getName());
		monitor.worked(1);
		try {
			//调用创建项目方法
			IJavaProject javaProject = createJavaProject(location);
			
			final List<IClasspathEntry> cp = new ArrayList<IClasspathEntry>();
			IProject project = javaProject.getProject();
			addToClasspath(javaProject, cp.toArray(new IClasspathEntry[cp.size()]));
			convertGradleProject(project, monitor);
			return project;
		} catch (CoreException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	protected void addToClasspath(final IJavaProject jproj,
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
		description.setComment(BOMProjectNature.NATURE_ID);
		
		// 设置工程标记,即为java工程
		String[] javaNature = description.getNatureIds();
		String[] newJavaNature = new String[javaNature.length + 2];
		
		//1要复制的数组，2是从要复制的数组的第几个开始，3是复制到那，4是复制到的数组第几个开始，5是复制长度
		System.arraycopy(javaNature, 0, newJavaNature, 0, javaNature.length);
		newJavaNature[javaNature.length] = JavaCore.NATURE_ID;
		newJavaNature[javaNature.length+1] = BOMProjectNature.NATURE_ID;
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
		createSrc(javaProject, jrelibs);
		buildClasspath(javaProject, jrelibs);
		//创建bin目录
		createBin(javaProject);
		

		return javaProject;
	}
	/**
	 * 创建bin目录
	 * @param javaProject
	 */
	private void createBin(IJavaProject javaProject) {
		// create ouput folders
		IFolder outPutFolder = javaProject.getProject().getFolder("bin");
		try {
			mkFolders(outPutFolder);
			javaProject.setOutputLocation(outPutFolder.getFullPath(), null);
		} catch (Exception e) {
			Activator.log(e);
		}
	}
	/**
	 * 构建classpath文件
	 * @param javaProject
	 * @param jrelibs
	 */
	private void buildClasspath(IJavaProject javaProject, List<IClasspathEntry> jrelibs) {
		try {
			
			// 获取默认的JRE库
			IClasspathEntry[] jreLibrary = PreferenceConstants.getDefaultJRELibrary();
			
			// 构建classpath
			jrelibs.addAll(Arrays.asList(jreLibrary));
			
//			//创建gradle classpath
//			IClasspathEntry gradleClasspathEntry = JavaCore.newContainerEntry(new Path(GRADLE_CLASSPATH_ID), false);
//			jrelibs.add(gradleClasspathEntry);
			javaProject.setRawClasspath(
					jrelibs.toArray(new IClasspathEntry[jrelibs.size()]), null);
		} catch (JavaModelException e1) {
			e1.printStackTrace();
		}
	}
	/**
	 * 创建src目录
	 * @param javaProject
	 * @param jrelibs
	 */
	private void createSrc(IJavaProject javaProject, List<IClasspathEntry> jrelibs) {
		List<String> folders = new ArrayList<String>();
		folders.add("src");
		for (String string : folders) {
			
			// 创建SourceLibrary
			IFolder folder = javaProject.getProject().getFolder(string);
			
			// 加入JRE
			IClasspathEntry newClasspathEntry = JavaCore.newSourceEntry(folder.getFullPath());
			jrelibs.add(newClasspathEntry);
			mkFolders(folder);
		}
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

	private void convertGradleProject(IProject pj,
			IProgressMonitor monitor) {
		monitor.setTaskName("Convert to BOM Project " + pj.getName());
		monitor.worked(1);
		try {
			if (!pj.exists())
			NatureUtils.ensure(pj, new SubProgressMonitor(monitor,1), 
					JavaCore.NATURE_ID,BOMProjectNature.NATURE_ID);
			pj.open(new NullProgressMonitor());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
