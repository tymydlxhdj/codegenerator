package com.mqfdy.code.springboot.ui.util;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.progress.IProgressService;

import com.mqfdy.code.springboot.core.MicroProjectPlugin;
import com.mqfdy.code.springboot.core.util.ExceptionUtil;
import com.mqfdy.code.springboot.core.util.Operation2Runnable;


/**
 * @author lenovo
 */
public class UIJobUtil {

	public static void busyCursorWhile(Operation2Runnable runnable) throws InvocationTargetException, InterruptedException {
		IWorkbench wb = PlatformUI.getWorkbench();
		IProgressService ps = wb.getProgressService();
		ps.busyCursorWhile(runnable);
	}

	/**
	 * Execute a runable while showing a modal progress dialog. Note that it is preferred in general to
	 * use "busyCursorWhile". Use this method only in the context of modal
	 * dialog, where busyCursorwhile will not switch to showing a progress dialog (because the dialog is
	 * suppressed when another modal dialog is already open).
	 */
	public static void withProgressDialog(Shell shell, final Operation2Runnable runnable) {
		ProgressMonitorDialog progressDialog = new ProgressMonitorDialog(shell);
		try {
			progressDialog.run(true, false, runnable);
		} catch (InvocationTargetException e) {
			//TODO: probably, this should propagate error messages to the context, because this called from some UI
			// context, like a dialog, and this dialog may want to display the error in a dialog status line rather than popup message.
			MessageDialog.openError(shell, "Error in runnable '"+runnable+"'", ExceptionUtil.getMessage(e) 
					+ "\nSee error log for details");
			MicroProjectPlugin.log(e);
		} catch (InterruptedException e) {
			throw new OperationCanceledException();
		}
	}
	
}
