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
import com.mqfdy.code.springboot.utilities.model.value.WritablePropertyValueModel;


// TODO: Auto-generated Javadoc
/**
 * Extend ValueAspectAdapter to listen to one or more collection aspects of the
 * value in the wrapped value model.
 *
 * @author mqfdy
 * @param <T>
 *            the generic type
 */
public class ValueCollectionAdapter<T extends Model>
	extends ValueAspectAdapter<T>
{

	/** The names of the value's collections that we listen to. */
	protected final String[] collectionNames;

	/** Listener that listens to the value. */
	protected final CollectionChangeListener valueCollectionListener;


	// ********** constructors **********

	/**
	 * Construct an adapter for the specified value collections.
	 *
	 * @param valueHolder
	 *            the value holder
	 * @param collectionNames
	 *            the collection names
	 */
	public ValueCollectionAdapter(WritablePropertyValueModel<T> valueHolder, String... collectionNames) {
		super(valueHolder);
		this.collectionNames = collectionNames;
		this.valueCollectionListener = this.buildValueCollectionListener();
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
	protected CollectionChangeListener buildValueCollectionListener() {
		return new CollectionChangeListener() {
			public void itemsAdded(CollectionChangeEvent event) {
				ValueCollectionAdapter.this.valueAspectChanged();
			}
			public void itemsRemoved(CollectionChangeEvent event) {
				ValueCollectionAdapter.this.valueAspectChanged();
			}
			public void collectionCleared(CollectionChangeEvent event) {
				ValueCollectionAdapter.this.valueAspectChanged();
			}
			public void collectionChanged(CollectionChangeEvent event) {
				ValueCollectionAdapter.this.valueAspectChanged();
			}
			@Override
			public String toString() {
				return "value collection listener: " + Arrays.asList(ValueCollectionAdapter.this.collectionNames);
			}
		};
	}

	@Override
	protected void engageValue_() {
		for (String collectionName : this.collectionNames) {
			this.value.addCollectionChangeListener(collectionName, this.valueCollectionListener);
		}
	}

	@Override
	protected void disengageValue_() {
		for (String collectionName : this.collectionNames) {
			this.value.removeCollectionChangeListener(collectionName, this.valueCollectionListener);
		}
	}

}
