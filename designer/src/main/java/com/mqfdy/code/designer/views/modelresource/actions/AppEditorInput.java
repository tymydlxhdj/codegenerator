package com.mqfdy.code.designer.views.modelresource.actions;

import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

// TODO: Auto-generated Javadoc
/**
 * The Class AppEditorInput.
 *
 * @author mqfdy
 */
public class AppEditorInput implements IEditorInput {
	
	/** The path. */
	IPath path;

	/**
	 * Instantiates a new app editor input.
	 *
	 * @param path
	 *            the path
	 */
	public AppEditorInput(IPath path) {
		// TODO Auto-generated constructor stub 6
		this.path = path;
	}

	/**
	 * @return
	 */
	public boolean exists() {
		// TODO Auto-generated method stub12
		return path.toFile().exists();
	}

	/**
	 * @return
	 */
	public ImageDescriptor getImageDescriptor() {
		// TODO Auto-generated method stub19
		return null;
	}

	/**
	 * @return
	 */
	public String getName() {
		// TODO Auto-generated method stub26
		return path.toString();
	}

	/**
	 * @return
	 */
	public IPersistableElement getPersistable() {
		// TODO Auto-generated method stub33
		return null;
	}

	/**
	 * @return
	 */
	public String getToolTipText() {
		// TODO Auto-generated method stub40
		return path.toString();
	}

	/**
	 * Gets the adapter.
	 *
	 * @author mqfdy
	 * @param adapter
	 *            the adapter
	 * @return the adapter
	 * @Date 2018-09-03 09:00
	 */
	public Object getAdapter(Class adapter) {
		// TODO Auto-generated method stub47
		return null;
	}

	/**
	 * Gets the path.
	 *
	 * @author mqfdy
	 * @return the path
	 * @Date 2018-09-03 09:00
	 */
	public IPath getPath() {
		// TODO Auto-generated method stub54
		return path;
	}
}
