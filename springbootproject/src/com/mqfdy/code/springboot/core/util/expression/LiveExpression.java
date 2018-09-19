package com.mqfdy.code.springboot.core.util.expression;

import org.eclipse.core.runtime.ListenerList;

// TODO: Auto-generated Javadoc
/**
 * A 'live' expression is something that conceptually one would like to think of
 * as an expression that returns a value. However, this expression provides a
 * listener-style interface so that interested parties can subscribe to be
 * notified when the value of the expression changes.
 *
 * @author mqfdy
 * @param <V>
 *            the value type
 */
public abstract class LiveExpression<V> {
	
	private ListenerList fListeners = new ListenerList();
 
	/**
	 * The last computed value of the expression.
	 */
	private V value;

	/**
	 * Instantiates a new live expression.
	 *
	 * @param initialValue
	 *            the initial value
	 */
	public LiveExpression(V initialValue) {
		this.value = initialValue;
	}

	/**
	 * Clients may call this method to request a recomputation of the expression's value from its inputs.
	 */
	public void refresh() {
		//V oldValue = value;
		V newValue = compute();
		if (!equals(newValue, value)) {
			value = newValue;
			changed();
		}
	}

	/**
	 * Implementation of value equals that works if either one of the values is null.
	 */
	private static <V> boolean equals(V a, V b) {
		if (a==null||b==null) {
			return a==b;
		} else {
			return a.equals(b);
		}
	}

	/**
	 * Compute.
	 *
	 * @author mqfdy
	 * @return the v
	 * @Date 2018-09-03 09:00
	 */
	protected abstract V compute();

	private void changed() {
		Object[] listeners = fListeners.getListeners();
		for (Object _l : listeners) {
			@SuppressWarnings("unchecked")
			ValueListener<V> l = (ValueListener<V>) _l;
			l.gotValue(this, value);
		}
	}

	/**
	 * Retrieves the current (cached) value of the expression.
	 *
	 * @author mqfdy
	 * @return the value
	 * @Date 2018-09-03 09:00
	 */
	public V getValue() {
		return value;
	}

	/**
	 * Adds the listener.
	 *
	 * @author mqfdy
	 * @param l
	 *            the l
	 * @Date 2018-09-03 09:00
	 */
	public void addListener(ValueListener<V> l) {
		fListeners.add(l);
		l.gotValue(this, value);
	}
	
	/**
	 * Removes the listener.
	 *
	 * @author mqfdy
	 * @param l
	 *            the l
	 * @Date 2018-09-03 09:00
	 */
	public void removeListener(ValueListener<V> l) {
		fListeners.remove(l);
	}

	/**
	 * Constant.
	 *
	 * @author mqfdy
	 * @param <V>
	 *            the value type
	 * @param value
	 *            the value
	 * @return the live expression
	 * @Date 2018-09-03 09:00
	 */
	public static <V> LiveExpression<V> constant(final V value) {
		//TODO: Constant expression can be implemented more efficiently they do not need really any of the
		// super class infrastructure since the value of a constant can never change. 
		return new LiveExpression<V>(value) {
			
			@Override
			protected V compute() {
				return value;
			}
			
			@Override
			public void addListener(ValueListener<V> l) {
				l.gotValue(this, value);
			}
			@Override
			public void removeListener(ValueListener<V> l) {
				//Ignore all listeners we will never notify anyone since
				//constants can't change
			}
			
			/* (non-Javadoc)
			 * @see org.springsource.ide.eclipse.gradle.core.util.expression.LiveExpression#refresh()
			 */
			@Override
			public void refresh() {
				//Ignore all refreshes... no need to refresh anything since
				//constants can't change
			}
		};
	}

}
