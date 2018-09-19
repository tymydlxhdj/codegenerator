/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package com.mqfdy.code.springboot.utilities.internal;

import java.io.Serializable;

// TODO: Auto-generated Javadoc
/**
 * Straightforward implementation of Association.
 *
 * @author mqfdy
 * @param <K>
 *            the key type
 * @param <V>
 *            the value type
 */
public class SimpleAssociation<K, V>
	extends AbstractAssociation<K, V>
	implements Cloneable, Serializable
{
	private final K key;
	private V value;

	private static final long serialVersionUID = 1L;


	/**
	 * Construct an association with the specified key and a null value.
	 *
	 * @param key
	 *            the key
	 */
	public SimpleAssociation(K key) {
		super();
		this.key = key;
	}

	/**
	 * Construct an association with the specified key and value.
	 *
	 * @param key
	 *            the key
	 * @param value
	 *            the value
	 */
	public SimpleAssociation(K key, V value) {
		this(key);
		this.value = value;
	}


	public K key() {
		return this.key;
	}

	public synchronized V value() {
		return this.value;
	}

	public synchronized V setValue(V value) {
		V old = this.value;
		this.value = value;
		return old;
	}

	@Override
	@SuppressWarnings("unchecked")
	public synchronized SimpleAssociation<K, V> clone() {
		try {
			return (SimpleAssociation<K, V>) super.clone();
		} catch (CloneNotSupportedException ex) {
			throw new InternalError();
		}
	}

}
