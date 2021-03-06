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

import java.util.Collection;
import java.util.Iterator;

import com.mqfdy.code.springboot.utilities.internal.StringTools;

// TODO: Auto-generated Javadoc
/**
 * A <code>CloneIterator</code> iterates over a copy of a collection, allowing
 * for concurrent access to the original collection.
 * <p>
 * The original collection passed to the <code>CloneIterator</code>'s
 * constructor should be synchronized; otherwise you run the risk of a corrupted
 * collection.
 * <p>
 * By default, a <code>CloneIterator</code> does not support the
 * <code>#remove()</code> operation; this is because it does not have access to
 * the original collection. But if the <code>CloneIterator</code> is supplied
 * with an <code>Mutator</code> it will delegate the <code>#remove()</code>
 * operation to the <code>Mutator</code>. Alternatively, a subclass can override
 * the <code>#remove(Object)</code> method.
 *
 * @author mqfdy
 * @param <E>
 *            the element type
 */
public class CloneIterator<E>
	implements Iterator<E>
{
	private final Iterator<Object> nestedIterator;
	private E current;
	private final Mutator<E> mutator;
	private boolean removeAllowed;


	// ********** constructors **********

	/**
	 * Construct an iterator on a copy of the specified collection. The
	 * <code>#remove()</code> method will not be supported, unless a subclass
	 * overrides the <code>#remove(Object)</code>.
	 *
	 * @param c
	 *            the c
	 */
	public CloneIterator(Collection<? extends E> c) {
		this(c, Mutator.ReadOnly.<E>instance());
	}

	/**
	 * Construct an iterator on a copy of the specified collection. Use the
	 * specified mutator to remove objects from the original collection.
	 *
	 * @param c
	 *            the c
	 * @param mutator
	 *            the mutator
	 */
	public CloneIterator(Collection<? extends E> c, Mutator<E> mutator) {
		super();
		this.nestedIterator = new ArrayIterator<Object>(c.toArray());
		this.current = null;
		this.mutator = mutator;
		this.removeAllowed = false;
	}


	// ********** Iterator implementation **********

	public boolean hasNext() {
		return this.nestedIterator.hasNext();
	}

	public E next() {
		this.current = this.nestedNext();
		this.removeAllowed = true;
		return this.current;
	}

	public void remove() {
		if ( ! this.removeAllowed) {
			throw new IllegalStateException();
		}
		this.remove(this.current);
		this.removeAllowed = false;
	}


	// ********** internal methods **********

	/**
	 * The collection passed in during construction held Es, so this cast is not
	 * a problem. We need this cast because all the elements of the original
	 * collection were copied into an object array (Object[]).
	 *
	 * @author mqfdy
	 * @return the e
	 * @Date 2018-09-03 09:00
	 */
	@SuppressWarnings("unchecked")
	protected E nestedNext() {
		return (E) this.nestedIterator.next();
	}

	/**
	 * Remove the specified element from the original collection.
	 * <p>
	 * This method can be overridden by a subclass as an alternative to building
	 * an <code>Mutator</code>.
	 *
	 * @author mqfdy
	 * @param e
	 *            the e
	 * @Date 2018-09-03 09:00
	 */
	protected void remove(E e) {
		this.mutator.remove(e);
	}

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this);
	}


	//********** member interface **********

	/**
	 * Used by <code>CloneIterator</code> to remove elements from the original
	 * collection; since the iterator does not have direct access to the
	 * original collection.
	 *
	 * @author mqfdy
	 * @param <T>
	 *            the generic type
	 */
	public interface Mutator<T> {

		/**
		 * Remove the specified object from the original collection.
		 */
		void remove(T current);


		final class ReadOnly<S> implements Mutator<S> {
			@SuppressWarnings("unchecked")
			public static final Mutator INSTANCE = new ReadOnly();
			@SuppressWarnings("unchecked")
			public static <R> Mutator<R> instance() {
				return INSTANCE;
			}
			// ensure single instance
			private ReadOnly() {
				super();
			}
			// remove is not supported
			public void remove(Object current) {
				throw new UnsupportedOperationException();
			}
			@Override
			public String toString() {
				return "CloneIterator.Mutator.ReadOnly";
			}
		}

	}

}
