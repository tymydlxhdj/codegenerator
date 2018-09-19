package com.mqfdy.code.datasource.model;

// TODO: Auto-generated Javadoc
/**
 * The listener interface for receiving IModelChange events. The class that is
 * interested in processing a IModelChange event implements this interface, and
 * the object created with that class is registered with a component using the
 * component's <code>addIModelChangeListener<code> method. When the IModelChange
 * event occurs, that object's appropriate method is invoked.
 *
 * @author mqfdy
 */
public interface IModelChangeListener {

	/**
	 * Notifies that one or more attributes of one or more model elements have
	 * changed. The specific details of the change are described by the given
	 * event.
	 *
	 * @author mqfdy
	 * @param event
	 *            the change event
	 * @Date 2018-9-3 11:38:39
	 */
	public void elementChanged(ModelChangeEvent event);
}
