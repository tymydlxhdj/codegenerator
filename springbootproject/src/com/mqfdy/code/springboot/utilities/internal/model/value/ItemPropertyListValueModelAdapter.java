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
import com.mqfdy.code.springboot.utilities.model.event.PropertyChangeEvent;
import com.mqfdy.code.springboot.utilities.model.listener.PropertyChangeListener;
import com.mqfdy.code.springboot.utilities.model.value.CollectionValueModel;
import com.mqfdy.code.springboot.utilities.model.value.ListValueModel;


// TODO: Auto-generated Javadoc
/**
 * Extend ItemAspectListValueModelAdapter to listen to one or more properties of
 * each item in the wrapped list model.
 *
 * @author mqfdy
 * @param <E>
 *            the element type
 */
public class ItemPropertyListValueModelAdapter<E>
	extends ItemAspectListValueModelAdapter<E>
{

	/** The names of the items' properties that we listen to. */
	protected final String[] propertyNames;

	/** Listener that listens to all the items in the list. */
	protected final PropertyChangeListener itemPropertyListener;


	// ********** constructors **********

	/**
	 * Construct an adapter for the specified item properties.
	 *
	 * @param listHolder
	 *            the list holder
	 * @param propertyNames
	 *            the property names
	 */
	public ItemPropertyListValueModelAdapter(ListValueModel<E> listHolder, String... propertyNames) {
		super(listHolder);
		this.propertyNames = propertyNames;
		this.itemPropertyListener = this.buildItemPropertyListener();
	}

	/**
	 * Construct an adapter for the specified item properties.
	 *
	 * @param collectionHolder
	 *            the collection holder
	 * @param propertyNames
	 *            the property names
	 */
	public ItemPropertyListValueModelAdapter(CollectionValueModel<E> collectionHolder, String... propertyNames) {
		this(new CollectionListValueModelAdapter<E>(collectionHolder), propertyNames);
	}


	// ********** initialization **********

	/**
	 * Builds the item property listener.
	 *
	 * @author mqfdy
	 * @return the property change listener
	 * @Date 2018-09-03 09:00
	 */
	protected PropertyChangeListener buildItemPropertyListener() {
		return new PropertyChangeListener() {
			public void propertyChanged(PropertyChangeEvent event) {
				ItemPropertyListValueModelAdapter.this.itemAspectChanged(event);
			}
			@Override
			public String toString() {
				return "item property listener: " + Arrays.asList(ItemPropertyListValueModelAdapter.this.propertyNames);
			}
		};
	}
	

	// ********** behavior **********

	@Override
	protected void startListeningToItem(Model item) {
		for (String propertyName : this.propertyNames) {
			item.addPropertyChangeListener(propertyName, this.itemPropertyListener);
		}
	}

	@Override
	protected void stopListeningToItem(Model item) {
		for (String propertyName : this.propertyNames) {
			item.removePropertyChangeListener(propertyName, this.itemPropertyListener);
		}
	}

}
