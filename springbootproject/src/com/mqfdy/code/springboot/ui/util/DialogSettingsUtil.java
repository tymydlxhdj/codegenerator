package com.mqfdy.code.springboot.ui.util;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.dialogs.IDialogSettings;

import com.mqfdy.code.springboot.core.MicroProject;
import com.mqfdy.code.springboot.core.MicroProjectNature;
import com.mqfdy.code.springboot.core.MicroProjectPlugin;

// TODO: Auto-generated Javadoc
/**
 * Method that I wish would exist on IDialogSettings but don't.
 * 
 * @author lenovo
 */
public class DialogSettingsUtil {

	/** The dialog settings. */
	protected IDialogSettings dialogSettings;
	
	/**
	 * Gets the boolean.
	 *
	 * @author mqfdy
	 * @param dialogSettings
	 *            the dialog settings
	 * @param key
	 *            the key
	 * @param defaultValue
	 *            the default value
	 * @return the boolean
	 * @Date 2018-09-03 09:00
	 */
	public static boolean getBoolean(IDialogSettings dialogSettings, String key, boolean defaultValue) {
		if (dialogSettings.get(key)==null) {
			return defaultValue;
		} else {
			return dialogSettings.getBoolean(key);
		}
	}

	/**
	 * Store a GradleProject reference in a dialogSettings. This method only
	 * works if the project exists in the Eclipse workspace since it stores the
	 * Eclipse project name. If the project is null or has no eclipse name this
	 * method silently fails.
	 *
	 * @author mqfdy
	 * @param dialogSettings
	 *            the dialog settings
	 * @param key
	 *            the key
	 * @param project
	 *            the project
	 * @Date 2018-09-03 09:00
	 */
	public static void put(IDialogSettings dialogSettings, String key, MicroProject project) {
		if (project!=null) {
			String name = project.getName();
			if (name!=null) {
				dialogSettings.put(key,name);
			}
		}
	}

	/**
	 * Retrieves a GradleProject from dialogSettings. This may return null if no
	 * project was stored, or if the project no longer exists or doesn't look
	 * like a Gradle project.
	 *
	 * @author mqfdy
	 * @param dialogSettings
	 *            the dialog settings
	 * @param key
	 *            the key
	 * @return the gradle project
	 * @Date 2018-09-03 09:00
	 */
	public static MicroProject getGradleProject(IDialogSettings dialogSettings, String key) {
		String name = dialogSettings.get(key);
		if (name!=null) {
			IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(name);
			if (MicroProjectNature.hasNature(project)) {
				return MicroProjectPlugin.getProjectManager().getOrCreate(project);
			}
		}
		return null;
	}

	
}
