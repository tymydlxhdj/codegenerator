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
import java.util.Collection;
import java.util.Iterator;

import com.mqfdy.code.springboot.utilities.Filter;
import com.mqfdy.code.springboot.utilities.internal.CollectionTools;
import com.mqfdy.code.springboot.utilities.internal.iterators.FilteringIterator;
import com.mqfdy.code.springboot.utilities.internal.iterators.ReadOnlyIterator;
import com.mqfdy.code.springboot.utilities.model.event.CollectionChangeEvent;
import com.mqfdy.code.springboot.utilities.model.value.CollectionValueModel;
import com.mqfdy.code.springboot.utilities.model.value.ListValueModel;


// TODO: Auto-generated Javadoc
/**
 * A <code>FilteringCollectionValueModel</code> wraps another
 * <code>CollectionValueModel</code> and uses a <code>Filter</code> to determine
 * which items in the collection are returned by calls to
 * <code>#iterator()</code>.
 * <p>
 * The filter can be changed at any time; allowing the same adapter to be used
 * with different filter criteria (e.g. when the user wants to view a list of
 * .java files).
 * <p>
 * NB: If the objects in the "filtered" collection can change in such a way that
 * they should be removed from the "filtered" collection, you will need to wrap
 * the original collection in an ItemAspectListValueModelAdapter. For example,
 * if the filter only "accepts" items whose names begin with "X" and the names
 * of the items can change, you will need to wrap the original list of
 * unfiltered items with an ItemPropertyListValueModelAdapter that listens for
 * changes to each item's name and fires the appropriate event whenever an
 * item's name changes. The event will cause this wrapper to re-filter the
 * changed item and add or remove it from the "filtered" collection as
 * appropriate.
 *
 * @author mqfdy
 * @param <E>
 *            the element type
 */
public class FilteringCollectionValueModel<E>
	extends CollectionValueModelWrapper<E>
	implements CollectionValueModel<E>
{
	/** This filters the items in the nested collection. */
	private Filter<E> filter;

	/** Cache the items that were accepted by the filter */
	private final Collection<E> filteredItems;


	// ********** constructors **********

	/**
	 * Construct a collection value model with the specified wrapped collection
	 * value model and a filter that simply accepts every object.
	 *
	 * @param collectionHolder
	 *            the collection holder
	 */
	public FilteringCollectionValueModel(CollectionValueModel<? extends E> collectionHolder) {
		this(collectionHolder, Filter.Null.<E>instance());
	}

	/**
	 * Construct a collection value model with the specified wrapped collection
	 * value model and filter.
	 *
	 * @param collectionHolder
	 *            the collection holder
	 * @param filter
	 *            the filter
	 */
	public FilteringCollectionValueModel(CollectionValueModel<? extends E> collectionHolder, Filter<E> filter) {
		super(collectionHolder);
		this.filter = filter;
		this.filteredItems = new ArrayList<E>();
	}

	/**
	 * Construct a collection value model with the specified wrapped list value
	 * model and a filter that simply accepts every object.
	 *
	 * @param listHolder
	 *            the list holder
	 */
	public FilteringCollectionValueModel(ListValueModel<E> listHolder) {
		this(new ListCollectionValueModelAdapter<E>(listHolder));
	}

	/**
	 * Construct a collection value model with the specified wrapped list value
	 * model and filter.
	 *
	 * @param listHolder
	 *            the list holder
	 * @param filter
	 *            the filter
	 */
	public FilteringCollectionValueModel(ListValueModel<E> listHolder, Filter<E> filter) {
		this(new ListCollectionValueModelAdapter<E>(listHolder), filter);
	}


	// ********** CollectionValueModel implementation **********

	public Iterator<E> iterator() {
		return new ReadOnlyIterator<E>(this.filteredItems);
	}

	public int size() {
		return this.filteredItems.size();
	}


	// ********** CollectionValueModelWrapper overrides/implementation **********

	@Override
	protected void engageModel() {
		super.engageModel();
		// synch our cache *after* we start listening to the nested collection,
		// since its value might change when a listener is added
		CollectionTools.addAll(this.filteredItems, this.filter(this.collectionHolder.iterator()));
	}

	@Override
	protected void disengageModel() {
		super.disengageModel();
		// clear out the cache when we are not listening to the nested collection
		this.filteredItems.clear();
	}

	@Override
	protected void itemsAdded(CollectionChangeEvent event) {
		// filter the values before propagating the change event
		this.addItemsToCollection(this.filter(this.items(event)), this.filteredItems, VALUES);
	}

	@Override
	protected void itemsRemoved(CollectionChangeEvent event) {
		// do *not* filter the values, because they may no longer be
		// "accepted" and that might be why they were removed in the first place;
		// anyway, any extraneous items are harmless
		this.removeItemsFromCollection(event.items(), this.filteredItems, VALUES);
	}

	@Override
	protected void collectionCleared(CollectionChangeEvent event) {
		this.clearCollection(this.filteredItems, VALUES);
	}

	@Override
	protected void collectionChanged(CollectionChangeEvent event) {
		this.rebuildFilteredItems();
	}


	// ********** miscellaneous **********

	/**
	 * Change the filter and rebuild the collection.
	 *
	 * @author mqfdy
	 * @param filter
	 *            the new filter
	 * @Date 2018-09-03 09:00
	 */
	public void setFilter(Filter<E> filter) {
		this.filter = filter;
		this.rebuildFilteredItems();
	}

	/**
	 * Return an iterator that filters the specified iterator.
	 *
	 * @author mqfdy
	 * @param items
	 *            the items
	 * @return the iterator
	 * @Date 2018-09-03 09:00
	 */
	protected Iterator<E> filter(Iterator<? extends E> items) {
		return new FilteringIterator<E, E>(items, this.filter);
	}

	/**
	 * Synchronize our cache with the wrapped collection.
	 */
	protected void rebuildFilteredItems() {
		this.filteredItems.clear();
		CollectionTools.addAll(this.filteredItems, this.filter(this.collectionHolder.iterator()));
		this.fireCollectionChanged(VALUES);
	}

}
