package com.mqfdy.code.reverse.views.providers;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;

import com.mqfdy.code.reverse.views.models.DataSourceTableModel;

public class DataSourceTableContentProvider implements IStructuredContentProvider,
		PropertyChangeListener {

	private TableViewer viewer;

	private DataSourceTableModel model;

	public Object[] getElements(Object inputElement) {
		return model.elements();
	}

	public void dispose() {
	}

	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		this.viewer = (TableViewer) viewer;

		if (oldInput instanceof DataSourceTableModel)
			((DataSourceTableModel) oldInput).removePropertyChangeListener(this);

		if (newInput instanceof DataSourceTableModel) {
			this.model = (DataSourceTableModel) newInput;
			((DataSourceTableModel) newInput).addPropertyChangeListener(this);
		}
	}

	public void propertyChange(PropertyChangeEvent evt) {
		if (DataSourceTableModel.ADD_ELEMENT.equals(evt.getPropertyName()))
			viewer.add(evt.getNewValue());
		if (DataSourceTableModel.REMOVE_ELEMENT.equals(evt.getPropertyName()))
			viewer.remove(evt.getNewValue());
	}
}
