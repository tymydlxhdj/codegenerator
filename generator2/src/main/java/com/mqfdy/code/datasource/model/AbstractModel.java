package com.mqfdy.code.datasource.model;

import org.eclipse.core.runtime.ListenerList;

// TODO: Auto-generated Javadoc
/**
 * The Class AbstractModel.
 *
 * @author mqfdy
 */
public abstract class AbstractModel extends AbstractModelElement implements
		IModel {

	/** The listeners. */
	private ListenerList<IModelChangeListener> listeners;

	/**
	 * Instantiates a new abstract model.
	 *
	 * @param parent
	 *            the parent
	 * @param name
	 *            the name
	 */
	public AbstractModel(IModelElement parent, String name) {
		super(parent, name);
		listeners = new ListenerList<IModelChangeListener>();
	}

	/**
	 * Gets the element type.
	 *
	 * @return AbstractModel
	 * @see com.mqfdy.code.datasource.model.IModelElement#getElementType()
	 */
	public int getElementType() {
		return IModelElementTypes.MODEL_TYPE;
	}

	/**
	 * Adds the change listener.
	 *
	 * @param listener
	 *            AbstractModel
	 * @see com.mqfdy.code.datasource.model.IModel#addChangeListener(com.mqfdy.code.datasource.model.IModelChangeListener)
	 */
	public final void addChangeListener(IModelChangeListener listener) {
		listeners.add(listener);
	}

	/**
	 * Removes the change listener.
	 *
	 * @param listener
	 *            AbstractModel
	 * @see com.mqfdy.code.datasource.model.IModel#removeChangeListener(com.mqfdy.code.datasource.model.IModelChangeListener)
	 */
	public final void removeChangeListener(IModelChangeListener listener) {
		listeners.remove(listener);
	}

	/**
	 * Notify listeners.
	 *
	 * @author mqfdy
	 * @param element
	 *            the element
	 * @param type
	 *            the type
	 * @Date 2018-09-03 09:00
	 */
	public final void notifyListeners(IModelElement element, int type) {
		ModelChangeEvent event = new ModelChangeEvent(element, type);
		for (Object listener : listeners.getListeners()) {
			((IModelChangeListener) listener).elementChanged(event);
		}
	}
	

	/**
	 * Gets the adapter.
	 *
	 * @param <T>
	 *            the generic type
	 * @param adapter
	 *            the adapter
	 * @return AbstractModel
	 * @see org.eclipse.core.runtime.IAdaptable#getAdapter(java.lang.Class)
	 */
	public <T> T getAdapter(Class<T> adapter) {
		return null;
	}
}
