package com.mqfdy.code.springboot.core.util;

import com.mqfdy.code.springboot.core.MicroProjectPlugin;


// TODO: Auto-generated Javadoc
/**
 * A continuation is a callback function that is called at the completion of
 * some asynchronous computation. When called it may receive some object
 * providing the result of the computation.
 *
 * @author lenovo
 * @param <T>
 *            the generic type
 */
public abstract class Continuation<T> {
	
	/**
	 * Apply.
	 *
	 * @author mqfdy
	 * @param value
	 *            the value
	 * @Date 2018-09-03 09:00
	 */
	public abstract void apply(T value);
	
	/**
	 * Error.
	 *
	 * @author mqfdy
	 * @param e
	 *            the e
	 * @Date 2018-09-03 09:00
	 */
	public void error(Throwable e) {
		MicroProjectPlugin.log(e);
	}
}
