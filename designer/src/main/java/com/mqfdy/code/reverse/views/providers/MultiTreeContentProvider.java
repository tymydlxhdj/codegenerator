package com.mqfdy.code.reverse.views.providers;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;

import com.mqfdy.code.reverse.views.beans.TreeNode;
import com.mqfdy.code.reverse.views.models.TreeModel;

public class MultiTreeContentProvider implements ITreeContentProvider, PropertyChangeListener {

	private TreeViewer viewer;
	private List<TreeModel> modelList = new ArrayList<TreeModel>();
	
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@SuppressWarnings("unchecked")
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		this.viewer = (TreeViewer) viewer;
		if (oldInput != null) {
			List<TreeModel> modelList = (List<TreeModel>) oldInput;
			for(TreeModel model : modelList) {
				model.removePropertyChangeListener(this);
			}
		}
			
		if (newInput != null) {
			modelList = (List<TreeModel>) newInput;
			for(TreeModel model : modelList) {
				model.addPropertyChangeListener(this);
			}
		}
		
	}

	public Object[] getElements(Object inputElement) {
		Object[] array = new Object[modelList.size()];
		int index = 0;
		for(TreeModel model : modelList) {
			array[index++] = model.getRoot();
		}
		return array;
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
