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
import com.mqfdy.code.springboot.utilities.model.value.WritablePropertyValueModel;


// TODO: Auto-generated Javadoc
/**
 * Extend ValueAspectAdapter to listen to one or more list aspects of the value
 * in the wrapped value model.
 *
 * @author mqfdy
 * @param <T>
 *            the generic type
 */
public class ValueListAdapter<T extends Model>
	extends ValueAspectAdapter<T>
{

	/** The names of the value's lists that we listen to. */
	protected final String[] listNames;

	/** Listener that listens to the value. */
	protected final ListChangeListener valueListListener;


	// ********** constructors **********

	/**
	 * Construct an adapter for the specified value lists.
	 *
	 * @param valueHolder
	 *            the value holder
	 * @param listNames
	 *            the list names
	 */
	public ValueListAdapter(WritablePropertyValueModel<T> valueHolder, String... listNames) {
		super(valueHolder);
		this.listNames = listNames;
		this.valueListListener = this.buildValueListListener();
	}


	// ********** initialization **********

	/**
	 * All we really care about is the fact that a List aspect has changed. Do
	 * the same thing no matter which event occurs.
	 *
	 * @author mqfdy
	 * @return the list change listener
	 * @Date 2018-09-03 09:00
	 */
	protected ListChangeListener buildValueListListener() {
		return new ListChangeListener() {
			public void itemsAdded(ListChangeEvent event) {
				ValueListAdapter.this.valueAspectChanged();
			}
			public void itemsRemoved(ListChangeEvent event) {
				ValueListAdapter.this.valueAspectChanged();
			}
			public void itemsReplaced(ListChangeEvent event) {
				ValueListAdapter.this.valueAspectChanged();
			}
			public void itemsMoved(ListChangeEvent event) {
				ValueListAdapter.this.valueAspectChanged();
			}
			public void listCleared(ListChangeEvent event) {
				ValueListAdapter.this.valueAspectChanged();
			}
			public void listChanged(ListChangeEvent event) {
				ValueListAdapter.this.valueAspectChanged();
			}
			@Override
			public String toString() {
				return "value list listener: " + Arrays.asList(ValueListAdapter.this.listNames);
			}
		};
	}

	@Override
	protected void engageValue_() {
		for (String listName : this.listNames) {
			this.value.addListChangeListener(listName, this.valueListListener);
		}
	}

	@Override
	protected void disengageValue_() {
		for (String listName : this.listNames) {
			this.value.removeListChangeListener(listName, this.valueListListener);
		}
	}

}
