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

import java.util.Iterator;

import com.mqfdy.code.springboot.utilities.internal.iterators.EmptyIterator;
import com.mqfdy.code.springboot.utilities.internal.iterators.SingleElementIterator;
import com.mqfdy.code.springboot.utilities.internal.model.AbstractModel;
import com.mqfdy.code.springboot.utilities.internal.model.ChangeSupport;
import com.mqfdy.code.springboot.utilities.internal.model.SingleAspectChangeSupport;
import com.mqfdy.code.springboot.utilities.model.event.PropertyChangeEvent;
import com.mqfdy.code.springboot.utilities.model.listener.CollectionChangeListener;
import com.mqfdy.code.springboot.utilities.model.listener.PropertyChangeListener;
import com.mqfdy.code.springboot.utilities.model.value.CollectionValueModel;
import com.mqfdy.code.springboot.utilities.model.value.PropertyValueModel;



// TODO: Auto-generated Javadoc
/**
 * An adapter that allows us to make a PropertyValueModel behave like a
 * read-only, single-element CollectionValueModel, sorta.
 * 
 * If the property's value is null, an empty iterator is returned (i.e. you
 * can't have a collection with a null element).
 *
 * @author mqfdy
 * @param <E>
 *            the element type
 */
public class PropertyCollectionValueModelAdapter<E>
	extends AbstractModel
	implements CollectionValueModel<E>
{
	/** The wrapped property value model. */
	protected final PropertyValueModel<? extends E> valueHolder;

	/** A listener that forwards any events fired by the value holder. */
	protected final PropertyChangeListener propertyChangeListener;

	/** Cache the value. */
	protected E value;


	// ********** constructors/initialization **********

	/**
	 * Wrap the specified ListValueModel.
	 *
	 * @param valueHolder
	 *            the value holder
	 */
	public PropertyCollectionValueModelAdapter(PropertyValueModel<? extends E> valueHolder) {
		super();
		if (valueHolder == null) {
			throw new NullPointerException();
		}
		this.valueHolder = valueHolder;
		this.propertyChangeListener = this.buildPropertyChangeListener();
		// postpone building the value and listening to the underlying value
		// until we have listeners ourselves...
	}

	@Override
	protected ChangeSupport buildChangeSupport() {
		return new SingleAspectChangeSupport(this, CollectionChangeListener.class, VALUES);
	}

	/**
	 * The wrapped value has changed, forward an equivalent collection change
	 * event to our listeners.
	 *
	 * @author mqfdy
	 * @return the property change listener
	 * @Date 2018-09-03 09:00
	 */
	protected PropertyChangeListener buildPropertyChangeListener() {
		return new PropertyChangeListener() {
			@SuppressWarnings("unchecked")
			public void propertyChanged(PropertyChangeEvent event) {
				PropertyCollectionValueModelAdapter.this.valueChanged((E) event.getNewValue());
			}
			@Override
			public String toString() {
				return "property change listener";
			}
		};
	}


	// ********** CollectionValueModel implementation **********

	public Iterator<E> iterator() {
		return (this.value == null) ?
					EmptyIterator.<E>instance()
				:
					new SingleElementIterator<E>(this.value);
	}

	public int size() {
		return (this.value == null) ? 0 : 1;
	}


	// ********** extend change support **********

	/**
	 * Override to start listening to the value holder if necessary.
	 */
	@Override
	public void addCollectionChangeListener(CollectionChangeListener listener) {
		if (this.hasNoListeners()) {
			this.engageModel();
		}
		super.addCollectionChangeListener(listener);
	}

	/**
	 * Override to start listening to the value holder if necessary.
	 */
	@Override
	public void addCollectionChangeListener(String collectionName, CollectionChangeListener listener) {
		if (collectionName == VALUES && this.hasNoListeners()) {
			this.engageModel();
		}
		super.addCollectionChangeListener(collectionName, listener);
	}

	/**
	 * Override to stop listening to the value holder if appropriate.
	 */
	@Override
	public void removeCollectionChangeListener(CollectionChangeListener listener) {
		super.removeCollectionChangeListener(listener);
		if (this.hasNoListeners()) {
			this.disengageModel();
		}
	}

	/**
	 * Override to stop listening to the value holder if appropriate.
	 */
	@Override
	public void removeCollectionChangeListener(String collectionName, CollectionChangeListener listener) {
		super.removeCollectionChangeListener(collectionName, listener);
		if (collectionName == VALUES && this.hasNoListeners()) {
			this.disengageModel();
		}
	}


	// ********** queries **********

	/**
	 * Checks for listeners.
	 *
	 * @author mqfdy
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	protected boolean hasListeners() {
		return this.hasAnyCollectionChangeListeners(VALUES);
	}

	/**
	 * Checks for no listeners.
	 *
	 * @author mqfdy
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	protected boolean hasNoListeners() {
		return ! this.hasListeners();
	}


	// ********** behavior **********

	/**
	 * Engage model.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	protected void engageModel() {
		this.valueHolder.addPropertyChangeListener(PropertyValueModel.VALUE, this.propertyChangeListener);
		// synch our value *after* we start listening to the value holder,
		// since its value might change when a listener is added
		this.value = this.valueHolder.getValue();
	}

	/**
	 * Disengage model.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	protected void disengageModel() {
		this.valueHolder.removePropertyChangeListener(PropertyValueModel.VALUE, this.propertyChangeListener);
		// clear out the value when we are not listening to the value holder
		this.value = null;
	}

	/**
	 * synchronize our internal value with the wrapped value and fire the
	 * appropriate events.
	 *
	 * @author mqfdy
	 * @param newValue
	 *            the new value
	 * @Date 2018-09-03 09:00
	 */
	protected void valueChanged(E newValue) {
		// put in "empty" check so we don't fire events unnecessarily
		if (this.value != null) {
			E oldValue = this.value;
			this.value = null;
			this.fireItemRemoved(VALUES, oldValue);
		}
		this.value = newValue;
		// put in "empty" check so we don't fire events unnecessarily
		if (this.value != null) {
			this.fireItemAdded(VALUES, this.value);
		}
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.valueHolder);
	}

}
