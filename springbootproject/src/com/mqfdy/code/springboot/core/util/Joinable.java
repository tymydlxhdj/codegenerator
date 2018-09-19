package com.mqfdy.code.springboot.core.util;

/**
 * A 'joinable' is some representative of an asynch computation that can be
 * waited for by calling the 'join' method.
 *
 * @author lenovo
 * @param <T>
 *            the generic type
 */
public interface Joinable<T> {

	/**
	 * Block current thread until some asynchronous computation is finished. 
	 * If the asynch computation ended by returning a value then this value is returned by
	 * join. If the computation ended by throwing an exception than this exception is
	 * rethrown in the current thread by join.
	 */
	T join() throws Exception;
	
}
