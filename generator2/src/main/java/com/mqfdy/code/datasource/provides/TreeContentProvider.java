package com.mqfdy.code.datasource.provides;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;

import com.mqfdy.code.datasource.mapping.TreeModel;
import com.mqfdy.code.datasource.mapping.TreeNode;

// TODO: Auto-generated Javadoc
/**
 * The Class TreeContentProvider.
 *
 * @author mqfdy
 */
public class TreeContentProvider implements ITreeContentProvider, PropertyChangeListener {

	/** The viewer. */
	private TreeViewer viewer;
	
	/** The model. */
	private TreeModel model;
	
	/**
	 * Dispose.
	 *
	 * @see org.eclipse.jface.viewers.IContentProvider#dispose()
	 *      TreeContentProvider
	 */
	public void dispose() {

	}

	/**
	 * Input changed.
	 *
	 * @param viewer
	 *            the viewer
	 * @param oldInput
	 *            the old input
	 * @param newInput
	 *            TreeContentProvider
	 * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer,
	 *      java.lang.Object, java.lang.Object)
	 */
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		this.viewer = (TreeViewer) viewer;
		if (oldInput != null) {
			((TreeModel) oldInput).removePropertyChangeListener(this);
		}
			
		if (newInput != null) {
			model = (TreeModel) newInput;
			((TreeModel) newInput).addPropertyChangeListener(this);
		}
		
	}

	/**
	 * Gets the elements.
	 *
	 * @param inputElement
	 *            the input element
	 * @return TreeContentProvider
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#getElements(java.lang.Object)
	 */
	public Object[] getElements(Object inputElement) {
		return new Object[] {model.getRoot()};
	}

	/**
	 * Gets the children.
	 *
	 * @param parentElement
	 *            the parent element
	 * @return TreeContentProvider
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#getChildren(java.lang.Object)
	 */
	public Object[] getChildren(Object parentElement) {
		return ((TreeNode) parentElement).getChilds().toArray();
	}

	/**
	 * Gets the parent.
	 *
	 * @param element
	 *            the element
	 * @return TreeContentProvider
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#getParent(java.lang.Object)
	 */
	public Object getParent(Object element) {
		return ((TreeNode) element).getParent();
	}

	/**
	 * Checks for children.
	 *
	 * @param element
	 *            the element
	 * @return TreeContentProvider
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#hasChildren(java.lang.Object)
	 */
	public boolean hasChildren(Object element) {
		List<TreeNode> children = ((TreeNode) element).getChilds();
		return !(children == null || children.size() == 0);
	}

	/**
	 * Property change.
	 *
	 * @param evt
	 *            TreeContentProvider
	 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
	 */
	public void propertyChange(PropertyChangeEvent evt) {
		if (TreeModel.ADD.equals(evt.getPropertyName())) { 
			Object[] values = (Object[])evt.getNewValue();
			viewer.add(values[0], values[1]);
		}
		if (TreeModel.REMOVE.equals(evt.getPropertyName())) {
			viewer.remove(evt.getNewValue());
		}
		
	}

}
