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

import java.util.ListIterator;
import java.util.NoSuchElementException;

// TODO: Auto-generated Javadoc
/**
 * An <code>ArrayListIterator</code> provides a <code>ListIterator</code> for an
 * array of objects.
 * 
 * The name might be a bit confusing: This is a <code>ListIterator</code> for an
 * <code>Array</code>; <em>not</em> an <code>Iterator</code> for an
 * <code>ArrayList</code>.
 *
 * @author mqfdy
 * @param <E>
 *            the element type
 */
public class ArrayListIterator<E>
	extends ArrayIterator<E>
	implements ListIterator<E>
{
	private final int minIndex;

	/**
	 * Construct a list iterator for the specified array.
	 *
	 * @param array
	 *            the array
	 */
	public ArrayListIterator(E... array) {
		this(array, 0, array.length);
	}
	
	/**
	 * Construct a list iterator for the specified array, starting at the
	 * specified start index and continuing for the specified length.
	 *
	 * @param array
	 *            the array
	 * @param start
	 *            the start
	 * @param length
	 *            the length
	 */
	public ArrayListIterator(E[] array, int start, int length) {
		super(array, start, length);
		this.minIndex = start;
	}
	
	public int nextIndex() {
		return this.nextIndex;
	}
	
	public int previousIndex() {
		return this.nextIndex - 1;
	}
	
	public boolean hasPrevious() {
		return this.nextIndex > this.minIndex;
	}
	
	public E previous() {
		if (this.hasPrevious()) {
			return this.array[--this.nextIndex];
		}
		throw new NoSuchElementException();
	}
	
	public void add(E e) {
		throw new UnsupportedOperationException();
	}
	
	public void set(E e) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public String toString() {
		return super.toString();
	}
	
}
