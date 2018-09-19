/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package com.mqfdy.code.springboot.utilities.internal.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.mqfdy.code.springboot.utilities.internal.CollectionTools;
import com.mqfdy.code.springboot.utilities.internal.HashBag;
import com.mqfdy.code.springboot.utilities.internal.StringTools;
import com.mqfdy.code.springboot.utilities.internal.iterators.ArrayIterator;
import com.mqfdy.code.springboot.utilities.model.Model;
import com.mqfdy.code.springboot.utilities.model.event.CollectionChangeEvent;
import com.mqfdy.code.springboot.utilities.model.event.ListChangeEvent;
import com.mqfdy.code.springboot.utilities.model.event.PropertyChangeEvent;
import com.mqfdy.code.springboot.utilities.model.event.StateChangeEvent;
import com.mqfdy.code.springboot.utilities.model.event.TreeChangeEvent;
import com.mqfdy.code.springboot.utilities.model.listener.CollectionChangeListener;
import com.mqfdy.code.springboot.utilities.model.listener.ListChangeListener;
import com.mqfdy.code.springboot.utilities.model.listener.PropertyChangeListener;
import com.mqfdy.code.springboot.utilities.model.listener.StateChangeListener;
import com.mqfdy.code.springboot.utilities.model.listener.TreeChangeListener;


// TODO: Auto-generated Javadoc
/**
 * Convenience implementation of Model protocol.
 */
public abstract class AbstractModel implements Model, Serializable {
	/**
	 * Delegate state/property/collection/list/tree change support to this
	 * helper object. The change support object is "lazy-initialized".
	 */
	private ChangeSupport changeSupport;


	// ********** constructors/initialization **********

	/**
	 * Default constructor.
	 * This will call #initialize() on the newly-created instance.
	 */
	protected AbstractModel() {
		super();
		this.initialize();
	}

	/**
	 * Initialize.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	protected void initialize() {
		// do nothing by default
	}

	/**
	 * This accessor will build the change support when required.
	 *
	 * @author mqfdy
	 * @return the change support
	 * @Date 2018-09-03 09:00
	 */
	protected synchronized ChangeSupport getChangeSupport() {
		if (this.changeSupport == null) {
			this.changeSupport = this.buildChangeSupport();
		}
		return this.changeSupport;
	}

	/**
	 * Allow subclasses to tweak the change support used.
	 *
	 * @author mqfdy
	 * @return the change support
	 * @Date 2018-09-03 09:00
	 */
	protected ChangeSupport buildChangeSupport() {
		return new ChangeSupport(this);
	}


	// ********** state change support **********

	public synchronized void addStateChangeListener(StateChangeListener listener) {
		this.getChangeSupport().addStateChangeListener(listener);
	}

	public synchronized void removeStateChangeListener(StateChangeListener listener) {
		this.getChangeSupport().removeStateChangeListener(listener);
	}

	/**
	 * Fire state changed.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	protected final void fireStateChanged() {
		this.getChangeSupport().fireStateChanged();
	}

	/**
	 * Fire state changed.
	 *
	 * @author mqfdy
	 * @param event
	 *            the event
	 * @Date 2018-09-03 09:00
	 */
	protected final void fireStateChanged(StateChangeEvent event) {
		this.getChangeSupport().fireStateChanged(event);
	}


	// ********** property change support **********

	public synchronized void addPropertyChangeListener(PropertyChangeListener listener) {
		this.getChangeSupport().addPropertyChangeListener(listener);
	}

	public synchronized void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		this.getChangeSupport().addPropertyChangeListener(propertyName, listener);
	}

	public synchronized void removePropertyChangeListener(PropertyChangeListener listener) {
		this.getChangeSupport().removePropertyChangeListener(listener);
	}

	public synchronized void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		this.getChangeSupport().removePropertyChangeListener(propertyName, listener);
	}

	/**
	 * Fire property changed.
	 *
	 * @author mqfdy
	 * @param propertyName
	 *            the property name
	 * @param oldValue
	 *            the old value
	 * @param newValue
	 *            the new value
	 * @Date 2018-09-03 09:00
	 */
	protected final void firePropertyChanged(String propertyName, Object oldValue, Object newValue) {
		this.getChangeSupport().firePropertyChanged(propertyName, oldValue, newValue);
	}

	/**
	 * Fire property changed.
	 *
	 * @author mqfdy
	 * @param propertyName
	 *            the property name
	 * @param oldValue
	 *            the old value
	 * @param newValue
	 *            the new value
	 * @Date 2018-09-03 09:00
	 */
	protected final void firePropertyChanged(String propertyName, int oldValue, int newValue) {
		this.getChangeSupport().firePropertyChanged(propertyName, oldValue, newValue);
	}

	/**
	 * Fire property changed.
	 *
	 * @author mqfdy
	 * @param propertyName
	 *            the property name
	 * @param oldValue
	 *            the old value
	 * @param newValue
	 *            the new value
	 * @Date 2018-09-03 09:00
	 */
	protected final void firePropertyChanged(String propertyName, boolean oldValue, boolean newValue) {
		this.getChangeSupport().firePropertyChanged(propertyName, oldValue, newValue);
	}

	/**
	 * Fire property changed.
	 *
	 * @author mqfdy
	 * @param propertyName
	 *            the property name
	 * @param newValue
	 *            the new value
	 * @Date 2018-09-03 09:00
	 */
	protected final void firePropertyChanged(String propertyName, Object newValue) {
		this.getChangeSupport().firePropertyChanged(propertyName, null, newValue);
	}

	/**
	 * Fire property changed.
	 *
	 * @author mqfdy
	 * @param event
	 *            the event
	 * @Date 2018-09-03 09:00
	 */
	protected final void firePropertyChanged(PropertyChangeEvent event) {
		this.getChangeSupport().firePropertyChanged(event);
	}


	// ********** collection change support **********

	public synchronized void addCollectionChangeListener(CollectionChangeListener listener) {
		this.getChangeSupport().addCollectionChangeListener(listener);
	}

	public synchronized void addCollectionChangeListener(String collectionName, CollectionChangeListener listener) {
		this.getChangeSupport().addCollectionChangeListener(collectionName, listener);
	}

	public synchronized void removeCollectionChangeListener(CollectionChangeListener listener) {
		this.getChangeSupport().removeCollectionChangeListener(listener);
	}

	public synchronized void removeCollectionChangeListener(String collectionName, CollectionChangeListener listener) {
		this.getChangeSupport().removeCollectionChangeListener(collectionName, listener);
	}

	/**
	 * Fire item added.
	 *
	 * @author mqfdy
	 * @param collectionName
	 *            the collection name
	 * @param addedItem
	 *            the added item
	 * @Date 2018-09-03 09:00
	 */
	protected final void fireItemAdded(String collectionName, Object addedItem) {
		this.getChangeSupport().fireItemAdded(collectionName, addedItem);
	}

	/**
	 * Fire items added.
	 *
	 * @author mqfdy
	 * @param collectionName
	 *            the collection name
	 * @param addedItems
	 *            the added items
	 * @Date 2018-09-03 09:00
	 */
	protected final void fireItemsAdded(String collectionName, Collection<?> addedItems) {
		this.getChangeSupport().fireItemsAdded(collectionName, addedItems);
	}

	/**
	 * Fire items added.
	 *
	 * @author mqfdy
	 * @param event
	 *            the event
	 * @Date 2018-09-03 09:00
	 */
	protected final void fireItemsAdded(CollectionChangeEvent event) {
		this.getChangeSupport().fireItemsAdded(event);
	}

	/**
	 * Fire item removed.
	 *
	 * @author mqfdy
	 * @param collectionName
	 *            the collection name
	 * @param removedItem
	 *            the removed item
	 * @Date 2018-09-03 09:00
	 */
	protected final void fireItemRemoved(String collectionName, Object removedItem) {
		this.getChangeSupport().fireItemRemoved(collectionName, removedItem);
	}

	/**
	 * Fire items removed.
	 *
	 * @author mqfdy
	 * @param collectionName
	 *            the collection name
	 * @param removedItems
	 *            the removed items
	 * @Date 2018-09-03 09:00
	 */
	protected final void fireItemsRemoved(String collectionName, Collection<?> removedItems) {
		this.getChangeSupport().fireItemsRemoved(collectionName, removedItems);
	}

	/**
	 * Fire items removed.
	 *
	 * @author mqfdy
	 * @param event
	 *            the event
	 * @Date 2018-09-03 09:00
	 */
	protected final void fireItemsRemoved(CollectionChangeEvent event) {
		this.getChangeSupport().fireItemsRemoved(event);
	}

	/**
	 * Fire collection cleared.
	 *
	 * @author mqfdy
	 * @param collectionName
	 *            the collection name
	 * @Date 2018-09-03 09:00
	 */
	protected final void fireCollectionCleared(String collectionName) {
		this.getChangeSupport().fireCollectionCleared(collectionName);
	}

	/**
	 * Fire collection cleared.
	 *
	 * @author mqfdy
	 * @param event
	 *            the event
	 * @Date 2018-09-03 09:00
	 */
	protected final void fireCollectionCleared(CollectionChangeEvent event) {
		this.getChangeSupport().fireCollectionCleared(event);
	}

	/**
	 * Fire collection changed.
	 *
	 * @author mqfdy
	 * @param collectionName
	 *            the collection name
	 * @Date 2018-09-03 09:00
	 */
	protected final void fireCollectionChanged(String collectionName) {
		this.getChangeSupport().fireCollectionChanged(collectionName);
	}

	/**
	 * Fire collection changed.
	 *
	 * @author mqfdy
	 * @param event
	 *            the event
	 * @Date 2018-09-03 09:00
	 */
	protected final void fireCollectionChanged(CollectionChangeEvent event) {
		this.getChangeSupport().fireCollectionChanged(event);
	}

	/**
	 * Convenience method. Add the specified item to the specified bound
	 * collection and fire the appropriate event if necessary. Return whether
	 * the collection changed.
	 *
	 * @param <E>
	 *            the element type
	 * @param item
	 *            the item
	 * @param collection
	 *            the collection
	 * @param collectionName
	 *            the collection name
	 * @return true, if successful
	 * @see java.util.Collection#add(Object)
	 */
	protected <E> boolean addItemToCollection(E item, Collection<E> collection, String collectionName) {
		if (collection.add(item)) {
			this.fireItemAdded(collectionName, item);
			return true;
		}
		return false;
	}

	/**
	 * Convenience method. Add the specified items to the specified bound
	 * collection and fire the appropriate event if necessary. Return whether
	 * collection changed.
	 *
	 * @param <E>
	 *            the element type
	 * @param items
	 *            the items
	 * @param collection
	 *            the collection
	 * @param collectionName
	 *            the collection name
	 * @return true, if successful
	 * @see java.util.Collection#addAll(java.util.Collection)
	 */
	protected <E> boolean addItemsToCollection(E[] items, Collection<E> collection, String collectionName) {
		return this.addItemsToCollection(new ArrayIterator<E>(items), collection, collectionName);
	}

	/**
	 * Convenience method. Add the specified items to the specified bound
	 * collection and fire the appropriate event if necessary. Return whether
	 * collection changed.
	 *
	 * @param <E>
	 *            the element type
	 * @param items
	 *            the items
	 * @param collection
	 *            the collection
	 * @param collectionName
	 *            the collection name
	 * @return true, if successful
	 * @see java.util.Collection#addAll(java.util.Collection)
	 */
	protected <E> boolean addItemsToCollection(Iterable<? extends E> items, Collection<E> collection, String collectionName) {
		return this.addItemsToCollection(items.iterator(), collection, collectionName);
	}

	/**
	 * Convenience method. Add the specified items to the specified bound
	 * collection and fire the appropriate event if necessary. Return whether
	 * collection changed.
	 *
	 * @param <E>
	 *            the element type
	 * @param items
	 *            the items
	 * @param collection
	 *            the collection
	 * @param collectionName
	 *            the collection name
	 * @return true, if successful
	 * @see java.util.Collection#addAll(java.util.Collection)
	 */
	protected <E> boolean addItemsToCollection(Iterator<? extends E> items, Collection<E> collection, String collectionName) {
		Collection<E> addedItems = null;
		while (items.hasNext()) {
			E item = items.next();
			if (collection.add(item)) {
				if (addedItems == null) {
					addedItems = new ArrayList<E>();
				}
				addedItems.add(item);
			}
		}
		if (addedItems != null) {
			this.fireItemsAdded(collectionName, addedItems);
			return true;
		}
		return false;
	}

	/**
	 * Convenience method. Remove the specified item from the specified bound
	 * collection and fire the appropriate event if necessary. Return whether
	 * the collection changed.
	 *
	 * @param item
	 *            the item
	 * @param collection
	 *            the collection
	 * @param collectionName
	 *            the collection name
	 * @return true, if successful
	 * @see java.util.Collection#remove(Object)
	 */
	protected boolean removeItemFromCollection(Object item, Collection<?> collection, String collectionName) {
		if (collection.remove(item)) {
			this.fireItemRemoved(collectionName, item);
			return true;
		}
		return false;
	}

	/**
	 * Convenience method. Remove the specified items from the specified bound
	 * collection and fire the appropriate event if necessary. Return whether
	 * the collection changed.
	 *
	 * @param items
	 *            the items
	 * @param collection
	 *            the collection
	 * @param collectionName
	 *            the collection name
	 * @return true, if successful
	 * @see java.util.Collection#removeAll(java.util.Collection)
	 */
	protected boolean removeItemsFromCollection(Object[] items, Collection<?> collection, String collectionName) {
		return this.removeItemsFromCollection(new ArrayIterator<Object>(items), collection, collectionName);
	}

	/**
	 * Convenience method. Remove the specified items from the specified bound
	 * collection and fire the appropriate event if necessary. Return whether
	 * the collection changed.
	 *
	 * @param items
	 *            the items
	 * @param collection
	 *            the collection
	 * @param collectionName
	 *            the collection name
	 * @return true, if successful
	 * @see java.util.Collection#removeAll(java.util.Collection)
	 */
	protected boolean removeItemsFromCollection(Iterable<?> items, Collection<?> collection, String collectionName) {
		return this.removeItemsFromCollection(items.iterator(), collection, collectionName);
	}

	/**
	 * Convenience method. Remove the specified items from the specified bound
	 * collection and fire the appropriate event if necessary. Return whether
	 * the collection changed.
	 *
	 * @param items
	 *            the items
	 * @param collection
	 *            the collection
	 * @param collectionName
	 *            the collection name
	 * @return true, if successful
	 * @see java.util.Collection#removeAll(java.util.Collection)
	 */
	protected boolean removeItemsFromCollection(Iterator<?> items, Collection<?> collection, String collectionName) {
		Collection<?> items2 = CollectionTools.collection(items);
		items2.retainAll(collection);
		boolean changed = collection.removeAll(items2);

		if ( ! items2.isEmpty()) {
			this.fireItemsRemoved(collectionName, items2);
		}
		return changed;
	}

	/**
	 * Convenience method. Retain the specified items in the specified bound
	 * collection and fire the appropriate event if necessary. Return whether
	 * the collection changed.
	 *
	 * @param items
	 *            the items
	 * @param collection
	 *            the collection
	 * @param collectionName
	 *            the collection name
	 * @return true, if successful
	 * @see java.util.Collection#retainAll(java.util.Collection)
	 */
	protected boolean retainItemsInCollection(Object[] items, Collection<?> collection, String collectionName) {
		return this.retainItemsInCollection(new ArrayIterator<Object>(items), collection, collectionName);
	}

	/**
	 * Convenience method. Retain the specified items in the specified bound
	 * collection and fire the appropriate event if necessary. Return whether
	 * the collection changed.
	 *
	 * @param items
	 *            the items
	 * @param collection
	 *            the collection
	 * @param collectionName
	 *            the collection name
	 * @return true, if successful
	 * @see java.util.Collection#retainAll(java.util.Collection)
	 */
	protected boolean retainItemsInCollection(Iterable<?> items, Collection<?> collection, String collectionName) {
		return this.retainItemsInCollection(items.iterator(), collection, collectionName);
	}

	/**
	 * Convenience method. Retain the specified items in the specified bound
	 * collection and fire the appropriate event if necessary. Return whether
	 * the collection changed.
	 *
	 * @param items
	 *            the items
	 * @param collection
	 *            the collection
	 * @param collectionName
	 *            the collection name
	 * @return true, if successful
	 * @see java.util.Collection#retainAll(java.util.Collection)
	 */
	protected boolean retainItemsInCollection(Iterator<?> items, Collection<?> collection, String collectionName) {
		Collection<?> items2 = CollectionTools.collection(items);
		Collection<?> removedItems = CollectionTools.collection(collection);
		removedItems.removeAll(items2);
		boolean changed = collection.retainAll(items2);

		if ( ! removedItems.isEmpty()) {
			this.fireItemsRemoved(collectionName, removedItems);
		}
		return changed;
	}

	/**
	 * Convenience method. Clear the entire collection and fire the appropriate
	 * event if necessary. Return whether the list changed.
	 *
	 * @param collection
	 *            the collection
	 * @param collectionName
	 *            the collection name
	 * @return true, if successful
	 * @see java.util.Collection#clear()
	 */
	protected boolean clearCollection(Collection<?> collection, String collectionName) {
		if (collection.isEmpty()) {
			return false;
		}
		collection.clear();
		this.fireCollectionCleared(collectionName);
		return true;
	}

	/**
	 * Convenience method. Synchronize the collection with the specified new
	 * collection, making a minimum number of removes and adds. Return whether
	 * the collection changed.
	 *
	 * @author mqfdy
	 * @param <E>
	 *            the element type
	 * @param newCollection
	 *            the new collection
	 * @param collection
	 *            the collection
	 * @param collectionName
	 *            the collection name
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	protected <E> boolean synchronizeCollection(Collection<E> newCollection, Collection<E> collection, String collectionName) {
		if (newCollection.isEmpty()) {
			return this.clearCollection(collection, collectionName);
		}

		if (collection.isEmpty()) {
			return this.addItemsToCollection(newCollection, collection, collectionName);
		}

		boolean changed = false;
		Collection<E> removeItems = new HashBag<E>(collection);
		removeItems.removeAll(newCollection);
		changed |= this.removeItemsFromCollection(removeItems, collection, collectionName);

		Collection<E> addItems = new HashBag<E>(newCollection);
		addItems.removeAll(collection);
		changed |= this.addItemsToCollection(addItems, collection, collectionName);

		return changed;
	}

	/**
	 * Convenience method. Synchronize the collection with the specified new
	 * collection, making a minimum number of removes and adds. Return whether
	 * the collection changed.
	 *
	 * @author mqfdy
	 * @param <E>
	 *            the element type
	 * @param newItems
	 *            the new items
	 * @param collection
	 *            the collection
	 * @param collectionName
	 *            the collection name
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	protected <E> boolean synchronizeCollection(Iterator<E> newItems, Collection<E> collection, String collectionName) {
		return this.synchronizeCollection(CollectionTools.collection(newItems), collection, collectionName);
	}


	// ********** list change support **********

	public synchronized void addListChangeListener(ListChangeListener listener) {
		this.getChangeSupport().addListChangeListener(listener);
	}

	public synchronized void addListChangeListener(String listName, ListChangeListener listener) {
		this.getChangeSupport().addListChangeListener(listName, listener);
	}

	public synchronized void removeListChangeListener(ListChangeListener listener) {
		this.getChangeSupport().removeListChangeListener(listener);
	}

	public synchronized void removeListChangeListener(String listName, ListChangeListener listener) {
		this.getChangeSupport().removeListChangeListener(listName, listener);
	}

	/**
	 * Fire item added.
	 *
	 * @author mqfdy
	 * @param listName
	 *            the list name
	 * @param index
	 *            the index
	 * @param addedItem
	 *            the added item
	 * @Date 2018-09-03 09:00
	 */
	protected final void fireItemAdded(String listName, int index, Object addedItem) {
		this.getChangeSupport().fireItemAdded(listName, index, addedItem);
	}

	/**
	 * Fire items added.
	 *
	 * @author mqfdy
	 * @param listName
	 *            the list name
	 * @param index
	 *            the index
	 * @param addedItems
	 *            the added items
	 * @Date 2018-09-03 09:00
	 */
	protected final void fireItemsAdded(String listName, int index, List<?> addedItems) {
		this.getChangeSupport().fireItemsAdded(listName, index, addedItems);
	}

	/**
	 * Fire items added.
	 *
	 * @author mqfdy
	 * @param event
	 *            the event
	 * @Date 2018-09-03 09:00
	 */
	protected final void fireItemsAdded(ListChangeEvent event) {
		this.getChangeSupport().fireItemsAdded(event);
	}

	/**
	 * Fire item removed.
	 *
	 * @author mqfdy
	 * @param listName
	 *            the list name
	 * @param index
	 *            the index
	 * @param removedItem
	 *            the removed item
	 * @Date 2018-09-03 09:00
	 */
	protected final void fireItemRemoved(String listName, int index, Object removedItem) {
		this.getChangeSupport().fireItemRemoved(listName, index, removedItem);
	}

	/**
	 * Fire items removed.
	 *
	 * @author mqfdy
	 * @param listName
	 *            the list name
	 * @param index
	 *            the index
	 * @param removedItems
	 *            the removed items
	 * @Date 2018-09-03 09:00
	 */
	protected final void fireItemsRemoved(String listName, int index, List<?> removedItems) {
		this.getChangeSupport().fireItemsRemoved(listName, index, removedItems);
	}

	/**
	 * Fire items removed.
	 *
	 * @author mqfdy
	 * @param event
	 *            the event
	 * @Date 2018-09-03 09:00
	 */
	protected final void fireItemsRemoved(ListChangeEvent event) {
		this.getChangeSupport().fireItemsRemoved(event);
	}

	/**
	 * Fire item replaced.
	 *
	 * @author mqfdy
	 * @param listName
	 *            the list name
	 * @param index
	 *            the index
	 * @param newItem
	 *            the new item
	 * @param replacedItem
	 *            the replaced item
	 * @Date 2018-09-03 09:00
	 */
	protected final void fireItemReplaced(String listName, int index, Object newItem, Object replacedItem) {
		this.getChangeSupport().fireItemReplaced(listName, index, newItem, replacedItem);
	}

	/**
	 * Fire items replaced.
	 *
	 * @author mqfdy
	 * @param <E>
	 *            the element type
	 * @param listName
	 *            the list name
	 * @param index
	 *            the index
	 * @param newItems
	 *            the new items
	 * @param replacedItems
	 *            the replaced items
	 * @Date 2018-09-03 09:00
	 */
	protected final <E> void fireItemsReplaced(String listName, int index, List<? extends E> newItems, List<E> replacedItems) {
		this.getChangeSupport().fireItemsReplaced(listName, index, newItems, replacedItems);
	}

	/**
	 * Fire items replaced.
	 *
	 * @author mqfdy
	 * @param event
	 *            the event
	 * @Date 2018-09-03 09:00
	 */
	protected final void fireItemsReplaced(ListChangeEvent event) {
		this.getChangeSupport().fireItemsReplaced(event);
	}

	/**
	 * Fire item moved.
	 *
	 * @author mqfdy
	 * @param listName
	 *            the list name
	 * @param targetIndex
	 *            the target index
	 * @param sourceIndex
	 *            the source index
	 * @Date 2018-09-03 09:00
	 */
	protected final void fireItemMoved(String listName, int targetIndex, int sourceIndex) {
		this.getChangeSupport().fireItemMoved(listName, targetIndex, sourceIndex);
	}

	/**
	 * Fire items moved.
	 *
	 * @author mqfdy
	 * @param <E>
	 *            the element type
	 * @param listName
	 *            the list name
	 * @param targetIndex
	 *            the target index
	 * @param sourceIndex
	 *            the source index
	 * @param length
	 *            the length
	 * @Date 2018-09-03 09:00
	 */
	protected final <E> void fireItemsMoved(String listName, int targetIndex, int sourceIndex, int length) {
		this.getChangeSupport().fireItemsMoved(listName, targetIndex, sourceIndex, length);
	}

	/**
	 * Fire items moved.
	 *
	 * @author mqfdy
	 * @param event
	 *            the event
	 * @Date 2018-09-03 09:00
	 */
	protected final void fireItemsMoved(ListChangeEvent event) {
		this.getChangeSupport().fireItemsMoved(event);
	}

	/**
	 * Fire list cleared.
	 *
	 * @author mqfdy
	 * @param listName
	 *            the list name
	 * @Date 2018-09-03 09:00
	 */
	protected final void fireListCleared(String listName) {
		this.getChangeSupport().fireListCleared(listName);
	}

	/**
	 * Fire list cleared.
	 *
	 * @author mqfdy
	 * @param event
	 *            the event
	 * @Date 2018-09-03 09:00
	 */
	protected final void fireListCleared(ListChangeEvent event) {
		this.getChangeSupport().fireListCleared(event);
	}

	/**
	 * Fire list changed.
	 *
	 * @author mqfdy
	 * @param listName
	 *            the list name
	 * @Date 2018-09-03 09:00
	 */
	protected final void fireListChanged(String listName) {
		this.getChangeSupport().fireListChanged(listName);
	}

	/**
	 * Fire list changed.
	 *
	 * @author mqfdy
	 * @param event
	 *            the event
	 * @Date 2018-09-03 09:00
	 */
	protected final void fireListChanged(ListChangeEvent event) {
		this.getChangeSupport().fireListChanged(event);
	}

	/**
	 * Convenience method. Add the specified item to the specified bound list
	 * and fire the appropriate event if necessary.
	 *
	 * @param <E>
	 *            the element type
	 * @param index
	 *            the index
	 * @param item
	 *            the item
	 * @param list
	 *            the list
	 * @param listName
	 *            the list name
	 * @see java.util.List#add(int, Object)
	 */
	protected <E> void addItemToList(int index, E item, List<E> list, String listName) {
		list.add(index, item);
		this.fireItemAdded(listName, index, item);
	}

	/**
	 * Convenience method. Add the specified item to the end of the specified
	 * bound list and fire the appropriate event if necessary. Return whether
	 * list changed.
	 *
	 * @param <E>
	 *            the element type
	 * @param item
	 *            the item
	 * @param list
	 *            the list
	 * @param listName
	 *            the list name
	 * @return true, if successful
	 * @see java.util.List#add(Object)
	 */
	protected <E> boolean addItemToList(E item, List<E> list, String listName) {
		if (list.add(item)) {
			this.fireItemAdded(listName, list.size() - 1, item);
			return true;
		}
		return false;
	}

	/**
	 * Convenience method. Add the specified items to the specified bound list
	 * and fire the appropriate event if necessary.
	 *
	 * @param <E>
	 *            the element type
	 * @param index
	 *            the index
	 * @param items
	 *            the items
	 * @param list
	 *            the list
	 * @param listName
	 *            the list name
	 * @return true, if successful
	 * @see java.util.List#addAll(int, java.util.Collection)
	 */
	protected <E> boolean addItemsToList(int index, E[] items, List<E> list, String listName) {
		return this.addItemsToList(index, new ArrayIterator<E>(items), list, listName);
	}

	/**
	 * Convenience method. Add the specified items to the specified bound list
	 * and fire the appropriate event if necessary.
	 *
	 * @param <E>
	 *            the element type
	 * @param index
	 *            the index
	 * @param items
	 *            the items
	 * @param list
	 *            the list
	 * @param listName
	 *            the list name
	 * @return true, if successful
	 * @see java.util.List#addAll(int, java.util.Collection)
	 */
	protected <E> boolean addItemsToList(int index, Iterable<? extends E> items, List<E> list, String listName) {
		return this.addItemsToList(index, items.iterator(), list, listName);
	}

	/**
	 * Convenience method. Add the specified items to the specified bound list
	 * and fire the appropriate event if necessary.
	 *
	 * @param <E>
	 *            the element type
	 * @param index
	 *            the index
	 * @param items
	 *            the items
	 * @param list
	 *            the list
	 * @param listName
	 *            the list name
	 * @return true, if successful
	 * @see java.util.List#addAll(int, java.util.Collection)
	 */
	protected <E> boolean addItemsToList(int index, Iterator<? extends E> items, List<E> list, String listName) {
		List<E> items2 = CollectionTools.list(items);
		if (list.addAll(index, items2)) {
			this.fireItemsAdded(listName, index, items2);
			return true;
		}
		return false;
	}

	/**
	 * Convenience method. Add the specified items to the end of to the
	 * specified bound list and fire the appropriate event if necessary.
	 *
	 * @param <E>
	 *            the element type
	 * @param items
	 *            the items
	 * @param list
	 *            the list
	 * @param listName
	 *            the list name
	 * @return true, if successful
	 * @see java.util.List#addAll(java.util.Collection)
	 */
	protected <E> boolean addItemsToList(E[] items, List<E> list, String listName) {
		return this.addItemsToList(new ArrayIterator<E>(items), list, listName);
	}

	/**
	 * Convenience method. Add the specified items to the end of to the
	 * specified bound list and fire the appropriate event if necessary.
	 *
	 * @param <E>
	 *            the element type
	 * @param items
	 *            the items
	 * @param list
	 *            the list
	 * @param listName
	 *            the list name
	 * @return true, if successful
	 * @see java.util.List#addAll(java.util.Collection)
	 */
	protected <E> boolean addItemsToList(Iterable<? extends E> items, List<E> list, String listName) {
		return this.addItemsToList(items.iterator(), list, listName);
	}

	/**
	 * Convenience method. Add the specified items to the end of to the
	 * specified bound list and fire the appropriate event if necessary.
	 *
	 * @param <E>
	 *            the element type
	 * @param items
	 *            the items
	 * @param list
	 *            the list
	 * @param listName
	 *            the list name
	 * @return true, if successful
	 * @see java.util.List#addAll(java.util.Collection)
	 */
	protected <E> boolean addItemsToList(Iterator<? extends E> items, List<E> list, String listName) {
		List<E> items2 = CollectionTools.list(items);
		int index = list.size();
		if (list.addAll(items2)) {
			this.fireItemsAdded(listName, index, items2);
			return true;
		}
		return false;  // empty list of items added
	}

	/**
	 * Convenience method. Remove the specified item from the specified bound
	 * list and fire the appropriate event if necessary. Return the removed
	 * item.
	 *
	 * @param <E>
	 *            the element type
	 * @param index
	 *            the index
	 * @param list
	 *            the list
	 * @param listName
	 *            the list name
	 * @return the e
	 * @see java.util.List#remove(int)
	 */
	protected <E> E removeItemFromList(int index, List<E> list, String listName) {
		E item = list.remove(index);
		this.fireItemRemoved(listName, index, item);
		return item;
	}

	/**
	 * Convenience method. Remove the specified item from the specified bound
	 * list and fire the appropriate event if necessary. Return the removed
	 * item.
	 *
	 * @param item
	 *            the item
	 * @param list
	 *            the list
	 * @param listName
	 *            the list name
	 * @return true, if successful
	 * @see java.util.List#remove(Object)
	 */
	protected boolean removeItemFromList(Object item, List<?> list, String listName) {
		int index = list.indexOf(item);
		if (index == -1) {
			return false;
		}
		list.remove(index);
		this.fireItemRemoved(listName, index, item);
		return true;
	}

	/**
	 * Convenience method. Remove the specified items from the specified bound
	 * list and fire the appropriate event if necessary. Return the removed
	 * items.
	 *
	 * @param <E>
	 *            the element type
	 * @param index
	 *            the index
	 * @param length
	 *            the length
	 * @param list
	 *            the list
	 * @param listName
	 *            the list name
	 * @return the list
	 * @see java.util.List#remove(int)
	 */
	protected <E> List<E> removeItemsFromList(int index, int length, List<E> list, String listName) {
		List<E> subList = list.subList(index, index + length);
		List<E> removedItems = new ArrayList<E>(subList);
		subList.clear();
		this.fireItemsRemoved(listName, index, removedItems);
		return removedItems;
	}

	/**
	 * Convenience method. Remove the specified items from the specified bound
	 * list and fire the appropriate event if necessary. Return the removed
	 * items.
	 *
	 * @param items
	 *            the items
	 * @param list
	 *            the list
	 * @param listName
	 *            the list name
	 * @return true, if successful
	 * @see java.util.List#removeAll(java.util.Collection)
	 */
	protected boolean removeItemsFromList(Object[] items, List<?> list, String listName) {
		return this.removeItemsFromList(new ArrayIterator<Object>(items), list, listName);
	}

	/**
	 * Convenience method. Remove the specified items from the specified bound
	 * list and fire the appropriate event if necessary. Return the removed
	 * items.
	 *
	 * @param items
	 *            the items
	 * @param list
	 *            the list
	 * @param listName
	 *            the list name
	 * @return true, if successful
	 * @see java.util.List#removeAll(java.util.Collection)
	 */
	protected boolean removeItemsFromList(Iterable<?> items, List<?> list, String listName) {
		return this.removeItemsFromList(items.iterator(), list, listName);
	}

	/**
	 * Convenience method. Remove the specified items from the specified bound
	 * list and fire the appropriate event if necessary. Return the removed
	 * items.
	 *
	 * @param items
	 *            the items
	 * @param list
	 *            the list
	 * @param listName
	 *            the list name
	 * @return true, if successful
	 * @see java.util.List#removeAll(java.util.Collection)
	 */
	protected boolean removeItemsFromList(Iterator<?> items, List<?> list, String listName) {
		boolean changed = false;
		while (items.hasNext()) {
			changed |= this.removeItemFromList(items.next(), list, listName);
		}
		return changed;
	}

	/**
	 * Convenience method. Retain the specified items in the specified bound
	 * list and fire the appropriate event if necessary. Return whether the
	 * collection changed.
	 *
	 * @param items
	 *            the items
	 * @param list
	 *            the list
	 * @param listName
	 *            the list name
	 * @return true, if successful
	 * @see java.util.List#retainAll(java.util.Collection)
	 */
	protected boolean retainItemsInList(Object[] items, List<?> list, String listName) {
		return this.retainItemsInList(new ArrayIterator<Object>(items), list, listName);
	}

	/**
	 * Convenience method. Retain the specified items in the specified bound
	 * list and fire the appropriate event if necessary. Return whether the
	 * collection changed.
	 *
	 * @param items
	 *            the items
	 * @param list
	 *            the list
	 * @param listName
	 *            the list name
	 * @return true, if successful
	 * @see java.util.List#retainAll(java.util.Collection)
	 */
	protected boolean retainItemsInList(Iterable<?> items, List<?> list, String listName) {
		return this.retainItemsInList(items.iterator(), list, listName);
	}

	/**
	 * Convenience method. Retain the specified items in the specified bound
	 * list and fire the appropriate event if necessary. Return whether the
	 * collection changed.
	 *
	 * @param items
	 *            the items
	 * @param list
	 *            the list
	 * @param listName
	 *            the list name
	 * @return true, if successful
	 * @see java.util.List#retainAll(java.util.Collection)
	 */
	protected boolean retainItemsInList(Iterator<?> items, List<?> list, String listName) {
		Collection<?> items2 = CollectionTools.collection(items);
		Collection<?> removedItems = CollectionTools.collection(list);
		removedItems.removeAll(items2);
		return this.removeItemsFromList(removedItems, list, listName);
	}

	/**
	 * Convenience method. Set the specified item in the specified bound list
	 * and fire the appropriate event if necessary. Return the replaced item.
	 *
	 * @param <E>
	 *            the element type
	 * @param index
	 *            the index
	 * @param item
	 *            the item
	 * @param list
	 *            the list
	 * @param listName
	 *            the list name
	 * @return the e
	 * @see java.util.List#set(int, Object)
	 */
	protected <E> E setItemInList(int index, E item, List<E> list, String listName) {
		E replacedItem = list.set(index, item);
		this.fireItemReplaced(listName, index, item, replacedItem);
		return replacedItem;
	}

	/**
	 * Convenience method. Replace the specified item in the specified bound
	 * list and fire the appropriate event if necessary. Return the replaced
	 * item.
	 *
	 * @param <E>
	 *            the element type
	 * @param oldItem
	 *            the old item
	 * @param newItem
	 *            the new item
	 * @param list
	 *            the list
	 * @param listName
	 *            the list name
	 * @return the e
	 * @see java.util.List#set(int, Object)
	 */
	protected <E> E replaceItemInList(E oldItem, E newItem, List<E> list, String listName) {
		return this.setItemInList(list.indexOf(oldItem), newItem, list, listName);
	}

	/**
	 * Convenience method. Set the specified items in the specified bound list
	 * and fire the appropriate event if necessary. Return the replaced items.
	 *
	 * @param <E>
	 *            the element type
	 * @param index
	 *            the index
	 * @param items
	 *            the items
	 * @param list
	 *            the list
	 * @param listName
	 *            the list name
	 * @return the list
	 * @see java.util.List#set(int, Object)
	 */
	protected <E> List<E> setItemsInList(int index, E[] items, List<E> list, String listName) {
		return this.setItemsInList(index, Arrays.asList(items), list, listName);
	}

	/**
	 * Convenience method. Set the specified items in the specified bound list
	 * and fire the appropriate event if necessary. Return the replaced items.
	 *
	 * @param <E>
	 *            the element type
	 * @param index
	 *            the index
	 * @param items
	 *            the items
	 * @param list
	 *            the list
	 * @param listName
	 *            the list name
	 * @return the list
	 * @see java.util.List#set(int, Object)
	 */
	protected <E> List<E> setItemsInList(int index, List<? extends E> items, List<E> list, String listName) {
		List<E> subList = list.subList(index, index + items.size());
		List<E> replacedItems = new ArrayList<E>(subList);
		for (int i = 0; i < items.size(); i++) {
			subList.set(i, items.get(i));
		}
		this.fireItemsReplaced(listName, index, items, replacedItems);
		return replacedItems;
	}

	/**
	 * Convenience method. Move items in the specified list from the specified
	 * source index to the specified target index for the specified length.
	 *
	 * @author mqfdy
	 * @param <E>
	 *            the element type
	 * @param targetIndex
	 *            the target index
	 * @param sourceIndex
	 *            the source index
	 * @param length
	 *            the length
	 * @param list
	 *            the list
	 * @param listName
	 *            the list name
	 * @Date 2018-09-03 09:00
	 */
	protected <E> void moveItemsInList(int targetIndex, int sourceIndex, int length, List<E> list, String listName) {
		CollectionTools.move(list, targetIndex, sourceIndex, length);
		this.fireItemsMoved(listName, targetIndex, sourceIndex, length);
	}

	/**
	 * Convenience method. Move an item in the specified list from the specified
	 * source index to the specified target index.
	 *
	 * @author mqfdy
	 * @param <E>
	 *            the element type
	 * @param targetIndex
	 *            the target index
	 * @param sourceIndex
	 *            the source index
	 * @param list
	 *            the list
	 * @param listName
	 *            the list name
	 * @Date 2018-09-03 09:00
	 */
	protected <E> void moveItemInList(int targetIndex, int sourceIndex, List<E> list, String listName) {
		CollectionTools.move(list, targetIndex, sourceIndex);
		this.fireItemMoved(listName, targetIndex, sourceIndex);
	}

	/**
	 * Convenience method. Clear the entire list and fire the appropriate event
	 * if necessary. Return whether the list changed.
	 *
	 * @param list
	 *            the list
	 * @param listName
	 *            the list name
	 * @return true, if successful
	 * @see java.util.List#clear()
	 */
	protected boolean clearList(List<?> list, String listName) {
		if (list.isEmpty()) {
			return false;
		}
		list.clear();
		this.fireListCleared(listName);
		return true;
	}


	// ********** tree change support **********

	public synchronized void addTreeChangeListener(TreeChangeListener listener) {
		this.getChangeSupport().addTreeChangeListener(listener);
	}

	public synchronized void addTreeChangeListener(String treeName, TreeChangeListener listener) {
		this.getChangeSupport().addTreeChangeListener(treeName, listener);
	}

	public synchronized void removeTreeChangeListener(TreeChangeListener listener) {
		this.getChangeSupport().removeTreeChangeListener(listener);
	}

	public synchronized void removeTreeChangeListener(String treeName, TreeChangeListener listener) {
		this.getChangeSupport().removeTreeChangeListener(treeName, listener);
	}

	/**
	 * Fire node added.
	 *
	 * @author mqfdy
	 * @param treeName
	 *            the tree name
	 * @param path
	 *            the path
	 * @Date 2018-09-03 09:00
	 */
	protected final void fireNodeAdded(String treeName, Object[] path) {
		this.getChangeSupport().fireNodeAdded(treeName, path);
	}

	/**
	 * Fire node added.
	 *
	 * @author mqfdy
	 * @param event
	 *            the event
	 * @Date 2018-09-03 09:00
	 */
	protected final void fireNodeAdded(TreeChangeEvent event) {
		this.getChangeSupport().fireNodeAdded(event);
	}

	/**
	 * Fire node removed.
	 *
	 * @author mqfdy
	 * @param treeName
	 *            the tree name
	 * @param path
	 *            the path
	 * @Date 2018-09-03 09:00
	 */
	protected final void fireNodeRemoved(String treeName, Object[] path) {
		this.getChangeSupport().fireNodeRemoved(treeName, path);
	}

	/**
	 * Fire node removed.
	 *
	 * @author mqfdy
	 * @param event
	 *            the event
	 * @Date 2018-09-03 09:00
	 */
	protected final void fireNodeRemoved(TreeChangeEvent event) {
		this.getChangeSupport().fireNodeRemoved(event);
	}

	/**
	 * Fire tree cleared.
	 *
	 * @author mqfdy
	 * @param treeName
	 *            the tree name
	 * @Date 2018-09-03 09:00
	 */
	protected final void fireTreeCleared(String treeName) {
		this.getChangeSupport().fireTreeCleared(treeName);
	}

	/**
	 * Fire tree cleared.
	 *
	 * @author mqfdy
	 * @param event
	 *            the event
	 * @Date 2018-09-03 09:00
	 */
	protected final void fireTreeCleared(TreeChangeEvent event) {
		this.getChangeSupport().fireTreeCleared(event);
	}

	/**
	 * Fire tree changed.
	 *
	 * @author mqfdy
	 * @param treeName
	 *            the tree name
	 * @Date 2018-09-03 09:00
	 */
	protected final void fireTreeChanged(String treeName) {
		this.getChangeSupport().fireTreeChanged(treeName);
	}

	/**
	 * Fire tree changed.
	 *
	 * @author mqfdy
	 * @param treeName
	 *            the tree name
	 * @param path
	 *            the path
	 * @Date 2018-09-03 09:00
	 */
	protected final void fireTreeChanged(String treeName, Object[] path) {
		this.getChangeSupport().fireTreeChanged(treeName, path);
	}

	/**
	 * Fire tree changed.
	 *
	 * @author mqfdy
	 * @param event
	 *            the event
	 * @Date 2018-09-03 09:00
	 */
	protected final void fireTreeChanged(TreeChangeEvent event) {
		this.getChangeSupport().fireTreeChanged(event);
	}


	// ********** queries **********

	/**
	 * Return whether there are any state change listeners.
	 *
	 * @author mqfdy
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	public boolean hasAnyStateChangeListeners() {
		return this.getChangeSupport().hasAnyStateChangeListeners();
	}

	/**
	 * Return whether there are no state change listeners.
	 *
	 * @author mqfdy
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	public boolean hasNoStateChangeListeners() {
		return ! this.hasAnyStateChangeListeners();
	}

	/**
	 * Return whether there are any property change listeners for a specific
	 * property.
	 *
	 * @author mqfdy
	 * @param propertyName
	 *            the property name
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	public boolean hasAnyPropertyChangeListeners(String propertyName) {
		return this.getChangeSupport().hasAnyPropertyChangeListeners(propertyName);
	}

	/**
	 * Return whether there are any property change listeners for a specific
	 * property.
	 *
	 * @author mqfdy
	 * @param propertyName
	 *            the property name
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	public boolean hasNoPropertyChangeListeners(String propertyName) {
		return ! this.hasAnyPropertyChangeListeners(propertyName);
	}

	/**
	 * Return whether there are any collection change listeners for a specific
	 * collection.
	 *
	 * @author mqfdy
	 * @param collectionName
	 *            the collection name
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	public boolean hasAnyCollectionChangeListeners(String collectionName) {
		return this.getChangeSupport().hasAnyCollectionChangeListeners(collectionName);
	}

	/**
	 * Return whether there are any collection change listeners for a specific
	 * collection.
	 *
	 * @author mqfdy
	 * @param collectionName
	 *            the collection name
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	public boolean hasNoCollectionChangeListeners(String collectionName) {
		return ! this.hasAnyCollectionChangeListeners(collectionName);
	}

	/**
	 * Return whether there are any list change listeners for a specific list.
	 *
	 * @author mqfdy
	 * @param listName
	 *            the list name
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	public boolean hasAnyListChangeListeners(String listName) {
		return this.getChangeSupport().hasAnyListChangeListeners(listName);
	}

	/**
	 * Return whether there are any list change listeners for a specific list.
	 *
	 * @author mqfdy
	 * @param listName
	 *            the list name
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	public boolean hasNoListChangeListeners(String listName) {
		return ! this.hasAnyListChangeListeners(listName);
	}

	/**
	 * Return whether there are any tree change listeners for a specific tree.
	 *
	 * @author mqfdy
	 * @param treeName
	 *            the tree name
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	public boolean hasAnyTreeChangeListeners(String treeName) {
		return this.getChangeSupport().hasAnyTreeChangeListeners(treeName);
	}

	/**
	 * Return whether there are any tree change listeners for a specific tree.
	 *
	 * @author mqfdy
	 * @param treeName
	 *            the tree name
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	public boolean hasNoTreeChangeListeners(String treeName) {
		return ! this.hasAnyTreeChangeListeners(treeName);
	}


	// ********** convenience methods **********

	/**
	 * Return whether the values are equal, with the appropriate null checks.
	 * Convenience method for checking whether an attribute value has changed.
	 * 
	 * DO NOT use this to determine whether to fire a change notification,
	 * ChangeSupport already does that.
	 *
	 * @author mqfdy
	 * @param value1
	 *            the value 1
	 * @param value2
	 *            the value 2
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	protected final boolean valuesAreEqual(Object value1, Object value2) {
		return this.getChangeSupport().valuesAreEqual(value1, value2);
	}
	
	/**
	 * Attribute value has not changed.
	 *
	 * @author mqfdy
	 * @param oldValue
	 *            the old value
	 * @param newValue
	 *            the new value
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	protected final boolean attributeValueHasNotChanged(Object oldValue, Object newValue) {
		return this.valuesAreEqual(oldValue, newValue);
	}


	/**
	 * Return whether the values are different, with the appropriate null
	 * checks. Convenience method for checking whether an attribute value has
	 * changed.
	 * 
	 * DO NOT use this to determine whether to fire a change notification,
	 * ChangeSupport already does that.
	 * 
	 * For example, after firing the change notification, you can use this
	 * method to decide if some other, related, piece of state needs to be
	 * synchronized with the state that just changed.
	 *
	 * @author mqfdy
	 * @param value1
	 *            the value 1
	 * @param value2
	 *            the value 2
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	protected final boolean valuesAreDifferent(Object value1, Object value2) {
		return this.getChangeSupport().valuesAreDifferent(value1, value2);
	}
	
	/**
	 * Attribute value has changed.
	 *
	 * @author mqfdy
	 * @param oldValue
	 *            the old value
	 * @param newValue
	 *            the new value
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	protected final boolean attributeValueHasChanged(Object oldValue, Object newValue) {
		return this.valuesAreDifferent(oldValue, newValue);
	}


	// ********** Object overrides **********

	/**
	 * Although cloning models is usually not a Good Idea,
	 * we should at least support it properly.
	 */
	@Override
	protected AbstractModel clone() throws CloneNotSupportedException {
		AbstractModel clone = (AbstractModel) super.clone();
		clone.postClone();
		return clone;
	}

	/**
	 * Perform any post-clone processing necessary to
	 * successfully disconnect the clone from the original.
	 * When this method is called on the clone, the clone
	 * is a "shallow" copy of the original (i.e. the clone
	 * shares all its instance variables with the original).
	 */
	protected void postClone() {
		// clear out change support - models do not share listeners
		this.changeSupport = null;
	// when you override this method, don't forget to include:
	//	super.postClone();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		StringTools.buildSimpleToStringOn(this, sb);
		sb.append(" (");
		this.toString(sb);
		sb.append(')');
		return sb.toString();
	}

	/**
	 * make this public so one model can call a nested model's
	 * #toString(StringBuilder).
	 *
	 * @author mqfdy
	 * @param sb
	 *            the sb
	 * @Date 2018-09-03 09:00
	 */
	public void toString(StringBuilder sb) {
		// subclasses should override this to do something a bit more helpful
	}

}
