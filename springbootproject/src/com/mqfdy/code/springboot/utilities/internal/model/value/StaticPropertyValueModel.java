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

import com.mqfdy.code.springboot.utilities.internal.StringTools;
import com.mqfdy.code.springboot.utilities.internal.model.AbstractModel;
import com.mqfdy.code.springboot.utilities.model.value.PropertyValueModel;

/**
 * Implementation of PropertyValueModel that can be used for
 * returning a static value, but still allows listeners to be added.
 * Listeners will NEVER be notified of any changes, because there should be none.
 */
public class StaticPropertyValueModel<T>
	extends AbstractModel
	implements PropertyValueModel<T>
{
	/** The value. */
	protected final T value;

	private static final long serialVersionUID = 1L;


	/**
	 * Construct a static PropertyValueModel for the specified value.
	 */
	public StaticPropertyValueModel(T value) {
		super();
		this.value = value;
	}


	// ********** PropertyValueModel implementation **********

	public T getValue() {
		return this.value;
	}


	// ********** Object overrides **********

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, this.value);
	}

}
