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

import com.mqfdy.code.springboot.utilities.internal.StringTools;


// TODO: Auto-generated Javadoc
/**
 * A <code>ChainIterator</code> provides a pluggable <code>Iterator</code> that
 * loops over a chain of arbitrarily linked objects. The chain should be
 * null-terminated (i.e. a call to the <code>nextLink(Object)</code> method
 * should return <code>null</code> when it is passed the last link of the
 * chain). To use, supply a starting link and supply a <code>Linker</code> or
 * subclass <code>ChainIterator</code> and override the
 * <code>nextLink(Object)</code> method. The starting link will be the first
 * object returned by the iterator. If the starting link is <code>null</code>,
 * the iterator will be empty. Note that the iterator does not support
 * <code>null</code> elements.
 *
 * @author mqfdy
 * @param <E>
 *            the element type
 */
public class ChainIterator<E>
	implements Iterator<E>
{
	private E nextLink;
	private final Linker<E> linker;


	/**
	 * Construct an iterator with the specified starting link and a disabled
	 * linker. Use this constructor if you want to override the
	 * <code>nextLink(Object)</code> method instead of building a
	 * <code>Linker</code>.
	 *
	 * @param startLink
	 *            the start link
	 */
	public ChainIterator(E startLink) {
		this(startLink, Linker.Disabled.<E>instance());
	}
	
	/**
	 * Construct an iterator with the specified starting link and linker.
	 *
	 * @param startLink
	 *            the start link
	 * @param linker
	 *            the linker
	 */
	public ChainIterator(E startLink, Linker<E> linker) {
		super();
		this.nextLink = startLink;
		this.linker = linker;
	}
	
	public boolean hasNext() {
		return this.nextLink != null;
	}
	
	public E next() {
		if (this.nextLink == null) {
			throw new NoSuchElementException();
		}
		E result = this.nextLink;
		this.nextLink = this.nextLink(this.nextLink);
		return result;
	}
	
	public void remove() {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Return the next link in the chain.
	 *
	 * @author mqfdy
	 * @param currentLink
	 *            the current link
	 * @return the e
	 * @Date 2018-09-03 09:00
	 */
	protected E nextLink(E currentLink) {
		return this.linker.nextLink(currentLink);
	}
	
	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, this.nextLink);
	}
	

	//********** inner classes **********

	/**
	 * Used by <code>ChainIterator</code> to link the elements in the chain.
	 *
	 * @author mqfdy
	 * @param <T>
	 *            the generic type
	 */
	public interface Linker<T> {

		/**
		 * Return the next link in the chain.
		 */
		T nextLink(T currentLink);


		final class Null<S> implements Linker<S> {
			@SuppressWarnings("unchecked")
			public static final Linker INSTANCE = new Null();
			@SuppressWarnings("unchecked")
			public static <R> Linker<R> instance() {
				return INSTANCE;
			}
			// ensure single instance
			private Null() {
				super();
			}
			// simply return null, indicating the chain is ended
			public S nextLink(S currentLink) {
				return null;
			}
			@Override
			public String toString() {
				return "ChainIterator.Linker.Null";
			}
		}

		final class Disabled<S> implements Linker<S> {
			@SuppressWarnings("unchecked")
			public static final Linker INSTANCE = new Disabled();
			@SuppressWarnings("unchecked")
			public static <R> Linker<R> instance() {
				return INSTANCE;
			}
			// ensure single instance
			private Disabled() {
				super();
			}
			// throw an exception
			public S nextLink(S currentLink) {
				throw new UnsupportedOperationException();  // ChainIterator.nextLink(Object) was not implemented
			}
			@Override
			public String toString() {
				return "ChainIterator.Linker.Disabled";
			}
		}

	}

}
