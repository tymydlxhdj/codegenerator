package com.mqfdy.code.datasource.model;


/**
 * IModel
 * @author mqfdy
 *
 */
public interface IModel extends IModelElement {

	IModelElement getElement(String id);

	void addChangeListener(IModelChangeListener listener);

	void removeChangeListener(IModelChangeListener listener);
}
