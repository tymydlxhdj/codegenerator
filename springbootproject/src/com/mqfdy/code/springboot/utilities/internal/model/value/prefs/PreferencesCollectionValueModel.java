/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package com.mqfdy.code.springboot.utilities.internal.model.value.prefs;

import java.util.HashMap;
import java.util.Iterator;
import java.util.prefs.BackingStoreException;
import java.util.prefs.PreferenceChangeEvent;
import java.util.prefs.PreferenceChangeListener;
import java.util.prefs.Preferences;

import com.mqfdy.code.springboot.utilities.internal.iterators.ArrayIterator;
import com.mqfdy.code.springboot.utilities.internal.iterators.TransformationIterator;
import com.mqfdy.code.springboot.utilities.internal.model.value.AspectAdapter;
import com.mqfdy.code.springboot.utilities.internal.model.value.StaticPropertyValueModel;
import com.mqfdy.code.springboot.utilities.model.listener.ChangeListener;
import com.mqfdy.code.springboot.utilities.model.listener.CollectionChangeListener;
import com.mqfdy.code.springboot.utilities.model.value.CollectionValueModel;
import com.mqfdy.code.springboot.utilities.model.value.PropertyValueModel;


// TODO: Auto-generated Javadoc
/**
 * This adapter wraps a Preferences node and converts its preferences into a
 * CollectionValueModel of PreferencePropertyValueModels. It listens for
 * "preference" changes and converts them into VALUE collection changes.
 *
 * @author mqfdy
 * @param <P>
 *            the generic type
 */
public class PreferencesCollectionValueModel<P>
	extends AspectAdapter<Preferences>
	implements CollectionValueModel<PreferencePropertyValueModel<P>>
{

	/** Cache the current preferences, stored in models and keyed by name. */
	protected final HashMap<String, PreferencePropertyValueModel<P>> preferences;

	/** A listener that listens to the preferences node for added or removed preferences. */
	protected final PreferenceChangeListener preferenceChangeListener;


	// ********** constructors **********

	/**
	 * Construct an adapter for the specified preferences node.
	 *
	 * @param preferences
	 *            the preferences
	 */
	public PreferencesCollectionValueModel(Preferences preferences) {
		this(new StaticPropertyValueModel<Preferences>(preferences));
	}

	/**
	 * Construct an adapter for the specified preferences node.
	 *
	 * @param preferencesHolder
	 *            the preferences holder
	 */
	public PreferencesCollectionValueModel(PropertyValueModel<? extends Preferences> preferencesHolder) {
		super(preferencesHolder);
		this.preferences = new HashMap<String, PreferencePropertyValueModel<P>>();
		this.preferenceChangeListener = this.buildPreferenceChangeListener();
	}


	// ********** initialization **********

	/**
	 * A preferences have changed, notify the listeners.
	 *
	 * @author mqfdy
	 * @return the preference change listener
	 * @Date 2018-09-03 09:00
	 */
	protected PreferenceChangeListener buildPreferenceChangeListener() {
		// transform the preference change events into VALUE collection change events
		return new PreferenceChangeListener() {
			public void preferenceChange(PreferenceChangeEvent event) {
				PreferencesCollectionValueModel.this.preferenceChanged(event.getKey(), event.getNewValue());
			}
			@Override
			public String toString() {
				return "preference change listener";
			}
		};
	}


	// ********** CollectionValueModel implementation **********

	/**
	 * Return an iterator on the preference models.
	 */
	public synchronized Iterator<PreferencePropertyValueModel<P>> iterator() {
		return this.preferences.values().iterator();
	}

	public synchronized int size() {
		return this.preferences.size();
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
		this.subject.addPreferenceChangeListener(this.preferenceChangeListener);
		for (Iterator<PreferencePropertyValueModel<P>> stream = this.preferenceModels(); stream.hasNext(); ) {
			PreferencePropertyValueModel<P> preferenceModel = stream.next();
			this.preferences.put(preferenceModel.getKey(), preferenceModel);
		}
	}

    @Override
	protected void disengageSubject_() {
		try {
			this.subject.removePreferenceChangeListener(this.preferenceChangeListener);
		} catch (IllegalStateException ex) {
			// for some odd reason, we are not allowed to remove a listener from a "dead"
			// preferences node; so handle the exception that gets thrown here
			if ( ! ex.getMessage().equals("Node has been removed.")) {
				// if it is not the expected exception, re-throw it
				throw ex;
			}
		}
		this.preferences.clear();
	}


	// ********** AbstractModel implementation **********

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.subject);
	}


	// ********** internal methods **********

	/**
	 * Return an iterator on the preference models. At this point we can be sure
	 * that the subject is not null.
	 *
	 * @author mqfdy
	 * @return the iterator
	 * @Date 2018-09-03 09:00
	 */
	protected Iterator<PreferencePropertyValueModel<P>> preferenceModels() {
		String[] keys;
		try {
			keys = this.subject.keys();
		} catch (BackingStoreException ex) {
			throw new RuntimeException(ex);
		}
		return new TransformationIterator<String, PreferencePropertyValueModel<P>>(new ArrayIterator<String>(keys)) {
			@Override
			protected PreferencePropertyValueModel<P> transform(String key) {
				return PreferencesCollectionValueModel.this.buildPreferenceModel(key);
			}
		};
	}

	/**
	 * Override this method to tweak the model used to wrap the specified
	 * preference (e.g. to customize the model's converter).
	 *
	 * @author mqfdy
	 * @param key
	 *            the key
	 * @return the preference property value model
	 * @Date 2018-09-03 09:00
	 */
	protected PreferencePropertyValueModel<P> buildPreferenceModel(String key) {
		return new PreferencePropertyValueModel<P>(this.subjectHolder, key);
	}

	/**
	 * Preference changed.
	 *
	 * @author mqfdy
	 * @param key
	 *            the key
	 * @param newValue
	 *            the new value
	 * @Date 2018-09-03 09:00
	 */
	protected synchronized void preferenceChanged(String key, String newValue) {
		if (newValue == null) {
			// a preference was removed
			PreferencePropertyValueModel<P> preferenceModel = this.preferences.remove(key);
			this.fireItemRemoved(VALUES, preferenceModel);
		} else if ( ! this.preferences.containsKey(key)) {
			// a preference was added
			PreferencePropertyValueModel<P> preferenceModel = this.buildPreferenceModel(key);
			this.preferences.put(key, preferenceModel);
			this.fireItemAdded(VALUES, preferenceModel);
		} else {
			// a preference's value changed - do nothing
		}
	}

}
