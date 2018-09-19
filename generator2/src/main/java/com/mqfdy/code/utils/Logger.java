package com.mqfdy.code.utils;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;

import com.mqfdy.code.generator.GeneratorPlugin;

// TODO: Auto-generated Javadoc
/**
 * The Class Logger.
 *
 * @author mqfdy
 */
public class Logger {
	
	/**
	 * Creates the status.
	 *
	 * @author mqfdy
	 * @param severity
	 *            the severity
	 * @param code
	 *            the code
	 * @param message
	 *            the message
	 * @param exception
	 *            the exception
	 * @return the i status
	 * @Date 2018-09-03 09:00
	 */
	public static IStatus createStatus(int severity, int code, String message,
			Throwable exception) {
		return new Status(severity, GeneratorPlugin.PLUGIN_ID, code, message,
				exception);
	}

	/**
	 * Creates the multi status.
	 *
	 * @author mqfdy
	 * @param severity
	 *            the severity
	 * @param code
	 *            the code
	 * @param message
	 *            the message
	 * @param exception
	 *            the exception
	 * @return the multi status
	 * @Date 2018-09-03 09:00
	 */
	public static MultiStatus createMultiStatus(int severity, int code,
			String message, Throwable exception) {
		return new MultiStatus(GeneratorPlugin.PLUGIN_ID, code, message, exception);
	}

	/**
	 * Creates the status.
	 *
	 * @author mqfdy
	 * @param severity
	 *            the severity
	 * @param pluginID
	 *            the plugin ID
	 * @param code
	 *            the code
	 * @param message
	 *            the message
	 * @param exception
	 *            the exception
	 * @return the i status
	 * @Date 2018-09-03 09:00
	 */
	public static IStatus createStatus(int severity, String pluginID, int code,
			String message, Throwable exception) {
		return new Status(severity, pluginID, code, message, exception);
	}

	/**
	 * Log.
	 *
	 * @author mqfdy
	 * @param severity
	 *            the severity
	 * @param code
	 *            the code
	 * @param message
	 *            the message
	 * @param exception
	 *            the exception
	 * @Date 2018-09-03 09:00
	 */
	public static void log(int severity, int code, String message,
			Throwable exception) {
		log(createStatus(severity, code, message, exception));
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
		GeneratorPlugin.getDefault().getLog().log(status);
	}

	/**
	 * Log.
	 *
	 * @author mqfdy
	 * @param message
	 *            the message
	 * @Date 2018-09-03 09:00
	 */
	public static void log(String message) {
		log(new Status(IStatus.ERROR, GeneratorPlugin.PLUGIN_ID, IStatus.ERROR,
				message, null));
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
		log(new Status(IStatus.ERROR, GeneratorPlugin.PLUGIN_ID, IStatus.ERROR,
				"Internal Error", e)); //$NON-NLS-1$
	}

	/**
	 * Log error.
	 *
	 * @author mqfdy
	 * @param message
	 *            the message
	 * @param e
	 *            the e
	 * @Date 2018-09-03 09:00
	 */
	public static void logError(String message, Throwable e) {
		log(IStatus.ERROR, IStatus.OK, message, null);
	}

	/**
	 * Log.
	 *
	 * @author mqfdy
	 * @param message
	 *            the message
	 * @param e
	 *            the e
	 * @Date 2018-09-03 09:00
	 */
	public static void log(String message, Throwable e) {
		logError(message, e);
	}

	/**
	 * Log info.
	 *
	 * @author mqfdy
	 * @param message
	 *            the message
	 * @Date 2018-09-03 09:00
	 */
	public static void logInfo(String message) {
		log(IStatus.INFO, IStatus.OK, message, null);
	}
}
