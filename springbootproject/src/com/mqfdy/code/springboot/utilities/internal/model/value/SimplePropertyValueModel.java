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

import com.mqfdy.code.springboot.utilities.internal.model.AbstractModel;
import com.mqfdy.code.springboot.utilities.internal.model.ChangeSupport;
import com.mqfdy.code.springboot.utilities.internal.model.SingleAspectChangeSupport;
import com.mqfdy.code.springboot.utilities.model.listener.PropertyChangeListener;
import com.mqfdy.code.springboot.utilities.model.value.WritablePropertyValueModel;

// TODO: Auto-generated Javadoc
/**
 * Implementation of WritablePropertyValueModel that simply holds on to an
 * object and uses it as the value.
 *
 * @author mqfdy
 * @param <T>
 *            the generic type
 */
public class SimplePropertyValueModel<T>
	extends AbstractModel
	implements WritablePropertyValueModel<T>
{
	/** The value. */
	protected T value;


	/**
	 * Construct a PropertyValueModel for the specified value.
	 *
	 * @param value
	 *            the value
	 */
	public SimplePropertyValueModel(T value) {
		super();
		this.value = value;
	}

	/**
	 * Construct a PropertyValueModel with a starting value of null.
	 */
	public SimplePropertyValueModel() {
		this(null);
	}

	@Override
	protected ChangeSupport buildChangeSupport() {
		return new SingleAspectChangeSupport(this, PropertyChangeListener.class, VALUE);
	}


	public T getValue() {
		return this.value;
	}

	public void setValue(T value) {
		T old = this.value;
		this.value = value;
		this.firePropertyChanged(VALUE, old, value);
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.value);
	}

}
