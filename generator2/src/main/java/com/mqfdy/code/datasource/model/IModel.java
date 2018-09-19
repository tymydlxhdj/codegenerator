package com.mqfdy.code.datasource.model;


// TODO: Auto-generated Javadoc
/**
 * IModel.
 *
 * @author mqfdy
 */
public interface IModel extends IModelElement {

	/**
	 * Gets the element.
	 *
	 * @author mqfdy
	 * @param id
	 *            the id
	 * @return the element
	 * @Date 2018-9-3 11:38:38
	 */
	IModelElement getElement(String id);

	/**
	 * Adds the change listener.
	 *
	 * @author mqfdy
	 * @param listener
	 *            the listener
	 * @Date 2018-9-3 11:38:38
	 */
	void addChangeListener(IModelChangeListener listener);

	/**
	 * Removes the change listener.
	 *
	 * @author mqfdy
	 * @param listener
	 *            the listener
	 * @Date 2018-9-3 11:38:38
	 */
	void removeChangeListener(IModelChangeListener listener);
}
