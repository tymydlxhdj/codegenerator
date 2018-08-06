package com.mqfdy.code.datasource.model;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;

/**
 * 
 * @author mqfdy
 *
 */
public interface IModelElement extends IAdaptable {

	/** Character used for delimiting nodes within an element's unique id */
	char ID_DELIMITER = '|';

	/**
	 * Character used separate an element's type and name within an element's
	 * unique id
	 */
	char ID_SEPARATOR = ':';

	IModelElement[] NO_CHILDREN = new IModelElement[0];

	/**
	 * Returns the element directly containing this element, or
	 * <code>null</code> if this element has no parent.
	 * 
	 * @return the parent element, or <code>null</code> if this element has no
	 *         parent
	 */
	IModelElement getElementParent();

	/**
	 * Returns an array with all children of this element.
	 * 
	 * @return an array with the children elements
	 */
	IModelElement[] getElementChildren();

	/**
	 * Returns the name of this element.
	 * 
	 * @return the element's name
	 */
	String getElementName();

	/**
	 * Returns this element's kind encoded as an integer. This is a handle-only
	 * method.
	 * 
	 * @return the kind of element; e.g. one of the constants declared in
	 *         {@link ISpringModelElementTypes}
	 */
	int getElementType();

	/**
	 * Returns the unique ID of this element.
	 * 
	 * @return the element's unique ID
	 */
	String getElementID();

	/**
	 * Accepts the given visitor. The visitor's <code>visit</code> method is
	 * called with this model element. If the visitor returns <code>true</code>,
	 * this method visits this element's members.
	 * 
	 * @param visitor
	 *            the visitor
	 * @param monitor
	 *            the progress monitor used to give feedback on progress and to
	 *            check for cancelation
	 * @see IModelElementVisitor#visit(IModelElement)
	 */
	void accept(IModelElementVisitor visitor, IProgressMonitor monitor);
}
