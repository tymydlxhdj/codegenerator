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

import com.mqfdy.code.springboot.utilities.internal.StringTools;


// TODO: Auto-generated Javadoc
/**
 * An <code>EmptyIterator</code> is just that.
 *
 * @author mqfdy
 * @param <E>
 *            the element type
 */
public final class EmptyIterator<E>
	implements Iterator<E>
{

	// singleton
	@SuppressWarnings("unchecked")
	private static final EmptyIterator INSTANCE = new EmptyIterator();

	/**
	 * Return the singleton.
	 *
	 * @author mqfdy
	 * @param <T>
	 *            the generic type
	 * @return the iterator
	 * @Date 2018-09-03 09:00
	 */
	@SuppressWarnings("unchecked")
	public static <T> Iterator<T> instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private EmptyIterator() {
		super();
	}

	public boolean hasNext() {
		return false;
	}

	public E next() {
		throw new NoSuchElementException();
	}

	public void remove() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this);
	}

}
