package com.mqfdy.code.datasource.model;

import org.eclipse.core.runtime.IProgressMonitor;

// TODO: Auto-generated Javadoc
/**
 * The Interface IModelElementVisitor.
 *
 * @author mqfdy
 */
public interface IModelElementVisitor {

	/**
	 * Visits the given model element.
	 *
	 * @author mqfdy
	 * @param element
	 *            the model element to visit
	 * @param monitor
	 *            the progress monitor used to give feedback on progress and to
	 *            check for cancelation
	 * @return <code>true</code> if the elements's members should be visited;
	 *         <code>false</code> if they should be skipped
	 * @Date 2018-9-3 11:38:27
	 */
	boolean visit(IModelElement element, IProgressMonitor monitor);
}
