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

import com.mqfdy.code.springboot.utilities.internal.ClassTools;
import com.mqfdy.code.springboot.utilities.internal.model.AbstractModel;
import com.mqfdy.code.springboot.utilities.model.value.PropertyValueModel;

/**
 * A property value model for when you don't need to support a value.
 * 
 * We don't use a singleton because we hold on to listeners.
 *
 * @author mqfdy
 * @param <T>
 *            the generic type
 */
public class NullPropertyValueModel<T>
	extends AbstractModel
	implements PropertyValueModel<T>
{
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public NullPropertyValueModel() {
		super();
	}
	

	// ********** PropertyValueModel implementation **********

	public T getValue() {
		return null;
	}


	// ********** Object overrides **********

    @Override
	public String toString() {
		return ClassTools.shortClassNameForObject(this);
	}

}
