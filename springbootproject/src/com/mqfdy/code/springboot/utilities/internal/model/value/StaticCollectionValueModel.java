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

import java.util.Collection;
import java.util.Iterator;

import com.mqfdy.code.springboot.utilities.internal.StringTools;
import com.mqfdy.code.springboot.utilities.internal.iterators.ReadOnlyIterator;
import com.mqfdy.code.springboot.utilities.internal.model.AbstractModel;
import com.mqfdy.code.springboot.utilities.model.value.CollectionValueModel;


// TODO: Auto-generated Javadoc
/**
 * Implementation of CollectionValueModel that can be used for returning an
 * iterator on a static collection, but still allows listeners to be added.
 * Listeners will NEVER be notified of any changes, because there should be
 * none.
 *
 * @author mqfdy
 * @param <E>
 *            the element type
 */
public class StaticCollectionValueModel<E>
	extends AbstractModel
	implements CollectionValueModel<E>
{
	/** The collection. */
	protected final Collection<? extends E> collection;

	private static final long serialVersionUID = 1L;


	/**
	 * Construct a static CollectionValueModel for the specified collection.
	 *
	 * @param collection
	 *            the collection
	 */
	public StaticCollectionValueModel(Collection<? extends E> collection) {
		super();
		if (collection == null) {
			throw new NullPointerException();
		}
		this.collection = collection;
	}

	// ********** CollectionValueModel implementation **********

	public int size() {
		return this.collection.size();
	}

	public Iterator<E> iterator() {
		return new ReadOnlyIterator<E>(this.collection.iterator());
	}


	// ********** Object overrides **********

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, this.collection);
	}

}
