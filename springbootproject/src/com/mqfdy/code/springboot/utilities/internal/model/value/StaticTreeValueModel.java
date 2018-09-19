/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package com.mqfdy.code.springboot.utilities.internal.model.value;

import java.util.Iterator;

import com.mqfdy.code.springboot.utilities.internal.CollectionTools;
import com.mqfdy.code.springboot.utilities.internal.StringTools;
import com.mqfdy.code.springboot.utilities.internal.iterators.ReadOnlyIterator;
import com.mqfdy.code.springboot.utilities.internal.model.AbstractModel;
import com.mqfdy.code.springboot.utilities.model.value.TreeValueModel;


// TODO: Auto-generated Javadoc
/**
 * Implementation of TreeValueModel that can be used for returning an iterator
 * on a static tree, but still allows listeners to be added. Listeners will
 * NEVER be notified of any changes, because there should be none.
 *
 * @author mqfdy
 * @param <E>
 *            the element type
 */
public class StaticTreeValueModel<E>
	extends AbstractModel
	implements TreeValueModel<E>
{
	/** The tree's nodes. */
	protected final Iterable<? extends E> nodes;

	private static final long serialVersionUID = 1L;


	/**
	 * Construct a read-only TreeValueModel for the specified nodes.
	 *
	 * @param nodes
	 *            the nodes
	 */
	public StaticTreeValueModel(Iterable<? extends E> nodes) {
		super();
		if (nodes == null) {
			throw new NullPointerException();
		}
		this.nodes = nodes;
	}

	// ********** TreeValueModel implementation **********

	public Iterator<E> nodes() {
		return new ReadOnlyIterator<E>(this.nodes.iterator());
	}


	// ********** Object overrides **********

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, CollectionTools.collection(this.nodes()));
	}

}
