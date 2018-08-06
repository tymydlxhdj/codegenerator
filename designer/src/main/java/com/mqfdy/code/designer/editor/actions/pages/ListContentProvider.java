package com.mqfdy.code.designer.editor.actions.pages;

import java.util.List;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

/**
 * 默认的对list的content provider
 * 
 * @author mqfdy
 */
public class ListContentProvider implements IStructuredContentProvider {

	public Object[] getElements(Object inputElement) {
		if (inputElement instanceof List) {
			return ((List<?>) inputElement).toArray();
		}
		return null;
	}

	public void dispose() {
	}

	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
	}

}
