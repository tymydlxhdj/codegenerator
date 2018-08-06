package com.mqfdy.code.reverse.views.providers;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;

import com.mqfdy.code.reverse.views.models.SpecialTableModel;

public class TableContentProvider implements IStructuredContentProvider,
		PropertyChangeListener {

	private TableViewer viewer;

	private SpecialTableModel model;

	public Object[] getElements(Object inputElement) {
		return model.elements();
	}

	public void dispose() {
	}

	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		this.viewer = (TableViewer) viewer;

		if (oldInput instanceof SpecialTableModel)
			((SpecialTableModel) oldInput).removePropertyChangeListener(this);

		if (newInput instanceof SpecialTableModel) {
			this.model = (SpecialTableModel) newInput;
			((SpecialTableModel) newInput).addPropertyChangeListener(this);
		}
	}

	public void propertyChange(PropertyChangeEvent evt) {
		if (SpecialTableModel.ADD_ELEMENT.equals(evt.getPropertyName()))
			viewer.add(evt.getNewValue());
		if (SpecialTableModel.REMOVE_ELEMENT.equals(evt.getPropertyName()))
			viewer.remove(evt.getNewValue());
	}
}
