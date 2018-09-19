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

import com.mqfdy.code.springboot.utilities.internal.CollectionTools;
import com.mqfdy.code.springboot.utilities.internal.iterators.ReadOnlyListIterator;
import com.mqfdy.code.springboot.utilities.internal.model.AbstractModel;
import com.mqfdy.code.springboot.utilities.internal.model.ChangeSupport;
import com.mqfdy.code.springboot.utilities.internal.model.SingleAspectChangeSupport;
import com.mqfdy.code.springboot.utilities.model.event.CollectionChangeEvent;
import com.mqfdy.code.springboot.utilities.model.listener.CollectionChangeListener;
import com.mqfdy.code.springboot.utilities.model.listener.ListChangeListener;
import com.mqfdy.code.springboot.utilities.model.value.CollectionValueModel;
import com.mqfdy.code.springboot.utilities.model.value.ListValueModel;



// TODO: Auto-generated Javadoc
/**
 * An adapter that allows us to make a CollectionValueModel behave like a
 * read-only ListValueModel, sorta.
 * 
 * To maintain a reasonably consistent appearance to client code, we keep an
 * internal list somewhat in synch with the wrapped collection.
 * 
 * NB: Since we only listen to the wrapped collection when we have listeners
 * ourselves and we can only stay in synch with the wrapped collection while we
 * are listening to it, results to various methods (e.g. #size(), getItem(int))
 * will be unpredictable whenever we do not have any listeners. This should not
 * be too painful since, most likely, client objects will also be listeners.
 *
 * @author mqfdy
 * @param <E>
 *            the element type
 */
public class CollectionListValueModelAdapter<E>
	extends AbstractModel
	implements ListValueModel<E>
{
	/** The wrapped collection value model. */
	protected final CollectionValueModel<? extends E> collectionHolder;

	/** A listener that forwards any events fired by the collection holder. */
	protected final CollectionChangeListener collectionChangeListener;

	/**
	 * Our internal list, which holds the same elements as
	 * the wrapped collection, but keeps them in order.
	 */
	// we declare this an ArrayList so we can use #clone() and #ensureCapacity(int)
	protected final ArrayList<E> list;


	// ********** constructors **********

	/**
	 * Wrap the specified CollectionValueModel.
	 *
	 * @param collectionHolder
	 *            the collection holder
	 */
	public CollectionListValueModelAdapter(CollectionValueModel<? extends E> collectionHolder) {
		super();
		if (collectionHolder == null) {
			throw new NullPointerException();
		}
		this.collectionHolder = collectionHolder;
		this.collectionChangeListener = this.buildCollectionChangeListener();
		this.list = new ArrayList<E>();
		// postpone building the list and listening to the underlying collection
		// until we have listeners ourselves...
	}


	// ********** initialization **********

	@Override
	protected ChangeSupport buildChangeSupport() {
		return new SingleAspectChangeSupport(this, ListChangeListener.class, LIST_VALUES);
	}

	/**
	 * The wrapped collection has changed, forward an equivalent list change
	 * event to our listeners.
	 *
	 * @author mqfdy
	 * @return the collection change listener
	 * @Date 2018-09-03 09:00
	 */
	protected CollectionChangeListener buildCollectionChangeListener() {
		return new CollectionChangeListener() {
			public void itemsAdded(CollectionChangeEvent event) {
				CollectionListValueModelAdapter.this.itemsAdded(event);
			}
			public void itemsRemoved(CollectionChangeEvent event) {
				CollectionListValueModelAdapter.this.itemsRemoved(event);
			}
			public void collectionCleared(CollectionChangeEvent event) {
				CollectionListValueModelAdapter.this.collectionCleared(event);
			}
			public void collectionChanged(CollectionChangeEvent event) {
				CollectionListValueModelAdapter.this.collectionChanged(event);
			}
			@Override
			public String toString() {
				return "collection change listener";
			}
		};
	}


	// ********** ListValueModel implementation **********

	public Iterator<E> iterator() {
		return this.listIterator();
	}

	public ListIterator<E> listIterator() {
		return new ReadOnlyListIterator<E>(this.list);
	}

	public E get(int index) {
		return this.list.get(index);
	}

	public int size() {
		return this.list.size();
	}

	public Object[] toArray() {
		return this.list.toArray();
	}


	// ********** extend change support **********

	/**
	 * Override to start listening to the collection holder if necessary.
	 */
	@Override
	public synchronized void addListChangeListener(ListChangeListener listener) {
		if (this.hasNoListeners()) {
			this.engageModel();
		}
		super.addListChangeListener(listener);
	}

	/**
	 * Override to start listening to the collection holder if necessary.
	 */
	@Override
	public synchronized void addListChangeListener(String listName, ListChangeListener listener) {
		if (listName == LIST_VALUES && this.hasNoListeners()) {
			this.engageModel();
		}
		super.addListChangeListener(listName, listener);
	}

	/**
	 * Override to stop listening to the collection holder if appropriate.
	 */
	@Override
	public void removeListChangeListener(ListChangeListener listener) {
		super.removeListChangeListener(listener);
		if (this.hasNoListeners()) {
			this.disengageModel();
		}
	}

	/**
	 * Override to stop listening to the collection holder if appropriate.
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
		return this.lastIdentityIndexOf(o, this.list.size());
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
			if (this.list.get(i) == o) {
				return i;
			}
		}
		return -1;
	}
	

	// ********** behavior **********

	/**
	 * Builds the list.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	protected void buildList() {
		Iterator<? extends E> stream = this.collectionHolder.iterator();
		// if the new collection is empty, do nothing
		if (stream.hasNext()) {
			this.list.ensureCapacity(this.collectionHolder.size());
			while (stream.hasNext()) {
				this.list.add(stream.next());
			}
			this.postBuildList();
		}
	}

	/**
	 * Allow subclasses to manipulate the internal list before
	 * sending out change notification.
	 */
	protected void postBuildList() {
		// the default is to do nothing...
	}

	/**
	 * Engage model.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	protected void engageModel() {
		this.collectionHolder.addCollectionChangeListener(CollectionValueModel.VALUES, this.collectionChangeListener);
		// synch our list *after* we start listening to the collection holder,
		// since its value might change when a listener is added
		this.buildList();
	}

	/**
	 * Disengage model.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	protected void disengageModel() {
		this.collectionHolder.removeCollectionChangeListener(CollectionValueModel.VALUES, this.collectionChangeListener);
		// clear out the list when we are not listening to the collection holder
		this.list.clear();
	}

	/**
	 * Items added.
	 *
	 * @author mqfdy
	 * @param e
	 *            the e
	 * @Date 2018-09-03 09:00
	 */
	protected void itemsAdded(CollectionChangeEvent e) {
		this.addItemsToList(this.indexToAddItems(), CollectionTools.list(this.items(e)), this.list, LIST_VALUES);
	}
	
	/**
	 * Index to add items.
	 *
	 * @author mqfdy
	 * @return the int
	 * @Date 2018-09-03 09:00
	 */
	protected int indexToAddItems() {
		return this.list.size();
	}

	/**
	 * Items.
	 *
	 * @author mqfdy
	 * @param e
	 *            the e
	 * @return the iterator
	 * @Date 2018-09-03 09:00
	 */
	@SuppressWarnings("unchecked")
	protected Iterator<E> items(CollectionChangeEvent e) {
		return (Iterator<E>) e.items();
	}

	/**
	 * Items removed.
	 *
	 * @author mqfdy
	 * @param e
	 *            the e
	 * @Date 2018-09-03 09:00
	 */
	protected void itemsRemoved(CollectionChangeEvent e) {
		// we have to remove the items individually,
		// since they are probably not in sequence
		for (Iterator<E> stream = this.items(e); stream.hasNext(); ) {
			this.removeItemFromList(this.lastIdentityIndexOf(stream.next()), this.list, LIST_VALUES);
		}
	}

	/**
	 * Collection cleared.
	 *
	 * @author mqfdy
	 * @param e
	 *            the e
	 * @Date 2018-09-03 09:00
	 */
	protected void collectionCleared(CollectionChangeEvent e) {
		this.clearList(this.list, LIST_VALUES);
	}
	
	/**
	 * synchronize our internal list with the wrapped collection and fire the
	 * appropriate events.
	 *
	 * @author mqfdy
	 * @param e
	 *            the e
	 * @Date 2018-09-03 09:00
	 */
	protected void collectionChanged(CollectionChangeEvent e) {
		// put in empty check so we don't fire events unnecessarily
		if ( ! this.list.isEmpty()) {
			@SuppressWarnings("unchecked")
			ArrayList<E> removedItems = (ArrayList<E>) this.list.clone();
			this.list.clear();
			this.fireItemsRemoved(LIST_VALUES, 0, removedItems);
		}

		this.buildList();
		// put in empty check so we don't fire events unnecessarily
		if ( ! this.list.isEmpty()) {
			this.fireItemsAdded(LIST_VALUES, 0, this.list);
		}
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.collectionHolder);
	}

}
