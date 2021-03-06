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
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import com.mqfdy.code.springboot.utilities.internal.CollectionTools;
import com.mqfdy.code.springboot.utilities.internal.iterators.ReadOnlyCompositeListIterator;
import com.mqfdy.code.springboot.utilities.internal.iterators.ReadOnlyListIterator;
import com.mqfdy.code.springboot.utilities.model.event.ListChangeEvent;
import com.mqfdy.code.springboot.utilities.model.value.ListValueModel;


// TODO: Auto-generated Javadoc
/**
 * This wrapper extends a ListValueModel (or CollectionValueModel) with fixed
 * collections of items on either end.
 * <p>
 * NB: Be careful using or wrapping this list value model, since the "extended"
 * items may be unexpected by the client code or wrapper.
 *
 * @author mqfdy
 * @param <E>
 *            the element type
 */
public class ExtendedListValueModelWrapper<E>
	extends ListValueModelWrapper<E>
	implements ListValueModel<E>
{
	/** the items "prepended" to the wrapped list */
	protected List<E> prefix;

	/** the items "appended" to the wrapped list */
	protected List<E> suffix;


	// ********** lots o' constructors **********

	/**
	 * Extend the specified list with a prefix and suffix.
	 *
	 * @param prefix
	 *            the prefix
	 * @param listHolder
	 *            the list holder
	 * @param suffix
	 *            the suffix
	 */
	public ExtendedListValueModelWrapper(List<? extends E> prefix, ListValueModel<? extends E> listHolder, List<? extends E> suffix) {
		super(listHolder);
		this.prefix = new ArrayList<E>(prefix);
		this.suffix = new ArrayList<E>(suffix);
	}

	/**
	 * Extend the specified list with a prefix and suffix.
	 *
	 * @param prefix
	 *            the prefix
	 * @param listHolder
	 *            the list holder
	 * @param suffix
	 *            the suffix
	 */
	public ExtendedListValueModelWrapper(E prefix, ListValueModel<? extends E> listHolder, E suffix) {
		super(listHolder);
		this.prefix = Collections.singletonList(prefix);
		this.suffix = Collections.singletonList(suffix);
	}

	/**
	 * Extend the specified list with a prefix.
	 *
	 * @param prefix
	 *            the prefix
	 * @param listHolder
	 *            the list holder
	 */
	public ExtendedListValueModelWrapper(List<? extends E> prefix, ListValueModel<? extends E> listHolder) {
		super(listHolder);
		this.prefix = new ArrayList<E>(prefix);
		this.suffix = Collections.emptyList();
	}

	/**
	 * Extend the specified list with a prefix.
	 *
	 * @param prefix
	 *            the prefix
	 * @param listHolder
	 *            the list holder
	 */
	public ExtendedListValueModelWrapper(E prefix, ListValueModel<? extends E> listHolder) {
		super(listHolder);
		this.prefix = Collections.singletonList(prefix);
		this.suffix = Collections.emptyList();
	}

	/**
	 * Extend the specified list with a suffix.
	 *
	 * @param listHolder
	 *            the list holder
	 * @param suffix
	 *            the suffix
	 */
	public ExtendedListValueModelWrapper(ListValueModel<? extends E> listHolder, List<? extends E> suffix) {
		super(listHolder);
		this.prefix = Collections.emptyList();
		this.suffix = new ArrayList<E>(suffix);
	}

	/**
	 * Extend the specified list with a suffix.
	 *
	 * @param listHolder
	 *            the list holder
	 * @param suffix
	 *            the suffix
	 */
	public ExtendedListValueModelWrapper(ListValueModel<? extends E> listHolder, E suffix) {
		super(listHolder);
		this.prefix = Collections.emptyList();
		this.suffix = Collections.singletonList(suffix);
	}

	/**
	 * Extend the specified list with a prefix containing a single null item.
	 *
	 * @param listHolder
	 *            the list holder
	 */
	public ExtendedListValueModelWrapper(ListValueModel<? extends E> listHolder) {
		super(listHolder);
		this.prefix = Collections.singletonList(null);
		this.suffix = Collections.emptyList();
	}


	// ********** ListValueModel implementation **********

	public Iterator<E> iterator() {
		return this.listIterator();
	}

	public ListIterator<E> listIterator() {
		return new ReadOnlyListIterator<E>(this.listIterator_());
	}

	/**
	 * List iterator.
	 *
	 * @author mqfdy
	 * @return the list iterator
	 * @Date 2018-09-03 09:00
	 */
	@SuppressWarnings("unchecked")
	protected ListIterator<E> listIterator_() {
		return new ReadOnlyCompositeListIterator<E>(
			this.prefix.listIterator(),
			this.listHolder.listIterator(),
			this.suffix.listIterator()
		);
	}

	public E get(int index) {
		int prefixSize = this.prefix.size();
		if (index < prefixSize) {
			return this.prefix.get(index);
		} else if (index >= prefixSize + this.listHolder.size()) {
			return this.suffix.get(index - (prefixSize + this.listHolder.size()));
		} else {
			return this.listHolder.get(index - prefixSize);
		}
	}

	public int size() {
		return this.prefix.size() + this.listHolder.size() + this.suffix.size();
	}

	public Object[] toArray() {
		ArrayList<E> list = new ArrayList<E>(this.size());
		list.addAll(this.prefix);
		CollectionTools.addAll(list, this.listHolder.iterator());
		list.addAll(this.suffix);
		return list.toArray();
	}


	// ********** ListValueModelWrapper implementation/overrides **********

	@Override
	protected void itemsAdded(ListChangeEvent event) {
		this.fireItemsAdded(event.cloneWithSource(this, LIST_VALUES, this.prefix.size()));
	}

	@Override
	protected void itemsRemoved(ListChangeEvent event) {
		this.fireItemsRemoved(event.cloneWithSource(this, LIST_VALUES, this.prefix.size()));
	}

	@Override
	protected void itemsReplaced(ListChangeEvent event) {
		this.fireItemsReplaced(event.cloneWithSource(this, LIST_VALUES, this.prefix.size()));
	}

	@Override
	protected void itemsMoved(ListChangeEvent event) {
		this.fireItemsMoved(event.cloneWithSource(this, LIST_VALUES, this.prefix.size()));
	}

	@Override
	protected void listCleared(ListChangeEvent event) {
		this.fireListChanged(LIST_VALUES);  // not "cleared"
	}

	@Override
	protected void listChanged(ListChangeEvent event) {
		this.fireListChanged(LIST_VALUES);
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.prefix);
		sb.append(" ");
		super.toString(sb);
		sb.append(" ");
		sb.append(this.suffix);
	}


	// ********** miscellaneous **********

	/**
	 * Sets the prefix.
	 *
	 * @author mqfdy
	 * @param prefix
	 *            the new prefix
	 * @Date 2018-09-03 09:00
	 */
	public void setPrefix(List<E> prefix) {
		this.prefix = prefix;
		this.fireListChanged(LIST_VALUES);
	}

	/**
	 * Sets the suffix.
	 *
	 * @author mqfdy
	 * @param suffix
	 *            the new suffix
	 * @Date 2018-09-03 09:00
	 */
	public void setSuffix(List<E> suffix) {
		this.suffix = suffix;
		this.fireListChanged(LIST_VALUES);
	}

}
