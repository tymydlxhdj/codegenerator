package com.mqfdy.code.datasource.model;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.Status;
import org.osgi.framework.BundleContext;

// TODO: Auto-generated Javadoc
/**
 * The activator class controls the plug-in life cycle.
 *
 * @author mqfdy
 */
public class ModelPlugin extends Plugin {

	/** The Constant PLUGIN_ID. */
	// The plug-in ID
	public static final String PLUGIN_ID = "com.sgcc.uap.ide.model";

	/** The plugin. */
	// The shared instance
	private static ModelPlugin plugin;

	/**
	 * The constructor.
	 */
	public ModelPlugin() {
	}
	
	/**
	 * Gets the plugin id.
	 *
	 * @author mqfdy
	 * @return the plugin id
	 * @Date 2018-09-03 09:00
	 */
	public static String getPluginId() {
		return getDefault().getBundle().getSymbolicName();
	}

	/**
	 * Start.
	 *
	 * @param context
	 *            the context
	 * @throws Exception
	 *             ModelPlugin
	 * @see org.eclipse.core.runtime.Plugin#start(org.osgi.framework.BundleContext)
	 */
	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/**
	 * Stop.
	 *
	 * @param context
	 *            the context
	 * @throws Exception
	 *             ModelPlugin
	 * @see org.eclipse.core.runtime.Plugin#stop(org.osgi.framework.BundleContext)
	 */
	@Override
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance.
	 *
	 * @author mqfdy
	 * @return the shared instance
	 * @Date 2018-9-3 11:38:36
	 */
	public static ModelPlugin getDefault() {
		return plugin;
	}
	
	/**
	 * Log.
	 *
	 * @author mqfdy
	 * @param status
	 *            the status
	 * @Date 2018-09-03 09:00
	 */
	public static void log(IStatus status) {
		ResourcesPlugin.getPlugin().getLog().log(status);
	}

	/**
	 * Log error message.
	 *
	 * @author mqfdy
	 * @param message
	 *            the message
	 * @Date 2018-09-03 09:00
	 */
	public static void logErrorMessage(String message) {
		log(new Status(IStatus.ERROR, getPluginId(), IStatus.ERROR, message, null));
	}

	/**
	 * Log exception.
	 *
	 * @author mqfdy
	 * @param e
	 *            the e
	 * @param title
	 *            the title
	 * @param message
	 *            the message
	 * @Date 2018-09-03 09:00
	 */
	public static void logException(Throwable e, final String title, String message) {
		if (e instanceof InvocationTargetException) {
			e = ((InvocationTargetException) e).getTargetException();
		}
		IStatus status = null;
		if (e instanceof CoreException)
			status = ((CoreException) e).getStatus();
		else {
			if (message == null)
				message = e.getMessage();
			if (message == null)
				message = e.toString();
			status = new Status(IStatus.ERROR, getPluginId(), IStatus.OK, message, e);
		}
		ResourcesPlugin.getPlugin().getLog().log(status);
	}

	/**
	 * Log exception.
	 *
	 * @author mqfdy
	 * @param e
	 *            the e
	 * @Date 2018-09-03 09:00
	 */
	public static void logException(Throwable e) {
		logException(e, null, null);
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
		if (e instanceof InvocationTargetException)
			e = ((InvocationTargetException) e).getTargetException();
		IStatus status = null;
		if (e instanceof CoreException)
			status = ((CoreException) e).getStatus();
		else
			status = new Status(IStatus.ERROR, getPluginId(), IStatus.OK, e.getMessage(), e);
		log(status);
	}

}
