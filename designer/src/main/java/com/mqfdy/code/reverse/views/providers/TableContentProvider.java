package com.mqfdy.code.reverse.views.providers;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;

import com.mqfdy.code.reverse.views.models.SpecialTableModel;

// TODO: Auto-generated Javadoc
/**
 * The Class TableContentProvider.
 *
 * @author mqfdy
 */
public class TableContentProvider implements IStructuredContentProvider,
		PropertyChangeListener {

	/** The viewer. */
	private TableViewer viewer;

	/** The model. */
	private SpecialTableModel model;

	/**
	 * Gets the elements.
	 *
	 * @author mqfdy
	 * @param inputElement
	 *            the input element
	 * @return the elements
	 * @Date 2018-09-03 09:00
	 */
	public Object[] getElements(Object inputElement) {
		return model.elements();
	}

	/**
	 * 
	 */
	public void dispose() {
	}

	/**
	 * Input changed.
	 *
	 * @author mqfdy
	 * @param viewer
	 *            the viewer
	 * @param oldInput
	 *            the old input
	 * @param newInput
	 *            the new input
	 * @Date 2018-09-03 09:00
	 */
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		this.viewer = (TableViewer) viewer;

		if (oldInput instanceof SpecialTableModel)
			((SpecialTableModel) oldInput).removePropertyChangeListener(this);

		if (newInput instanceof SpecialTableModel) {
			this.model = (SpecialTableModel) newInput;
			((SpecialTableModel) newInput).addPropertyChangeListener(this);
		}
	}

	/**
	 * Property change.
	 *
	 * @author mqfdy
	 * @param evt
	 *            the evt
	 * @Date 2018-09-03 09:00
	 */
	public void propertyChange(PropertyChangeEvent evt) {
		if (SpecialTableModel.ADD_ELEMENT.equals(evt.getPropertyName()))
			viewer.add(evt.getNewValue());
		if (SpecialTableModel.REMOVE_ELEMENT.equals(evt.getPropertyName()))
			viewer.remove(evt.getNewValue());
	}
}
