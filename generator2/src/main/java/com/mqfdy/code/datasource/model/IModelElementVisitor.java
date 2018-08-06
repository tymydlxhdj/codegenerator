package com.mqfdy.code.datasource.model;

import org.eclipse.core.runtime.IProgressMonitor;

public interface IModelElementVisitor {

	/**
	 * Visits the given model element.
	 * 
	 * @param element
	 *            the model element to visit
	 * @param monitor
	 *            the progress monitor used to give feedback on progress and to
	 *            check for cancelation
	 * @return <code>true</code> if the elements's members should be visited;
	 *         <code>false</code> if they should be skipped
	 */
	boolean visit(IModelElement element, IProgressMonitor monitor);
}
