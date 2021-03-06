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
import java.util.Comparator;

// TODO: Auto-generated Javadoc
/**
 * This comparator will reverse the order of the specified comparator. If the
 * comparator is null, the natural ordering of the objects will be used.
 *
 * @author mqfdy
 * @param <E>
 *            the element type
 */
public class ReverseComparator<E extends Comparable<? super E>>
	implements Comparator<E>, Serializable
{
	private final Comparator<E> comparator;

	/**
	 * Instantiates a new reverse comparator.
	 */
	public ReverseComparator() {
		this(null);
	}

	/**
	 * Instantiates a new reverse comparator.
	 *
	 * @param comparator
	 *            the comparator
	 */
	public ReverseComparator(Comparator<E> comparator) {
		super();
		this.comparator = comparator;
	}

	public int compare(E e1, E e2) {
		return (this.comparator == null) ?
			e2.compareTo(e1)
		:
			this.comparator.compare(e2, e1);
	}

}
