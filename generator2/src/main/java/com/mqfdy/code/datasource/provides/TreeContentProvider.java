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
	 * @see org.eclipse.jface.viewers.IContentProvider#dispose() TreeContentProvider
	 */
	public void dispose() {

	}

	/**
	 * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
	 * @param viewer
	 * @param oldInput
	 * @param newInput TreeContentProvider
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
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#getElements(java.lang.Object)
	 * @param inputElement
	 * @return TreeContentProvider
	 */
	public Object[] getElements(Object inputElement) {
		return new Object[] {model.getRoot()};
	}

	/**
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#getChildren(java.lang.Object)
	 * @param parentElement
	 * @return TreeContentProvider
	 */
	public Object[] getChildren(Object parentElement) {
		return ((TreeNode) parentElement).getChilds().toArray();
	}

	/**
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#getParent(java.lang.Object)
	 * @param element
	 * @return TreeContentProvider
	 */
	public Object getParent(Object element) {
		return ((TreeNode) element).getParent();
	}

	/**
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#hasChildren(java.lang.Object)
	 * @param element
	 * @return TreeContentProvider
	 */
	public boolean hasChildren(Object element) {
		List<TreeNode> children = ((TreeNode) element).getChilds();
		return !(children == null || children.size() == 0);
	}

	/**
	 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
	 * @param evt TreeContentProvider
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
