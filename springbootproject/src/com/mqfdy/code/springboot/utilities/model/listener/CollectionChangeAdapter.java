/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package com.mqfdy.code.springboot.utilities.model.listener;

import com.mqfdy.code.springboot.utilities.model.event.CollectionChangeEvent;

/**
 * Convenience implementation of CollectionChangeListener.
 * 
 * Provisional API: This class is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public class CollectionChangeAdapter implements CollectionChangeListener {

	/**
	 * Default constructor.
	 */
	public CollectionChangeAdapter() {
		super();
	}

	public void itemsAdded(CollectionChangeEvent event) {
		// do nothing
	}

	public void itemsRemoved(CollectionChangeEvent event) {
		// do nothing
	}

	public void collectionCleared(CollectionChangeEvent event) {
		// do nothing
	}

	public void collectionChanged(CollectionChangeEvent event) {
		// do nothing
	}

}
