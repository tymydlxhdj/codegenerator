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

// TODO: Auto-generated Javadoc
/**
 * The Class MultiTreeContentProvider.
 *
 * @author mqfdy
 */
public class MultiTreeContentProvider implements ITreeContentProvider, PropertyChangeListener {

	/** The viewer. */
	private TreeViewer viewer;
	
	/** The model list. */
	private List<TreeModel> modelList = new ArrayList<TreeModel>();
	
	/**
	 * 
	 */
	public void dispose() {
		// TODO Auto-generated method stub

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
		Object[] array = new Object[modelList.size()];
		int index = 0;
		for(TreeModel model : modelList) {
			array[index++] = model.getRoot();
		}
		return array;
	}

	/**
	 * Gets the children.
	 *
	 * @author mqfdy
	 * @param parentElement
	 *            the parent element
	 * @return the children
	 * @Date 2018-09-03 09:00
	 */
	public Object[] getChildren(Object parentElement) {
		return ((TreeNode) parentElement).getChilds().toArray();
	}

	/**
	 * Gets the parent.
	 *
	 * @author mqfdy
	 * @param element
	 *            the element
	 * @return the parent
	 * @Date 2018-09-03 09:00
	 */
	public Object getParent(Object element) {
		return ((TreeNode) element).getParent();
	}

	/**
	 * Checks for children.
	 *
	 * @author mqfdy
	 * @param element
	 *            the element
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	public boolean hasChildren(Object element) {
		List<TreeNode> children = ((TreeNode) element).getChilds();
		return !(children == null || children.size() == 0);
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
		if (TreeModel.ADD.equals(evt.getPropertyName())) { 
			Object[] values = (Object[])evt.getNewValue();
			viewer.add(values[0], values[1]);
		}
		if (TreeModel.REMOVE.equals(evt.getPropertyName())) {
			viewer.remove(evt.getNewValue());
		}
		
	}
}
