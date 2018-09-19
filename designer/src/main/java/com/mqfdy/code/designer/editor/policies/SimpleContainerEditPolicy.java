package com.mqfdy.code.designer.editor.policies;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ContainerEditPolicy;
import org.eclipse.gef.requests.CreateRequest;

// TODO: Auto-generated Javadoc
/**
 * Edit policy for BusinessClassFigures and ObjectFigures.
 *
 * @author mqfdy
 */
public class SimpleContainerEditPolicy extends ContainerEditPolicy {

	/**
	 * Forbid moving of elements outside its parent figure.
	 *
	 * @author mqfdy
	 * @param request
	 *            the request
	 * @return the creates the command
	 * @Date 2018-09-03 09:00
	 */
	@Override
	protected Command getCreateCommand(CreateRequest request) {
		return null;
	}

}
