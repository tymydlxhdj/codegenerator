package com.mqfdy.code.reverse.views.models;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

import com.mqfdy.code.reverse.views.beans.BizBean;

// TODO: Auto-generated Javadoc
/**
 * The Class BizTableModel.
 *
 * @author mqfdy
 */
public class BizTableModel {

	/** The Constant ADD_ELEMENT. */
	public static final String ADD_ELEMENT = "addElement";

	/** The Constant REMOVE_ELEMENT. */
	public static final String REMOVE_ELEMENT = "removeElement";

	/** The delegate. */
	private PropertyChangeSupport delegate;

	/** The content. */
	private List<BizBean> content;

	/**
	 * Instantiates a new biz table model.
	 */
	public BizTableModel() {
		content = new ArrayList<BizBean>();
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
		if (content.add((BizBean) element))
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
