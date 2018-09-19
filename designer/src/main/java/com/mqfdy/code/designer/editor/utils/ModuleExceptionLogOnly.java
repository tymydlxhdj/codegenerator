package com.mqfdy.code.designer.editor.utils;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import com.mqfdy.code.designer.editor.BusinessModelEditorPlugin;

// TODO: Auto-generated Javadoc
/**
 * 自定义异常类.
 *
 * @author mqfdy
 */
public class ModuleExceptionLogOnly extends ModuleException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 5645105894868022838L;

	/**
	 * Instantiates a new module exception log only.
	 *
	 * @param e
	 *            the e
	 * @param message
	 *            the message
	 */
	public ModuleExceptionLogOnly(CoreException e, String message) {

		BusinessModelEditorPlugin plugin = BusinessModelEditorPlugin
				.getDefault();

			plugin.getLog().log(e.getStatus());

	}

	/**
	 * Instantiates a new module exception log only.
	 *
	 * @param message
	 *            the message
	 */
	public ModuleExceptionLogOnly(String message) {

		BusinessModelEditorPlugin plugin = BusinessModelEditorPlugin
				.getDefault();
		Exception e = new Exception();

			IStatus status = new Status(IStatus.ERROR, plugin.getBundle()
					.getSymbolicName(), 0, message, e);
			plugin.getLog().log(status);

	}

}
