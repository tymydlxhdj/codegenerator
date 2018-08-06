package com.mqfdy.code.springboot.ui.wizards;

import java.io.File;
import java.util.List;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

/**
 * This is a content provider providing a tree of Springboot projects.
 * @author lenovo
 */
public class ProjectTreeContentProvider implements ITreeContentProvider {

	/**
	 * Gets the children of the specified object
	 * 
	 * @param arg0
	 *            the parent object
	 * @return Object[]
	 */
	public Object[] getChildren(Object arg) {
		return null;
	}

	/**
	 * Gets the parent of the specified object
	 * 
	 * @param arg0
	 *            the object
	 * @return Object
	 */
	public Object getParent(Object arg) {
		return null;
	}

	/**
	 * Returns whether the passed object has children
	 * 
	 * @param arg0
	 *            the parent object
	 * @return boolean
	 */
	public boolean hasChildren(Object arg0) {
		return false;
	}

	/**
	 * Gets the root element(s) of the tree
	 * 
	 * @param arg0
	 *            the input data
	 * @return Object[]
	 */
	public Object[] getElements(Object input) {
		if (input!=null) {
			if (input instanceof List) {
				List<?> list = (List<?>) input;
				if(list.size() != 0 ){
					return list.toArray(new File[list.size()]);
				}
			}
		}
		return new Object[0];
	}

	/**
	 * Disposes any created resources
	 */
	public void dispose() {
		// Nothing to dispose
	}

	/**
	 * Called when the input (root folder) changes 
	 */
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
	}
}
