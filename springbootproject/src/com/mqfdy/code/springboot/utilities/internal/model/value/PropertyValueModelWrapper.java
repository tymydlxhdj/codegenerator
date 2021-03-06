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
import com.mqfdy.code.springboot.utilities.model.event.PropertyChangeEvent;
import com.mqfdy.code.springboot.utilities.model.listener.PropertyChangeListener;
import com.mqfdy.code.springboot.utilities.model.value.PropertyValueModel;

// TODO: Auto-generated Javadoc
/**
 * This abstract class provides the infrastructure needed to wrap another
 * property value model, "lazily" listen to it, and propagate its change
 * notifications.
 *
 * @author mqfdy
 * @param <T>
 *            the generic type
 */
public abstract class PropertyValueModelWrapper<T>
	extends AbstractModel
{

	/** The wrapped property value model. */
	protected final PropertyValueModel<? extends T> valueHolder;

	/** A listener that allows us to synch with changes to the wrapped value holder. */
	protected final PropertyChangeListener valueChangeListener;


	// ********** constructors/initialization **********

	/**
	 * Construct a property value model with the specified wrapped property
	 * value model. The value holder is required.
	 *
	 * @param valueHolder
	 *            the value holder
	 */
	protected PropertyValueModelWrapper(PropertyValueModel<? extends T> valueHolder) {
		super();
		if (valueHolder == null) {
			throw new NullPointerException();
		}
		this.valueHolder = valueHolder;
		this.valueChangeListener = this.buildValueChangeListener();
	}
	
	@Override
	protected ChangeSupport buildChangeSupport() {
		return new SingleAspectChangeSupport(this, PropertyChangeListener.class, PropertyValueModel.VALUE);
	}

	/**
	 * Builds the value change listener.
	 *
	 * @author mqfdy
	 * @return the property change listener
	 * @Date 2018-09-03 09:00
	 */
	protected PropertyChangeListener buildValueChangeListener() {
		return new PropertyChangeListener() {
			public void propertyChanged(PropertyChangeEvent event) {
				PropertyValueModelWrapper.this.valueChanged(event);
			}
		    @Override
			public String toString() {
				return "value change listener";
			}
		};
	}
	

	// ********** extend change support **********

	/**
	 * Extend to start listening to the nested model if necessary.
	 */
    @Override
	public synchronized void addPropertyChangeListener(PropertyChangeListener listener) {
		if (this.hasNoPropertyChangeListeners(PropertyValueModel.VALUE)) {
			this.engageValueHolder();
		}
		super.addPropertyChangeListener(listener);
	}
	
	/**
	 * Extend to start listening to the nested model if necessary.
	 */
    @Override
	public synchronized void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		if (propertyName == PropertyValueModel.VALUE && this.hasNoPropertyChangeListeners(PropertyValueModel.VALUE)) {
			this.engageValueHolder();
		}
		super.addPropertyChangeListener(propertyName, listener);
	}
	
	/**
	 * Extend to stop listening to the nested model if necessary.
	 */
    @Override
	public synchronized void removePropertyChangeListener(PropertyChangeListener listener) {
		super.removePropertyChangeListener(listener);
		if (this.hasNoPropertyChangeListeners(PropertyValueModel.VALUE)) {
			this.disengageValueHolder();
		}
	}
	
	/**
	 * Extend to stop listening to the nested model if necessary.
	 */
    @Override
	public synchronized void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		super.removePropertyChangeListener(propertyName, listener);
		if (propertyName == PropertyValueModel.VALUE && this.hasNoPropertyChangeListeners(PropertyValueModel.VALUE)) {
			this.disengageValueHolder();
		}
	}
	

	// ********** behavior **********
	
	/**
	 * Begin listening to the value holder.
	 */
	protected void engageValueHolder() {
		this.valueHolder.addPropertyChangeListener(PropertyValueModel.VALUE, this.valueChangeListener);
	}
	
	/**
	 * Stop listening to the value holder.
	 */
	protected void disengageValueHolder() {
		this.valueHolder.removePropertyChangeListener(PropertyValueModel.VALUE, this.valueChangeListener);
	}
	
	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.valueHolder);
	}


	// ********** property change support **********

	/**
	 * The value of the wrapped value holder has changed; propagate the change
	 * notification appropriately.
	 *
	 * @author mqfdy
	 * @param event
	 *            the event
	 * @Date 2018-09-03 09:00
	 */
	protected abstract void valueChanged(PropertyChangeEvent event);

}
