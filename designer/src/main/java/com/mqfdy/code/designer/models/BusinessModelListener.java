package com.mqfdy.code.designer.models;

import com.mqfdy.code.model.AbstractModelElement;

// TODO: Auto-generated Javadoc
/**
 * The listener interface for receiving businessModel events. The class that is
 * interested in processing a businessModel event implements this interface, and
 * the object created with that class is registered with a component using the
 * component's <code>addBusinessModelListener<code> method. When the
 * businessModel event occurs, that object's appropriate method is invoked.
 *
 * @see BusinessModelEvent
 */
public interface BusinessModelListener {

	/**
	 * Model element add.
	 *
	 * @author mqfdy
	 * @param element
	 *            the element
	 * @Date 2018-09-03 09:00
	 */
	public void modelElementAdd(AbstractModelElement element);

	/**
	 * Model element update.
	 *
	 * @author mqfdy
	 * @param element
	 *            the element
	 * @Date 2018-09-03 09:00
	 */
	public void modelElementUpdate(AbstractModelElement element);

	/**
	 * Model element delete.
	 *
	 * @author mqfdy
	 * @param element
	 *            the element
	 * @Date 2018-09-03 09:00
	 */
	public void modelElementDelete(AbstractModelElement element);

	/**
	 * Model save.
	 *
	 * @author mqfdy
	 * @param element
	 *            the element
	 * @Date 2018-09-03 09:00
	 */
	public void modelSave(AbstractModelElement element);

	/**
	 * Repository model add.
	 *
	 * @author mqfdy
	 * @param element
	 *            the element
	 * @Date 2018-09-03 09:00
	 */
	public void repositoryModelAdd(AbstractModelElement element);
}
