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
import java.util.List;
import java.util.ListIterator;

import com.mqfdy.code.springboot.utilities.internal.StringTools;
import com.mqfdy.code.springboot.utilities.internal.iterators.ReadOnlyIterator;
import com.mqfdy.code.springboot.utilities.internal.iterators.ReadOnlyListIterator;
import com.mqfdy.code.springboot.utilities.internal.model.AbstractModel;
import com.mqfdy.code.springboot.utilities.model.value.ListValueModel;


// TODO: Auto-generated Javadoc
/**
 * Implementation of ListValueModel that can be used for returning a list
 * iterator on a static list, but still allows listeners to be added. Listeners
 * will NEVER be notified of any changes, because there should be none.
 *
 * @author mqfdy
 * @param <E>
 *            the element type
 */
public class StaticListValueModel<E>
	extends AbstractModel
	implements ListValueModel<E>
{
	/** The value. */
	protected final List<? extends E> list;

	private static final long serialVersionUID = 1L;


	/**
	 * Construct a static ListValueModel for the specified list.
	 *
	 * @param list
	 *            the list
	 */
	public StaticListValueModel(List<? extends E> list) {
		super();
		if (list == null) {
			throw new NullPointerException();
		}
		this.list = list;
	}


	// ********** ListValueModel implementation **********

	public Iterator<E> iterator() {
		return new ReadOnlyIterator<E>(this.list.iterator());
	}

	public ListIterator<E> listIterator() {
		return new ReadOnlyListIterator<E>(this.list.listIterator());
	}

	public int size() {
		return this.list.size();
	}

	public E get(int index) {
		return this.list.get(index);
	}

	public Object[] toArray() {
		return this.list.toArray();
	}


	// ********** Object overrides **********

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, this.list);
	}

}
