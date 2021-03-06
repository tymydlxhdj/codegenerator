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

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

// TODO: Auto-generated Javadoc
/**
 * This encoder will replace any of a specified set of characters with an XML
 * "character reference": '/' => "&#x2f;"
 */
public final class XMLStringEncoder {

	/** The set of characters to be converted into XML character references. */
	private final char[] chars;

	/** Cache the value of the highest character in the set above. */
	private final char maxChar;


	// ********** constructors/initialization **********

	/**
	 * Construct an encoder that converts the specified set of characters into
	 * XML character references.
	 *
	 * @param chars
	 *            the chars
	 */
	public XMLStringEncoder(char[] chars) {
		super();
		if (chars == null) {
			throw new NullPointerException();
		}
		// the ampersand must be included since it is the escape character
		if (CollectionTools.contains(chars, '&')) {
			this.chars = chars;
		} else {
			this.chars = CollectionTools.add(chars, '&');
		}
		this.maxChar = this.calculateMaxInvalidFileNameChar();
	}

	/**
	 * Calculate the maximum value of the set of characters to be converted
	 * into XML character references. This will be used to short-circuit the
	 * search for a character in the set.
	 * @see #charIsToBeEncoded(char)
	 */
	private char calculateMaxInvalidFileNameChar() {
		char[] localChars = this.chars;
		char max = 0;
		for (int i = localChars.length; i-- > 0; ) {
			char c = localChars[i];
			if (max < c) {
				max = c;
			}
		}
		return max;
	}


	// ********** API **********

	/**
	 * Return the specified string with any characters in the set replaced with
	 * XML character references.
	 *
	 * @author mqfdy
	 * @param s
	 *            the s
	 * @return the string
	 * @Date 2018-09-03 09:00
	 */
	public String encode(String s) {
		int len = s.length();
		// allow for a few encoded characters
		StringBuilder sb = new StringBuilder(len + 20);
		for (int i = 0; i < len; i++) {
			this.appendCharacterTo(s.charAt(i), sb);
		}
		return sb.toString();
	}

	/**
	 * Return the specified string with any XML character references replaced by
	 * the characters themselves.
	 *
	 * @author mqfdy
	 * @param s
	 *            the s
	 * @return the string
	 * @Date 2018-09-03 09:00
	 */
	public String decode(String s) {
		StringBuilder sb = new StringBuilder(s.length());
		StringBuilder temp = new StringBuilder();	// performance tweak
		this.decodeTo(new StringReader(s), sb, temp);
		return sb.toString();
	}


	// ********** internal methods **********

	/**
	 * Append the specified character to the string buffer,
	 * converting it to an XML character reference if necessary.
	 */
	private void appendCharacterTo(char c, StringBuilder sb) {
		if (this.charIsToBeEncoded(c)) {
			this.appendCharacterReferenceTo(c, sb);
		} else {
			sb.append(c);
		}
	}

	/**
	 * Return whether the specified character is one of the characters
	 * to be converted to XML character references.
	 */
	private boolean charIsToBeEncoded(char c) {
		return (c <= this.maxChar) && CollectionTools.contains(this.chars, c);
	}

	/**
	 * Append the specified character's XML character reference to the
	 * specified string buffer (e.g. '/' => "&#x2f;").
	 */
	private void appendCharacterReferenceTo(char c, StringBuilder sb) {
		sb.append("&#x");
		sb.append(Integer.toString(c, 16));
		sb.append(';');
	}

	private void decodeTo(Reader reader, StringBuilder sb, StringBuilder temp) {
		try {
			this.decodeTo_(reader, sb, temp);
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}

	private void decodeTo_(Reader reader, StringBuilder sb, StringBuilder temp) throws IOException {
		int c = reader.read();
		while (c != -1) {
			if (c == '&') {
				this.decodeCharacterReferenceTo(reader, sb, temp);
			} else {
				sb.append((char) c);
			}
			c = reader.read();
		}
		reader.close();
	}

	private void decodeCharacterReferenceTo(Reader reader, StringBuilder sb, StringBuilder temp) throws IOException {
		int c = reader.read();
		this.checkChar(c, '#');
		c = reader.read();
		this.checkChar(c, 'x');

		temp.setLength(0);  // re-use temp
		c = reader.read();
		while (c != ';') {
			this.checkEndOfStream(c);
			temp.append((char) c);
			c = reader.read();
		}
		String charValue = temp.toString();
		if (charValue.length() == 0) {
			throw new IllegalStateException("missing numeric string");
		}
		sb.append((char) Integer.parseInt(charValue, 16));
	}

	private void checkChar(int c, int expected) {
		this.checkEndOfStream(c);
		if (c != expected) {
			throw new IllegalStateException("expected '" + (char) expected + "', but encountered '" + (char) c + "'");
		}
	}

	private void checkEndOfStream(int c) {
		if (c == -1) {
			throw new IllegalStateException("unexpected end of string");
		}
	}

}
