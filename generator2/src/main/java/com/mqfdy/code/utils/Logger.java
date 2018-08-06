package com.mqfdy.code.utils;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;

import com.mqfdy.code.generator.GeneratorPlugin;

/**
 */
public class Logger {
	public static IStatus createStatus(int severity, int code, String message,
			Throwable exception) {
		return new Status(severity, GeneratorPlugin.PLUGIN_ID, code, message,
				exception);
	}

	public static MultiStatus createMultiStatus(int severity, int code,
			String message, Throwable exception) {
		return new MultiStatus(GeneratorPlugin.PLUGIN_ID, code, message, exception);
	}

	public static IStatus createStatus(int severity, String pluginID, int code,
			String message, Throwable exception) {
		return new Status(severity, pluginID, code, message, exception);
	}

	public static void log(int severity, int code, String message,
			Throwable exception) {
		log(createStatus(severity, code, message, exception));
	}

	public static void log(IStatus status) {
		GeneratorPlugin.getDefault().getLog().log(status);
	}

	public static void log(String message) {
		log(new Status(IStatus.ERROR, GeneratorPlugin.PLUGIN_ID, IStatus.ERROR,
				message, null));
	}

	public static void log(Throwable e) {
		log(new Status(IStatus.ERROR, GeneratorPlugin.PLUGIN_ID, IStatus.ERROR,
				"Internal Error", e)); //$NON-NLS-1$
	}

	public static void logError(String message, Throwable e) {
		log(IStatus.ERROR, IStatus.OK, message, null);
	}

	public static void log(String message, Throwable e) {
		logError(message, e);
	}

	public static void logInfo(String message) {
		log(IStatus.INFO, IStatus.OK, message, null);
	}
}
