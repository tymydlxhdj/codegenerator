package com.mqfdy.code.designer.views.valiresult;

import java.util.Collection;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

public class FindObjectConentProvider implements IStructuredContentProvider {
	public void dispose() {
	}

	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
	}

	@SuppressWarnings("rawtypes")
	public Object[] getElements(Object inputElement) {
		if (inputElement instanceof Collection) {
			Object[] objects = ((Collection) inputElement).toArray();
			return objects;
		}
		return new Object[0];
	}
}
