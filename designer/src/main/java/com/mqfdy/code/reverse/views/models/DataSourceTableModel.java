package com.mqfdy.code.reverse.views.models;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

import com.mqfdy.code.reverse.DataSourceInfo;

// TODO: Auto-generated Javadoc
/**
 * The Class DataSourceTableModel.
 *
 * @author mqfdy
 */
public class DataSourceTableModel {

	/** The Constant ADD_ELEMENT. */
	public static final String ADD_ELEMENT = "addElement";

	/** The Constant REMOVE_ELEMENT. */
	public static final String REMOVE_ELEMENT = "removeElement";
	
	/** The Constant UPDATE_ELEMENT. */
	public static final String UPDATE_ELEMENT = "removeElement";

	/** The delegate. */
	private PropertyChangeSupport delegate;

	/** The content. */
	private List<DataSourceInfo> content;

	/**
	 * Instantiates a new data source table model.
	 */
	public DataSourceTableModel() {
		content = new ArrayList<DataSourceInfo>();
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
		if (content.add((DataSourceInfo) element))
			firePropertyChange(new PropertyChangeEvent(this, ADD_ELEMENT, null, element));
	}
	
	/**
	 * Adds the.
	 *
	 * @author mqfdy
	 * @param index
	 *            the index
	 * @param element
	 *            the element
	 * @Date 2018-09-03 09:00
	 */
	public void add(int index, Object element) {
		content.add(index, (DataSourceInfo) element);
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
			firePropertyChange(new PropertyChangeEvent(this, REMOVE_ELEMENT, null, element));
	}
	
	/**
	 * Update.
	 *
	 * @author mqfdy
	 * @param oldElement
	 *            the old element
	 * @param newElement
	 *            the new element
	 * @Date 2018-09-03 09:00
	 */
	public void update(DataSourceInfo oldElement, DataSourceInfo newElement) {
		for(DataSourceInfo element: content) {
			if(element.equals(oldElement)) {
				element.setDataSourceName(newElement.getDataSourceName());
				element.setUapName(newElement.getUapName());
				element.setDbType(newElement.getDbType());
				element.setDriverClass(newElement.getDriverClass());
				element.setIp(newElement.getIp());
				element.setPort(newElement.getPort());
				element.setPwd(newElement.getPwd());
				element.setSid(newElement.getSid());
				element.setUrl(newElement.getUrl());
				element.setUserName(newElement.getUserName());
				break;
			}
		}
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
