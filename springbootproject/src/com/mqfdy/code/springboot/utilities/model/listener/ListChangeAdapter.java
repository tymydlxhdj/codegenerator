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

import com.mqfdy.code.springboot.utilities.model.event.ListChangeEvent;

/**
 * Convenience implementation of ListChangeListener.
 * 
 * Provisional API: This class is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public class ListChangeAdapter implements ListChangeListener {

	/**
	 * Default constructor.
	 */
	public ListChangeAdapter() {
		super();
	}

	public void itemsAdded(ListChangeEvent event) {
		// do nothing
	}

	public void itemsRemoved(ListChangeEvent event) {
		// do nothing
	}

	public void itemsReplaced(ListChangeEvent event) {
		// do nothing
	}

	public void itemsMoved(ListChangeEvent event) {
		// do nothing
	}

	public void listCleared(ListChangeEvent event) {
		// do nothing
	}

	public void listChanged(ListChangeEvent event) {
		// do nothing
	}

}
