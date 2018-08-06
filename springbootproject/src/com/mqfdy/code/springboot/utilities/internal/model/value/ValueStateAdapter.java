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
import com.mqfdy.code.springboot.utilities.model.value.WritablePropertyValueModel;

/**
 * Extend ValueAspectAdapter to listen to the
 * "state" of the value in the wrapped value model.
 */
public class ValueStateAdapter<T extends Model>
	extends ValueAspectAdapter<T>
{
	/** Listener that listens to value. */
	protected final StateChangeListener valueStateListener;


	// ********** constructors **********

	/**
	 * Construct an adapter for the value state.
	 */
	public ValueStateAdapter(WritablePropertyValueModel<T> valueHolder) {
		super(valueHolder);
		this.valueStateListener = this.buildValueStateListener();
	}


	// ********** initialization **********

	protected StateChangeListener buildValueStateListener() {
		return new StateChangeListener() {
			public void stateChanged(StateChangeEvent event) {
				ValueStateAdapter.this.valueAspectChanged();
			}
			@Override
			public String toString() {
				return "value state listener";
			}
		};
	}
	

	// ********** behavior **********

	@Override
	protected void engageValue_() {
		this.value.addStateChangeListener(this.valueStateListener);
	}

	@Override
	protected void disengageValue_() {
		this.value.removeStateChangeListener(this.valueStateListener);
	}

}