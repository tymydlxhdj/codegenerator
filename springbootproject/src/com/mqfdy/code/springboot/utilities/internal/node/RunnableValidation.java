/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package com.mqfdy.code.springboot.utilities.internal.node;

import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import com.mqfdy.code.springboot.utilities.internal.SynchronizedBoolean;

/**
 * This implementation of Runnable will asynchronously validate
 * a branch of nodes. It will wait until a shared "validate" flag is
 * set to validate the branch. Once the the branch is validated,
 * the thread will quiesce until the flag is set again.
 * 
 * There are two ways to stop this thread:
 * 	- Thread.interrupt()
 * 	- set the "continue" flag to false
 * For now, these two are equivalent; but in the future we might
 * pass the "continue" flag to the Node.validateBranch()
 * method so we can short-circuit the validation instead of waiting
 * until the entire branch is validated.
 */
public class RunnableValidation
	implements Runnable
{
	/** The node whose branch this thread will validate. */
	private final Node node;

	/** When this flag is set to true, we kick off the validation routine. */
	private final SynchronizedBoolean validateFlag;

	/** When this flag is set to false, we allow this thread to die. */
	private final SynchronizedBoolean continueFlag;

	/** Log any exceptions encountered during validation with the following settings. */
	private final  Logger exceptionLogger;
	private final Level exceptionLevel;
	private final String exceptionMessage;


	/**
	 * Construct a validation thread.
	 *
	 * @param node
	 *            the node
	 * @param validateFlag
	 *            the validate flag
	 * @param continueFlag
	 *            the continue flag
	 * @param exceptionLogger
	 *            the exception logger
	 * @param exceptionLevel
	 *            the exception level
	 * @param exceptionMessage
	 *            the exception message
	 */
	public RunnableValidation(
			Node node,
			SynchronizedBoolean validateFlag,
			SynchronizedBoolean continueFlag,
			Logger exceptionLogger,
			Level exceptionLevel,
			String exceptionMessage
	) {
		super();
		this.node = node;
		this.validateFlag = validateFlag;
		this.continueFlag = continueFlag;
		this.exceptionLogger = exceptionLogger;
		this.exceptionLevel = exceptionLevel;
		this.exceptionMessage = exceptionMessage;
	}


	// ********** Runnable Implementation **********

	/**
	 * Loop while the "continue" flag is true and the thread
	 * has not been interrupted by another thread.
	 * In each loop: Wait until the "validate" flag is set to true,
	 * then set it back to false and validate the branch of nodes.
	 */
	public void run() {
		while (this.continueFlag.isTrue()) {
			try {
				this.validateFlag.waitToSetFalse();
			} catch (InterruptedException ex) {
				// we were interrupted while waiting, must be quittin' time
				return;
			}
			this.validateNode();
		}
	}

	/**
	 * Validate the node, logging any exceptions.
	 * If an exception occurs, we terminate the validation until the
	 * "validation" flag is set again. Some exceptions occur because
	 * of concurrent changes to the model that occur *after* validation
	 * starts but before it completes an entire pass over the model. If that
	 * is the case, things should be OK; because the exception will be
	 * caught and the "validation" flag will have been set again *during* the
	 * initial validation pass. So when we return from catching the exception
	 * we will simply re-start the validation, hopefully with the model in
	 * a consistent state that will prevent another exception from
	 * occurring. Of course, if we have any exceptions that are *not*
	 * the result of the model being in an inconsistent state, we will
	 * probably fill the log; and those exceptions are bugs that need
	 * to be fixed. (!) Hopefully the user will notice the enormous log and
	 * contact support....  ~bjv
	 */
	private void validateNode() {
		try {
			this.node.validateBranch();
		} catch (Throwable ex) {
			this.logException(ex);
		}
	}

	/**
	 * We need to do all this because Logger#log(LogRecord) does not pass through
	 * Logger#doLog(LogRecord) like all the other Logger#log(...) methods.
	 */
	private void logException(Throwable ex) {
		LogRecord logRecord = new LogRecord(this.exceptionLevel, this.exceptionMessage);
		logRecord.setParameters(new Object[] { this.node.displayString() });
		logRecord.setThrown(ex);
		logRecord.setLoggerName(this.exceptionLogger.getName());
		logRecord.setResourceBundle(this.exceptionLogger.getResourceBundle());
		this.exceptionLogger.log(logRecord);
	}

}
