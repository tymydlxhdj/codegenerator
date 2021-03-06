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
import java.io.Writer;

// TODO: Auto-generated Javadoc
/**
 * Extend PrintWriter to automatically indent new lines.
 */
public class IndentingPrintWriter extends PrintWriter {

	private final String indent;
	private int indentLevel;
	private boolean needsIndent;

	/** The default indent. */
	public static String DEFAULT_INDENT = "\t";


	/**
	 * Construct a writer that indents with tabs.
	 *
	 * @param out
	 *            the out
	 */
	public IndentingPrintWriter(Writer out) {
		this(out, DEFAULT_INDENT);
	}
	
	/**
	 * Construct a writer that indents with the specified string.
	 *
	 * @param out
	 *            the out
	 * @param indent
	 *            the indent
	 */
	public IndentingPrintWriter(Writer out, String indent) {
		super(out);
		this.indent = indent;
		this.indentLevel = 0;
		this.needsIndent = true;
	}
	
	/**
	 * Set flag so following line is indented.
	 */
	@Override
	public void println() {
		synchronized (this.lock) {
			super.println();
			this.needsIndent = true;
		}
	}
	
	/**
	 * Print the appropriate indent.
	 */
	private void printIndent() {
		if (this.needsIndent) {
			this.needsIndent = false;
			for (int i = this.indentLevel; i-- > 0; ) {
				this.print(this.indent);
			}
		}
	}
	
	/**
	 * Write a portion of an array of characters.
	 */
	@Override
	public void write(char buf[], int off, int len) {
		synchronized (this.lock) {
			this.printIndent();
			super.write(buf, off, len);
		}
	}
	
	/**
	 * Write a single character.
	 */
	@Override
	public void write(int c) {
		synchronized (this.lock) {
			this.printIndent();
			super.write(c);
		}
	}
	
	/**
	 * Write a portion of a string.
	 */
	@Override
	public void write(String s, int off, int len) {
		synchronized (this.lock) {
			this.printIndent();
			super.write(s, off, len);
		}
	}
	
	/**
	 * Bump the indent level.
	 */
	public void indent() {
		this.incrementIndentLevel();
	}
	
	/**
	 * Decrement the indent level.
	 */
	public void undent() {
		this.decrementIndentLevel();
	}
	
	/**
	 * Bump the indent level.
	 */
	public void incrementIndentLevel() {
		synchronized (this.lock) {
			this.indentLevel++;
		}
	}
	
	/**
	 * Decrement the indent level.
	 */
	public void decrementIndentLevel() {
		synchronized (this.lock) {
			this.indentLevel--;
		}
	}
	
	/**
	 * Return the current indent level.
	 *
	 * @author mqfdy
	 * @return the int
	 * @Date 2018-09-03 09:00
	 */
	public int indentLevel() {
		return this.indentLevel;
	}
	
	/**
	 * Allow the indent level to be set directly.
	 *
	 * @author mqfdy
	 * @param indentLevel
	 *            the new indent level
	 * @Date 2018-09-03 09:00
	 */
	public void setIndentLevel(int indentLevel) {
		synchronized (this.lock) {
			this.indentLevel = indentLevel;
		}
	}

}
