package com.mqfdy.code.datasource.model;

import org.eclipse.core.runtime.ListenerList;

public abstract class AbstractModel extends AbstractModelElement implements
		IModel {

	private ListenerList<IModelChangeListener> listeners;

	public AbstractModel(IModelElement parent, String name) {
		super(parent, name);
		listeners = new ListenerList<IModelChangeListener>();
	}

	public int getElementType() {
		return IModelElementTypes.MODEL_TYPE;
	}

	public final void addChangeListener(IModelChangeListener listener) {
		listeners.add(listener);
	}

	public final void removeChangeListener(IModelChangeListener listener) {
		listeners.remove(listener);
	}

	public final void notifyListeners(IModelElement element, int type) {
		ModelChangeEvent event = new ModelChangeEvent(element, type);
		for (Object listener : listeners.getListeners()) {
			((IModelChangeListener) listener).elementChanged(event);
		}
	}
	

	public <T> T getAdapter(Class<T> adapter) {
		return null;
	}
}
