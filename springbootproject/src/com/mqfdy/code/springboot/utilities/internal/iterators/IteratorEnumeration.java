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

import java.util.Enumeration;
import java.util.Iterator;

import com.mqfdy.code.springboot.utilities.internal.StringTools;


// TODO: Auto-generated Javadoc
/**
 * An <code>IteratorEnumeration</code> wraps an <code>Iterator</code> so that it
 * can be treated like an <code>Enumeration</code>.
 *
 * @author mqfdy
 * @param <E>
 *            the element type
 */
public class IteratorEnumeration<E>
	implements Enumeration<E>
{
	private final Iterator<? extends E> iterator;

	/**
	 * Construct an enumeration that wraps the specified iterator.
	 *
	 * @param iterator
	 *            the iterator
	 */
	public IteratorEnumeration(Iterator<? extends E> iterator) {
		super();
		this.iterator = iterator;
	}

	public boolean hasMoreElements() {
		return this.iterator.hasNext();
	}

	public E nextElement() {
		return this.iterator.next();
	}

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, this.iterator);
	}
	
}
