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

import java.util.ListIterator;

import com.mqfdy.code.springboot.utilities.internal.model.AbstractModel;
import com.mqfdy.code.springboot.utilities.internal.model.ChangeSupport;
import com.mqfdy.code.springboot.utilities.internal.model.SingleAspectChangeSupport;
import com.mqfdy.code.springboot.utilities.model.event.ListChangeEvent;
import com.mqfdy.code.springboot.utilities.model.listener.ListChangeListener;
import com.mqfdy.code.springboot.utilities.model.value.ListValueModel;


// TODO: Auto-generated Javadoc
/**
 * This abstract class provides the infrastructure needed to wrap another list
 * value model, "lazily" listen to it, and propagate its change notifications.
 *
 * @author mqfdy
 * @param <E>
 *            the element type
 */
public abstract class ListValueModelWrapper<E>
	extends AbstractModel
{

	/** The wrapped list value model. */
	protected final ListValueModel<? extends E> listHolder;

	/** A listener that allows us to synch with changes to the wrapped list holder. */
	protected final ListChangeListener listChangeListener;


	// ********** constructors **********

	/**
	 * Construct a list value model with the specified wrapped list value model.
	 *
	 * @param listHolder
	 *            the list holder
	 */
	protected ListValueModelWrapper(ListValueModel<? extends E> listHolder) {
		super();
		if (listHolder == null) {
			throw new NullPointerException();
		}
		this.listHolder = listHolder;
		this.listChangeListener = this.buildListChangeListener();
	}
	

	// ********** initialization **********

	@Override
	protected ChangeSupport buildChangeSupport() {
		return new SingleAspectChangeSupport(this, ListChangeListener.class, ListValueModel.LIST_VALUES);
	}

	/**
	 * Builds the list change listener.
	 *
	 * @author mqfdy
	 * @return the list change listener
	 * @Date 2018-09-03 09:00
	 */
	protected ListChangeListener buildListChangeListener() {
		return new ListChangeListener() {
			public void itemsAdded(ListChangeEvent event) {
				ListValueModelWrapper.this.itemsAdded(event);
			}
			public void itemsRemoved(ListChangeEvent event) {
				ListValueModelWrapper.this.itemsRemoved(event);
			}
			public void itemsReplaced(ListChangeEvent event) {
				ListValueModelWrapper.this.itemsReplaced(event);
			}
			public void itemsMoved(ListChangeEvent event) {
				ListValueModelWrapper.this.itemsMoved(event);
			}
			public void listCleared(ListChangeEvent event) {
				ListValueModelWrapper.this.listCleared(event);
			}
			public void listChanged(ListChangeEvent event) {
				ListValueModelWrapper.this.listChanged(event);
			}
			@Override
			public String toString() {
				return "list change listener";
			}
		};
	}


	// ********** extend change support **********

	/**
	 * Extend to start listening to the nested model if necessary.
	 */
	@Override
	public synchronized void addListChangeListener(ListChangeListener listener) {
		if (this.hasNoListChangeListeners(ListValueModel.LIST_VALUES)) {
			this.engageModel();
		}
		super.addListChangeListener(listener);
	}
	
	/**
	 * Extend to start listening to the nested model if necessary.
	 */
	@Override
	public synchronized void addListChangeListener(String listName, ListChangeListener listener) {
		if (listName == ListValueModel.LIST_VALUES && this.hasNoListChangeListeners(ListValueModel.LIST_VALUES)) {
			this.engageModel();
		}
		super.addListChangeListener(listName, listener);
	}
	
	/**
	 * Extend to stop listening to the nested model if necessary.
	 */
	@Override
	public synchronized void removeListChangeListener(ListChangeListener listener) {
		super.removeListChangeListener(listener);
		if (this.hasNoListChangeListeners(ListValueModel.LIST_VALUES)) {
			this.disengageModel();
		}
	}
	
	/**
	 * Extend to stop listening to the nested model if necessary.
	 */
	@Override
	public synchronized void removeListChangeListener(String listName, ListChangeListener listener) {
		super.removeListChangeListener(listName, listener);
		if (listName == ListValueModel.LIST_VALUES && this.hasNoListChangeListeners(ListValueModel.LIST_VALUES)) {
			this.disengageModel();
		}
	}
	

	// ********** behavior **********

	/**
	 * Start listening to the list holder.
	 */
	protected void engageModel() {
		this.listHolder.addListChangeListener(ListValueModel.LIST_VALUES, this.listChangeListener);
	}

	/**
	 * Stop listening to the list holder.
	 */
	protected void disengageModel() {
		this.listHolder.removeListChangeListener(ListValueModel.LIST_VALUES, this.listChangeListener);
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.listHolder);
	}

	/**
	 * Items.
	 *
	 * @author mqfdy
	 * @param event
	 *            the event
	 * @return the list iterator
	 * @Date 2018-09-03 09:00
	 */
	// minimize suppressed warnings
	@SuppressWarnings("unchecked")
	protected ListIterator<E> items(ListChangeEvent event) {
		return (ListIterator<E>) event.items();
	}

	/**
	 * Replaced items.
	 *
	 * @author mqfdy
	 * @param event
	 *            the event
	 * @return the list iterator
	 * @Date 2018-09-03 09:00
	 */
	// minimize suppressed warnings
	@SuppressWarnings("unchecked")
	protected ListIterator<E> replacedItems(ListChangeEvent event) {
		return (ListIterator<E>) event.replacedItems();
	}


	// ********** list change support **********

	/**
	 * Items were added to the wrapped list holder; propagate the change
	 * notification appropriately.
	 *
	 * @author mqfdy
	 * @param event
	 *            the event
	 * @Date 2018-09-03 09:00
	 */
	protected abstract void itemsAdded(ListChangeEvent event);

	/**
	 * Items were removed from the wrapped list holder; propagate the change
	 * notification appropriately.
	 *
	 * @author mqfdy
	 * @param event
	 *            the event
	 * @Date 2018-09-03 09:00
	 */
	protected abstract void itemsRemoved(ListChangeEvent event);

	/**
	 * Items were replaced in the wrapped list holder; propagate the change
	 * notification appropriately.
	 *
	 * @author mqfdy
	 * @param event
	 *            the event
	 * @Date 2018-09-03 09:00
	 */
	protected abstract void itemsReplaced(ListChangeEvent event);

	/**
	 * Items were moved in the wrapped list holder; propagate the change
	 * notification appropriately.
	 *
	 * @author mqfdy
	 * @param event
	 *            the event
	 * @Date 2018-09-03 09:00
	 */
	protected abstract void itemsMoved(ListChangeEvent event);

	/**
	 * The wrapped list holder was cleared; propagate the change notification
	 * appropriately.
	 *
	 * @author mqfdy
	 * @param event
	 *            the event
	 * @Date 2018-09-03 09:00
	 */
	protected abstract void listCleared(ListChangeEvent event);

	/**
	 * The value of the wrapped list holder has changed; propagate the change
	 * notification appropriately.
	 *
	 * @author mqfdy
	 * @param event
	 *            the event
	 * @Date 2018-09-03 09:00
	 */
	protected abstract void listChanged(ListChangeEvent event);

}
