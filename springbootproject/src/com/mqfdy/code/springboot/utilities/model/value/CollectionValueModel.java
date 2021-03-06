/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package com.mqfdy.code.springboot.utilities.model.value;

import java.util.Iterator;

import com.mqfdy.code.springboot.utilities.model.Model;


/**
 * Interface used to abstract collection accessing and change notification and
 * make it more pluggable.
 * 
 * Provisional API: This interface is part of an interim API that is still under
 * development and expected to change significantly before reaching stability.
 * It is available at this early stage to solicit feedback from pioneering
 * adopters on the understanding that any code that uses this API will almost
 * certainly be broken (repeatedly) as the API evolves.
 *
 * @author mqfdy
 * @param <E>
 *            the element type
 */
public interface CollectionValueModel<E>
	extends Model, Iterable<E>
{

	/**
	 * Return the collection's values.
	 */
	Iterator<E> iterator();
		String VALUES = "values";

	/**
	 * Return the size of the collection value.
	 */
	int size();

}
