package com.mqfdy.code.designer.editor.policies;

import java.util.Iterator;
import java.util.List;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.gef.requests.GroupRequest;

// TODO: Auto-generated Javadoc
/**
 * 透视图中包含的节点位置发生移动造成两个图形上下重合时的编辑策略.
 *
 * @author mqfdy
 */
public class OmContainerEditPolicy extends
		org.eclipse.gef.editpolicies.ContainerEditPolicy {

	/**
	 * Gets the creates the command.
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

	/**
	 * Gets the orphan children command.
	 *
	 * @author mqfdy
	 * @param request
	 *            the request
	 * @return the orphan children command
	 * @Date 2018-09-03 09:00
	 */
	@Override
	/*
	 * Called when one or more elements are removed from the associated
	 * container (e.g. a {@link
	 * org.sotower.studio.model.editors.classdiagram.model.nodes.PackageElement}
	 * or a {@link ClassDiagram}).
	 */
	protected Command getOrphanChildrenCommand(GroupRequest request) {
		// create commands to remove all parts from their parents (= orphan
		// them)
		List parts = request.getEditParts();

		// create new compound command : can contain various commands
		CompoundCommand commands = new CompoundCommand(
				"compound orphan command");

		// for each edit part add the corresponding NodeOrphanChildCommand to
		// the compound command
		for (Iterator partIt = parts.iterator(); partIt.hasNext();) {
			EditPart part = (EditPart) partIt.next();
			// NodeOrphanChildCommand：当选中一个对象拖动到另外一个对象之中时的命名
			// NodeOrphanChildCommand command = new NodeOrphanChildCommand(
			// (IContainerElement) getHost().getModel(),
			// (NodeModelElement) part.getModel());
			// command.setLabel("orphan command");
			// commands.add(command);
		}
		return commands.unwrap();
	}

	/**
	 * Gets the command.
	 *
	 * @author mqfdy
	 * @param request
	 *            the request
	 * @return the command
	 * @Date 2018-09-03 09:00
	 */
	@Override
	public Command getCommand(Request request) {
		// TODO Auto-generated method stub
		return super.getCommand(request);
	}

}
