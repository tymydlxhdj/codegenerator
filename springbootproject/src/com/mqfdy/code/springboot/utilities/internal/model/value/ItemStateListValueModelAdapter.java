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

import com.mqfdy.code.springboot.utilities.model.Model;
import com.mqfdy.code.springboot.utilities.model.event.StateChangeEvent;
import com.mqfdy.code.springboot.utilities.model.listener.StateChangeListener;
import com.mqfdy.code.springboot.utilities.model.value.CollectionValueModel;
import com.mqfdy.code.springboot.utilities.model.value.ListValueModel;

// TODO: Auto-generated Javadoc
/**
 * Extend ItemAspectListValueModelAdapter to listen to the "state" of each item
 * in the wrapped list model.
 *
 * @author mqfdy
 * @param <E>
 *            the element type
 */
public class ItemStateListValueModelAdapter<E>
	extends ItemAspectListValueModelAdapter<E>
{
	/** Listener that listens to all the items in the list. */
	protected final StateChangeListener itemStateListener;


	// ********** constructors **********

	/**
	 * Construct an adapter for the item state.
	 *
	 * @param listHolder
	 *            the list holder
	 */
	public ItemStateListValueModelAdapter(ListValueModel<E> listHolder) {
		super(listHolder);
		this.itemStateListener = this.buildItemStateListener();
	}

	/**
	 * Construct an adapter for the item state.
	 *
	 * @param collectionHolder
	 *            the collection holder
	 */
	public ItemStateListValueModelAdapter(CollectionValueModel<E> collectionHolder) {
		this(new CollectionListValueModelAdapter<E>(collectionHolder));
	}


	// ********** initialization **********

	/**
	 * Builds the item state listener.
	 *
	 * @author mqfdy
	 * @return the state change listener
	 * @Date 2018-09-03 09:00
	 */
	protected StateChangeListener buildItemStateListener() {
		return new StateChangeListener() {
			public void stateChanged(StateChangeEvent event) {
				ItemStateListValueModelAdapter.this.itemAspectChanged(event);
			}
			@Override
			public String toString() {
				return "item state listener";
			}
		};
	}
	

	// ********** behavior **********

	@Override
	protected void startListeningToItem(Model item) {
		item.addStateChangeListener(this.itemStateListener);
	}

	@Override
	protected void stopListeningToItem(Model item) {
		item.removeStateChangeListener(this.itemStateListener);
	}

}
