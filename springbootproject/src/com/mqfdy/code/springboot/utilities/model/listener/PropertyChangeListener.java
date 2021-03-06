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

import com.mqfdy.code.springboot.utilities.model.event.PropertyChangeEvent;

/**
 * A "property change" event gets fired whenever a model changes a "bound"
 * property. You can register a PropertyChangeListener with a source
 * model so as to be notified of any bound property updates.
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface PropertyChangeListener extends ChangeListener {

	/**
	 * This method gets called when a model has changed a bound property.
	 * 
	 * @param event A StateChangeEvent describing the event source
	 * and the property's old and new values.
	 */
	void propertyChanged(PropertyChangeEvent event);

}
