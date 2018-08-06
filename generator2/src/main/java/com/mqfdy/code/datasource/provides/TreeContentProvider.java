package com.mqfdy.code.datasource.provides;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;

import com.mqfdy.code.datasource.mapping.TreeModel;
import com.mqfdy.code.datasource.mapping.TreeNode;

public class TreeContentProvider implements ITreeContentProvider, PropertyChangeListener {

	private TreeViewer viewer;
	
	private TreeModel model;
	
	public void dispose() {

	}

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

	public Object[] getElements(Object inputElement) {
		return new Object[] {model.getRoot()};
	}

	public Object[] getChildren(Object parentElement) {
		return ((TreeNode) parentElement).getChilds().toArray();
	}

	public Object getParent(Object element) {
		return ((TreeNode) element).getParent();
	}

	public boolean hasChildren(Object element) {
		List<TreeNode> children = ((TreeNode) element).getChilds();
		return !(children == null || children.size() == 0);
	}

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
