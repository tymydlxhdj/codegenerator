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
 * An <code>EnumerationIterator</code> wraps an <code>Enumeration</code> so that
 * it can be treated like an <code>Iterator</code>.
 *
 * @author mqfdy
 * @param <E>
 *            the element type
 */
public class EnumerationIterator<E>
	implements Iterator<E>
{
	private final Enumeration<? extends E> enumeration;

	/**
	 * Construct an iterator that wraps the specified enumeration.
	 *
	 * @param enumeration
	 *            the enumeration
	 */
	public EnumerationIterator(Enumeration<? extends E> enumeration) {
		this.enumeration = enumeration;
	}
	
	public boolean hasNext() {
		return this.enumeration.hasMoreElements();
	}
	
	public E next() {
		return this.enumeration.nextElement();
	}
	
	public void remove() {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, this.enumeration);
	}
	
}
