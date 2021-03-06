/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package com.mqfdy.code.springboot.utilities.internal.model.value;

import java.util.Iterator;
import java.util.ListIterator;

import com.mqfdy.code.springboot.utilities.internal.ClassTools;
import com.mqfdy.code.springboot.utilities.internal.iterators.EmptyIterator;
import com.mqfdy.code.springboot.utilities.internal.iterators.EmptyListIterator;
import com.mqfdy.code.springboot.utilities.internal.model.AbstractModel;
import com.mqfdy.code.springboot.utilities.model.value.ListValueModel;



/**
 * An empty list value model for when you don't need to support a list.
 * 
 * We don't use a singleton because we hold on to listeners.
 *
 * @author mqfdy
 * @param <E>
 *            the element type
 */
public final class NullListValueModel<E>
	extends AbstractModel
	implements ListValueModel<E>
{
	private static final Object[] EMPTY_ARRAY = new Object[0];
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public NullListValueModel() {
		super();
	}


	// ********** ListValueModel implementation **********

	public Iterator<E> iterator() {
		return EmptyIterator.instance();
	}

	public ListIterator<E> listIterator() {
		return EmptyListIterator.instance();
	}

	public int size() {
		return 0;
	}

	public E get(int index) {
		throw new IndexOutOfBoundsException("Index: " + index + ", Size: 0");
	}

	public Object[] toArray() {
		return EMPTY_ARRAY;
	}


	// ********** Object overrides **********

	@Override
	public String toString() {
		return ClassTools.shortClassNameForObject(this);
	}

}
