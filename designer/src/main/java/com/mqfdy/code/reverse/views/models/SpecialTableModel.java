package com.mqfdy.code.reverse.views.models;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

import com.mqfdy.code.reverse.views.beans.SpecialTable;

// TODO: Auto-generated Javadoc
/**
 * The Class SpecialTableModel.
 *
 * @author mqfdy
 */
public class SpecialTableModel {

	/** The Constant ADD_ELEMENT. */
	public static final String ADD_ELEMENT = "addElement";

	/** The Constant REMOVE_ELEMENT. */
	public static final String REMOVE_ELEMENT = "removeElement";

	/** The delegate. */
	private PropertyChangeSupport delegate;

	/** The content. */
	private List<SpecialTable> content;

	/**
	 * Instantiates a new special table model.
	 */
	public SpecialTableModel() {
		content = new ArrayList<SpecialTable>();
		delegate = new PropertyChangeSupport(this);
	}

	/**
	 * Adds the property change listener.
	 *
	 * @author mqfdy
	 * @param listener
	 *            the listener
	 * @Date 2018-09-03 09:00
	 */
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		delegate.addPropertyChangeListener(listener);
	}

	/**
	 * Fire property change.
	 *
	 * @author mqfdy
	 * @param evt
	 *            the evt
	 * @Date 2018-09-03 09:00
	 */
	public void firePropertyChange(PropertyChangeEvent evt) {
		delegate.firePropertyChange(evt);
	}

	/**
	 * Removes the property change listener.
	 *
	 * @author mqfdy
	 * @param listener
	 *            the listener
	 * @Date 2018-09-03 09:00
	 */
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		delegate.removePropertyChangeListener(listener);
	}

	/**
	 * Adds the.
	 *
	 * @author mqfdy
	 * @param element
	 *            the element
	 * @Date 2018-09-03 09:00
	 */
	public void add(Object element) {
		if (content.add((SpecialTable) element))
			firePropertyChange(new PropertyChangeEvent(this, ADD_ELEMENT, null, element));
	}

	/**
	 * Removes the.
	 *
	 * @author mqfdy
	 * @param element
	 *            the element
	 * @Date 2018-09-03 09:00
	 */
	public void remove(Object element) {
		if (content.remove(element))
			firePropertyChange(new PropertyChangeEvent(this, REMOVE_ELEMENT,
					null, element));
	}

	/**
	 * Elements.
	 *
	 * @author mqfdy
	 * @return the object[]
	 * @Date 2018-09-03 09:00
	 */
	public Object[] elements() {
		return content.toArray();
	}
}
