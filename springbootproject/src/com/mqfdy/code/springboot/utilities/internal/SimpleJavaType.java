/*******************************************************************************
 * Copyright (c) 2005, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package com.mqfdy.code.springboot.utilities.internal;

import java.io.PrintWriter;
import java.io.Serializable;
import java.text.Collator;

import com.mqfdy.code.springboot.utilities.JavaType;


// TODO: Auto-generated Javadoc
/**
 * Straightforward implementation of the JavaType interface.
 */
public final class SimpleJavaType
	implements JavaType, Cloneable, Serializable
{

	/**
	 * store the type as a name, so we can reference classes
	 * that are not loaded
	 */
	private final String elementTypeName;

	/**
	 * non-array types have an array depth of zero
	 */
	private final int arrayDepth;

	private static final long serialVersionUID = 1L;


	// ********** constructors **********

	/**
	 * Construct a Java type with the specified element type and array depth.
	 *
	 * @param elementTypeName
	 *            the element type name
	 * @param arrayDepth
	 *            the array depth
	 */
	public SimpleJavaType(String elementTypeName, int arrayDepth) {
		super();
		if ((elementTypeName == null) || (elementTypeName.length() == 0)) {
			throw new IllegalArgumentException("The element type name is required.");
		}
		if (ClassTools.arrayDepthForClassNamed(elementTypeName) != 0) {		// e.g. "[Ljava.lang.Object;"
			throw new IllegalArgumentException("The element type must not be an array: " + elementTypeName + '.');
		}
		if (arrayDepth < 0) {
			throw new IllegalArgumentException("The array depth must be greater than or equal to zero: " + arrayDepth + '.');
		}
		if (elementTypeName.equals(void.class.getName()) && (arrayDepth != 0)) {
			throw new IllegalArgumentException("'void' must have an array depth of zero: " + arrayDepth + '.');
		}
		this.elementTypeName = elementTypeName;
		this.arrayDepth = arrayDepth;
	}

	/**
	 * Construct a Java type for the specified class. The class name can be in
	 * one of the following forms: java.lang.Object int java.util.Map$Entry
	 * [Ljava.lang.Object; [I [Ljava.util.Map$Entry;
	 *
	 * @param javaClassName
	 *            the java class name
	 */
	public SimpleJavaType(String javaClassName) {
		this(ClassTools.elementTypeNameForClassNamed(javaClassName), ClassTools.arrayDepthForClassNamed(javaClassName));
	}

	/**
	 * Construct a Java type for the specified class.
	 *
	 * @param javaClass
	 *            the java class
	 */
	public SimpleJavaType(Class<?> javaClass) {
		this(javaClass.getName());
	}


	// ********** accessors **********

	public String getElementTypeName() {
		return this.elementTypeName;
	}

	public int getArrayDepth() {
		return this.arrayDepth;
	}


	// ********** queries **********

	public boolean isArray() {
		return this.arrayDepth > 0;
	}

	public boolean isPrimitive() {
		return (this.arrayDepth == 0) && ClassTools.classNamedIsPrimitive(this.elementTypeName);
	}

	public boolean isPrimitiveWrapper() {
		return (this.arrayDepth == 0) && ClassTools.classNamedIsPrimitiveWrapperClass(this.elementTypeName);
	}

	public boolean isVariablePrimitive() {
		return (this.arrayDepth == 0) && ClassTools.classNamedIsVariablePrimitive(this.elementTypeName);
	}

	public boolean isVariablePrimitiveWrapper() {
		return (this.arrayDepth == 0) && ClassTools.classNamedIsVariablePrimitiveWrapperClass(this.elementTypeName);
	}

	public Class<?> getJavaClass() throws ClassNotFoundException {
		return ClassTools.classForTypeDeclaration(this.elementTypeName, this.arrayDepth);
	}

	public String getJavaClassName() {
		return ClassTools.classNameForTypeDeclaration(this.elementTypeName, this.arrayDepth);
	}


	// ********** comparison **********

	public boolean equals(String otherElementTypeName, int otherArrayDepth) {
		return (this.arrayDepth == otherArrayDepth)
			&& this.elementTypeName.equals(otherElementTypeName);
	}

	public boolean describes(String className) {
		return this.equals(ClassTools.elementTypeNameForClassNamed(className), ClassTools.arrayDepthForClassNamed(className));
	}

	public boolean describes(Class<?> javaClass) {
		return this.describes(javaClass.getName());
	}

	public boolean equals(JavaType other) {
		return this.equals(other.getElementTypeName(), other.getArrayDepth());
	}

	@Override
	public boolean equals(Object o) {
		return (this == o) ? true : (o instanceof JavaType) ? this.equals((JavaType) o) : false;
	}

	@Override
	public int hashCode() {
		return this.elementTypeName.hashCode() ^ this.arrayDepth;
	}

	public int compareTo(JavaType jt) {
		int x = Collator.getInstance().compare(this.elementTypeName, jt.getElementTypeName());
		return (x != 0) ? x : (this.arrayDepth - jt.getArrayDepth());
	}


	// ********** printing and displaying **********

	/**
	 * Return the version of the type's name that can be used in source code:
	 *     "[[J" => "long[][]"
	 *     "java.util.Map$Entry" => "java.util.Map.Entry"
	 */
	public String declaration() {
		if (this.arrayDepth == 0) {
			return this.elementTypeNameDeclaration();
		}
		StringBuilder sb = new StringBuilder(this.elementTypeName.length() + (2 * this.arrayDepth));
		this.appendDeclarationTo(sb);
		return sb.toString();
	}

	/**
	 * Append the version of the type's name that can be used in source code:
	 *     "[[J" => "long[][]"
	 *     "java.util.Map$Entry" => "java.util.Map.Entry"
	 */
	public void appendDeclarationTo(StringBuilder sb) {
		sb.append(this.elementTypeNameDeclaration());
		for (int i = this.arrayDepth; i-- > 0; ) {
			sb.append("[]");
		}
	}

	/**
	 * Print the version of the type's name that can be used in source code:
	 *     "[[J" => "long[][]"
	 *     "java.util.Map$Entry" => "java.util.Map.Entry"
	 */
	public void printDeclarationOn(PrintWriter pw) {
		pw.print(this.elementTypeNameDeclaration());
		for (int i = this.arrayDepth; i-- > 0; ) {
			pw.print("[]");
		}
	}

	/**
	 * The '$' version of the name is used in Class.forName(String),
	 * but the '.' verions of the name is used in source code.
	 * Very irritating....
	 */
	private String elementTypeNameDeclaration() {
		return this.elementTypeName.replace('$', '.');
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(ClassTools.shortClassNameForObject(this));
		sb.append('(');
		this.appendDeclarationTo(sb);
		sb.append(')');
		return sb.toString();
	}


	// ********** cloning **********

	@Override
	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException ex) {
			throw new InternalError();
		}
	}

}
