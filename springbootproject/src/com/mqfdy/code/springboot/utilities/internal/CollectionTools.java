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

import java.lang.reflect.Array;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;
import java.util.RandomAccess;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Vector;

import com.mqfdy.code.springboot.utilities.internal.iterators.ArrayIterator;
import com.mqfdy.code.springboot.utilities.internal.iterators.GenericIteratorWrapper;
import com.mqfdy.code.springboot.utilities.internal.iterators.SingleElementIterator;


// TODO: Auto-generated Javadoc
/**
 * The Class CollectionTools.
 *
 * @author mqfdy
 */
public final class CollectionTools {

	@SuppressWarnings("unchecked")
	private static <E> E[] newArray(E[] array, int length) {
		return (E[]) Array.newInstance(array.getClass().getComponentType(), length);
	}

	/**
	 * Return a new array that contains the elements in the specified array
	 * followed by the specified object to be added.
	 * java.util.Arrays#add(Object[] array, Object o)
	 *
	 * @author mqfdy
	 * @param <E>
	 *            the element type
	 * @param array
	 *            the array
	 * @param value
	 *            the value
	 * @return the e[]
	 * @Date 2018-09-03 09:00
	 */
	public static <E> E[] add(E[] array, E value) {
		int len = array.length;
		E[] result = newArray(array, len + 1);
		if (len > 0) {
			System.arraycopy(array, 0, result, 0, len);
		}
		result[len] = value;
		return result;
	}

	/**
	 * Return a new array that contains the elements in the specified array with
	 * the specified object added at the specified index.
	 * java.util.Arrays#add(Object[] array, int index, Object o)
	 *
	 * @author mqfdy
	 * @param <E>
	 *            the element type
	 * @param array
	 *            the array
	 * @param index
	 *            the index
	 * @param value
	 *            the value
	 * @return the e[]
	 * @Date 2018-09-03 09:00
	 */
	public static <E> E[] add(E[] array, int index, E value) {
		int len = array.length;
		E[] result = newArray(array, len + 1);
		if (index > 0) {
			System.arraycopy(array, 0, result, 0, index);
		}
		result[index] = value;
		if (len > index) {
			System.arraycopy(array, index, result, index + 1, len - index);
		}
		return result;
	}

	/**
	 * Return a new array that contains the elements in the specified array
	 * followed by the specified value to be added. java.util.Arrays#add(char[]
	 * array, char value)
	 *
	 * @author mqfdy
	 * @param array
	 *            the array
	 * @param value
	 *            the value
	 * @return the char[]
	 * @Date 2018-09-03 09:00
	 */
	public static char[] add(char[] array, char value) {
		int len = array.length;
		char[] result = new char[len + 1];
		if (len > 0) {
			System.arraycopy(array, 0, result, 0, len);
		}
		result[len] = value;
		return result;
	}

	/**
	 * Return a new array that contains the elements in the specified array with
	 * the specified value added at the specified index.
	 * java.util.Arrays#add(char[] array, int index, char value)
	 *
	 * @author mqfdy
	 * @param array
	 *            the array
	 * @param index
	 *            the index
	 * @param value
	 *            the value
	 * @return the char[]
	 * @Date 2018-09-03 09:00
	 */
	public static char[] add(char[] array, int index, char value) {
		int len = array.length;
		char[] result = new char[len + 1];
		if (index > 0) {
			System.arraycopy(array, 0, result, 0, index);
		}
		result[index] = value;
		if (len > index) {
			System.arraycopy(array, index, result, index + 1, len - index);
		}
		return result;
	}

	/**
	 * Return a new array that contains the elements in the specified array
	 * followed by the specified value to be added. java.util.Arrays#add(int[]
	 * array, int value)
	 *
	 * @author mqfdy
	 * @param array
	 *            the array
	 * @param value
	 *            the value
	 * @return the int[]
	 * @Date 2018-09-03 09:00
	 */
	public static int[] add(int[] array, int value) {
		int len = array.length;
		int[] result = new int[len + 1];
		if (len > 0) {
			System.arraycopy(array, 0, result, 0, len);
		}
		result[len] = value;
		return result;
	}

	/**
	 * Return a new array that contains the elements in the specified array with
	 * the specified value added at the specified index.
	 * java.util.Arrays#add(int[] array, int index, int value)
	 *
	 * @author mqfdy
	 * @param array
	 *            the array
	 * @param index
	 *            the index
	 * @param value
	 *            the value
	 * @return the int[]
	 * @Date 2018-09-03 09:00
	 */
	public static int[] add(int[] array, int index, int value) {
		int len = array.length;
		int[] result = new int[len + 1];
		if (index > 0) {
			System.arraycopy(array, 0, result, 0, index);
		}
		result[index] = value;
		if (len > index) {
			System.arraycopy(array, index, result, index + 1, len - index);
		}
		return result;
	}

	/**
	 * Add all the elements returned by the specified iterable to the specified
	 * collection. Return whether the collection changed as a result.
	 * java.util.Collection#addAll(java.lang.Iterable iterable)
	 *
	 * @author mqfdy
	 * @param <E>
	 *            the element type
	 * @param collection
	 *            the collection
	 * @param iterable
	 *            the iterable
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	public static <E> boolean addAll(Collection<? super E> collection, Iterable<? extends E> iterable) {
		return addAll(collection, iterable.iterator());
	}

	/**
	 * Add all the elements returned by the specified iterable to the specified
	 * collection. Return whether the collection changed as a result.
	 * java.util.Collection#addAll(java.lang.Iterable iterable)
	 *
	 * @author mqfdy
	 * @param <E>
	 *            the element type
	 * @param collection
	 *            the collection
	 * @param iterable
	 *            the iterable
	 * @param size
	 *            the size
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	public static <E> boolean addAll(Collection<? super E> collection, Iterable<? extends E> iterable, int size) {
		return addAll(collection, iterable.iterator(), size);
	}

	/**
	 * Add all the elements returned by the specified iterator to the specified
	 * collection. Return whether the collection changed as a result.
	 * java.util.Collection#addAll(java.util.Iterator iterator)
	 *
	 * @author mqfdy
	 * @param <E>
	 *            the element type
	 * @param collection
	 *            the collection
	 * @param iterator
	 *            the iterator
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	public static <E> boolean addAll(Collection<? super E> collection, Iterator<? extends E> iterator) {
		return (iterator.hasNext()) ? collection.addAll(list(iterator)) : false;
	}

	/**
	 * Add all the elements returned by the specified iterator to the specified
	 * collection. Return whether the collection changed as a result.
	 * java.util.Collection#addAll(java.util.Iterator iterator)
	 *
	 * @author mqfdy
	 * @param <E>
	 *            the element type
	 * @param collection
	 *            the collection
	 * @param iterator
	 *            the iterator
	 * @param size
	 *            the size
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	public static <E> boolean addAll(Collection<? super E> collection, Iterator<? extends E> iterator, int size) {
		return (iterator.hasNext()) ? collection.addAll(list(iterator, size)) : false;
	}

	/**
	 * Add all the elements in the specified array to the specified collection.
	 * Return whether the collection changed as a result.
	 * java.util.Collection#addAll(Object[] array)
	 *
	 * @author mqfdy
	 * @param <E>
	 *            the element type
	 * @param collection
	 *            the collection
	 * @param array
	 *            the array
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	public static <E> boolean addAll(Collection<? super E> collection, E[] array) {
		return (array.length == 0) ? false : collection.addAll(Arrays.asList(array));
	}

	/**
	 * Add all the elements returned by the specified iterable to the specified
	 * list at the specified index. Return whether the list changed as a result.
	 * java.util.List#addAll(java.lang.Iterable iterable)
	 *
	 * @author mqfdy
	 * @param <E>
	 *            the element type
	 * @param list
	 *            the list
	 * @param index
	 *            the index
	 * @param iterable
	 *            the iterable
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	public static <E> boolean addAll(List<? super E> list, int index, Iterable<E> iterable) {
		return addAll(list, index, iterable.iterator());
	}

	/**
	 * Add all the elements returned by the specified iterable to the specified
	 * list at the specified index. Return whether the list changed as a result.
	 * java.util.List#addAll(java.lang.Iterable iterable)
	 *
	 * @author mqfdy
	 * @param <E>
	 *            the element type
	 * @param list
	 *            the list
	 * @param index
	 *            the index
	 * @param iterable
	 *            the iterable
	 * @param size
	 *            the size
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	public static <E> boolean addAll(List<? super E> list, int index, Iterable<E> iterable, int size) {
		return addAll(list, index, iterable.iterator(), size);
	}

	/**
	 * Add all the elements returned by the specified iterator to the specified
	 * list at the specified index. Return whether the list changed as a result.
	 * java.util.List#addAll(java.util.Iterator iterator)
	 *
	 * @author mqfdy
	 * @param <E>
	 *            the element type
	 * @param list
	 *            the list
	 * @param index
	 *            the index
	 * @param iterator
	 *            the iterator
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	public static <E> boolean addAll(List<? super E> list, int index, Iterator<? extends E> iterator) {
		return (iterator.hasNext()) ? list.addAll(index, list(iterator)) : false;
	}

	/**
	 * Add all the elements returned by the specified iterator to the specified
	 * list at the specified index. Return whether the list changed as a result.
	 * java.util.List#addAll(java.util.Iterator iterator)
	 *
	 * @author mqfdy
	 * @param <E>
	 *            the element type
	 * @param list
	 *            the list
	 * @param index
	 *            the index
	 * @param iterator
	 *            the iterator
	 * @param size
	 *            the size
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	public static <E> boolean addAll(List<? super E> list, int index, Iterator<? extends E> iterator, int size) {
		return (iterator.hasNext()) ? list.addAll(index, list(iterator, size)) : false;
	}

	/**
	 * Add all the elements in the specified array to the specified list at the
	 * specified index. Return whether the list changed as a result.
	 * java.util.List#addAll(Object[] array)
	 *
	 * @author mqfdy
	 * @param <E>
	 *            the element type
	 * @param list
	 *            the list
	 * @param index
	 *            the index
	 * @param array
	 *            the array
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	public static <E> boolean addAll(List<? super E> list, int index, E[] array) {
		return (array.length == 0) ? false : list.addAll(index, Arrays.asList(array));
	}

	/**
	 * Return a new array that contains the elements in the specified array
	 * followed by the elements in the specified collection.
	 * java.util.Arrays#addAll(Object[] array, java.util.Collection c)
	 *
	 * @author mqfdy
	 * @param <E>
	 *            the element type
	 * @param array
	 *            the array
	 * @param collection
	 *            the collection
	 * @return the e[]
	 * @Date 2018-09-03 09:00
	 */
	public static <E> E[] addAll(E[] array, Collection<? extends E> collection) {
		int size = collection.size();
		return (size == 0) ? array : addAll(array, collection, size);
	}

	/**
	 * assume collection is non-empty
	 */
	private static <E> E[] addAll_(E[] array, Collection<? extends E> collection) {
		return addAll(array, collection, collection.size());
	}

	/**
	 * assume collection is non-empty
	 */
	private static <E> E[] addAll(E[] array, Collection<? extends E> collection, int collectionSize) {
		int len = array.length;
		E[] result = newArray(array, len + collectionSize);
		if (len > 0) {
			System.arraycopy(array, 0, result, 0, len);
		}
		int i = len;
		for (E item : collection) {
			result[i++] = item;
		}
		return result;
	}

	/**
	 * Return a new array that contains the elements in the specified array
	 * followed by the elements in the specified iterable.
	 * java.util.Arrays#addAll(Object[] array, java.lang.Iterable iterable)
	 *
	 * @author mqfdy
	 * @param <E>
	 *            the element type
	 * @param array
	 *            the array
	 * @param iterable
	 *            the iterable
	 * @return the e[]
	 * @Date 2018-09-03 09:00
	 */
	public static <E> E[] addAll(E[] array, Iterable<? extends E> iterable) {
		return addAll(array, iterable.iterator());
	}

	/**
	 * Return a new array that contains the elements in the specified array
	 * followed by the elements in the specified iterable.
	 * java.util.Arrays#addAll(Object[] array, java.lang.Iterable iterable)
	 *
	 * @author mqfdy
	 * @param <E>
	 *            the element type
	 * @param array
	 *            the array
	 * @param iterable
	 *            the iterable
	 * @param size
	 *            the size
	 * @return the e[]
	 * @Date 2018-09-03 09:00
	 */
	public static <E> E[] addAll(E[] array, Iterable<? extends E> iterable, int size) {
		return addAll(array, iterable.iterator(), size);
	}

	/**
	 * Return a new array that contains the elements in the specified array
	 * followed by the elements in the specified iterator.
	 * java.util.Arrays#addAll(Object[] array, java.util.Iterator iterator)
	 *
	 * @author mqfdy
	 * @param <E>
	 *            the element type
	 * @param array
	 *            the array
	 * @param iterator
	 *            the iterator
	 * @return the e[]
	 * @Date 2018-09-03 09:00
	 */
	public static <E> E[] addAll(E[] array, Iterator<? extends E> iterator) {
		return (iterator.hasNext()) ? addAll_(array, list(iterator)) : array;
	}

	/**
	 * Return a new array that contains the elements in the specified array
	 * followed by the elements in the specified iterator.
	 * java.util.Arrays#addAll(Object[] array, java.util.Iterator iterator)
	 *
	 * @author mqfdy
	 * @param <E>
	 *            the element type
	 * @param array
	 *            the array
	 * @param iterator
	 *            the iterator
	 * @param size
	 *            the size
	 * @return the e[]
	 * @Date 2018-09-03 09:00
	 */
	public static <E> E[] addAll(E[] array, Iterator<? extends E> iterator, int size) {
		return (iterator.hasNext()) ? addAll_(array, list(iterator, size)) : array;
	}

	/**
	 * Return a new array that contains the elements in the specified array 1
	 * followed by the elements in the specified array 2.
	 * java.util.Arrays#addAll(Object[] array1, Object[] array2)
	 *
	 * @author mqfdy
	 * @param <E>
	 *            the element type
	 * @param array1
	 *            the array 1
	 * @param array2
	 *            the array 2
	 * @return the e[]
	 * @Date 2018-09-03 09:00
	 */
	public static <E> E[] addAll(E[] array1, E[] array2) {
		int array2Length = array2.length;
		return (array2Length == 0) ? array1 : addAll(array1, array2, array2Length);
	}

	/**
	 * assume array2Length > 0
	 */
	private static <E> E[] addAll(E[] array1, E[] array2, int array2Length) {
		int array1Length = array1.length;
		return (array1Length == 0) ? array2 : addAll(array1, array2, array1Length, array2Length);
	}

	/**
	 * assume array1Length > 0 and array2Length > 0
	 */
	private static <E> E[] addAll(E[] array1, E[] array2, int array1Length, int array2Length) {
		E[] result = newArray(array1, array1Length + array2Length);
		System.arraycopy(array1, 0, result, 0, array1Length);
		System.arraycopy(array2, 0, result, array1Length, array2Length);
		return result;
	}

	/**
	 * Return a new array that contains the elements in the first specified
	 * array with the objects in the second specified array added at the
	 * specified index. java.util.Arrays#add(Object[] array1, int index,
	 * Object[] array2)
	 *
	 * @author mqfdy
	 * @param <E>
	 *            the element type
	 * @param array1
	 *            the array 1
	 * @param index
	 *            the index
	 * @param array2
	 *            the array 2
	 * @return the e[]
	 * @Date 2018-09-03 09:00
	 */
	public static <E> E[] addAll(E[] array1, int index, E[] array2) {
		int array2Length = array2.length;
		return (array2Length == 0) ? array1 : addAll(array1, index, array2, array2Length);
	}

	/**
	 * assume array2Length > 0
	 */
	private static <E> E[] addAll(E[] array1, int index, E[] array2, int array2Length) {
		int array1Length = array1.length;
		return (index == array1Length) ?  // array2 added to end of array1
				(array1Length == 0) ? array2 : addAll(array1, array2, array1Length, array2Length)
			:
				addAll(array1, index, array2, array1Length, array2Length);
	}

	/**
	 * assume array1Length > 0 and array2Length > 0
	 */
	private static <E> E[] addAll(E[] array1, int index, E[] array2, int array1Length, int array2Length) {
		E[] result = newArray(array1, array1Length + array2Length);
		System.arraycopy(array1, 0, result, 0, index);
		System.arraycopy(array2, 0, result, index, array2Length);
		System.arraycopy(array1, index, result, index + array2Length, array1Length - index);
		return result;
	}

	/**
	 * Return a new array that contains the elements in the specified array with
	 * the elements in the specified collection inserted at the specified index.
	 * java.util.Arrays#addAll(Object[] array, int index, java.util.Collection
	 * c)
	 *
	 * @author mqfdy
	 * @param <E>
	 *            the element type
	 * @param array
	 *            the array
	 * @param index
	 *            the index
	 * @param collection
	 *            the collection
	 * @return the e[]
	 * @Date 2018-09-03 09:00
	 */
	public static <E> E[] addAll(E[] array, int index, Collection<? extends E> collection) {
		int size = collection.size();
		return (size == 0) ? array : addAll(array, index, collection, size);
	}

	/**
	 * assume collection is non-empty
	 */
	private static <E> E[] addAll_(E[] array, int index, Collection<? extends E> collection) {
		return addAll(array, index, collection, collection.size());
	}

	/**
	 * assume collection is non-empty
	 */
	private static <E> E[] addAll(E[] array, int index, Collection<? extends E> collection, int collectionSize) {
		int arrayLength = array.length;
		E[] result = newArray(array, arrayLength + collectionSize);
		if ((arrayLength == 0) && (index == 0)) {
			return collection.toArray(result);
		}
		System.arraycopy(array, 0, result, 0, index);
		int i = index;
		for (E item : collection) {
			result[i++] = item;
		}
		System.arraycopy(array, index, result, index + collectionSize, arrayLength - index);
		return result;
	}

	/**
	 * Return a new array that contains the elements in the specified array with
	 * the elements in the specified iterable inserted at the specified index.
	 * java.util.Arrays#addAll(Object[] array, int index, java.lang.Iterable
	 * iterable)
	 *
	 * @author mqfdy
	 * @param <E>
	 *            the element type
	 * @param array
	 *            the array
	 * @param index
	 *            the index
	 * @param iterable
	 *            the iterable
	 * @return the e[]
	 * @Date 2018-09-03 09:00
	 */
	public static <E> E[] addAll(E[] array, int index, Iterable<? extends E> iterable) {
		return addAll(array, index, iterable.iterator());
	}

	/**
	 * Return a new array that contains the elements in the specified array with
	 * the elements in the specified iterable inserted at the specified index.
	 * java.util.Arrays#addAll(Object[] array, int index, java.lang.Iterable
	 * iterable)
	 *
	 * @author mqfdy
	 * @param <E>
	 *            the element type
	 * @param array
	 *            the array
	 * @param index
	 *            the index
	 * @param iterable
	 *            the iterable
	 * @param size
	 *            the size
	 * @return the e[]
	 * @Date 2018-09-03 09:00
	 */
	public static <E> E[] addAll(E[] array, int index, Iterable<? extends E> iterable, int size) {
		return addAll(array, index, iterable.iterator(), size);
	}

	/**
	 * Return a new array that contains the elements in the specified array with
	 * the elements in the specified iterator inserted at the specified index.
	 * java.util.Arrays#addAll(Object[] array, int index, java.util.Iterator
	 * iterator)
	 *
	 * @author mqfdy
	 * @param <E>
	 *            the element type
	 * @param array
	 *            the array
	 * @param index
	 *            the index
	 * @param iterator
	 *            the iterator
	 * @return the e[]
	 * @Date 2018-09-03 09:00
	 */
	public static <E> E[] addAll(E[] array, int index, Iterator<? extends E> iterator) {
		return (iterator.hasNext()) ? addAll_(array, index, list(iterator)) : array;
	}

	/**
	 * Return a new array that contains the elements in the specified array with
	 * the elements in the specified iterator inserted at the specified index.
	 * java.util.Arrays#addAll(Object[] array, int index, java.util.Iterator
	 * iterator)
	 *
	 * @author mqfdy
	 * @param <E>
	 *            the element type
	 * @param array
	 *            the array
	 * @param index
	 *            the index
	 * @param iterator
	 *            the iterator
	 * @param size
	 *            the size
	 * @return the e[]
	 * @Date 2018-09-03 09:00
	 */
	public static <E> E[] addAll(E[] array, int index, Iterator<? extends E> iterator, int size) {
		return (iterator.hasNext()) ? addAll_(array, index, list(iterator, size)) : array;
	}

	/**
	 * Return a new array that contains the elements in the specified array 1
	 * followed by the elements in the specified array 2.
	 * java.util.Arrays#addAll(char[] array1, char[] array2)
	 *
	 * @author mqfdy
	 * @param array1
	 *            the array 1
	 * @param array2
	 *            the array 2
	 * @return the char[]
	 * @Date 2018-09-03 09:00
	 */
	public static char[] addAll(char[] array1, char[] array2) {
		int array2Length = array2.length;
		return (array2Length == 0) ? array1 : addAll(array1, array2, array2Length);
	}

	/**
	 * assume array2Length > 0
	 */
	private static char[] addAll(char[] array1, char[] array2, int array2Length) {
		int array1Length = array1.length;
		return (array1Length == 0) ? array2 : addAll(array1, array2, array1Length, array2Length);
	}

	/**
	 * assume array1Length > 0 and array2Length > 0
	 */
	private static char[] addAll(char[] array1, char[] array2, int array1Length, int array2Length) {
		char[] result = new char[array1Length + array2Length];
		System.arraycopy(array1, 0, result, 0, array1Length);
		System.arraycopy(array2, 0, result, array1Length, array2Length);
		return result;
	}

	/**
	 * Return a new array that contains the elements in the first specified
	 * array with the objects in the second specified array added at the
	 * specified index. java.util.Arrays#add(char[] array1, int index, char[]
	 * array2)
	 *
	 * @author mqfdy
	 * @param array1
	 *            the array 1
	 * @param index
	 *            the index
	 * @param array2
	 *            the array 2
	 * @return the char[]
	 * @Date 2018-09-03 09:00
	 */
	public static char[] addAll(char[] array1, int index, char[] array2) {
		int array2Length = array2.length;
		return (array2Length == 0) ? array1 : addAll(array1, index, array2, array2Length);
	}

	/**
	 * assume array2Length > 0
	 */
	private static char[] addAll(char[] array1, int index, char[] array2, int array2Length) {
		int array1Length = array1.length;
		return (index == array1Length) ?  // array2 added to end of array1
				(array1Length == 0) ? array2 : addAll(array1, array2, array1Length, array2Length)
			:
				addAll(array1, index, array2, array1Length, array2Length);
	}

	/**
	 * assume array1Length > 0 and array2Length > 0
	 */
	private static char[] addAll(char[] array1, int index, char[] array2, int array1Length, int array2Length) {
		char[] result = new char[array1Length + array2Length];
		System.arraycopy(array1, 0, result, 0, index);
		System.arraycopy(array2, 0, result, index, array2Length);
		System.arraycopy(array1, index, result, index + array2Length, array1Length - index);
		return result;
	}

	/**
	 * Return a new array that contains the elements in the specified array 1
	 * followed by the elements in the specified array 2.
	 * java.util.Arrays#addAll(int[] array1, int[] array2)
	 *
	 * @author mqfdy
	 * @param array1
	 *            the array 1
	 * @param array2
	 *            the array 2
	 * @return the int[]
	 * @Date 2018-09-03 09:00
	 */
	public static int[] addAll(int[] array1, int[] array2) {
		int array2Length = array2.length;
		return (array2Length == 0) ? array1 : addAll(array1, array2, array2Length);
	}

	/**
	 * assume array2Length > 0
	 */
	private static int[] addAll(int[] array1, int[] array2, int array2Length) {
		int array1Length = array1.length;
		return (array1Length == 0) ? array2 : addAll(array1, array2, array1Length, array2Length);
	}

	/**
	 * assume array1Length > 0 and array2Length > 0
	 */
	private static int[] addAll(int[] array1, int[] array2, int array1Length, int array2Length) {
		int[] result = new int[array1Length + array2Length];
		System.arraycopy(array1, 0, result, 0, array1Length);
		System.arraycopy(array2, 0, result, array1Length, array2Length);
		return result;
	}

	/**
	 * Return a new array that contains the elements in the first specified
	 * array with the objects in the second specified array added at the
	 * specified index. java.util.Arrays#add(int[] array1, int index, int[]
	 * array2)
	 *
	 * @author mqfdy
	 * @param array1
	 *            the array 1
	 * @param index
	 *            the index
	 * @param array2
	 *            the array 2
	 * @return the int[]
	 * @Date 2018-09-03 09:00
	 */
	public static int[] addAll(int[] array1, int index, int[] array2) {
		int array2Length = array2.length;
		return (array2Length == 0) ? array1 : addAll(array1, index, array2, array2Length);
	}

	/**
	 * assume array2Length > 0
	 */
	private static int[] addAll(int[] array1, int index, int[] array2, int array2Length) {
		int array1Length = array1.length;
		return (index == array1Length) ?  // array2 added to end of array1
				(array1Length == 0) ? array2 : addAll(array1, array2, array1Length, array2Length)
			:
				addAll(array1, index, array2, array1Length, array2Length);
	}

	/**
	 * assume array1Length > 0 and array2Length > 0
	 */
	private static int[] addAll(int[] array1, int index, int[] array2, int array1Length, int array2Length) {
		int[] result = new int[array1Length + array2Length];
		System.arraycopy(array1, 0, result, 0, index);
		System.arraycopy(array2, 0, result, index, array2Length);
		System.arraycopy(array1, index, result, index + array2Length, array1Length - index);
		return result;
	}

	/**
	 * Return an array corresponding to the specified iterable.
	 *
	 * @param iterable
	 *            the iterable
	 * @return the object[]
	 * @see java.util.Collection#toArray() java.lang.Iterable#toArray()
	 */
	public static Object[] array(Iterable<?> iterable) {
		return array(iterable.iterator());
	}

	/**
	 * Return an array corresponding to the specified iterable.
	 *
	 * @param iterable
	 *            the iterable
	 * @param size
	 *            the size
	 * @return the object[]
	 * @see java.util.Collection#toArray() java.lang.Iterable#toArray()
	 */
	public static Object[] array(Iterable<?> iterable, int size) {
		return array(iterable.iterator(), size);
	}

	/**
	 * Return an array corresponding to the specified iterable; the runtime type
	 * of the returned array is that of the specified array. If the collection
	 * fits in the specified array, it is returned therein. Otherwise, a new
	 * array is allocated with the runtime type of the specified array and the
	 * size of this collection.
	 *
	 * @param <E>
	 *            the element type
	 * @param iterable
	 *            the iterable
	 * @param array
	 *            the array
	 * @return the e[]
	 * @see java.util.Collection#toArray(java.lang.Object[])
	 *      java.lang.Iterable#toArray(Object[])
	 */
	public static <E> E[] array(Iterable<? extends E> iterable, E[] array) {
		return array(iterable.iterator(), array);
	}

	/**
	 * Return an array corresponding to the specified iterable; the runtime type
	 * of the returned array is that of the specified array. If the collection
	 * fits in the specified array, it is returned therein. Otherwise, a new
	 * array is allocated with the runtime type of the specified array and the
	 * size of this collection.
	 *
	 * @param <E>
	 *            the element type
	 * @param iterable
	 *            the iterable
	 * @param size
	 *            the size
	 * @param array
	 *            the array
	 * @return the e[]
	 * @see java.util.Collection#toArray(java.lang.Object[])
	 *      java.lang.Iterable#toArray(Object[])
	 */
	public static <E> E[] array(Iterable<? extends E> iterable, int size, E[] array) {
		return array(iterable.iterator(), size, array);
	}

	/**
	 * Return an array corresponding to the specified iterator.
	 *
	 * @param iterator
	 *            the iterator
	 * @return the object[]
	 * @see java.util.Collection#toArray() java.util.Iterator#toArray()
	 */
	public static Object[] array(Iterator<?> iterator) {
		return (iterator.hasNext()) ? list(iterator).toArray() : EMPTY_OBJECT_ARRAY;
	}
	private static final Object[] EMPTY_OBJECT_ARRAY = new Object[0];

	/**
	 * Return an array corresponding to the specified iterator.
	 *
	 * @param iterator
	 *            the iterator
	 * @param size
	 *            the size
	 * @return the object[]
	 * @see java.util.Collection#toArray() java.util.Iterator#toArray()
	 */
	public static Object[] array(Iterator<?> iterator, int size) {
		return (iterator.hasNext()) ? list(iterator, size).toArray() : EMPTY_OBJECT_ARRAY;
	}

	/**
	 * Return an array corresponding to the specified iterator; the runtime type
	 * of the returned array is that of the specified array. If the collection
	 * fits in the specified array, it is returned therein. Otherwise, a new
	 * array is allocated with the runtime type of the specified array and the
	 * size of this collection.
	 *
	 * @param <E>
	 *            the element type
	 * @param iterator
	 *            the iterator
	 * @param array
	 *            the array
	 * @return the e[]
	 * @see java.util.Collection#toArray(java.lang.Object[])
	 *      java.util.Iterator#toArray(Object[])
	 */
	public static <E> E[] array(Iterator<? extends E> iterator, E[] array) {
		return (iterator.hasNext()) ? list(iterator).toArray(array) : newArray(array, 0);
	}

	/**
	 * Return an array corresponding to the specified iterator; the runtime type
	 * of the returned array is that of the specified array. If the collection
	 * fits in the specified array, it is returned therein. Otherwise, a new
	 * array is allocated with the runtime type of the specified array and the
	 * size of this collection.
	 *
	 * @param <E>
	 *            the element type
	 * @param iterator
	 *            the iterator
	 * @param size
	 *            the size
	 * @param array
	 *            the array
	 * @return the e[]
	 * @see java.util.Collection#toArray(java.lang.Object[])
	 *      java.util.Iterator#toArray(Object[])
	 */
	public static <E> E[] array(Iterator<? extends E> iterator, int size, E[] array) {
		return (iterator.hasNext()) ? list(iterator, size).toArray(array) : newArray(array, 0);
	}

	/**
	 * Return a bag corresponding to the specified enumeration.
	 * HashBag(java.util.Enumeration enumeration)
	 *
	 * @author mqfdy
	 * @param <E>
	 *            the element type
	 * @param enumeration
	 *            the enumeration
	 * @return the bag
	 * @Date 2018-09-03 09:00
	 */
	public static <E> Bag<E> bag(Enumeration<? extends E> enumeration) {
		return (enumeration.hasMoreElements()) ?
				bag(enumeration, new HashBag<E>())
			:
				Bag.Empty.<E>instance();
	}

	/**
	 * Return a bag corresponding to the specified enumeration.
	 * HashBag(java.util.Enumeration enumeration)
	 *
	 * @author mqfdy
	 * @param <E>
	 *            the element type
	 * @param enumeration
	 *            the enumeration
	 * @param size
	 *            the size
	 * @return the bag
	 * @Date 2018-09-03 09:00
	 */
	public static <E> Bag<E> bag(Enumeration<? extends E> enumeration, int size) {
		return (enumeration.hasMoreElements()) ?
				bag(enumeration, new HashBag<E>(size))
			:
				Bag.Empty.<E>instance();
	}

	private static <E> Bag<E> bag(Enumeration<? extends E> enumeration, HashBag<E> bag) {
		while (enumeration.hasMoreElements()) {
			bag.add(enumeration.nextElement());
		}
		return bag;
	}

	/**
	 * Return a bag corresponding to the specified iterable.
	 * HashBag(java.lang.Iterable iterable)
	 *
	 * @author mqfdy
	 * @param <E>
	 *            the element type
	 * @param iterable
	 *            the iterable
	 * @return the bag
	 * @Date 2018-09-03 09:00
	 */
	public static <E> Bag<E> bag(Iterable<? extends E> iterable) {
		return bag(iterable.iterator());
	}

	/**
	 * Return a bag corresponding to the specified iterable.
	 * HashBag(java.lang.Iterable iterable)
	 *
	 * @author mqfdy
	 * @param <E>
	 *            the element type
	 * @param iterable
	 *            the iterable
	 * @param size
	 *            the size
	 * @return the bag
	 * @Date 2018-09-03 09:00
	 */
	public static <E> Bag<E> bag(Iterable<? extends E> iterable, int size) {
		return bag(iterable.iterator(), size);
	}

	/**
	 * Return a bag corresponding to the specified iterator.
	 * HashBag(java.util.Iterator iterator)
	 *
	 * @author mqfdy
	 * @param <E>
	 *            the element type
	 * @param iterator
	 *            the iterator
	 * @return the bag
	 * @Date 2018-09-03 09:00
	 */
	public static <E> Bag<E> bag(Iterator<? extends E> iterator) {
		return (iterator.hasNext()) ?
				bag(iterator, new HashBag<E>())
			:
				Bag.Empty.<E>instance();
	}

	/**
	 * Return a bag corresponding to the specified iterator.
	 * HashBag(java.util.Iterator iterator)
	 *
	 * @author mqfdy
	 * @param <E>
	 *            the element type
	 * @param iterator
	 *            the iterator
	 * @param size
	 *            the size
	 * @return the bag
	 * @Date 2018-09-03 09:00
	 */
	public static <E> Bag<E> bag(Iterator<? extends E> iterator, int size) {
		return (iterator.hasNext()) ?
				bag(iterator, new HashBag<E>(size))
			:
				Bag.Empty.<E>instance();
	}

	private static <E> Bag<E> bag(Iterator<? extends E> iterator, HashBag<E> bag) {
		while (iterator.hasNext()) {
			bag.add(iterator.next());
		}
		return bag;
	}

	/**
	 * Return a bag corresponding to the specified array. HashBag(Object[]
	 * array)
	 *
	 * @author mqfdy
	 * @param <E>
	 *            the element type
	 * @param array
	 *            the array
	 * @return the bag
	 * @Date 2018-09-03 09:00
	 */
	public static <E> Bag<E> bag(E... array) {
		int len = array.length;
		if (len == 0) {
			return Bag.Empty.<E>instance();
		}
		Bag<E> bag = new HashBag<E>(len);
		for (E item : array) {
			bag.add(item);
		}
		return bag;
	}

	/**
	 * Return a collection corresponding to the specified enumeration.
	 *
	 * @author mqfdy
	 * @param <E>
	 *            the element type
	 * @param enumeration
	 *            the enumeration
	 * @return the collection
	 * @Date 2018-09-03 09:00
	 */
	public static <E> Collection<E> collection(Enumeration<? extends E> enumeration) {
		return bag(enumeration);
	}

	/**
	 * Return a collection corresponding to the specified enumeration.
	 *
	 * @author mqfdy
	 * @param <E>
	 *            the element type
	 * @param enumeration
	 *            the enumeration
	 * @param size
	 *            the size
	 * @return the collection
	 * @Date 2018-09-03 09:00
	 */
	public static <E> Collection<E> collection(Enumeration<? extends E> enumeration, int size) {
		return bag(enumeration, size);
	}

	/**
	 * Return a collection corresponding to the specified iterable.
	 *
	 * @author mqfdy
	 * @param <E>
	 *            the element type
	 * @param iterable
	 *            the iterable
	 * @return the collection
	 * @Date 2018-09-03 09:00
	 */
	public static <E> Collection<E> collection(Iterable<? extends E> iterable) {
		return collection(iterable.iterator());
	}

	/**
	 * Return a collection corresponding to the specified iterable.
	 *
	 * @author mqfdy
	 * @param <E>
	 *            the element type
	 * @param iterable
	 *            the iterable
	 * @param size
	 *            the size
	 * @return the collection
	 * @Date 2018-09-03 09:00
	 */
	public static <E> Collection<E> collection(Iterable<? extends E> iterable, int size) {
		return collection(iterable.iterator(), size);
	}

	/**
	 * Return a collection corresponding to the specified iterator.
	 *
	 * @author mqfdy
	 * @param <E>
	 *            the element type
	 * @param iterator
	 *            the iterator
	 * @return the collection
	 * @Date 2018-09-03 09:00
	 */
	public static <E> Collection<E> collection(Iterator<? extends E> iterator) {
		return bag(iterator);
	}

	/**
	 * Return a collection corresponding to the specified iterator.
	 *
	 * @author mqfdy
	 * @param <E>
	 *            the element type
	 * @param iterator
	 *            the iterator
	 * @param size
	 *            the size
	 * @return the collection
	 * @Date 2018-09-03 09:00
	 */
	public static <E> Collection<E> collection(Iterator<? extends E> iterator, int size) {
		return bag(iterator, size);
	}

	/**
	 * Return a collection corresponding to the specified array.
	 *
	 * @author mqfdy
	 * @param <E>
	 *            the element type
	 * @param array
	 *            the array
	 * @return the collection
	 * @Date 2018-09-03 09:00
	 */
	public static <E> Collection<E> collection(E... array) {
		return bag(array);
	}

	/**
	 * Return whether the specified enumeration contains the specified element.
	 * java.util.Enumeration#contains(Object o)
	 *
	 * @author mqfdy
	 * @param enumeration
	 *            the enumeration
	 * @param value
	 *            the value
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	public static boolean contains(Enumeration<?> enumeration, Object value) {
		if (value == null) {
			while (enumeration.hasMoreElements()) {
				if (enumeration.nextElement() == null) {
					return true;
				}
			}
		} else {
			while (enumeration.hasMoreElements()) {
				if (value.equals(enumeration.nextElement())) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Return whether the specified iterable contains the specified element.
	 * java.lang.Iterable#contains(Object o)
	 *
	 * @author mqfdy
	 * @param iterable
	 *            the iterable
	 * @param value
	 *            the value
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	public static boolean contains(Iterable<?> iterable, Object value) {
		return contains(iterable.iterator(), value);
	}

	/**
	 * Return whether the specified iterator contains the specified element.
	 * java.util.Iterator#contains(Object o)
	 *
	 * @author mqfdy
	 * @param iterator
	 *            the iterator
	 * @param value
	 *            the value
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	public static boolean contains(Iterator<?> iterator, Object value) {
		if (value == null) {
			while (iterator.hasNext()) {
				if (iterator.next() == null) {
					return true;
				}
			}
		} else {
			while (iterator.hasNext()) {
				if (value.equals(iterator.next())) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Return whether the specified array contains the specified element.
	 * java.util.Arrays#contains(Object[] array, Object o)
	 *
	 * @author mqfdy
	 * @param array
	 *            the array
	 * @param value
	 *            the value
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	public static boolean contains(Object[] array, Object value) {
		if (value == null) {
			for (int i = array.length; i-- > 0; ) {
				if (array[i] == null) {
					return true;
				}
			}
		} else {
			for (int i = array.length; i-- > 0; ) {
				if (value.equals(array[i])) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Return whether the specified array contains the specified element.
	 * java.util.Arrays#contains(char[] array, char value)
	 *
	 * @author mqfdy
	 * @param array
	 *            the array
	 * @param value
	 *            the value
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	public static boolean contains(char[] array, char value) {
		for (int i = array.length; i-- > 0; ) {
			if (array[i] == value) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Return whether the specified array contains the specified element.
	 * java.util.Arrays#contains(int[] array, int value)
	 *
	 * @author mqfdy
	 * @param array
	 *            the array
	 * @param value
	 *            the value
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	public static boolean contains(int[] array, int value) {
		for (int i = array.length; i-- > 0; ) {
			if (array[i] == value) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Return whether the specified collection contains all of the elements in
	 * the specified iterable.
	 * java.util.Collection#containsAll(java.lang.Iterable iterable)
	 *
	 * @author mqfdy
	 * @param collection
	 *            the collection
	 * @param iterable
	 *            the iterable
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	public static boolean containsAll(Collection<?> collection, Iterable<?> iterable) {
		return containsAll(collection, iterable.iterator());
	}

	/**
	 * Return whether the specified collection contains all of the elements in
	 * the specified iterator.
	 * java.util.Collection#containsAll(java.util.Iterator iterator)
	 *
	 * @author mqfdy
	 * @param collection
	 *            the collection
	 * @param iterator
	 *            the iterator
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	public static boolean containsAll(Collection<?> collection, Iterator<?> iterator) {
		while (iterator.hasNext()) {
			if ( ! collection.contains(iterator.next())) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Return whether the specified collection contains all of the elements in
	 * the specified array. java.util.Collection#containsAll(Object[] array)
	 *
	 * @author mqfdy
	 * @param collection
	 *            the collection
	 * @param array
	 *            the array
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	public static boolean containsAll(Collection<?> collection, Object[] array) {
		for (int i = array.length; i-- > 0; ) {
			if ( ! collection.contains(array[i])) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Return whether the specified iterable contains all of the elements in the
	 * specified collection. java.lang.Iterable#containsAll(java.util.Collection
	 * collection)
	 *
	 * @author mqfdy
	 * @param iterable
	 *            the iterable
	 * @param collection
	 *            the collection
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	public static boolean containsAll(Iterable<?> iterable, Collection<?> collection) {
		return containsAll(iterable.iterator(), collection);
	}

	/**
	 * Return whether the specified iterable contains all of the elements in the
	 * specified collection. java.lang.Iterable#containsAll(java.util.Collection
	 * collection)
	 *
	 * @author mqfdy
	 * @param iterable
	 *            the iterable
	 * @param size
	 *            the size
	 * @param collection
	 *            the collection
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	public static boolean containsAll(Iterable<?> iterable, int size, Collection<?> collection) {
		return containsAll(iterable.iterator(), size, collection);
	}

	/**
	 * Return whether the specified iterable 1 contains all of the elements in
	 * the specified iterable 2.
	 * java.lang.Iterable#containsAll(java.lang.Iterable iterable)
	 *
	 * @author mqfdy
	 * @param iterable1
	 *            the iterable 1
	 * @param iterable2
	 *            the iterable 2
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	public static boolean containsAll(Iterable<?> iterable1, Iterable<?> iterable2) {
		return containsAll(iterable1.iterator(), iterable2.iterator());
	}

	/**
	 * Return whether the specified iterable 1 contains all of the elements in
	 * the specified iterable 2.
	 * java.lang.Iterable#containsAll(java.lang.Iterable iterable)
	 *
	 * @author mqfdy
	 * @param iterable1
	 *            the iterable 1
	 * @param size
	 *            the size
	 * @param iterable2
	 *            the iterable 2
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	public static boolean containsAll(Iterable<?> iterable1, int size, Iterable<?> iterable2) {
		return containsAll(iterable1.iterator(), size, iterable2.iterator());
	}

	/**
	 * Return whether the specified iterable contains all of the elements in the
	 * specified iterator. java.lang.Iterable#containsAll(java.util.Iterator
	 * iterator)
	 *
	 * @author mqfdy
	 * @param iterable
	 *            the iterable
	 * @param iterator
	 *            the iterator
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	public static boolean containsAll(Iterable<?> iterable, Iterator<?> iterator) {
		return containsAll(iterable.iterator(), iterator);
	}

	/**
	 * Return whether the specified iterable contains all of the elements in the
	 * specified iterator. java.lang.Iterable#containsAll(java.util.Iterator
	 * iterator)
	 *
	 * @author mqfdy
	 * @param iterable
	 *            the iterable
	 * @param size
	 *            the size
	 * @param iterator
	 *            the iterator
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	public static boolean containsAll(Iterable<?> iterable, int size, Iterator<?> iterator) {
		return containsAll(iterable.iterator(), size, iterator);
	}

	/**
	 * Return whether the specified iterable contains all of the elements in the
	 * specified array. java.lang.Iterable#containsAll(Object[] array)
	 *
	 * @author mqfdy
	 * @param iterable
	 *            the iterable
	 * @param array
	 *            the array
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	public static boolean containsAll(Iterable<?> iterable, Object[] array) {
		return containsAll(iterable.iterator(), array);
	}

	/**
	 * Return whether the specified iterable contains all of the elements in the
	 * specified array. java.lang.Iterable#containsAll(Object[] array)
	 *
	 * @author mqfdy
	 * @param iterable
	 *            the iterable
	 * @param size
	 *            the size
	 * @param array
	 *            the array
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	public static boolean containsAll(Iterable<?> iterable, int size, Object[] array) {
		return containsAll(iterable.iterator(), size, array);
	}

	/**
	 * Return whether the specified iterator contains all of the elements in the
	 * specified collection. java.util.Iterator#containsAll(java.util.Collection
	 * collection)
	 *
	 * @author mqfdy
	 * @param iterator
	 *            the iterator
	 * @param collection
	 *            the collection
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	public static boolean containsAll(Iterator<?> iterator, Collection<?> collection) {
		return collection(iterator).containsAll(collection);
	}

	/**
	 * Return whether the specified iterator contains all of the elements in the
	 * specified collection. java.util.Iterator#containsAll(java.util.Collection
	 * collection)
	 *
	 * @author mqfdy
	 * @param iterator
	 *            the iterator
	 * @param size
	 *            the size
	 * @param collection
	 *            the collection
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	public static boolean containsAll(Iterator<?> iterator, int size, Collection<?> collection) {
		return collection(iterator, size).containsAll(collection);
	}

	/**
	 * Return whether the specified iterator contains all of the elements in the
	 * specified iterable. java.util.Iterator#containsAll(java.lang.Iterable
	 * iterable)
	 *
	 * @author mqfdy
	 * @param iterator
	 *            the iterator
	 * @param iterable
	 *            the iterable
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	public static boolean containsAll(Iterator<?> iterator, Iterable<?> iterable) {
		return containsAll(collection(iterator), iterable);
	}

	/**
	 * Return whether the specified iterator contains all of the elements in the
	 * specified iterable. java.util.Iterator#containsAll(java.lang.Iterable
	 * iterable)
	 *
	 * @author mqfdy
	 * @param iterator
	 *            the iterator
	 * @param size
	 *            the size
	 * @param iterable
	 *            the iterable
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	public static boolean containsAll(Iterator<?> iterator, int size, Iterable<?> iterable) {
		return containsAll(collection(iterator, size), iterable);
	}

	/**
	 * Return whether the specified iterator 1 contains all of the elements in
	 * the specified iterator 2.
	 * java.util.Iterator#containsAll(java.util.Iterator iterator)
	 *
	 * @author mqfdy
	 * @param iterator1
	 *            the iterator 1
	 * @param iterator2
	 *            the iterator 2
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	public static boolean containsAll(Iterator<?> iterator1, Iterator<?> iterator2) {
		return containsAll(collection(iterator1), iterator2);
	}

	/**
	 * Return whether the specified iterator 1 contains all of the elements in
	 * the specified iterator 2.
	 * java.util.Iterator#containsAll(java.util.Iterator iterator)
	 *
	 * @author mqfdy
	 * @param iterator1
	 *            the iterator 1
	 * @param size
	 *            the size
	 * @param iterator2
	 *            the iterator 2
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	public static boolean containsAll(Iterator<?> iterator1, int size, Iterator<?> iterator2) {
		return containsAll(collection(iterator1, size), iterator2);
	}

	/**
	 * Return whether the specified iterator contains all of the elements in the
	 * specified array. java.util.Iterator#containsAll(Object[] array)
	 *
	 * @author mqfdy
	 * @param iterator
	 *            the iterator
	 * @param array
	 *            the array
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	public static boolean containsAll(Iterator<?> iterator, Object[] array) {
		return containsAll(collection(iterator), array);
	}

	/**
	 * Return whether the specified iterator contains all of the elements in the
	 * specified array. java.util.Iterator#containsAll(Object[] array)
	 *
	 * @author mqfdy
	 * @param iterator
	 *            the iterator
	 * @param size
	 *            the size
	 * @param array
	 *            the array
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	public static boolean containsAll(Iterator<?> iterator, int size, Object[] array) {
		return containsAll(collection(iterator, size), array);
	}

	/**
	 * Return whether the specified array contains all of the elements in the
	 * specified collection. java.util.Arrays#containsAll(Object[] array,
	 * java.util.Collection collection)
	 *
	 * @author mqfdy
	 * @param array
	 *            the array
	 * @param collection
	 *            the collection
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	public static boolean containsAll(Object[] array, Collection<?> collection) {
		return containsAll(array, collection.iterator());
	}

	/**
	 * Return whether the specified array contains all of the elements in the
	 * specified iterable. java.util.Arrays#containsAll(Object[] array,
	 * java.lang.Iterable iterable)
	 *
	 * @author mqfdy
	 * @param array
	 *            the array
	 * @param iterable
	 *            the iterable
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	public static boolean containsAll(Object[] array, Iterable<?> iterable) {
		return containsAll(array, iterable.iterator());
	}

	/**
	 * Return whether the specified array contains all of the elements in the
	 * specified iterator. java.util.Arrays#containsAll(Object[] array,
	 * java.util.Iterator iterator)
	 *
	 * @author mqfdy
	 * @param array
	 *            the array
	 * @param iterator
	 *            the iterator
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	public static boolean containsAll(Object[] array, Iterator<?> iterator) {
		while (iterator.hasNext()) {
			if ( ! contains(array, iterator.next())) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Return whether the specified array 1 contains all of the elements in the
	 * specified array 2. java.util.Arrays#containsAll(Object[] array1, Object[]
	 * array2)
	 *
	 * @author mqfdy
	 * @param array1
	 *            the array 1
	 * @param array2
	 *            the array 2
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	public static boolean containsAll(Object[] array1, Object[] array2) {
		for (int i = array2.length; i-- > 0; ) {
			if ( ! contains(array1, array2[i])) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Return whether the specified array 1 contains all of the elements in the
	 * specified array 2. java.util.Arrays#containsAll(char[] array1, char[]
	 * array2)
	 *
	 * @author mqfdy
	 * @param array1
	 *            the array 1
	 * @param array2
	 *            the array 2
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	public static boolean containsAll(char[] array1, char[] array2) {
		for (int i = array2.length; i-- > 0; ) {
			if ( ! contains(array1, array2[i])) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Return whether the specified array 1 contains all of the elements in the
	 * specified array 2. java.util.Arrays#containsAll(int[] array1, int[]
	 * array2)
	 *
	 * @author mqfdy
	 * @param array1
	 *            the array 1
	 * @param array2
	 *            the array 2
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	public static boolean containsAll(int[] array1, int[] array2) {
		for (int i = array2.length; i-- > 0; ) {
			if ( ! contains(array1, array2[i])) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Return the index of the first elements in the specified arrays that are
	 * different, beginning at the end. If the arrays are identical, return -1.
	 * If the arrays are different sizes, return the index of the last element
	 * in the longer array. Use the elements' #equals() method to compare the
	 * elements.
	 *
	 * @author mqfdy
	 * @param array1
	 *            the array 1
	 * @param array2
	 *            the array 2
	 * @return the int
	 * @Date 2018-09-03 09:00
	 */
	public static int diffEnd(Object[] array1, Object[] array2) {
		return diffEnd(Arrays.asList(array1), Arrays.asList(array2));
	}

	/**
	 * Return the index of the first elements in the specified lists that are
	 * different, beginning at the end. If the lists are identical, return -1.
	 * If the lists are different sizes, return the index of the last element in
	 * the longer list. Use the elements' #equals() method to compare the
	 * elements.
	 *
	 * @author mqfdy
	 * @param list1
	 *            the list 1
	 * @param list2
	 *            the list 2
	 * @return the int
	 * @Date 2018-09-03 09:00
	 */
	public static int diffEnd(List<?> list1, List<?> list2) {
		int size1 = list1.size();
		int size2 = list2.size();
		if (size1 != size2) {
			return Math.max(size1, size2) - 1;
		}
		int end = size1 - 1;
		while (end > -1) {
			Object o = list1.get(end);
			if (o == null) {
				if (list2.get(end) == null) {
					end--;
				} else {
					return end;
				}
			} else {
				if (o.equals(list2.get(end))) {
					end--;
				} else {
					return end;
				}
			}
		}
		return end;
	}

	/**
	 * Return the range of elements in the specified arrays that are different.
	 * If the arrays are identical, return [size, -1]. Use the elements'
	 * #equals() method to compare the elements.
	 *
	 * @param array1
	 *            the array 1
	 * @param array2
	 *            the array 2
	 * @return the range
	 * @see #diffStart(Object[], Object[])
	 * @see #diffEnd(Object[], Object[])
	 */
	public static Range diffRange(Object[] array1, Object[] array2) {
		return diffRange(Arrays.asList(array1), Arrays.asList(array2));
	}

	/**
	 * Return the range of elements in the specified arrays that are different.
	 * If the arrays are identical, return [size, -1]. Use the elements'
	 * #equals() method to compare the elements.
	 *
	 * @param list1
	 *            the list 1
	 * @param list2
	 *            the list 2
	 * @return the range
	 * @see #diffStart(java.util.List, java.util.List)
	 * @see #diffEnd(java.util.List, java.util.List)
	 */
	public static Range diffRange(List<?> list1, List<?> list2) {
		int end = diffEnd(list1, list2);
		if (end == -1) {
			// the lists are identical, the start is the size of the two lists
			return new Range(list1.size(), end);
		}
		// the lists are different, calculate the start of the range
		return new Range(diffStart(list1, list2), end);
	}

	/**
	 * Return the index of the first elements in the specified arrays that are
	 * different. If the arrays are identical, return the size of the two arrays
	 * (i.e. one past the last index). If the arrays are different sizes and all
	 * the elements in the shorter array match their corresponding elements in
	 * the longer array, return the size of the shorter array (i.e. one past the
	 * last index of the shorter array). Use the elements' #equals() method to
	 * compare the elements.
	 *
	 * @author mqfdy
	 * @param array1
	 *            the array 1
	 * @param array2
	 *            the array 2
	 * @return the int
	 * @Date 2018-09-03 09:00
	 */
	public static int diffStart(Object[] array1, Object[] array2) {
		return diffStart(Arrays.asList(array1), Arrays.asList(array2));
	}

	/**
	 * Return the index of the first elements in the specified lists that are
	 * different. If the lists are identical, return the size of the two lists
	 * (i.e. one past the last index). If the lists are different sizes and all
	 * the elements in the shorter list match their corresponding elements in
	 * the longer list, return the size of the shorter list (i.e. one past the
	 * last index of the shorter list). Use the elements' #equals() method to
	 * compare the elements.
	 *
	 * @author mqfdy
	 * @param list1
	 *            the list 1
	 * @param list2
	 *            the list 2
	 * @return the int
	 * @Date 2018-09-03 09:00
	 */
	public static int diffStart(List<?> list1, List<?> list2) {
		int end = Math.min(list1.size(), list2.size());
		int start = 0;
		while (start < end) {
			Object o = list1.get(start);
			if (o == null) {
				if (list2.get(start) == null) {
					start++;
				} else {
					return start;
				}
			} else {
				if (o.equals(list2.get(start))) {
					start++;
				} else {
					return start;
				}
			}
		}
		return start;
	}

	/**
	 * Return whether the specified iterators return equal elements.
	 * java.util.Iterator#equals(java.util.Iterator iterator)
	 *
	 * @author mqfdy
	 * @param iterator1
	 *            the iterator 1
	 * @param iterator2
	 *            the iterator 2
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	public static boolean equals(Iterator<?> iterator1, Iterator<?> iterator2) {
		while (iterator1.hasNext() && iterator2.hasNext()) {
			Object o1 = iterator1.next();
			Object o2 = iterator2.next();
			if ( ! (o1 == null ? o2 == null : o1.equals(o2))) {
				return false;
			}
		}
		return ! (iterator1.hasNext() || iterator2.hasNext());
	}

	/**
	 * Return the element corresponding to the specified index in the specified
	 * iterator. java.util.ListIterator#get(int index)
	 *
	 * @author mqfdy
	 * @param <E>
	 *            the element type
	 * @param iterator
	 *            the iterator
	 * @param index
	 *            the index
	 * @return the e
	 * @Date 2018-09-03 09:00
	 */
	public static <E> E get(ListIterator<? extends E> iterator, int index) {
		while (iterator.hasNext()) {
			E next = iterator.next();
			if (iterator.previousIndex() == index) {
				return next;
			}
		}
		throw new IndexOutOfBoundsException(String.valueOf(index) + ':' + String.valueOf(iterator.previousIndex()));
	}

	/**
	 * Return whether the specified arrays contain the same elements.
	 * java.util.Arrays#identical(Object[] array1, Object[] array2)
	 *
	 * @author mqfdy
	 * @param array1
	 *            the array 1
	 * @param array2
	 *            the array 2
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	public static boolean identical(Object[] array1, Object[] array2) {
		if (array1 == array2) {
			return true;
		}
		if (array1 == null || array2 == null) {
			return false;
		}
		int length = array1.length;
		if (array2.length != length) {
			return false;
		}
		for (int i = length; i-- > 0; ) {
			if (array1[i] != array2[i]) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Return whether the specified iterators return the same elements.
	 * java.util.Iterator#identical(java.util.Iterator iterator)
	 *
	 * @author mqfdy
	 * @param iterator1
	 *            the iterator 1
	 * @param iterator2
	 *            the iterator 2
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	public static boolean identical(Iterator<?> iterator1, Iterator<?> iterator2) {
		while (iterator1.hasNext() && iterator2.hasNext()) {
			if (iterator1.next() != iterator2.next()) {
				return false;
			}
		}
		return ! (iterator1.hasNext() || iterator2.hasNext());
	}

	/**
	 * Return the index of the first elements in the specified arrays that are
	 * different, beginning at the end. If the arrays are identical, return -1.
	 * If the arrays are different sizes, return the index of the last element
	 * in the longer array. Use object identity to compare the elements.
	 *
	 * @author mqfdy
	 * @param array1
	 *            the array 1
	 * @param array2
	 *            the array 2
	 * @return the int
	 * @Date 2018-09-03 09:00
	 */
	public static int identityDiffEnd(Object[] array1, Object[] array2) {
		return identityDiffEnd(Arrays.asList(array1), Arrays.asList(array2));
	}

	/**
	 * Return the index of the first elements in the specified lists that are
	 * different, beginning at the end. If the lists are identical, return -1.
	 * If the lists are different sizes, return the index of the last element in
	 * the longer list. Use object identity to compare the elements.
	 *
	 * @author mqfdy
	 * @param list1
	 *            the list 1
	 * @param list2
	 *            the list 2
	 * @return the int
	 * @Date 2018-09-03 09:00
	 */
	public static int identityDiffEnd(List<?> list1, List<?> list2) {
		int size1 = list1.size();
		int size2 = list2.size();
		if (size1 != size2) {
			return Math.max(size1, size2) - 1;
		}
		int end = size1 - 1;
		while (end > -1) {
			if (list1.get(end) == list2.get(end)) {
				end--;
			} else {
				return end;
			}
		}
		return end;
	}

	/**
	 * Return the range of elements in the specified arrays that are different.
	 * If the arrays are identical, return [size, -1]. Use object identity to
	 * compare the elements.
	 *
	 * @param array1
	 *            the array 1
	 * @param array2
	 *            the array 2
	 * @return the range
	 * @see #identityDiffStart(Object[], Object[])
	 * @see #identityDiffEnd(Object[], Object[])
	 */
	public static Range identityDiffRange(Object[] array1, Object[] array2) {
		return identityDiffRange(Arrays.asList(array1), Arrays.asList(array2));
	}

	/**
	 * Return the range of elements in the specified arrays that are different.
	 * If the arrays are identical, return [size, -1]. Use object identity to
	 * compare the elements.
	 *
	 * @param list1
	 *            the list 1
	 * @param list2
	 *            the list 2
	 * @return the range
	 * @see #identityDiffStart(java.util.List, java.util.List)
	 * @see #identityDiffEnd(java.util.List, java.util.List)
	 */
	public static Range identityDiffRange(List<?> list1, List<?> list2) {
		int end = identityDiffEnd(list1, list2);
		if (end == -1) {
			// the lists are identical, the start is the size of the two lists
			return new Range(list1.size(), end);
		}
		// the lists are different, calculate the start of the range
		return new Range(identityDiffStart(list1, list2), end);
	}

	/**
	 * Return the index of the first elements in the specified arrays that are
	 * different. If the arrays are identical, return the size of the two arrays
	 * (i.e. one past the last index). If the arrays are different sizes and all
	 * the elements in the shorter array match their corresponding elements in
	 * the longer array, return the size of the shorter array (i.e. one past the
	 * last index of the shorter array). Use object identity to compare the
	 * elements.
	 *
	 * @author mqfdy
	 * @param array1
	 *            the array 1
	 * @param array2
	 *            the array 2
	 * @return the int
	 * @Date 2018-09-03 09:00
	 */
	public static int identityDiffStart(Object[] array1, Object[] array2) {
		return identityDiffStart(Arrays.asList(array1), Arrays.asList(array2));
	}

	/**
	 * Return the index of the first elements in the specified lists that are
	 * different. If the lists are identical, return the size of the two lists
	 * (i.e. one past the last index). If the lists are different sizes and all
	 * the elements in the shorter list match their corresponding elements in
	 * the longer list, return the size of the shorter list (i.e. one past the
	 * last index of the shorter list). Use object identity to compare the
	 * elements.
	 *
	 * @author mqfdy
	 * @param list1
	 *            the list 1
	 * @param list2
	 *            the list 2
	 * @return the int
	 * @Date 2018-09-03 09:00
	 */
	public static int identityDiffStart(List<?> list1, List<?> list2) {
		int end = Math.min(list1.size(), list2.size());
		int start = 0;
		while (start < end) {
			if (list1.get(start) == list2.get(start)) {
				start++;
			} else {
				return start;
			}
		}
		return start;
	}

	/**
	 * Return the index of the first occurrence of the specified element in the
	 * specified iterator, or return -1 if there is no such index.
	 * java.util.Iterator#indexOf(Object o)
	 *
	 * @author mqfdy
	 * @param iterator
	 *            the iterator
	 * @param value
	 *            the value
	 * @return the int
	 * @Date 2018-09-03 09:00
	 */
	public static int indexOf(Iterator<?> iterator, Object value) {
		if (value == null) {
			for (int i = 0; iterator.hasNext(); i++) {
				if (iterator.next() == null) {
					return i;
				}
			}
		} else {
			for (int i = 0; iterator.hasNext(); i++) {
				if (value.equals(iterator.next())) {
					return i;
				}
			}
		}
		return -1;
	}

	/**
	 * Return the index of the first occurrence of the specified element in the
	 * specified array, or return -1 if there is no such index.
	 * java.util.Arrays#indexOf(Object[] array, Object o)
	 *
	 * @author mqfdy
	 * @param array
	 *            the array
	 * @param value
	 *            the value
	 * @return the int
	 * @Date 2018-09-03 09:00
	 */
	public static int indexOf(Object[] array, Object value) {
		int len = array.length;
		if (value == null) {
			for (int i = 0; i < len; i++) {
				if (array[i] == null) {
					return i;
				}
			}
		} else {
			for (int i = 0; i < len; i++) {
				if (value.equals(array[i])) {
					return i;
				}
			}
		}
		return -1;
	}

	/**
	 * Return the index of the first occurrence of the specified element in the
	 * specified array, or return -1 if there is no such index.
	 * java.util.Arrays#indexOf(char[] array, char value)
	 *
	 * @author mqfdy
	 * @param array
	 *            the array
	 * @param value
	 *            the value
	 * @return the int
	 * @Date 2018-09-03 09:00
	 */
	public static int indexOf(char[] array, char value) {
		int len = array.length;
		for (int i = 0; i < len; i++) {
			if (array[i] == value) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Return the index of the first occurrence of the specified element in the
	 * specified array, or return -1 if there is no such index.
	 * java.util.Arrays#indexOf(int[] array, int value)
	 *
	 * @author mqfdy
	 * @param array
	 *            the array
	 * @param value
	 *            the value
	 * @return the int
	 * @Date 2018-09-03 09:00
	 */
	public static int indexOf(int[] array, int value) {
		int len = array.length;
		for (int i = 0; i < len; i++) {
			if (array[i] == value) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Return the maximum index of where the specified comparable object should
	 * be inserted into the specified sorted list and still keep the list
	 * sorted.
	 *
	 * @author mqfdy
	 * @param <E>
	 *            the element type
	 * @param sortedList
	 *            the sorted list
	 * @param value
	 *            the value
	 * @return the int
	 * @Date 2018-09-03 09:00
	 */
	public static <E extends Comparable<? super E>> int insertionIndexOf(List<E> sortedList, Comparable<E> value) {
		int len = sortedList.size();
		for (int i = 0; i < len; i++) {
			if (value.compareTo(sortedList.get(i)) < 0) {
				return i;
			}
		}
		return len;
	}

	/**
	 * Return the maximum index of where the specified object should be inserted
	 * into the specified sorted list and still keep the list sorted.
	 *
	 * @author mqfdy
	 * @param <E>
	 *            the element type
	 * @param sortedList
	 *            the sorted list
	 * @param value
	 *            the value
	 * @param comparator
	 *            the comparator
	 * @return the int
	 * @Date 2018-09-03 09:00
	 */
	public static <E> int insertionIndexOf(List<E> sortedList, E value, Comparator<? super E> comparator) {
		int len = sortedList.size();
		for (int i = 0; i < len; i++) {
			if (comparator.compare(value, sortedList.get(i)) < 0) {
				return i;
			}
		}
		return len;
	}

	/**
	 * Return the maximum index of where the specified comparable object should
	 * be inserted into the specified sorted array and still keep the array
	 * sorted.
	 *
	 * @author mqfdy
	 * @param <E>
	 *            the element type
	 * @param sortedArray
	 *            the sorted array
	 * @param value
	 *            the value
	 * @return the int
	 * @Date 2018-09-03 09:00
	 */
	public static <E extends Comparable<? super E>> int insertionIndexOf(E[] sortedArray, Comparable<E> value) {
		int len = sortedArray.length;
		for (int i = 0; i < len; i++) {
			if (value.compareTo(sortedArray[i]) < 0) {
				return i;
			}
		}
		return len;
	}

	/**
	 * Return the maximum index of where the specified comparable object should
	 * be inserted into the specified sorted array and still keep the array
	 * sorted.
	 *
	 * @author mqfdy
	 * @param <E>
	 *            the element type
	 * @param sortedArray
	 *            the sorted array
	 * @param value
	 *            the value
	 * @param comparator
	 *            the comparator
	 * @return the int
	 * @Date 2018-09-03 09:00
	 */
	public static <E> int insertionIndexOf(E[] sortedArray, E value, Comparator<? super E> comparator) {
		int len = sortedArray.length;
		for (int i = 0; i < len; i++) {
			if (comparator.compare(value, sortedArray[i]) < 0) {
				return i;
			}
		}
		return len;
	}

	/**
	 * Return a one-use Iterable for the Iterator given. Throws an
	 * IllegalStateException if iterable() is called more than once. As such,
	 * this utility should only be used in one-use situations, such as a "for"
	 * loop.
	 *
	 * @author mqfdy
	 * @param <E>
	 *            the element type
	 * @param iterator
	 *            the iterator
	 * @return the iterable
	 * @Date 2018-09-03 09:00
	 */
	public static <E> Iterable<E> iterable(Iterator<? extends E> iterator) {
		return new SingleUseIterable<E>(iterator);
	}

	/**
	 * Return an iterable on the elements in the specified array.
	 * java.util.Arrays#iterable(Object[] array)
	 *
	 * @author mqfdy
	 * @param <E>
	 *            the element type
	 * @param array
	 *            the array
	 * @return the iterable
	 * @Date 2018-09-03 09:00
	 */
	public static <E> Iterable<E> iterable(E... array) {
		return Arrays.asList(array);
	}

	/**
	 * Return an iterator on the elements in the specified array.
	 * java.util.Arrays#iterator(Object[] array)
	 *
	 * @author mqfdy
	 * @param <E>
	 *            the element type
	 * @param array
	 *            the array
	 * @return the iterator
	 * @Date 2018-09-03 09:00
	 */
	public static <E> Iterator<E> iterator(E... array) {
		return new ArrayIterator<E>(array);
	}

	/**
	 * Return the index of the last occurrence of the specified element in the
	 * specified iterator, or return -1 if there is no such index.
	 * java.util.Iterator#lastIndexOf(Object o)
	 *
	 * @author mqfdy
	 * @param iterator
	 *            the iterator
	 * @param value
	 *            the value
	 * @return the int
	 * @Date 2018-09-03 09:00
	 */
	public static int lastIndexOf(Iterator<?> iterator, Object value) {
		return (iterator.hasNext()) ? list(iterator).lastIndexOf(value) : -1;
	}

	/**
	 * Return the index of the last occurrence of the specified element in the
	 * specified iterator, or return -1 if there is no such index.
	 * java.util.Iterator#lastIndexOf(Object o)
	 *
	 * @author mqfdy
	 * @param iterator
	 *            the iterator
	 * @param size
	 *            the size
	 * @param value
	 *            the value
	 * @return the int
	 * @Date 2018-09-03 09:00
	 */
	public static int lastIndexOf(Iterator<?> iterator, int size, Object value) {
		return (iterator.hasNext()) ? list(iterator, size).lastIndexOf(value) : -1;
	}

	/**
	 * Return the index of the last occurrence of the specified element in the
	 * specified array, or return -1 if there is no such index.
	 * java.util.Arrays#lastIndexOf(Object[] array, Object o)
	 *
	 * @author mqfdy
	 * @param array
	 *            the array
	 * @param value
	 *            the value
	 * @return the int
	 * @Date 2018-09-03 09:00
	 */
	public static int lastIndexOf(Object[] array, Object value) {
		int len = array.length;
		if (value == null) {
			for (int i = len; i-- > 0; ) {
				if (array[i] == null) {
					return i;
				}
			}
		} else {
			for (int i = len; i-- > 0; ) {
				if (value.equals(array[i])) {
					return i;
				}
			}
		}
		return -1;
	}

	/**
	 * Return the index of the last occurrence of the specified element in the
	 * specified array, or return -1 if there is no such index.
	 * java.util.Arrays#lastIndexOf(char[] array, char value)
	 *
	 * @author mqfdy
	 * @param array
	 *            the array
	 * @param value
	 *            the value
	 * @return the int
	 * @Date 2018-09-03 09:00
	 */
	public static int lastIndexOf(char[] array, char value) {
		for (int i = array.length; i-- > 0; ) {
			if (array[i] == value) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Return the index of the last occurrence of the specified element in the
	 * specified array, or return -1 if there is no such index.
	 * java.util.Arrays#lastIndexOf(int[] array, int value)
	 *
	 * @author mqfdy
	 * @param array
	 *            the array
	 * @param value
	 *            the value
	 * @return the int
	 * @Date 2018-09-03 09:00
	 */
	public static int lastIndexOf(int[] array, int value) {
		for (int i = array.length; i-- > 0; ) {
			if (array[i] == value) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Return a list corresponding to the specified iterable.
	 * java.lang.Iterable#toList()
	 *
	 * @author mqfdy
	 * @param <E>
	 *            the element type
	 * @param iterable
	 *            the iterable
	 * @return the list
	 * @Date 2018-09-03 09:00
	 */
	public static <E> List<E> list(Iterable<? extends E> iterable) {
		return list(iterable.iterator());
	}

	/**
	 * Return a list corresponding to the specified iterable.
	 * java.lang.Iterable#toList()
	 *
	 * @author mqfdy
	 * @param <E>
	 *            the element type
	 * @param iterable
	 *            the iterable
	 * @param size
	 *            the size
	 * @return the list
	 * @Date 2018-09-03 09:00
	 */
	public static <E> List<E> list(Iterable<? extends E> iterable, int size) {
		return list(iterable.iterator(), size);
	}

	/**
	 * Return a list corresponding to the specified iterator.
	 * java.util.Iterator#toList()
	 *
	 * @author mqfdy
	 * @param <E>
	 *            the element type
	 * @param iterator
	 *            the iterator
	 * @return the list
	 * @Date 2018-09-03 09:00
	 */
	public static <E> List<E> list(Iterator<? extends E> iterator) {
		return (iterator.hasNext()) ? list(iterator, new ArrayList<E>()) : Collections.<E>emptyList();
	}

	/**
	 * Return a list corresponding to the specified iterator.
	 * java.util.Iterator#toList()
	 *
	 * @author mqfdy
	 * @param <E>
	 *            the element type
	 * @param iterator
	 *            the iterator
	 * @param size
	 *            the size
	 * @return the list
	 * @Date 2018-09-03 09:00
	 */
	public static <E> List<E> list(Iterator<? extends E> iterator, int size) {
		return (iterator.hasNext()) ? list(iterator, new ArrayList<E>(size)) : Collections.<E>emptyList();
	}

	private static <E> List<E> list(Iterator<? extends E> iterator, ArrayList<E> list) {
		while (iterator.hasNext()) {
			list.add(iterator.next());
		}
		return list;
	}

	/**
	 * Return a list corresponding to the specified array. Unlike
	 * java.util.Arrays.asList(Object[]), the list is modifiable and is not
	 * backed by the array.
	 *
	 * @author mqfdy
	 * @param <E>
	 *            the element type
	 * @param array
	 *            the array
	 * @return the list
	 * @Date 2018-09-03 09:00
	 */
	public static <E> List<E> list(E... array) {
		List<E> list = new ArrayList<E>(array.length);
		for (E item : array) {
			list.add(item);
		}
		return list;
	}

	/**
	 * Return a list iterator for the specified array.
	 * java.util.Arrays#listIterator(Object[] array)
	 *
	 * @author mqfdy
	 * @param <E>
	 *            the element type
	 * @param array
	 *            the array
	 * @return the list iterator
	 * @Date 2018-09-03 09:00
	 */
	public static <E> ListIterator<E> listIterator(E... array) {
		return listIterator(array, 0);
	}

	/**
	 * Return a list iterator for the specified array starting at the specified
	 * position in the array. java.util.Arrays#listIterator(Object[] array, int
	 * index)
	 *
	 * @author mqfdy
	 * @param <E>
	 *            the element type
	 * @param array
	 *            the array
	 * @param index
	 *            the index
	 * @return the list iterator
	 * @Date 2018-09-03 09:00
	 */
	public static <E> ListIterator<E> listIterator(E[] array, int index) {
		return Arrays.asList(array).listIterator(index);
	}

	/**
	 * Return the character from the specified array with the maximum value.
	 * java.util.Arrays#max(char[] array)
	 *
	 * @author mqfdy
	 * @param array
	 *            the array
	 * @return the char
	 * @Date 2018-09-03 09:00
	 */
	public static char max(char... array) {
		int len = array.length;
		if (len == 0) {
			throw new IndexOutOfBoundsException();
		}
		char max = array[0];
		// start at 1
		for (int i = 1; i < len; i++) {
			char next = array[i];
			if (next > max) {
				max = next;
			}
		}
		return max;
	}

	/**
	 * Return the integer from the specified array with the maximum value.
	 * java.util.Arrays#max(int[] array)
	 *
	 * @author mqfdy
	 * @param array
	 *            the array
	 * @return the int
	 * @Date 2018-09-03 09:00
	 */
	public static int max(int... array) {
		int len = array.length;
		if (len == 0) {
			throw new IndexOutOfBoundsException();
		}
		int max = array[0];
		// start at 1
		for (int i = 1; i < len; i++) {
			int next = array[i];
			if (next > max) {
				max = next;
			}
		}
		return max;
	}

	/**
	 * Return the character from the specified array with the minimum value.
	 * java.util.Arrays#min(char[] array)
	 *
	 * @author mqfdy
	 * @param array
	 *            the array
	 * @return the char
	 * @Date 2018-09-03 09:00
	 */
	public static char min(char... array) {
		int len = array.length;
		if (len == 0) {
			throw new IndexOutOfBoundsException();
		}
		char min = array[0];
		// start at 1
		for (int i = 1; i < len; i++) {
			char next = array[i];
			if (next < min) {
				min = next;
			}
		}
		return min;
	}

	/**
	 * Return the integer from the specified array with the minimum value.
	 * java.util.Arrays#min(int[] array)
	 *
	 * @author mqfdy
	 * @param array
	 *            the array
	 * @return the int
	 * @Date 2018-09-03 09:00
	 */
	public static int min(int... array) {
		int len = array.length;
		if (len == 0) {
			throw new IndexOutOfBoundsException();
		}
		int min = array[0];
		// start at 1
		for (int i = 1; i < len; i++) {
			int next = array[i];
			if (next < min) {
				min = next;
			}
		}
		return min;
	}

	/**
	 * Move an element from the specified source index to the specified target
	 * index. Return the altered array. java.util.Arrays#move(Object[] array,
	 * int targetIndex, int sourceIndex)
	 *
	 * @author mqfdy
	 * @param <E>
	 *            the element type
	 * @param array
	 *            the array
	 * @param targetIndex
	 *            the target index
	 * @param sourceIndex
	 *            the source index
	 * @return the e[]
	 * @Date 2018-09-03 09:00
	 */
	public static <E> E[] move(E[] array, int targetIndex, int sourceIndex) {
		return (targetIndex == sourceIndex) ? array : move_(array, targetIndex, sourceIndex);
	}

	/**
	 * assume targetIndex != sourceIndex
	 */
	private static <E> E[] move_(E[] array, int targetIndex, int sourceIndex) {
		E temp = array[sourceIndex];
		if (targetIndex < sourceIndex) {
			System.arraycopy(array, targetIndex, array, targetIndex + 1, sourceIndex - targetIndex);
		} else {
			System.arraycopy(array, sourceIndex + 1, array, sourceIndex, targetIndex - sourceIndex);
		}
		array[targetIndex] = temp;
		return array;
	}

	/**
	 * Move elements from the specified source index to the specified target
	 * index. Return the altered array. java.util.Arrays#move(Object[] array,
	 * int targetIndex, int sourceIndex, int length)
	 *
	 * @author mqfdy
	 * @param <E>
	 *            the element type
	 * @param array
	 *            the array
	 * @param targetIndex
	 *            the target index
	 * @param sourceIndex
	 *            the source index
	 * @param length
	 *            the length
	 * @return the e[]
	 * @Date 2018-09-03 09:00
	 */
	public static <E> E[] move(E[] array, int targetIndex, int sourceIndex, int length) {
		if ((targetIndex == sourceIndex) || (length == 0)) {
			return array;
		}
		if (length == 1) {
			return move_(array, targetIndex, sourceIndex);
		}
		E[] temp = newArray(array, length);
		System.arraycopy(array, sourceIndex, temp, 0, length);
		if (targetIndex < sourceIndex) {
			System.arraycopy(array, targetIndex, array, targetIndex + length, sourceIndex - targetIndex);
		} else {
			System.arraycopy(array, sourceIndex + length, array, sourceIndex, targetIndex - sourceIndex);
		}
		System.arraycopy(temp, 0, array, targetIndex, length);
		return array;
	}

	/**
	 * Move an element from the specified source index to the specified target
	 * index. Return the altered array. java.util.Arrays#move(int[] array, int
	 * targetIndex, int sourceIndex)
	 *
	 * @author mqfdy
	 * @param array
	 *            the array
	 * @param targetIndex
	 *            the target index
	 * @param sourceIndex
	 *            the source index
	 * @return the int[]
	 * @Date 2018-09-03 09:00
	 */
	public static int[] move(int[] array, int targetIndex, int sourceIndex) {
		return (targetIndex == sourceIndex) ? array : move_(array, targetIndex, sourceIndex);
	}

	/**
	 * assume targetIndex != sourceIndex
	 */
	private static int[] move_(int[] array, int targetIndex, int sourceIndex) {
		int temp = array[sourceIndex];
		if (targetIndex < sourceIndex) {
			System.arraycopy(array, targetIndex, array, targetIndex + 1, sourceIndex - targetIndex);
		} else {
			System.arraycopy(array, sourceIndex + 1, array, sourceIndex, targetIndex - sourceIndex);
		}
		array[targetIndex] = temp;
		return array;
	}

	/**
	 * Move elements from the specified source index to the specified target
	 * index. Return the altered array. java.util.Arrays#move(int[] array, int
	 * targetIndex, int sourceIndex, int length)
	 *
	 * @author mqfdy
	 * @param array
	 *            the array
	 * @param targetIndex
	 *            the target index
	 * @param sourceIndex
	 *            the source index
	 * @param length
	 *            the length
	 * @return the int[]
	 * @Date 2018-09-03 09:00
	 */
	public static int[] move(int[] array, int targetIndex, int sourceIndex, int length) {
		if ((targetIndex == sourceIndex) || (length == 0)) {
			return array;
		}
		if (length == 1) {
			return move_(array, targetIndex, sourceIndex);
		}
		int[] temp = new int[length];
		System.arraycopy(array, sourceIndex, temp, 0, length);
		if (targetIndex < sourceIndex) {
			System.arraycopy(array, targetIndex, array, targetIndex + length, sourceIndex - targetIndex);
		} else {
			System.arraycopy(array, sourceIndex + length, array, sourceIndex, targetIndex - sourceIndex);
		}
		System.arraycopy(temp, 0, array, targetIndex, length);
		return array;
	}

	/**
	 * Move an element from the specified source index to the specified target
	 * index. Return the altered array. java.util.Arrays#move(char[] array, int
	 * targetIndex, int sourceIndex)
	 *
	 * @author mqfdy
	 * @param array
	 *            the array
	 * @param targetIndex
	 *            the target index
	 * @param sourceIndex
	 *            the source index
	 * @return the char[]
	 * @Date 2018-09-03 09:00
	 */
	public static char[] move(char[] array, int targetIndex, int sourceIndex) {
		return (targetIndex == sourceIndex) ? array : move_(array, targetIndex, sourceIndex);
	}

	/**
	 * assume targetIndex != sourceIndex
	 */
	private static char[] move_(char[] array, int targetIndex, int sourceIndex) {
		char temp = array[sourceIndex];
		if (targetIndex < sourceIndex) {
			System.arraycopy(array, targetIndex, array, targetIndex + 1, sourceIndex - targetIndex);
		} else {
			System.arraycopy(array, sourceIndex + 1, array, sourceIndex, targetIndex - sourceIndex);
		}
		array[targetIndex] = temp;
		return array;
	}

	/**
	 * Move elements from the specified source index to the specified target
	 * index. Return the altered array. java.util.Arrays#move(char[] array, int
	 * targetIndex, int sourceIndex, int length)
	 *
	 * @author mqfdy
	 * @param array
	 *            the array
	 * @param targetIndex
	 *            the target index
	 * @param sourceIndex
	 *            the source index
	 * @param length
	 *            the length
	 * @return the char[]
	 * @Date 2018-09-03 09:00
	 */
	public static char[] move(char[] array, int targetIndex, int sourceIndex, int length) {
		if ((targetIndex == sourceIndex) || (length == 0)) {
			return array;
		}
		if (length == 1) {
			return move_(array, targetIndex, sourceIndex);
		}
		char[] temp = new char[length];
		System.arraycopy(array, sourceIndex, temp, 0, length);
		if (targetIndex < sourceIndex) {
			System.arraycopy(array, targetIndex, array, targetIndex + length, sourceIndex - targetIndex);
		} else {
			System.arraycopy(array, sourceIndex + length, array, sourceIndex, targetIndex - sourceIndex);
		}
		System.arraycopy(temp, 0, array, targetIndex, length);
		return array;
	}

	/**
	 * Move an element from the specified source index to the specified target
	 * index. Return the altered list. java.util.List#move(int targetIndex, int
	 * sourceIndex)
	 *
	 * @author mqfdy
	 * @param <E>
	 *            the element type
	 * @param list
	 *            the list
	 * @param targetIndex
	 *            the target index
	 * @param sourceIndex
	 *            the source index
	 * @return the list
	 * @Date 2018-09-03 09:00
	 */
	public static <E> List<E> move(List<E> list, int targetIndex, int sourceIndex) {
		return (targetIndex == sourceIndex) ? list : move_(list, targetIndex, sourceIndex);
	}

	/**
	 * assume targetIndex != sourceIndex
	 */
	private static <E> List<E> move_(List<E> list, int targetIndex, int sourceIndex) {
		if (list instanceof RandomAccess) {
			// move elements, leaving the list in place
			E temp = list.get(sourceIndex);
			if (targetIndex < sourceIndex) {
				for (int i = sourceIndex; i-- > targetIndex; ) {
					list.set(i + 1, list.get(i));
				}
			} else {
				for (int i = sourceIndex; i < targetIndex; i++) {
					list.set(i, list.get(i + 1));
				}
			}
			list.set(targetIndex, temp);
		} else {
			// remove the element and re-add it at the target index
			list.add(targetIndex, list.remove(sourceIndex));
		}
		return list;
	}

	/**
	 * Move elements from the specified source index to the specified target
	 * index. Return the altered list. java.util.List#move(int targetIndex, int
	 * sourceIndex, int length)
	 *
	 * @author mqfdy
	 * @param <E>
	 *            the element type
	 * @param list
	 *            the list
	 * @param targetIndex
	 *            the target index
	 * @param sourceIndex
	 *            the source index
	 * @param length
	 *            the length
	 * @return the list
	 * @Date 2018-09-03 09:00
	 */
	public static <E> List<E> move(List<E> list, int targetIndex, int sourceIndex, int length) {
		if ((targetIndex == sourceIndex) || (length == 0)) {
			return list;
		}
		if (length == 1) {
			return move_(list, targetIndex, sourceIndex);
		}
		if (list instanceof RandomAccess) {
			// move elements, leaving the list in place
			ArrayList<E> temp = new ArrayList<E>(list.subList(sourceIndex, sourceIndex + length));
			if (targetIndex < sourceIndex) {
				for (int i = sourceIndex; i-- > targetIndex; ) {
					list.set(i + length, list.get(i));
				}
			} else {
				for (int i = sourceIndex; i < targetIndex; i++) {
					list.set(i, list.get(i + length));
				}
			}
			for (int i = 0; i < length; i++) {
				list.set(targetIndex + i, temp.get(i));
			}
		} else {
			// remove the elements and re-add them at the target index
			list.addAll(targetIndex, removeElementsAtIndex(list, sourceIndex, length));
		}
		return list;
	}

	/**
	 * Replace all occurrences of the specified old value with the specified new
	 * value. java.util.Arrays#replaceAll(Object[] array, Object oldValue,
	 * Object newValue)
	 *
	 * @author mqfdy
	 * @param <E>
	 *            the element type
	 * @param array
	 *            the array
	 * @param oldValue
	 *            the old value
	 * @param newValue
	 *            the new value
	 * @return the e[]
	 * @Date 2018-09-03 09:00
	 */
	public static <E> E[] replaceAll(E[] array, Object oldValue, E newValue) {
		if (oldValue == null) {
			for (int i = array.length; i-- > 0; ) {
				if (array[i] == null) {
					array[i] = newValue;
				}
			}
		} else {
			for (int i = array.length; i-- > 0; ) {
				if (oldValue.equals(array[i])) {
					array[i] = newValue;
				}
			}
		}
		return array;
	}

	/**
	 * Replace all occurrences of the specified old value with the specified new
	 * value. java.util.Arrays#replaceAll(int[] array, int oldValue, int
	 * newValue)
	 *
	 * @author mqfdy
	 * @param array
	 *            the array
	 * @param oldValue
	 *            the old value
	 * @param newValue
	 *            the new value
	 * @return the int[]
	 * @Date 2018-09-03 09:00
	 */
	public static int[] replaceAll(int[] array, int oldValue, int newValue) {
		for (int i = array.length; i-- > 0; ) {
			if (array[i] == oldValue) {
				array[i] = newValue;
			}
		}
		return array;
	}

	/**
	 * Replace all occurrences of the specified old value with the specified new
	 * value. java.util.Arrays#replaceAll(char[] array, char oldValue, char
	 * newValue)
	 *
	 * @author mqfdy
	 * @param array
	 *            the array
	 * @param oldValue
	 *            the old value
	 * @param newValue
	 *            the new value
	 * @return the char[]
	 * @Date 2018-09-03 09:00
	 */
	public static char[] replaceAll(char[] array, char oldValue, char newValue) {
		for (int i = array.length; i-- > 0; ) {
			if (array[i] == oldValue) {
				array[i] = newValue;
			}
		}
		return array;
	}

	/**
	 * Return a new array that contains the elements in the specified array with
	 * the specified element removed. java.util.Arrays#remove(Object[] array,
	 * Object value)
	 *
	 * @author mqfdy
	 * @param <E>
	 *            the element type
	 * @param array
	 *            the array
	 * @param value
	 *            the value
	 * @return the e[]
	 * @Date 2018-09-03 09:00
	 */
	public static <E> E[] remove(E[] array, Object value) {
		return removeElementAtIndex(array, indexOf(array, value));
	}

	/**
	 * Return a new array that contains the elements in the specified array with
	 * the specified element removed. java.util.Arrays#remove(char[] array, char
	 * value)
	 *
	 * @author mqfdy
	 * @param array
	 *            the array
	 * @param value
	 *            the value
	 * @return the char[]
	 * @Date 2018-09-03 09:00
	 */
	public static char[] remove(char[] array, char value) {
		return removeElementAtIndex(array, indexOf(array, value));
	}

	/**
	 * Return a new array that contains the elements in the specified array with
	 * the specified element removed. java.util.Arrays#remove(int[] array, int
	 * value)
	 *
	 * @author mqfdy
	 * @param array
	 *            the array
	 * @param value
	 *            the value
	 * @return the int[]
	 * @Date 2018-09-03 09:00
	 */
	public static int[] remove(int[] array, int value) {
		return removeElementAtIndex(array, indexOf(array, value));
	}

	/**
	 * Remove all the elements returned by the specified iterable from the
	 * specified collection. Return whether the collection changed as a result.
	 * java.util.Collection#removeAll(java.lang.Iterable iterable)
	 *
	 * @author mqfdy
	 * @param collection
	 *            the collection
	 * @param iterable
	 *            the iterable
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	public static boolean removeAll(Collection<?> collection, Iterable<?> iterable) {
		return removeAll(collection, iterable.iterator());
	}

	/**
	 * Remove all the elements returned by the specified iterable from the
	 * specified collection. Return whether the collection changed as a result.
	 * java.util.Collection#removeAll(java.lang.Iterable iterable)
	 *
	 * @author mqfdy
	 * @param collection
	 *            the collection
	 * @param iterable
	 *            the iterable
	 * @param size
	 *            the size
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	public static boolean removeAll(Collection<?> collection, Iterable<?> iterable, int size) {
		return removeAll(collection, iterable.iterator(), size);
	}

	/**
	 * Remove all the elements returned by the specified iterator from the
	 * specified collection. Return whether the collection changed as a result.
	 * java.util.Collection#removeAll(java.util.Iterator iterator)
	 *
	 * @author mqfdy
	 * @param collection
	 *            the collection
	 * @param iterator
	 *            the iterator
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	public static boolean removeAll(Collection<?> collection, Iterator<?> iterator) {
		return (iterator.hasNext()) ? collection.removeAll(set(iterator)) : false;
	}

	/**
	 * Remove all the elements returned by the specified iterator from the
	 * specified collection. Return whether the collection changed as a result.
	 * java.util.Collection#removeAll(java.util.Iterator iterator)
	 *
	 * @author mqfdy
	 * @param collection
	 *            the collection
	 * @param iterator
	 *            the iterator
	 * @param size
	 *            the size
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	public static boolean removeAll(Collection<?> collection, Iterator<?> iterator, int size) {
		return (iterator.hasNext()) ? collection.removeAll(set(iterator, size)) : false;
	}

	/**
	 * Remove all the elements in the specified array from the specified
	 * collection. Return whether the collection changed as a result.
	 * java.util.Collection#removeAll(Object[] array)
	 *
	 * @author mqfdy
	 * @param collection
	 *            the collection
	 * @param array
	 *            the array
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	public static boolean removeAll(Collection<?> collection, Object[] array) {
		return collection.removeAll(set(array));
	}

	/**
	 * Remove from the specified array all the elements in the specified
	 * iterable and return the result. java.util.Arrays#removeAll(Object[]
	 * array, Iterable iterable)
	 *
	 * @author mqfdy
	 * @param <E>
	 *            the element type
	 * @param array
	 *            the array
	 * @param iterable
	 *            the iterable
	 * @return the e[]
	 * @Date 2018-09-03 09:00
	 */
	public static <E> E[] removeAll(E[] array, Iterable<?> iterable) {
		return removeAll(array, iterable.iterator());
	}

	/**
	 * Remove from the specified array all the elements in the specified
	 * iterable and return the result. java.util.Arrays#removeAll(Object[]
	 * array, Iterable iterable)
	 *
	 * @author mqfdy
	 * @param <E>
	 *            the element type
	 * @param array
	 *            the array
	 * @param iterable
	 *            the iterable
	 * @param size
	 *            the size
	 * @return the e[]
	 * @Date 2018-09-03 09:00
	 */
	public static <E> E[] removeAll(E[] array, Iterable<?> iterable, int size) {
		return removeAll(array, iterable.iterator(), size);
	}

	/**
	 * Remove from the specified array all the elements in the specified
	 * iterator and return the result. java.util.Arrays#removeAll(Object[]
	 * array, Iterator iterator)
	 *
	 * @author mqfdy
	 * @param <E>
	 *            the element type
	 * @param array
	 *            the array
	 * @param iterator
	 *            the iterator
	 * @return the e[]
	 * @Date 2018-09-03 09:00
	 */
	public static <E> E[] removeAll(E[] array, Iterator<?> iterator) {
		return (iterator.hasNext()) ? removeAll_(array, set(iterator)) : array;
	}

	/**
	 * Remove from the specified array all the elements in the specified
	 * iterator and return the result. java.util.Arrays#removeAll(Object[]
	 * array, Iterator iterator)
	 *
	 * @author mqfdy
	 * @param <E>
	 *            the element type
	 * @param array
	 *            the array
	 * @param iterator
	 *            the iterator
	 * @param size
	 *            the size
	 * @return the e[]
	 * @Date 2018-09-03 09:00
	 */
	public static <E> E[] removeAll(E[] array, Iterator<?> iterator, int size) {
		return (iterator.hasNext()) ? removeAll_(array, set(iterator, size)) : array;
	}

	/**
	 * Remove from the specified array all the elements in the specified
	 * collection and return the result. java.util.Arrays#removeAll(Object[]
	 * array, Collection collection)
	 *
	 * @author mqfdy
	 * @param <E>
	 *            the element type
	 * @param array
	 *            the array
	 * @param collection
	 *            the collection
	 * @return the e[]
	 * @Date 2018-09-03 09:00
	 */
	public static <E> E[] removeAll(E[] array, Collection<?> collection) {
		return (collection.isEmpty()) ? array : removeAll_(array, collection);
	}

	/**
	 * assume the collection is non-empty
	 */
	private static <E> E[] removeAll_(E[] array, Collection<?> collection) {
		int arrayLength = array.length;
		return (arrayLength == 0) ? array : removeAll(array, collection, arrayLength);
	}

	/**
	 * assume the collection is non-empty and arrayLength > 0
	 */
	private static <E> E[] removeAll(E[] array, Collection<?> collection, int arrayLength) {
		int[] indices = new int[arrayLength];
		int j = 0;
		for (int i = 0; i < arrayLength; i++) {
			if ( ! collection.contains(array[i])) {
				indices[j++] = i;
			}
		}
		if (j == arrayLength) {
			return array;  // nothing was removed
		}
		E[] result = newArray(array, j);
		int resultLength = result.length;
		for (int i = 0; i < resultLength; i++) {
			result[i] = array[indices[i]];
		}
		return result;
	}

	/**
	 * Remove from the first specified array all the elements in the second
	 * specified array and return the result.
	 * java.util.Arrays#removeAll(Object[] array1, Object[] array2)
	 *
	 * @author mqfdy
	 * @param <E>
	 *            the element type
	 * @param array1
	 *            the array 1
	 * @param array2
	 *            the array 2
	 * @return the e[]
	 * @Date 2018-09-03 09:00
	 */
	public static <E> E[] removeAll(E[] array1, Object[] array2) {
		// convert to a bag to take advantage of hashed look-up
		return (array2.length == 0) ? array1 : removeAll_(array1, set(array2));
	}

	/**
	 * Remove from the first specified array all the elements in the second
	 * specified array and return the result. java.util.Arrays#removeAll(char[]
	 * array1, char[] array2)
	 *
	 * @author mqfdy
	 * @param array1
	 *            the array 1
	 * @param array2
	 *            the array 2
	 * @return the char[]
	 * @Date 2018-09-03 09:00
	 */
	public static char[] removeAll(char[] array1, char[] array2) {
		if (array2.length == 0) {
			return array1;
		}
		int array1Length = array1.length;
		if (array1Length == 0) {
			return array1;
		}
		int[] indices = new int[array1Length];
		int j = 0;
		for (int i = 0; i < array1Length; i++) {
			if ( ! contains(array2, array1[i])) {
				indices[j++] = i;
			}
		}
		if (j == array1Length) {
			return array1;  // nothing was removed
		}
		char[] result = new char[j];
		int resultLength = result.length;
		for (int i = 0; i < resultLength; i++) {
			result[i] = array1[indices[i]];
		}
		return result;
	}

	/**
	 * Remove from the first specified array all the elements in the second
	 * specified array and return the result. java.util.Arrays#removeAll(int[]
	 * array1, int[] array2)
	 *
	 * @author mqfdy
	 * @param array1
	 *            the array 1
	 * @param array2
	 *            the array 2
	 * @return the int[]
	 * @Date 2018-09-03 09:00
	 */
	public static int[] removeAll(int[] array1, int[] array2) {
		if (array2.length == 0) {
			return array1;
		}
		int array1Length = array1.length;
		if (array1Length == 0) {
			return array1;
		}
		int[] indices = new int[array1Length];
		int j = 0;
		for (int i = 0; i < array1Length; i++) {
			if ( ! contains(array2, array1[i])) {
				indices[j++] = i;
			}
		}
		if (j == array1Length) {
			return array1;  // nothing was removed
		}
		int[] result = new int[j];
		int resultLength = result.length;
		for (int i = 0; i < resultLength; i++) {
			result[i] = array1[indices[i]];
		}
		return result;
	}

	/**
	 * Remove all occurrences of the specified element from the specified
	 * collection. Return whether the collection changed as a result.
	 * java.util.Collection#removeAllOccurrences(Object value)
	 *
	 * @author mqfdy
	 * @param collection
	 *            the collection
	 * @param value
	 *            the value
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	public static boolean removeAllOccurrences(Collection<?> collection, Object value) {
		boolean modified = false;
		Iterator<?> stream = collection.iterator();
		if (value == null) {
			while (stream.hasNext()) {
				if (stream.next() == null) {
					stream.remove();
					modified = true;
				}
			}
		} else {
			while (stream.hasNext()) {
				if (value.equals(stream.next())) {
					stream.remove();
					modified = true;
				}
			}
		}
		return modified;
	}

	/**
	 * Remove from the specified array all occurrences of the specified element
	 * and return the result. java.util.Arrays#removeAllOccurrences(Object[]
	 * array, Object value)
	 *
	 * @author mqfdy
	 * @param <E>
	 *            the element type
	 * @param array
	 *            the array
	 * @param value
	 *            the value
	 * @return the e[]
	 * @Date 2018-09-03 09:00
	 */
	public static <E> E[] removeAllOccurrences(E[] array, Object value) {
		int arrayLength = array.length;
		if (arrayLength == 0) {
			return array;
		}
		int[] indices = new int[arrayLength];
		int j = 0;
		if (value == null) {
			for (int i = arrayLength; i-- > 0; ) {
				if (array[i] != null) {
					indices[j++] = i;
				}
			}
		} else {
			for (int i = array.length; i-- > 0; ) {
				if ( ! value.equals(array[i])) {
					indices[j++] = i;
				}
			}
		}
		if (j == arrayLength) {
			return array;  // nothing was removed
		}
		E[] result = newArray(array, j);
		int resultLength = result.length;
		for (int i = 0; i < resultLength; i++) {
			result[i] = array[indices[i]];
		}
		return result;
	}

	/**
	 * Remove from the specified array all occurrences of the specified element
	 * and return the result. java.util.Arrays#removeAllOccurrences(char[]
	 * array, char value)
	 *
	 * @author mqfdy
	 * @param array
	 *            the array
	 * @param value
	 *            the value
	 * @return the char[]
	 * @Date 2018-09-03 09:00
	 */
	public static char[] removeAllOccurrences(char[] array, char value) {
		int arrayLength = array.length;
		if (arrayLength == 0) {
			return array;
		}
		int[] indices = new int[arrayLength];
		int j = 0;
		for (int i = arrayLength; i-- > 0; ) {
			if (array[i] != value) {
				indices[j++] = i;
			}
		}
		if (j == arrayLength) {
			return array;  // nothing was removed
		}
		char[] result = new char[j];
		int resultLength = result.length;
		for (int i = 0; i < resultLength; i++) {
			result[i] = array[indices[i]];
		}
		return result;
	}

	/**
	 * Remove from the specified array all occurrences of the specified element
	 * and return the result. java.util.Arrays#removeAllOccurrences(int[] array,
	 * int value)
	 *
	 * @author mqfdy
	 * @param array
	 *            the array
	 * @param value
	 *            the value
	 * @return the int[]
	 * @Date 2018-09-03 09:00
	 */
	public static int[] removeAllOccurrences(int[] array, int value) {
		int arrayLength = array.length;
		if (arrayLength == 0) {
			return array;
		}
		int[] indices = new int[arrayLength];
		int j = 0;
		for (int i = arrayLength; i-- > 0; ) {
			if (array[i] != value) {
				indices[j++] = i;
			}
		}
		if (j == arrayLength) {
			return array;  // nothing was removed
		}
		int[] result = new int[j];
		int resultLength = result.length;
		for (int i = 0; i < resultLength; i++) {
			result[i] = array[indices[i]];
		}
		return result;
	}

	/**
	 * Return a new array that contains the elements in the specified array with
	 * the specified element removed.
	 * java.util.Arrays#removeElementAtIndex(Object[] array, int index)
	 *
	 * @author mqfdy
	 * @param <E>
	 *            the element type
	 * @param array
	 *            the array
	 * @param index
	 *            the index
	 * @return the e[]
	 * @Date 2018-09-03 09:00
	 */
	public static <E> E[] removeElementAtIndex(E[] array, int index) {
		return removeElementsAtIndex(array, index, 1);
	}

	/**
	 * Return a new array that contains the elements in the specified array with
	 * the specified element removed.
	 * java.util.Arrays#removeElementAtIndex(char[] array, int index)
	 *
	 * @author mqfdy
	 * @param array
	 *            the array
	 * @param index
	 *            the index
	 * @return the char[]
	 * @Date 2018-09-03 09:00
	 */
	public static char[] removeElementAtIndex(char[] array, int index) {
		return removeElementsAtIndex(array, index, 1);
	}

	/**
	 * Return a new array that contains the elements in the specified array with
	 * the specified element removed.
	 * java.util.Arrays#removeElementAtIndex(int[] array, int index)
	 *
	 * @author mqfdy
	 * @param array
	 *            the array
	 * @param index
	 *            the index
	 * @return the int[]
	 * @Date 2018-09-03 09:00
	 */
	public static int[] removeElementAtIndex(int[] array, int index) {
		return removeElementsAtIndex(array, index, 1);
	}

	/**
	 * Remove the elements at the specified index. Return the removed elements.
	 * java.util.List#remove(int index, int length)
	 *
	 * @author mqfdy
	 * @param <E>
	 *            the element type
	 * @param list
	 *            the list
	 * @param index
	 *            the index
	 * @param length
	 *            the length
	 * @return the list
	 * @Date 2018-09-03 09:00
	 */
	public static <E> List<E> removeElementsAtIndex(List<E> list, int index, int length) {
		List<E> subList = list.subList(index, index + length);
		ArrayList<E> result = new ArrayList<E>(subList);
		subList.clear();
		return result;
	}

	/**
	 * Return a new array that contains the elements in the specified array with
	 * the specified elements removed.
	 * java.util.Arrays#removeElementsAtIndex(Object[] array, int index, int
	 * length)
	 *
	 * @author mqfdy
	 * @param <E>
	 *            the element type
	 * @param array
	 *            the array
	 * @param index
	 *            the index
	 * @param length
	 *            the length
	 * @return the e[]
	 * @Date 2018-09-03 09:00
	 */
	public static <E> E[] removeElementsAtIndex(E[] array, int index, int length) {
		int arrayLength = array.length;
		int newLength = arrayLength - length;
		E[] result = newArray(array, newLength);
		if ((newLength == 0) && (index == 0)) {
			return result;  // performance tweak
		}
		System.arraycopy(array, 0, result, 0, index);
		System.arraycopy(array, index + length, result, index, newLength - index);
		return result;
	}

	/**
	 * Return a new array that contains the elements in the specified array with
	 * the specified elements removed.
	 * java.util.Arrays#removeElementAtIndex(char[] array, int index, int
	 * length)
	 *
	 * @author mqfdy
	 * @param array
	 *            the array
	 * @param index
	 *            the index
	 * @param length
	 *            the length
	 * @return the char[]
	 * @Date 2018-09-03 09:00
	 */
	public static char[] removeElementsAtIndex(char[] array, int index, int length) {
		int arrayLength = array.length;
		int newLength = arrayLength - length;
		if ((newLength == 0) && (index == 0)) {
			return EMPTY_CHAR_ARRAY;  // performance tweak
		}
		char[] result = new char[newLength];
		System.arraycopy(array, 0, result, 0, index);
		System.arraycopy(array, index + length, result, index, newLength - index);
		return result;
	}
	private static final char[] EMPTY_CHAR_ARRAY = new char[0];

	/**
	 * Return a new array that contains the elements in the specified array with
	 * the specified elements removed.
	 * java.util.Arrays#removeElementAtIndex(int[] array, int index, int length)
	 *
	 * @author mqfdy
	 * @param array
	 *            the array
	 * @param index
	 *            the index
	 * @param length
	 *            the length
	 * @return the int[]
	 * @Date 2018-09-03 09:00
	 */
	public static int[] removeElementsAtIndex(int[] array, int index, int length) {
		int arrayLength = array.length;
		int newLength = arrayLength - length;
		if ((newLength == 0) && (index == 0)) {
			return EMPTY_INT_ARRAY;  // performance tweak
		}
		int[] result = new int[newLength];
		System.arraycopy(array, 0, result, 0, index);
		System.arraycopy(array, index + length, result, index, newLength - index);
		return result;
	}
	private static final int[] EMPTY_INT_ARRAY = new int[0];

	/**
	 * Remove any duplicate elements from the specified array, while maintaining
	 * the order.
	 *
	 * @author mqfdy
	 * @param <E>
	 *            the element type
	 * @param array
	 *            the array
	 * @return the e[]
	 * @Date 2018-09-03 09:00
	 */
	public static <E> E[] removeDuplicateElements(E... array) {
		int len = array.length;
		if ((len == 0) || (len == 1)) {
			return array;
		}
		List<E> list = removeDuplicateElements(Arrays.asList(array), len);
		return list.toArray(newArray(array, list.size()));
	}

	/**
	 * Remove any duplicate elements from the specified list, while maintaining
	 * the order.
	 *
	 * @author mqfdy
	 * @param <E>
	 *            the element type
	 * @param list
	 *            the list
	 * @return the list
	 * @Date 2018-09-03 09:00
	 */
	public static <E> List<E> removeDuplicateElements(List<E> list) {
		int size = list.size();
		if ((size == 0) || (size == 1)) {
			return list;
		}
		return removeDuplicateElements(list, size);
	}

	/**
	 * assume list is non-empty
	 */
	private static <E> List<E> removeDuplicateElements(List<E> list, int size) {
		List<E> result = new ArrayList<E>(size);
		Set<E> set = new HashSet<E>(size);		// take advantage of hashed look-up
		for (E item : list) {
			if (set.add(item)) {
				result.add(item);
			}
		}
		return result;
	}

	/**
	 * Retain only the elements in the specified iterable in the specified
	 * collection. Return whether the collection changed as a result.
	 * java.util.Collection#retainAll(java.lang.Iterable iterable)
	 *
	 * @author mqfdy
	 * @param collection
	 *            the collection
	 * @param iterable
	 *            the iterable
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	public static boolean retainAll(Collection<?> collection, Iterable<?> iterable) {
		return retainAll(collection, iterable.iterator());
	}

	/**
	 * Retain only the elements in the specified iterable in the specified
	 * collection. Return whether the collection changed as a result.
	 * java.util.Collection#retainAll(java.lang.Iterable iterable)
	 *
	 * @author mqfdy
	 * @param collection
	 *            the collection
	 * @param iterable
	 *            the iterable
	 * @param size
	 *            the size
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	public static boolean retainAll(Collection<?> collection, Iterable<?> iterable, int size) {
		return retainAll(collection, iterable.iterator(), size);
	}

	/**
	 * Retain only the elements in the specified iterator in the specified
	 * collection. Return whether the collection changed as a result.
	 * java.util.Collection#retainAll(java.util.Iterator iterator)
	 *
	 * @author mqfdy
	 * @param collection
	 *            the collection
	 * @param iterator
	 *            the iterator
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	public static boolean retainAll(Collection<?> collection, Iterator<?> iterator) {
		if (iterator.hasNext()) {
			return collection.retainAll(set(iterator));
		}
		if (collection.isEmpty()) {
			return false;
		}
		collection.clear();
		return true;
	}

	/**
	 * Retain only the elements in the specified iterator in the specified
	 * collection. Return whether the collection changed as a result.
	 * java.util.Collection#retainAll(java.util.Iterator iterator)
	 *
	 * @author mqfdy
	 * @param collection
	 *            the collection
	 * @param iterator
	 *            the iterator
	 * @param size
	 *            the size
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	public static boolean retainAll(Collection<?> collection, Iterator<?> iterator, int size) {
		if (iterator.hasNext()) {
			return collection.retainAll(set(iterator, size));
		}
		if (collection.isEmpty()) {
			return false;
		}
		collection.clear();
		return true;
	}

	/**
	 * Retain only the elements in the specified array in the specified
	 * collection. Return whether the collection changed as a result.
	 * java.util.Collection#retainAll(Object[] array)
	 *
	 * @author mqfdy
	 * @param collection
	 *            the collection
	 * @param array
	 *            the array
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	public static boolean retainAll(Collection<?> collection, Object[] array) {
		if (array.length > 0) {
			return collection.retainAll(set(array));
		}
		if (collection.isEmpty()) {
			return false;
		}
		collection.clear();
		return true;
	}

	/**
	 * Retain in the specified array all the elements in the specified iterable
	 * and return the result. java.util.Arrays#retainAll(Object[] array,
	 * Iterable iterable)
	 *
	 * @author mqfdy
	 * @param <E>
	 *            the element type
	 * @param array
	 *            the array
	 * @param iterable
	 *            the iterable
	 * @return the e[]
	 * @Date 2018-09-03 09:00
	 */
	public static <E> E[] retainAll(E[] array, Iterable<?> iterable) {
		int arrayLength = array.length;
		return (arrayLength == 0) ? array : retainAll(array, arrayLength, iterable.iterator());
	}

	/**
	 * Retain in the specified array all the elements in the specified iterable
	 * and return the result. java.util.Arrays#retainAll(Object[] array,
	 * Iterable iterable)
	 *
	 * @author mqfdy
	 * @param <E>
	 *            the element type
	 * @param array
	 *            the array
	 * @param iterable
	 *            the iterable
	 * @param size
	 *            the size
	 * @return the e[]
	 * @Date 2018-09-03 09:00
	 */
	public static <E> E[] retainAll(E[] array, Iterable<?> iterable, int size) {
		int arrayLength = array.length;
		return (arrayLength == 0) ? array : retainAll(array, arrayLength, iterable.iterator(), size);
	}

	/**
	 * Retain in the specified array all the elements in the specified iterator
	 * and return the result. java.util.Arrays#retainAll(Object[] array,
	 * Iterator iterator)
	 *
	 * @author mqfdy
	 * @param <E>
	 *            the element type
	 * @param array
	 *            the array
	 * @param iterator
	 *            the iterator
	 * @return the e[]
	 * @Date 2018-09-03 09:00
	 */
	public static <E> E[] retainAll(E[] array, Iterator<?> iterator) {
		int arrayLength = array.length;
		return (arrayLength == 0) ? array : retainAll(array, arrayLength, iterator);
	}

	/**
	 * Retain in the specified array all the elements in the specified iterator
	 * and return the result. java.util.Arrays#retainAll(Object[] array,
	 * Iterator iterator)
	 *
	 * @author mqfdy
	 * @param <E>
	 *            the element type
	 * @param array
	 *            the array
	 * @param iterator
	 *            the iterator
	 * @param size
	 *            the size
	 * @return the e[]
	 * @Date 2018-09-03 09:00
	 */
	public static <E> E[] retainAll(E[] array, Iterator<?> iterator, int size) {
		int arrayLength = array.length;
		return (arrayLength == 0) ? array : retainAll(array, arrayLength, iterator, size);
	}

	/**
	 * assume arrayLength > 0
	 */
	private static <E> E[] retainAll(E[] array, int arrayLength, Iterator<?> iterator) {
		return (iterator.hasNext()) ?
				retainAll_(array, set(iterator), arrayLength)
			:
				newArray(array, 0);
	}

	/**
	 * assume arrayLength > 0
	 */
	private static <E> E[] retainAll(E[] array, int arrayLength, Iterator<?> iterator, int iteratorSize) {
		return (iterator.hasNext()) ?
				retainAll_(array, set(iterator, iteratorSize), arrayLength)
			:
				newArray(array, 0);
	}

	/**
	 * Retain in the specified array all the elements in the specified
	 * collection and return the result. java.util.Arrays#retainAll(Object[]
	 * array, Collection collection)
	 *
	 * @author mqfdy
	 * @param <E>
	 *            the element type
	 * @param array
	 *            the array
	 * @param collection
	 *            the collection
	 * @return the e[]
	 * @Date 2018-09-03 09:00
	 */
	public static <E> E[] retainAll(E[] array, Collection<?> collection) {
		int arrayLength = array.length;
		return (arrayLength == 0) ? array : retainAll(array, collection, arrayLength);
	}

	/**
	 * assume arrayLength > 0
	 */
	private static <E> E[] retainAll(E[] array, Collection<?> collection, int arrayLength) {
		return (collection.isEmpty()) ?
				newArray(array, 0)
			:
				retainAll_(array, collection, arrayLength);
	}

	/**
	 * assume collection is non-empty and arrayLength > 0
	 */
	private static <E> E[] retainAll_(E[] array, Collection<?> collection, int arrayLength) {
		int[] indices = new int[arrayLength];
		int j = 0;
		for (int i = 0; i < arrayLength; i++) {
			if (collection.contains(array[i])) {
				indices[j++] = i;
			}
		}
		if (j == arrayLength) {
			return array;  // everything was retained
		}
		E[] result = newArray(array, j);
		int resultLength = result.length;
		for (int i = 0; i < resultLength; i++) {
			result[i] = array[indices[i]];
		}
		return result;
	}

	/**
	 * Remove from the first specified array all the elements in the second
	 * specified array and return the result.
	 * java.util.Arrays#retainAll(Object[] array1, Object[] array2)
	 *
	 * @author mqfdy
	 * @param <E>
	 *            the element type
	 * @param array1
	 *            the array 1
	 * @param array2
	 *            the array 2
	 * @return the e[]
	 * @Date 2018-09-03 09:00
	 */
	public static <E> E[] retainAll(E[] array1, Object[] array2) {
		int array1Length = array1.length;
		return (array2.length == 0) ?
				(array1Length == 0) ? array1 : newArray(array1, 0)
			:
				retainAll(array1, set(array2), array1Length);
	}

	/**
	 * Remove from the first specified array all the elements in the second
	 * specified array and return the result. java.util.Arrays#retainAll(char[]
	 * array1, char[] array2)
	 *
	 * @author mqfdy
	 * @param array1
	 *            the array 1
	 * @param array2
	 *            the array 2
	 * @return the char[]
	 * @Date 2018-09-03 09:00
	 */
	public static char[] retainAll(char[] array1, char[] array2) {
		int array1Length = array1.length;
		return (array1Length == 0) ? array1 : retainAll(array1, array2, array1Length);
	}

	/**
	 * assume array1Length > 0
	 */
	private static char[] retainAll(char[] array1, char[] array2, int array1Length) {
		int array2Length = array2.length;
		return (array2Length == 0) ? EMPTY_CHAR_ARRAY : retainAll(array1, array2, array1Length, array2Length);
	}

	/**
	 * assume array1Length > 0 and array2Length > 0
	 */
	private static char[] retainAll(char[] array1, char[] array2, int array1Length, int array2Length) {
		int[] indices = new int[array1Length];
		int j = 0;
		for (int i = 0; i < array1Length; i++) {
			if (contains(array2, array1[i])) {
				indices[j++] = i;
			}
		}
		if (j == array1Length) {
			return array1;  // everything was retained
		}
		char[] result = new char[j];
		int resultLength = result.length;
		for (int i = 0; i < resultLength; i++) {
			result[i] = array1[indices[i]];
		}
		return result;
	}

	/**
	 * Remove from the first specified array all the elements in the second
	 * specified array and return the result. java.util.Arrays#retainAll(int[]
	 * array1, int[] array2)
	 *
	 * @author mqfdy
	 * @param array1
	 *            the array 1
	 * @param array2
	 *            the array 2
	 * @return the int[]
	 * @Date 2018-09-03 09:00
	 */
	public static int[] retainAll(int[] array1, int[] array2) {
		int array1Length = array1.length;
		return (array1Length == 0) ? array1 : retainAll(array1, array2, array1Length);
	}

	/**
	 * assume array1Length > 0
	 */
	private static int[] retainAll(int[] array1, int[] array2, int array1Length) {
		int array2Length = array2.length;
		return (array2Length == 0) ? EMPTY_INT_ARRAY : retainAll(array1, array2, array1Length, array2Length);
	}

	/**
	 * assume array1Length > 0 and array2Length > 0
	 */
	private static int[] retainAll(int[] array1, int[] array2, int array1Length, int array2Length) {
		int[] indices = new int[array1Length];
		int j = 0;
		for (int i = 0; i < array1Length; i++) {
			if (contains(array2, array1[i])) {
				indices[j++] = i;
			}
		}
		if (j == array1Length) {
			return array1;  // everything was retained
		}
		int[] result = new int[j];
		int resultLength = result.length;
		for (int i = 0; i < resultLength; i++) {
			result[i] = array1[indices[i]];
		}
		return result;
	}

	/**
	 * Return the array reversed. java.util.Arrays.reverse(Object[] array)
	 *
	 * @author mqfdy
	 * @param <E>
	 *            the element type
	 * @param array
	 *            the array
	 * @return the e[]
	 * @Date 2018-09-03 09:00
	 */
	public static <E> E[] reverse(E... array) {
		int len = array.length;
		for (int i = 0, mid = len >> 1, j = len - 1; i < mid; i++, j--) {
			swap(array, i, j);
		}
		return array;
	}

	/**
	 * Return the array reversed. java.util.Arrays.reverse(char[] array)
	 *
	 * @author mqfdy
	 * @param array
	 *            the array
	 * @return the char[]
	 * @Date 2018-09-03 09:00
	 */
	public static char[] reverse(char... array) {
		int len = array.length;
		for (int i = 0, mid = len >> 1, j = len - 1; i < mid; i++, j--) {
			swap(array, i, j);
		}
		return array;
	}

	/**
	 * Return the array reversed. java.util.Arrays.reverse(int[] array)
	 *
	 * @author mqfdy
	 * @param array
	 *            the array
	 * @return the int[]
	 * @Date 2018-09-03 09:00
	 */
	public static int[] reverse(int... array) {
		int len = array.length;
		for (int i = 0, mid = len >> 1, j = len - 1; i < mid; i++, j--) {
			swap(array, i, j);
		}
		return array;
	}

	/**
	 * Return a list with entries in reverse order from those returned by the
	 * specified iterable.
	 *
	 * @author mqfdy
	 * @param <E>
	 *            the element type
	 * @param iterable
	 *            the iterable
	 * @return the list
	 * @Date 2018-09-03 09:00
	 */
	public static <E> List<E> reverseList(Iterable<? extends E> iterable) {
		return reverse(list(iterable));
	}

	/**
	 * Return a list with entries in reverse order from those returned by the
	 * specified iterable.
	 *
	 * @author mqfdy
	 * @param <E>
	 *            the element type
	 * @param iterable
	 *            the iterable
	 * @param size
	 *            the size
	 * @return the list
	 * @Date 2018-09-03 09:00
	 */
	public static <E> List<E> reverseList(Iterable<? extends E> iterable, int size) {
		return reverse(list(iterable, size));
	}

	/**
	 * Return a list with entries in reverse order from those returned by the
	 * specified iterator.
	 *
	 * @author mqfdy
	 * @param <E>
	 *            the element type
	 * @param iterator
	 *            the iterator
	 * @return the list
	 * @Date 2018-09-03 09:00
	 */
	public static <E> List<E> reverseList(Iterator<? extends E> iterator) {
		return reverse(list(iterator));
	}

	/**
	 * Return a list with entries in reverse order from those returned by the
	 * specified iterator.
	 *
	 * @author mqfdy
	 * @param <E>
	 *            the element type
	 * @param iterator
	 *            the iterator
	 * @param size
	 *            the size
	 * @return the list
	 * @Date 2018-09-03 09:00
	 */
	public static <E> List<E> reverseList(Iterator<? extends E> iterator, int size) {
		return reverse(list(iterator, size));
	}

	/**
	 * Return the rotated array after rotating it one position.
	 * java.util.Arrays.rotate(Object[] array)
	 *
	 * @author mqfdy
	 * @param <E>
	 *            the element type
	 * @param array
	 *            the array
	 * @return the e[]
	 * @Date 2018-09-03 09:00
	 */
	public static <E> E[] rotate(E... array) {
		return rotate(array, 1);
	}

	/**
	 * Return the rotated array after rotating it the specified distance.
	 * java.util.Arrays.rotate(Object[] array, int distance)
	 *
	 * @author mqfdy
	 * @param <E>
	 *            the element type
	 * @param array
	 *            the array
	 * @param distance
	 *            the distance
	 * @return the e[]
	 * @Date 2018-09-03 09:00
	 */
	public static <E> E[] rotate(E[] array, int distance) {
		int len = array.length;
		if ((len == 0) || (len == 1)) {
			return array;
		}
		distance = distance % len;
		if (distance < 0) {
			distance += len;
		}
		if (distance == 0) {
			return array;
		}
		for (int cycleStart = 0, nMoved = 0; nMoved != len; cycleStart++) {
			E displaced = array[cycleStart];
			int i = cycleStart;
			do {
				i += distance;
				if (i >= len) {
					i -= len;
				}
				E temp = array[i];
				array[i] = displaced;
				displaced = temp;
				nMoved ++;
			} while (i != cycleStart);
		}
		return array;
	}

	/**
	 * Return the rotated array after rotating it one position.
	 * java.util.Arrays.rotate(char[] array)
	 *
	 * @author mqfdy
	 * @param array
	 *            the array
	 * @return the char[]
	 * @Date 2018-09-03 09:00
	 */
	public static char[] rotate(char... array) {
		return rotate(array, 1);
	}

	/**
	 * Return the rotated array after rotating it the specified distance.
	 * java.util.Arrays.rotate(char[] array, int distance)
	 *
	 * @author mqfdy
	 * @param array
	 *            the array
	 * @param distance
	 *            the distance
	 * @return the char[]
	 * @Date 2018-09-03 09:00
	 */
	public static char[] rotate(char[] array, int distance) {
		int len = array.length;
		if ((len == 0) || (len == 1)) {
			return array;
		}
		distance = distance % len;
		if (distance < 0) {
			distance += len;
		}
		if (distance == 0) {
			return array;
		}
		for (int cycleStart = 0, nMoved = 0; nMoved != len; cycleStart++) {
			char displaced = array[cycleStart];
			int i = cycleStart;
			do {
				i += distance;
				if (i >= len) {
					i -= len;
				}
				char temp = array[i];
				array[i] = displaced;
				displaced = temp;
				nMoved ++;
			} while (i != cycleStart);
		}
		return array;
	}

	/**
	 * Return the rotated array after rotating it one position.
	 * java.util.Arrays.rotate(int[] array)
	 *
	 * @author mqfdy
	 * @param array
	 *            the array
	 * @return the int[]
	 * @Date 2018-09-03 09:00
	 */
	public static int[] rotate(int... array) {
		return rotate(array, 1);
	}

	/**
	 * Return the rotated array after rotating it the specified distance.
	 * java.util.Arrays.rotate(int[] array, int distance)
	 *
	 * @author mqfdy
	 * @param array
	 *            the array
	 * @param distance
	 *            the distance
	 * @return the int[]
	 * @Date 2018-09-03 09:00
	 */
	public static int[] rotate(int[] array, int distance) {
		int len = array.length;
		if ((len == 0) || (len == 1)) {
			return array;
		}
		distance = distance % len;
		if (distance < 0) {
			distance += len;
		}
		if (distance == 0) {
			return array;
		}
		for (int cycleStart = 0, nMoved = 0; nMoved != len; cycleStart++) {
			int displaced = array[cycleStart];
			int i = cycleStart;
			do {
				i += distance;
				if (i >= len) {
					i -= len;
				}
				int temp = array[i];
				array[i] = displaced;
				displaced = temp;
				nMoved ++;
			} while (i != cycleStart);
		}
		return array;
	}

	/**
	 * Return a set corresponding to the specified iterable.
	 * java.util.HashSet(java.lang.Iterable iterable)
	 *
	 * @author mqfdy
	 * @param <E>
	 *            the element type
	 * @param iterable
	 *            the iterable
	 * @return the sets the
	 * @Date 2018-09-03 09:00
	 */
	public static <E> Set<E> set(Iterable<? extends E> iterable) {
		return set(iterable.iterator());
	}

	/**
	 * Return a set corresponding to the specified iterable.
	 * java.util.HashSet(java.lang.Iterable iterable)
	 *
	 * @author mqfdy
	 * @param <E>
	 *            the element type
	 * @param iterable
	 *            the iterable
	 * @param size
	 *            the size
	 * @return the sets the
	 * @Date 2018-09-03 09:00
	 */
	public static <E> Set<E> set(Iterable<? extends E> iterable, int size) {
		return set(iterable.iterator(), size);
	}

	/**
	 * Return a set corresponding to the specified iterator.
	 * java.util.HashSet(java.util.Iterator iterator)
	 *
	 * @author mqfdy
	 * @param <E>
	 *            the element type
	 * @param iterator
	 *            the iterator
	 * @return the sets the
	 * @Date 2018-09-03 09:00
	 */
	public static <E> Set<E> set(Iterator<? extends E> iterator) {
		return set(iterator, new HashSet<E>());
	}

	/**
	 * Return a set corresponding to the specified iterator.
	 * java.util.HashSet(java.util.Iterator iterator)
	 *
	 * @author mqfdy
	 * @param <E>
	 *            the element type
	 * @param iterator
	 *            the iterator
	 * @param size
	 *            the size
	 * @return the sets the
	 * @Date 2018-09-03 09:00
	 */
	public static <E> Set<E> set(Iterator<? extends E> iterator, int size) {
		return set(iterator, new HashSet<E>(size));
	}

	private static <E> Set<E> set(Iterator<? extends E> iterator, HashSet<E> set) {
		while (iterator.hasNext()) {
			set.add(iterator.next());
		}
		return set;
	}

	/**
	 * Return a set corresponding to the specified array.
	 * java.util.HashSet(Object[] array)
	 *
	 * @author mqfdy
	 * @param <E>
	 *            the element type
	 * @param array
	 *            the array
	 * @return the sets the
	 * @Date 2018-09-03 09:00
	 */
	public static <E> Set<E> set(E... array) {
		Set<E> set = new HashSet<E>(array.length);
		for (E item : array) {
			set.add(item);
		}
		return set;
	}

	private static final Random RANDOM = new Random();

	/**
	 * Return the array after "shuffling" it. java.util.Arrays#shuffle(Object[]
	 * array)
	 *
	 * @author mqfdy
	 * @param <E>
	 *            the element type
	 * @param array
	 *            the array
	 * @return the e[]
	 * @Date 2018-09-03 09:00
	 */
	public static <E> E[] shuffle(E... array) {
		return shuffle(array, RANDOM);
	}

	/**
	 * Return the array after "shuffling" it. java.util.Arrays#shuffle(Object[]
	 * array, Random r)
	 *
	 * @author mqfdy
	 * @param <E>
	 *            the element type
	 * @param array
	 *            the array
	 * @param random
	 *            the random
	 * @return the e[]
	 * @Date 2018-09-03 09:00
	 */
	public static <E> E[] shuffle(E[] array, Random random) {
		int len = array.length;
		if ((len == 0) || (len == 1)) {
			return array;
		}
		for (int i = len; i-- > 0; ) {
			SecureRandom sr = new SecureRandom();
			swap(array, i, sr.nextInt(len));
		}
		return array;
	}

	/**
	 * Return the array after "shuffling" it. java.util.Arrays#shuffle(char[]
	 * array)
	 *
	 * @author mqfdy
	 * @param array
	 *            the array
	 * @return the char[]
	 * @Date 2018-09-03 09:00
	 */
	public static char[] shuffle(char... array) {
		return shuffle(array, RANDOM);
	}

	/**
	 * Return the array after "shuffling" it. java.util.Arrays#shuffle(char[]
	 * array, Random r)
	 *
	 * @author mqfdy
	 * @param array
	 *            the array
	 * @param random
	 *            the random
	 * @return the char[]
	 * @Date 2018-09-03 09:00
	 */
	public static char[] shuffle(char[] array, Random random) {
		int len = array.length;
		if ((len == 0) || (len == 1)) {
			return array;
		}
		for (int i = len; i-- > 0; ) {
			SecureRandom sr = new SecureRandom();
			swap(array, i, sr.nextInt(len));
		}
		return array;
	}

	/**
	 * Return the array after "shuffling" it. java.util.Arrays#shuffle(int[]
	 * array)
	 *
	 * @author mqfdy
	 * @param array
	 *            the array
	 * @return the int[]
	 * @Date 2018-09-03 09:00
	 */
	public static int[] shuffle(int... array) {
		return shuffle(array, RANDOM);
	}

	/**
	 * Return the array after "shuffling" it. java.util.Arrays#shuffle(int[]
	 * array, Random r)
	 *
	 * @author mqfdy
	 * @param array
	 *            the array
	 * @param random
	 *            the random
	 * @return the int[]
	 * @Date 2018-09-03 09:00
	 */
	public static int[] shuffle(int[] array, Random random) {
		int len = array.length;
		if ((len == 0) || (len == 1)) {
			return array;
		}
		for (int i = len; i-- > 0; ) {
			SecureRandom sr = new SecureRandom();
			swap(array, i, sr.nextInt(len));
		}
		return array;
	}

	/**
	 * Return an iterator that returns only the single, specified object.
	 * Object#toIterator() ?!
	 *
	 * @author mqfdy
	 * @param <E>
	 *            the element type
	 * @param value
	 *            the value
	 * @return the iterator
	 * @Date 2018-09-03 09:00
	 */
	public static <E> Iterator<E> singletonIterator(E value) {
		return new SingleElementIterator<E>(value);
	}

	/**
	 * Return the number of elements returned by the specified iterable.
	 * java.lang.Iterable#size()
	 *
	 * @author mqfdy
	 * @param iterable
	 *            the iterable
	 * @return the int
	 * @Date 2018-09-03 09:00
	 */
	public static int size(Iterable<?> iterable) {
		return size(iterable.iterator());
	}

	/**
	 * Return the number of elements returned by the specified iterator.
	 * java.util.Iterator#size()
	 *
	 * @author mqfdy
	 * @param iterator
	 *            the iterator
	 * @return the int
	 * @Date 2018-09-03 09:00
	 */
	public static int size(Iterator<?> iterator) {
		int size = 0;
		while (iterator.hasNext()) {
			iterator.next();
			size++;
		}
		return size;
	}

	/**
	 * Return a sorted set corresponding to the specified iterable.
	 * java.util.TreeSet(java.lang.Iterable iterable)
	 *
	 * @author mqfdy
	 * @param <E>
	 *            the element type
	 * @param iterable
	 *            the iterable
	 * @return the sorted set
	 * @Date 2018-09-03 09:00
	 */
	public static <E extends Comparable<? super E>> SortedSet<E> sortedSet(Iterable<? extends E> iterable) {
		return sortedSet(iterable, null);
	}

	/**
	 * Return a sorted set corresponding to the specified iterable.
	 * java.util.TreeSet(java.lang.Iterable iterable)
	 *
	 * @author mqfdy
	 * @param <E>
	 *            the element type
	 * @param iterable
	 *            the iterable
	 * @param size
	 *            the size
	 * @return the sorted set
	 * @Date 2018-09-03 09:00
	 */
	public static <E extends Comparable<? super E>> SortedSet<E> sortedSet(Iterable<? extends E> iterable, int size) {
		return sortedSet(iterable, size, null);
	}

	/**
	 * Return a sorted set corresponding to the specified iterable and
	 * comparator. java.util.TreeSet(java.lang.Iterable iterable,
	 * java.util.Comparator c)
	 *
	 * @author mqfdy
	 * @param <E>
	 *            the element type
	 * @param iterable
	 *            the iterable
	 * @param comparator
	 *            the comparator
	 * @return the sorted set
	 * @Date 2018-09-03 09:00
	 */
	public static <E> SortedSet<E> sortedSet(Iterable<? extends E> iterable, Comparator<? super E> comparator) {
		return sortedSet(iterable.iterator(), comparator);
	}

	/**
	 * Return a sorted set corresponding to the specified iterable and
	 * comparator. java.util.TreeSet(java.lang.Iterable iterable,
	 * java.util.Comparator c)
	 *
	 * @author mqfdy
	 * @param <E>
	 *            the element type
	 * @param iterable
	 *            the iterable
	 * @param size
	 *            the size
	 * @param comparator
	 *            the comparator
	 * @return the sorted set
	 * @Date 2018-09-03 09:00
	 */
	public static <E> SortedSet<E> sortedSet(Iterable<? extends E> iterable, int size, Comparator<? super E> comparator) {
		return sortedSet(iterable.iterator(), size, comparator);
	}

	/**
	 * Return a sorted set corresponding to the specified iterator.
	 * java.util.TreeSet(java.util.Iterator iterator)
	 *
	 * @author mqfdy
	 * @param <E>
	 *            the element type
	 * @param iterator
	 *            the iterator
	 * @return the sorted set
	 * @Date 2018-09-03 09:00
	 */
	public static <E extends Comparable<? super E>> SortedSet<E> sortedSet(Iterator<? extends E> iterator) {
		return sortedSet(iterator, null);
	}

	/**
	 * Return a sorted set corresponding to the specified iterator.
	 * java.util.TreeSet(java.util.Iterator iterator)
	 *
	 * @author mqfdy
	 * @param <E>
	 *            the element type
	 * @param iterator
	 *            the iterator
	 * @param size
	 *            the size
	 * @return the sorted set
	 * @Date 2018-09-03 09:00
	 */
	public static <E extends Comparable<? super E>> SortedSet<E> sortedSet(Iterator<? extends E> iterator, int size) {
		return sortedSet(iterator, size, null);
	}

	/**
	 * Return a sorted set corresponding to the specified iterator and
	 * comparator. java.util.TreeSet(java.util.Iterator iterator,
	 * java.util.Comparator c)
	 *
	 * @author mqfdy
	 * @param <E>
	 *            the element type
	 * @param iterator
	 *            the iterator
	 * @param comparator
	 *            the comparator
	 * @return the sorted set
	 * @Date 2018-09-03 09:00
	 */
	public static <E> SortedSet<E> sortedSet(Iterator<? extends E> iterator, Comparator<? super E> comparator) {
		return sortedSet(list(iterator), comparator);
	}

	/**
	 * Return a sorted set corresponding to the specified iterator and
	 * comparator. java.util.TreeSet(java.util.Iterator iterator,
	 * java.util.Comparator c)
	 *
	 * @author mqfdy
	 * @param <E>
	 *            the element type
	 * @param iterator
	 *            the iterator
	 * @param size
	 *            the size
	 * @param comparator
	 *            the comparator
	 * @return the sorted set
	 * @Date 2018-09-03 09:00
	 */
	public static <E> SortedSet<E> sortedSet(Iterator<? extends E> iterator, int size, Comparator<? super E> comparator) {
		return sortedSet(list(iterator, size), comparator);
	}

	private static <E> SortedSet<E> sortedSet(List<E> list, Comparator<? super E> comparator) {
		SortedSet<E> sortedSet = new TreeSet<E>(comparator);
		sortedSet.addAll(list);
		return sortedSet;
	}

	/**
	 * Return a sorted set corresponding to the specified array.
	 * java.util.TreeSet(Object[] array)
	 *
	 * @author mqfdy
	 * @param <E>
	 *            the element type
	 * @param array
	 *            the array
	 * @return the sorted set
	 * @Date 2018-09-03 09:00
	 */
	public static <E extends Comparable<? super E>> SortedSet<E> sortedSet(E... array) {
		return sortedSet(array, null);
	}

	/**
	 * Return a sorted set corresponding to the specified array and comparator.
	 * java.util.TreeSet(Object[] array, java.util.Comparator c)
	 *
	 * @author mqfdy
	 * @param <E>
	 *            the element type
	 * @param array
	 *            the array
	 * @param comparator
	 *            the comparator
	 * @return the sorted set
	 * @Date 2018-09-03 09:00
	 */
	public static <E> SortedSet<E> sortedSet(E[] array, Comparator<? super E> comparator) {
		SortedSet<E> sortedSet = new TreeSet<E>(comparator);
		sortedSet.addAll(Arrays.asList(array));
		return sortedSet;
	}

	/**
	 * Return a sub-array of the specified array, starting at the specified
	 * position with the specified length. java.util.Arrays#subArray(E[] array,
	 * int start, int length)
	 *
	 * @author mqfdy
	 * @param <E>
	 *            the element type
	 * @param array
	 *            the array
	 * @param start
	 *            the start
	 * @param length
	 *            the length
	 * @return the e[]
	 * @Date 2018-09-03 09:00
	 */
	public static <E> E[] subArray(E[] array, int start, int length) {
		E[] result = newArray(array, length);
		if (length > 0) {
			System.arraycopy(array, start, result, 0, length);
		}
		return result;
	}

	/**
	 * Return a sub-array of the specified array, starting at the specified
	 * position with the specified length. java.util.Arrays#subArray(int[]
	 * array, int start, int length)
	 *
	 * @author mqfdy
	 * @param array
	 *            the array
	 * @param start
	 *            the start
	 * @param length
	 *            the length
	 * @return the int[]
	 * @Date 2018-09-03 09:00
	 */
	public static int[] subArray(int[] array, int start, int length) {
		int[] result = new int[length];
		if (length > 0) {
			System.arraycopy(array, start, result, 0, length);
		}
		return result;
	}

	/**
	 * Return a sub-array of the specified array, starting at the specified
	 * position with the specified length. java.util.Arrays#subArray(char[]
	 * array, int start, int length)
	 *
	 * @author mqfdy
	 * @param array
	 *            the array
	 * @param start
	 *            the start
	 * @param length
	 *            the length
	 * @return the char[]
	 * @Date 2018-09-03 09:00
	 */
	public static char[] subArray(char[] array, int start, int length) {
		char[] result = new char[length];
		if (length > 0) {
			System.arraycopy(array, start, result, 0, length);
		}
		return result;
	}

	/**
	 * Return the array after the specified elements have been "swapped".
	 * java.util.Arrays#swap(Object[] array, int i, int j)
	 *
	 * @author mqfdy
	 * @param <E>
	 *            the element type
	 * @param array
	 *            the array
	 * @param i
	 *            the i
	 * @param j
	 *            the j
	 * @return the e[]
	 * @Date 2018-09-03 09:00
	 */
	public static <E> E[] swap(E[] array, int i, int j) {
		E temp = array[i];
		array[i] = array[j];
		array[j] = temp;
		return array;
	}

	/**
	 * Return the array after the specified elements have been "swapped".
	 * java.util.Arrays#swap(char[] array, int i, int j)
	 *
	 * @author mqfdy
	 * @param array
	 *            the array
	 * @param i
	 *            the i
	 * @param j
	 *            the j
	 * @return the char[]
	 * @Date 2018-09-03 09:00
	 */
	public static char[] swap(char[] array, int i, int j) {
		char temp = array[i];
		array[i] = array[j];
		array[j] = temp;
		return array;
	}

	/**
	 * Return the array after the specified elements have been "swapped".
	 * java.util.Arrays#swap(int[] array, int i, int j)
	 *
	 * @author mqfdy
	 * @param array
	 *            the array
	 * @param i
	 *            the i
	 * @param j
	 *            the j
	 * @return the int[]
	 * @Date 2018-09-03 09:00
	 */
	public static int[] swap(int[] array, int i, int j) {
		int temp = array[i];
		array[i] = array[j];
		array[j] = temp;
		return array;
	}

	/**
	 * Return a vector corresponding to the specified iterable. This is useful
	 * for legacy code that requires a java.util.Vector.
	 * java.util.Vector(java.lang.Iterable iterable)
	 *
	 * @author mqfdy
	 * @param <E>
	 *            the element type
	 * @param iterable
	 *            the iterable
	 * @return the vector
	 * @Date 2018-09-03 09:00
	 */
	public static <E> Vector<E> vector(Iterable<? extends E> iterable) {
		return vector(iterable.iterator());
	}

	/**
	 * Return a vector corresponding to the specified iterable. This is useful
	 * for legacy code that requires a java.util.Vector.
	 * java.util.Vector(java.lang.Iterable iterable)
	 *
	 * @author mqfdy
	 * @param <E>
	 *            the element type
	 * @param iterable
	 *            the iterable
	 * @param size
	 *            the size
	 * @return the vector
	 * @Date 2018-09-03 09:00
	 */
	public static <E> Vector<E> vector(Iterable<? extends E> iterable, int size) {
		return vector(iterable.iterator(), size);
	}

	/**
	 * Return a vector corresponding to the specified iterator. This is useful
	 * for legacy code that requires a java.util.Vector.
	 * java.util.Vector(java.util.Iterator iterator)
	 *
	 * @author mqfdy
	 * @param <E>
	 *            the element type
	 * @param iterator
	 *            the iterator
	 * @return the vector
	 * @Date 2018-09-03 09:00
	 */
	public static <E> Vector<E> vector(Iterator<? extends E> iterator) {
		return vector(iterator, new Vector<E>());
	}

	/**
	 * Return a vector corresponding to the specified iterator. This is useful
	 * for legacy code that requires a java.util.Vector.
	 * java.util.Vector(java.util.Iterator iterator)
	 *
	 * @author mqfdy
	 * @param <E>
	 *            the element type
	 * @param iterator
	 *            the iterator
	 * @param size
	 *            the size
	 * @return the vector
	 * @Date 2018-09-03 09:00
	 */
	public static <E> Vector<E> vector(Iterator<? extends E> iterator, int size) {
		return vector(iterator, new Vector<E>(size));
	}

	private static <E> Vector<E> vector(Iterator<? extends E> iterator, Vector<E> v) {
		while (iterator.hasNext()) {
			v.addElement(iterator.next());
		}
		return v;
	}

	/**
	 * Return a vector corresponding to the specified array. This is useful for
	 * legacy code that requires a java.util.Vector. java.util.Vector(Object[]
	 * array)
	 *
	 * @author mqfdy
	 * @param <E>
	 *            the element type
	 * @param array
	 *            the array
	 * @return the vector
	 * @Date 2018-09-03 09:00
	 */
	public static <E> Vector<E> vector(E... array) {
		Vector<E> v = new Vector<E>(array.length);
		for (E item : array) {
			v.addElement(item);
		}
		return v;
	}


	// ********** single-use Iterable **********

	/**
	 * This is a one-time use iterable that can return a single iterator. Once
	 * the iterator is returned the iterable is no longer valid. As such, this
	 * utility should only be used in one-time use situations, such as a
	 * 'for-each' loop.
	 *
	 * @author mqfdy
	 * @param <E>
	 *            the element type
	 */
	public static class SingleUseIterable<E> implements Iterable<E> {
		private Iterator<E> iterator;

		/**
		 * Instantiates a new single use iterable.
		 *
		 * @param iterator
		 *            the iterator
		 */
		public SingleUseIterable(Iterator<? extends E> iterator) {
			super();
			if (iterator == null) {
				throw new NullPointerException();
			}
			this.iterator = new GenericIteratorWrapper<E>(iterator);
		}

		public Iterator<E> iterator() {
			if (this.iterator == null) {
				throw new IllegalStateException("This method has already been called.");
			}
			Iterator<E> result = this.iterator;
			this.iterator = null;
			return result;
		}

		@Override
		public String toString() {
			return StringTools.buildToStringFor(this, this.iterator);
		}

	}


	// ********** java.util.Collections enhancements **********

	/**
	 * Return the destination list after the source list has been copied into
	 * it.
	 *
	 * @param <E>
	 *            the element type
	 * @param dest
	 *            the dest
	 * @param src
	 *            the src
	 * @return the list<? super e>
	 * @see java.util.Collections#copy(java.util.List, java.util.List)
	 */
	public static <E> List<? super E> copy(List<? super E> dest, List<? extends E> src) {
		Collections.copy(dest, src);
		return dest;
	}

	/**
	 * Return the list after it has been "filled".
	 *
	 * @param <E>
	 *            the element type
	 * @param list
	 *            the list
	 * @param value
	 *            the value
	 * @return the list<? super e>
	 * @see java.util.Collections#fill(java.util.List, java.lang.Object)
	 */
	public static <E> List<? super E> fill(List<? super E> list, E value) {
		Collections.fill(list, value);
		return list;
	}

	/**
	 * Return the list after it has been "reversed".
	 *
	 * @param <E>
	 *            the element type
	 * @param list
	 *            the list
	 * @return the list
	 * @see java.util.Collections#reverse(java.util.List)
	 */
	public static <E> List<E> reverse(List<E> list) {
		Collections.reverse(list);
		return list;
	}

	/**
	 * Return the list after it has been "rotated" by one position.
	 *
	 * @param <E>
	 *            the element type
	 * @param list
	 *            the list
	 * @return the list
	 * @see java.util.Collections#rotate(java.util.List, int)
	 */
	public static <E> List<E> rotate(List<E> list) {
		return rotate(list, 1);
	}

	/**
	 * Return the list after it has been "rotated".
	 *
	 * @param <E>
	 *            the element type
	 * @param list
	 *            the list
	 * @param distance
	 *            the distance
	 * @return the list
	 * @see java.util.Collections#rotate(java.util.List, int)
	 */
	public static <E> List<E> rotate(List<E> list, int distance) {
		Collections.rotate(list, distance);
		return list;
	}

	/**
	 * Return the list after it has been "shuffled".
	 *
	 * @param <E>
	 *            the element type
	 * @param list
	 *            the list
	 * @return the list
	 * @see java.util.Collections#shuffle(java.util.List)
	 */
	public static <E> List<E> shuffle(List<E> list) {
		Collections.shuffle(list);
		return list;
	}

	/**
	 * Return the list after it has been "shuffled".
	 *
	 * @param <E>
	 *            the element type
	 * @param list
	 *            the list
	 * @param random
	 *            the random
	 * @return the list
	 * @see java.util.Collections#shuffle(java.util.List, java.util.Random)
	 */
	public static <E> List<E> shuffle(List<E> list, Random random) {
		Collections.shuffle(list, random);
		return list;
	}

	/**
	 * Return the list after it has been "sorted".
	 *
	 * @param <E>
	 *            the element type
	 * @param list
	 *            the list
	 * @return the list
	 * @see java.util.Collections#sort(java.util.List)
	 */
	public static <E extends Comparable<? super E>> List<E> sort(List<E> list) {
		Collections.sort(list);
		return list;
	}

	/**
	 * Return the list after it has been "sorted".
	 *
	 * @param <E>
	 *            the element type
	 * @param list
	 *            the list
	 * @param comparator
	 *            the comparator
	 * @return the list
	 * @see java.util.Collections#sort(java.util.List, java.util.Comparator)
	 */
	public static <E> List<E> sort(List<E> list, Comparator<? super E> comparator) {
		Collections.sort(list, comparator);
		return list;
	}

	/**
	 * Return the iterable after it has been "sorted".
	 *
	 * @author mqfdy
	 * @param <E>
	 *            the element type
	 * @param iterable
	 *            the iterable
	 * @return the iterable
	 * @Date 2018-09-03 09:00
	 */
	public static <E extends Comparable<? super E>> Iterable<E> sort(Iterable<E> iterable) {
		return sort(iterable, null);
	}

	/**
	 * Return the iterable after it has been "sorted".
	 *
	 * @author mqfdy
	 * @param <E>
	 *            the element type
	 * @param iterable
	 *            the iterable
	 * @param comparator
	 *            the comparator
	 * @return the iterable
	 * @Date 2018-09-03 09:00
	 */
	public static <E> Iterable<E> sort(Iterable<E> iterable, Comparator<? super E> comparator) {
		return sort(list(iterable), comparator);
	}

	/**
	 * Return the iterator after it has been "sorted".
	 *
	 * @author mqfdy
	 * @param <E>
	 *            the element type
	 * @param iterator
	 *            the iterator
	 * @return the iterator<? extends e>
	 * @Date 2018-09-03 09:00
	 */
	public static <E extends Comparable<? super E>> Iterator<? extends E> sort(Iterator<? extends E> iterator) {
		return sort(iterator, null);
	}

	/**
	 * Return the iterator after it has been "sorted".
	 *
	 * @author mqfdy
	 * @param <E>
	 *            the element type
	 * @param iterator
	 *            the iterator
	 * @param comparator
	 *            the comparator
	 * @return the iterator<? extends e>
	 * @Date 2018-09-03 09:00
	 */
	public static <E> Iterator<? extends E> sort(Iterator<? extends E> iterator, Comparator<? super E> comparator) {
		return sort(list(iterator), comparator).iterator();
	}

	/**
	 * Return the list after the specified elements have been "swapped".
	 *
	 * @param <E>
	 *            the element type
	 * @param list
	 *            the list
	 * @param i
	 *            the i
	 * @param j
	 *            the j
	 * @return the list
	 * @see java.util.Collections#swap(java.util.List, int, int)
	 */
	public static <E> List<E> swap(List<E> list, int i, int j) {
		Collections.swap(list, i, j);
		return list;
	}


	// ********** java.util.Arrays enhancements **********

	/**
	 * Return the array after it has been "filled".
	 *
	 * @param array
	 *            the array
	 * @param value
	 *            the value
	 * @return the boolean[]
	 * @see java.util.Arrays#fill(boolean[], boolean)
	 */
	public static boolean[] fill(boolean[] array, boolean value) {
		Arrays.fill(array, value);
		return array;
	}

	/**
	 * Return the array after it has been "filled".
	 *
	 * @param array
	 *            the array
	 * @param fromIndex
	 *            the from index
	 * @param toIndex
	 *            the to index
	 * @param value
	 *            the value
	 * @return the boolean[]
	 * @see java.util.Arrays#fill(boolean[], int, int, boolean)
	 */
	public static boolean[] fill(boolean[] array, int fromIndex, int toIndex, boolean value) {
		Arrays.fill(array, fromIndex, toIndex, value);
		return array;
	}

	/**
	 * Return the array after it has been "filled".
	 *
	 * @param array
	 *            the array
	 * @param value
	 *            the value
	 * @return the byte[]
	 * @see java.util.Arrays#fill(byte[], byte)
	 */
	public static byte[] fill(byte[] array, byte value) {
		Arrays.fill(array, value);
		return array;
	}

	/**
	 * Return the array after it has been "filled".
	 *
	 * @param array
	 *            the array
	 * @param fromIndex
	 *            the from index
	 * @param toIndex
	 *            the to index
	 * @param value
	 *            the value
	 * @return the byte[]
	 * @see java.util.Arrays#fill(byte[], int, int, byte)
	 */
	public static byte[] fill(byte[] array, int fromIndex, int toIndex, byte value) {
		Arrays.fill(array, fromIndex, toIndex, value);
		return array;
	}

	/**
	 * Return the array after it has been "filled".
	 *
	 * @param array
	 *            the array
	 * @param value
	 *            the value
	 * @return the char[]
	 * @see java.util.Arrays#fill(char[], char)
	 */
	public static char[] fill(char[] array, char value) {
		Arrays.fill(array, value);
		return array;
	}

	/**
	 * Return the array after it has been "filled".
	 *
	 * @param array
	 *            the array
	 * @param fromIndex
	 *            the from index
	 * @param toIndex
	 *            the to index
	 * @param value
	 *            the value
	 * @return the char[]
	 * @see java.util.Arrays#fill(char[], int, int, char)
	 */
	public static char[] fill(char[] array, int fromIndex, int toIndex, char value) {
		Arrays.fill(array, fromIndex, toIndex, value);
		return array;
	}

	/**
	 * Return the array after it has been "filled".
	 *
	 * @param array
	 *            the array
	 * @param value
	 *            the value
	 * @return the double[]
	 * @see java.util.Arrays#fill(double[], double)
	 */
	public static double[] fill(double[] array, double value) {
		Arrays.fill(array, value);
		return array;
	}

	/**
	 * Return the array after it has been "filled".
	 *
	 * @param array
	 *            the array
	 * @param fromIndex
	 *            the from index
	 * @param toIndex
	 *            the to index
	 * @param value
	 *            the value
	 * @return the double[]
	 * @see java.util.Arrays#fill(double[], int, int, double)
	 */
	public static double[] fill(double[] array, int fromIndex, int toIndex, double value) {
		Arrays.fill(array, fromIndex, toIndex, value);
		return array;
	}

	/**
	 * Return the array after it has been "filled".
	 *
	 * @param array
	 *            the array
	 * @param value
	 *            the value
	 * @return the float[]
	 * @see java.util.Arrays#fill(float[], float)
	 */
	public static float[] fill(float[] array, float value) {
		Arrays.fill(array, value);
		return array;
	}

	/**
	 * Return the array after it has been "filled".
	 *
	 * @param array
	 *            the array
	 * @param fromIndex
	 *            the from index
	 * @param toIndex
	 *            the to index
	 * @param value
	 *            the value
	 * @return the float[]
	 * @see java.util.Arrays#fill(float[], int, int, float)
	 */
	public static float[] fill(float[] array, int fromIndex, int toIndex, float value) {
		Arrays.fill(array, fromIndex, toIndex, value);
		return array;
	}

	/**
	 * Return the array after it has been "filled".
	 *
	 * @param array
	 *            the array
	 * @param value
	 *            the value
	 * @return the int[]
	 * @see java.util.Arrays#fill(int[], int)
	 */
	public static int[] fill(int[] array, int value) {
		Arrays.fill(array, value);
		return array;
	}

	/**
	 * Return the array after it has been "filled".
	 *
	 * @param array
	 *            the array
	 * @param fromIndex
	 *            the from index
	 * @param toIndex
	 *            the to index
	 * @param value
	 *            the value
	 * @return the int[]
	 * @see java.util.Arrays#fill(int[], int, int, int)
	 */
	public static int[] fill(int[] array, int fromIndex, int toIndex, int value) {
		Arrays.fill(array, fromIndex, toIndex, value);
		return array;
	}

	/**
	 * Return the array after it has been "filled".
	 *
	 * @param <E>
	 *            the element type
	 * @param array
	 *            the array
	 * @param value
	 *            the value
	 * @return the e[]
	 * @see java.util.Arrays#fill(Object[], Object)
	 */
	public static <E> E[] fill(E[] array, E value) {
		Arrays.fill(array, value);
		return array;
	}

	/**
	 * Return the array after it has been "filled".
	 *
	 * @param <E>
	 *            the element type
	 * @param array
	 *            the array
	 * @param fromIndex
	 *            the from index
	 * @param toIndex
	 *            the to index
	 * @param value
	 *            the value
	 * @return the e[]
	 * @see java.util.Arrays#fill(Object[], int, int, Object)
	 */
	public static <E> E[] fill(E[] array, int fromIndex, int toIndex, E value) {
		Arrays.fill(array, fromIndex, toIndex, value);
		return array;
	}

	/**
	 * Return the array after it has been "filled".
	 *
	 * @param array
	 *            the array
	 * @param value
	 *            the value
	 * @return the long[]
	 * @see java.util.Arrays#fill(long[], long)
	 */
	public static long[] fill(long[] array, long value) {
		Arrays.fill(array, value);
		return array;
	}

	/**
	 * Return the array after it has been "filled".
	 *
	 * @param array
	 *            the array
	 * @param fromIndex
	 *            the from index
	 * @param toIndex
	 *            the to index
	 * @param value
	 *            the value
	 * @return the long[]
	 * @see java.util.Arrays#fill(long[], int, int, long)
	 */
	public static long[] fill(long[] array, int fromIndex, int toIndex, long value) {
		Arrays.fill(array, fromIndex, toIndex, value);
		return array;
	}

	/**
	 * Return the array after it has been "filled".
	 *
	 * @param array
	 *            the array
	 * @param value
	 *            the value
	 * @return the short[]
	 * @see java.util.Arrays#fill(short[], short)
	 */
	public static short[] fill(short[] array, short value) {
		Arrays.fill(array, value);
		return array;
	}

	/**
	 * Return the array after it has been "filled".
	 *
	 * @param array
	 *            the array
	 * @param fromIndex
	 *            the from index
	 * @param toIndex
	 *            the to index
	 * @param value
	 *            the value
	 * @return the short[]
	 * @see java.util.Arrays#fill(short[], int, int, short)
	 */
	public static short[] fill(short[] array, int fromIndex, int toIndex, short value) {
		Arrays.fill(array, fromIndex, toIndex, value);
		return array;
	}

	/**
	 * Return the array after it has been "sorted".
	 *
	 * @param array
	 *            the array
	 * @return the byte[]
	 * @see java.util.Arrays#sort(byte[])
	 */
	public static byte[] sort(byte... array) {
		Arrays.sort(array);
		return array;
	}

	/**
	 * Return the array after it has been "sorted".
	 *
	 * @param array
	 *            the array
	 * @param fromIndex
	 *            the from index
	 * @param toIndex
	 *            the to index
	 * @return the byte[]
	 * @see java.util.Arrays#sort(byte[], int, int)
	 */
	public static byte[] sort(byte[] array, int fromIndex, int toIndex) {
		Arrays.sort(array, fromIndex, toIndex);
		return array;
	}

	/**
	 * Return the array after it has been "sorted".
	 *
	 * @param array
	 *            the array
	 * @return the char[]
	 * @see java.util.Arrays#sort(char[])
	 */
	public static char[] sort(char... array) {
		Arrays.sort(array);
		return array;
	}

	/**
	 * Return the array after it has been "sorted".
	 *
	 * @param array
	 *            the array
	 * @param fromIndex
	 *            the from index
	 * @param toIndex
	 *            the to index
	 * @return the char[]
	 * @see java.util.Arrays#sort(char[], int, int)
	 */
	public static char[] sort(char[] array, int fromIndex, int toIndex) {
		Arrays.sort(array, fromIndex, toIndex);
		return array;
	}

	/**
	 * Return the array after it has been "sorted".
	 *
	 * @param array
	 *            the array
	 * @return the double[]
	 * @see java.util.Arrays#sort(double[])
	 */
	public static double[] sort(double... array) {
		Arrays.sort(array);
		return array;
	}

	/**
	 * Return the array after it has been "sorted".
	 *
	 * @param array
	 *            the array
	 * @param fromIndex
	 *            the from index
	 * @param toIndex
	 *            the to index
	 * @return the double[]
	 * @see java.util.Arrays#sort(double[], int, int)
	 */
	public static double[] sort(double[] array, int fromIndex, int toIndex) {
		Arrays.sort(array, fromIndex, toIndex);
		return array;
	}

	/**
	 * Return the array after it has been "sorted".
	 *
	 * @param array
	 *            the array
	 * @return the float[]
	 * @see java.util.Arrays#sort(float[])
	 */
	public static float[] sort(float... array) {
		Arrays.sort(array);
		return array;
	}

	/**
	 * Return the array after it has been "sorted".
	 *
	 * @param array
	 *            the array
	 * @param fromIndex
	 *            the from index
	 * @param toIndex
	 *            the to index
	 * @return the float[]
	 * @see java.util.Arrays#sort(float[], int, int)
	 */
	public static float[] sort(float[] array, int fromIndex, int toIndex) {
		Arrays.sort(array, fromIndex, toIndex);
		return array;
	}

	/**
	 * Return the array after it has been "sorted".
	 *
	 * @param array
	 *            the array
	 * @return the int[]
	 * @see java.util.Arrays#sort(int[])
	 */
	public static int[] sort(int... array) {
		Arrays.sort(array);
		return array;
	}

	/**
	 * Return the array after it has been "sorted".
	 *
	 * @param array
	 *            the array
	 * @param fromIndex
	 *            the from index
	 * @param toIndex
	 *            the to index
	 * @return the int[]
	 * @see java.util.Arrays#sort(int[], int, int)
	 */
	public static int[] sort(int[] array, int fromIndex, int toIndex) {
		Arrays.sort(array, fromIndex, toIndex);
		return array;
	}

	/**
	 * Return the array after it has been "sorted".
	 *
	 * @param <E>
	 *            the element type
	 * @param array
	 *            the array
	 * @return the e[]
	 * @see java.util.Arrays#sort(Object[])
	 */
	public static <E> E[] sort(E... array) {
		Arrays.sort(array);
		return array;
	}

	/**
	 * Return the array after it has been "sorted".
	 *
	 * @param <E>
	 *            the element type
	 * @param array
	 *            the array
	 * @param comparator
	 *            the comparator
	 * @return the e[]
	 * @see java.util.Arrays#sort(Object[], java.util.Comparator)
	 */
	public static <E> E[] sort(E[] array, Comparator<? super E> comparator) {
		Arrays.sort(array, comparator);
		return array;
	}

	/**
	 * Return the array after it has been "sorted".
	 *
	 * @param <E>
	 *            the element type
	 * @param array
	 *            the array
	 * @param fromIndex
	 *            the from index
	 * @param toIndex
	 *            the to index
	 * @return the e[]
	 * @see java.util.Arrays#sort(Object[], int, int)
	 */
	public static <E> E[] sort(E[] array, int fromIndex, int toIndex) {
		Arrays.sort(array, fromIndex, toIndex);
		return array;
	}

	/**
	 * Return the array after it has been "sorted".
	 *
	 * @param <E>
	 *            the element type
	 * @param array
	 *            the array
	 * @param fromIndex
	 *            the from index
	 * @param toIndex
	 *            the to index
	 * @param comparator
	 *            the comparator
	 * @return the e[]
	 * @see java.util.Arrays#sort(Object[], int, int, java.util.Comparator)
	 */
	public static <E> E[] sort(E[] array, int fromIndex, int toIndex, Comparator<? super E> comparator) {
		Arrays.sort(array, fromIndex, toIndex, comparator);
		return array;
	}

	/**
	 * Return the array after it has been "sorted".
	 *
	 * @param array
	 *            the array
	 * @return the long[]
	 * @see java.util.Arrays#sort(long[])
	 */
	public static long[] sort(long... array) {
		Arrays.sort(array);
		return array;
	}

	/**
	 * Return the array after it has been "sorted".
	 *
	 * @param array
	 *            the array
	 * @param fromIndex
	 *            the from index
	 * @param toIndex
	 *            the to index
	 * @return the long[]
	 * @see java.util.Arrays#sort(long[], int, int)
	 */
	public static long[] sort(long[] array, int fromIndex, int toIndex) {
		Arrays.sort(array, fromIndex, toIndex);
		return array;
	}

	/**
	 * Return the array after it has been "sorted".
	 *
	 * @param array
	 *            the array
	 * @return the short[]
	 * @see java.util.Arrays#sort(short[])
	 */
	public static short[] sort(short... array) {
		Arrays.sort(array);
		return array;
	}

	/**
	 * Return the array after it has been "sorted".
	 *
	 * @param array
	 *            the array
	 * @param fromIndex
	 *            the from index
	 * @param toIndex
	 *            the to index
	 * @return the short[]
	 * @see java.util.Arrays#sort(short[], int, int)
	 */
	public static short[] sort(short[] array, int fromIndex, int toIndex) {
		Arrays.sort(array, fromIndex, toIndex);
		return array;
	}


	// ********** constructor **********

	/**
	 * Suppress default constructor, ensuring non-instantiability.
	 */
	private CollectionTools() {
		super();
		throw new UnsupportedOperationException();
	}

}
