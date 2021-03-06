/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package com.mqfdy.code.springboot.utilities.model.listener;

import java.lang.reflect.Method;

import com.mqfdy.code.springboot.utilities.internal.ClassTools;
import com.mqfdy.code.springboot.utilities.model.event.PropertyChangeEvent;
import com.mqfdy.code.springboot.utilities.model.event.StateChangeEvent;


/**
 * This class is used by ReflectiveChangeListener when the requested listener
 * need only implement a single method (i.e. StateChangeListener or
 * PropertyChangeListener).
 */
class SingleMethodReflectiveChangeListener
	extends ReflectiveChangeListener
	implements StateChangeListener, PropertyChangeListener
{

	/** the method we will invoke on the target object */
	private final Method method;
	/** cache the number of arguments */
	private final boolean methodIsZeroArgument;

	SingleMethodReflectiveChangeListener(Object target, Method method) {
		super(target);
		this.method = method;
		this.methodIsZeroArgument = method.getParameterTypes().length == 0;
	}


	// ********** StateChangeListener implementation **********

	public void stateChanged(StateChangeEvent event) {
		if (this.methodIsZeroArgument) {
			ClassTools.executeMethod(this.method, this.target, EMPTY_STATE_CHANGE_EVENT_ARRAY);
		} else {
			ClassTools.executeMethod(this.method, this.target, new StateChangeEvent[] {event});
		}
	}


	// ********** PropertyChangeListener implementation **********

	public void propertyChanged(PropertyChangeEvent event) {
		if (this.methodIsZeroArgument) {
			ClassTools.executeMethod(this.method, this.target, EMPTY_PROPERTY_CHANGE_EVENT_ARRAY);
		} else {
			ClassTools.executeMethod(this.method, this.target, new PropertyChangeEvent[] {event});
		}
	}

}
