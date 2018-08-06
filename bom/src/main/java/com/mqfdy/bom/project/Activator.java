package com.mqfdy.bom.project;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.mqfdy.bom.project.wizard.BOMProjectManager;



/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.mqfdy.bom.project"; //$NON-NLS-1$

	// The shared instance
	private static Activator plugin;
	
	private static BOMProjectManager projectManager = new BOMProjectManager();
	
	public static final String TOOL_PLUGIN_ID = "com.mqfdy.code.bom.project";
	
	public static final String RESOURCESPATH = "bom";
	
	private static BundleContext context;
	
	/**
	 * The constructor
	 */
	public Activator() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		Activator.context = context;
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}
	
	public static BOMProjectManager getProjectManager() {
		return projectManager;
	}
	
	/**
	 * @param filepath
	 *            releative path
	 * @return file
	 */
	public static String getResourceFile(String relatepath) {
		URL url = context.getBundle().getEntry(relatepath);
		String find = null;
		try {
			if (url != null) {
				find = FileLocator.toFileURL(url).getPath();
				File file = new File(find);
				if (file.exists())
					return file.toString();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void log(Throwable e) {
		log(e.getMessage());
	}
	
	public static void logInfo(Throwable e) {
		log(e.getMessage());
	}

	public static void log(IStatus s) {
		plugin.getLog().log(s);
	}
	
	public static void log(String msg) {
		log(msg);
	}
}
