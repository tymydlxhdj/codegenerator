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

import java.util.EventObject;

import com.mqfdy.code.springboot.utilities.internal.StringTools;
import com.mqfdy.code.springboot.utilities.model.Model;


// TODO: Auto-generated Javadoc
/**
 * Abstract class for all the change events that can be fired by models.
 * 
 * Provisional API: This class is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public abstract class ChangeEvent extends EventObject {

	private static final long serialVersionUID = 1L;


	/**
	 * Construct a new change event.
	 *
	 * @param source The object on which the event initially occurred.
	 */
	protected ChangeEvent(Model source) {
		super(source);
	}

	/**
	 * Covariant override.
	 */
	@Override
	public Model getSource() {
		return (Model) super.getSource();
	}

	/**
	 * Return the name of the aspect of the source that changed. May be null if
	 * inappropriate.
	 *
	 * @author mqfdy
	 * @return the aspect name
	 * @Date 2018-09-03 09:00
	 */
	public abstract String getAspectName();

	/**
	 * Return a copy of the event with the specified source replacing the
	 * current source.
	 *
	 * @author mqfdy
	 * @param newSource
	 *            the new source
	 * @return the change event
	 * @Date 2018-09-03 09:00
	 */
	public abstract ChangeEvent cloneWithSource(Model newSource);

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, this.getAspectName());
	}

}
