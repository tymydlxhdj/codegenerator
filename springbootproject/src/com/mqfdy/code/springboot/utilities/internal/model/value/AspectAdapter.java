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

import com.mqfdy.code.springboot.utilities.internal.model.AbstractModel;
import com.mqfdy.code.springboot.utilities.internal.model.ChangeSupport;
import com.mqfdy.code.springboot.utilities.internal.model.SingleAspectChangeSupport;
import com.mqfdy.code.springboot.utilities.model.event.PropertyChangeEvent;
import com.mqfdy.code.springboot.utilities.model.listener.ChangeListener;
import com.mqfdy.code.springboot.utilities.model.listener.PropertyChangeListener;
import com.mqfdy.code.springboot.utilities.model.value.PropertyValueModel;

// TODO: Auto-generated Javadoc
/**
 * This abstract extension of AbstractModel provides a base for adding change
 * listeners (PropertyChange, CollectionChange, ListChange, TreeChange) to a
 * subject and converting the subject's change notifications into a single set
 * of change notifications for a common aspect (e.g. VALUE).
 * 
 * The adapter will only listen to the subject (and subject holder) when the
 * adapter itself actually has listeners. This will allow the adapter to be
 * garbage collected when appropriate
 *
 * @author mqfdy
 * @param <S>
 *            the generic type
 */
public abstract class AspectAdapter<S>
	extends AbstractModel
{
	/**
	 * The subject that holds the aspect and fires
	 * change notification when the aspect changes.
	 * We need to hold on to this directly so we can
	 * disengage it when it changes.
	 */
	protected S subject;

	/**
	 * A value model that holds the subject
	 * that holds the aspect and provides change notification.
	 * This is useful when there are a number of AspectAdapters
	 * that have the same subject and that subject can change.
	 * All the AspectAdapters should share the same subject holder.
	 * For now, this is can only be set upon construction and is
	 * immutable.
	 */
	protected final PropertyValueModel<? extends S> subjectHolder;

	/** A listener that keeps us in synch with the subjectHolder. */
	protected final PropertyChangeListener subjectChangeListener;


	// ********** constructors **********

	/**
	 * Construct an AspectAdapter for the specified subject.
	 *
	 * @param subject
	 *            the subject
	 */
	protected AspectAdapter(S subject) {
		this(new StaticPropertyValueModel<S>(subject));
	}

	/**
	 * Construct an AspectAdapter for the specified subject holder. The subject
	 * holder cannot be null.
	 *
	 * @param subjectHolder
	 *            the subject holder
	 */
	protected AspectAdapter(PropertyValueModel<? extends S> subjectHolder) {
		super();
		if (subjectHolder == null) {
			throw new NullPointerException();
		}
		this.subjectHolder = subjectHolder;
		this.subjectChangeListener = this.buildSubjectChangeListener();
		// the subject is null when we are not listening to it
		// this will typically result in our value being null
		this.subject = null;
	}


	// ********** initialization **********

	@Override
	protected ChangeSupport buildChangeSupport() {
		return new LocalChangeSupport(this, this.getListenerClass(), this.getListenerAspectName());
	}

	/**
	 * The subject holder's value has changed, keep our subject in synch.
	 *
	 * @author mqfdy
	 * @return the property change listener
	 * @Date 2018-09-03 09:00
	 */
	protected PropertyChangeListener buildSubjectChangeListener() {
		return new PropertyChangeListener() {
			public void propertyChanged(PropertyChangeEvent event) {
				AspectAdapter.this.subjectChanged();
			}
			@Override
			public String toString() {
				return "subject change listener";
			}
		};
	}


	// ********** behavior **********

	/**
	 * The subject has changed. Notify listeners that the value has changed.
	 */
	protected synchronized void subjectChanged() {
		Object oldValue = this.getValue();
		boolean hasListeners = this.hasListeners();
		if (hasListeners) {
			this.disengageSubject();
		}
		this.subject = this.subjectHolder.getValue();
		if (hasListeners) {
			this.engageSubject();
			this.fireAspectChange(oldValue, this.getValue());
		}
	}

	/**
	 * Return the aspect's current value.
	 *
	 * @author mqfdy
	 * @return the value
	 * @Date 2018-09-03 09:00
	 */
	protected abstract Object getValue();

	/**
	 * Return the class of listener that is interested in the aspect adapter's
	 * changes.
	 *
	 * @author mqfdy
	 * @return the listener class
	 * @Date 2018-09-03 09:00
	 */
	protected abstract Class<? extends ChangeListener> getListenerClass();

	/**
	 * Return the name of the aspect adapter's aspect (e.g. VALUE). This is the
	 * name of the aspect adapter's single aspect, not the name of the subject's
	 * aspect the aspect adapter is adapting.
	 *
	 * @author mqfdy
	 * @return the listener aspect name
	 * @Date 2018-09-03 09:00
	 */
	protected abstract String getListenerAspectName();

	/**
	 * Return whether there are any listeners for the aspect.
	 *
	 * @author mqfdy
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	protected abstract boolean hasListeners();

	/**
	 * Return whether there are no listeners for the aspect.
	 *
	 * @author mqfdy
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	protected boolean hasNoListeners() {
		return ! this.hasListeners();
	}

	/**
	 * The aspect has changed, notify listeners appropriately.
	 *
	 * @author mqfdy
	 * @param oldValue
	 *            the old value
	 * @param newValue
	 *            the new value
	 * @Date 2018-09-03 09:00
	 */
	protected abstract void fireAspectChange(Object oldValue, Object newValue);

	/**
	 * Engage subject.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	protected void engageSubject() {
		// check for nothing to listen to
		if (this.subject != null) {
			this.engageSubject_();
		}
	}

	/**
	 * The subject is not null - add our listener.
	 */
	protected abstract void engageSubject_();

	/**
	 * Disengage subject.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	protected void disengageSubject() {
		// check for nothing to listen to
		if (this.subject != null) {
			this.disengageSubject_();
		}
	}

	/**
	 * The subject is not null - remove our listener.
	 */
	protected abstract void disengageSubject_();

	/**
	 * Engage subject holder.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	protected void engageSubjectHolder() {
		this.subjectHolder.addPropertyChangeListener(PropertyValueModel.VALUE, this.subjectChangeListener);
		// synch our subject *after* we start listening to the subject holder,
		// since its value might change when a listener is added
		this.subject = this.subjectHolder.getValue();
	}

	/**
	 * Disengage subject holder.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	protected void disengageSubjectHolder() {
		this.subjectHolder.removePropertyChangeListener(PropertyValueModel.VALUE, this.subjectChangeListener);
		// clear out the subject when we are not listening to its holder
		this.subject = null;
	}

	/**
	 * Engage models.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	protected void engageModels() {
		this.engageSubjectHolder();
		this.engageSubject();
	}

	/**
	 * Disengage models.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	protected void disengageModels() {
		this.disengageSubject();
		this.disengageSubjectHolder();
	}


	// ********** local change support **********

	/**
	 * Extend change support to start listening to the aspect adapter's
	 * models (the subject holder and the subject itself) when the first
	 * relevant listener is added.
	 * Conversely, stop listening to the aspect adapter's models when the
	 * last relevant listener is removed.
	 * A relevant listener is a listener of the relevant type.
	 */
	protected class LocalChangeSupport extends SingleAspectChangeSupport {
		private static final long serialVersionUID = 1L;

		/**
		 * Instantiates a new local change support.
		 *
		 * @param source
		 *            the source
		 * @param listenerClass
		 *            the listener class
		 * @param aspectName
		 *            the aspect name
		 */
		public LocalChangeSupport(AspectAdapter<S> source, Class<? extends ChangeListener> listenerClass, String aspectName) {
			super(source, listenerClass, aspectName);
		}

		/**
		 * Listener is relevant.
		 *
		 * @author mqfdy
		 * @param lClass
		 *            the l class
		 * @return true, if successful
		 * @Date 2018-09-03 09:00
		 */
		protected boolean listenerIsRelevant(Class<? extends ChangeListener> lClass) {
			return lClass == this.listenerClass;
		}

		/**
		 * Checks for no relevant listeners.
		 *
		 * @author mqfdy
		 * @param lClass
		 *            the l class
		 * @return true, if successful
		 * @Date 2018-09-03 09:00
		 */
		protected boolean hasNoRelevantListeners(Class<? extends ChangeListener> lClass) {
			return this.listenerIsRelevant(lClass)
						&& this.hasNoListeners(lClass);
		}

		/**
		 * Listener is relevant.
		 *
		 * @author mqfdy
		 * @param lClass
		 *            the l class
		 * @param listenerAspectName
		 *            the listener aspect name
		 * @return true, if successful
		 * @Date 2018-09-03 09:00
		 */
		protected boolean listenerIsRelevant(Class<? extends ChangeListener> lClass, String listenerAspectName) {
			return this.listenerIsRelevant(lClass)
						&& (listenerAspectName == AspectAdapter.this.getListenerAspectName());
		}

		/**
		 * Checks for no relevant listeners.
		 *
		 * @author mqfdy
		 * @param lClass
		 *            the l class
		 * @param listenerAspectName
		 *            the listener aspect name
		 * @return true, if successful
		 * @Date 2018-09-03 09:00
		 */
		protected boolean hasNoRelevantListeners(Class<? extends ChangeListener> lClass, String listenerAspectName) {
			return this.listenerIsRelevant(lClass, listenerAspectName)
						&& this.hasNoListeners(lClass, listenerAspectName);
		}


		// ********** overrides **********

		@Override
		protected <T extends ChangeListener> void addListener(Class<T> lClass, T listener) {
			if (this.hasNoRelevantListeners(lClass)) {
				AspectAdapter.this.engageModels();
			}
			super.addListener(lClass, listener);
		}

		@Override
		protected <T extends ChangeListener> void addListener(String listenerAspectName, Class<T> lClass, T listener) {
			if (this.hasNoRelevantListeners(lClass, listenerAspectName)) {
				AspectAdapter.this.engageModels();
			}
			super.addListener(listenerAspectName, lClass, listener);
		}

		@Override
		protected <T extends ChangeListener> void removeListener(Class<T> lClass, T listener) {
			super.removeListener(lClass, listener);
			if (this.hasNoRelevantListeners(lClass)) {
				AspectAdapter.this.disengageModels();
			}
		}

		@Override
		protected <T extends ChangeListener> void removeListener(String listenerAspectName, Class<T> lClass, T listener) {
			super.removeListener(listenerAspectName, lClass, listener);
			if (this.hasNoRelevantListeners(lClass, listenerAspectName)) {
				AspectAdapter.this.disengageModels();
			}
		}

	}

}
