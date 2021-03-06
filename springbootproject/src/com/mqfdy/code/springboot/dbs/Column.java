/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package com.mqfdy.code.springboot.dbs;

import com.mqfdy.code.springboot.utilities.JavaType;

/**
 * Database column
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * This interface is not intended to be implemented by clients.
 */
public interface Column extends Comparable<Column> {

	/**
	 * Return the column's name.
	 */
	String getName();

	/**
	 * Return the name of the column's datatype.
	 */
	String getDataTypeName();

	/**
	 * Return a Java-appropriate version of the column's name.
	 */
	String getJavaFieldName();

	/**
	 * Return whether the column's name matches the specified Java identifier,
	 * respecting the database's case-sensitivity.
	 */
	boolean matchesJavaFieldName(String javaFieldName);

	/**
	 * Return a Java type declaration that is reasonably
	 * similar to the column's data type and suitable for use as a
	 * primary key field.
	 */
	String getPrimaryKeyJavaTypeDeclaration();

	/**
	 * Return a Java type that is reasonably
	 * similar to the column's data type and suitable for use as a
	 * primary key field.
	 */
	JavaType getPrimaryKeyJavaType();

	/**
	 * Return a Java type declaration that is reasonably
	 * similar to the column's data type.
	 */
	String getJavaTypeDeclaration();

	/**
	 * Return a Java type that is reasonably
	 * similar to the column's data type.
	 */
	JavaType getJavaType();

	/**
	 * Return whether the column's datatype is a LOB type
	 * (i.e. BLOB, CLOB, or NCLOB).
	 */
	boolean dataTypeIsLOB();

}
