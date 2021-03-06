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
import java.util.Collection;
import java.util.Iterator;

import com.mqfdy.code.springboot.utilities.internal.CollectionTools;
import com.mqfdy.code.springboot.utilities.internal.iterators.EmptyIterator;
import com.mqfdy.code.springboot.utilities.model.Model;
import com.mqfdy.code.springboot.utilities.model.event.CollectionChangeEvent;
import com.mqfdy.code.springboot.utilities.model.listener.ChangeListener;
import com.mqfdy.code.springboot.utilities.model.listener.CollectionChangeListener;
import com.mqfdy.code.springboot.utilities.model.value.CollectionValueModel;
import com.mqfdy.code.springboot.utilities.model.value.PropertyValueModel;


// TODO: Auto-generated Javadoc
/**
 * This extension of AspectAdapter provides CollectionChange support. This
 * allows us to convert a set of one or more collections into a single
 * collection, VALUES.
 * 
 * The typical subclass will override the following methods: #iterator_() at the
 * very minimum, override this method to return an iterator on the subject's
 * collection aspect; it does not need to be overridden if #iterator() is
 * overridden and its behavior changed #size_() override this method to improve
 * performance; it does not need to be overridden if #size() is overridden and
 * its behavior changed #iterator() override this method only if returning an
 * empty iterator when the subject is null is unacceptable #size() override this
 * method only if returning a zero when the subject is null is unacceptable
 *
 * @author mqfdy
 * @param <S>
 *            the generic type
 * @param <E>
 *            the element type
 */
public abstract class CollectionAspectAdapter<S extends Model, E>
	extends AspectAdapter<S>
	implements CollectionValueModel<E>
{
	/**
	 * The name of the subject's collections that we use for the value.
	 */
	protected final String[] collectionNames;
		
		/** The Constant EMPTY_COLLECTION_NAMES. */
		protected static final String[] EMPTY_COLLECTION_NAMES = new String[0];

	/** A listener that listens to the subject's collection aspect. */
	protected final CollectionChangeListener collectionChangeListener;


	// ********** constructors **********

	/**
	 * Construct a CollectionAspectAdapter for the specified subject and
	 * collection.
	 *
	 * @param collectionName
	 *            the collection name
	 * @param subject
	 *            the subject
	 */
	protected CollectionAspectAdapter(String collectionName, S subject) {
		this(new String[] {collectionName}, subject);
	}

	/**
	 * Construct a CollectionAspectAdapter for the specified subject and
	 * collections.
	 *
	 * @param collectionNames
	 *            the collection names
	 * @param subject
	 *            the subject
	 */
	protected CollectionAspectAdapter(String[] collectionNames, S subject) {
		this(new StaticPropertyValueModel<S>(subject), collectionNames);
	}

	/**
	 * Construct a CollectionAspectAdapter for the specified subject holder and
	 * collections.
	 *
	 * @param subjectHolder
	 *            the subject holder
	 * @param collectionNames
	 *            the collection names
	 */
	protected CollectionAspectAdapter(PropertyValueModel<? extends S> subjectHolder, String... collectionNames) {
		super(subjectHolder);
		this.collectionNames = collectionNames;
		this.collectionChangeListener = this.buildCollectionChangeListener();
	}

	/**
	 * Construct a CollectionAspectAdapter for the specified subject holder and
	 * collections.
	 *
	 * @param subjectHolder
	 *            the subject holder
	 * @param collectionNames
	 *            the collection names
	 */
	protected CollectionAspectAdapter(PropertyValueModel<? extends S> subjectHolder, Collection<String> collectionNames) {
		this(subjectHolder, collectionNames.toArray(new String[collectionNames.size()]));
	}

	/**
	 * Construct a CollectionAspectAdapter for an "unchanging" collection in the
	 * specified subject. This is useful for a collection aspect that does not
	 * change for a particular subject; but the subject will change, resulting
	 * in a new collection.
	 *
	 * @param subjectHolder
	 *            the subject holder
	 */
	protected CollectionAspectAdapter(PropertyValueModel<? extends S> subjectHolder) {
		this(subjectHolder, EMPTY_COLLECTION_NAMES);
	}


	// ********** initialization **********

	/**
	 * The subject's collection aspect has changed, notify the listeners.
	 *
	 * @author mqfdy
	 * @return the collection change listener
	 * @Date 2018-09-03 09:00
	 */
	protected CollectionChangeListener buildCollectionChangeListener() {
		// transform the subject's collection change events into VALUE collection change events
		return new CollectionChangeListener() {
			public void itemsAdded(CollectionChangeEvent event) {
				CollectionAspectAdapter.this.itemsAdded(event);
			}
			public void itemsRemoved(CollectionChangeEvent event) {
				CollectionAspectAdapter.this.itemsRemoved(event);
			}
			public void collectionCleared(CollectionChangeEvent event) {
				CollectionAspectAdapter.this.collectionCleared(event);
			}
			public void collectionChanged(CollectionChangeEvent event) {
				CollectionAspectAdapter.this.collectionChanged(event);
			}
			@Override
			public String toString() {
				return "collection change listener: " + Arrays.asList(CollectionAspectAdapter.this.collectionNames);
			}
		};
	}


	// ********** CollectionValueModel implementation **********

	/**
	 * Return the elements of the subject's collection aspect.
	 */
	public Iterator<E> iterator() {
		return (this.subject == null) ? EmptyIterator.<E>instance() : this.iterator_();
	}

	/**
	 * Return the elements of the subject's collection aspect. At this point we
	 * can be sure that the subject is not null.
	 *
	 * @return the iterator
	 * @see #iterator()
	 */
	protected Iterator<E> iterator_() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Return the size of the subject's collection aspect.
	 */
	public int size() {
		return (this.subject == null) ? 0 : this.size_();
	}

	/**
	 * Return the size of the subject's collection aspect. At this point we can
	 * be sure that the subject is not null.
	 *
	 * @return the int
	 * @see #size()
	 */
	protected int size_() {
		return CollectionTools.size(this.iterator());
	}


	// ********** AspectAdapter implementation **********

	@Override
	protected Object getValue() {
		return this.iterator();
	}

	@Override
	protected Class<? extends ChangeListener> getListenerClass() {
		return CollectionChangeListener.class;
	}

	@Override
	protected String getListenerAspectName() {
		return VALUES;
	}

	@Override
	protected boolean hasListeners() {
		return this.hasAnyCollectionChangeListeners(VALUES);
	}

	@Override
	protected void fireAspectChange(Object oldValue, Object newValue) {
		this.fireCollectionChanged(VALUES);
	}

	@Override
	protected void engageSubject_() {
    	for (String collectionName : this.collectionNames) {
			((Model) this.subject).addCollectionChangeListener(collectionName, this.collectionChangeListener);
		}
	}

	@Override
	protected void disengageSubject_() {
    	for (String collectionName : this.collectionNames) {
			((Model) this.subject).removeCollectionChangeListener(collectionName, this.collectionChangeListener);
		}
	}

	@Override
	public void toString(StringBuilder sb) {
		for (int i = 0; i < this.collectionNames.length; i++) {
			if (i != 0) {
				sb.append(", ");
			}
			sb.append(this.collectionNames[i]);
		}
	}


	// ********** behavior **********

	/**
	 * Items added.
	 *
	 * @author mqfdy
	 * @param event
	 *            the event
	 * @Date 2018-09-03 09:00
	 */
	protected void itemsAdded(CollectionChangeEvent event) {
		this.fireItemsAdded(event.cloneWithSource(this, VALUES));
	}

	/**
	 * Items removed.
	 *
	 * @author mqfdy
	 * @param event
	 *            the event
	 * @Date 2018-09-03 09:00
	 */
	protected void itemsRemoved(CollectionChangeEvent event) {
		this.fireItemsRemoved(event.cloneWithSource(this, VALUES));
	}

	/**
	 * Collection cleared.
	 *
	 * @author mqfdy
	 * @param event
	 *            the event
	 * @Date 2018-09-03 09:00
	 */
	protected void collectionCleared(CollectionChangeEvent event) {
		this.fireCollectionCleared(VALUES);  // nothing from original event to forward
	}

	/**
	 * Collection changed.
	 *
	 * @author mqfdy
	 * @param event
	 *            the event
	 * @Date 2018-09-03 09:00
	 */
	protected void collectionChanged(CollectionChangeEvent event) {
		this.fireCollectionChanged(VALUES);  // nothing from original event to forward
	}

}
