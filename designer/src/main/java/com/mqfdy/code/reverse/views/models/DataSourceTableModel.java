package com.mqfdy.code.reverse.views.models;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

import com.mqfdy.code.reverse.DataSourceInfo;

public class DataSourceTableModel {

	public static final String ADD_ELEMENT = "addElement";

	public static final String REMOVE_ELEMENT = "removeElement";
	
	public static final String UPDATE_ELEMENT = "removeElement";

	private PropertyChangeSupport delegate;

	private List<DataSourceInfo> content;

	public DataSourceTableModel() {
		content = new ArrayList<DataSourceInfo>();
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
		if (content.add((DataSourceInfo) element))
			firePropertyChange(new PropertyChangeEvent(this, ADD_ELEMENT, null, element));
	}
	
	public void add(int index, Object element) {
		content.add(index, (DataSourceInfo) element);
		firePropertyChange(new PropertyChangeEvent(this, ADD_ELEMENT, null, element));
	}

	public void remove(Object element) {
		if (content.remove(element))
			firePropertyChange(new PropertyChangeEvent(this, REMOVE_ELEMENT, null, element));
	}
	
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

	public Object[] elements() {
		return content.toArray();
	}
}
