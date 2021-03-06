/*******************************************************************************
 * Copyright (c) 2005, 2007 Oracle. All rights reserved.
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
 * This simple container class simply puts a bit of semantics
 * around a pair of numbers.
 */
public class Range
	implements Cloneable, Serializable
{
	/** The starting index of the range. */
	public final int start;

	/** The ending index of the range. */
	public final int end;

	/**
	 * The size can be negative if the ending index
	 * is less than the starting index.
	 */
	public final int size;

	private static final long serialVersionUID = 1L;


	/**
	 * Construct with the specified start and end, both of which are immutable.
	 *
	 * @param start
	 *            the start
	 * @param end
	 *            the end
	 */
	public Range(int start, int end) {
		super();
		this.start = start;
		this.end = end;
		this.size = end - start + 1;
	}

	/**
	 * Return whether the range includes the specified index.
	 *
	 * @author mqfdy
	 * @param index
	 *            the index
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	public boolean includes(int index) {
		return (this.start <= index) && (index <= this.end);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if ( ! (o instanceof Range)) {
			return false;
		}
		Range otherRange = (Range) o;
		return (this.start == otherRange.start)
			&& (this.end == otherRange.end);
	}

	@Override
	public int hashCode() {
		return this.start ^ this.end;
	}

	@Override
	public Range clone() {
		try {
			return (Range) super.clone();
		} catch (CloneNotSupportedException ex) {
			throw new InternalError();
		}
	}

	@Override
	public String toString() {
		return "[" + this.start + ", " + this.end + ']';
	}

}
