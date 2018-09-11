package com.mqfdy.bom.project.util;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.MultiRule;
import org.eclipse.swt.widgets.Display;

// TODO: Auto-generated Javadoc
/**
 * The Class JobUtil.
 *
 * @author mqfdy
 */
public class JobUtil {

	/**
	 * Schedule this job with an appropriate default scheduling rule to avoid
	 * concurrency issues. This scheduling is safe, but it is very restrictive,
	 * so if it can be relaxed, and you care about this then don't use this
	 * method. Instead use the schedule method that allows specifying an
	 * explicit rule.
	 *
	 * @author mqfdy
	 * @param runable
	 *            the runable
	 * @return the job
	 * @Date 2018-09-03 09:00
	 */
	public static Job schedule(Operation2Runnable runable) {
		Job job = runable.asJob();
		schedule(job, buildRule());
		return job;
	}

	/**
	 * Schedule a piece of work without attaching any scheduling rule to the
	 * Job.
	 * <p>
	 * Be careful when using this method of running tasks because it provides no
	 * guarantees whatsoever about what other things may be executing
	 * concurrently.
	 *
	 * @author mqfdy
	 * @param rule
	 *            the rule
	 * @param runable
	 *            the runable
	 * @Date 2018-09-03 09:00
	 */
	public static void schedule(ISchedulingRule rule, Operation2Runnable runable) {
		schedule(runable.asJob(), rule);
	}


	/**
	 * Schedule this job as a "workspace job" (atomic operation, so only one
	 * resource delta generated for whole job).
	 *
	 * @author mqfdy
	 * @param runable
	 *            the runable
	 * @Date 2018-09-03 09:00
	 */
	public static void workspaceJob(Operation2Runnable runable) {
		Job job = runable.asWorkspaceJob();
		schedule(job);
	}
	
	/**
	 * Schedule as a "user" job. (Normally this means that the job will popup a
	 * dialog to show progress. But the user can send the job into background if
	 * they so wish.
	 * <p>
	 * This is "normal" behavior, but this behavior is not guaranteed. It can be
	 * changed by user preferences, or because other circumstances (e.g. the
	 * dialog will not pop, in the context of another modal dialog because
	 * Eclipse has a kind of "design rule" not to pop dialogs on top of
	 * eachother if it can be avoided.)
	 *
	 * @author mqfdy
	 * @param runable
	 *            the runable
	 * @return the job
	 * @Date 2018-09-03 09:00
	 */
	public static Job userJob(Operation2Runnable runable) {
		Job job = runable.asJob();
		job.setUser(true);
		schedule(job);
		return job;
	}
	
	/**
	 * Schedule.
	 *
	 * @author mqfdy
	 * @param job
	 *            the job
	 * @param rule
	 *            the rule
	 * @Date 2018-09-03 09:00
	 */
	private static void schedule(Job job, ISchedulingRule rule) {
		job.setPriority(Job.BUILD);
		job.setRule(rule);
		job.schedule();
	}
	
	/**
	 * Schedule.
	 *
	 * @author mqfdy
	 * @param job
	 *            the job
	 * @Date 2018-09-03 09:00
	 */
	private static void schedule(Job job) {
		schedule(job, buildRule());
	}
	

	/**
	 * Scheduling rule that conflicts only with itself and only contains itself.
	 * GradleRunnables that want to have a 'light' impact on blocking other jobs
	 * but still some guarantee that they won't trample over other GradleRunnable's
	 * should use this rule.
	 */
	public static final ISchedulingRule LIGHT_RULE = lightRule("LIGHT_RULE");

	/**
	 * Create a scheduling rule that conflicts only with itself and only
	 * contains itself. GradleRunnables that want to have a 'light' impact on
	 * blocking other jobs but still some guarantee that they won't trample over
	 * other things that require access to some internal shared resource that
	 * only they can access should use this rule to protect the resource.
	 *
	 * @author mqfdy
	 * @param name
	 *            the name
	 * @return the i scheduling rule
	 * @Date 2018-09-03 09:00
	 */
	public static ISchedulingRule lightRule(final String name) {
		return new ISchedulingRule() {
			public boolean contains(ISchedulingRule rule) {
				return rule == this;
			}

			public boolean isConflicting(ISchedulingRule rule) {
				return rule == this || rule.contains(this);
			}
			public String toString() {
				return name;
			};
		};
	}
	
	/**
	 * Value used to indicate we don't want to use a scheduling rule at all. GradleRunnable's 
	 * scheduled with this 'rule' are completely unconstrained and can run concurrently with
	 * anything else.
	 */
	public static final ISchedulingRule NO_RULE = null;

	/**
	 * Join a job (wait for it to complete). If the job is null, the method
	 * returns immediately. (This assumes that things returning jobs to join,
	 * may, on occasion, return null if there's was nothing to do and so there
	 * is nothing to wair for).
	 *
	 * @author mqfdy
	 * @param job
	 *            the job
	 * @Date 2018-09-03 09:00
	 */
	public static void join(Job job) {
		if (job!=null) {
			boolean done = false;
			do {
				try {
					job.join();
					done = true;
				} catch (InterruptedException e) {
				}
			} while (!done);
		}
	}
	
	/**
	 * Check canceled.
	 *
	 * @author mqfdy
	 * @param monitor
	 *            the monitor
	 * @throws OperationCanceledException
	 *             the operation canceled exception
	 * @Date 2018-09-03 09:00
	 */
	public static void checkCanceled(IProgressMonitor monitor) throws OperationCanceledException {
		if (monitor.isCanceled()) {
			throw new OperationCanceledException();
		}
	}

	/**
	 * Sleep method that puts the current thread to sleep for some time and then
	 * returns. This method should be safe to call from UI or non-ui threads.
	 * <p>
	 * When called from UI threads it will ensure the UI event queue keeps
	 * getting processed while sleeping.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	public static void sleep() {
		Display display = Display.getCurrent();
		if (display!=null) {
			//We are running in UI thread
			display.readAndDispatch();
			display.sleep();
		} else {
			try {
				//We are not running in UI thread
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
		}
	}

	/**
	 * What all Gradle related jobs should use instead of 'buildRule'. This is
	 * buildRule, but augmented so it also contains the LIGHT_RULE.
	 *
	 * @author mqfdy
	 * @return the i scheduling rule
	 * @Date 2018-09-03 09:00
	 */
	public static ISchedulingRule buildRule() {
		return new MultiRule(new ISchedulingRule[] {
				ResourcesPlugin.getWorkspace().getRuleFactory().buildRule(),
				LIGHT_RULE
		});
	}

}
