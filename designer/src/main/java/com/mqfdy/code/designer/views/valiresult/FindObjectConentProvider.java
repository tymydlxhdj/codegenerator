package com.mqfdy.code.designer.views.valiresult;

import java.util.Collection;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

// TODO: Auto-generated Javadoc
/**
 * The Class FindObjectConentProvider.
 *
 * @author mqfdy
 */
public class FindObjectConentProvider implements IStructuredContentProvider {
	
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
	}

	/**
	 * Gets the elements.
	 *
	 * @author mqfdy
	 * @param inputElement
	 *            the input element
	 * @return the elements
	 * @Date 2018-09-03 09:00
	 */
	@SuppressWarnings("rawtypes")
	public Object[] getElements(Object inputElement) {
		if (inputElement instanceof Collection) {
			Object[] objects = ((Collection) inputElement).toArray();
			return objects;
		}
		return new Object[0];
	}
}
