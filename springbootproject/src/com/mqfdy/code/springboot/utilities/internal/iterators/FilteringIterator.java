/*******************************************************************************
 * Copyright (c) 2005, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package com.mqfdy.code.springboot.utilities.internal.iterators;

import java.util.Iterator;
import java.util.NoSuchElementException;

import com.mqfdy.code.springboot.utilities.Filter;
import com.mqfdy.code.springboot.utilities.internal.StringTools;


// TODO: Auto-generated Javadoc
/**
 * A <code>FilteringIterator</code> wraps another <code>Iterator</code> and uses
 * a <code>Filter</code> to determine which elements in the nested iterator are
 * to be returned by calls to <code>next()</code>.
 * <p>
 * As an alternative to building a <code>Filter</code>, a subclass of
 * <code>FilteringIterator</code> can override the <code>accept(Object)</code>
 * method.
 * <p>
 * One, possibly undesirable, side-effect of using this iterator is that the
 * nested iterator's <code>next()</code> method will be invoked <em>before</em>
 * the filtered iterator's <code>next()</code> method is invoked. This is
 * because the "next" element must be checked for whether it is to be accepted
 * before the filtered iterator can determine whether it has a "next" element
 * (i.e. that the <code>hasNext()</code> method should return
 * <code>true</code>). This also prevents a filtered iterator from supporting
 * the optional <code>remove()</code> method.
 *
 * @author mqfdy
 * @param <E1>
 *            the generic type
 * @param <E2>
 *            the generic type
 */
public class FilteringIterator<E1, E2>
	implements Iterator<E2>
{
	private final Iterator<? extends E1> nestedIterator;
	private final Filter<E1> filter;
	private E2 next;
	private boolean done;


	/**
	 * Construct an iterator with the specified nested iterator and a disabled
	 * filter. Use this constructor if you want to override the
	 * <code>accept(Object)</code> method instead of building a
	 * <code>Filter</code>.
	 *
	 * @param nestedIterator
	 *            the nested iterator
	 */
	public FilteringIterator(Iterator<? extends E1> nestedIterator) {
		this(nestedIterator, Filter.Disabled.<E1>instance());
	}

	/**
	 * Construct an iterator with the specified nested iterator and filter.
	 *
	 * @param nestedIterator
	 *            the nested iterator
	 * @param filter
	 *            the filter
	 */
	public FilteringIterator(Iterator<? extends E1> nestedIterator, Filter<E1> filter) {
		super();
		this.nestedIterator = nestedIterator;
		this.filter = filter;
		this.loadNext();
	}

	public boolean hasNext() {
		return ! this.done;
	}

	public E2 next() {
		if (this.done) {
			throw new NoSuchElementException();
		}
		E2 result = this.next;
		this.loadNext();
		return result;
	}

	/**
	 * Because we need to pre-load the next element
	 * to be returned, we cannot support the <code>remove()</code>
	 * method.
	 */
	public void remove() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Load next with the next valid entry from the nested
	 * iterator. If there are none, next is set to <code>END</code>.
	 */
	private void loadNext() {
		this.done = true;
		while (this.nestedIterator.hasNext() && (this.done)) {
			E1 temp = this.nestedIterator.next();
			if (this.accept(temp)) {
				// assume that if the object was accepted it is of type E
				this.next = this.cast(temp);
				this.done = false;
			} else {
				this.next = null;
				this.done = true;
			}
		}
	}

	/**
	 * We have to assume the filter will only "accept" objects that can
	 * be cast to E2.
	 */
	@SuppressWarnings("unchecked")
	private E2 cast(E1 o) {
		return (E2) o;
	}

	/**
	 * Return whether the <code>FilteringIterator</code> should return the
	 * specified next element from a call to the <code>next()</code> method.
	 * <p>
	 * This method can be overridden by a subclass as an alternative to building
	 * a <code>Filter</code>.
	 *
	 * @author mqfdy
	 * @param o
	 *            the o
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	protected boolean accept(E1 o) {
		return this.filter.accept(o);
	}

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, this.nestedIterator);
	}

}
