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

import com.mqfdy.code.springboot.utilities.model.event.CollectionChangeEvent;
import com.mqfdy.code.springboot.utilities.model.listener.CollectionChangeListener;
import com.mqfdy.code.springboot.utilities.model.value.CollectionValueModel;

// TODO: Auto-generated Javadoc
/**
 * This abstract class provides the infrastructure needed to wrap a collection
 * value model, "lazily" listen to it, and convert its change notifications into
 * property value model change notifications.
 * 
 * Subclasses must override: - #buildValue() to return the current property
 * value, as derived from the current collection value
 * 
 * Subclasses might want to override: - #itemsAdded(CollectionChangeEvent event)
 * - #itemsRemoved(CollectionChangeEvent event) -
 * #collectionCleared(CollectionChangeEvent event) -
 * #collectionChanged(CollectionChangeEvent event) to improve performance (by
 * not recalculating the value, if possible)
 *
 * @author mqfdy
 * @param <T>
 *            the generic type
 */
public abstract class CollectionPropertyValueModelAdapter<T>
	extends AspectPropertyValueModelAdapter<T>
{
	/** The wrapped collection value model. */
	protected final CollectionValueModel<?> collectionHolder;

	/** A listener that allows us to synch with changes to the wrapped collection holder. */
	protected final CollectionChangeListener collectionChangeListener;


	// ********** constructor/initialization **********

	/**
	 * Construct a property value model with the specified wrapped collection
	 * value model.
	 *
	 * @param collectionHolder
	 *            the collection holder
	 */
	protected CollectionPropertyValueModelAdapter(CollectionValueModel<?> collectionHolder) {
		super();
		this.collectionHolder = collectionHolder;
		this.collectionChangeListener = this.buildCollectionChangeListener();
	}

	/**
	 * Builds the collection change listener.
	 *
	 * @author mqfdy
	 * @return the collection change listener
	 * @Date 2018-09-03 09:00
	 */
	protected CollectionChangeListener buildCollectionChangeListener() {
		return new CollectionChangeListener() {
			public void itemsAdded(CollectionChangeEvent event) {
				CollectionPropertyValueModelAdapter.this.itemsAdded(event);
			}		
			public void itemsRemoved(CollectionChangeEvent event) {
				CollectionPropertyValueModelAdapter.this.itemsRemoved(event);
			}
			public void collectionCleared(CollectionChangeEvent event) {
				CollectionPropertyValueModelAdapter.this.collectionCleared(event);
			}
			public void collectionChanged(CollectionChangeEvent event) {
				CollectionPropertyValueModelAdapter.this.collectionChanged(event);
			}
			@Override
			public String toString() {
				return "collection change listener";
			}
		};
	}


	// ********** behavior **********

	/**
	 * Start listening to the collection holder.
	 */
	@Override
	protected void engageModel_() {
		this.collectionHolder.addCollectionChangeListener(CollectionValueModel.VALUES, this.collectionChangeListener);
	}

	/**
	 * Stop listening to the collection holder.
	 */
	@Override
	protected void disengageModel_() {
		this.collectionHolder.removeCollectionChangeListener(CollectionValueModel.VALUES, this.collectionChangeListener);
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.collectionHolder);
	}

	
	// ********** collection change support **********

	/**
	 * Items were added to the wrapped collection holder; propagate the change
	 * notification appropriately.
	 *
	 * @author mqfdy
	 * @param event
	 *            the event
	 * @Date 2018-09-03 09:00
	 */
	protected void itemsAdded(CollectionChangeEvent event) {
		// by default, simply recalculate the value and fire an event
		this.propertyChanged();
	}

	/**
	 * Items were removed from the wrapped collection holder; propagate the
	 * change notification appropriately.
	 *
	 * @author mqfdy
	 * @param event
	 *            the event
	 * @Date 2018-09-03 09:00
	 */
	protected void itemsRemoved(CollectionChangeEvent event) {
		// by default, simply recalculate the value and fire an event
		this.propertyChanged();
	}

	/**
	 * The wrapped collection holder was cleared; propagate the change
	 * notification appropriately.
	 *
	 * @author mqfdy
	 * @param event
	 *            the event
	 * @Date 2018-09-03 09:00
	 */
	protected void collectionCleared(CollectionChangeEvent event) {
		// by default, simply recalculate the value and fire an event
		this.propertyChanged();
	}

	/**
	 * The value of the wrapped collection holder has changed; propagate the
	 * change notification appropriately.
	 *
	 * @author mqfdy
	 * @param event
	 *            the event
	 * @Date 2018-09-03 09:00
	 */
	protected void collectionChanged(CollectionChangeEvent event) {
		// by default, simply recalculate the value and fire an event
		this.propertyChanged();
	}

}
