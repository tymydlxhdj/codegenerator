package com.mqfdy.code.springboot.core.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.launching.IVMInstall;
import org.eclipse.jdt.launching.IVMInstallType;
import org.eclipse.jdt.launching.JavaRuntime;
import org.eclipse.jdt.launching.environments.IExecutionEnvironment;
import org.eclipse.jdt.launching.environments.IExecutionEnvironmentsManager;

// TODO: Auto-generated Javadoc
/**
 * Utilities to retrieve info about JVMs configured in the workspace.
 * 
 * @author lenovo
 */
public class JavaRuntimeUtils {
	
	private List<IVMInstall> allInstalls = null; //Lazy initialised
	private IExecutionEnvironment[] allExecEnvs = null;
	
	/**
	 * Gets the execution envs.
	 *
	 * @author mqfdy
	 * @return the execution envs
	 * @Date 2018-09-03 09:00
	 */
	public IExecutionEnvironment[] getExecutionEnvs() {
		if (allExecEnvs==null) {
			IExecutionEnvironmentsManager mgr = JavaRuntime.getExecutionEnvironmentsManager();
			allExecEnvs = mgr.getExecutionEnvironments();
			if (allExecEnvs==null) {
				allExecEnvs = new IExecutionEnvironment[0];
			}
		}
		return allExecEnvs;
	}
	
	/**
	 * Gets the execution env names.
	 *
	 * @author mqfdy
	 * @return the execution env names
	 * @Date 2018-09-03 09:00
	 */
	public String[] getExecutionEnvNames() {
		IExecutionEnvironment[] envs = getExecutionEnvs();
		if (envs!=null) {
			String[] names = new String[envs.length];
			for (int i = 0; i < names.length; i++) {
				names[i] = envs[i].getId();
			}
			return names;
		}
		return new String[0];
	}

	/**
	 * Gets the workspace JV ms.
	 *
	 * @author mqfdy
	 * @return the workspace JV ms
	 * @Date 2018-09-03 09:00
	 */
	public List<IVMInstall> getWorkspaceJVMs() {
		if (allInstalls==null) {
			allInstalls = new ArrayList<IVMInstall>();
			IVMInstallType[] types = JavaRuntime.getVMInstallTypes();
			for (int i = 0; i < types.length; i++) {
				IVMInstallType type = types[i];
				IVMInstall[] installs = type.getVMInstalls();
				for (int j = 0; j < installs.length; j++) {
					IVMInstall install = installs[j];
					allInstalls.add(install);
				}
			}
		}
		return allInstalls;
	}
	
	/**
	 * Gets the workspace JVM names.
	 *
	 * @author mqfdy
	 * @return the workspace JVM names
	 * @Date 2018-09-03 09:00
	 */
	public String[] getWorkspaceJVMNames() {
		List<IVMInstall> jvms = getWorkspaceJVMs();
		String[] names = new String[jvms.size()];
		for (int i = 0; i < names.length; i++) {
			names[i] = jvms.get(i).getName();
		}
		return names;
	}

	/**
	 * Gets the install.
	 *
	 * @author mqfdy
	 * @param name
	 *            the name
	 * @return the install
	 * @Date 2018-09-03 09:00
	 */
	public IVMInstall getInstall(String name) {
		List<IVMInstall> installs = getWorkspaceJVMs();
		for (IVMInstall install : installs) {
			if (install.getName().equals(name)) {
				return install;
			}
		}
		return null;
	}
	
	/**
	 * This method returns true if we are fairly certain that the given JVM is a
	 * JRE and not a proper JDK. If we can't determine whether it is a JDK then
	 * we conservatively return false.
	 *
	 * @author mqfdy
	 * @param jvm
	 *            the jvm
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	public static boolean hasTheJREProblem(IVMInstall jvm) {
		String os = System.getProperty("os.name");
		if (os!=null) {
			if (os.startsWith("Windows") || os.equals("Linux")) {
				//Note: See http://lopica.sourceforge.net/os.html (list of OS names)
				//Only consider OS's where we are relatively confident that the check is valid
				if (jvm!=null) {
					File javaHome = jvm.getInstallLocation();
					if (javaHome!=null && javaHome.exists()) {
						File toolsJar = new File(new File(javaHome, "lib"), "tools.jar");
						return !toolsJar.exists();
					}
				}
			}
		}
		return false; // Conservatively assume check for 'not a JRE' is passing if unknown OS, or Mac OS, or something went wrong,
		// or Java home doesn't exist  etc.
	}

	/**
	 * Gets the install for EE.
	 *
	 * @author mqfdy
	 * @param execEnvName
	 *            the exec env name
	 * @return the install for EE
	 * @Date 2018-09-03 09:00
	 */
	public IVMInstall getInstallForEE(String execEnvName) {
		IExecutionEnvironment[] ees = getExecutionEnvs();
		IExecutionEnvironment found = null;
		for (IExecutionEnvironment ee : ees) {
			if (ee.getId().equals(execEnvName)) {
				found = ee;
			}
		}
		if (found!=null) {
			return found.getDefaultVM();
		}
		return null;
	}

	
}
