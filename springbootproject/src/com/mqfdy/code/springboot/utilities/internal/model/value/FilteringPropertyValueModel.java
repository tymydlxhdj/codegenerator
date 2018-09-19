/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package com.mqfdy.code.springboot.utilities.internal.model.value;

import com.mqfdy.code.springboot.utilities.Filter;
import com.mqfdy.code.springboot.utilities.model.event.PropertyChangeEvent;
import com.mqfdy.code.springboot.utilities.model.value.PropertyValueModel;

// TODO: Auto-generated Javadoc
/**
 * A <code>FilteringPropertyValueModel</code> wraps another
 * <code>PropertyValueModel</code> and uses a <code>Filter</code> to determine
 * when the wrapped value is to be returned by calls to <code>value()</code>.
 * <p>
 * As an alternative to building a <code>Filter</code>, a subclass of
 * <code>FilteringPropertyValueModel</code> can override the
 * <code>accept(Object)</code> method.
 * <p>
 * One, possibly undesirable, side-effect of using this value model is that it
 * must return *something* as the value. The default behavior is to return
 * <code>null</code> whenever the wrapped value is not "accepted", which can be
 * configured and/or overridden.
 *
 * @author mqfdy
 * @param <T>
 *            the generic type
 */
public class FilteringPropertyValueModel<T>
	extends PropertyValueModelWrapper<T>
	implements PropertyValueModel<T>
{
	
	/** The filter. */
	protected final Filter<T> filter;
	
	/** The default value. */
	protected final T defaultValue;


	// ********** constructors **********

	/**
	 * Construct a property value model with the specified nested property value
	 * model and a disabled filter. Use this constructor if you want to override
	 * the <code>accept(Object)</code> method instead of building a
	 * <code>Filter</code>. The default value will be <code>null</code>.
	 *
	 * @param valueHolder
	 *            the value holder
	 */
	public FilteringPropertyValueModel(PropertyValueModel<? extends T> valueHolder) {
		this(valueHolder, Filter.Disabled.<T>instance(), null);
	}

	/**
	 * Construct a property value model with the specified nested property value
	 * model, specified default value, and a disabled filter. Use this
	 * constructor if you want to override the <code>accept(Object)</code>
	 * method instead of building a <code>Filter</code> <em>and</em> you need to
	 * specify a default value other than <code>null</code>.
	 *
	 * @param valueHolder
	 *            the value holder
	 * @param defaultValue
	 *            the default value
	 */
	public FilteringPropertyValueModel(PropertyValueModel<? extends T> valueHolder, T defaultValue) {
		this(valueHolder, Filter.Disabled.<T>instance(), defaultValue);
	}

	/**
	 * Construct an property value model with the specified nested property
	 * value model and filter. The default value will be <code>null</code>.
	 *
	 * @param valueHolder
	 *            the value holder
	 * @param filter
	 *            the filter
	 */
	public FilteringPropertyValueModel(PropertyValueModel<? extends T> valueHolder, Filter<T> filter) {
		this(valueHolder, filter, null);
	}

	/**
	 * Construct an property value model with the specified nested property
	 * value model, filter, and default value.
	 *
	 * @param valueHolder
	 *            the value holder
	 * @param filter
	 *            the filter
	 * @param defaultValue
	 *            the default value
	 */
	public FilteringPropertyValueModel(PropertyValueModel<? extends T> valueHolder, Filter<T> filter, T defaultValue) {
		super(valueHolder);
		this.filter = filter;
		this.defaultValue = defaultValue;
	}


	// ********** PropertyValueModel implementation **********

	public T getValue() {
		return this.filterValue(this.valueHolder.getValue());
	}


	// ********** PropertyValueModelWrapper implementation **********

	@Override
	protected void valueChanged(PropertyChangeEvent event) {
		// filter the values before propagating the change event
		@SuppressWarnings("unchecked")
		Object oldValue = this.filterValue((T) event.getOldValue());
		@SuppressWarnings("unchecked")
		Object newValue = this.filterValue((T) event.getNewValue());
		this.firePropertyChanged(VALUE, oldValue, newValue);
	}


	// ********** queries **********

	/**
	 * If the specified value is "accepted" simply return it, otherwise return
	 * the default value.
	 *
	 * @author mqfdy
	 * @param value
	 *            the value
	 * @return the t
	 * @Date 2018-09-03 09:00
	 */
	protected T filterValue(T value) {
		return this.accept(value) ? value : this.defaultValue();
	}

	/**
	 * Return whether the <code>FilteringPropertyValueModel</code> should return
	 * the specified value from a call to the <code>value()</code> method; the
	 * value came from the nested property value model
	 * <p>
	 * This method can be overridden by a subclass as an alternative to building
	 * a <code>Filter</code>.
	 *
	 * @author mqfdy
	 * @param value
	 *            the value
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	protected boolean accept(T value) {
		return this.filter.accept(value);
	}

	/**
	 * Return the object that should be returned if the nested value was
	 * rejected by the filter. The default is <code>null</code>.
	 *
	 * @author mqfdy
	 * @return the t
	 * @Date 2018-09-03 09:00
	 */
	protected T defaultValue() {
		return this.defaultValue;
	}

}
