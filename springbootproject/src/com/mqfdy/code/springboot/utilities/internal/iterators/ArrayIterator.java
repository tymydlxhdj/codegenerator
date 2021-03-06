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

import java.util.Iterator;
import java.util.NoSuchElementException;

import com.mqfdy.code.springboot.utilities.internal.CollectionTools;
import com.mqfdy.code.springboot.utilities.internal.StringTools;


// TODO: Auto-generated Javadoc
/**
 * An <code>ArrayIterator</code> provides an <code>Iterator</code> for an array
 * of objects of type E.
 *
 * @author mqfdy
 * @param <E>
 *            the element type
 */
public class ArrayIterator<E>
	implements Iterator<E>
{
	final E[] array;	// private-protected
	int nextIndex;		// private-protected
	private final int maxIndex;

	/**
	 * Construct an iterator for the specified array.
	 *
	 * @param array
	 *            the array
	 */
	public ArrayIterator(E... array) {
		this(array, 0, array.length);
	}
	
	/**
	 * Construct an iterator for the specified array, starting at the specified
	 * start index and continuing for the specified length.
	 *
	 * @param array
	 *            the array
	 * @param start
	 *            the start
	 * @param length
	 *            the length
	 */
	public ArrayIterator(E[] array, int start, int length) {
		if ((start < 0) || (start > array.length)) {
			throw new IllegalArgumentException("start: " + start);
		}
		if ((length < 0) || (length > array.length - start)) {
			throw new IllegalArgumentException("length: " + length);
		}
		this.array = array;
		this.nextIndex = start;
		this.maxIndex = start + length;
	}
	
	public boolean hasNext() {
		return this.nextIndex < this.maxIndex;
	}
	
	public E next() {
		if (this.hasNext()) {
			return this.array[this.nextIndex++];
		}
		throw new NoSuchElementException();
	}
	
	public void remove() {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, CollectionTools.list(this.array));
	}
	
}
