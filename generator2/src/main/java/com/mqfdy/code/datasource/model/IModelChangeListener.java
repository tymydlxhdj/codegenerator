package com.mqfdy.code.datasource.model;

/**
 * 
 * @author mqfdy
 *
 */
public interface IModelChangeListener {

	/**
	 * Notifies that one or more attributes of one or more model elements have
	 * changed. The specific details of the change are described by the given
	 * event.
	 * 
	 * @param event
	 *            the change event
	 */
	public void elementChanged(ModelChangeEvent event);
}
