/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package com.mqfdy.code.springboot.dbs.internal;

import java.text.Collator;

import com.mqfdy.code.springboot.dbs.Sequence;


/**
 *  Wrap a DTP Sequence
 */
final class DTPSequenceWrapper
	extends DTPWrapper
	implements Sequence
{
	// backpointer to parent
	private final DTPSchemaWrapper schema;

	// the wrapped DTP sequence
	private final org.eclipse.datatools.modelbase.sql.schema.Sequence dtpSequence;


	// ********** constructor **********

	DTPSequenceWrapper(DTPSchemaWrapper schema, org.eclipse.datatools.modelbase.sql.schema.Sequence dtpSequence) {
		super(schema, dtpSequence);
		this.schema = schema;
		this.dtpSequence = dtpSequence;
	}


	// ********** DTPWrapper implementation **********

	@Override
	synchronized void catalogObjectChanged(int eventType) {
		this.getConnectionProfile().sequenceChanged(this, eventType);
	}


	// ********** Sequence implementation **********

	@Override
	public String getName() {
		return this.dtpSequence.getName();
	}


	// ********** Comparable implementation **********

	public int compareTo(Sequence sequence) {
		return Collator.getInstance().compare(this.getName(), sequence.getName());
	}


	// ********** internal methods **********

	boolean wraps(org.eclipse.datatools.modelbase.sql.schema.Sequence sequence) {
		return this.dtpSequence == sequence;
	}

	boolean isCaseSensitive() {
		return this.schema.isCaseSensitive();
	}

	DTPDatabaseWrapper database() {
		return this.schema.database();
	}

}
