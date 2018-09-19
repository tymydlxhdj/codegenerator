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
import com.mqfdy.code.springboot.utilities.model.event.CollectionChangeEvent;
import com.mqfdy.code.springboot.utilities.model.listener.CollectionChangeListener;
import com.mqfdy.code.springboot.utilities.model.value.CollectionValueModel;
import com.mqfdy.code.springboot.utilities.model.value.ListValueModel;


// TODO: Auto-generated Javadoc
/**
 * Extend ItemAspectListValueModelAdapter to listen to one or more collection
 * aspects of each item in the wrapped list model.
 *
 * @author mqfdy
 * @param <E>
 *            the element type
 */
public class ItemCollectionListValueModelAdapter<E>
	extends ItemAspectListValueModelAdapter<E>
{

	/** The names of the items' collections that we listen to. */
	protected final String[] collectionNames;

	/** Listener that listens to all the items in the list. */
	protected final CollectionChangeListener itemCollectionListener;


	// ********** constructors **********

	/**
	 * Construct an adapter for the specified item Collections.
	 *
	 * @param listHolder
	 *            the list holder
	 * @param collectionNames
	 *            the collection names
	 */
	public ItemCollectionListValueModelAdapter(ListValueModel<E> listHolder, String... collectionNames) {
		super(listHolder);
		this.collectionNames = collectionNames;
		this.itemCollectionListener = this.buildItemCollectionListener();
	}

	/**
	 * Construct an adapter for the specified item Collections.
	 *
	 * @param collectionHolder
	 *            the collection holder
	 * @param collectionNames
	 *            the collection names
	 */
	public ItemCollectionListValueModelAdapter(CollectionValueModel<E> collectionHolder, String... collectionNames) {
		this(new CollectionListValueModelAdapter<E>(collectionHolder), collectionNames);
	}


	// ********** initialization **********

	/**
	 * All we really care about is the fact that a Collection aspect has
	 * changed. Do the same thing no matter which event occurs.
	 *
	 * @author mqfdy
	 * @return the collection change listener
	 * @Date 2018-09-03 09:00
	 */
	protected CollectionChangeListener buildItemCollectionListener() {
		return new CollectionChangeListener() {
			public void itemsAdded(CollectionChangeEvent event) {
				ItemCollectionListValueModelAdapter.this.itemAspectChanged(event);
			}
			public void itemsRemoved(CollectionChangeEvent event) {
				ItemCollectionListValueModelAdapter.this.itemAspectChanged(event);
			}
			public void collectionCleared(CollectionChangeEvent event) {
				ItemCollectionListValueModelAdapter.this.itemAspectChanged(event);
			}
			public void collectionChanged(CollectionChangeEvent event) {
				ItemCollectionListValueModelAdapter.this.itemAspectChanged(event);
			}
			@Override
			public String toString() {
				return "item collection listener: " + Arrays.asList(ItemCollectionListValueModelAdapter.this.collectionNames);
			}
		};
	}


	// ********** behavior **********

	@Override
	protected void startListeningToItem(Model item) {
		for (String collectionName : this.collectionNames) {
			item.addCollectionChangeListener(collectionName, this.itemCollectionListener);
		}
	}

	@Override
	protected void stopListeningToItem(Model item) {
		for (String collectionName : this.collectionNames) {
			item.removeCollectionChangeListener(collectionName, this.itemCollectionListener);
		}
	}

}
