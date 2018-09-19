package com.mqfdy.code.springboot.core.util;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.operation.IRunnableWithProgress;

// TODO: Auto-generated Javadoc
/**
 * Wrapper for long running operations, provides a way to turn the operation into
 * different types of "runnables" that exist within eclipse.
 * 
 * @author lenovo
 */
public abstract class Operation2Runnable implements IRunnableWithProgress {
	
	/** The job name. */
	protected String jobName = "Gradle Job "+generateId();
	private int jobCtr = 0;
	private synchronized int generateId() {
		return jobCtr++;
	}
	
	/**
	 * Instantiates a new operation 2 runnable.
	 *
	 * @param jobName
	 *            the job name
	 */
	public Operation2Runnable(String jobName) {
		this.jobName = jobName;
	}

	/**
	 * Doit.
	 *
	 * @author mqfdy
	 * @param mon
	 *            the mon
	 * @throws Exception
	 *             the exception
	 * @Date 2018-09-03 09:00
	 */
	public abstract void doit(IProgressMonitor mon) throws Exception;

	/**
	 * As job.
	 *
	 * @author mqfdy
	 * @return the job
	 * @Date 2018-09-03 09:00
	 */
	public Job asJob() {
		return new Job(jobName) {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				try {
					doit(monitor);
					return Status.OK_STATUS;
				} catch (Throwable e) {
					return ExceptionUtil.status(e);
				}
			}
			
			@Override
			public Job yieldRule(IProgressMonitor monitor) { //Avoids a deadlocking problem when WTP tasks call yieldRule
				return null;
			}
		};
	}

	/**
	 * As workspace job.
	 *
	 * @author mqfdy
	 * @return the workspace job
	 * @Date 2018-09-03 09:00
	 */
	public WorkspaceJob asWorkspaceJob() {
		return new WorkspaceJob(jobName) {
			@Override
			public IStatus runInWorkspace(IProgressMonitor monitor)
					throws CoreException {
				try {
					doit(monitor);
					return Status.OK_STATUS;
				} catch (Throwable e) {
					return ExceptionUtil.status(e);
				}
			}
		};
	}
	
	/**
	 * This method is here so that GradleRunnable can be used as an {@link IRunnableWithProgress}. It is final
	 * as you are not supposed to implement it directly. Implement the doit method instead.
	 */
	public final void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
		try {
			doit(monitor);
		} catch (InterruptedException e) {
			throw e;
		} catch (OperationCanceledException e) {
			throw new InterruptedException("Canceled by user");
		} catch (InvocationTargetException e) {
			throw e;
		} catch (Throwable e) {
			throw new InvocationTargetException(e);
		}
	}
	
	@Override
	public String toString() {
		return jobName;
	}
	
}
