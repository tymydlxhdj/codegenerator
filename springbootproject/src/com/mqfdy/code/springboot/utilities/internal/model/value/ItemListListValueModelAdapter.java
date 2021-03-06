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

import java.util.Arrays;

import com.mqfdy.code.springboot.utilities.model.Model;
import com.mqfdy.code.springboot.utilities.model.event.ListChangeEvent;
import com.mqfdy.code.springboot.utilities.model.listener.ListChangeListener;
import com.mqfdy.code.springboot.utilities.model.value.CollectionValueModel;
import com.mqfdy.code.springboot.utilities.model.value.ListValueModel;


// TODO: Auto-generated Javadoc
/**
 * Extend ItemAspectListValueModelAdapter to listen to one or more list aspects
 * of each item in the wrapped list model.
 *
 * @author mqfdy
 * @param <E>
 *            the element type
 */
public class ItemListListValueModelAdapter<E>
	extends ItemAspectListValueModelAdapter<E>
{

	/** The names of the subject's lists that we listen to. */
	protected final String[] listNames;

	/** Listener that listens to all the items in the list. */
	protected final ListChangeListener itemListListener;


	// ********** constructors **********

	/**
	 * Construct an adapter for the specified item List aspects.
	 *
	 * @param listHolder
	 *            the list holder
	 * @param listNames
	 *            the list names
	 */
	public ItemListListValueModelAdapter(ListValueModel<E> listHolder, String... listNames) {
		super(listHolder);
		this.listNames = listNames;
		this.itemListListener = this.buildItemListListener();
	}

	/**
	 * Construct an adapter for the specified item List aspects.
	 *
	 * @param collectionHolder
	 *            the collection holder
	 * @param listNames
	 *            the list names
	 */
	public ItemListListValueModelAdapter(CollectionValueModel<E> collectionHolder, String... listNames) {
		this(new CollectionListValueModelAdapter<E>(collectionHolder), listNames);
	}


	// ********** initialization **********

	/**
	 * All we really care about is the fact that the List aspect has changed. Do
	 * the same thing no matter which event occurs.
	 *
	 * @author mqfdy
	 * @return the list change listener
	 * @Date 2018-09-03 09:00
	 */
	protected ListChangeListener buildItemListListener() {
		return new ListChangeListener() {
			public void itemsAdded(ListChangeEvent event) {
				ItemListListValueModelAdapter.this.itemAspectChanged(event);
			}
			public void itemsRemoved(ListChangeEvent event) {
				ItemListListValueModelAdapter.this.itemAspectChanged(event);
			}
			public void itemsReplaced(ListChangeEvent event) {
				ItemListListValueModelAdapter.this.itemAspectChanged(event);
			}
			public void itemsMoved(ListChangeEvent event) {
				ItemListListValueModelAdapter.this.itemAspectChanged(event);
			}
			public void listCleared(ListChangeEvent event) {
				ItemListListValueModelAdapter.this.itemAspectChanged(event);
			}
			public void listChanged(ListChangeEvent event) {
				ItemListListValueModelAdapter.this.itemAspectChanged(event);
			}
			@Override
			public String toString() {
				return "item list listener: " + Arrays.asList(ItemListListValueModelAdapter.this.listNames);
			}
		};
	}
	

	// ********** behavior **********

	@Override
	protected void startListeningToItem(Model item) {
		for (String listName : this.listNames) {
			item.addListChangeListener(listName, this.itemListListener);
		}
	}

	@Override
	protected void stopListeningToItem(Model item) {
		for (String listName : this.listNames) {
			item.removeListChangeListener(listName, this.itemListListener);
		}
	}

}
