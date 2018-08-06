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
import java.util.NoSuchElementException;

import com.mqfdy.code.springboot.utilities.internal.StringTools;


/**
 * A <code>NullEnumeration</code> is just that.
 */
public final class EmptyEnumeration<E>
	implements Enumeration<E>
{

	// singleton
	@SuppressWarnings("unchecked")
	private static final EmptyEnumeration INSTANCE = new EmptyEnumeration();

	/**
	 * Return the singleton.
	 */
	@SuppressWarnings("unchecked")
	public static <T> Enumeration<T> instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private EmptyEnumeration() {
		super();
	}

	public boolean hasMoreElements() {
		return false;
	}

	public E nextElement() {
		throw new NoSuchElementException();
	}

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this);
	}

}