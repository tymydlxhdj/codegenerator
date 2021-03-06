/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package com.mqfdy.code.springboot.utilities.internal.iterators;

import java.util.Iterator;

import com.mqfdy.code.springboot.utilities.internal.StringTools;


// TODO: Auto-generated Javadoc
/**
 * Wrap an iterator on elements of any sub-type of E, converting it into an
 * iterator on elements of type E. This shouldn't be a problem since there is no
 * way to add elements to the iterator.
 *
 * @author mqfdy
 * @param <E>
 *            the element type
 */
public class GenericIteratorWrapper<E>
	implements Iterator<E>
{
	private final Iterator<? extends E> iterator;

	/**
	 * Instantiates a new generic iterator wrapper.
	 *
	 * @param iterator
	 *            the iterator
	 */
	public GenericIteratorWrapper(Iterator<? extends E> iterator) {
		super();
		this.iterator = iterator;
	}

	public boolean hasNext() {
		return this.iterator.hasNext();
	}

	public E next() {
		return this.iterator.next();
	}

	public void remove() {
		this.iterator.remove();
	}

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, this.iterator);
	}

}
