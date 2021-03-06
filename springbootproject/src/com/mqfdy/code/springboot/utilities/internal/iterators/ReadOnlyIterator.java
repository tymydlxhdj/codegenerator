/*******************************************************************************
 * Copyright (c) 2005, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package com.mqfdy.code.springboot.utilities.internal.iterators;

import java.util.Collection;
import java.util.Iterator;

import com.mqfdy.code.springboot.utilities.internal.StringTools;


// TODO: Auto-generated Javadoc
/**
 * A <code>ReadOnlyIterator</code> wraps another <code>Iterator</code> and
 * removes support for #remove().
 *
 * @author mqfdy
 * @param <E>
 *            the element type
 */
public class ReadOnlyIterator<E>
	implements Iterator<E>
{
	private final Iterator<? extends E> nestedIterator;

	/**
	 * Construct an iterator on the specified collection that disallows removes.
	 *
	 * @param c
	 *            the c
	 */
	public ReadOnlyIterator(Collection<? extends E> c) {
		this(c.iterator());
	}

	/**
	 * Construct an iterator with the specified nested iterator and disallow
	 * removes.
	 *
	 * @param nestedIterator
	 *            the nested iterator
	 */
	public ReadOnlyIterator(Iterator<? extends E> nestedIterator) {
		super();
		this.nestedIterator = nestedIterator;
	}

	public boolean hasNext() {
		// delegate to the nested iterator
		return this.nestedIterator.hasNext();
	}

	public E next() {
		// delegate to the nested iterator
		return this.nestedIterator.next();
	}

	public void remove() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, this.nestedIterator);
	}
	
}
