package com.mqfdy.code.reverse.views.models;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

import com.mqfdy.code.reverse.views.beans.BizBean;

public class BizTableModel {

	public static final String ADD_ELEMENT = "addElement";

	public static final String REMOVE_ELEMENT = "removeElement";

	private PropertyChangeSupport delegate;

	private List<BizBean> content;

	public BizTableModel() {
		content = new ArrayList<BizBean>();
		delegate = new PropertyChangeSupport(this);
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		delegate.addPropertyChangeListener(listener);
	}

	public void firePropertyChange(PropertyChangeEvent evt) {
		delegate.firePropertyChange(evt);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		delegate.removePropertyChangeListener(listener);
	}

	public void add(Object element) {
		if (content.add((BizBean) element))
			firePropertyChange(new PropertyChangeEvent(this, ADD_ELEMENT, null, element));
	}

	public void remove(Object element) {
		if (content.remove(element))
			firePropertyChange(new PropertyChangeEvent(this, REMOVE_ELEMENT,
					null, element));
	}

	public Object[] elements() {
		return content.toArray();
	}
}
