package com.mqfdy.code.designer.editor.policies;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ContainerEditPolicy;
import org.eclipse.gef.requests.CreateRequest;

/**
 * Edit policy for BusinessClassFigures and ObjectFigures
 * 
 * @author mqfdy
 * 
 */
public class SimpleContainerEditPolicy extends ContainerEditPolicy {

	/**
	 * Forbid moving of elements outside its parent figure.
	 */
	@Override
	protected Command getCreateCommand(CreateRequest request) {
		return null;
	}

}
