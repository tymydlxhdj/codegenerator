package com.mqfdy.code.designer.editor.utils;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import com.mqfdy.code.designer.editor.BusinessModelEditorPlugin;

/**
 * 自定义异常类
 * 
 * @author mqfdy
 * 
 */
public class ModuleExceptionLogOnly extends ModuleException {

	private static final long serialVersionUID = 5645105894868022838L;

	public ModuleExceptionLogOnly(CoreException e, String message) {

		BusinessModelEditorPlugin plugin = BusinessModelEditorPlugin
				.getDefault();

			plugin.getLog().log(e.getStatus());

	}

	public ModuleExceptionLogOnly(String message) {

		BusinessModelEditorPlugin plugin = BusinessModelEditorPlugin
				.getDefault();
		Exception e = new Exception();

			IStatus status = new Status(IStatus.ERROR, plugin.getBundle()
					.getSymbolicName(), 0, message, e);
			plugin.getLog().log(status);

	}

}
