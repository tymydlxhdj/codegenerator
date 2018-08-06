package com.mqfdy.code.springboot.core.util;

import com.mqfdy.code.springboot.core.MicroProjectPlugin;


/**
 * A continuation is a callback function that is called at the completion of some
 * asynchronous computation. When called it may receive some object providing the result of
 * the computation.
 * 
 * @author lenovo
 */
public abstract class Continuation<T> {
	public abstract void apply(T value);
	public void error(Throwable e) {
		MicroProjectPlugin.log(e);
	}
}
