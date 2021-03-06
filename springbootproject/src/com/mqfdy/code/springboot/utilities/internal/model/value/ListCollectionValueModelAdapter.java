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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

import com.mqfdy.code.springboot.utilities.internal.iterators.ReadOnlyIterator;
import com.mqfdy.code.springboot.utilities.internal.model.AbstractModel;
import com.mqfdy.code.springboot.utilities.internal.model.ChangeSupport;
import com.mqfdy.code.springboot.utilities.internal.model.SingleAspectChangeSupport;
import com.mqfdy.code.springboot.utilities.model.event.ListChangeEvent;
import com.mqfdy.code.springboot.utilities.model.listener.CollectionChangeListener;
import com.mqfdy.code.springboot.utilities.model.listener.ListChangeListener;
import com.mqfdy.code.springboot.utilities.model.value.CollectionValueModel;
import com.mqfdy.code.springboot.utilities.model.value.ListValueModel;



// TODO: Auto-generated Javadoc
/**
 * An adapter that allows us to make a ListValueModel behave like a read-only
 * CollectionValueModel, sorta.
 * 
 * We keep an internal collection somewhat in synch with the wrapped list.
 * 
 * NB: Since we only listen to the wrapped list when we have listeners ourselves
 * and we can only stay in synch with the wrapped list while we are listening to
 * it, results to various methods (e.g. #size(), value()) will be unpredictable
 * whenever we do not have any listeners. This should not be too painful since,
 * most likely, client objects will also be listeners.
 *
 * @author mqfdy
 * @param <E>
 *            the element type
 */
public class ListCollectionValueModelAdapter<E>
	extends AbstractModel
	implements CollectionValueModel<E>
{
	/** The wrapped list value model. */
	protected final ListValueModel<? extends E> listHolder;

	/** A listener that forwards any events fired by the list holder. */
	protected final ListChangeListener listChangeListener;

	/**
	 * Our internal collection, which holds the same elements as
	 * the wrapped list.
	 */
	// we declare this an ArrayList so we can use #clone() and #ensureCapacity(int)
	protected final ArrayList<E> collection;


	// ********** constructors/initialization **********

	/**
	 * Wrap the specified ListValueModel.
	 *
	 * @param listHolder
	 *            the list holder
	 */
	public ListCollectionValueModelAdapter(ListValueModel<? extends E> listHolder) {
		super();
		if (listHolder == null) {
			throw new NullPointerException();
		}
		this.listHolder = listHolder;
		this.listChangeListener = this.buildListChangeListener();
		this.collection = new ArrayList<E>();
		// postpone building the collection and listening to the underlying list
		// until we have listeners ourselves...
	}

	@Override
	protected ChangeSupport buildChangeSupport() {
		return new SingleAspectChangeSupport(this, CollectionChangeListener.class, VALUES);
	}

	/**
	 * The wrapped list has changed, forward an equivalent collection change
	 * event to our listeners.
	 *
	 * @author mqfdy
	 * @return the list change listener
	 * @Date 2018-09-03 09:00
	 */
	protected ListChangeListener buildListChangeListener() {
		return new ListChangeListener() {
			public void itemsAdded(ListChangeEvent event) {
				ListCollectionValueModelAdapter.this.itemsAdded(event);
			}
			public void itemsRemoved(ListChangeEvent event) {
				ListCollectionValueModelAdapter.this.itemsRemoved(event);
			}
			public void itemsReplaced(ListChangeEvent event) {
				ListCollectionValueModelAdapter.this.itemsReplaced(event);
			}
			public void itemsMoved(ListChangeEvent event) {
				ListCollectionValueModelAdapter.this.itemsMoved(event);
			}
			public void listCleared(ListChangeEvent event) {
				ListCollectionValueModelAdapter.this.listCleared(event);
			}
			public void listChanged(ListChangeEvent event) {
				ListCollectionValueModelAdapter.this.listChanged(event);
			}
			@Override
			public String toString() {
				return "list change listener";
			}
		};
	}


	// ********** CollectionValueModel implementation **********

	public Iterator<E> iterator() {
		// try to prevent backdoor modification of the list
		return new ReadOnlyIterator<E>(this.collection);
	}

	public int size() {
		return this.collection.size();
	}


	// ********** extend change support **********

	/**
	 * Override to start listening to the list holder if necessary.
	 */
	@Override
	public void addCollectionChangeListener(CollectionChangeListener listener) {
		if (this.hasNoListeners()) {
			this.engageModel();
		}
		super.addCollectionChangeListener(listener);
	}

	/**
	 * Override to start listening to the list holder if necessary.
	 */
	@Override
	public void addCollectionChangeListener(String collectionName, CollectionChangeListener listener) {
		if (collectionName == VALUES && this.hasNoListeners()) {
			this.engageModel();
		}
		super.addCollectionChangeListener(collectionName, listener);
	}

	/**
	 * Override to stop listening to the list holder if appropriate.
	 */
	@Override
	public void removeCollectionChangeListener(CollectionChangeListener listener) {
		super.removeCollectionChangeListener(listener);
		if (this.hasNoListeners()) {
			this.disengageModel();
		}
	}

	/**
	 * Override to stop listening to the list holder if appropriate.
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

	/**
	 * Return the index of the specified item, using object identity instead of
	 * equality.
	 *
	 * @author mqfdy
	 * @param o
	 *            the o
	 * @return the int
	 * @Date 2018-09-03 09:00
	 */
	protected int lastIdentityIndexOf(Object o) {
		return this.lastIdentityIndexOf(o, this.collection.size());
	}
	
	/**
	 * Return the last index of the specified item, starting just before the the
	 * specified endpoint, and using object identity instead of equality.
	 *
	 * @author mqfdy
	 * @param o
	 *            the o
	 * @param end
	 *            the end
	 * @return the int
	 * @Date 2018-09-03 09:00
	 */
	protected int lastIdentityIndexOf(Object o, int end) {
		for (int i = end; i-- > 0; ) {
			if (this.collection.get(i) == o) {
				return i;
			}
		}
		return -1;
	}
	

	// ********** behavior **********

	/**
	 * Builds the collection.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	protected void buildCollection() {
		Iterator<? extends E> stream = this.listHolder.iterator();
		// if the new list is empty, do nothing
		if (stream.hasNext()) {
			this.collection.ensureCapacity(this.listHolder.size());
			while (stream.hasNext()) {
				this.collection.add(stream.next());
			}
		}
	}

	/**
	 * Engage model.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	protected void engageModel() {
		this.listHolder.addListChangeListener(ListValueModel.LIST_VALUES, this.listChangeListener);
		// synch our collection *after* we start listening to the list holder,
		// since its value might change when a listener is added
		this.buildCollection();
	}

	/**
	 * Disengage model.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	protected void disengageModel() {
		this.listHolder.removeListChangeListener(ListValueModel.LIST_VALUES, this.listChangeListener);
		// clear out the collection when we are not listening to the list holder
		this.collection.clear();
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

	/**
	 * Items added.
	 *
	 * @author mqfdy
	 * @param event
	 *            the event
	 * @Date 2018-09-03 09:00
	 */
	protected void itemsAdded(ListChangeEvent event) {
		this.addItemsToCollection(this.items(event), this.collection, VALUES);
	}

	/**
	 * Removes the internal items.
	 *
	 * @author mqfdy
	 * @param items
	 *            the items
	 * @Date 2018-09-03 09:00
	 */
	protected void removeInternalItems(Iterator<E> items) {
		// we have to remove the items individually,
		// since they are probably not in sequence
		while (items.hasNext()) {
			Object removedItem = items.next();
			int index = this.lastIdentityIndexOf(removedItem);
			this.collection.remove(index);
			this.fireItemRemoved(VALUES, removedItem);
		}
	}

	/**
	 * Items removed.
	 *
	 * @author mqfdy
	 * @param event
	 *            the event
	 * @Date 2018-09-03 09:00
	 */
	protected void itemsRemoved(ListChangeEvent event) {
		this.removeInternalItems(this.items(event));
	}

	/**
	 * Items replaced.
	 *
	 * @author mqfdy
	 * @param event
	 *            the event
	 * @Date 2018-09-03 09:00
	 */
	protected void itemsReplaced(ListChangeEvent event) {
		this.removeInternalItems(this.replacedItems(event));
		this.addItemsToCollection(this.items(event), this.collection, VALUES);
	}

	/**
	 * Items moved.
	 *
	 * @author mqfdy
	 * @param event
	 *            the event
	 * @Date 2018-09-03 09:00
	 */
	protected void itemsMoved(ListChangeEvent event) {
		// do nothing? moving items in a list has no net effect on a collection...
	}

	/**
	 * List cleared.
	 *
	 * @author mqfdy
	 * @param event
	 *            the event
	 * @Date 2018-09-03 09:00
	 */
	protected void listCleared(ListChangeEvent event) {
		// put in empty check so we don't fire events unnecessarily
		if ( ! this.collection.isEmpty()) {
			this.collection.clear();
			this.fireCollectionCleared(VALUES);
		}
	}

	/**
	 * synchronize our internal collection with the wrapped list and fire the
	 * appropriate events.
	 *
	 * @author mqfdy
	 * @param event
	 *            the event
	 * @Date 2018-09-03 09:00
	 */
	protected void listChanged(ListChangeEvent event) {
		// put in empty check so we don't fire events unnecessarily
		if ( ! this.collection.isEmpty()) {
			@SuppressWarnings("unchecked")
			ArrayList<E> removedItems = (ArrayList<E>) this.collection.clone();
			this.collection.clear();
			this.fireItemsRemoved(VALUES, removedItems);
		}

		this.buildCollection();
		// put in empty check so we don't fire events unnecessarily
		if ( ! this.collection.isEmpty()) {
			this.fireItemsAdded(VALUES, this.collection);
		}
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.listHolder);
	}

}
