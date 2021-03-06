/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package com.mqfdy.code.springboot.utilities.internal.model.value;

import java.util.Iterator;
import java.util.ListIterator;

import com.mqfdy.code.springboot.utilities.internal.iterators.EmptyListIterator;
import com.mqfdy.code.springboot.utilities.internal.iterators.SingleElementListIterator;
import com.mqfdy.code.springboot.utilities.internal.model.AbstractModel;
import com.mqfdy.code.springboot.utilities.internal.model.ChangeSupport;
import com.mqfdy.code.springboot.utilities.internal.model.SingleAspectChangeSupport;
import com.mqfdy.code.springboot.utilities.model.event.PropertyChangeEvent;
import com.mqfdy.code.springboot.utilities.model.listener.ListChangeListener;
import com.mqfdy.code.springboot.utilities.model.listener.PropertyChangeListener;
import com.mqfdy.code.springboot.utilities.model.value.ListValueModel;
import com.mqfdy.code.springboot.utilities.model.value.PropertyValueModel;


// TODO: Auto-generated Javadoc
/**
 * An adapter that allows us to make a PropertyValueModel behave like a
 * read-only, single-element ListValueModel, sorta.
 * 
 * If the property's value is null, an empty iterator is returned (i.e. you
 * can't have a list with a null element).
 *
 * @author mqfdy
 * @param <E>
 *            the element type
 */
public class PropertyListValueModelAdapter<E>
	extends AbstractModel
	implements ListValueModel<E>
{

	/** The wrapped property value model. */
	protected final PropertyValueModel<? extends E> valueHolder;

	/** A listener that forwards any events fired by the value holder. */
	protected final PropertyChangeListener propertyChangeListener;

	/** Cache the value. */
	protected E value;


	// ********** constructors/initialization **********

	/**
	 * Wrap the specified property value model.
	 *
	 * @param valueHolder
	 *            the value holder
	 */
	public PropertyListValueModelAdapter(PropertyValueModel<? extends E> valueHolder) {
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
		return new SingleAspectChangeSupport(this, ListChangeListener.class, LIST_VALUES);
	}

	/**
	 * The wrapped value has changed, forward an equivalent list change event to
	 * our listeners.
	 *
	 * @author mqfdy
	 * @return the property change listener
	 * @Date 2018-09-03 09:00
	 */
	protected PropertyChangeListener buildPropertyChangeListener() {
		return new PropertyChangeListener() {
			@SuppressWarnings("unchecked")
			public void propertyChanged(PropertyChangeEvent event) {
				PropertyListValueModelAdapter.this.valueChanged((E) event.getNewValue());
			}
			@Override
			public String toString() {
				return "property change listener";
			}
		};
	}


	// ********** ListValueModel implementation **********

	public Iterator<E> iterator() {
		return this.listIterator();
	}

	public ListIterator<E> listIterator() {
		return (this.value == null) ?
					EmptyListIterator.<E>instance()
				:
					new SingleElementListIterator<E>(this.value);
	}

	public int size() {
		return (this.value == null) ? 0 : 1;
	}

	public E get(int index) {
		if (this.value == null) {
			throw this.ioobe(index, 0);
		}
		if (index > 0) {
			throw this.ioobe(index, 1);
		}
		return this.value;
	}

	/** The Constant EMPTY_OBJECT_ARRAY. */
	protected static final Object[] EMPTY_OBJECT_ARRAY = new Object[0];
	public Object[] toArray() {
		return (this.value == null) ? EMPTY_OBJECT_ARRAY : new Object[] {this.value};
	}


	// ********** extend change support **********

	/**
	 * Override to start listening to the value holder if necessary.
	 */
	@Override
	public void addListChangeListener(ListChangeListener listener) {
		if (this.hasNoListeners()) {
			this.engageModel();
		}
		super.addListChangeListener(listener);
	}

	/**
	 * Override to start listening to the value holder if necessary.
	 */
	@Override
	public void addListChangeListener(String listName, ListChangeListener listener) {
		if (listName == LIST_VALUES && this.hasNoListeners()) {
			this.engageModel();
		}
		super.addListChangeListener(listName, listener);
	}

	/**
	 * Override to stop listening to the value holder if appropriate.
	 */
	@Override
	public void removeListChangeListener(ListChangeListener listener) {
		super.removeListChangeListener(listener);
		if (this.hasNoListeners()) {
			this.disengageModel();
		}
	}

	/**
	 * Override to stop listening to the value holder if appropriate.
	 */
	@Override
	public void removeListChangeListener(String listName, ListChangeListener listener) {
		super.removeListChangeListener(listName, listener);
		if (listName == LIST_VALUES && this.hasNoListeners()) {
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
		return this.hasAnyListChangeListeners(LIST_VALUES);
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
	 * Ioobe.
	 *
	 * @author mqfdy
	 * @param index
	 *            the index
	 * @param size
	 *            the size
	 * @return the index out of bounds exception
	 * @Date 2018-09-03 09:00
	 */
	protected IndexOutOfBoundsException ioobe(int index, int size) {
		return new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
	}

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
		E oldValue = this.value;
		this.value = newValue;
		if (oldValue == null) {
			this.fireItemAdded(LIST_VALUES, 0, newValue);
		} else {
			if (newValue == null) {
				this.fireItemRemoved(LIST_VALUES, 0, oldValue);
			} else {
				this.fireItemReplaced(LIST_VALUES, 0, newValue, oldValue);
			}
		}
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.valueHolder);
	}

}
