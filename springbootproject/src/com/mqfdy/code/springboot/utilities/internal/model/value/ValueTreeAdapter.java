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

import java.util.Arrays;

import com.mqfdy.code.springboot.utilities.model.Model;
import com.mqfdy.code.springboot.utilities.model.event.TreeChangeEvent;
import com.mqfdy.code.springboot.utilities.model.listener.TreeChangeListener;
import com.mqfdy.code.springboot.utilities.model.value.WritablePropertyValueModel;


// TODO: Auto-generated Javadoc
/**
 * Extend ValueAspectAdapter to listen to one or more tree aspects of the value
 * in the wrapped value model.
 *
 * @author mqfdy
 * @param <T>
 *            the generic type
 */
public class ValueTreeAdapter<T extends Model>
	extends ValueAspectAdapter<T>
{
	/** The names of the value's trees that we listen to. */
	protected final String[] treeNames;

	/** Listener that listens to the value. */
	protected final TreeChangeListener valueTreeListener;


	// ********** constructors **********

	/**
	 * Construct an adapter for the specified value trees.
	 *
	 * @param valueHolder
	 *            the value holder
	 * @param treeNames
	 *            the tree names
	 */
	public ValueTreeAdapter(WritablePropertyValueModel<T> valueHolder, String... treeNames) {
		super(valueHolder);
		this.treeNames = treeNames;
		this.valueTreeListener = this.buildValueTreeListener();
	}


	// ********** initialization **********

	/**
	 * Builds the value tree listener.
	 *
	 * @author mqfdy
	 * @return the tree change listener
	 * @Date 2018-09-03 09:00
	 */
	protected TreeChangeListener buildValueTreeListener() {
		return new TreeChangeListener() {
			public void nodeAdded(TreeChangeEvent event) {
				ValueTreeAdapter.this.valueAspectChanged();
			}
			public void nodeRemoved(TreeChangeEvent event) {
				ValueTreeAdapter.this.valueAspectChanged();
			}
			public void treeCleared(TreeChangeEvent event) {
				ValueTreeAdapter.this.valueAspectChanged();
			}
			public void treeChanged(TreeChangeEvent event) {
				ValueTreeAdapter.this.valueAspectChanged();
			}
			@Override
			public String toString() {
				return "value tree listener: " + Arrays.asList(ValueTreeAdapter.this.treeNames);
			}
		};
	}


	// ********** behavior **********

	@Override
	protected void engageValue_() {
		for (String treeName : this.treeNames) {
			this.value.addTreeChangeListener(treeName, this.valueTreeListener);
		}
	}

	@Override
	protected void disengageValue_() {
		for (String treeName : this.treeNames) {
			this.value.removeTreeChangeListener(treeName, this.valueTreeListener);
		}
	}

}
