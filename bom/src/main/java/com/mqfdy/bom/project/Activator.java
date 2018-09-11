package com.mqfdy.bom.project;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.mqfdy.bom.project.wizard.BOMProjectManager;



// TODO: Auto-generated Javadoc
/**
 * The activator class controls the plug-in life cycle.
 *
 * @author mqfdy
 */
public class Activator extends AbstractUIPlugin {

	/** The Constant PLUGIN_ID. */
	// The plug-in ID
	public static final String PLUGIN_ID = "com.mqfdy.bom.project"; //$NON-NLS-1$

	/** The plugin. */
	// The shared instance
	private static Activator plugin;
	
	/** The project manager. */
	private static BOMProjectManager projectManager = new BOMProjectManager();
	
	/** The Constant TOOL_PLUGIN_ID. */
	public static final String TOOL_PLUGIN_ID = "com.mqfdy.code.bom.project";
	
	/** The Constant RESOURCESPATH. */
	public static final String RESOURCESPATH = "bom";
	
	/** The context. */
	private static BundleContext context;
	
	/**
	 * The constructor.
	 */
	public Activator() {
	}

	/**
	 * @param context
	 * @throws Exception
	 */
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		Activator.context = context;
		plugin = this;
	}

	/**
	 * @param context
	 * @throws Exception
	 */
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance.
	 *
	 * @author mqfdy
	 * @return the shared instance
	 * @Date 2018-09-03 09:00
	 */
	public static Activator getDefault() {
		return plugin;
	}
	
	/**
	 * Gets the project manager.
	 *
	 * @author mqfdy
	 * @return the project manager
	 * @Date 2018-09-03 09:00
	 */
	public static BOMProjectManager getProjectManager() {
		return projectManager;
	}
	
	/**
	 * Gets the resource file.
	 *
	 * @author mqfdy
	 * @param relatepath
	 *            the relatepath
	 * @return file
	 * @Date 2018-09-03 09:00
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
	
	/**
	 * Log.
	 *
	 * @author mqfdy
	 * @param e
	 *            the e
	 * @Date 2018-09-03 09:00
	 */
	public static void log(Throwable e) {
		log(e.getMessage());
	}
	
	/**
	 * Log info.
	 *
	 * @author mqfdy
	 * @param e
	 *            the e
	 * @Date 2018-09-03 09:00
	 */
	public static void logInfo(Throwable e) {
		log(e.getMessage());
	}

	/**
	 * Log.
	 *
	 * @author mqfdy
	 * @param s
	 *            the s
	 * @Date 2018-09-03 09:00
	 */
	public static void log(IStatus s) {
		plugin.getLog().log(s);
	}
	
	/**
	 * Log.
	 *
	 * @author mqfdy
	 * @param msg
	 *            the msg
	 * @Date 2018-09-03 09:00
	 */
	public static void log(String msg) {
		log(msg);
	}
}
