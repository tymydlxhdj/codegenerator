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

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.mqfdy.code.springboot.utilities.internal.iterators.ArrayIterator;


// TODO: Auto-generated Javadoc
/**
 * Various helper methods for generating names.
 */
public final class NameTools {

	/**
	 * Given a "root" name and a set of existing names, generate a unique,
	 * Java-legal name that is either the "root" name or some variation on the
	 * "root" name (e.g. "root2", "root3",...). The names are case-sensitive.
	 *
	 * @author mqfdy
	 * @param rootName
	 *            the root name
	 * @param existingNames
	 *            the existing names
	 * @return the string
	 * @Date 2018-09-03 09:00
	 */
	public static String uniqueJavaNameFor(String rootName, Iterator<String> existingNames) {
		Collection<String> existingNames2 = CollectionTools.set(existingNames);
		existingNames2.addAll(JAVA_RESERVED_WORDS_SET);
		return uniqueNameFor(rootName, existingNames2, rootName);
	}
	
	/**
	 * Given a "root" name and a set of existing names, generate a unique,
	 * Java-legal name that is either the "root" name or some variation on the
	 * "root" name (e.g. "root2", "root3",...). The names are case-sensitive.
	 *
	 * @author mqfdy
	 * @param rootName
	 *            the root name
	 * @param existingNames
	 *            the existing names
	 * @return the string
	 * @Date 2018-09-03 09:00
	 */
	public static String uniqueJavaNameFor(String rootName, Collection<String> existingNames) {
		Collection<String> existingNames2 = new HashSet<String>(existingNames);
		existingNames2.addAll(JAVA_RESERVED_WORDS_SET);
		return uniqueNameFor(rootName, existingNames2, rootName);
	}

	/**
	 * Given a "root" name and a set of existing names, generate a unique name
	 * that is either the "root" name or some variation on the "root" name (e.g.
	 * "root2", "root3",...). The names are case-sensitive.
	 *
	 * @author mqfdy
	 * @param rootName
	 *            the root name
	 * @param existingNames
	 *            the existing names
	 * @return the string
	 * @Date 2018-09-03 09:00
	 */
	public static String uniqueNameFor(String rootName, Iterator<String> existingNames) {
		return uniqueNameFor(rootName, CollectionTools.set(existingNames));
	}
	
	/**
	 * Given a "root" name and a set of existing names, generate a unique name
	 * that is either the "root" name or some variation on the "root" name (e.g.
	 * "root2", "root3",...). The names are case-sensitive.
	 *
	 * @author mqfdy
	 * @param rootName
	 *            the root name
	 * @param existingNames
	 *            the existing names
	 * @return the string
	 * @Date 2018-09-03 09:00
	 */
	public static String uniqueNameFor(String rootName, Collection<String> existingNames) {
		return uniqueNameFor(rootName, existingNames, rootName);
	}

	/**
	 * Given a "root" name and a set of existing names, generate a unique name
	 * that is either the "root" name or some variation on the "root" name (e.g.
	 * "root2", "root3",...). The names are NOT case-sensitive.
	 *
	 * @author mqfdy
	 * @param rootName
	 *            the root name
	 * @param existingNames
	 *            the existing names
	 * @return the string
	 * @Date 2018-09-03 09:00
	 */
	public static String uniqueNameForIgnoreCase(String rootName, Iterator<String> existingNames) {
		return uniqueNameForIgnoreCase(rootName, CollectionTools.set(existingNames));
	}

	/**
	 * Given a "root" name and a set of existing names, generate a unique name
	 * that is either the "root" name or some variation on the "root" name (e.g.
	 * "root2", "root3",...). The names are NOT case-sensitive.
	 *
	 * @author mqfdy
	 * @param rootName
	 *            the root name
	 * @param existingNames
	 *            the existing names
	 * @return the string
	 * @Date 2018-09-03 09:00
	 */
	public static String uniqueNameForIgnoreCase(String rootName, Collection<String> existingNames) {
		return uniqueNameFor(rootName, convertToLowerCase(existingNames), rootName.toLowerCase());
	}

	/**
	 * use the suffixed "template" name to perform the comparisons, but RETURN
	 * the suffixed "root" name; this allows case-insensitive comparisons
	 * (i.e. the "template" name has been morphed to the same case as
	 * the "existing" names, while the "root" name has not, but the "root" name
	 * is what the client wants morphed to be unique)
	 */
	private static String uniqueNameFor(String rootName, Collection<String> existingNames, String templateName) {
		if ( ! existingNames.contains(templateName)) {
			return rootName;
		}
		String uniqueName = templateName;
		for (int suffix = 2; true; suffix++) {
			if ( ! existingNames.contains(uniqueName + suffix)) {
				return rootName.concat(String.valueOf(suffix));
			}
		}
	}

	/**
	 * Convert the specified collection of strings to a collection of the same
	 * strings converted to lower case.
	 */
	private static Collection<String> convertToLowerCase(Collection<String> strings) {
		Collection<String> result = new HashBag<String>(strings.size());
		for (String string : strings) {
			result.add(string.toLowerCase());
		}
		return result;
	}

	/**
	 * Build a fully-qualified name for the specified database object.
	 * Variations: catalog.schema.name catalog..name schema.name name
	 *
	 * @author mqfdy
	 * @param catalog
	 *            the catalog
	 * @param schema
	 *            the schema
	 * @param name
	 *            the name
	 * @return the string
	 * @Date 2018-09-03 09:00
	 */
	public static String buildQualifiedDatabaseObjectName(String catalog, String schema, String name) {
		if (name == null) {
			return null;
		}
		if ((catalog == null) && (schema == null)) {
			return name;
		}

		StringBuilder sb = new StringBuilder(100);
		if (catalog != null) {
			sb.append(catalog);
			sb.append('.');
		}
		if (schema != null) {
			sb.append(schema);
		}
		sb.append('.');
		sb.append(name);
		return sb.toString();
	}

	/**
	 * The set of reserved words in the Java programming language.
	 * These words cannot be used as identifiers (i.e. names).
	 * http://java.sun.com/docs/books/tutorial/java/nutsandbolts/_keywords.html
	 */
	public static final String[] JAVA_RESERVED_WORDS = new String[] {
				"abstract",
				"assert",  // jdk 1.4
				"boolean",
				"break",
				"byte",
				"case",
				"catch",
				"char",
				"class",
				"const",  // unused
				"continue",
				"default",
				"do",
				"double",
				"else",
				"enum",  // jdk 1.5
				"extends",
				"false",
				"final",
				"finally",
				"float",
				"for",
				"goto",  // unused
				"if",
				"implements",
				"import",
				"instanceof",
				"int",
				"interface",
				"long",
				"native",
				"new",
				"null",
				"package",
				"private",
				"protected",
				"public",
				"return",
				"short",
				"static",
				"strictfp",  // jdk 1.2
				"super",
				"switch",
				"synchronized",
				"this",
				"throw",
				"throws",
				"transient",
				"true",
				"try",
				"void",
				"volatile",
				"while"
			};

	/**
	 * The set of reserved words in the Java programming language.
	 * These words cannot be used as identifiers (i.e. names).
	 * http://java.sun.com/docs/books/tutorial/java/nutsandbolts/_keywords.html
	 */
	public static final Set<String> JAVA_RESERVED_WORDS_SET = CollectionTools.set(JAVA_RESERVED_WORDS);

	/**
	 * Return the set of Java programming language reserved words. These words
	 * cannot be used as identifiers (i.e. names).
	 * http://java.sun.com/docs/books/tutorial/java/nutsandbolts/_keywords.html
	 *
	 * @author mqfdy
	 * @return the iterator
	 * @Date 2018-09-03 09:00
	 */
	public static Iterator<String> javaReservedWords() {
		return new ArrayIterator<String>(JAVA_RESERVED_WORDS);
	}

	/**
	 * Convert the specified string to a valid Java identifier by substituting
	 * an underscore '_' for any invalid characters in the string and appending
	 * an underscore '_' to the string if it is a Java reserved word.
	 *
	 * @author mqfdy
	 * @param string
	 *            the string
	 * @return the string
	 * @Date 2018-09-03 09:00
	 */
	public static String convertToJavaIdentifier(String string) {
		return convertToJavaIdentifier(string, '_');
	}

	/**
	 * Convert the specified string to a valid Java identifier by substituting
	 * the specified character for any invalid characters in the string and, if
	 * necessary, appending the specified character to the string until it is
	 * not a Java reserved word.
	 *
	 * @author mqfdy
	 * @param string
	 *            the string
	 * @param c
	 *            the c
	 * @return the string
	 * @Date 2018-09-03 09:00
	 */
	public static String convertToJavaIdentifier(String string, char c) {
		if (string.length() == 0) {
			return string;
		}
		if (JAVA_RESERVED_WORDS_SET.contains(string)) {
			// a reserved word is a valid identifier, we just need to tweak it a bit
			checkCharIsJavaIdentifierPart(c);
			return convertToJavaIdentifier(string + c, c);
		}
		char[] array = string.toCharArray();
		return (convertToJavaIdentifier_(array, c)) ? new String(array) : string;
	}

	/**
	 * Convert the specified string to a valid Java identifier by substituting
	 * an underscore '_' for any invalid characters in the string and appending
	 * an underscore '_' to the string if it is a Java reserved word.
	 *
	 * @author mqfdy
	 * @param string
	 *            the string
	 * @return the char[]
	 * @Date 2018-09-03 09:00
	 */
	public static char[] convertToJavaIdentifier(char[] string) {
		return convertToJavaIdentifier(string, '_');
	}

	/**
	 * Convert the specified string to a valid Java identifier by substituting
	 * the specified character for any invalid characters in the string and, if
	 * necessary, appending the specified character to the string until it is
	 * not a Java reserved word.
	 *
	 * @author mqfdy
	 * @param string
	 *            the string
	 * @param c
	 *            the c
	 * @return the char[]
	 * @Date 2018-09-03 09:00
	 */
	public static char[] convertToJavaIdentifier(char[] string, char c) {
		int length = string.length;
		if (length == 0) {
			return string;
		}
		if (JAVA_RESERVED_WORDS_SET.contains(new String(string))) {
			// a reserved word is a valid identifier, we just need to tweak it a bit
			checkCharIsJavaIdentifierPart(c);
			return convertToJavaIdentifier(CollectionTools.add(string, c), c);
		}
		convertToJavaIdentifier_(string, c);
		return string;
	}

	/**
	 * The specified must not be empty.
	 * Return whether the string was modified.
	 */
	private static boolean convertToJavaIdentifier_(char[] string, char c) {
		boolean mod = false;
		if ( ! Character.isJavaIdentifierStart(string[0])) {
			checkCharIsJavaIdentifierStart(c);
			string[0] = c;
			mod = true;
		}
		checkCharIsJavaIdentifierPart(c);
		for (int i = string.length; i-- > 1; ) {  // NB: end with 1
			if ( ! Character.isJavaIdentifierPart(string[i])) {
				string[i] = c;
				mod = true;
			}
		}
		return mod;
	}

	private static void checkCharIsJavaIdentifierStart(char c) {
		if ( ! Character.isJavaIdentifierStart(c)) {
			throw new IllegalArgumentException("invalid Java identifier start char: '" + c + "'");
		}
	}

	private static void checkCharIsJavaIdentifierPart(char c) {
		if ( ! Character.isJavaIdentifierPart(c)) {
			throw new IllegalArgumentException("invalid Java identifier part char: '" + c + "'");
		}
	}


	// ********** constructor **********

	/**
	 * Suppress default constructor, ensuring non-instantiability.
	 */
	private NameTools() {
		super();
		throw new UnsupportedOperationException();
	}

}
