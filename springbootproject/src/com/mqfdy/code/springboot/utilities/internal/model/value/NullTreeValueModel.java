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

import com.mqfdy.code.springboot.utilities.internal.ClassTools;
import com.mqfdy.code.springboot.utilities.internal.iterators.EmptyIterator;
import com.mqfdy.code.springboot.utilities.internal.model.AbstractModel;
import com.mqfdy.code.springboot.utilities.model.value.TreeValueModel;


/**
 * A tree value model for when you don't need to support any nodes.
 * 
 * We don't use a singleton because we hold on to listeners.
 *
 * @author mqfdy
 * @param <E>
 *            the element type
 */
public class NullTreeValueModel<E>
	extends AbstractModel
	implements TreeValueModel<E>
{
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public NullTreeValueModel() {
		super();
	}
	

	// ********** TreeValueModel implementation **********

	public Iterator<E> nodes() {
		return EmptyIterator.instance();
	}


	// ********** Object overrides **********

    @Override
	public String toString() {
		return ClassTools.shortClassNameForObject(this);
	}

}
