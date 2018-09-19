package com.mqfdy.code.springboot.core.util.expression;

// TODO: Auto-generated Javadoc
/**
 * A concrete implementation of LiveExpression that represents a single storage
 * cell in which a value of a given type can be stored.
 * <p>
 * Can be used in Mockup UIs for testing. Here a LiveVariable can take the place
 * of an input field in the GUI. Core functionality can then be tested against
 * the headless Mock in which it is easy to set values.
 *
 * @author zjing
 * @param <T>
 *            the generic type
 */
public class LiveVariable<T> extends LiveExpression<T> {

	/**
	 * Instantiates a new live variable.
	 *
	 * @param initialValue
	 *            the initial value
	 */
	public LiveVariable(T initialValue) {
		super(initialValue);
	}
	
	/**
	 * Instantiates a new live variable.
	 */
	public LiveVariable() {
		this(null);
	}

	private T storedValue;

	@Override
	protected T compute() {
		return storedValue;
	}

	/**
	 * Sets the value.
	 *
	 * @author mqfdy
	 * @param v
	 *            the new value
	 * @Date 2018-09-03 09:00
	 */
	public void setValue(T v) {
		storedValue = v;
		refresh();
	}

}
