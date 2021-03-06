/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package com.mqfdy.code.springboot.utilities.model.event;

import com.mqfdy.code.springboot.utilities.model.Model;

// TODO: Auto-generated Javadoc
/**
 * A "property change" event gets delivered whenever a model changes a "bound"
 * or "constrained" property. A PropertyChangeEvent is sent as an
 * argument to the PropertyChangeListener.
 * 
 * Provisional API: This class is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public class PropertyChangeEvent extends ChangeEvent {

	/** Name of the property that changed. */
	private final String propertyName;

	/** The property's old value, before the change. */
	private final Object oldValue;

	/** The property's new value, after the change. */
	private final Object newValue;

	private static final long serialVersionUID = 1L;


	// ********** constructors **********

	/**
	 * Construct a new property change event.
	 *
	 * @param source The object on which the event initially occurred.
	 * @param propertyName The programmatic name of the property that was changed.
	 * @param oldValue The old value of the property.
	 * @param newValue The new value of the property.
	 */
	public PropertyChangeEvent(Model source, String propertyName, Object oldValue, Object newValue) {
		super(source);
		if (propertyName == null) {
			throw new NullPointerException();
		}
		this.propertyName = propertyName;
		this.oldValue = oldValue;
		this.newValue = newValue;
	}


	// ********** standard state **********

	/**
	 * Return the programmatic name of the property that was changed.
	 *
	 * @author mqfdy
	 * @return the property name
	 * @Date 2018-09-03 09:00
	 */
	public String getPropertyName() {
		return this.propertyName;
	}

	@Override
	public String getAspectName() {
		return this.propertyName;
	}

	/**
	 * Return the old value of the property.
	 *
	 * @author mqfdy
	 * @return the old value
	 * @Date 2018-09-03 09:00
	 */
	public Object getOldValue() {
		return this.oldValue;
	}

	/**
	 * Return the new value of the property.
	 *
	 * @author mqfdy
	 * @return the new value
	 * @Date 2018-09-03 09:00
	 */
	public Object getNewValue() {
		return this.newValue;
	}


	// ********** cloning **********

	@Override
	public PropertyChangeEvent cloneWithSource(Model newSource) {
		return new PropertyChangeEvent(newSource, this.propertyName, this.oldValue, this.newValue);
	}

	/**
	 * Return a copy of the event with the specified source replacing the
	 * current source and the property name.
	 *
	 * @author mqfdy
	 * @param newSource
	 *            the new source
	 * @param newPropertyName
	 *            the new property name
	 * @return the property change event
	 * @Date 2018-09-03 09:00
	 */
	public PropertyChangeEvent cloneWithSource(Model newSource, String newPropertyName) {
		return new PropertyChangeEvent(newSource, newPropertyName, this.oldValue, this.newValue);
	}

}
