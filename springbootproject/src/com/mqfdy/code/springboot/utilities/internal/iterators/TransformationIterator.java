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

import com.mqfdy.code.springboot.utilities.internal.StringTools;
import com.mqfdy.code.springboot.utilities.internal.Transformer;



// TODO: Auto-generated Javadoc
/**
 * A <code>TransformationIterator</code> wraps another <code>Iterator</code> and
 * transforms its results for client consumption. To use, supply a
 * <code>Transformer</code> or subclass <code>TransformationIterator</code> and
 * override the <code>transform(Object)</code> method. Objects of type E1 are
 * transformed into objects of type E2; i.e. the iterator returns objects of
 * type E2.
 *
 * @author mqfdy
 * @param <E1>
 *            the generic type
 * @param <E2>
 *            the generic type
 */
public class TransformationIterator<E1, E2>
	implements Iterator<E2>
{
	private final Iterator<? extends E1> nestedIterator;
	private final Transformer<E1, ? extends E2> transformer;


	/**
	 * Construct an iterator with the specified nested iterator and a disabled
	 * transformer. Use this constructor if you want to override the
	 * <code>transform(Object)</code> method instead of building a
	 * <code>Transformer</code>.
	 *
	 * @param nestedIterator
	 *            the nested iterator
	 */
	public TransformationIterator(Iterator<? extends E1> nestedIterator) {
		this(nestedIterator, Transformer.Disabled.<E1, E2>instance());
	}

	/**
	 * Construct an iterator with the specified nested iterator and transformer.
	 *
	 * @param nestedIterator
	 *            the nested iterator
	 * @param transformer
	 *            the transformer
	 */
	public TransformationIterator(Iterator<? extends E1> nestedIterator, Transformer<E1, ? extends E2> transformer) {
		super();
		this.nestedIterator = nestedIterator;
		this.transformer = transformer;
	}

	public boolean hasNext() {
		// delegate to the nested iterator
		return this.nestedIterator.hasNext();
	}

	public E2 next() {
		// transform the object returned by the nested iterator before returning it
		return this.transform(this.nestedIterator.next());
	}

	public void remove() {
		// delegate to the nested iterator
		this.nestedIterator.remove();
	}

	/**
	 * Transform the specified object and return the result.
	 *
	 * @author mqfdy
	 * @param next
	 *            the next
	 * @return the e2
	 * @Date 2018-09-03 09:00
	 */
	protected E2 transform(E1 next) {
		return this.transformer.transform(next);
	}

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, this.nestedIterator);
	}

}
