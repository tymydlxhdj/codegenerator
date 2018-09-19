package com.mqfdy.code;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;



// TODO: Auto-generated Javadoc
/**
 * The activator class controls the plug-in life cycle.
 *
 * @author mqfdy
 */
public class Activator extends AbstractUIPlugin {

	/** The Constant PLUGIN_ID. */
	// The plug-in ID
	public static final String PLUGIN_ID = "com.mqfdy.code.generator"; //$NON-NLS-1$

	/** The plugin. */
	// The shared instance
	private static Activator plugin;
	
	/**
	 * The constructor.
	 */
	public Activator() {
	}


	/**
	 * start.
	 *
	 * @author mqfdy
	 * @param context
	 *            context
	 * @throws Exception
	 *             the object
	 * @Date 2018-9-3 16:02:07
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/**
	 *
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
	 * @Date 2018-9-3 11:38:31
	 */
	public static Activator getDefault() {
		return plugin;
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
