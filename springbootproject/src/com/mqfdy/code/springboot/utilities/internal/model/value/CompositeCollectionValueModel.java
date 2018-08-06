/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package com.mqfdy.code.springboot.utilities.internal.model.value;

import java.util.ArrayList;
import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;

import com.mqfdy.code.springboot.utilities.internal.CollectionTools;
import com.mqfdy.code.springboot.utilities.internal.NullList;
import com.mqfdy.code.springboot.utilities.internal.Transformer;
import com.mqfdy.code.springboot.utilities.internal.iterators.CompositeIterator;
import com.mqfdy.code.springboot.utilities.internal.iterators.TransformationIterator;
import com.mqfdy.code.springboot.utilities.model.event.CollectionChangeEvent;
import com.mqfdy.code.springboot.utilities.model.listener.CollectionChangeListener;
import com.mqfdy.code.springboot.utilities.model.value.CollectionValueModel;
import com.mqfdy.code.springboot.utilities.model.value.ListValueModel;



/**
 * A <code>CompositeCollectionValueModel</code> wraps another
 * <code>CollectionValueModel</code> and uses a <code>Transformer</code>
 * to convert each item in the wrapped collection to yet another
 * <code>CollectionValueModel</code>. This composite collection contains
 * the combined items from all these component collections.
 * 
 * NB: The wrapped collection must be an "identity set" that does not
 * contain the same item twice or this class will throw an exception.
 * 
 * Terminology:
 * - sources - the items in the wrapped collection value model; these
 *    are converted into component CVMs by the transformer
 * - component CVMs - the component collection value models that are combined
 *    by this composite collection value model
 * - items - the items held by the component CVMs
 */
public class CompositeCollectionValueModel<E1, E2>
	extends CollectionValueModelWrapper<E1>
	implements CollectionValueModel<E2>
{
	/**
	 * This is the (optional) user-supplied object that transforms
	 * the items in the wrapped collection to collection value models.
	 */
	private final Transformer<E1, CollectionValueModel<E2>> transformer;

	/**
	 * Cache of the component collection value models that
	 * were generated by the transformer; keyed by the item
	 * in the wrapped collection that was passed to the transformer.
	 */
	private final IdentityHashMap<E1, CollectionValueModel<E2>> componentCVMs;

	/**
	 * Cache of the collections corresponding to the component
	 * collection value models above; keyed by the component
	 * collection value models.
	 * Use ArrayLists so we can use ArrayList-specific methods
	 * (e.g. #clone() and #ensureCapacity()).
	 */
	private final IdentityHashMap<CollectionValueModel<E2>, ArrayList<E2>> collections;

	/** Listener that listens to all the component collection value models. */
	private final CollectionChangeListener componentCVMListener;

	/** Cache the size of the composite collection. */
	private int size;


	// ********** constructors **********

	/**
	 * Construct a collection value model with the specified wrapped
	 * collection value model. Use this constructor if
	 *     - the wrapped collection value model already contains other
	 *       collection value models or
	 *     - you want to override the <code>transform(E1)</code> method
	 *       instead of building a <code>Transformer</code>
	 */
	public CompositeCollectionValueModel(CollectionValueModel<? extends E1> collectionHolder) {
		this(collectionHolder, Transformer.Null.<E1, CollectionValueModel<E2>>instance());
	}

	/**
	 * Construct a collection value model with the specified wrapped
	 * collection value model and transformer.
	 */
	public CompositeCollectionValueModel(CollectionValueModel<? extends E1> collectionHolder, Transformer<E1, CollectionValueModel<E2>> transformer) {
		super(collectionHolder);
		this.transformer = transformer;
		this.componentCVMs = new IdentityHashMap<E1, CollectionValueModel<E2>>();
		this.collections = new IdentityHashMap<CollectionValueModel<E2>, ArrayList<E2>>();
		this.componentCVMListener = this.buildComponentListener();
		this.size = 0;
	}

	/**
	 * Construct a collection value model with the specified wrapped
	 * list value model. Use this constructor if
	 *     - the wrapped list value model already contains collection
	 *       value models or
	 *     - you want to override the <code>transform(E1)</code> method
	 *       instead of building a <code>Transformer</code>
	 */
	public CompositeCollectionValueModel(ListValueModel<? extends E1> listHolder) {
		this(new ListCollectionValueModelAdapter<E1>(listHolder));
	}

	/**
	 * Construct a collection value model with the specified wrapped
	 * list value model and transformer.
	 */
	public CompositeCollectionValueModel(ListValueModel<? extends E1> listHolder, Transformer<E1, CollectionValueModel<E2>> transformer) {
		this(new ListCollectionValueModelAdapter<E1>(listHolder), transformer);
	}

	/**
	 * Construct a collection value model with the specified, unchanging, wrapped
	 * collection. Use this constructor if
	 *     - the wrapped collection already contains collection
	 *       value models or
	 *     - you want to override the <code>transform(E1)</code> method
	 *       instead of building a <code>Transformer</code>
	 */
	public CompositeCollectionValueModel(Collection<? extends E1> collection) {
		this(new StaticCollectionValueModel<E1>(collection));
	}

	/**
	 * Construct a collection value model with the specified, unchanging, wrapped
	 * collection and transformer.
	 */
	public CompositeCollectionValueModel(Collection<? extends E1> collection, Transformer<E1, CollectionValueModel<E2>> transformer) {
		this(new StaticCollectionValueModel<E1>(collection), transformer);
	}


	// ********** initialization **********

	protected CollectionChangeListener buildComponentListener() {
		return new CollectionChangeListener() {
			public void itemsAdded(CollectionChangeEvent event) {
				CompositeCollectionValueModel.this.componentItemsAdded(event);
			}		
			public void itemsRemoved(CollectionChangeEvent event) {
				CompositeCollectionValueModel.this.componentItemsRemoved(event);
			}
			public void collectionCleared(CollectionChangeEvent event) {
				CompositeCollectionValueModel.this.componentCollectionCleared(event);
			}
			public void collectionChanged(CollectionChangeEvent event) {
				CompositeCollectionValueModel.this.componentCollectionChanged(event);
			}
			@Override
			public String toString() {
				return "component listener";
			}
		};
	}


	// ********** CollectionValueModel implementation **********

	public Iterator<E2> iterator() {
		return new CompositeIterator<E2>(this.buildCollectionsIterators());
	}

	protected Iterator<Iterator<E2>> buildCollectionsIterators() {
		return new TransformationIterator<ArrayList<E2>, Iterator<E2>>(this.collections.values().iterator()) {
			@Override
			protected Iterator<E2> transform(ArrayList<E2> next) {
				return next.iterator();
			}
		};
	}

	public int size() {
		return this.size;
	}


	// ********** CollectionValueModelWrapper overrides/implementation **********

	@Override
	protected void engageModel() {
		super.engageModel();
		// synch our cache *after* we start listening to the wrapped collection,
		// since its value might change when a listener is added
		this.addAllComponentSources();
	}

	/**
	 * Transform all the sources to collection value models
	 * and add their items to our cache, with no event notification.
	 */
	protected void addAllComponentSources() {
		for (E1 source : this.collectionHolder) {
			this.addComponentSource(source, NullList.<E2>instance());
		}
	}

	@Override
	protected void disengageModel() {
		super.disengageModel();
		// stop listening to the components...
		for (CollectionValueModel<E2> componentCVM : this.componentCVMs.values()) {
			componentCVM.removeCollectionChangeListener(VALUES, this.componentCVMListener);
		}
		// ...and clear the cache
		this.componentCVMs.clear();
		this.collections.clear();
		this.size = 0;
	}

	/**
	 * Some component sources were added;
	 * add their corresponding items to our cache.
	 */
	@Override
	protected void itemsAdded(CollectionChangeEvent event) {
		ArrayList<E2> addedItems = new ArrayList<E2>();
		for (Iterator<E1> stream = this.items(event); stream.hasNext(); ) {
			this.addComponentSource(stream.next(), addedItems);
		}
		this.fireItemsAdded(VALUES, addedItems);
	}

	/**
	 * Transform the specified source to a collection value model
	 * and add its items to our cache and the "collecting parameter".
	 */
	protected void addComponentSource(E1 source, List<E2> addedItems) {
		CollectionValueModel<E2> componentCVM = this.transform(source);
		if (this.componentCVMs.put(source, componentCVM) != null) {
			throw new IllegalStateException("duplicate component: " + source);
		}
		componentCVM.addCollectionChangeListener(VALUES, this.componentCVMListener);
		ArrayList<E2> componentCollection = new ArrayList<E2>(componentCVM.size());
		if (this.collections.put(componentCVM, componentCollection) != null) {
			throw new IllegalStateException("duplicate collection: " + source);
		}
		this.addComponentItems(componentCVM, componentCollection);
		addedItems.addAll(componentCollection);
	}

	/**
	 * Add the items in the specified component CVM to the specified component
	 * collection.
	 */
	protected void addComponentItems(CollectionValueModel<E2> componentCVM, ArrayList<E2> componentCollection) {
		int itemsSize = componentCVM.size();
		this.size += itemsSize;
		componentCollection.ensureCapacity(componentCollection.size() + itemsSize);
		CollectionTools.addAll(componentCollection, componentCVM);
	}

	/**
	 * Some component sources were removed;
	 * remove their corresponding items from our cache.
	 */
	@Override
	protected void itemsRemoved(CollectionChangeEvent event) {
		ArrayList<E2> removedItems = new ArrayList<E2>();
		for (Iterator<E1> stream = this.items(event); stream.hasNext(); ) {
			this.removeComponentSource(stream.next(), removedItems);
		}
		this.fireItemsRemoved(VALUES, removedItems);
	}

	/**
	 * Remove the items corresponding to the specified source
	 * from our cache.
	 */
	protected void removeComponentSource(E1 source, List<E2> removedItems) {
		CollectionValueModel<E2> componentCVM = this.componentCVMs.remove(source);
		if (componentCVM == null) {
			throw new IllegalStateException("missing component: " + source);
		}
		componentCVM.removeCollectionChangeListener(VALUES, this.componentCVMListener);
		ArrayList<E2> componentCollection = this.collections.remove(componentCVM);
		if (componentCollection == null) {
			throw new IllegalStateException("missing collection: " + source);
		}
		removedItems.addAll(componentCollection);
		this.removeComponentItems(componentCollection);
	}

	/**
	 * Update our size and collection cache.
	 */
	protected void removeComponentItems(ArrayList<E2> componentCollection) {
		this.size -= componentCollection.size();
		componentCollection.clear();
	}

	/**
	 * The component sources cleared;
	 * clear our cache.
	 */
	@Override
	protected void collectionCleared(CollectionChangeEvent event) {
		this.removeAllComponentSources();
		this.fireCollectionCleared(VALUES);
	}

	protected void removeAllComponentSources() {
		// copy the keys so we don't eat our own tail
		ArrayList<E1> copy = new ArrayList<E1>(this.componentCVMs.keySet());
		for (E1 source : copy) {
			this.removeComponentSource(source, NullList.<E2>instance());
		}
	}

	/**
	 * The component sources changed;
	 * rebuild our cache.
	 */
	@Override
	protected void collectionChanged(CollectionChangeEvent event) {
		this.removeAllComponentSources();
		this.addAllComponentSources();
		this.fireCollectionChanged(VALUES);
	}


	// ********** internal methods **********

	/**
	 * Transform the specified object into a collection value model.
	 * <p>
	 * This method can be overridden by a subclass as an
	 * alternative to building a <code>Transformer</code>.
	 */
	protected CollectionValueModel<E2> transform(E1 value) {
		return this.transformer.transform(value);
	}

	/**
	 * One of the component collections had items added;
	 * synchronize our caches.
	 */
	protected void componentItemsAdded(CollectionChangeEvent event) {
		int itemsSize = event.itemsSize();
		this.size += itemsSize;

		ArrayList<E2> componentCollection = this.collections.get(this.componentCVM(event));
		componentCollection.ensureCapacity(componentCollection.size() + itemsSize);

		this.addItemsToCollection(this.componentItems(event), componentCollection, VALUES);
	}

	/**
	 * One of the component collections had items removed;
	 * synchronize our caches.
	 */
	protected void componentItemsRemoved(CollectionChangeEvent event) {
		this.size -= event.itemsSize();
		ArrayList<E2> componentCollection = this.collections.get(this.componentCVM(event));
		this.removeItemsFromCollection(this.componentItems(event), componentCollection, VALUES);
	}

	/**
	 * One of the component collections was cleared;
	 * synchronize our caches by clearing out the appropriate
	 * collection.
	 */
	protected void componentCollectionCleared(CollectionChangeEvent event) {
		ArrayList<E2> componentCollection = this.collections.get(this.componentCVM(event));
		ArrayList<E2> removedItems = new ArrayList<E2>(componentCollection);
		this.removeComponentItems(componentCollection);
		this.fireItemsRemoved(VALUES, removedItems);
	}

	/**
	 * One of the component collections changed;
	 * synchronize our caches by clearing out the appropriate
	 * collection and then rebuilding it.
	 */
	protected void componentCollectionChanged(CollectionChangeEvent event) {
		CollectionValueModel<E2> componentCVM = this.componentCVM(event);
		ArrayList<E2> componentCollection = this.collections.get(componentCVM);
		this.removeComponentItems(componentCollection);
		this.addComponentItems(componentCVM, componentCollection);
		this.fireCollectionChanged(VALUES);
	}

	// minimize scope of suppressed warnings
	@SuppressWarnings("unchecked")
	protected Iterator<E2> componentItems(CollectionChangeEvent event) {
		return (Iterator<E2>) event.items();
	}

	// minimize scope of suppressed warnings
	@SuppressWarnings("unchecked")
	protected CollectionValueModel<E2> componentCVM(CollectionChangeEvent event) {
		return (CollectionValueModel<E2>) event.getSource();
	}

}