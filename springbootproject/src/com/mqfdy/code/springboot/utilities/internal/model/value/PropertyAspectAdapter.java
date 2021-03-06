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

import com.mqfdy.code.springboot.utilities.model.Model;
import com.mqfdy.code.springboot.utilities.model.event.PropertyChangeEvent;
import com.mqfdy.code.springboot.utilities.model.listener.ChangeListener;
import com.mqfdy.code.springboot.utilities.model.listener.PropertyChangeListener;
import com.mqfdy.code.springboot.utilities.model.value.PropertyValueModel;
import com.mqfdy.code.springboot.utilities.model.value.WritablePropertyValueModel;


// TODO: Auto-generated Javadoc
/**
 * This AspectAdapter provides basic PropertyChange support. This allows us to
 * convert a set of one or more properties into a single property, VALUE.
 * 
 * The typical subclass will override the following methods: #buildValue_() at
 * the very minimum, override this method to return the value of the subject's
 * property (or "virtual" property); it does not need to be overridden if
 * #buildValue() is overridden and its behavior changed #setValue_(Object)
 * override this method if the client code needs to *set* the value of the
 * subject's property; oftentimes, though, the client code (e.g. UI) will need
 * only to *get* the value; it does not need to be overridden if
 * #setValue(Object) is overridden and its behavior changed #buildValue()
 * override this method only if returning a null value when the subject is null
 * is unacceptable #setValue(Object) override this method only if something must
 * be done when the subject is null (e.g. throw an exception)
 *
 * @author mqfdy
 * @param <S>
 *            the generic type
 * @param <T>
 *            the generic type
 */
public abstract class PropertyAspectAdapter<S extends Model, T>
	extends AspectAdapter<S>
	implements WritablePropertyValueModel<T>
{
	/**
	 * Cache the current value of the aspect so we
	 * can pass an "old value" when we fire a property change event.
	 * We need this because the value may be calculated and may
	 * not be in the property change event fired by the subject,
	 * especially when dealing with multiple aspects.
	 */
	protected T value;

	/** The name of the subject's properties that we use for the value. */
	protected final String[] propertyNames;
		
		/** The Constant EMPTY_PROPERTY_NAMES. */
		protected static final String[] EMPTY_PROPERTY_NAMES = new String[0];

	/** A listener that listens to the appropriate properties of the subject. */
	protected final PropertyChangeListener propertyChangeListener;


	// ********** constructors **********

	/**
	 * Construct a PropertyAspectAdapter for the specified subject and property.
	 *
	 * @param propertyName
	 *            the property name
	 * @param subject
	 *            the subject
	 */
	protected PropertyAspectAdapter(String propertyName, S subject) {
		this(new String[] {propertyName}, subject);
	}

	/**
	 * Construct a PropertyAspectAdapter for the specified subject and
	 * properties.
	 *
	 * @param propertyNames
	 *            the property names
	 * @param subject
	 *            the subject
	 */
	protected PropertyAspectAdapter(String[] propertyNames, S subject) {
		this(new StaticPropertyValueModel<S>(subject), propertyNames);
	}

	/**
	 * Construct a PropertyAspectAdapter for the specified subject holder and
	 * properties.
	 *
	 * @param subjectHolder
	 *            the subject holder
	 * @param propertyNames
	 *            the property names
	 */
	protected PropertyAspectAdapter(PropertyValueModel<? extends S> subjectHolder, String... propertyNames) {
		super(subjectHolder);
		this.propertyNames = propertyNames;
		this.propertyChangeListener = this.buildPropertyChangeListener();
		// our value is null when we are not listening to the subject
		this.value = null;
	}

	/**
	 * Construct a PropertyAspectAdapter for the specified subject holder and
	 * properties.
	 *
	 * @param subjectHolder
	 *            the subject holder
	 * @param propertyNames
	 *            the property names
	 */
	protected PropertyAspectAdapter(PropertyValueModel<? extends S> subjectHolder, Collection<String> propertyNames) {
		this(subjectHolder, propertyNames.toArray(new String[propertyNames.size()]));
	}

	/**
	 * Construct a PropertyAspectAdapter for an "unchanging" property in the
	 * specified subject. This is useful for a property aspect that does not
	 * change for a particular subject; but the subject will change, resulting
	 * in a new property. (A TransformationPropertyValueModel could also be used
	 * in this situation.)
	 *
	 * @param subjectHolder
	 *            the subject holder
	 */
	protected PropertyAspectAdapter(PropertyValueModel<? extends S> subjectHolder) {
		this(subjectHolder, EMPTY_PROPERTY_NAMES);
	}


	// ********** initialization **********

	/**
	 * The subject's property has changed, notify the listeners.
	 *
	 * @author mqfdy
	 * @return the property change listener
	 * @Date 2018-09-03 09:00
	 */
	protected PropertyChangeListener buildPropertyChangeListener() {
		// transform the subject's property change events into VALUE property change events
		return new PropertyChangeListener() {
			public void propertyChanged(PropertyChangeEvent event) {
				PropertyAspectAdapter.this.propertyChanged();
			}
			@Override
			public String toString() {
				return "property change listener: " + Arrays.asList(PropertyAspectAdapter.this.propertyNames);
			}
		};
	}


	// ********** PropertyValueModel implementation **********

	/**
	 * Return the value of the subject's property.
	 */
	@Override
	public final T getValue() {
		return this.value;
	}


	// ********** WritablePropertyValueModel implementation **********

	/**
	 * Set the value of the subject's property.
	 */
	public void setValue(T value) {
		if (this.subject != null) {
			this.setValue_(value);
		}
	}

	/**
	 * Set the value of the subject's property. At this point we can be sure
	 * that the subject is not null.
	 *
	 * @param value
	 *            the new value
	 * @see #setValue(Object)
	 */
	protected void setValue_(T value) {
		throw new UnsupportedOperationException();
	}


	// ********** AspectAdapter implementation **********

	@Override
	protected Class<? extends ChangeListener> getListenerClass() {
		return PropertyChangeListener.class;
	}

	@Override
	protected String getListenerAspectName() {
		return VALUE;
	}

    @Override
	protected boolean hasListeners() {
		return this.hasAnyPropertyChangeListeners(VALUE);
	}

    @Override
	protected void fireAspectChange(Object oldValue, Object newValue) {
		this.firePropertyChanged(VALUE, oldValue, newValue);
	}

    @Override
	protected void engageSubject() {
		super.engageSubject();
		// synch our value *after* we start listening to the subject,
		// since its value might change when a listener is added
		this.value = this.buildValue();
	}

    @Override
	protected void engageSubject_() {
    	for (String propertyName : this.propertyNames) {
			((Model) this.subject).addPropertyChangeListener(propertyName, this.propertyChangeListener);
		}
	}

    @Override
	protected void disengageSubject() {
		super.disengageSubject();
		// clear out our value when we are not listening to the subject
		this.value = null;
	}

    @Override
	protected void disengageSubject_() {
    	for (String propertyName : this.propertyNames) {
			((Model) this.subject).removePropertyChangeListener(propertyName, this.propertyChangeListener);
		}
	}


	// ********** AbstractModel implementation **********

	@Override
	public void toString(StringBuilder sb) {
		for (int i = 0; i < this.propertyNames.length; i++) {
			if (i != 0) {
				sb.append(", ");
			}
			sb.append(this.propertyNames[i]);
		}
	}


	// ********** behavior **********

	/**
	 * Return the aspect's value. At this point the subject may be null.
	 *
	 * @author mqfdy
	 * @return the t
	 * @Date 2018-09-03 09:00
	 */
	protected T buildValue() {
		return (this.subject == null) ? null : this.buildValue_();
	}

	/**
	 * Return the value of the subject's property. At this point we can be sure
	 * that the subject is not null.
	 *
	 * @return the t
	 * @see #buildValue()
	 */
	protected T buildValue_() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Property changed.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	protected void propertyChanged() {
		T old = this.value;
		this.value = this.buildValue();
		this.fireAspectChange(old, this.value);
	}

}
