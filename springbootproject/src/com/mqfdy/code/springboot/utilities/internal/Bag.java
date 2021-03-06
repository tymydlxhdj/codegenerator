/*******************************************************************************
 * Copyright (c) 2005, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package com.mqfdy.code.springboot.utilities.internal;

import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.Iterator;

import com.mqfdy.code.springboot.utilities.internal.iterators.EmptyIterator;


/**
 * A collection that allows duplicate elements.
 * <p>
 * The <code>Bag</code> interface places additional stipulations, beyond those
 * inherited from the <code>java.util.Collection</code> interface, on the
 * contracts of the <code>equals</code> and <code>hashCode</code> methods.
 *
 * @param <E>
 *            the element type
 * @see HashBag
 */

public interface Bag<E> extends java.util.Collection<E> {

	/**
	 * Compares the specified object with this bag for equality. Returns
	 * <code>true</code> if the specified object is also a bag, the two bags
	 * have the same size, and every member of the specified bag is
	 * contained in this bag with the same number of occurrences (or equivalently,
	 * every member of this bag is contained in the specified bag with the same
	 * number of occurrences). This definition ensures that the
	 * equals method works properly across different implementations of the
	 * bag interface.
	 */
	boolean equals(Object o);

	/**
	 * Returns the hash code value for this bag. The hash code of a bag is
	 * defined to be the sum of the hash codes of the elements in the bag,
	 * where the hashcode of a <code>null</code> element is defined to be zero.
	 * This ensures that <code>b1.equals(b2)</code> implies that
	 * <code>b1.hashCode() == b2.hashCode()</code> for any two bags
	 * <code>b1</code> and <code>b2</code>, as required by the general
	 * contract of the <code>Object.hashCode</code> method.
	 */
	int hashCode();

	/**
	 * Return the number of times the specified object occurs in the bag.
	 */
	int count(Object o);

	/**
	 * Add the specified object the specified number of times to the bag.
	 */
	boolean add(E o, int count);

	/**
	 * Remove the specified number of occurrences of the specified object
	 * from the bag. Return whether the bag changed.
	 */
	boolean remove(Object o, int count);

	/**
	 * Return an iterator that returns each item in the bag
	 * once and only once, irrespective of how many times
	 * the item was added to the bag.
	 */
	java.util.Iterator<E> uniqueIterator();


	final class Empty<E> extends AbstractCollection<E> implements Bag<E>, Serializable {
		@SuppressWarnings("unchecked")
		public static final Bag INSTANCE = new Empty();
		@SuppressWarnings("unchecked")
		public static <T> Bag<T> instance() {
			return INSTANCE;
		}
		// ensure single instance
		private Empty() {
			super();
		}
		@Override
		public Iterator<E> iterator() {
			return EmptyIterator.instance();
		}
		@Override
		public int size() {
			return 0;
		}
		public Iterator<E> uniqueIterator() {
			return EmptyIterator.instance();
		}
		public int count(Object o) {
			return 0;
		}
		public boolean remove(Object o, int count) {
			return false;
		}
		public boolean add(E o, int count) {
			throw new UnsupportedOperationException();
		}
		@Override
		public boolean equals(Object o) {
			if (o == this) {
				return true;
			}
			if ( ! (o instanceof Bag)) {
				return false;
			}
			return ((Bag<?>) o).size() == 0;
		}
		@Override
		public int hashCode() {
			return 0;
		}
		private static final long serialVersionUID = 1L;
		private Object readResolve() {
			return INSTANCE;
		}
	}

}
