/*******************************************************************************
 * Copyright (c) 2005, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package com.mqfdy.code.springboot.utilities.internal.iterators;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Set;

import com.mqfdy.code.springboot.utilities.internal.StringTools;


// TODO: Auto-generated Javadoc
/**
 * A <code>GraphIterator</code> is similar to a <code>TreeIterator</code> except
 * that it cannot be assumed that all nodes assume a strict tree structure. For
 * instance, in a tree, a node cannot be a descendent of itself, but a graph may
 * have a cyclical structure.
 * 
 * A <code>GraphIterator</code> simplifies the traversal of a graph of objects,
 * where the objects' protocol(s) provides a method for getting the next
 * collection of nodes in the graph, (or *neighbors*), but does not provide a
 * method for getting *all* of the nodes in the graph. (e.g. a neighbor can
 * return his neighbors, and those neighbors can return their neighbors, which
 * might also include the original neighbor, but you only want to visit the
 * original neighbor once.)
 * <p>
 * If a neighbor has already been visited (determined by using
 * <code>equals(Object)</code>), that neighbor is not visited again, nor are the
 * neighbors of that object.
 * <p>
 * It is up to the user of this class to ensure a *complete* graph.
 * <p>
 * To use, supply:
 * <ul>
 * <li>either the initial node of the graph or an Iterator over an initial
 * collection of graph nodes
 * <li>a <code>MisterRogers</code> that tells who the neighbors are of each node
 * (alternatively, subclass <code>GraphIterator</code> and override the
 * <code>neighbors(Object)</code> method)
 * </ul>
 * <p>
 * <code>remove()</code> is not supported. This method, if desired, must be
 * implemented by the user of this class.
 *
 * @author mqfdy
 * @param <E>
 *            the element type
 */
public class GraphIterator<E>
	implements Iterator<E>
{
	private final Collection<Iterator<? extends E>> iterators;
	private final Set<E> visitedNeighbors;
	private final MisterRogers<E> misterRogers;
	
	private Iterator<? extends E> currentIterator;
	
	private E nextNeighbor;
	private boolean done;


	/**
	 * Construct an iterator with the specified collection of roots and a
	 * disabled mister rogers. Use this constructor if you want to override the
	 * <code>children(Object)</code> method instead of building a
	 * <code>MisterRogers</code>.
	 *
	 * @param roots
	 *            the roots
	 */
	public GraphIterator(E... roots) {
		this(new ArrayIterator<E>(roots));
	}

	/**
	 * Construct an iterator with the specified collection of roots and a
	 * disabled mister rogers. Use this constructor if you want to override the
	 * <code>children(Object)</code> method instead of building a
	 * <code>MisterRogers</code>.
	 *
	 * @param roots
	 *            the roots
	 */
	public GraphIterator(Iterator<? extends E> roots) {
		this(roots, MisterRogers.Disabled.<E>instance());
	}

	/**
	 * Construct an iterator with the specified root and a disabled mister
	 * rogers. Use this constructor if you want to override the
	 * <code>children(Object)</code> method instead of building a
	 * <code>MisterRogers</code>.
	 *
	 * @param root
	 *            the root
	 */
	public GraphIterator(E root) {
		this(root, MisterRogers.Disabled.<E>instance());
	}

	/**
	 * Construct an iterator with the specified root and mister rogers.
	 *
	 * @param root
	 *            the root
	 * @param misterRogers
	 *            the mister rogers
	 */
	public GraphIterator(E root, MisterRogers<E> misterRogers) {
		this(new SingleElementIterator<E>(root), misterRogers);
	}

	/**
	 * Construct an iterator with the specified roots and mister rogers.
	 *
	 * @param roots
	 *            the roots
	 * @param misterRogers
	 *            the mister rogers
	 */
	public GraphIterator(Iterator<? extends E> roots, MisterRogers<E> misterRogers) {
		super();
		this.currentIterator = roots;
		// use a LinkedList since we will be pulling off the front and adding to the end
		this.iterators = new LinkedList<Iterator<? extends E>>();
		this.misterRogers = misterRogers;
		this.visitedNeighbors = new HashSet<E>();
		this.loadNextNeighbor();
	}

	/**
	 * Load next neighbor with the next entry from the current iterator.
	 * If the current iterator has none, load the next iterator.
	 * If there are no more, the 'done' flag is set.
	 */
	private void loadNextNeighbor() {
		if (this.currentIterator == EmptyIterator.instance()) {
			this.done = true;
		}
		else if (this.currentIterator.hasNext()) {
			E nextPossibleNeighbor = this.currentIterator.next();
			if (this.visitedNeighbors.contains(nextPossibleNeighbor)) {
				this.loadNextNeighbor();  // recurse
			} else {
				this.nextNeighbor = nextPossibleNeighbor;
				this.visitedNeighbors.add(nextPossibleNeighbor);
				this.iterators.add(this.neighbors(nextPossibleNeighbor));
			}
		} 
		else {
			for (Iterator<? extends Iterator<? extends E>> stream = this.iterators.iterator(); ! this.currentIterator.hasNext() && stream.hasNext(); ) {
				this.currentIterator = stream.next();
				stream.remove();
			}
			if ( ! this.currentIterator.hasNext()) {
				this.currentIterator = EmptyIterator.instance();
			}
			this.loadNextNeighbor();  // recurse
		}
	}

	public boolean hasNext() {
		return ! this.done;
	}

	public E next() {
		if (this.done) {
			throw new NoSuchElementException();
		}
		E next = this.nextNeighbor;
		this.loadNextNeighbor();
		return next;
	}

	public void remove() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Return the immediate neighbors of the specified object.
	 *
	 * @author mqfdy
	 * @param next
	 *            the next
	 * @return the iterator<? extends e>
	 * @Date 2018-09-03 09:00
	 */
	protected Iterator<? extends E> neighbors(E next) {
		return this.misterRogers.neighbors(next);
	}

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, this.currentIterator);
	}
	
	
	//********** inner classes **********
	
	/**
	 * Used by <code>GraphIterator</code> to retrieve the immediate neighbors of
	 * a node in the graph. "These are the people in your neighborhood..."
	 *
	 * @author mqfdy
	 * @param <T>
	 *            the generic type
	 */
	public interface MisterRogers<T> {

		/**
		 * Return the immediate neighbors of the specified object.
		 */
		Iterator<? extends T> neighbors(T next);
		
		
		final class Null<S> implements MisterRogers<S> {
			@SuppressWarnings("unchecked")
			public static final MisterRogers INSTANCE = new Null();
			@SuppressWarnings("unchecked")
			public static <R> MisterRogers<R> instance() {
				return INSTANCE;
			}
			// ensure single instance
			private Null() {
				super();
			}
			// return no neighbors
			public Iterator<S> neighbors(S next) {
				return EmptyIterator.instance();
			}
			@Override
			public String toString() {
				return "GraphIterator.MisterRogers.Null";
			}
		}

		/** The mister rogers used when the #neighbors(Object) method is overridden. */
		final class Disabled<S> implements MisterRogers<S> {
			@SuppressWarnings("unchecked")
			public static final MisterRogers INSTANCE = new Disabled();
			@SuppressWarnings("unchecked")
			public static <R> MisterRogers<R> instance() {
				return INSTANCE;
			}
			// ensure single instance
			private Disabled() {
				super();
			}
			// throw an exception
			public Iterator<S> neighbors(S next) {
				throw new UnsupportedOperationException();  // GraphIterator.neighbors(Object) was not implemented
			}
			@Override
			public String toString() {
				return "GraphIterator.MisterRogers.Disabled";
			}
		}

	}

}
