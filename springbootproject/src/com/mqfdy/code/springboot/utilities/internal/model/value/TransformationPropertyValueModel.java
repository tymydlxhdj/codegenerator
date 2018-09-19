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

import com.mqfdy.code.springboot.utilities.internal.Transformer;
import com.mqfdy.code.springboot.utilities.model.event.PropertyChangeEvent;
import com.mqfdy.code.springboot.utilities.model.value.PropertyValueModel;

// TODO: Auto-generated Javadoc
/**
 * A <code>TransformationPropertyValueModel</code> wraps another
 * <code>PropertyValueModel</code> and uses a <code>Transformer</code> to
 * transform the wrapped value before it is returned by <code>value()</code>.
 * <p>
 * As an alternative to building a <code>Transformer</code>, a subclass of
 * <code>TransformationPropertyValueModel</code> can either override the
 * <code>transform_(Object)</code> method or, if something other than null
 * should be returned when the wrapped value is null, override the
 * <code>transform(Object)</code> method.
 *
 * @author mqfdy
 * @param <T1>
 *            the generic type
 * @param <T2>
 *            the generic type
 */
public class TransformationPropertyValueModel<T1, T2>
	extends PropertyValueModelWrapper<T1>
	implements PropertyValueModel<T2>
{
	
	/** The transformer. */
	protected final Transformer<T1, T2> transformer;


	// ********** constructors/initialization **********

	/**
	 * Construct a property value model with the specified nested property value
	 * model and the default transformer. Use this constructor if you want to
	 * override the <code>transform_(Object)</code> or
	 * <code>transform(Object)</code> method instead of building a
	 * <code>Transformer</code>.
	 *
	 * @param valueHolder
	 *            the value holder
	 */
	public TransformationPropertyValueModel(PropertyValueModel<? extends T1> valueHolder) {
		super(valueHolder);
		this.transformer = this.buildTransformer();
	}

	/**
	 * Construct an property value model with the specified nested property
	 * value model and transformer.
	 *
	 * @param valueHolder
	 *            the value holder
	 * @param transformer
	 *            the transformer
	 */
	public TransformationPropertyValueModel(PropertyValueModel<? extends T1> valueHolder, Transformer<T1, T2> transformer) {
		super(valueHolder);
		this.transformer = transformer;
	}

	/**
	 * Builds the transformer.
	 *
	 * @author mqfdy
	 * @return the transformer
	 * @Date 2018-09-03 09:00
	 */
	protected Transformer<T1, T2> buildTransformer() {
		return new DefaultTransformer();
	}


	// ********** PropertyValueModel implementation **********

	public T2 getValue() {
		// transform the object returned by the nested value model before returning it
		return this.transform(this.valueHolder.getValue());
	}


	// ********** PropertyValueModelWrapper implementation **********

	@Override
	protected void valueChanged(PropertyChangeEvent event) {
		// transform the values before propagating the change event
	    @SuppressWarnings("unchecked")
		Object oldValue = this.transform((T1) event.getOldValue());
	    @SuppressWarnings("unchecked")
		Object newValue = this.transform((T1) event.getNewValue());
		this.firePropertyChanged(VALUE, oldValue, newValue);
	}


	// ********** behavior **********

	/**
	 * Transform the specified value and return the result. This is called by
	 * #value() and #valueChanged(PropertyChangeEvent).
	 *
	 * @author mqfdy
	 * @param value
	 *            the value
	 * @return the t2
	 * @Date 2018-09-03 09:00
	 */
	protected T2 transform(T1 value) {
		return this.transformer.transform(value);
	}

	/**
	 * Transform the specified, non-null, value and return the result.
	 *
	 * @author mqfdy
	 * @param value
	 *            the value
	 * @return the t2
	 * @Date 2018-09-03 09:00
	 */
	protected T2 transform_(T1 value) {
		throw new UnsupportedOperationException();
	}


	// ********** default transformer **********

	/**
	 * The default transformer will return null if the wrapped value is null.
	 * If the wrapped value is not null, it is transformed by a subclass
	 * implementation of #transform_(Object).
	 */
	protected class DefaultTransformer implements Transformer<T1, T2> {
		public T2 transform(T1 value) {
			return (value == null) ? null : TransformationPropertyValueModel.this.transform_(value);
		}
	}

}
