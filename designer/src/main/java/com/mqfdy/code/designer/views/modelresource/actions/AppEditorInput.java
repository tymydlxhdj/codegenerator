package com.mqfdy.code.designer.views.modelresource.actions;

import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

public class AppEditorInput implements IEditorInput {
	IPath path;

	public AppEditorInput(IPath path) {
		// TODO Auto-generated constructor stub 6
		this.path = path;
	}

	public boolean exists() {
		// TODO Auto-generated method stub12
		return path.toFile().exists();
	}

	public ImageDescriptor getImageDescriptor() {
		// TODO Auto-generated method stub19
		return null;
	}

	public String getName() {
		// TODO Auto-generated method stub26
		return path.toString();
	}

	public IPersistableElement getPersistable() {
		// TODO Auto-generated method stub33
		return null;
	}

	public String getToolTipText() {
		// TODO Auto-generated method stub40
		return path.toString();
	}

	public Object getAdapter(Class adapter) {
		// TODO Auto-generated method stub47
		return null;
	}

	public IPath getPath() {
		// TODO Auto-generated method stub54
		return path;
	}
}
