package com.mqfdy.code.designer.provider;

import java.util.Collection;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.mqfdy.code.model.Property;

public class PropertyConentProvider implements IStructuredContentProvider {
	public void dispose() {
	}

	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
	}

	@SuppressWarnings("rawtypes")
	public Object[] getElements(Object inputElement) {
		if (inputElement instanceof Collection) {
			Object[] objects = ((Collection) inputElement).toArray();
			if (objects != null) {
				for (int i = 0; i < objects.length; i++) {
					Property element = (Property) objects[i];
					element.setOrderNum(i + 1);
				}
			}
			return objects;
		}
		return new Object[0];
	}

}