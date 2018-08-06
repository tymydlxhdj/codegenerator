package com.mqfdy.code.springboot.core.util;

/**
 * A NullJoinable represents something that waits for an 'empty' amount of work.
 * As such join simply returns immediately as there is never anything to wait for.
 * <p>
 * Return an instance of this class wherever a Joinable is expected but there is 
 * no work to do.
 * 
 * @author lenovo
 */
public class NullJoinable<T> implements Joinable<T> {

	private T value = null;

	public NullJoinable() {
	}
	
	public NullJoinable(T value) {
		this.value = value;
	}
	
	public T join() throws Exception {
		return value;
	}

}
