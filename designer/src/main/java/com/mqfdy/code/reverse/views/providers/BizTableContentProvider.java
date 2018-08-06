package com.mqfdy.code.reverse.views.providers;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;

import com.mqfdy.code.reverse.views.models.BizTableModel;

public class BizTableContentProvider implements IStructuredContentProvider,
		PropertyChangeListener {

	private TableViewer viewer;

	private BizTableModel model;

	public Object[] getElements(Object inputElement) {
		return model.elements();
	}

	public void dispose() {
	}

	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		this.viewer = (TableViewer) viewer;

		if (oldInput instanceof BizTableModel)
			((BizTableModel) oldInput).removePropertyChangeListener(this);

		if (newInput instanceof BizTableModel) {
			this.model = (BizTableModel) newInput;
			((BizTableModel) newInput).addPropertyChangeListener(this);
		}
	}

	public void propertyChange(PropertyChangeEvent evt) {
		if (BizTableModel.ADD_ELEMENT.equals(evt.getPropertyName()))
			viewer.add(evt.getNewValue());
		if (BizTableModel.REMOVE_ELEMENT.equals(evt.getPropertyName()))
			viewer.remove(evt.getNewValue());
	}
}
